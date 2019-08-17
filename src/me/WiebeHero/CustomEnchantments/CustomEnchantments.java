package me.WiebeHero.CustomEnchantments;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import NeededStuff.AFKSystem;
import NeededStuff.CancelJoinLeaveAdvancementMessages;
import NeededStuff.CancelLootSteal;
import NeededStuff.CancelWeaponPlaced;
import NeededStuff.Chat;
import NeededStuff.ChatItem;
import NeededStuff.CombatTag;
import NeededStuff.Disparitys;
import NeededStuff.EnderPearlCooldown;
import NeededStuff.LevelRestrictions;
import NeededStuff.MOTDSetting;
import NeededStuff.MoneyCreation;
import NeededStuff.Portals;
import NeededStuff.PreventIllegalItems;
import NeededStuff.RestrictInteractionWithBlocks;
import NeededStuff.ScoreDungeon;
import NeededStuff.SetHomeSystem;
import NeededStuff.SpawnCommand;
import NeededStuff.SwordSwingProgress;
import NeededStuff.TNTExplodeCovered;
import NeededStuff.TPACommand;
import Skills.ClassEnvy;
import Skills.ClassGluttony;
import Skills.ClassGreed;
import Skills.ClassLust;
import Skills.ClassMenuSelection;
import Skills.ClassPride;
import Skills.ClassSloth;
import Skills.ClassWrath;
import Skills.EffectSkills;
import Skills.PlayerClass;
import Skills.SkillCommand;
import Skills.SkillEnum.Skills;
import Skills.SkillJoin;
import Skills.SkillMenuInteract;
import Skills.XPEarningMobs;
import me.WiebeHero.CraftRecipes.CallRecipe;
import me.WiebeHero.CraftRecipes.CraftableWeapons;
import me.WiebeHero.CraftRecipes.UnblockBrewing;
import me.WiebeHero.CustomArmor.Common.ArmorBoots;
import me.WiebeHero.CustomArmor.Common.ArmorChestplate;
import me.WiebeHero.CustomArmor.Common.ArmorHelmet;
import me.WiebeHero.CustomArmor.Common.ArmorLeggings;
import me.WiebeHero.CustomBows.Bows;
import me.WiebeHero.CustomEnchantmentsA.Absorbing;
import me.WiebeHero.CustomEnchantmentsA.AbsorbingCombo;
import me.WiebeHero.CustomEnchantmentsA.AbsorbingComeback;
import me.WiebeHero.CustomEnchantmentsA.ArcanistExplosion;
import me.WiebeHero.CustomEnchantmentsA.Bane;
import me.WiebeHero.CustomEnchantmentsA.BlastOff;
import me.WiebeHero.CustomEnchantmentsA.Cactus;
import me.WiebeHero.CustomEnchantmentsA.CreeperSpirit;
import me.WiebeHero.CustomEnchantmentsA.Curse;
import me.WiebeHero.CustomEnchantmentsA.DamageReturn;
import me.WiebeHero.CustomEnchantmentsA.DodgeRoll;
import me.WiebeHero.CustomEnchantmentsA.DragonsSkin;
import me.WiebeHero.CustomEnchantmentsA.Dummy;
import me.WiebeHero.CustomEnchantmentsA.Enlightened;
import me.WiebeHero.CustomEnchantmentsA.Escape;
import me.WiebeHero.CustomEnchantmentsA.ExplodingParrot;
import me.WiebeHero.CustomEnchantmentsA.Fortitude;
import me.WiebeHero.CustomEnchantmentsA.FossilBlaze;
import me.WiebeHero.CustomEnchantmentsA.Ghostly;
import me.WiebeHero.CustomEnchantmentsA.Harden;
import me.WiebeHero.CustomEnchantmentsA.Hastened;
import me.WiebeHero.CustomEnchantmentsA.Ignite;
import me.WiebeHero.CustomEnchantmentsA.Invincibility;
import me.WiebeHero.CustomEnchantmentsA.JellyFish;
import me.WiebeHero.CustomEnchantmentsA.Kadabra;
import me.WiebeHero.CustomEnchantmentsA.LastStand;
import me.WiebeHero.CustomEnchantmentsA.Madeoutofblocks;
import me.WiebeHero.CustomEnchantmentsA.MagicResistance;
import me.WiebeHero.CustomEnchantmentsA.MeleeResistance;
import me.WiebeHero.CustomEnchantmentsA.Nurtrition;
import me.WiebeHero.CustomEnchantmentsA.PowerfullStrike;
import me.WiebeHero.CustomEnchantmentsA.Rage;
import me.WiebeHero.CustomEnchantmentsA.RangedResistance;
import me.WiebeHero.CustomEnchantmentsA.Regenerator;
import me.WiebeHero.CustomEnchantmentsA.Saturation;
import me.WiebeHero.CustomEnchantmentsA.SelfDestruct;
import me.WiebeHero.CustomEnchantmentsA.Slowness1;
import me.WiebeHero.CustomEnchantmentsA.Slowness2;
import me.WiebeHero.CustomEnchantmentsA.SmokeScreen;
import me.WiebeHero.CustomEnchantmentsA.SnareA;
import me.WiebeHero.CustomEnchantmentsA.SurvivalistInstinct;
import me.WiebeHero.CustomEnchantmentsA.Tank;
import me.WiebeHero.CustomEnchantmentsA.TitanicOath;
import me.WiebeHero.CustomEnchantmentsA.ToughenedAura;
import me.WiebeHero.CustomEnchantmentsA.Valor;
import me.WiebeHero.CustomEnchantmentsA.Withering;
import me.WiebeHero.CustomEnchantmentsB.BlackHeartB;
import me.WiebeHero.CustomEnchantmentsB.BlastB;
import me.WiebeHero.CustomEnchantmentsB.ConfusionB;
import me.WiebeHero.CustomEnchantmentsB.DisarmorB;
import me.WiebeHero.CustomEnchantmentsB.GrapplingHook;
import me.WiebeHero.CustomEnchantmentsB.LifestealB;
import me.WiebeHero.CustomEnchantmentsB.Lightning;
import me.WiebeHero.CustomEnchantmentsB.ParalyzeB;
import me.WiebeHero.CustomEnchantmentsB.PickpocketB;
import me.WiebeHero.CustomEnchantmentsB.PierceB;
import me.WiebeHero.CustomEnchantmentsB.PoisonB;
import me.WiebeHero.CustomEnchantmentsB.Sandstorm;
import me.WiebeHero.CustomEnchantmentsB.WeaknessB;
import me.WiebeHero.CustomEnchantmentsB.WitherB;
import me.WiebeHero.CustomEnchantmentsM.AllOut;
import me.WiebeHero.CustomEnchantmentsM.Beserk;
import me.WiebeHero.CustomEnchantmentsM.Blast;
import me.WiebeHero.CustomEnchantmentsM.Bleed;
import me.WiebeHero.CustomEnchantmentsM.Blind;
import me.WiebeHero.CustomEnchantmentsM.Blizzard;
import me.WiebeHero.CustomEnchantmentsM.Brand;
import me.WiebeHero.CustomEnchantmentsM.Charge;
import me.WiebeHero.CustomEnchantmentsM.ChronicalDisturbance;
import me.WiebeHero.CustomEnchantmentsM.Confusion;
import me.WiebeHero.CustomEnchantmentsM.Cyclone;
import me.WiebeHero.CustomEnchantmentsM.DefensivePosition;
import me.WiebeHero.CustomEnchantmentsM.Disarmor;
import me.WiebeHero.CustomEnchantmentsM.DragonsFireball;
import me.WiebeHero.CustomEnchantmentsM.Drain;
import me.WiebeHero.CustomEnchantmentsM.Featherweight;
import me.WiebeHero.CustomEnchantmentsM.FinalBlow;
import me.WiebeHero.CustomEnchantmentsM.FireAspect;
import me.WiebeHero.CustomEnchantmentsM.Freeze;
import me.WiebeHero.CustomEnchantmentsM.HeadHunter;
import me.WiebeHero.CustomEnchantmentsM.Headbash;
import me.WiebeHero.CustomEnchantmentsM.HeavyHand;
import me.WiebeHero.CustomEnchantmentsM.LargeFireball;
import me.WiebeHero.CustomEnchantmentsM.Levitate;
import me.WiebeHero.CustomEnchantmentsM.Lifesteal;
import me.WiebeHero.CustomEnchantmentsM.Looting;
import me.WiebeHero.CustomEnchantmentsM.NegativeHollow;
import me.WiebeHero.CustomEnchantmentsM.Paralyze;
import me.WiebeHero.CustomEnchantmentsM.PerfectSlash;
import me.WiebeHero.CustomEnchantmentsM.Phantom;
import me.WiebeHero.CustomEnchantmentsM.Pinch;
import me.WiebeHero.CustomEnchantmentsM.Poison;
import me.WiebeHero.CustomEnchantmentsM.Precision;
import me.WiebeHero.CustomEnchantmentsM.Revenge;
import me.WiebeHero.CustomEnchantmentsM.Sharpness;
import me.WiebeHero.CustomEnchantmentsM.SkyHigh;
import me.WiebeHero.CustomEnchantmentsM.Slow;
import me.WiebeHero.CustomEnchantmentsM.SoulBurst;
import me.WiebeHero.CustomEnchantmentsM.SoulReaper;
import me.WiebeHero.CustomEnchantmentsM.Spectral;
import me.WiebeHero.CustomEnchantmentsM.StarShine;
import me.WiebeHero.CustomEnchantmentsM.Steal;
import me.WiebeHero.CustomEnchantmentsM.Vampirism;
import me.WiebeHero.CustomEnchantmentsM.Venom;
import me.WiebeHero.CustomEnchantmentsM.Weakness;
import me.WiebeHero.CustomEnchantmentsM.Wither;
import me.WiebeHero.CustomEnchantmentsM.Wizard;
import me.WiebeHero.CustomEnchantmentsM.WolfPack;
import me.WiebeHero.CustomEnchantmentsS.BlastOffS;
import me.WiebeHero.CustomEnchantmentsS.CactusS;
import me.WiebeHero.CustomEnchantmentsS.EncouragedS;
import me.WiebeHero.CustomEnchantmentsS.EnlightenedS;
import me.WiebeHero.CustomEnchantmentsS.HardenS;
import me.WiebeHero.CustomEnchantmentsS.IgniteS;
import me.WiebeHero.CustomEnchantmentsS.KadabraS;
import me.WiebeHero.CustomEnchantmentsS.SaturationS;
import me.WiebeHero.CustomEnchantmentsS.SelfDestructS;
import me.WiebeHero.CustomEnchantmentsS.SlownessS;
import me.WiebeHero.CustomEnchantmentsS.SunshineS;
import me.WiebeHero.CustomEnchantmentsS.TankS;
import me.WiebeHero.CustomEnchantmentsS.ValorS;
import me.WiebeHero.CustomHitDelay.HitDelay;
import me.WiebeHero.CustomItemsFOOD.BunnyPotion;
import me.WiebeHero.CustomItemsFOOD.ButterscotchPie;
import me.WiebeHero.CustomItemsFOOD.ChocolateChippedCookie;
import me.WiebeHero.CustomItemsFOOD.FaceSteak;
import me.WiebeHero.CustomItemsFOOD.Fusgel;
import me.WiebeHero.CustomItemsFOOD.HolyPotato;
import me.WiebeHero.CustomItemsFOOD.LegendaryHero;
import me.WiebeHero.CustomItemsFOOD.MenJerky;
import me.WiebeHero.CustomItemsFOOD.PurifiedSpiderEye;
import me.WiebeHero.CustomItemsFOOD.SeasonedCarrot;
import me.WiebeHero.CustomItemsFOOD.SkyFish;
import me.WiebeHero.CustomItemsFOOD.TrickyFish;
import me.WiebeHero.DFShops.DFShop;
import me.WiebeHero.DFShops.MoneyCreate;
import me.WiebeHero.DFShops.PayCommand;
import me.WiebeHero.DFWeapons.DFWeaponUpgrade;
import me.WiebeHero.Factions.DFFactions;
import me.WiebeHero.Factions.FactionsHandler;
import me.WiebeHero.FishingLoot.ChangeFishDrops;
import me.WiebeHero.LootChest.ChestList;
import me.WiebeHero.LootChest.LootRewards;
import me.WiebeHero.LootChest.SetChest;
import me.WiebeHero.Moderation.ModerationGUI;
import me.WiebeHero.Moderation.ModerationGUICommand;
import me.WiebeHero.Novis.NovisInventory;
import me.WiebeHero.Shields.DFShields;
import me.WiebeHero.Spawners.DeathOfMob;
import me.WiebeHero.Spawners.MobDamage;
import me.WiebeHero.Spawners.SetSpawner;
import me.WiebeHero.Spawners.SpawnerList;
import me.WiebeHero.XpTrader.XPAddPlayers;
import me.WiebeHero.XpTrader.XPAddWeapons;
import me.WiebeHero.XpTrader.XPTraderMenu;

