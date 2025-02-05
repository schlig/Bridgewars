package bridgewars.menus;

import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import bridgewars.messages.Chat;
import bridgewars.settings.GameSettings;

public class TimeLimitEditor {

	private static GUI menu = new GUI();
	private static final int defaultTimeLimit = 150;
	private static final int minimumTimeLimit = 35;
	
	public static void sendInput(Player p, Inventory inv, ItemStack button) throws IOException {
		if(!(button.hasItemMeta()))
			return;

		ItemStack timerClock = new ItemStack(Material.WATCH, 1);
		ItemMeta amount = timerClock.getItemMeta();
		
		
		switch(button.getType()) {
		case ARROW:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			p.openInventory(menu.getSettings());
			GameSettingsEditor.loadSettings(p);
			return;
			
		case WOOL:
			int value = button.getAmount();
			if(button.getDurability() == (short) 14) {
				if(GameSettings.getTimeLimit() - value < minimumTimeLimit) {
					p.sendMessage(Chat.color("&cYou can't set the time limit below " + minimumTimeLimit + " seconds!"));
					return;
				}
				GameSettings.setTimeLimit((GameSettings.getTimeLimit() - value));
				p.sendMessage(Chat.color("Decreased the time limit by &c&l" + value +"&r seconds &7(now " + GameSettings.getTimeLimit() + ")"));
				p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			}
			
			else if(button.getDurability() == (short) 5) {
				GameSettings.setTimeLimit((GameSettings.getTimeLimit() + value));
				p.sendMessage(Chat.color("Increased the time limit by &a&l" + value + "&r seconds &7(now " + GameSettings.getTimeLimit() + ")"));
				p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			}
			break;
			
		case WATCH:
			GameSettings.setTimeLimit(defaultTimeLimit);
			p.sendMessage(Chat.color("&6Reset the time limit to 150 seconds"));
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			break;
			
		default:
		}
		amount.setDisplayName(Chat.color("&6Time Limit: " + GameSettings.getTimeLimit()));
		timerClock.setItemMeta(amount);
		p.getOpenInventory().setItem(13, timerClock);
	}
}
