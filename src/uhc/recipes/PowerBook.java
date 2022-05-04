package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class PowerBook implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta enchants = (EnchantmentStorageMeta) item.getItemMeta();
		enchants.addStoredEnchant(Enchantment.ARROW_DAMAGE, 1, false);
		item.setItemMeta(enchants);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("F  ", " PP", " PS");
		recipe.setIngredient('F', Material.FLINT);
		recipe.setIngredient('P', Material.PAPER);
		recipe.setIngredient('S', Material.BONE);
		
		return recipe;
	}
}
