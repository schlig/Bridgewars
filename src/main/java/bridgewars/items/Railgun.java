package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

import bridgewars.Main;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;
import bridgewars.utils.Packet;
import bridgewars.utils.Utils;
import bridgewars.utils.World;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class Railgun implements ICustomItem, Listener {
	
	private final int damage = 10;
	private final int range = 50;
	
    public Railgun(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR
        || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())) {
                BlockIterator blockIterator = new BlockIterator(e.getPlayer(), range);
                Block block;
                Player p = e.getPlayer();
                ArrayList<Player> hitPlayers = new ArrayList<>();
                while (blockIterator.hasNext()){
                    block = blockIterator.next();
                        Location loc = block.getLocation();
                            for (Player player : Bukkit.getOnlinePlayers()) {
                            	if(player.getEyeLocation().distance(loc) < 1
                            	|| player.getLocation().distance(loc) < 1) {
	                                if(!Utils.matchTeam(player, p) && !hitPlayers.contains(player)){
	                                    player.damage(damage, p);
	                                    hitPlayers.add(player);
	                                }
                            	}
                                if(block.getType() != Material.AIR)
                                    player.playSound(block.getLocation(), Sound.DIG_WOOL, 1F, 1F);
                            }
                        Packet.sendParticle(EnumParticle.REDSTONE, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
                                100, 100, 100, 1, 1);
                        
                        if(block.getType() == Material.WOOL && World.inGameArea(block.getLocation()))
                        	block.breakNaturally(e.getItem());
                }
                if(hitPlayers.size() > 0)
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 100F, 0F);
                if(p.getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                    p.setItemInHand(item);
                }
                p.playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST2, 0.5F, 0.7F);
            }
        }
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.GREEN;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.BLAZE_ROD, 1);
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Railgun");
        ItemBuilder.setLore(item, Arrays.asList(
                Message.chat("&r&7Shoots a beam that destroys blocks,"),
                Message.chat("&r&7dealing " + damage + " hearts of damage")));
        ItemBuilder.disableStacking(item);
        return item;
    }
}
