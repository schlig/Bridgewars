package bridgewars.effects;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import bridgewars.Main;
import bridgewars.game.CSManager;
import bridgewars.game.GameState;
import bridgewars.utils.Packet;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class RepelField extends BukkitRunnable {
	
    private final Player user; //user
    private final double radius; //radius
    private final double power; //this dictates the maximum power of the field's epicenter (y-intercept on a graph)
    private final int particleDensity;
    
    private int duration;    //duration
    
    public RepelField(Player user, double radius, double power, int duration, Main plugin){
        this.user = user;
        this.radius = radius;
        this.power = power;
        this.particleDensity = (int) (power * 10);
        
        this.duration = duration;
    }
    
    @Override
    public void run(){
        Location pLoc = user.getLocation();
        List<Entity> entities = user.getNearbyEntities(radius, radius, radius);
        for (Entity e : entities) {
        	if(entities.size() <= 0)
        		break;
            Location eLoc = e.getLocation();
            Vector directionVector = eLoc.toVector().subtract(pLoc.toVector());
            double magnitude = directionVector.distance(new Vector(0,0,0));
            
            if(e instanceof Projectile) { //prevent repulsion of projectiles thrown by teammates (or yourself)
            	if( ((Projectile)e).getShooter() instanceof Player )
            		if(CSManager.matchTeam(user, (Player)((Projectile)e).getShooter() ))
            			continue;
            }
            
            if(e instanceof Player) //prevent repulsion of teammates or creative mode players
            	if(CSManager.matchTeam(user, (Player)e) || ((Player)e).getGameMode() == GameMode.CREATIVE)
            		continue;
            
            if(magnitude > radius) //if magnitude is less than d it will start pulling entities inward
            	continue;
            
            e.setVelocity(e.getVelocity().add(directionVector.multiply //hugely increases pushback the closer you are to target
            		( (power / (Math.pow(radius, 4))) * Math.pow(radius - magnitude, 4) )));
        }

        Vector particlePos;
        
        for(int i = 0; i < particleDensity; i++) {
            particlePos = new Vector(Math.random() - .5f, Math.random() - .5f, Math.random() - .5f).normalize();
	        Packet.sendParticle(EnumParticle.CRIT_MAGIC,  
	            	(float) user.getLocation().getX() + (float) (particlePos.getX() * radius), 
	            	(float) user.getLocation().getY() + 0.5f + (float) (particlePos.getY() * radius), 
	            	(float) user.getLocation().getZ() + (float) (particlePos.getZ() * radius),
	            	0, 0, 0, 0.05f, 5);
        }
        
        duration--;
        if(duration <= 0 || GameState.isState(GameState.ENDING))
            this.cancel();
    }
}