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

public class PotionofToughness implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.POTION, 1);
		item.setDurability((short) 8263);
		ItemMeta meta = item.getItemMeta();
		PotionMeta effect = (PotionMeta) meta;
		effect.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2400, 1), true);
		meta = effect;
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" S ", " N ", " B ");
		recipe.setIngredient('S', Material.SLIME_BALL);
		recipe.setIngredient('N', Material.SNOW_BLOCK);
		recipe.setIngredient('B', Material.GLASS_BOTTLE);
		
		return recipe;
	}
}
