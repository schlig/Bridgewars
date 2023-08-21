package bridgewars.messages;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import bridgewars.Main;
import bridgewars.game.CustomScoreboard;
import bridgewars.utils.Utils;

public class OnJoin implements Listener {
	
	private CustomScoreboard cs;
	
	public OnJoin(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		cs = new CustomScoreboard();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		cs.resetTeam(p, false);
		p.setLevel(0);
		p.sendMessage(Utils.chat("Welcome to &6Bridgewars&r! Type &c/menu&r to get started."));
		cs.removePlayerFromTimer(p);
		if(p.getGameMode() != GameMode.CREATIVE) {
			p.setAllowFlight(false);
			p.setFlying(false);
			p.teleport(new Location(Bukkit.getWorld("world"), 1062.5, 52, 88.5, -90, 10));
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.setGameMode(GameMode.ADVENTURE);
		}
	}
}