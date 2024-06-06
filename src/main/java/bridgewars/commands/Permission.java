package bridgewars.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.utils.Message;
import bridgewars.utils.Permissions;
import bridgewars.utils.Utils;

public class Permission implements CommandExecutor {
	
	private ArrayList<String> permissions = new ArrayList<>();
	
	public Permission(Main plugin) {
		plugin.getCommand("permission").setExecutor(this);
		permissions.add("bridgewars.trusted");
		permissions.add("bridgewars.trusted.gamemode");
		permissions.add("bridgewars.trusted.fly");
		permissions.add("bridgewars.trusted.label");
		permissions.add("bridgewars.trusted.endgame");
		permissions.add("bridgewars.trusted.settings");
		permissions.add("worldedit.*");
		permissions.add("bukkit.command.setblock");
		permissions.add("bukkit.command.clear");
		permissions.add("bukkit.command.op");
		permissions.add("bukkit.command.teleport");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!sender.isOp() && sender instanceof Player) {
			sender.sendMessage(Message.chat("&cYou do not have permission to do this."));
			return false;
		}
		
		if(args.length < 2) {
			sender.sendMessage(Message.chat("&cUsage: /permission [get|clear|give|take] <player> [permission]"));
			return false;
		}
		
		UUID uuid = Utils.getUUID(args[1]);
		
		switch(args[0]) {
		case "get":
			sender.sendMessage(Permissions.getPermissions(uuid).toString());
			break;
			
		case "clear":
			for(String permission : Permissions.getPermissions(uuid))
				Permissions.setPermission(Utils.getUUID(args[1]), permission, false, true);
			sender.sendMessage(Message.chat("&c" + Utils.getName(uuid) + " has been revoked of all permissions."));
			break;
			
		case "give":
			if(!permissions.contains(args[2])) {
				sender.sendMessage(Message.chat("&cInvalid permission."));
				return false;
			}
			Permissions.setPermission(Utils.getUUID(args[1]), args[2], true, true);
			sender.sendMessage(Message.chat("&6"+ Utils.getName(uuid) + " has been given permission \"&c" + args[2] + "&6\"."));
			break;
			
		case "take":
			if(!permissions.contains(args[2])) {
				sender.sendMessage(Message.chat("&cInvalid permission. Permissions: <trusted>.[fly, gamemode, label, endgame, settings, worldedit]"));
				return false;
			}
			Permissions.setPermission(Utils.getUUID(args[1]), args[2], false, true);
			sender.sendMessage(Message.chat("&6"+ Utils.getName(uuid) + " has been revoked of permission \"&c" + args[2] + "&6\"."));
			break;
		}
		
		return false;
	}
}
