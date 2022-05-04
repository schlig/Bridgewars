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

public class Rename implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Rename(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("rename").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("No");
			return true;
		}

		Player player = (Player) sender;
		if(player.getItemInHand().getType() == Material.AIR) {
			player.sendMessage(Utils.chat("&cYou are not holding an item"));
			return true;
		}
		if(args.length > 0) {
			ItemStack item = player.getItemInHand();
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("");
			for(int i = 0; i < args.length; i++) {
				meta.setDisplayName(Utils.chat(meta.getDisplayName() + args[i]));
				if(i < args.length - 1)
					meta.setDisplayName(Utils.chat(meta.getDisplayName() + " "));
			}
			player.sendMessage("Item renamed to " + meta.getDisplayName());
			item.setItemMeta(meta);
			player.setItemInHand(item);
		}
		else
			player.sendMessage(Utils.chat("&cUsage: /rename <name>"));
		return true;
	}
}
