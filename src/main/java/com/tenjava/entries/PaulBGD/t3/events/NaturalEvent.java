package com.tenjava.entries.PaulBGD.t3.events;

import com.tenjava.entries.PaulBGD.t3.TenJava;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public abstract class NaturalEvent {

    private final static List<NaturalEvent> events = new ArrayList<>();

    private final String name;
    private final Material item;
    private final List<World> allowedWorlds = new ArrayList<>();
    private int chance;

    public NaturalEvent(String name, int chance, Material item) {
        this.name = name;
        this.item = item;
        this.chance = chance;

        for (NaturalEvent event : events) {
            if (event.getName().equals(name)) {
                TenJava.getPlugin().getLogger().warning(String.format("There is a duplicate natural event with the name of '%s'!", name));
                return;
            }
        }
        TenJava.addWorlds(this);
    }

    public static List<NaturalEvent> getEvents() {
        return events;
    }

    public abstract boolean canOccur(Block block);

    public abstract void start(Block block, int id);

    public String getName() {
        return name;
    }

    public Material getItem() {
        return this.item;
    }

    public int getChance() {
        return this.chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public List<World> getAllowedWorlds() {
        return this.allowedWorlds;
    }

}
