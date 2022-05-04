package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Excalibur implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a76Excalibur");
		meta.setLore(Arrays.asList("\u00a79Chaos","","\u00a76The Holy Sword","\u00a77Smites on hit, dealing 2","\u00a77hearts of damage! (5 second","\u00a77cooldown)"));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.DURABILITY, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("SFS", "STS", "SDS");
		recipe.setIngredient('S', Material.SOUL_SAND);
		recipe.setIngredient('T', Material.TNT);
		recipe.setIngredient('F', Material.FIREBALL);
		recipe.setIngredient('D', Material.DIAMOND_SWORD);
		
		return recipe;
	}
}
