package bridgewars.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;

public class Fly implements CommandExecutor {
	
	public static HashMap<Player, Boolean> allowFlight = new HashMap<>();

	public Fly(Main plugin) {
		plugin.getCommand("fly").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if(!p.hasPermission("trusted.fly")) {
			p.sendMessage(Message.chat("&cYou do not have permission to do this."));
			return false;
		}
		
		if(GameState.isState(GameState.ACTIVE)
		&& !Utils.isOutOfBounds(p.getLocation(), 200, 40, 200)
		&& !p.isOp())
			p.sendMessage(Message.chat("&cYou can't fly while in a game!"));
		else if(bridgewars.parkour.Timer.parkourList.contains(p)
				&& !p.isOp()) {
			p.sendMessage(Message.chat("&cYou can't fly during a parkour challenge!"));
		}
		
		else
			setFlight(p, !allowFlight.get(p), true);
		
		return false;
	}
	
	public static void setFlight(Player p, boolean state, boolean showMessage) {
		allowFlight.put(p, state);
		p.setAllowFlight(state);
		
		if(!state)
			p.setFlying(false);
		
		if(showMessage)
			if(state)
				p.sendMessage(Message.chat("&6Turned on flight"));
			else
				p.sendMessage(Message.chat("&6Turned off flight"));
	}
}
