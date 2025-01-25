package bridgewars.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.messages.Chat;
import bridgewars.utils.Utils;

public class Whisper implements CommandExecutor {
	
	public Whisper(Main plugin) {
		Bukkit.getPluginCommand("whisper").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(args.length < 2) {
			sender.sendMessage(Chat.color("&cUsage: /whisper <player> <message>"));
			return true;
		}
		
		Player target = Bukkit.getPlayer(Utils.getUUID(args[0]));
		String senderName = sender instanceof Player ? ((Player) sender).getDisplayName() : "&6Console";
		
		if(target == null || !target.isOnline()) {
			sender.sendMessage(Chat.color("&cThat player is not online."));
			return true;
		}
		
		else {
			String message = "";
			for(int i = 1; i < args.length; i++)
				message = message + args[i] + " ";
			message = message.substring(0, message.length() - 1);
			sender.sendMessage(Chat.color("&7[&6You &7>&r " + target.getDisplayName() + "&7]: " + message));
			target.sendMessage(Chat.color("&7[&r" + senderName + "&7 > &6You&7]: ") + message);
		}
		
		return true;
	}
}
