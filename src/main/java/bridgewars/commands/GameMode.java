package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;

public class GameMode implements CommandExecutor {
	
	public GameMode(Main plugin) {
		plugin.getCommand("gm").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		if(!p.hasPermission("trusted.gamemode")) {
			p.sendMessage(Chat.color("&cYou do not have permission to do this."));
			return true;
		}
		
		if(GameState.isState(GameState.ACTIVE) && !p.isOp()) {
			p.sendMessage(Chat.color("&cYou can't do this while a game is active."));
			return true;
		}
		
		switch(label) {
		case "gm":
			p.sendMessage(Chat.color("&cUsage: /gms, /gmc, /gma, /gmsp"));
			break;
		case "gms":
			p.setGameMode(org.bukkit.GameMode.SURVIVAL);
			p.sendMessage(Chat.color("&6Gamemode set to &cSurvival"));
			break;
		case "gmc":
			p.setGameMode(org.bukkit.GameMode.CREATIVE);
			p.sendMessage(Chat.color("&6Gamemode set to &cCreative"));
			break;
		case "gma":
			p.setGameMode(org.bukkit.GameMode.ADVENTURE);
			p.sendMessage(Chat.color("&6Gamemode set to &cAdventure"));
			break;
		case "gmsp":
			p.setGameMode(org.bukkit.GameMode.SPECTATOR);
			p.sendMessage(Chat.color("&6Gamemode set to &cSpectator"));
			break;
		}
		
		return true;
	}
}
