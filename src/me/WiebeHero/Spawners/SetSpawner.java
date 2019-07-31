package me.WiebeHero.Spawners;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
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
	public static HashMap<Integer, Location> locationSpawner = new HashMap<Integer, Location>();
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
							int total = 0;
							for(Entry<Integer, Location> entry : locationSpawner.entrySet()) {
								if(entry.getValue().distance(player.getLocation()) <= 1.5) {
									locationSpawner.remove(entry.getKey());
									tieredList.remove(entry.getKey());
									entityTypeList.remove(entry.getKey());
									deleted = true;
									total++;
								}
							}
							if(deleted == true) {
								if(total == 1) {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have deleted a total of &6" + total + " &aspawner!"));
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have deleted a total of &6" + total + " &aspawners!"));
								}
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
							for(Entry<Integer, Location> entry : locationSpawner.entrySet()) {
								locationSpawner.remove(entry.getKey());
								tieredList.remove(entry.getKey());
								entityTypeList.remove(entry.getKey());
							}
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
							if(id != -1) {
								locationSpawner.remove(id);
								tieredList.remove(id);
								entityTypeList.remove(id);
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have deleted the spawner with id &6" + id));
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis spawner doesn't exist!"));
							}
						}
						else if(args[0].equalsIgnoreCase("heal")) {
							Player target = Bukkit.getPlayer(args[1]);
							target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.00);
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
									locationSpawner.put(locationSpawner.size() + 1, player.getLocation());
									tieredList.put(tieredList.size() + 1, tier);
									entityTypeList.put(entityTypeList.size() + 1, args[2]);
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid mob type."));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid tier, choose a number from 1 to 5"));
							}
						}	
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&6/spawner create (Tier of mob, can be a number from 1 to 5) (Type of mob, for example: zombie)"));
					}
				}
			}
			else {
				player.sendMessage(new ColorCodeTranslator().colorize("&cYou are not OP, you can't use this."));
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
			Bukkit.getServer().getConsoleSender().sendMessage(new ColorCodeTranslator().colorize("&aSize" + set.size()));
			for(int i = 1; i <= set.size(); i++) {
				double x = yml.getDouble("Spawners.UUID." + i + ".Location.X");
				double y = yml.getDouble("Spawners.UUID." + i + ".Location.Y");
				double z = yml.getDouble("Spawners.UUID." + i + ".Location.Z");
				String worldName = yml.getString("Spawners.UUID." + i + ".Location.World");
				World world = Bukkit.getWorld(worldName);
				Location loc = new Location(world, x, y, z);
				int tier = yml.getInt("Spawners.UUID." + i + ".Tier");
				String type = yml.getString("Spawners.UUID." + i + ".Type");
				locationSpawner.put(i, loc);
				tieredList.put(i, tier);
				entityTypeList.put(i, type);
			}
		}
	}
	public void saveSpawners(YamlConfiguration yml, File f) {
		yml.createSection("Spawners.UUID");
		for(Entry<Integer, Location> entry : locationSpawner.entrySet()) {
			yml.set("Spawners.UUID." + entry.getKey() + ".Location", entry.getValue());
			yml.set("Spawners.UUID." + entry.getKey() + ".Tier", tieredList.get(entry.getKey()));
			yml.set("Spawners.UUID." + entry.getKey() + ".EntityType", (String) entityTypeList.get(entry.getKey()));
		}
		try{
			yml.save(f);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public static HashMap<Integer, Location> getSpawnerLocList(){
		return locationSpawner;
	}
	public static HashMap<Integer, Integer> getTieredList(){
		return tieredList;
	}
	public static HashMap<Integer, String> getTypeList(){
		return entityTypeList;
	}
	
}
