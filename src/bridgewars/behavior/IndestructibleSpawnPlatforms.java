package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import bridgewars.Main;
import bridgewars.game.GameState;

public class IndestructibleSpawnPlatforms implements Listener {
	public IndestructibleSpawnPlatforms(Main plugin) {
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(e.getBlock().getType() == Material.STAINED_GLASS 
				&& e.getPlayer().getGameMode() != GameMode.CREATIVE
				&& GameState.isState(GameState.ACTIVE))
			if(e.getBlock().getLocation().getY() > 25)
				e.setCancelled(true);
	}
}
