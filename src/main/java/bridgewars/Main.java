package bridgewars;

import org.bukkit.plugin.java.JavaPlugin;

import bridgewars.behavior.BuildLimits;
import bridgewars.behavior.DisableArmorRemoval;
import bridgewars.behavior.DisableBasicItemDrops;
import bridgewars.behavior.DisableChickenSpawns;
import bridgewars.behavior.DisableFallDamage;
import bridgewars.behavior.DisableInventoryCrafting;
import bridgewars.behavior.DisableWeather;
import bridgewars.behavior.Explosions;
import bridgewars.behavior.HitDetection;
import bridgewars.behavior.ImmediateRespawn;
import bridgewars.behavior.IndestructibleBlocks;
import bridgewars.behavior.InfiniteBlocks;
import bridgewars.behavior.InstantVoidKill;
import bridgewars.behavior.PreventAnnoyingBowUse;
import bridgewars.behavior.PreventDecorationChanges;
import bridgewars.behavior.Saturation;
import bridgewars.commands.ChatSetting;
import bridgewars.commands.ClearMap;
import bridgewars.commands.Debug;
import bridgewars.commands.DeleteMap;
import bridgewars.commands.EditMode;
import bridgewars.commands.EndGame;
import bridgewars.commands.Fly;
import bridgewars.commands.GameMode;
import bridgewars.commands.GameSetting;
import bridgewars.commands.GiveItem;
import bridgewars.commands.JoinGame;
import bridgewars.commands.JoinTeam;
import bridgewars.commands.Label;
import bridgewars.commands.LeaveGame;
import bridgewars.commands.LoadMap;
import bridgewars.commands.MapList;
import bridgewars.commands.Menu;
import bridgewars.commands.OverwriteMap;
import bridgewars.commands.Permission;
import bridgewars.commands.PlayerSetting;
import bridgewars.commands.SaveMap;
import bridgewars.commands.StartGame;
import bridgewars.commands.Transform;
import bridgewars.commands.Warp;
import bridgewars.commands.Whisper;
import bridgewars.effects.Piggyback;
import bridgewars.effects.PlotArmor;
import bridgewars.game.CustomScoreboard;
import bridgewars.game.GameState;
import bridgewars.game.Leaderboards;
import bridgewars.game.PlayerKills;
import bridgewars.game.Timer;
import bridgewars.menus.InputHandler;
import bridgewars.messages.Chat;
import bridgewars.messages.DeathMessages;
import bridgewars.messages.OnJoin;
import bridgewars.messages.OnLeave;
import bridgewars.parkour.Checkpoints;
import bridgewars.parkour.ParkourCheckpoint;
import bridgewars.parkour.ParkourQuit;
import bridgewars.parkour.ParkourReset;
import bridgewars.parkour.ParkourTeleport;
import bridgewars.settings.GameSettings;
import bridgewars.settings.PlayerSettings;
import bridgewars.utils.ItemManager;
import bridgewars.utils.Permissions;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
		//initialization
		ItemManager.Initialize(this);
		
		//behavior
		new BuildLimits(this);
		new DisableArmorRemoval(this);
		new DisableChickenSpawns(this);
		new DisableFallDamage(this);
		new DisableInventoryCrafting(this);
		new DisableBasicItemDrops(this);
		new DisableWeather(this);
		new IndestructibleBlocks(this);
		new InfiniteBlocks(this);
		new Saturation(this);
		new HitDetection(this);
		new PreventAnnoyingBowUse(this);
		new PreventDecorationChanges(this);
		new InstantVoidKill(this);
		new Explosions(this);
		
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
		new Label(this);
		new Transform(this);
		new GameMode(this);
		new Permission(this);
		new Whisper(this);
		new ChatSetting(this);
		new PlayerSetting(this);
		new GameSetting(this);
		
		//messages
		new DeathMessages(this);
		new OnJoin(this);
		new OnLeave(this);
		new Chat(this);

		//game
		new Leaderboards();
		new CustomScoreboard().resetBoard();
		new PlayerKills(this);
		new Timer(this);
		new ImmediateRespawn(this);
		
		//effects
		new PlotArmor(this);
		new Piggyback(this);
		
		//parkour
		new Checkpoints(this);
		new ParkourTeleport(this);
		new ParkourQuit(this);
		new ParkourReset(this);
		new ParkourCheckpoint(this);
		
		//settings
		new InputHandler(this);
		
		//utils
		new Permissions(this);

		GameSettings.load();
		PlayerSettings.load();
		GameState.setState(GameState.INACTIVE);
	}
	
	@Override
	public void onDisable() {

	}
}
