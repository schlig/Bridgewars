package bridgewars.effects;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.Main;
import bridgewars.game.CustomScoreboard;

public class ScoreboardJam extends BukkitRunnable {
	
	private CustomScoreboard cs;
	
    private Player u; //user
    private int t;    //duration
    
    private Main plugin;
    
    public ScoreboardJam(Player u, double d, double p, int t, Main plugin){
        cs = new CustomScoreboard();
        this.u = u;
        this.t = t;
        this.plugin = plugin;
    }
    
    @Override
    public void run(){
    	this.cancel();
    }
}
