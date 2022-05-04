package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class EssenceofYggdrasil implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.EXP_BOTTLE, 32);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a76Essence of Yggdrasil");
		meta.setLore(Arrays.asList("\u00a77Gives 30 levels if solo, or 15","\u00a77levels to you and 8 to your","\u00a77teammates!"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("LEL", "GBG", "LRL");
		recipe.setIngredient('L', Material.LEAVES);
		recipe.setIngredient('E', Material.ENCHANTMENT_TABLE);
		recipe.setIngredient('G', Material.GLOWSTONE);
		recipe.setIngredient('B', Material.GLASS_BOTTLE);
		recipe.setIngredient('R', Material.REDSTONE_BLOCK);
		
		return recipe;
	}
}
