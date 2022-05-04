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

public class HolyWater implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.POTION, 1);
		item.setDurability((short) 8263);
		ItemMeta meta = item.getItemMeta();
		PotionMeta effect = (PotionMeta) meta;
		effect.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 3), true);
		meta = effect;
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("GRG", " M ", " B ");
		recipe.setIngredient('G', Material.GOLD_INGOT);
		recipe.setIngredient('R', Material.REDSTONE_BLOCK);
		recipe.setIngredient('M', Material.GOLD_RECORD);
		recipe.setIngredient('B', Material.GLASS_BOTTLE);
		
		return recipe;
	}
}
