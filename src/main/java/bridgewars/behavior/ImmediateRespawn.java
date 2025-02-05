package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import bridgewars.Main;
import bridgewars.effects.PlotArmor;
import bridgewars.game.CSManager;
import bridgewars.game.Game;
import bridgewars.game.GameState;
import bridgewars.game.Leaderboards;
import bridgewars.items.SadTear;
import bridgewars.items.SuperStar;
import bridgewars.settings.enums.Blocks;
import bridgewars.settings.enums.Bows;
import bridgewars.settings.enums.DigWars;
import bridgewars.settings.enums.Shears;
import bridgewars.settings.enums.Swords;
import bridgewars.settings.enums.UnlockedInventory;
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
		
		if(SuperStar.playerIsInvincible(p)) {
			p.setHealth(p.getMaxHealth());
			SadTear.activateEffect(p, 30);
			return;
		}
		
		final Vector v = new Vector();
		p.setHealth(p.getMaxHealth());
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> p.setVelocity(v), 1L);
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> p.setFireTicks(0), 1L);
		
		for(PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
		p.setLevel(0);
		
		if(GameState.isState(GameState.ACTIVE)) {
			if(CSManager.hasTeam(p)) {
				CSManager.resetTime(p);
				Game.placeSpawns();
				Game.spawnPlayer(p);
				Leaderboards.addPoint(p, "lifetimeDeaths");
				p.playSound(p.getLocation(), Sound.HURT_FLESH, 1F, 1F);
				PlayerInventory inv = p.getInventory();
				p.setNoDamageTicks(60);
				
				if(UnlockedInventory.getState().isEnabled())
					Game.grantItems(p, true);
				
				else {
					//base equipment
					if(!inv.contains(Material.GOLD_SWORD) && Swords.getState().isEnabled())
						inv.addItem(ItemManager.getItem("BasicSword").createItem(p));
					if(!inv.contains(Material.SHEARS) && Shears.getState().isEnabled())
						inv.addItem(ItemManager.getItem("Shears").createItem(p));
					if(!inv.contains(Material.WOOL) && Blocks.getState().isEnabled())
						inv.addItem(ItemManager.getItem("WoolBlocks").createItem(p));
					
					//bows
					if(!inv.contains(Material.BOW) && Bows.getState().isEnabled())
						inv.addItem(ItemManager.getItem("Bow").createItem(p));
					if(!inv.contains(Material.ARROW) && Bows.getState().isEnabled())
						inv.addItem(new ItemStack(Material.ARROW, 1));
					
					//digwars stuff
					if(DigWars.getState().isEnabled()) {
						inv.remove(Material.WOOD);
						inv.addItem(new ItemStack(Material.WOOD, 64));
					}
					if(!inv.contains(Material.STONE_AXE) && DigWars.getState().isEnabled())
						inv.addItem(ItemManager.getItem("Axe").createItem(p));
					
					//give armor back if it's missing
					if(inv.getHelmet() == null)
						inv.setHelmet(ItemManager.getItem("BasicHelmet").createItem(p));
					if(inv.getChestplate() == null)
						inv.setChestplate(ItemManager.getItem("BasicChestplate").createItem(p));
					if(inv.getLeggings() == null)
						inv.setLeggings(ItemManager.getItem("BasicLeggings").createItem(p));
					if(inv.getBoots() == null)
						inv.setBoots(ItemManager.getItem("BasicBoots").createItem(p));
					return;
				}
			}
		}
		
		p.setPassenger(null);
		
		if(p.getBedSpawnLocation() != null)
			p.setBedSpawnLocation(null);
		p.teleport(World.getSpawn());
		p.playSound(p.getLocation(), Sound.HURT_FLESH, 1F, 1F);
	}
}
