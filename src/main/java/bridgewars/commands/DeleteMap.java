package bridgewars.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.messages.Chat;

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
				p.sendMessage(Chat.color("&cYou must specify a map name."));
				return true;
			}
			
			String mapName = "";
			
			for(int i = 0; i < args.length; i++)
				mapName = mapName + args[i] + " ";
			
			mapName = mapName.substring(0, mapName.length() - 1);
			
			File file = new File("./plugins/bridgewars/maps/" + mapName + ".map");
			if(file.exists()) {
				file.delete();
				p.sendMessage(Chat.color("&7Deleted map \"&6" + mapName + "&7\"."));
			}
			else
				p.sendMessage(Chat.color("&cThat map does not exist."));
		}
		
		else
			p.sendMessage(Chat.color("&cYou do not have permission to do this."));
		
		return true;
	}
}
