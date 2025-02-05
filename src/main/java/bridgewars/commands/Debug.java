package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.effects.DebugEffect;

public class Debug implements CommandExecutor {
	
	private Main plugin;
	
	public Debug(Main plugin) {
		plugin.getCommand("debug").setExecutor(this);
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player))
			return true;;
		
		Player user = (Player) sender;
		
        if(!user.isOp()) {
        	user.sendMessage("A severe unhandled error has occurred. Error: You're not Schlog.");
        	return true;
        }
        
//		public DebugEffect(Player p, double pitchChange, double yawChange, double radius, int duration) {
		
		new DebugEffect(user, Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Integer.parseInt(args[3])).runTaskTimer(plugin, 0, 1);
		
		return true;
	}
}