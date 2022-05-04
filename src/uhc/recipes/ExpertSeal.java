package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ExpertSeal implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.NETHER_STAR, 6);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aExpert Seal");
		meta.setLore(Arrays.asList("\u00a79Favor","\u00a77Using this item on top of","\u00a77another item in your","\u00a77inventory will upgrade all","\u00a77enchantments on that item","\u00a77by 1 level."));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("EIE", "GDG", "EIE");
		recipe.setIngredient('E', Material.EXP_BOTTLE);
		recipe.setIngredient('I', Material.IRON_BLOCK);
		recipe.setIngredient('G', Material.GOLD_BLOCK);
		recipe.setIngredient('D', Material.DIAMOND_BLOCK);
		
		return recipe;
	}
}
