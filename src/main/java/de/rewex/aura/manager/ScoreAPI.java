package de.rewex.aura.manager;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import de.rewex.aura.Gamestate;
import de.rewex.aura.Main;
import de.rewex.aura.manager.utils.TitleAPI;

public class ScoreAPI {

    public static ArrayList<String> teamblau = new ArrayList<String>();
    public static ArrayList<String> teamrot = new ArrayList<String>();
    public static ArrayList<String> teamgelb = new ArrayList<String>();
    public static ArrayList<String> teamgrün = new ArrayList<String>();

    public static ArrayList<String> teamspectator = new ArrayList<String>();

    @SuppressWarnings({ "deprecation" })
    public static void setScoreboard(Player p) {
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.getObjective("aaa");
        if(obj == null) {
            obj = sb.registerNewObjective("aaa", "bbb");
        }

        obj.setDisplayName("  §9Rewex.de  ");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        if(Main.getInstance().state == Gamestate.Lobby) {
            obj.getScore("§1").setScore(6);
            obj.getScore("§8•§7● Map").setScore(5);
            obj.getScore("§8➜ §5" + Main.getInstance().getAuraConfig().mapname).setScore(4);
            obj.getScore("§2").setScore(3);
            obj.getScore("§8•§7● Spieler").setScore(2);
            obj.getScore("§8➜ §5" + Main.getInstance().getAuraConfig().teams).setScore(1);
            obj.getScore("§3").setScore(0);
        } else if(Main.getInstance().state == Gamestate.Ingame) {
            obj.getScore("§1").setScore(6);
            obj.getScore("§8•§7● Map").setScore(5);
            obj.getScore("§8➜ §5" + Main.getInstance().getAuraConfig().mapname).setScore(4);
            obj.getScore("§2").setScore(3);
            if(teamblau.size() > 0) {
                obj.getScore(updateTeam(sb, "Blau", "§a✔ §9Blau", "", ChatColor.BLUE)).setScore(teamblau.size());
            } else {
                obj.getScore(updateTeam(sb, "Blau", "§c✖ §9Blau", "", ChatColor.BLUE)).setScore(teamblau.size());
            }
            if(teamrot.size() > 0) {
                obj.getScore(updateTeam(sb, "Rot", "§a✔ §cRot", "", ChatColor.RED)).setScore(teamrot.size());
            } else {
                obj.getScore(updateTeam(sb, "Rot", "§c✖ §cRot", "", ChatColor.RED)).setScore(teamrot.size());
            }
            if(teamgelb.size() > 0) {
                obj.getScore(updateTeam(sb, "Gelb", "§a✔ §eGelb", "", ChatColor.YELLOW)).setScore(teamgelb.size());
            } else {
                obj.getScore(updateTeam(sb, "Gelb", "§c✖ §eGelb", "", ChatColor.YELLOW)).setScore(teamgelb.size());
            }
            if(teamgrün.size() > 0) {
                obj.getScore(updateTeam(sb, "Grün", "§a✔ §aGrün", "", ChatColor.GREEN)).setScore(teamgrün.size());
            } else {
                obj.getScore(updateTeam(sb, "Grün", "§c✖ §aGrün", "", ChatColor.GREEN)).setScore(teamgrün.size());
            }

        } else if(Main.getInstance().state == Gamestate.Restarting) {
            Objective robj = sb.getObjective("ccc");
            if(robj == null) {
                robj = sb.registerNewObjective("ccc", "ddd");
            }
            robj.setDisplayName("  §9Rewex.de  ");
            robj.setDisplaySlot(DisplaySlot.SIDEBAR);
            robj.getScore("§1").setScore(2);
            robj.getScore("§8➜ §5Restarting").setScore(1);
            robj.getScore("§2").setScore(0);
        }

        Team blau = getTeam(sb, "0Blau", "§9Blau §7| §9", "");
        Team rot = getTeam(sb, "1Rot", "§cRot §7| §c", "");
        Team gelb = getTeam(sb, "2Gelb", "§eGelb §7| §e", "");
        Team grün = getTeam(sb, "3Grün", "§aGrün §7| §a", "");
        /*Team blau = getTeam(sb, "0Blau", "§9▮ §8• §9", "");
        Team rot = getTeam(sb, "1Rot", "§c▮ §8• §c", "");
        Team gelb = getTeam(sb, "2Gelb", "§e▮ §8• §e", "");
        Team grün = getTeam(sb, "3Grün", "§a▮ §8• §a", "");*/

        Team spectator = getTeam(sb, "spectator", "§7", "");
        spectator.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
        spectator.setCanSeeFriendlyInvisibles(true);


        for(Player all:Bukkit.getOnlinePlayers()) {
            if(teamblau.contains(all.getName())) {
                blau.addPlayer(all);
                all.setDisplayName(getColor(all) + all.getName());
                all.setPlayerListName(sb.getPlayerTeam(all).getPrefix() + all.getName());
            } else if(teamrot.contains(all.getName())) {
                rot.addPlayer(all);
                all.setDisplayName(getColor(all) + all.getName());
                all.setPlayerListName(sb.getPlayerTeam(all).getPrefix() + all.getName());
            } else if(teamgelb.contains(all.getName())) {
                gelb.addPlayer(all);
                all.setDisplayName(getColor(all) + all.getName());
                all.setPlayerListName(sb.getPlayerTeam(all).getPrefix() + all.getName());
            } else if(teamgrün.contains(all.getName())) {
                grün.addPlayer(all);
                all.setDisplayName(getColor(all) + all.getName());
                all.setPlayerListName(sb.getPlayerTeam(all).getPrefix() + all.getName());
            } else if(teamspectator.contains(all.getName())) {
                all.setDisplayName(spectator.getPrefix() + p.getName());
                all.setPlayerListName("");

            } else {
                all.setDisplayName(RangManager.getColor(all) + all.getName());
                all.setPlayerListName(RangManager.getColor(all) + all.getName());
            }

        }
        p.setScoreboard(sb);
    }

