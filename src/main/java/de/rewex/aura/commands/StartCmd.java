package de.rewex.aura.commands;

import de.rewex.aura.Main;
import de.rewex.aura.countdown.Countdown;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCmd implements CommandExecutor {

    private final Main plugin;

    public StartCmd(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.noplayer);
            return true;
        }
        Player p = (Player)sender;
        if (!p.hasPermission("server.start")) {
            p.sendMessage(Main.noperm);
            return true;
        }

        Countdown c = new Countdown();


        c.accelerateStart();
        return true;


		/*if(Bukkit.getOnlinePlayers().size() < Main.getInstance().getAuraConfig().minplayers) {
			p.sendMessage(Main.prefix + "§cEs befinden sich nicht genug Spieler auf dem Server");
			return true;
		} else {
			c.accelerateStart();
			p.sendMessage(Main.prefix + "§aStart erfolgreich beschleunigt");
		}*/



    }
}
