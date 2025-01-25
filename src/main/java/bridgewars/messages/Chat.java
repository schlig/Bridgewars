package bridgewars.messages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import bridgewars.Main;
import bridgewars.commands.ChatSetting;
import bridgewars.game.CustomScoreboard;

public class Chat implements Listener {

	private CustomScoreboard cs = new CustomScoreboard();
	
	public Chat(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onMessage(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(ChatSetting.allChat.containsKey(p)) {
			if(ChatSetting.allChat.get(p) || !cs.hasTeam(p))
				return;
			else {
				e.setCancelled(true);
				String m = color("&7[&6Team&7]&r ") + "<" + p.getDisplayName() + "> " + e.getMessage();
				for(Player player : Bukkit.getOnlinePlayers())
					if(cs.hasTeam(p))
						if(cs.getTeam(p).equals(cs.getTeam(player)))
							player.sendMessage(m);
				System.out.print(m);
			}
		}
	}
	
	public static String color(String s) { //this is used to color any text that appears in minecraft
		return ChatColor.translateAlternateColorCodes('&', s);
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
	}
}
