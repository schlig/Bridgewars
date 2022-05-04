package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class TheDeep implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.FISHING_ROD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aThe Deep");
		meta.setLore(Arrays.asList("\u00a76Chance to fish up gold","\u00a76nuggets or ingots when","\u00a76you're low on health!","\u00a7cWhen high on health, big","\u00a7cchance to fish up a","\u00a7cdangerous monster."));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.LURE, 2);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("  B", " BS", "B S");
		recipe.setIngredient('B', Material.BONE);
		recipe.setIngredient('S', Material.STRING);
		
		return recipe;
	}
}
