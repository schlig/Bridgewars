package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class DustofLight implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.GLOWSTONE_DUST, 8);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("RRR", "RFR", "RRR");
		recipe.setIngredient('R', Material.REDSTONE);
		recipe.setIngredient('F', Material.FLINT_AND_STEEL);
		
		return recipe;
	}
}
