package bridgewars.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import bridgewars.game.CustomScoreboard;
import bridgewars.utils.Utils;

public class Items {
	
	private CustomScoreboard cs;
	
	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggings;
	private ItemStack boots;
	
	private ItemStack sword;
	private ItemStack shears;
	private ItemStack blocks;
	private ItemStack bow;

	private ItemStack bridgeEgg;
	private ItemStack portableDoinkHut;
	
	private ItemStack fireball;
	private ItemStack homeRunBat;
	
	private ItemStack lifeforcePotion;
	private ItemStack blackHole;
	
	public Items() {
		
		cs = new CustomScoreboard();
		
		//armor
		helmet = new ItemStack(Material.LEATHER_HELMET, 1);
		setUnbreakable(helmet, true);
		
		chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		setUnbreakable(chestplate, true);
		
		leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		setUnbreakable(leggings, true);
		
		boots = new ItemStack(Material.LEATHER_BOOTS, 1);
		setUnbreakable(boots, true);
		
		//basic items
		sword = new ItemStack(Material.GOLD_SWORD, 1);
		setUnbreakable(sword, true);
		
		shears = new ItemStack(Material.SHEARS, 1);
		shears.addEnchantment(Enchantment.DIG_SPEED, 3);
		setUnbreakable(shears, true);
		
		blocks = new ItemStack(Material.WOOL, 64);

		bow = new ItemStack(Material.BOW, 1);
		bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		setUnbreakable(bow, true);
		
		//killstreak rewards
		bridgeEgg = new ItemStack(Material.EGG, 1);
		setName(bridgeEgg, "&bBridge Egg");
		setLore(bridgeEgg, Arrays.asList(Utils.chat("&r&7Automatically builds a bridge for you")));
		
		portableDoinkHut = new ItemStack(Material.MOB_SPAWNER, 1);
		setName(portableDoinkHut, "&bPortable Doink Hut");
		setLore(portableDoinkHut, Arrays.asList(Utils.chat("&r&7Builds an instant house"),
												Utils.chat("&r&7where you're standing")));
		
		fireball = new ItemStack(Material.FIREBALL, 1);
		setName(fireball, "&6Fireball");
		setLore(fireball, Arrays.asList(Utils.chat("&r&7Throws an exploding fireball"),
										Utils.chat("&r&7that deals heavy knockback")));
		
		homeRunBat = new ItemStack(Material.WOOD_SWORD, 1);
		homeRunBat.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
		setName(homeRunBat, "&6Home Run Bat");
		setLore(homeRunBat, Arrays.asList(Utils.chat("&r&7Deals massive knockback"),
										  Utils.chat("&r&7Only has 3 uses")));
		
		lifeforcePotion = new ItemStack(Material.POTION, 1);
		lifeforcePotion.setDurability((short) 8233);
		ItemMeta meta = lifeforcePotion.getItemMeta();
		PotionMeta effect = (PotionMeta) meta;
		effect.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0), true);
		effect.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1200, 4), true);
		meta = effect;
		lifeforcePotion.setItemMeta(meta);
		lifeforcePotion.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		setName(lifeforcePotion, "&cLifeforce Potion");
		setLore(lifeforcePotion, Arrays.asList(Utils.chat("&r&7Strength I (0:30)"), 
											   Utils.chat("&r&7Absorption V (1:00)")));
		
		blackHole = new ItemStack(Material.SNOW_BALL, 1);
		setName(blackHole, "&cBlack Hole");
		setLore(blackHole, Arrays.asList(Utils.chat("&r&7A throwable black hole that eats"),
										 Utils.chat("&r&7every block in its path")));
	}
	
	private ItemStack hideFlags(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
		item.setItemMeta(meta);
		return item;
	}
	
	private ItemStack setName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.chat(name));
		item.setItemMeta(meta);
		return item;
	}
	
	private ItemStack setLore(ItemStack item, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	private ItemStack setUnbreakable(ItemStack item, boolean state) {
		ItemMeta meta = item.getItemMeta();
		if(state)
			meta.spigot().setUnbreakable(true);
		else
			meta.spigot().setUnbreakable(false);
		item.setItemMeta(meta);
		item.setDurability((short)0);
		return item;
	}
	
	private Color getColor(String s) {
		switch(s) {
		case "red":
			return Color.fromRGB(255, 0, 0);
		case "blue":
			return Color.fromRGB(0, 255, 255);
		case "green":
			return Color.fromRGB(0, 255, 0);
		case "yellow":
			return Color.fromRGB(255, 255, 0);
		}
		return Color.fromRGB(255, 255, 255);
	}
	
	public static ItemStack setLeatherColor(ItemStack item, Color color) {
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		return item;
	}
	
	public ItemStack getHelm(Player p, String s) {
		ItemStack item = helmet;
		setName(item, "&r" + s.substring(0, 1).toUpperCase() + s.substring(1) + " Chestplate");
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(getColor(s));
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack getChest(Player p, String s) {
		ItemStack item = chestplate;
		setName(item, "&r" + s.substring(0, 1).toUpperCase() + s.substring(1) + " Chestplate");
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(getColor(s));
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack getLegs(Player p, String s) {
		ItemStack item = leggings;
		setName(item, "&r" + s.substring(0, 1).toUpperCase() + s.substring(1) + " Leggings");
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(getColor(s));
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack getBoots(Player p, String s) {
		ItemStack item = boots;
		setName(item, "&r" + s.substring(0, 1).toUpperCase() + s.substring(1) + " Boots");
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(getColor(s));
		item.setItemMeta(meta);
		return boots;
	}
	
	public ItemStack getSword(Player p, String s) {
		ItemStack item = sword;
		setName(item, "&r" + s.substring(0, 1).toUpperCase() + s.substring(1) + " Sword");
		return item;
	}
	
	public ItemStack getShears(Player p) {
		return shears;
	}
	
	public ItemStack getBlocks(Player p, String s) {
		ItemStack item = blocks;
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
		return item;
	}
	
	public ItemStack getBow(Player p, String s) {
		ItemStack item = bow;
		setName(item, "&r" + s.substring(0, 1).toUpperCase() + s.substring(1) + " Bow");
		return item;
	}
	
	public ItemStack getBridgeEgg(int amount, Boolean hideFlags) {
		ItemStack item = bridgeEgg;
		item.setAmount(amount);
		if(hideFlags)
			item = this.hideFlags(item);
		return item;
	}
	
	public ItemStack getPortableDoinkHut(int amount, Boolean hideFlags) {
		ItemStack item = portableDoinkHut;
		item.setAmount(amount);
		if(hideFlags)
			item = this.hideFlags(item);
		return item;
	}
	
	public ItemStack getFireball(int amount, Boolean hideFlags) {
		ItemStack item = fireball;
		item.setAmount(amount);
		if(hideFlags)
			item = this.hideFlags(item);
		return item;
	}
	
	public ItemStack getHomeRunBat(int amount, Boolean hideFlags) {
		ItemStack item = homeRunBat;
		item.setAmount(amount);
		if(hideFlags)
			item = this.hideFlags(item);
		return item;
	}
	
	public ItemStack getBlackHole(int amount, Boolean hideFlags) {
		ItemStack item = blackHole;
		item.setAmount(amount);
		if(hideFlags)
			item = this.hideFlags(item);
		return item;
	}
	
	public ItemStack getLifeforcePotion(int amount, Boolean hideFlags) {
		ItemStack item = lifeforcePotion;
		item.setAmount(amount);
		if(hideFlags)
			item = this.hideFlags(item);
		return item;
	}
}
