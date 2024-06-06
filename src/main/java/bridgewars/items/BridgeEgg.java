package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;

public class BridgeEgg implements ICustomItem, Listener {
	
    public BridgeEgg(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {
        if(e.getEntity() instanceof Egg && e.getEntity().getShooter() instanceof Player) {
            if( ((Player)e.getEntity().getShooter()).getGameMode() == GameMode.CREATIVE)
                new bridgewars.effects.BridgeEgg(e.getEntity(), true).runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 3L, 0L);
            else
                new bridgewars.effects.BridgeEgg(e.getEntity(), false).runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 3L, 0L);
        }
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.WHITE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.EGG, 1);
        ItemBuilder.setName(item, "&fBridge Egg");
        ItemBuilder.setLore(item, Arrays.asList(Message.chat("&r&7Automatically builds a bridge for you")));
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        return item;
    }
}