package bridgewars.behavior;

import bridgewars.utils.World;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.Message;

public class BuildLimits implements Listener {

	
	public BuildLimits(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) { //prevents players that are in game from placing blocks out of bounds
		Location loc = e.getBlockPlaced().getLocation();
		if(GameState.isState(GameState.ACTIVE)) {
			if(World.outsideGameArea(loc)) {
				if(e.getPlayer().getGameMode() == GameMode.CREATIVE)
					return;
				e.setCancelled(true);
				e.getPlayer().sendMessage(Message.chat("&cYou can't place blocks here!"));
			}
		}
		
		if(GameState.isState(GameState.EDIT)
		&& !e.getPlayer().isOp()) {
			if(World.outsideGameArea(loc)) {
				if(e.getPlayer().getGameMode() == GameMode.CREATIVE)
					return;
				e.setCancelled(true);
				e.getPlayer().sendMessage(Message.chat("&cYou can't place blocks here!"));
			}
		}
	}

	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e){
		Location loc = e.getBlockClicked().getRelative(e.getBlockFace()).getLocation();
		if(GameState.isState(GameState.ACTIVE)) {
			if(World.outsideGameArea(loc)) {
				if(e.getPlayer().getGameMode() == GameMode.CREATIVE)
					return;
				e.setCancelled(true);
				e.getPlayer().sendMessage(Message.chat("&cYou can't place fluids here!"));
			}
		}

		if(GameState.isState(GameState.EDIT)
				&& !e.getPlayer().isOp()) {
			if(World.outsideGameArea(loc)) {
				if(e.getPlayer().getGameMode() == GameMode.CREATIVE)
					return;
				e.setCancelled(true);
				e.getPlayer().sendMessage(Message.chat("&cYou can't place fluids here!"));
			}
		}
	}
}
