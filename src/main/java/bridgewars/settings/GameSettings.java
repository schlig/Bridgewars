package bridgewars.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import bridgewars.settings.enums.Blocks;
import bridgewars.settings.enums.Bows;
import bridgewars.settings.enums.DigWars;
import bridgewars.settings.enums.DoubleHealth;
import bridgewars.settings.enums.FriendlyFire;
import bridgewars.settings.enums.GigaDrill;
import bridgewars.settings.enums.HidePlayers;
import bridgewars.settings.enums.IndestructibleMap;
import bridgewars.settings.enums.KillstreakRewards;
import bridgewars.settings.enums.NaturalItemSpawning;
import bridgewars.settings.enums.Piggyback;
import bridgewars.settings.enums.Quake;
import bridgewars.settings.enums.RandomTeams;
import bridgewars.settings.enums.RevealWinner;
import bridgewars.settings.enums.Shears;
import bridgewars.settings.enums.Swords;
import bridgewars.settings.enums.UnlockedInventory;
import bridgewars.settings.enums.WoolDecay;

public class GameSettings {
	
	private static Integer TimeLimit, KillBonus;
	private static Integer GameRadius, GameHeight;
	private static final Integer RevealTime = 30;
	private static String filepath = "./plugins/bridgewars/options.ini";

	public static void load() {
		File file = new File(filepath);
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			
			TimeLimit = Integer.parseInt(br.readLine());
			KillBonus = Integer.parseInt(br.readLine());
			GameRadius = Integer.parseInt(br.readLine());
			GameHeight = Integer.parseInt(br.readLine());
			
			Swords.setState(Boolean.parseBoolean(br.readLine()));
			Blocks.setState(Boolean.parseBoolean(br.readLine()));
			Shears.setState(Boolean.parseBoolean(br.readLine()));
			KillstreakRewards.setState(Boolean.parseBoolean(br.readLine()));
			Bows.setState(Boolean.parseBoolean(br.readLine()));
			
			DoubleHealth.setState(Boolean.parseBoolean(br.readLine()));
			RevealWinner.setState(Boolean.parseBoolean(br.readLine()));
			GigaDrill.setState(Boolean.parseBoolean(br.readLine()));
			DigWars.setState(Boolean.parseBoolean(br.readLine()));
			NaturalItemSpawning.setState(Boolean.parseBoolean(br.readLine()));
			FriendlyFire.setState(Boolean.parseBoolean(br.readLine()));
			Piggyback.setState(Boolean.parseBoolean(br.readLine()));
			RandomTeams.setState(Boolean.parseBoolean(br.readLine()));
			HidePlayers.setState(Boolean.parseBoolean(br.readLine()));
			IndestructibleMap.setState(Boolean.parseBoolean(br.readLine()));
			WoolDecay.setState(Boolean.parseBoolean(br.readLine()));
			UnlockedInventory.setState(Boolean.parseBoolean(br.readLine()));
			Quake.setState(Boolean.parseBoolean(br.readLine()));
			
			br.close();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public static void save() {
		File file = new File(filepath);

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			
			bw.write(TimeLimit.toString());
			bw.newLine();
			bw.write(KillBonus.toString());
			bw.newLine();
			bw.write(GameRadius.toString());
			bw.newLine();
			bw.write(GameHeight.toString());
			bw.newLine();

			bw.write(Swords.getState().isEnabled().toString());
			bw.newLine();
			bw.write(Blocks.getState().isEnabled().toString());
			bw.newLine();
			bw.write(Shears.getState().isEnabled().toString());
			bw.newLine();
			bw.write(KillstreakRewards.getState().isEnabled().toString());
			bw.newLine();
			bw.write(Bows.getState().isEnabled().toString());
			bw.newLine();
			
			bw.write(DoubleHealth.getState().isEnabled().toString());
			bw.newLine();
			bw.write(RevealWinner.getState().isEnabled().toString());
			bw.newLine();
			bw.write(GigaDrill.getState().isEnabled().toString());
			bw.newLine();
			bw.write(DigWars.getState().isEnabled().toString());
			bw.newLine();
			bw.write(NaturalItemSpawning.getState().isEnabled().toString());
			bw.newLine();
			bw.write(FriendlyFire.getState().isEnabled().toString());
			bw.newLine();
			bw.write(Piggyback.getState().isEnabled().toString());
			bw.newLine();
			bw.write(RandomTeams.getState().isEnabled().toString());
			bw.newLine();
			bw.write(HidePlayers.getState().isEnabled().toString());
			bw.newLine();
			bw.write(IndestructibleMap.getState().isEnabled().toString());
			bw.newLine();
			bw.write(WoolDecay.getState().isEnabled().toString());
			bw.newLine();
			bw.write(UnlockedInventory.getState().isEnabled().toString());
			bw.newLine();
			bw.write(Quake.getState().isEnabled().toString());
			bw.newLine();
			
			bw.flush();
			bw.close();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	//get/set the x/z limits of the map
	public static void setGameRadius(int value) {
		GameRadius = value;
		save();
	}
	
	public static Integer getGameRadius() {
		return GameRadius;
	}
	
	//get/set the y limit of the map
	public static void setGameHeight(int value) {
		GameHeight = value;
		save();
	}
	
	public static Integer getGameHeight() {
		return GameHeight;
	}
	
	//get/set the game's time limit
	public static void setTimeLimit(int value) {
		TimeLimit = value;
		save();
	}
	
	public static Integer getTimeLimit() {
		return TimeLimit;
	}
	
	public static Integer getRevealTime() {
		return TimeLimit - RevealTime;
	}
	
	//get/set time bonus on kill
	public static void setKillBonus(int value) {
		KillBonus = value;
		save();
	}
	
	public static Integer getKillBonus() {
		return KillBonus;
	}
}