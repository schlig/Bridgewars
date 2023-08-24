package bridgewars.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import bridgewars.commands.Fly;
import bridgewars.effects.Fireworks;
import bridgewars.items.CustomItems;
import bridgewars.items.SadRoom;
import bridgewars.settings.Blocks;
import bridgewars.settings.Bows;
import bridgewars.settings.DigWars;
import bridgewars.settings.GigaDrill;
import bridgewars.settings.HotbarLayout;
import bridgewars.settings.Shears;
import bridgewars.settings.Swords;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;

public class Game {
	
	private static CustomItems items = new CustomItems();
	private static CustomScoreboard cs = new CustomScoreboard();
	private static HotbarLayout hotbar = new HotbarLayout();
	
	private static String filepath = "./plugins/bridgewars/maps/";
	
	public static void startGame(Player p, boolean debugMessages) {
		
		if(GameState.isState(GameState.EDIT)) {
			p.sendMessage(Message.chat("&cYou can't start a game while Edit Mode is active."));
			return;
		}
		if(GameState.isState(GameState.ACTIVE)) {
			p.sendMessage(Message.chat("&cThere is already a game in progress."));
			return;
		}
		
		Game.clearMap();
		if(debugMessages)
			p.sendMessage(Message.chat("&7Cleared the map"));
		
		if(!(Game.buildMap(null, p, false)))
			return;
		if(debugMessages)
			p.sendMessage(Message.chat("&7Built the map"));
		
		Timer.runTimer();
		if(debugMessages)
			p.sendMessage(Message.chat("&7Started the timer"));
		
		Game.placeSpawns();
		if(debugMessages)
			p.sendMessage(Message.chat("&7Placed spawn platforms"));
		
		SadRoom.clearSadRoom();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			Fly.allowFlight.remove(player);
			cs.removePlayerFromTimer(player);
			Game.randomizeTeam(player, true);
			Game.spawnPlayer(player);
			Game.grantItems(player);
			player.setFlying(false);
			player.setAllowFlight(false);
		}
		if(debugMessages) {
			p.sendMessage(Message.chat("&7Randomized teams"));
			p.sendMessage(Message.chat("&7Teleported players"));
			p.sendMessage(Message.chat("&7Granted starting items"));
		}
		
