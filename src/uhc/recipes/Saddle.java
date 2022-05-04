package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Saddle implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.SADDLE, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("LLL", "SLS", "I I");
		recipe.setIngredient('L', Material.LEATHER);
		recipe.setIngredient('S', Material.STRING);
		recipe.setIngredient('I', Material.IRON_INGOT);
		
		return recipe;
	}
}
