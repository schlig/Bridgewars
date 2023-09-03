package bridgewars.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.effects.Cloak;
import bridgewars.utils.Utils;

public class Debug implements CommandExecutor {
	
	private Main plugin;
	
	public Debug(Main plugin) {
		plugin.getCommand("debug").setExecutor(this);
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		if(!p.isOp())
			return false;

		ArrayList<Player> players = new ArrayList<>();
		for(Player player : Bukkit.getOnlinePlayers())
			players.add(player);
		
		players.remove(p);
		new Cloak(p, Utils.getUUID(args[0]), 20).runTaskTimer(plugin, 0L, 20L);
		
		return false;
	}
}