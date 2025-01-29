package bridgewars.behavior;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.settings.enums.UnlockedInventory;

public class DisableBasicItemDrops implements Listener {
	
	private ArrayList<Material> bannedItems = new ArrayList<Material>();
	
	public DisableBasicItemDrops(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		bannedItems.add(Material.GOLD_SWORD);
		bannedItems.add(Material.SHEARS);
		bannedItems.add(Material.WOOL);
		bannedItems.add(Material.WATER_BUCKET);
		bannedItems.add(Material.LAVA_BUCKET);
		bannedItems.add(Material.LEATHER_HELMET);
		bannedItems.add(Material.LEATHER_CHESTPLATE);
		bannedItems.add(Material.LEATHER_LEGGINGS);
		bannedItems.add(Material.LEATHER_BOOTS);
		bannedItems.add(Material.BOW);
		bannedItems.add(Material.POISONOUS_POTATO);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) { //prevent players from dropping items
		ItemStack item = e.getItemDrop().getItemStack();
		if(e.getPlayer().getGameMode() != GameMode.CREATIVE)
			if(bannedItems.contains(item.getType()) && !UnlockedInventory.getState().isEnabled())
				e.setCancelled(true);
		if(e.getPlayer().getOpenInventory() != null)
			if(e.getPlayer().getOpenInventory().getTitle() == "Hotbar Layout")
				e.getItemDrop().remove();;
	}
}
