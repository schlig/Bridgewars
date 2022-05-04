package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ArtemisBow implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.BOW, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7cArtemis' Bow");
		meta.setLore(Arrays.asList("\u00a79Hunting","","\u00a77Has a chance for your arrow","\u00a77to home into your opponent!"));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("FDF", "FBF", "FEF");
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('B', Material.BOW);
		recipe.setIngredient('E', Material.EYE_OF_ENDER);
		
		return recipe;
	}
}
