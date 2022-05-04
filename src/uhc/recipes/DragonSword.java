package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class DragonSword implements Listener {
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta meta;
		meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aDragon Sword");
		meta.setLore(Arrays.asList("\u00a77Weaponsmith Ultimate."));
		item.setItemMeta(meta);
		
		net.minecraft.server.v1_8_R3.ItemStack itemTags = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = itemTags.getTag();
		NBTTagList list = new NBTTagList();
		NBTTagCompound damage = new NBTTagCompound();
		damage.set("AttributeName", new NBTTagString("generic.attackDamage"));
		damage.set("Name", new NBTTagString("generic.attackDamage"));
		damage.set("Amount", new NBTTagInt(8));
		damage.set("Operation", new NBTTagInt(0));
		damage.set("UUIDLeast", new NBTTagInt(1));
		damage.set("UUIDMost", new NBTTagInt(1));
		list.add(damage);
		compound.set("AttributeModifiers", list);
		itemTags.setTag(compound);
		item = CraftItemStack.asBukkitCopy(itemTags);
		
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape(" B "," D ","OBO");
		recipe.setIngredient('B', Material.BLAZE_POWDER);
		recipe.setIngredient('D', Material.DIAMOND_SWORD);
		recipe.setIngredient('O', Material.OBSIDIAN);
			
		return recipe;
	}
}
