package bridgewars.behavior;

import org.bukkit.Bukkit;
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
import bridgewars.effects.PlotArmor;
import bridgewars.game.CSManager;
import bridgewars.game.Game;
import bridgewars.game.GameState;
import bridgewars.game.Leaderboards;
import bridgewars.settings.enums.Blocks;
import bridgewars.settings.enums.Bows;
import bridgewars.settings.enums.DigWars;
import bridgewars.settings.enums.GigaDrill;
import bridgewars.settings.enums.Shears;
import bridgewars.settings.enums.Swords;
import bridgewars.utils.ItemManager;
import bridgewars.utils.World;

public class ImmediateRespawn implements Listener {
	
	public ImmediateRespawn(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if(PlotArmor.armoredPlayers.contains(p))
			return;
		
		p.setHealth(p.getMaxHealth());
		final Vector v = new Vector();
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> p.setVelocity(v), 1L);
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> p.setFireTicks(0), 1L);
		
		for(PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
		p.setLevel(0);
		
		if(GameState.isState(GameState.ACTIVE)) {
			if(CSManager.hasTeam(p)) {
				Game.placeSpawns();
				Game.spawnPlayer(p);
				Leaderboards.addPoint(p, "lifetimeDeaths");
				p.playSound(p.getLocation(), Sound.HURT_FLESH, 1F, 1F);
				//base equipment
				if(!p.getInventory().contains(Material.GOLD_SWORD) && Swords.getState().isEnabled())
					p.getInventory().addItem(ItemManager.getItem("BasicSword").createItem(p));
				if(!p.getInventory().contains(Material.SHEARS) && Shears.getState().isEnabled() && !GigaDrill.getState().isEnabled())
					p.getInventory().addItem(ItemManager.getItem("Shears").createItem(p));
				if(!p.getInventory().contains(Material.WOOL) && Blocks.getState().isEnabled())
					p.getInventory().addItem(ItemManager.getItem("WoolBlocks").createItem(p));
				
				//bows
				if(!p.getInventory().contains(Material.BOW) && Bows.getState().isEnabled())
					p.getInventory().addItem(ItemManager.getItem("Bow").createItem(p));
				if(!p.getInventory().contains(Material.ARROW) && Bows.getState().isEnabled())
					p.getInventory().addItem(new ItemStack(Material.ARROW, 1));
				
				//giga shears
				if(!p.getInventory().contains(Material.SHEARS) && GigaDrill.getState().isEnabled())
					p.getInventory().addItem(ItemManager.getItem("GigaShears").createItem(p));
				
				//digwars stuff
				if(DigWars.getState().isEnabled()) {
					p.getInventory().remove(Material.WOOD);
					p.getInventory().addItem(new ItemStack(Material.WOOD, 64));
				}
				if(!p.getInventory().contains(Material.STONE_AXE) && DigWars.getState().isEnabled())
					p.getInventory().addItem(ItemManager.getItem("Axe").createItem(p));
				
				//give armor back if it's missing
				if(p.getInventory().getHelmet() == null)
					p.getInventory().setHelmet(ItemManager.getItem("BasicHelmet").createItem(p));
				if(p.getInventory().getChestplate() == null)
					p.getInventory().setChestplate(ItemManager.getItem("BasicChestplate").createItem(p));
				if(p.getInventory().getLeggings() == null)
					p.getInventory().setLeggings(ItemManager.getItem("BasicLeggings").createItem(p));
				if(p.getInventory().getBoots() == null)
					p.getInventory().setBoots(ItemManager.getItem("BasicBoots").createItem(p));
				return;
			}
		}
		
		p.setPassenger(null);
		
		if(p.getBedSpawnLocation() != null)
			p.setBedSpawnLocation(null);
		p.teleport(World.getSpawn());
		p.playSound(p.getLocation(), Sound.HURT_FLESH, 1F, 1F);
	}
}
