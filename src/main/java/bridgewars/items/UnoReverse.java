package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;

public class UnoReverse implements ICustomItem, Listener {
	
    public UnoReverse(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public Rarity getRarity() {
        return Rarity.WHITE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Uno Reverse Card");
        ItemBuilder.setLore(item, Arrays.asList(
                Message.chat("&r&7Flips another player to face the opposite"),
                Message.chat("&r&7direction")));
        return item;
    }
    
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {

		if(e.getEntity() instanceof Player
		&& e.getDamager() instanceof Player) {
			Player user = (Player) e.getDamager();
			Player target = (Player) e.getEntity();
			
			if(Utils.getID(user.getItemInHand()).equals(getClass().getSimpleName().toLowerCase())
			&& GameState.isState(GameState.ACTIVE)) {
				
				if(Utils.matchTeam(user, target))
					return;
				
				Location loc = target.getLocation();
				
				double x = loc.getX();
				double y = loc.getY();
				double z = loc.getZ();

				float yaw = loc.getYaw();
				float pitch = loc.getPitch();
				
				if(yaw > 0)
					yaw -= 180;
				else yaw += 180;
				//note: pitch goes from -180 to 180 (360 degrees, idk why its handled like this)
				
				target.teleport(new Location(Bukkit.getWorld("world"), x, y, z, yaw, pitch));
				Utils.subtractItem(user);
			}
		}
    }
}
