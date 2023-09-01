package bridgewars.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Invincibility extends BukkitRunnable {

	public static List<Player> invinciblePlayers = new ArrayList<>();
	public static HashMap<Player, Boolean> removeOnHit = new HashMap<>();
	private int duration;
	private Player p;
	
	public Invincibility(Player p, int duration, boolean removeOnHit) {
		this.p = p;
		this.duration = duration;
		Invincibility.removeOnHit.put(p, removeOnHit);
		invinciblePlayers.add(p);
	}
	
	@Override
	public void run() {
		if(duration <= 0 || !invinciblePlayers.contains(p)) {
			if(invinciblePlayers.contains(p))
				invinciblePlayers.remove(p);
			this.cancel();
		}
		duration--;
	}
}
