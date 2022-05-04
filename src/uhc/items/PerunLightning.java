package uhc.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import uhc.Main;
import uhc.utils.Utils;

public class PerunLightning implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public PerunLightning(Main plugin) {
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
			if(weapon != null && weapon.getType() == Material.DIAMOND_AXE && weapon.getItemMeta().hasDisplayName() && weapon.getItemMeta().getDisplayName().equals("\u00a76Axe of Perun")) {
				if(cooldown.contains(player.getName()))
					return;
				else {
					victim.getWorld().strikeLightningEffect(victim.getLocation());
					victim.setHealth(victim.getHealth() - 3);
					cooldown.add(player.getName());
					Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(uhc.Main.class), new Runnable() {
						@Override
						public void run() {
							if(cooldown.contains(player.getName()))
								cooldown.remove(player.getName());
							player.sendMessage(Utils.chat("&aAxe of Perun's Lightning has recharged."));
						}
					}, 160L);
				}
			}
		}
		return;
	}
}
