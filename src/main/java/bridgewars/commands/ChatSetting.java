package bridgewars.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.utils.Message;

public class ChatSetting implements CommandExecutor {
	
	public static HashMap<Player, Boolean> allChat = new HashMap<>();
	
	public ChatSetting(Main plugin) {
		Bukkit.getPluginCommand("chat").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player))
			return true;
		
		Player p = (Player) sender;
		
		if(args.length < 1) {
			if(label.equals("tc"))
				args = new String[] { "team" };
			else if(label.equals("ac"))
				args = new String[] { "all" };
			else {
				p.sendMessage(Message.chat("&cUsage: /chat <all|team>, /tc | /ac"));
				return false;
			}
		}
		
		if(args[0].equals("all")) {
			p.sendMessage(Message.chat("&6You are now in &cAll Chat"));
			allChat.put(p, true);
		}
		else if(args[0].equals("team")) {
			p.sendMessage(Message.chat("&6You are now in &cTeam Chat"));
			allChat.put(p, false);
		}
		else {
			p.sendMessage(Message.chat("&cUsage: /chat <all|team>, /tc | /ac"));
			return false;
		}
		
		return true;
	}
}
