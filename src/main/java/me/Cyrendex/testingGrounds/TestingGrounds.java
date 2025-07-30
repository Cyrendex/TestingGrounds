package me.Cyrendex.testingGrounds;

import me.Cyrendex.testingGrounds.commands.FeedCommand;
import me.Cyrendex.testingGrounds.commands.GodCommand;
import me.Cyrendex.testingGrounds.listeners.SnowballListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestingGrounds extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Plugin started.");
        getCommand("god").setExecutor(new GodCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getServer().getPluginManager().registerEvents(new SnowballListener(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Plugin stopped.");
    }
}
