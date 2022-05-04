package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class BookofThoth implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta enchants = (EnchantmentStorageMeta) item.getItemMeta();
		enchants.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, false);
		enchants.addStoredEnchant(Enchantment.DAMAGE_ALL, 2, false);
		enchants.addStoredEnchant(Enchantment.ARROW_DAMAGE, 2, false);
		enchants.addStoredEnchant(Enchantment.ARROW_KNOCKBACK, 1, false);
		enchants.addStoredEnchant(Enchantment.FIRE_ASPECT, 1, false);
		item.setItemMeta(enchants);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("E  ", " PP", " PF");
		recipe.setIngredient('E', Material.EYE_OF_ENDER);
		recipe.setIngredient('P', Material.PAPER);
		recipe.setIngredient('F', Material.FIREBALL);
		
		return recipe;
	}
}
