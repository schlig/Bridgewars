package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class FlamingArtifact implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.BLAZE_ROD, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("GLG", "GFG", "GLG");
		recipe.setIngredient('G', Material.STAINED_GLASS, 1);
		recipe.setIngredient('L', Material.FIREWORK);
		recipe.setIngredient('F', Material.LAVA_BUCKET);
		
		return recipe;
	}
}
