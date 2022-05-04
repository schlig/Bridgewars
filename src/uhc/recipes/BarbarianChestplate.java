package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class BarbarianChestplate implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aBarbarian Chestplate");
		meta.setLore(Arrays.asList("\u00a79Strength I","\u00a79Resistance I","\u00a79Rage"));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("BDB", "IPI");
		recipe.setIngredient('B', Material.BLAZE_ROD);
		recipe.setIngredient('D', Material.DIAMOND_CHESTPLATE);
		recipe.setIngredient('I', Material.IRON_BLOCK);
		recipe.setIngredient('P', Material.POTION, 8265);
		
		return recipe;
	}
}
