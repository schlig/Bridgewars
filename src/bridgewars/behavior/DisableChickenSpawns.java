package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;

import bridgewars.Main;

public class DisableChickenSpawns implements Listener {
	
	public DisableChickenSpawns(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onThrow(PlayerEggThrowEvent e) {
		e.setHatching(false);
	}
}
