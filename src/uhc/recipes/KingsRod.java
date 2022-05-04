package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class KingsRod implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.FISHING_ROD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aKing's Rod");
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		item.addUnsafeEnchantment(Enchantment.LUCK, 10);
		item.addUnsafeEnchantment(Enchantment.LURE, 5);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" F ", "LCL", " W ");
		recipe.setIngredient('L', Material.WATER_LILY);
		recipe.setIngredient('F', Material.FISHING_ROD);
		recipe.setIngredient('C', Material.COMPASS);
		recipe.setIngredient('W', Material.WATER_BUCKET);
		
		return recipe;
	}
}
