package de.rewex.aura.manager;

import java.io.File;
import java.io.IOException;

import de.rewex.aura.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class AuraConfig {

    public String name;
    public String path;
    public File file;
    public FileConfiguration cfg;

    public boolean setup;
    public int server;
    public int minplayers;
    public int maxplayers;
    public int maxteamplayers;
    public String teams;
    public String mapname;

    public AuraConfig() {
        file = new File("plugins/Aura/aura.yml");
        cfg = YamlConfiguration.loadConfiguration(file);

        FileConfiguration cfg = getConfig();
        cfg.options().copyDefaults(true);
        cfg.addDefault("setup", true);
        cfg.addDefault("server", 1);
        cfg.addDefault("minplayers", 2);
        cfg.addDefault("maxplayers", 4);
        cfg.addDefault("maxteamplayers", 1);
        cfg.addDefault("teams", "4x1");
        cfg.addDefault("mapname", "Desert");
        saveCfg();

        this.setup = cfg.getBoolean("setup");
        this.server = cfg.getInt("server");
        this.minplayers = cfg.getInt("minplayers");
        this.maxplayers = cfg.getInt("maxplayers");
        this.maxteamplayers = cfg.getInt("maxteamplayers");
        this.teams = cfg.getString("teams");
        this.mapname = cfg.getString("mapname");

        Bukkit.getConsoleSender().sendMessage(Main.prefix + "§7Aura.yml geladen");


    }

    public AuraConfig(String name, String path) {
        file = new File(name, path);
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return cfg;
    }

    public void saveCfg() {
        try {
            cfg.save(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
