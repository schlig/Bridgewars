package bridgewars.items;

import bridgewars.Main;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class HomeRunBat implements ICustomItem, Listener {

    public HomeRunBat(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onHit(PlayerItemDamageEvent e) {
        if(e.getItem().getType() == Material.WOOD_SWORD)
            if(Utils.compareItemName(e.getItem(), "&aHome Run Bat"))
                e.setDamage(20);
    }

    @Override
    public Rarity getRarity() {
        return Rarity.RED;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack homeRunBat = new ItemStack(Material.WOOD_SWORD, 1);
        homeRunBat.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
        ItemBuilder.setName(homeRunBat, "&aHome Run Bat");
        ItemBuilder.setLore(homeRunBat, Arrays.asList(Message.chat("&r&7Deals massive knockback"),
                Message.chat("&r&7Only has 3 uses")));
        return homeRunBat;
    }
}