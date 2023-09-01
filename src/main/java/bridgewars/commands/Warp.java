package bridgewars.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.Message;

public class Warp implements CommandExecutor {
	
	public Warp(Main plugin) {
		plugin.getCommand("warp").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return true;
		
		Player p = (Player) sender;
		
		if(GameState.isState(GameState.ACTIVE)
		&& !p.isOp()) {
			p.sendMessage(Message.chat("&cYou can't use this command while a game is active!"));
			return true;
		}
		
		if(args.length < 1)
			args = new String[] {"none"};
		
		if(args[0] == "none"
		&& !label.contains("hub")) {
			p.sendMessage(Message.chat("&7Available locations&7: &6spawn&7|&6hub&7,&6 observatory&7|&6map"));
			return true;
		}
		
		else if(args[0].toLowerCase().contains("spawn")
		|| label.contains("hub")) {
			p.teleport(new Location(Bukkit.getWorld("world"), 1062.5, 52, 88.5, -90, 10));
			p.sendMessage(Message.chat("&7Teleported to Spawn"));
		}
		
		else if(args[0].toLowerCase().contains("observatory")
		|| args[0].toLowerCase().contains("map")) {
			p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 46.0, 6.5, 180, 10));
			p.sendMessage(Message.chat("&7Teleported to the Observatory"));
		}
		
		return false;
	}
}
