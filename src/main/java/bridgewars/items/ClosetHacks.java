package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import bridgewars.Main;
import bridgewars.effects.CooldownTimer;
import bridgewars.messages.Chat;
import bridgewars.settings.enums.FriendlyFire;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class ClosetHacks implements ICustomItem, Listener {
	
	private Main plugin;
	
	private ArrayList<Player> activeEffects = new ArrayList<>();
	private HashMap<Player, Integer> rangeBonus = new HashMap<>();
	private final int duration = 15;
	
	public ClosetHacks(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
    
    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.DIODE, 1);
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
		ItemBuilder.setName(item, "Closet Hacks");
        ItemBuilder.setLore(item, Arrays.asList(
        		Chat.color("&r&7Increases attack range"),
        		Chat.color(""),
        		Chat.color("&r&7May be screwy")));
        return item;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.WHITE;
    }
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		Player user = e.getPlayer();
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR
		|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(Utils.getID(e.getItem()).equalsIgnoreCase(getClass().getSimpleName().toLowerCase())) {
				if(activeEffects.contains(user)) {
					user.sendMessage(Chat.color("&cThis item is currently on cooldown."));
					return;
				}
				activeEffects.add(user);
				new CooldownTimer(user, duration, activeEffects, Chat.color("&cYour cheats have mysteriously stopped working.")).runTaskTimer(plugin, 0, 20l);
				rangeBonus.put(user, e.getItem().getAmount() + 4);
				user.sendMessage(Chat.color("&cYou are now cheating. &7(" + rangeBonus.get(user) + " block reach)"));
				user.setItemInHand(null);
				return;
			}
		}

		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(activeEffects.contains(user)) {
				Location origin = user.getEyeLocation();
				Vector ray = Utils.raycast(origin.getYaw(), origin.getPitch());
	    		float x = (float) origin.getX() - (float) (ray.getX() * rangeBonus.get(user));
	    		float y = (float) origin.getY() - (float) (ray.getY() * rangeBonus.get(user));
	    		float z = (float) origin.getZ() + (float) (ray.getZ() * rangeBonus.get(user));
				Vector pointA = new Vector(origin.getX(), origin.getY(), origin.getZ());
				Vector pointB = new Vector(x, y, z);
				for(Player targets : Bukkit.getOnlinePlayers()) {
					if(Utils.checkAABBCollision(targets, pointA, pointB) && targets != user) {
						if(Utils.matchTeam(targets, user) && !FriendlyFire.getState().isEnabled())
							continue;
						targets.damage(Utils.getAttackDamage(user), user);
					}
				}
			}
		}
	}
}
