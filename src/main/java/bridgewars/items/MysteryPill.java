package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import bridgewars.game.Leaderboards;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class MysteryPill implements ICustomItem, Listener{
	
	private final static int halfHearts = 6;
	
	private final static int witherDuration = 10 * 20;
	private final static int nauseaDuration = 15 * 20;
	private final static int absorptionDuration = 300 * 20;
	private final static int speedDuration = 15 * 20;
	
	private final static int witherLevel = 5;
	private final static int nauseaLevel = 1;
	private final static int absorptionLevel = 5;
	private final static int speedLevel = 1;
	
	private final static float explosionPower = 20;
	
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
                Chat.color("&r&7Taking random pills"),
                Chat.color("&r&7may be a bad idea")));
        ItemBuilder.disableStacking(item);
        return item;
    }
    
    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR
        || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())
            && GameState.isState(GameState.ACTIVE)) {
            	Player user = e.getPlayer();
            	activateEffect(user);
            	Utils.subtractItem(user);
            }
        }
    }

    public static void activateEffect(Player user) {
    	double val = Math.random();
    	
    	if(val < 0.005) {
    		World world = user.getWorld();
    		user.sendMessage(Chat.color("&lYou swallowed a bomb."));
    		world.createExplosion(user.getLocation(), explosionPower);
    	}
    	else if(val < 0.20) {
    		user.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, witherDuration, witherLevel - 1), true);
    		user.sendMessage(Chat.color("&lYou took a cyanide pill."));
    	}
    	else if(val < 0.50) {
    		user.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, speedDuration, speedLevel - 1), true);
    		user.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, nauseaDuration, nauseaLevel - 1), true);
    		user.sendMessage(Chat.color("&lYou start freaking out."));
    	}
    	else if(val < 0.85) {
    		if(user.getHealth() + halfHearts > user.getMaxHealth())
    			user.setHealth(user.getMaxHealth());
    		else
    			user.setHealth(user.getHealth() + halfHearts);
    		user.sendMessage(Chat.color("&lYou feel a little better. " + "&r&8(Healed " + halfHearts / 2 + " hearts)"));
    	}
    	else if(val < 0.98){
    		if(user.getHealth() + (halfHearts * 2) > user.getMaxHealth())
    			user.setHealth(user.getMaxHealth());
    		else
    			user.setHealth(user.getHealth() + (halfHearts * 2));
    		user.sendMessage(Chat.color("&lYou feel a lot better. " + "&r&8(Healed " + halfHearts + " hearts)"));
    	}
    	else {
    		user.setHealth(user.getMaxHealth());
    		user.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, absorptionDuration, absorptionLevel - 1), true);
    		user.sendMessage(Chat.color("&lYou feel unstoppable! &r&8(Fully healed + 10 absorption hearts)"));
    	}
    	
    	user.playSound(user.getLocation(), Sound.EAT, 1F, 1F);
	    Leaderboards.addPoint(user, "items");
    }
}
