package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class GoldenHead implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
		ItemMeta meta = item.getItemMeta();
		SkullMeta skull = (SkullMeta) meta;
		skull.setDisplayName("\u00a76Golden Head");
		skull.setLore(Arrays.asList("\u00a77Absorption (2:00)","\u00a77Regeneration II (0:05)","\u00a7a*Given to all team","\u00a7amembers*"));
		skull.setOwner("Schlog");
		item.setItemMeta(skull);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("GGG", "GHG", "GGG");
		recipe.setIngredient('G', Material.SKULL_ITEM, 3);
		recipe.setIngredient('H', Material.GOLD_INGOT);
		
		return recipe;
	}
}
