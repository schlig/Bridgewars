package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import bridgewars.Main;

public class Debug implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Debug(Main plugin) {
		plugin.getCommand("debug").setExecutor(this);
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		

		
		
		
		return false;
	}
}