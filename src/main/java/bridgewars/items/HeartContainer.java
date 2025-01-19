package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
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

public class HeartContainer implements ICustomItem, Listener {
	
	private final int halfHearts = 4;
	
    public HeartContainer(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public Rarity getRarity() {
        return Rarity.BLUE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Heart Container");
        ItemBuilder.setLore(item, Arrays.asList(
                Message.chat("&r&7Increases maximum health by " + (halfHearts / 2) +" hearts"),
                Message.chat("&r&7Permanent Upgrade")));
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
            	p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
            	p.setMaxHealth(p.getMaxHealth() + halfHearts);
            	p.setHealth(p.getHealth() + halfHearts);
            	
            	Utils.subtractItem(p);
            }
        }
    }
}
