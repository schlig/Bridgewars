package bridgewars.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bridgewars.game.CustomScoreboard;
import bridgewars.game.Leaderboards;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class Utils {
	
	private static CustomScoreboard cs = new CustomScoreboard();
	
	public static int rand(int range) {
		Random rng = new Random();
		return rng.nextInt(range);
	}
	
	public static Vector raycast(double yaw, double pitch) {
		//this is such a basic raytracing formula but minecraft's coordinate system
		//is fucking dumb and is rotated on the x axis or something
		//anyways you can probably use this to draw more than just lines by plugging in whatever you want for pitch and yaw, or using loops to get neat results
		
		double theta = Math.toRadians(yaw); //angle for the x/z axis
		double phi = Math.toRadians(pitch); //"height" angle

		double x = 1 * Math.cos(phi) * Math.sin(theta);
		double z = 1 * Math.cos(phi) * Math.cos(theta);
		double y = 1 * Math.sin(phi);
		
		return new Vector(x, y, z).normalize();
	}
	
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
	
	public static boolean isOutOfBounds(Location loc, int x, int y, int z) {
        return Math.abs(loc.getBlockX()) > x || loc.getBlockY() > y || Math.abs(loc.getBlockZ()) > z;
	}
	
	public static boolean isOutOfBounds(Location loc, int x, int y, int z, int x2, int y2, int z2) {
        return loc.getBlockX() > x || loc.getBlockY() > y || loc.getBlockZ() > z
                || loc.getBlockX() < x2 || loc.getBlockY() < y2 || loc.getBlockZ() < z2;
	}
	
	public static boolean checkAABBCollision(Player target, Vector a, Vector b) {
		//so glad i found this, i was going crazy trying to understand the math behind it
		//takes two points and checks if a player's hitbox is between them
		//useful with raycasts, make sure the attacker or w/e uses their eye location or it'll be off quite a bit
		CraftPlayer player = (CraftPlayer) target;
		AxisAlignedBB hitbox = player.getHandle().getBoundingBox();
		
		final double epsilon = 0.0001f;
		
		Vector min = new Vector(Math.min(hitbox.a, hitbox.d), Math.min(hitbox.b, hitbox.e), Math.min(hitbox.c, hitbox.f));
		Vector max = new Vector(Math.max(hitbox.a, hitbox.d), Math.max(hitbox.b, hitbox.e), Math.max(hitbox.c, hitbox.f));
		
		Vector p1 = a.clone();
		Vector p2 = b.clone();
		Vector d = p2.subtract(p1).multiply(0.5);
		Vector e = max.clone().subtract(min).multiply(0.5);
		Vector c = p1.add(d).subtract(min.clone().add(max).multiply(0.5));
		Vector ad = new Vector(Math.abs(d.getX()), Math.abs(d.getY()), Math.abs(d.getZ()));
		
		if(Math.abs(c.getX()) > e.getX() + ad.getX())
			return false;
		if(Math.abs(c.getY()) > e.getY() + ad.getY())
			return false;
		if(Math.abs(c.getZ()) > e.getZ() + ad.getZ())
			return false;

        if(Math.abs(d.getY() * c.getZ() - d.getZ() * c.getY()) > e.getY() * ad.getZ() + e.getZ() * ad.getY() + epsilon)
            return false;
        if(Math.abs(d.getZ() * c.getX() - d.getX() * c.getZ()) > e.getZ() * ad.getX() + e.getX() * ad.getZ() + epsilon)
            return false;
        if(Math.abs(d.getX() * c.getY() - d.getY() * c.getX()) > e.getX() * ad.getY() + e.getY() * ad.getX() + epsilon)
            return false;
		
		return true;
	}
	
	public static ItemStack a(ItemStack item, Player p) {
		
		net.minecraft.server.v1_8_R3.ItemStack itemTags = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = itemTags.getTag();
		NBTTagList list = new NBTTagList();
		NBTTagCompound speed = new NBTTagCompound();
		speed.set("AttributeName", new NBTTagString("generic.movementSpeed"));
		speed.set("Name", new NBTTagString("generic.movementSpeed"));
//		speed.set("Amount", new NBTTagDouble(speedModifier.get(p)));
		speed.set("Operation", new NBTTagInt(1));
		UUID uuid = UUID.randomUUID();
		speed.set("UUIDLeast", new NBTTagLong(uuid.getLeastSignificantBits()));
		speed.set("UUIDMost", new NBTTagLong(uuid.getMostSignificantBits()));
		list.add(speed);
		compound.set("AttributeModifiers", list);
		itemTags.setTag(compound);
		item = CraftItemStack.asBukkitCopy(itemTags);
		
		return item;
	}
	
	public static int getAttackDamage(Player p) {
		int damage = 1;

		ItemStack weapon = p.getItemInHand();
		
		switch(weapon.getType()) {
		case WOOD_SWORD:
			damage += 4;
			break;
			
		case GOLD_SWORD:
			damage += 4;
			break;
			
		case STONE_SWORD:
			damage += 5;
			break;
			
		case IRON_SWORD:
			damage += 6;
			break;
			
		case DIAMOND_SWORD:
			damage += 7;
			break;
			
		default:
			damage += 0;
			break;
		}
		
		return damage;
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
			Leaderboards.addPoint(p, "items");
		}
		else return;
	}
    
    public static void heal(Player p) {
    	p.setHealth(p.getMaxHealth());
    }
    
    public static void heal(Player p, double amount) {
    	if(p.getHealth() + amount >= p.getMaxHealth())
    		heal(p);
    	else p.setHealth(p.getHealth() + amount);
    }
}
