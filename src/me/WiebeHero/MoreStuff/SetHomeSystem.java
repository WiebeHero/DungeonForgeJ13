package me.WiebeHero.MoreStuff;

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

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.RankedPlayerPackage.RankedManager;
import me.WiebeHero.RankedPlayerPackage.RankedPlayer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ChatColor;

public class SetHomeSystem implements Listener,CommandExecutor{
	
	private RankedManager rManager;
	public String command1 = "delhome";
	public String command = "sethome";
	public String homeCommand = "home";
	public String homesCommand = "homes";
	public HashMap<UUID, HashMap<String, Location>> outerList;
	
	public SetHomeSystem(RankedManager rManager) {
		this.outerList = new HashMap<UUID, HashMap<String, Location>>();
		this.rManager = rManager;
	}
	
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
			RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
			if(cmd.getName().equalsIgnoreCase(command)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(player.getWorld().getName().equals("FactionWorld-1")) {
						if(args.length == 1) {
							if(!innerList.containsKey(args[0])) {
								if(innerList.size() < rPlayer.getHomeCount()) {
									innerList.put(args[0], player.getLocation());
									outerList.put(player.getUniqueId(), innerList);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aHome &6" + args[0] + " &ahas been set!"));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have reached the maximum amount of homes you can have!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis home already exists!"));
							}
						}
						else {
							if(innerList.size() < rPlayer.getHomeCount()) {
								innerList.put("Home", player.getLocation());
								outerList.put(player.getUniqueId(), innerList);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aHome &6Home &ahas been set!"));
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have reached the maximum amount of homes you can have!"));
							}
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't set a home here!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(homeCommand)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length >= 1 && args.length <= 2) {
						if(innerList.containsKey(args[0])) {
							final Location loc = innerList.get(args[0]);
							double locX = player.getLocation().getX();
							double locY = player.getLocation().getY();
							double locZ = player.getLocation().getZ();
							new BukkitRunnable() {
								int count = 10;
								@Override
								public void run() {
									if(player.getLocation().getX() == locX && player.getLocation().getY() == locY && player.getLocation().getZ() == locZ) {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aSending you to home in " + count + "..."));
										count--;
										if(count == 0) {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTeleported!"));
											player.teleport(loc);
											count = 10;
											cancel();
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCancelled teleporting because of you moving."));
										cancel();
									}
								}	
							}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis home doesn't exist!"));
						}
					}
					else {
						if(innerList.containsKey("Home")) {
							final Location loc = innerList.get("Home");
							double locX = player.getLocation().getX();
							double locY = player.getLocation().getY();
							double locZ = player.getLocation().getZ();
							new BukkitRunnable() {
								int count = 10;
								@Override
								public void run() {
									if(player.getLocation().getX() == locX && player.getLocation().getY() == locY && player.getLocation().getZ() == locZ) {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aSending you to home in " + count + "..."));
										count--;
										if(count == 0) {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTeleported!"));
											player.teleport(loc);
											count = 10;
											cancel();
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCancelled teleporting because of you moving."));
										cancel();
									}
								}	
							}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis home doesn't exist!"));
						}
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(homesCommand)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length == 1) {
						for(Entry<String, Location> entry : innerList.entrySet()) {
							if(innerList.isEmpty()) {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cNo homes have been set!"));
								break;
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aHome Name: &6" + entry.getKey()) + " X: " + (int)entry.getValue().getX() + " Y: " + (int)entry.getValue().getY() + " Z: " + (int)entry.getValue().getZ());
							}
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments!: /homes"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(command1)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length == 1) {
						if(innerList.containsKey(args[0])) {
							innerList.remove(args[0]);
							outerList.put(player.getUniqueId(), innerList);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cHome &6" + args[0] + " &chas been removed."));
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis home doesn't exist!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments!: /homes"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
		}
		return true;
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
			outerList.put(id, locList);
		}
	}
	
	public void saveHomes() {
		File f =  new File("plugins/CustomEnchantments/setHomeConfig.yml");
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