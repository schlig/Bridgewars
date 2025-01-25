package bridgewars.effects;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.messages.Chat;
import bridgewars.utils.Disguise;
import bridgewars.utils.ItemManager;

public class Cloak extends BukkitRunnable {
	
	public static ArrayList<UUID> cloakedPlayers = new ArrayList<>();
	
	private Player p;
	private ItemStack[] prevArmor;
	private int d;    //duration
	
	public Cloak(Player p, UUID t, int d) {
		this.p = p;
		this.d = d;
		this.prevArmor = p.getInventory().getArmorContents();
		if(Bukkit.getPlayer(t) != null)
			setArmor(Bukkit.getPlayer(t));
		Disguise.setDisguise(p, t);
		cloakedPlayers.add(p.getUniqueId());
		p.sendMessage(Chat.color("&lYou have disguised yourself as " + Bukkit.getPlayer(t).getDisplayName() + "&r&l!"));
	}
	
	@Override
	public void run() {
		if(d == 0 || !cloakedPlayers.contains(p.getUniqueId())) {
			if(cloakedPlayers.contains(p.getUniqueId()))
				cloakedPlayers.remove(p.getUniqueId());
			Disguise.setDisguise(p, p.getUniqueId());
			setArmor(p, prevArmor);
			p.sendMessage(Chat.color("&c&lYou are no longer disguised!"));
			this.cancel();
		}
		d--;
	}
	
	private void setArmor(Player u) {
		p.getInventory().setHelmet(ItemManager.getItem("BasicHelmet").createItem(u));
		p.getInventory().setChestplate(ItemManager.getItem("BasicChestplate").createItem(u));
		p.getInventory().setLeggings(ItemManager.getItem("BasicLeggings").createItem(u));
		p.getInventory().setBoots(ItemManager.getItem("BasicBoots").createItem(u));
	}
	
	private void setArmor(Player u, ItemStack[] armor) {
		u.getInventory().setArmorContents(armor);
	}
	
	public static void remove(Player p) {
		if(cloakedPlayers.contains(p.getUniqueId()))
			cloakedPlayers.remove(p.getUniqueId());
	}
}
