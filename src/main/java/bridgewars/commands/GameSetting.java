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
import bridgewars.messages.Chat;
import bridgewars.settings.GameSettings;

public class GameSetting implements CommandExecutor, TabCompleter {
	
	private List<String> settings = new ArrayList<>();
	
	public GameSetting(Main plugin) {
		Bukkit.getPluginCommand("gamesetting").setExecutor(this);
		
		settings.add("timelimit");
		settings.add("killbonus");
		settings.add("gameradius");
		settings.add("gameheight");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!sender.isOp())
			return true;
		
		Player player = (Player) sender;
		
		try {
			switch(args[0]) {
			case "timelimit":
				GameSettings.setTimeLimit(Integer.parseInt(args[1]));
				break;
			case "killbonus":
				GameSettings.setKillBonus(Integer.parseInt(args[1]));
				break;
			case "gameradius":
				GameSettings.setGameRadius(Integer.parseInt(args[1]));
				break;
			case "gameheight":
				GameSettings.setGameHeight(Integer.parseInt(args[1]));
				break;
			}
		}
		catch (Exception e) {
			player.sendMessage(Chat.color("&cUsage: /gamesetting <setting> <amount> &7(defaults: 150, 0, 22, 24)"));
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		final List<String> completionList = new ArrayList<>();
		final List<String> completeArgument = new ArrayList<>();
		if(args.length == 1){
			StringUtil.copyPartialMatches(args[0], settings, completionList);

			for(String index : completionList)
				if(!completeArgument.contains(index))
					completeArgument.add(index);
			
			Collections.sort(completeArgument);
			return completeArgument;
		}
		return null;
	}
}
