package bridgewars.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bridgewars.game.CustomScoreboard;

public class Utils {
	
	private static CustomScoreboard cs = new CustomScoreboard();
	
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
	
	public static boolean matchItem(ItemStack item, ItemStack target){
		if(item == null)
			return false;
		if(!item.hasItemMeta())
			return false;
		if(!item.getItemMeta().hasDisplayName())
			return false;

        return item.getItemMeta().getDisplayName().equals(target.getItemMeta().getDisplayName());
	}
	
	public static String getName(UUID uuid) {
		try {
            URL session = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader r = new InputStreamReader(session.openStream());
            return new JsonParser().parse(r).getAsJsonObject().get("name").getAsString();
            
		} catch (Exception e) { e.printStackTrace(); return null; }
	}
	
	public static UUID getUUID(String name) {
        try {
			URL profile = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader r = new InputStreamReader(profile.openStream());
            return UUID.fromString(new JsonParser().parse(r).getAsJsonObject().get("id").getAsString().replaceFirst(
            		"(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", 
            		"$1-$2-$3-$4-$5"));
		} 
        catch (IOException e) { e.printStackTrace(); return null; }
	}
	
	public static String[] getSkin(UUID uuid) {
		try {
            URL session = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString() + "?unsigned=false");
            InputStreamReader r = new InputStreamReader(session.openStream());
            JsonObject textureProperty = new JsonParser().parse(r).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();
            String signature = textureProperty.get("signature").getAsString();
     
            return new String[] { texture, signature };
            
		} catch (IOException e) { 
			e.printStackTrace();
			return null; }
	}
	
	public static Boolean matchTeam(Player p, Player p2) {
		return cs.matchTeam(p, p2);
	}
	
	public static Location getSpawn() {
		return new Location(Bukkit.getWorld("world"), 1062.5, 52, 88.5, -90, 10);
	}
}
