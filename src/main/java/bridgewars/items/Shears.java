package bridgewars.items;

import bridgewars.settings.enums.GigaDrill;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Shears implements ICustomItem {
    @Override
    public Rarity getRarity() {
        return Rarity.NONE;
    }
    
    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.SHEARS, 1);
        if(GigaDrill.getState().isEnabled())
        	item.addEnchantment(Enchantment.DIG_SPEED, 5);
        else
        	item.addEnchantment(Enchantment.DIG_SPEED, 3);
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
		ItemBuilder.setName(item, "Shears");
        ItemBuilder.setUnbreakable(item, true);
        return item;
    }
    
    public ItemStack createItem() {
    	return createItem(null);
    }
}