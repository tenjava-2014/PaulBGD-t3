package com.tenjava.entries.PaulBGD.t3.events.water;

import com.tenjava.entries.PaulBGD.t3.TenJava;
import com.tenjava.entries.PaulBGD.t3.events.NaturalEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

public class Flood extends NaturalEvent {

    private final BlockFace[] faces = new BlockFace[]{BlockFace.NORTH, BlockFace.WEST, BlockFace.EAST, BlockFace.WEST};

    public Flood() {
        super("Flood", 3);

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
        wave(block.getRelative(BlockFace.UP));
    }

    private void wave(Block block) {
        for(BlockFace face : faces) {
            final Block relative = block.getRelative(face);
            if(relative.getType() == Material.AIR && TenJava.getRandom().nextInt(50) != 0) {
                relative.setType(Material.WATER);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        wave(relative);
                    }
                }.runTaskLater(TenJava.getPlugin(), 15);
            }
        }
    }
}
