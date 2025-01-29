package bridgewars.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;

public class EditMode implements CommandExecutor {
	
	public EditMode(Main plugin) {
		plugin.getCommand("editmode").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return true;
		
		Player p = (Player) sender;
		
		if(p.isOp()) {
			if(GameState.isState(GameState.ACTIVE)) {
				p.sendMessage(Chat.color("&cYou can't enter edit mode while a game is active!"));
				return true;
			}
			else if(GameState.isState(GameState.INACTIVE)) {
				Bukkit.broadcastMessage(Chat.color("&6Edit mode has been enabled."));
				p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 12, 0.5));
				p.setGameMode(GameMode.CREATIVE);
				GameState.setState(GameState.EDIT);
				return true;
			}
			else if(GameState.isState(GameState.EDIT)) {
				Bukkit.broadcastMessage(Chat.color("&6Edit mode has been disabled."));
				GameState.setState(GameState.INACTIVE);
				return true;
			}
		}
		
		else
			p.sendMessage(Chat.color("&cYou do not have permission to do this."));
		
		return true;
	}
}
