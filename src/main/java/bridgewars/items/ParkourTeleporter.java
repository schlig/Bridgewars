package bridgewars.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;

public class ParkourTeleporter implements ICustomItem {
	
	@Override
	public Rarity getRarity() {
		return Rarity.NONE;
	}

	@Override
	public ItemStack createItem(Player p) {
		ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
		ItemBuilder.setName(item, Message.chat("&6Teleport to Last Checkpoint"));
		return item;
	}
}
