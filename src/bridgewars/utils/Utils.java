package bridgewars.utils;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class Utils {
	public static String chat(String s) {
		//Colors:
//		0: Black
//		1: Dark Blue
//		2: Dark Green
//		3: Dark Aqua
//		4: Dark Red
//		5: Dark Purple
//		6: Gold
//		7: Gray
//		8: Dark Gray
//		9: Blue
//		a: Green
//		b: Aqua
//		c: Red
//		d: Pink
//		e: Yellow
//		f: White
		
//		k: Obfuscated
//		l: Bold
//		m: Strikethrough
//		n: Underline
//		o: Italic
//		r: Reset
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static int rand(int range) {
		Random rng = new Random();
		return rng.nextInt(range);
	}

	public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = ChatSerializer.a("{'text': '" + title + "'}");
        IChatBaseComponent subtitleJSON = ChatSerializer.a("{'text': '" + subtitle + "'}");
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleJSON);
        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);
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
