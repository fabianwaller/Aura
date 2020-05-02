package de.rewex.aura.manager;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UUIDManager {

    public static Player getPlayer(String playername) {
        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            if (players.getName().toLowerCase().equals(playername.toLowerCase())) {
                return players;
            }
        }
        return null;
    }

    public static OfflinePlayer getOfflinePlayer(String playername) {
        OfflinePlayer[] arrayOfOfflinePlayer;
        int j = (arrayOfOfflinePlayer = Bukkit.getOfflinePlayers()).length;
        for (int i = 0; i < j; i++) {
            OfflinePlayer players = arrayOfOfflinePlayer[i];
            if (players.getName().toLowerCase().equals(playername.toLowerCase())) {
                return players;
            }
        }
        return null;
    }

    public static UUID getUUID(String playername) {
        Player p = getPlayer(playername);
        if (p != null) {
            return p.getUniqueId();
        }
        return null;
    }

}
