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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import bridgewars.effects.ParticleTrail;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class World {
	
	private final static String mapPath = "./plugins/bridgewars/maps/";
	private final static String objectPath = "/plugins/bridgewars/objects";
	
	private static int x = 0, y = 0, z = 0;
	private static final int gameXSize = 22, gameYSize = 24, gameZSize = 22;
	
	private static final double worldSpawnX = 1062.5;
	private static final double worldSpawnY = 52;
	private static final double worldSpawnZ = 88.5;
	private static final float worldSpawnYaw = -90;
	private static final float worldSpawnPitch = 10;
	
	private static final  int maximumSpawnAttempts = 500;
	
	public static void fill(Location origin, int x, int y, int z, int x2, int y2, int z2, Material material) {
		for(x+=0; x < x2; x++)
			for(y+=0; y < y2; y++)
				for(z+=0; z < z2; z++)
					Bukkit.getWorld("world").getBlockAt(x, y, z).setType(material);
	}

	public static boolean inGameArea(Location loc){
		return !Utils.isOutOfBounds(loc, gameXSize, gameYSize, gameZSize);
	}
	
	public static Location getSpawn() {
		return new Location(Bukkit.getWorld("world"), worldSpawnX, worldSpawnY, worldSpawnZ, worldSpawnYaw, worldSpawnPitch);
	}
	
	@SuppressWarnings("deprecation")
	public static void removeObject(String object, int xtrans, int ytrans, int ztrans) {
		File file = new File(objectPath + object + ".bwobj");
		
		try {
			if(!(file.exists()))
				return;
			
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			int x, y, z, t;
			br.readLine();
			while((line = br.readLine()) != null) {
				String[] block = line.split("[,]");
				x = Integer.parseInt(block[1].replaceAll(",", ""));
				y = Integer.parseInt(block[2].replaceAll(",", ""));
				z = Integer.parseInt(block[3].replaceAll(",", ""));
				t = Integer.parseInt(block[4].replaceAll(",", ""));
				Block build = Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x + xtrans, y + ytrans, z + ztrans));
				build.setType(Material.AIR);
				build.setData((byte) t);
			}
			br.close();
		} catch (IOException e) {
			
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void loadObject(String object, int xtrans, int ytrans, int ztrans) {
		File file = new File(objectPath + object + ".bwobj");
		
		try {
			if(!(file.exists()))
				return;
			
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			int x, y, z, t;
			br.readLine();
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
			
		}
	}
	
	public static void clearMap() {
		//map bounds
		//-28 0 -28
		//28 41 28
		for(int x = gameXSize * -1; x <= gameXSize; x++)
			for(int z = gameZSize * -1; z <= gameZSize; z++)
				for(int y = 0; y <= gameYSize; y++)
					Bukkit.getWorld("world").getBlockAt(x, y, z).setType(Material.AIR);
		
		for(Entity e : Bukkit.getWorld("world").getEntities())
			if(e instanceof Item)
				e.teleport(new Location(Bukkit.getWorld("world"), 0, -100, 0));
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
				p.sendMessage(Message.chat("&cFailed to load map \"&6" + map + "&c\""));
			}
			if(mapList.size() == 0) {
				Bukkit.broadcastMessage(Message.chat("&cThere aren't any maps in rotation!"));
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
				p.sendMessage(Message.chat("&cFailed to load map \"&6" + map + "&c\""));
				return false;
			}
		}
		
		try {
			if(!(file.exists())) {
				p.sendMessage(Message.chat("&cFailed to load map \"&6" + map + "&c\" &7(try checking case sensitivity?)"));
				return false;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			int x, y, z, t;
			br.readLine();
			while((line = br.readLine()) != null) {
				String[] block = line.split("[,]");
				x = Integer.parseInt(block[1].replaceAll(",", ""));
				y = Integer.parseInt(block[2].replaceAll(",", ""));
				z = Integer.parseInt(block[3].replaceAll(",", ""));
				t = Integer.parseInt(block[4].replaceAll(",", ""));
				Block build = Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, y, z));
				build.setType(Material.getMaterial(block[0].replaceAll(",", "")));
				build.setData((byte) t);
			}
			br.close();
		} catch (IOException e) {
			
		}
		map = map.substring(0, map.length() - 4);
		for(Player player : Bukkit.getOnlinePlayers())
			player.sendMessage(Message.chat("&lMap: &6&l" + map));
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
