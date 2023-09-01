package bridgewars.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import bridgewars.game.CustomScoreboard;

public class Disguise {

	@SuppressWarnings("unused")
	private static CustomScoreboard cs = new CustomScoreboard();
	
	public static Player getPlayerByName(String name) {
		try {
			URL profile = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
			InputStreamReader r = new InputStreamReader(profile.openStream());
            String uuid = new JsonParser().parse(r).getAsJsonObject().get("id").getAsString();
            
            return Bukkit.getOfflinePlayer(uuid).getPlayer();
		} catch (Exception e) { e.printStackTrace(); return null; }
	}
	
	public static void setDisguise(Player p, Player target) {
		CraftPlayer player = (CraftPlayer) p;
		GameProfile profile = player.getHandle().getProfile();
		
		for(Player players : Bukkit.getOnlinePlayers())
			players.hidePlayer(p);

		String[] propertyData = getSkin(target.getName());
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", new Property("textures", propertyData[0], propertyData[1]));
		setName(profile, player, target.getName());
		
		for(Player players : Bukkit.getOnlinePlayers())
			players.showPlayer(p);
	}
	
	private static void setName(GameProfile profile, Player p, String name) {
		try {
			Field field = profile.getClass().getDeclaredField("name");
			field.setAccessible(true);
			field.set(profile, name);
		} catch (Exception e) { }
	}
	
	private static String[] getSkin(String name) {
		try {
			URL profile = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
			InputStreamReader r = new InputStreamReader(profile.openStream());
            String uuid = new JsonParser().parse(r).getAsJsonObject().get("id").getAsString();
     
            URL session = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            r = new InputStreamReader(session.openStream());
            JsonObject textureProperty = new JsonParser().parse(r).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();
            String signature = textureProperty.get("signature").getAsString();
     
            return new String[] { texture, signature };
            
		} catch (IOException e) { 
			e.printStackTrace();
			return null; }
	}
}
