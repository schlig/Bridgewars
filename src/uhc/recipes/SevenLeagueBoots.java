package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class SevenLeagueBoots implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_BOOTS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aSeven-league Boots");
		meta.setLore(Arrays.asList("\u00a77Survivalism Ultimate."));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		item.addEnchantment(Enchantment.PROTECTION_FALL, 3);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("FEF", "FDF", "FWF");
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('E', Material.ENDER_PEARL);
		recipe.setIngredient('D', Material.DIAMOND_BOOTS);
		recipe.setIngredient('W', Material.WATER_BUCKET);
		
		return recipe;
	}
}
