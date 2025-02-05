package bridgewars.effects;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.game.GameState;
import bridgewars.settings.enums.HidePlayers;
import bridgewars.utils.ItemManager;

public class MetalBoxEffect extends BukkitRunnable {

	private final Player user;
	private int duration;
	private boolean hasChestplate = false;
	
	public MetalBoxEffect(Player user, int duration) {
		this.user = user;
		this.duration = duration;
		if(user.getInventory().getChestplate() != null)
			hasChestplate = true;
    	user.getInventory().setChestplate(ItemManager.getItem("MetalChestplate").createItem(user));
	}
	
	@Override
	public void run() {
		duration--;
		
		if(duration <= 0 || GameState.isState(GameState.ENDING)) {
			if(hasChestplate) {
				if(HidePlayers.getState().isEnabled() || !GameState.isState(GameState.ACTIVE))
					user.getInventory().setChestplate(ItemManager.getItem("BasicChestplate").createItem(null));
				else
					user.getInventory().setChestplate(ItemManager.getItem("BasicChestplate").createItem(user));
			}
			else user.getInventory().setChestplate(null);
			this.cancel();
		}
	}
}
