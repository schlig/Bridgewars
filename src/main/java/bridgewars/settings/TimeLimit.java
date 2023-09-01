package bridgewars.settings;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class TimeLimit {
	
	private Scoreboard scoreboard;
	private Objective timeLimit;
	private Score value;
	
	public TimeLimit() {
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

		if(scoreboard.getObjective("timeLimit") == null) {
			timeLimit = scoreboard.registerNewObjective("timeLimit", "dummy");
			value = timeLimit.getScore("a");
			value.setScore(150);
		}
		timeLimit = scoreboard.getObjective("timeLimit");
	}
	
	public Integer getLimit() {
		value = timeLimit.getScore("a");
		return value.getScore();
	}
	
	public void setLimit(int amount) {
		value = timeLimit.getScore("a");
		value.setScore(amount);
	}
}
