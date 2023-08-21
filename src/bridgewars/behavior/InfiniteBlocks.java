package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.GameState;

public class InfiniteBlocks implements Listener {
	
	public InfiniteBlocks(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e){
		if(GameState.isState(GameState.ACTIVE) 
		&& e.getPlayer().getGameMode() != GameMode.CREATIVE)
			if(e.getItemInHand().getType() == Material.WOOL
			&& e.getItemInHand().getAmount() < 10) {
				ItemStack item = e.getItemInHand();
				item.setAmount(64);
				e.getPlayer().setItemInHand(item);
			}
	}
}
