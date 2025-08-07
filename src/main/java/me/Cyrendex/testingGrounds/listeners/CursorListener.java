package me.Cyrendex.testingGrounds.listeners;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class CursorListener implements Listener {
    private final JavaPlugin plugin;
    // Track active "drag" tasks per player
    private final HashMap<UUID, BukkitRunnable> draggingTasks = new HashMap<>();
    private final double TRACE_LENGTH = 15.0;

    public CursorListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getItem();

        if (!isCursor(item)) return; // Cancel operation if the held item isn't a cursor

        e.setCancelled(true);

        switch (e.getAction()) {
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                handleRightClickHoldStart(player);
                break;
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                handleLeftClick(player);
                break;
            default:
                break;
        }
    }

    private void handleRightClickHoldStart(Player player) {
        // Prevent duplicate tasks
        if (draggingTasks.containsKey(player.getUniqueId())) return;

        // Raytrace for entity
        RayTraceResult result = player.getWorld().rayTraceEntities(
                player.getEyeLocation().add(player.getLocation().getDirection().multiply(0.5)),
                player.getLocation().getDirection(),
                TRACE_LENGTH
        );

        if (result == null || result.getHitEntity() == null) return;

        Entity target = result.getHitEntity();

        // Start repeating drag task
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || target.isDead() || !target.isValid()) {
                    stopDragging(player);
                    return;
                }

                if (!isCursor(player.getInventory().getItemInMainHand())) {
                    stopDragging(player);
                    return;
                }

                Location eye = player.getEyeLocation();
                Vector eyeDirection = eye.getDirection().normalize();
                Location dragLocation = eye.clone().add(eyeDirection.multiply(10.0));

                shootParticles(player.getWorld(), eye, dragLocation, eye.getDirection(), eye.distance(dragLocation), 0.5);

                Location entityLocation = target.getLocation();
                Vector velocity = dragLocation.toVector().subtract(entityLocation.toVector()).multiply(0.25);

                if (velocity.lengthSquared() > 1.5) {
                    velocity = velocity.normalize().multiply(1.2); // Cap max speed
                }

                target.setVelocity(velocity);
            }
        };

        task.runTaskTimer(plugin, 0L, 1L);
        draggingTasks.put(player.getUniqueId(), task);
    }

    private void handleLeftClick(Player player) {
        // Basic raytrace to damage
        RayTraceResult result = player.getWorld().rayTraceEntities(
                player.getEyeLocation().add(player.getLocation().getDirection().multiply(0.25)),
                player.getLocation().getDirection(),
                TRACE_LENGTH
        );

        if (result != null && result.getHitEntity() instanceof LivingEntity target) {
            target.damage(10.0, player);
        }
    }

    private void stopDragging(Player player) {
        BukkitRunnable task = draggingTasks.remove(player.getUniqueId());
        if (task != null) task.cancel();
    }

    private boolean isCursor(ItemStack item) {
        if (item == null || item.getType() != Material.TRIPWIRE_HOOK) return false;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasCustomModelDataComponent()) return false;

        return meta.getCustomModelDataComponent().getStrings().contains("cursor");
    }

    private void shootParticles(World world, Location start, Location end, Vector direction, double length, double step) {
        for (double d = 2.0; d < length; d += step) {
            Location point = start.clone().add(direction.clone().multiply(d));

            Particle.DustOptions beamColor = new Particle.DustOptions(Color.WHITE, 0.5F);
            world.spawnParticle(Particle.DUST, point, 1, beamColor);
        }

        Particle.DustOptions tipColor = new Particle.DustOptions(Color.GREEN, 1.5F);
        world.spawnParticle(Particle.DUST, end, 1, tipColor);
    }
}
