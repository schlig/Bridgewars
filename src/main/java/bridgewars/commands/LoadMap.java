package bridgewars.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import bridgewars.Main;
import bridgewars.utils.World;

public class LoadMap implements CommandExecutor, TabCompleter {
	
	private final String filepath = "./plugins/bridgewars/maps/";
	private final ArrayList<String> mapList = new ArrayList<>();
	
	public LoadMap(Main plugin) {
		plugin.getCommand("loadmap").setExecutor(this);
		File file = new File(filepath);
		String[] mapList = file.list();
		for(String map : mapList)
			this.mapList.add(map.substring(0, map.length() - 4));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!sender.isOp())
			return true;
		
		Player p = (Player) sender;
		String mapName = "";
		
		if(args.length == 0)
			mapName = null;
		
		else {
			for(int i = 0; i < args.length; i++)
				mapName = mapName + args[i] + " ";
			mapName = mapName.substring(0, mapName.length() - 1);
		}
		
		World.clearMap();
		World.loadMap(mapName, p, true);
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length == 1){
			final List<String> completionList = new ArrayList<>();
			final List<String> completeArgument = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], mapList, completionList);

			for(String index : completionList)
				if(!completeArgument.contains(index))
					completeArgument.add(index);
			
			Collections.sort(completeArgument);
			return completeArgument;
		}
		return null;
	}
}
