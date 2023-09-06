package bridgewars.effects;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import bridgewars.utils.Disguise;
import bridgewars.utils.Message;

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
			setArmor(p, getArmor(Bukkit.getPlayer(t)));
		Disguise.setDisguise(p, t);
		cloakedPlayers.add(p.getUniqueId());
		p.sendMessage(Message.chat("&lYou have disguised yourself as " + Bukkit.getPlayer(t).getDisplayName() + "&r&l!"));
	}
	
	@Override
	public void run() {
		if(d == 0 || !cloakedPlayers.contains(p.getUniqueId())) {
			if(cloakedPlayers.contains(p.getUniqueId()))
				cloakedPlayers.remove(p.getUniqueId());
			Disguise.setDisguise(p, p.getUniqueId());
			setArmor(p, prevArmor);
			p.sendMessage(Message.chat("&c&lYou are no longer disguised!"));
			this.cancel();
		}
		d--;
	}
	
	private void setArmor(Player u, ItemStack[] armor) {
		u.getInventory().setArmorContents(armor);
	}
	
	private ItemStack[] getArmor(Player t) {
		return t.getInventory().getArmorContents();
	}
}
