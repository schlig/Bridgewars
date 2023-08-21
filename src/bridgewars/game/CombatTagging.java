package bridgewars.game;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class CombatTagging {
	
	private HashMap<Player, Player> lastAttacker;
	private HashMap<Player, Material> lastWeapon;
	private HashMap<Player, DamageCause> lastDamage;
	
	public CombatTagging() {
		lastWeapon  = new HashMap<>();
		lastAttacker = new HashMap<>();
		lastDamage = new HashMap<>();
	}
	
	public DamageCause getDamageCause(Player p) {
		if(lastDamage.containsKey(p))
			return lastDamage.get(p);
		else
			return null;
	}
	
	public Material getMurderWeapon(Player p) {
		if(lastWeapon.containsKey(p))
			return lastWeapon.get(p);
		else
			return null;
	}
	
	public Player getAttacker(Player p) {
		if(lastAttacker.containsKey(p))
			return lastAttacker.get(p);
		else
			return null;
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
			lastWeapon.put(p, weapon.getType());
	}
	
	public void setAttacker(Player p, Player k) {
		if(k == null)
			lastAttacker.remove(p);
		else
			lastAttacker.put(p, k);
	}
}
