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

public class PotionofVitality implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.POTION, 1);
		item.setDurability((short) 16455);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aPotion of Vitality");
		PotionMeta effect = (PotionMeta) meta;
		effect.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 240, 1), true);
		effect.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 160, 1), true);
		effect.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, 240, 1), true);
		effect.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 120, 1), true);
		meta = effect;
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" S ", " N ", " B ");
		recipe.setIngredient('S', Material.SKULL_ITEM, 3);
		recipe.setIngredient('N', Material.NETHER_STALK);
		recipe.setIngredient('B', Material.GLASS_BOTTLE);
		
		return recipe;
	}
}
