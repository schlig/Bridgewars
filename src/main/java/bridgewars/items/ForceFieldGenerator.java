package bridgewars.items;

import java.util.Arrays;

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
import bridgewars.effects.RepelField;
import bridgewars.game.Leaderboards;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class ForceFieldGenerator implements ICustomItem, Listener {
	
    private Main plugin;
    
    private final int radius = 5;
    private final int power = 1;
    private final int duration = 5; //in seconds (* 20 for total ticks)

    public ForceFieldGenerator (Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.WHITE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.DIAMOND, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Force Field Generator");
        ItemBuilder.setLore(item, Arrays.asList(
        		Chat.color("&r&7Repels all entities in a 5"),
        		Chat.color("&r&7block radius for 5 seconds"),
        		Chat.color("&r&7Stackable")));
        return item;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack item = e.getItem();
            if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())) {
                Player user = e.getPlayer();

                Utils.subtractItem(user);
                new RepelField(user, radius, power, duration * 20, plugin).runTaskTimer(plugin, 0, 1);
                user.playSound(user.getLocation(), Sound.FIREWORK_LAUNCH, 1F, .5F);
    		    Leaderboards.addPoint(e.getPlayer(), "items");
            }
        }
    }
}