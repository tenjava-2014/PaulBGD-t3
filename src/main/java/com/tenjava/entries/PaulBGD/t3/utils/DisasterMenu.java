package com.tenjava.entries.PaulBGD.t3.utils;

import com.tenjava.entries.PaulBGD.t3.NaturalDisasterStarter;
import com.tenjava.entries.PaulBGD.t3.TenJava;
import com.tenjava.entries.PaulBGD.t3.events.NaturalEvent;
import java.util.Arrays;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DisasterMenu extends GUI {

    public DisasterMenu() {
        super(ChatColor.BOLD + "Disaster Menu", 9);

        for (NaturalEvent event : NaturalEvent.getEvents()) {
            ItemStack item = new ItemStack(event.getItem());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(String.format("%s%s%s", ChatColor.AQUA, ChatColor.BOLD, event.getName()));
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Creates a " + event.getName() + " where you", ChatColor.GRAY + "are looking!"));
            item.setItemMeta(meta);
            this.addItem(item);
        }

        ItemStack item = new ItemStack(Material.INK_SACK, 1, (short) DyeColor.RED.getDyeData());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(String.format("%s%sClose", ChatColor.RED, ChatColor.BOLD));
        item.setItemMeta(meta);
        this.setItem(item, 8);
    }

    @Override
    protected void onClick(Player whoClicked, ItemStack currentItem) {
        String title = ChatColor.stripColor(currentItem.getItemMeta().getDisplayName());
        if (title.equals("Close")) {
            whoClicked.closeInventory();
        } else {
            for (NaturalEvent event : NaturalEvent.getEvents()) {
                if (event.getName().equals(title)) {
                    if (whoClicked.hasPermission("naturaldisasters." + event.getName().toLowerCase())) {
                        Block block = whoClicked.getTargetBlock(null, 200);
                        if (event.canOccur(block)) {
                            event.start(block, NaturalDisasterStarter.id++);
                        } else {
                            whoClicked.sendMessage(String.format("%s%sYou cannot use this here!", TenJava.title, ChatColor.RED));
                        }
                    } else {
                        whoClicked.sendMessage(String.format("%s%sYou do not have permission!", TenJava.title, ChatColor.RED));
                    }
                }
            }
        }
    }
}
