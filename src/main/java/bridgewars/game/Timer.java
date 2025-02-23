package bridgewars.game;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import bridgewars.Main;
import bridgewars.effects.ParticleTrail;
import bridgewars.messages.Chat;
import bridgewars.settings.GameSettings;
import bridgewars.settings.enums.NaturalItemSpawning;
import bridgewars.settings.enums.RevealWinner;
import bridgewars.utils.World;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class Timer {
	
	private static ArrayList<BukkitTask> particleList = new ArrayList<>();
	
	private static final int itemSpawnLimit = 10;
	private static int itemSpawnTimer = itemSpawnLimit;
	
	private static Main plugin;
	
	public Timer(Main plugin) {
		Timer.plugin = plugin;
	}
	
	public static void runTimer() {
		for(Player p : Bukkit.getOnlinePlayers())
			CSManager.resetTime(p);
		Bukkit.broadcastMessage(Chat.color("&r&lTime Limit: &6&l" + GameSettings.getTimeLimit()));
		
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
					if(CSManager.getTime(p) == GameSettings.getRevealTime() && RevealWinner.getState().isEnabled())
						particleList.add(new ParticleTrail((Entity) p, EnumParticle.EXPLOSION_NORMAL, 0, 30, 0, 0, 10 * 255, 0, 0.0001F, 20, 30 * 20, true).runTaskTimer(plugin, 0L, 1L));
					CSManager.updateTime(p, GameSettings.getTimeLimit());
				}
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 0L, 20L);
	}
}
