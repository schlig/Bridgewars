package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class LightApple implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" G ", "GAG", " G ");
		recipe.setIngredient('G', Material.GOLD_INGOT);
		recipe.setIngredient('A', Material.APPLE);
		
		return recipe;
	}
}
