package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class DragonArmor implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aDragon Armor");
		meta.setLore(Arrays.asList("\u00a77Armorsmith Ultimate."));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" M ", " C ", "OAO");
		recipe.setIngredient('M', Material.MAGMA_CREAM);
		recipe.setIngredient('C', Material.DIAMOND_CHESTPLATE);
		recipe.setIngredient('O', Material.OBSIDIAN);
		recipe.setIngredient('A', Material.ANVIL);
		
		return recipe;
	}
}
