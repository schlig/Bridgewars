package bridgewars.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class GigaShears implements ICustomItem, Listener {
	
	public GigaShears(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
    @Override
    public Rarity getRarity() {
        return Rarity.GREEN;
    }
    
    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.SHEARS, 1);
        item.addEnchantment(Enchantment.DIG_SPEED, 5);
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
		ItemBuilder.setName(item, "Giga Shears");
        return item;
    }
    
    public ItemStack createItem() {
    	return createItem(null);
    }

    @EventHandler
    public void onHit(PlayerItemDamageEvent e) {
        if(Utils.getID(e.getItem()).equals(getClass().getSimpleName().toLowerCase()))
            e.setDamage(7);
    }
}