package bridgewars.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class Disguise {
	
	public static List<String> randomDisguiseList = Arrays.asList(
			"Schlog",
			"Wabadaba",
			"MookW",
			"Physiological",
			"nicktoot",
			"TalonBX",
			"Freejourner",
			"Northwind5545",
			"ThuggishPrune",
			"EmergenCheese",
			"towwl",
			"eggwave",
			"MrFrexxy",
			"onjit",
			"tiralmo",
			"turewjyg",
			"SirLeo__",
			"Ohhkkay",
			"SirJosh3917",
			"Anch");
	
	public static void setDisguise(Player p, UUID target) {
		CraftPlayer player = (CraftPlayer) p;
		GameProfile profile = player.getHandle().getProfile();
		
		for(Player players : Bukkit.getOnlinePlayers())
			players.hidePlayer(p);

		setSkin(profile, player, target);
		setName(profile, player, Utils.getName(target));
		
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
	
	private static void setSkin(GameProfile profile, CraftPlayer p, UUID target) {
		String[] propertyData = Utils.getSkin(target);
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", new Property("textures", propertyData[0], propertyData[1]));
	}
}
