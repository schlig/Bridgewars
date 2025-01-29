package bridgewars.menus;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import bridgewars.messages.Chat;

public class MapRotation {
	
	private static GUI menu = new GUI();
	private final static String filepath = "./plugins/bridgewars/maps/";
	
	private final static int mapsPerPage = 21;
	
	public static void sendInput(Player p, Inventory inv, ItemStack button) throws IOException {
		if(inv.getType().equals(InventoryType.PLAYER))
			return;
		
		switch(button.getType()) {
		case ARROW:
			String operation = button.getItemMeta().getDisplayName();
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			if(operation.contains("Go Back")) {
				p.openInventory(menu.getMain());
				break;
			}

			String title = p.getOpenInventory().getTitle().substring(p.getOpenInventory().getTitle().length() - 1);
			int currentPage = Integer.valueOf(title) - 1;
			int nextPage = currentPage;
			if(operation.contains("Next")) 
				nextPage++;
			if(operation.contains("Previous"))
				nextPage--;
			p.openInventory(menu.getMapPage(nextPage));
			loadMapSettings(p, nextPage);
			break;
			
		case MAP:
		case BARRIER:
			String mapName = button.getItemMeta().getDisplayName().substring(4, button.getItemMeta().getDisplayName().length());
			File file = new File(filepath + mapName + ".map");
			RandomAccessFile f = new RandomAccessFile(file, "rw");
				
			String value = f.readLine();
			f.seek(0);
					
			if(value.contains("0")) {
				button = updateMap(button, true);
				f.write("1".getBytes());
				Bukkit.broadcastMessage(Chat.color("&6&l" + mapName + "&r has been " + "&cremoved" + " &rfrom the map rotation."));
				p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			}
			else {
				button = updateMap(button, false);
				f.write("0".getBytes());
				Bukkit.broadcastMessage(Chat.color("&6&l" + mapName + "&r has been " + "&aadded" + " &rto the map rotation."));
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
				
			f.close();
			
		default:
		}
	}
	
	public static void loadMapSettings(Player p, int page) throws IOException {
		menu.buildMapPages();
		
		File file = new File(filepath);
		List<String> mapList = new ArrayList<>(Arrays.asList(file.list()));
		Collections.sort(mapList);
		int index = page * mapsPerPage;
		
		for(int x = 0; x < 7; x++)
			for(int y = 0; y < 3; y++) {
				if(index >= mapList.size())
					break;
				int slot = (x + 1) + ((y + 1) * 9);
				//top left slot of an inventory is id 0 and bottom right is id 53
				//+ 1 to x to shift maps to the right one slot
				//* 9 to y to shift maps down one row
				RandomAccessFile f = new RandomAccessFile(filepath + mapList.get(index), "rw");
				f.seek(0);
				if(f.readLine().contains("1"))
					p.getOpenInventory().setItem(slot, updateMap(p.getOpenInventory().getItem(slot), true));
				f.close();
				index++;
			}
	}
	
	private static ItemStack updateMap(ItemStack map, boolean enabled) {
		if(enabled) {
			map.setType(Material.BARRIER);
			map.removeEnchantment(Enchantment.LURE);
		}
		else {
			map.setType(Material.MAP);
			map.addUnsafeEnchantment(Enchantment.LURE, 1);
		}
		return map;
	}
}
