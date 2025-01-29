package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;
import bridgewars.utils.World;

public class PitfallTrap implements ICustomItem, Listener {
	
	private final int maxRadius = 32;
	private final int maxHeight = 32;
	
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
                Chat.color("&r&7Right click a wool block"),
                Chat.color("&r&7to delete the entire column")));
        return item;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())
            && GameState.isState(GameState.ACTIVE)) {
            	Player p = e.getPlayer();
            	Block targetBlock = e.getClickedBlock();
            	int x = targetBlock.getX();
            	int z = targetBlock.getZ();

            	if(Math.abs(x) > maxRadius || Math.abs(z) > maxRadius)
            		return;
            	
            	Block block = p.getWorld().getBlockAt(new Location(p.getWorld(), x, 0, z));
            	
            	
            	for(int i = 0; i <= maxHeight; i++)
            		if(block.getRelative(0, i, 0).getType() != Material.BEDROCK && !World.blockIsIndestructible(block.getRelative(0, i, 0)))
            			block.getRelative(0, i, 0).breakNaturally();
            	
            	p.playSound(p.getLocation(), Sound.FIZZ, 1F, 2F);
            	Utils.subtractItem(p);
            }
        }
    }
}
