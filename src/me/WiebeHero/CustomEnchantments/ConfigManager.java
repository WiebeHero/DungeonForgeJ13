package me.WiebeHero.CustomEnchantments;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class ConfigManager {
	private CustomEnchantments plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	public FileConfiguration skillscfg;
	public File playerskills;
	
	public FileConfiguration spawnerList;
	public File spawnerConfig;
	
	public FileConfiguration lootchest;
	public File lootConfig;
	
	public FileConfiguration factions;
	public File factionsConfig;
	
	public FileConfiguration worldInv;
	public File worldInvConfig;
	
	public FileConfiguration money;
	public File moneyConfig;
	
	public FileConfiguration sethome;
	public File setHomeConfig;
	
	public FileConfiguration shop;
	public File shopConfig;
	
	public FileConfiguration mod;
	public File modConfig;
	
	public FileConfiguration dungeon;
	public File dungeonConfig;
	
	public FileConfiguration blowable;
	public File blowableConfig;
	
	public void setUp() {
		if(!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		playerskills = new File(plugin.getDataFolder(), "playerskillsDF.yml");
		skillscfg = YamlConfiguration.loadConfiguration(playerskills);
		skillscfg.options().copyDefaults(true);
		
		spawnerConfig = new File(plugin.getDataFolder(), "spawnerConfig.yml");
		spawnerList = YamlConfiguration.loadConfiguration(spawnerConfig);
		spawnerList.options().copyDefaults(true);
		
		lootConfig = new File(plugin.getDataFolder(), "lootConfig.yml");
		lootchest = YamlConfiguration.loadConfiguration(spawnerConfig);
		lootchest.options().copyDefaults(true);
		
		factionsConfig = new File(plugin.getDataFolder(), "factionsConfig.yml");
		factions = YamlConfiguration.loadConfiguration(factionsConfig);
		factions.options().copyDefaults(true);
		
		worldInvConfig = new File(plugin.getDataFolder(), "worldInv.yml");
		worldInv = YamlConfiguration.loadConfiguration(worldInvConfig);
		worldInv.options().copyDefaults(true);
		
		moneyConfig = new File(plugin.getDataFolder(), "cashConfig.yml");
		money = YamlConfiguration.loadConfiguration(moneyConfig);
		money.options().copyDefaults(true);
		
		setHomeConfig = new File(plugin.getDataFolder(), "setHomeConfig.yml");
		sethome = YamlConfiguration.loadConfiguration(setHomeConfig);
		sethome.options().copyDefaults(true);
		
		shopConfig = new File(plugin.getDataFolder(), "shopConfig.yml");
		shop = YamlConfiguration.loadConfiguration(shopConfig);
		shop.options().copyDefaults(true);
		
		modConfig = new File(plugin.getDataFolder(), "modConfig.yml");
		mod = YamlConfiguration.loadConfiguration(modConfig);
		mod.options().copyDefaults(true);
		
		dungeonConfig = new File(plugin.getDataFolder(), "dungeonConfig.yml");
		dungeon = YamlConfiguration.loadConfiguration(dungeonConfig);
		dungeon.options().copyDefaults(true);
		
		blowableConfig = new File(plugin.getDataFolder(), "Blowable.yml");
		blowable = YamlConfiguration.loadConfiguration(dungeonConfig);
		blowable.options().copyDefaults(true);
		if(!playerskills.exists()) {
			try {
				playerskills.createNewFile();
				skillscfg.addDefault("Skills.Players", "");
				skillscfg.options().copyDefaults(true);
				try{
					skillscfg.save(playerskills);
		        }
		        catch(IOException e){
		            e.printStackTrace();
		        }
				
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "PlayerSkills has been created!");
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "PlayerSkills could not be created!");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
		}
		
		if(!spawnerConfig.exists()) {
			try {
				spawnerConfig.createNewFile();
				spawnerList.addDefault("Spawners.UUID", "");
				spawnerList.options().copyDefaults(true);
				try{
					spawnerList.save(spawnerConfig);
		        }
		        catch(IOException e){
		            e.printStackTrace();
		        }
				
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Spawner Config has been created!");
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Spawner Config could not be created!");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
		}
		
		if(!lootConfig.exists()) {
			try {
				lootConfig.createNewFile();
				lootchest.addDefault("Loot.Chests", "");
				lootchest.options().copyDefaults(true);
				try{
					lootchest.save(lootConfig);
		        }
		        catch(IOException e){
		            e.printStackTrace();
		        }
				
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Loot Config has been created!");
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Loot Config could not be created!");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
		}
		
		if(!factionsConfig.exists()) {
			try {
				factionsConfig.createNewFile();
				factions.addDefault("Factions.List", "");
				factions.options().copyDefaults(true);
				try{
					factions.save(factionsConfig);
		        }
		        catch(IOException e){
		            e.printStackTrace();
		        }
				
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Spawner Config has been created!");
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Spawner Config could not be created!");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
		}
		
		if(!worldInvConfig.exists()) {
			try {
				worldInvConfig.createNewFile();
				worldInv.addDefault("Inventorys.Worlds.world", "");
				worldInv.options().copyDefaults(true);
				try{
					worldInv.save(worldInvConfig);
		        }
		        catch(IOException e){
		            e.printStackTrace();
		        }
				
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "WorldInv Config has been created!");
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "WorldInv Config could not be created!");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
		}
		if(!moneyConfig.exists()) {
			try {
				moneyConfig.createNewFile();
				money.addDefault("List", "");
				money.options().copyDefaults(true);
				try{
					money.save(moneyConfig);
		        }
		        catch(IOException e){
		            e.printStackTrace();
		        }
				
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Money Config has been created!");
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Money Config could not be created!");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
		}
		if(!setHomeConfig.exists()) {
			try {
				setHomeConfig.createNewFile();
				sethome.addDefault("Homes", "");
				sethome.options().copyDefaults(true);
				try{
					sethome.save(setHomeConfig);
		        }
		        catch(IOException e){
		            e.printStackTrace();
		        }
				
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Home Config has been created!");
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Home Config could not be created!");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
		}
		if(!shopConfig.exists()) {
			try {
				shopConfig.createNewFile();
				shop.addDefault("Shop", "");
				shop.options().copyDefaults(true);
				try{
					shop.save(shopConfig);
		        }
		        catch(IOException e){
		            e.printStackTrace();
		        }
				
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Shop Config has been created!");
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Shop Config could not be created!");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
		}
		if(!modConfig.exists()) {
			try {
				modConfig.createNewFile();
				mod.addDefault("Mod.Punishments", "");
				mod.options().copyDefaults(true);
				try{
					mod.save(modConfig);
		        }
		        catch(IOException e){
		            e.printStackTrace();
		        }
				
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Mod Config has been created!");
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Mod Config could not be created!");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
		}
		if(!dungeonConfig.exists()) {
			try {
				dungeonConfig.createNewFile();
				dungeon.addDefault("Dungeons.Instances", "");
				dungeon.options().copyDefaults(true);
				try{
					dungeon.save(dungeonConfig);
		        }
		        catch(IOException e){
		            e.printStackTrace();
		        }
				
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Dungeon Config has been created!");
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Dungeon Config could not be created!");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
		}
		if(!blowableConfig.exists()) {
			try {
				blowableConfig.createNewFile();
				blowable.addDefault("Blowable.List", "");
				blowable.options().copyDefaults(true);
				try{
					blowable.save(blowableConfig);
		        }
		        catch(IOException e){
		            e.printStackTrace();
		        }
				
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Dungeon Config has been created!");
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Dungeon Config could not be created!");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
		}
	}
	public FileConfiguration getPlayers() {
		return skillscfg;
	}
	public void savePlayers() {
		try {
			skillscfg.save(playerskills);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "PlayerSkills has been saved!");
		}catch(IOException e){
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "PlayerSkills could not be saved!");
		}
	}
	public void reloadPlayers() {
		skillscfg = YamlConfiguration.loadConfiguration(playerskills);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "PlayerSkills has been reloaded!");
	}
	
	
}
