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
import bridgewars.game.CSManager;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;

public class JoinTeam implements CommandExecutor, TabCompleter {
	
	private ArrayList<String> teams = new ArrayList<>();
	
	public JoinTeam(Main plugin) {
		plugin.getCommand("jointeam").setExecutor(this);
		teams.add("red");
		teams.add("blue");
		teams.add("green");
		teams.add("yellow");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if(GameState.isState(GameState.ACTIVE) && !p.isOp()) {
			p.sendMessage(Chat.color("&cYou can't change teams while a game is active!"));
			return true;
		}
		else {
			Player target = p;
			
			if(args.length == 2)
				target = Bukkit.getPlayer(args[1]);
			
			if(args.length == 1)
				CSManager.setTeam(target, args[0]);
			else
				CSManager.resetTeam(target, true);
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		final List<String> completionList = new ArrayList<>();
		final List<String> completeArgument = new ArrayList<>();
		if(args.length == 1){
			StringUtil.copyPartialMatches(args[0], teams, completionList);

			for(String index : completionList)
				if(!completeArgument.contains(index))
					completeArgument.add(index);
			
			Collections.sort(completeArgument);
			return completeArgument;
		}
		return null;
	}
}
