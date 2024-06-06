package bridgewars.items;

import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BasicSword implements ICustomItem {
	
    @Override
    public Rarity getRarity() {
        return Rarity.NONE;
    }
    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.GOLD_SWORD, 1);
        ItemBuilder.setUnbreakable(item, true);
        String team = ItemBuilder.getTeamName(p);
        ItemBuilder.setName(item, "&r" + team.substring(0, 1).toUpperCase() + team.substring(1) + " Sword");
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        return item;
    }
}