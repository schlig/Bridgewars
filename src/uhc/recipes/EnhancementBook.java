package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class EnhancementBook implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aEnhancement Book");
		meta.setLore(Arrays.asList("\u00a77Acts like a level 30","\u00a77enchantment when used with","\u00a77any item in an anvil!","","\u00a77Does not work on Pickaxes!"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("EEE", "PTA", "BBB");
		recipe.setIngredient('E', Material.EXP_BOTTLE);
		recipe.setIngredient('P', Material.IRON_PICKAXE);
		recipe.setIngredient('T', Material.ENCHANTMENT_TABLE);
		recipe.setIngredient('A', Material.IRON_AXE);
		recipe.setIngredient('B', Material.BOOKSHELF);
		
		return recipe;
	}
}
