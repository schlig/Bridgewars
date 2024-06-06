package bridgewars.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import bridgewars.Main;

public class Permissions {

	private static HashMap<UUID, PermissionAttachment> perms = new HashMap<>();
	private static Main plugin;
	
	public Permissions(Main plugin) {
		Permissions.plugin = plugin;
	}
	
	public static void setPermission(UUID uuid, String permission, boolean state, boolean save) {
		Player target = Bukkit.getPlayer(uuid);
		if(target != null && target.isOnline() && !target.isOp()) {
			PermissionAttachment attachment = perms.containsKey(uuid) ? perms.get(uuid) : target.addAttachment(plugin);
			attachment.setPermission(permission, state);
			perms.put(target.getUniqueId(), attachment);
		}
		if(save)
			save(uuid, permission, state);
	}
	
	public static ArrayList<String> getPermissions(UUID uuid) {
		ArrayList<String> permissions = new ArrayList<>();
		for(String permission : plugin.getConfig().getConfigurationSection("User." + uuid + ".permissions").getKeys(false))
			if(plugin.getConfig().getBoolean("User." + uuid + ".permissions." + permission))
				permissions.add(permission);
		return permissions;
	}
	
	private static void save(UUID uuid, String permission, boolean state) {
		plugin.getConfig().set("User." + uuid + ".permissions." + permission, state);
		try { plugin.getConfig().save("./plugins/bridgewars/config.yml"); } 
		catch (IOException e) {}
	}
	
	public static void flush(UUID uuid) {
		if(perms.containsKey(uuid))
			perms.remove(uuid);
	}
	
	public static void loadSettings(Player p) {
		if(p.isOp() || plugin.getConfig().getConfigurationSection("User." + p.getUniqueId()) == null)
			return;
		for(String permissionGroup : plugin.getConfig().getConfigurationSection("User." + p.getUniqueId() + ".permissions.").getKeys(false))
			if(plugin.getConfig().getConfigurationSection("User." + p.getUniqueId() + ".permissions." + permissionGroup + ".") != null)
				for(String permission : plugin.getConfig().getConfigurationSection("User." + p.getUniqueId() + ".permissions." + permissionGroup + ".").getKeys(false))
						setPermission(p.getUniqueId(), 
								permissionGroup + "." + permission, 
								plugin.getConfig().getBoolean("User." + p.getUniqueId() + ".permissions." + permissionGroup + "." + permission), 
								false);
			else
				setPermission(p.getUniqueId(), 
						permissionGroup, 
						plugin.getConfig().getBoolean("User." + p.getUniqueId() + ".permissions." + permissionGroup), 
						false);
	}
}
