package de.rewex.aura.listeners;

import de.rewex.aura.Gamestate;
import de.rewex.aura.Main;
import de.rewex.aura.countdown.Countdown;
import de.rewex.aura.manager.InventoryHandler;
import de.rewex.aura.manager.ScoreAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class LobbyListeners implements Listener {

    @EventHandler
    public void onInvInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if(Main.getInstance().state == Gamestate.Lobby) {
            if((e.getAction() == Action.RIGHT_CLICK_BLOCK) || (e.getAction() == Action.RIGHT_CLICK_AIR)) {

                if(e.getItem() != null) {

                    if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Teamauswahl")) {
                        InventoryHandler.fillTeamInv();
                        p.openInventory(InventoryHandler.teaminv);
                        p.playSound(p.getLocation(), Sound.CLICK, 12.0F, 12.0F);
                        e.setCancelled(true);
                    }

                    if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5§lKits")) {
                        InventoryHandler.fillKitInv(p);
                        p.openInventory(InventoryHandler.kitinv);
                        p.playSound(p.getLocation(), Sound.CLICK, 12.0F, 12.0F);
                        e.setCancelled(true);
                    }

                    if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5§lErfolge")) {
                        InventoryHandler.fillErfolgeInv(p);
                        p.openInventory(InventoryHandler.erfolgeinv);
                        p.playSound(p.getLocation(), Sound.CLICK, 12.0F, 12.0F);
                        e.setCancelled(true);
                    }

                }


            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = null;
        if((e.getWhoClicked() instanceof Player)) {
            p = (Player) e.getWhoClicked();
        }

        if(e.getInventory().getName().equals("§7Teamauswahl")) {
            if((e.getCurrentItem() != null) && (e.getCurrentItem().hasItemMeta())) {

                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§9Team Blau")) {
                    ScoreAPI.setTeam(p, "0Blau");
                    p.closeInventory();
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cTeam Rot")) {
                    ScoreAPI.setTeam(p, "1Rot");
                    p.closeInventory();
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eTeam Gelb")) {
                    ScoreAPI.setTeam(p, "2Gelb");
                    p.closeInventory();
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§aTeam Grün")) {
                    ScoreAPI.setTeam(p, "3Grün");
                    p.closeInventory();
                }

                p.playSound(p.getLocation(), Sound.CLICK, 3.0F, 2.0F);
            }
        }

        if(Main.getInstance().state != Gamestate.Ingame) {
            e.setCancelled(true);
        }

    }


    @EventHandler
    public void onLobbyDamage(EntityDamageEvent e) {
        if((Main.getInstance().state == Gamestate.Lobby) || (Main.getInstance().state == Gamestate.Restarting) || (Countdown.schutzphase == true)) {

            e.setCancelled(true);
        }
    }

	/*@EventHandler
	public void onOutofMap(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if((Main.getInstance().state == Gamestate.Lobby) || (Main.getInstance().state == Gamestate.Restarting)) {
			if(p.getLocation().getBlockY() < 40) {
				LocationManager.telLocation(p, "wartelobby");
			}
		}
	}*/

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if((Main.getInstance().state == Gamestate.Lobby) || (Main.getInstance().state == Gamestate.Restarting) || (ScoreAPI.teamspectator.contains(e.getPlayer().getName()))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if(e.getEntity() instanceof Player) {
            if((Main.getInstance().state == Gamestate.Lobby) || (Main.getInstance().state == Gamestate.Restarting) || (Countdown.schutzphase == true) || (ScoreAPI.teamspectator.contains(e.getEntity().getName()))) {
                Player p = (Player) e.getEntity();
                p.setFoodLevel(20);
                e.setCancelled(true);
            }
        }

    }

}
