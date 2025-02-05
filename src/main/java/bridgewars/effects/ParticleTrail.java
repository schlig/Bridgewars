package bridgewars.effects;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.game.CSManager;
import bridgewars.game.GameState;
import bridgewars.settings.GameSettings;
import bridgewars.utils.Packet;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class ParticleTrail extends BukkitRunnable {
	
	//this class is solely used for spawning the particle beam above players when they're near the time limit
	//use packet.sendparticle with some math for your own effects
	
	private Entity target;
	private EnumParticle effect;
	
	private float x, y, z;
	private float targetXPos, targetYPos, targetZPos;
	private float xOffset, yOffset, zOffset;
	private float speed;
	private int duration;
	private int amount;
	private boolean checkTimeLimit;
	
	public ParticleTrail(Entity target, EnumParticle effect, float x, float y, float z, 
			float xOffset, float yOffset, float zOffset, float speed, int amount, 
			int duration, boolean checkTimeLimit) {
		this.target = target;
		this.effect = effect;

		this.targetXPos = (float) target.getLocation().getX();
		this.targetYPos = (float) target.getLocation().getY();
		this.targetZPos = (float) target.getLocation().getZ();
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
		
		this.speed = speed;
		this.duration = duration;
		this.amount = amount;
		
		this.checkTimeLimit = checkTimeLimit;
	}


	@Override
	public void run() {
		if(checkTimeLimit && target instanceof Player)
			if(CSManager.getTime((Player) target) < GameSettings.getRevealTime())
				this.cancel();
		
		if(target.isDead() || duration == 0 || GameState.isState(GameState.ENDING)) {
			this.cancel();
			return;
		}
		
		targetXPos = (float) target.getLocation().getX();
		targetYPos = (float) target.getLocation().getY();
		targetZPos = (float) target.getLocation().getZ();
		
		Packet.sendParticle(effect, targetXPos + x, targetYPos + y, targetZPos + z, xOffset, yOffset, zOffset, speed, amount);
		
		duration--;
	}
}
