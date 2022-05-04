package uhc.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import uhc.Main;

public class AndrewEffects implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public AndrewEffects(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public void applyEffects() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getProvidingPlugin(uhc.Main.class), new Runnable() {
			@Override
			public void run() {
				for(Player players : Bukkit.getOnlinePlayers()) {
					if(players.getItemInHand().getType() == Material.IRON_SWORD && players.getItemInHand().getItemMeta().hasDisplayName() && players.getItemInHand().getItemMeta().getDisplayName().contains("\u00a7aAndúril")) {
						if(players.hasPotionEffect(PotionEffectType.SPEED)) {
							for(PotionEffect effect : players.getActivePotionEffects())
								if(effect.getType().equals(PotionEffectType.SPEED))
									if(effect.getAmplifier() > 0 || effect.getDuration() > 60)
										break;
									else {
										players.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 0), true);
										break;
									}
						}
						else
							players.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 0));
						
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
