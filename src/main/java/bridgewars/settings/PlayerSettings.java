package bridgewars.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerSettings {
	
	private final static String filepath = "./plugins/bridgewars/players/";
	
	public static HashMap<UUID, List<String>> playerSettings = new HashMap<>();
	private static HashMap<String, Integer> settingsMap = new HashMap<>();
	private static UUID defaultSettings = null;
	
	public static void load() {
//		Settings format:
//		Line 1: Sword slot                           (0)
//		Line 2: Shears slot							 (1)
//		Line 3: Blocks slot 						 (2)
//		Line 4: Killstreak item 1, given at 3 items  (3)
//		Line 5: Killstreak item 2, given at 5 items  (4)
//		Line 6: Killstreak item 3, given at 7 items  (5)
//		Line 7: Killstreak item 4, given at 15 items (6)
		
//		Default settings place sword in first slot, shears in second slot, blocks in third slot
//		Default killstreak rewards should be obvious from the next line
		
		settingsMap.put("SwordSlot", 0);
		settingsMap.put("ShearsSlot", 1);
		settingsMap.put("WoolSlot", 2);
		settingsMap.put("KillstreakRewardWhite", 3);
		settingsMap.put("KillstreakRewardGreen", 4);
		settingsMap.put("KillstreakRewardRed", 5);
		settingsMap.put("KillstreakRewardBlue", 6);
		
		playerSettings.put(defaultSettings, Arrays.asList("0", "1", "2", "bridgeegg", "portabledoinkhut", "homerunbat", "heartcontainer"));
		
		try {
			File folder = new File(filepath);
			String[] playerList = folder.list();
			for(String filename : playerList) {
				List<String> settings = new ArrayList<>();
				UUID uuid = UUID.fromString(filename.substring(0, filename.length() - 4));
				File file = new File(filepath + filename);
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String setting;
				while((setting = br.readLine()) != null)
					settings.add(setting);
				playerSettings.put(uuid, settings);
				br.close();
			}
		}
		
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveSettings(Player p, List<String> settings) {
		try {
			File file = new File(filepath + p.getUniqueId() + ".ini");
			if(file.exists())
				file.createNewFile();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			for(String setting : settings) {
				bw.write(setting.toString());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}
		
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setSetting(Player p, String setting, String value) {
		
		if(!settingsMap.containsKey(setting))
			return;
		
		UUID uuid = p.getUniqueId();
		List<String> settings = new ArrayList<>();
		
		if(!playerSettings.containsKey(uuid))
			settings = playerSettings.get(defaultSettings);
		else
			settings = playerSettings.get(uuid);
		
		settings.set(settingsMap.get(setting), value);
		playerSettings.put(uuid, settings);
		saveSettings(p, settings);
	}
	
	public static String getSetting(Player p, String setting) {
		return getSetting(p, settingsMap.get(setting));
	}
	
	public static String getSetting(Player p, int setting) {
		UUID uuid = p.getUniqueId();
		if(playerSettings.containsKey(uuid))
			return playerSettings.get(uuid).get(setting);
		else return playerSettings.get(defaultSettings).get(setting);
	}
}
