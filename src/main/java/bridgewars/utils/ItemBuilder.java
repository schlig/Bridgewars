package bridgewars.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import bridgewars.game.CustomScoreboard;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class ItemBuilder {

	private static CustomScoreboard cs = new CustomScoreboard();
	
	public static ItemStack hideFlags(ItemStack item) { //hides an item's nbt data flags
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack setName(ItemStack item, String name) { //sets an item's display name
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Message.chat(name));
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack setLore(ItemStack item, List<String> lore) { //sets the lore of an item
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack setUnbreakable(ItemStack item, boolean state) { //makes an item unbreakable
		ItemMeta meta = item.getItemMeta();
		if(state)
			meta.spigot().setUnbreakable(true);
		else
			meta.spigot().setUnbreakable(false);
		item.setItemMeta(meta);
		item.setDurability((short)0);
		return item;
	}
	
	public static ItemStack setLeatherColor(Player p, ItemStack item, String s) { //sets the color of leather armor
		ItemStack armorPiece = item;
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(getColor(cs.getTeam(p)));
		armorPiece.setItemMeta(meta);
		return armorPiece;
	}
	
	public static ItemStack setSkullTexture(ItemStack item, UUID uuid, String texture) {
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		GameProfile skullProfile = new GameProfile(uuid, null);
		skullProfile.getProperties().put("textures", new Property("textures", texture));
		
		try {
			Field profileField = meta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(meta, skullProfile);
        } catch (Exception e) { }
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static byte getColorID(Player p) {
		byte value = 0;
		if(cs.hasTeam(p)) {
			switch(cs.getTeam(p)) {
			case "red":
				value = 14;
				break;
			case "blue":
				value = 3;
				break;
			case "green":
				value = 5;
				break;
			case "yellow":
				value = 4;
				break;
			}
		}
		return value;
	}
	
	private static Color getColor(String s) { //gets a color from a specified team name
		switch(s) {
		case "red":
			return Color.fromRGB(255, 0, 0);
		case "blue":
			return Color.fromRGB(0, 255, 255);
		case "green":
			return Color.fromRGB(0, 255, 0);
		case "yellow":
			return Color.fromRGB(255, 255, 0);
		}
		return Color.fromRGB(255, 255, 255);
	}
	
	public static String getTeamName(Player p){
		return cs.getTeam(p);
	}
	
	public static ItemStack setID(ItemStack in, String id) {

		net.minecraft.server.v1_8_R3.ItemStack item = CraftItemStack.asNMSCopy(in);
		NBTTagCompound idtag = item.getTag();
		idtag.setString("id", id);
		item.setTag(idtag);
		
		ItemStack out = CraftItemStack.asBukkitCopy(item);
		in.setItemMeta(out.getItemMeta());
		
		return in;
	}
	
//	public static ItemStack setID(ItemStack item) {    [this is just here so i have something to work with]
//
//		net.minecraft.server.v1_8_R3.ItemStack itemTags = CraftItemStack.asNMSCopy(item);
//		NBTTagCompound compound = itemTags.getTag();
//		NBTTagList list = new NBTTagList();
//		NBTTagCompound speed = new NBTTagCompound();
//		speed.set("AttributeName", new NBTTagString("generic.movementSpeed"));
//		speed.set("Name", new NBTTagString("generic.movementSpeed"));
//		speed.set("Amount", new NBTTagDouble(0.1));
//		speed.set("Operation", new NBTTagInt(1));
//		speed.set("UUIDLeast", new NBTTagInt(1));
//		speed.set("UUIDMost", new NBTTagInt(1));
//		list.add(speed);
//		compound.set("AttributeModifiers", list);
//		itemTags.setTag(compound);
//		
//		return item = CraftItemStack.asBukkitCopy(itemTags);
//	}
}
