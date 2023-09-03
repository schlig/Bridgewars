package bridgewars.parkour;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import bridgewars.Main;
import bridgewars.utils.ItemManager;
import bridgewars.utils.Utils;

public class ParkourQuit implements Listener {
	
	public ParkourQuit (Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void teleportToCheckpoint(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = e.getPlayer();
			if(Checkpoints.cp.containsKey(p) 
					&& Utils.matchItem(p.getItemInHand(), ItemManager.getItem("ParkourQuitter").createItem(null))) {
				p.teleport(Utils.getSpawn());
				if(Checkpoints.cp.containsKey(p))
					Checkpoints.cp.remove(p);
				Timer.cancelled.put(p, true);
				if(p.getGameMode() != GameMode.CREATIVE)
					p.getInventory().clear();
				if(p.getGameMode() == GameMode.CREATIVE)
					p.setAllowFlight(true);
			}
		}
	}
	
	@EventHandler
	public void preventPlacement(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if(Timer.parkourList.contains(p)
				&& Utils.matchItem(p.getItemInHand(), ItemManager.getItem("ParkourQuitter").createItem(null)))
			e.setCancelled(true);
	}
}
