package bridgewars.messages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import bridgewars.Main;
import bridgewars.commands.Fly;
import bridgewars.effects.Cloak;
import bridgewars.game.CSManager;
import bridgewars.game.GameState;
import bridgewars.items.SadTear;
import bridgewars.utils.Permissions;
import bridgewars.utils.Utils;

public class OnLeave implements Listener {
	
	public OnLeave(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		Cloak.remove(p);
		CSManager.resetTeam(p, false);
		CSManager.removePlayerFromTimer(p);
		SadTear.removePlayerFromSadRoom(p);
		Permissions.flush(p.getUniqueId());
		if(Bukkit.getOnlinePlayers().size() == 0)
			if(GameState.isState(GameState.EDIT))
				GameState.setState(GameState.INACTIVE);
		Fly.allowFlight.remove(p);
		e.setQuitMessage(Chat.color("&e" + Utils.getName(p.getUniqueId()) + " left the game"));
	}
}
