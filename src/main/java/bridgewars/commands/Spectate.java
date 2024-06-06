package bridgewars.commands;

import bridgewars.Main;
import bridgewars.game.Game;
import bridgewars.game.GameState;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spectate implements CommandExecutor {

	public Spectate(Main plugin) {
		plugin.getCommand("spectate").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return true;
		
		Player p = (Player) sender;
		
		if(GameState.isState(GameState.INACTIVE)) {
			p.sendMessage(Message.chat("&cThere is no game in progress."));
			return false;
		}
		
		else if(GameState.isState(GameState.EDIT)) {
			p.sendMessage(Message.chat("&cThe game is currently in Edit mode."));
			return false;
		}

		Location target = new Location(Bukkit.getWorld("world"), 0, 34, 0, 0, 20);
		if (args.length > 0){
			target = Bukkit.getPlayer(args[0]).getLocation();
		}
		p.setGameMode(GameMode.SPECTATOR);
		p.teleport(target);
		p.sendMessage(Message.chat("&6You joined spectators."));
		
		return false;
	}
}
