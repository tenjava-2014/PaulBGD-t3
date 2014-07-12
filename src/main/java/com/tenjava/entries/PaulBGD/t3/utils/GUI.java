package com.tenjava.entries.PaulBGD.t3.utils;

import com.tenjava.entries.PaulBGD.t3.TenJava;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class GUI implements Listener {

    private final Inventory inventory;

    public GUI(String title, int size) {
        this.inventory = Bukkit.createInventory(null, size, title);

        Bukkit.getPluginManager().registerEvents(this, TenJava.getPlugin());
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    protected void addItem(ItemStack... item) {
        this.inventory.addItem(item);
    }

    protected void setItem(ItemStack item, int position) {
        this.inventory.setItem(position, item);
    }

    protected void setItem(ItemStack item, int row, int position) {
        this.setItem(item, (row * 9) + position);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

    }

}
