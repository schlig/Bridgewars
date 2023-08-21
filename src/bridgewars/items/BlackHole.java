package bridgewars.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import bridgewars.Main;

public class BlackHole implements Listener {
	
	public BlackHole(Main plugin) {
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
			

			new BukkitRunnable() {
				Block block;
				int r = 1;
				
				@Override
				public void run() {
					if(e.getEntity().isDead())
						this.cancel();
					block = e.getEntity().getWorld().getBlockAt(e.getEntity().getLocation());
					for(int x = -r; x <= r; x++)
						for(int y = -r; y <= r; y++)
							for(int z = -r; z <= r; z++)
								if(block.getRelative(x, y, z).getType() == Material.WOOL)
									block.getRelative(x, y, z).setType(Material.AIR);
					if(r == 1)
						Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> r = 2, 2L);
				}
			}.runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 0L, 1L);
		}
	}
}
