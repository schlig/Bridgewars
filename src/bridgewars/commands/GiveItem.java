package bridgewars.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.items.CustomItems;
import bridgewars.utils.Message;

public class GiveItem implements CommandExecutor {
	
	private CustomItems item;
	
	public GiveItem(Main plugin) {
		plugin.getCommand("giveitem").setExecutor(this);
		item = new CustomItems();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if(p.isOp()) {
			
			if(args.length == 0)
				return false;
			
			int amount;
			try {
				amount = Integer.parseInt(args[1]);
			}
			catch (Exception e) { amount = 1; }
			
			p.getInventory().addItem(item.getItem(p, args[0], amount, false));
			p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.6F, 1.8F);
		}
		
		else
			p.sendMessage(Message.chat("&cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error."));
		
		return false;
	}
}
