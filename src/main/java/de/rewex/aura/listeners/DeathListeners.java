package de.rewex.aura.listeners;

import java.util.HashMap;

import de.rewex.mysql.players.stats.AuraStatsAPI;
import de.rewex.mysql.players.stats.PlayersAPI;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import de.rewex.aura.Gamestate;
import de.rewex.aura.Main;
import de.rewex.aura.manager.ScoreAPI;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;

public class DeathListeners implements Listener {

    public static HashMap<String, String> damager = new HashMap();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        e.getDrops().clear();

        if(p.getKiller() instanceof Player) {

            Player k = p.getKiller();
            e.setDeathMessage(Main.prefix + ScoreAPI.getName(p) + " §7wurde von " + ScoreAPI.getName(k) + " §7getötet");
            k.sendMessage(Main.prefix + "§7Du hast " + ScoreAPI.getName(p) + " §7getötet §8[§a+ §b5 Coins§8]");
            p.sendMessage(Main.prefix + "§7Du wurdest von " + ScoreAPI.getName(k) + " §7getötet");

            AuraStatsAPI.addKills(k.getUniqueId().toString(), Integer.valueOf(1));
            PlayersAPI.addCoins(k.getUniqueId().toString(), Integer.valueOf(5));

        } else if (damager.containsKey(p.getName())) {
            String killer = (String)damager.get(p.getName());
            Player k = Bukkit.getPlayer(killer);

            if(k!= null) {

                e.setDeathMessage(Main.prefix + ScoreAPI.getName(p) + " §7wurde von " + ScoreAPI.getName(k) + " §7getötet");
                k.sendMessage(Main.prefix + "§7Du hast " + ScoreAPI.getName(p) + " §7getötet §8[§a+ §b5 Coins§8]");
                p.sendMessage(Main.prefix + "§7Du wurdest von " + ScoreAPI.getName(k) + " §7getötet");
                AuraStatsAPI.addKills(k.getUniqueId().toString(), Integer.valueOf(1));
                PlayersAPI.addCoins(k.getUniqueId().toString(), Integer.valueOf(5));

            } else {
                e.setDeathMessage(Main.prefix + ScoreAPI.getName(p) + " §7ist gestorben");
            }

        } else {

            e.setDeathMessage(Main.prefix + ScoreAPI.getName(p) + " §7ist gestorben");
        }


        Respawn(p, 5);
        ScoreAPI.removeTeam(p);
        Main.getInstance().alive.remove(p.getName());

        AuraStatsAPI.addDeaths(p.getUniqueId().toString(), Integer.valueOf(1));

        Main.getInstance().checkgameover();

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if ((e.getEntity() instanceof Player)) {

            final Player p = (Player)e.getEntity();
            if(e.getDamager() instanceof Player) {
                Player t = (Player)e.getDamager();
                if(t == null) {
                    return;
                }
                damager.put(p.getName(), t.getName());

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                    public void run() {
                        damager.remove(p.getName());
                    }
                }, 20L*30);
            }


        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if(Main.getInstance().state == Gamestate.Ingame) {
            Main.getInstance().setSpectator(e.getPlayer());
        }
    }


    public void Respawn(final Player p, int Time) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            public void run() {
                ((CraftPlayer)p).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
            }
        }, Time);
    }

}
