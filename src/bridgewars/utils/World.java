package bridgewars.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import bridgewars.items.CustomItems;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class World {

	private static CustomItems items = new CustomItems();
	
	public static void fill(Location origin, int x, int y, int z) {
	}
	
	public static void attemptItemSpawn(int chance, boolean showParticles) {
		boolean validLocation = false;
		boolean success = false;
		
		int attempts = 0;
		int x = 0, y = 0, z = 0;
		
		 if(Utils.rand(chance) + 1 == chance)
			 success = true;
		
		while(!validLocation && success) {
			x = Utils.rand(45) - 22;
			z = Utils.rand(45) - 22;
			
			for(y = 0; y < 24; y++)
				if(Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, y, z)).getType() != Material.AIR
				&& Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, y + 1, z)).getType() == Material.AIR) {
					validLocation = true;
					break;
				}
			attempts++;
			if(attempts == 300)
				break;
		}
		
		if(validLocation) {
			Item item = null; 
			int r = -255, g = -255, b = -255;
			int quality = Utils.rand(100) + 1;
			if(quality <= 70) {
				item = Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"), x + 0.5, y + 1, z + 0.5), items.getWhiteItem());
				r = 255;
				g = 255;
				b = 255;
			}
			
			else if (quality >= 71 && quality <= 90) {
				item = Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"), x + 0.5, y + 1, z + 0.5), items.getGreenItem());
				g = 255;
			}
			
			else if (quality >= 91) {
				item = Bukkit.getWorld("world").dropItem(new Location(Bukkit.getWorld("world"), x + 0.5, y + 1, z + 0.5), items.getRedItem());
				r = 255;
				g = 0;
				b = 0;
			}
			item.setVelocity(item.getVelocity().setX(0).setY(0).setZ(0));
			if(showParticles)
				new Particle((Entity) item, EnumParticle.REDSTONE, 0, 1, 0, r, g, b, 1F, 0, 3000, false).runTaskTimer(Bukkit.getPluginManager().getPlugin("bridgewars"), 0L, 1L);
		}
	}
}
