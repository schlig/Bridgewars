package bridgewars.effects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.game.GameState;
import bridgewars.utils.Utils;

public class BridgeEgg extends BukkitRunnable {

	private int xDir = 1, zDir = 1;
	private int height = 2;
	private Location loc;
	private Block block, blockR;
	
	private Egg e;
	private boolean override;
	
	public BridgeEgg(Entity e, boolean override) {
		this.e = (Egg) e;
		this.override = override;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		if(e.isDead() || GameState.isState(GameState.INACTIVE) && !override) {
			this.cancel();
			return;
		}
		
		loc = e.getLocation().add(e.getLocation().getDirection().multiply(-1));
				
		if(loc.getX() < 0) xDir = -1;
		else xDir = 1;
		if(loc.getZ() < 0) zDir = -1;
		else zDir = 1;
		
		loc.setX(Math.abs(Math.floor(loc.getX())) * xDir);
		loc.setY(loc.getY() - height);
		loc.setZ(Math.abs(Math.floor(loc.getZ())) * zDir);
				
		block = e.getWorld().getBlockAt(loc);
		
		for(int x = 0; x != xDir * 2; x += xDir)
			for(int z = 0; z != zDir * 2; z += zDir) {
				blockR = block.getRelative(x, 0, z);
				if(blockR.getType() == Material.AIR && !Utils.isOutOfBounds(blockR.getLocation(), 22, 24, 22) || override) {
					blockR.setType(Material.WOOL);
					blockR.setData((byte)Utils.rand(16));
				}
			}
	}
}
