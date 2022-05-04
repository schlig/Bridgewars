package uhc.recipes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FlaskofIchor implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.POTION, 1);
		item.setDurability((short) 16455);
		ItemMeta meta = item.getItemMeta();
		PotionMeta effect = (PotionMeta) meta;
		meta.setDisplayName("\u00a7aFlask of Ichor");
		effect.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 1, 2), true);
		meta = effect;
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" H ", "MBM", " I ");
		recipe.setIngredient('H', Material.SKULL_ITEM, 3);
		recipe.setIngredient('M', Material.BROWN_MUSHROOM);
		recipe.setIngredient('B', Material.GLASS_BOTTLE);
		recipe.setIngredient('I', Material.INK_SACK);
		
		return recipe;
	}
}
