package bridgewars.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.Message;

public class GM implements CommandExecutor {
	
	public GM(Main plugin) {
		plugin.getCommand("gm").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		if(!p.hasPermission("trusted.gamemode")) {
			p.sendMessage(Message.chat("&cYou do not have permission to do this."));
			return false;
		}
		
		if(GameState.isState(GameState.ACTIVE) && !p.isOp()) {
			p.sendMessage(Message.chat("&cYou can't do this while a game is active."));
			return false;
		}
		
		switch(label) {
		case "gm":
			p.sendMessage(Message.chat("&cUsage: /gms, /gmc, /gma, /gmsp"));
			break;
		case "gms":
			p.setGameMode(GameMode.SURVIVAL);
			p.sendMessage(Message.chat("&6Gamemode set to &cSurvival"));
			break;
		case "gmc":
			p.setGameMode(GameMode.CREATIVE);
			p.sendMessage(Message.chat("&6Gamemode set to &cCreative"));
			break;
		case "gma":
			p.setGameMode(GameMode.ADVENTURE);
			p.sendMessage(Message.chat("&6Gamemode set to &cAdventure"));
			break;
		case "gmsp":
			p.setGameMode(GameMode.SPECTATOR);
			p.sendMessage(Message.chat("&6Gamemode set to &cSpectator"));
			break;
		}
		
		return false;
	}
}
