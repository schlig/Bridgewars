package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ShoesofVidar implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_BOOTS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aShoes of Vidar");
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.DEPTH_STRIDER, 2);
		item.addEnchantment(Enchantment.DURABILITY, 3);
		item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);
		item.addEnchantment(Enchantment.THORNS, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" P ","WBW"," F ");
		recipe.setIngredient('P', Material.RAW_FISH, 3);
		recipe.setIngredient('W', Material.POTION);
		recipe.setIngredient('B', Material.DIAMOND_BOOTS);
		recipe.setIngredient('F', Material.FISHING_ROD);
		
		return recipe;
	}
}
