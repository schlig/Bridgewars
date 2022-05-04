package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class DeliciousMeal implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.COOKED_BEEF, 10);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("BBB", "BCB", "BBB");
		recipe.setIngredient('B', Material.RAW_CHICKEN);
		recipe.setIngredient('B', Material.RAW_BEEF);
		recipe.setIngredient('C', Material.COAL);
		
		return recipe;
	}
}
