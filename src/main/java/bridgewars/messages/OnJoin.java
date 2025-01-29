package bridgewars.messages;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import bridgewars.Main;
import bridgewars.commands.ChatSetting;
import bridgewars.commands.Fly;
import bridgewars.game.CSManager;
import bridgewars.game.Leaderboards;
import bridgewars.items.MagicStopwatch;
import bridgewars.settings.enums.DoubleHealth;
import bridgewars.utils.Permissions;
import bridgewars.utils.World;

public class OnJoin implements Listener {
	
	public OnJoin(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		
		CSManager.sendScoreboard(p);
		CSManager.resetTeam(p, false);
		CSManager.removePlayerFromTimer(p);
		
		Leaderboards.clearPoints(p, "kills");
		Leaderboards.clearPoints(p, "items");
		if(MagicStopwatch.speedModifier.containsKey(p))
			MagicStopwatch.speedModifier.remove(p);
		
		p.setLevel(0);
		p.sendMessage(Chat.color("Welcome to &6Bridgewars&r! Type &c/menu&r to get started."));
		Fly.allowFlight.put(p, false);
		if(!ChatSetting.allChat.containsKey(p))
			ChatSetting.allChat.put(p, true);
		Permissions.loadSettings(p);
		
		if(DoubleHealth.getState().isEnabled()) {
			p.setMaxHealth(40);
			p.setHealth(40);
		}
		else {
			p.setHealth(20);
			p.setMaxHealth(20);
		}
		
		for(PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
		
		p.setAllowFlight(false);
		p.setFlying(false);
		p.teleport(World.getSpawn()); //uses bridgewars.utils.World, not bukkit.org.World
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.setGameMode(GameMode.ADVENTURE);
		e.setJoinMessage(Chat.color("&e" + p.getName() + " joined the game"));
	}
}
