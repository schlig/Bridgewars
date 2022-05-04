package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class PhilosophersPickaxe implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		item.setDurability((short) 1559);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aPhilosopher's Pickaxe");
		meta.setLore(Arrays.asList("\u00a77Engineering Ultimate."));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("IGI", "LSL", " S ");
		recipe.setIngredient('I', Material.IRON_ORE);
		recipe.setIngredient('G', Material.GOLD_ORE);
		recipe.setIngredient('L', Material.LAPIS_BLOCK);
		recipe.setIngredient('S', Material.STICK);
		
		return recipe;
	}
}
