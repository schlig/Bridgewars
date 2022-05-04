package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class VorpalSword implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aVorpal Sword");
		meta.setLore(Arrays.asList("\u00a77Weaponsmith Craft 1"));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.DAMAGE_UNDEAD, 2);
		item.addEnchantment(Enchantment.DAMAGE_ARTHROPODS, 2);
		item.addEnchantment(Enchantment.LOOT_BONUS_MOBS, 2);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" B ", " S ", " R ");
		recipe.setIngredient('B', Material.BONE);
		recipe.setIngredient('S', Material.IRON_SWORD);
		recipe.setIngredient('R', Material.ROTTEN_FLESH);
		
		return recipe;
	}
}
