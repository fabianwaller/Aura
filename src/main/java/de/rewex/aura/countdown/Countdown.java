package de.rewex.aura.countdown;

import de.rewex.aura.Gamestate;
import de.rewex.aura.Main;
import de.rewex.aura.manager.ScoreAPI;
import de.rewex.aura.manager.utils.TitleAPI;
import de.rewex.mysql.players.stats.AuraStatsAPI;
import de.rewex.mysql.players.stats.PlayersAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Countdown {

    public static boolean schutzphase = true;

    public int idle;
    private boolean isIdling = true;
    private static final int IDLE_TIME = 45;

    public int lobby;
    public boolean acceleratedlobby = false;
    public boolean lobbyisrunning;
    public int schutz;
    public int restart;

    public int lobbyzeit = 60, schutzzeit = 10, restartzeit = 20;

    public void startIdle() {
        isIdling = true;
        idle = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            @Override
            public void run() {

                Bukkit.broadcastMessage(Main.prefix + "Warte auf §5" + (Main.getInstance().getAuraConfig().minplayers - Bukkit.getOnlinePlayers().size()) + " §7weitere(n) Spieler um das Spiel zu starten");
            }
        }, IDLE_TIME, 20 * IDLE_TIME);
    }

    public void stopIdle() {
        if(isIdling == true) {
            Bukkit.getScheduler().cancelAllTasks();
            ScoreAPI.startUpdater();
            isIdling = false;
        }
    }

    public void startLobbyCountdown() {
        stopIdle();
        lobbyisrunning = true;
        for(Player all:Bukkit.getOnlinePlayers()) {
            all.setExp(0.99f);
            all.setLevel((int)lobbyzeit);
        }

        lobby = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            @Override
            public void run() {
                switch(lobbyzeit) {
                    case 60: case 30: case 20: case 10: case 4: case 3: case 2:
                        Bukkit.broadcastMessage(Main.prefix + "Das Spiel startet in §5" + lobbyzeit + " §7Sekunden");

                        for(Player all:Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 3.0F, 2.0F);
                            float exp = all.getExp();
                            float remove = (float)1/lobbyzeit;
                            float newExp = exp - remove;
                            all.setExp(newExp);
                            all.setLevel((int)lobbyzeit);
                        }
                        break;

                    case 5:
                        Bukkit.broadcastMessage(Main.prefix + "Das Spiel startet in §5" + lobbyzeit + " §7Sekunden");

                        for(Player all:Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 3.0F, 2.0F);
                            TitleAPI.sendTitle(all, 5, 60, 5, "§5§lAura", "§fDesert");
                            float exp = all.getExp();
                            float remove = (float)1/lobbyzeit;
                            float newExp = exp - remove;
                            all.setExp(newExp);
                            all.setLevel((int)lobbyzeit);
                        }

                        break;

                    case 1:
                        Bukkit.broadcastMessage(Main.prefix + "Das Spiel startet in §5einer §7Sekunde");

                        for(Player all:Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 3.0F, 2.0F);
                            float exp = all.getExp();
                            float remove = (float)1/lobbyzeit;
                            float newExp = exp - remove;
                            all.setExp(newExp);
                            all.setLevel((int)lobbyzeit);
                        }
                        break;

                    case 0:
                        Bukkit.broadcastMessage(Main.prefix + "§aDas Spiel wurde gestartet");

                        Main.getInstance().state = Gamestate.Ingame;
                        Main.getInstance().updateMotd();

                        ScoreAPI.forceTeams();
                        for(Player all: Bukkit.getOnlinePlayers()) {
                            Main.getInstance().setGame(all);
                            float exp = all.getExp();
                            float remove = (float)1/lobbyzeit;
                            float newExp = exp - remove;
                            all.setExp(newExp);
                            all.setLevel((int)lobbyzeit);
                            AuraStatsAPI.addMatches(all.getUniqueId().toString(), Integer.valueOf(1));
                            PlayersAPI.addCoins(all.getUniqueId().toString(), Integer.valueOf(1));
                        }
                        Bukkit.getScheduler().cancelTask(lobby);
                        startSchutzCountdown();
                        break;

                    default:
                        for(Player all:Bukkit.getOnlinePlayers()) {
                            float exp = all.getExp();
                            float remove = (float)1/lobbyzeit;
                            float newExp = exp - remove;
                            all.setExp(newExp);
                            all.setLevel((int)lobbyzeit);

                        }
                        break;
                }


                lobbyzeit--;


            }

        }, 0, 20);

    }

    public void accelerateStart() {
        stopLobbyCountdown();
        this.lobbyzeit = 5;
        startLobbyCountdown();
    }

    public void stopLobbyCountdown() {
        if(lobbyisrunning == true) {
            LoadBar.stop();
            for(Player all:Bukkit.getOnlinePlayers()) {
                all.setLevel(0);
                all.setExp(0);
            }
            Bukkit.getScheduler().cancelTask(lobby);
            lobbyzeit = 60;
            lobbyisrunning = false;
        }

    }

    public void startSchutzCountdown() {
        for(Player all:Bukkit.getOnlinePlayers()) {
            all.setExp(0.99f);
            all.setLevel((int)schutzzeit);
        }
        schutz = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            @Override
            public void run() {

                switch(schutzzeit) {
                    case 15: case 10:
                        Bukkit.broadcastMessage(Main.prefix + "Die Schutzzeit endet in §5" + schutzzeit + " §7Sekunden");
                        break;
                    case 5:
                        Bukkit.broadcastMessage(Main.prefix + "Die Schutzzeit endet in §5" + schutzzeit + " §7Sekunden");
                        for(String alive : Main.getInstance().alive) {
                            Player all = Bukkit.getPlayer(alive);
                            if(all != null) {
                                TitleAPI.sendTitle(all, 0, 20, 0, "§45", "");
                                all.playSound(all.getLocation(), Sound.NOTE_BASS, 3.0F, 2.0F);
                                float exp = all.getExp();
                                float remove = (float)1/schutzzeit;
                                float newExp = exp - remove;
                                all.setExp(newExp);
                                all.setLevel((int)schutzzeit);
                            }
                        }
                        break;
                    case 4:
                        Bukkit.broadcastMessage(Main.prefix + "Die Schutzzeit endet in §5" + schutzzeit + " §7Sekunden");
                        for(String alive : Main.getInstance().alive) {
                            Player all = Bukkit.getPlayer(alive);
                            if(all != null) {
                                TitleAPI.sendTitle(all, 0, 20, 0, "§c4", "");
                                all.playSound(all.getLocation(), Sound.NOTE_BASS, 3.0F, 2.0F);
                                float exp = all.getExp();
                                float remove = (float)1/schutzzeit;
                                float newExp = exp - remove;
                                all.setExp(newExp);
                                all.setLevel((int)schutzzeit);
                            }
                        }
                        break;
                    case 3:
                        Bukkit.broadcastMessage(Main.prefix + "Die Schutzzeit endet in §5" + schutzzeit + " §7Sekunden");
                        for(String alive : Main.getInstance().alive) {
                            Player all = Bukkit.getPlayer(alive);
                            if(all != null) {
                                TitleAPI.sendTitle(all, 0, 20, 0, "§63", "");
                                all.playSound(all.getLocation(), Sound.NOTE_BASS, 3.0F, 2.0F);
                                float exp = all.getExp();
                                float remove = (float)1/schutzzeit;
                                float newExp = exp - remove;
                                all.setExp(newExp);
                                all.setLevel((int)schutzzeit);
                            }
                        }
                        break;
                    case 2:
                        Bukkit.broadcastMessage(Main.prefix + "Die Schutzzeit endet in §5" + schutzzeit + " §7Sekunden");
                        for(String alive : Main.getInstance().alive) {
                            Player all = Bukkit.getPlayer(alive);
                            if(all != null) {
                                TitleAPI.sendTitle(all, 0, 20, 0, "§e2", "");
                                all.playSound(all.getLocation(), Sound.NOTE_BASS, 3.0F, 2.0F);
                                float exp = all.getExp();
                                float remove = (float)1/schutzzeit;
                                float newExp = exp - remove;
                                all.setExp(newExp);
                                all.setLevel((int)schutzzeit);
                            }
                        }
                        break;
                    case 1:
                        if(schutzzeit == 1) {
                            Bukkit.broadcastMessage(Main.prefix + "Die Schutzzeit endet in §5einer §7Sekunde");
                            for(String alive : Main.getInstance().alive) {
                                Player all = Bukkit.getPlayer(alive);
                                if(all != null) {
                                    TitleAPI.sendTitle(all, 0, 20, 0, "§a1", "");
                                    all.playSound(all.getLocation(), Sound.NOTE_BASS, 3.0F, 2.0F);
                                    float exp = all.getExp();
                                    float remove = (float)1/schutzzeit;
                                    float newExp = exp - remove;
                                    all.setExp(newExp);
                                    all.setLevel((int)schutzzeit);
                                }
                            }
                        }
                        break;

                    case 0:
                        schutzphase = false;
                        Bukkit.broadcastMessage(Main.prefix + "§eDie Schutzzeit ist vorrüber! Lasst die Kämpfe beginnen!");
                        for(String alive : Main.getInstance().alive) {
                            Player all = Bukkit.getPlayer(alive);
                            if(all != null) {
                                all.playSound(all.getLocation(), Sound.NOTE_PLING, 3.0F, 2.0F);
                                float exp = all.getExp();
                                float remove = (float)1/schutzzeit;
                                float newExp = exp - remove;
                                all.setExp(newExp);
                                all.setLevel((int)schutzzeit);
                            }
                        }
                        Bukkit.getScheduler().cancelTask(schutz);

                    default:
                        for(Player all:Bukkit.getOnlinePlayers()) {
                            float exp = all.getExp();
                            float remove = (float)1/schutzzeit;
                            float newExp = exp - remove;
                            all.setExp(newExp);
                            all.setLevel((int)schutzzeit);

                        }
                        break;
                }

                schutzzeit--;
            }

        }, 20*2, 20);

    }

    public void startRestartCountdown() {

        Main.getInstance().state = Gamestate.Restarting;
        Main.getInstance().updateMotd();
		/*for(Player all:Bukkit.getOnlinePlayers()) {
			new LoadBar(Main.getInstance(), all, restartzeit);
		}*/
        restart = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            @Override
            public void run() {
                switch(restartzeit) {
                    case 30: case 20: case 15: case 10:
                        Bukkit.broadcastMessage(Main.prefix + "Der Server startet in §5" + restartzeit + " §7Sekunden neu");
                        break;
                    case 5: case 4: case 3: case 2:
                        Bukkit.broadcastMessage(Main.prefix + "Der Server startet in §5" + restartzeit + " §7Sekunden neu");
                        for(Player all:Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 3.0F, 2.0F);
                        }
                        break;
                    case 1:
                        Bukkit.broadcastMessage(Main.prefix + "Der Server startet in §5einer §7Sekunde neu");
                        for(Player all:Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 3.0F, 2.0F);
                        }
                        break;
                    case 0:
                        for(Player all:Bukkit.getOnlinePlayers()) {
                            all.kickPlayer(Main.prefix + "Server startet neu");
                        }
                        Bukkit.shutdown();
                        break;

                    default:
                        break;
                }

                restartzeit--;
            }
        }, 0, 20);


    }

}
