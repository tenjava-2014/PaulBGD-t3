package com.tenjava.entries.PaulBGD.t3.commands;

import com.tenjava.entries.PaulBGD.t3.TenJava;
import com.tenjava.entries.PaulBGD.t3.utils.DisasterMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisasterCommand implements CommandExecutor {

    private final DisasterMenu menu = new DisasterMenu();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You need to be in game to use this command!");
            return true;
        }
        if (sender.hasPermission("naturaldisasters.menu")) {
            ((Player) sender).openInventory(menu.getInventory());
        } else {
            sender.sendMessage(String.format("%s%sYou do not have permission!", TenJava.title, ChatColor.RED));
        }
        return true;
    }
}
