package bridgewars.game;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import bridgewars.effects.Cloak;
import bridgewars.effects.ScoreboardJam;
import bridgewars.messages.Chat;
import bridgewars.settings.TimeLimit;
import bridgewars.utils.Utils;

public class CustomScoreboard {
	
	private Scoreboard scoreboard;
	private Objective time;
	private Team team;
	private Score score;
	
	private TimeLimit limit = new TimeLimit();
	private HashMap<UUID, String> display = new HashMap<>();
	
	private final int firstWarning = 60;
	private final int secondWarning = 30; //time remaining to notify players
	private final int finalWarning = 15;
	
	private final int boundingRadius = 25; // max X and Z before timer starts ticking downwards
	private final int boundingHeight = 30; // max y elevation for timer to tick down
	
	public CustomScoreboard() {
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		
		if(scoreboard.getObjective("time") == null) {
			time = scoreboard.registerNewObjective("time", "dummy");
			time.setDisplaySlot(DisplaySlot.SIDEBAR);
			time.setDisplayName(Chat.color("&6Time"));
		}
		
		teamSetup("red", "&c", "&r");
		teamSetup("blue", "&b", "&r");
		teamSetup("green", "&a", "&r");
		teamSetup("yellow", "&e", "&r");
		
		teamSetup("redC", "&c", "&r");
		teamSetup("blueC", "&b", "&r");
		teamSetup("greenC", "&a", "&r");
		teamSetup("yellowC", "&e", "&r");
		
		time = scoreboard.getObjective("time");
	}
	
	private void teamSetup(String team, String prefix, String suffix) {
		if(scoreboard.getTeam(team) == null)
			scoreboard.registerNewTeam(team);
		this.team = scoreboard.getTeam(team);
		this.team.setAllowFriendlyFire(true);
		this.team.setCanSeeFriendlyInvisibles(true);
		this.team.setPrefix(Chat.color(prefix));
		this.team.setSuffix(Chat.color(suffix));
	}
	
	public void sendScoreboard(Player p) {
		p.setScoreboard(scoreboard);
	}
	
	public void updateTime(Player p, int TimeLimit) {
		if(!display.containsKey(p.getUniqueId()))
			display.put(p.getUniqueId(), Utils.getName(p.getUniqueId()));
		time.setDisplaySlot(DisplaySlot.SIDEBAR);
		if(hasTeam(p)) {
			if(!ScoreboardJam.blacklist.contains(p))
				p.setScoreboard(scoreboard);
			score = time.getScore(display.get(p.getUniqueId()));
			int points = score.getScore();
			
			if(p.getLocation().getY() <= boundingHeight //increase time by 1 if within bounds
			&& Math.abs(p.getLocation().getX()) <= boundingRadius
			&& Math.abs(p.getLocation().getZ()) <= boundingRadius
			&& !p.getGameMode().equals(GameMode.CREATIVE)
			&& points < TimeLimit)
				score.setScore(points + 1);
			
			if(p.getLocation().getY() > boundingHeight  //decrease time by 1 if out of bounds
			|| Math.abs(p.getLocation().getX()) > boundingRadius
			|| Math.abs(p.getLocation().getZ()) > boundingRadius) {
				if(p.getGameMode() != GameMode.CREATIVE
				&& points > 0) {
					score.setScore(points - 1);
					return;
				}
			}
			
			if(points == TimeLimit - firstWarning) {
				Bukkit.broadcastMessage(Chat.color("&l" + p.getDisplayName() + " &rhas &l&e60&r seconds remaining!"));
				for(Player player : Bukkit.getOnlinePlayers())
					player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
			}
			
			else if(points == TimeLimit - secondWarning) {
				Bukkit.broadcastMessage(Chat.color("&l" + p.getDisplayName() + " &rhas &l&630&r seconds remaining! Their location has been revealed!"));
				for(Player player : Bukkit.getOnlinePlayers())
					player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
				if(Cloak.cloakedPlayers.contains(p.getUniqueId()))
					Cloak.cloakedPlayers.remove(p.getUniqueId());
			}
			
			else if(points >= TimeLimit - finalWarning) {
				if(points == TimeLimit - finalWarning)
					Bukkit.broadcastMessage(Chat.color("&l" + p.getDisplayName() + " &rhas &l&c15&r seconds remaining!"));
				if(points == TimeLimit - finalWarning || points >= TimeLimit - 9 && TimeLimit - finalWarning > 0)
					for(Player player : Bukkit.getOnlinePlayers())
						player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
				}
			if(score.getScore() >= TimeLimit)
				Game.endGame(p, false);
		}
	}
	
