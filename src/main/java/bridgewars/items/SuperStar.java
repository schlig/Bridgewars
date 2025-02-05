package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import bridgewars.Main;
import bridgewars.effects.CooldownTimer;
import bridgewars.effects.CustomMusic;
import bridgewars.effects.ParticleTrail;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class SuperStar implements ICustomItem, Listener {

	private static Main plugin;
    private static ArrayList<Player> activeEffects = new ArrayList<>();
	private static final int duration = 20;
	
	public SuperStar(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		SuperStar.plugin = plugin;
	}

	@Override
	public Rarity getRarity() {
		return Rarity.GREEN;
	}

	@Override
	public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Super Star");
        ItemBuilder.setLore(item, Arrays.asList(
                Chat.color("&r&7Become invincible for 10 seconds")));
        ItemBuilder.disableStacking(item);
		return item;
	}
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR
        || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())) {
            	Player user = e.getPlayer();
            	if(activeEffects.contains(user)) {
                	user.sendMessage(Chat.color("&cThis item is currently on cooldown."));
            		return;
            	}
            	activateEffect(user, duration, false);
            	Utils.subtractItem(user);
            }
        }
	}
	
	public static void activateEffect(Player user, int duration, boolean effects) {
    	activeEffects.add(user);
    	new CooldownTimer(user, duration, activeEffects, null).runTaskTimer(plugin, 0, 20);
    	if(effects) {
        	user.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration * 20, 0));
        	new CustomMusic(user, "invincibility", duration * 20, false).runTaskTimer(plugin, 0, 1L);
    		new ParticleTrail(user, EnumParticle.SPELL_MOB, 
    				0, 1, 0, 
    				0, 0, 0, 
    				1, 10, duration * 20, false).runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 0L, 1L);
    	}
	}
	
	public static boolean playerIsInvincible(Player p) {
		return activeEffects.contains(p) ? true : false;
	}
	
	public static void setInvincibility(Player p, boolean state) {
		if(state || activeEffects.contains(p))
			activeEffects.remove(p);
		else if(!state)
			activeEffects.add(p);

	}
}
