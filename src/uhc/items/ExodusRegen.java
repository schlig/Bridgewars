package uhc.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import uhc.Main;

public class ExodusRegen implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ExodusRegen(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		Player player = null;
		if(e.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getDamager();
			if(arrow.getShooter() instanceof Player)
				player = (Player) arrow.getShooter();
		}
		else if (e.getDamager() instanceof Player)
			player = (Player) e.getDamager();
		else
			return;
		
		if(player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getType() == Material.DIAMOND_HELMET && player.getInventory().getHelmet().getItemMeta().getDisplayName().equals("\u00a76Exodus")) {
			if(player.hasPotionEffect(PotionEffectType.REGENERATION)) {
				for(PotionEffect effect : player.getActivePotionEffects())
					if(effect.getType().equals(PotionEffectType.REGENERATION))
						if(effect.getAmplifier() < 1) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 1), true);
							break;
						}
			}
			else
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 1), true);
		}
		return;
	}
}
