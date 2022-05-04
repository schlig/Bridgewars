package uhc.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import uhc.Main;
import uhc.utils.Utils;

public class GoldenHeadEat implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public GoldenHeadEat(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	public static List<String> headCooldown = new ArrayList<String>();

	@EventHandler
	public void onEat(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack item = player.getItemInHand();
		if(item.getType() == Material.AIR)
			return;
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(item.getType() == Material.SKULL_ITEM && player.getGameMode() != GameMode.CREATIVE) {
				if(headCooldown.contains(player.getName())) {
					player.sendMessage(Utils.chat("&cThis item is on cooldown!"));
					return;
				}
				else {
					headCooldown.add(player.getName());
					if(player.hasPotionEffect(PotionEffectType.SPEED)) {
						for(PotionEffect effect : player.getActivePotionEffects())
							if(effect.getType().equals(PotionEffectType.SPEED))
								if(effect.getAmplifier() > 1 || effect.getDuration() > 400)
									break;
								else {
									player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 1), true);
									break;
								}
					}
					else
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 1), true);
					
					if(player.hasPotionEffect(PotionEffectType.REGENERATION)) {
						for(PotionEffect effect : player.getActivePotionEffects())
							if(effect.getType().equals(PotionEffectType.REGENERATION))
								if(effect.getAmplifier() > 2 || effect.getDuration() > 100)
									break;
								else {
									player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2), true);
									break;
								}
					}
					else
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2), true);
					
					if(player.hasPotionEffect(PotionEffectType.ABSORPTION)) {
						for(PotionEffect effect : player.getActivePotionEffects())
							if(effect.getType().equals(PotionEffectType.ABSORPTION))
								if(effect.getAmplifier() > 1 || effect.getDuration() > 2400)
									break;
								else {
									player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 1), true);
									break;
								}
					}
					else
						player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 1), true);
					player.playSound(player.getLocation(), Sound.EAT, 0.75f, 1.0f);
					item.setAmount(item.getAmount() - 1);
					player.setItemInHand(item);
					Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(uhc.Main.class), new Runnable() {
						@Override
						public void run() {
							if(headCooldown.contains(player.getName()))
								headCooldown.remove(player.getName());
						}
					}, 10L);
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void preventPlacingHead(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		if(player.getGameMode() != GameMode.CREATIVE && e.getBlockPlaced().getType() == Material.SKULL)
			e.setCancelled(true);
	}
}
