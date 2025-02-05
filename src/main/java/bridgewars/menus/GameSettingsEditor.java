package bridgewars.menus;

import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import bridgewars.messages.Chat;
import bridgewars.settings.GameSettings;
import bridgewars.settings.enums.Blocks;
import bridgewars.settings.enums.Bows;
import bridgewars.settings.enums.DigWars;
import bridgewars.settings.enums.DoubleHealth;
import bridgewars.settings.enums.FriendlyFire;
import bridgewars.settings.enums.GigaDrill;
import bridgewars.settings.enums.HidePlayers;
import bridgewars.settings.enums.IndestructibleMap;
import bridgewars.settings.enums.KillstreakRewards;
import bridgewars.settings.enums.NaturalItemSpawning;
import bridgewars.settings.enums.Piggyback;
import bridgewars.settings.enums.Quake;
import bridgewars.settings.enums.RandomTeams;
import bridgewars.settings.enums.RevealWinner;
import bridgewars.settings.enums.Shears;
import bridgewars.settings.enums.Swords;
import bridgewars.settings.enums.UnlockedInventory;
import bridgewars.settings.enums.WoolDecay;
import bridgewars.utils.Utils;

public class GameSettingsEditor {

	private static GUI menu = new GUI();
	
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
				amount.setDisplayName(Chat.color("&6Time Limit: " + GameSettings.getTimeLimit()));
				timerClock.setItemMeta(amount);
				p.getOpenInventory().setItem(13, timerClock);
			}
			else
				p.sendMessage(Chat.color("&cYou do not have permission to do this."));
			break;
			
		case DIAMOND_SWORD:
			if(p.isOp()) {
				p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
				p.openInventory(menu.getBonusClock());
				ItemStack killBonus = new ItemStack(Material.DIAMOND_SWORD, 1);
				ItemMeta amount = killBonus.getItemMeta();
				amount.setDisplayName(Chat.color("&6Kill Bonus: " + GameSettings.getKillBonus()));
				killBonus.setItemMeta(amount);
				p.getOpenInventory().setItem(13, killBonus);
			}
			else
				p.sendMessage(Chat.color("&cYou do not have permission to do this."));
			break;
			
		case GOLD_SWORD:
			Swords.toggle();
			p.sendMessage(Chat.color("Swords are now ") + Swords.getState().toString().toLowerCase());
			modifyButton(button, Swords.getState().isEnabled(), true, p);
			break;
		
		case WOOL:
			Blocks.toggle(); 
			p.sendMessage(Chat.color("Blocks are now ") + Blocks.getState().toString().toLowerCase());
			modifyButton(button, Blocks.getState().isEnabled(), true, p);
			break;
			
		case SHEARS:
			Shears.toggle(); 
			p.sendMessage(Chat.color("Shears are now ") + Shears.getState().toString().toLowerCase());
			modifyButton(button, Shears.getState().isEnabled(), true, p);
			break;
			
		case IRON_SWORD:
			KillstreakRewards.toggle(); 
			p.sendMessage(Chat.color("Killstreak Rewards are now ") + KillstreakRewards.getState().toString().toLowerCase());
			modifyButton(button, KillstreakRewards.getState().isEnabled(), true, p);
			break;
			
		case BOW:
			Bows.toggle(); 
			p.sendMessage(Chat.color("Bows are now ") + Bows.getState().toString().toLowerCase());
			modifyButton(button, Bows.getState().isEnabled(), true, p);
			break;
			
		case GOLDEN_APPLE:
			DoubleHealth.toggle();
			p.sendMessage(Chat.color("Double Health is now ") + DoubleHealth.getState().toString().toLowerCase());
			modifyButton(button, DoubleHealth.getState().isEnabled(), true, p);
			for(Player players : Bukkit.getOnlinePlayers()) {
				if(DoubleHealth.getState().isEnabled())
					players.setMaxHealth(40);
				else players.setMaxHealth(20);
				Utils.heal(players);
			}
			break;
			
		case RABBIT_FOOT:
			RevealWinner.toggle(); 
			p.sendMessage(Chat.color("Reveal Winner is now ") + RevealWinner.getState().toString().toLowerCase());
			modifyButton(button, RevealWinner.getState().isEnabled(), true, p);
			break;
			
		case DIAMOND_PICKAXE:
			GigaDrill.toggle(); 
			p.sendMessage(Chat.color("Giga Drill is now ") + GigaDrill.getState().toString().toLowerCase());
			modifyButton(button, GigaDrill.getState().isEnabled(), true, p);
			break;
			
		case WOOD:
			DigWars.toggle(); 
			p.sendMessage(Chat.color("Dig Wars is now ") + DigWars.getState().toString().toLowerCase());
			modifyButton(button, DigWars.getState().isEnabled(), true, p);
			break;
			
		case DIAMOND:
			NaturalItemSpawning.toggle(); 
			p.sendMessage(Chat.color("Natural Item Spawning is now ") + NaturalItemSpawning.getState().toString().toLowerCase());
			modifyButton(button, NaturalItemSpawning.getState().isEnabled(), true, p);
			break;
			
		case BLAZE_POWDER:
			FriendlyFire.toggle(); 
			p.sendMessage(Chat.color("Friendly Fire is now ") + FriendlyFire.getState().toString().toLowerCase());
			modifyButton(button, FriendlyFire.getState().isEnabled(), true, p);
			break;
			
		case SADDLE:
			Piggyback.toggle(); 
			p.sendMessage(Chat.color("Piggyback is now ") + Piggyback.getState().toString().toLowerCase());
			modifyButton(button, Piggyback.getState().isEnabled(), true, p);
			break;
			
		case EYE_OF_ENDER:
			RandomTeams.toggle(); 
			p.sendMessage(Chat.color("Random Teams are now ") + RandomTeams.getState().toString().toLowerCase());
			modifyButton(button, RandomTeams.getState().isEnabled(), true, p);
			break;
			
		case RABBIT_HIDE:
			HidePlayers.toggle(); 
			p.sendMessage(Chat.color("Hide Players is now ") + HidePlayers.getState().toString().toLowerCase());
			modifyButton(button, HidePlayers.getState().isEnabled(), true, p);
			break;
			
		case BEDROCK:
			IndestructibleMap.toggle(); 
			p.sendMessage(Chat.color("Indestructible Map is now ") + IndestructibleMap.getState().toString().toLowerCase());
			modifyButton(button, IndestructibleMap.getState().isEnabled(), true, p);
			break;
			
		case GLASS:
			WoolDecay.toggle(); 
			p.sendMessage(Chat.color("Wool Decay is now ") + WoolDecay.getState().toString().toLowerCase());
			modifyButton(button, WoolDecay.getState().isEnabled(), true, p);
			break;
			
		case WORKBENCH:
			UnlockedInventory.toggle(); 
			p.sendMessage(Chat.color("Unlocked Inventory is now ") + UnlockedInventory.getState().toString().toLowerCase());
			modifyButton(button, UnlockedInventory.getState().isEnabled(), true, p);
			break;
			
		case BLAZE_ROD:
			Quake.toggle(); 
			p.sendMessage(Chat.color("Quake is now ") + Quake.getState().toString().toLowerCase());
			modifyButton(button, Quake.getState().isEnabled(), true, p);
			break;
			
		default:
			break;
		}
		
		bridgewars.settings.GameSettings.save();
	}
	
	private static void modifyButton(ItemStack button, boolean state) {
		modifyButton(button, state, false, null);
	}
	
	private static void modifyButton(ItemStack button, boolean state, boolean playsound, Player p) {
		ItemMeta meta = button.getItemMeta();
		List<String> text = meta.getLore();
		
		if(state) {
			if(playsound)
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
			text.set(0, Chat.color("&aEnabled"));
			meta.addEnchant(Enchantment.LURE, 1, true);
		}
		else {
			if(playsound)
				p.playSound(p.getLocation(), Sound.CLICK, 0.8f, 1f);
			text.set(0, Chat.color("&cDisabled"));
			meta.removeEnchant(Enchantment.LURE);
		}
		
		meta.setLore(text);
		button.setItemMeta(meta);
	}
	
	public static void loadSettings(Player p) throws IOException {
		InventoryView inv = p.getOpenInventory();
		
		ItemStack timerClock = inv.getItem(10);
		ItemMeta meta = timerClock.getItemMeta();
		meta.setDisplayName(Chat.color("&6Time Limit: " + GameSettings.getTimeLimit()));
		timerClock.setItemMeta(meta);
		inv.setItem(10, timerClock);

		ItemStack killBonus = inv.getItem(11);
		meta = killBonus.getItemMeta();
		meta.setDisplayName(Chat.color("&6Kill Bonus: " + GameSettings.getKillBonus()));
		killBonus.setItemMeta(meta);
		inv.setItem(11, killBonus);
		
		modifyButton(inv.getItem(12), Swords.getState().isEnabled());
		modifyButton(inv.getItem(13), Blocks.getState().isEnabled());
		modifyButton(inv.getItem(14), Shears.getState().isEnabled());
		modifyButton(inv.getItem(15), KillstreakRewards.getState().isEnabled());
		modifyButton(inv.getItem(16), Bows.getState().isEnabled());
		
		modifyButton(inv.getItem(19), DoubleHealth.getState().isEnabled());
		modifyButton(inv.getItem(20), RevealWinner.getState().isEnabled());
		modifyButton(inv.getItem(21), GigaDrill.getState().isEnabled());
		modifyButton(inv.getItem(22), DigWars.getState().isEnabled());
		modifyButton(inv.getItem(23), NaturalItemSpawning.getState().isEnabled());
		modifyButton(inv.getItem(24), FriendlyFire.getState().isEnabled());
		modifyButton(inv.getItem(25), Piggyback.getState().isEnabled());
		
		modifyButton(inv.getItem(28), RandomTeams.getState().isEnabled());
		modifyButton(inv.getItem(29), HidePlayers.getState().isEnabled());
		modifyButton(inv.getItem(30), IndestructibleMap.getState().isEnabled());
		modifyButton(inv.getItem(31), WoolDecay.getState().isEnabled());
		modifyButton(inv.getItem(32), UnlockedInventory.getState().isEnabled());
		modifyButton(inv.getItem(33), Quake.getState().isEnabled());
	}
}
