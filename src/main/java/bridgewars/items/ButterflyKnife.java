package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.Leaderboards;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class ButterflyKnife implements ICustomItem, Listener {
	
    public ButterflyKnife(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.WHITE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.GOLD_SWORD, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Butterfly Knife");
        ItemBuilder.setLore(item, Arrays.asList(
        		Chat.color("&r&7Deals no damage, but ignores"),
                Chat.color("&r&7immunity frames")));
        ItemBuilder.hideFlags(item);
        return item;
    }
    
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
    	if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player user = (Player) e.getDamager();
			Player target = (Player) e.getEntity();
			ItemStack weapon = user.getItemInHand();
			if(Utils.getID(weapon).equalsIgnoreCase(getClass().getSimpleName().toLowerCase())) {
				Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> target.setNoDamageTicks(0), 1L);
				e.setDamage(0);
			}
    	}
    }

    @EventHandler
    public void onUse(PlayerItemDamageEvent e) {
        if(Utils.getID(e.getItem()).equals(getClass().getSimpleName().toLowerCase())) {
            e.setDamage(3);
		    Leaderboards.addPoint(e.getPlayer(), "items");
        }
    }
}