    @SuppressWarnings({ "unused", "deprecation" })
    public static void updateScoreboard(Player p) {
        if(p.getScoreboard() == null) {
            setScoreboard(p);
        }

        Scoreboard sb = p.getScoreboard();
        Objective obj = sb.getObjective("aaa");

        if(Main.getInstance().state == Gamestate.Ingame) {
            if(teamblau.size() > 0) {
                obj.getScore(updateTeam(sb, "Blau", "§a✔ §9Blau", "", ChatColor.BLUE)).setScore(teamblau.size());
            } else {
                obj.getScore(updateTeam(sb, "Blau", "§c✖ §9Blau", "", ChatColor.BLUE)).setScore(teamblau.size());
            }
            if(teamrot.size() > 0) {
                obj.getScore(updateTeam(sb, "Rot", "§a✔ §cRot", "", ChatColor.RED)).setScore(teamrot.size());
            } else {
                obj.getScore(updateTeam(sb, "Rot", "§c✖ §cRot", "", ChatColor.RED)).setScore(teamrot.size());
            }
            if(teamgelb.size() > 0) {
                obj.getScore(updateTeam(sb, "Gelb", "§a✔ §eGelb", "", ChatColor.YELLOW)).setScore(teamgelb.size());
            } else {
                obj.getScore(updateTeam(sb, "Gelb", "§c✖ §eGelb", "", ChatColor.YELLOW)).setScore(teamgelb.size());
            }
            if(teamgrün.size() > 0) {
                obj.getScore(updateTeam(sb, "Grün", "§a✔ §aGrün", "", ChatColor.GREEN)).setScore(teamgrün.size());
            } else {
                obj.getScore(updateTeam(sb, "Grün", "§c✖ §aGrün", "", ChatColor.GREEN)).setScore(teamgrün.size());
            }

        }

        Team blau = getTeam(sb, "0Blau", "§9Blau §7| §9", "");
        Team rot = getTeam(sb, "1Rot", "§cRot §7| §c", "");
        Team gelb = getTeam(sb, "2Gelb", "§eGelb §7| §e", "");
        Team grün = getTeam(sb, "3Grün", "§aGrün §7| §a", "");

        /*Team blau = getTeam(sb, "0Blau", "§9▮ §8• §9", "");
        Team rot = getTeam(sb, "1Rot", "§c▮ §8• §c", "");
        Team gelb = getTeam(sb, "2Gelb", "§e▮ §8• §e", "");
        Team grün = getTeam(sb, "3Grün", "§a▮ §8• §a", "");*/

        Team spectator = getTeam(sb, "spectator", "§7", "");
        spectator.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
        spectator.setAllowFriendlyFire(false);
        spectator.setCanSeeFriendlyInvisibles(true);

        for(Player all:Bukkit.getOnlinePlayers()) {
            if(teamblau.contains(all.getName())) {
                blau.addPlayer(all);
                all.setDisplayName(getColor(all) + all.getName());
                all.setPlayerListName(sb.getPlayerTeam(all).getPrefix() + all.getName());
            } else if(teamrot.contains(all.getName())) {
                rot.addPlayer(all);
                all.setDisplayName(getColor(all) + all.getName());
                all.setPlayerListName(sb.getPlayerTeam(all).getPrefix() + all.getName());
            } else if(teamgelb.contains(all.getName())) {
                gelb.addPlayer(all);
                all.setDisplayName(getColor(all) + all.getName());
                all.setPlayerListName(sb.getPlayerTeam(all).getPrefix() + all.getName());
            } else if(teamgrün.contains(all.getName())) {
                grün.addPlayer(all);
                all.setDisplayName(getColor(all) + all.getName());
                all.setPlayerListName(sb.getPlayerTeam(all).getPrefix() + all.getName());
            } else if(teamspectator.contains(all.getName())) {
                all.setDisplayName(spectator.getPrefix() + p.getName());
                all.setPlayerListName(spectator.getPrefix() + p.getName());

            } else {
                all.setDisplayName(RangManager.getColor(all) + all.getName());
                all.setPlayerListName(RangManager.getColor(all) + all.getName());
            }
        }

    }

