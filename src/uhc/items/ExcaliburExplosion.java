package uhc.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import uhc.Main;
import uhc.utils.Utils;

public class ExcaliburExplosion implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ExcaliburExplosion(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public static List<String> cooldown = new ArrayList<String>();

	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			if(!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player))
				return;
			
			Player player = (Player) e.getDamager();
			Player victim = (Player) e.getEntity();
			ItemStack weapon = player.getItemInHand();
			if(weapon != null && weapon.getType() == Material.DIAMOND_SWORD && weapon.getItemMeta().hasDisplayName() && weapon.getItemMeta().getDisplayName().equals("\u00a76Excalibur"))  {
				if(cooldown.contains(player.getName()))
					return;
				else {
					victim.setHealth(victim.getHealth() - 5);
					cooldown.add(player.getName());
					player.playSound(player.getLocation(), Sound.EXPLODE, 0.8f, 1.0f);
					victim.playSound(player.getLocation(), Sound.EXPLODE, 0.8f, 1.0f);
					Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(uhc.Main.class), new Runnable() {
						@Override
						public void run() {
							if(cooldown.contains(player.getName()))
								cooldown.remove(player.getName());
							player.sendMessage(Utils.chat("&aExcalibur's Smite has recharged."));
						}
					}, 100L);
				}
			}
		}
		return;
	}
}
