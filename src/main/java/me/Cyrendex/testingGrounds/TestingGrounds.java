package me.Cyrendex.testingGrounds;

import me.Cyrendex.testingGrounds.commands.FeedCommand;
import me.Cyrendex.testingGrounds.commands.GodCommand;
import me.Cyrendex.testingGrounds.commands.PeekCommand;
import me.Cyrendex.testingGrounds.listeners.CursorListener;
import me.Cyrendex.testingGrounds.listeners.SnowballListener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestingGrounds extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Plugin started.");
        getCommand("god").setExecutor(new GodCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("peek").setExecutor(new PeekCommand());
        
        CustomItemManager.init();
        ShapedRecipe cursorRecipe = new ShapedRecipe(new NamespacedKey(this, "cursor"), CustomItemManager.cursor);
        cursorRecipe.shape(" I "," B ","   ");
        cursorRecipe.setIngredient('I', Material.IRON_INGOT);
        cursorRecipe.setIngredient('B', Material.STONE_BUTTON);
        getServer().addRecipe(cursorRecipe);

        getServer().getPluginManager().registerEvents(new SnowballListener(),this);
        getServer().getPluginManager().registerEvents(new CursorListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Plugin stopped.");
    }
}
