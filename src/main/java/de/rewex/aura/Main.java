package de.rewex.aura;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import de.rewex.aura.commands.SetlocCmd;
import de.rewex.aura.listeners.*;
import de.rewex.aura.manager.InventoryHandler;
import de.rewex.aura.manager.utils.ItemBuilder;
import de.rewex.mysql.MySQL;
import de.rewex.mysql.players.stats.AuraStatsAPI;
import de.rewex.mysql.players.stats.PlayersAPI;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import de.rewex.aura.chat.ChatListeners;
import de.rewex.aura.commands.StartCmd;
import de.rewex.aura.commands.StatsCmd;
import de.rewex.aura.countdown.Countdown;
import de.rewex.aura.manager.AuraConfig;
import de.rewex.aura.manager.LocationManager;
import de.rewex.aura.manager.ScoreAPI;
import de.rewex.aura.manager.utils.TitleAPI;

public class Main extends JavaPlugin {

    public static String prefix = "§d•§5● Aura §7| ";
    public static String passpr = "§e•§6● Gamepass §7| ";
    public static String noperm = prefix + "§cDazu hast du keine Rechte§8!";
    public static String offplayer = prefix + "§cDieser Spieler ist offline§8!";
    public static String noplayer = "[Aura] Nur ein Spieler kann diesen Befehl ausführen";

    public Gamestate state;

    public ArrayList<String> alive = new ArrayList<>();
    Countdown c = new Countdown();

    private static AuraConfig cfg;

    public static Main instance;
    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        state = Gamestate.Lobby;
        updateMotd();

        cfg = new AuraConfig();

        registerCommands();
        registerListeners();

        MySQL.connect();
        MySQL.createTable();
        if (!MySQL.isConnected()) {
            Bukkit.getPluginManager().disablePlugin(this);
        }

