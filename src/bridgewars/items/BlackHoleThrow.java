package bridgewars.items;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

import bridgewars.Main;
import bridgewars.effects.BlackHole;

public class BlackHoleThrow extends CustomItems implements Listener {
	
	public BlackHoleThrow(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onThrow(ProjectileLaunchEvent e) {
		if(e.getEntity() instanceof Snowball) {
			if(!(e.getEntity().getShooter() instanceof Player))
				return;
			Vector v = e.getEntity().getVelocity();
			v.multiply(1.5);
			e.getEntity().setVelocity(v);

			new BlackHole(e.getEntity(), 1).runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 0L, 0L);
		}
	}
}
