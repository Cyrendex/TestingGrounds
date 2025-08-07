package me.Cyrendex.testingGrounds.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.hasPermission("TestingGrounds.feed")) {
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    player.setSaturation(20.0f);
                    player.setFoodLevel(20);
                    player.sendMessage(ChatColor.GREEN + "Yummy in your tummy.");
                } else {
                    sender.sendMessage(ChatColor.RED + "Only a player can use feed without arguments");
                }
            } else if (args.length == 1) {
                Player player = Bukkit.getPlayerExact(args[0]);

                if (player != null) {
                    player.setSaturation(20.0f);
                    player.setFoodLevel(20);
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + sender.getName() + " filled you up.");
                    sender.sendMessage(ChatColor.GREEN + "You filled up " + player.getName());
                } else {
                    sender.sendMessage(ChatColor.RED + args[0] + " not in the player list.");
                }
            }
        } else {
            sender.sendMessage(ChatColor.ITALIC + "You don't have the culinary skills to make something edible.");
        }

        return true;
    }
}
