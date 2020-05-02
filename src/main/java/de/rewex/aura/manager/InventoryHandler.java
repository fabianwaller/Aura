package de.rewex.aura.manager;

import de.rewex.aura.Main;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import java.util.ArrayList;

public class InventoryHandler {

    public static Inventory teaminv = Bukkit.createInventory(null, 9, "§7Teamauswahl");
    public static Inventory kitinv = Bukkit.createInventory(null, 45, "§5§lKits");
    public static Inventory erfolgeinv = Bukkit.createInventory(null, 45, "§5§lErfolge");

    public static void fillTeamInv() {

        ArrayList<String> blaulore = new ArrayList<String>();
        blaulore.add(" ");
        if(ScoreAPI.teamblau.size()>0) {
            for(int i=0; i<ScoreAPI.teamblau.size(); i++) {
                blaulore.add("§7» §9" + ScoreAPI.teamblau.get(i));
            }
            for(int i=1; i<=Main.getInstance().getAuraConfig().maxteamplayers-ScoreAPI.teamblau.size(); i++) {
                blaulore.add("§7» ");
            }
        } else {
            for(int i = 1; i<= Main.getInstance().getAuraConfig().maxteamplayers; i++) {
                blaulore.add("§7» ");
            }
        }
        blaulore.add(" ");
        ItemStack blau = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta blaumeta = (LeatherArmorMeta) blau.getItemMeta();
        blaumeta.setDisplayName("§9Team Blau");
        blaumeta.setColor(Color.BLUE);
        blaumeta.setLore(blaulore);
        blau.setItemMeta(blaumeta);

        ArrayList<String> rotlore = new ArrayList<String>();
        rotlore.add(" ");
        if(ScoreAPI.teamrot.size()>0) {
            for(int i=0; i<ScoreAPI.teamrot.size(); i++) {
                rotlore.add("§7» §c" + ScoreAPI.teamrot.get(i));
            }
            for(int i=1; i<=Main.getInstance().getAuraConfig().maxteamplayers-ScoreAPI.teamrot.size(); i++) {
                rotlore.add("§7» ");
            }
        } else {
            for(int i=1; i<=Main.getInstance().getAuraConfig().maxteamplayers; i++) {
                rotlore.add("§7» ");
            }
        }
        rotlore.add(" ");
        ItemStack rot = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta rotmeta = (LeatherArmorMeta) rot.getItemMeta();
        rotmeta.setDisplayName("§cTeam Rot");
        rotmeta.setColor(Color.RED);
        rotmeta.setLore(rotlore);
        rot.setItemMeta(rotmeta);

        ArrayList<String> gelblore = new ArrayList<String>();
        gelblore.add(" ");
        if(ScoreAPI.teamgelb.size()>0) {
            for(int i=0; i<ScoreAPI.teamgelb.size(); i++) {
                gelblore.add("§7» §e" + ScoreAPI.teamgelb.get(i));
            }
            for(int i=1; i<=Main.getInstance().getAuraConfig().maxteamplayers-ScoreAPI.teamgelb.size(); i++) {
                gelblore.add("§7» ");
            }
        } else {
            for(int i=1; i<=Main.getInstance().getAuraConfig().maxteamplayers; i++) {
                gelblore.add("§7» ");
            }
        }
        gelblore.add(" ");
        ItemStack gelb = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta gelbmeta = (LeatherArmorMeta) gelb.getItemMeta();
        gelbmeta.setDisplayName("§eTeam Gelb");
        gelbmeta.setColor(Color.YELLOW);
        gelbmeta.setLore(gelblore);
        gelb.setItemMeta(gelbmeta);

        ArrayList<String> grünlore = new ArrayList<String>();
        grünlore.add(" ");
        if(ScoreAPI.teamgrün.size()>0) {
            for(int i=0; i<ScoreAPI.teamgrün.size(); i++) {
                grünlore.add("§7» §a" + ScoreAPI.teamgrün.get(i));
            }
            for(int i=1; i<=Main.getInstance().getAuraConfig().maxteamplayers-ScoreAPI.teamgrün.size(); i++) {
                grünlore.add("§7» ");
            }
        } else {
            for(int i=1; i<=Main.getInstance().getAuraConfig().maxteamplayers; i++) {
                grünlore.add("§7» ");
            }
        }
        grünlore.add(" ");
        ItemStack grün = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta grünmeta = (LeatherArmorMeta) grün.getItemMeta();
        grünmeta.setDisplayName("§aTeam Grün");
        grünmeta.setColor(Color.LIME);
        grünmeta.setLore(grünlore);
        grün.setItemMeta(grünmeta);

        teaminv.setItem(1, blau);
        teaminv.setItem(3, rot);
        teaminv.setItem(5, gelb);
        teaminv.setItem(7, grün);
    }

    public static void fillKitInv(Player p) {
        //kitinv.setItem(22, ItemManager.createItem(Material.BARRIER, 1, 0, "§c§lSoon"));
    }

    public static void fillErfolgeInv(Player p) {
        //erfolgeinv.setItem(22, ItemManager.createItem(Material.BARRIER, 1, 0, "§c§lSoon"));
    }

}
