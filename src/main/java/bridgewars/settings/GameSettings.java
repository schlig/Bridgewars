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
import bridgewars.settings.enums.DoubleJump;
import bridgewars.settings.enums.FriendlyFire;
import bridgewars.settings.enums.GigaDrill;
import bridgewars.settings.enums.HidePlayers;
import bridgewars.settings.enums.IndestructibleMap;
import bridgewars.settings.enums.KillstreakRewards;
import bridgewars.settings.enums.NaturalItemSpawning;
import bridgewars.settings.enums.Piggyback;
import bridgewars.settings.enums.RandomTeams;
import bridgewars.settings.enums.Shears;
import bridgewars.settings.enums.Swords;
import bridgewars.settings.enums.UnlockedInventory;
import bridgewars.settings.enums.WoolDecay;

public class GameSettings {
	
	private static String filepath = "./plugins/bridgewars/options.ini";

	public static void load() {
		File file = new File(filepath);
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

			swords(Boolean.parseBoolean(br.readLine()));
			blocks(Boolean.parseBoolean(br.readLine()));
			shears(Boolean.parseBoolean(br.readLine()));
			killstreakRewards(Boolean.parseBoolean(br.readLine()));
			bows(Boolean.parseBoolean(br.readLine()));
			
			doubleHealth(Boolean.parseBoolean(br.readLine()));
			doubleJump(Boolean.parseBoolean(br.readLine()));
			gigaDrill(Boolean.parseBoolean(br.readLine()));
			digWars(Boolean.parseBoolean(br.readLine()));
			naturalItemSpawns(Boolean.parseBoolean(br.readLine()));
			friendlyFire(Boolean.parseBoolean(br.readLine()));
			piggyback(Boolean.parseBoolean(br.readLine()));
			chosenTeams(Boolean.parseBoolean(br.readLine()));
			hidePlayers(Boolean.parseBoolean(br.readLine()));
			indestructibleMap(Boolean.parseBoolean(br.readLine()));
			woolDecay(Boolean.parseBoolean(br.readLine()));
			unlockedInventory(Boolean.parseBoolean(br.readLine()));
			
			br.close();
		} catch (IOException e) { }
	}
	
	public static void save() {
		File file = new File(filepath);

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			
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
			bw.write(DoubleJump.getState().isEnabled().toString());
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
			
			bw.flush();
			bw.close();
		} catch (IOException e) { }
	}
	
	private static void swords(boolean value) {
		if(value)
			Swords.setState(Swords.ENABLED);
		else
			Swords.setState(Swords.DISABLED);
	}
	
	private static void blocks(boolean value) {
		if(value)
			Blocks.setState(Blocks.ENABLED);
		else
			Blocks.setState(Blocks.DISABLED);
	}
	
	private static void shears(boolean value) {
		if(value)
			Shears.setState(Shears.ENABLED);
		else
			Shears.setState(Shears.DISABLED);
	}
	
	private static void killstreakRewards(boolean value) {
		if(value)
			KillstreakRewards.setState(KillstreakRewards.ENABLED);
		else
			KillstreakRewards.setState(KillstreakRewards.DISABLED);
	}
	
	private static void bows(boolean value) {
		if(value)
			Bows.setState(Bows.ENABLED);
		else
			Bows.setState(Bows.DISABLED);
	}
	
	private static void doubleHealth(boolean value) {
		if(value)
			DoubleHealth.setState(DoubleHealth.ENABLED);
		else
			DoubleHealth.setState(DoubleHealth.DISABLED);
	}
	
	private static void doubleJump(boolean value) {
		if(value)
			DoubleJump.setState(DoubleJump.ENABLED);
		else
			DoubleJump.setState(DoubleJump.DISABLED);
	}
	
	private static void gigaDrill(boolean value) {
		if(value)
			GigaDrill.setState(GigaDrill.ENABLED);
		else
			GigaDrill.setState(GigaDrill.DISABLED);
	}
	
	private static void digWars(boolean value) {
		if(value)
			DigWars.setState(DigWars.ENABLED);
		else
			DigWars.setState(DigWars.DISABLED);
	}
	
	private static void naturalItemSpawns(boolean value) {
		if(value)
			NaturalItemSpawning.setState(NaturalItemSpawning.ENABLED);
		else
			NaturalItemSpawning.setState(NaturalItemSpawning.DISABLED);
	}
	
	private static void friendlyFire(boolean value) {
		if(value)
			FriendlyFire.setState(FriendlyFire.ENABLED);
		else
			FriendlyFire.setState(FriendlyFire.DISABLED);
	}
	
	private static void piggyback(boolean value) {
		if(value)
			Piggyback.setState(Piggyback.ENABLED);
		else
			Piggyback.setState(Piggyback.DISABLED);
	}
	
	private static void chosenTeams(boolean value) {
		if(value)
			RandomTeams.setState(RandomTeams.ENABLED);
		else
			RandomTeams.setState(RandomTeams.DISABLED);
	}
	
	private static void hidePlayers(boolean value) {
		if(value)
			HidePlayers.setState(HidePlayers.ENABLED);
		else
			HidePlayers.setState(HidePlayers.DISABLED);
	}
	
	private static void indestructibleMap(boolean value) {
		if(value)
			IndestructibleMap.setState(IndestructibleMap.ENABLED);
		else
			IndestructibleMap.setState(IndestructibleMap.DISABLED);
	}
	
	private static void woolDecay(boolean value) {
		if(value)
			WoolDecay.setState(WoolDecay.ENABLED);
		else
			WoolDecay.setState(WoolDecay.DISABLED);
	}
	
	private static void unlockedInventory(boolean value) {
		if(value)
			UnlockedInventory.setState(UnlockedInventory.ENABLED);
		else
			UnlockedInventory.setState(UnlockedInventory.DISABLED);
	}
}
