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
import bridgewars.game.GameState;
import bridgewars.messages.Chat;

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
		
		if(!p.hasPermission("trusted.label")) {
			p.sendMessage(Chat.color("&cYou do not have permission to do this."));
			return true;
		}
		
		if(!p.isOp() && GameState.isState(GameState.ACTIVE)) {
			p.sendMessage(Chat.color("&cYou cannot do this while a game is active."));
			return true;
		}
		
		if(args.length == 0) {
			p.sendMessage(Chat.color("&cUsage: /label [offset|undo] <text>"));
			return true;
		}
		
		if(args[0].equals("undo")) {
			
			if(entries.size() <= 0) {
				p.sendMessage(Chat.color("&cThere are no labels to remove."));
				return true;
			}
			
			ArmorStand Label = null;
			if(args.length > 1) {
				for(ArmorStand entry : entries)
					if(entry.getCustomName().equals(args[1]))
						Label = entry;
			}
			else
				Label = entries.get(entries.size() - 1);
			
			entries.remove(Label);
			Label.remove();
			p.sendMessage("Removed label \"" + Label.getCustomName() + "\"");
			return true;
		}
		
		try {
			offset = Double.parseDouble(args[0]);
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
		loc.setY(loc.getY() + (offset) + 1);
		loc.setZ(loc.getBlockZ() + 0.5);
			
		ArmorStand Label = (ArmorStand) p.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		Label.setCustomName(Chat.color(text));
		Label.setCustomNameVisible(true);
		Label.setVisible(false);
		Label.setGravity(false);
		Label.setSmall(true);
		Label.setMarker(true);
		entries.add(Label);
			
		p.sendMessage(Chat.color("Placed label with text \"" + text + "\""));
		
		return true;
	}
}
