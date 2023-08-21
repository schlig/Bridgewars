package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import bridgewars.Main;
import bridgewars.game.GameState;

public class DisableItemDrops implements Listener {

	
	public DisableItemDrops(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(e.getPlayer().getGameMode() != GameMode.CREATIVE)
			if(GameState.isState(GameState.ACTIVE))
				e.setCancelled(true);
		if(e.getPlayer().getOpenInventory() != null)
			if(e.getPlayer().getOpenInventory().getTitle() == "Hotbar Layout")
				e.getItemDrop().remove();;
	}
}
