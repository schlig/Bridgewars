package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.settings.enums.DigWars;
import bridgewars.settings.enums.UnlockedInventory;

public class DisableInventoryCrafting implements Listener {
	
	public DisableInventoryCrafting(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent e) { //disable crafting, mainly for carpets lol
		if(GameState.isState(GameState.ACTIVE)
		&& !DigWars.getState().isEnabled()
		&& !UnlockedInventory.getState().isEnabled())
			e.setCancelled(true);
	}
}
