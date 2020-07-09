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
import me.WiebeHero.Boosters.BoosterCommands;
import me.WiebeHero.Boosters.BoosterEvents;
import me.WiebeHero.Boosters.BoosterInventory;
import me.WiebeHero.Boosters.BoosterManager;
import me.WiebeHero.CapturePoints.CapturePointCommand;
import me.WiebeHero.CapturePoints.CapturePointEvents;
import me.WiebeHero.CapturePoints.CapturePointManager;
import me.WiebeHero.CapturePoints.CapturePointMenu;
import me.WiebeHero.Chat.ChatEvents;
import me.WiebeHero.Chat.MSGCommand;
import me.WiebeHero.Chat.MSGEvents;
import me.WiebeHero.Chat.MSGManager;
import me.WiebeHero.Consumables.Consumable;
import me.WiebeHero.Consumables.ConsumableHandler;
import me.WiebeHero.Consumables.CustomDurability;
import me.WiebeHero.CraftRecipes.UnblockBrewing;
import me.WiebeHero.CustomClasses.Methods;
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
import me.WiebeHero.DFShops.PayCommand;
import me.WiebeHero.DFShops.ShopEvents;
import me.WiebeHero.DFShops.ShopItemManager;
import me.WiebeHero.DFShops.ShopMenu;
import me.WiebeHero.DailyRewards.DailyRewardEvents;
import me.WiebeHero.DailyRewards.DailyRewardLootTable;
import me.WiebeHero.DailyRewards.DailyRewardManager;
import me.WiebeHero.DailyRewards.DailyRewardMenu;
import me.WiebeHero.DailyRewards.DailyRewardRoll;
import me.WiebeHero.DungeonInstances.PartyCommand;
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
import me.WiebeHero.FlyTicket.FlyTicketEvents;
import me.WiebeHero.FlyTicket.FlyTicketManager;
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
import me.WiebeHero.MoreStuff.DisableCollision;
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
import me.WiebeHero.Novis.NovisEnchantmentGetting;
import me.WiebeHero.Novis.NovisInventory;
import me.WiebeHero.Novis.NovisRewards;
import me.WiebeHero.OtherClasses.SignMenuFactory;
import me.WiebeHero.Party.PartyEvents;
import me.WiebeHero.Party.PartyManager;
import me.WiebeHero.RankedPlayerPackage.KitCommand;
import me.WiebeHero.RankedPlayerPackage.KitListener;
import me.WiebeHero.RankedPlayerPackage.KitMenu;
import me.WiebeHero.RankedPlayerPackage.KitRoll;
import me.WiebeHero.RankedPlayerPackage.RankEnum;
import me.WiebeHero.RankedPlayerPackage.RankedCommands;
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
import me.WiebeHero.Trade.TradeCommand;
import me.WiebeHero.Trade.TradeEvents;
import me.WiebeHero.Trade.TradeMenu;
import me.WiebeHero.XpTrader.XPAddPlayers;
import me.WiebeHero.XpTrader.XPTraderMenu;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.GroupManager;
import net.luckperms.api.model.user.User;

