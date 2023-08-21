package bridgewars.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.items.Items;
import bridgewars.utils.Utils;

public class GiveItem implements CommandExecutor {
	
	private Items item;
	
	public GiveItem(Main plugin) {
		plugin.getCommand("giveitem").setExecutor(this);
		item = new Items();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if(p.isOp()) {
			int amount;
			try {
				amount = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException e) {
				amount = 1;
			}
			catch (ArrayIndexOutOfBoundsException e) {
				amount = 1;
			}
			
			switch(args[0]) {
			case "bridgeegg": 
			case "be":
				p.getInventory().addItem(item.getBridgeEgg(amount, false));
				p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.6F, 1.8F);
				break;
			case "homerunbat":
			case "hrb":
				p.getInventory().addItem(item.getHomeRunBat(amount, false));
				p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.6F, 1.8F);
				break;
			case "lifeforcepotion":
			case "lp":
				p.getInventory().addItem(item.getLifeforcePotion(amount, false));
				p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.6F, 1.8F);
				break;
			case "blackhole":
			case "bh":
				p.getInventory().addItem(item.getBlackHole(amount, false));
				p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.6F, 1.8F);
				break;
			case "portabledoinkhut":
			case "pdh":
				p.getInventory().addItem(item.getPortableDoinkHut(amount, false));
				p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.6F, 1.8F);
				break;
			case "fireball":
			case "fb":
				p.getInventory().addItem(item.getFireball(amount, false));
				p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.6F, 1.8F);
				break;
			}
		}
		
		else
			p.sendMessage(Utils.chat("&cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error."));
		
		return false;
	}
}
