package bridgewars.items;

import bridgewars.utils.ICustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Error implements ICustomItem {
	
    @Override
    public Rarity getRarity() {
        return Rarity.NONE;
    }
    
    @Override
    public ItemStack createItem(Player p) {
        return new ItemStack(Material.AIR);
    }
}