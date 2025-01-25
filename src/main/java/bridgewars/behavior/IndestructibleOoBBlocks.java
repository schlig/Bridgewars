package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.utils.World;

public class IndestructibleOoBBlocks implements Listener {
	
	public IndestructibleOoBBlocks(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) { //prevents blocks from being broken if they're out of bounds (mainly spawn plats and sad rooms)
		if(e.getPlayer().getGameMode() != GameMode.CREATIVE && GameState.isState(GameState.ACTIVE))
			if(!World.inGameArea(e.getBlock().getLocation())) {
				e.getPlayer().sendMessage(Chat.color("&cYou can't break blocks here!"));
				e.setCancelled(true);
			}
	}
}
