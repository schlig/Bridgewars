package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class WarlockPants implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aWarlock Pants");
		meta.setLore(Arrays.asList("\u00a76Reduces incoming damage","\u00a76based on missing health","\u00a77Reduces damage by for","\u00a77each missing heart, up to","\u00a7725%"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("III", "IDI", "B B");
		recipe.setIngredient('I', Material.IRON_FENCE);
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('B', Material.BLAZE_POWDER);
		
		return recipe;
	}
}
