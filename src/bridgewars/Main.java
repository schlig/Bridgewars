package bridgewars;

import org.bukkit.plugin.java.JavaPlugin;

import bridgewars.behavior.BuildLimits;
import bridgewars.behavior.DisableArmorRemoval;
import bridgewars.behavior.DisableChickenSpawns;
import bridgewars.behavior.DisableFallDamage;
import bridgewars.behavior.DisableFriendlyFire;
import bridgewars.behavior.DisableInventoryCrafting;
import bridgewars.behavior.DisableItemDrops;
import bridgewars.behavior.DisableWeather;
import bridgewars.behavior.IndestructibleSpawnPlatforms;
import bridgewars.behavior.InfiniteBlocks;
import bridgewars.behavior.InstantVoidKill;
import bridgewars.behavior.PreventAnnoyingBowUse;
import bridgewars.behavior.Saturation;
import bridgewars.commands.ClearMap;
import bridgewars.commands.Debug;
import bridgewars.commands.DeleteMap;
import bridgewars.commands.EditMode;
import bridgewars.commands.EndGame;
import bridgewars.commands.Fly;
import bridgewars.commands.GiveItem;
import bridgewars.commands.JoinGame;
import bridgewars.commands.JoinTeam;
import bridgewars.commands.LeaveGame;
import bridgewars.commands.LoadMap;
import bridgewars.commands.MapList;
import bridgewars.commands.Menu;
import bridgewars.commands.OverwriteMap;
import bridgewars.commands.SaveMap;
import bridgewars.commands.StartGame;
import bridgewars.commands.Warp;
import bridgewars.effects.DoubleJumpEffect;
import bridgewars.game.CustomScoreboard;
import bridgewars.game.GameState;
import bridgewars.game.InstantRespawn;
import bridgewars.game.Kills;
import bridgewars.game.Timer;
import bridgewars.items.BlackHoleThrow;
import bridgewars.items.BridgeEggThrow;
import bridgewars.items.FireballLaunch;
import bridgewars.items.ForceFieldGenerator;
import bridgewars.items.HomeRunBat;
import bridgewars.items.PortableDoinkHut;
import bridgewars.items.SadRoom;
import bridgewars.menus._MenuInput;
import bridgewars.messages.DeathMessages;
import bridgewars.messages.OnJoin;
import bridgewars.messages.OnLeave;
import bridgewars.settings._Settings;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
		//behavior
		new BuildLimits(this);
		new DisableArmorRemoval(this);
		new DisableChickenSpawns(this);
		new DisableFallDamage(this);
		new DisableInventoryCrafting(this);
		new DisableItemDrops(this);
		new DisableWeather(this);
		new IndestructibleSpawnPlatforms(this);
		new InfiniteBlocks(this);
		new InstantRespawn(this);
		new InstantVoidKill(this);
		new Saturation(this);
		new DisableFriendlyFire(this);
		new PreventAnnoyingBowUse(this);
		
		//commands
		new Menu(this);
		new StartGame(this);
		new JoinTeam(this);
		new EndGame(this);
		new JoinGame(this);
		new GiveItem(this);
		new SaveMap(this);
		new LoadMap(this);
		new ClearMap(this);
		new MapList(this);
		new DeleteMap(this);
		new OverwriteMap(this);
		new Warp(this);
		new EditMode(this);
		new LeaveGame(this);
		new Fly(this);
		new Debug(this);
		
		//effects
		new DoubleJumpEffect(this);
		
		//game
		new CustomScoreboard().clearTeams();
		new Kills(this);
		new Timer(this);
		
		//settings
		new _MenuInput(this);
		
		//items
		new BlackHoleThrow(this);
		new BridgeEggThrow(this);
		new FireballLaunch(this);
		new PortableDoinkHut(this);
		new ForceFieldGenerator(this);
		new HomeRunBat(this);
		new SadRoom(this);
		
		//messages
		new DeathMessages(this);
		new OnJoin(this);
		new OnLeave(this);

		GameState.setState(GameState.INACTIVE);
		_Settings.load();
	}
}

/*Item Ideas:
 * -Proximity Mine
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */ 
