package bridgewars.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import bridgewars.effects.ParticleTrail;
import bridgewars.messages.Chat;
import bridgewars.settings.GameSettings;
import bridgewars.settings.enums.IndestructibleMap;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class World {
	
	private final static String mapPath = "./plugins/bridgewars/maps/";
	private final static String objectPath = "./plugins/bridgewars/objects/";
	
	private static int x = 0, y = 0, z = 0;
	private static int buildLimitRadius = 22, buildLimitHeight = 24;
	private static int timerZoneRadius = buildLimitRadius + 3, timerZoneHeight = buildLimitHeight + 6;
	
	private static final double worldSpawnX = 1062.5;
	private static final double worldSpawnY = 52.5;
	private static final double worldSpawnZ = 88.5;
	private static final float worldSpawnYaw = -90;
	private static final float worldSpawnPitch = 10;
	
	private static final int maximumSpawnAttempts = 500;

	public static ArrayList<Block> indestructibleBlockList = new ArrayList<>();
	
	public static void fill(int x, int y, int z, int x2, int y2, int z2, Material material) {
		fill(x, y, z, x2, y2, z2, material, (byte)0);
	}
	
	@SuppressWarnings("deprecation")
	public static void fill(int x, int y, int z, int x2, int y2, int z2, Material material, byte data) {
		for(int X = x; X <= x2; X++)
			for(int Y = y; Y <= y2; Y++)
				for(int Z = z; Z <= z2; Z++) {
					Bukkit.getWorld("world").getBlockAt(X, Y, Z).setType(material);
					Bukkit.getWorld("world").getBlockAt(X, Y, Z).setData(data);
				}
	}
	
	public static Boolean blockIsIndestructible(Block block) {
		if(indestructibleBlockList.contains(block) && IndestructibleMap.getState().isEnabled())
			return true;
		else return false;
	}

	public static boolean inGameArea(Location loc){
		buildLimitRadius = GameSettings.getGameRadius();
		buildLimitHeight = GameSettings.getGameHeight();
		return !Utils.isOutOfBounds(loc, buildLimitRadius, buildLimitHeight, buildLimitRadius);
	}
	
	public static boolean inTimerZone(Location loc) {
		return !Utils.isOutOfBounds(loc, timerZoneRadius, timerZoneHeight, timerZoneRadius);
	}
	
	public static Location getSpawn() {
		return new Location(Bukkit.getWorld("world"), worldSpawnX, worldSpawnY, worldSpawnZ, worldSpawnYaw, worldSpawnPitch);
	}
	
	public static void deleteLabel(Location loc, double radius) {
		org.bukkit.World world = Bukkit.getWorld("world");
		for(Entity entity : world.getNearbyEntities(loc, radius, radius, radius))
			if(entity instanceof ArmorStand)
				entity.remove();
	}
	
	public static void placeLabel(String text, double x, double y, double z) {
		org.bukkit.World world = Bukkit.getWorld("world");
		Location loc = new Location(world, x, y, z);
		ArmorStand label = (ArmorStand) Bukkit.getWorld("world").spawnEntity(loc, EntityType.ARMOR_STAND);
		label.setCustomName(Chat.color(text));
		label.setCustomNameVisible(true);
		label.setVisible(false);
		label.setGravity(false);
		label.setSmall(true);
		label.setMarker(true);
		label.setCanPickupItems(false);
	}
	
	@SuppressWarnings("deprecation")
	public static void loadObject(String object, int xtrans, int ytrans, int ztrans) {
		File file = new File(objectPath + object + ".bwobj");
		
		try {
			if(!(file.exists())) {
				Bukkit.broadcastMessage("L, didn't work dipshit");
				return;
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			int x, y, z, t;
			while((line = br.readLine()) != null) {
				String[] block = line.split("[,]");
				x = Integer.parseInt(block[1].replaceAll(",", ""));
				y = Integer.parseInt(block[2].replaceAll(",", ""));
				z = Integer.parseInt(block[3].replaceAll(",", ""));
				t = Integer.parseInt(block[4].replaceAll(",", ""));
				Block build = Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x + xtrans, y + ytrans, z + ztrans));
				build.setType(Material.getMaterial(block[0].replaceAll(",", "")));
				build.setData((byte) t);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void clearMap() {
		//map bounds
		//-28 0 -28
		//28 41 28
		fill(-buildLimitRadius, 0, -buildLimitRadius, buildLimitRadius, buildLimitHeight, buildLimitRadius, Material.AIR);
		
		for(Entity e : Bukkit.getWorld("world").getEntities())
			if(e instanceof Item)
				e.remove();
	}

	@SuppressWarnings("deprecation")
	public static boolean loadMap(String map, Player p, boolean override) {
		File file;
		int i;
		if(map == null) {
			file = new File(mapPath);
			ArrayList<String> mapList = new ArrayList<>(Arrays.asList(file.list()));
			ArrayList<String> blacklist = new ArrayList<>();
			try {
				for(String name : mapList) {
					RandomAccessFile r = new RandomAccessFile(mapPath + name, "rw");
					r.seek(0);
					String test = r.readLine();
					if(test.contains("1"))
						blacklist.add(name);
					r.close();
				}
				for(String name : blacklist)
					mapList.remove(name);
				
			} catch (IOException e) { 
				e.printStackTrace();
				p.sendMessage(Chat.color("&cFailed to load map \"&6" + map + "&c\""));
			}
			if(mapList.size() == 0) {
				Bukkit.broadcastMessage(Chat.color("&cThere aren't any maps in rotation!"));
				return false;
			}
			i = Utils.rand(mapList.size());
			map = mapList.get(i);
			file = new File(mapPath + map);
		}
		else {
			map = map + ".map";
			file = new File(mapPath + map);
			try {
				map = file.getCanonicalFile().getName();
			} catch (IOException e) {
				p.sendMessage(Chat.color("&cFailed to load map \"&6" + map + "&c\""));
				return false;
			}
		}
		
		try {
			if(!(file.exists())) {
				p.sendMessage(Chat.color("&cFailed to load map \"&6" + map + "&c\" &7(try checking case sensitivity?)"));
				return false;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			int x, y, z, t;
			br.readLine();
			indestructibleBlockList.clear();
			while((line = br.readLine()) != null) {
				String[] blockData = line.split("[,]");
				Material material = Material.getMaterial(blockData[0].replaceAll(",", ""));
				x = Integer.parseInt(blockData[1].replaceAll(",", ""));
				y = Integer.parseInt(blockData[2].replaceAll(",", ""));
				z = Integer.parseInt(blockData[3].replaceAll(",", ""));
				t = Integer.parseInt(blockData[4].replaceAll(",", ""));
				Block block = Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, y, z));
				if(IndestructibleMap.getState().isEnabled())
					indestructibleBlockList.add(block);
				block.setType(material);
				block.setData((byte) t);
			}
			br.close();
		} catch (IOException e) {
			
		}
		map = map.substring(0, map.length() - 4);
		for(Player player : Bukkit.getOnlinePlayers())
			player.sendMessage(Chat.color("&lMap: &6&l" + map));
		return true;
	}
	
	public static void attemptItemSpawn(final int originX, final int originY, final int originZ, final int radius, boolean showParticles) {
		
		boolean validLocation = false;
		
		int attempts = 0;
		
		while(!validLocation) { //searches an area at specified coordinates for a block with air above it, anywhere within the radius (r)
			x = Utils.rand(radius * 2 + 1) - radius + originX;
			z = Utils.rand(radius * 2 + 1) - radius + originZ;
			
			for(y = originY; y < originY + 24; y++)
				if(Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, y, z)).getType() != Material.AIR
				&& Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, y + 1, z)).getType() == Material.AIR) { //successfully found a location
					validLocation = true;
					break;
				}
			attempts++;
			if(attempts == maximumSpawnAttempts) //the check for a valid location is only ran a certain number of times to prevent excessive resource use
				break;
		}
		
		if(validLocation) {
			Item item = null; 
			int r = -255, g = -255, b = -255; 
			ICustomItem spawnableItem = ItemManager.getRandomItem();
			switch(spawnableItem.getRarity()){
				default:
				case WHITE:
					r = 255;
					g = 255;
					b = 255;
					break;
					
				case GREEN:
					g = 255;
					break;
					
				case RED:
					r = 255;
					g = 0;
					b = 0;
					break;
					
				case BLUE:
					r = -255;
					g = -255;
					b = 255;
			}
			item = Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"), x + 0.5, y + 1, z + 0.5), spawnableItem.createItem(null));
			item.setVelocity(item.getVelocity().setX(0).setY(0).setZ(0));
			if(showParticles) //if particles are turned on, it will create a colored particle above the item to easily be seen from a distance
				new ParticleTrail((Entity) item, EnumParticle.REDSTONE, 
						0, 1, 0, 
						r, g, b, 
						1F, 0, 6000, false).runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 0L, 1L);
		}
	}
}
