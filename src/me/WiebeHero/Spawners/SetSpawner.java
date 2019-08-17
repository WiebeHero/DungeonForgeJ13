package me.WiebeHero.Spawners;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class SetSpawner implements Listener,CommandExecutor{
	public String cmdSpawner = "spawner";
	public static TreeMap<Integer, Location> locationSpawner = new TreeMap<Integer, Location>();
	public static HashMap<Integer, Integer> tieredList = new HashMap<Integer, Integer>();
	public static HashMap<Integer, String> entityTypeList = new HashMap<Integer, String>();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.isOp()) {
				if(cmd.getName().equalsIgnoreCase(cmdSpawner)) {
					if(args.length == 1) {
						if(args[0].equalsIgnoreCase("delete")) {
							boolean deleted = false;
							int id = 0;
							for(Entry<Integer, Location> entry : locationSpawner.entrySet()) {
								if(entry.getValue().distance(player.getLocation()) <= 1.5) {
									id = entry.getKey();
									deleted = true;
								}
							}
							if(deleted == true) {
								locationSpawner.remove(id);
								tieredList.remove(id);
								entityTypeList.remove(id);
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have the deleted the spawner with ID: &6" + id));
							}
							else if(deleted == false){
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cNo spawners could be deleted! Get closer to the spawner!"));
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cSomething went wrong when deleting the spawner..."));
							}
						}
						else if(args[0].equalsIgnoreCase("see")) {
							for(Entry<Integer, Location> entry : locationSpawner.entrySet()) {
								if(entry.getValue().distance(player.getLocation()) <= 30) {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aSpawner ID: &6" + entry.getKey() + " &aSpawner Coords: &6" + entry.getValue().getBlockX() + " " + entry.getValue().getBlockY() + " " + entry.getValue().getBlockZ()));
									player.sendBlockChange(entry.getValue(), Material.YELLOW_STAINED_GLASS.createBlockData());
								}
							}
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aSpawners will only be shown to you as yellow stained glass!"));
						}
						else if(args[0].equalsIgnoreCase("deleteall")) {
							locationSpawner.clear();
							tieredList.clear();
							entityTypeList.clear();
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have deleted all the spawners!"));
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
							if(locationSpawner.containsKey(id)) {
								locationSpawner.remove(id);
								tieredList.remove(id);
								entityTypeList.remove(id);
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have deleted the spawner with id &6" + id));
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis spawner doesn't exist!"));
							}
						}
					}
					else if(args.length == 3) {
						if(args[0].equalsIgnoreCase("create")) {
							int tier = 0;
							try{
								tier = Integer.parseInt(args[1]);
							}
							catch(NumberFormatException e){
								e.printStackTrace();
							}
							if(tier >= 1 && tier <= 5) {
								String mobType = args[2].toUpperCase();
								EntityType type = null;
								try {
									type = EntityType.valueOf(mobType);
								}
								catch(IllegalArgumentException exp){
									exp.printStackTrace();
								}
								if(type != null) {
									if(locationSpawner.isEmpty()) {
										locationSpawner.put(1, player.getLocation());
										tieredList.put(1, tier);
										entityTypeList.put(1, mobType);
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aA new spawner with the id of &61 &ahas been created!"));
									}
									else {
										int currentId = locationSpawner.size() + 1;
										for(int i = 1; i <= locationSpawner.lastKey(); i++) {
											if(locationSpawner.get(i) == null) {
												currentId = i;
												break;
											}
										}
										locationSpawner.put(currentId, player.getLocation());
										tieredList.put(currentId, tier);
										entityTypeList.put(currentId, mobType);
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aA new spawner with the id of &6" + currentId + " &ahas been created!"));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid mob type."));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid tier, choose a number from 1 to 5"));
							}
						}	
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &a/spawner create (Tier of mob, can be a number from 1 to 5) (Type of mob, for example: zombie)"));
					}
				}
			}
			else {
				player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou are not OP, you can't use this."));
			}
		}
		return false;
	}
	public void loadSpawners(YamlConfiguration yml, File f) {
		locationSpawner.clear();
		tieredList.clear();
		entityTypeList.clear();
		if(yml.getConfigurationSection("Spawners.UUID") != null) {
			Set<String> set = yml.getConfigurationSection("Spawners.UUID").getKeys(false);
			for(int i = 1; i <= set.size(); i++) {
				if(yml.get("Spawners.UUID." + i) == null) {
					continue;
				}
				else {
					Location loc = (Location) yml.get("Spawners.UUID." + i + ".Location");
					int tier = yml.getInt("Spawners.UUID." + i + ".Tier");
					String type = yml.getString("Spawners.UUID." + i + ".EntityType");
					locationSpawner.put(i, loc);
					tieredList.put(i, tier);
					entityTypeList.put(i, type);
				}
			}
		}
	}
	public void saveSpawners(YamlConfiguration yml, File f) {
		yml.createSection("Spawners.UUID");
		if(!locationSpawner.isEmpty()) {
			for(int i = 1; i <= locationSpawner.lastKey(); i++) {
				if(locationSpawner.get(i) == null) {
					continue;
				}
				else {
					yml.set("Spawners.UUID." + i + ".Location", locationSpawner.get(i));
					yml.set("Spawners.UUID." + i + ".Tier", tieredList.get(i));
					yml.set("Spawners.UUID." + i + ".EntityType", entityTypeList.get(i));
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
	public static TreeMap<Integer, Location> getSpawnerLocList(){
		return locationSpawner;
	}
	public static HashMap<Integer, Integer> getTieredList(){
		return tieredList;
	}
	public static HashMap<Integer, String> getTypeList(){
		return entityTypeList;
	}
	
}
