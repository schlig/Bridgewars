package bridgewars.items;

import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Axe implements ICustomItem {
    @Override
    public Rarity getRarity() {
        return Rarity.NONE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.STONE_AXE, 1);
        item.addEnchantment(Enchantment.DIG_SPEED, 5);
        ItemBuilder.setUnbreakable(item,true);
        String team = ItemBuilder.getTeamName(p);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "&r" + team.substring(0, 1).toUpperCase() + team.substring(1) + " Axe");
        return item;
    }
}