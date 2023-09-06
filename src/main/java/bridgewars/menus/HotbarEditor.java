package bridgewars.menus;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import bridgewars.settings.HotbarLayout;
import bridgewars.utils.Message;

public class HotbarEditor {

	private static GUI menu = new GUI();
	private static HotbarLayout hotbar = new HotbarLayout();
	
	public static void sendInput(Player p, Inventory inv, ItemStack button, InventoryClickEvent e) {
		
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

			for(int i = 0; i < 9; i++)
				switch(p.getOpenInventory().getItem(i + 18).getType()) {
				case GOLD_SWORD:
					hotbar.setSlot(p, i, "swordSlot");
					break;
				case SHEARS:
					hotbar.setSlot(p, i, "shearsSlot");
					break;
				case WOOL:
					hotbar.setSlot(p, i, "woolSlot");
					break;
				case STONE_AXE:
					hotbar.setSlot(p, i, "axeSlot");
					break;
				case WOOD:
					hotbar.setSlot(p, i, "woodSlot");
					break;
				case BOW:
					hotbar.setSlot(p, i, "bowSlot");
					break;
				case WATER_BUCKET:
					hotbar.setSlot(p, i, "waterSlot");
					break;
				case LAVA_BUCKET:
					hotbar.setSlot(p, i, "lavaSlot");
					break;
				default:
				}
			
			e.setCancelled(true);
			p.sendMessage(Message.chat("&aSaved your hotbar layout!"));
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
