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
import org.bukkit.scoreboard.Team;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtinjector.NBTInjector;
import javafx.util.Pair;
import me.WiebeHero.ArmoryPackage.DFArmorUpgrade;
import me.WiebeHero.ArmoryPackage.DFShieldUpgrade;
import me.WiebeHero.ArmoryPackage.DFWeaponUpgrade;
import me.WiebeHero.ArmoryPackage.XPAddWeapons;
import me.WiebeHero.Consumables.Consumable;
import me.WiebeHero.Consumables.ConsumableHandler;
import me.WiebeHero.Consumables.CustomDurability;
import me.WiebeHero.CraftRecipes.CallRecipe;
import me.WiebeHero.CraftRecipes.UnblockBrewing;
import me.WiebeHero.CustomHitDelay.HitDelay;
import me.WiebeHero.CustomMethods.MethodLuck;
import me.WiebeHero.DFShops.DFShop;
import me.WiebeHero.DFShops.PayCommand;
import me.WiebeHero.DungeonInstances.DungeonMaxima;
import me.WiebeHero.DungeonInstances.DungeonParty;
import me.WiebeHero.DungeonInstances.DungeonPartyCommand;
import me.WiebeHero.EnchantmentAPI.CommandFile;
import me.WiebeHero.EnchantmentAPI.Enchantment;
import me.WiebeHero.EnchantmentAPI.EnchantmentCondition.Condition;
import me.WiebeHero.EnchantmentAPI.EnchantmentHandler;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactions;
import me.WiebeHero.Factions.FactionsHandler;
import me.WiebeHero.FishingLoot.ChangeFishDrops;
import me.WiebeHero.LootChest.ChestEvents;
import me.WiebeHero.LootChest.LootChestManager;
import me.WiebeHero.LootChest.LootRewards;
import me.WiebeHero.LootChest.MoneyNotes;
import me.WiebeHero.Moderation.ModerationEvents;
import me.WiebeHero.Moderation.ModerationGUI;
import me.WiebeHero.Moderation.PunishManager;
import me.WiebeHero.Moderation.StaffManager;
import me.WiebeHero.MoreStuff.AFKSystem;
import me.WiebeHero.MoreStuff.CancelJoinLeaveAdvancementMessages;
import me.WiebeHero.MoreStuff.CancelLootSteal;
import me.WiebeHero.MoreStuff.Chat;
import me.WiebeHero.MoreStuff.ChatItem;
import me.WiebeHero.MoreStuff.CombatTag;
import me.WiebeHero.MoreStuff.DisableThings;
import me.WiebeHero.MoreStuff.Disparitys;
import me.WiebeHero.MoreStuff.EnderPearlCooldown;
import me.WiebeHero.MoreStuff.LevelRestrictions;
import me.WiebeHero.MoreStuff.MOTDSetting;
import me.WiebeHero.MoreStuff.MoneyCreation;
import me.WiebeHero.MoreStuff.Portals;
import me.WiebeHero.MoreStuff.PreventIllegalItems;
import me.WiebeHero.MoreStuff.PreventPhantom;
import me.WiebeHero.MoreStuff.RestrictInteractionWithBlocks;
import me.WiebeHero.MoreStuff.SetHomeSystem;
import me.WiebeHero.MoreStuff.SpawnCommand;
import me.WiebeHero.MoreStuff.SwordSwingProgress;
import me.WiebeHero.MoreStuff.TNTExplodeCovered;
import me.WiebeHero.MoreStuff.TPACommand;
import me.WiebeHero.Novis.NovisInventory;
import me.WiebeHero.Scoreboard.DFScoreboard;
import me.WiebeHero.SeasonalEvents.ChristmasInventoryEvents;
import me.WiebeHero.Skills.ClassEnvy;
import me.WiebeHero.Skills.ClassGluttony;
import me.WiebeHero.Skills.ClassGreed;
import me.WiebeHero.Skills.ClassLust;
import me.WiebeHero.Skills.ClassMenuSelection;
import me.WiebeHero.Skills.ClassPride;
import me.WiebeHero.Skills.ClassSloth;
import me.WiebeHero.Skills.ClassWrath;
import me.WiebeHero.Skills.DFPlayer;
import me.WiebeHero.Skills.DFPlayerRegister;
import me.WiebeHero.Skills.EffectSkills;
import me.WiebeHero.Skills.SkillCommand;
import me.WiebeHero.Skills.SkillJoin;
import me.WiebeHero.Skills.SkillMenuInteract;
import me.WiebeHero.Skills.XPEarningMobs;
import me.WiebeHero.Spawners.DFSpawnerManager;
import me.WiebeHero.Spawners.DeathOfMob;
import me.WiebeHero.XpTrader.XPAddPlayers;
import me.WiebeHero.XpTrader.XPTraderMenu;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.GroupManager;
import net.luckperms.api.model.user.User;

