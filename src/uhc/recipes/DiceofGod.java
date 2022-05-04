package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class DiceofGod implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.ENDER_PORTAL_FRAME, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aDice of God");
		meta.setLore(Arrays.asList("\u00a77Tempt the fate of the Gods for a","\u00a77random item!"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("MHM", "MJM", "MMM");
		recipe.setIngredient('M', Material.MOSSY_COBBLESTONE);
		recipe.setIngredient('H', Material.SKULL_ITEM, 3);
		recipe.setIngredient('J', Material.JUKEBOX);
		
		return recipe;
	}
}
