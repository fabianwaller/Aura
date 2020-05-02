package de.rewex.aura.commands;

import de.rewex.aura.Main;
import de.rewex.aura.manager.RangManager;
import de.rewex.aura.manager.UUIDManager;
import de.rewex.mysql.players.stats.AuraStatsAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCmd implements CommandExecutor {

    private final Main plugin;

    public StatsCmd(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.noplayer);
            return true;
        }
        Player p = (Player)sender;

        if(args.length == 0) {

            int aurakills = AuraStatsAPI.getKills(p.getUniqueId().toString());
            int auradeaths = AuraStatsAPI.getDeaths(p.getUniqueId().toString());
            double aurakd = aurakills;
            if(auradeaths>0) {
                aurakd = (double)aurakills/auradeaths;
                aurakd = AuraStatsAPI.round(aurakd);
            }
            int auramatches = AuraStatsAPI.getMatches(p.getUniqueId().toString());
            int aurawins = AuraStatsAPI.getWins(p.getUniqueId().toString());

            double aurawinrate;
            if(auramatches > 0) {
                aurawinrate = ((double)aurawins/auramatches)*100;
                aurawinrate = AuraStatsAPI.round(aurawinrate);
            } else {
                aurawinrate = 0;
            }

            p.sendMessage(Main.prefix + "§7Statistiken von " + RangManager.getName(p) + " §7(insgesamt)");
            p.sendMessage("§8x §7Kills§8: §d" + aurakills);
            p.sendMessage("§8x §7Deaths§8: §d" + auradeaths);
            p.sendMessage("§8x §7K/D§8: §5" + aurakd);
            p.sendMessage("§8x §7Gespielte Spiele§8: §d" + auramatches);
            p.sendMessage("§8x §7Gewonnene Spiele§8: §d" + aurawins);
            p.sendMessage("§8x §7Siegeswahrscheinlichkeit§8: §5" + aurawinrate + "%");

        } else if(args.length > 0) {

            String playername = args[0];
            Player onplayer = Bukkit.getPlayer(playername);
            String playeruuid;

            if(onplayer == null) {
                OfflinePlayer offplayer = UUIDManager.getOfflinePlayer(playername);
                playeruuid = offplayer.getUniqueId().toString();
            } else {
                playeruuid = onplayer.getUniqueId().toString();
            }

            int aurakills = AuraStatsAPI.getKills(playeruuid);
            int auradeaths = AuraStatsAPI.getDeaths(playeruuid);
            double aurakd = aurakills;
            if(auradeaths>0) {
                aurakd = (double)aurakills/auradeaths;
                aurakd = AuraStatsAPI.round(aurakd);
            }
            int auramatches = AuraStatsAPI.getMatches(playeruuid);
            int aurawins = AuraStatsAPI.getWins(playeruuid);

            double aurawinrate;
            if(auramatches > 0) {
                aurawinrate = ((double)aurawins/auramatches)*100;
                aurawinrate = AuraStatsAPI.round(aurawinrate);
            } else {
                aurawinrate = 0;
            }

			/*p.sendMessage(Main.prefix + "§7Statistiken von " + RangManager.getName(p) + " §7(insgesamt)");
			p.sendMessage("§8x §7Kills§8: §d" + aurakills);
			p.sendMessage("§8x §7Deaths§8: §d" + auradeaths);
			p.sendMessage("§8x §7K/D§8: §5" + aurakd);
			p.sendMessage("§8x §7Gespielte Spiele§8: §d" + auramatches);
			p.sendMessage("§8x §7Gewonnene Spiele§8: §d" + aurawins);
			p.sendMessage("§8x §7Siegeswahrscheinlichkeit§8: §5" + aurawinrate + "%");*/
            p.sendMessage(Main.prefix + "Statistiken von anderen Spieler bald verfügbar");


        }



        return true;
    }
}
