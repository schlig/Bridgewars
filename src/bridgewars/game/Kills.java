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
				
				Player p = e.getEntity();
				Player k = e.getEntity().getKiller();
				
				if(p == k)
					return;
				
				k.setLevel(k.getLevel() + 1);
				if(KillstreakRewards.getState().isEnabled()) {
					if(k.getLevel() % 3 == 0)
						if(ks.getThreeStreak(k) == 0)
							k.getInventory().addItem(ItemManager.getItem("BridgeEgg").createItem(p));
						else if(ks.getThreeStreak(k) == 1)
							k.getInventory().addItem(ItemManager.getItem("PortableDoinkHut").createItem(p));
					if(k.getLevel() % 5 == 0)
						if(ks.getFiveStreak(k) == 0)
							k.getInventory().addItem(ItemManager.getItem("HomeRunBat").createItem(p));
						else if(ks.getFiveStreak(k) == 1)
							k.getInventory().addItem(ItemManager.getItem("Fireball").createItem(p));
					if(k.getLevel() % 7 == 0)
						if(ks.getSevenStreak(k) == 0)
							k.getInventory().addItem(ItemManager.getItem("LifeforcePotion").createItem(p));
						else if(ks.getSevenStreak(k) == 1)
							k.getInventory().addItem(ItemManager.getItem("BlackHole").createItem(p));
				}
				
				if(cs.getTime(p) < tl.getLimit() - 15)
					k.playSound(k.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
				else
					k.playSound(k.getLocation(), Sound.LEVEL_UP, 1F, 1F);
				
				if(k.getHealth() + 7 > k.getMaxHealth())
					k.setHealth(k.getMaxHealth());
				else
					k.setHealth(k.getHealth() + 7);
				
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
