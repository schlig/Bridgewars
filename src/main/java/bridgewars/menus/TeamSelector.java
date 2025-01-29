package bridgewars.menus;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import bridgewars.game.CSManager;
import bridgewars.utils.ItemBuilder;

public class TeamSelector {
	
	private static GUI menu = new GUI();
	
	public static void sendInput(Player p, Inventory inv, ItemStack button) {
		if(inv.getType().equals(InventoryType.PLAYER))
			return;
		
		for(int i = 19; i <= 25; i += 2)
			if(p.getOpenInventory().getItem(i).containsEnchantment(Enchantment.LURE))
				p.getOpenInventory().getItem(i).removeEnchantment(Enchantment.LURE);
		
		if(button.getType() == Material.ARROW
		&& button.getItemMeta().getDisplayName().contains("Go Back")) {
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			p.openInventory(menu.getMain());
			return;
		}
		
		else if(button.getType() == Material.WOOL) {
			String team;
			if(CSManager.hasTeam(p))
				team = CSManager.getTeam(p);
			else
				team = "none";
			
			switch(button.getDurability()) {
			case (short)0:
				if(CSManager.hasTeam(p)) {
					p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
					CSManager.resetTeam(p, true);
				}
				break;
			case (short)14:
				selectButton(p, team, "red", button);
				break;
			case (short)3:
				selectButton(p, team, "blue", button);
				break;
			case (short)5:
				selectButton(p, team, "green", button);
				break;
			case (short)4:
				selectButton(p, team, "yellow", button);
			}
		}
	}
	
	private static void selectButton(Player p, String team, String newTeam, ItemStack button) {
		if(!button.getItemMeta().getDisplayName().toLowerCase().contains(team)) {
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
			ItemBuilder.hideFlags(button);
			button.addUnsafeEnchantment(Enchantment.LURE, 1);
			CSManager.setTeam(p, newTeam);
		}
	}
}
