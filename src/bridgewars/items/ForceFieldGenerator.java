package bridgewars.items;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import bridgewars.Main;
import bridgewars.effects.RepelField;

public class ForceFieldGenerator implements Listener {

    ArrayList<BukkitTask> taskList = new ArrayList<BukkitTask>();
    private Main plugin;

    public ForceFieldGenerator (Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(item.getType() == Material.DIAMOND) {
                taskList.add(new RepelField(p, 5, 1, 100, plugin).runTaskTimer(plugin,0,1));
            }
        }
    }
}