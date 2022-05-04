package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Backpack implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.CHEST, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aBackpack");
		meta.setLore(Arrays.asList("\u00a77Use this handy backpack to store","\u00a77some of those items which are","\u00a77cluttering up your inventory!"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("SLS", "SCS", "SLS");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('L', Material.LEATHER);
		recipe.setIngredient('C', Material.CHEST);
		
		return recipe;
	}
}
