package bridgewars.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import bridgewars.settings.TimeLimit;
import bridgewars.utils.Message;

@SuppressWarnings("unused")
public class CustomScoreboard {
	
	private Scoreboard scoreboard;
	private Objective time;
	private Team team;
	private Score score;
	
	private TimeLimit limit = new TimeLimit();
	
	public CustomScoreboard() {
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		
		if(scoreboard.getObjective("time") == null) {
			time = scoreboard.registerNewObjective("time", "dummy");
			time.setDisplaySlot(DisplaySlot.SIDEBAR);
			time.setDisplayName(Message.chat("&6Time"));
		}
		
		teamSetup("red", "&c", "&r");
		teamSetup("blue", "&b", "&r");
		teamSetup("green", "&a", "&r");
		teamSetup("yellow", "&e", "&r");
		
		time = scoreboard.getObjective("time");
	}
	
	private void teamSetup(String team, String prefix, String suffix) {
		if(scoreboard.getTeam(team) == null)
			scoreboard.registerNewTeam(team);
		this.team = scoreboard.getTeam(team);
		this.team.setAllowFriendlyFire(true);
		this.team.setCanSeeFriendlyInvisibles(true);
		this.team.setPrefix(Message.chat(prefix));
		this.team.setSuffix(Message.chat(suffix));
	}
	
	public void sendScoreboard(Player p) {
		p.setScoreboard(scoreboard);
	}
	
	public void updateTime(Player p, int TimeLimit) {
		time.setDisplaySlot(DisplaySlot.SIDEBAR);
		if(hasTeam(p)) {
			p.setScoreboard(scoreboard);
			score = time.getScore(p.getName());
			if(p.getLocation().getY() <= 30
					&& !p.getGameMode().equals(GameMode.CREATIVE)
					&& score.getScore() < TimeLimit)
				score.setScore(score.getScore() + 1);
			if(score.getScore() == TimeLimit - 60 && TimeLimit - 60 > 0) {
				Bukkit.broadcastMessage(Message.chat("&l" + p.getDisplayName() + " &rhas &l&e60&r seconds remaining!"));
				for(Player player : Bukkit.getOnlinePlayers())
					player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
			}
			else if(score.getScore() == TimeLimit - 30 && TimeLimit - 30 > 0) {
				Bukkit.broadcastMessage(Message.chat("&l" + p.getDisplayName() + " &rhas &l&630&r seconds remaining!"));
				for(Player player : Bukkit.getOnlinePlayers())
					player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
			}
			else if(score.getScore() >= TimeLimit - 15 && TimeLimit - 15 > 0) {
				if(score.getScore() == TimeLimit - 15 && TimeLimit - 15 > 0)
					Bukkit.broadcastMessage(Message.chat("&l" + p.getDisplayName() + " &rhas &l&c15&r seconds remaining! Their location has been revealed!"));
				if(score.getScore() == TimeLimit - 15 && TimeLimit - 15 > 0 || score.getScore() >= TimeLimit - 9 && TimeLimit - 15 > 0)
					for(Player player : Bukkit.getOnlinePlayers())
						player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
				}
			if(score.getScore() >= TimeLimit)
				Game.endGame(p, false);
		}
	}
	
	public int getTime(Player p) {
		return time.getScore(p.getName()).getScore();
	}

	public void resetTime(Player p) {
		score = time.getScore(p.getName());
		score.setScore(0);
	}
	
	public void removePlayerFromTimer(Player p) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard players reset " + p.getName() + " time");
	}
	
	public void resetAllTimes() {
		int timelimit = limit.getLimit();
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard players reset * time");
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> limit.setLimit(timelimit), 1L);
	}
	
	public void setTeam(Player p, String team) {
		if(!(scoreboard.getTeam(team) == null)) {
			this.team = scoreboard.getTeam(team);
			this.team.addEntry(p.getName());
			p.setDisplayName(this.team.getPrefix() + p.getName() + this.team.getSuffix());
			p.sendMessage(Message.chat("&r&lYou joined the " + this.team.getPrefix() + "&l" + this.team.getName().toUpperCase() + "&r&l team."));
		}
		else
			p.sendMessage(Message.chat("&cThat team does not exist."));
	}
	
	public String getTeam(Player p) {
		if(scoreboard.getEntryTeam(p.getName()) == null)
			return null;
		return scoreboard.getEntryTeam(p.getName()).getName();
	}
	
	public Boolean matchTeam(Player p1, Player p2) {
		if(getTeam(p1) == getTeam(p2))
			if(getTeam(p1) == null)
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
	}
	
	public Integer getTeamSize(String s) {
		team = scoreboard.getTeam(s);
		return team.getEntries().size();
	}
	
	public void resetTeam(Player p, boolean message) {
		if(getTeam(p) != null) {
			team = scoreboard.getTeam(getTeam(p));
			team.removeEntry(p.getName());
			p.setDisplayName(p.getName());
			if(message)
				p.sendMessage(Message.chat("&lYou left your team."));
		}
	}
}
