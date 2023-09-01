package bridgewars.menus;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import bridgewars.game.CustomScoreboard;

public class TeamSelector {
	
	private static GUI menu = new GUI();
	private static CustomScoreboard cs = new CustomScoreboard();
	
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
			if(cs.hasTeam(p))
				team = cs.getTeam(p);
			else
				team = "none";
			
			switch(button.getDurability()) {
			case (short)0:
				if(cs.hasTeam(p)) {
					p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
					cs.resetTeam(p, true);
				}
				break;
			case (short)14:
				if(!button.getItemMeta().getDisplayName().toLowerCase().contains(team)) {
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
					button.addUnsafeEnchantment(Enchantment.LURE, 1);
					cs.setTeam(p, "red");
				}
				break;
			case (short)3:
				if(!button.getItemMeta().getDisplayName().toLowerCase().contains(team)) {
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
					button.addUnsafeEnchantment(Enchantment.LURE, 1);
					cs.setTeam(p, "blue");
				}
				break;
			case (short)5:
				if(!button.getItemMeta().getDisplayName().toLowerCase().contains(team)) {
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
					button.addUnsafeEnchantment(Enchantment.LURE, 1);
					cs.setTeam(p, "green");
				}
				break;
			case (short)4:
				if(!button.getItemMeta().getDisplayName().toLowerCase().contains(team)) {
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
					button.addUnsafeEnchantment(Enchantment.LURE, 1);
					cs.setTeam(p, "yellow");
				}
			}
		}
	}
}
