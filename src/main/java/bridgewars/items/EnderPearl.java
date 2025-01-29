package bridgewars.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.Leaderboards;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;

public class EnderPearl implements ICustomItem, Listener {
	
	public EnderPearl(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
    @Override
    public Rarity getRarity() {
        return Rarity.GREEN;
    }
    
    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.ENDER_PEARL, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Ender Pearl");
        return item;
    }
    
    @EventHandler
    public void onUse(ProjectileLaunchEvent e) {
    	if(e.getEntity() instanceof EnderPearl
    	&& e.getEntity().getShooter() instanceof Player)
			Leaderboards.addPoint((Player) e.getEntity().getShooter(), "items");
    }
}