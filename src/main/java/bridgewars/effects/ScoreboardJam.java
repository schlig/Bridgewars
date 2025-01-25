package bridgewars.effects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import bridgewars.Main;
import bridgewars.game.CSManager;
import bridgewars.utils.Utils;

public class ScoreboardJam extends BukkitRunnable {
	
    private int timer;    //duration
    
    public static Boolean forceCancel = false;
    public static List<Player> blacklist = new ArrayList<Player>();
    
    private static Scoreboard scrambledScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    
    public ScoreboardJam(Player user, int duration, Main plugin){
        this.timer = duration;
        
        blacklist.clear();
        forceCancel = false;
        
        for(Player target : Bukkit.getOnlinePlayers())
        	if(!Utils.matchTeam(target, user)) {
        		blacklist.add(target);
        		target.setScoreboard(scrambledScoreboard);
        	}
        	else
        		CSManager.sendScoreboard(target);
    }
    
    @Override
    public void run(){
    	timer--;
    	if(timer <= 0 || forceCancel) {
    		timer = 0;
    		forceCancel = false;
    		blacklist.clear();
    		this.cancel();
    	}
    }
}
