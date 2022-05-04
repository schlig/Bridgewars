package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Exodus implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_HELMET, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a76Exodus");
		meta.setLore(Arrays.asList("\u00a79Aeon","","\u00a77Regenerates a small portion","\u00a77of health when you hit an","\u00a77enemy! (2 second cooldown)"));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.DURABILITY, 3);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("DDD", "DHD", "ECE");
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('H', Material.SKULL_ITEM, 3);
		recipe.setIngredient('E', Material.EMERALD);
		recipe.setIngredient('C', Material.GOLDEN_CARROT);
		
		return recipe;
	}
}
