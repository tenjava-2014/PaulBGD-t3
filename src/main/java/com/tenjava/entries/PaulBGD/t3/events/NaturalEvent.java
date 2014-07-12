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

    /**
     * Creates a new natural event
     *
     * @param name   of disaster
     * @param chance of happened (higher is better)
     * @param item   in the menu
     */
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

    /**
     * Gets all Events. You can use this to add your own as well
     *
     * @return natural events
     */
    public static List<NaturalEvent> getEvents() {
        return events;
    }

    /**
     * If your event can occur on this block
     *
     * @param block
     * @return true, if successful
     */
    public abstract boolean canOccur(Block block);

    /**
     * Starts your event
     *
     * @param block to start on
     * @param id    the unique id
     */
    public abstract void start(Block block, int id);

    /**
     * Gets the name of the event
     *
     * @return event name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the menu item
     *
     * @return menu item
     */
    public Material getItem() {
        return this.item;
    }

    /**
     * Returns chance of happening
     *
     * @return chance of happening
     */
    public int getChance() {
        return this.chance;
    }

    /**
     * Sets chance of happening
     *
     * @param chance of happening
     */
    public void setChance(int chance) {
        this.chance = chance;
    }

    /**
     * Gets the worlds it is allowed in
     *
     * @return allowed worlds
     */
    public List<World> getAllowedWorlds() {
        return this.allowedWorlds;
    }

}
