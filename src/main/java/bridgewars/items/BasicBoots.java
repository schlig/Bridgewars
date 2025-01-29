package bridgewars.items;

import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BasicBoots implements ICustomItem {

    @Override
    public Rarity getRarity() {
        return Rarity.NONE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        String team = ItemBuilder.getTeamName(p);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "&r" + team.substring(0, 1).toUpperCase() + team.substring(1) + " Boots");
        ItemBuilder.setUnbreakable(item, true);
        ItemBuilder.setLeatherColor(p, item, team);
        return item;
    }
}