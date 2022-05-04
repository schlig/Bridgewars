package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class ExperienceBottle implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.EXP_BOTTLE, 8);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" R ", "RBR", " R ");
		recipe.setIngredient('R', Material.REDSTONE_BLOCK);
		recipe.setIngredient('B', Material.GLASS_BOTTLE);
		
		return recipe;
	}
}
