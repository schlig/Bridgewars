package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class LeatherEconomy implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.LEATHER, 8);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("SLS", "SLS", "SLS");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('L', Material.LEATHER);
		
		return recipe;
	}
}
