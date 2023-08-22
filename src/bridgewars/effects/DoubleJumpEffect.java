package bridgewars.effects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import bridgewars.Main;
import bridgewars.commands.Fly;
import bridgewars.settings.DoubleJump;

public class DoubleJumpEffect implements Listener {
	
	public DoubleJumpEffect(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJump(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		
		if(DoubleJump.getState().isEnabled()
		&& !Fly.allowFlight.contains(p)) {
			e.setCancelled(true);

			p.setFlying(false);
			p.setAllowFlight(false);
			p.setVelocity(p.getVelocity().multiply(4).setY(1));
		}
	}
	
	@EventHandler
	public void onLand(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR
		&& DoubleJump.getState().isEnabled()
		&& p.getVelocity().getBlockY() <= 0)
			p.setAllowFlight(true);
	}
}
