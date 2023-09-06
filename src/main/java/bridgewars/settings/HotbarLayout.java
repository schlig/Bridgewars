package bridgewars.settings;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import bridgewars.utils.Utils;

public class HotbarLayout {

	private Scoreboard scoreboard;
	private ArrayList<Objective> slots = new ArrayList<>();
	
	public HotbarLayout () {
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		
		if(scoreboard.getObjective("swordSlot") == null)
			scoreboard.registerNewObjective("swordSlot", "dummy");
		if(scoreboard.getObjective("shearsSlot") == null)
			scoreboard.registerNewObjective("shearsSlot", "dummy");
		if(scoreboard.getObjective("woolSlot") == null)
			scoreboard.registerNewObjective("woolSlot", "dummy");
		if(scoreboard.getObjective("bowSlot") == null)
			scoreboard.registerNewObjective("bowSlot", "dummy");
		if(scoreboard.getObjective("woodSlot") == null)
			scoreboard.registerNewObjective("woodSlot", "dummy");
		if(scoreboard.getObjective("axeSlot") == null)
			scoreboard.registerNewObjective("axeSlot", "dummy");
		if(scoreboard.getObjective("waterSlot") == null)
			scoreboard.registerNewObjective("waterSlot", "dummy");
		if(scoreboard.getObjective("lavaSlot") == null)
			scoreboard.registerNewObjective("lavaSlot", "dummy");

		slots.add(scoreboard.getObjective("swordSlot"));
		slots.add(scoreboard.getObjective("woolSlot"));
		slots.add(scoreboard.getObjective("shearsSlot"));
		slots.add(scoreboard.getObjective("axeSlot"));
		slots.add(scoreboard.getObjective("bowSlot"));
		slots.add(scoreboard.getObjective("woodSlot"));
		slots.add(scoreboard.getObjective("waterSlot"));
		slots.add(scoreboard.getObjective("lavaSlot"));
	}
	
	public void restoreDefaults(Player p) {
		int index = 0;
		for(Objective slot : slots) {
			slot.getScore(Utils.getName(p.getUniqueId())).setScore(index);
			index++;
		}
	}
	
	public void setSlot(Player p, int s, String slot) {
		scoreboard.getObjective(slot).getScore(Utils.getName(p.getUniqueId())).setScore(s);;
	}
	
	public int getSlot(Player p, String slot) {
		return scoreboard.getObjective(slot).getScore(Utils.getName(p.getUniqueId())).getScore();
	}
	
	public boolean slotsAreValid(Player p) {
		for(Objective obj1 : slots)
			for(Objective obj2 : slots)
				if(obj1.getScore(p.getName()).getScore() == obj2.getScore(p.getName()).getScore() && obj1 != obj2)
					return false;
		return true;
	}
}
