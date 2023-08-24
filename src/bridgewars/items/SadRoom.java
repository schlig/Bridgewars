package bridgewars.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.Main;
import bridgewars.game.Game;
import bridgewars.game.GameState;
import bridgewars.utils.Message;

public class SadRoom implements Listener {
	
	private static List<Player> sadRoomed = new ArrayList<>();
	
	private Main plugin;
	
	public SadRoom(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player
		&& e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player k = (Player) e.getDamager();
			if(k.getItemInHand().getType() == Material.GHAST_TEAR
			&& GameState.isState(GameState.ACTIVE)) {
				if(sadRoomed.size() == 0) {
					
					for(int x = 0; x < 7; x++)
						for(int y = 0; y < 7; y++)
							for(int z = 0; z < 7; z++)
								Bukkit.getWorld("world").getBlockAt(x - 3, y + 35, z - 3).setType(Material.GLASS);
					
					for(int x = 0; x < 5; x++)
						for(int y = 0; y < 6; y++)
							for(int z = 0; z < 5; z++)
								Bukkit.getWorld("world").getBlockAt(x - 2, y + 36, z - 2).setType(Material.AIR);
					
					Bukkit.getWorld("world").getBlockAt(0, 38, 3).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(1, 38, 3).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(-1, 38, 3).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(2, 37, 3).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(-2, 37, 3).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(1, 40, 3).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(-1, 40, 3).setType(Material.SEA_LANTERN);
					
					Bukkit.getWorld("world").getBlockAt(0, 38, -3).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(1, 38, -3).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(-1, 38, -3).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(2, 37, -3).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(-2, 37, -3).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(1, 40, -3).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(-1, 40, -3).setType(Material.SEA_LANTERN);
					
					Bukkit.getWorld("world").getBlockAt(3, 38, 0).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(3, 38, 1).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(3, 38, -1).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(3, 37, 2).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(3, 37, -2).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(3, 40, 1).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(3, 40, -1).setType(Material.SEA_LANTERN);

					Bukkit.getWorld("world").getBlockAt(-3, 38, 0).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(-3, 38, 1).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(-3, 38, -1).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(-3, 37, 2).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(-3, 37, -2).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(-3, 40, 1).setType(Material.SEA_LANTERN);
					Bukkit.getWorld("world").getBlockAt(-3, 40, -1).setType(Material.SEA_LANTERN);
				}
				
				if(sadRoomed.contains(p)) {
					k.sendMessage(Message.chat("&cThat player is already in the Sad Room. You are too. L."));
					return;
				}
				
				sadRoomed.add(p);
				p.teleport(new Location(Bukkit.getWorld("world"), 0, 36, 0));
				
				ItemStack sadTear = k.getItemInHand();
				sadTear.setAmount(sadTear.getAmount() - 1);
				k.setItemInHand(sadTear);

				Bukkit.broadcastMessage(Message.chat(p.getDisplayName() + Message.chat(" &chas been banished to the Sad Room.")));
				
				new BukkitRunnable() {
					@Override
					public void run() {
						sadRoomed.remove(p);
						Game.spawnPlayer(p);
						if(sadRoomed.size() == 0)
							clearSadRoom();
					}
				}.runTaskLater(plugin, 200L);
			}
		}
	}
	
	public static void clearSadRoom() {
		sadRoomed.clear();
		for(int x = 0; x < 7; x++)
			for(int y = 0; y < 7; y++)
				for(int z = 0; z < 7; z++)
					Bukkit.getWorld("world").getBlockAt(x - 3, y + 35, z - 3).setType(Material.AIR);
	}
	
	public static void removePlayerFromSadRoom(Player p) {
		if(sadRoomed.contains(p))
			sadRoomed.remove(p);
		if(sadRoomed.size() == 0)
			clearSadRoom();
	}
}
