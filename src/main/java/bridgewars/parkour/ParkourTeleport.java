package bridgewars.parkour;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import bridgewars.Main;
import bridgewars.utils.Utils;

public class ParkourTeleport implements Listener {
	
	public ParkourTeleport(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void teleportToCheckpoint(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = e.getPlayer();
			if(Checkpoints.cp.containsKey(p) 
					&& Utils.getID(p.getItemInHand()).equals("parkourteleporter"))
				p.teleport(Checkpoints.cp.get(p));
		}
	}
	
	@EventHandler
	public void preventOpeningInventories(InventoryOpenEvent e) {
		if(Utils.getID(e.getPlayer().getItemInHand()).equals(getClass().getSimpleName().toLowerCase()))
			e.setCancelled(true);
	}
}
