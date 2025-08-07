package me.Cyrendex.testingGrounds.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender.hasPermission("TestingGrounds.god")) {
            if (!(commandSender instanceof Player)) return true;

            Player player = (Player) commandSender;

            if (player.isInvulnerable()) {
                player.setInvulnerable(false);
                player.sendMessage(ChatColor.RED + "You are no longer invulnerable.");
            } else {
                player.setInvulnerable(true);
                player.sendMessage(ChatColor.GREEN + "You are now invulnerable.");
            }
        } else {
            commandSender.sendMessage(ChatColor.BOLD + "You haven't attained" + ChatColor.YELLOW + "godhood.");
        }


        return true;
    }
}
