package de.rewex.aura.manager;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class LocationManager {

    private static File file = new File("plugins/Aura/locs.yml");
    private static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public static void saveCfg() {
        try {
            cfg.save(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLocation(String name, Location loc) {
        cfg.set(name + ".world", loc.getWorld().getName());
        cfg.set(name + ".x", Double.valueOf(loc.getX()));
        cfg.set(name + ".y", Double.valueOf(loc.getY()));
        cfg.set(name + ".z", Double.valueOf(loc.getZ()));
        cfg.set(name + ".yaw", Float.valueOf(loc.getYaw()));
        cfg.set(name + ".pitch", Float.valueOf(loc.getPitch()));
        saveCfg();
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (Exception localException) {}
        }
    }

    public static Location getLocation(String name) {
        World world = Bukkit.getWorld(cfg.getString(name + ".world"));
        double x = cfg.getDouble(name + ".x");
        double y = cfg.getDouble(name + ".y");
        double z = cfg.getDouble(name + ".z");
        Location loc = new Location(world, x, y, z);
        loc.setYaw(cfg.getInt(name + ".yaw"));
        loc.setPitch(cfg.getInt(name + ".pitch"));
        return loc;
    }

    public static void telLocation(Player p, String name) {
        Location loc = getLocation(name);
        p.teleport(loc);
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0F, 2.0F);
    }

}
