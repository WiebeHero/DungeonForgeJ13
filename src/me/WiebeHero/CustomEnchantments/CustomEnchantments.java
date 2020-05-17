package me.WiebeHero.CustomEnchantments;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtinjector.NBTInjector;
import javafx.util.Pair;
import me.WiebeHero.APIs.ParticleAPI;
import me.WiebeHero.ArmoryPackage.DFArmorUpgrade;
import me.WiebeHero.ArmoryPackage.DFShieldUpgrade;
import me.WiebeHero.ArmoryPackage.DFWeaponUpgrade;
import me.WiebeHero.ArmoryPackage.LevelRequired;
import me.WiebeHero.ArmoryPackage.XPAddWeapons;
import me.WiebeHero.AuctionHouse.AHCommand;
import me.WiebeHero.AuctionHouse.AHEvents;
import me.WiebeHero.AuctionHouse.AHInventory;
import me.WiebeHero.AuctionHouse.AHManager;
import me.WiebeHero.CapturePoints.CapturePointManager;
import me.WiebeHero.CapturePoints.ExtraExperience;
import me.WiebeHero.Chat.ChatEvents;
import me.WiebeHero.Chat.MSGCommand;
import me.WiebeHero.Chat.MSGEvents;
import me.WiebeHero.Chat.MSGManager;
import me.WiebeHero.Consumables.Consumable;
import me.WiebeHero.Consumables.ConsumableHandler;
import me.WiebeHero.Consumables.CustomDurability;
import me.WiebeHero.CraftRecipes.UnblockBrewing;
import me.WiebeHero.CustomClasses.Methods;
import me.WiebeHero.CustomHitDelay.HitDelay;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.CustomMethods.MethodLuck;
import me.WiebeHero.CustomMethods.MethodMulti;
import me.WiebeHero.CustomMethods.PotionM;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.DFPlayerPackage.DFPlayerRegister;
import me.WiebeHero.DFPlayerPackage.EffectSkills;
import me.WiebeHero.DFPlayerPackage.Enums;
import me.WiebeHero.DFPlayerPackage.Enums.Classes;
import me.WiebeHero.DFShops.DFShop;
import me.WiebeHero.DFShops.PayCommand;
import me.WiebeHero.DungeonInstances.DungeonMaxima;
import me.WiebeHero.DungeonInstances.DungeonParty;
import me.WiebeHero.DungeonInstances.DungeonPartyCommand;
import me.WiebeHero.EnchantmentAPI.CommandFile;
import me.WiebeHero.EnchantmentAPI.Enchantment;
import me.WiebeHero.EnchantmentAPI.EnchantmentCondition.Condition;
import me.WiebeHero.EnchantmentAPI.EnchantmentGuideInventory;
import me.WiebeHero.EnchantmentAPI.EnchantmentHandler;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.Factions.DFFactionPlayerManager;
import me.WiebeHero.Factions.DFFactions;
import me.WiebeHero.Factions.FactionInventory;
import me.WiebeHero.Factions.FactionsHandler;
import me.WiebeHero.FishingLoot.ChangeFishDrops;
import me.WiebeHero.LootChest.ChestEvents;
import me.WiebeHero.LootChest.LootChestManager;
import me.WiebeHero.LootChest.LootRewards;
import me.WiebeHero.LootChest.MoneyNotes;
import me.WiebeHero.Moderation.ModerationEvents;
import me.WiebeHero.Moderation.ModerationGUI;
import me.WiebeHero.Moderation.Punish;
import me.WiebeHero.Moderation.PunishManager;
import me.WiebeHero.Moderation.StaffManager;
import me.WiebeHero.MoreStuff.AFKSystem;
import me.WiebeHero.MoreStuff.CancelJoinLeaveAdvancementMessages;
import me.WiebeHero.MoreStuff.CancelLootSteal;
import me.WiebeHero.MoreStuff.CombatTag;
import me.WiebeHero.MoreStuff.DisableThings;
import me.WiebeHero.MoreStuff.Disparitys;
import me.WiebeHero.MoreStuff.EnderPearlCooldown;
import me.WiebeHero.MoreStuff.MOTDSetting;
import me.WiebeHero.MoreStuff.Portals;
import me.WiebeHero.MoreStuff.PreventIllegalItems;
import me.WiebeHero.MoreStuff.PreventPhantom;
import me.WiebeHero.MoreStuff.RestrictInteractionWithBlocks;
import me.WiebeHero.MoreStuff.RestrictItemInteraction;
import me.WiebeHero.MoreStuff.SetHomeSystem;
import me.WiebeHero.MoreStuff.SpawnCommand;
import me.WiebeHero.MoreStuff.SwordSwingProgress;
import me.WiebeHero.MoreStuff.TNTExplodeCovered;
import me.WiebeHero.MoreStuff.TPACommand;
import me.WiebeHero.Novis.ItemCommand;
import me.WiebeHero.Novis.NovisEnchantmentGetting;
import me.WiebeHero.Novis.NovisInventory;
import me.WiebeHero.Novis.NovisRewards;
import me.WiebeHero.RankedPlayerPackage.KitCommand;
import me.WiebeHero.RankedPlayerPackage.KitListener;
import me.WiebeHero.RankedPlayerPackage.KitMenu;
import me.WiebeHero.RankedPlayerPackage.RankEnum;
import me.WiebeHero.RankedPlayerPackage.RankedManager;
import me.WiebeHero.RankedPlayerPackage.RankedPlayerListener;
import me.WiebeHero.Scoreboard.DFScoreboard;
import me.WiebeHero.Scoreboard.WGMethods;
import me.WiebeHero.SeasonalEvents.ChristmasInventoryEvents;
import me.WiebeHero.Skills.ActivateAbility;
import me.WiebeHero.Skills.ClassMenu;
import me.WiebeHero.Skills.ClassMenuSelection;
import me.WiebeHero.Skills.SkillCommand;
import me.WiebeHero.Skills.SkillJoin;
import me.WiebeHero.Skills.SkillMenu;
import me.WiebeHero.Skills.SkillMenuInteract;
import me.WiebeHero.Skills.XPEarningMobs;
import me.WiebeHero.Spawners.DFMobHealth;
import me.WiebeHero.Spawners.DFSpawnerManager;
import me.WiebeHero.Spawners.DeathOfMob;
import me.WiebeHero.XpTrader.XPAddPlayers;
import me.WiebeHero.XpTrader.XPTraderMenu;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.GroupManager;
import net.luckperms.api.model.user.User;

