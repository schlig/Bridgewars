package bridgewars.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import bridgewars.Main;
import bridgewars.messages.Chat;

public class Leaderboards implements CommandExecutor {

	public Leaderboards(Main plugin) {
		Bukkit.getPluginCommand("leaderboards").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage(Chat.color("&cYou do not have permission to do this."));
		}
		
		switch(args[0]) {
		case "":
			break;
		
		default:
			sender.sendMessage(Chat.color("&cAn error has occurred."));
			return true;
		}
		
		return true;
	}
}
