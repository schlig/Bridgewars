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
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class ForceFieldGenerator implements ICustomItem, Listener {
    private Main plugin;

    public ForceFieldGenerator (Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack item = e.getItem();
            if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())) {
                Player user = e.getPlayer();
                
                new RepelField(user, 5, 1, 100, plugin).runTaskTimer(plugin,0,1);
                Utils.subtractItem(user);
                user.playSound(user.getLocation(), Sound.FIREWORK_LAUNCH, 1F, .5F);
            }
        }
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
        		Chat.color("&r&7block radius for 5 seconds")));
        return item;
    }
}