    @SuppressWarnings("deprecation")
    public static void setTeam(Player p, String team) {
        if(p.getScoreboard() == null) {
            setScoreboard(p);
        }

        Scoreboard sb = p.getScoreboard();

        if(team.equalsIgnoreCase("spectator")) {
            removeTeam(p);
            sb.getTeam("spectator").addEntry(p.getName());
            return;
        }

        if(sb.getTeam(team).hasPlayer(p)) {
            p.sendMessage(Main.prefix + "§cDu bist bereits in diesem Team§8!");
            return;
        }
        if(sb.getTeam(team).getSize() >= Main.getInstance().getAuraConfig().maxteamplayers) {
            p.sendMessage(Main.prefix + "§cDas Team ist bereits voll§8!");
            return;
        }
        ScoreAPI.removeTeam(p);
		/*sb.getTeam(team).addPlayer(p);

		if(sb.getTeam("0Blau").hasPlayer(p)) {
			ScoreAPI.teamblau.add(p);
		} else if(sb.getTeam("1Rot").hasPlayer(p)) {
			ScoreAPI.teamrot.add(p);
		} else if(sb.getTeam("2Gelb").hasPlayer(p)) {
			ScoreAPI.teamgelb.add(p);
		} else if(sb.getTeam("3Grün").hasPlayer(p)) {
			ScoreAPI.teamgrün.add(p);
		}*/
        if(team.equalsIgnoreCase("0Blau")) {
            teamblau.add(p.getName());
        } else if(team.equalsIgnoreCase("1Rot")) {
            teamrot.add(p.getName());
        } else if(team.equalsIgnoreCase("2Gelb")) {
            teamgelb.add(p.getName());
        } else if(team.equalsIgnoreCase("3Grün")) {
            teamgrün.add(p.getName());
        }

        ScoreAPI.updateScoreboard(p);
        p.sendMessage(Main.prefix + "Du bist nun in Team " + sb.getPlayerTeam(p).getPrefix().substring(0, sb.getPlayerTeam(p).getPrefix().length()-7));

    }

