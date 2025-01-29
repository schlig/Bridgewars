package bridgewars.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import bridgewars.Main;
import bridgewars.effects.BlockDecay;
import bridgewars.game.GameState;
import bridgewars.game.Leaderboards;
import bridgewars.settings.enums.WoolDecay;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class WoolBlocks implements ICustomItem, Listener {
	
	private List<BukkitTask> woolDecay = new ArrayList<BukkitTask>();
	private final int duration = 90;
	private Main plugin;
	
	public WoolBlocks(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
    @Override
    public Rarity getRarity() {
        return Rarity.NONE;
    }
    
    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.WOOL, 64);
        String team = ItemBuilder.getTeamName(p);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "&r" + team.substring(0, 1).toUpperCase() + team.substring(1) + " Wool");
        
        switch(team) {
            case "red":
                item.setDurability((short) 14);
                break;
            case "blue":
                item.setDurability((short) 3);
                break;
            case "green":
                item.setDurability((short) 5);
                break;
            case "yellow":
                item.setDurability((short) 4);
                break;
        }
        return item;
    }
    
    public ItemStack createItem() {
    	return createItem(null);
    }
    
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
    	if(Utils.getID(e.getItemInHand()).equals(getClass().getSimpleName().toLowerCase())
    	&& e.getPlayer().getGameMode() != GameMode.CREATIVE
    	&& GameState.isState(GameState.ACTIVE)) {
    		Leaderboards.addPoint(e.getPlayer(), "lifetimeBlocks");
    		
    		if(WoolDecay.getState().isEnabled())
    			woolDecay.add(new BlockDecay(e.getBlock(), duration * 20).runTaskTimer(plugin, 0L, 1L));
    	}
    }
}