package com.tenjava.entries.PaulBGD.t3;

import com.tenjava.entries.PaulBGD.t3.events.NaturalEvent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

public class NaturalDisasterStarter extends BukkitRunnable {

    private int id = 0;

    public NaturalDisasterStarter() {
        this.runTaskTimer(TenJava.getPlugin(), 20l, 20l);
    }

    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            for (NaturalEvent disaster : NaturalEvent.getEvents()) {
                if (disaster.getAllowedWorlds().contains(world)) {
                    // we can cause DISASTERS! WHOO!
                    for (Chunk chunk : world.getLoadedChunks()) {
                        for (int x = 0; x < 16; x++) {
                            z:
                            for (int z = 0; z < 16; z++) {
                                Block block = chunk.getBlock(x, 0, z);
                                while (block.getType() != Material.AIR) {
                                    block = block.getRelative(BlockFace.UP);
                                    if (block.getY() >= world.getMaxHeight()) {
                                        continue z;
                                    }
                                }
                                block = block.getRelative(BlockFace.DOWN);
                                if (disaster.canOccur(block) && TenJava.getRandom().nextInt(100) == disaster.getChance()) {
                                    disaster.start(block, id++);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
