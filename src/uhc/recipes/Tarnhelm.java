package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Tarnhelm implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_HELMET, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aTarnhelm");
		meta.setLore(Arrays.asList("\u00a77Engineering Craft 3."));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 1);
		item.addUnsafeEnchantment(Enchantment.WATER_WORKER, 3);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("DID", "DRD");
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('R', Material.REDSTONE_BLOCK);
		
		return recipe;
	}
}
