package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.CustomScoreboard;
import bridgewars.game.GameState;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;

public class Fireball implements ICustomItem, Listener {
	
    private CustomScoreboard cs = new CustomScoreboard();

    public Fireball(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onThrow(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR
                || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(Utils.compareItemName(e.getItem(), "&aFireball")) {
                if(GameState.isState(GameState.ACTIVE))
                    if(cs.getTime(e.getPlayer()) == 0) {
                        e.setCancelled(true);
                        return;
                    }
                if(e.getAction() == Action.RIGHT_CLICK_AIR)
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.GHAST_FIREBALL, 0.5F, 1F);
                org.bukkit.entity.Fireball fireball = e.getPlayer().launchProjectile(org.bukkit.entity.Fireball.class);
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
            if(Utils.compareItemName(e.getItemInHand(), "&aFireball"))
                e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player
                && e.getDamager() instanceof org.bukkit.entity.Fireball) {
            org.bukkit.entity.Fireball fb = (org.bukkit.entity.Fireball) e.getDamager();
            Player p = (Player) e.getEntity();
            if(cs.getTeam(p).equals(cs.getTeam((Player) fb.getShooter())) && p != (Player) fb.getShooter())
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
            if(e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK
                    || e.getCause() == EntityDamageEvent.DamageCause.FIRE) {
                e.setCancelled(true);
                Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> ((Player)e.getEntity()).setFireTicks(0), 1L);
            }
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.GREEN;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack out = new ItemStack(Material.FIREBALL, 1);
        ItemBuilder.setName(out, "&aFireball");
        ItemBuilder.setLore(out, Arrays.asList(Message.chat("&r&7Throws an exploding fireball"),
                Message.chat("&r&7that deals heavy knockback")));
        return out;
    }
}