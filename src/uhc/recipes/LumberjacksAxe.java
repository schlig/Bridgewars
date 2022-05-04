package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class LumberjacksAxe implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.IRON_AXE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aLumberjack's Axe");
		meta.setLore(Arrays.asList("\u00a77Can drop an entire tree with 1","\u00a77strike!"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("IIF", "IS ", " S ");
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('F', Material.FLINT);
		recipe.setIngredient('S', Material.STICK);
		
		return recipe;
	}
}
