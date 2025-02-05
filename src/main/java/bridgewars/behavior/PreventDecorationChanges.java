package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import bridgewars.Main;

public class PreventDecorationChanges implements Listener {

	public PreventDecorationChanges(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEntityEvent e) {
		if(e.getRightClicked() instanceof ItemFrame && e.getPlayer().getGameMode() != GameMode.CREATIVE)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onRightClick(PlayerArmorStandManipulateEvent e) {
		if(e.getPlayer().getGameMode() != GameMode.CREATIVE)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onLeftClick(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof ItemFrame)
			e.setCancelled(true);
		
		if(e.getDamager() instanceof Player)
			if(((Player)e.getDamager()).getGameMode() == GameMode.CREATIVE)
				e.setCancelled(false);
	}
}
