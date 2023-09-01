package bridgewars.menus;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import bridgewars.Main;

public class _MenuInput implements Listener {
	
	private GUI menu;
	
	public _MenuInput(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		menu = new GUI();
	}
	
	@EventHandler
	public void onDrag(InventoryDragEvent e) {
		if(e.getWhoClicked().getOpenInventory().getTitle() == "Hotbar Layout")
			for(int x : e.getRawSlots())
				if(x >= 45)
					e.setCancelled(true);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) throws IOException {
		
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getClickedInventory();
		String title = p.getOpenInventory().getTitle();
		
		if(title == menu.getMain().getName()
				|| title == menu.getKS().getName()
				|| title.contains("Map Rotation")
				|| title == menu.getClock().getName()
				|| title == menu.getHotbar().getName()
				|| title == menu.getSettings().getName()
				|| title == menu.getTeam().getName()) {
			
			e.setCancelled(true);
			
			ItemStack button = e.getCurrentItem();
			
			if(inv == null
			|| inv.getType() == InventoryType.PLAYER)
				return;
			
			if(title.contains("Map Rotation"))
				title = "Map Rotation";
			
			switch (title) {
			case "Bridgewars":
				MainMenu.sendInput(p, inv, button);
				break;
			case "Game Settings":
				Settings.sendInput(p, inv, button);
				break;
			case "Time Limit":
				TimeLimitEditor.sendInput(p, inv, button);
				break;
			case "Map Rotation":
				MapRotation.sendInput(p, inv, button);
				break;
			case "Hotbar Layout":
				HotbarEditor.sendInput(p, inv, button, e);
				break;
			case "Killstreak Bonuses":
				KillstreakEditor.sendInput(p, inv, button);
				break;
			case "Team Selector":
				TeamSelector.sendInput(p, inv, button);
				break;
			}
		}
	}
	
}
