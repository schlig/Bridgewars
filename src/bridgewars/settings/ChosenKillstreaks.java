package bridgewars.settings;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ChosenKillstreaks {
	
	private Scoreboard scoreboard;
	private Objective three;
	private Objective five;
	private Objective seven;
	private Score value;
	
	public ChosenKillstreaks() {
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		
		if(scoreboard.getObjective("three") == null)
			three = scoreboard.registerNewObjective("three", "dummy");
		three = scoreboard.getObjective("three");
		if(scoreboard.getObjective("five") == null)
			five = scoreboard.registerNewObjective("five", "dummy");
		five = scoreboard.getObjective("five");
		if(scoreboard.getObjective("seven") == null)
			seven= scoreboard.registerNewObjective("seven", "dummy");
		seven = scoreboard.getObjective("seven");
	}
	
	private void restoreDefaults(Player p) {
		
	}
	
	public void setThreeStreak(Player p, int amount) {
		value = three.getScore(p.getName());
		value.setScore(amount);
	}
	
	public void setFiveStreak(Player p, int amount) {
		value = five.getScore(p.getName());
		value.setScore(amount);
	}
	
	public void setSevenStreak(Player p, int amount) {
		value = seven.getScore(p.getName());
		value.setScore(amount);
	}
	
	public int getThreeStreak(Player p) {
		value = three.getScore(p.getName());
		if(value.getScore() == five.getScore(p.getName()).getScore()
				|| value.getScore() == seven.getScore(p.getName()).getScore())
			restoreDefaults(p);
		return value.getScore();
	}
	
	public int getFiveStreak(Player p) {
		value = five.getScore(p.getName());
		if(value.getScore() == three.getScore(p.getName()).getScore()
				|| value.getScore() == seven.getScore(p.getName()).getScore())
			restoreDefaults(p);
		return value.getScore();
	}
	
	public int getSevenStreak(Player p) {
		value = seven.getScore(p.getName());
		if(value.getScore() == three.getScore(p.getName()).getScore()
				|| value.getScore() == five.getScore(p.getName()).getScore())
			restoreDefaults(p);
		return value.getScore();
	}
}
