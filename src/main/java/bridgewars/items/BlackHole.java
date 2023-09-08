package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import bridgewars.Main;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;

public class BlackHole implements ICustomItem, Listener {
	
	private Main plugin;
	
	public BlackHole(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onThrow(ProjectileLaunchEvent e) {
		if(e.getEntity() instanceof Snowball) {
			if(!(e.getEntity().getShooter() instanceof Player))
				return;
			Vector v = e.getEntity().getVelocity();
			v.multiply(1.5);
			e.getEntity().setVelocity(v);

			if(((Player)e.getEntity().getShooter()).getGameMode() != GameMode.CREATIVE)
				new bridgewars.effects.BlackHole(e.getEntity(), 1, false).runTaskTimer(plugin, 0L, 0L);
			else
				new bridgewars.effects.BlackHole(e.getEntity(), 1, true).runTaskTimer(plugin, 0L, 0L);
		}
	}
	
    @Override
    public Rarity getRarity() {
        return Rarity.RED;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.SNOW_BALL, 1);
        ItemBuilder.setName(item, "&cBlack Hole");
        ItemBuilder.setLore(item, Arrays.asList(Message.chat("&r&7A throwable black hole that eats"),
                Message.chat("&r&7every block in its path")));
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        return item;
    }
}
