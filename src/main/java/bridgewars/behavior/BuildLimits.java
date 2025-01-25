package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.utils.Utils;

public class BuildLimits implements Listener {

	
	public BuildLimits(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) { //prevents players that are in game from placing blocks out of bounds
		Location loc = e.getBlockPlaced().getLocation();
		if(GameState.isState(GameState.ACTIVE)) {
			if(Utils.isOutOfBounds(loc, 22, 24, 22)) {
				if(e.getPlayer().getGameMode() == GameMode.CREATIVE)
					return;
				e.setCancelled(true);
				e.getPlayer().sendMessage(Chat.color("&cYou can't place blocks here!"));
			}
		}
		
		if(GameState.isState(GameState.EDIT)
		&& !e.getPlayer().isOp()) {
			if(Utils.isOutOfBounds(loc, 22, 24, 22)) {
				if(e.getPlayer().getGameMode() == GameMode.CREATIVE)
					return;
				e.setCancelled(true);
				e.getPlayer().sendMessage(Chat.color("&cYou can't place blocks here!"));
			}
		}
	}
}
