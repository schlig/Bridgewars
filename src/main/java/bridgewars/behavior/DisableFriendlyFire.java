package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import bridgewars.Main;
import bridgewars.game.CustomScoreboard;
import bridgewars.settings.FriendlyFire;

public class DisableFriendlyFire implements Listener {

	private CustomScoreboard cs;
	
	public DisableFriendlyFire(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		cs = new CustomScoreboard();
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) { //a manual friendly fire check for specific use cases
		if(e.getEntity() instanceof Player) {
			
			Player target = (Player) e.getEntity();
			Player attacker = null;
			
			if(e.getDamager() instanceof Player)
				attacker = (Player) e.getDamager();
			
			else if(e.getDamager() instanceof Projectile)
				attacker = (Player)((Projectile)e.getDamager()).getShooter();
			
			else return;
			
			if(target == attacker || cs.getTeam(attacker) == null)
				return;
			
			if(cs.matchTeam(target, attacker)) {
				if(FriendlyFire.getState().isEnabled())
					e.setDamage(0);
				else
					e.setCancelled(true);
			}
		}
	}
}
