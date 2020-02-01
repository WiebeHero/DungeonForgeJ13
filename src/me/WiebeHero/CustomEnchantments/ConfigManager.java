package me.WiebeHero.CustomEnchantments;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class ConfigManager {
	public void setUpConfiguration(String fileName, String def, Object value, boolean copyDef) {
		if(fileName.contains(".yml")) {
			File file = new File(CustomEnchantments.getInstance().getDataFolder(), fileName);
			FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
			fileConfig.options().copyDefaults(copyDef);
			if(!file.exists()) {
				try {
					file.createNewFile();
					fileConfig.addDefault(def, value);
					fileConfig.options().copyDefaults(copyDef);
					try{
						fileConfig.save(file);
			        }
			        catch(IOException e){
			            e.printStackTrace();
			        }
					
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + fileName + " has been created!");
				}catch(IOException e) {
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + fileName + " could not be created!");
				}
			}
			else {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Not a valid data type has been found!");
		}
	}
	public void setUpConfiguration(String fileName, String def, Object value) {
		if(fileName.contains(".yml")) {
			File file = new File(CustomEnchantments.getInstance().getDataFolder(), fileName);
			FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
			fileConfig.options().copyDefaults(true);
			if(!file.exists()) {
				try {
					file.createNewFile();
					fileConfig.addDefault(def, value);
					fileConfig.options().copyDefaults(true);
					try{
						fileConfig.save(file);
			        }
			        catch(IOException e){
			            e.printStackTrace();
			        }
					
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + fileName + " has been created!");
				}catch(IOException e) {
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + fileName + " could not be created!");
				}
			}
			else {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Not a valid data type has been found!");
		}
	}
	public void setUpConfiguration(String fileName, String def) {
		if(fileName.contains(".yml")) {
			File file = new File(CustomEnchantments.getInstance().getDataFolder(), fileName);
			FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
			fileConfig.options().copyDefaults(true);
			if(!file.exists()) {
				try {
					file.createNewFile();
					fileConfig.addDefault(def, "");
					fileConfig.options().copyDefaults(true);
					try{
						fileConfig.save(file);
			        }
			        catch(IOException e){
			            e.printStackTrace();
			        }
					
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + fileName + " has been created!");
				}catch(IOException e) {
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + fileName + " could not be created!");
				}
			}
			else {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "else");
			}
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Not a valid data type has been found!");
		}
	}
}
