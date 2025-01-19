package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;

public class PitfallTrap implements ICustomItem, Listener {
	
    public PitfallTrap(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.WHITE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.CLAY_BALL, 1);
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Pitfall");
        ItemBuilder.setLore(item, Arrays.asList(
                Message.chat("&r&7Deletes every wool block beneath you in a column")));
        return item;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR
        || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())
            && GameState.isState(GameState.ACTIVE)) {
            	Player p = e.getPlayer();
            	Location loc = p.getLocation();
            	int y = loc.getBlockY();
            	int x = loc.getBlockX();
            	int z = loc.getBlockZ();
            	Block block = p.getWorld().getBlockAt(x, y - 1, z);
            	
            	for(int i = 0; i < y; i++)
            		if(block.getRelative(0, -i, 0).getType() == Material.WOOL)
            			block.getRelative(0, -i, 0).breakNaturally();
            	
            	Utils.subtractItem(p);
            }
        }
    }
}
