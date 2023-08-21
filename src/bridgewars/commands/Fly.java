package bridgewars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.CustomScoreboard;
import bridgewars.game.GameState;
import bridgewars.utils.Utils;

public class Fly implements CommandExecutor {
	
	private CustomScoreboard cs = new CustomScoreboard();

	public Fly(Main plugin) {
		plugin.getCommand("fly").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if(GameState.isState(GameState.ACTIVE)
		&& cs.getTeam(p) != null) {
			p.sendMessage(Utils.chat("&cYou can't fly while in a game!"));
		}
		else {
			if(p.getAllowFlight()) {
				p.setAllowFlight(false);
				p.setFlying(false);
				p.sendMessage(Utils.chat("&6Turned off flight"));
			}
			else {
				p.setAllowFlight(true);
				p.sendMessage(Utils.chat("&6Turned on flight"));
			}
		}
		
		return false;
	}
}
