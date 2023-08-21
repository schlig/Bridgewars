package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.Utils;

public class BuildLimits implements Listener {

	
	public BuildLimits(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(GameState.isState(GameState.ACTIVE)) {
			Location loc = e.getBlockPlaced().getLocation();
			if(Utils.checkBounds(loc, 22, 24, 22)) {
				if(e.getPlayer().getGameMode() == GameMode.CREATIVE)
					return;
				e.setCancelled(true);
				e.getPlayer().sendMessage(Utils.chat("&cYou can't place blocks here!"));
			}
		}
		
		if(GameState.isState(GameState.EDIT)
		&& !e.getPlayer().isOp()) {
			Location loc = e.getBlockPlaced().getLocation();
			if(Utils.checkBounds(loc, 22, 24, 22)) {
				if(e.getPlayer().getGameMode() == GameMode.CREATIVE)
					return;
				e.setCancelled(true);
				e.getPlayer().sendMessage(Utils.chat("&cYou can't place blocks here!"));
			}
		}
	}
}