public class CustomEnchantments extends JavaPlugin implements Listener{
	public HashMap<UUID, DFPlayer> dfPlayerList = new HashMap<UUID, DFPlayer>();
	public HashMap<UUID, DungeonParty> dfDungeonParty = new HashMap<UUID, DungeonParty>();
	public ArrayList<DungeonMaxima> dfDungeonList = new ArrayList<DungeonMaxima>();
	public ArrayList<DFFaction> factionList = new ArrayList<DFFaction>();
	private static CustomEnchantments instance;
	private SkillCommand skillCommand = new SkillCommand();
	private DungeonPartyCommand party = new DungeonPartyCommand();
	private DFFactions fac = new DFFactions();
	private Portals p = new Portals();
	private PayCommand pay = new PayCommand();
	private SpawnCommand spa = new SpawnCommand();
	private SetHomeSystem sethome = new SetHomeSystem();
	private TPACommand tpa = new TPACommand();
	private LootRewards lootR = new LootRewards();
	private ConfigManager cfgm;
	private DFPlayer pl = new DFPlayer();
	private DFFaction method = new DFFaction();
	public Enchantment enchant = new Enchantment();
	public Consumable con = new Consumable();
	public DFScoreboard score = new DFScoreboard();
	public PunishManager punishManager = new PunishManager();
	public StaffManager staffManager = new StaffManager();
	public DFSpawnerManager spawnerManager = new DFSpawnerManager();
	public LootChestManager lootChestManager = new LootChestManager();
	private MethodLuck luck = new MethodLuck();
	int level;
	public Scoreboard scoreboard;
	public static boolean hardSave = false;
	public static boolean shutdown = false;
	public static boolean maintenance = false;
	public static boolean load = true;
	@Override
	public void onEnable() {
		instance = this;
		scoreboard = this.getServer().getScoreboardManager().getMainScoreboard();
		//Enable Plugin Message
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nThe plugin CustomEnchantments has been enabled!\n\n");
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
		maintenance = yml.getBoolean("General.Values.Maintenance");
		//Config Manager
		loadConfigManager();
		ModerationEvents mod = new ModerationEvents();
		//Custom Weapons
		getServer().getPluginManager().registerEvents(new DFWeaponUpgrade(), this);
		//Custom Armor
		getServer().getPluginManager().registerEvents(new DFArmorUpgrade(), this);
		getServer().getPluginManager().registerEvents(new DFShieldUpgrade(), this);
		//Custom Shields
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
		getServer().getPluginManager().registerEvents(new CustomDurability(), this);
		getServer().getPluginManager().registerEvents(new PreventPhantom(), this);
		//Novis
		getServer().getPluginManager().registerEvents(new NovisInventory(), this);
		//Loot Chest
		getServer().getPluginManager().registerEvents(new ChestEvents(), this);
		getServer().getPluginManager().registerEvents(new MoneyNotes(), this);
		//Brewing Recipes
		getServer().getPluginManager().registerEvents(new UnblockBrewing(), this);
		getServer().getPluginManager().registerEvents(new CallRecipe(), this);
		//AFKSystem
		getServer().getPluginManager().registerEvents(new AFKSystem(), this);
		//Scoreboard
		getServer().getPluginManager().registerEvents(score, this);
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
		getServer().getPluginManager().registerEvents(new DFPlayerRegister(), this);
		getServer().getPluginManager().registerEvents(new ConsumableHandler(), this);
		//Seasonal Events
		getServer().getPluginManager().registerEvents(new ChristmasInventoryEvents(), this);
		method.loadFactions();
		method.activeEnergyTimer();
		punishManager.loadPunishList();
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
		getServer().getPluginManager().registerEvents(new EnchantmentHandler(), this);
		enchant.loadMeleeEnchantments();
		enchant.loadBowEnchantments();
		enchant.loadArmorEnchantments();
		enchant.loadShieldEnchantments();
		for(Entry<String, Pair<Condition, CommandFile>> entry : enchant.getMeleeEnchantments().entrySet()) {
			Bukkit.getPluginManager().registerEvents(entry.getValue().getValue(), CustomEnchantments.getInstance());
		}
		con.loadConsumables();
		con.registerRecipes();
		for(Entry<String, Pair<me.WiebeHero.Consumables.ConsumableCondition.Condition, me.WiebeHero.Consumables.CommandFile>> entry : con.getConsumables().entrySet()) {
			Bukkit.getPluginManager().registerEvents(entry.getValue().getValue(), CustomEnchantments.getInstance());
		}
		//NeededStuff
		getServer().getPluginManager().registerEvents(tpa, this);
		getServer().getPluginManager().registerEvents(new MoneyCreation(), this);
		getServer().getPluginManager().registerEvents(new CombatTag(), this);
		getServer().getPluginManager().registerEvents(new Chat(), this);
		getServer().getPluginManager().registerEvents(new Portals(), this);
		getServer().getPluginManager().registerEvents(new SwordSwingProgress(), this);
		getServer().getPluginManager().registerEvents(new PreventIllegalItems(), this);
		getServer().getPluginManager().registerEvents(new Disparitys(), this);
		getServer().getPluginManager().registerEvents(new LevelRestrictions(), this);
		getServer().getPluginManager().registerEvents(new RestrictInteractionWithBlocks(), this);
		getServer().getPluginManager().registerEvents(new EnderPearlCooldown(), this);
		getServer().getPluginManager().registerEvents(new MOTDSetting(), this);
		getServer().getPluginManager().registerEvents(new DisableThings(), this);
		getServer().getPluginManager().registerEvents(new ChatItem(), this);
		getServer().getPluginManager().registerEvents(new CancelJoinLeaveAdvancementMessages(), this);
		//Shop
		getServer().getPluginManager().registerEvents(new DFShop(), this);
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
		getCommand(sethome.homeCommand).setExecutor(sethome);
		getCommand(sethome.homesCommand).setExecutor(sethome);
		getCommand(tpa.tpa).setExecutor(tpa);
		getCommand(tpa.tpaccept).setExecutor(tpa);
		getCommand(tpa.tpahere).setExecutor(tpa);
		getCommand(tpa.tpatoggle).setExecutor(tpa);
		getCommand(tpa.tpdeny).setExecutor(tpa);
		getCommand(skillCommand.skill).setExecutor(skillCommand);
		getCommand(skillCommand.skills).setExecutor(skillCommand);
		//Moderation
		getCommand(mod.ban).setExecutor(mod);
		getCommand(mod.unban).setExecutor(mod);
		getCommand(mod.mute).setExecutor(mod);
		getCommand(mod.unmute).setExecutor(mod);
		getCommand(mod.staffmode).setExecutor(mod);
		getCommand(mod.protocol).setExecutor(mod);
		//Dungeon Parties
		getCommand(party.comParty).setExecutor(party);
		getServer().getPluginManager().registerEvents(mod, this);
		getServer().getPluginManager().registerEvents(new ModerationGUI(), this);
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
							e = NBTInjector.patchEntity(e);
							NBTCompound comp = NBTInjector.getNbtData(e);
							comp.setString("SpawnerUUID", "");
							comp.setInteger("Level", 1);
							comp.setInteger("Tier", 0);
							dfPlayerList.put(e.getUniqueId(), new DFPlayer());
						}
					}
				}
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 5L);
		new BukkitRunnable(){
			public void run() {
				pl.savePlayers();
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 18000L);
		new BukkitRunnable(){
			public void run() {
				method.saveFactions();
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 19200L);
		new BukkitRunnable(){
			public void run() {
				punishManager.savePunishList();
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20400L);
		new BukkitRunnable(){
			public void run() {
				lootChestManager.saveLootChests();
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 21600L);
		new BukkitRunnable(){
			public void run() {
				spawnerManager.saveSpawners();
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 22800L);
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
		for(Team t : scoreboard.getTeams()) {
			t.unregister();
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
		
		if(hardSave) {
			pl.hardSavePlayers();
			method.hardSaveFactions();
			punishManager.savePunishList();
			lootChestManager.saveLootChests();
			spawnerManager.saveSpawners();
		}
		else {
			pl.savePlayers();
			method.saveFactions();
			punishManager.savePunishList();
			lootChestManager.saveLootChests();
			spawnerManager.saveSpawners();
		}
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
	public void clearDfPlayers() {
		this.dfPlayerList = new HashMap<UUID, DFPlayer>();
	}
	public void clearFactions() {
		this.factionList = new ArrayList<DFFaction>();
	}
	public static CustomEnchantments getInstance() {
		return instance;
	}
	public boolean getShutdownState() {
		return CustomEnchantments.shutdown;
	}
}
