package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class HideofLeviathan implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aHide of Leviathan");
		meta.setLore(Arrays.asList("\u00a79Maelstrom."));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		item.addUnsafeEnchantment(Enchantment.OXYGEN, 3);
		item.addUnsafeEnchantment(Enchantment.WATER_WORKER, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("LWL", "DPD", "A A");
		recipe.setIngredient('L', Material.LAPIS_BLOCK);
		recipe.setIngredient('W', Material.WATER_BUCKET);
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('P', Material.DIAMOND_LEGGINGS);
		recipe.setIngredient('A', Material.WATER_LILY);
		
		return recipe;
	}
}
