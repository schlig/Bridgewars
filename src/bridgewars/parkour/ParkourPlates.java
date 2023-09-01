package bridgewars.parkour;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import bridgewars.Main;
import bridgewars.utils.ItemManager;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;

public class ParkourPlates implements Listener {

	private HashMap<Player, Location> checkpoint = new HashMap<>();
	private Main plugin;
	
	public ParkourPlates(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void setCheckpoint(PlayerInteractEvent e) {
		if(e.getAction() == Action.PHYSICAL) {
			Player p = e.getPlayer();
			Location loc = e.getClickedBlock().getLocation();
			if(Bukkit.getWorld("world").getBlockAt(loc).getType() != Material.IRON_PLATE)
				return;
			loc.setX(loc.getX() + 0.5);
			loc.setZ(loc.getZ() + 0.5);
			loc.setPitch(p.getLocation().getPitch());
			loc.setYaw(p.getLocation().getYaw());
			checkpoint.put(p, loc);
			if(!p.getInventory().contains(ItemManager.getItem("ParkourTeleporter").createItem(null)))
				p.getInventory().addItem(ItemManager.getItem("ParkourTeleporter").createItem(null));
			if(!Timer.parkourList.contains(p)) {
				p.sendMessage(Message.chat("&6Parkour challenge started!"));
				new Timer(p).runTaskTimer(plugin, 0L, 0L);
			}
		}
	}
	
	@EventHandler
	public void endTimer(PlayerInteractEvent e) {
		if(e.getAction() == Action.PHYSICAL) {
			Player p = e.getPlayer();
			Location loc = e.getClickedBlock().getLocation();
			if(Bukkit.getWorld("world").getBlockAt(loc).getType() != Material.GOLD_PLATE)
				return;
			if(Timer.parkourList.contains(p))
				Timer.parkourList.remove(p);
		}
	}
	
	@EventHandler
	public void teleportToCheckpoint(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = e.getPlayer();
			if(checkpoint.containsKey(p) && Utils.compareItemName(p.getItemInHand(), "&6Teleport to Last Checkpoint"))
				p.teleport(checkpoint.get(p));
		}
	}
}
