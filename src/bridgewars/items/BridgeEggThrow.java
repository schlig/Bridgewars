package bridgewars.items;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import bridgewars.Main;
import bridgewars.effects.BridgeEgg;

public class BridgeEggThrow extends CustomItems implements Listener {
	
	public BridgeEggThrow(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onThrow(ProjectileLaunchEvent e) {
		if(e.getEntity() instanceof Egg && e.getEntity().getShooter() instanceof Player) {
			if( ((Player)e.getEntity().getShooter()).getGameMode() == GameMode.CREATIVE)
				new BridgeEgg(e.getEntity(), true).runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 3L, 0L);
			else
				new BridgeEgg(e.getEntity(), false).runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 3L, 0L);
		}
	}
}
