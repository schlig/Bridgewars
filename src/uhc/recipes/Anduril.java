package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Anduril implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aAndúril");
		meta.setLore(Arrays.asList("\u00a79Resistance I (While Holding)","\u00a79Speed I (While Holding)","\u00a79Justice"));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("FIF", "FIF", "FBF");
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('I', Material.IRON_BLOCK);
		recipe.setIngredient('B', Material.BLAZE_ROD);
		
		return recipe;
	}
}