public class CustomEnchantments extends JavaPlugin implements Listener{
	private ArrayList<Integer> availableSlots = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35));
	private DFPlayerManager dfManager = new DFPlayerManager();
	private static CustomEnchantments instance;
	private PartyManager pManager = new PartyManager();
	private PartyCommand party = new PartyCommand(this.pManager);
	private Portals p = new Portals();
	private SpawnCommand spa = new SpawnCommand();
	private TPACommand tpa = new TPACommand();
	private LootRewards lootR = new LootRewards();
	private ConfigManager cfgm;
	private PunishManager punishManager = new PunishManager();
	private StaffManager staffManager = new StaffManager();
	private LootChestManager lootChestManager = new LootChestManager();
	private MSGManager msgManager = new MSGManager();
	// Faction classes
	private DFFactionPlayerManager facPlayerManager = new DFFactionPlayerManager();
	private DFFactionManager facManager = new DFFactionManager(this.facPlayerManager);
	// Classes that only need to be called once and can be given through
	private Methods method = new Methods();
	private Disparitys disp = new Disparitys(this.dfManager);
	private PotionM potionM = new PotionM();
	private ParticleAPI pApi = new ParticleAPI();
	private SwordSwingProgress sword = new SwordSwingProgress();
	private MethodLuck luck = new MethodLuck();
	private RankedManager rankedManager = new RankedManager(this.luck);
	private WGMethods wg = new WGMethods();
	private ItemStackBuilder builder = new ItemStackBuilder();
	private ClassMenu classMenu = new ClassMenu(this.dfManager);
	private SkillMenu skill = new SkillMenu(this.dfManager, this.builder, this.classMenu);
	private SkillCommand skillCommand = new SkillCommand(this.skill);
	private DFScoreboard score = new DFScoreboard(this.dfManager, this.facManager, this.facPlayerManager, this.rankedManager, this.wg);
	private FactionInventory facInv = new FactionInventory(this.builder);
	private AHManager ahManager = new AHManager(this.availableSlots);
	private AHInventory ahInv = new AHInventory(this.ahManager, this.builder);
	private Enchantment enchant = new Enchantment(this.dfManager, this.facManager, this.potionM, this.pApi, this.facPlayerManager, this.builder, this.wg);
	private Consumable con = new Consumable(this.dfManager, this.facManager, this.potionM, this.builder);
	private PayCommand pay = new PayCommand(this.dfManager);
	private DFSpawnerManager spawnerManager = new DFSpawnerManager(this.dfManager, this.disp);
	private ModerationGUI gui = new ModerationGUI(this.dfManager, this.builder, this.punishManager);
	private MethodMulti multi = new MethodMulti();
	private NovisEnchantmentGetting nEnchant = new NovisEnchantmentGetting();
	private RankEnum rEnum = new RankEnum(this.builder, this.con);
	private KitMenu menu = new KitMenu(this.builder, this.rankedManager, this.rEnum);
	private KitCommand kitCommand = new KitCommand(this.rankedManager, this.rEnum, this.menu);
	private EnchantmentGuideInventory enchantmentGuideInv;
	private SetHomeSystem sethome = new SetHomeSystem(this.rankedManager);
	private RankedPlayerListener rListener = new RankedPlayerListener(this.rankedManager, this.luck, this.wg);
	private CapturePointManager cpManager = new CapturePointManager(this.facManager, this.facPlayerManager);
	private CapturePointMenu cpMenu = new CapturePointMenu(this.cpManager, this.builder, this.facManager);
	private CapturePointCommand cpCommand = new CapturePointCommand(this.cpMenu);
	private DFFactions fac = new DFFactions(this.facManager, this.facPlayerManager, this.score, this.dfManager, this.wg, this.facInv, this.cpManager);
	private TradeMenu tradeMenu = new TradeMenu(this.builder);
	private DailyRewardManager dailyManager = new DailyRewardManager();
	private DailyRewardMenu dailyMenu = new DailyRewardMenu(this.builder, this.dailyManager, this.rankedManager);
	private DailyRewardLootTable dailyTable = new DailyRewardLootTable(this.builder);
	private DailyRewardRoll dailyRoll = new DailyRewardRoll(this.dailyTable);
	private DailyRewardEvents dailyEvents = new DailyRewardEvents(this.dailyManager, this.dailyMenu, this.rankedManager, this.dailyRoll);
	private BoosterManager boosterManager = new BoosterManager();
	private BoosterEvents boosterEvents = new BoosterEvents(this.boosterManager, this.facManager, this.facPlayerManager);
	private BoosterInventory bInventory = new BoosterInventory(this.builder, this.boosterManager, this.facManager, this.facPlayerManager);
	private BoosterCommands boosterCommand = new BoosterCommands(this.bInventory);
	private FlyTicketManager flyTicketManager = new FlyTicketManager();
	private FlyTicketEvents flyTicketEvents = new FlyTicketEvents(this.flyTicketManager, this.facManager, this.facPlayerManager);
	private KitRoll kitRoll = new KitRoll(this.rankedManager, this.builder);
	private XPTraderMenu xpTraderMenu = new XPTraderMenu();
	private ShopItemManager shopItemManager = new ShopItemManager();
	private ShopMenu shopMenu = new ShopMenu(this.shopItemManager, this.builder);
	private RankedCommands rankedCommands = new RankedCommands(this.rankedManager, this.wg, this.xpTraderMenu, this.msgManager, this.shopItemManager, this.shopMenu, this.potionM, this.score, this.dfManager, this.cpManager, this.facPlayerManager, this.builder, this.luck);
	private SignMenuFactory signFactory;
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
		
		NovisRewards rewards = new NovisRewards(this.nEnchant);
		this.signFactory = new SignMenuFactory(this);
		TradeEvents tradeEvents = new TradeEvents(this.tradeMenu, this.signFactory, this.builder, this.dfManager);
		TradeCommand tradeCmd = new TradeCommand(this.tradeMenu, tradeEvents, this.rankedManager);
		this.enchantmentGuideInv = new EnchantmentGuideInventory(this.enchant, this.builder);
		AHCommand ahCommand = new AHCommand(this.ahManager, this.rankedManager, this.ahInv);
		MSGCommand msgCommand = new MSGCommand(this.msgManager, this.method, this.rankedManager);
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
		this.shopItemManager.loadShopItems();
		this.shopMenu.loadShops();
		this.dailyManager.loadDailys();
		this.boosterManager.loadBoosters();
		this.boosterManager.runRemoval();
		this.rankedManager.loadKitCooldowns();
		this.facPlayerManager.loadPlayers();
		this.facManager.loadFactions();
		this.facManager.activeEnergyTimer();
		this.ahManager.loadAuctionHouse();
		this.dfManager.loadPlayers();
		maintenance = yml.getBoolean("General.Values.Maintenance");
		this.ahManager.start();
		this.cpManager.loadCapturePoints();
		this.cpManager.activateCapturePoints();
		this.dailyTable.constructDailyList();
		this.dailyTable.constructWeeklyList();
		this.dailyTable.constructMonthlyList();
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
		Enums en = new Enums(this.dfManager, this.facManager);
		for(Classes e : Classes.values()) {
			Bukkit.getPluginManager().registerEvents(e, CustomEnchantments.getInstance());
		}
		//Config Manager
		ModerationEvents mod = new ModerationEvents(this.facManager, this.dfManager, this.rankedManager, this.punishManager, this.staffManager, this.spawnerManager, this.lootChestManager, this.gui, this.multi, this.luck, this.method, this.score, this.classMenu, this.msgManager, this.cpManager, this.facPlayerManager, rewards, this.nEnchant, this.builder);
		//Custom Weapons
		getServer().getPluginManager().registerEvents(en, this);
		getServer().getPluginManager().registerEvents(new ActivateAbility(this.dfManager, this.facManager), this);
		getServer().getPluginManager().registerEvents(new DFWeaponUpgrade(this.dfManager, this.nEnchant), this);
		//Custom Armor
		getServer().getPluginManager().registerEvents(new DFArmorUpgrade(this.dfManager, this.nEnchant), this);
		getServer().getPluginManager().registerEvents(new DFShieldUpgrade(this.dfManager, this.nEnchant), this);
		//Custom Shields
		//Skills
		getServer().getPluginManager().registerEvents(new SkillMenuInteract(this.dfManager, this.skill, this.classMenu, this.score), this);
		getServer().getPluginManager().registerEvents(new SkillJoin(this.dfManager, this.classMenu, this.rEnum), this);
		getServer().getPluginManager().registerEvents(new ClassMenuSelection(this.dfManager, this.classMenu), this);
		getServer().getPluginManager().registerEvents(new CapturePointEvents(this.facPlayerManager, this.cpManager), this);
		getServer().getPluginManager().registerEvents(new XPEarningMobs(this.dfManager, this.score), this);
		getServer().getPluginManager().registerEvents(new EffectSkills(this.dfManager, this.sword), this);
		getServer().getPluginManager().registerEvents(new KitListener(this.menu, this.rankedManager, this.kitRoll), this);
		//Spawners
		getServer().getPluginManager().registerEvents(new DeathOfMob(), this);
		getServer().getPluginManager().registerEvents(new CustomDurability(), this);
		getServer().getPluginManager().registerEvents(new PreventPhantom(), this);
		//Novis
		getServer().getPluginManager().registerEvents(new NovisInventory(rewards), this);
		//Loot Chest
		getServer().getPluginManager().registerEvents(new ChestEvents(), this);
		getServer().getPluginManager().registerEvents(new MoneyNotes(this.dfManager, this.score), this);
		//Brewing Recipes
		getServer().getPluginManager().registerEvents(new UnblockBrewing(), this);
		//AFKSystem
		getServer().getPluginManager().registerEvents(new AFKSystem(), this);
		//Scoreboard
		getServer().getPluginManager().registerEvents(this.score, this);
		//XP Trader
		getServer().getPluginManager().registerEvents(this.xpTraderMenu, this);
		getServer().getPluginManager().registerEvents(new XPAddWeapons(this.nEnchant, this.dfManager), this);
		getServer().getPluginManager().registerEvents(new XPAddPlayers(this.dfManager, this.score), this);
		//lootSteal
		getServer().getPluginManager().registerEvents(new CancelLootSteal(), this);
		//Factions
		getServer().getPluginManager().registerEvents(new FactionsHandler(this.facManager, this.facPlayerManager, this.facInv, this.cpManager), this);
		getServer().getPluginManager().registerEvents(this.fac, this);
		//Stuff
		getServer().getPluginManager().registerEvents(this.sethome, this);
		getServer().getPluginManager().registerEvents(new DFPlayerRegister(this.dfManager), this);
		getServer().getPluginManager().registerEvents(new MSGEvents(this.msgManager), this);
		getServer().getPluginManager().registerEvents(new AHEvents(this.ahInv, this.ahManager, this.dfManager), this);
		getServer().getPluginManager().registerEvents(new ConsumableHandler(this.dfManager, this.con), this);
		getServer().getPluginManager().registerEvents(this.rListener, this);
		getServer().getPluginManager().registerEvents(new DFMobHealth(this.dfManager), this);
		getServer().getPluginManager().registerEvents(this.dailyEvents, this);
		getServer().getPluginManager().registerEvents(new DisableCollision(), this);
		//Boosters
		getServer().getPluginManager().registerEvents(this.boosterEvents, this);
		//Fly Tickets
		getServer().getPluginManager().registerEvents(this.flyTicketEvents, this);
		//Seasonal Events
		getServer().getPluginManager().registerEvents(new ChristmasInventoryEvents(), this);
		//Trader
		getServer().getPluginManager().registerEvents(tradeEvents, this);
		//Party
		getServer().getPluginManager().registerEvents(new PartyEvents(this.pManager), this);
		this.punishManager.loadPunishList();
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
				this.sethome.loadHomes(yml5, f6);
			}
		}
		//TNT
		getServer().getPluginManager().registerEvents(new TNTExplodeCovered(), this);
		getServer().getPluginManager().registerEvents(new EnchantmentHandler(this.enchant, this.enchantmentGuideInv, this.wg), this);
		for(Entry<String, Pair<Condition, CommandFile>> entry : this.enchant.getMeleeEnchantments().entrySet()) {
			Bukkit.getPluginManager().registerEvents(entry.getValue().getValue(), CustomEnchantments.getInstance());
		}
		for(Entry<String, Pair<Condition, CommandFile>> entry : this.enchant.getBowEnchantments().entrySet()) {
			Bukkit.getPluginManager().registerEvents(entry.getValue().getValue(), CustomEnchantments.getInstance());
		}
		for(Entry<String, Pair<Condition, CommandFile>> entry : this.enchant.getArmorEnchantments().entrySet()) {
			Bukkit.getPluginManager().registerEvents(entry.getValue().getValue(), CustomEnchantments.getInstance());
		}
		for(Entry<String, Pair<Condition, CommandFile>> entry : this.enchant.getShieldEnchantments().entrySet()) {
			Bukkit.getPluginManager().registerEvents(entry.getValue().getValue(), CustomEnchantments.getInstance());
		}
		this.con.loadConsumables();
		this.con.registerRecipes();
		for(Entry<String, Pair<me.WiebeHero.Consumables.ConsumableCondition.Condition, me.WiebeHero.Consumables.CommandFile>> entry : this.con.getConsumables().entrySet()) {
			Bukkit.getPluginManager().registerEvents(entry.getValue().getValue(), CustomEnchantments.getInstance());
		}
		//NeededStuff
		getServer().getPluginManager().registerEvents(this.tpa, this);
		getServer().getPluginManager().registerEvents(new CombatTag(), this);
		getServer().getPluginManager().registerEvents(new LevelRequired(this.dfManager), this);
		getServer().getPluginManager().registerEvents(new ChatEvents(this.dfManager, this.msgManager, this.facManager, this.facPlayerManager, this.rankedManager), this);
		getServer().getPluginManager().registerEvents(new Portals(), this);
		getServer().getPluginManager().registerEvents(new SwordSwingProgress(), this);
		getServer().getPluginManager().registerEvents(new PreventIllegalItems(), this);
		getServer().getPluginManager().registerEvents(new Disparitys(this.dfManager), this);
		getServer().getPluginManager().registerEvents(new RestrictInteractionWithBlocks(), this);
		getServer().getPluginManager().registerEvents(new EnderPearlCooldown(), this);
		getServer().getPluginManager().registerEvents(new MOTDSetting(), this);
		getServer().getPluginManager().registerEvents(new DisableThings(), this);	
		getServer().getPluginManager().registerEvents(new RestrictItemInteraction(), this);
		getServer().getPluginManager().registerEvents(new CancelJoinLeaveAdvancementMessages(), this);
		//Shop
		getServer().getPluginManager().registerEvents(new ShopEvents(this.dfManager, this.shopMenu, this.score, this.shopItemManager), this);
		//Commands
		getCommand(this.fac.faction).setExecutor(this.fac);
		getCommand(this.p.rtp).setExecutor(this.p);
		getCommand(this.spa.spawn).setExecutor(this.spa);
		getCommand(this.pay.command).setExecutor(this.pay);
		getCommand(this.pay.balance).setExecutor(this.pay);
		getCommand(this.pay.money).setExecutor(this.pay);
		getCommand(this.sethome.command).setExecutor(this.sethome);
		getCommand(this.sethome.command1).setExecutor(this.sethome);
		getCommand(this.sethome.homeCommand).setExecutor(this.sethome);
		getCommand(this.sethome.homesCommand).setExecutor(this.sethome);
		getCommand(this.tpa.tpa).setExecutor(this.tpa);
		getCommand(this.tpa.tpaccept).setExecutor(this.tpa);
		getCommand(this.tpa.tpahere).setExecutor(this.tpa);
		getCommand(this.tpa.tpatoggle).setExecutor(this.tpa);
		getCommand(this.tpa.tpdeny).setExecutor(this.tpa);
		getCommand(this.tpa.tpdeny).setExecutor(this.tpa);
		getCommand(msgCommand.msg).setExecutor(msgCommand);
		getCommand(msgCommand.ignore).setExecutor(msgCommand);
		getCommand(this.skillCommand.skill).setExecutor(this.skillCommand);
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
		getCommand(mod.a).setExecutor(mod);
		getCommand(mod.god).setExecutor(mod);
		getCommand(mod.gamemode).setExecutor(mod);
		getCommand(mod.item).setExecutor(mod);
		//Dungeon Parties
		getCommand(this.party.comParty).setExecutor(this.party);
		//Kits
		getCommand(this.kitCommand.kit).setExecutor(this.kitCommand);
		getCommand(this.kitCommand.kits).setExecutor(this.kitCommand);
		//Capture Points
		getCommand(this.cpCommand.capturepoints).setExecutor(this.cpCommand);
		getServer().getPluginManager().registerEvents(mod, this);
		//Trade Command
		getCommand(tradeCmd.trade).setExecutor(tradeCmd);
		//Booster Command
		getCommand(this.boosterCommand.boosters).setExecutor(this.boosterCommand);
		//Ranked Commands
		getCommand(this.rankedCommands.dchat).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.xptrader).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.workbench).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.pv).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.near).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.enderchest).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.playershop).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.playertime).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.playerbright).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.sellhand).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.playerweather).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.playerfeed).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.teleportcapturepoint).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.sellall).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.giftall).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.playerfly).setExecutor(this.rankedCommands);
		getCommand(this.rankedCommands.freerank).setExecutor(this.rankedCommands);
		getServer().getPluginManager().registerEvents(new ModerationGUI(this.dfManager, this.builder, this.punishManager), this);
		//Glowing Drops
	    Bukkit.getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new GlowingItemDrops(), this);
		loadConfig();
		//Custom Items FOOD
		getServer().getPluginManager().registerEvents(new ChangeFishDrops(), this);
		this.lootR.loadRewards();
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
		}.runTaskLater(CustomEnchantments.getInstance(), 504000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server will restart in 30 minutes!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 540000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server will restart in 15 minutes!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 558000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server will restart in 10 minutes!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 564000L);
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
		}.runTaskLater(CustomEnchantments.getInstance(), 570000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server will restart in 1 minute!"));
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 574800L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cRestarting!"));
				Bukkit.getServer().shutdown();
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 576000L);
		new BukkitRunnable() {
			public void run() {
				load = false;
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 100L);
		this.lootChestManager.loadLootChests();
		this.spawnerManager.loadSpawners();
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
			this.dfManager.savePlayers();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.facManager.saveFactions();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.ahManager.saveAuctionHouse();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.lootChestManager.saveLootChests();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.spawnerManager.saveSpawners();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.sethome.saveHomes();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.punishManager.savePunishList();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.rankedManager.saveKitCooldowns();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.cpManager.saveCapturePoints();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.boosterManager.saveBoosters();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.dailyManager.saveDailys();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nThe plugin CustomEnchantments has been Disabled!\n\n");
	}
	public void loadConfigManager() {
		this.cfgm = new ConfigManager();
		this.cfgm.setUpConfiguration("playerskillsDF.yml", "Skills.Players");
		this.cfgm.setUpConfiguration("spawnerConfig.yml", "Spawners.UUID");
		this.cfgm.setUpConfiguration("lootConfig.yml", "Loot.Chests");
		this.cfgm.setUpConfiguration("factionsConfig.yml", "Factions.List");
		this.cfgm.setUpConfiguration("setHomeConfig.yml", "Homes");
		this.cfgm.setUpConfiguration("shopConfig.yml", "Shop");
		this.cfgm.setUpConfiguration("modConfig.yml", "Mod.Punishments");
		this.cfgm.setUpConfiguration("dungeonConfig.yml", "Dungeons.Instances");
		this.cfgm.setUpConfiguration("GeneralConfig.yml", "General.Values");
		this.cfgm.setUpConfiguration("AuctionConfig.yml", "AuctionHouse.Items");
		this.cfgm.setUpConfiguration("KitCooldowns.yml", "Kits.Cooldowns");
		this.cfgm.setUpConfiguration("CapturePoints.yml", "Capture Points");
		this.cfgm.setUpConfiguration("Boosters.yml", "Boosters");
		this.cfgm.setUpConfiguration("DailyRewards.yml", "DailyRewards");
		this.cfgm.setUpConfiguration("FlyTickets.yml", "FlyTickets");
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
            	if(this.luck.containsParrent(user, "bronze")) {
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
