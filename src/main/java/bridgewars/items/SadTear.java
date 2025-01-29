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
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import bridgewars.Main;
import bridgewars.effects.SadRoom;
import bridgewars.game.CSManager;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;
import bridgewars.utils.World;

public class SadTear implements ICustomItem, Listener {
	
	public static List<Player> sadRoomed = new ArrayList<>();
	private static List<BukkitTask> sadRoomTimers = new ArrayList<>();
	
	private static final int sadRoomX = 0;
	private static final int sadRoomY = 36;
	private static final int sadRoomZ = 0;
	
	private static final int radius = 3;
	
	private int duration = 15;
	
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
				
				if(CSManager.matchTeam(user, target))
					return;

				if(sadRoomed.size() == 0)
					World.loadObject("sad room", sadRoomX, sadRoomY - 1, sadRoomZ);
				
				if(sadRoomed.contains(target)) {
					user.sendMessage(Chat.color("&cThat player is already in the Sad Room, and so are you. L, idiot."));
					return;
				}
				
				target.teleport(new Location(Bukkit.getWorld("world"), sadRoomX + 0.5, sadRoomY, sadRoomZ + 0.5));
				final Vector v = new Vector();
				Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> target.setVelocity(v), 1L);
				sadRoomed.add(target);
				sadRoomTimers.add(new SadRoom(target, duration * 20).runTaskTimer(plugin, 0, 1L));
				
				Utils.subtractItem(user);
				Bukkit.broadcastMessage(Chat.color(target.getDisplayName() + Chat.color(" &chas been sent to the Sad Room")));
			}
		}
	}
	
	public static void clearSadRoom() {
		sadRoomed.clear();
		for(int x = -radius; x <= radius; x++)
			for(int y = 0; y < (radius * 2) + 2; y++)
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
