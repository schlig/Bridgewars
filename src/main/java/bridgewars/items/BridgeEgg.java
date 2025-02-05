package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.Leaderboards;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;

public class BridgeEgg implements ICustomItem, Listener {
	
    public BridgeEgg(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.WHITE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.EGG, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Bridge Egg");
        ItemBuilder.setLore(item, Arrays.asList(Chat.color("&r&7Automatically builds a bridge for you")));
        return item;
    }
    
    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {
        if(e.getEntity() instanceof Egg && e.getEntity().getShooter() instanceof Player) {
        	Projectile egg = (Projectile) e.getEntity();
        	Player user = (Player) egg.getShooter();
        	
            new bridgewars.effects.BridgeEgg(e.getEntity(), (user.getGameMode() == GameMode.CREATIVE)).runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 3L, 0L);
            
			Leaderboards.addPoint(user, "items");
        }
    }
}