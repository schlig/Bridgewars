package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import bridgewars.Main;
import bridgewars.game.Leaderboards;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;

public class BlackHole implements ICustomItem, Listener {
	
	private Main plugin;
	
	public BlackHole(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
    @Override
    public Rarity getRarity() {
        return Rarity.RED;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.SNOW_BALL, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Black Hole");
        ItemBuilder.setLore(item, Arrays.asList(Chat.color("&r&7A throwable black hole that eats"),
                								Chat.color("&r&7every block in its path")));
        ItemBuilder.disableStacking(item);
        return item;
    }
	
	@EventHandler
	public void onThrow(ProjectileLaunchEvent e) {
		if(e.getEntity() instanceof Snowball) {
			if(!(e.getEntity().getShooter() instanceof Player))
				return;
			Vector v = e.getEntity().getVelocity();
			v.multiply(1.5);
			e.getEntity().setVelocity(v);
			
			Leaderboards.addPoint( (Player) e.getEntity().getShooter(), "items");
			new bridgewars.effects.BlackHole(e.getEntity(), 1, false).runTaskTimer(plugin, 0L, 0L);
		}
	}
}
