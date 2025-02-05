package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import bridgewars.Main;
import bridgewars.settings.enums.IndestructibleMap;
import bridgewars.utils.World;

public class Explosions implements Listener {
	
	public Explosions(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		if(IndestructibleMap.getState().isEnabled())
			for(Block block : e.blockList())
				if(World.blockIsIndestructible(block))
					e.blockList().remove(block);
	}
}
