package bridgewars.items;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.utils.*;
import bridgewars.utils.World;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.Arrays;

public class Railgun implements ICustomItem, Listener {
    public Railgun(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @Override
    public Rarity getRarity() {
        return Rarity.GREEN;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack out = new ItemStack(Material.IRON_INGOT, 1);
        ItemBuilder.setName(out, "&rRailgun");
        ItemBuilder.setLore(out, Arrays.asList(
                Message.chat("&r&7Shoots a beam that destroys"),
                Message.chat("&r&7blocks and players.")));
        return out;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR
                || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item;
            if(Utils.matchItem(item = e.getItem(), ItemManager.getItem("Railgun").createItem(null))) {
                BlockIterator blockIterator = new BlockIterator(e.getPlayer(),50);
                Block block;
                Player p = e.getPlayer();
                while (blockIterator.hasNext()){
                    block = blockIterator.next();
                    if(!World.inGameArea(block.getLocation()) && block.getType() != Material.BEDROCK){
                        Location loc = block.getLocation();
                            for (Player player : Bukkit.getOnlinePlayers()
                            ) {
                                if(player.getLocation().distance(loc) < 1 && !Utils.matchTeam(player, p)){
                                    player.damage(5, p);
                                }
                                if(block.getType() != Material.AIR){
                                    player.playSound(block.getLocation(), Sound.DIG_WOOL, 1F, 1F);
                                }
                            }
                        Utils.sendSingleParticle(EnumParticle.REDSTONE, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
                                100, 100, 100,1, 1);
                        block.breakNaturally(e.getItem());
                    }
                    else {
                        break;
                    }
                }
                item.setAmount(item.getAmount() - 1);
                p.setItemInHand(item);
                p.playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST2, 0.5F, 0.7F);
            }
        }
    }
}