        ScoreAPI.startUpdater();
        c.startIdle();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "LABYMOD");

        Bukkit.createWorld(new WorldCreator("wartelobby"));
        Bukkit.getConsoleSender().sendMessage(Main.prefix + "§aPlugin aktiviert");

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Main.prefix + "§cPlugin deaktiviert");
    }

    private void registerCommands() {
        getCommand("setlocation").setExecutor(new SetlocCmd(this));
        getCommand("start").setExecutor(new StartCmd(this));
        getCommand("stats").setExecutor(new StatsCmd(this));
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new ChatListeners(), this);

        pm.registerEvents(new ConnectListeners(), this);
        pm.registerEvents(new DeathListeners(), this);
        pm.registerEvents(new LobbyListeners(), this);
        pm.registerEvents(new MapProtect(), this);
        pm.registerEvents(new Spectator(), this);
    }

    public AuraConfig getAuraConfig() {
        return cfg;
    }

    public void updateMotd() {
        if(Main.getInstance().state == Gamestate.Lobby) {
            if(Bukkit.getOnlinePlayers().size()<Bukkit.getMaxPlayers()) {
                ((CraftServer)Bukkit.getServer()).getServer().setMotd("§aLobby");
            } else {
                ((CraftServer)Bukkit.getServer()).getServer().setMotd("§cLobby");
            }
        } else if(Main.getInstance().state == Gamestate.Ingame) {
            ((CraftServer)Bukkit.getServer()).getServer().setMotd("§4Ingame");
        } else if(Main.getInstance().state == Gamestate.Restarting) {
            ((CraftServer)Bukkit.getServer()).getServer().setMotd("§cRestarting");
        }
    }


    @SuppressWarnings("deprecation")
    public void checkgameover() {
        if(Main.getInstance().alive.size() <= getAuraConfig().getConfig().getInt("maxteamplayers")) {

            Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                public void run() {
                    setRestarting();
                }
            }, 20L*2);

            String player = Main.getInstance().alive.get(0);
            Player p = Bukkit.getPlayer(player);

            //if(p.getScoreboard() == null) {
            ScoreAPI.setScoreboard(p);
            //}
            Scoreboard sb = p.getScoreboard();
            for(Player all:Bukkit.getOnlinePlayers()) {
                all.playSound(all.getLocation(), Sound.FIREWORK_TWINKLE, 3.0F, 2.0F);

                TitleAPI.sendTitle(all, 5, 80, 5, ScoreAPI.getColor(p) + "Team " + sb.getPlayerTeam(p).getPrefix().substring(0, sb.getPlayerTeam(p).getPrefix().length()-7), "hat das Spiel gewonnen");
            }

            for(OfflinePlayer winners: ScoreAPI.getPlayerTeam(p).getPlayers()) {
                if(Bukkit.getPlayer(winners.getName()) != null) {
                    AuraStatsAPI.addWins(winners.getUniqueId().toString(), 1);
                    PlayersAPI.addCoins(winners.getUniqueId().toString(), 15);
                    Bukkit.getPlayer(winners.getName()).sendMessage(Main.prefix + "§aRunde gewonnen §8[§a+ §b15 Coins§8]");
                }

            }
        }
    }

    public void setWartelobby(Player p) {
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        p.setHealthScale(20);
        p.setFoodLevel(20);
        p.setExp(0);
        p.setLevel(0);

        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        for(PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }

        p.getInventory().setItem(0, new ItemBuilder(Material.BOOK,1).setName(InventoryHandler.getTeamname()).build());
       // p.getInventory().setItem(1, ItemManager.createItem(Material.ENDER_CHEST, 1, 0, "§5§lKits"));
       // p.getInventory().setItem(8, ItemManager.createItem(Material.NETHER_STAR, 1, 0, "§5§lErfolge"));
        LocationManager.telLocation(p, "wartelobby");
    }

    public void setGame(Player p) {

        ScoreAPI.spawnTeams(p);

        Main.getInstance().alive.add(p.getName());

        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        p.setHealthScale(20);
        p.setFoodLevel(20);
        p.setExp(0);
        p.setLevel(0);

        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        for(PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }

        p.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).setName("Eisenhelm").build());
        p.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).setName("Eisenbrustpanzer").build());
        p.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).setName("Eisenbeinschutz").build());
        p.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).setName("Eisenstiefel").addEnchant(Enchantment.PROTECTION_FALL,4).build());

        p.getInventory().setItem(0, new ItemBuilder(Material.BLAZE_ROD).setName("Stock").addEnchant(Enchantment.KNOCKBACK,4).build());
        p.getInventory().setItem(2, new ItemBuilder(Material.ENDER_PEARL,16).setName("Enderperle").build());
        p.getInventory().setItem(3, new ItemBuilder(Material.SNOW_BALL,16).setName("Schneeball").build());
        p.getInventory().setItem(8, new ItemBuilder(Material.PUMPKIN_PIE,10).setName("Kürbiskuchen").build());
        p.getInventory().setItem(4, new ItemBuilder(Material.GOLDEN_APPLE,1).setName("Goldener Apfel").build());
        p.getInventory().setItem(5, new ItemBuilder(Material.MONSTER_EGG,1, (short) 50).setName("Creeper").build());
        p.getInventory().setItem(6, new ItemBuilder(Material.POTION,1, (short) 8261).setName("Heiltrank").build());
        p.getInventory().setItem(7, new ItemBuilder(Material.POTION,1, (short) 16418).setName("Schnelligkeit").build());
        p.getInventory().setItem(1, new ItemBuilder(Material.FISHING_ROD,1).setName("Angel").build());

        ScoreAPI.setScoreboard(p);
    }

    @SuppressWarnings("deprecation")
    public void setSpectator(Player p) {

        LocationManager.telLocation(p, "spectator");

        p.setGameMode(GameMode.SPECTATOR);
        p.setCanPickupItems(false);
        p.setAllowFlight(true);
        p.setFlying(true);
        p.setHealth(20);
        p.setHealthScale(20);
        p.setFoodLevel(20);
        p.setExp(0);
        p.setLevel(0);

        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        for(PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }

        ScoreAPI.setScoreboard(p);
        ScoreAPI.teamspectator.add(p.getName());


        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE , 1));

        for(Player all: Bukkit.getOnlinePlayers()) {
            all.hidePlayer(p);
        }

        p.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§7Teleporter").build());
        ArrayList<String> nextlore = new ArrayList<String>();
        ItemStack nextgame = new ItemStack(Material.getMaterial(397), 1, (short) 3);
        SkullMeta nextmeta = (SkullMeta)nextgame.getItemMeta();
        nextmeta.setDisplayName("§7Nochmal spielen");
        nextlore.add("§7Klicke, um eine neue Runde zu betreten");
        nextmeta.setLore(nextlore);
        nextmeta.setOwner("MHF_ArrowRight");
        nextgame.setItemMeta(nextmeta);
        p.getInventory().setItem(8, nextgame);

    }

    public void setRestarting() {
        for(Player all:Bukkit.getOnlinePlayers()) {
            LocationManager.telLocation(all, "wartelobby");

            all.setGameMode(GameMode.SURVIVAL);
            all.setHealth(20);
            all.setHealthScale(20);
            all.setFoodLevel(20);
            all.setExp(0);
            all.setLevel(0);

            all.getInventory().clear();
            all.getInventory().setArmorContents(null);
            for(PotionEffect effect : all.getActivePotionEffects()) {
                all.removePotionEffect(effect.getType());
            }

            all.showPlayer(all);
        }
        Main.getInstance().state = Gamestate.Restarting;
        c.startRestartCountdown();

    }

}
