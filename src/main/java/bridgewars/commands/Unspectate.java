package bridgewars.commands;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Unspectate implements CommandExecutor {

	public Unspectate(Main plugin) {
		plugin.getCommand("spectate").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return true;
		
		Player p = (Player) sender;

		p.setGameMode(GameMode.SURVIVAL);
		p.teleport(new Location(Bukkit.getWorld("world"), 1062.5, 52, 88.5, -90, 10));
		p.sendMessage(Message.chat("&6You left spectators."));
		
		return false;
	}
}