	public int getTime(Player p) {
		if(!display.containsKey(p.getUniqueId()))
			display.put(p.getUniqueId(), Utils.getName(p.getUniqueId()));
		return time.getScore(display.get(p.getUniqueId())).getScore();
	}
	
	public void setTime(Player p, int value) {
		if(!display.containsKey(p.getUniqueId()))
			display.put(p.getUniqueId(), Utils.getName(p.getUniqueId()));
		score = time.getScore(display.get(p.getUniqueId()));
		score.setScore(value);
	}

	public void resetTime(Player p) {
		if(!display.containsKey(p.getUniqueId()))
			display.put(p.getUniqueId(), Utils.getName(p.getUniqueId()));
		score = time.getScore(display.get(p.getUniqueId()));
		score.setScore(0);
	}
	
	public void removePlayerFromTimer(Player p) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard players reset " + Utils.getName(p.getUniqueId()) + " time");
	}
	
	public void resetAllTimes() {
		int timelimit = limit.getLimit();
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard players reset * time");
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> limit.setLimit(timelimit), 1L);
	}
	
	public void setTeam(Player p, String team) {
		if(!(scoreboard.getTeam(team) == null)) {
			this.team = scoreboard.getTeam(team + "C");
			this.team.addEntry(Utils.getName(p.getUniqueId()));
			this.team = scoreboard.getTeam(team);
			this.team.addEntry(p.getUniqueId().toString());
			p.setDisplayName(this.team.getPrefix() + Utils.getName(p.getUniqueId()) + this.team.getSuffix());
			p.sendMessage(Chat.color("&r&lYou joined the " + this.team.getPrefix() + "&l" + this.team.getName().toUpperCase() + "&r&l team."));
		}
		else
			p.sendMessage(Chat.color("&cThat team does not exist."));
	}
	
	public String getTeam(Player p) {
		if(scoreboard.getEntryTeam(p.getUniqueId().toString()) == null)
			return null;
		return scoreboard.getEntryTeam(p.getUniqueId().toString()).getName();
	}
	
	public Boolean matchTeam(Player p1, Player p2) {
		if(getTeam(p1) == getTeam(p2))
			if(getTeam(p1) == null || getTeam(p2) == null)
				return false;
			else
				return true;
		else
			return false;
	}
	
	public Boolean hasTeam(Player p) {
		if(getTeam(p) != null)
			return true;
		else
			return false;
	}
	
	public void clearTeams() {
		for(String entry : scoreboard.getTeam("red").getEntries())
			scoreboard.getTeam("red").removeEntry(entry);
		for(String entry : scoreboard.getTeam("blue").getEntries())
			scoreboard.getTeam("blue").removeEntry(entry);
		for(String entry : scoreboard.getTeam("green").getEntries())
			scoreboard.getTeam("green").removeEntry(entry);
		for(String entry : scoreboard.getTeam("yellow").getEntries())
			scoreboard.getTeam("yellow").removeEntry(entry);
		
		for(String entry : scoreboard.getTeam("redC").getEntries())
			scoreboard.getTeam("redC").removeEntry(entry);
		for(String entry : scoreboard.getTeam("blueC").getEntries())
			scoreboard.getTeam("blueC").removeEntry(entry);
		for(String entry : scoreboard.getTeam("greenC").getEntries())
			scoreboard.getTeam("greenC").removeEntry(entry);
		for(String entry : scoreboard.getTeam("yellowC").getEntries())
			scoreboard.getTeam("yellowC").removeEntry(entry);
	}
	
	public Integer getTeamSize(String s) {
		team = scoreboard.getTeam(s);
		return team.getEntries().size();
	}
	
	public void resetTeam(Player p, boolean message) {
		if(getTeam(p) != null) {
			team = scoreboard.getTeam(getTeam(p) + "C");
			team.removeEntry(Utils.getName(p.getUniqueId()));
			team = scoreboard.getTeam(getTeam(p));
			team.removeEntry(p.getUniqueId().toString());
			p.setDisplayName(p.getName());
			if(message)
				p.sendMessage(Chat.color("&lYou left your team."));
		}
	}
	
	public void resetBoard() {
		resetAllTimes();
		clearTeams();
	}
}
