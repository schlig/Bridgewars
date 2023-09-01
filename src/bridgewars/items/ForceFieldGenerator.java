package bridgewars.items;

import bridgewars.Main;
import bridgewars.effects.RepelField;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;
import bridgewars.utils.ICustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ForceFieldGenerator implements ICustomItem, Listener {
    private Main plugin;

    public ForceFieldGenerator (Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(item.getType() == Material.DIAMOND) {
                new RepelField(p, 5, 1, 100, plugin).runTaskTimer(plugin,0,1);
            }
        }
    }
    @Override
    public Rarity getRarity() {
        return Rarity.GREEN;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack out = new ItemStack(Material.DIAMOND, 1);
        ItemBuilder.setName(out, "&gForce Field Generator");
        ItemBuilder.setLore(out, Arrays.asList(Message.chat("&r&7A repels enemies in a 5 block radius")));
        return out;
    }
}