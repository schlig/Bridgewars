package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.Game;
import bridgewars.utils.Message;

public class StartGame implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public StartGame(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("startgame").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return true;
		
		Player p = (Player) sender;
		
		if(p.isOp())
			Game.startGame(p, true);
		
		else
			p.sendMessage(Message.chat("&cYou do not have permission to do this."));
		
		return false;
	}
}
