package bridgewars.effects;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.utils.Disguise;

public class Cloak extends BukkitRunnable {
	
	private final Player u; //user
//	private Player t; //target
	private String name;
	private int d;    //duration
	
	public Cloak(Player u, Player t, int d) {
		this.u = u;
//		this.t = t;
		this.name = u.getName();
		this.d = d;
		
		Disguise.setDisguise(u, t);
		getArmor(u, t);
	}
	
	@Override
	public void run() {
		if(d == 0) {
			Disguise.setDisguise(u, u);
			this.cancel();
		}
		d--;
	}
	
	private void getArmor(Player u, Player t) {
		u.getInventory().setArmorContents(t.getInventory().getArmorContents());
	}
}
