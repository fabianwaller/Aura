package de.rewex.aura.listeners;

import de.rewex.aura.Gamestate;
import de.rewex.aura.Main;
import de.rewex.aura.manager.ScoreAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Spectator implements Listener {

    public static Inventory teleporter;

    @EventHandler
    public void onSpectatorDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player && ScoreAPI.teamspectator.contains(e.getEntity().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSpecatorHit(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player && ScoreAPI.teamspectator.contains(e.getDamager().getName())) {
            e.setCancelled(true);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInvInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if((e.getAction() == Action.RIGHT_CLICK_BLOCK) || (e.getAction() == Action.RIGHT_CLICK_AIR)) {


            if(e.getItem() != null) {

                if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§5§lTeleporter")) {
                    teleporter = Bukkit.createInventory(null, 9, "§5§lTeleporter");

                    for(int i=0; i<ScoreAPI.teamblau.size(); i++) {
                        Player teleportplayer = Bukkit.getPlayer(ScoreAPI.teamblau.get(i));
                        if(teleportplayer != null) {
                            ItemStack playerhead = new ItemStack(Material.getMaterial(397), 1, (short) 3);
                            SkullMeta headmeta = (SkullMeta)playerhead.getItemMeta();
                            headmeta.setDisplayName(ScoreAPI.getColor(teleportplayer) + teleportplayer.getName());
                            headmeta.setOwner(teleportplayer.getName());
                            playerhead.setItemMeta(headmeta);

                            teleporter.addItem(playerhead);
                        }

                    }

                    for(int i=0; i<ScoreAPI.teamrot.size(); i++) {
                        Player teleportplayer = Bukkit.getPlayer(ScoreAPI.teamrot.get(i));
                        if(teleportplayer != null) {
                            ItemStack playerhead = new ItemStack(Material.getMaterial(397), 1, (short) 3);
                            SkullMeta headmeta = (SkullMeta)playerhead.getItemMeta();
                            headmeta.setDisplayName(ScoreAPI.getColor(teleportplayer) + teleportplayer.getName());
                            headmeta.setOwner(teleportplayer.getName());
                            playerhead.setItemMeta(headmeta);

                            teleporter.addItem(playerhead);
                        }

                    }

                    for(int i=0; i<ScoreAPI.teamgelb.size(); i++) {
                        Player teleportplayer = Bukkit.getPlayer(ScoreAPI.teamgelb.get(i));
                        if(teleportplayer != null) {
                            ItemStack playerhead = new ItemStack(Material.getMaterial(397), 1, (short) 3);
                            SkullMeta headmeta = (SkullMeta)playerhead.getItemMeta();
                            headmeta.setDisplayName(ScoreAPI.getColor(teleportplayer) + teleportplayer.getName());
                            headmeta.setOwner(teleportplayer.getName());
                            playerhead.setItemMeta(headmeta);

                            teleporter.addItem(playerhead);
                        }

                    }

                    for(int i=0; i<ScoreAPI.teamgrün.size(); i++) {
                        Player teleportplayer = Bukkit.getPlayer(ScoreAPI.teamgrün.get(i));
                        if(teleportplayer != null) {
                            ItemStack playerhead = new ItemStack(Material.getMaterial(397), 1, (short) 3);
                            SkullMeta headmeta = (SkullMeta)playerhead.getItemMeta();
                            headmeta.setDisplayName(ScoreAPI.getColor(teleportplayer) + teleportplayer.getName());
                            headmeta.setOwner(teleportplayer.getName());
                            playerhead.setItemMeta(headmeta);

                            teleporter.addItem(playerhead);
                        }

                    }

                    p.openInventory(teleporter);
                    p.playSound(p.getLocation(), Sound.CLICK, 12.0F, 12.0F);
                    e.setCancelled(true);
                }

                else if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lNochmal spielen")) {
                    p.kickPlayer("");
                    p.playSound(p.getLocation(), Sound.CLICK, 12.0F, 12.0F);
                    e.setCancelled(true);
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

        if(e.getInventory().getName().equals("§5§lTeleporter")) {
            if((e.getCurrentItem() != null) && (e.getCurrentItem().hasItemMeta())) {

                String name = e.getCurrentItem().getItemMeta().getDisplayName();
                Player teleportto = Bukkit.getPlayer(name);
                if(teleportto != null) {
                    p.teleport(teleportto.getLocation());
                    p.closeInventory();
                }

                p.playSound(p.getLocation(), Sound.CLICK, 3.0F, 2.0F);
            }
        }

        if(Main.getInstance().state != Gamestate.Ingame) {
            e.setCancelled(true);
        }

    }

}
