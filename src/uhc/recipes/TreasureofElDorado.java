package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class TreasureofElDorado implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.MAP, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aTreasure of El Dorado");
		meta.setLore(Arrays.asList("\u00a77Unknown map","\u00a77Follow the map to your","\u00a77Treasure of El Dorado","\u00a77location","\u00a77Your Treasure of El","\u00a77Dorado will spawn on the","\u00a77opposite side of the map.","\u00a77It will contain healing","\u00a77items and materials to help","\u00a77you.","\u00a77Can only be crafted below 5","\u00a77hearts, and without alive","\u00a77teammates"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" R ", "WCW", "S S");
		recipe.setIngredient('R', Material.WOOL, 14);
		recipe.setIngredient('W', Material.WOOL);
		recipe.setIngredient('C', Material.CHEST);
		recipe.setIngredient('S', Material.STRING);
		
		return recipe;
	}
}
