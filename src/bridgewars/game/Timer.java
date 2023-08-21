package bridgewars.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.Main;
import bridgewars.settings.TimeLimit;
import bridgewars.utils.Utils;

public class Timer implements Listener {
	
	private static CustomScoreboard cs;
	private static TimeLimit limit;
	
	public Timer(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		cs = new CustomScoreboard();
		limit = new TimeLimit();
	}
	
	public static void runTimer() {
		for(Player p : Bukkit.getOnlinePlayers())
			cs.resetTime(p);
		Bukkit.broadcastMessage(Utils.chat("&r&lTime Limit: &6&l" + limit.getLimit()));
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if(Bukkit.getOnlinePlayers().size() == 0) {
					Game.endGame(null, true);
					this.cancel();
				}
				if(GameState.isState(GameState.ACTIVE))
					cs.updateTime(limit.getLimit());
				else
					this.cancel();
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 0L, 20L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if(Bukkit.getOnlinePlayers().size() == 0
				|| GameState.isState(GameState.INACTIVE)) {
					this.cancel();
					return;
				}
				for(Player p : Bukkit.getOnlinePlayers())
					if(cs.getTime(p) >= limit.getLimit() - 15)
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "execute @a[score_time_min=" + ((Integer)(limit.getLimit() - 15)).toString() + "] ~ ~ ~ particle explode ~ ~26 ~ .25 10 .25 .0001 20 force @a");
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 0L, 1L);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		cs.sendScoreboard(e.getPlayer());
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player)
			if(GameState.isState(GameState.ACTIVE) && cs.hasTeam((Player) e.getEntity()))
				cs.resetTime((Player) e.getEntity());
	}
}
