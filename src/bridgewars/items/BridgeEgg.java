package bridgewars.items;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.Utils;

public class BridgeEgg implements Listener {
	
	public BridgeEgg(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onThrow(ProjectileLaunchEvent e) {
		if(!(e.getEntity() instanceof Egg)
		|| !(e.getEntity().getShooter() instanceof Player))
			return;
		
		new BukkitRunnable(){
			int ticks = 0, x = 1, z = 1;
			Location loc;
			Block block, blockR;
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				
				if(e.getEntity().isDead() || ticks == 200) {
					this.cancel();
					return;
				}
				
				if(GameState.getState() == GameState.ACTIVE) {
					loc = e.getEntity().getLocation().add(e.getEntity().getLocation().getDirection().multiply(-1));
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
						
					block = e.getEntity().getWorld().getBlockAt(loc);
					
					if(block.getType() == Material.AIR
							&& Math.abs(block.getX()) <= 22
							&& block.getY() <= 24
							&& Math.abs(block.getZ()) <= 22) {
						block.setType(Material.WOOL);
						block.setData((byte)Utils.rand(16));
					}

					blockR = block.getRelative(x, 0, 0);
					if(blockR.getType() == Material.AIR
							&& Math.abs(blockR.getX()) <= 22
						    && blockR.getY() <= 24
							&& Math.abs(blockR.getZ()) <= 22) {
						blockR.setType(Material.WOOL);
						blockR.setData((byte)Utils.rand(16));;
					}

					blockR = block.getRelative(0, 0, z);
					if(blockR.getType() == Material.AIR
							&& Math.abs(blockR.getX()) <= 22
							&& blockR.getY() <= 24
							&& Math.abs(blockR.getZ()) <= 22) {
						blockR.setType(Material.WOOL);
						blockR.setData((byte)Utils.rand(16));;
					}

					blockR = block.getRelative(x, 0, z);
					if(blockR.getType() == Material.AIR
							&& Math.abs(blockR.getX()) <= 22
							&& blockR.getY() <= 24
							&& Math.abs(blockR.getZ()) <= 22) {
						blockR.setType(Material.WOOL);
						blockR.setData((byte)Utils.rand(16));;
					}
				}
				else if(GameState.getState() == GameState.INACTIVE)
					if(e.getEntity().getShooter() instanceof Player) {
						Player p = (Player) e.getEntity().getShooter();
						if(p.getGameMode() == GameMode.CREATIVE) {
							loc = e.getEntity().getLocation().add(e.getEntity().getLocation().getDirection().multiply(-1));
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
								
							block = e.getEntity().getWorld().getBlockAt(loc);
							
							if(block.getType() == Material.AIR) {
								block.setType(Material.WOOL);
								block.setData((byte)Utils.rand(16));
							}

							blockR = block.getRelative(x, 0, 0);
							if(blockR.getType() == Material.AIR) {
								blockR.setType(Material.WOOL);
								blockR.setData((byte)Utils.rand(16));;
							}

							blockR = block.getRelative(0, 0, z);
							if(blockR.getType() == Material.AIR) {
								blockR.setType(Material.WOOL);
								blockR.setData((byte)Utils.rand(16));;
							}

							blockR = block.getRelative(x, 0, z);
							if(blockR.getType() == Material.AIR) {
								blockR.setType(Material.WOOL);
								blockR.setData((byte)Utils.rand(16));;
							}
						}
					}
				ticks++;
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 3L, 1L);
	}
}
