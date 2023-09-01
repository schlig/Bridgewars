package bridgewars.utils;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import bridgewars.game.CustomScoreboard;

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
}
