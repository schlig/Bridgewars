package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.utils.World;

public class Debug implements CommandExecutor {
	
	public Debug(Main plugin) {
		plugin.getCommand("debug").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		if(!p.isOp())
			return false;

		for(int i = 0; i < Integer.parseInt(args[0]); i++)
			World.attemptItemSpawn(1, true);
		
		return false;
	}
}
