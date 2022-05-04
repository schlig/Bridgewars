package uhc.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import uhc.Main;
import uhc.utils.Utils;

public class ClearName implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ClearName(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("clearname").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("No");
			return true;
		}

		Player player = (Player) sender;
		ItemStack item = player.getItemInHand();
		ItemMeta meta = item.getItemMeta();
		if(player.getItemInHand().getType() == Material.AIR)
			player.sendMessage(Utils.chat("&cYou are not holding an item."));
		else {
			meta.setDisplayName("");
			item.setItemMeta(meta);
			player.setItemInHand(item);
			player.sendMessage(Utils.chat("&aCleared the item's name!"));
		}
		return true;
	}
}
