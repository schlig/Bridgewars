package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.utils.Message;
import bridgewars.utils.World;

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
			World.clearMap();
			p.sendMessage(Message.chat("&7Cleared the map"));
		}
		
		else
			p.sendMessage(Message.chat("&cYou do not have permission to do this."));
		
		return false;
	}
}
