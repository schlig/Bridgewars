package bridgewars.menus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import bridgewars.utils.Message;

public class GUI {
	
	private Inventory main = Bukkit.createInventory(null, 45, "Bridgewars");
	private Inventory ks = Bukkit.createInventory(null, 45, "Killstreak Bonuses");
	private Inventory emptyPage = Bukkit.createInventory(null, 45, "Map Rotation");
	private Inventory clock = Bukkit.createInventory(null, 45, "Time Limit");
	private Inventory hotbar = Bukkit.createInventory(null, 45, "Hotbar Layout");
	private Inventory settings = Bukkit.createInventory(null, 45, "Game Settings");
	private Inventory teams = Bukkit.createInventory(null, 45, "Team Selector");
	
	private List<Inventory> mapPages = new ArrayList<>();
	
	private String filepath = "./plugins/bridgewars/maps/";
	
	public GUI () {
		for(int i = 0; i < 54; i++) {
			ItemStack s = item(Material.STAINED_GLASS_PANE, 1, 8, ".", false, null);
			if(i < main.getSize())
				main.setItem(i, s);
			if(i < ks.getSize())
				ks.setItem(i, s);
			if(i < emptyPage.getSize())
				emptyPage.setItem(i, s);
			if(i < clock.getSize())
				clock.setItem(i, s);
			if(i < settings.getSize())
				settings.setItem(i, s);
			if(i < hotbar.getSize())
				hotbar.setItem(i, s);
			if(i < teams.getSize())
				teams.setItem(i, s);
		}
		
		//build main menu
		main.setItem(10, item(Material.LADDER, 1, 0, "&6&lStart Game", false, null)); // Start Game button
		main.setItem(12, item(Material.REDSTONE_COMPARATOR, 1, 0, "&r&lGame Settings", false, Arrays.asList(Message.chat("&7Admin only")))); // Game Settings button
		main.setItem(14, item(Material.MAP, 1, 0, "&e&lMap Rotation", false, null)); // Map Rotation button
		main.setItem(16, item(Material.TNT, 1, 0, "&c&lEnd Game", false, Arrays.asList(Message.chat("&7Admin only")))); // End Game button
		
		main.setItem(29, item(Material.SADDLE, 1, 0, "&a&lJoin Game", false, null)); // Join Game button
		main.setItem(31, item(Material.BOOK, 1, 0, "&b&lHotbar Layout", false, null)); // Hotbar Layout button
		main.setItem(33, item(Material.IRON_SWORD, 1, 0, "&c&lKillstreak Bonuses", true, null)); // Killstreak Editor button
		
		main.setItem(36, item(Material.WOOL, 1, 0, "&r&lTeam Selector", false, null));
		main.setItem(44, item(Material.BARRIER, 1, 0, "&c&lClose", false, null));
		
		//build the killstreak menu
		ks.setItem(40, button(0));
		
		//build the map rotation menu
		emptyPage.setItem(40, button(0));
		buildMapPages();
		
		//build the settings menu
		settings.setItem(10, item(Material.WATCH, 1, 0, "&r&6Time Limit: ?", false, null));
		settings.setItem(11, item(Material.COMPASS, 1, 0, "&r&6Timer Acceleration", false, Arrays.asList(
				Message.chat("&r0%"))));
		
		settings.setItem(12, item(Material.GOLD_SWORD, 1, 0, "&r&6Swords", false, Arrays.asList(
				Message.chat("&r&cDisabled"),
				Message.chat("&7Players have golden swords"))));
		
		settings.setItem(13, item(Material.WOOL, 1, 0, "&r&6Blocks", false, Arrays.asList(
				Message.chat("&r&cDisabled"),
				Message.chat("&7Players have infinite wool blocks"))));
		
		settings.setItem(14, item(Material.SHEARS, 1, 0, "&r&6Shears", false, Arrays.asList(
				Message.chat("&r&cDisabled"),
				Message.chat("&7Players have shears"),
				Message.chat("&7Efficiency III"),
				Message.chat("&7Unbreakable"))));
		
		settings.setItem(15, item(Material.IRON_SWORD, 1, 0, "&r&6Killstreak Bonuses", false, Arrays.asList(
				Message.chat("&r&cDisabled"),
				Message.chat("&7Determines whether players"),
				Message.chat("&7should receive items from"),
				Message.chat("&7killstreaks"))));
		
		settings.setItem(16, item(Material.BOW, 1, 0, "&r&6Bows", false, Arrays.asList(
				Message.chat("&r&cDisabled"),
				Message.chat("&7Players have bows"),
				Message.chat("&7Cannot be shot at or from spawn"),
				Message.chat("&7Infinite arrows"))));
		
		settings.setItem(19, item(Material.GOLDEN_APPLE, 1, 0, "&r&6Double Health", false, Arrays.asList(
				Message.chat("&r&cDisabled"),
				Message.chat("&7Increase maximum health from"),
				Message.chat("&710 hearts to 20"))));
		
		settings.setItem(20, item(Material.RABBIT_FOOT, 1, 0, "&r&6Double Jump", false, Arrays.asList(
				Message.chat("&r&cDisabled"),
				Message.chat("&7Players can jump midair once"))));
		
		settings.setItem(21, item(Material.DIAMOND_PICKAXE, 1, 0, "&r&6Giga Drill", false, Arrays.asList(
				Message.chat("&r&cDisabled"),
				Message.chat("&7Shears have Efficiency V"))));
		
		settings.setItem(22, item(Material.WOOD, 1, 0, "&r&6Dig Wars", false, Arrays.asList(
				Message.chat("&r&cDisabled"),
				Message.chat("&7All crafting recipes are enabled"),
				Message.chat("&7Start with 64 wood and a stone axe"),
				Message.chat("&7Wood is replenished on death"))));
		
		settings.setItem(23, item(Material.DIAMOND, 1, 0, "&r&6Natural Item Spawns", false, Arrays.asList(
				Message.chat("&r&cDisabled"),
				Message.chat("&7Items will randomly spawn anywhere"),
				Message.chat("&7on the map"))));
		
		settings.setItem(40, button(0));
		
		//build the timer menu
		clock.setItem(13, item(Material.WATCH, 1, 0, "&6&lTime Limit: " + "-", false, null));
		clock.setItem(28, item(Material.WOOL, 30, 14, "&c&l-30 seconds", false, null));
		clock.setItem(29, item(Material.WOOL, 10, 14, "&c&l-10 seconds", false, null));
		clock.setItem(30, item(Material.WOOL, 5, 14, "&c&l-5 seconds", false, null));
		clock.setItem(32, item(Material.WOOL, 5, 5, "&a&l+5 seconds ", false, null));
		clock.setItem(33, item(Material.WOOL, 10, 5, "&a&l+10 seconds ", false, null));
		clock.setItem(34, item(Material.WOOL, 30, 5, "&a&l+30 seconds ", false, null));
		clock.setItem(40, button(0));
		
		//build the hotbar layout menu
		for(int i = 18; i < 27; i++)
			hotbar.setItem(i, null);
		hotbar.setItem(39, item(Material.WOOL, 1, 5, "&a&lSave Layout", false, null));
		hotbar.setItem(41, button(0));
		
		//build the team selector menu
		teams.setItem(41, button(0));
		teams.setItem(39, item(Material.WOOL, 1, 0, "&r&lLeave Team", false, null));
		teams.setItem(19, item(Material.WOOL, 1, 14, "&c&lJoin Red Team", false, null));
		teams.setItem(21, item(Material.WOOL, 1, 3, "&b&lJoin Blue Team", false, null));
		teams.setItem(23, item(Material.WOOL, 1, 5, "&a&lJoin Green Team", false, null));
		teams.setItem(25, item(Material.WOOL, 1, 4, "&e&lJoin Yellow Team", false, null));
	}
	
