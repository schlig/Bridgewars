package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import bridgewars.Main;
import bridgewars.game.CustomScoreboard;

public class DisableFriendlyFire implements Listener {

	private CustomScoreboard cs;
	
	public DisableFriendlyFire(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		cs = new CustomScoreboard();
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)
		|| !(e.getDamager() instanceof Player))
			return;
		
		Player target = (Player) e.getEntity();
		Player attacker = (Player) e.getDamager();
		
		if(target == attacker || cs.getTeam(attacker) == null)
			return;
		
		if(cs.matchTeam(target, attacker))
			e.setCancelled(true);
	}
}
