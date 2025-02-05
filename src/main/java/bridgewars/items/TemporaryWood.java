package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import bridgewars.Main;
import bridgewars.effects.BlockDecay;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class TemporaryWood implements ICustomItem, Listener {
	
	private Main plugin;
	
	private final int duration = 10;
	private final int amount = 16;
	
	private List<BukkitTask> decayingWoodBlocks = new ArrayList<>();
	
	public TemporaryWood(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
    @Override
    public Rarity getRarity() {
        return Rarity.WHITE;
    }
    
    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.WOOD, amount);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Temporary Wood");
        ItemBuilder.setLore(item, Arrays.asList(
                Chat.color("&r&7Decays after " + duration + " seconds"),
                Chat.color("&r&7Can be placed out of bounds")));
        return item;
    }
    
    @EventHandler
    public void onPlace (BlockPlaceEvent e) {
    	ItemStack item = e.getItemInHand();
    	if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase()))
    		decayingWoodBlocks.add(new BlockDecay(e.getBlock(), duration * 20).runTaskTimer(plugin, 0L, 1L));
    }
}