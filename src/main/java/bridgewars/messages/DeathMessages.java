package bridgewars.messages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import bridgewars.Main;
import bridgewars.effects.PlotArmor;
import bridgewars.game.CombatTagging;
import bridgewars.game.GameState;
import bridgewars.settings.PlayerSettings;
import bridgewars.utils.ItemManager;
import bridgewars.utils.Utils;

public class DeathMessages implements Listener {

	private CombatTagging ct;
//	private HashMap<String, String> messages = new HashMap<>();
	
	public DeathMessages(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		ct = new CombatTagging();
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		
		Player victim = (Player) e.getEntity();
		
		if(PlotArmor.armoredPlayers.contains(victim))
			return;

		if(victim.getKiller() instanceof Player)
			if(victim.getKiller() == victim) {
				sendDeathMessage(victim, victim.getDisplayName() + PlayerSettings.getSetting(victim, "KillMessageSuicide"));
				return;
			}
		
		switch(victim.getLastDamageCause().getCause()) {
		case VOID:
			if(victim.getKiller() instanceof Player && ct.getAttacker(victim) != null) {
				Player killer = (Player) victim.getKiller();
				if(ct.getMurderWeapon(victim) != null) {
					if(Utils.getID(ct.getMurderWeapon(victim)).equals(Utils.getID(ItemManager.getItem("HomeRunBat").createItem(null))))
						sendDeathMessage(victim, victim.getDisplayName() + " was struck out by " + killer.getDisplayName());
					else
						sendDeathMessage(victim, victim.getDisplayName() + getMessage(victim, "KillMessageVoid") + killer.getDisplayName());
					ct.setMurderWeapon(victim, null);
					ct.setAttacker(victim, null);
				}
			}
			else
				sendDeathMessage(victim, victim.getDisplayName() + " fell into the void");
			return;
			
		case ENTITY_ATTACK:
			if(ct.getAttacker(victim) != null) {
				Player killer = (Player) victim.getKiller();
				sendDeathMessage(victim, victim.getDisplayName() + getMessage(killer, "KillMessageMelee") + killer.getDisplayName());
			}
			return;
			
		case ENTITY_EXPLOSION:
			if(victim.getKiller() instanceof Player) {
				Player killer = (Player) victim.getKiller();
				sendDeathMessage(victim, victim.getDisplayName() + " was fireballed by " + killer.getDisplayName());
			}
			return;

		case PROJECTILE:
			if(victim.getKiller() instanceof Player) {
				Player killer = (Player) victim.getKiller();
				sendDeathMessage(victim, victim.getDisplayName() + getMessage(victim, "KillMessageBow") + killer.getDisplayName());
			}
			return;
			
		default:
		}
		
		sendDeathMessage(victim, e.getDeathMessage());
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			Player victim = (Player) e.getEntity();
			ct.setDamageCause(victim, e.getCause());
			if(e.getDamager() instanceof Player) {
				Player attacker = (Player) e.getDamager();
				ct.setAttacker(victim, attacker);
				ct.setMurderWeapon(victim, attacker.getItemInHand());
			}
		}
	}
	
	private void sendDeathMessage(Player p, String message) {
		if(GameState.isState(GameState.ACTIVE)) Bukkit.getServer().broadcastMessage(message);
		else p.sendMessage(message);
	}
	
//	private void buildCustomMessages() {
//		messages.put("default", " was killed by ");
//		messages.put("Schlog", " was slain by ");
//		messages.put("Thuggishprune", " was poop socked by ");
//		messages.put("wBeard", " was promptly dealt with after attempting to lick the beard of ");
//		messages.put("Wabadaba", " was too slow for ");
//		messages.put("onjit", " was snagged by ");
//		messages.put("TalonBX", " got bonked by ");
//		messages.put("Northwind5545", " was sanded by ");
//		messages.put("nicktoot", " was sad roomed by ");
//		messages.put("EmergenCheese", " blundered horribly against ");
//		messages.put("eggwave", " came up with a bad name for ");
//		messages.put("MrFrexxy", " wasn't as cute as ");
//		messages.put("Freejourner", " had inferior fishing skills to ");
//		messages.put("MookW", " wasn't gas enough for ");
//		messages.put("towwl", " was somehow killed by ");
//		messages.put("Physiological", " was violently murdered by ");
//	}
	
	private String getMessage(Player p, String setting) {
		return PlayerSettings.getSetting(p, setting);
//		return messages.containsKey(name) ? messages.get(name) : messages.get("default");
	}
}
