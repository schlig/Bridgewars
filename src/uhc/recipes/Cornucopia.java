package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Cornucopia implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.GOLDEN_CARROT, 3);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a76Cornucopia");
		meta.setLore(Arrays.asList("\u00a79Saturation I (10 Min)", "\u00a79Regeneration II (10 Sec)"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("CCC","CGC","CCC");
		recipe.setIngredient('C', Material.CARROT_ITEM);
		recipe.setIngredient('G', Material.GOLDEN_APPLE);
		
		return recipe;
	}
}
