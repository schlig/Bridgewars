package bridgewars.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import bridgewars.Main;
import bridgewars.messages.Chat;
import bridgewars.utils.ICustomItem.Rarity;
import bridgewars.utils.ItemManager;

public class GiveItem implements CommandExecutor, TabCompleter {

	private final ArrayList<String> allItems;
	
	public GiveItem(Main plugin) {
		plugin.getCommand("giveitem").setExecutor(this);
		plugin.getCommand("giveitem").setTabCompleter(this);
		allItems = ItemManager.getItemNames();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player) || !sender.isOp() || args.length == 0) {
			if(!sender.isOp())
				sender.sendMessage(Chat.color("&cYou do not have permission to do this."));
			return true;
		}
		
		Player p = (Player) sender;
			
		String item = args[0];
		int amount;
		try { amount = Integer.parseInt(args[1]); }
		catch (Exception e) { amount = 1; }
		Rarity rarity;
		try { rarity = getRarity(args[2]); }
		catch (Exception e) { rarity = null; }
		
		ItemStack itemstack = null;
		
		for(String entry : allItems) {
			if(entry.equalsIgnoreCase(item)) {
				itemstack = ItemManager.getItem(entry).createItem(p);
				break;
			}
			else if(item.equals("random")) {
				itemstack = ItemManager.getRandomItem(rarity).createItem(null);
				break;
			}
		}
		if(itemstack == null) {
			p.sendMessage(Chat.color("&cThis item does not exist."));
			return true;
		}
		
		itemstack.setAmount(amount);
		p.getInventory().addItem(itemstack);
		p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.6F, 1.8F);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length == 1){
			final List<String> completionList = new ArrayList<>();
			final List<String> completeArgument = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], allItems, completionList);

			for(String index : completionList)
				if(!completeArgument.contains(index))
					completeArgument.add(index);
			
			Collections.sort(completeArgument);
			return completeArgument;
		}
		return null;
	}
	
	private Rarity getRarity(String rarity) {
		switch(rarity) {
		default: return null;
		case "none": return Rarity.NONE;
		case "white": return Rarity.WHITE;
		case "green": return Rarity.GREEN;
		case "red": return Rarity.RED;
		case "blue": return Rarity.BLUE;
		}
	}
}