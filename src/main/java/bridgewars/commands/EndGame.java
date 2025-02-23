package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.Game;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;

public class EndGame implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public EndGame(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("endgame").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if(p.hasPermission("trusted.endgame")) {
			if(GameState.isState(GameState.INACTIVE)) {
				p.sendMessage(Chat.color("&cThere is no game in progress."));
				return true;
			}

			Game.endGame(p, true);
			p.sendMessage(Chat.color("&7Ended the game"));
		}
		
		else
			p.sendMessage(Chat.color("&cYou do not have permission to do this."));
		
		return true;
	}
}
