package bridgewars.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import bridgewars.Main;
import bridgewars.settings.PlayerSettings;
import bridgewars.utils.Utils;

public class PlayerSetting implements CommandExecutor, TabCompleter {
	
	private List<String> settings = new ArrayList<>();
	
	public PlayerSetting(Main plugin) {
		Bukkit.getPluginCommand("playersetting").setExecutor(this);
		
		settings.add("SwordSlot");
		settings.add("ShearsSlot");
		settings.add("WoolSlot");
		settings.add("KillstreakRewardWhite");
		settings.add("KillstreakRewardGreen");
		settings.add("KillstreakRewardRed");
		settings.add("KillstreakRewardBlue");
		settings.add("KillMessageMelee");
		settings.add("KillMessageVoid");
		settings.add("KillMessageBow");
		settings.add("KillMessageSuicide");
		settings.add("KillMessageSuffocation");
		settings.add("KillMessageFallDamage");
		settings.add("ColorblindMode");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!sender.isOp() || args.length < 3)
			return true;
		
		Player target = Bukkit.getPlayer(Utils.getUUID(args[0]));
		String setting = args[2];
		if(args.length > 3) {
			setting = " ";
			for(int i = 2; i < args.length; i++) {
				setting = setting + args[i] + " ";
			}
		}
		PlayerSettings.setSetting(target, args[1], setting);
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		final List<String> completionList = new ArrayList<>();
		final List<String> completeArgument = new ArrayList<>();
		if(args.length == 2){
			StringUtil.copyPartialMatches(args[1], settings, completionList);

			for(String index : completionList)
				if(!completeArgument.contains(index))
					completeArgument.add(index);
			
			Collections.sort(completeArgument);
			return completeArgument;
		}
		return null;
	}
}
