package bridgewars.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class _Settings {
	
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
}
