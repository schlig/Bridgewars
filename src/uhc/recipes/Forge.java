package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Forge implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.FURNACE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aForge");
		meta.setLore(Arrays.asList("\u00a77A mystical Forge with the power","\u00a77to instantly smelt 10 items","\u00a77before self-destructing!"));
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("SSS", "SCS", "SSS");
		recipe.setIngredient('S', Material.COBBLESTONE);
		recipe.setIngredient('C', Material.COAL_BLOCK);
		
		return recipe;
	}
}
