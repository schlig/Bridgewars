package bridgewars.menus;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import bridgewars.items.CustomItems;
import bridgewars.settings.ChosenKillstreaks;
import bridgewars.utils.Message;

public class KillstreakEditor {

	private static GUI menu = new GUI();
	private static CustomItems items = new CustomItems();
	private static ChosenKillstreaks ks = new ChosenKillstreaks();
	
	public static void sendInput(Player p, Inventory inv, ItemStack button) {
		if(inv.getType().equals(InventoryType.PLAYER))
			return;
		
		switch(button.getType()) {
		case ARROW:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			p.openInventory(menu.getMain());
			break;
			
		case EGG:
			if(ks.getThreeStreak(p) == 0)
				break;
			ks.setThreeStreak(p, 0);
			if(p.getOpenInventory().getTopInventory().getItem(28).containsEnchantment(Enchantment.LURE))
				p.getOpenInventory().getTopInventory().getItem(28).removeEnchantment(Enchantment.LURE);
			button.addUnsafeEnchantment(Enchantment.LURE, 1);
			p.sendMessage(Message.chat("You will now receive 2 &f&lBridge Eggs&r every &l3&r kills."));
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			break;
			
		case MOB_SPAWNER:
			if(ks.getThreeStreak(p) == 1)
				break;
			ks.setThreeStreak(p, 1);
			if(p.getOpenInventory().getTopInventory().getItem(10).containsEnchantment(Enchantment.LURE))
				p.getOpenInventory().getTopInventory().getItem(10).removeEnchantment(Enchantment.LURE);
			button.addUnsafeEnchantment(Enchantment.LURE, 1);
			p.sendMessage(Message.chat("You will now receive a &f&lPortable Doink Hut&r every &l3&r kills."));
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			break;
			
		case WOOD_SWORD:
			if(ks.getFiveStreak(p) == 0)
				break;
			ks.setFiveStreak(p, 0);
			if(p.getOpenInventory().getTopInventory().getItem(30).containsEnchantment(Enchantment.LURE))
				p.getOpenInventory().getTopInventory().getItem(30).removeEnchantment(Enchantment.LURE);
			button.addUnsafeEnchantment(Enchantment.LURE, 1);
			p.sendMessage(Message.chat("You will now receive a &a&lHome Run Bat&r every &l5&r kills."));
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			break;
			
		case FIREBALL:
			if(ks.getFiveStreak(p) == 1)
				break;
			ks.setFiveStreak(p, 1);
			if(p.getOpenInventory().getTopInventory().getItem(12).containsEnchantment(Enchantment.LURE))
				p.getOpenInventory().getTopInventory().getItem(12).removeEnchantment(Enchantment.LURE);
			button.addUnsafeEnchantment(Enchantment.LURE, 1);
			p.sendMessage(Message.chat("You will now receive a &a&lFireball&r every &l5&r kills."));
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			break;
			
		case POTION:
			if(ks.getSevenStreak(p) == 0)
				break;
			ks.setSevenStreak(p, 0);
			if(p.getOpenInventory().getTopInventory().getItem(32).containsEnchantment(Enchantment.LURE))
				p.getOpenInventory().getTopInventory().getItem(32).removeEnchantment(Enchantment.LURE);
			PotionMeta meta = (PotionMeta) button.getItemMeta();
			meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 1, 1), false);
			button.setItemMeta(meta);
			p.sendMessage(Message.chat("You will now receive a &c&lLifeforce Potion&r every &l7&r kills."));
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			break;
			
		case SNOW_BALL:
			if(ks.getSevenStreak(p) == 1)
				break;
			ks.setSevenStreak(p, 1);
			ItemStack potion = new ItemStack(Material.POTION, 7, (short) 8192);
			ItemMeta pmeta = potion.getItemMeta();
			pmeta.setDisplayName(items.getItem(p, "lp", 1, true).getItemMeta().getDisplayName());
			pmeta.setLore(items.getItem(p, "lp", 1, true).getItemMeta().getLore());
			pmeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			potion.setItemMeta(pmeta);
			p.getOpenInventory().setItem(14, potion);
			button.addUnsafeEnchantment(Enchantment.LURE, 1);
			p.sendMessage(Message.chat("You will now receive a &c&lBlack Hole&r every &l7&r kills."));
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			break;
			
		default:
			break;
		}
	}
}
