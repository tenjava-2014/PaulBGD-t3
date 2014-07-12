package com.tenjava.entries.PaulBGD.t3.events.land;

import com.tenjava.entries.PaulBGD.t3.TenJava;
import com.tenjava.entries.PaulBGD.t3.events.NaturalEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MudslideEvent extends NaturalEvent implements Listener {

    private final List<Material> allowedTypes = Arrays.asList(Material.GRASS, Material.DIRT, Material.CLAY, Material.SAND, Material.GRAVEL);
    private final BlockFace[] relative = new BlockFace[]{BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.DOWN};

    private final HashMap<Integer, List<Block>> timings = new HashMap<>();

    public MudslideEvent() {
        super("Mudslide", 3, Material.DIRT);

        NaturalEvent.getEvents().add(this);
        Bukkit.getPluginManager().registerEvents(this, TenJava.getPlugin());
    }

    @Override
    public boolean canOccur(Block block) {
        return allowedTypes.contains(block.getType());
    }

    @Override
    public void start(Block block, int id) {
        if (block.getY() < TenJava.heightLevel + 5 || !block.getWorld().hasStorm()) {
            return;
        }
        timings.put(id, new ArrayList<Block>());

        if (canMudslide(block, id)) {
            checkBlockForMudslide(block, id);
        }
        timings.remove(id);
    }

    private void mudslideAfter(Block block, int id) {
        BlockFace toGo = null;
        for (BlockFace relative : this.relative) {
            if (relative == BlockFace.DOWN || relative == BlockFace.UP) {
                continue;
            }
            Block relativeBlock = block.getRelative(relative);
            if (relativeBlock.getType() == Material.AIR) {
                toGo = relative;
                break;
            }
        }
        if (toGo != null) {
            Material type = block.getType() == Material.GRASS ? Material.DIRT : block.getType();
            byte data = block.getData();
            block.setType(Material.AIR);
            FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), type, data);
            fallingBlock.setDropItem(false);
            fallingBlock.setVelocity(new Vector(toGo.getModX() / 2, toGo.getModY() / 3, toGo.getModZ() / 2));
        }
    }

    private void checkBlockForMudslide(final Block block, final int id) {
        timings.get(id).add(block);
        for (BlockFace face : this.relative) {
            Block relative = block.getRelative(face);
            if (canMudslide(relative, id)) {
                checkBlockForMudslide(relative, id);
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                mudslideAfter(block, id);
            }
        }.runTaskLater(TenJava.getPlugin(), timings.get(id).size() / 5l);
    }

    private boolean canMudslide(Block block, int id) {
        List<Block> blocks = timings.get(id);
        Block original = blocks.isEmpty() ? block : blocks.get(0);
        return (allowedTypes.contains(block.getType()) || (block.getType() == Material.STONE && block.getRelative(BlockFace.UP).getType() != Material.STONE)) && !timings.get(id).contains(block) && block.getY() > TenJava.heightLevel + 3 && block.getLocation().distanceSquared(original.getLocation()) < 250;
    }

}
