package bridgewars.effects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import bridgewars.Main;
import bridgewars.messages.Chat;

public class PlotArmor implements Listener {

	public static List<Player> armoredPlayers = new ArrayList<>();
	
	private int duration = 10  //duration of strength on revive (seconds, gets multiplied by 20 later)
						 * 20; 
	private int level = 1; //level of strength
	
	public PlotArmor(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		if(!armoredPlayers.contains(p))
			return;

		p.setHealth(p.getMaxHealth());
		armoredPlayers.remove(p);
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, duration, level - 1), true);

		p.sendMessage(Chat.color("&cYou withstood a fatal attack!"));
		p.playSound(p.getLocation(), Sound.LAVA_POP, 0.5F, 0.8F);
		if(p.getKiller() instanceof Player)  {
			p.getKiller().sendMessage(Chat.color(p.getDisplayName() + " &cwithstood your blow!"));
			p.getKiller().playSound(p.getKiller().getLocation(), Sound.ENDERDRAGON_HIT, 0.8F, 1F);
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player)
			if(e.getCause() == DamageCause.VOID && armoredPlayers.contains((Player) e.getEntity()))
				PlotArmor.armoredPlayers.remove((Player) e.getEntity());
	}
}
