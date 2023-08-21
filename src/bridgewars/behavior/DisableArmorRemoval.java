package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import bridgewars.Main;
import bridgewars.game.GameState;

public class DisableArmorRemoval implements Listener {
	
	public DisableArmorRemoval(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(GameState.isState(GameState.ACTIVE))
			if(e.getWhoClicked().getGameMode() != GameMode.CREATIVE)
				if(e.getSlotType() == SlotType.ARMOR)
					e.setCancelled(true);
	}
}
