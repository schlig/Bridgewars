package bridgewars.effects;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.game.CSManager;
import bridgewars.game.Game;
import bridgewars.game.GameState;
import bridgewars.items.SadTear;

public class SadRoom extends BukkitRunnable {
	
    private int timer;    //duration
    private Player player;
    
    public SadRoom(Player target, int duration){
    	this.player = target;
        this.timer = duration;
    }
    
    @Override
    public void run(){
    	timer--;
    	if(timer <= 0 
    	|| !SadTear.sadRoomed.contains(player)
    	|| !CSManager.hasTeam(player)
    	|| !(player.getGameMode() != GameMode.CREATIVE)) {
    		SadTear.removePlayerFromSadRoom(player);
    		if(GameState.isState(GameState.ACTIVE)
    		&& player.getGameMode() != GameMode.CREATIVE)
    			Game.spawnPlayer(player);
    		this.cancel();
    	}
    }
}
