package bridgewars.effects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.game.GameState;
import bridgewars.settings.enums.WoolDecay;
import bridgewars.utils.Packet;
import bridgewars.utils.Utils;

public class BlockDecay extends BukkitRunnable {

	//runs every tick
	//probably a bad idea
	
	private final int duration;
	private final int blockID;
	
	private byte damageTick;
	private double timer;
	private Block block;
	private final Material material;
	
	public BlockDecay(Block block, int duration) {
		this.blockID = Utils.rand(2147483647);
		this.duration = duration;
		
		this.damageTick = 0;
		this.timer = duration;
		this.block = block;
		this.material = block.getType();
	}
	
	@Override
	public void run() {
		timer--;
		
		if(timer == duration - ((duration / 10) * (damageTick + 1)) ) {
			Packet.sendBlockDamageAnimation(blockID, block.getLocation(), damageTick);
			damageTick += 1;
		}
		
		if(block.getType() == Material.WOOL && !WoolDecay.getState().isEnabled()) {
			Packet.sendBlockDamageAnimation(blockID, block.getLocation(), (byte) -1);
			this.cancel();
		}
		
		if(timer <= 0
		|| block.getType() != material 
		|| GameState.isState(GameState.ACTIVE)) {
			Packet.sendBlockDamageAnimation(blockID, block.getLocation(), (byte) -1);
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