		GameState.setState(GameState.ACTIVE);
	}
	
	public static void joinGame(Player p) {
		p.setAllowFlight(false);
		p.setFlying(false);
		randomizeTeam(p, false);
		spawnPlayer(p);
		grantItems(p);
	}
	
	public static void leaveGame(Player p) {
		cs.resetTeam(p, false);
		cs.removePlayerFromTimer(p);
		if(p.getGameMode() != GameMode.CREATIVE) {
			p.getInventory().clear();
			p.teleport(new Location(Bukkit.getWorld("world"), 1062.5, 52, 88.5, -90, 10));
		}
	}
	
	public static void clearMap() {
		//map bounds
		//-28 0 -28
		//28 41 28
		for(int x = -22; x <= 22; x++)
			for(int z = -22; z <= 22; z++)
				for(int y = 0; y <= 24; y++)
					Bukkit.getWorld("world").getBlockAt(x, y, z).setType(Material.AIR);
		
		for(Entity e : Bukkit.getWorld("world").getEntities())
			if(e instanceof Item)
				e.teleport(new Location(Bukkit.getWorld("world"), 0, -100, 0));
	}
	
	@SuppressWarnings("deprecation")
	public static boolean buildMap(String map, Player p, boolean override) {
		File file;
		int i;
		if(map == null) {
			file = new File(filepath);
			ArrayList<String> mapList = new ArrayList<>(Arrays.asList(file.list()));
			ArrayList<String> blacklist = new ArrayList<>();
			try {
				for(String name : mapList) {
					RandomAccessFile r = new RandomAccessFile(filepath + name, "rw");
					r.seek(0);
					String test = r.readLine();
					if(test.contains("1"))
						blacklist.add(name);
					r.close();
				}
				for(String name : blacklist)
					mapList.remove(name);
				
			} catch (IOException e) { 
				e.printStackTrace();
				p.sendMessage(Message.chat("&cFailed to load map \"&6" + map + "&c\""));
			}
			if(mapList.size() == 0) {
				Bukkit.broadcastMessage(Message.chat("&cThere aren't any maps in rotation!"));
				return false;
			}
			i = Utils.rand(mapList.size());
			map = mapList.get(i);
			file = new File(filepath + map);
		}
		else {
			map = map + ".map";
			file = new File(filepath + map);
			try {
				map = file.getCanonicalFile().getName();
			} catch (IOException e) {
				p.sendMessage(Message.chat("&cFailed to load map \"&6" + map + "&c\""));
				return false;
			}
		}
		
		try {
			if(!(file.exists())) {
				p.sendMessage(Message.chat("&cFailed to load map \"&6" + map + "&c\" &7(try checking case sensitivity?)"));
				return false;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			int x, y, z, t;
			br.readLine();
			while((line = br.readLine()) != null) {
				String[] block = line.split("[,]");
				x = Integer.parseInt(block[1].replaceAll(",", ""));
				y = Integer.parseInt(block[2].replaceAll(",", ""));
				z = Integer.parseInt(block[3].replaceAll(",", ""));
				t = Integer.parseInt(block[4].replaceAll(",", ""));
				Block build = Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, y, z));
				build.setType(Material.getMaterial(block[0].replaceAll(",", "")));
				build.setData((byte) t);
			}
			br.close();
		} catch (IOException e) {
			
		}
		map = map.substring(0, map.length() - 4);
		for(Player player : Bukkit.getOnlinePlayers())
			player.sendMessage(Message.chat("&lMap: &6&l" + map));
		return true;
	}
	
	public static void randomizeTeam(Player p, Boolean override) {
		if(override)
			cs.resetTeam(p, false);
		
		if(cs.hasTeam(p))
			return;
		
		Map<String, Integer> teamSizes = new HashMap<>();
		List<String> options = new ArrayList<>();
		
		teamSizes.put("red", (cs.getTeamSize("red")));
		teamSizes.put("blue", (cs.getTeamSize("blue")));
		teamSizes.put("green", (cs.getTeamSize("green")));
		teamSizes.put("yellow", (cs.getTeamSize("yellow")));

		options.addAll(teamSizes.keySet());
		
		int smallestTeam = Collections.min(teamSizes.values());
		for(Entry<String,Integer> entry : teamSizes.entrySet())
			if(smallestTeam != entry.getValue())
				options.remove(entry.getKey());
		
		if(options.size() == 0)
			options.addAll(teamSizes.keySet());
		cs.setTeam(p, options.get(Utils.rand(options.size())));
	}
	
	public static void spawnPlayer(Player p) {
		String s = cs.getTeam(p);
		
		switch(s) {
		case "red":
			p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 34, -21.5, 0, 20));
			break;
		case "blue":
			p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 34, 22.5, 180, 20));
			break;
		case "green":
			p.teleport(new Location(Bukkit.getWorld("world"), -21.5, 34, 0.5, -90, 20));
			break;
		case "yellow":
			p.teleport(new Location(Bukkit.getWorld("world"), 22.5, 34, 0.5, 90, 20));
			break;
		}
	}
	
	public static void endGame(Player winner, Boolean forced) {
		if(winner != null) {
			List<Player> winners = new ArrayList<>();
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p.getGameMode() != GameMode.CREATIVE
				&& cs.hasTeam(p)) {
					if(cs.getTeam(p) == cs.getTeam(winner))
						winners.add(p);
					if(forced)
						p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 45.0, 0.5, 0, 10));
					else
						if(p == winner)
							p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 48.0, -5.5, 0, 10));
						else
							p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 45.0, 6.5, 180, 10));
					p.setLevel(0);
					p.getInventory().clear();
					p.getInventory().setArmorContents(null);
					p.setGameMode(GameMode.ADVENTURE);
					if(!forced) {
						Message.sendTitle(p, Message.chat("&6&lGAME OVER"), Message.chat("&l" + cs.getTeam(winner).substring(0, 1).toUpperCase() + cs.getTeam(winner).substring(1, cs.getTeam(winner).length()) + " team wins!"), 5, 20, 5);
						if(cs.getTeam(p) == cs.getTeam(winner))
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
						else
							p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
					}
				}
			}
			if(forced)
				Bukkit.broadcastMessage("The game was ended by " + winner.getDisplayName());
			else {
				if(winners.size() == 1)
					Bukkit.broadcastMessage(winner.getDisplayName() + " won the game!");
				else {
					String winnerMessage = winners.get(0).getDisplayName();
					for(int i = 1; i < winners.size(); i++) {
						if(i == winners.size() - 1)
							winnerMessage = winnerMessage + " and " + winners.get(i).getDisplayName();
						else
							winnerMessage = winnerMessage + ", " + winners.get(i).getDisplayName();
					}
					winnerMessage = winnerMessage + " won the game!";
					Bukkit.broadcastMessage(winnerMessage);
				}
			}
		}
		
		else if(winner == null)
			Bukkit.broadcastMessage("There were no players left, so the game has ended.");
		
		if(!forced)
			new Fireworks(7).runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 0L, 10L);
		
		SadRoom.clearSadRoom();
		GameState.setState(GameState.INACTIVE);
		cs.resetAllTimes();
		deleteSpawns();
	}
	
	@SuppressWarnings("deprecation")
	public static void placeSpawns() {
		//red spawn platform
		for(int x = -1; x <= 1; x++)
			for(int z = -23; z <= -21; z++) {
				Block block = Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, 33, z));
				block.setType(Material.STAINED_GLASS);
				block.setData((byte) 14);
			}
		
		//blue spawn platform
		for(int x = -1; x <= 1; x++)
			for(int z = 21; z <= 23; z++) {
				Block block = Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, 33, z));
				block.setType(Material.STAINED_GLASS);
				block.setData((byte) 3);
			}
		
		//green spawn platform
		for(int x = -23; x <= -21; x++)
			for(int z = -1; z <= 1; z++) {
				Block block = Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, 33, z));
				block.setType(Material.STAINED_GLASS);
				block.setData((byte) 5);
			}
			
		//yellow spawn platform
		for(int x = 21; x <= 23; x++)
			for(int z = -1; z <= 1; z++) {
				Block block = Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, 33, z));
				block.setType(Material.STAINED_GLASS);
				block.setData((byte) 4);
			}
	}
	
	public static void deleteSpawns() {
		//red spawn platform
		for(int x = -1; x <= 1; x++)
			for(int z = -23; z <= -21; z++)
				Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, 33, z)).setType(Material.AIR);
		
		//blue spawn platform
		for(int x = -1; x <= 1; x++)
			for(int z = 21; z <= 23; z++)
				Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, 33, z)).setType(Material.AIR);
		
		//green spawn platform
		for(int x = -23; x <= -21; x++)
			for(int z = -1; z <= 1; z++)
				Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, 33, z)).setType(Material.AIR);
			
		//yellow spawn platform
		for(int x = 21; x <= 23; x++)
			for(int z = -1; z <= 1; z++)
				Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, 33, z)).setType(Material.AIR);
	}
	
	public static void grantItems(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(items.getItem(p, "helmet"));
		p.getInventory().setChestplate(items.getItem(p, "chestplate"));;
		p.getInventory().setLeggings(items.getItem(p, "leggings"));;
		p.getInventory().setBoots(items.getItem(p, "boots"));
		if(Swords.isState(Swords.ENABLED))
			p.getInventory().setItem(hotbar.getSwordSlot(p), items.getItem(p, "sword"));
		if(Shears.isState(Shears.ENABLED) && !GigaDrill.getState().isEnabled())
			p.getInventory().setItem(hotbar.getShearsSlot(p), items.getItem(p, "shears"));
		if(Blocks.isState(Blocks.ENABLED))
			p.getInventory().setItem(hotbar.getWoolSlot(p), items.getItem(p, "blocks", 64));
		
		if(GigaDrill.isState(GigaDrill.ENABLED))
			p.getInventory().setItem(hotbar.getShearsSlot(p), items.getItem(p, "gigashears"));
		
		if(Bows.isState(Bows.ENABLED)) {
			p.getInventory().addItem(items.getItem(p, "bow"));
			p.getInventory().setItem(9, new ItemStack(Material.ARROW, 1));;
		}
		if(DigWars.getState().isEnabled()) {
			p.getInventory().setItem(hotbar.getAxeSlot(p), items.getItem(p, "axe"));
			p.getInventory().setItem(hotbar.getWoodSlot(p), new ItemStack(Material.WOOD, 64));
		}
		p.getInventory().addItem(items.getItem(p, "be"));
		p.setGameMode(GameMode.SURVIVAL);
	}
}
