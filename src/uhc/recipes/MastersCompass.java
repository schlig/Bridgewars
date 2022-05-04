package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class MastersCompass implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.COMPASS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7cMaster's Compass");
		meta.setLore(Arrays.asList("\u00a77A powerful compass forged by","\u00a77ancient Vampires. Points a fiery","\u00a77arrow towards the nearest player","\u00a77at or above ground level!"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("RTR", "RCR", "RRR");
		recipe.setIngredient('T', Material.REDSTONE_TORCH_ON);
		recipe.setIngredient('C', Material.COMPASS);
		recipe.setIngredient('R', Material.REDSTONE);
		
		return recipe;
	}
}
