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
        ItemStack axe = new ItemStack(Material.STONE_AXE, 1);
        axe.addEnchantment(Enchantment.DIG_SPEED, 5);
        ItemBuilder.setUnbreakable(axe,true);
        String team = ItemBuilder.getTeamName(p);
        ItemBuilder.setName(axe, "&r" + team.substring(0, 1).toUpperCase() + team.substring(1) + " Axe");
        return axe;
    }
}