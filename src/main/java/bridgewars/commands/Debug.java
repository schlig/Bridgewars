package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.Leaderboards;

public class Debug implements CommandExecutor {
	
	public Debug(Main plugin) {
		plugin.getCommand("debug").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player))
			return true;;
		
		Player p = (Player) sender;
		
		if(!p.isOp()) {
			p.sendMessage("u aint using this command chief gtfo");
			return true;
		}
		
		Leaderboards.refreshLifetimeLeaderboards();
		Leaderboards.refreshInstanceLeaderboards();
		
		return true;
	}
}