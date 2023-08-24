package bridgewars.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.utils.Message;

public class DeleteMap implements CommandExecutor {
	
	public DeleteMap(Main plugin) {
		plugin.getCommand("deletemap").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if(p.getUniqueId() == Bukkit.getPlayer("Schlog").getUniqueId()) {
			if(args.length == 0) {
				p.sendMessage(Message.chat("&cYou must specify a map name."));
				return false;
			}
			
			String mapName = "";
			
			for(int i = 0; i < args.length; i++)
				mapName = mapName + args[i] + " ";
			
			mapName = mapName.substring(0, mapName.length() - 1);
			
			File file = new File("./plugins/bridgewars/maps/" + mapName + ".map");
			if(file.exists()) {
				file.delete();
				p.sendMessage(Message.chat("&7Deleted map \"&6" + mapName + "&7\"."));
			}
			else
				p.sendMessage(Message.chat("&cThat map does not exist!"));
		}
		
		else
			p.sendMessage(Message.chat("&cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error."));
		
		return false;
	}
}
