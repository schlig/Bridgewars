package uhc.items;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import uhc.Main;

public class Scythe implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Scythe(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if((e.getDamager() instanceof Player || e.getDamager() instanceof Arrow) && e.getEntity() instanceof Player) {
			
			Player player = null;
			if (e.getDamager() instanceof Player)
				player = (Player) e.getDamager();
			else if(e.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) e.getDamager();
				if(arrow.getShooter() instanceof Player)
					player = (Player) arrow.getShooter();
			}
			
			Player victim = (Player) e.getEntity();
			ItemStack weapon = player.getItemInHand();
			if(!(weapon == null) && weapon.getType() == Material.IRON_HOE && weapon.getItemMeta().hasDisplayName() && weapon.getItemMeta().getDisplayName().equals("\u00a7dDeath's Scythe")) {
				victim.setHealth(victim.getHealth() * 0.8);
				player.setHealth(player.getHealth() + (victim.getHealth() * 0.05));
				if(player.getGameMode() != GameMode.CREATIVE)
					weapon.setDurability((short)(weapon.getDurability() + 25));
				if(weapon.getDurability() >= (short)250) {
					player.setItemInHand(null);
					player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0f, 1.0f);
				}
			}
		}
		return;
	}
}
