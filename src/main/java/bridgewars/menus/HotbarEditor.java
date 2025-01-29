package bridgewars.menus;

import java.io.IOException;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import bridgewars.messages.Chat;
import bridgewars.settings.PlayerSettings;

public class HotbarEditor {

	private static GUI menu = new GUI();
	
	public static void sendInput(Player p, Inventory inv, ItemStack button, InventoryClickEvent e) throws IOException {
		
		switch(e.getAction()) {
		case DROP_ONE_SLOT:
		case MOVE_TO_OTHER_INVENTORY:
		case HOTBAR_SWAP:
			e.setCancelled(true);
			break;
		default:
			e.setCancelled(false);
		}
		
		switch(button.getType()) {
		case WOOL:
			if(!button.getItemMeta().hasDisplayName())
				return;
			
			for(int i = 0; i <= 8; i++)
				switch(p.getOpenInventory().getItem(i + 18).getType()) {
				case GOLD_SWORD:
					PlayerSettings.setSetting(p, "SwordSlot", ((Integer)i).toString());
					break;
				case SHEARS:
					PlayerSettings.setSetting(p, "ShearsSlot", ((Integer)i).toString());
					break;
				case WOOL:
					PlayerSettings.setSetting(p, "WoolSlot", ((Integer)i).toString());
					break;
				default:
				}
			
			e.setCancelled(true);
			p.sendMessage(Chat.color("&aSaved your hotbar layout!"));
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			break;

		case ARROW:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			p.openInventory(menu.getMain());
		case STAINED_GLASS_PANE:
			e.setCancelled(true);
		default:
		}
	}
}
