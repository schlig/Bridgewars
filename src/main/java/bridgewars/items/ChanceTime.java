package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.CSManager;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.ItemManager;
import bridgewars.utils.Utils;

public class ChanceTime implements ICustomItem, Listener {
	private final UUID id = UUID.fromString("8397c018-4b0f-4b88-b434-c43a9d26c374");
	private final String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0d"
			+ "HA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTBhNDQzZTBlY2E"
			+ "3ZjVkMzA2MjJkZDkzN2YxZTVlYTJjZGYxNWQxMGMyN2ExOTljNjhhN2NlMDljM"
			+ "zlmNmI2OSJ9fX0=";
	public ChanceTime(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@Override
	public Rarity getRarity() {
		return Rarity.RED;
	}

	@Override
	public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
        item.setDurability((short) 3);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Chance Block");
        ItemBuilder.setSkullTexture(item, id, texture);
        ItemBuilder.setLore(item, Arrays.asList(
        		Chat.color("&r&7It's time to gamble")));
        ItemBuilder.disableStacking(item);
        return item;
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.SKULL)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(!Utils.getID(e.getItem()).equals(getClass().getSimpleName().toLowerCase()))
				return;
			
			Player user = e.getPlayer();
			
			List<Player> allPlayers = new ArrayList<Player>();
			for(Player p : Bukkit.getOnlinePlayers()) 
				if(p.getGameMode() == GameMode.SURVIVAL && GameState.isState(GameState.ACTIVE))
					allPlayers.add(p);

			Utils.subtractItem(user);
			
			int i = Utils.rand(allPlayers.size());
			Player p1 = allPlayers.get(i);
			allPlayers.remove(i);
			Player p2 = allPlayers.get(Utils.rand(allPlayers.size()));

			for(i = 0; i < 5; i++) {
				double rng = Math.random();
				if(rng < 0.5)
					continue;
				switch (i) {
				case 0:
					swapHealth(p1, p2);
					break;
				case 1:
					swapLocations(p1, p2);
					break;
				case 2:
					swapInventories(p1, p2);
					break;
				case 3:
					swapKillstreakProgress(p1, p2);
					break;
				case 4:
					swapTime(p1, p2);
					break;
				default:
					break;
				}
			}
		}
	}
	
	private void swapInventories(Player p1, Player p2) {
		ItemStack[] p1Inv = p1.getInventory().getContents();
		ItemStack[] p2Inv = p2.getInventory().getContents();
		
		p1.getInventory().clear();
		p2.getInventory().clear();
		
		p1.getInventory().addItem(ItemManager.getItem("BasicSword").createItem(p1));
		p1.getInventory().addItem(ItemManager.getItem("WoolBlocks").createItem(p1));
		p1.getInventory().addItem(ItemManager.getItem("Shears").createItem(p1));
		
		for(ItemStack item : p2Inv)
			if(item == null
			|| item.getType() == Material.GOLD_SWORD
			|| item.getType() == Material.WOOL
			|| item.getType() == Material.SHEARS)
				continue;
			else
				p1.getInventory().addItem(item);
		
		p2.getInventory().addItem(ItemManager.getItem("BasicSword").createItem(p2));
		p2.getInventory().addItem(ItemManager.getItem("WoolBlocks").createItem(p2));
		p2.getInventory().addItem(ItemManager.getItem("Shears").createItem(p2));
		
		for(ItemStack item : p1Inv)
			if(item == null
			|| item.getType() == Material.GOLD_SWORD
			|| item.getType() == Material.WOOL
			|| item.getType() == Material.SHEARS)
				continue;
			else
				p2.getInventory().addItem(item);
		
		p1.sendMessage(Chat.color("&cYou swapped inventories with " + p2.getDisplayName() + "&r&c!"));
		p2.sendMessage(Chat.color("&cYou swapped inventories with " + p1.getDisplayName() + "&r&c!"));
	}
	
	private void swapHealth(Player p1, Player p2) {
		double p1MaxHP = p1.getMaxHealth();
		double p1HP = p1.getHealth();
		
		double p2MaxHP = p2.getMaxHealth();
		double p2HP = p2.getHealth();

		p1.setMaxHealth(p2MaxHP);
		p1.setHealth(p2HP);
		
		p2.setMaxHealth(p1MaxHP);
		p2.setHealth(p1HP);
		
		p1.sendMessage(Chat.color("&cYou swapped health with " + p2.getDisplayName() + "&r&c!"));
		p2.sendMessage(Chat.color("&cYou swapped health with " + p1.getDisplayName() + "&r&c!"));
	}
	
	private void swapLocations(Player p1, Player p2) {
		Location p1Loc = p1.getLocation();
		Location p2Loc = p2.getLocation();
		
		p1.teleport(p2Loc);
		p2.teleport(p1Loc);
		
		p1.sendMessage(Chat.color("&cYou swapped locations with " + p2.getDisplayName() + "&r&c!"));
		p2.sendMessage(Chat.color("&cYou swapped locations with " + p1.getDisplayName() + "&r&c!"));
	}
	
	private void swapKillstreakProgress(Player p1, Player p2) {
		int p1Streak = p1.getLevel();
		int p2Streak = p2.getLevel();
		
		p1.setLevel(p2Streak);
		p2.setLevel(p1Streak);
		
		p1.sendMessage(Chat.color("&cYou swapped killstreak progress with " + p2.getDisplayName() + "&r&c!"));
		p2.sendMessage(Chat.color("&cYou swapped killstreak progress with " + p1.getDisplayName() + "&r&c!"));
	}
	
	private void swapTime(Player p1, Player p2) {
		int p1Time = CSManager.getTime(p1);
		int p2Time = CSManager.getTime(p2);
		
		CSManager.setTime(p1, p2Time);
		CSManager.setTime(p2, p1Time);
		
		p1.sendMessage(Chat.color("&cYou swapped time with " + p2.getDisplayName() + "&r&c!"));
		p2.sendMessage(Chat.color("&cYou swapped time with " + p1.getDisplayName() + "&r&c!"));
	}
}
