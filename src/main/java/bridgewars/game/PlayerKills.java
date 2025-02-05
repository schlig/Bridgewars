package bridgewars.game;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import bridgewars.Main;
import bridgewars.effects.ParticleTrail;
import bridgewars.messages.Chat;
import bridgewars.settings.GameSettings;
import bridgewars.settings.PlayerSettings;
import bridgewars.settings.enums.KillstreakRewards;
import bridgewars.utils.ItemManager;
import bridgewars.utils.Utils;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class PlayerKills implements Listener {
	
	private CombatTagging ct;
	private Main plugin;
	
	public PlayerKills(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
		ct = new CombatTagging();
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {		
		if(GameState.isState(GameState.ACTIVE) && CSManager.hasTeam(e.getEntity())) {
			Player victim = e.getEntity();
			
			if(e.getEntity().getKiller() instanceof Player) {

				Player player = e.getEntity().getKiller();   //killer
				
				if(player == victim || CSManager.matchTeam(player, victim))
					return;

				Leaderboards.addPoint(player, "kills");
				player.setLevel(player.getLevel() + 1);

				if(CSManager.getTime(player) + GameSettings.getKillBonus() > GameSettings.getRevealTime())
					new ParticleTrail((Entity) player, EnumParticle.EXPLOSION_NORMAL, 0, 30, 0, 0, 10 * 255, 0, 0.0001F, 20, 30 * 20, true).runTaskTimer(plugin, 0L, 1L);
				CSManager.setTime(player, CSManager.getTime(player) + GameSettings.getKillBonus());
				if(GameSettings.getKillBonus() > 0)
					player.sendMessage(Chat.color("&7+" + GameSettings.getKillBonus() + " seconds"));
				
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
				
				if(CSManager.getTime(victim) > GameSettings.getTimeLimit() - 15)
					player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
				else
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
				
				Utils.heal(player, 7);
				
				ct.setAttacker((Player)e.getEntity(), null);
			}
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
