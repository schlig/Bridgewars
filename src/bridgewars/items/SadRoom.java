package bridgewars.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import bridgewars.Main;
import bridgewars.game.CombatTagging;

public class SadRoom implements Listener {
	
	private CombatTagging ct;
	
	public SadRoom(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		ct = new CombatTagging();
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player
		&& e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player k = (Player) e.getDamager();
			ct.setAttacker(p, k);
			ct.setMurderWeapon(p, k.getItemInHand());
			ct.setDamageCause(p, e.getCause());
			
			if(ct.getMurderWeapon(p) == Material.GHAST_TEAR) {
				
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = (Player) e.getEntity();
		ct.setAttacker(p, null);
		ct.setMurderWeapon(p, null);
		ct.setDamageCause(p, null);
	}
}
