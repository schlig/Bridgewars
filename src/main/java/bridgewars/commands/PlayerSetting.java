package bridgewars.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.settings.PlayerSettings;
import bridgewars.utils.Utils;

public class PlayerSetting implements CommandExecutor {
	
	public PlayerSetting(Main plugin) {
		Bukkit.getPluginCommand("playersetting").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!sender.isOp() || args.length < 3)
			return true;
		
		Player target = Bukkit.getPlayer(Utils.getUUID(args[0]));
		PlayerSettings.setSetting(target, args[1], args[2]);
		
		return true;
	}
}
