package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.settings.enums.UnlockedInventory;

public class DisableArmorRemoval implements Listener {
	
	public DisableArmorRemoval(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) { //prevents armor from being dropped or removed from inventory while in game
		if(GameState.isState(GameState.ACTIVE) 
				&& e.getWhoClicked().getGameMode() != GameMode.CREATIVE
				&& e.getSlotType() == SlotType.ARMOR
				&& !UnlockedInventory.getState().isEnabled())
					e.setCancelled(true);
	}
}
