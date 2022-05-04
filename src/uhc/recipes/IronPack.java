package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class IronPack implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.IRON_INGOT, 10);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("GGG", "GCG", "GGG");
		recipe.setIngredient('G', Material.IRON_ORE);
		recipe.setIngredient('C', Material.COAL);
		
		return recipe;
	}
}
