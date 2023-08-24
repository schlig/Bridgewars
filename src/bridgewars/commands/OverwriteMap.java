package bridgewars.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.utils.Message;

public class OverwriteMap implements CommandExecutor {
	
	private String filepath = "./plugins/bridgewars/maps/";
	
	public OverwriteMap(Main plugin) {
		plugin.getCommand("overwritemap").setExecutor(this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return true;
		
		Player p = (Player) sender;
		
		if(p.isOp()) {
			if(args.length == 0) {
				p.sendMessage(Message.chat("&cYou must specify a map name."));
				return false;
			}
			
			String mapName = "";
			
			for(int i = 0; i < args.length; i++)
				mapName = mapName + args[i] + " ";
			
			mapName = mapName.substring(0, mapName.length() - 1);
			
			File file = new File(filepath + mapName + ".map");
			if(!file.exists()) {
				p.sendMessage(Message.chat("&cThat map does not exist."));
				return false;
			}
			try {
				Block block;
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write("0");
				bw.newLine();
				for(int x = -22; x <= 22; x++)
					for(int z = -22; z <= 22; z++)
						for(int y = 0; y <= 24; y++) {
							block = Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld(label), x, y, z));
							if(block.getType() != Material.AIR) {
								bw.write(block.getType().toString() + "," + 
										((Integer)block.getX()).toString() + "," +
										((Integer)block.getY()).toString() + "," +
										((Integer)block.getZ()).toString() + "," +
										((Integer)(int)block.getData()).toString());
								bw.newLine();
							}
						}
				bw.flush();
				bw.close();
				p.sendMessage(Message.chat("&7Saved new version of map \"&6" + mapName + "&7\"."));
			} catch (IOException e) {
				p.sendMessage(Message.chat("&cFailed to save the map."));
			}
		}
	
		else
			p.sendMessage(Message.chat("&cYou do not have permission to do this."));
		
		return false;
	}
}
