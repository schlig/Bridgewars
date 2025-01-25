package bridgewars.parkour;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import bridgewars.Main;
import bridgewars.commands.Fly;
import bridgewars.messages.Chat;
import bridgewars.utils.ItemManager;

public class Checkpoints implements Listener {
	
	public static HashMap<Player, Location> cp = new HashMap<>();
	public static HashMap<Player, Location> startPlate = new HashMap<>();
	private Main plugin;
	
	public Checkpoints(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void setCheckpoint(PlayerInteractEvent e) { //also starts the parkour challenge
		if(e.getAction() == Action.PHYSICAL) {
			Player p = e.getPlayer();
			Location loc = e.getClickedBlock().getLocation();
			if(Bukkit.getWorld("world").getBlockAt(loc).getType() != Material.IRON_PLATE)
				return;
			loc.setX(loc.getX() + 0.5);
			loc.setZ(loc.getZ() + 0.5);
			loc.setPitch(p.getLocation().getPitch());
			loc.setYaw(p.getLocation().getYaw());
			Checkpoints.cp.put(p, loc);
			if(!Checkpoints.startPlate.containsKey(p))
				Checkpoints.startPlate.put(p, loc);
			
			if(!p.getInventory().contains(ItemManager.getItem("ParkourTeleporter").createItem(null)))
				p.getInventory().setItem(3, ItemManager.getItem("ParkourTeleporter").createItem(null));
			if(!p.getInventory().contains(ItemManager.getItem("ParkourResetter").createItem(null)))
				p.getInventory().setItem(4, ItemManager.getItem("ParkourResetter").createItem(null));
			if(!p.getInventory().contains(ItemManager.getItem("ParkourQuitter").createItem(null)))
				p.getInventory().setItem(5, ItemManager.getItem("ParkourQuitter").createItem(null));
			if(!p.getInventory().contains(ItemManager.getItem("ParkourCheckpoint").createItem(null)) && p.getGameMode() == GameMode.CREATIVE)
				p.getInventory().setItem(8, ItemManager.getItem("ParkourCheckpoint").createItem(null));
			
			Fly.setFlight(p, false, false);
			
			if(!Timer.parkourList.contains(p)) {
				p.sendMessage(Chat.color("&6Parkour challenge started!"));
				new Timer(p).runTaskTimer(plugin, 0L, 0L);
			}
		}
	}
	
	@EventHandler
	public void endTimer(PlayerInteractEvent e) { //ends the parkour
		if(e.getAction() == Action.PHYSICAL) {
			Player p = e.getPlayer();
			Location loc = e.getClickedBlock().getLocation();
			if(Bukkit.getWorld("world").getBlockAt(loc).getType() != Material.GOLD_PLATE)
				return;
			if(Timer.parkourList.contains(p))
				Timer.parkourList.remove(p);
		}
	}
}
