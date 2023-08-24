package bridgewars.utils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.game.CustomScoreboard;
import bridgewars.settings.TimeLimit;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class Particle extends BukkitRunnable {
	
	private CustomScoreboard cs = new CustomScoreboard();
	private TimeLimit tl = new TimeLimit();
	
	private Entity target;
	private EnumParticle effect;
	
	private float x, y, z;
	private float targetXPos, targetYPos, targetZPos;
	private float xOffset, yOffset, zOffset;
	private float speed;
	private int duration;
	private int amount;
	private boolean checkTimeLimit;
	
	public Particle(Entity target, EnumParticle effect, float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int amount, int duration, boolean checkTimeLimit) {
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

//	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "execute @a[score_time_min=" + ((Integer)(limit.getLimit() - 15)).toString() + "] ~ ~ ~ particle explode ~ ~26 ~ .25 10 .25 .0001 20 force @a");
	@Override
	public void run() {
		
		if(checkTimeLimit && target instanceof Player)
			if(cs.getTime((Player) target) < tl.getLimit() - 15)
				this.cancel();
		
		if(target.isDead() || duration == 0) {
			this.cancel();
			return;
		}
		
		targetXPos = (float) target.getLocation().getX();
		targetYPos = (float) target.getLocation().getY();
		targetZPos = (float) target.getLocation().getZ();
		
		for(Player p : Bukkit.getOnlinePlayers())
			sendParticlePacket(p, targetXPos + x, targetYPos + y, targetZPos + z, xOffset, yOffset, zOffset);
		
		duration--;
	}
	
	private void sendParticlePacket(Player p, float x, float y, float z, float xOffset, float yOffset, float zOffset) {
		PacketPlayOutWorldParticles particlePacket = new PacketPlayOutWorldParticles(effect, true, x, y, z, xOffset/255, yOffset/255, zOffset/255, speed, amount);
		CraftPlayer player = (CraftPlayer) p;
        PlayerConnection connection = player.getHandle().playerConnection;
        connection.sendPacket(particlePacket);
	}
}
