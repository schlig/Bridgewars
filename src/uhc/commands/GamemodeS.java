package uhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uhc.Main;
import uhc.utils.Utils;

public class GamemodeS implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public GamemodeS(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("gms").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("No");
			return true;
		}

		Player player = (Player) sender;
		
		if(args.length == 0) {
			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage("Your game mode has been updated");
		}
		else
		{
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if(target == null) {
				player.sendMessage(Utils.chat("&cThat player is not online."));
				return true;
			}
			target.setGameMode(GameMode.SURVIVAL);
			player.sendMessage("Set " + target.getName() + "'s game mode to Survival Mode");
			target.sendMessage(Utils.chat("Your game mode has been updated"));
		}
		return true;
	}
}
