package com.tenjava.entries.PaulBGD.t3;

import com.tenjava.entries.PaulBGD.t3.commands.DisasterCommand;
import com.tenjava.entries.PaulBGD.t3.events.NaturalEvent;
import com.tenjava.entries.PaulBGD.t3.events.air.Tornado;
import com.tenjava.entries.PaulBGD.t3.events.land.MudslideEvent;
import com.tenjava.entries.PaulBGD.t3.events.water.Flood;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class TenJava extends JavaPlugin {

    private static final Random random = new Random();
    public static int heightLevel = 63;
    public static String title = String.format("%s%s%s: ", ChatColor.RED, ChatColor.BOLD, "Natural Disasters");
    private static TenJava instance;

    public static TenJava getPlugin() {
        return instance;
    }

    public static Random getRandom() {
        return random;
    }

    public static void addWorlds(NaturalEvent naturalEvent) {
        FileConfiguration configuration = instance.getConfig();
        if (!configuration.isSet(naturalEvent.getName())) {
            configuration.set(naturalEvent.getName() + ".Chance", naturalEvent.getChance());
            List<String> allowedWorlds = new ArrayList<>();
            for (World world : Bukkit.getWorlds()) {
                if (world.getWorldType() == WorldType.NORMAL) {
                    allowedWorlds.add(world.getName());
                }
            }
            configuration.set(naturalEvent.getName() + ".Worlds", allowedWorlds);
            instance.saveConfig();
        }
        List<?> allowedWorlds = configuration.getList(naturalEvent.getName() + ".Worlds");
        Iterator<?> it = allowedWorlds.iterator();
        while (it.hasNext()) {
            Object allowedWorld = it.next();
            World world = Bukkit.getWorld(allowedWorld.toString());
            if (world == null) {
                it.remove();
            } else {
                naturalEvent.getAllowedWorlds().add(world);
            }
        }
        naturalEvent.setChance(configuration.getInt(naturalEvent.getName() + ".Chance", 0));
        if (allowedWorlds.size() != configuration.getList(naturalEvent.getName() + ".Worlds").size()) {
            configuration.set(naturalEvent.getName() + ".Worlds", allowedWorlds);
            instance.saveConfig();
        }
    }

    @Override
    public void onEnable() {
        instance = this;

        new MudslideEvent();
        new Tornado();
        new Flood();

        new NaturalDisasterStarter();

        getCommand("disaster").setExecutor(new DisasterCommand());
    }
}
