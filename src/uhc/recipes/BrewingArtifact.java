package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class BrewingArtifact implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.NETHER_STALK, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" S ", "SES", " S ");
		recipe.setIngredient('S', Material.SEEDS);
		recipe.setIngredient('E', Material.FERMENTED_SPIDER_EYE);
		
		return recipe;
	}
}
