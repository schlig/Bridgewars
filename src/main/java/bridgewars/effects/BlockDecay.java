package bridgewars.effects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.game.GameState;

public class BlockDecay extends BukkitRunnable {
	
	private int timer;
	private Block block;
	private Material material;
	
	public BlockDecay(Block block, int duration) {
		this.timer = duration;
		this.block = block;
		this.material = block.getType();
	}
	
	@Override
	public void run() {
		timer--;
		if(timer <= 0
		|| block.getType() != material 
		|| !GameState.isState(GameState.ACTIVE)) {
			
			block.breakNaturally();
			
			if(timer <= 0) {
				for(Player players : Bukkit.getOnlinePlayers()) {
					switch(material) {
					case WOOD:
						players.playSound(block.getLocation(), Sound.DIG_WOOD, 1f, 0.8f);
						break;
					case STONE:
						players.playSound(block.getLocation(), Sound.DIG_STONE, 1f, 0.8f);
						break;
					case IRON_BLOCK:
						players.playSound(block.getLocation(), Sound.DIG_STONE, 1f, 1.2f);
						break;
					case WOOL:
						players.playSound(block.getLocation(), Sound.DIG_WOOL, 1f, 0.8f);
						break;
					default:
						break;
					}
				}
			}
			
			this.cancel();
		}
	}
}
