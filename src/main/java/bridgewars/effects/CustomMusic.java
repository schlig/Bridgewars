package bridgewars.effects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.game.GameState;
import bridgewars.utils.Packet;

public class CustomMusic extends BukkitRunnable {
	
	private final String filepath = "./plugins/bridgewars/music/";

	private Player player;
	private String musicID;
	private int duration;
	
	private int tickDelay;
	private Boolean autoCancel;
	private BufferedReader br;
	private List<String[]> dataList = new ArrayList<String[]>();
	private int i = 0;
	
	public CustomMusic(Player player, String musicID, int duration, Boolean autoCancel) {
		this.player = player;
		this.duration = duration;
		this.musicID = musicID;
		this.autoCancel = autoCancel;
		
		this.tickDelay = 0;
		String line;
		
		try { this.br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath + this.musicID + ".bwmus")))); }
		catch (Exception e) { e.printStackTrace(); }
		if(dataList.size() == 0) {
			try {
				while((line = br.readLine()) != null) {
					dataList.add(line.split("[,]"));
				}
			} catch (IOException e) {  }
		}
	}
	
	@Override
	public void run() {
		if(tickDelay <= 0 || GameState.isState(GameState.ENDING))
			for(int j = i; j < dataList.size(); j++) {
				String instrument = dataList.get(i)[0].replaceAll(",", "");
				Double rawPitch = Double.parseDouble(dataList.get(i)[1].replaceAll(",", ""));
				Float pitch = (float) Math.pow(2, (rawPitch - 12) / 12);
				tickDelay = Integer.parseInt(dataList.get(i)[2].replaceAll(",", ""));
				Packet.playSound(instrument, player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), 1, pitch);
				i++;
				if(tickDelay > 0)
					break;
			}
		if(i >= dataList.size())
			i = 0;
		if(duration <= 0 || player.isDead() || autoCancel)
			this.cancel();
		tickDelay--;
		duration--;
	}
}
