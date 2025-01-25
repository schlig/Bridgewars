package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.effects.ScoreboardJam;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class SignalJammer implements ICustomItem, Listener {
	
	private static Main plugin;
	
	private final static int duration = 20;
	
    public SignalJammer(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        SignalJammer.plugin = plugin;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.NONE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.REDSTONE_COMPARATOR, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Signal Jammer");
        ItemBuilder.setLore(item, Arrays.asList(
                Chat.color("&r&7Disables the scoreboard for"),
                Chat.color("&r&7everyone except your team")));
        return item;
    }
    
    @EventHandler
    public void onUse(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR
		|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player user = e.getPlayer();
			if(Utils.getID(user.getItemInHand()).equals(getClass().getSimpleName().toLowerCase())
			&& GameState.isState(GameState.ACTIVE)) {
				activateEffect(user);
                Utils.subtractItem(user);
			}
		}
    }
    
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
    	if(Utils.getID(e.getPlayer().getItemInHand()).equals(getClass().getSimpleName().toLowerCase()))
    		e.setCancelled(true);
    }
    
    public static void activateEffect(Player user) {
		for(Player targets : Bukkit.getOnlinePlayers())
			if(!Utils.matchTeam(targets, user))
				targets.sendMessage(Chat.color("&cYour clock has been jammed!"));
			else
				targets.sendMessage(Chat.color("&6Your enemies' clocks have been jammed!"));
		
		ScoreboardJam.forceCancel = true;
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> 
			new ScoreboardJam(user, duration * 20, plugin).runTaskTimer(plugin, 0, 1L), 1L);
    }
}
