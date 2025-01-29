package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;
import bridgewars.utils.World;

public class Eraser implements ICustomItem, Listener {

	private final int radius = 3;
	
	private final int mapRadius = 22;
	private final int mapHeight = 24;
	
    public Eraser(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.CLAY_BRICK, 1);
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
		ItemBuilder.setName(item, "Eraser");
        ItemBuilder.setLore(item, Arrays.asList(
        		Chat.color("&r&7Erases all the color from nearby blocks")));
        return item;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.WHITE;
    }
    
    public ItemStack createItem() {
    	return createItem(null);
    }
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getItemInHand();
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())) {
				Location loc = p.getLocation();
				loc.setY(Math.floor(loc.getY()) - 1);
				Block origin = p.getWorld().getBlockAt(loc);
				
				//replace nearby blocks
				for(int x = -radius; x <= radius; x++) {
					for(int z = -radius; z <= radius; z++) {
						for(int y = -radius; y <= radius; y++) {
							if(origin.getRelative(x, y, z).getType() == Material.AIR || origin.getRelative(x, y, z).getType() == Material.WOOL) {
								if(Math.abs(origin.getRelative(x, y, z).getX()) <= mapRadius
								&& Math.abs(origin.getRelative(x, y, z).getZ()) <= mapRadius
								&& origin.getRelative(x, y, z).getY() <= mapHeight
								|| p.getGameMode() == GameMode.CREATIVE) {
									if(origin.getRelative(x, y, z).getType() == Material.WOOL && !World.blockIsIndestructible(origin.getRelative(x, y, z)))
										origin.getRelative(x, y, z).setData((byte) 0);
								}
							}
						}
					}
				}
				
				Utils.subtractItem(p);
				p.playSound(p.getLocation(), Sound.IRONGOLEM_THROW, 1.5F, 0.5F);
			}
		}
	}
}
