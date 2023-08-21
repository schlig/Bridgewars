package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

import bridgewars.Main;

public class InstantVoidKill implements Listener {

	
	public InstantVoidKill(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onFall(PlayerMoveEvent e) {
		Location loc = e.getPlayer().getLocation();
		if(loc.getY() < -10 && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			loc.setY(loc.getY() - 100);
			e.getPlayer().teleport(loc);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player && e.getCause() == DamageCause.VOID)
			if(((Player) e.getEntity()).getGameMode() != GameMode.CREATIVE)
				e.setDamage(500);
	}
}
