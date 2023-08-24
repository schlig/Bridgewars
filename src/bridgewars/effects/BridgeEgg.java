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

	private int x = 1, z = 1;
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
		loc.setY(loc.getY() - 2);
				
		if(loc.getX() > 0)
			loc.setX(Math.floor(loc.getX()));
		else {
			loc.setX(Math.ceil(loc.getX()));
			x = -1;
		}
				
		if(loc.getZ() > 0)
			loc.setZ(Math.floor(loc.getZ()));
		else {
			loc.setZ(Math.ceil(loc.getZ()));
			z = -1;
		}
				
		block = e.getWorld().getBlockAt(loc);
			
		if(block.getType() == Material.AIR)
			if(!Utils.isOutOfBounds(block.getLocation(), 21, 24, 21) || override) {
				block.setType(Material.WOOL);
				block.setData((byte)Utils.rand(16));
			}
			
		blockR = block.getRelative(x, 0, 0);
		if(blockR.getType() == Material.AIR)
			if(!Utils.isOutOfBounds(block.getLocation(), 21, 24, 21) || override) {
				blockR.setType(Material.WOOL);
				blockR.setData((byte)Utils.rand(16));
			}

		blockR = block.getRelative(0, 0, z);
		if(blockR.getType() == Material.AIR)
			if(!Utils.isOutOfBounds(block.getLocation(), 21, 24, 21) || override){
				blockR.setType(Material.WOOL);
				blockR.setData((byte)Utils.rand(16));
			}
			
		blockR = block.getRelative(x, 0, z);
		if(blockR.getType() == Material.AIR)
			if(!Utils.isOutOfBounds(block.getLocation(), 21, 24, 21) || override){
				blockR.setType(Material.WOOL);
				blockR.setData((byte)Utils.rand(16));
			}
	}
}
