package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class HealingFruit implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.MELON, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("BSB", "SAS", "BSB");
		recipe.setIngredient('B', Material.INK_SACK, 15);
		recipe.setIngredient('S', Material.SEEDS);
		recipe.setIngredient('A', Material.APPLE);
		
		return recipe;
	}
}
