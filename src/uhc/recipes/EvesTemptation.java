package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class EvesTemptation implements Listener {
	@SuppressWarnings("deprecation")
	public ShapelessRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.APPLE, 2);
		ShapelessRecipe recipe = new ShapelessRecipe(item);
		recipe.addIngredient(Material.INK_SACK, 15);
		recipe.addIngredient(Material.APPLE);
		
		return recipe;
	}
}
