package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class ArrowEconomy implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.ARROW, 20);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("FFF", "SSS", "EEE");
		recipe.setIngredient('F', Material.FLINT);
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('E', Material.FEATHER);
		
		return recipe;
	}
}
