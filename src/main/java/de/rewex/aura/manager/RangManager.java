package de.rewex.aura.manager;

import org.bukkit.entity.Player;

public class RangManager {

    public static String getName(Player p) {
        return getColor(p) + p.getName();
    }

    public static String getColor(Player p) {
        if(p.hasPermission("team.admin")) {
            return "§4";

        } else if(p.hasPermission("team.mod")) {
            return "§9";

        } else if(p.hasPermission("team.sup")) {
            return "§b";

        } else if(p.hasPermission("team.dev")) {
            return "§d";

        } else if(p.hasPermission("team.builder")) {
            return "§a";

        } else if(p.hasPermission("team.content")) {
            return "§3";

        } else if(p.hasPermission("server.yt")) {
            return "§5";

        } else if(p.hasPermission("server.titan")) {
            return "§e";

        } else if(p.hasPermission("server.champ")) {
            return "§c";

        } else if(p.hasPermission("server.prime")) {
            return "§6";

        } else {
            return "§7";
        }
    }

    public static String getRang(Player p) {
        if(p.hasPermission("team.admin")) {
            return "§4Admin";

        } else if(p.hasPermission("team.mod")) {
            return "§9Moderator";

        } else if(p.hasPermission("team.sup")) {
            return "§bSupporter";

        } else if(p.hasPermission("team.dev")) {
            return "§dDeveloper";

        } else if(p.hasPermission("team.builder")) {
            return "§aBuilder";

        } else if(p.hasPermission("team.content")) {
            return "§3Content";

        } else if(p.hasPermission("server.yt")) {
            return "§5Youtuber";

        } else if(p.hasPermission("server.titan")) {
            return "§eTitan";

        } else if(p.hasPermission("server.champ")) {
            return "§cChamp";

        } else if(p.hasPermission("server.prime")) {
            return "§6Prime";
        }

        return "§7Spieler";

    }

}
