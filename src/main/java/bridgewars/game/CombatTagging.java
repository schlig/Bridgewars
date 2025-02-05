package bridgewars.game;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class CombatTagging {
	
	private HashMap<Player, Player> lastAttacker;
	private HashMap<Player, ItemStack> lastWeapon;
	private HashMap<Player, DamageCause> lastDamage;
	
	public CombatTagging() {
		lastWeapon  = new HashMap<>();
		lastAttacker = new HashMap<>();
		lastDamage = new HashMap<>();
	}
	
	public DamageCause getDamageCause(Player p) {
		return lastDamage.containsKey(p) ? lastDamage.get(p) : null;
	}
	
	public ItemStack getMurderWeapon(Player p) {
		return lastWeapon.containsKey(p) ? lastWeapon.get(p) : null;
	}
	
	public Player getAttacker(Player p) {
		return lastAttacker.containsKey(p) ? lastAttacker.get(p) : null;
	}
	
	public void setDamageCause(Player p, DamageCause cause) {
		if(cause == null)
			lastDamage.remove(p);
		else
			lastDamage.put(p, cause);
	}
	
	public void setMurderWeapon(Player p, ItemStack weapon) {
		if(weapon == null)
			lastWeapon.remove(p);
		else
			lastWeapon.put(p, weapon);
	}
	
	public void setAttacker(Player p, Player k) {
		if(k == null)
			lastAttacker.remove(p);
		else
			lastAttacker.put(p, k);
	}
}
