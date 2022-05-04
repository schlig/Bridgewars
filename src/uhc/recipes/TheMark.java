package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class TheMark implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.SNOW_BALL, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7bThe Mark");
		meta.setLore(Arrays.asList("\u00a76Hit an enemy with this","\u00a76snowball to apply The Mark","\u00a76to them. Deal +5% damage","\u00a76against this player for","\u00a76each Mark on them.","\u00a77Max effect stack: 5","\u00a77Cooldown: 5s"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("SBS", "DCD", "SBS");
		recipe.setIngredient('S', Material.SNOW_BLOCK);
		recipe.setIngredient('B', Material.BLAZE_POWDER);
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('C', Material.WATCH);
		
		return recipe;
	}
}
