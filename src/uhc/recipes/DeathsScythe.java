package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class DeathsScythe implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.IRON_HOE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7dDeath's Scythe");
		meta.setLore(Arrays.asList("\u00a79Oblivion","","\u00a77Deals 20% of your","\u00a77opponent's health as","\u00a77damage, and lifesteals 25%","\u00a77of the damage dealt!"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" HH", " BC", "B  ");
		recipe.setIngredient('H', Material.SKULL_ITEM, 3);
		recipe.setIngredient('B', Material.BONE);
		recipe.setIngredient('C', Material.WATCH);
		
		return recipe;
	}
}
