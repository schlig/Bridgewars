package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.effects.CooldownTimer;
import bridgewars.game.GameState;
import bridgewars.game.Leaderboards;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class UltimastPortal implements ICustomItem, Listener {

	private Main plugin;
	
	private ArrayList<Player> cooldownList = new ArrayList<>();
	private final int cooldown = 3;
	
	private final static int radius = 35;
	private final static int height = 30;
	
    public UltimastPortal(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }
    
    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.EYE_OF_ENDER, 1);
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
		ItemBuilder.setName(item, "Ultimast Portal");
        ItemBuilder.setLore(item, Arrays.asList(
                Chat.color("&r&7Will teleport you literally anywhere")));
        return item;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.WHITE;
    }
    
    public ItemStack createItem() {
    	return createItem(null);
    }
    
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		Player user = e.getPlayer();
		ItemStack item = user.getItemInHand();
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())) {
				if(cooldownList.contains(user)) {
					user.sendMessage(Chat.color("&cThis item is currently on cooldown."));
					return;
				}
				cooldownList.add(user);
				new CooldownTimer(user, cooldown, cooldownList, Chat.color("&aThe Ultimast Portal has recharged.")).runTaskTimer(plugin, 0, 20);
				activateEffect(user);
				Utils.subtractItem(user);
			    Leaderboards.addPoint(user, "items");
			}
		}
	}
	
	@EventHandler
	public void disableThrow(ProjectileLaunchEvent e) {
		if(e.getEntity() instanceof EnderSignal
		&& GameState.isState(GameState.ACTIVE))
			e.setCancelled(true);
	}
	
	public static void activateEffect(Player user) {
		if(SadTear.sadRoomed.contains(user)) {
			user.kickPlayer("Oopsie Poopsie!!! Looks like that Ultimast Portal was tampered with.");
			return;
		}
		double x = Utils.rand(radius * 2) - radius;
		double y = Utils.rand(height) - 2;
		double z = Utils.rand(radius * 2) - radius;
		float pitch = user.getLocation().getPitch();
		float yaw = user.getLocation().getYaw();
		user.teleport(new Location(Bukkit.getWorld("world"), x, y, z, yaw, pitch));
		user.playSound(user.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
	}
}
