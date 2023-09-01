package bridgewars.commands;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.utils.Message;

public class Label implements CommandExecutor {
	
	private ArrayList<ArmorStand> entries = new ArrayList<>();
	private double offset = 0;
	private int i = 1;
	
	public Label(Main plugin) {
		plugin.getCommand("label").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if(!p.isOp()) {
			p.sendMessage(Message.chat("&cYou do not have permission to do this."));
			return false;
		}
		
		if(args.length == 0)
			return false;
		
		if(args[0].equals("undo")) {
			if(entries.size() <= 0) {
				p.sendMessage(Message.chat("&cThere were no more labels to remove."));
				return false;
			}
			ArmorStand Label = entries.get(entries.size() - 1);
			entries.remove(Label);
			Label.remove();
			p.sendMessage("Removed label \"" + Label.getCustomName() + "\"");
			return false;
		}
		
		try {
			offset = (double) Integer.parseInt(args[0]);
			i = 1;
		} 
		catch (Exception e) { 
			offset = 0;
			i = 0; 
		}
		
		String text = "";
		while(i < args.length) {
			text = text + args[i] + " ";
			i++;
		}
		text = text.substring(0, text.length() - 1) + "&r";
			
		Location loc = p.getLocation();
		loc.setX(loc.getBlockX() + 0.5);
		loc.setY(loc.getY() + (offset * 0.3) + 1);
		loc.setZ(loc.getBlockZ() + 0.5);
			
		ArmorStand Label = (ArmorStand) p.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		Label.setCustomName(Message.chat(text));
		Label.setCustomNameVisible(true);
		Label.setVisible(false);
		Label.setGravity(false);
		Label.setSmall(true);
		entries.add(Label);
			
		p.sendMessage(Message.chat("Placed label with text \"" + text + "\""));
		
		return false;
	}
}
