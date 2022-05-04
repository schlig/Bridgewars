package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class SugarRush implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.SUGAR_CANE, 4);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" S ", "GDG");
		recipe.setIngredient('S', Material.SAPLING);
		recipe.setIngredient('G', Material.SEEDS);
		recipe.setIngredient('D', Material.SUGAR);
		
		return recipe;
	}
}
