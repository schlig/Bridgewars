package bridgewars.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

import bridgewars.Main;

public class HomeRunBat extends CustomItems implements Listener {
	
	public HomeRunBat(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onHit(PlayerItemDamageEvent e) {
		if(e.getItem().getType() == Material.WOOD_SWORD)
			if(matchItem(e.getItem(), "hrb"))
				e.setDamage(20);
	}
}
