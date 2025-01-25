package bridgewars.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;

public class ParkourResetter implements ICustomItem {
	
	@Override
	public Rarity getRarity() {
		return Rarity.NONE;
	}

	@Override
	public ItemStack createItem(Player p) {
		ItemStack item = new ItemStack(Material.REDSTONE_TORCH_ON, 1);
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
		ItemBuilder.setName(item, Chat.color("&6Reset Parkour"));
		return item;
	}
}
