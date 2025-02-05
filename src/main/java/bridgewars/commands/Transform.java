package bridgewars.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.messages.Chat;
import bridgewars.utils.Packet;
import bridgewars.utils.Utils;

public class Transform implements CommandExecutor {
	
	public Transform (Main plugin) {
		plugin.getCommand("transform").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		if(!p.hasPermission("trusted.disguise")) {
			p.sendMessage(Chat.color("&cYou do not have permission to do this."));
			return true;
		}

		ArrayList<Player> players = new ArrayList<>();
		for(Player player : Bukkit.getOnlinePlayers())
			players.add(player);
		
		players.remove(p);
		
		if(args.length == 0)
			Packet.setDisguise(p, p.getUniqueId());
		else
			Packet.setDisguise(p, Utils.getUUID(args[0]));
		
		return true;
	}
}
