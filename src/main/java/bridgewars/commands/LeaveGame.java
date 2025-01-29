package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.CSManager;
import bridgewars.game.Game;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;

public class LeaveGame implements CommandExecutor {
	
	public LeaveGame(Main plugin) {
		plugin.getCommand("leave").setExecutor(this);
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return true;
		
		Player p = (Player) sender;
		
		if(!GameState.isState(GameState.ACTIVE)) {
			p.sendMessage(Chat.color("&cThere is no game in progress."));
			return true;
		}
		
		if(!CSManager.hasTeam(p)) {
			p.sendMessage(Chat.color("&cYou are not in a game."));
			return true;
		}
			
		Game.leaveGame(p);
		p.sendMessage(Chat.color("&cYou left the game."));
		
		return true;
	}
}
