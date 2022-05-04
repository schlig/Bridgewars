package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Ambrosia implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.GLOWSTONE_DUST, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aAmbrosia");
		meta.setLore(Arrays.asList("\u00a771 time use. Buffs all","\u00a77Potions in a brewing stand","\u00a77to level III and reduces","\u00a77duration to 1 minute if","\u00a77above!","\u00a77Does not work on splash","\u00a77potions."));
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 3);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("BSB", "GTG", "GGG");
		recipe.setIngredient('B', Material.BLAZE_POWDER);
		recipe.setIngredient('S', Material.SKULL_ITEM, 1);
		recipe.setIngredient('G', Material.GLOWSTONE);
		recipe.setIngredient('T', Material.GHAST_TEAR);
		
		return recipe;
	}
}
