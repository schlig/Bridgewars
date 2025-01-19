package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;

public class MysteryPill implements ICustomItem, Listener{
	
	private final int halfHearts = 6;
	private final int duration = 8
							   * 20;
	private final int level = 1;
	
	private final float explosionPower = 15;
	
    public MysteryPill(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public Rarity getRarity() {
        return Rarity.WHITE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.GOLD_NUGGET, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Mystery Pill");
        ItemBuilder.setLore(item, Arrays.asList(
                Message.chat("&r&750% chance to heal " + (halfHearts / 2) +" hearts"),
                Message.chat("&r&750% chance to die instantly")));
        return item;
    }
    
    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR
        || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())
            && GameState.isState(GameState.ACTIVE)) {
            	Player p = e.getPlayer();
            	double val = Math.random();
            	
            	if(val < 0.01) {
            		World world = p.getWorld();
            		world.createExplosion(p.getLocation(), explosionPower);
            		p.sendMessage(Message.chat("&lHOLY SHIT"));
            	}
            	else if(val < 0.20) {
            		p.damage(69420, p);
            		p.sendMessage(Message.chat("&lYou took a cyanide pill."));
            	}
            	else if(val < 0.50) {
            		p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duration, level - 1), true);
            		p.sendMessage(Message.chat("&lYou feel sick."));
            	}
            	else if(val < 0.75) {
            		if(p.getHealth() + halfHearts > p.getMaxHealth())
            			p.setHealth(p.getMaxHealth());
            		else
            			p.setHealth(p.getHealth() + halfHearts);
            		p.sendMessage(Message.chat("&lYou feel a little better."));
            	}
            	else {
            		if(p.getHealth() + (halfHearts * 2) > p.getMaxHealth())
            			p.setHealth(p.getMaxHealth());
            		else
            			p.setHealth(p.getHealth() + (halfHearts * 2));
            		p.sendMessage(Message.chat("&lYou feel a lot better."));
            	}
            	Utils.subtractItem(p);
            }
        }
    }

}
