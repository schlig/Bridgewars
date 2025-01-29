package bridgewars.menus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import bridgewars.game.CSManager;
import bridgewars.game.Game;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.settings.PlayerSettings;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.ItemManager;

public class MainMenu {

	private static GUI menu = new GUI();
	
	public static void sendInput(Player p, Inventory inv, ItemStack button) throws IOException {
		switch(button.getType()) {
		case LADDER:
			p.closeInventory();
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			Game.startGame(p, false);
			break;
			
		case REDSTONE_COMPARATOR:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			p.openInventory(menu.getSettings());
			GameSettingsEditor.loadSettings(p);
			
			break;
			
		case MAP:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			menu.buildMapPages();
			p.openInventory(menu.getMapPage(0));

			MapRotation.loadMapSettings(p, 0);
			break;
			
		case TNT:
			if(p.isOp()) {
				p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
				if(GameState.isState(GameState.INACTIVE)) {
					p.sendMessage(Chat.color("&cThere is no game in progress."));
					p.getOpenInventory().close();
					return;
				}
				
				Game.endGame(p, true);
				p.sendMessage(Chat.color("&7Ended the game"));
			}
			else
				p.sendMessage(Chat.color("&cYou do not have permission to do this."));
			break;
			
		case SADDLE:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			if(GameState.isState(GameState.INACTIVE)) {
				p.sendMessage(Chat.color("&cThere is no game in progress."));
				p.getOpenInventory().close();
				return;
			}
			Game.joinGame(p);
			p.sendMessage(Chat.color("&6You joined the game."));
			break;
			
		case BOOK:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			Inventory hotbarEditor = Bukkit.createInventory(null, 45, "Hotbar Layout");
			hotbarEditor.setContents(menu.getHotbar().getContents());
			p.openInventory(hotbarEditor);
			
			p.getOpenInventory().setItem(Integer.parseInt(PlayerSettings.getSetting(p, "SwordSlot")) + 18, new ItemStack(Material.GOLD_SWORD));
			p.getOpenInventory().setItem(Integer.parseInt(PlayerSettings.getSetting(p, "ShearsSlot")) + 18, new ItemStack(Material.SHEARS));
			p.getOpenInventory().setItem(Integer.parseInt(PlayerSettings.getSetting(p, "WoolSlot")) + 18, new ItemStack(Material.WOOL));
			
			break;
			
		case IRON_SWORD: //killstreak menu
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			Inventory killstreakEditor = Bukkit.createInventory(null, 45, "Killstreak Bonuses");
			killstreakEditor.setContents(menu.getKS().getContents());
			p.openInventory(killstreakEditor);
			
			//main items
			loadSlot(p, 10, "BridgeEgg", "KillstreakRewardWhite");
			loadSlot(p, 28, "MysteryPill", "KillstreakRewardWhite");

			loadSlot(p, 12, "PortableDoinkHut", "KillstreakRewardGreen");
			loadSlot(p, 30, "Fireball", "KillstreakRewardGreen");
			
			loadSlot(p, 14, "HomeRunBat", "KillstreakRewardRed");
			loadSlot(p, 32, "BlackHole", "KillstreakRewardRed");

			loadSlot(p, 16, "HeartContainer", "KillstreakRewardBlue");
			loadSlot(p, 34, "MagicStopwatch", "KillstreakRewardBlue");
			break;
			
		case WOOL:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			Inventory inventory = menu.getTeam();
			p.openInventory(inventory);

			String team = CSManager.getTeam(p);
			
			for(int s = 19; s <= 25; s += 2)
				if(inventory.getItem(s).getItemMeta().getDisplayName().toLowerCase().contains(team))
					inventory.getItem(s).addUnsafeEnchantment(Enchantment.LURE, 1);
			break;
			
		case BARRIER:
			p.closeInventory();
			break;
			
		default:
			break;
		}
	}
	
	private static void loadSlot(Player p, int slot, String itemID, String setting) {
		ItemStack item = ItemManager.getItem(itemID).createItem(null);
		ItemBuilder.hideFlags(item);
		p.getOpenInventory().setItem(slot, item);
		ItemStack button = p.getOpenInventory().getItem(slot);
		
		if(PlayerSettings.getSetting(p, setting).equalsIgnoreCase(itemID))
			button.addUnsafeEnchantment(Enchantment.LURE, 1);
		else {
			List<Enchantment> enchantments = new ArrayList<>();
			for(Enchantment enchantment : button.getEnchantments().keySet())
				enchantments.add(enchantment);
			for(Enchantment enchantment : enchantments)
				button.removeEnchantment(enchantment);
		}
	}
}
