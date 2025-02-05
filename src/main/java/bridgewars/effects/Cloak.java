package bridgewars.effects;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.utils.Packet;

public class Cloak extends BukkitRunnable {
	
	public static ArrayList<UUID> cloakedPlayers = new ArrayList<>();
	
	private final Player player;
	private int duration; 
	
	public Cloak(Player player, UUID target, int duration) {
		this.player = player;
		this.duration = duration;
		Packet.setDisguise(player, target);
		cloakedPlayers.add(player.getUniqueId());
		player.sendMessage(Chat.color("&lYou have disguised yourself as " + Bukkit.getPlayer(target).getDisplayName() + "&r&l!"));
	}
	
	@Override
	public void run() {
		if(duration == 0 || !cloakedPlayers.contains(player.getUniqueId()) || GameState.isState(GameState.ENDING)) {
			if(cloakedPlayers.contains(player.getUniqueId()))
				cloakedPlayers.remove(player.getUniqueId());
			Packet.setDisguise(player, player.getUniqueId());
			player.sendMessage(Chat.color("&c&lYou are no longer disguised!"));
			this.cancel();
		}
		duration--;
	}
	
	public static void remove(Player player) {
		if(cloakedPlayers.contains(player.getUniqueId()))
			cloakedPlayers.remove(player.getUniqueId());
	}
}
