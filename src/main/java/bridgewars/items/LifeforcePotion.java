package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import bridgewars.Main;
import bridgewars.effects.PlotArmor;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Message;
import bridgewars.utils.Utils;

public class LifeforcePotion implements ICustomItem, Listener {

	public LifeforcePotion(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDrink(PlayerItemConsumeEvent e) {
		if(Utils.getID(e.getItem()).equals(getClass().getSimpleName().toLowerCase())) {
			Player p = e.getPlayer();
			PlotArmor.armoredPlayers.add(p);
			Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> 
				p.getInventory().remove(Material.GLASS_BOTTLE), 0L);
		}
	}	
	
    @Override
    public Rarity getRarity() {
        return Rarity.RED;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.POTION, 1);
        item.setDurability((short) 8233);
        ItemMeta meta = item.getItemMeta();
        PotionMeta effect = (PotionMeta) meta;
        effect.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0), true);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        ItemBuilder.setName(item, "&cLifeforce Potion");
        ItemBuilder.setLore(item, Arrays.asList(Message.chat("&r&7Strength I (0:30)"),
                Message.chat("&r&7Plot Armor"),
                Message.chat("&r&7Survive fatal damage"),
                Message.chat("&r&7with half a heart")));
        ItemBuilder.hideFlags(item);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        return item;
    }
}
