package bridgewars.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;

public class ParkourQuitter implements ICustomItem {
	
	@Override
	public Rarity getRarity() {
		return Rarity.NONE;
	}

	@Override
	public ItemStack createItem(Player p) {
		ItemStack ParkourTeleporter = new ItemStack(Material.BARRIER, 1);
		ItemBuilder.setName(ParkourTeleporter, Message.chat("&6Quit Parkour"));
		return ParkourTeleporter;
	}
}
