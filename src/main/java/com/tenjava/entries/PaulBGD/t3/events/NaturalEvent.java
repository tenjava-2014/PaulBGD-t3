package com.tenjava.entries.PaulBGD.t3.events;

import com.tenjava.entries.PaulBGD.t3.TenJava;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.World;
import org.bukkit.block.Block;

public abstract class NaturalEvent {

    private final static List<NaturalEvent> events = new ArrayList<>();

    private final String name;
    private int chance;

    private final List<World> allowedWorlds = new ArrayList<>();

    public NaturalEvent(String name, int chance) {
        this.name = name;
        this.chance = chance;

        for (NaturalEvent event : events) {
            if (event.getName().equals(name)) {
                TenJava.getPlugin().getLogger().warning(String.format("There is a duplicate natural event with the name of '%s'!", name));
                return;
            }
        }
        TenJava.addWorlds(this);
    }

    public abstract boolean canOccur(Block block);

    public abstract void start(Block block, int id);

    public String getName() {
        return name;
    }

    public int getChance() {
        return this.chance;
    }

    public List<World> getAllowedWorlds() {
        return this.allowedWorlds;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public static List<NaturalEvent> getEvents() {
        return events;
    }

}
