package bridgewars.items;

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
import bridgewars.game.CustomScoreboard;
import bridgewars.utils.Message;

public class PortableDoinkHut implements Listener {
	
	private CustomScoreboard cs = new CustomScoreboard();
	
	public PortableDoinkHut (Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
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
				
				//generate doink hut
				if(origin.getType() == Material.AIR 
				|| origin.getType() == Material.WOOL) {
					if(Math.abs(origin.getX()) <= 22
					&& Math.abs(origin.getZ()) <= 22
					&& origin.getY() <= 24
					|| p.getGameMode() == GameMode.CREATIVE) {
						success = true;
						origin.setType(Material.WOOL);
						origin.setData(getColor(p));
					}
				}
				
				for(int x = -2; x <= 2; x++)
					for(int z = -2; z <= 2; z++)
						for(int y = 0; y <= 4; y++)
							if(origin.getRelative(x, y, z).getType() == Material.AIR || origin.getRelative(x, y, z).getType() == Material.WOOL) {
								if(Math.abs(origin.getRelative(x, y, z).getX()) <= 22
								&& Math.abs(origin.getRelative(x, y, z).getZ()) <= 22
								&& origin.getRelative(x, y, z).getY() <= 24
								|| p.getGameMode() == GameMode.CREATIVE) {
									success = true;
									origin.getRelative(x, y, z).setType(Material.WOOL);
									origin.getRelative(x, y, z).setData(getColor(p));
								}
							}
				
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
		if(e.getBlock().getType() == Material.MOB_SPAWNER)
			e.setCancelled(true);
	}
	
	private byte getColor(Player p) {
		byte value = 0;
		if(cs.hasTeam(p)) {
			switch(cs.getTeam(p)) {
			case "red":
				value = 14;
				break;
			case "blue":
				value = 3;
				break;
			case "green":
				value = 5;
				break;
			case "yellow":
				value = 4;
				break;
			}
		}
		return value;
	}
}
