package bridgewars.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import net.minecraft.server.v1_8_R3.EnumParticle;

public class World {
	
	private static int x = 0, y = 0, z = 0;
	
	public static void fill(Location origin, int x, int y, int z) {
		for(x = 0; x < 1; x++)
			for(y = 0; y < 1; y++)
				for(z = 0; z < 1; z++) {
					
				}
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
			if(attempts == 300) //the check for a valid location is only ran 300 times to prevent excessive resource use
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
					r = 0;
					g = 0;
					b = 255;
			}
			item = Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"), x + 0.5, y + 1, z + 0.5), spawnableItem.createItem(null));
			item.setVelocity(item.getVelocity().setX(0).setY(0).setZ(0));
			if(showParticles) //if particles are turned on, it will create a colored particle above the item to easily be seen from a distance
				new Particle((Entity) item, EnumParticle.REDSTONE, 
						0, 1, 0, 
						r, g, b, 
						1F, 0, 6000, false).runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 0L, 1L);
		}
	}
}
