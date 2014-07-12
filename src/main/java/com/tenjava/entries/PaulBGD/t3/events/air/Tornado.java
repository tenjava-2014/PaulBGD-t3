package com.tenjava.entries.PaulBGD.t3.events.air;

import com.tenjava.entries.PaulBGD.t3.TenJava;
import com.tenjava.entries.PaulBGD.t3.events.NaturalEvent;
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

        private Location location;

        public TornadoObject(Location start) {
            this.location = start;

            this.runTaskTimer(TenJava.getPlugin(), 2, 2);
        }

        @Override
        public void run() {
            location.add(direction(TenJava.getRandom().nextInt(2)), 0, direction(TenJava.getRandom().nextInt(2)));
            while (location.getBlock().isEmpty()) {
                location.subtract(0, 1, 0);
            }
            while (!location.getBlock().getRelative(BlockFace.UP).isEmpty()) {
                location.add(0, 1, 0);
            }
            int blocks = 0;
            for (BlockFace face : relative) {
                if(TenJava.getRandom().nextInt(3) == 0) {
                    continue;
                }
                Block block = location.getBlock().getRelative(face);
                if (block.getRelative(BlockFace.UP).isEmpty() && block.getType() != Material.OBSIDIAN && block.getType() != Material.STONE && block.getType() != Material.BEDROCK) {
                    Material type = block.getType();
                    byte data = block.getData();
                    block.setType(Material.AIR);
                    blocks++;
                    FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), type, data);
                    fallingBlock.setVelocity(new Vector(0, TenJava.getRandom().nextDouble() * TenJava.getRandom().nextInt(3), 0));
                    for (Entity entity : fallingBlock.getNearbyEntities(1.5, 1.5, 1.5)) {
                        entity.setVelocity(entity.getVelocity().setY(1));
                    }
                }
            }
            for (FallingBlock block : location.getWorld().getEntitiesByClass(FallingBlock.class)) {
                System.out.println("Found block " + block.getLocation().getY() + " " + location.getY());
                if (block.getLocation().getY() >= location.getY()) {
                    System.out.println("good y");
                    double twoDistance = twoDimesionalDistance(block.getLocation(), location);
                    if (twoDistance < 100) {
                        System.out.println("close");
                        double xDiff = location.getX() - block.getLocation().getX();
                        double zDiff = location.getZ() - block.getLocation().getZ();
                        block.setVelocity(new Vector(xDiff > 0 ? -0.12 : 0.12, block.getVelocity().getY() + 0.03, zDiff > 0 ? -0.12 : 0.12));
                    }
                }
            }

            if (duration++ > maxDuration) {
                this.cancel();
            }
        }

        private double twoDimesionalDistance(Location l1, Location l2) {
            return Math.abs(l1.getX() - l2.getX()) + (l1.getZ() - l2.getZ());
        }

        private double direction(double original) {
            return original * (TenJava.getRandom().nextBoolean() ? -1 : 1);
        }
    }

}
