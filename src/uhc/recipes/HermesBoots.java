package uhc.recipes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class HermesBoots implements Listener {
	@SuppressWarnings("deprecation")
	public ShapedRecipe addRecipe() {
		ItemStack item = new ItemStack(Material.DIAMOND_BOOTS, 1);
		ItemMeta meta;
		meta = item.getItemMeta();
		meta.setDisplayName("\u00a7aHermes' Boots");
		meta.setLore(Arrays.asList("\u00a77Provides the wearer with 10%","\u00a77increase walk speed!"));
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		item.addEnchantment(Enchantment.PROTECTION_FALL, 1);
		item.addEnchantment(Enchantment.DURABILITY, 2);
		
		net.minecraft.server.v1_8_R3.ItemStack itemTags = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = itemTags.getTag();
		NBTTagList list = new NBTTagList();
		NBTTagCompound speed = new NBTTagCompound();
		speed.set("AttributeName", new NBTTagString("generic.movementSpeed"));
		speed.set("Name", new NBTTagString("generic.movementSpeed"));
		speed.set("Amount", new NBTTagDouble(0.1));
		speed.set("Operation", new NBTTagInt(1));
		speed.set("UUIDLeast", new NBTTagInt(1));
		speed.set("UUIDMost", new NBTTagInt(1));
		list.add(speed);
		compound.set("AttributeModifiers", list);
		itemTags.setTag(compound);
		item = CraftItemStack.asBukkitCopy(itemTags);
		
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("DHD","PBP","F F");
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('H', Material.SKULL_ITEM, 3);
		recipe.setIngredient('P', Material.BLAZE_POWDER);
		recipe.setIngredient('B', Material.DIAMOND_BOOTS);
		recipe.setIngredient('F', Material.FEATHER);
			
		return recipe;
	}
}
