package bridgewars.utils;

import java.util.Random;

import org.bukkit.Location;

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
	
	public static boolean isOutOfBounds(Location loc, int x, int y, int z, int a, int b, int c) {
		if(Math.abs(loc.getBlockX()) > x || loc.getBlockY() > y || Math.abs(loc.getBlockZ()) > z)
			return true;
		else
			return false;
	}
}
