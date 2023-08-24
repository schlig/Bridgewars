package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.CustomScoreboard;
import bridgewars.game.Game;
import bridgewars.game.GameState;
import bridgewars.utils.Message;

public class LeaveGame implements CommandExecutor {
	
	private CustomScoreboard cs;
	
	public LeaveGame(Main plugin) {
		plugin.getCommand("leave").setExecutor(this);
		cs = new CustomScoreboard();
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return true;
		
		Player p = (Player) sender;
		
		if(!GameState.isState(GameState.ACTIVE)) {
			p.sendMessage(Message.chat("&cThere is no game in progress."));
			return false;
		}
		
		if(!cs.hasTeam(p)) {
			p.sendMessage(Message.chat("&cYou are not in a game."));
			return false;
		}
			
		Game.leaveGame(p);
		p.sendMessage(Message.chat("&cYou left the game."));
		
		return false;
	}
}
