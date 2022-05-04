package uhc.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import uhc.Main;

public class CornucopiaEat implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public CornucopiaEat(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	public static List<String> headCooldown = new ArrayList<String>();

	@EventHandler
	public void onPlayerUse(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack item = player.getItemInHand();
		if(item.getType() == Material.AIR)
			return;
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(item.getItemMeta().getDisplayName() != null && item.getType() == Material.GOLDEN_CARROT && item.getItemMeta().getDisplayName().equals("\u00a76Cornucopia")) {
				if(player.hasPotionEffect(PotionEffectType.SATURATION)) {
					for(PotionEffect effect : player.getActivePotionEffects())
						if(effect.getType().equals(PotionEffectType.SATURATION))
							if(effect.getAmplifier() > 0 || effect.getDuration() > 12000)
								break;
							else {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 12000, 0), true);
								break;
							}
				}
				else
					player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 12000, 0), true);
				
				if(player.hasPotionEffect(PotionEffectType.REGENERATION)) {
					for(PotionEffect effect : player.getActivePotionEffects())
						if(effect.getType().equals(PotionEffectType.REGENERATION))
							if(effect.getAmplifier() > 1 || effect.getDuration() > 200)
								break;
							else {
								player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1), true);
								break;
							}
				}
				else
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1), true);
				player.playSound(player.getLocation(), Sound.EAT, 0.0f, 1.0f);
				item.setAmount(item.getAmount() - 1);
				player.setItemInHand(item);
				return;
			}
		}
	}
}