    @SuppressWarnings("deprecation")
    public static void removeTeam(Player p) {
        if(p.getScoreboard() == null) {
            setScoreboard(p);
        }

        Scoreboard sb = p.getScoreboard();

        if(sb.getPlayerTeam(p) != null) {

            if(teamblau.contains(p.getName())) {
                sb.getTeam("0Blau").removePlayer(p);
                teamblau.remove(p.getName());
            } else if(teamrot.contains(p.getName())) {
                sb.getTeam("1Rot").removePlayer(p);
                teamrot.remove(p.getName());
            } else if(teamgelb.contains(p.getName())) {
                sb.getTeam("2Gelb").removePlayer(p);
                teamgelb.remove(p.getName());
            } else if(teamgrün.contains(p.getName())) {
                sb.getTeam("3Grün").removePlayer(p);
                teamgrün.remove(p.getName());
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void forceTeams() {
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(all.getScoreboard() == null) {
                setScoreboard(all);
            }

            Scoreboard sb = all.getScoreboard();
            if(sb.getPlayerTeam(all) == null) {
                if(teamblau.size() < Main.getInstance().getAuraConfig().maxteamplayers) {
                    setTeam(all, "0Blau");
                } else if(teamrot.size() < Main.getInstance().getAuraConfig().maxteamplayers) {
                    setTeam(all, "1Rot");
                }  else if(teamgelb.size() < Main.getInstance().getAuraConfig().maxteamplayers) {
                    setTeam(all, "2Gelb");
                }  else if(teamgrün.size() < Main.getInstance().getAuraConfig().maxteamplayers) {
                    setTeam(all, "3Grün");
                } else {
                    all.kickPlayer("Seltener Fehler: Team konnte nicht gesetzt werden!");
                }

            }
        }
    }

    public static void spawnTeams(Player p) {
        if(p.getScoreboard() == null) {
            setScoreboard(p);
        }
        Scoreboard sb = p.getScoreboard();

        if(sb.getTeam("0Blau").hasEntry(p.getName())) {
            LocationManager.telLocation(p, "spawn1");
        } else if(sb.getTeam("1Rot").hasEntry(p.getName())) {
            LocationManager.telLocation(p, "spawn2");
        } else if(sb.getTeam("2Gelb").hasEntry(p.getName())) {
            LocationManager.telLocation(p, "spawn3");
        } else if(sb.getTeam("3Grün").hasEntry(p.getName())){
            LocationManager.telLocation(p, "spawn4");
        }
    }

    public static Team getPlayerTeam(Player p) {
        if(p.getScoreboard() == null) {
            setScoreboard(p);
        }
        Scoreboard sb = p.getScoreboard();
        if(sb.getTeam("0Blau").hasEntry(p.getName())) {
            return sb.getTeam("0Blau");
        } else if(sb.getTeam("1Rot").hasEntry(p.getName())) {
            return sb.getTeam("1Rot");
        } else if(sb.getTeam("2Gelb").hasEntry(p.getName())) {
            return sb.getTeam("2Gelb");
        } else if(sb.getTeam("3Grün").hasEntry(p.getName())){
            return sb.getTeam("3Grün");
        }
        return null;

    }

    public static String getName(Player p) {
        return getColor(p) + p.getName();
    }

    public static String getColor(Player p) {
        if(p.getScoreboard() == null) {
            setScoreboard(p);
        }
        Scoreboard sb = p.getScoreboard();
        if(sb.getTeam("0Blau").hasEntry(p.getName())) {
            return "§9";
        } else if(sb.getTeam("1Rot").hasEntry(p.getName())) {
            return "§c";
        } else if(sb.getTeam("2Gelb").hasEntry(p.getName())) {
            return "§e";
        } else if(sb.getTeam("3Grün").hasEntry(p.getName())){
            return "§a";
        }
        return "§7";
    }

    public static Team getTeam(Scoreboard sb, String Team, String prefix, String suffix) {
        Team team = sb.getTeam(Team);
        if(team == null) {
            team = sb.registerNewTeam(Team);
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.setAllowFriendlyFire(false);
        team.setCanSeeFriendlyInvisibles(true);
        return team;
    }

    public static String updateTeam(Scoreboard sb, String Team, String prefix, String suffix, ChatColor entry) {
        Team team = sb.getTeam(Team);
        if(team == null) {
            team = sb.registerNewTeam(Team);
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.addEntry(entry.toString());
        return entry.toString();
    }

    public static void startUpdater() {
        new BukkitRunnable() {

            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                for(Player all:Bukkit.getOnlinePlayers()) {

                    if(all.getScoreboard() == null) {
                        setScoreboard(all);
                    }
                    Scoreboard sb = all.getScoreboard();

                    updateScoreboard(all);

                    if(Main.getInstance().state == Gamestate.Ingame) {
                        //TitleAPI.sendActionBar(all, "§cTeams verboten");
                        if(teamspectator.contains(all.getName())) {
                            TitleAPI.sendActionBar(all, "§7Zuschauer");
                        } else {
                            TitleAPI.sendActionBar(all, getColor(all) + "Team " + sb.getPlayerTeam(all).getPrefix().substring(0, sb.getPlayerTeam(all).getPrefix().length()-7));
                        }



                    }

                }

            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }

}
