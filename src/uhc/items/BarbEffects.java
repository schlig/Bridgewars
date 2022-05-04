package uhc.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import uhc.Main;

public class BarbEffects implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public BarbEffects(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public void applyEffects() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getProvidingPlugin(uhc.Main.class), new Runnable() {
			@Override
			public void run() {
				for(Player players : Bukkit.getOnlinePlayers()) {if(players.getInventory().getChestplate() != null && players.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE && players.getInventory().getChestplate().getItemMeta().hasDisplayName() && players.getInventory().getChestplate().getItemMeta().getDisplayName().contains("\u00a7aBarbarian Chestplate")) {
					if(players.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
						for(PotionEffect effect : players.getActivePotionEffects())
							if(effect.getType().equals(PotionEffectType.INCREASE_DAMAGE))
								if(effect.getAmplifier() > 0 || effect.getDuration() > 60)
									break;
								else {
									players.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0), true);
									break;
								}
					}
					else
						players.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0));
					
					if(players.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
						for(PotionEffect effect : players.getActivePotionEffects())
							if(effect.getType().equals(PotionEffectType.DAMAGE_RESISTANCE))
								if(effect.getAmplifier() > 0 || effect.getDuration() > 60)
									break;
								else {
									players.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 0), true);
									break;
								}
					}
					else
						players.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 0));
					
				}
				}
			}
		}, 0L, 20L);
	}
}

