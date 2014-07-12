package com.tenjava.entries.PaulBGD.t3;

import com.tenjava.entries.PaulBGD.t3.events.NaturalEvent;
import org.bukkit.block.Block;

public class NaturalDisaster {

    /**
     * Creates a disaster of your choice, on a specific block!
     *
     * @param disaster
     * @param block
     */
    public static void createDisaster(Disaster disaster, Block block) {
        getEvent(disaster).start(block, NaturalDisasterStarter.id++);
    }

    /**
     * Checks if you can create a disaster at a specific spot
     *
     * @param disaster
     * @param block
     * @return can create disaster at spot
     */
    public static boolean canCreateDisaster(Disaster disaster, Block block) {
        return getEvent(disaster).canOccur(block);
    }

    /**
     * Gets the event object from the Disaster Enum
     *
     * @param disaster
     * @return event
     */
    public static NaturalEvent getEvent(Disaster disaster) {
        for (NaturalEvent event : NaturalEvent.getEvents()) {
            if (event.getName().equalsIgnoreCase(disaster.name())) {
                return event;
            }
        }
        throw new IllegalArgumentException("Illegal disaster!");
    }

    public enum Disaster {
        /**
         * Represents a tornado disaster
         */
        TORNADO,
        /**
         * Represents a mudslide disaster
         */
        MUDSLIDE,
        /**
         * Represents a flood disaster
         */
        FLOOD;
    }

}
