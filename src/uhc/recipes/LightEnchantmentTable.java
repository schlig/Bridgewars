package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class LightEnchantmentTable implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.ENCHANTMENT_TABLE, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" B ", "ODO", "OEO");
		recipe.setIngredient('B', Material.BOOKSHELF);
		recipe.setIngredient('O', Material.OBSIDIAN);
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('E', Material.EXP_BOTTLE);
		
		return recipe;
	}
}
