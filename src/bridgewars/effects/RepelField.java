package bridgewars.effects;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import bridgewars.game.CustomScoreboard;

public class RepelField extends BukkitRunnable {
	
	private CustomScoreboard cs;
	
    private Player u; //user
    private double d; //distance
    private double p; //this dictates the maximum power of the field's epicenter (y-intercept on a graph)
    private int t;    //duration
    
    public RepelField(Player u, double d, double p, int t){
        cs = new CustomScoreboard();
        this.u = u;
        this.d = d;
        this.t = t;
        this.p = p;
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
            		( ((p * d) / (Math.pow(d, 3))) * Math.pow(d - magnitude, 2) )));
        }
        t--;
        if(t <= 0) {
            this.cancel();
        }
    }
}