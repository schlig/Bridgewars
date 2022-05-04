package uhc.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import uhc.Main;

public class GoldenApples implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public GoldenApples(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerEat(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();
		if(e.getItem().getType() == Material.GOLDEN_APPLE) {
			if(player.hasPotionEffect(PotionEffectType.REGENERATION)) {
				for(PotionEffect effect : player.getActivePotionEffects()) {
					if(effect.getType().equals(PotionEffectType.REGENERATION)) {
						if(effect.getAmplifier() > 2)
							break;
						else if(effect.getAmplifier() == 2 && effect.getDuration() > 100)
							break;
						else {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2), true);
							break;
						}
					}
				}
			}
			else
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2), true);
		}
	}
}
