package com.tenjava.entries.PaulBGD.t3.events.air;

import com.tenjava.entries.PaulBGD.t3.TenJava;
import com.tenjava.entries.PaulBGD.t3.events.NaturalEvent;
import com.tenjava.entries.PaulBGD.t3.utils.Timer;
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
        super("Tornado", 3);

        NaturalEvent.getEvents().add(this);
    }

    @Override
    public boolean canOccur(Block block) {
        return false;
    }

    @Override
    public void start(Block block, int id) {
        new TornadoObject(block.getLocation());
    }

    public class TornadoObject extends BukkitRunnable {

        private final int maxDuration = (20 + TenJava.getRandom().nextInt(100)) * 10;
        private int duration = 0;
        List<Entity> blocks = new ArrayList<>();

        private Location location;

        public TornadoObject(Location start) {
            this.location = start;

            this.runTaskTimer(TenJava.getPlugin(), 2, 2);
        }

        @Override
        public void run() {
            Timer timer = new Timer();
            double movedX = direction(TenJava.getRandom().nextInt(2));
            double movedZ = direction(TenJava.getRandom().nextInt(2));
            System.out.println("x: " + movedX + " z: " + movedZ);
            location.add(movedX, 0, movedZ);
            while (location.getBlock().isEmpty()) {
                location.subtract(0, 1, 0);
            }
            while (!location.getBlock().getRelative(BlockFace.UP).isEmpty()) {
                location.add(0, 1, 0);
            }
            timer.time("Move tornado");
            for (BlockFace face : relative) {
                if (TenJava.getRandom().nextInt(3) == 0) {
                    continue;
                }
                Block block = location.getBlock().getRelative(face);
                if (block.getRelative(BlockFace.UP).isEmpty() && block.getType() != Material.OBSIDIAN && block.getType() != Material.STONE && block.getType() != Material.BEDROCK) {
                    Material type = block.getType();
                    byte data = block.getData();
                    block.setType(Material.AIR);
                    FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), type, data);
                    fallingBlock.setVelocity(new Vector(0, 1, 0));
                    blocks.add(fallingBlock);
                    for (Entity entity : fallingBlock.getNearbyEntities(1.5, 1.5, 1.5)) {
                        if (!blocks.contains(entity)) {
                            blocks.add(entity);
                        }
                    }
                }
            }
            timer.time("Toss up new blocks");
            System.out.println("blocks: " + blocks.size());
            Iterator<Entity> it = blocks.iterator();
            while (it.hasNext()) {
                Entity block = it.next();
                if (!block.isValid() || block.isDead()) {
                    it.remove();
                    continue;
                }
                if (block.getLocation().getY() >= location.getY()) {
                    double twoDistance = twoDimensionalDistance(block.getLocation(), location);
                    if (twoDistance < 100) {
                        block.setVelocity(block.getVelocity().add(new Vector(movedX / 3, 0.05, movedZ / 3)));
                    } else {
                        it.remove();
                    }
                } else {
                    it.remove();
                }
            }
            timer.time("Update old blocks");
            if (duration++ > maxDuration) {
                this.cancel();
            }
        }

        private double twoDimensionalDistance(Location l1, Location l2) {
            return Math.abs(l1.getX() - l2.getX()) + (l1.getZ() - l2.getZ());
        }

        private double direction(double original) {
            return original * (TenJava.getRandom().nextBoolean() ? -1 : 1);
        }
    }

}
