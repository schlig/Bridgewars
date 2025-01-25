package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
import bridgewars.effects.Cloak;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class DisguiseKit implements ICustomItem, Listener {
	
	private Main plugin;
	private final UUID id = UUID.fromString("6012ca86-7d9b-4ae6-99ca-a332f6814edd"); //this was a fucking pain, uses the UUID of a player with the texture i wanted
	private final String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0d"
			+ "HA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzg1Mzk1MDhlM2I"
			+ "2YjY5MDRjMmVhYWU3NjZhZmFlYzlhM2Q1OTRiOTc1MTFiZDEyNmY4NTc5NTZhM"
			+ "DRiMDUyNCJ9fX0=";
	
	private int duration = 20; //duration of disguise kit in seconds (multiplied by 20 for ticks per second)
	
	public DisguiseKit(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
    @Override
    public Rarity getRarity() {
        return Rarity.GREEN;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
        item.setDurability((short) 3);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Disguise Kit");
        ItemBuilder.setSkullTexture(item, id, texture);
        ItemBuilder.setLore(item, Arrays.asList(
        		Chat.color("&r&7A special kit that transforms you"),
        		Chat.color("&r&7into a different player for " + duration + " seconds")));
        return item;
    }
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.SKULL)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(!Utils.getID(e.getItem()).equals(getClass().getSimpleName().toLowerCase()))
				return;
			
			Player p = e.getPlayer();
			if(Cloak.cloakedPlayers.contains(p.getUniqueId())) {
				p.sendMessage(Chat.color("&cYou are already disguised"));
				return;
			}
			
			List<Player> options = new ArrayList<>();
			for(Player o : Bukkit.getOnlinePlayers()) {
				if(!Utils.matchTeam(p, o) && p != o)
					options.add(o);
			}
			
			if(options.size() == 0) {
				p.sendMessage(Chat.color("&cThere is nobody online to disguise yourself as."));
				return;
			}
			
			new Cloak(p, options.get(Utils.rand(options.size())).getUniqueId(), duration).runTaskTimer(plugin, 0L, 20L);
			Utils.subtractItem(p);
		}
	}
}
