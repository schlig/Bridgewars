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
import bridgewars.game.CustomScoreboard;
import bridgewars.utils.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class RepelField extends BukkitRunnable {
	
	private CustomScoreboard cs;
	
    private Player u; //user
    private double d; //distance
    private double p; //this dictates the maximum power of the field's epicenter (y-intercept on a graph)
    private int t;    //duration
    
    private Main plugin;
    
    public RepelField(Player u, double d, double p, int t, Main plugin){
        cs = new CustomScoreboard();
        this.u = u;
        this.d = d;
        this.t = t;
        this.p = p;
        this.plugin = plugin;
    }
    
    @Override
    public void run(){
        Location pLoc = u.getLocation();
        List<Entity> entities = u.getNearbyEntities(d,d,d);
        for (Entity e : entities) {
            Location eLoc = e.getLocation();
            Vector directionVector = eLoc.toVector().subtract(pLoc.toVector());
            double magnitude = directionVector.distance(new Vector(0,0,0));
            
            if(e instanceof Projectile) { //prevent repulsion of projectiles thrown by teammates (or yourself)
            	if( ((Projectile)e).getShooter() instanceof Player )
            		if(cs.matchTeam(u, (Player)((Projectile)e).getShooter() ))
            			continue;
            }
            
            if(e instanceof Player) //prevent repulsion of teammates or creative mode players
            	if(cs.matchTeam(u, (Player)e) || ((Player)e).getGameMode() == GameMode.CREATIVE)
            		continue;
            
            if(magnitude > d) //if magnitude is less than d it will start pulling entities inward
            	continue;
            
            e.setVelocity(e.getVelocity().add(directionVector.multiply //put this formula into desmos to understand it lol
            		( (p / (Math.pow(d, 4))) * Math.pow(d - magnitude, 4) )));
        }

        Vector particlePos;
        for(int i = 0; i < 5; i++) {
            particlePos = new Vector(Math.random() - .5f, Math.random() - .5f, Math.random() - .5f).normalize();
            new Particle(u, EnumParticle.CRIT_MAGIC,
                    (float) (particlePos.getX() * d), (float) (particlePos.getY() * d), (float) (particlePos.getZ() * d),
                    0, 0, 0,
                    0.05f, 5, 1000, false).runTask(plugin);
        }
        t--;
        if(t <= 0) {
            this.cancel();
        }
    }
}