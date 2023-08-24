package bridgewars.effects;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.utils.Utils;

public class Fireworks extends BukkitRunnable {

	private int amount;
	
	public Fireworks (int amount) {
		this.amount = amount;
	}
	
	@Override
	public void run() {
		int x = Utils.rand(5) - 2;
		int z = Utils.rand(5) - 2;
		
		Firework firework = (Firework) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), x, 47, z), EntityType.FIREWORK);
		FireworkMeta effects = firework.getFireworkMeta();
		Color color = Color.ORANGE;
		int i = Utils.rand(4);
		switch(i) {
		case 0:
			color = Color.RED;
			break;
		case 1:
			color = Color.AQUA;
			break;
		case 2:
			color = Color.LIME;
			break;
		case 3:
			color = Color.YELLOW;
			break;
		}
		effects.setPower(0);
		effects.addEffect(FireworkEffect.builder().withColor(color).build());
		
		firework.setFireworkMeta(effects);
		
		amount--;
		if(amount == 0)
			this.cancel();
	}
}
