package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class TabletsofDestiny implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a76Tablets of Destiny");
		EnchantmentStorageMeta enchants = (EnchantmentStorageMeta) meta;
		enchants.addStoredEnchant(Enchantment.DAMAGE_ALL, 3, false);
		enchants.addStoredEnchant(Enchantment.ARROW_DAMAGE, 3, false);
		enchants.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
		enchants.addStoredEnchant(Enchantment.FIRE_ASPECT, 1, false);
		item.setItemMeta(enchants);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" M ", "SbB", "EEE");
		recipe.setIngredient('M', Material.MAGMA_CREAM);
		recipe.setIngredient('S', Material.DIAMOND_SWORD);
		recipe.setIngredient('b', Material.BOOK_AND_QUILL);
		recipe.setIngredient('B', Material.BOW);
		recipe.setIngredient('E', Material.EXP_BOTTLE);
		
		return recipe;
	}
}
