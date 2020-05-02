package de.rewex.aura.commands;

import de.rewex.aura.Main;
import de.rewex.aura.manager.LocationManager;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetlocCmd implements CommandExecutor {

    private final Main plugin;

    public SetlocCmd(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.noplayer);
            return true;
        }
        Player p = (Player)sender;
        if (!p.hasPermission("ffa.setlocation")) {
            p.sendMessage(Main.noperm);
            return true;
        }
        if (args.length != 1) {
            p.sendMessage(Main.prefix + "§c/setlocation <Name>");
        }
        else {
            LocationManager.setLocation(args[0], p.getLocation());
            p.sendMessage(Main.prefix + "§7Du hast die Location §6" + args[0] + "§7 erfolgreich gesetzt.");
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0F, 2.0F);
        }
        return true;
    }
}
