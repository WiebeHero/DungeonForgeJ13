package NeededStuff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import net.md_5.bungee.api.ChatColor;

public class SetHomeSystem implements Listener,CommandExecutor{
	public String command = "sethome";
	public String homeCommand = "home";
	public String homesCommand = "homes";
	public HashMap<UUID, HashMap<String, Location>> outerList = new HashMap<UUID, HashMap<String, Location>>();
	public HashMap<UUID, Integer> rankList = new HashMap<UUID, Integer>();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			HashMap<String, Location> innerList = null;
			if(outerList.get(player.getUniqueId()) == null) {
				innerList = new HashMap<String, Location>();
			}
			else {
				innerList = outerList.get(player.getUniqueId());
			}
			if(cmd.getName().equalsIgnoreCase(command)) {
				if(player.getWorld().getName().equals("FactionWorld-1")) {
					if(args.length == 1) {
						if(!innerList.containsKey(args[0])) {
							if(innerList.size() < rankList.get(player.getUniqueId())) {
								innerList.put(args[0], player.getLocation());
								outerList.put(player.getUniqueId(), innerList);
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aHome &6" + args[0] + " &ahas been set!"));
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have reached the maximum amount of homes you can have!"));
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis home already exists!"));
						}
					}
					else {
						if(innerList.size() < rankList.get(player.getUniqueId())) {
							innerList.put("Home", player.getLocation());
							outerList.put(player.getUniqueId(), innerList);
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aHome &6Home &ahas been set!"));
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have reached the maximum amount of homes you can have!"));
						}
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't set a home here!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(homeCommand)) {
				if(args.length >= 1 && args.length <= 2) {
					if(args[0].equalsIgnoreCase("remove")) {
						if(innerList.containsKey(args[1])) {
							innerList.remove(args[1]);
							outerList.put(player.getUniqueId(), innerList);
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cHome &6" + args[1] + " &chas been removed."));
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis home doesn't exist!"));
						}
					}
					else if(innerList.containsKey(args[0])) {
						player.teleport(innerList.get(args[0]));
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aTeleported!"));
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis home doesn't exist!"));
					}
				}
				else {
					if(innerList.containsKey("Home")) {
						player.teleport(innerList.get("Home"));
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aTeleported!"));
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis home doesn't exist!"));
					}
				}
			}
			else if(cmd.getName().equalsIgnoreCase(homesCommand)) {
				if(args.length == 0) {
					for(Entry<String, Location> entry : innerList.entrySet()) {
						if(innerList.isEmpty()) {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cNo homes have been set!"));
							break;
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aHome Name: &6" + entry.getKey()) + " X: " + (int)entry.getValue().getX() + " Y: " + (int)entry.getValue().getY() + " Z: " + (int)entry.getValue().getZ());
						}
					}
				}
			}
		}
		return false;
	}
	@EventHandler
	public void rankedListregister(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!rankList.containsKey(player.getUniqueId())) {
			LuckPermsApi api = LuckPerms.getApi();
			User user = api.getUser(player.getUniqueId());
			if(user.getPrimaryGroup().equalsIgnoreCase("bronze")) {
				rankList.put(player.getUniqueId(), 2);
			}
			else if(user.getPrimaryGroup().equalsIgnoreCase("silver")) {
				rankList.put(player.getUniqueId(), 3);
			}
			else if(user.getPrimaryGroup().equalsIgnoreCase("gold")) {
				rankList.put(player.getUniqueId(), 4);
			}
			else if(user.getPrimaryGroup().equalsIgnoreCase("platinum")) {
				rankList.put(player.getUniqueId(), 5);
			}
			else if(user.getPrimaryGroup().equalsIgnoreCase("diamond")) {
				rankList.put(player.getUniqueId(), 6);
			}
			else if(user.getPrimaryGroup().equalsIgnoreCase("emerald")) {
				rankList.put(player.getUniqueId(), 8);
			}
			else if(user.getPrimaryGroup().equalsIgnoreCase("owner") || user.getPrimaryGroup().equalsIgnoreCase("manager") || user.getPrimaryGroup().equalsIgnoreCase("headadmin")) {
				rankList.put(player.getUniqueId(), 999);
			}
			else {
				rankList.put(player.getUniqueId(), 1);
			}
		}
	}
	
	public void loadHomes(YamlConfiguration yml, File f) {
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		Set<String> set = yml.getConfigurationSection("Homes").getKeys(false);
		ArrayList<String> idList = new ArrayList<String>(set);
		CustomEnchantments.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "" + idList);
		for(int i = 0; i < idList.size(); i++) {
			UUID id = UUID.fromString(idList.get(i));
			Set<String> homes = yml.getConfigurationSection("Homes." + id).getKeys(false);
			ArrayList<String> homeList = new ArrayList<String>(homes);
			HashMap<String, Location> locList = new HashMap<String, Location>();
			for(int i1 = 0; i1 < homeList.size(); i1++) {
				Location loc = null;
				loc = (Location) yml.get("Homes." + id + "." + homeList.get(i1));
				locList.put(homeList.get(i1), loc);
			}
			CustomEnchantments.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "" + homeList);
			CustomEnchantments.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "" + locList);
			outerList.put(id, locList);
		}
	}
	public void saveHomes(YamlConfiguration yml, File f) {
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		for(Entry<UUID, HashMap<String, Location>> entry : outerList.entrySet()) {
			yml.createSection("Homes." + entry.getKey());
			HashMap<String, Location> innerList = outerList.get(entry.getKey());
			for(Entry<String, Location> entry2 : innerList.entrySet()) {
				yml.set("Homes." + entry.getKey() + "." + entry2.getKey(), entry2.getValue());
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