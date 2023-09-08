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
	
    public Railgun(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR
                || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())) {
                BlockIterator blockIterator = new BlockIterator(e.getPlayer(),50);
                Block block;
                Player p = e.getPlayer();
                ArrayList<Player> hitPlayers = new ArrayList<>();
                while (blockIterator.hasNext()){
                    block = blockIterator.next();
                    if(p.getGameMode() == GameMode.CREATIVE || !World.inGameArea(block.getLocation()) 
                    && block.getType() != Material.BEDROCK){
                        Location loc = block.getLocation();
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if(player.getLocation().distance(loc) < 1 && !Utils.matchTeam(player, p) && !hitPlayers.contains(player)){
                                    player.damage(7, p);
                                    hitPlayers.add(player);
                                }
                                if(block.getType() != Material.AIR){
                                    player.playSound(block.getLocation(), Sound.DIG_WOOL, 1F, 1F);
                                }
                            }
                        Packet.sendParticle(EnumParticle.REDSTONE, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
                                100, 100, 100,1, 1);
                        block.breakNaturally(e.getItem());
                    }
                    else {
                    	p.sendMessage(Message.chat("&cYou tried to shoot the Railgun, but it jammed"));
                    	return;
                    }
                }
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
        ItemBuilder.setName(item, "&rRailgun");
        ItemBuilder.setLore(item, Arrays.asList(
                Message.chat("&r&7Shoots a beam that destroys"),
                Message.chat("&r&7blocks and players.")));
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        return item;
    }
}
