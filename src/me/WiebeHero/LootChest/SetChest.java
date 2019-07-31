package me.WiebeHero.LootChest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class SetChest implements Listener,CommandExecutor {
	public String getLast(Set<String> set) {
        return set.stream().skip(set.stream().count() - 1).findFirst().get();
	}
	public static HashMap<Integer, Location> chestLocations = new HashMap<Integer, Location>();
	public static HashMap<Integer, Integer> chestTier = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> chestRadius = new HashMap<Integer, Integer>();
	int counter;
	public String cmdNovis = "Loot";
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.isOp()) {
				if(args.length == 3) {
					if(args[0].equalsIgnoreCase(ChatColor.stripColor("create"))) {
						int tier = -1;
						int radius = 0;
						try {
							tier = Integer.parseInt(args[1]);
						}
						catch(NumberFormatException ex){
							ex.printStackTrace();
						}
						try {
							radius = Integer.parseInt(args[2]);
						}
						catch(NumberFormatException ex){
							ex.printStackTrace();
						}
						if(tier >= 1 && tier <= 4) {
							if(radius >= 1) {
								chestLocations.put(chestLocations.size() + 1, player.getLocation());
								chestTier.put(chestTier.size() + 1, tier);
								chestRadius.put(chestRadius.size() + 1, radius);
								player.getLocation().getBlock().setType(Material.CHEST);
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid radius"));
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid tier"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid command"));
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&cTo many/few arguments"));
				}
			}
			else {
				player.sendMessage(new ColorCodeTranslator().colorize("&cYou aren't op, so you can't use this."));
			}
		}
		return false;
	}
	public static HashMap<Integer, Integer> getChestTierList(){
		return SetChest.chestTier;
	}
	public static HashMap<Integer, Integer> getChestRadiusList(){
		return SetChest.chestRadius;
	}
	public static HashMap<Integer, Location> getChestLocationList(){
		return SetChest.chestLocations;
	}
	public void loadLootChests(YamlConfiguration yml, File f) {
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		Set<String> set = yml.getConfigurationSection("Loot.Chests").getKeys(false);
		for(int i = 1; i < set.size(); i++) {
			Location loc = (Location) yml.get("Loot.Chests." + i + ".Location");
			int tier = yml.getInt("Loot.Chests." + i + ".Tier");
			int radius = yml.getInt("Loot.Chests." + i + ".Radius");
			chestLocations.put(i, loc);
			chestTier.put(i, tier);
			chestRadius.put(i, radius);
		}
	}
	public void saveLootChests(YamlConfiguration yml, File f) {
		yml.createSection("Loot.Chests");
		for(int i = 1; i < chestLocations.size(); i++) {
			yml.set("Loot.Chests." + i + ".Location", chestLocations.get(i));
			yml.set("Loot.Chests." + i + ".Tier", chestTier.get(i));
			yml.set("Loot.Chests." + i + ".Radius", chestRadius.get(i));
		}
		try{
			yml.save(f);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
}
