package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.effects.CooldownTimer;
import bridgewars.game.Leaderboards;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class HomeRunBat implements ICustomItem, Listener {
	
	private Main plugin;
	private ArrayList<Player> cooldownList = new ArrayList<>();
	private final int duration = 1;
	
    public HomeRunBat(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.RED;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.WOOD_SWORD, 1);
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Home Run Bat");
        ItemBuilder.setLore(item, Arrays.asList(Chat.color("&r&7Deals massive knockback"),
                Chat.color("&r&7Only has 3 uses")));
        return item;
    }
    
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
    	if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
    		Player user = (Player) e.getDamager();
    		if(cooldownList.contains(user))
    			e.setCancelled(true);
    	}
    }

    @EventHandler
    public void onUse(PlayerItemDamageEvent e) {
	    if(Utils.getID(e.getItem()).equals(getClass().getSimpleName().toLowerCase())) {
	    	Player user = e.getPlayer();
	    	if(cooldownList.contains(user)) {
	    		user.sendMessage(Chat.color("&cThis item is currently on cooldown."));
	    		return;
	    	}
	    	cooldownList.add(user);
	    	new CooldownTimer(user, duration, cooldownList, null).runTaskTimer(plugin, 0, 20L);
		    user.playSound(e.getPlayer().getLocation(), Sound.ANVIL_LAND, 1F, 1.8F);
		    e.setDamage(20);
		    Leaderboards.addPoint(e.getPlayer(), "items");
	    }
    }
}