package uhc;

import org.bukkit.plugin.java.JavaPlugin;

import uhc.commands.*;
import uhc.recipes.*;
import uhc.items.*;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		new GamemodeS(this);
		new GamemodeC(this);
		new SetHealth(this);
		new Heal(this);
		new Rename(this);
		new ClearName(this);
		new ForceEnchant(this);

		new Scythe(this);
		new ExodusRegen(this);
		new GoldenApples(this);
		new GoldenHeadEat(this);
		new CornucopiaEat(this);
		new ExpertSealUse(this);
		new PerunLightning(this);
		new ExcaliburExplosion(this);
		new DeusInvulnerability(this);
		new BarbEffects(this).applyEffects();
		new AndrewEffects(this).applyEffects();

		addRecipes();
	}
	
	public void addRecipes() {
		getServer().addRecipe(new Ambrosia().addRecipe());
		getServer().addRecipe(new Anduril().addRecipe());
		getServer().addRecipe(new ApprenticeBow().addRecipe());
		getServer().addRecipe(new ApprenticeHelmet().addRecipe());
		getServer().addRecipe(new ApprenticeSword().addRecipe());
		getServer().addRecipe(new ArtemisBow().addRecipe());
		getServer().addRecipe(new AxeofPerun().addRecipe());
		getServer().addRecipe(new Backpack().addRecipe());
		getServer().addRecipe(new BarbarianChestplate().addRecipe());
		getServer().addRecipe(new BookofThoth().addRecipe());
		getServer().addRecipe(new BrewingArtifact().addRecipe());
		getServer().addRecipe(new ChestofFate().addRecipe());
		getServer().addRecipe(new Cornucopia().addRecipe());
		getServer().addRecipe(new CupidsBow().addRecipe());
//		getServer().addRecipe(new Daredevil().addRecipe());
		getServer().addRecipe(new DeathsScythe().addRecipe());
		getServer().addRecipe(new DeliciousMeal().addRecipe());
		getServer().addRecipe(new DeusExMachina().addRecipe());
		getServer().addRecipe(new DiceofGod().addRecipe());
		getServer().addRecipe(new DragonArmor().addRecipe());
		getServer().addRecipe(new DragonSword().addRecipe());
		getServer().addRecipe(new DustofLight().addRecipe());
		getServer().addRecipe(new EnhancementBook().addRecipe());
		getServer().addRecipe(new EssenceofYggdrasil().addRecipe());
		getServer().addRecipe(new EvesTemptation().addRecipe());
		getServer().addRecipe(new Excalibur().addRecipe());
		getServer().addRecipe(new Exodus().addRecipe());
		getServer().addRecipe(new ExperienceBottle().addRecipe());
		getServer().addRecipe(new ExpertSeal().addRecipe());
		getServer().addRecipe(new FatesCall().addRecipe());
//		getServer().addRecipe(new Fenrir().addRecipe());
		getServer().addRecipe(new FlamingArtifact().addRecipe());
		getServer().addRecipe(new FlaskofCleansing().addRecipe());
		getServer().addRecipe(new FlaskofIchor().addRecipe());
		getServer().addRecipe(new Forge().addRecipe());
//		getServer().addRecipe(new FusionArmor().addRecipe());
		getServer().addRecipe(new GoldenHead().addRecipe());
		getServer().addRecipe(new GoldPack().addRecipe());
		getServer().addRecipe(new HealingFruit().addRecipe());
		getServer().addRecipe(new HermesBoots().addRecipe());
		getServer().addRecipe(new HideofLeviathan().addRecipe());
		getServer().addRecipe(new HolyWater().addRecipe());
		getServer().addRecipe(new IronPack().addRecipe());
		getServer().addRecipe(new KingsRod().addRecipe());
		getServer().addRecipe(new LeatherEconomy().addRecipe());
		getServer().addRecipe(new LightAnvil().addRecipe());
		getServer().addRecipe(new LightApple().addRecipe());
		getServer().addRecipe(new LightEnchantmentTable().addRecipe());
		getServer().addRecipe(new LuckyShears().addRecipe());
		getServer().addRecipe(new LumberjacksAxe().addRecipe());
		getServer().addRecipe(new MastersCompass().addRecipe());
		getServer().addRecipe(new MinersBlessing().addRecipe());
		getServer().addRecipe(new ModularBow().addRecipe());
		getServer().addRecipe(new Nectar().addRecipe());
		getServer().addRecipe(new Obsidian().addRecipe());
		getServer().addRecipe(new Panacea().addRecipe());
		getServer().addRecipe(new PhilosophersPickaxe().addRecipe());
		getServer().addRecipe(new PotionofToughness().addRecipe());
		getServer().addRecipe(new PotionofVelocity().addRecipe());
		getServer().addRecipe(new PotionofVitality().addRecipe());
		getServer().addRecipe(new PowerBook().addRecipe());
		getServer().addRecipe(new ProjProtectionBook().addRecipe());
		getServer().addRecipe(new ProtectionBook().addRecipe());
		getServer().addRecipe(new QuickPick().addRecipe());
		getServer().addRecipe(new Saddle().addRecipe());
		getServer().addRecipe(new SevenLeagueBoots().addRecipe());
		getServer().addRecipe(new SharpnessBook().addRecipe());
		getServer().addRecipe(new ShoesofVidar().addRecipe());
		getServer().addRecipe(new SpikedArmor().addRecipe());
		getServer().addRecipe(new SugarRush().addRecipe());
		getServer().addRecipe(new SwanSong().addRecipe());
		getServer().addRecipe(new TabletsofDestiny().addRecipe());
		getServer().addRecipe(new Tarnhelm().addRecipe());
		getServer().addRecipe(new TheDeep().addRecipe());
		getServer().addRecipe(new TheMark().addRecipe());
		getServer().addRecipe(new TreasureofElDorado().addRecipe());
		getServer().addRecipe(new VorpalSword().addRecipe());
		getServer().addRecipe(new WarlockPants().addRecipe());
	}
}
