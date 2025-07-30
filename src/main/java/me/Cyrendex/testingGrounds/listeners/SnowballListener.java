package me.Cyrendex.testingGrounds.listeners;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.Random;

public class SnowballListener implements Listener {
    @EventHandler
    public void onSnowballHit(ProjectileHitEvent e) {
        Block block = e.getHitBlock();
        Projectile proj = e.getEntity();
        if (block != null) {
            if (proj.getType() == EntityType.SNOWBALL) {
                DyeColor[] colors = DyeColor.values();
                DyeColor randomColor = colors[new Random().nextInt(colors.length)];
                Material woolMaterial = Material.valueOf(randomColor.name() + "_WOOL");
                block.setType(woolMaterial);
            }
        }
    }
}
