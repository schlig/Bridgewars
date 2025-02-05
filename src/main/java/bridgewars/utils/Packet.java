package bridgewars.utils;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import bridgewars.settings.enums.HidePlayers;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class Packet {
	
	public static void sendParticle(EnumParticle effect, float x, float y, float z) {
		sendParticle(effect, x, y, z, 0, 0, 0, 0, 1);
	}
	
	public static void sendParticle(EnumParticle effect, float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int amount) {
		//added this so not specifying a target player will simply send the packets to everyone
		
		for(Player p : Bukkit.getOnlinePlayers())
			sendParticle(effect, x, y, z, xOffset, yOffset, zOffset, speed, amount, p);
	}
	
	public static void sendParticle(EnumParticle effect, float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int amount, Player target) {
		//just spawns a client sided particle for everyone
		//this is a lot more useful combined with the effects.ParticleTrail class
		
		//to use it: effect will create a particle effect of your choice, e.g. explosions, potion effects, blocks breaking, slimes
		//x, y, z are the coordinates it will spawn at
		//xoffset, yoffset, zoffset will essentially create a huge box it could spawn within instead
		//speed is how long the particles would last
		//amount is the amount of particles will spawn within the box
		//target is who the particles are shown to
		
		//but a colored particle is different: EnumParticle.REDSTONE will create a redstone dust particle
		//xoffset, yoffset, and zoffset don't make a box anymore but instead are converted into RGB values
		//speed must be set to 1 for this, and amount must be set to 0
		//it will be red by default so if you want the red value to be 0, make sure it's -255 instead
		
		//Utils.raycast is very helpful for drawing shapes using this function
		
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(effect, true, x, y, z, xOffset / 255, yOffset / 255, zOffset / 255, speed, amount);
		CraftPlayer player = (CraftPlayer) target;
		PlayerConnection connection = player.getHandle().playerConnection;
		connection.sendPacket(packet);
	}
	
	public static void playSound(String instrument, int x, int y, int z, float volume, float pitch) {
		for(Player players : Bukkit.getOnlinePlayers())
			playSound(instrument, x, y, z, volume, pitch, players);
	}
	
	public static void playSound(String instrument, int x, int y, int z, float volume, float pitch, Player target) {
		CraftPlayer player = (CraftPlayer) target;
		PlayerConnection connection = player.getHandle().playerConnection;
		PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect(instrument, x, y, z, volume, pitch);
		connection.sendPacket(packet);
	}

	public static void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		for(Player players : Bukkit.getOnlinePlayers())
			sendTitle(title, subtitle, fadeIn, stay, fadeOut, players);
	}
	
	public static void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut, Player player) {
		//sends a title to specified player
		//functionally identical to the /title command
		//bukkit does not have a feature for this on 1.8.9 for some reason
		
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = ChatSerializer.a("{'text': '" + title + "'}");
        IChatBaseComponent subtitleJSON = ChatSerializer.a("{'text': '" + subtitle + "'}");
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleJSON);
        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);
    }
	
	public static void sendBlockDamageAnimation(int id, Location loc, byte stage) {
		//triggers function below but for all online players
		for(Player p : Bukkit.getOnlinePlayers())
			sendBlockDamageAnimation(id, loc, stage, p);
	}
	
	public static void sendBlockDamageAnimation(int id, Location loc, byte stage, Player target) {
		//causes a block at specified location to appear damaged to the target as if a player is mining it
		//each damage animation has its own ID, so if you input an id of 10 multiple times it will update the animation
		//if you were to apply multiple of these to the same block with different ids, it will cause a bunch of them to spawn overlapping
		//stage ranges from 0-10, 0 for least damaged and 10 for most
		//setting stage to any other number will delete the cracks
		
		CraftPlayer player = (CraftPlayer) target;
		BlockPosition block = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		PlayerConnection connection = player.getHandle().playerConnection;
		
		PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(id, block, stage);
		connection.sendPacket(packet);
	}
	
	public static void setArmor(Player target, Player armorOwner, ItemStack armor, int slot) {
		//this ones fucking confusing, it essentially makes the armor owner's color appear as whatever fakecolor is to the target and only the target
		//this is mostly for hideplayers and colorblind support
		//slot 0: held item, slot 1: boots, slot 2: leggings, slot 3: chestplate, slot 4: helmet
		
		CraftPlayer ao = (CraftPlayer) armorOwner;
		CraftPlayer t = (CraftPlayer) target;
		PlayerConnection connection = t.getHandle().playerConnection;
		PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(ao.getEntityId(), slot, CraftItemStack.asNMSCopy(armor));
		connection.sendPacket(packet);
	}
	
	public static void setDisguise(Player user, UUID targetUUID) {
		Player target = Bukkit.getPlayer(targetUUID);
		CraftPlayer player = (CraftPlayer) user;
		GameProfile profile = player.getHandle().getProfile();
		
		for(Player players : Bukkit.getOnlinePlayers())
			players.hidePlayer(user);

		setSkin(profile, player, targetUUID);
		setName(profile, player, Utils.getName(targetUUID));
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			players.showPlayer(user);
			
			if(HidePlayers.getState().isEnabled() || players == user)
				continue;

			setArmor(players, user, ItemManager.getItem("BasicBoots").createItem(target), 1);
			setArmor(players, user, ItemManager.getItem("BasicLeggings").createItem(target), 2);
			setArmor(players, user, ItemManager.getItem("BasicChestplate").createItem(target), 3);
			setArmor(players, user, ItemManager.getItem("BasicHelmet").createItem(target), 4);
		}
	}
	
	private static void setName(GameProfile profile, Player p, String name) {
		try {
			Field field = profile.getClass().getDeclaredField("name");
			field.setAccessible(true);
			field.set(profile, name);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	private static void setSkin(GameProfile profile, CraftPlayer p, UUID target) {
		String[] propertyData = Utils.getSkin(target);
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", new Property("textures", propertyData[0], propertyData[1]));
	}
}
