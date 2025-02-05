package bridgewars.effects;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.game.GameState;

public class CooldownTimer extends BukkitRunnable {

	private final Player user;
	private int cooldown;
	private ArrayList<Player> cooldownList;
	private String message;
	
	public CooldownTimer(Player user, int cooldown, ArrayList<Player> cooldownList, String message) {
		this.user = user;
		this.cooldown = cooldown;
		this.cooldownList = cooldownList;
		this.message = message;
	}
	
	@Override
	public void run() {
		if(cooldown <= 0 || user.getGameMode() == GameMode.CREATIVE || GameState.isState(GameState.ENDING)) {
			if(message != null && user.getGameMode() != GameMode.CREATIVE)
				user.sendMessage(message);
			cooldownList.remove(user);
			this.cancel();
		}
		cooldown--;
	}
}
