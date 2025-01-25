package bridgewars.game;

import java.util.ArrayList;
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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import bridgewars.commands.Fly;
import bridgewars.effects.Cloak;
import bridgewars.effects.Fireworks;
import bridgewars.items.SadTear;
import bridgewars.messages.Chat;
import bridgewars.settings.Blocks;
import bridgewars.settings.Bows;
import bridgewars.settings.DigWars;
import bridgewars.settings.DoubleHealth;
import bridgewars.settings.DoubleJump;
import bridgewars.settings.GigaDrill;
import bridgewars.settings.HotbarLayout;
import bridgewars.settings.RandomTeams;
import bridgewars.settings.Shears;
import bridgewars.settings.Swords;
import bridgewars.utils.ItemManager;
import bridgewars.utils.Packet;
import bridgewars.utils.Utils;
import bridgewars.utils.World;

public class Game {
	
	private static CustomScoreboard cs = new CustomScoreboard();
	private static HotbarLayout hotbar = new HotbarLayout();
	
	
	public static void startGame(Player p, boolean debugMessages) {
		
		if(GameState.isState(GameState.EDIT)) {
			p.sendMessage(Chat.color("&cYou can't start a game while Edit Mode is active."));
			return;
		}
		if(GameState.isState(GameState.ACTIVE)) {
			p.sendMessage(Chat.color("&cThere is already a game in progress."));
			return;
		}
		
		World.clearMap();
		if(debugMessages)
			p.sendMessage(Chat.color("&7Cleared the map"));
		
		if(!(World.loadMap(null, p, false)))
			return;
		if(debugMessages)
			p.sendMessage(Chat.color("&7Built the map"));
		
		Timer.runTimer();
		if(debugMessages)
			p.sendMessage(Chat.color("&7Started the timer"));
		
		Game.placeSpawns();
		if(debugMessages)
			p.sendMessage(Chat.color("&7Placed spawn platforms"));
		
		SadTear.clearSadRoom();
		
		Leaderboards.deleteLabels();
		Leaderboards.clearKills();
		
		if(RandomTeams.isState(RandomTeams.ENABLED))
			cs.clearTeams();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getGameMode() == GameMode.CREATIVE
			|| player.getGameMode() == GameMode.SPECTATOR)
				continue;
			player.getInventory().clear();
			Fly.allowFlight.put(player, false);
			player.setFlying(false);
			if(!DoubleJump.getState().isEnabled())
				player.setAllowFlight(false);
			cs.removePlayerFromTimer(player);
			Game.spawnPlayer(player);
			Game.grantItems(player, false);
		}
		if(debugMessages) {
			p.sendMessage(Chat.color("&7Randomized teams"));
			p.sendMessage(Chat.color("&7Teleported players"));
			p.sendMessage(Chat.color("&7Granted starting items"));
		}
		
		GameState.setState(GameState.ACTIVE);
	}
	
	public static void joinGame(Player p) {
		p.setAllowFlight(false);
		p.setFlying(false);
		randomizeTeam(p, false);
		spawnPlayer(p);
		grantItems(p, true);
	}
	
	public static void leaveGame(Player p) {
		cs.resetTeam(p, false);
		cs.removePlayerFromTimer(p);
		if(p.getGameMode() != GameMode.CREATIVE) {
			p.getInventory().clear();
			p.teleport(new Location(Bukkit.getWorld("world"), 1062.5, 52, 88.5, -90, 10));
		}
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
		if(!cs.hasTeam(p))
			Game.randomizeTeam(p, true);
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
		Cloak.cloakedPlayers.clear();
		Leaderboards.placeLabels();
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
						if(Utils.matchTeam(p, winner))
							p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 48.0, -5.5, 0, 10));
						else
							p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 45.0, 6.5, 180, 10));
					p.setLevel(0);
					if(DoubleHealth.getState().isEnabled()) {
						p.setMaxHealth(40);
						p.setHealth(40);
					}
					else {
						p.setHealth(20);
						p.setMaxHealth(20);
					}
					p.getInventory().clear();
					p.getInventory().setArmorContents(null);
					p.setGameMode(GameMode.ADVENTURE);
					if(!forced) {
						Packet.sendTitle(p, Chat.color("&6&lGAME OVER"), 
								Chat.color("&l" + cs.getTeam(winner).substring(0, 1).toUpperCase()
										          + cs.getTeam(winner).substring(1, cs.getTeam(winner).length()) 
										          + " team wins!"), 5, 20, 5);
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
		
		SadTear.clearSadRoom();
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
	
	public static void grantItems(Player p, boolean override) {
		if(override)
			p.getInventory().clear();
		
		p.getInventory().setHelmet(ItemManager.getItem("BasicHelmet").createItem(p));
		p.getInventory().setChestplate(ItemManager.getItem("BasicChestplate").createItem(p));
		p.getInventory().setLeggings(ItemManager.getItem("BasicLeggings").createItem(p));
		p.getInventory().setBoots(ItemManager.getItem("BasicBoots").createItem(p));
		if(Swords.isState(Swords.ENABLED))
			p.getInventory().setItem(hotbar.getSlot(p, "swordSlot"),ItemManager.getItem("BasicSword").createItem(p));
		if(Shears.isState(Shears.ENABLED) && !GigaDrill.getState().isEnabled())
			p.getInventory().setItem(hotbar.getSlot(p, "shearsSlot"), ItemManager.getItem("Shears").createItem(p));
		if(Blocks.isState(Blocks.ENABLED))
			p.getInventory().setItem(hotbar.getSlot(p, "woolSlot"), ItemManager.getItem("WoolBlocks").createItem(p));
		
		if(GigaDrill.isState(GigaDrill.ENABLED))
			p.getInventory().setItem(hotbar.getSlot(p, "shearsSlot"), ItemManager.getItem("GigaShears").createItem(p));
		
		if(Bows.isState(Bows.ENABLED)) {
			p.getInventory().addItem(ItemManager.getItem("Bow").createItem(p));
			p.getInventory().setItem(9, new ItemStack(Material.ARROW, 1));;
		}
		if(DigWars.getState().isEnabled()) {
			p.getInventory().setItem(hotbar.getSlot(p, "axeSlot"), ItemManager.getItem("Axe").createItem(p));
			p.getInventory().setItem(hotbar.getSlot(p, "woodSlot"), new ItemStack(Material.WOOD, 64));
			p.getInventory().setItem(hotbar.getSlot(p, "waterSlot"), ItemManager.getItem("BottomlessWaterBucket").createItem(null));
			p.getInventory().setItem(hotbar.getSlot(p, "lavaSlot"), ItemManager.getItem("BottomlessLavaBucket").createItem(null));
		}
		if(p.getName().equals("nicktoot"))
			p.getInventory().addItem(ItemManager.getItem("SadRoom").createItem(p));
		p.setGameMode(GameMode.SURVIVAL);
	}
}
