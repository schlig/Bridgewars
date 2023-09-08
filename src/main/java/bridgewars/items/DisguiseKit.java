package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;

public class DisguiseKit implements ICustomItem, Listener {
	
	private Main plugin;
	private final UUID id = UUID.fromString("6012ca86-7d9b-4ae6-99ca-a332f6814edd");
	private final String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzg1Mzk1MDhlM2I2YjY5MDRjMmVhYWU3NjZhZmFlYzlhM2Q1OTRiOTc1MTFiZDEyNmY4NTc5NTZhMDRiMDUyNCJ9fX0=";
	
	public DisguiseKit(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(!Utils.getID(e.getItem()).equals(getClass().getSimpleName().toLowerCase()))
				return;
			
			Player p = e.getPlayer();
			if(Cloak.cloakedPlayers.contains(p.getUniqueId())) {
				p.sendMessage(Message.chat("&cYou are already disguised!"));
				return;
			}
			
			List<Player> options = new ArrayList<>();
			for(Player o : Bukkit.getOnlinePlayers()) {
				if(!Utils.matchTeam(p, o) && p != o)
					options.add(o);
			}
			
			if(options.size() == 0) {
				p.sendMessage(Message.chat("&cThere is nobody online to disguise yourself as!"));
				return;
			}
			
			new Cloak(p, options.get(options.size() - 1).getUniqueId(), 60).runTaskTimer(plugin, 0L, 20L);
			if(p.getGameMode() != GameMode.CREATIVE) {
				ItemStack item = e.getItem();
				item.setAmount(item.getAmount() - 1);
				p.setItemInHand(item);
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(Utils.getID(e.getItemInHand()).equals(getClass().getSimpleName().toLowerCase()))
			e.setCancelled(true);
	}
	
    @Override
    public Rarity getRarity() {
        return Rarity.GREEN;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
        item.setDurability((short) 3);
        ItemBuilder.setName(item, "&aDisguise Kit");
        ItemBuilder.setSkullTexture(item, id, texture);
        ItemBuilder.setLore(item, Arrays.asList(
        		Message.chat("&r&7A special kit that transforms you"),
        		Message.chat("&r&7into a different player for 1 minute")));
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        return item;
    }
}
