package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class Obsidian implements Listener {
	public ShapelessRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.OBSIDIAN, 1);
		ShapelessRecipe recipe = new ShapelessRecipe(item);
		recipe.addIngredient(Material.WATER_BUCKET);
		recipe.addIngredient(Material.LAVA_BUCKET);
		
		return recipe;
	}
}
