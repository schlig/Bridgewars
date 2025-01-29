package bridgewars.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Utils;

public class UpgradeBook implements ICustomItem, Listener {
	
	private final double procChance = 0.25; //25% chance of actually doing anything
	
	public UpgradeBook(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@Override
	public Rarity getRarity() {
		return Rarity.GREEN;
	}

	@Override
	public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.BOOK, 1);
        ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Upgrade Book");
        ItemBuilder.setLore(item, Arrays.asList(
                Chat.color("&r&71/4 chance to upgrade one"),
                Chat.color("&r&7of your items")));
        return item;
	}
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		Player user = e.getPlayer();
		ItemStack item = e.getItem();
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())) {
				double rng = Math.random();
				
				if(rng < 1 - procChance) {
					user.sendMessage("Nope!");
					user.damage(1);
					Utils.subtractItem(user);
					return;
				}
				
				ItemStack targetItem = null;
				int slot = 0;
				while(targetItem == null) {
					slot = Utils.rand(39);
					targetItem = user.getInventory().getItem(slot);
				}
				
				rng = Math.random();
				if(rng < 0.1)
					enchantItem(user, targetItem, slot, Enchantment.PROTECTION_EXPLOSIONS, 1);
				else if(rng < 0.2)
					enchantItem(user, targetItem, slot, Enchantment.PROTECTION_FIRE, 1);
				else if(rng < 0.3)
					enchantItem(user, targetItem, slot, Enchantment.ARROW_KNOCKBACK, 1);
				else if(rng < 0.4)
					enchantItem(user, targetItem, slot, Enchantment.DURABILITY, 1);
				else if(rng < 0.5)
					enchantItem(user, targetItem, slot, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				else if(rng < 0.6)
					enchantItem(user, targetItem, slot, Enchantment.KNOCKBACK, 1);
				else if(rng < 0.7)
					enchantItem(user, targetItem, slot, Enchantment.ARROW_DAMAGE, 1);
				else if(rng < 0.8)
					enchantItem(user, targetItem, slot, Enchantment.PROTECTION_PROJECTILE, 1);
				else if(rng < 0.9)
					enchantItem(user, targetItem, slot, Enchantment.DAMAGE_ALL, 1);
				else
					enchantItem(user, targetItem, slot, Enchantment.FIRE_ASPECT, 1);
				
				Utils.subtractItem(user);
			}
		}
	}
	
	private void enchantItem(Player user, ItemStack item, int slot, Enchantment enchant, int level) {
		user.sendMessage(Chat.color("&6One of your items have been upgraded!"));
		item.addUnsafeEnchantment(enchant, level);
		user.getInventory().setItem(slot, item);
	}
}
