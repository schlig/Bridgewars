package bridgewars.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
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
	private static Objective kills;
	private static Score score;
	
	private final static double topKillerX = -5.5;
	private final static double topKillerY = 46; //places the #1 killer label at these coords in the observatory above the play area
	private final static double topKillerZ = -6.5;
	
	private final static int d = 5; //diameter where labels are deleted
	
	private static HashMap<UUID, String> display = new HashMap<UUID, String>();
	
	public Leaderboards() {
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		
		if(scoreboard.getObjective("kills") == null)
			kills = scoreboard.registerNewObjective("kills", "dummy");
		
		kills = scoreboard.getObjective("kills");
	}
	
	public static void addKill(Player p) {
		if(!display.containsKey(p.getUniqueId()))
			display.put(p.getUniqueId(), Utils.getName(p.getUniqueId()));
		score = kills.getScore(display.get(p.getUniqueId()));
		score.setScore(score.getScore() + 1);
	}
	
	public static void clearKills() {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard players set * kills 0");
	}
	
	public static void clearKills(Player p) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard players set " + Utils.getName(p.getUniqueId()) + " kills 0");
	}
	
	public static void placeLabels() {
		Map<String, Integer> scoreTable = new HashMap<String, Integer>();
		kills = scoreboard.getObjective("kills");
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(!display.containsKey(p.getUniqueId()))
				display.put(p.getUniqueId(), Utils.getName(p.getUniqueId()));
			score = kills.getScore(display.get(p.getUniqueId()));
			scoreTable.put(display.get(p.getUniqueId()), score.getScore());
		}
		
		List<Integer> scores = new ArrayList<>();
		List<String> blacklist = new ArrayList<String>();
		scores.addAll(scoreTable.values());

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
					bridgewars.utils.World.placeLabel(Chat.color("&l" + rank + ". " + p.getDisplayName() + "&r: " + score), topKillerX, topKillerY - ((double) i / 2), topKillerZ);
					blacklist.add(display.get(p.getUniqueId()));
					scores.remove(score);
					break;
				}
			}
		}
		
		bridgewars.utils.World.placeLabel(Chat.color("&l&nTop Killers"), topKillerX, topKillerY + 0.5, topKillerZ);
	}
	
	public static void deleteLabels() {
		World world = Bukkit.getWorld("world");
		Location loc = new Location(world, topKillerX, topKillerY, topKillerZ);
		bridgewars.utils.World.deleteLabel(loc, d);
	}
}
