package bridgewars.game;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import bridgewars.Main;
import bridgewars.settings.PlayerSettings;
import bridgewars.settings.TimeLimit;
import bridgewars.settings.enums.KillstreakRewards;
import bridgewars.utils.ItemManager;

public class Kills implements Listener {
	
	private TimeLimit tl;
	private CombatTagging ct;
	
	public Kills(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		ct = new CombatTagging();
		tl = new TimeLimit();
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player)
			if(GameState.isState(GameState.ACTIVE) && CSManager.hasTeam((Player) e.getEntity()))
				CSManager.resetTime((Player) e.getEntity());
		
		if(GameState.isState(GameState.ACTIVE))
			if(e.getEntity().getKiller() instanceof Player) {

				Player player = e.getEntity().getKiller();   //killer
				Player victim = e.getEntity();				 //victim
				
				if(player == victim || CSManager.matchTeam(player, victim))
					return;
				
				Leaderboards.addPoint(player, "kills");
				player.setLevel(player.getLevel() + 1);
				if(KillstreakRewards.getState().isEnabled()) {
					if(player.getLevel() % 3 == 0)
						player.getInventory().addItem(ItemManager.getItem(PlayerSettings.getSetting(player, "KillstreakRewardWhite")).createItem(player));
					
					if(player.getLevel() % 5 == 0)
						player.getInventory().addItem(ItemManager.getItem(PlayerSettings.getSetting(player, "KillstreakRewardGreen")).createItem(player));
					
					if(player.getLevel() % 7 == 0)
						player.getInventory().addItem(ItemManager.getItem(PlayerSettings.getSetting(player, "KillstreakRewardRed")).createItem(player));
					
					if(player.getLevel() % 15 == 0)
						player.getInventory().addItem(ItemManager.getItem(PlayerSettings.getSetting(player, "KillstreakRewardBlue")).createItem(player));
				}
				
				if(CSManager.getTime(victim) >= tl.revealTime())
					player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
				else
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
				
				if(player.getHealth() + 7 > player.getMaxHealth())
					player.setHealth(player.getMaxHealth());
				else
					player.setHealth(player.getHealth() + 7);
				
				ct.setAttacker((Player)e.getEntity(), null);
			}
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			ct.setDamageCause(p, e.getCause());
			if(e.getDamager() instanceof Player) {
				Player k = (Player) e.getDamager();
				ct.setAttacker(p, k);
			}
			else if(e.getDamager() instanceof Projectile) {
				Player k = (Player) ((Projectile)e.getDamager()).getShooter();
				ct.setAttacker(p, k);
			}
		}
	}
}
