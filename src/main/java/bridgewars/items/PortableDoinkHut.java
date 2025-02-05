package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import bridgewars.Main;
import bridgewars.effects.CooldownTimer;
import bridgewars.game.Leaderboards;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;
import bridgewars.utils.World;

public class PortableDoinkHut implements ICustomItem, Listener {
	
	private Main plugin;
	
	private final static int radius = 2;
	private final static int height = 3;
	
	private final static int mapRadius = 22;
	private final static int mapHeight = 24;
	
	private static ArrayList<Player> cooldownList = new ArrayList<>();
	private final static int cooldown = 5;
	
	public PortableDoinkHut (Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	@Override
	public Rarity getRarity() {
		return Rarity.GREEN;
	}

	@Override
	public ItemStack createItem(Player p) {
		ItemStack item = new ItemStack(Material.MOB_SPAWNER, 1);
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
		ItemBuilder.setName(item, "Portable Doink Hut");
		ItemBuilder.setLore(item, Arrays.asList(Chat.color("&r&7Builds an instant house"),
				Chat.color("&r&7where you're standing")));
		ItemBuilder.disableStacking(item);
		return item;
	}

	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		Player user = e.getPlayer();
		ItemStack item = user.getItemInHand();
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(item.getType() == Material.MOB_SPAWNER) {
				if(cooldownList.contains(user)) {
					user.sendMessage(Chat.color("This item is currently on cooldown."));
					return;
				}
				cooldownList.add(user);
				new CooldownTimer(user, cooldown, cooldownList, null).runTaskTimer(plugin, 0, 20);
				activateEffect(user);
				Utils.subtractItem(user);
			    Leaderboards.addPoint(user, "items");
			}
		}
	}
	
	public static void activateEffect(Player user) {
		activateEffect(user, true);
	}
	
	@SuppressWarnings("deprecation")
	public static void activateEffect(Player user, Boolean failmessage) {
		Location loc = user.getLocation();
		loc.setY(Math.floor(loc.getY()) - 1);
		Block origin = user.getWorld().getBlockAt(loc);
		
		boolean success = false;
		
		//build doink hut
		for(int x = -radius; x <= radius; x++)
			for(int z = -radius; z <= radius; z++)
				for(int y = 0; y <= height; y++)
					if(origin.getRelative(x, y, z).getType() == Material.AIR || origin.getRelative(x, y, z).getType() == Material.WOOL) {
						if(Math.abs(origin.getRelative(x, y, z).getX()) <= mapRadius
						&& Math.abs(origin.getRelative(x, y, z).getZ()) <= mapRadius
						&& origin.getRelative(x, y, z).getY() <= mapHeight
						|| user.getGameMode() == GameMode.CREATIVE) {
							success = true;
							if(World.indestructibleBlockList.contains(origin.getRelative(x, y, z)))
								 continue;
							origin.getRelative(x, y, z).setType(Material.WOOL);
							origin.getRelative(x, y, z).setData(ItemBuilder.getColorID(user));
						}
					}
		
		//fill doink with air
		for(int x = -1; x <= 1; x++)
			for(int z = -1; z <= 1; z++)
				for(int y = 1; y <= 2; y++)
					if(origin.getRelative(x, y, z).getType() == Material.WOOL && !World.blockIsIndestructible(origin.getRelative(x, y, z)))
						origin.getRelative(x, y, z).setType(Material.AIR);
		
		if(success || user.getGameMode() == GameMode.CREATIVE) {
			Vector v = user.getVelocity();
			v.setY(0);
			user.setVelocity(v);
			user.playSound(user.getLocation(), Sound.CHICKEN_EGG_POP, 1F, 1F);
			user.sendMessage(Chat.color("&6Doink!"));
			loc.setY(loc.getY() + 1.1);
			user.teleport(loc);
		}
		else {
			if(failmessage) {
				user.sendMessage(Chat.color("&cYou can't place this here!"));
				if(cooldownList.contains(user))
					cooldownList.remove(user);
			}
		}
	}
}
