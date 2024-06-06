package bridgewars.utils;

import org.bukkit.ChatColor;

public class Message {

	public static String chat(String s) { //this is used to color any text that appears in minecraft
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
}
