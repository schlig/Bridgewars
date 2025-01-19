package bridgewars.effects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import bridgewars.Main;
import bridgewars.game.CustomScoreboard;

public class Piggyback implements Listener {

	private static CustomScoreboard cs = new CustomScoreboard();
	
	public Piggyback(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClick(PlayerInteractAtEntityEvent e) {
		if(e.getRightClicked() instanceof Player 
				&& bridgewars.settings.Piggyback.getState().isEnabled()) {
			Player p = e.getPlayer();
			Player t = (Player) e.getRightClicked();
			if(cs.matchTeam(p, t))
				t.setPassenger(p);
		}
	}
}
