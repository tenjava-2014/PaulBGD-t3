package com.tenjava.entries.PaulBGD.t3.events.air;

import com.tenjava.entries.PaulBGD.t3.TenJava;
import com.tenjava.entries.PaulBGD.t3.events.NaturalEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Tornado extends NaturalEvent {

    private final BlockFace[] relative = BlockFace.values();

    public Tornado() {
        super("Tornado", 1, Material.FEATHER);

        NaturalEvent.getEvents().add(this);
    }

    @Override
    public boolean canOccur(Block block) {
        return true;
    }

    @Override
    public void start(Block block, int id) {
        new TornadoObject(block.getLocation());
    }

    public class TornadoObject extends BukkitRunnable {

        private final int maxDuration = (20 + TenJava.getRandom().nextInt(80)) * 4;
        List<Entity> blocks = new ArrayList<>();
        private int duration = 0;
        private Location location;

        public TornadoObject(Location start) {
            this.location = start;

            this.runTaskTimer(TenJava.getPlugin(), 5, 5);
        }

        @Override
        public void run() {
            // gets direction to move, within 0-2 blocks x and z
            double movedX = direction(TenJava.getRandom().nextInt(3));
            double movedZ = direction(TenJava.getRandom().nextInt(3));
            location.add(movedX, 0, movedZ);
            // to make sure that we're the best we can be, we go to the top
            location = location.getWorld().getHighestBlockAt(location).getLocation();

            // we're going to get the surrounding blocks, so we can throw them in the air like we just don't care!
            for (BlockFace face : relative) {
                Block block = location.getBlock().getRelative(face);
                if (block.getRelative(BlockFace.UP).isEmpty() && block.getType() != Material.OBSIDIAN && block.getType() != Material.STONE && block.getType() != Material.BEDROCK) {
                    Material type = block.getType();
                    byte data = block.getData();
                    block.setType(Material.AIR);
                    FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), type, data);
                    fallingBlock.setVelocity(new Vector(0, 5, 0));
                    fallingBlock.setDropItem(false);
                    fallingBlock.teleport(fallingBlock.getLocation().add(0, 1, 0));
                    blocks.add(fallingBlock);
                }
            }
            // now we'll loop our blocks to make them fly (if they're within 4 blocks). We also find other entities and add them to our list
            Iterator<Entity> it = blocks.iterator();
            List<Entity> toAdd = new ArrayList<>();
            while (it.hasNext()) {
                Entity block = it.next();
                if (!block.isValid() || block.isDead()) {
                    it.remove();
                    continue;
                }
                if (block.getLocation().getY() >= location.getY() && block.getLocation().getY() - location.getY() < 40) {
                    double twoDistance = twoDimensionalDistance(block.getLocation(), location);
                    if (twoDistance < 16) {
                        for (Entity entity : block.getNearbyEntities(4, 4, 4)) {
                            if (!blocks.contains(entity)) {
                                toAdd.add(entity);
                            }
                        }
                        block.setVelocity(new Vector(movedX / 3 + direction(TenJava.getRandom().nextDouble() / 2), 0.09 + direction(TenJava.getRandom().nextDouble()), movedZ / 3 + direction(TenJava.getRandom().nextDouble() / 2)));
                    } else {
                        it.remove();
                    }
                } else {
                    it.remove();
                }
            }
            blocks.addAll(toAdd);
            if (duration++ > maxDuration) {
                this.cancel();
            }
        }

        /**
         * Calculates the distance on only two axis'
         *
         * @param l1
         * @param l2
         * @return distance
         */
        private double twoDimensionalDistance(Location l1, Location l2) {
            return Math.abs(l1.getX() - l2.getX()) + (l1.getZ() - l2.getZ());
        }

        /**
         * Makes a direction negative or position, possibly
         *
         * @param original number
         * @return possible opposite version
         */
        private double direction(double original) {
            return original * (TenJava.getRandom().nextBoolean() ? -1 : 1);
        }
    }

}
