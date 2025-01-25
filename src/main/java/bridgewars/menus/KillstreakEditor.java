package bridgewars.menus;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import bridgewars.messages.Chat;
import bridgewars.settings.ChosenKillstreaks;
import bridgewars.utils.Utils;

public class KillstreakEditor {

	private static GUI menu = new GUI();
	private static ChosenKillstreaks ks = new ChosenKillstreaks();
	
	public static void sendInput(Player p, Inventory inv, ItemStack button) {
		if(inv.getType().equals(InventoryType.PLAYER))
			return;
		if(button.getType() == Material.ARROW) {
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			p.openInventory(menu.getMain());
			return;
		}
		
		switch(Utils.getID(button)) {
			
		case "bridgeegg":
			if(ks.getThreeStreak(p) == 0)
				break;
			ks.setThreeStreak(p, 0);
			sendMenuFeedback(p, button, 28, "3");
			break;
			
		case "mysterypill":
			if(ks.getThreeStreak(p) == 1)
				break;
			ks.setThreeStreak(p, 1);
			sendMenuFeedback(p, button, 10, "3");
			break;
			
		case "portabledoinkhut":
			if(ks.getFiveStreak(p) == 0)
				break;
			ks.setFiveStreak(p, 0);
			sendMenuFeedback(p, button, 30, "5");
			break;
			
		case "fireball":
			if(ks.getFiveStreak(p) == 1)
				break;
			ks.setFiveStreak(p, 1);
			sendMenuFeedback(p, button, 12, "5");
			break;
			
		case "homerunbat":
			if(ks.getSevenStreak(p) == 0)
				break;
			ks.setSevenStreak(p, 0);
			sendMenuFeedback(p, button, 32, "7");
			break;
			
		case "blackhole":
			if(ks.getSevenStreak(p) == 1)
				break;
			ks.setSevenStreak(p, 1);
			sendMenuFeedback(p, button, 14, "7");
			break;
			
		case "heartcontainer":
			if(ks.getFinalStreak(p) == 0)
				break;
			ks.setFinalStreak(p, 1);
			sendMenuFeedback(p, button, 34, "15");
			break;
			
		case "magicstopwatch":
			if(ks.getFinalStreak(p) == 1)
				break;
			ks.setFinalStreak(p, 0);
			sendMenuFeedback(p, button, 16, "15");
			break;
			
		default:
			break;
		}
	}
	
	private static void sendMenuFeedback(Player p, ItemStack button, int slot, String streak) {
		Inventory menu = p.getOpenInventory().getTopInventory();
		if(menu.getItem(slot).containsEnchantment(Enchantment.LURE))
			menu.getItem(slot).removeEnchantment(Enchantment.LURE);
		button.addUnsafeEnchantment(Enchantment.LURE, 1);
		p.sendMessage(Chat.color("You will now recieve a " + button.getItemMeta().getDisplayName() + "&r every " + streak + " kills."));
		p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
	}
}
