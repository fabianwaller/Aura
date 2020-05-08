package de.rewex.aura.chat;

import de.rewex.aura.Gamestate;
import de.rewex.aura.Main;
import de.rewex.aura.manager.RangManager;
import de.rewex.aura.manager.ScoreAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.util.ArrayList;

public class ChatListeners implements Listener {

    ArrayList<String> spam = new ArrayList<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        final Player p = e.getPlayer();
        if (!this.spam.contains(p.getName())) {
            this.spam.add(p.getName());

            String msg = e.getMessage();

            if(Main.getInstance().state != Gamestate.Ingame) {

                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(RangManager.getRang(p) + " §8● " + RangManager.getName(p) + " §8➜ §7" + msg);
                }
                e.setCancelled(true);


            } else {

                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage("§5@all §8● " + ScoreAPI.getName(p) + " §8➜ §7" + msg);
                }
            }


            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                public void run() {
                    spam.remove(p.getName());
                }
            }, 30L);

            e.setCancelled(true);
        } else {
            p.sendMessage(Main.prefix + "§cDu schreibst zu schnell...");
            e.setCancelled(true);
        }
    }


}
