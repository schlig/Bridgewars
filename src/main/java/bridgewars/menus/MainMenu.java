package bridgewars.menus;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import bridgewars.game.CustomScoreboard;
import bridgewars.game.Game;
import bridgewars.game.GameState;
import bridgewars.settings.ChosenKillstreaks;
import bridgewars.settings.HotbarLayout;
import bridgewars.utils.ItemManager;
import bridgewars.utils.Message;

public class MainMenu {

	private static GUI menu = new GUI();
	private static HotbarLayout hotbar = new HotbarLayout();
	private static ChosenKillstreaks ks = new ChosenKillstreaks();
	private static CustomScoreboard cs = new CustomScoreboard();
	
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
			Settings.loadSettings(p);
			
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
					p.sendMessage(Message.chat("&cThere is no game in progress."));
					p.getOpenInventory().close();
					return;
				}
				
				Game.endGame(p, true);
				p.sendMessage(Message.chat("&7Ended the game"));
			}
			else
				p.sendMessage(Message.chat("&cYou do not have permission to do this."));
			break;
			
		case SADDLE:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			if(GameState.isState(GameState.INACTIVE)) {
				p.sendMessage(Message.chat("&cThere is no game in progress."));
				p.getOpenInventory().close();
				return;
			}
			Game.joinGame(p);
			p.sendMessage(Message.chat("&6You joined the game."));
			break;
			
		case BOOK:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			Inventory hotbarEditor = Bukkit.createInventory(null, 45, "Hotbar Layout");
			hotbarEditor.setContents(menu.getHotbar().getContents());
			p.openInventory(hotbarEditor);

			if(!hotbar.slotsAreValid(p))
				hotbar.restoreDefaults(p);
			p.getOpenInventory().setItem(hotbar.getSlot(p, "shearsSlot") + 18, new ItemStack(Material.SHEARS));
			p.getOpenInventory().setItem(hotbar.getSlot(p, "woolSlot") + 18, new ItemStack(Material.WOOL));
			p.getOpenInventory().setItem(hotbar.getSlot(p, "bowSlot") + 18, new ItemStack(Material.BOW));
			p.getOpenInventory().setItem(hotbar.getSlot(p, "woodSlot") + 18, new ItemStack(Material.WOOD));
			p.getOpenInventory().setItem(hotbar.getSlot(p, "axeSlot") + 18, new ItemStack(Material.STONE_AXE));
			p.getOpenInventory().setItem(hotbar.getSlot(p, "waterSlot") + 18, new ItemStack(Material.WATER_BUCKET));
			p.getOpenInventory().setItem(hotbar.getSlot(p, "lavaSlot") + 18, new ItemStack(Material.LAVA_BUCKET));
			break;
			
		case IRON_SWORD:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			Inventory killstreakEditor = Bukkit.createInventory(null, 45, "Killstreak Bonuses");
			killstreakEditor.setContents(menu.getKS().getContents());
			p.openInventory(killstreakEditor);
			
			//main items
			p.getOpenInventory().setItem(10, ItemManager.getItem("BridgeEgg").createItem(null));
			if(ks.getThreeStreak(p) == 0)
				p.getOpenInventory().getItem(10).addUnsafeEnchantment(Enchantment.LURE, 1);
			
			ItemStack bat = ItemManager.getItem("HomeRunBat").createItem(null);
			bat.setDurability((short) 0);
			bat.removeEnchantment(Enchantment.KNOCKBACK);
			p.getOpenInventory().setItem(12, bat);
			if(ks.getFiveStreak(p) == 0)
				p.getOpenInventory().getItem(12).addUnsafeEnchantment(Enchantment.LURE, 1);
			
			ItemStack potion = new ItemStack(Material.POTION, 7, (short) 8192);
			ItemMeta meta = potion.getItemMeta();
			meta.setDisplayName(ItemManager.getItem("LifeforcePotion").createItem(null).getItemMeta().getDisplayName());
			meta.setLore(ItemManager.getItem("LifeforcePotion").createItem(null).getItemMeta().getLore());
			meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			potion.setItemMeta(meta);
			p.getOpenInventory().setItem(14, potion);
			if(ks.getSevenStreak(p) == 0) {
				PotionMeta effect = (PotionMeta) potion.getItemMeta();
				effect.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 1, 1), false);
			}
			p.getOpenInventory().setItem(16, new ItemStack(Material.BARRIER, 1));
			
			//alt items
			p.getOpenInventory().setItem(28, ItemManager.getItem("PortableDoinkHut").createItem(null));
			if(ks.getThreeStreak(p) == 1)
				p.getOpenInventory().getItem(28).addUnsafeEnchantment(Enchantment.LURE, 1);
			
			p.getOpenInventory().setItem(30, ItemManager.getItem("Fireball").createItem(null));
			if(ks.getFiveStreak(p) == 1)
				p.getOpenInventory().getItem(30).addUnsafeEnchantment(Enchantment.LURE, 1);
			
			p.getOpenInventory().setItem(32, ItemManager.getItem("BlackHole").createItem(null));
			if(ks.getSevenStreak(p) == 1)
				p.getOpenInventory().getItem(32).addUnsafeEnchantment(Enchantment.LURE, 1);
			
			p.getOpenInventory().setItem(34, new ItemStack(Material.BARRIER, 1));
			break;
			
		case WOOL:
			p.playSound(p.getLocation(), Sound.CLICK, 0.8F, 1F);
			Inventory inventory = menu.getTeam();
			p.openInventory(inventory);

			String team;
			if(cs.hasTeam(p))
				team = cs.getTeam(p);
			else
				team = "none";
			
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
}
