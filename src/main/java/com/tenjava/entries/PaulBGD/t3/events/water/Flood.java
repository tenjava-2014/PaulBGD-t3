package com.tenjava.entries.PaulBGD.t3.events.water;

import com.tenjava.entries.PaulBGD.t3.events.NaturalEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Flood extends NaturalEvent {

    private final BlockFace[] faces = new BlockFace[]{BlockFace.NORTH, BlockFace.WEST, BlockFace.EAST, BlockFace.WEST};

    public Flood() {
        super("Flood", 10);

        NaturalEvent.getEvents().add(this);
    }

    @Override
    public boolean canOccur(Block block) {
        return block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER;
    }

    @Override
    public void start(Block block, int id) {
        while (block.getRelative(BlockFace.UP).getType() == Material.STATIONARY_WATER || block.getRelative(BlockFace.UP).getType() == Material.WATER) {
            block = block.getRelative(BlockFace.UP);
        }
        if (!block.getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
            return;
        }
        block.getRelative(BlockFace.UP).setType(Material.WATER);
    }

}
