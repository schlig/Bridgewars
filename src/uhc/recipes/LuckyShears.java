package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class LuckyShears implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.SHEARS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aLucky Shears");
		meta.setLore(Arrays.asList("\u00a77Quintuples the chance for","\u00a77leaves to drop apples","\u00a77Can only be crafted below 5","\u00a77hearts"));
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.LUCK, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("SSS", "SCS", "SSS");
		recipe.setIngredient('S', Material.LEAVES);
		recipe.setIngredient('C', Material.SHEARS);
		
		return recipe;
	}
}
