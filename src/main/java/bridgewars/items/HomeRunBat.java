package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.game.Leaderboards;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class HomeRunBat implements ICustomItem, Listener {

    public HomeRunBat(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public Rarity getRarity() {
        return Rarity.RED;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.WOOD_SWORD, 1);
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Home Run Bat");
        ItemBuilder.setLore(item, Arrays.asList(Chat.color("&r&7Deals massive knockback"),
                Chat.color("&r&7Only has 3 uses")));
        return item;
    }

    @EventHandler
    public void onHit(PlayerItemDamageEvent e) {
	    if(Utils.getID(e.getItem()).equals(getClass().getSimpleName().toLowerCase())) {
		    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ANVIL_LAND, 1F, 1.8F);
		    e.setDamage(20);
		    Leaderboards.addPoint(e.getPlayer(), "items");
	    }
    }
}