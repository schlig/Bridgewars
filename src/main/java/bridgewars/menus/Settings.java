package bridgewars.menus;

import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import bridgewars.messages.Chat;
import bridgewars.settings.Blocks;
import bridgewars.settings.Bows;
import bridgewars.settings.DigWars;
import bridgewars.settings.DoubleHealth;
import bridgewars.settings.DoubleJump;
import bridgewars.settings.FriendlyFire;
import bridgewars.settings.GigaDrill;
import bridgewars.settings.KillstreakRewards;
import bridgewars.settings.NaturalItemSpawning;
import bridgewars.settings.Piggyback;
import bridgewars.settings.RandomTeams;
import bridgewars.settings.Shears;
import bridgewars.settings.Swords;
import bridgewars.settings.TimeLimit;

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
				amount.setDisplayName(Chat.color("&6Time Limit: " + time.getLimit().toString()));
				timerClock.setItemMeta(amount);
				p.getOpenInventory().setItem(13, timerClock);
			}
			else
				p.sendMessage(Chat.color("&cYou do not have permission to do this."));
			break;
			
		case GOLD_SWORD:
			if(Swords.isState(Swords.ENABLED)) {
				Swords.setState(Swords.DISABLED);
				p.sendMessage(Chat.color("Swords are now disabled"));
				modifyButton(p.getOpenInventory().getItem(12), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				Swords.setState(Swords.ENABLED);
				p.sendMessage(Chat.color("Swords are now enabled"));
				modifyButton(p.getOpenInventory().getItem(12), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
		
		case WOOL:
			if(Blocks.isState(Blocks.ENABLED)) {
				Blocks.setState(Blocks.DISABLED);
				p.sendMessage(Chat.color("Blocks are now disabled"));
				modifyButton(p.getOpenInventory().getItem(13), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				Blocks.setState(Blocks.ENABLED);
				p.sendMessage(Chat.color("Blocks are now enabled"));
				modifyButton(p.getOpenInventory().getItem(13), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case SHEARS:
			if(Shears.isState(Shears.ENABLED)) {
				Shears.setState(Shears.DISABLED);
				p.sendMessage(Chat.color("Shears are now disabled"));
				modifyButton(p.getOpenInventory().getItem(14), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				Shears.setState(Shears.ENABLED);
				p.sendMessage(Chat.color("Shears are now enabled"));
				modifyButton(p.getOpenInventory().getItem(14), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case IRON_SWORD:
			if(KillstreakRewards.isState(KillstreakRewards.ENABLED)) {
				KillstreakRewards.setState(KillstreakRewards.DISABLED);
				p.sendMessage(Chat.color("Killstreak Rewards are now disabled"));
				modifyButton(p.getOpenInventory().getItem(15), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				KillstreakRewards.setState(KillstreakRewards.ENABLED);
				p.sendMessage(Chat.color("Killstreak Rewards are now enabled"));
				modifyButton(p.getOpenInventory().getItem(15), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case BOW:
			if(Bows.isState(Bows.ENABLED)) {
				Bows.setState(Bows.DISABLED);
				p.sendMessage(Chat.color("Bows are now disabled"));
				modifyButton(p.getOpenInventory().getItem(16), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				Bows.setState(Bows.ENABLED);
				p.sendMessage(Chat.color("Bows are now enabled"));
				modifyButton(p.getOpenInventory().getItem(16), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case GOLDEN_APPLE:
			if(DoubleHealth.isState(DoubleHealth.ENABLED)) {
				DoubleHealth.setState(DoubleHealth.DISABLED);
				p.sendMessage(Chat.color("Double Health now disabled"));
				modifyButton(p.getOpenInventory().getItem(19), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
				for(Player player : Bukkit.getOnlinePlayers()) {
					player.setHealth(20);
					player.setMaxHealth(20);
				}
			}
			else {
				DoubleHealth.setState(DoubleHealth.ENABLED);
				p.sendMessage(Chat.color("Double Health is now enabled"));
				modifyButton(p.getOpenInventory().getItem(19), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
				for(Player player : Bukkit.getOnlinePlayers()) {
					player.setMaxHealth(40);
					player.setHealth(40);
				}
			}
			break;
			
		case RABBIT_FOOT:
			if(DoubleJump.isState(DoubleJump.ENABLED)) {
				DoubleJump.setState(DoubleJump.DISABLED);
				p.sendMessage(Chat.color("Double Jump is now disabled"));
				modifyButton(p.getOpenInventory().getItem(20), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				DoubleJump.setState(DoubleJump.ENABLED);
				p.sendMessage(Chat.color("Double Jump is now enabled"));
				modifyButton(p.getOpenInventory().getItem(20), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case DIAMOND_PICKAXE:
			if(GigaDrill.isState(GigaDrill.ENABLED)) {
				GigaDrill.setState(GigaDrill.DISABLED);
				p.sendMessage(Chat.color("Giga Drill is now disabled"));
				modifyButton(p.getOpenInventory().getItem(21), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				GigaDrill.setState(GigaDrill.ENABLED);
				p.sendMessage(Chat.color("Giga Drill is now enabled"));
				modifyButton(p.getOpenInventory().getItem(21), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case WOOD:
			if(DigWars.isState(DigWars.ENABLED)) {
				DigWars.setState(DigWars.DISABLED);
				p.sendMessage(Chat.color("Dig Wars is now disabled"));
				modifyButton(p.getOpenInventory().getItem(22), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				DigWars.setState(DigWars.ENABLED);
				p.sendMessage(Chat.color("Dig Wars is now enabled"));
				modifyButton(p.getOpenInventory().getItem(22), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case DIAMOND:
			if(NaturalItemSpawning.isState(NaturalItemSpawning.ENABLED)) {
				NaturalItemSpawning.setState(NaturalItemSpawning.DISABLED);
				p.sendMessage(Chat.color("Natural Item Spawning is now disabled"));
				modifyButton(p.getOpenInventory().getItem(23), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				NaturalItemSpawning.setState(NaturalItemSpawning.ENABLED);
				p.sendMessage(Chat.color("Natural Item Spawning is now enabled"));
				modifyButton(p.getOpenInventory().getItem(23), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case BLAZE_POWDER:
			if(FriendlyFire.isState(FriendlyFire.ENABLED)) {
				FriendlyFire.setState(FriendlyFire.DISABLED);
				p.sendMessage(Chat.color("Friendly Fire is now disabled"));
				modifyButton(p.getOpenInventory().getItem(24), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				FriendlyFire.setState(FriendlyFire.ENABLED);
				p.sendMessage(Chat.color("Friendly Fire is now enabled"));
				modifyButton(p.getOpenInventory().getItem(24), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case SADDLE:
			if(Piggyback.isState(Piggyback.ENABLED)) {
				Piggyback.setState(Piggyback.DISABLED);
				p.sendMessage(Chat.color("Piggyback is now disabled"));
				modifyButton(p.getOpenInventory().getItem(25), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				Piggyback.setState(Piggyback.ENABLED);
				p.sendMessage(Chat.color("Piggyback is now enabled"));
				modifyButton(p.getOpenInventory().getItem(25), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		case EYE_OF_ENDER:
			if(RandomTeams.isState(RandomTeams.ENABLED)) {
				RandomTeams.setState(RandomTeams.DISABLED);
				p.sendMessage(Chat.color("Random Teams are now disabled"));
				modifyButton(p.getOpenInventory().getItem(28), false);
				p.playSound(p.getLocation(), Sound.CLICK, .8F, 1F);
			}
			else {
				RandomTeams.setState(RandomTeams.ENABLED);
				p.sendMessage(Chat.color("Random Teams are now enabled"));
				modifyButton(p.getOpenInventory().getItem(28), true);
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
			}
			break;
			
		default:
			break;
		}
		
		bridgewars.settings.Settings.save();
	}
	
	private static void modifyButton(ItemStack button, boolean state) {
		ItemMeta meta = button.getItemMeta();
		List<String> text = meta.getLore();
		
		if(state)
			text.set(0,  Chat.color("&aEnabled"));
		else
			text.set(0, Chat.color("&cDisabled"));
		meta.setLore(text);
		button.setItemMeta(meta);
		
		if(state)
			button.addUnsafeEnchantment(Enchantment.LURE, 1);
		else
			button.removeEnchantment(Enchantment.LURE);
	}
	
	public static void loadSettings(Player p) throws IOException {
		ItemStack timerClock = new ItemStack(Material.WATCH, 1);
		ItemMeta amount = timerClock.getItemMeta();
		amount.setDisplayName(Chat.color("&6Time Limit: " + time.getLimit().toString()));
		timerClock.setItemMeta(amount);
		p.getOpenInventory().setItem(10, timerClock);
		
		if(Swords.isState(Swords.ENABLED))
			modifyButton(p.getOpenInventory().getItem(12), true);
		if(Blocks.isState(Blocks.ENABLED))
			modifyButton(p.getOpenInventory().getItem(13), true);
		if(Shears.isState(Shears.ENABLED))
			modifyButton(p.getOpenInventory().getItem(14), true);
		if(KillstreakRewards.isState(KillstreakRewards.ENABLED))
			modifyButton(p.getOpenInventory().getItem(15), true);
		if(Bows.isState(Bows.ENABLED))
			modifyButton(p.getOpenInventory().getItem(16), true);
		
		if(DoubleHealth.isState(DoubleHealth.ENABLED))
			modifyButton(p.getOpenInventory().getItem(19), true);
		if(DoubleJump.isState(DoubleJump.ENABLED))
			modifyButton(p.getOpenInventory().getItem(20), true);
		if(GigaDrill.isState(GigaDrill.ENABLED))
			modifyButton(p.getOpenInventory().getItem(21), true);
		if(DigWars.isState(DigWars.ENABLED))
			modifyButton(p.getOpenInventory().getItem(22), true);
		if(NaturalItemSpawning.isState(NaturalItemSpawning.ENABLED))
			modifyButton(p.getOpenInventory().getItem(23), true);
		if(FriendlyFire.isState(FriendlyFire.ENABLED))
			modifyButton(p.getOpenInventory().getItem(24), true);
		if(Piggyback.isState(Piggyback.ENABLED))
			modifyButton(p.getOpenInventory().getItem(25), true);
		if(RandomTeams.isState(RandomTeams.ENABLED))
			modifyButton(p.getOpenInventory().getItem(28), true);
	}
}
