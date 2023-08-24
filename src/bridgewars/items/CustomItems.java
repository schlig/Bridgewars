package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import bridgewars.game.CustomScoreboard;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;

public class CustomItems {
	
	private CustomScoreboard cs;
	
	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggings;
	private ItemStack boots;
	
	private ItemStack sword;
	private ItemStack shears;
	private ItemStack blocks;
	
	private ItemStack bow;
	private ItemStack axe;
	private ItemStack gigaShears;

	private ItemStack bridgeEgg;
	private ItemStack portableDoinkHut;
	private ItemStack fireball;
	private ItemStack homeRunBat;
	private ItemStack lifeforcePotion;
	private ItemStack blackHole;
	private ItemStack depression;
	
	private List<ItemStack> whiteItems = new ArrayList<>();
	private List<ItemStack> greenItems = new ArrayList<>();
	private List<ItemStack> redItems = new ArrayList<>();
	
	public CustomItems() {
		
		cs = new CustomScoreboard();
		
		//armor
		helmet = new ItemStack(Material.LEATHER_HELMET, 1);
		ItemBuilder.setUnbreakable(helmet, true);
		
		chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		ItemBuilder.setUnbreakable(chestplate, true);
		
		leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		ItemBuilder.setUnbreakable(leggings, true);
		
		boots = new ItemStack(Material.LEATHER_BOOTS, 1);
		ItemBuilder.setUnbreakable(boots, true);
		
		//basic items
		sword = new ItemStack(Material.GOLD_SWORD, 1);
		ItemBuilder.setUnbreakable(sword, true);
		
		shears = new ItemStack(Material.SHEARS, 1);
		shears.addEnchantment(Enchantment.DIG_SPEED, 3);
		ItemBuilder.setUnbreakable(shears, true);

		gigaShears = new ItemStack(Material.SHEARS, 1);
		gigaShears.addEnchantment(Enchantment.DIG_SPEED, 5);
		ItemBuilder.setUnbreakable(gigaShears, true);
		
		blocks = new ItemStack(Material.WOOL, 64);

		bow = new ItemStack(Material.BOW, 1);
		bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		ItemBuilder.setUnbreakable(bow, true);
		
		axe = new ItemStack(Material.STONE_AXE, 1);
		axe.addEnchantment(Enchantment.DIG_SPEED, 5);
		ItemBuilder.setUnbreakable(axe,true);
		
		//killstreak rewards
		bridgeEgg = new ItemStack(Material.EGG, 1);
		ItemBuilder.setName(bridgeEgg, "&fBridge Egg");
		ItemBuilder.setLore(bridgeEgg, Arrays.asList(Message.chat("&r&7Automatically builds a bridge for you")));
		
		portableDoinkHut = new ItemStack(Material.MOB_SPAWNER, 1);
		ItemBuilder.setName(portableDoinkHut, "&fPortable Doink Hut");
		ItemBuilder.setLore(portableDoinkHut, Arrays.asList(Message.chat("&r&7Builds an instant house"),
												Message.chat("&r&7where you're standing")));
		
		fireball = new ItemStack(Material.FIREBALL, 1);
		ItemBuilder.setName(fireball, "&aFireball");
		ItemBuilder.setLore(fireball, Arrays.asList(Message.chat("&r&7Throws an exploding fireball"),
										Message.chat("&r&7that deals heavy knockback")));
		
		homeRunBat = new ItemStack(Material.WOOD_SWORD, 1);
		homeRunBat.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
		ItemBuilder.setName(homeRunBat, "&aHome Run Bat");
		ItemBuilder.setLore(homeRunBat, Arrays.asList(Message.chat("&r&7Deals massive knockback"),
										  Message.chat("&r&7Only has 3 uses")));
		
		lifeforcePotion = new ItemStack(Material.POTION, 1);
		lifeforcePotion.setDurability((short) 8233);
		ItemMeta meta = lifeforcePotion.getItemMeta();
		PotionMeta effect = (PotionMeta) meta;
		effect.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0), true);
		effect.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1200, 4), true);
		meta = effect;
		lifeforcePotion.setItemMeta(meta);
		lifeforcePotion.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ItemBuilder.setName(lifeforcePotion, "&cLifeforce Potion");
		ItemBuilder.setLore(lifeforcePotion, Arrays.asList(Message.chat("&r&7Strength I (0:30)"), 
											   Message.chat("&r&7Absorption V (1:00)")));
		
		blackHole = new ItemStack(Material.SNOW_BALL, 1);
		ItemBuilder.setName(blackHole, "&cBlack Hole");
		ItemBuilder.setLore(blackHole, Arrays.asList(Message.chat("&r&7A throwable black hole that eats"),
										 Message.chat("&r&7every block in its path")));
		
		depression = new ItemStack(Material.GHAST_TEAR, 1);
		ItemBuilder.setName(depression, "&cDepression");
		ItemBuilder.setLore(depression, Arrays.asList(Message.chat("&r&7Banishes a player to the"),
										  Message.chat("&r&7Sad Room for 15 seconds")));
		
		createTierList();
	}
	
	private void createTierList() {
		whiteItems.add(bridgeEgg);
		whiteItems.add(portableDoinkHut);
		
		greenItems.add(fireball);
		greenItems.add(homeRunBat);
		
		redItems.add(lifeforcePotion);
		redItems.add(blackHole);
		redItems.add(depression);
	}
	
	public ItemStack getItem(Player p, String id) {
		return getItem(p, id, 1, false);
	}
	
	public ItemStack getItem(Player p, String id, int amount) {
		return getItem(p, id, amount, false);
	}
	
	public ItemStack getItem(Player p, String id, int amount, boolean hideFlags) {
		ItemStack item = new ItemStack(Material.AIR, 0);
		String s = null;
		if(p != null)
			s = cs.getTeam(p);
		
		switch(id) {
		case "helmet":
			item = helmet;
			ItemBuilder.setLeatherColor(p, item, s);
			ItemBuilder.setName(item, "&r" + s.substring(0, 1).toUpperCase() + s.substring(1) + " Helmet");
			break;
			
		case "chestplate":
			item = chestplate;
			ItemBuilder.setLeatherColor(p, item, s);
			ItemBuilder.setName(item, "&r" + s.substring(0, 1).toUpperCase() + s.substring(1) + " Chestplate");
			break;
			
		case "leggings":
			item = leggings;
			ItemBuilder.setLeatherColor(p, item, s);
			ItemBuilder.setName(item, "&r" + s.substring(0, 1).toUpperCase() + s.substring(1) + " Leggings");
			break;
			
		case "boots":
			item = boots;
			ItemBuilder.setLeatherColor(p, item, s);
			ItemBuilder.setName(item, "&r" + s.substring(0, 1).toUpperCase() + s.substring(1) + " Boots");
			break;
			
		case "sword":
			item = sword;
			ItemBuilder.setName(item, "&r" + s.substring(0, 1).toUpperCase() + s.substring(1) + " Sword");
			break;
			
		case "bow":
			item = bow;
			ItemBuilder.setName(item, "&r" + s.substring(0, 1).toUpperCase() + s.substring(1) + " Bow");
			break;
			
		case "blocks":
			item = blocks;
			switch(cs.getTeam(p)) {
			case "red":
				item.setDurability((short) 14);
				break;
			case "blue":
				item.setDurability((short) 3);
				break;
			case "green":
				item.setDurability((short) 5);
				break;
			case "yellow":
				item.setDurability((short) 4);
				break;
			}
			break;
			
		case "shears":
			item = shears;
			break;
			
		case "gigashears":
			item = gigaShears;
			break;
			
		case "axe":
			item = axe;
			break;
			
		case "be":
			item = bridgeEgg;
			break;
			
		case "pdh":
			item = portableDoinkHut;
			break;
			
		case "hrb":
			item = homeRunBat;
			break;
			
		case "fb":
			item = fireball;
			break;
			
		case "lp":
			item = lifeforcePotion;
			break;
			
		case "bh":
			item = blackHole;
			break;
			
		case "d":
			item = depression;
			break;
		}
		if(hideFlags)
			ItemBuilder.hideFlags(item);
		item.setAmount(amount);
		return item;
	}
	
	public ItemStack getWhiteItem() {
		return whiteItems.get(Utils.rand(whiteItems.size()));
	}
	
	public ItemStack getGreenItem() {
		return greenItems.get(Utils.rand(greenItems.size()));
	}
	
	public ItemStack getRedItem() {
		return redItems.get(Utils.rand(redItems.size()));
	}
	
	public boolean matchItem(ItemStack a, String id) {
		if(a == null)
			return false;
		if(!a.hasItemMeta())
			return false;
		if(!a.getItemMeta().hasDisplayName())
			return false;
		
		ItemStack b = getItem(null, id);
		if(a.getItemMeta().getDisplayName().equals(b.getItemMeta().getDisplayName()))
			return true;
		return false;
	}
}
