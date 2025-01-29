package bridgewars.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.utils.World;

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
			p.sendMessage(Chat.color("&cYou can't use this command while a game is active!"));
			return true;
		}
		
		if(args.length < 1)
			args = new String[] {"none"};
		
		if(label.contains("hub") || label.contains("l"))
			args = new String[] {"hub"};
		
		if(args[0] == "none") {
			p.sendMessage(Chat.color("&7Available locations&7: &6spawn&7|&6hub&7,&6 observatory&7|&6map, bmcl"));
			return true;
		}
		
		
		switch(args[0]) {
		case "hub":
		case "spawn":
			p.teleport(World.getSpawn());
			p.sendMessage(Chat.color("&7Teleported to Spawn"));
			break;
			
		case "map":
		case "observatory":
			p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 46.0, 6.5, 180, 10));
			p.sendMessage(Chat.color("&7Teleported to the Observatory"));
			if(GameState.isState(GameState.ACTIVE))
				p.setGameMode(org.bukkit.GameMode.SPECTATOR);
			break;
			
		case "bmcl":
			p.teleport(new Location(Bukkit.getWorld("world"), 69420.5, 69.0, 69420.5, -90, 10));
			p.sendMessage(Chat.color("&7Teleported to Bad MC Level"));
			break;
		}
		
		return true;
	}
}
