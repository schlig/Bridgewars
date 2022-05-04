package uhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uhc.Main;
import uhc.utils.Utils;

public class Heal implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public Heal(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("heal").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("No");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(args.length == 0) {
			player.setHealth(player.getMaxHealth());
			player.setFoodLevel(20);
			player.setSaturation(40);
			player.sendMessage(Utils.chat("&aYou have been restored to health and saturation."));
		}
		else
		{
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if(target == null) {
				player.sendMessage(Utils.chat("&cThat player is not online."));
				return true;
			}
			target.setHealth(target.getMaxHealth());
			target.setFoodLevel(20);
			target.setSaturation(40);
			player.sendMessage(Utils.chat("&a" + target.getDisplayName() + " has been restored to full health and saturation."));
		}
		return true;
	}
}
