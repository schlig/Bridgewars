package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class AxeofPerun implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_AXE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a76Axe of Perun");
		meta.setLore(Arrays.asList("\u00a79Thunder","","\u00a77Strikes Lightning, dealing","\u00a771.5 hearts! (8 second","\u00a77cooldown)"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("DTF", "DS ", " S ");
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('T', Material.TNT);
		recipe.setIngredient('F', Material.FIREBALL);
		recipe.setIngredient('S', Material.STICK);
		
		return recipe;
	}
}
