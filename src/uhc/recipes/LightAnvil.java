package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class LightAnvil implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.ANVIL, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("III", " B ", "III");
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('B', Material.IRON_BLOCK);
		
		return recipe;
	}
}
