package bridgewars.parkour;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.game.GameState;
import bridgewars.utils.ItemManager;
import bridgewars.utils.Message;

public class Timer extends BukkitRunnable implements Listener {
	
	public static ArrayList<Player> parkourList = new ArrayList<>();
	public static HashMap<Player, Integer> time = new HashMap<>();
	public static HashMap<Player, Boolean> cancelled = new HashMap<>();
	private Player p;
	
	public Timer(Player p) {
		this.p = p;
		time.put(p, 0);
		parkourList.add(p);
		cancelled.put(p, false);
	}
	
	@Override
	public void run() {
		if(GameState.isState(GameState.ACTIVE)) {
			p.sendMessage(Message.chat("&cParkour challenge aborted due to the game starting."));
			parkourList.remove(p);
			time.remove(p);
			this.cancel();
			return;
		}
		
		if(cancelled.get(p)) {
			p.sendMessage(Message.chat("&cYou quit the parkour challenge."));
			parkourList.remove(p);
			removeParkourItems(p);
			time.remove(p);
			this.cancel();
			return;
		}
		
		if(!parkourList.contains(p)) {
			Integer minutes = time.get(p) / 1200;
			Integer seconds = (time.get(p) - (minutes * 1200) ) / 20;
			Integer milliseconds = (time.get(p) - (minutes * 1200) - (seconds * 20)) * 5;
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

			removeParkourItems(p);
			time.remove(p);
			this.cancel();
			return;
		}
		time.put(p, time.get(p) + 1);
	}
	
	private void removeParkourItems(Player p) {
		if(p.getInventory().contains(ItemManager.getItem("ParkourTeleporter").createItem(null)))
			p.getInventory().remove(ItemManager.getItem("ParkourTeleporter").createItem(null));
		if(p.getInventory().contains(ItemManager.getItem("ParkourResetter").createItem(null)))
			p.getInventory().remove(ItemManager.getItem("ParkourResetter").createItem(null));
		if(p.getInventory().contains(ItemManager.getItem("ParkourQuitter").createItem(null)))
			p.getInventory().remove(ItemManager.getItem("ParkourQuitter").createItem(null));
	}
}
