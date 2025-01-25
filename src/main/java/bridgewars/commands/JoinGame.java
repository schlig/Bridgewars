package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.Game;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.utils.Utils;

public class JoinGame implements CommandExecutor {
	
	public JoinGame(Main plugin) {
		plugin.getCommand("joingame").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return true;
		
		Player p = (Player) sender;
		
		if(GameState.isState(GameState.INACTIVE)) {
			p.sendMessage(Chat.color("&cThere is no game in progress."));
			return false;
		}
		
		else if(GameState.isState(GameState.EDIT)) {
			p.sendMessage(Chat.color("&cThe game is currently in Edit mode."));
			return false;
		}
		
		if(!Utils.isOutOfBounds(p.getLocation(), 80, 40, 80)) {
			p.sendMessage(Chat.color("&cYou are already in a game."));
			return false;
		}
			
		Game.joinGame(p);
		p.sendMessage(Chat.color("&6You joined the game."));
		
		return false;
	}
}
