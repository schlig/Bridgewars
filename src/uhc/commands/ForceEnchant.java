package uhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import uhc.Main;
import uhc.utils.Utils;

public class ForceEnchant implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public ForceEnchant(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("forceenchant").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("No");
			return true;
		}
		
		Player player = (Player) sender;
		ItemStack item = player.getItemInHand();
		String ench = "Error";
		int value;
		
		if(args.length < 2) {
			player.sendMessage(Utils.chat("&cUsage: /forceenchant <enchantment> <value>"));
			return true;
		}
		
		try {
			value = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			player.sendMessage(Utils.chat("&cUsage: /forceenchant <enchantment> <value>"));
			return true;
		}
		
		if(value < 0) {
			player.sendMessage(Utils.chat("&cThe number you have entered (" + args[1] + ") is too small, it must be at least 0"));
			return true;
		}
		else if (value > 32767) {
			player.sendMessage(Utils.chat("&cThe number you have entered (" + args[1] + ") is too big, it must be at most 32767"));
			return true;
		}
		
		else {
			switch(args[0]) {
			case "prot":
			case "protection":
				if(value > 0)
					item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, value);
				else
					item.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
				ench = "Protection";
				break;
			case "fprot":
			case "fire_protection":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, value);
				else
					item.removeEnchantment(Enchantment.PROTECTION_FIRE);
				ench = "Fire Protection";
				break;
			case "ff":
			case "feather_falling":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, value);
				else
					item.removeEnchantment(Enchantment.PROTECTION_FALL);
				ench = "Feather Falling";
				break;
			case "bprot":
			case "blast_protection":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, value);
				else
					item.removeEnchantment(Enchantment.PROTECTION_EXPLOSIONS);
				ench = "Blast Protection";
				break;
			case "proj":
			case "projectile_protection":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, value);
				else
					item.removeEnchantment(Enchantment.PROTECTION_PROJECTILE);
				ench = "Projectile Protection";
				break;
			case "resp":
			case "respiration":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.OXYGEN, value);
				else
					item.removeEnchantment(Enchantment.OXYGEN);
				ench = "Respiration";
				break;
			case "aqua":
			case "aqua_affinity":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.WATER_WORKER, value);
				else
					item.removeEnchantment(Enchantment.WATER_WORKER);
				ench = "Aqua Affinity";
				break;
			case "thorns":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.THORNS, value);
				else
					item.removeEnchantment(Enchantment.THORNS);
				ench = "Thorns";
				break;
			case "depth":
			case "depth_strider":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, value);
				else
					item.removeEnchantment(Enchantment.DEPTH_STRIDER);
				ench = "Depth Strider";
				break;
			case "sharp":
			case "sharpness":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, value);
				else
					item.removeEnchantment(Enchantment.DAMAGE_ALL);
				ench = "Sharpness";
				break;
			case "smite":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, value);
				else
					item.removeEnchantment(Enchantment.DAMAGE_UNDEAD);
				ench = "Smite";
				break;
			case "bane":
			case "bane_of_arthropods":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, value);
				else
					item.removeEnchantment(Enchantment.DAMAGE_ARTHROPODS);
				ench = "Bane of Arthropods";
				break;
			case "kb":
			case "knockback":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.KNOCKBACK, value);
				else
					item.removeEnchantment(Enchantment.KNOCKBACK);
				ench = "Knockback";
				break;
			case "fire":
			case "fire_aspect":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, value);
				else
					item.removeEnchantment(Enchantment.FIRE_ASPECT);
				ench = "Fire Aspect";
				break;
			case "looting":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, value);
				else
					item.removeEnchantment(Enchantment.LOOT_BONUS_MOBS);
				ench = "Looting";
				break;
			case "eff":
			case "efficiency":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.DIG_SPEED, value);
				else
					item.removeEnchantment(Enchantment.DIG_SPEED);
				ench = "Efficiency";
				break;
			case "silk":
			case "silk_touch":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.SILK_TOUCH, value);
				else
					item.removeEnchantment(Enchantment.SILK_TOUCH);
				ench = "Silk Touch";
				break;
			case "unb":
			case "unbreaking":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.DURABILITY, value);
				else
					item.removeEnchantment(Enchantment.DURABILITY);
				ench = "Unbreaking";
				break;
			case "fortune":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, value);
				else
					item.removeEnchantment(Enchantment.LOOT_BONUS_BLOCKS);
				ench = "Fortune";
				break;
			case "power":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, value);
				else
					item.removeEnchantment(Enchantment.ARROW_DAMAGE);
				ench = "Power";
				break;
			case "punch":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, value);
				else
					item.removeEnchantment(Enchantment.ARROW_KNOCKBACK);
				ench = "Punch";
				break;
			case "flame":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.ARROW_FIRE, value);
				else
					item.removeEnchantment(Enchantment.ARROW_FIRE);
				ench = "Flame";
				break;
			case "inf":
			case "infinity":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, value);
				else
					item.removeEnchantment(Enchantment.ARROW_INFINITE);
				ench = "Infinity";
				break;
			case "lots":
			case "luck_of_the_sea":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.LUCK, value);
				else
					item.removeEnchantment(Enchantment.LUCK);
				ench = "Luck of the Sea";
				break;
			case "lure":
				if (value > 0)
					item.addUnsafeEnchantment(Enchantment.LURE, value);
				else
					item.removeEnchantment(Enchantment.LURE);
				ench = "Lure";
				break;
			default:
				player.sendMessage(Utils.chat("&cUsage: /forceenchant <enchantment> <value>"));
				return true;
			}
			if(value > 0)
				player.sendMessage(Utils.chat("&aApplied " + ench + " " + args[1] + " to your held item."));
			else
				player.sendMessage(Utils.chat("&aRemoved " + ench + " from your held item."));
		}
		return true;
	}
}
