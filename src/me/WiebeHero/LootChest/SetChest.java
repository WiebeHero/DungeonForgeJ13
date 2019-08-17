package me.WiebeHero.LootChest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

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
	public static TreeMap<Integer, Location> chestLocations = new TreeMap<Integer, Location>();
	public static HashMap<Integer, Integer> chestTier = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> chestRadius = new HashMap<Integer, Integer>();
	int counter;
	public String cmdNovis = "Loot";
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.isOp()) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase(ChatColor.stripColor("delete"))) {
						int id = 0;
						boolean deleted = false;
						for(Entry<Integer, Location> entry : chestLocations.entrySet()) {
							if(entry.getValue().distance(player.getLocation()) <= 1.5) {
								id = entry.getKey();
								deleted = true;
							}
						}
						if(deleted == true) {
							chestLocations.remove(id);
							chestTier.remove(id);
							chestRadius.remove(id);
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have the deleted the chest with ID: &6" + id));
						}
						else if(deleted == false){
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cNo chests could be deleted! Get closer to the chest!"));
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cSomething went wrong when deleting the chest..."));
						}
					}
					else if(args[0].equalsIgnoreCase("see")) {
						for(Entry<Integer, Location> entry : chestLocations.entrySet()) {
							if(entry.getValue().distance(player.getLocation()) <= 30) {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aChest ID: &6" + entry.getKey() + " &aChest Coords: &6" + entry.getValue().getBlockX() + " " + entry.getValue().getBlockY() + " " + entry.getValue().getBlockZ()));
								player.sendBlockChange(entry.getValue(), Material.ORANGE_STAINED_GLASS.createBlockData());
							}
						}
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aChest will only be shown to you as orange stained glass!"));
					}
					else if(args[0].equalsIgnoreCase("deleteall")) {
						chestLocations.clear();
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have deleted all the chests!"));
					}
				}
				else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("delete")) {
						int id = -1;
						try {
							id = Integer.parseInt(args[1]);
						}
						catch(NumberFormatException ex){
							ex.printStackTrace();
						}
						if(chestLocations.containsKey(id)) {
							chestLocations.remove(id);
							chestTier.remove(id);
							chestRadius.remove(id);
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have deleted the chest with id &6" + id));
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis chest doesn't exist!"));
						}
					}
				}
				else if(args.length == 3) {
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
								if(chestLocations.isEmpty()) {
									chestLocations.put(1, player.getLocation());
									chestTier.put(1, tier);
									chestRadius.put(1, radius);
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aA new loot chest with the id of &61 &ahas been created!"));
								}
								else {
									int currentId = chestLocations.size() + 1;
									for(int i = 1; i <= chestLocations.lastKey(); i++) {
										if(chestLocations.get(i) == null) {
											currentId = i;
											break;
										}
									}
									chestLocations.put(currentId, player.getLocation());
									chestTier.put(currentId, tier);
									chestRadius.put(currentId, radius);
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aA new loot chest with the id of &6" + currentId + " &ahas been created!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid radius"));
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid tier"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid command"));
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cTo many/few arguments"));
				}
			}
			else {
				player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou aren't op, so you can't use this."));
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
	public static TreeMap<Integer, Location> getChestLocationList(){
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
		for(int i = 1; i < set.size() + 1; i++) {
			if(yml.get("Loot.Chests." + i) == null) {
				continue;
			}
			else {
				Location loc = (Location) yml.get("Loot.Chests." + i + ".Location");
				int tier = yml.getInt("Loot.Chests." + i + ".Tier");
				int radius = yml.getInt("Loot.Chests." + i + ".Radius");
				chestLocations.put(i, loc);
				chestTier.put(i, tier);
				chestRadius.put(i, radius);
			}
		}
	}
	public void saveLootChests(YamlConfiguration yml, File f) {
		yml.createSection("Loot.Chests");
		if(!chestLocations.isEmpty()) {
			for(int i = 1; i <= chestLocations.lastKey(); i++) {
				if(chestLocations.get(i) == null) {
					continue;
				}
				else {
					yml.set("Loot.Chests." + i + ".Location", chestLocations.get(i));
					yml.set("Loot.Chests." + i + ".Tier", chestTier.get(i));
					yml.set("Loot.Chests." + i + ".Radius", chestRadius.get(i));
				}
			}
		}
		try{
			yml.save(f);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
}