public class CustomEnchantments extends JavaPlugin implements Listener{
	private static CustomEnchantments instance;
	private SkillCommand skillCommand = new SkillCommand();
	private SetSpawner command = new SetSpawner();
	private SpawnerList sp = new SpawnerList();
	private DFFactions fac = new DFFactions();
	private Portals p = new Portals();
	private PayCommand pay = new PayCommand();
	private SpawnCommand spa = new SpawnCommand();
	private SetChest loot = new SetChest();
	private MoneyCreate money = new MoneyCreate();
	private SetHomeSystem sethome = new SetHomeSystem();
	private TPACommand tpa = new TPACommand();
	private ModerationGUICommand mod = new ModerationGUICommand();
	private ChestList cList = new ChestList();
	private LootRewards lootR = new LootRewards();
	private ConfigManager cfgm;
	private PlayerClass pc = new PlayerClass();
	private CraftableWeapons cw = new CraftableWeapons();
	int level;
	public Scoreboard scoreboard;
	public static boolean shutdown = false;
	@Override
	public void onEnable() {
		instance = this;
		scoreboard = this.getServer().getScoreboardManager().getMainScoreboard();
		//Enable Plugin Message
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nThe plugin CustomEnchantments has been enabled!\n\n");
		//Config Manager
		loadConfigManager();
		//Melee Enchantments
		getServer().getPluginManager().registerEvents(new Confusion(), this);
		getServer().getPluginManager().registerEvents(new Wither(), this);
		getServer().getPluginManager().registerEvents(new HeavyHand(), this);
		getServer().getPluginManager().registerEvents(new WolfPack(), this);
		getServer().getPluginManager().registerEvents(new SkyHigh(), this);
		getServer().getPluginManager().registerEvents(new Poison(), this);
		getServer().getPluginManager().registerEvents(new LargeFireball(), this);
		getServer().getPluginManager().registerEvents(new Beserk(), this);
		getServer().getPluginManager().registerEvents(new Blizzard(), this);
		getServer().getPluginManager().registerEvents(new Bleed(), this);
		getServer().getPluginManager().registerEvents(new Slow(), this);
		getServer().getPluginManager().registerEvents(new NegativeHollow(), this);
		getServer().getPluginManager().registerEvents(new Paralyze(), this);
		getServer().getPluginManager().registerEvents(new Weakness(), this);
		getServer().getPluginManager().registerEvents(new Freeze(), this);
		getServer().getPluginManager().registerEvents(new Lifesteal(), this);
		getServer().getPluginManager().registerEvents(new Blind(), this);
		getServer().getPluginManager().registerEvents(new Spectral(), this);
		getServer().getPluginManager().registerEvents(new Levitate(), this);
		getServer().getPluginManager().registerEvents(new FinalBlow(), this);
		getServer().getPluginManager().registerEvents(new Blast(), this);
		getServer().getPluginManager().registerEvents(new Venom(), this);
		getServer().getPluginManager().registerEvents(new Featherweight(), this);
		getServer().getPluginManager().registerEvents(new ChronicalDisturbance(), this);
		getServer().getPluginManager().registerEvents(new AllOut(), this);
		getServer().getPluginManager().registerEvents(new Wizard(), this);
		getServer().getPluginManager().registerEvents(new StarShine(), this);
		getServer().getPluginManager().registerEvents(new FireAspect(), this);
		getServer().getPluginManager().registerEvents(new Disarmor(), this);
		getServer().getPluginManager().registerEvents(new Drain(), this);
		getServer().getPluginManager().registerEvents(new HeadHunter(), this);
		getServer().getPluginManager().registerEvents(new Vampirism(), this);
		getServer().getPluginManager().registerEvents(new FireAspect(), this);
		getServer().getPluginManager().registerEvents(new DragonsFireball(), this);
		getServer().getPluginManager().registerEvents(new Steal(), this);
		getServer().getPluginManager().registerEvents(new Pinch(), this);
		getServer().getPluginManager().registerEvents(new Sharpness(), this);
		getServer().getPluginManager().registerEvents(new Looting(), this);
		getServer().getPluginManager().registerEvents(new Headbash(), this);
		getServer().getPluginManager().registerEvents(new DefensivePosition(), this);
		getServer().getPluginManager().registerEvents(new Phantom(), this);
		getServer().getPluginManager().registerEvents(new Revenge(), this);
		getServer().getPluginManager().registerEvents(new SoulReaper(), this);
		getServer().getPluginManager().registerEvents(new Precision(), this);
		getServer().getPluginManager().registerEvents(new PerfectSlash(), this);
		getServer().getPluginManager().registerEvents(new Charge(), this);
		getServer().getPluginManager().registerEvents(new Cyclone(), this);
		getServer().getPluginManager().registerEvents(new SoulBurst(), this);
		getServer().getPluginManager().registerEvents(new Brand(), this);
		//Armor Enchantments
		getServer().getPluginManager().registerEvents(new Saturation(), this);
		getServer().getPluginManager().registerEvents(new Absorbing(), this);
		getServer().getPluginManager().registerEvents(new Ghostly(), this);
		getServer().getPluginManager().registerEvents(new Hastened(), this);
		getServer().getPluginManager().registerEvents(new LastStand(), this);
		getServer().getPluginManager().registerEvents(new Rage(), this);
		getServer().getPluginManager().registerEvents(new SmokeScreen(), this);
		getServer().getPluginManager().registerEvents(new Withering(), this);
		getServer().getPluginManager().registerEvents(new Slowness1(), this);
		getServer().getPluginManager().registerEvents(new Regenerator(), this);
		getServer().getPluginManager().registerEvents(new Kadabra(), this);
		getServer().getPluginManager().registerEvents(new Invincibility(), this);
		getServer().getPluginManager().registerEvents(new Ignite(), this);
		getServer().getPluginManager().registerEvents(new Cactus(), this);
		getServer().getPluginManager().registerEvents(new Enlightened(), this);
		getServer().getPluginManager().registerEvents(new Bane(), this);
		getServer().getPluginManager().registerEvents(new SnareA(), this);
		getServer().getPluginManager().registerEvents(new SelfDestruct(), this);
		getServer().getPluginManager().registerEvents(new Slowness2(), this);
		getServer().getPluginManager().registerEvents(new BlastOff(), this);
		getServer().getPluginManager().registerEvents(new Nurtrition(), this);
		getServer().getPluginManager().registerEvents(new Escape(), this);
		getServer().getPluginManager().registerEvents(new AbsorbingComeback(), this);
		getServer().getPluginManager().registerEvents(new DamageReturn(), this);
		getServer().getPluginManager().registerEvents(new Tank(), this);
		getServer().getPluginManager().registerEvents(new ArcanistExplosion(), this);
		getServer().getPluginManager().registerEvents(new PowerfullStrike(), this);
		getServer().getPluginManager().registerEvents(new Harden(), this);
		getServer().getPluginManager().registerEvents(new Valor(), this);
		getServer().getPluginManager().registerEvents(new ExplodingParrot(), this);
		getServer().getPluginManager().registerEvents(new AbsorbingCombo(), this);
		getServer().getPluginManager().registerEvents(new JellyFish(), this);
		getServer().getPluginManager().registerEvents(new Dummy(), this);
		getServer().getPluginManager().registerEvents(new FossilBlaze(), this);
		getServer().getPluginManager().registerEvents(new Fortitude(), this);
		getServer().getPluginManager().registerEvents(new Curse(), this);
		getServer().getPluginManager().registerEvents(new TitanicOath(), this);
		getServer().getPluginManager().registerEvents(new RangedResistance(), this);
		getServer().getPluginManager().registerEvents(new MeleeResistance(), this);
		getServer().getPluginManager().registerEvents(new CreeperSpirit(), this);
		getServer().getPluginManager().registerEvents(new DragonsSkin(), this);
		getServer().getPluginManager().registerEvents(new DodgeRoll(), this);
		getServer().getPluginManager().registerEvents(new SurvivalistInstinct(), this);
		getServer().getPluginManager().registerEvents(new MagicResistance(), this);
		getServer().getPluginManager().registerEvents(new Madeoutofblocks(), this);
		getServer().getPluginManager().registerEvents(new ToughenedAura(), this);
		//Shield Enchantments
		getServer().getPluginManager().registerEvents(new BlastOffS(), this);
		getServer().getPluginManager().registerEvents(new CactusS(), this);
		getServer().getPluginManager().registerEvents(new EncouragedS(), this);
		getServer().getPluginManager().registerEvents(new EnlightenedS(), this);
		getServer().getPluginManager().registerEvents(new HardenS(), this);
		getServer().getPluginManager().registerEvents(new IgniteS(), this);
		getServer().getPluginManager().registerEvents(new KadabraS(), this);
		getServer().getPluginManager().registerEvents(new SaturationS(), this);
		getServer().getPluginManager().registerEvents(new SelfDestructS(), this);
		getServer().getPluginManager().registerEvents(new SlownessS(), this);
		getServer().getPluginManager().registerEvents(new SunshineS(), this);
		getServer().getPluginManager().registerEvents(new TankS(), this);
		getServer().getPluginManager().registerEvents(new ValorS(), this);
		//Bow Enchantments
		getServer().getPluginManager().registerEvents(new BlackHeartB(), this);
		getServer().getPluginManager().registerEvents(new BlastB(), this);
		getServer().getPluginManager().registerEvents(new ConfusionB(), this);
		getServer().getPluginManager().registerEvents(new DisarmorB(), this);
		getServer().getPluginManager().registerEvents(new LifestealB(), this);
		getServer().getPluginManager().registerEvents(new ParalyzeB(), this);
		getServer().getPluginManager().registerEvents(new PickpocketB(), this);
		getServer().getPluginManager().registerEvents(new PierceB(), this);
		getServer().getPluginManager().registerEvents(new PoisonB(), this);
		getServer().getPluginManager().registerEvents(new WeaknessB(), this);
		getServer().getPluginManager().registerEvents(new WitherB(), this);
		getServer().getPluginManager().registerEvents(new Lightning(), this);
		getServer().getPluginManager().registerEvents(new Sandstorm(), this);
		getServer().getPluginManager().registerEvents(new GrapplingHook(), this);
		//Custom Weapons
		getServer().getPluginManager().registerEvents(new DFWeaponUpgrade(), this);
		//Custom Armor
		getServer().getPluginManager().registerEvents(new ArmorHelmet(), this);
		getServer().getPluginManager().registerEvents(new ArmorChestplate(), this);
		getServer().getPluginManager().registerEvents(new ArmorLeggings(), this);
		getServer().getPluginManager().registerEvents(new ArmorBoots(), this);
		//Custom Shields
		getServer().getPluginManager().registerEvents(new DFShields(), this);
		//Custom Bows
		getServer().getPluginManager().registerEvents(new Bows(), this);
		//Skills
		getServer().getPluginManager().registerEvents(new SkillMenuInteract(), this);
		getServer().getPluginManager().registerEvents(new SkillJoin(), this);
		getServer().getPluginManager().registerEvents(new ClassMenuSelection(), this);
		getServer().getPluginManager().registerEvents(new XPEarningMobs(), this);
		getServer().getPluginManager().registerEvents(new EffectSkills(), this);
		getServer().getPluginManager().registerEvents(new ClassWrath(), this);
		getServer().getPluginManager().registerEvents(new ClassLust(), this);
		getServer().getPluginManager().registerEvents(new ClassGluttony(), this);
		getServer().getPluginManager().registerEvents(new ClassGreed(), this);
		getServer().getPluginManager().registerEvents(new ClassSloth(), this);
		getServer().getPluginManager().registerEvents(new ClassEnvy(), this);
		getServer().getPluginManager().registerEvents(new ClassPride(), this);
		//Spawners
		
		getServer().getPluginManager().registerEvents(new DeathOfMob(), this);
		getServer().getPluginManager().registerEvents(new MobDamage(), this);
		//Novis
		getServer().getPluginManager().registerEvents(new NovisInventory(), this);
		//Loot Chest
		getServer().getPluginManager().registerEvents(cList, this);
		//Brewing Recipes
		getServer().getPluginManager().registerEvents(new UnblockBrewing(), this);
		getServer().getPluginManager().registerEvents(new CallRecipe(), this);
		//AFKSystem
		getServer().getPluginManager().registerEvents(new AFKSystem(), this);
		//Scoreboard
		getServer().getPluginManager().registerEvents(new ScoreDungeon(), this);
		//XP Trader
		getServer().getPluginManager().registerEvents(new XPTraderMenu(), this);
		getServer().getPluginManager().registerEvents(new XPAddWeapons(), this);
		getServer().getPluginManager().registerEvents(new XPAddPlayers(), this);
		//lootSteal
		getServer().getPluginManager().registerEvents(new CancelLootSteal(), this);
		//Factions
		getServer().getPluginManager().registerEvents(new FactionsHandler(), this);
		getServer().getPluginManager().registerEvents(fac, this);
		//Stuff
		getServer().getPluginManager().registerEvents(sethome, this);
		//Crafteable Weapons and item recipes ;)
		getServer().getPluginManager().registerEvents(cw, this);
		cw.addChainHemlet();
		cw.addChainChestplate();
		cw.addChainLeggings();
		cw.addChainBoots();
		cw.addLongBow();
		cw.addBow();
		cw.addShortBow();
		cw.addAmuletOfHealth();
		cw.addAmuletOfDefense();
		cw.addAmuletOfCharge();
		cw.addAmuletOffResistance();
		cw.addAmuletOfPower();
		cw.addAmuletOfSpeed();
		cw.addAmuletOfToughness();
		File f1 =  new File("plugins/CustomEnchantments/factionsConfig.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f1);
		try{
			yml.load(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(yml.getConfigurationSection("Factions.List") != null) {
			if(!yml.get("Factions.List").equals("{}")) {
				fac.loadFactionNameList(yml, f1);
				fac.loadAllyList(yml, f1);
				fac.loadChunkList(yml, f1);
				fac.loadChunkTotalList(yml, f1);
				fac.loadFTop(yml, f1);
				fac.loadMemberList(yml, f1);
				fac.loadRankedList(yml, f1);
				fac.loadPlayersAlliedList(yml, f1);
			}
		}
		File f2 =  new File("plugins/CustomEnchantments/modConfig.yml");
		YamlConfiguration yml1 = YamlConfiguration.loadConfiguration(f2);
		try{
			yml1.load(f2);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(yml1.getConfigurationSection("Moderation.Punishments") != null) {
			if(!yml1.get("Moderation.Punishments").equals("{}")) {
				mod.loadBanTime(yml1, f2);
				mod.loadMuteTime(yml1, f2);
				mod.loadPermBan(yml1, f2);
				mod.loadPermMute(yml1, f2);
				mod.loadReasonBanList(yml1, f2);
				mod.loadReasonMuteList(yml1, f2);
				mod.loadWarnOffenseList(yml1, f2);
				mod.loadWarnReasonList(yml1, f2);
			}
		}
		File f3 = new File("plugins/CustomEnchantments/spawnerConfig.yml");
		YamlConfiguration yml2 = YamlConfiguration.loadConfiguration(f3);
		command.loadSpawners(yml2, f3);
		sp.spawnerJoin();
		File f4 =  new File("plugins/CustomEnchantments/cashConfig.yml");
		YamlConfiguration yml3 = YamlConfiguration.loadConfiguration(f4);
		try{
			yml3.load(f4);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(yml3.getConfigurationSection("List") != null) {
			if(!yml3.get("List").equals("{}")) {
				money.loadMoney(yml3, f4);
			}
		}
		File f5 = new File("plugins/CustomEnchantments/playerskillsDF.yml");
		YamlConfiguration yml4 = YamlConfiguration.loadConfiguration(f5);
		try{
			yml4.load(f5);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(yml4.getConfigurationSection("Skills.Players") != null) {
			if(!yml4.get("Skills.Players").equals("{}")) {
				pc.registerProfiles(yml4, f5);
			}
		}
		File f6 =  new File("plugins/CustomEnchantments/setHomeConfig.yml");
		YamlConfiguration yml5 = YamlConfiguration.loadConfiguration(f6);
		try{
			yml5.load(f6);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(yml5.getConfigurationSection("Homes") != null) {
			if(!yml5.get("Homes").equals("{}")) {
				sethome.loadHomes(yml5, f6);
			}
		}
		File f7 =  new File("plugins/CustomEnchantments/lootConfig.yml");
		YamlConfiguration yml6 = YamlConfiguration.loadConfiguration(f7);
		try{
			yml6.load(f7);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(yml6.getConfigurationSection("Loot.Chests") != null) {
			if(!yml6.get("Loot.Chests").equals("{}")) {
				loot.loadLootChests(yml6, f7);
			}
		}
		//TNT
		getServer().getPluginManager().registerEvents(new TNTExplodeCovered(), this);
		//NeededStuff
		getServer().getPluginManager().registerEvents(new MoneyCreation(), this);
		getServer().getPluginManager().registerEvents(new CombatTag(), this);
		getServer().getPluginManager().registerEvents(new Chat(), this);
		getServer().getPluginManager().registerEvents(new Portals(), this);
		getServer().getPluginManager().registerEvents(new SwordSwingProgress(), this);
		getServer().getPluginManager().registerEvents(new CancelWeaponPlaced(), this);
		getServer().getPluginManager().registerEvents(new PreventIllegalItems(), this);
		getServer().getPluginManager().registerEvents(new Disparitys(), this);
		getServer().getPluginManager().registerEvents(new LevelRestrictions(), this);
		getServer().getPluginManager().registerEvents(new RestrictInteractionWithBlocks(), this);
		getServer().getPluginManager().registerEvents(new EnderPearlCooldown(), this);
		getServer().getPluginManager().registerEvents(new MOTDSetting(), this);
		getServer().getPluginManager().registerEvents(new ChatItem(), this);
		getServer().getPluginManager().registerEvents(new CancelJoinLeaveAdvancementMessages(), this);
		//Shop
		getServer().getPluginManager().registerEvents(new DFShop(), this);
		getServer().getPluginManager().registerEvents(new MoneyCreate(), this);
		//HitDelay
		getServer().getPluginManager().registerEvents(new HitDelay(), this);
		//Commands
		getCommand(command.cmdSpawner).setExecutor(command);
		getCommand(loot.cmdNovis).setExecutor(loot);
		getCommand(fac.faction).setExecutor(fac);
		getCommand(p.rtp).setExecutor(p);
		getCommand(spa.spawn).setExecutor(spa);
		getCommand(pay.command).setExecutor(pay);
		getCommand(pay.balance).setExecutor(pay);
		getCommand(pay.money).setExecutor(pay);
		getCommand(sethome.command).setExecutor(sethome);
		getCommand(sethome.homeCommand).setExecutor(sethome);
		getCommand(sethome.homesCommand).setExecutor(sethome);
		getCommand(tpa.tpa).setExecutor(tpa);
		getCommand(tpa.tpaccept).setExecutor(tpa);
		getCommand(tpa.tpdeny).setExecutor(tpa);
		getCommand(skillCommand.skill).setExecutor(skillCommand);
		getCommand(skillCommand.skills).setExecutor(skillCommand);
		//Moderation
		getCommand(mod.ban).setExecutor(mod);
		getCommand(mod.unban).setExecutor(mod);
		getCommand(mod.mute).setExecutor(mod);
		getCommand(mod.unmute).setExecutor(mod);
		getCommand(mod.warn).setExecutor(mod);
		getCommand(mod.unwarn).setExecutor(mod);
		getCommand(mod.staffmode).setExecutor(mod);
		getServer().getPluginManager().registerEvents(mod, this);
		getServer().getPluginManager().registerEvents(new ModerationGUI(), this);
		//Glowing Drops
	    registerHealthBar();
	    Bukkit.getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new GlowingItemDrops(), this);
		loadConfig();
		//Custom Items FOOD
		getServer().getPluginManager().registerEvents(new TrickyFish(), this);
		getServer().getPluginManager().registerEvents(new SeasonedCarrot(), this);
		getServer().getPluginManager().registerEvents(new BunnyPotion(), this);
		getServer().getPluginManager().registerEvents(new ChocolateChippedCookie(), this);
		getServer().getPluginManager().registerEvents(new FaceSteak(), this);
		getServer().getPluginManager().registerEvents(new SkyFish(), this);
		getServer().getPluginManager().registerEvents(new LegendaryHero(), this);
		getServer().getPluginManager().registerEvents(new MenJerky(), this);
		getServer().getPluginManager().registerEvents(new PurifiedSpiderEye(), this);
		getServer().getPluginManager().registerEvents(new HolyPotato(), this);
		getServer().getPluginManager().registerEvents(new ButterscotchPie(), this);
		getServer().getPluginManager().registerEvents(new Fusgel(), this);
		getServer().getPluginManager().registerEvents(new ChangeFishDrops(), this);
		lootR.loadRewards();
		cList.lootChest();
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThe server will restart in 1 hour!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 216000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThe server will restart in 30 minutes!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 252000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThe server will restart in 15 minutes!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 270000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThe server will restart in 10 minutes!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 276000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThe server will restart in 5 minutes!"));
				shutdown = true;
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p != null) {
						p.kickPlayer(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThe server is going into shutdown, try joining back in 5 minutes."));
					}
				}
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 282000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThe server will restart in 1 minute!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 286800L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cRestarting!"));
				Bukkit.getServer().shutdown();
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 288000L);
	}
	public void onDisable() {
		for(Entity e : Bukkit.getWorld("DFWarzone-1").getEntities()) {
			if(e != null && !(e instanceof Player)) {
				e.remove();
			}
		}
		for(Entity e : Bukkit.getWorld("FactionWorld-1").getEntities()) {
			if(e != null && !(e instanceof Player)) {
				e.remove();
			}
		}
		File f5 =  new File("plugins/CustomEnchantments/playerskillsDF.yml");
		YamlConfiguration yml4 = YamlConfiguration.loadConfiguration(f5);
		try{
			yml4.load(f5);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		pc.saveProfiles(yml4, f5);
		for(Team t : scoreboard.getTeams()) {
			t.unregister();
		}
		File f1 =  new File("plugins/CustomEnchantments/factionsConfig.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f1);
		try{
			yml.load(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		yml.set("Factions.List", null);
		ArrayList<String> factionNameList = new ArrayList<String>(fac.getFactionNameList());
		HashMap<String, ArrayList<UUID>> factionMemberList = new HashMap<String, ArrayList<UUID>>(fac.getFactionMemberList());
		HashMap<UUID, Integer> factionRankedList = new HashMap<UUID, Integer>(fac.getRankedList());
		HashMap<String, ArrayList<String>> factionAllyList = new HashMap<String, ArrayList<String>>(fac.getAllyList());
		HashMap<String, ArrayList<Chunk>> chunkList = new HashMap<String, ArrayList<Chunk>>(fac.getChunkList());
		HashMap<String, Integer> chunkTotalList = new HashMap<String, Integer>(fac.getTotalChunkList());
		HashMap<String, Integer> fTopList = new HashMap<String, Integer>(fac.getFTop());
		HashMap<String, Location> fHomeList = new HashMap<String, Location>(fac.getFHomes());
		for(int i = 0; i < factionNameList.size(); i++) {
			yml.createSection("Factions.List." + factionNameList.get(i));
			for(int i1 = 0; i1 < factionMemberList.get(factionNameList.get(i)).size(); i1++) {
				yml.set("Factions.List." + factionNameList.get(i) + ".Members." + factionMemberList.get(factionNameList.get(i)).get(i1) + ".Rank", factionRankedList.get(factionMemberList.get(factionNameList.get(i)).get(i1)));
				yml.set("Factions.List." + factionNameList.get(i) + ".Members." + factionMemberList.get(factionNameList.get(i)).get(i1) + ".Name", Bukkit.getPlayer(factionMemberList.get(factionNameList.get(i)).get(i1)));
			}
			yml.set("Factions.List." + factionNameList.get(i) + ".Chunks", chunkTotalList.get(factionNameList.get(i)));
			ArrayList<String> list = new ArrayList<String>();
			for(Entry<String, ArrayList<Chunk>> entry : chunkList.entrySet()) {
				for(int i1 = 0; i1 < entry.getValue().size(); i1++) {
					list.add(entry.getValue().get(i1).toString());
				}
			}
			yml.set("Factions.List." + factionNameList.get(i) + ".Faction Home", fHomeList.get(factionNameList.get(i)));
			yml.set("Factions.List." + factionNameList.get(i) + ".Chunks List", list);
			yml.set("Factions.List." + factionNameList.get(i) + ".Faction Points", fTopList.get(factionNameList.get(i)));
			yml.set("Factions.List." + factionNameList.get(i) + ".Allies", factionAllyList.get(factionNameList.get(i)));
		}
		try{
			yml.save(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
		File f2 =  new File("plugins/CustomEnchantments/modConfig.yml");
		YamlConfiguration yml1 = YamlConfiguration.loadConfiguration(f2);
		try{
			yml1.load(f2);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		yml1.set("Mod.Punishments", null);
		int size = Bukkit.getOfflinePlayers().length;
		OfflinePlayer players[] = Bukkit.getOfflinePlayers();
		for(int i = 0; i < size; i++) {
			UUID uuid = players[i].getUniqueId();
			yml1.createSection("Mod.Punishments." + uuid);
			if(mod.getMutePermList().contains(uuid)) {
				yml1.set("Mod.Punishments." + uuid + ".Mute Data.Perm", true);
			}
			else {
				yml1.set("Mod.Punishments." + uuid + ".Mute Data.Perm", false);
			}
			if(mod.getMuteTimeList().get(uuid) != null) {
				yml1.set("Mod.Punishments." + uuid + ".Mute Data.Temp", mod.getMuteTimeList().get(uuid));
			}
			else {
				yml1.set("Mod.Punishments." + uuid + ".Mute Data.Temp", 0);
			}
			if(mod.getBanPermList().contains(uuid)) {
				yml1.set("Mod.Punishments." + uuid + ".Ban Data.Perm", true);
			}
			else {
				yml1.set("Mod.Punishments." + uuid + ".Ban Data.Perm", false);
			}
			if(mod.getBanTimeList().get(uuid) != null) {
				yml1.set("Mod.Punishments." + uuid + ".Ban Data.Temp", mod.getBanTimeList().get(uuid));
			}
			else {
				yml1.set("Mod.Punishments." + uuid + ".Ban Data.Temp", 0);
			}
			yml1.set("Mod.Punishments." + uuid + ".Mute Data.Reason", mod.getMuteReasonList().get(uuid));
			yml1.set("Mod.Punishments." + uuid + ".Ban Data.Reason", mod.getBanReasonList().get(uuid));
			yml1.set("Mod.Punishments." + uuid + ".Mute Data.Offends", mod.getMuteOffendsList().get(uuid));
			yml1.set("Mod.Punishments." + uuid + ".Ban Data.Offends", mod.getBanOffendsList().get(uuid));
			yml1.set("Mod.Punishments." + uuid + ".Warn Data.Offends", mod.getWarnList().get(uuid));
			yml1.set("Mod.Punishments." + uuid + ".Warn Data.Reason", mod.getWarnReasonList().get(uuid));
		}
		try{
			yml1.save(f2);
        }
        catch(IOException e){
            e.printStackTrace();
        }
		File f3 =  new File("plugins/CustomEnchantments/spawnerConfig.yml");
		YamlConfiguration yml2 = YamlConfiguration.loadConfiguration(f3);
		try{
			yml2.load(f3);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		command.saveSpawners(yml2, f3);
		File f4 =  new File("plugins/CustomEnchantments/cashConfig.yml");
		YamlConfiguration yml3 = YamlConfiguration.loadConfiguration(f4);
		try{
			yml3.load(f4);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		money.saveMoney(yml3, f4);
		
		File f6 =  new File("plugins/CustomEnchantments/setHomeConfig.yml");
		YamlConfiguration yml5 = YamlConfiguration.loadConfiguration(f6);
		try{
			yml5.load(f6);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		sethome.saveHomes(yml5, f6);
		File f7 =  new File("plugins/CustomEnchantments/lootConfig.yml");
		YamlConfiguration yml6 = YamlConfiguration.loadConfiguration(f7);
		loot.saveLootChests(yml6, f7);
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nThe plugin CustomEnchantments has been Disabled!\n\n");
	}
	public void loadConfigManager() {
		cfgm = new ConfigManager();
		cfgm.setUp();
		cfgm.savePlayers();
		cfgm.reloadPlayers();
	}
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(shutdown != true) {
			Player player = event.getPlayer();
			registerRank(player);
			registerNameTag(player);
		}
		else {
			event.getPlayer().kickPlayer(new ColorCodeTranslator().colorize("&cThe server is going into shutdown, try to join back in 5 minutes."));
		}
	}
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(scoreboard.getTeam(player.getName() + "1") != null) {
			Team t = scoreboard.getTeam(player.getName() + "1");
			t.unregister();
		}
		scores.remove(player.getUniqueId());
	}
	public void registerHealthBar() {
		if(scoreboard.getObjective("myHealth") != null) {
			scoreboard.getObjective("myHealth").unregister();
		}
		Objective o = scoreboard.registerNewObjective("myHealth", "health", "display");
		o.setDisplayName(ChatColor.RED + "");
		o.setDisplaySlot(DisplaySlot.BELOW_NAME);
	}
	public static HashMap<UUID, String> ranks = new HashMap<UUID, String>();
	public void registerRank(Player player) {
		if(player.hasPermission("owner")) {
			ranks.put(player.getUniqueId(), "&2Owner");
		}
		else if(player.hasPermission("manager")) {
			ranks.put(player.getUniqueId(), "&5Manager");
		}
		else if(player.hasPermission("headadmin")) {
			ranks.put(player.getUniqueId(), "&4Head Admin");
		}
		else if(player.hasPermission("admin")) {
			ranks.put(player.getUniqueId(), "&cAdmin");
		}
		else if(player.hasPermission("headmod")) {
			ranks.put(player.getUniqueId(), "&1Head Mod");
		}
		else if(player.hasPermission("moderator")) {
			ranks.put(player.getUniqueId(), "&9Mod");
		}
		else if(player.hasPermission("helper+")) {
			ranks.put(player.getUniqueId(), "&aHelper+");
		}
		else if(player.hasPermission("helper")) {
			ranks.put(player.getUniqueId(), "&aHelper");
		}
		else if(player.hasPermission("qaadmin")) {
			ranks.put(player.getUniqueId(), "&cQA Admin");
		}
		else if(player.hasPermission("qa")) {
			ranks.put(player.getUniqueId(), "&bQA");
		}
		else if(player.hasPermission("youtuber")) {
			ranks.put(player.getUniqueId(), "&dYoutuber");
		}
		else if(player.hasPermission("bronze")) {
			ranks.put(player.getUniqueId(), "&6Bronze");
		}
		else if(player.hasPermission("silver")) {
			ranks.put(player.getUniqueId(), "&7Silver");
		}
		else if(player.hasPermission("gold")) {
			ranks.put(player.getUniqueId(), "&eGold");
		}
		else if(player.hasPermission("platinum")) {
			ranks.put(player.getUniqueId(), "&3Platinum");
		}
		else if(player.hasPermission("diamond")) {
			ranks.put(player.getUniqueId(), "&bDiamond");
		}
		else if(player.hasPermission("emerald")) {
			ranks.put(player.getUniqueId(), "&aEmerald");
		}
		else {
			ranks.put(player.getUniqueId(), "&7User");
		}
	}
	public static HashMap<UUID, Scoreboard> scores = new HashMap<UUID, Scoreboard>();
	public void registerNameTag(Player player) {
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		World world = player.getWorld();
		RegionManager regions = container.get(BukkitAdapter.adapt(world));
		if(!scores.containsKey(player.getUniqueId())) {
			UUID uuid = player.getUniqueId();
			int level = 1;
			if(pc.getSkill(uuid, Skills.LEVEL) != 0) {
				level = pc.getSkill(uuid, Skills.LEVEL);
			}
			double cash = 1500.0;
			if(money.getMoneyList().get(player.getUniqueId()) != null) {
				cash = money.getMoneyList().get(player.getUniqueId());
			}
			ScoreboardManager manager = Bukkit.getScoreboardManager();
			Scoreboard board = manager.getNewScoreboard();
			org.bukkit.scoreboard.Scoreboard b = board;
			Objective o = null;
			if(b.getObjective(player.getName()) == null) {
				o = b.registerNewObjective(player.getName(), "Scoreboard", "myScoreboard");
			}
			else {
				o = b.getObjective(player.getName());
			}
			if(board.getTeam("GRAY") == null) {
				board.registerNewTeam("GRAY");
				board.getTeam("GRAY").setPrefix(ChatColor.GRAY + "");
				board.getTeam("GRAY").setColor(ChatColor.GRAY);
				board.registerNewTeam("GREEN");
				board.getTeam("GREEN").setPrefix(ChatColor.GREEN + "");
				board.getTeam("GREEN").setColor(ChatColor.GREEN);
				board.registerNewTeam("AQUA");
				board.getTeam("AQUA").setPrefix(ChatColor.AQUA + "");
				board.getTeam("AQUA").setColor(ChatColor.AQUA);
				board.registerNewTeam("RED");
				board.getTeam("RED").setPrefix(ChatColor.RED + "");
				board.getTeam("RED").setColor(ChatColor.RED);
				board.registerNewTeam("PURPLE");
				board.getTeam("PURPLE").setPrefix(ChatColor.DARK_PURPLE + "");
				board.getTeam("PURPLE").setColor(ChatColor.DARK_PURPLE);
				board.registerNewTeam("YELLOW");
				board.getTeam("YELLOW").setPrefix(ChatColor.YELLOW + "");
				board.getTeam("YELLOW").setColor(ChatColor.YELLOW);
			}
			o.setDisplayName(new ColorCodeTranslator().colorize("&2&lDungeonForge"));
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
			if(board.getTeam(player.getName() + "1") != null) {
				board.getTeam(player.getName() + "1").unregister();
			}
			board.registerNewTeam(player.getName() + "1");
			Team t = board.getTeam(player.getName() + "1");
			board.getTeam(player.getName() + "1").addPlayer(player);
			t.setPrefix(new ColorCodeTranslator().colorize("&7[&b" + level + "&7] "));
			t.setSuffix(new ColorCodeTranslator().colorize(" &6" + pc.getClass(player.getUniqueId())));
			player.setPlayerListName(new ColorCodeTranslator().colorize(t.getPrefix() + player.getName() + " " + t.getSuffix()));
			t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			//Faction Info
			Score blank1 = o.getScore("");
			Score blank2 = o.getScore(" ");
			Score blank3 = o.getScore("  ");
			Score facName = null;
			Score facTeritory = null;
			String facN = "";
			for(Entry<String, ArrayList<UUID>> entry : fac.getFactionMemberList().entrySet()) {
				if(entry.getValue().contains(player.getUniqueId())) {
					facN = entry.getKey();
				}
			}
			if(!facN.equals("")) {
				facName = o.getScore(new ColorCodeTranslator().colorize("&7Faction: &6" + facN));
			}
			else {
				facName = o.getScore(new ColorCodeTranslator().colorize("&7Faction: &7None"));
			}
			if(player.getWorld().getName().equals(Bukkit.getWorld("DFWarzone-1").getName())) {
				if(regions.hasRegion("spawn")) {
					if(regions.getRegion("spawn").contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) {
						facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &a&lSpawn"));
					}
					else {
						facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &6Wilderniss"));
					}
				}
				else if(regions.hasRegion("warzone")) {
					if(regions.getRegion("warzone").contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) {
						facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &c&lWarzone"));
					}
					else {
						facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &6Wilderniss"));
					}
				}
				else {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &6Wilderniss"));
				}
			}
			else if(!facN.equals("")) {
				String tempName = "";
				for(Entry<String, ArrayList<Chunk>> entry : fac.getChunkList().entrySet()) {
					if(!entry.getKey().equals(facN)) {
						if(entry.getValue().contains(player.getLocation().getChunk())) {
							tempName = entry.getKey();
						}
					}
				}
				if(fac.getChunkList().get(facN).contains(player.getLocation().getChunk())) {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &a&l" + facN));
				}
				else if(!tempName.equals("")) {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &c&l" + tempName));
				}
				else {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &6Wilderniss"));
				}
			}
			else {
				String tempName = "";
				for(Entry<String, ArrayList<Chunk>> entry : fac.getChunkList().entrySet()) {
					if(!entry.getKey().equals(facN)) {
						if(entry.getValue().contains(player.getLocation().getChunk())) {
							tempName = entry.getKey();
						}
					}
				}
				if(!tempName.equals("")) {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &c&l" + tempName));
				}
				else {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: Wilderniss"));
				}
			}
			Score money = o.getScore(new ColorCodeTranslator().colorize("&7Money: &a" + cash));
			Score level1 = o.getScore(new ColorCodeTranslator().colorize("&7Level: &b&l" + level));
			Score rank = o.getScore(new ColorCodeTranslator().colorize("&7Rank: " + ranks.get(player.getUniqueId())));
			Score adress = o.getScore(new ColorCodeTranslator().colorize("    &2&lplay.dungeonforge.net"));
			Set<String> entries;
	        entries = b.getEntries();
	        for(String entry : entries){
	            b.resetScores(entry);
	        }
	        blank1.setScore(9);
			facName.setScore(8);
			facTeritory.setScore(7);
			money.setScore(6);
			blank2.setScore(5);
			rank.setScore(4);
			level1.setScore(3);
			blank3.setScore(2);
			adress.setScore(1);
			player.setScoreboard(b);
			scores.put(player.getUniqueId(), b);
		}
		else {
			UUID uuid = player.getUniqueId();
			int level = 1;
			if(pc.getSkill(uuid, Skills.LEVEL) != 0) {
				level = pc.getSkill(uuid, Skills.LEVEL);
			}
			double cash = 1500.0;
			if(money.getMoneyList().get(player.getUniqueId()) != null) {
				cash = money.getMoneyList().get(player.getUniqueId());
			}
			Scoreboard board = scores.get(player.getUniqueId());
			org.bukkit.scoreboard.Scoreboard b = board;
			Objective o = null;
			if(b.getObjective(player.getName()) == null) {
				o = b.registerNewObjective(player.getName(), "Scoreboard", "myScoreboard");
			}
			else {
				o = b.getObjective(player.getName());
			}
			if(board.getTeam("GRAY") == null) {
				board.registerNewTeam("GRAY");
				board.getTeam("GRAY").setPrefix(ChatColor.GRAY + "");
				board.getTeam("GRAY").setColor(ChatColor.GRAY);
				board.registerNewTeam("GREEN");
				board.getTeam("GREEN").setPrefix(ChatColor.GREEN + "");
				board.getTeam("GREEN").setColor(ChatColor.GREEN);
				board.registerNewTeam("AQUA");
				board.getTeam("AQUA").setPrefix(ChatColor.AQUA + "");
				board.getTeam("AQUA").setColor(ChatColor.AQUA);
				board.registerNewTeam("RED");
				board.getTeam("RED").setPrefix(ChatColor.RED + "");
				board.getTeam("RED").setColor(ChatColor.RED);
				board.registerNewTeam("PURPLE");
				board.getTeam("PURPLE").setPrefix(ChatColor.DARK_PURPLE + "");
				board.getTeam("PURPLE").setColor(ChatColor.DARK_PURPLE);
				board.registerNewTeam("YELLOW");
				board.getTeam("YELLOW").setPrefix(ChatColor.YELLOW + "");
				board.getTeam("YELLOW").setColor(ChatColor.YELLOW);
			}
			o.setDisplayName(new ColorCodeTranslator().colorize("&2&lDungeonForge"));
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
			if(board.getTeam(player.getName() + "1") != null) {
				board.getTeam(player.getName() + "1").unregister();
			}
			board.registerNewTeam(player.getName() + "1");
			Team t = board.getTeam(player.getName() + "1");
			board.getTeam(player.getName() + "1").addPlayer(player);
			t.setPrefix(new ColorCodeTranslator().colorize("&7[&b" + level + "&7] "));
			t.setSuffix(new ColorCodeTranslator().colorize(" &6" + pc.getClass(player.getUniqueId())));
			player.setPlayerListName(new ColorCodeTranslator().colorize(t.getPrefix() + player.getName() + " " + t.getSuffix()));
			t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
			
			//Faction Info
			Score blank1 = o.getScore("");
			Score blank2 = o.getScore(" ");
			Score blank3 = o.getScore("  ");
			Score facName = null;
			Score facTeritory = null;
			String facN = "";
			for(Entry<String, ArrayList<UUID>> entry : fac.getFactionMemberList().entrySet()) {
				if(entry.getValue().contains(player.getUniqueId())) {
					facN = entry.getKey();
				}
			}
			if(!facN.equals("")) {
				facName = o.getScore(new ColorCodeTranslator().colorize("&7Faction: &6" + facN));
			}
			else {
				facName = o.getScore(new ColorCodeTranslator().colorize("&7Faction: &7None"));
			}
			if(player.getWorld().getName().equals(Bukkit.getWorld("DFWarzone-1").getName())) {
				if(regions.hasRegion("spawn")) {
					if(regions.getRegion("spawn").contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) {
						facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &a&lSpawn"));
					}
					else {
						facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &6Wilderniss"));
					}
				}
				else if(regions.hasRegion("warzone")) {
					if(regions.getRegion("warzone").contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) {
						facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &c&lWarzone"));
					}
					else {
						facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &6Wilderniss"));
					}
				}
				else {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &6Wilderniss"));
				}
			}
			else if(!facN.equals("")) {
				String tempName = "";
				for(Entry<String, ArrayList<Chunk>> entry : fac.getChunkList().entrySet()) {
					if(!entry.getKey().equals(facN)) {
						if(entry.getValue().contains(player.getLocation().getChunk())) {
							tempName = entry.getKey();
						}
					}
				}
				if(fac.getChunkList().get(facN).contains(player.getLocation().getChunk())) {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &a&l" + facN));
				}
				else if(!tempName.equals("")) {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &c&l" + tempName));
				}
				else {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &6Wilderniss"));
				}
			}
			else {
				String tempName = "";
				for(Entry<String, ArrayList<Chunk>> entry : fac.getChunkList().entrySet()) {
					if(!entry.getKey().equals(facN)) {
						if(entry.getValue().contains(player.getLocation().getChunk())) {
							tempName = entry.getKey();
						}
					}
				}
				if(!tempName.equals("")) {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: &c&l" + tempName));
				}
				else {
					facTeritory = o.getScore(new ColorCodeTranslator().colorize("&7Faction Teritory: Wilderniss"));
				}
			}
			Score money = o.getScore(new ColorCodeTranslator().colorize("&7Money: &a" + cash));
			Score level1 = o.getScore(new ColorCodeTranslator().colorize("&7Level: &b&l" + level));
			Score rank = o.getScore(new ColorCodeTranslator().colorize("&7Rank: " + ranks.get(player.getUniqueId())));
			Score adress = o.getScore(new ColorCodeTranslator().colorize("    &2&lplay.dungeonforge.net"));
			Set<String> entries;
	        entries = b.getEntries();
	        for(String entry : entries){
	            b.resetScores(entry);
	        }
	        blank1.setScore(9);
			facName.setScore(8);
			facTeritory.setScore(7);
			money.setScore(6);
			blank2.setScore(5);
			rank.setScore(4);
			level1.setScore(3);
			blank3.setScore(2);
			adress.setScore(1);
			player.setScoreboard(b);
		}
	}
	public ItemStack createHead(String paramString)
    {
        ItemStack localItemStack = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta localSkullMeta = (SkullMeta)localItemStack.getItemMeta();

        GameProfile localGameProfile = new GameProfile(UUID.randomUUID(), null);
        byte[] arrayOfByte = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { paramString }).getBytes());
        localGameProfile.getProperties().put("textures", new Property("textures", new String(arrayOfByte)));
        Field localField = null;
        try
        {
            localField = localSkullMeta.getClass().getDeclaredField("profile");
            localField.setAccessible(true);
            localField.set(localSkullMeta, localGameProfile);
        }
        catch (NoSuchFieldException|IllegalArgumentException|IllegalAccessException localNoSuchFieldException)
        {
            System.out.println("error: " + localNoSuchFieldException.getMessage());
        }
        localSkullMeta.setDisplayName("head");
        localItemStack.setItemMeta(localSkullMeta);

        return localItemStack;
    }
	public static CustomEnchantments getInstance() {
		return instance;
	}
	public boolean getShutdownState() {
		return CustomEnchantments.shutdown;
	}
}
