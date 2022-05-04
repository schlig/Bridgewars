package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Panacea implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.POTION, 1);
		item.setDurability((short) 8263);
		ItemMeta meta = item.getItemMeta();
		PotionMeta effect = (PotionMeta) meta;
		meta.setDisplayName("\u00a7aPanacea");
		meta.setLore(Arrays.asList("\u00a7aHeals 16 hearts on consuption."));
		effect.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 1, 3), true);
		effect.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1200, 1), true);
		meta = effect;
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("HMH", " B ");
		recipe.setIngredient('H', Material.SKULL_ITEM, 3);
		recipe.setIngredient('M', Material.SPECKLED_MELON);
		recipe.setIngredient('B', Material.GLASS_BOTTLE);
		
		return recipe;
	}
}
