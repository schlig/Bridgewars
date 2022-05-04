package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class QuickPick implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.IRON_PICKAXE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aQuick Pick");
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.DIG_SPEED, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("III", "CSC", " S ");
		recipe.setIngredient('I', Material.IRON_ORE);
		recipe.setIngredient('C', Material.COAL);
		recipe.setIngredient('S', Material.STICK);
		
		return recipe;
	}
}
