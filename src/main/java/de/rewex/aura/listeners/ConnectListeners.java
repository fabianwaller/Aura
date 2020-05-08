package de.rewex.aura.listeners;

import de.rewex.mysql.players.stats.AuraStatsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import de.rewex.aura.Gamestate;
import de.rewex.aura.Main;
import de.rewex.aura.countdown.Countdown;
import de.rewex.aura.manager.RangManager;
import de.rewex.aura.manager.ScoreAPI;
import de.rewex.aura.manager.utils.TitleAPI;

public class ConnectListeners implements Listener {

    @EventHandler
    public void setMaxPlayers(ServerListPingEvent e) {
        e.setMaxPlayers(4);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        AuraStatsAPI.createPlayer(p.getUniqueId().toString());

        if(Bukkit.getServer().getOnlinePlayers().size() == Bukkit.getMaxPlayers()) {
            e.disallow(Result.KICK_FULL, Main.prefix + "§cDer Server ist voll");
        }

        if(Main.getInstance().state == Gamestate.Restarting) {
            e.disallow(Result.KICK_OTHER, Main.prefix + "§cDer Server startet gerade neu");
        }

    }

    Countdown c = new Countdown();
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if(Main.getInstance().state == Gamestate.Ingame) {
            Main.getInstance().setSpectator(p);
            e.setJoinMessage("");
        } else {
            Main.getInstance().setWartelobby(p);

            e.setJoinMessage(Main.prefix + "§7» " + RangManager.getName(p) +  " §7hat das Spiel betreten");

            if(Bukkit.getServer().getOnlinePlayers().size() >= Main.getInstance().getAuraConfig().minplayers) {
                if(!c.lobbyisrunning) {
                    c.startLobbyCountdown();
                }
            }
        }


        ScoreAPI.setScoreboard(p);
        Main.getInstance().updateMotd();

        TitleAPI.sendTabTitle(p, "\n   §9§lRewex.de §8× §7Dein Minigames Netzwerk   "
                        + "\n§7Derzeitiger Server §8× §5Aura #" + Main.getInstance().getAuraConfig().server
                        + "\n ",
                "\n §cHacker§7? §c/report"
                        + "\n ");




    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(Main.getInstance().state != Gamestate.Restarting) {
            e.setQuitMessage(Main.prefix + "§7« " + RangManager.getName(e.getPlayer()) +  " §7hat das Spiel verlassen");
        } else {
            e.setQuitMessage("");
        }


        if(Main.getInstance().state == Gamestate.Lobby) {
            ScoreAPI.removeTeam(p);
            if(Bukkit.getOnlinePlayers().size() < Main.getInstance().getAuraConfig().minplayers) {
                if(c.lobbyisrunning == true) {
                    c.stopLobbyCountdown();
                    c.startIdle();
                }
            }
        }

        if(Main.getInstance().alive.contains(p.getName())) {
            Main.getInstance().alive.remove(p.getName());

            Main.getInstance().checkgameover();
            return;
        }






    }

    @EventHandler
    public void onLeave(PlayerKickEvent e) {
        Player p = e.getPlayer();
        if(Main.getInstance().state != Gamestate.Restarting) {
            e.setLeaveMessage(Main.prefix + "§7« " + RangManager.getName(e.getPlayer()) +  " §7hat das Spiel verlassen");
        } else {
            e.setLeaveMessage("");
        }

        if(Main.getInstance().state == Gamestate.Lobby) {
            ScoreAPI.removeTeam(p);
            if(Bukkit.getOnlinePlayers().size() < Main.getInstance().getAuraConfig().minplayers) {
                if(c.lobbyisrunning == true) {
                    c.stopLobbyCountdown();
                    c.startIdle();
                }
            }
        }

        if(Main.getInstance().alive.contains(p.getName())) {
            Main.getInstance().alive.remove(p.getName());

            Main.getInstance().checkgameover();
            return;
        }



    }

}
