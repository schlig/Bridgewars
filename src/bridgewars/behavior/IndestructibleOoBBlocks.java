package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;

public class IndestructibleOoBBlocks implements Listener {
	
	public IndestructibleOoBBlocks(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) { //prevents blocks from being broken if they're out of bounds (mainly spawn plats and sad rooms)
		if(e.getPlayer().getGameMode() != GameMode.CREATIVE && GameState.isState(GameState.ACTIVE))
			if(Utils.isOutOfBounds(e.getBlock().getLocation(), 22, 24, 22)) {
				e.getPlayer().sendMessage(Message.chat("&cYou can't break blocks here!"));
				e.setCancelled(true);
			}
	}
}
