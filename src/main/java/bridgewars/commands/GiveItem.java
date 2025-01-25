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

			for(int i = 0; i < allItems.size(); i++){
				if(args[0].equals(allItems.get(i))){
					ItemStack itemStack = ItemManager.getItem(i).createItem(p);
					itemStack.setAmount(amount);
					p.getInventory().addItem(itemStack);
					p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 0.6F, 1.8F);
					return false;
				}
			}
			p.sendMessage(Chat.color("&cThis item does not exist!"));
		}
		
		else
			p.sendMessage(Chat.color("&cYou do not have permission to do this."));
		
		return false;
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
}