package bridgewars.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.Main;
import bridgewars.items.Items;
import bridgewars.settings.NaturalItemSpawning;
import bridgewars.settings.TimeLimit;
import bridgewars.utils.Utils;

public class Timer implements Listener {
	
	private static CustomScoreboard cs;
	private static TimeLimit limit;
	private static Items items;
	private static int itemSpawnTimer = 0;
	
	public Timer(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		cs = new CustomScoreboard();
		limit = new TimeLimit();
		items = new Items();
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
				
				if(NaturalItemSpawning.getState().isEnabled()) {
					if(itemSpawnTimer == 20)
						attemptItemSpawn(3);
					itemSpawnTimer++;
				}
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
	
	private static void attemptItemSpawn(int chance) {
		boolean validLocation = false;
		boolean success = false;
		
		int attempts = 0;
		int x = 0, y = 0, z = 0;
		
		 if(Utils.rand(chance) + 1 == chance)
			 success = true;
		
		while(!validLocation && success) {
			x = Utils.rand(45) - 22;
			z = Utils.rand(45) - 22;
			
			for(y = 0; y < 24; y++)
				if(Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, y, z)).getType() != Material.AIR
				&& Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, y + 1, z)).getType() == Material.AIR) {
					validLocation = true;
					break;
				}
			attempts++;
			if(attempts == 300)
				break;
		}
		
		if(validLocation) {
			Item item = null; 
			int quality = Utils.rand(100) + 1;
			if(quality <= 75)
				switch(Utils.rand(2) + 1) {
				case 1:
					item = Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"), x + 0.5, y + 1, z + 0.5), items.getBridgeEgg(1, false));
					break;
				case 2:
					item = Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"), x + 0.5, y + 1, z + 0.5), items.getPortableDoinkHut(1, false));
					break;
				}
			
			else if (quality >= 76 && quality <= 95)
				switch(Utils.rand(2) + 1) {
				case 1:
					item = Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"), x + 0.5, y + 1, z + 0.5), items.getHomeRunBat(1, false));
					break;
				case 2:
					item = Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"), x + 0.5, y + 1, z + 0.5), items.getFireball(1, false));
					break;
				}
			
			else if (quality >= 95)
				switch(Utils.rand(2) + 1) {
				case 1:
					item = Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"), x + 0.5, y + 1, z + 0.5), items.getLifeforcePotion(1, false));
					break;
				case 2:
					item = Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"), x + 0.5, y + 1, z + 0.5), items.getBlackHole(1, false));
					break;
				}
			item.setVelocity(item.getVelocity().setX(0).setY(0).setZ(0));
		}
		itemSpawnTimer = 0;
	}
}
