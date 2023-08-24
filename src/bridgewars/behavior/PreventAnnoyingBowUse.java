package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.Message;

public class PreventAnnoyingBowUse implements Listener {

	public PreventAnnoyingBowUse(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onShoot(EntityShootBowEvent e){
		if(e.getEntity() instanceof Player
		&& GameState.isState(GameState.ACTIVE)) {
			Player p = (Player) e.getEntity();
			if(p.getGameMode() != GameMode.CREATIVE) {
				if(p.getLocation().getY() > 30) {
					e.setCancelled(true);
					p.sendMessage(Message.chat("&cYou can't shoot arrows from spawn!"));
				}
			}
		}
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player
		&& e.getDamager() instanceof Arrow
		&& GameState.isState(GameState.ACTIVE)) {
			Player p = (Player) e.getEntity();
			if(p.getGameMode() != GameMode.CREATIVE && p.getLocation().getY() > 30)
				e.setCancelled(true);
		}
	}
}
