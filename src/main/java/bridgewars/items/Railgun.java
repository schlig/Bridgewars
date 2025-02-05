package bridgewars.items;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import bridgewars.Main;
import bridgewars.effects.CooldownTimer;
import bridgewars.game.CSManager;
import bridgewars.game.Leaderboards;
import bridgewars.messages.Chat;
import bridgewars.settings.enums.Quake;
import bridgewars.utils.ICustomItem;
import bridgewars.utils.ItemBuilder;
import bridgewars.utils.Packet;
import bridgewars.utils.Utils;
import bridgewars.utils.World;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class Railgun implements ICustomItem, Listener {
	
	private Main plugin;
	
	private final int damage = 10;
	private final int range = 50;
	private ArrayList<Player> cooldown = new ArrayList<>();
	
    public Railgun(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.GREEN;
    }

    @Override
    public ItemStack createItem(Player p) {
        ItemStack item = new ItemStack(Material.BLAZE_ROD, 1);
		ItemBuilder.setID(item, getClass().getSimpleName().toLowerCase());
        ItemBuilder.setName(item, "Railgun");
        ItemBuilder.setLore(item, Arrays.asList(
                Chat.color("&r&7Shoots a beam that destroys wool blocks"),
                Chat.color(""),
                Chat.color("&r&9+" + damage + " Attack Damage")));
        ItemBuilder.disableStacking(item);
        return item;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR
        || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if(Utils.getID(item).equals(getClass().getSimpleName().toLowerCase())) {
                Player user = e.getPlayer();
                
                if(cooldown.contains(user))
                	return;
                
                if(!World.inGameArea(user.getLocation())) {
                	user.sendMessage(Chat.color("&cYou can't use this here!"));
                	return;
                }
                
                Block block;
                ArrayList<Player> hitPlayers = new ArrayList<>();
                
                float range2 = range;
                Location origin = user.getEyeLocation();
                Vector ray = Utils.raycast(origin.getYaw(), origin.getPitch());
                int[] beamColor = getRGB(user);
                
                //Phased out usage of BlockIterator since it was very limited.
                //This new raycast will step through blocks, 1/3 at a time and spawn a particle to show the railgun beam.
                //If the calculated point has a solid block that is NOT wool, it will stop and reduce the range of the hitbox.
                //If it is wool, the wool block gets destroyed. If Quake is active, wool becomes solid like other blocks.
        		for(float particlePoint = 1/3f; particlePoint < range2; particlePoint += 1/3f) {
        			float particleX = (float) origin.getX() - (float) (ray.getX() * particlePoint);
        			float particleY = (float) origin.getY() - (float) (ray.getY() * particlePoint);
        			float particleZ = (float) origin.getZ() + (float) (ray.getZ() * particlePoint);
                    Packet.sendParticle(EnumParticle.REDSTONE, particleX, particleY, particleZ, beamColor[0], beamColor[1], beamColor[2], 1, beamColor[3]);
                    block = user.getWorld().getBlockAt(new Location(user.getWorld(), particleX, particleY, particleZ));
                    if(block.getType().isSolid()) {
                    	if(block.getType() != Material.WOOL || Quake.getState().isEnabled())
                    		range2 = particlePoint;
                    	else if(block.getType() == Material.WOOL) {
                    		for(Player players : Bukkit.getOnlinePlayers())
                        		players.playSound(block.getLocation(), Sound.DIG_WOOL, 1F, 1F);
                    		block.breakNaturally();
                    	}
                    }
        		}
        		
        		//Creates a line segment using 2 points, then checks to see if the line intersects a player's hitbox (the f3+b one)
        		//much more accurate than what was here before, but may result in latency issues
    			float x = (float) origin.getX() - (float) (ray.getX() * range2);
    			float y = (float) origin.getY() - (float) (ray.getY() * range2);
    			float z = (float) origin.getZ() + (float) (ray.getZ() * range2);
                Vector pointA = new Vector(origin.getX(), origin.getY(), origin.getZ());
                Vector pointB = new Vector(x, y, z);
    			
                for(Player player : Bukkit.getOnlinePlayers()) {
                    if(Utils.checkAABBCollision(player, pointA, pointB)){
                        if(!Utils.matchTeam(player, user) && !hitPlayers.contains(player) && user != player) {
                        	if(SuperStar.playerIsInvincible(player) || player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR || !World.inGameArea(player.getLocation()))
                        		continue;
                            player.damage(damage, user);
                            hitPlayers.add(player);
                        }
                    }
                }
        		
                //Plays a sound if you hit someone.
                for(int i = 0; i < hitPlayers.size(); i++) {
                	float pitch = (float) Math.pow(2, (i - 12) / 12);
					Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("bridgewars"), () -> 
					user.playSound(user.getLocation(), Sound.ORB_PICKUP, 1F, pitch), i * 4);
                }
                
                //Quake being enabled gives the Railgun infinite uses.
                if(!Quake.getState().isEnabled()) {
                	Utils.subtractItem(user);
        		    Leaderboards.addPoint(user, "items");
                }

                //Runs a 0.5 second cooldown to prevent waste.
            	cooldown.add(user);
            	new CooldownTimer(user, 10, cooldown, null).runTaskTimer(plugin, 0, 1);
                user.playSound(user.getLocation(), Sound.FIREWORK_LARGE_BLAST2, 0.9F, 0.7F);
            }
        }
    }
    
    private int[] getRGB(Player p) {
        String color = CSManager.getTeam(p);
        int r = -255, g = 0, b = 0, a = 0;
        
        if(color == null) color = "rainbow";
        switch(color) {
        case "yellow":
            g = 255;
        case "red":
            r = 255;
        	break;
        		
        case "blue":
            b = 255;
        case "green":
            g = 255;
            break;
        		
        default:
        	r = 0;
        	a = 1;
        	break;
        }
        int[] test = {r, g, b, a};
        return test;
    }
}
