package bridgewars.effects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import bridgewars.utils.Packet;
import bridgewars.utils.Utils;
import net.minecraft.server.v1_8_R3.EnumParticle;

@SuppressWarnings("unused")
public class DebugEffect extends BukkitRunnable{
	
	private Player p;
	private double pitchChange, yawChange, radius;
	private int duration;
	
	public DebugEffect(Player p, double pitchChange, double yawChange, double radius, int duration) {
		this.p = p;
		this.radius = radius;
		this.pitchChange = pitchChange;
		this.yawChange = yawChange;
		this.duration = duration;
	}

	@Override
	public void run() {
		Location origin = p.getEyeLocation();
		Vector ray = Utils.raycast(origin.getYaw(), origin.getPitch());
		List<Location> points = new ArrayList<>();
		World world = p.getWorld();
		for(int i = 0; i < 8; i++) {
			Vector point = Utils.raycast(origin.getYaw() + 90, i * 45);
			points.add(new Location(world, point.getX(), point.getY(), point.getZ()));
		}
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < points.size(); j++)
				Packet.sendParticle(EnumParticle.REDSTONE, (float)points.get(j).getX(), (float)points.get(j).getY(), (float)points.get(j).getZ(), 255, 255, 255, 1, 0);
		}
		
		duration--;
		if(duration <=0 )
			this.cancel();
	}
	
	private Vector raycast(double yaw, double pitch, double pitchRotation) {
		//this is such a basic raytracing formula but minecraft's coordinate system
		//is fucking dumb and is rotated on the x axis or something
		//anyways you can probably use this to draw more than just lines by plugging in whatever you want for pitch and yaw, or using loops to get neat results
		
		double theta = Math.toRadians(yaw); //angle for the x/z axis
		double phi = Math.toRadians(pitch); //"height" angle

		double x = Math.cos(phi) * Math.sin(theta);
		double z = Math.cos(phi) * Math.cos(theta);
		double y = Math.sin(phi);
		
		return new Vector(x, y, z).normalize();
	}
}
