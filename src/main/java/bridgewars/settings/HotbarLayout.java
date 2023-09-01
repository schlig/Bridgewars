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
	private Objective woodSlot;
	private Objective axeSlot;
	private Objective bowSlot;
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
		
		if(scoreboard.getObjective("bowSlot") == null)
			bowSlot = scoreboard.registerNewObjective("bowSlot", "dummy");
		bowSlot = scoreboard.getObjective("bowSlot");
		
		if(scoreboard.getObjective("woodSlot") == null)
			woodSlot = scoreboard.registerNewObjective("woodSlot", "dummy");
		woodSlot = scoreboard.getObjective("woodSlot");
		
		if(scoreboard.getObjective("axeSlot") == null)
			axeSlot = scoreboard.registerNewObjective("axeSlot", "dummy");
		axeSlot = scoreboard.getObjective("axeSlot");
	}
	
	public void restoreDefaults(Player p) {
		setSwordSlot(p, 0);
		setShearsSlot(p, 1);
		setWoolSlot(p, 2);
		setBowSlot(p, 3);
		setWoodSlot(p, 4);
		setAxeSlot(p, 5);
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
	
	public void setBowSlot(Player p, int s) {
		value = bowSlot.getScore(p.getName());
		value.setScore(s);
	}
	
	public void setWoodSlot(Player p, int s) {
		value = woodSlot.getScore(p.getName());
		value.setScore(s);
	}
	
	public void setAxeSlot(Player p, int s) {
		value = axeSlot.getScore(p.getName());
		value.setScore(s);
	}

	public int getSwordSlot(Player p) {
		value = swordSlot.getScore(p.getName());
		if(value.getScore() == axeSlot.getScore(p.getName()).getScore()
		|| value.getScore() == woolSlot.getScore(p.getName()).getScore()
		|| value.getScore() == shearsSlot.getScore(p.getName()).getScore()
		|| value.getScore() == bowSlot.getScore(p.getName()).getScore()
		|| value.getScore() == woodSlot.getScore(p.getName()).getScore()) {
			restoreDefaults(p);
			return 0;
		}
		return value.getScore();
	}
	
	public int getShearsSlot(Player p) {
		value = shearsSlot.getScore(p.getName());
		if(value.getScore() == swordSlot.getScore(p.getName()).getScore()
		|| value.getScore() == woolSlot.getScore(p.getName()).getScore()
		|| value.getScore() == axeSlot.getScore(p.getName()).getScore()
		|| value.getScore() == bowSlot.getScore(p.getName()).getScore()
		|| value.getScore() == woodSlot.getScore(p.getName()).getScore()) {
			restoreDefaults(p);
			return 1;
		}
		return value.getScore();
	}
	
	public int getWoolSlot(Player p) {
		value = woolSlot.getScore(p.getName());
		if(value.getScore() == swordSlot.getScore(p.getName()).getScore()
		|| value.getScore() == axeSlot.getScore(p.getName()).getScore()
		|| value.getScore() == shearsSlot.getScore(p.getName()).getScore()
		|| value.getScore() == bowSlot.getScore(p.getName()).getScore()
		|| value.getScore() == woodSlot.getScore(p.getName()).getScore()) {
			restoreDefaults(p);
			return 2;
		}
		return value.getScore();
	}
	
	public int getBowSlot(Player p) {
		value = bowSlot.getScore(p.getName());
		if(value.getScore() == swordSlot.getScore(p.getName()).getScore()
		|| value.getScore() == woolSlot.getScore(p.getName()).getScore()
		|| value.getScore() == shearsSlot.getScore(p.getName()).getScore()
		|| value.getScore() == axeSlot.getScore(p.getName()).getScore()
		|| value.getScore() == woodSlot.getScore(p.getName()).getScore()) {
			restoreDefaults(p);
			return 3;
		}
		return value.getScore();
	}
	
	public int getWoodSlot(Player p) {
		value = woodSlot.getScore(p.getName());
		if(value.getScore() == swordSlot.getScore(p.getName()).getScore()
		|| value.getScore() == woolSlot.getScore(p.getName()).getScore()
		|| value.getScore() == shearsSlot.getScore(p.getName()).getScore()
		|| value.getScore() == bowSlot.getScore(p.getName()).getScore()
		|| value.getScore() == axeSlot.getScore(p.getName()).getScore()) {
			restoreDefaults(p);
			return 4;
		}
		return value.getScore();
	}
	
	public int getAxeSlot(Player p) {
		value = axeSlot.getScore(p.getName());
		if(value.getScore() == swordSlot.getScore(p.getName()).getScore()
		|| value.getScore() == woolSlot.getScore(p.getName()).getScore()
		|| value.getScore() == shearsSlot.getScore(p.getName()).getScore()
		|| value.getScore() == bowSlot.getScore(p.getName()).getScore()
		|| value.getScore() == woodSlot.getScore(p.getName()).getScore()) {
			restoreDefaults(p);
			return 5;
		}
		return value.getScore();
	}
}
