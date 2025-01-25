package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;

public class EnhancementBook implements ICustomItem, Listener {
	
	public EnhancementBook(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@Override
	public Rarity getRarity() {
		return Rarity.GREEN;
	}

	@Override
	public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.BOOK, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Enhancement Book");
        ItemBuilder.setLore(item, Arrays.asList(
                Chat.color("&r&71/4 chance to upgrade one"),
                Chat.color("&r&7of your items")));
        return item;
	}
}
