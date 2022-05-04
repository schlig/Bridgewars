package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ApprenticeBow implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.BOW, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aApprentice Bow");
		meta.setLore(Arrays.asList("\u00a7b-> Gains Power I after Grace Period","\u00a7b-> Gains Power II 15 minutes after Grace Period","\u00a7b-> Gains Power III during Deathmatch","","\u00a77Cannot be used in an Anvil or Enchanting Table"));
		item.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" TS", "T S", " TS");
		recipe.setIngredient('T', Material.REDSTONE_TORCH_ON);
		recipe.setIngredient('S', Material.STRING);
		
		return recipe;
	}
}