	private ItemStack button(int id) {
		if(id == 0)
			return item(Material.ARROW, 1, 0, "&r&lGo Back", false, null);
		else
			return null;
	}
	
	private ItemStack item(Material m, int amount, int damage, String name, Boolean hideFlags, List<String> tooltip) {
		ItemStack item = new ItemStack(m, amount, (short) damage);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Message.chat(name));
		if(tooltip != null)
			meta.setLore(tooltip);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, 
				    	  ItemFlag.HIDE_DESTROYS,
						  ItemFlag.HIDE_ENCHANTS,
						  ItemFlag.HIDE_PLACED_ON,
						  ItemFlag.HIDE_POTION_EFFECTS,
						  ItemFlag.HIDE_UNBREAKABLE);
		item.setItemMeta(meta);
		return item;
	}
	
	public Inventory getMain() {
		return main;
	}
	
	public Inventory getKS() {
		return ks;
	}
	
	public void buildMapPages() {
		File file = new File(filepath);
		List<String> mapList = new ArrayList<>(Arrays.asList(file.list()));
		Collections.sort(mapList);
		mapPages = new ArrayList<>();
		Integer pageNumber = 0;
		
		while(mapList.size() > 0) {
			Inventory page = Bukkit.createInventory(null, 45, "Map Rotation - Page " + (pageNumber + 1));
			page.setContents(emptyPage.getContents());
			if(pageNumber > 0)
				page.setItem(37, item(Material.ARROW, 1, 0, "&r&lPrevious Page", false, null));
			
			for(int j = 10; j <= 28; j+=9)
				for(int i = 0; i < 7; i++) {
					if(mapList.size() == 0)
						break;
					ItemStack map = item(Material.MAP, 1, 0, Message.chat("&r&l") + mapList.get(0).substring(0, mapList.get(0).length() - 4), false, null);
					map.addUnsafeEnchantment(Enchantment.LURE, 1);
					page.setItem(i + j, map);
					mapList.remove(0);
				}
			if(mapList.size() > 0)
				page.setItem(43, item(Material.ARROW, 1, 0, "&r&lNext Page", false, null));
			mapPages.add(page);
			pageNumber++;
		}
	}
	
	public Inventory getMapPage(int i) {
		return mapPages.get(i);
	}
	
	public Inventory getSettings() {
		return settings;
	}
	
	public Inventory getClock() {
		return clock;
	}
	
	public Inventory getHotbar() {
		return hotbar;
	}
	
	public Inventory getTeam() {
		return teams;
	}
}
