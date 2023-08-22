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
import bridgewars.items.Items;
import bridgewars.settings.ChosenKillstreaks;
import bridgewars.settings.KillstreakRewards;

public class Kills implements Listener {
	
	private Items items;
	private ChosenKillstreaks ks;
	private CombatTagging ct;
	
	public Kills(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		items = new Items();
		ks = new ChosenKillstreaks();
		ct = new CombatTagging();
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		if(GameState.isState(GameState.ACTIVE))
			if(e.getEntity().getKiller() instanceof Player) {
				Player p = e.getEntity().getKiller();
				if(ct.getAttacker((Player)e.getEntity()) != p)
					return;

				
				p.setLevel(p.getLevel() + 1);
				if(KillstreakRewards.getState().isEnabled()) {
					if(p.getLevel() % 3 == 0)
						if(ks.getThreeStreak(p) == 0)
							p.getInventory().addItem(items.getBridgeEgg(2, false));
						else if(ks.getThreeStreak(p) == 1)
							p.getInventory().addItem(items.getPortableDoinkHut(1, false));
					if(p.getLevel() % 5 == 0)
						if(ks.getFiveStreak(p) == 0)
							p.getInventory().addItem(items.getHomeRunBat(1, false));
						else if(ks.getFiveStreak(p) == 1)
							p.getInventory().addItem(items.getFireball(1, false));
					if(p.getLevel() % 7 == 0)
						if(ks.getSevenStreak(p) == 0)
							p.getInventory().addItem(items.getLifeforcePotion(1, false));
						else if(ks.getSevenStreak(p) == 1)
							p.getInventory().addItem(items.getBlackHole(1, false));
				}
				
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
				
				if(p.getHealth() + 7 > 20)
					p.setHealth(20);
				else
					p.setHealth(p.getHealth() + 7);
				
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
