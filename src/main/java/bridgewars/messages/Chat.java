package bridgewars.messages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import bridgewars.Main;
import bridgewars.commands.ChatSetting;
import bridgewars.game.CustomScoreboard;
import bridgewars.utils.Message;

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
				String m = Message.chat("&7[&6Team&7]&r ") + "<" + p.getDisplayName() + "> " + e.getMessage();
				for(Player player : Bukkit.getOnlinePlayers())
					if(cs.hasTeam(p))
						if(cs.getTeam(p).equals(cs.getTeam(player)))
							player.sendMessage(m);
				System.out.print(m);
			}
		}
	}
}
