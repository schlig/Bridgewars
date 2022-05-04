package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ApprenticeHelmet implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.IRON_HELMET, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aApprentice Helmet");
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 1);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("III", "IRI");
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('R', Material.REDSTONE_TORCH_ON);
		
		return recipe;
	}
}
