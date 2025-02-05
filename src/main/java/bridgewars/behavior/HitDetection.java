package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import bridgewars.Main;
import bridgewars.game.CSManager;
import bridgewars.game.GameState;
import bridgewars.items.SadTear;
import bridgewars.items.SuperStar;
import bridgewars.settings.enums.FriendlyFire;

public class HitDetection implements Listener {
	
	public HitDetection(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) { //a manual friendly fire check for specific use cases
		if(e.getEntity() instanceof Player) {
			
			Player target = (Player) e.getEntity();
			Player attacker = null;
			Projectile proj = null;
			
			if(SuperStar.playerIsInvincible(target)) {
				e.setCancelled(true);
				return;
			}
			
			if(e.getDamager() instanceof Player)     //searches for attacker, grabs projectile shooter if it's an egg/arrow/snowball/fishing rod
				attacker = (Player) e.getDamager();  
			else if(e.getDamager() instanceof Projectile) {
				proj = (Projectile) e.getDamager();
				if(proj.getShooter() instanceof Player)
					attacker = (Player) proj.getShooter();
			} 
			
			else return; //just skips everything else since these are basically the only possible damage types other than splash potions of harming ig

			if(!GameState.isState(GameState.ACTIVE)
			|| SadTear.sadRoomed.contains(target))
				e.setDamage(0);
			
			if(CSManager.matchTeam(target, attacker)) {
				if(FriendlyFire.getState().isEnabled())
					e.setDamage(0);
				else
					e.setCancelled(true);
			}
		}
	}
}
