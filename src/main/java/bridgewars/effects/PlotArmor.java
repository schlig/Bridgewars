package bridgewars.effects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import bridgewars.Main;
import bridgewars.utils.Message;

public class PlotArmor implements Listener {

	public static List<Player> armoredPlayers = new ArrayList<>();
	private int iDuration = 10 * 20; //duration of invincibility given after "death" (seconds)
	private Main plugin;
	
	public PlotArmor(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		if(!armoredPlayers.contains(p))
			return;

		p.setHealth(1);
		armoredPlayers.remove(p);
		new Invincibility(p, iDuration, true).runTaskTimer(plugin, 0L, 1L);
		
		p.sendMessage(Message.chat("&cYou did not succumb."));
		if(p.getKiller() instanceof Player)
			p.getKiller().sendMessage(Message.chat(p.getDisplayName() + " &cdid not succumb."));
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player)
			if(e.getCause() == DamageCause.VOID && armoredPlayers.contains((Player) e.getEntity()))
				PlotArmor.armoredPlayers.remove((Player) e.getEntity());
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player)
			if(Invincibility.invinciblePlayers.contains((Player) e.getEntity()) && e.getCause() != DamageCause.VOID)
				e.setCancelled(true);
		
		if(e.getDamager() instanceof Player 
				&& Invincibility.removeOnHit.containsKey((Player) e.getDamager())
				&& Invincibility.removeOnHit.get((Player) e.getDamager()))
			if(Invincibility.invinciblePlayers.contains((Player) e.getDamager()))
				Invincibility.invinciblePlayers.remove((Player) e.getDamager());
	}
}
