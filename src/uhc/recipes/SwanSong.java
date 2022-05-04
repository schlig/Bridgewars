package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class SwanSong implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7bSwan Song I");
		meta.setLore(Arrays.asList("\u00a77Sword Enchant","", "\u00a77Deals +1% damage for each","\u00a77heart missing below 10","\u00a77hearts.","\u00a77Can only be crafted below 5","\u00a77hearts, and without alive","\u00a77teammates"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("F  ", " PP", " PS");
		recipe.setIngredient('F', Material.FEATHER);
		recipe.setIngredient('P', Material.PAPER);
		recipe.setIngredient('S', Material.GOLD_RECORD);
		
		return recipe;
	}
}
