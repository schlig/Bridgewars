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

public class FlaskofCleansing implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.POTION, 1);
		item.setDurability((short) 16455);
		ItemMeta meta = item.getItemMeta();
		PotionMeta effect = (PotionMeta) meta;
		meta.setDisplayName("\u00a7cFlask of Cleansing");
		meta.setLore(Arrays.asList("\u00a77Splashing your opponent clears","\u00a77all of their potion effects and","\u00a77prevents them from healing for","\u00a7720 seconds!"));
		effect.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 1), true);
		meta = effect;
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" H ", " E ", " B ");
		recipe.setIngredient('H', Material.SKULL_ITEM);
		recipe.setIngredient('E', Material.MILK_BUCKET);
		recipe.setIngredient('B', Material.GLASS_BOTTLE);
		
		return recipe;
	}
}
