package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class FatesCall implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.FLOWER_POT_ITEM, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aFate's Call");
		meta.setLore(Arrays.asList("\u00a77Spawns a chest with","\u00a77ingredients for recipes you","\u00a77have unlocked","\u00a77Spawns 1 ingredient for","\u00a77each 6 unique recipes you","\u00a77have unlocked, to a maximum","\u00a77of 7 ingredients."));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" R ", "RFR", " R ");
		recipe.setIngredient('R', Material.REDSTONE_LAMP_OFF);
		recipe.setIngredient('F', Material.FIREWORK_CHARGE);
		
		return recipe;
	}
}
