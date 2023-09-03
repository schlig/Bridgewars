package bridgewars.parkour;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import bridgewars.Main;
import bridgewars.utils.ItemManager;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;

public class ParkourReset implements Listener {

	public ParkourReset (Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void teleportToCheckpoint(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = e.getPlayer();
			if(Checkpoints.cp.containsKey(p) 
					&& Utils.matchItem(p.getItemInHand(), ItemManager.getItem("ParkourResetter").createItem(null))) {
				p.sendMessage(Message.chat("&6You reset your parkour time!"));
				p.teleport(Checkpoints.cp.get(p));
				Timer.time.put(p, 0);
			}
		}
	}
	
	@EventHandler
	public void preventPlacement(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if(Timer.parkourList.contains(p)
				&& Utils.matchItem(p.getItemInHand(), ItemManager.getItem("ParkourResetter").createItem(null)))
			e.setCancelled(true);
	}
}
