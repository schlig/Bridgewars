package uhc.items;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import uhc.Main;
import uhc.utils.Utils;

public class ExpertSealUse implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ExpertSealUse(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onUse(InventoryClickEvent e) {
		ItemStack item = e.getCurrentItem();
		ItemMeta meta = item.getItemMeta();
		Player player = (Player) e.getWhoClicked();
		player.sendMessage(e.getAction().toString());
		player.sendMessage(e.getSlotType().toString());
		
		if(player.getGameMode() != GameMode.CREATIVE && e.getAction() == InventoryAction.SWAP_WITH_CURSOR && e.getCursor().getType() == Material.NETHER_STAR && e.getCursor().getItemMeta().hasDisplayName() && e.getCursor().getItemMeta().getDisplayName().equals("\u00a7aExpert Seal")) {
			if(item.getType() != Material.AIR && meta.hasEnchants()) {
				if(meta.hasLore() && meta.getLore().contains("\u00a7c*Upgraded with Expert Seal*")) {
					return;
				}
				else {
					e.setCancelled(true);
					if(!(meta.hasLore()))
						meta.setLore(Arrays.asList(" ", "\u00a7c*Upgraded with Expert Seal*"));
					else {
						List<String> lore = meta.getLore();
						lore.add(" ");
						lore.add(Utils.chat("&c*Upgraded with Expert Seal*"));
						meta.setLore(lore);
					}
					item.setItemMeta(meta);
					Map<Enchantment, Integer> enchants = item.getEnchantments();
					for(Entry<Enchantment, Integer> index : enchants.entrySet())
						item.addUnsafeEnchantment(index.getKey(), index.getValue() + 1);
					e.setCurrentItem(item);
					ItemStack seals = player.getItemOnCursor();
					seals.setAmount(seals.getAmount() - 1);
					player.setItemOnCursor(new ItemStack(seals));
					player.playSound(player.getLocation(), Sound.LEVEL_UP, 0.8f, 1.0f);
				}
			}
		}
	}
}
