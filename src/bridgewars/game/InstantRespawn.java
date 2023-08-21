package bridgewars.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import bridgewars.Main;
import bridgewars.items.Items;
import bridgewars.settings.Bows;
import bridgewars.settings.Swords;

public class InstantRespawn implements Listener {
	
	private CustomScoreboard cs;
	private Items items;
	
	public InstantRespawn(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		cs = new CustomScoreboard();
		items = new Items();
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		p.setHealth(p.getMaxHealth());
		final Vector v = new Vector();
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> p.setVelocity(v), 1L);
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> p.setFireTicks(0), 1L);
		
		for(PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
		p.setLevel(0);
		
		if(GameState.isState(GameState.ACTIVE)) {
			if(cs.hasTeam(p)) {
				Game.spawnPlayer(p);
				p.playSound(p.getLocation(), Sound.HURT_FLESH, 1F, 1F);
				if(!p.getInventory().contains(Material.GOLD_SWORD) && Swords.getState().isEnabled())
					p.getInventory().addItem(items.getSword(p, cs.getTeam(p)));
				if(!p.getInventory().contains(Material.BOW) && Bows.getState().isEnabled())
					p.getInventory().addItem(items.getBow(p, cs.getTeam(p)));
				if(!p.getInventory().contains(Material.ARROW) && Bows.getState().isEnabled())
					p.getInventory().addItem(new ItemStack(Material.ARROW, 1));
				if(!p.getInventory().contains(Material.SHEARS))
					p.getInventory().addItem(items.getShears(p));
				if(!p.getInventory().contains(Material.WOOL))
					p.getInventory().addItem(items.getBlocks(p, cs.getTeam(p)));
				if(!p.getInventory().contains(Material.LEATHER_HELMET))
					p.getInventory().setHelmet((items.getHelm(p, cs.getTeam(p))));
				if(!p.getInventory().contains(Material.LEATHER_CHESTPLATE))
					p.getInventory().setChestplate(items.getChest(p, cs.getTeam(p)));
				if(!p.getInventory().contains(Material.LEATHER_LEGGINGS))
					p.getInventory().setLeggings(items.getLegs(p, cs.getTeam(p)));
				if(!p.getInventory().contains(Material.LEATHER_BOOTS))
					p.getInventory().setBoots(items.getBoots(p, cs.getTeam(p)));
				return;
			}
		}
		
		if(p.getBedSpawnLocation() != null)
			p.setBedSpawnLocation(null);
		p.teleport(new Location(Bukkit.getWorld("world"), 1062.5, 52, 88.5, -90, 10));
		p.playSound(p.getLocation(), Sound.HURT_FLESH, 1F, 1F);
	}
}