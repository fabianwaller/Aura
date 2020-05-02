package de.rewex.aura.countdown;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LoadBar {

    Player p;
    float time;
    float count;
    Plugin plugin;
    static int schedular;
    static boolean started;

    LoadBar(Plugin plugin, Player p, float time) {
        this.p = p;
        this.time = time;
        this.plugin = plugin;
        this.count = time;
        launch();
    }

    public void launch() {
        started = true;
        p.setExp(0.99f);
        p.setLevel((int)count);

        schedular = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {

            @Override
            public void run() {
                count--;
                float exp = p.getExp();
                float remove = (float)1/time;
                float newExp = exp - remove;
                p.setExp(newExp);
                p.setLevel((int)count);

                if(newExp <= 0) {
                    Bukkit.getScheduler().cancelTask(schedular);
                }
            }
        }, 0, 20);
    }

    public static void stop() {
        if(started = true) {
            Bukkit.getScheduler().cancelTask(schedular);
        }
    }


}
