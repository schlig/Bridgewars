package bridgewars.menus;

import java.io.IOException;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import bridgewars.settings.Bows;
import bridgewars.settings.DoubleJump;
import bridgewars.settings.KillstreakRewards;
import bridgewars.settings.PacifistRewards;
import bridgewars.settings.Swords;
import bridgewars.settings.TimeLimit;
import bridgewars.settings._Settings;
import bridgewars.utils.Utils;

public class Settings {

	private static GUI menu = new GUI();
	private static TimeLimit time = new TimeLimit();
	
	public static void sendInput(Player p, Inventory inv, ItemStack button) throws IOException {
		
		loadSettings(p);
		
		switch(button.getType()) {
		case ARROW:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			p.openInventory(menu.getMain());
			break;
			
		case WATCH:
			if(p.isOp()) {
				p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
				p.openInventory(menu.getClock());
				ItemStack timerClock = new ItemStack(Material.WATCH, 1);
				ItemMeta amount = timerClock.getItemMeta();
				amount.setDisplayName(Utils.chat("&6Time Limit: " + time.getLimit().toString()));
				timerClock.setItemMeta(amount);
				p.getOpenInventory().setItem(13, timerClock);
			}
			else
				p.sendMessage(Utils.chat("&cYou do not have permission to do this."));
			break;
			
		case GOLD_SWORD:
			if(Swords.isState(Swords.ENABLED)) {
				Swords.setState(Swords.DISABLED);
				p.sendMessage(Utils.chat("Swords are now disabled"));
				modifyButton(p.getOpenInventory().getItem(12), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				Swords.setState(Swords.ENABLED);
				p.sendMessage(Utils.chat("Swords are now enabled"));
				modifyButton(p.getOpenInventory().getItem(12), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case BOW:
			if(Bows.isState(Bows.ENABLED)) {
				Bows.setState(Bows.DISABLED);
				p.sendMessage(Utils.chat("Bows are now disabled"));
				modifyButton(p.getOpenInventory().getItem(13), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				Bows.setState(Bows.ENABLED);
				p.sendMessage(Utils.chat("Bows are now enabled"));
				modifyButton(p.getOpenInventory().getItem(13), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case IRON_SWORD:
			if(KillstreakRewards.isState(KillstreakRewards.ENABLED)) {
				KillstreakRewards.setState(KillstreakRewards.DISABLED);
				p.sendMessage(Utils.chat("Killstreak Rewards are now disabled"));
				modifyButton(p.getOpenInventory().getItem(14), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				KillstreakRewards.setState(KillstreakRewards.ENABLED);
				p.sendMessage(Utils.chat("Killstreak Rewards are now enabled"));
				modifyButton(p.getOpenInventory().getItem(14), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case FEATHER:
			if(PacifistRewards.isState(PacifistRewards.ENABLED)) {
				PacifistRewards.setState(PacifistRewards.DISABLED);
				p.sendMessage(Utils.chat("Pacifist Rewards are now disabled"));
				modifyButton(p.getOpenInventory().getItem(15), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				PacifistRewards.setState(PacifistRewards.ENABLED);
				p.sendMessage(Utils.chat("Pacifist Rewards are now enabled"));
				modifyButton(p.getOpenInventory().getItem(15), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case RABBIT_FOOT:
			if(DoubleJump.isState(DoubleJump.ENABLED)) {
				DoubleJump.setState(DoubleJump.DISABLED);
				p.sendMessage(Utils.chat("Double Jumping is now disabled"));
				modifyButton(p.getOpenInventory().getItem(16), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				DoubleJump.setState(DoubleJump.ENABLED);
				p.sendMessage(Utils.chat("Double Jumping is now enabled"));
				modifyButton(p.getOpenInventory().getItem(16), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		default:
			break;
		}
		
		_Settings.save();
	}
	
	private static void modifyButton(ItemStack button, boolean state) {
		ItemMeta meta = button.getItemMeta();
		String tag = "";
		
		if(state) {
			button.addUnsafeEnchantment(Enchantment.LURE, 1);
			tag = "&aEnabled";
		}
		else {
			button.removeEnchantment(Enchantment.LURE);
			tag = "&cDisabled";
		}
		
		meta.setLore(Arrays.asList(Utils.chat(tag)));
		button.setItemMeta(meta);
	}
	
	public static void loadSettings(Player p) throws IOException {
		ItemStack timerClock = new ItemStack(Material.WATCH, 1);
		ItemMeta amount = timerClock.getItemMeta();
		amount.setDisplayName(Utils.chat("&6Time Limit: " + time.getLimit().toString()));
		timerClock.setItemMeta(amount);
		p.getOpenInventory().setItem(10, timerClock);
		
		if(Swords.isState(Swords.ENABLED))
			modifyButton(p.getOpenInventory().getItem(12), true);
		if(Bows.isState(Bows.ENABLED))
			modifyButton(p.getOpenInventory().getItem(13), true);
		if(KillstreakRewards.isState(KillstreakRewards.ENABLED))
			modifyButton(p.getOpenInventory().getItem(14), true);
		if(PacifistRewards.isState(PacifistRewards.ENABLED))
			modifyButton(p.getOpenInventory().getItem(15), true);
		if(DoubleJump.isState(DoubleJump.ENABLED))
			modifyButton(p.getOpenInventory().getItem(16), true);
	}
}
