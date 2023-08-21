package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.Game;
import bridgewars.utils.Utils;

public class ClearMap implements CommandExecutor {
	
	public ClearMap(Main plugin) {
		plugin.getCommand("clearmap").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if(p.isOp()) {
			Game.clearMap();
			p.sendMessage(Utils.chat("&7Cleared the map"));
		}
		
		else
			p.sendMessage(Utils.chat("&cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error."));
		
		return false;
	}
}
