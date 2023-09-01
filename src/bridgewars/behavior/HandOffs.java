package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.CustomScoreboard;
import bridgewars.utils.Message;

public class HandOffs implements Listener {

	private CustomScoreboard cs = new CustomScoreboard();
	
	public HandOffs (Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClick(PlayerInteractAtEntityEvent e) {
		if(e.getRightClicked() instanceof Player) {
			Player p = e.getPlayer();
			Player t = (Player) e.getRightClicked();
			
			if(cs.matchTeam(p, t) && p.isSneaking()) {
				ItemStack item = p.getItemInHand();
				
				t.getInventory().addItem(item);
				p.setItemInHand(null);
				if(item.getAmount() == 1) {
					t.sendMessage(Message.chat(p.getDisplayName() + " handed you a " + item.getItemMeta().getDisplayName() + "&r!"));
					p.sendMessage(Message.chat("&rYou handed your " + item.getItemMeta().getDisplayName() + " &rto " + t.getDisplayName() + "&r!"));
				}
				else {
					t.sendMessage(Message.chat(p.getDisplayName() + " handed you some " + item.getItemMeta().getDisplayName() + "s&r!"));
					p.sendMessage(Message.chat("&rYou handed your " + item.getItemMeta().getDisplayName() + "s &rto " + t.getDisplayName() + "&r!"));
				}
			}
		}
	}
}
