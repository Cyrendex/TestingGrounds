package me.Cyrendex.testingGrounds.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PeekCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.hasPermission("TestingGrounds.feed")) {
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    String peekOut = item.getItemMeta().getCustomModelDataComponent().toString();
                    player.sendMessage(peekOut);
                    System.out.println(peekOut);
                } else {
                    sender.sendMessage(ChatColor.RED + "Only a player can check a handheld item.");
                }
            }
        } else {
            sender.sendMessage(ChatColor.ITALIC + "Your eyes aren't sharp enough to properly appraise items.");
        }

        return true;
    }
}
