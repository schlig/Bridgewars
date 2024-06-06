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
		
		if(label.contains("hub"))
			args = new String[] {"hub"};
		
		if(args[0] == "none") {
			p.sendMessage(Message.chat("&7Available locations&7: &6spawn&7|&6hub&7,&6 observatory&7|&6map, bmcl"));
			return true;
		}
		
		
		switch(args[0]) {
		case "hub":
		case "spawn":
			p.teleport(new Location(Bukkit.getWorld("world"), 1062.5, 52, 88.5, -90, 10));
			p.sendMessage(Message.chat("&7Teleported to Spawn"));
			break;
			
		case "map":
		case "observatory":
			p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 46.0, 6.5, 180, 10));
			p.sendMessage(Message.chat("&7Teleported to the Observatory"));
			break;
			
		case "bmcl":
			p.teleport(new Location(Bukkit.getWorld("world"), 69420.5, 69.0, 69420.5, -90, 10));
			p.sendMessage(Message.chat("&7Teleported to Bad MC Level"));
			break;
		}
		
		return false;
	}
}
