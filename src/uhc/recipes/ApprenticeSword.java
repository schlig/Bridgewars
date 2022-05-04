package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ApprenticeSword implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aApprentice Sword");
		meta.setLore(Arrays.asList("\u00a7b-> Gains Sharpness I after Grace Period","\u00a7b-> Gains Sharpness II 15 minutes after Grace Period","\u00a7b-> Gains Sharpness III during Deathmatch","","\u00a77Cannot be used in an Anvil or Enchanting Table"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" R ", " S ", " R ");
		recipe.setIngredient('R', Material.REDSTONE_BLOCK);
		recipe.setIngredient('S', Material.IRON_SWORD);
		
		return recipe;
	}
}
