package bridgewars.game;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import bridgewars.messages.Chat;
import bridgewars.utils.Utils;

public class Leaderboards {
	
	private static Scoreboard scoreboard;
	private static Objective objective;
	private static Score score;
	
	private final static double topKillerX = -4.5;
	private final static double topKillerY = 46; //places the #1 killer label at these coords in the observatory above the play area
	private final static double topKillerZ = 0.5;
	
	private final static double topItemUserX = 5.5;
	private final static double topItemUserY = 46; //places the #1 killer label at these coords in the observatory above the play area
	private final static double topItemUserZ = 0.5;
	
	private final static double lifetimeWinsX = 1031.5;
	private final static double lifetimeWinsY = 59;  //places these next five in the lobby
	private final static double lifetimeWinsZ = 92.5;
	
	private final static double lifetimeKillsX = 1031.5;
	private final static double lifetimeKillsY = 59;
	private final static double lifetimeKillsZ = 88.5;
	
	private final static double lifetimeBlocksX = 1031.5;
	private final static double lifetimeBlocksY = 59;
	private final static double lifetimeBlocksZ = 84.5;
	
	private final static double lifetimeLossesX = 1039.5;
	private final static double lifetimeLossesY = 53;  
	private final static double lifetimeLossesZ = 90.5;
	
	private final static double lifetimeDeathsX = 1039.5;
	private final static double lifetimeDeathsY = 53;
	private final static double lifetimeDeathsZ = 86.5;
	
	private final static int d = 10; //diameter where labels are deleted
	
	private static HashMap<UUID, String> display = new HashMap<UUID, String>();
	
	public Leaderboards() {
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		
		if(scoreboard.getObjective("kills") == null)
			scoreboard.registerNewObjective("kills", "dummy");
		
		if(scoreboard.getObjective("items") == null)
			scoreboard.registerNewObjective("items", "dummy");

		if(scoreboard.getObjective("lifetimeDeaths") == null)
			scoreboard.registerNewObjective("lifetimeDeaths", "dummy");
		
		if(scoreboard.getObjective("lifetimeLosses") == null)
			scoreboard.registerNewObjective("lifetimeLosses", "dummy");
		
		if(scoreboard.getObjective("lifetimeBlocks") == null)
			scoreboard.registerNewObjective("lifetimeBlocks", "dummy");
		
		if(scoreboard.getObjective("lifetimeWins") == null)
			scoreboard.registerNewObjective("lifetimeWins", "dummy");
		
		if(scoreboard.getObjective("lifetimeKills") == null)
			scoreboard.registerNewObjective("lifetimeKills", "dummy");
	}
	
	public static void addPoint(Player p, String leaderboard) {
		if(!display.containsKey(p.getUniqueId()))
			display.put(p.getUniqueId(), Utils.getName(p.getUniqueId()));
		objective = scoreboard.getObjective(leaderboard);
		score = objective.getScore(display.get(p.getUniqueId()));
		score.setScore(score.getScore() + 1);
	}
	
