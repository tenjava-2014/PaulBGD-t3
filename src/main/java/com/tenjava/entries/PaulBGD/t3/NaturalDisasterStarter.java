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

    public static int id = 0;

    public NaturalDisasterStarter() {
        this.runTaskTimer(TenJava.getPlugin(), 5 * 20l, 5 * 20l);
    }

    @Override
    public void run() {
        for (final World world : Bukkit.getWorlds()) {
            for (final NaturalEvent disaster : NaturalEvent.getEvents()) {
                if (disaster.getAllowedWorlds().contains(world)) {
                    // we can cause DISASTERS! WHOO!
                    int i = 0;
                    for (final Chunk chunk : world.getLoadedChunks()) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (int x = 0; x < 16; x++) {
                                    z:
                                    for (int z = 0; z < 16; z++) {
                                        Block block = world.getHighestBlockAt(chunk.getBlock(x, 0, z).getLocation());
                                        while (block.getType() != Material.AIR) {
                                            block = block.getRelative(BlockFace.UP);
                                            if (block.getY() >= world.getMaxHeight()) {
                                                continue z;
                                            }
                                        }
                                        block = block.getRelative(BlockFace.DOWN);
                                        if (disaster.canOccur(block) && TenJava.getRandom().nextInt(5000000) < disaster.getChance()) {
                                            TenJava.getPlugin().getLogger().info("Disaster!: " + disaster.getName());
                                            disaster.start(block, id++);
                                        }
                                    }
                                }
                            }
                        }.runTaskLater(TenJava.getPlugin(), i * 5l);
                    }
                }
            }
        }
    }
}
