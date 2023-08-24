package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.CustomScoreboard;
import bridgewars.game.GameState;
import bridgewars.utils.Message;

public class JoinTeam implements CommandExecutor {
	
	private CustomScoreboard cs;
	
	public JoinTeam(Main plugin) {
		plugin.getCommand("jointeam").setExecutor(this);
		cs = new CustomScoreboard();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if(GameState.isState(GameState.ACTIVE)) {
			p.sendMessage(Message.chat("&cYou can't change teams while a game is active!"));
			return true;
		}
		else {
			if(args.length > 0)
				cs.setTeam(p, args[0]);
			else
				cs.resetTeam(p, true);
		}
		
		return false;
	}
}
