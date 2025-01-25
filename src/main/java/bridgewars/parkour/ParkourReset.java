package bridgewars.parkour;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import bridgewars.Main;
import bridgewars.messages.Chat;
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
					&& Utils.getID(p.getItemInHand()).equals("parkourresetter")) {
				p.sendMessage(Chat.color("&6You reset your parkour time!"));
				p.teleport(Checkpoints.startPlate.get(p));
				if(Checkpoints.cp.containsKey(p))
					Checkpoints.cp.remove(p);
				Timer.time.put(p, 0);
			}
		}
	}
	
	@EventHandler
	public void preventPlacement(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if(Timer.parkourList.contains(p)
				&& Utils.getID(p.getItemInHand()).equals("parkourresetter"))
			e.setCancelled(true);
	}
	
	@EventHandler
	public void preventOpeningInventories(InventoryOpenEvent e) {
		if(Utils.getID(e.getPlayer().getItemInHand()).equals("parkourresetter"))
			e.setCancelled(true);
	}
}
