package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.utils.ItemManager;
import bridgewars.utils.Utils;
import bridgewars.utils.World;

public class BuildLimits implements Listener {
	
	public BuildLimits(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) { //prevents players that are in game from placing blocks out of bounds
		Player player = e.getPlayer();
		Location loc = e.getBlockPlaced().getLocation();
		if(GameState.isState(GameState.ACTIVE)) {
			if(e.getBlock().getType() == Material.MOB_SPAWNER
			|| e.getBlock().getType() == Material.SKULL
			|| e.getBlock().getType() == Material.DIODE
			|| e.getBlock().getType() == Material.REDSTONE_COMPARATOR) {
				e.setCancelled(true);
				return;
			}
			
			if(!World.inGameArea(loc)) {
				ItemStack block = e.getItemInHand();
				if(Utils.getID(block).equals(Utils.getID(ItemManager.getItem("TemporaryWood").createItem(null)))
				|| Utils.getID(block).equals(Utils.getID(ItemManager.getItem("TemporaryStone").createItem(null)))
				|| Utils.getID(block).equals(Utils.getID(ItemManager.getItem("TemporaryIron").createItem(null)))
				|| player.getGameMode() == GameMode.CREATIVE)
					return;
				
				e.setCancelled(true);
				player.sendMessage(Chat.color("&cYou can't place this here!"));
			}
		}
	}
}
