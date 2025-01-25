package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import bridgewars.Main;
import bridgewars.game.GameState;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class MagicStopwatch implements ICustomItem, Listener {
	
	private final int speedDuration = 999999
									* 20;
	private final int speedLevel = 1;
	
    public MagicStopwatch(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public Rarity getRarity() {
        return Rarity.BLUE;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.WATCH, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Magic Stopwatch");
        ItemBuilder.setLore(item, Arrays.asList(
                Chat.color("&r&7Grants Speed II until death"),
                Chat.color("&r&7Note: This item currently sucks for its rarity"),
                Chat.color("&r&7It will be buffed heavily when I stop being lazy")));
        return item;
    }
    
    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR
        || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())
            && GameState.isState(GameState.ACTIVE)) {
            	Player p = e.getPlayer();
            	p.playSound(p.getLocation(), Sound.CLICK, 1F, 1F);
            	p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, speedDuration, speedLevel));
            	
            	Utils.subtractItem(p);
            }
        }
    }
}
