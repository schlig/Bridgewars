package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CupidsBow implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.BOW, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7cCupid's Bow");
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
		item.addEnchantment(Enchantment.ARROW_FIRE, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" R ", "HBH", " L ");
		recipe.setIngredient('R', Material.BLAZE_ROD);
		recipe.setIngredient('H', Material.SKULL_ITEM, 3);
		recipe.setIngredient('B', Material.BOW);
		recipe.setIngredient('L', Material.LAVA_BUCKET);
		
		return recipe;
	}
}
