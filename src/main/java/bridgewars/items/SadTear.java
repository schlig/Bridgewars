package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;
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
import bridgewars.game.CustomScoreboard;
import bridgewars.game.Game;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class SadTear implements ICustomItem, Listener {
	
	private static List<Player> sadRoomed = new ArrayList<>();
	private CustomScoreboard cs = new CustomScoreboard();
	
	private static final int sadRoomX = 0;
	private static final int sadRoomY = 36;
	private static final int sadRoomZ = 0;
	
	private static final int radius = 3;
	
	private Main plugin;
	
	public SadTear(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	@Override
	public Rarity getRarity() {
		return Rarity.RED;
	}

	@Override
	public ItemStack createItem(Player p) {
		ItemStack item = new ItemStack(Material.GHAST_TEAR, 1);
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
		ItemBuilder.setName(item, "Sad Tear");
		ItemBuilder.setLore(item, Arrays.asList(Chat.color("&r&7Sends a player to the"),
				Chat.color("&r&7Sad Room for 15 seconds")));
		ItemBuilder.disableStacking(item);
		return item;
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player
		&& e.getDamager() instanceof Player) {

			Player user = (Player) e.getDamager();
			Player target = (Player) e.getEntity();
			if(Utils.getID(user.getItemInHand()).equals(getClass().getSimpleName().toLowerCase())
			&& GameState.isState(GameState.ACTIVE)) {
				
				if(cs.matchTeam(user, target))
					return;

				if(sadRoomed.size() == 0)
					buildSadRoom();
				
				if(sadRoomed.contains(user) && sadRoomed.contains(target)) {
					user.sendMessage(Chat.color("&cThat player is already in the Sad Room. You are too. L."));
					return;
				}
				
				sadRoomed.add(target);
				target.teleport(new Location(Bukkit.getWorld("world"), sadRoomX, sadRoomY, sadRoomZ));
				
				Utils.subtractItem(user);

				Bukkit.broadcastMessage(Chat.color(target.getDisplayName() + Chat.color(" &chas been sent to the Sad Room")));
				
				new BukkitRunnable() {
					@Override
					public void run() {
						sadRoomed.remove(target);
						Game.spawnPlayer(target);
						if(sadRoomed.size() == 0)
							clearSadRoom();
					}
				}.runTaskLater(plugin, 200L);
			}
		}
	}
	
	private void buildSadRoom() {
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
	
	public static void clearSadRoom() {
		sadRoomed.clear();
		for(int x = -radius; x <= radius; x++)
			for(int y = 0; y < (radius * 2) + 1; y++)
				for(int z = -radius; z <= radius; z++)
					Bukkit.getWorld("world").getBlockAt(x, y + sadRoomY - 1, z).setType(Material.AIR);
	}
	
	public static void removePlayerFromSadRoom(Player p) {
		if(sadRoomed.contains(p))
			sadRoomed.remove(p);
		if(sadRoomed.size() == 0)
			clearSadRoom();
	}
}
