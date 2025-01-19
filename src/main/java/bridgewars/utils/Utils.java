package bridgewars.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bridgewars.game.CustomScoreboard;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class Utils {
	
	private static CustomScoreboard cs = new CustomScoreboard();
	
	public static int rand(int range) {
		Random rng = new Random();
		return rng.nextInt(range);
	}
	
	public static boolean isOutOfBounds(Location loc, int x, int y, int z) {
        return Math.abs(loc.getBlockX()) > x || loc.getBlockY() > y || Math.abs(loc.getBlockZ()) > z;
	}
	
	public static boolean isOutOfBounds(Location loc, int x, int y, int z, int x2, int y2, int z2) {
        return loc.getBlockX() > x || loc.getBlockY() > y || loc.getBlockZ() > z
                || loc.getBlockX() < x2 || loc.getBlockY() < y2 || loc.getBlockZ() < z2;
	}
	
	public static String getID(ItemStack in) {
		net.minecraft.server.v1_8_R3.ItemStack item = CraftItemStack.asNMSCopy(in);
		if(item == null)
			return "Error";
		NBTTagCompound idtag = item.getTag();
		if(idtag != null && idtag.hasKey("id"))
			return idtag.getString("id");
		return "Error";
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
	
	public static void subtractItem(Player p) {
		ItemStack item = p.getItemInHand();
		if(p.getGameMode() != GameMode.CREATIVE) {
			item.setAmount(item.getAmount() - 1);
			p.setItemInHand(item);
		}
		else return;
	}
}
