package bridgewars.items;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.CustomScoreboard;
import bridgewars.game.GameState;
import bridgewars.utils.Utils;

public class FireballLaunch extends CustomItems implements Listener {
	
	private CustomScoreboard cs = new CustomScoreboard();
	
	public FireballLaunch(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onThrow(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR
		|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(matchItem(e.getItem(), "fb")) {
				if(GameState.isState(GameState.ACTIVE))
					if(cs.getTime(e.getPlayer()) == 0) {
						e.setCancelled(true);
						return;
					}
				if(e.getAction() == Action.RIGHT_CLICK_AIR)
					e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.GHAST_FIREBALL, 0.5F, 1F);
				Fireball fireball = e.getPlayer().launchProjectile(Fireball.class);
				fireball.setVelocity(fireball.getVelocity().multiply(4));
				fireball.setYield(4);
				fireball.setIsIncendiary(false);
				fireball.setShooter(e.getPlayer());
				if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
					if(e.getPlayer().getItemInHand().getAmount() == 1)
						e.getPlayer().setItemInHand(new ItemStack(Material.AIR, 1));
					else
						e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
				}	
			}
		}
	}
	
	@EventHandler
	public void onPlaceAttempt(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.FIRE)
			if(matchItem(e.getItemInHand(), "fb"))
				e.setCancelled(true);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player
		&& e.getDamager() instanceof Fireball) {
			Fireball fb = (Fireball) e.getDamager();
			Player p = (Player) e.getEntity();
			if(cs.getTeam(p) == cs.getTeam((Player) fb.getShooter()) && p != (Player) fb.getShooter())
				e.setCancelled(true);
			else {
				Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> p.setVelocity(p.getVelocity().multiply(3).setY(2)), 1L);
				e.setDamage(8);
			}
		}
	}
	
	@EventHandler
	public void onBurn(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player)
			if(e.getCause() == DamageCause.FIRE_TICK
			|| e.getCause() == DamageCause.FIRE) {
				e.setCancelled(true);
				Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> ((Player)e.getEntity()).setFireTicks(0), 1L);
			}
	}
	
	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		if(GameState.isState(GameState.ACTIVE)) {
			List<Block> blocks = e.blockList();
			for(Block block : blocks)
				if(Utils.isOutOfBounds(block.getLocation(), 22, 24, 22))
					blocks.remove(block);
		}
	}
}
