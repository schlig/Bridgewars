package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ModularBow implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.BOW, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aModular Bow");
		meta.setLore(Arrays.asList("\u00a7bMode 1: Punch","\u00a7bMode 2: Poison","\u00a7bMode 3: Lightning","","\u00a7aCurrent Mode: \u00a76Punch \u00a78[1]","","\u00a7eLEFT CLICK to cycle!"));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" C ", "PBP", "ESE");
		recipe.setIngredient('C', Material.WATCH);
		recipe.setIngredient('P', Material.BLAZE_POWDER);
		recipe.setIngredient('B', Material.BOW);
		recipe.setIngredient('E', Material.SPIDER_EYE);
		recipe.setIngredient('S', Material.SLIME_BALL);
		
		return recipe;
	}
}
