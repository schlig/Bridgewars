package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import bridgewars.Main;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

@SuppressWarnings("unused")
public class DebugItem implements ICustomItem, Listener {
	
	private final int range = 250;
	
    public DebugItem(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.NONE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.IRON_INGOT, 1);
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Schlog Hak");
        ItemBuilder.setLore(item, Arrays.asList(
                Chat.color("&r&7debug item")));
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
                if(!user.isOp()) {
                	user.sendMessage("Oopsie Poopsie!!! This item has been tampered with. You must be Schlog in order for it to do anything!!!!!!!!!");
                	return;
                }
                
                Vector a = new Vector(user.getEyeLocation().getX(), user.getEyeLocation().getY(), user.getEyeLocation().getZ());
                Vector b = Utils.raycast(user.getEyeLocation().getYaw(), user.getEyeLocation().getPitch());
                double x = a.getX() - (b.getX() * 10);
                double y = a.getY() - (b.getY() * 10);
                double z = a.getZ() + (b.getZ() * 10);
                Vector c = new Vector(x, y, z);
                for(Player player : Bukkit.getOnlinePlayers()) {
                	if(user == player)
                		continue;
                	if(Utils.checkAABBCollision(player, a, c))
                		user.sendMessage("yep");
                	else user.sendMessage("nope");
                }
            }
        }
    }
}
