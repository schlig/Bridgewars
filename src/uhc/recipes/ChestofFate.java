package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ChestofFate implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.CHEST, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aChest of Fate");
		meta.setLore(Arrays.asList("\u00a77Open it and see what you can","\u00a77find."));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("WWW", "WSW", "WWW");
		recipe.setIngredient('S', Material.SKULL_ITEM, 3);
		recipe.setIngredient('W', Material.WOOD);
		
		return recipe;
	}
}
