package bridgewars.effects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class BlackHole extends BukkitRunnable {

	private Entity e;
	private int radius;
	private Block block;
	
	public BlackHole(Entity e, int radius) {
		this.e = e;
		this.radius = radius;
	}
	
	@Override
	public void run() {
		if(e.isDead())
			this.cancel();
		block = e.getWorld().getBlockAt(e.getLocation());
		for(int x = -radius; x <= radius; x++)
			for(int y = -radius; y <= radius; y++)
				for(int z = -radius; z <= radius; z++)
					if(block.getRelative(x, y, z).getType() == Material.WOOL)
						block.getRelative(x, y, z).setType(Material.AIR);
		if(radius == 1)
			Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> radius = 2, 2L);
	}
}