	public static void clearPoints(Player p, String leaderboard) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), 
				"scoreboard players set " + Utils.getName(p.getUniqueId()) + " " + leaderboard +" 0");
	}
	
	public static void refreshLifetimeLeaderboards() {
		deleteLabels(lifetimeDeathsX, lifetimeDeathsY, lifetimeDeathsZ, d);
		deleteLabels(lifetimeKillsX, lifetimeKillsY, lifetimeKillsZ, d);
		
		placeLifetimeWinsLabels();
		placeLifetimeKillsLabels();
		placeLifetimeDeathsLabels();
		placeLifetimeLossesLabels();
		placeLifetimeBlocksLabels();
	}
	
	public static void clearLifetimeLeaderboards() {
		deleteLabels(lifetimeDeathsX, lifetimeDeathsY, lifetimeDeathsZ, d);
		deleteLabels(lifetimeKillsX, lifetimeKillsY, lifetimeKillsZ, d);
	}
	
	public static void buildLifetimeLeaderboards() {
		placeLifetimeWinsLabels();
		placeLifetimeKillsLabels();
		placeLifetimeDeathsLabels();
		placeLifetimeLossesLabels();
		placeLifetimeBlocksLabels();
	}
	
	private static void placeLifetimeWinsLabels() {
		buildLeaderboard("lifetimeWins", "Wins", 10, lifetimeWinsX, lifetimeWinsY, lifetimeWinsZ, true);
	}
	
	private static void placeLifetimeKillsLabels() {
		buildLeaderboard("lifetimeKills", "Kills", 10, lifetimeKillsX, lifetimeKillsY, lifetimeKillsZ, true);
	}
	
	private static void placeLifetimeDeathsLabels() {
		buildLeaderboard("lifetimeDeaths", "Deaths", 5, lifetimeDeathsX, lifetimeDeathsY, lifetimeDeathsZ, true);
	}
	
	private static void placeLifetimeLossesLabels() {
		buildLeaderboard("lifetimeLosses", "Losses", 5, lifetimeLossesX, lifetimeLossesY, lifetimeLossesZ, true);
	}
	
	private static void placeLifetimeBlocksLabels() {
		buildLeaderboard("lifetimeBlocks", "Blocks Placed", 10, lifetimeBlocksX, lifetimeBlocksY, lifetimeBlocksZ, true);
	}
	
	
	//temporary stuff that gets reset every game from here forward
	
	public static void refreshInstanceLeaderboards() {
		clearInstanceLeaderboards();
		clearInstanceLeaderboardsLabels();
		buildInstanceLeaderboards();
	}
	
	public static void clearInstanceLeaderboards() {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard players set * kills 0");
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard players set * items 0");
	}
	
	public static void clearInstanceLeaderboardsLabels() {
		deleteLabels(0, 40, 0, d);
	}
	
	public static void buildInstanceLeaderboards() {
		placeKillLabels();
		placeItemLabels();
	}
	
	private static void placeKillLabels() {
		buildLeaderboard("kills", "Top Killers", 5, topKillerX, topKillerY, topKillerZ, false);
	}
	
	private static void placeItemLabels() {
		buildLeaderboard("items", "Items Used", 5, topItemUserX, topItemUserY, topItemUserZ, false);
	}
	
	
	
	//functions that actually make/remove the leaderboards
	
	public static void buildLeaderboard(String leaderboard, String leaderboardName, int entries, double x, double y, double z, Boolean includeOfflinePlayers) {
		Map<String, Integer> scoreTable = new HashMap<String, Integer>();
		objective = scoreboard.getObjective(leaderboard);
		
		if(includeOfflinePlayers) {
			for(String name : scoreboard.getEntries()) {
				score = objective.getScore(name);
				scoreTable.put(name, score.getScore());
			}
		}
		else
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(!display.containsKey(p.getUniqueId()))
					display.put(p.getUniqueId(), Utils.getName(p.getUniqueId()));
				score = objective.getScore(display.get(p.getUniqueId()));
				scoreTable.put(display.get(p.getUniqueId()), score.getScore());
			}
		
		List<Integer> scores = new ArrayList<>();
		List<String> blacklist = new ArrayList<String>();
		scores.addAll(scoreTable.values());

		if(includeOfflinePlayers) {
			for(int i = 0; i < entries; i++) {
				if(scores.size() <= 0)
					break;
				Integer score = Collections.max(scores);
				for(String name : scoreboard.getEntries()) {
					if(!scoreTable.containsKey(name))
						continue;
					if(!blacklist.contains(name) &&
							scoreTable.get(name) == score) {
						int rank = i + 1;
						World world = Bukkit.getWorld("world");
						Chunk chunk = world.getChunkAt(new Location(world, lifetimeDeathsX, lifetimeDeathsY, lifetimeDeathsZ));
						chunk.load();
						
						NumberFormat nf = NumberFormat.getInstance(Locale.US);
						String formattedScore = nf.format(score);
						
						bridgewars.utils.World.placeLabel(Chat.color("&e" + rank + ". &r&l" + name + "&r&7 - &e" + formattedScore), x, y - ((double) i / 3), z);
						blacklist.add(name);
						scores.remove(score);
						break;
					}
				}
			}
		}
		
		else {
			for(int i = 0; i < 5; i++) {
				if(scores.size() <= 0)
					break;
				Integer score = Collections.max(scores);
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(!scoreTable.containsKey(display.get(p.getUniqueId())))
						continue;
					if(!blacklist.contains(display.get(p.getUniqueId()))
					&& scoreTable.get(display.get(p.getUniqueId())) == score) {
						int rank = i + 1;
						World world = Bukkit.getWorld("world");
						Chunk chunk = world.getChunkAt(new Location(world, lifetimeDeathsX, lifetimeDeathsY, lifetimeDeathsZ));
						chunk.load();
						
						NumberFormat nf = NumberFormat.getInstance(Locale.US);
						String formattedScore = nf.format(score);
						
						bridgewars.utils.World.placeLabel(Chat.color("&e" + rank + ". &r&l" + p.getDisplayName() + "&r&7 - &e" + formattedScore), x, y - ((double) i / 3), z);
						blacklist.add(display.get(p.getUniqueId()));
						scores.remove(score);
						break;
					}
				}
			}
		}
		
		bridgewars.utils.World.placeLabel(Chat.color("&6&l&n" + leaderboardName), x, y + 0.5, z);
	}
	
	public static void deleteLabels(double x, double y, double z, double radius) {
		World world = Bukkit.getWorld("world");
		Location loc = new Location(world, x, y, z);
		bridgewars.utils.World.deleteLabel(loc, radius);
	}
}
