package uhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import uhc.Main;

public class SetHealth implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public SetHealth(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("sethealth").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("No");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(args[0] == null || args[0] == "") {
			player.sendMessage(ChatColor.RED + "Usage: /sethealth <value>");
			return true;
		}
		try {
			if(Integer.parseInt(args[0]) > 0 && Integer.parseInt(args[0]) <= 1024) {
				player.setMaxHealth(Integer.parseInt(args[0]));
				player.setHealth(player.getMaxHealth());
				player.sendMessage("Maximum health set to " + args[0] + " (" +  String.valueOf(Integer.parseInt(args[0])/2) + " hearts)");
			}
			else {
				if(Integer.parseInt(args[0]) <= 0)
					player.sendMessage(ChatColor.RED + "The number you have entered (" + args[0] + ") is too small, it must be at least 1");
				if(Integer.parseInt(args[0]) > 1024)
					player.sendMessage(ChatColor.RED + "The number you have entered (" + args[0] + ") is too big, it must be at most 1024");
			}
			return true;
		} catch (NumberFormatException e){
			player.sendMessage(ChatColor.RED + "Usage: /sethealth <value>");
			return true;
		}
	}
}
