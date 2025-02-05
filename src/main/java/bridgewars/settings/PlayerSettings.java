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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerSettings {
	
	private final static String filepath = "./plugins/bridgewars/players/";
	
	private static HashMap<UUID, List<String>> playerSettings = new HashMap<>();
	private static HashMap<String, Integer> settingsMap = new HashMap<>();
	private static UUID defaultSettings = null;
	
	public static void load() {
//		Settings format:
//		Line 1: Sword slot                          
//		Line 2: Shears slot							
//		Line 3: Blocks slot 						
		
//		---Killstreak Rewards---
//		Line 4: Killstreak item 1, given at 3 items 
//		Line 5: Killstreak item 2, given at 5 items 
//		Line 6: Killstreak item 3, given at 7 items 
//		Line 7: Killstreak item 4, given at 15 items
		
//		---Custom Kill Messages---
//		Line 8: Melee kill
//		Line 9: Void kill
//		Line 10: Bow kill
//		Line 11: Fireball kill
//		Line 12: Suicide kill
//		Line 13: Colorblind Mode - causes all enemy players to appear with black armor, and friendly players to appear with white armor (this was for nort mainly, but also hideplayers)
		
//		Default settings place sword in first slot, shears in second slot, blocks in third slot
//		Default killstreak rewards should be obvious from the next line
		
		settingsMap.put("SwordSlot",           	 	0);
		settingsMap.put("ShearsSlot",  				1);
		settingsMap.put("WoolSlot", 				2);
		settingsMap.put("KillstreakRewardWhite", 	3);
		settingsMap.put("KillstreakRewardGreen", 	4);
		settingsMap.put("KillstreakRewardRed", 		5);
		settingsMap.put("KillstreakRewardBlue", 	6);
		settingsMap.put("KillMessageMelee", 		7);
		settingsMap.put("KillMessageVoid", 			8);
		settingsMap.put("KillMessageBow", 			9);
		settingsMap.put("KillMessageSuicide", 		10);
		settingsMap.put("KillMessageSuffocation", 	11);
		settingsMap.put("KillMessageFallDamage", 	12);
		settingsMap.put("ColorblindMode",		 	13);

		playerSettings.put(defaultSettings, Arrays.asList(
				"0", 								//0
				"1", 								//1
				"2",		 						//2
				"bridgeegg", 						//3
				"portabledoinkhut", 				//4
				"homerunbat", 						//5
				"heartcontainer", 					//6
				" was killed by ", 					//7
				" was knocked into the void by ", 	//8
				" was shot by ", 					//9
				" committed suicide", 				//10
				" suffocated in a wall",			//11
				" was knocked off a cliff by ",		//12
				"off"								//13
				));
		
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
				
				if(settings.size() < getSettingsList().size())
					for(int i = settings.size(); i < getSettingsList().size(); i++)
						settings.add(playerSettings.get(defaultSettings).get(i));
				
				playerSettings.put(uuid, settings);
				br.close();
			}
		} catch(IOException e) { e.printStackTrace(); }
	}
	
	public static void saveSettings(Player p, List<String> settings) {
		try {
			File file = new File(filepath + p.getUniqueId() + ".ini");
			if(!file.exists())
				file.createNewFile();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			for(String setting : settings) {
				bw.write(setting.toString());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch(IOException e) { e.printStackTrace();	}
	}
	
	public static void setSetting(Player p, String setting, String value) {
		if(!settingsMap.containsKey(setting)) {
			Bukkit.getLogger().severe("Error: Invalid request to player settings field.");
			return;
		}
		
		UUID uuid = p.getUniqueId();
		List<String> settings = new ArrayList<>();
		settings = playerSettings.containsKey(uuid) ? playerSettings.get(uuid) : playerSettings.get(defaultSettings);
		settings.set(settingsMap.get(setting), value);
		playerSettings.put(uuid, settings);
		saveSettings(p, settings);
	}
	
	public static String getSetting(Player p, String setting) {
		return getSetting(p, settingsMap.get(setting));
	}
	
	public static String getSetting(Player p, int setting) {
		UUID uuid = p.getUniqueId();
		return playerSettings.containsKey(uuid) ? playerSettings.get(uuid).get(setting) : playerSettings.get(defaultSettings).get(setting);
	}
	
	public static List<String> getSettingsList() {
		ArrayList<String> out = new ArrayList<>(); 
		for(String entry : settingsMap.keySet())
			out.add(entry);
		return out;
	}
}
