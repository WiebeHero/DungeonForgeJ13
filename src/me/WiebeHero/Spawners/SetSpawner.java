package me.WiebeHero.Spawners;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class SetSpawner implements Listener,CommandExecutor{
	
	String id;
	int newId;
	public String cmdSpawner = "spawner";
	public String getLast(Set<String> set) {
        return set.stream().skip(set.stream().count() - 1).findFirst().get();
    }
	public static HashMap<Integer, Location> locationSpawner = new HashMap<Integer, Location>();
	public static HashMap<Integer, String> worldSpawner = new HashMap<Integer, String>();
	public static HashMap<Integer, Integer> tieredList = new HashMap<Integer, Integer>();
	public static HashMap<Integer, String> entityTypeList = new HashMap<Integer, String>();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.isOp()) {
				if(cmd.getName().equalsIgnoreCase(cmdSpawner)) {
					if(args.length == 3) {
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
									worldSpawner.put(worldSpawner.size() + 1, player.getWorld().getName());
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
				worldSpawner.put(i, worldName);
			}
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
	public static HashMap<Integer, String> getWorldList(){
		return worldSpawner;
	}
}
