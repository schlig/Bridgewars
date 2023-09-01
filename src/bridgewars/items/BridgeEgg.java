package bridgewars.items;

import bridgewars.Main;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;
import bridgewars.utils.ICustomItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class BridgeEgg implements ICustomItem, Listener {
    public BridgeEgg(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.GREEN;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack out = new ItemStack(Material.EGG, 1);
        ItemBuilder.setName(out, "&fBridge Egg");
        ItemBuilder.setLore(out, Arrays.asList(Message.chat("&r&7Automatically builds a bridge for you")));
        return out;
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
}