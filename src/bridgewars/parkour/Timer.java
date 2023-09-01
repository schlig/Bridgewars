package bridgewars.parkour;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.game.GameState;
import bridgewars.utils.Message;

public class Timer extends BukkitRunnable implements Listener {
	
	public static ArrayList<Player> parkourList = new ArrayList<>();
	private Player p;
	private Integer time = 0;
	
	public Timer(Player p) {
		this.p = p;
		parkourList.add(p);
	}
	
	@Override
	public void run() {
		if(GameState.isState(GameState.ACTIVE)) {
			p.sendMessage(Message.chat("&cParkour challenge aborted due to the game starting."));
			parkourList.remove(p);
			this.cancel();
			return;
		}
		
		if(!parkourList.contains(p)) {
			Integer minutes = time / 1200;
			Integer seconds = (time - (minutes * 1200) ) / 20;
			Integer milliseconds = (time - (minutes * 1200) - (seconds * 20)) * 5;
			String extraSecondsZero = "";
			String extraMSZero = "";
			
			if(seconds < 10)
				extraSecondsZero += "0";
			
			if(milliseconds < 10)
				extraMSZero += "0";
			
			p.sendMessage(Message.chat("&6Parkour completed! Your time: &r"
					+ minutes.toString() + ":" 
					+ extraSecondsZero + seconds.toString() + "." 
					+ extraMSZero + milliseconds.toString() + "&6."));
			this.cancel();
			return;
		}
		time++;
	}
}
