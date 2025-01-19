package bridgewars.items;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.ItemManager;

public class BottomlessWaterBucket implements ICustomItem, Listener {
	
	private Main plugin;
	
	public BottomlessWaterBucket (Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlace(PlayerBucketEmptyEvent e) {
		if(e.getBucket() == Material.WATER_BUCKET 
				&& e.getPlayer().getGameMode() != GameMode.CREATIVE 
				&& GameState.isState(GameState.ACTIVE))
			Bukkit.getScheduler().runTaskLater(plugin, () -> 
			e.getPlayer().setItemInHand(ItemManager.getItem("BottomlessWaterBucket").createItem(null)) , 1L);
	}
	
    @Override
    public Rarity getRarity() {
        return Rarity.NONE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.WATER_BUCKET, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "&rBottomless Water Bucket");
        return item;
    }
}
