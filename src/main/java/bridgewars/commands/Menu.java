package bridgewars.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bridgewars.Main;
import bridgewars.game.CustomScoreboard;
import bridgewars.game.GameState;
import bridgewars.menus.GUI;
import bridgewars.utils.Message;

public class Menu implements CommandExecutor {
	
	private GUI menu;
	private CustomScoreboard cs;
	
	public Menu(Main plugin) {
		plugin.getCommand("menu").setExecutor(this);
		menu = new GUI();
		cs = new CustomScoreboard();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		if(GameState.isState(GameState.ACTIVE)
		&& p.getGameMode() != GameMode.CREATIVE
		&& cs.hasTeam(p)
		&& !p.isOp()) {
			p.sendMessage(Message.chat("&cYou can't open the menu while in a game!"));
			return true;
		}
		else
			p.openInventory(menu.getMain());
		
		return false;
	}
}
