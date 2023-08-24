package bridgewars.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.utils.Message;

public class MapList implements CommandExecutor {
	public MapList(Main plugin) {
		plugin.getCommand("maplist").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		File file = new File("./plugins/bridgewars/maps/");
		String[] mapList = file.list();
		String message = Message.chat("&lMap List &7(" + mapList.length + ")&r&l: " + "&r&6");
		for(int i = 0; i < mapList.length; i++)
			message = message + mapList[i].substring(0, mapList[i].length() - 4) + Message.chat("&r,&6 ");
		message = message.substring(0, message.length() - 6);
		p.sendMessage(message);
		
		return false;
	}
}
