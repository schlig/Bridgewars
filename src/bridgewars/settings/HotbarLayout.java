package bridgewars.settings;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class HotbarLayout {

	private Scoreboard scoreboard;
	private Objective swordSlot;
	private Objective shearsSlot;
	private Objective woolSlot;
	private Score value;
	
	public HotbarLayout () {
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		
		if(scoreboard.getObjective("swordSlot") == null)
			swordSlot = scoreboard.registerNewObjective("swordSlot", "dummy");
		swordSlot = scoreboard.getObjective("swordSlot");
		if(scoreboard.getObjective("shearsSlot") == null)
			shearsSlot = scoreboard.registerNewObjective("shearsSlot", "dummy");
		shearsSlot = scoreboard.getObjective("shearsSlot");
		if(scoreboard.getObjective("woolSlot") == null)
			woolSlot = scoreboard.registerNewObjective("woolSlot", "dummy");
		woolSlot = scoreboard.getObjective("woolSlot");
	}
	
	public void restoreDefaults(Player p) {
		setSwordSlot(p, 0);
		setShearsSlot(p, 1);
		setWoolSlot(p, 2);
	}

	public void setSwordSlot(Player p, int s) {
		value = swordSlot.getScore(p.getName());
		value.setScore(s);
	}
	
	public void setShearsSlot(Player p, int s) {
		value = shearsSlot.getScore(p.getName());
		value.setScore(s);
	}
	
	public void setWoolSlot(Player p, int s) {
		value = woolSlot.getScore(p.getName());
		value.setScore(s);
	}

	public int getSwordSlot(Player p) {
		value = swordSlot.getScore(p.getName());
		if(value.getScore() == shearsSlot.getScore(p.getName()).getScore()
				|| value.getScore() == woolSlot.getScore(p.getName()).getScore()) {
			restoreDefaults(p);
			return 0;
		}
		return value.getScore();
	}
	
	public int getShearsSlot(Player p) {
		value = shearsSlot.getScore(p.getName());
		if(value.getScore() == swordSlot.getScore(p.getName()).getScore()
				|| value.getScore() == woolSlot.getScore(p.getName()).getScore()) {
			restoreDefaults(p);
			return 1;
		}
		return value.getScore();
	}
	
	public int getWoolSlot(Player p) {
		value = woolSlot.getScore(p.getName());
		if(value.getScore() == swordSlot.getScore(p.getName()).getScore()
				|| value.getScore() == shearsSlot.getScore(p.getName()).getScore()) {
			restoreDefaults(p);
			return 2;
		}
		return value.getScore();
	}
}
