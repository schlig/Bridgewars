package bridgewars.game;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import bridgewars.Main;
import bridgewars.effects.ParticleTrail;
import bridgewars.settings.NaturalItemSpawning;
import bridgewars.settings.TimeLimit;
import bridgewars.utils.Message;
import bridgewars.utils.World;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class Timer {
	
	private static ArrayList<BukkitTask> particleList = new ArrayList<>();
	
	private static CustomScoreboard cs;
	private static TimeLimit limit;
	private static final int itemSpawnLimit = 10;
	private static int itemSpawnTimer = itemSpawnLimit;
	
	private static Main plugin;
	
	public Timer(Main plugin) {
		Timer.plugin = plugin;
		cs = new CustomScoreboard();
		limit = new TimeLimit();
	}
	
	public static void runTimer() {
		for(Player p : Bukkit.getOnlinePlayers())
			cs.resetTime(p);
		Bukkit.broadcastMessage(Message.chat("&r&lTime Limit: &6&l" + limit.getLimit()));
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if(Bukkit.getOnlinePlayers().size() == 0) { //automatically ends a game if no players are online
					particleList.clear();
					Game.endGame(null, true);
					this.cancel();
					return;
				}
				
				if(!GameState.isState(GameState.ACTIVE))
					this.cancel();
				
				if(NaturalItemSpawning.getState().isEnabled()) { //spawn an item anywhere within map bounds
					if(itemSpawnTimer == 0) {
						World.attemptItemSpawn(0, 0, 0, 22, true);
						itemSpawnTimer = itemSpawnLimit;
					}
					itemSpawnTimer--;
				}
				
				for(Player p : Bukkit.getOnlinePlayers()) { //creates the particle trail for players that are close to winning
					cs.updateTime(p, limit.getLimit());
					if(cs.getTime(p) == limit.getLimit() - 15)
						particleList.add(new ParticleTrail((Entity) p, EnumParticle.EXPLOSION_NORMAL, 0, 30, 0, 0, 10 * 255, 0, 0.0001F, 20, 300, true).runTaskTimer(plugin, 0L, 1L));
				}
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 0L, 20L);
	}
}