public class CustomEnchantments extends JavaPlugin implements Listener{
	private ArrayList<Integer> availableSlots = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35));
	public HashMap<UUID, DungeonParty> dfDungeonParty = new HashMap<UUID, DungeonParty>();
	public ArrayList<DungeonMaxima> dfDungeonList = new ArrayList<DungeonMaxima>();
	private DFPlayerManager dfManager = new DFPlayerManager();
	private static CustomEnchantments instance;
	private DungeonPartyCommand party = new DungeonPartyCommand();
	private Portals p = new Portals();
	private SpawnCommand spa = new SpawnCommand();
	private TPACommand tpa = new TPACommand();
	private LootRewards lootR = new LootRewards();
	private ConfigManager cfgm;
	private PunishManager punishManager = new PunishManager();
	private StaffManager staffManager = new StaffManager();
	private LootChestManager lootChestManager = new LootChestManager();
	private MSGManager msgManager = new MSGManager();
	private RankedManager rankedManager = new RankedManager();
	// Faction classes
	private DFFactionPlayerManager facPlayerManager = new DFFactionPlayerManager();
	private DFFactionManager facManager = new DFFactionManager(facPlayerManager);
	// Classes that only need to be called once and can be given through
	private Methods method = new Methods();
	private Disparitys disp = new Disparitys(dfManager);
	private PotionM potionM = new PotionM();
	private ParticleAPI pApi = new ParticleAPI();
	private SwordSwingProgress sword = new SwordSwingProgress();
	private MethodLuck luck = new MethodLuck();
	private WGMethods wg = new WGMethods();
	private ItemStackBuilder builder = new ItemStackBuilder();
	private ClassMenu classMenu = new ClassMenu(dfManager);
	private SkillMenu skill = new SkillMenu(dfManager, builder, classMenu);
	private SkillCommand skillCommand = new SkillCommand(skill, dfManager, rankedManager);
	private DFScoreboard score = new DFScoreboard(dfManager, facManager, facPlayerManager, rankedManager, wg);
	private FactionInventory facInv = new FactionInventory(builder);
	private DFFactions fac = new DFFactions(facManager, facPlayerManager, score, dfManager, wg, facInv);
	private AHManager ahManager = new AHManager(availableSlots);
	private AHInventory ahInv = new AHInventory(ahManager, builder);
	private Enchantment enchant = new Enchantment(dfManager, facManager, potionM, pApi, facPlayerManager, builder, wg);
	private Consumable con = new Consumable(dfManager, facManager, potionM, builder);
	private PayCommand pay = new PayCommand(dfManager);
	private DFSpawnerManager spawnerManager = new DFSpawnerManager(dfManager, disp);
	private ModerationGUI gui = new ModerationGUI(dfManager, builder, punishManager);
	private MethodMulti multi = new MethodMulti();
	private NovisEnchantmentGetting nEnchant = new NovisEnchantmentGetting();
	private RankEnum rEnum = new RankEnum(builder, con);
	private KitMenu menu = new KitMenu(builder, rankedManager, rEnum);
	private KitCommand kitCommand = new KitCommand(rankedManager, rEnum, menu);
	private EnchantmentGuideInventory enchantmentGuideInv;
	private SetHomeSystem sethome = new SetHomeSystem(rankedManager);
	private RankedPlayerListener rListener = new RankedPlayerListener(rankedManager, luck);
	private CapturePointManager cpManager = new CapturePointManager(facManager, facPlayerManager);
	// General Variables
	public static boolean hardSave = false;
	public static boolean shutdown = false;
	public static boolean maintenance = false;
	public static boolean load = true;
	@Override
	public void onEnable() {
		instance = this;
		//Enable Plugin Message
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nThe plugin CustomEnchantments has been enabled!\n\n");
		
		NovisRewards rewards = new NovisRewards(nEnchant);
		ItemCommand itemCommand = new ItemCommand(rewards, rankedManager, nEnchant);
		
		enchantmentGuideInv = new EnchantmentGuideInventory(enchant, builder);
		AHCommand ahCommand = new AHCommand(ahManager, rankedManager, ahInv);
		MSGCommand msgCommand = new MSGCommand(msgManager, null, rankedManager);
		loadConfigManager();
		File f1 =  new File("plugins/CustomEnchantments/GeneralConfig.yml");
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
		rankedManager.loadKitCooldowns();
		facPlayerManager.loadPlayers();
		facManager.loadFactions();
		facManager.activeEnergyTimer();
		ahManager.loadAuctionHouse();
		dfManager.loadPlayers();
		maintenance = yml.getBoolean("General.Values.Maintenance");
		ahManager.start();
		cpManager.loadCapturePoints();
		cpManager.activateCapturePoints();
		new BukkitRunnable() {
			public void run() {
				try {
					for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
						rListener.loadRankedPlayers(p);
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 3L);
		Enums en = new Enums(dfManager, facManager);
		for(Classes e : Classes.values()) {
			Bukkit.getPluginManager().registerEvents(e, CustomEnchantments.getInstance());
		}
		//Config Manager
		ModerationEvents mod = new ModerationEvents(facManager, dfManager, rankedManager, punishManager, staffManager, spawnerManager, lootChestManager, gui, multi, luck, method, score, classMenu, msgManager, cpManager, facPlayerManager);
		//Custom Weapons
		getServer().getPluginManager().registerEvents(en, this);
		getServer().getPluginManager().registerEvents(new ActivateAbility(dfManager, facManager), this);
		getServer().getPluginManager().registerEvents(new DFWeaponUpgrade(dfManager, nEnchant), this);
		//Custom Armor
		getServer().getPluginManager().registerEvents(new DFArmorUpgrade(dfManager, nEnchant), this);
		getServer().getPluginManager().registerEvents(new DFShieldUpgrade(dfManager, nEnchant), this);
		//Custom Shields
		//Skills
		getServer().getPluginManager().registerEvents(new SkillMenuInteract(dfManager, skill, classMenu, score), this);
		getServer().getPluginManager().registerEvents(new SkillJoin(dfManager, classMenu, rEnum), this);
		getServer().getPluginManager().registerEvents(new ClassMenuSelection(dfManager, classMenu), this);
		getServer().getPluginManager().registerEvents(new ExtraExperience(facPlayerManager, cpManager), this);
		getServer().getPluginManager().registerEvents(new XPEarningMobs(dfManager, score), this);
		getServer().getPluginManager().registerEvents(new EffectSkills(dfManager, sword), this);
		getServer().getPluginManager().registerEvents(new KitListener(menu, rankedManager), this);
		//Spawners
		getServer().getPluginManager().registerEvents(new DeathOfMob(), this);
		getServer().getPluginManager().registerEvents(new CustomDurability(), this);
		getServer().getPluginManager().registerEvents(new PreventPhantom(), this);
		//Novis
		getServer().getPluginManager().registerEvents(new NovisInventory(rewards), this);
		//Loot Chest
		getServer().getPluginManager().registerEvents(new ChestEvents(), this);
		getServer().getPluginManager().registerEvents(new MoneyNotes(dfManager, score), this);
		//Brewing Recipes
		getServer().getPluginManager().registerEvents(new UnblockBrewing(), this);
		//AFKSystem
		getServer().getPluginManager().registerEvents(new AFKSystem(), this);
		//Scoreboard
		getServer().getPluginManager().registerEvents(score, this);
		//XP Trader
		getServer().getPluginManager().registerEvents(new XPTraderMenu(), this);
		getServer().getPluginManager().registerEvents(new XPAddWeapons(nEnchant, dfManager), this);
		getServer().getPluginManager().registerEvents(new XPAddPlayers(dfManager, score), this);
		//lootSteal
		getServer().getPluginManager().registerEvents(new CancelLootSteal(), this);
		//Factions
		getServer().getPluginManager().registerEvents(new FactionsHandler(facManager, facPlayerManager, facInv), this);
		getServer().getPluginManager().registerEvents(fac, this);
		//Stuff
		getServer().getPluginManager().registerEvents(sethome, this);
		getServer().getPluginManager().registerEvents(new DFPlayerRegister(dfManager), this);
		getServer().getPluginManager().registerEvents(new MSGEvents(msgManager), this);
		getServer().getPluginManager().registerEvents(new AHEvents(ahInv, ahManager, dfManager), this);
		getServer().getPluginManager().registerEvents(new ConsumableHandler(dfManager, con), this);
		getServer().getPluginManager().registerEvents(rListener, this);
		getServer().getPluginManager().registerEvents(new DFMobHealth(dfManager), this);
		//Seasonal Events
		getServer().getPluginManager().registerEvents(new ChristmasInventoryEvents(), this);
		punishManager.loadPunishList();
		new BukkitRunnable() {
			public void run() {
				for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
					if(!punishManager.contains(p.getUniqueId())) {
						punishManager.add(p.getUniqueId(), new Punish(p.getUniqueId()));
					}
				}
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 3L);
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
		//TNT
		getServer().getPluginManager().registerEvents(new TNTExplodeCovered(), this);
		getServer().getPluginManager().registerEvents(new EnchantmentHandler(enchant, enchantmentGuideInv, wg), this);
		for(Entry<String, Pair<Condition, CommandFile>> entry : enchant.getMeleeEnchantments().entrySet()) {
			Bukkit.getPluginManager().registerEvents(entry.getValue().getValue(), CustomEnchantments.getInstance());
		}
		for(Entry<String, Pair<Condition, CommandFile>> entry : enchant.getBowEnchantments().entrySet()) {
			Bukkit.getPluginManager().registerEvents(entry.getValue().getValue(), CustomEnchantments.getInstance());
		}
		for(Entry<String, Pair<Condition, CommandFile>> entry : enchant.getArmorEnchantments().entrySet()) {
			Bukkit.getPluginManager().registerEvents(entry.getValue().getValue(), CustomEnchantments.getInstance());
		}
		for(Entry<String, Pair<Condition, CommandFile>> entry : enchant.getShieldEnchantments().entrySet()) {
			Bukkit.getPluginManager().registerEvents(entry.getValue().getValue(), CustomEnchantments.getInstance());
		}
		con.loadConsumables();
		con.registerRecipes();
		for(Entry<String, Pair<me.WiebeHero.Consumables.ConsumableCondition.Condition, me.WiebeHero.Consumables.CommandFile>> entry : con.getConsumables().entrySet()) {
			Bukkit.getPluginManager().registerEvents(entry.getValue().getValue(), CustomEnchantments.getInstance());
		}
		//NeededStuff
		getServer().getPluginManager().registerEvents(tpa, this);
		getServer().getPluginManager().registerEvents(new CombatTag(), this);
		getServer().getPluginManager().registerEvents(new LevelRequired(dfManager), this);
		getServer().getPluginManager().registerEvents(new ChatEvents(dfManager, msgManager, facManager, facPlayerManager, rankedManager), this);
		getServer().getPluginManager().registerEvents(new Portals(), this);
		getServer().getPluginManager().registerEvents(new SwordSwingProgress(), this);
		getServer().getPluginManager().registerEvents(new PreventIllegalItems(), this);
		getServer().getPluginManager().registerEvents(new Disparitys(dfManager), this);
		getServer().getPluginManager().registerEvents(new RestrictInteractionWithBlocks(), this);
		getServer().getPluginManager().registerEvents(new EnderPearlCooldown(), this);
		getServer().getPluginManager().registerEvents(new MOTDSetting(), this);
		getServer().getPluginManager().registerEvents(new DisableThings(), this);	
		getServer().getPluginManager().registerEvents(new RestrictItemInteraction(), this);
		getServer().getPluginManager().registerEvents(new CancelJoinLeaveAdvancementMessages(), this);
		//Shop
		getServer().getPluginManager().registerEvents(new DFShop(dfManager, score), this);
		//HitDelay
		getServer().getPluginManager().registerEvents(new HitDelay(), this);
		//Commands
		getCommand(fac.faction).setExecutor(fac);
		getCommand(p.rtp).setExecutor(p);
		getCommand(spa.spawn).setExecutor(spa);
		getCommand(pay.command).setExecutor(pay);
		getCommand(pay.balance).setExecutor(pay);
		getCommand(pay.money).setExecutor(pay);
		getCommand(sethome.command).setExecutor(sethome);
		getCommand(sethome.command1).setExecutor(sethome);
		getCommand(sethome.homeCommand).setExecutor(sethome);
		getCommand(sethome.homesCommand).setExecutor(sethome);
		getCommand(tpa.tpa).setExecutor(tpa);
		getCommand(tpa.tpaccept).setExecutor(tpa);
		getCommand(tpa.tpahere).setExecutor(tpa);
		getCommand(tpa.tpatoggle).setExecutor(tpa);
		getCommand(tpa.tpdeny).setExecutor(tpa);
		getCommand(tpa.tpdeny).setExecutor(tpa);
		getCommand(msgCommand.msg).setExecutor(msgCommand);
		getCommand(msgCommand.ignore).setExecutor(msgCommand);
		getCommand(skillCommand.skill).setExecutor(skillCommand);
		getCommand(skillCommand.skills).setExecutor(skillCommand);
		getCommand(skillCommand.level).setExecutor(skillCommand);
		getCommand(ahCommand.ah).setExecutor(ahCommand);
		//Moderation
		getCommand(mod.rank).setExecutor(mod);
		getCommand(mod.punish).setExecutor(mod);
		getCommand(mod.history).setExecutor(mod);
		getCommand(mod.ban).setExecutor(mod);
		getCommand(mod.unban).setExecutor(mod);
		getCommand(mod.mute).setExecutor(mod);
		getCommand(mod.unmute).setExecutor(mod);
		getCommand(mod.staffmode).setExecutor(mod);
		getCommand(mod.protocol).setExecutor(mod);
		getCommand(mod.checkstaff).setExecutor(mod);
		getCommand(mod.clearchat).setExecutor(mod);
		getCommand(mod.checkban).setExecutor(mod);
		getCommand(mod.checkmute).setExecutor(mod);
		getCommand(mod.stafflist).setExecutor(mod);
		getCommand(mod.staffchat).setExecutor(mod);
		getCommand(mod.freeze).setExecutor(mod);
		getCommand(mod.unfreeze).setExecutor(mod);
		getCommand(mod.fly).setExecutor(mod);
		getCommand(mod.feed).setExecutor(mod);
		getCommand(mod.heal).setExecutor(mod);
		getCommand(mod.time).setExecutor(mod);
		getCommand(mod.weather).setExecutor(mod);
		getCommand(mod.checkmode).setExecutor(mod);
		getCommand(mod.clearinventory).setExecutor(mod);
		getCommand(mod.tp).setExecutor(mod);
		getCommand(mod.tptp).setExecutor(mod);
		getCommand(mod.tphere).setExecutor(mod);
		getCommand(mod.af).setExecutor(mod);
		//Dungeon Parties
		getCommand(party.comParty).setExecutor(party);
		//
		getCommand(kitCommand.kit).setExecutor(kitCommand);
		getCommand(kitCommand.kits).setExecutor(kitCommand);
		getCommand(itemCommand.itemCmd).setExecutor(itemCommand);
		getServer().getPluginManager().registerEvents(mod, this);
		getServer().getPluginManager().registerEvents(new ModerationGUI(dfManager, builder, punishManager), this);
		//Glowing Drops
	    Bukkit.getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new GlowingItemDrops(), this);
		loadConfig();
		//Custom Items FOOD
		getServer().getPluginManager().registerEvents(new ChangeFishDrops(), this);
		lootR.loadRewards();
		new BukkitRunnable() {
			public void run() {
				for(Entity e : Bukkit.getWorld("FactionWorld-1").getEntities()) {
					if(e != null) {
						if(e instanceof LivingEntity) {
							LivingEntity ent = (LivingEntity) e;
							e = NBTInjector.patchEntity(e);
							NBTCompound comp = NBTInjector.getNbtData(e);
							comp.setString("SpawnerUUID", "");
							comp.setInteger("Level", 1);
							comp.setInteger("Tier", 0);
							dfManager.addEntity(e.getUniqueId(), new DFPlayer(ent));
						}
					}
				}
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 5L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server will restart in 1 hour!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 216000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server will restart in 30 minutes!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 252000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server will restart in 15 minutes!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 270000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server will restart in 10 minutes!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 276000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server will restart in 5 minutes!"));
				shutdown = true;
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p != null) {
						p.kickPlayer(new CCT().colorize("&2&l[DungeonForge]: &cThe server is going into shutdown, try joining back in 5 minutes."));
					}
				}
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 282000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server will restart in 1 minute!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 286800L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cRestarting!"));
				Bukkit.getServer().shutdown();
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 288000L);
		new BukkitRunnable() {
			public void run() {
				load = false;
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 100L);
		lootChestManager.loadLootChests();
		spawnerManager.loadSpawners();
		new BukkitRunnable() {
			public void run() {
				NBTInjector.inject();
				lootChestManager.startLootSpawning();
				spawnerManager.startSpawning();
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 1L);
		new BukkitRunnable() {
			public void run() {
				mod.priorityQueue();
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 2L);
	}
	public void onDisable() {
		File f =  new File("plugins/CustomEnchantments/GeneralConfig.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		yml.set("General.Values.Maintenance", maintenance);
		try{
			yml.save(f);
        }
        catch(IOException e){
            e.printStackTrace();
        }
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
		try {
			dfManager.savePlayers();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			facManager.saveFactions();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			ahManager.saveAuctionHouse();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			lootChestManager.saveLootChests();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			spawnerManager.saveSpawners();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			sethome.saveHomes();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			punishManager.savePunishList();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			rankedManager.saveKitCooldowns();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			cpManager.saveCapturePoints();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nThe plugin CustomEnchantments has been Disabled!\n\n");
	}
	public void loadConfigManager() {
		cfgm = new ConfigManager();
		cfgm.setUpConfiguration("playerskillsDF.yml", "Skills.Players");
		cfgm.setUpConfiguration("spawnerConfig.yml", "Spawners.UUID");
		cfgm.setUpConfiguration("lootConfig.yml", "Loot.Chests");
		cfgm.setUpConfiguration("factionsConfig.yml", "Factions.List");
		cfgm.setUpConfiguration("setHomeConfig.yml", "Homes");
		cfgm.setUpConfiguration("shopConfig.yml", "Shop");
		cfgm.setUpConfiguration("modConfig.yml", "Mod.Punishments");
		cfgm.setUpConfiguration("dungeonConfig.yml", "Dungeons.Instances");
		cfgm.setUpConfiguration("GeneralConfig.yml", "General.Values");
		cfgm.setUpConfiguration("AuctionConfig.yml", "AuctionHouse.Items");
		cfgm.setUpConfiguration("KitCooldowns.yml", "Kits.Cooldowns");
		cfgm.setUpConfiguration("CapturePoints.yml", "Capture Points");
	}
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	public void registerHealthBar(Scoreboard board) {
		if(board.getObjective("health") != null) {
			board.getObjective("health").unregister();
		}
		Objective o = board.registerNewObjective("health", "health", "display");
		o.setDisplayName(new CCT().colorize("&c❤"));
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
	public ItemStack createHead(OfflinePlayer p)
    {
        ItemStack localItemStack = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta localSkullMeta = (SkullMeta)localItemStack.getItemMeta();
        localSkullMeta.setOwningPlayer(p);
        localItemStack.setItemMeta(localSkullMeta);

        return localItemStack;
    }
	public void registerPlayersToDatabase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dungeonforge","root","");
            CustomEnchantments.getInstance().getServer().getConsoleSender().sendMessage(new CCT().colorize("&aConnection established!"));
            ArrayList<OfflinePlayer> playerList = new ArrayList<OfflinePlayer>(Arrays.asList(Bukkit.getOfflinePlayers()));
            LuckPerms api = LuckPermsProvider.get();
            for(int i = 0; i < playerList.size(); i++) {
            	String uuidString = playerList.get(i).getUniqueId().toString();
            	String playerName = playerList.get(i).getName();
            	GroupManager manager = api.getGroupManager();
            	User user = api.getUserManager().getUser(playerList.get(i).getUniqueId());
            	String groupName = "None";
            	if(luck.containsParrent(user, "bronze")) {
            		groupName = "bronze";
            	}
            	else if(luck.containsParrent(user, "silver")) {
            		groupName = "silver";
            	}
            	else if(luck.containsParrent(user, "gold")) {
            		groupName = "gold";
            	}
            	else if(luck.containsParrent(user, "platinum")) {
            		groupName = "platinum";
            	}
            	else if(luck.containsParrent(user, "diamond")) {
            		groupName = "diamond";
            	}
            	else if(luck.containsParrent(user, "emerald")) {
            		groupName = "emerald";
            	}
            	Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT UUID, PlayerName, Rank FROM ranktable WHERE UUID = '" + uuidString + "' AND PlayerName = '" + playerName + "'");
                if(rs.isBeforeFirst()) {
	                while(rs.next()) {
		                if(rs.getString(1).equals(uuidString) && !rs.getString(2).equals(playerName)) {
		                	if(!rs.getString(3).equals(groupName)) {
		                		groupName = rs.getString(3);
		                		if(groupName.equals("bronze")) {
		                			user.setPrimaryGroup("bronze");
		                		}
		                		else if(groupName.equals("silver")) {
		                			user.setPrimaryGroup("silver");
		                		}
		                		else if(groupName.equals("gold")) {
		                			user.setPrimaryGroup("gold");
		                		}
		                		else if(groupName.equals("platinum")) {
		                			user.setPrimaryGroup("platinum");
		                		}
		                		else if(groupName.equals("diamond")) {
		                			user.setPrimaryGroup("diamond");
		                		}
		                		else if(groupName.equals("emerald")) {
		                			user.setPrimaryGroup("emerald");
		                		}
		                	}
		                	Statement stmtNew = con.createStatement();
		                    int rsNew = stmtNew.executeUpdate("INSERT INTO ranktable (UUID, PlayerName, Rank) VALUES ('" + uuidString + "', '" + playerName + "', '" + groupName + "')");
		                }
		                break;
	                }
                }
                else {
                	Statement stmtNew = con.createStatement();
                    int rsNew = stmtNew.executeUpdate("INSERT INTO ranktable (UUID, PlayerName, Rank) VALUES ('" + uuidString + "', '" + playerName + "', 'None')");
                }
            }
            CustomEnchantments.getInstance().getServer().getConsoleSender().sendMessage(new CCT().colorize("&aConnection closing down!"));
            con.close();
		} catch(Exception e){
			CustomEnchantments.getInstance().getServer().getConsoleSender().sendMessage(new CCT().colorize("&aConnection failed!"));
            System.out.println(e);
		}
	}
	public static CustomEnchantments getInstance() {
		return instance;
	}
	public boolean getShutdownState() {
		return CustomEnchantments.shutdown;
	}
}
