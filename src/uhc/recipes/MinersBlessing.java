package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class MinersBlessing implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aMiner's Blessing");
		meta.setLore(Arrays.asList("\u00a77A mysterious Pickaxe","\u00a77blessed by the Gods.","\u00a77Holding provides","\u00a77Saturation III.","","\u00a7aEvery 100 durability used","\u00a7agives Regen I for 5","\u00a7aseconds.","\u00a7aEvery 250 durability used","\u00a7aadds 1 level of Sharpness","\u00a7and Efficiency."));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("EIE", "EPE", "BBB");
		recipe.setIngredient('E', Material.EXP_BOTTLE);
		recipe.setIngredient('I', Material.IRON_SWORD);
		recipe.setIngredient('P', Material.DIAMOND_PICKAXE);
		recipe.setIngredient('B', Material.BOOKSHELF);
		
		return recipe;
	}
}
