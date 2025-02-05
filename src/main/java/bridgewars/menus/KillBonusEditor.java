package bridgewars.menus;

import java.io.IOException;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import bridgewars.messages.Chat;
import bridgewars.settings.GameSettings;

public class KillBonusEditor {

	private static GUI menu = new GUI();
	private static final int defaultKillBonus = 0;
	
	public static void sendInput(Player p, Inventory inv, ItemStack button) throws IOException {
		if(!(button.hasItemMeta()))
			return;

		ItemStack killBonus = inv.getItem(13);
		ItemMeta amount = killBonus.getItemMeta();
		
		
		switch(button.getType()) {
		case ARROW:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			p.openInventory(menu.getSettings());
			GameSettingsEditor.loadSettings(p);
			return;
			
		case WOOL:
			int value = button.getAmount();
			if(button.getDurability() == (short) 14) {
				if(GameSettings.getKillBonus() - value < 0) {
					p.sendMessage(Chat.color("&cYou can't set the kill bonus below 0 seconds!"));
					return;
				}
				GameSettings.setKillBonus((GameSettings.getKillBonus() - value));
				String s = "s";
				if(value == 1)
					s = "";
				p.sendMessage(Chat.color("Decreased bonus time by &c&l" + value +"&r second" + s + " &7(now " + GameSettings.getKillBonus() + ")"));
				p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			}
			
			else if(button.getDurability() == (short) 5) {
				String s = "s";
				if(value == 1)
					s = "";
				GameSettings.setKillBonus((GameSettings.getKillBonus() + value));
				p.sendMessage(Chat.color("Increased bonus time by &a&l" + value + "&r second" + s + " &7(now " + GameSettings.getKillBonus() + ")"));
				p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			}
			break;
			
		case DIAMOND_SWORD:
			GameSettings.setKillBonus(defaultKillBonus);
			p.sendMessage(Chat.color("&6Reset bonus time to 0 seconds"));
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			break;
			
		default:
		}
		amount.setDisplayName(Chat.color("&6Kill Bonus: " + GameSettings.getKillBonus()));
		killBonus.setItemMeta(amount);
		p.getOpenInventory().setItem(13, killBonus);
	}
}
