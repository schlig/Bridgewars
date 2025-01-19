package bridgewars.items;

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
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import bridgewars.Main;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;

public class PortableDoinkHut implements ICustomItem, Listener {
	
	private final int radius = 2;
	private final int height = 3;
	
	private final int mapRadius = 22;
	private final int mapHeight = 24;
	
	public PortableDoinkHut (Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
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
		ItemBuilder.setLore(item, Arrays.asList(Message.chat("&r&7Builds an instant house"),
				Message.chat("&r&7where you're standing")));
		ItemBuilder.disableStacking(item);
		return item;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getItemInHand();
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(item.getType() == Material.MOB_SPAWNER) {
				Location loc = p.getLocation();
				loc.setY(Math.floor(loc.getY()) - 1);
				Block origin = p.getWorld().getBlockAt(loc);
				
				boolean success = false;
				
				//build doink hut
				for(int x = -radius; x <= radius; x++)
					for(int z = -radius; z <= radius; z++)
						for(int y = 0; y <= height; y++)
							if(origin.getRelative(x, y, z).getType() == Material.AIR || origin.getRelative(x, y, z).getType() == Material.WOOL) {
								if(Math.abs(origin.getRelative(x, y, z).getX()) <= mapRadius
								&& Math.abs(origin.getRelative(x, y, z).getZ()) <= mapRadius
								&& origin.getRelative(x, y, z).getY() <= mapHeight
								|| p.getGameMode() == GameMode.CREATIVE) {
									success = true;
									origin.getRelative(x, y, z).setType(Material.WOOL);
									origin.getRelative(x, y, z).setData(ItemBuilder.getColorID(p));
								}
							}
				
				//fill doink with air
				for(int x = -1; x <= 1; x++)
					for(int z = -1; z <= 1; z++)
						for(int y = 1; y <= 2; y++)
							if(origin.getRelative(x, y, z).getType() == Material.WOOL)
								origin.getRelative(x, y, z).setType(Material.AIR);
				
				if(success
				|| p.getGameMode() == GameMode.CREATIVE) {
					if(p.getGameMode() != GameMode.CREATIVE) {
						item.setAmount(item.getAmount() - 1);
						p.setItemInHand(item);
					}
					Vector v = p.getVelocity();
					v.setY(0);
					p.setVelocity(v);
					p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1F, 1F);
					p.sendMessage(Message.chat("&6Doink!"));
					loc.setY(loc.getY() + 1.1);
					p.teleport(loc);
				}
				else
					p.sendMessage(Message.chat("&cYou can't place this here!"));
			}
		}
	}
	
	@EventHandler
	public void blockPlacement(BlockPlaceEvent e) {
		if(Utils.getID(e.getItemInHand()).equals(getClass().getSimpleName().toLowerCase()))
			e.setCancelled(true);
	}
}
