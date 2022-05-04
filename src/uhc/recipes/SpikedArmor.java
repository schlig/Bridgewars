package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class SpikedArmor implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aSpiked Armor");
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		item.addEnchantment(Enchantment.THORNS, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" L ", " C ", " S ");
		recipe.setIngredient('L', Material.WATER_LILY);
		recipe.setIngredient('C', Material.CACTUS);
		recipe.setIngredient('S', Material.LEATHER_CHESTPLATE);
		
		return recipe;
	}
}
