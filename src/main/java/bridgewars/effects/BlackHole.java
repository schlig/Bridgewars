package bridgewars.effects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.utils.Utils;
import bridgewars.utils.World;

public class BlackHole extends BukkitRunnable {

	private Entity e;
	private int radius;
	private Block block;
	private boolean override;
	
	public BlackHole(Entity e, int radius, boolean override) {
		this.e = e;
		this.radius = radius;
		this.override = override;
	}
	
	@Override
	public void run() {
		if(e.isDead())
			this.cancel();
		block = e.getWorld().getBlockAt(e.getLocation());
		for(int x = -radius; x <= radius; x++)
			for(int y = -radius; y <= radius; y++)
				for(int z = -radius; z <= radius; z++)
					if(block.getRelative(x, y, z).getType() != Material.BEDROCK
					&& !Utils.isOutOfBounds(block.getLocation(), 22, 24, 22)
					|| override) {
						if(World.blockIsIndestructible(block.getRelative(x, y, z))) {
							continue;
						}
						block.getRelative(x, y, z).setType(Material.AIR);
						
					}
		if(radius == 1)
			Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> radius = 2, 2L);
	}
}
