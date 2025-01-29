package bridgewars.menus;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import bridgewars.messages.Chat;
import bridgewars.settings.PlayerSettings;
import bridgewars.utils.Utils;

public class KillstreakEditor {

	private static GUI menu = new GUI();
	
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
			updateSettings(p, button, 28, "3", "KillstreakRewardWhite");
			break;
			
		case "mysterypill":
			updateSettings(p, button, 10, "3", "KillstreakRewardWhite");
			break;
			
		case "portabledoinkhut":
			updateSettings(p, button, 30, "5", "KillstreakRewardGreen");
			break;
			
		case "fireball":
			updateSettings(p, button, 12, "5", "KillstreakRewardGreen");
			break;
			
		case "homerunbat":
			updateSettings(p, button, 32, "7", "KillstreakRewardRed");
			break;
			
		case "blackhole":
			updateSettings(p, button, 14, "7", "KillstreakRewardRed");
			break;
			
		case "heartcontainer":
			updateSettings(p, button, 34, "15", "KillstreakRewardBlue");
			break;
			
		case "magicstopwatch":
			updateSettings(p, button, 16, "15", "KillstreakRewardBlue");
			break;
			
		default:
			break;
		}
	}
	
	private static void updateSettings(Player p, ItemStack button, int inventorySlot, String kills, String streak) {
		if(PlayerSettings.getSetting(p, streak).equalsIgnoreCase(Utils.getID(button)))
			return;
		
		Inventory menu = p.getOpenInventory().getTopInventory();
		if(menu.getItem(inventorySlot).containsEnchantment(Enchantment.LURE))
			menu.getItem(inventorySlot).removeEnchantment(Enchantment.LURE);
		button.addUnsafeEnchantment(Enchantment.LURE, 1);
		button.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
		p.sendMessage(Chat.color("You will now recieve a " + button.getItemMeta().getDisplayName() + "&r every " + kills + " kills."));
		p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
		PlayerSettings.setSetting(p, streak, Utils.getID(button));
	}
}
