package bridgewars.utils;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Utils {
	
	public static int rand(int range) {
		Random rng = new Random();
		return rng.nextInt(range);
	}
	
	public static boolean isOutOfBounds(Location loc, int x, int y, int z) {
		if(Math.abs(loc.getBlockX()) > x || loc.getBlockY() > y || Math.abs(loc.getBlockZ()) > z)
			return true;
		else
			return false;
	}
	
	public static boolean isOutOfBounds(Location loc, int x, int y, int z, int x2, int y2, int z2) {
		if(loc.getBlockX() > x || loc.getBlockY() > y || loc.getBlockZ() > z
		|| loc.getBlockX() < x2 || loc.getBlockY() < y2 || loc.getBlockZ() < z2)
			return true;
		else
			return false;
	}
	
	public static boolean compareItemName(ItemStack item, String name){
		if(item == null)
			return false;
		if(!item.hasItemMeta())
			return false;
		if(!item.getItemMeta().hasDisplayName())
			return false;

        return item.getItemMeta().getDisplayName().equals(Message.chat(name));
	}
}
