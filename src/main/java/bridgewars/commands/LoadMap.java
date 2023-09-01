package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.Game;
import bridgewars.utils.Message;

public class LoadMap implements CommandExecutor {
	
	public LoadMap(Main plugin) {
		plugin.getCommand("loadmap").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;

		String mapName = "";
		
		if(args.length == 0)
			mapName = null;
		
		else {
			for(int i = 0; i < args.length; i++)
				mapName = mapName + args[i] + " ";
			mapName = mapName.substring(0, mapName.length() - 1);
		}
		
		if(p.isOp()) {
			Game.clearMap();
			Game.buildMap(mapName, p, true);
		}
		
		else
			p.sendMessage(Message.chat("&cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error."));
		
		return true;
	}
}
