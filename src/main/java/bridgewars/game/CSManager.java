package bridgewars.game;

import org.bukkit.entity.Player;

public class CSManager {
	
	private static CustomScoreboard cs = new CustomScoreboard();
	//this is basically only here so i don't have to create a bunch of CustomScoreboard variable things in every class that needs them
	//should create less garbage but is definitely still dumb and bad
	
	
	public static void sendScoreboard(Player p) {
		cs.sendScoreboard(p);
	}
	
	public static void updateTime(Player p, int TimeLimit) {
		cs.updateTime(p, TimeLimit);
	}
	
	public static int getTime(Player p) {
		return cs.getTime(p);
	}
	
	public static void setTime(Player p, int value) {
		cs.setTime(p, value);
	}

	public static void resetTime(Player p) {
		cs.resetTime(p);
	}
	
	public static void removePlayerFromTimer(Player p) {
		cs.removePlayerFromTimer(p);
	}
	
	public static void resetAllTimes() {
		cs.resetAllTimes();
	}
	
	public static void setTeam(Player p, String team) {
		cs.setTeam(p, team);
	}
	
	public static String getTeam(Player p) {
		return cs.getTeam(p);
	}
	
	public static Boolean matchTeam(Player p1, Player p2) {
		return cs.matchTeam(p1, p2);
	}
	
	public static Boolean hasTeam(Player p) {
		return cs.hasTeam(p);
	}
	
	public static void clearTeams() {
		cs.clearTeams();
	}
	
	public static Integer getTeamSize(String s) {
		return cs.getTeamSize(s);
	}
	
	public static void resetTeam(Player p, boolean message) {
		cs.resetTeam(p, message);
	}
	
	public static void resetBoard() {
		resetAllTimes();
		clearTeams();
	}
}
