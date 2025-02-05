package bridgewars.menus;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
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
	private static String filepath = "./plugins/bridgewars/maps/";
	
	private static int mapsPerPage = 21;
	
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
			File file = new File(filepath);
			String mapName = button.getItemMeta().getDisplayName().substring(4, button.getItemMeta().getDisplayName().length());
			
			file = new File(filepath + mapName + ".map");
			RandomAccessFile f = new RandomAccessFile(file, "rw");
			f.seek(0);
			String value = f.readLine();
			mapToggle(p, button, value.contains("0"), mapName, f);
			f.close();
			
		default:
		}
	}
	
	public static void loadMapSettings(Player p, int page) throws IOException {
		menu.buildMapPages();
		
		File file = new File(filepath);
		List<String> mapList = new ArrayList<>(Arrays.asList(file.list()));
		int index = page * mapsPerPage;

		for(int x = 10; x <= 28; x += 9) {
			for(int y = 0; y < 7; y++) {
				if(index >= mapList.size())
					return;
				int slot = x + y;
				
				//these for loops are ass but im not touching them because it broke everything
				
				RandomAccessFile f = new RandomAccessFile(filepath + mapList.get(index), "rw");
				f.seek(0);
				p.getOpenInventory().setItem(slot, updateMap(p.getOpenInventory().getItem(slot), !f.readLine().contains("1")));
				
				f.close();
				index++;
			}
		}
	}
	
	private static void mapToggle(Player p, ItemStack button, Boolean state, String mapName, RandomAccessFile f) {
		try {
			state = !state;
			button = updateMap(button, state);
			String mapstate = state ? "0" : "1";
			f.seek(0);
			f.write(mapstate.getBytes());
			
			mapstate = state ? "&aadded&r to" : "&cremoved&r from";
			Bukkit.broadcastMessage(Chat.color("&6&l" + mapName + "&r has been " + mapstate + " the map rotation."));
			
			if(state) p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			else p.playSound(p.getLocation(), Sound.CLICK, 1f, 1f);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	private static ItemStack updateMap(ItemStack map, boolean state) {
		map.setType(state ? Material.MAP : Material.BARRIER);
		if(state) map.addUnsafeEnchantment(Enchantment.LURE, 1);
		else map.removeEnchantment(Enchantment.LURE);
		return map;
	}
}
