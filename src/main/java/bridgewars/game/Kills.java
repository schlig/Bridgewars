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
import bridgewars.settings.ChosenKillstreaks;
import bridgewars.settings.KillstreakRewards;
import bridgewars.settings.TimeLimit;
import bridgewars.utils.ItemManager;

public class Kills implements Listener {
	
	private ChosenKillstreaks ks;
	private CustomScoreboard cs;
	private TimeLimit tl;
	private CombatTagging ct;
	
	public Kills(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		ks = new ChosenKillstreaks();
		ct = new CombatTagging();
		cs = new CustomScoreboard();
		tl = new TimeLimit();
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player)
			if(GameState.isState(GameState.ACTIVE) && cs.hasTeam((Player) e.getEntity()))
				cs.resetTime((Player) e.getEntity());
		
		if(GameState.isState(GameState.ACTIVE))
			if(e.getEntity().getKiller() instanceof Player) {

				Player player = e.getEntity().getKiller();   //killer
				Player victim = e.getEntity();				 //victim
				
				if(player == victim || cs.matchTeam(player, victim))
					return;
				
				Leaderboards.addKill(player);
				player.setLevel(player.getLevel() + 1);
				if(KillstreakRewards.getState().isEnabled()) {
					if(player.getLevel() % 3 == 0)
						if(ks.getThreeStreak(player) == 0)
							player.getInventory().addItem(ItemManager.getItem("BridgeEgg").createItem(player));
						else if(ks.getThreeStreak(player) == 1)
							player.getInventory().addItem(ItemManager.getItem("MysteryPill").createItem(player));
					if(player.getLevel() % 5 == 0)
						if(ks.getFiveStreak(player) == 0)
							player.getInventory().addItem(ItemManager.getItem("PortableDoinkHut").createItem(player));
						else if(ks.getFiveStreak(player) == 1)
							player.getInventory().addItem(ItemManager.getItem("Fireball").createItem(player));
					if(player.getLevel() % 7 == 0)
						if(ks.getSevenStreak(player) == 0)
							player.getInventory().addItem(ItemManager.getItem("HomeRunBat").createItem(player));
						else if(ks.getSevenStreak(player) == 1)
							player.getInventory().addItem(ItemManager.getItem("BlackHole").createItem(player));
					if(player.getLevel() % 15 == 0)
						if(ks.getFinalStreak(player) == 0)
							player.getInventory().addItem(ItemManager.getItem("HeartContainer").createItem(player));
						else if(ks.getFinalStreak(player) == 1)
							player.getInventory().addItem(ItemManager.getItem("MagicStopwatch").createItem(player));
						
				}
				
				if(cs.getTime(victim) < tl.getLimit() - 15)
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
				else
					player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
				
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
