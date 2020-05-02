package de.rewex.aura.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import de.rewex.aura.Gamestate;
import de.rewex.aura.Main;
import de.rewex.aura.manager.ScoreAPI;

public class MapProtect implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        //if(!BuildCmd.build.contains(e.getPlayer())) {
            e.setCancelled(true);
        //}
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        //if(!BuildCmd.build.contains(e.getPlayer())) {
            e.setCancelled(true);
        //}
    }

    @EventHandler
    public void WeatherChangeEvent(WeatherChangeEvent e) {
        if (e.toWeatherState()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMobSpawning(CreatureSpawnEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            if(Main.getInstance().state != Gamestate.Ingame) {
                e.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onPlayerAchievementAwardedEvent(PlayerAchievementAwardedEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void noUproot(PlayerInteractEvent e) {
        if ((e.getAction() == Action.PHYSICAL) && (e.getClickedBlock().getType() == Material.SOIL)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChestInteract(InventoryOpenEvent e) {
        if(Main.getInstance().state == Gamestate.Ingame) {
            if(!ScoreAPI.teamspectator.contains(e.getPlayer().getName()) && e.getInventory() != e.getPlayer().getInventory()) {
                e.setCancelled(true);
            }

        }
    }

	/*@EventHandler
	public void onChestInteract(PlayerInteractEvent e) {
		if(Main.getInstance().state == Gamestate.Ingame) {
			if(Main.getInstance().alive.contains(e.getPlayer().getName())) {
				if(e.getClickedBlock().getType() == Material.WOODEN_DOOR || e.getClickedBlock().getType() == Material.WOOD_BUTTON || e.getClickedBlock().getType() == Material.STONE_BUTTON) {
					e.setCancelled(true);
				}
			}

		}
	}*/

}
