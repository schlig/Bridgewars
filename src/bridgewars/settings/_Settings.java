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
			bows(Boolean.parseBoolean(br.readLine()));
			killstreakRewards(Boolean.parseBoolean(br.readLine()));
			pacifistRewards(Boolean.parseBoolean(br.readLine()));
			doubleJump(Boolean.parseBoolean(br.readLine()));
			
			br.close();
		} catch (IOException e) { }
	}
	
	public static void save() {
		File file = new File(filepath);

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			
			bw.write(Swords.getState().isEnabled().toString());
			bw.newLine();
			bw.write(Bows.getState().isEnabled().toString());
			bw.newLine();
			bw.write(KillstreakRewards.getState().isEnabled().toString());
			bw.newLine();
			bw.write(PacifistRewards.getState().isEnabled().toString());
			bw.newLine();
			bw.write(DoubleJump.getState().isEnabled().toString());
			
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
	
	private static void bows(boolean value) {
		if(value)
			Bows.setState(Bows.ENABLED);
		else
			Bows.setState(Bows.DISABLED);
	}
	
	private static void killstreakRewards(boolean value) {
		if(value)
			KillstreakRewards.setState(KillstreakRewards.ENABLED);
		else
			KillstreakRewards.setState(KillstreakRewards.DISABLED);
	}
	
	private static void pacifistRewards(boolean value) {
		if(value)
			PacifistRewards.setState(PacifistRewards.ENABLED);
		else
			PacifistRewards.setState(PacifistRewards.DISABLED);
	}
	
	private static void doubleJump(boolean value) {
		if(value)
			DoubleJump.setState(DoubleJump.ENABLED);
		else
			DoubleJump.setState(DoubleJump.DISABLED);
	}
}
