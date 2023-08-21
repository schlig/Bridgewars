package bridgewars.messages;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import bridgewars.Main;
import bridgewars.game.CombatTagging;
import bridgewars.game.GameState;

public class DeathMessages implements Listener {

	private CombatTagging ct;
	private HashMap<String, String> messages = new HashMap<>();
	
	public DeathMessages(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		ct = new CombatTagging();
		buildCustomMessages();
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		
		Player p = (Player) e.getEntity();

		if(p.getKiller() instanceof Player)
			if(p.getKiller() == p) {
				sendDeathMessage(p, p.getDisplayName() + " committed suicide");
				return;
			}
		
		switch(p.getLastDamageCause().getCause()) {
		case VOID:
			if(p.getKiller() instanceof Player && ct.getAttacker(p) != null) {
				Player k = (Player) p.getKiller();
				if(ct.getMurderWeapon(p) != null) {
					if(ct.getMurderWeapon(p) == Material.WOOD_SWORD)
						sendDeathMessage(p, p.getDisplayName() + " was struck out by " + k.getDisplayName());
					else
						sendDeathMessage(p, p.getDisplayName() + " was thrown into the void by " + k.getDisplayName());
					ct.setMurderWeapon(p, null);
					ct.setAttacker(p, null);
				}
			}
			else
				sendDeathMessage(p, p.getDisplayName() + " fell into the void");
			return;
			
		case ENTITY_ATTACK:
			if(ct.getAttacker(p) != null) {
				Player k = (Player) p.getKiller();
				sendDeathMessage(p, p.getDisplayName() + getCustomMessage(k.getName()) + k.getDisplayName());
			}
			return;
			
		case ENTITY_EXPLOSION:
			if(p.getKiller() instanceof Player) {
				Player k = (Player) p.getKiller();
				sendDeathMessage(p, p.getDisplayName() + " was fireballed by " + k.getDisplayName());
			}
			return;

		case PROJECTILE:
			if(p.getKiller() instanceof Player) {
				Player k = (Player) p.getKiller();
				sendDeathMessage(p, p.getDisplayName() + " was sniped by " + k.getDisplayName());
			}
			return;
			
		default:
		}
		
		sendDeathMessage(p, e.getDeathMessage());
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			ct.setDamageCause(p, e.getCause());
			if(e.getDamager() instanceof Player) {
				Player k = (Player) e.getDamager();
				ct.setAttacker(p, k);
				ct.setMurderWeapon(p, k.getItemInHand());
			}
		}
	}
	
	private void sendDeathMessage(Player p, String message) {
		if(GameState.isState(GameState.ACTIVE))
			Bukkit.getServer().broadcastMessage(message);
		else
			p.sendMessage(message);
	}
	
	private void buildCustomMessages() {
		messages.put("default", " was killed by ");
		messages.put("Schlog", " was slain by ");
		messages.put("Thuggishprune", " was poop socked by ");
		messages.put("wBeard", " was promptly dealt with after attempting to lick the beard of ");
		messages.put("Wabadaba", " wasn't sweaty enough for ");
		messages.put("onjit", " was snagged by ");
		messages.put("TalonBX", " got EEEOOOWWW'd by ");
		messages.put("Northwind5545", " was sanded by ");
		messages.put("nicktoot", " was sad roomed by ");
		messages.put("EmergenCheese", " blundered horribly against ");
		messages.put("eggwave", " came up with a bad name for ");
		messages.put("MrFrexxy", " wasn't as cute as ");
		messages.put("Freejourner", " had inferior fishing skills to ");
		messages.put("MookW", " wasn't gas enough for ");
	}
	
	private String getCustomMessage(String name) {
		if(messages.containsKey(name))
			return messages.get(name);
		else
			return messages.get("default");
	}
}
