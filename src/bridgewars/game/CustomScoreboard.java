package bridgewars.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import bridgewars.settings.TimeLimit;
import bridgewars.utils.Utils;

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
			time.setDisplayName(Utils.chat("&6Time"));
		}
		
		if(scoreboard.getTeam("red") == null)
			scoreboard.registerNewTeam("red");
		if(scoreboard.getTeam("blue") == null)
			scoreboard.registerNewTeam("blue");
		if(scoreboard.getTeam("green") == null)
			scoreboard.registerNewTeam("green");
		if(scoreboard.getTeam("yellow") == null)
			scoreboard.registerNewTeam("yellow");
		
		teamSetup("red", "&c", "&r");
		teamSetup("blue", "&b", "&r");
		teamSetup("green", "&a", "&r");
		teamSetup("yellow", "&e", "&r");
		
		time = scoreboard.getObjective("time");
	}
	
	private void teamSetup(String team, String prefix, String suffix) {
		this.team = scoreboard.getTeam(team);
		this.team.setAllowFriendlyFire(true);
		this.team.setCanSeeFriendlyInvisibles(true);
		this.team.setPrefix(Utils.chat(prefix));
		this.team.setSuffix(Utils.chat(suffix));
	}
	
	public void sendScoreboard(Player p) {
		p.setScoreboard(scoreboard);
	}
	
	public void updateTime(int TimeLimit) {
		time.setDisplaySlot(DisplaySlot.SIDEBAR);
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(hasTeam(p)) {
				p.setScoreboard(scoreboard);
				score = time.getScore(p.getName());
				if(p.getLocation().getY() <= 30
						&& !p.getGameMode().equals(GameMode.CREATIVE)
						&& score.getScore() < TimeLimit)
					score.setScore(score.getScore() + 1);
				if(score.getScore() == TimeLimit - 60 && TimeLimit - 60 > 0)
					Bukkit.broadcastMessage(Utils.chat("&l" + p.getDisplayName() + " &rhas &l&e60&r seconds remaining!"));
				else if(score.getScore() == TimeLimit - 30 && TimeLimit - 30 > 0)
					Bukkit.broadcastMessage(Utils.chat("&l" + p.getDisplayName() + " &rhas &l&630&r seconds remaining!"));
				else if(score.getScore() == TimeLimit - 15 && TimeLimit - 15 > 0)
					Bukkit.broadcastMessage(Utils.chat("&l" + p.getDisplayName() + " &rhas &l&c15&r seconds remaining! Their location has been revealed!"));
				if(score.getScore() >= TimeLimit) {
					Game.endGame(p, false);
					break;
				}
			}
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
			p.sendMessage(Utils.chat("&r&lYou joined the " + this.team.getPrefix() + "&l" + this.team.getName().toUpperCase() + "&r&l team."));
		}
		else
			p.sendMessage(Utils.chat("&cThat team does not exist."));
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
				p.sendMessage(Utils.chat("&lYou left your team."));
		}
	}
}
