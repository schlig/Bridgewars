package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.effects.CooldownTimer;
import bridgewars.effects.MetalBoxEffect;
import bridgewars.game.Leaderboards;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class MetalBox implements ICustomItem, Listener {
	
	private final UUID id = UUID.fromString("583bc9f2-2bf5-4205-88bf-84593306ced8");
	private final String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0d"
			+ "HA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjdlMjlhNThjMDY"
			+ "wNTExZmYwMzUwMjQxYzljODY0OTY5NDdkOGY4NGJmMzJmMmRmMzVlYmUzNzQyZ"
			+ "jBlMjAyOSJ9fX0=";
								   
	private Main plugin;
	private ArrayList<Player> cooldownList = new ArrayList<>();
	private final int duration = 15;
	
    public MetalBox(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.WHITE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
        item.setDurability((short) 3);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Metal Box");
        ItemBuilder.setSkullTexture(item, id, texture);
        ItemBuilder.setLore(item, Arrays.asList(
                Chat.color("&r&7Temporary knockback immunity")));
        return item;
    }
    
    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR
        || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())) {
            	Player user = e.getPlayer();
            	if(cooldownList.contains(user)) {
            		user.sendMessage(Chat.color("&cThis item is currently on cooldown."));
            		return;
            	}
            	cooldownList.add(user);
            	new CooldownTimer(user, duration, cooldownList, null).runTaskTimer(plugin, 0, 20);
            	new MetalBoxEffect(user, duration).runTaskTimer(plugin, 0, 20);
            	user.playSound(user.getLocation(), Sound.ANVIL_LAND, 1F, .8F);
            	Utils.subtractItem(user);
    		    Leaderboards.addPoint(e.getPlayer(), "items");
            }
        }
    }
}
