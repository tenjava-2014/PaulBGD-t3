package com.tenjava.entries.PaulBGD.t3.commands;

import com.tenjava.entries.PaulBGD.t3.events.NaturalEvent;
import java.util.Random;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender.isOp() && sender instanceof Player) {
            NaturalEvent.getEvents().get(2).start(((Player) sender).getTargetBlock(null, 10), new Random().nextInt(500));
        }
        return false;
    }
}
