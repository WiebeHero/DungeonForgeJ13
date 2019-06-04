package NeededStuff;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class HomeSystem implements Listener,CommandExecutor{
	public String command = "sethome";
	public String homeCommand = "home";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(homeCommand)) {
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
				if(CombatTag.getCombatTag().get(player.getName()) == 0) {
					if(args.length > 0) {
						String homeNumberS = args[0];
						int homeNumber = 0;
						Matcher matcher6 = Pattern.compile("(\\d+)").matcher(ChatColor.stripColor(homeNumberS));
						while(matcher6.find()) {
							homeNumber = Integer.parseInt(matcher6.group(1)); 
						}
						if(homeNumber == 1) {
							double locX = player.getLocation().getX();
							double locZ = player.getLocation().getZ();
							if(yml.getConfigurationSection("Homes." + player.getUniqueId().toString() + "Home 1" ) != null) {
								new BukkitRunnable() {
									int count = 10;
									@Override
									public void run() {
										if(player.getLocation().getX() == locX && player.getLocation().getZ() == locZ) {
											player.sendMessage(new ColorCodeTranslator().colorize("&aSending you to you're home in " + count + "..."));
											count--;
											if(count == 0) {
												player.sendMessage(new ColorCodeTranslator().colorize("&aYou have teleported to you're home!"));
												double x = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 1.LocX");
												double y = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 1.LocY");
												double z = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 1.LocZ");
												World world = Bukkit.getWorld("FactionWorld-1");
												Location loc = new Location(world, x, y, z);
												player.teleport(loc);
												cancel();
											}
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&cCancelled teleporting because of you moving."));
											cancel();
										}
									}	
								}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
							}
						}
						else if(homeNumber == 2) {
							if(player.hasPermission("bronze") || player.hasPermission("silver") || player.hasPermission("gold") || player.hasPermission("platinum") || player.hasPermission("diamond") || player.hasPermission("emerald")) {
								if(yml.getConfigurationSection("Homes." + player.getUniqueId().toString() + "Home 1" ) != null) {
									double locX = player.getLocation().getX();
									double locZ = player.getLocation().getZ();
									new BukkitRunnable() {
										int count = 10;
										@Override
										public void run() {
											if(player.getLocation().getX() == locX && player.getLocation().getZ() == locZ) {
												player.sendMessage(new ColorCodeTranslator().colorize("&aSending you to you're home in " + count + "..."));
												count--;
												if(count == 0) {
													player.sendMessage(new ColorCodeTranslator().colorize("&aYou have teleported to you're home!"));
													double x = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocX");
													double y = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocY");
													double z = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocZ");
													World world = Bukkit.getWorld("FactionWorld-1");
													Location loc = new Location(world, x, y, z);
													player.teleport(loc);
													cancel();
												}
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&cCancelled teleporting because of you moving."));
												cancel();
											}
										}	
									}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
								}
							}
						}
						else if(homeNumber == 3) {
							if(player.hasPermission("silver") || player.hasPermission("gold") || player.hasPermission("platinum") || player.hasPermission("diamond") || player.hasPermission("emerald")) {
								if(yml.getConfigurationSection("Homes." + player.getUniqueId().toString() + "Home 1" ) != null) {
									double locX = player.getLocation().getX();
									double locZ = player.getLocation().getZ();
									new BukkitRunnable() {
										int count = 10;
										@Override
										public void run() {
											if(player.getLocation().getX() == locX && player.getLocation().getZ() == locZ) {
												player.sendMessage(new ColorCodeTranslator().colorize("&aSending you to you're home in " + count + "..."));
												count--;
												if(count == 0) {
													player.sendMessage(new ColorCodeTranslator().colorize("&aYou have teleported to you're home!"));
													double x = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocX");
													double y = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocY");
													double z = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocZ");
													World world = Bukkit.getWorld("FactionWorld-1");
													Location loc = new Location(world, x, y, z);
													player.teleport(loc);
													cancel();
												}
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&cCancelled teleporting because of you moving."));
												cancel();
											}
										}	
									}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
								}
							}
						}
						else if(homeNumber == 4) {
							if(player.hasPermission("gold") || player.hasPermission("platinum") || player.hasPermission("diamond") || player.hasPermission("emerald")) {
								if(yml.getConfigurationSection("Homes." + player.getUniqueId().toString() + "Home 1" ) != null) {
									double locX = player.getLocation().getX();
									double locZ = player.getLocation().getZ();
									new BukkitRunnable() {
										int count = 10;
										@Override
										public void run() {
											if(player.getLocation().getX() == locX && player.getLocation().getZ() == locZ) {
												player.sendMessage(new ColorCodeTranslator().colorize("&aSending you to you're home in " + count + "..."));
												count--;
												if(count == 0) {
													player.sendMessage(new ColorCodeTranslator().colorize("&aYou have teleported to you're home!"));
													double x = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocX");
													double y = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocY");
													double z = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocZ");
													World world = Bukkit.getWorld("FactionWorld-1");
													Location loc = new Location(world, x, y, z);
													player.teleport(loc);
													cancel();
												}
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&cCancelled teleporting because of you moving."));
												cancel();
											}
										}	
									}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
								}
							}
						}
						else if(homeNumber == 5) {
							if(player.hasPermission("platinum") || player.hasPermission("diamond") || player.hasPermission("emerald")) {
								if(yml.getConfigurationSection("Homes." + player.getUniqueId().toString() + "Home 1" ) != null) {
									double locX = player.getLocation().getX();
									double locZ = player.getLocation().getZ();
									new BukkitRunnable() {
										int count = 10;
										@Override
										public void run() {
											if(player.getLocation().getX() == locX && player.getLocation().getZ() == locZ) {
												player.sendMessage(new ColorCodeTranslator().colorize("&aSending you to you're home in " + count + "..."));
												count--;
												if(count == 0) {
													player.sendMessage(new ColorCodeTranslator().colorize("&aYou have teleported to you're home!"));
													double x = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocX");
													double y = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocY");
													double z = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocZ");
													World world = Bukkit.getWorld("FactionWorld-1");
													Location loc = new Location(world, x, y, z);
													player.teleport(loc);
													cancel();
												}
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&cCancelled teleporting because of you moving."));
												cancel();
											}
										}	
									}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
								}
							}
						}
						else if(homeNumber == 6) {
							if(player.hasPermission("diamond") || player.hasPermission("emerald")) {
								if(yml.getConfigurationSection("Homes." + player.getUniqueId().toString() + "Home 1" ) != null) {
									double locX = player.getLocation().getX();
									double locZ = player.getLocation().getZ();
									new BukkitRunnable() {
										int count = 10;
										@Override
										public void run() {
											if(player.getLocation().getX() == locX && player.getLocation().getZ() == locZ) {
												player.sendMessage(new ColorCodeTranslator().colorize("&aSending you to you're home in " + count + "..."));
												count--;
												if(count == 0) {
													player.sendMessage(new ColorCodeTranslator().colorize("&aYou have teleported to you're home!"));
													double x = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocX");
													double y = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocY");
													double z = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocZ");
													World world = Bukkit.getWorld("FactionWorld-1");
													Location loc = new Location(world, x, y, z);
													player.teleport(loc);
													cancel();
												}
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&cCancelled teleporting because of you moving."));
												cancel();
											}
										}	
									}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
								}
							}
						}
						else if(homeNumber == 7) {
							if(player.hasPermission("emerald")) {
								if(yml.getConfigurationSection("Homes." + player.getUniqueId().toString() + "Home 1" ) != null) {
									double locX = player.getLocation().getX();
									double locZ = player.getLocation().getZ();
									new BukkitRunnable() {
										int count = 10;
										@Override
										public void run() {
											if(player.getLocation().getX() == locX && player.getLocation().getZ() == locZ) {
												player.sendMessage(new ColorCodeTranslator().colorize("&aSending you to you're home in " + count + "..."));
												count--;
												if(count == 0) {
													player.sendMessage(new ColorCodeTranslator().colorize("&aYou have teleported to you're home!"));
													double x = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocX");
													double y = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocY");
													double z = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 2.LocZ");
													World world = Bukkit.getWorld("FactionWorld-1");
													Location loc = new Location(world, x, y, z);
													player.teleport(loc);
													cancel();
												}
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&cCancelled teleporting because of you moving."));
												cancel();
											}
										}	
									}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
								}
							}
						}
					}
					else {
						double locX = player.getLocation().getX();
						double locZ = player.getLocation().getZ();
						new BukkitRunnable() {
							int count = 10;
							@Override
							public void run() {
								if(player.getLocation().getX() == locX && player.getLocation().getZ() == locZ) {
									player.sendMessage(new ColorCodeTranslator().colorize("&aSending you to you're home in " + count + "..."));
									count--;
									if(count == 0) {
										player.sendMessage(new ColorCodeTranslator().colorize("&aYou have teleported to you're home!"));
										double x = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 1.LocX");
										double y = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 1.LocY");
										double z = yml.getDouble("Homes." + player.getUniqueId().toString() + ".Home 1.LocZ");
										World world = Bukkit.getWorld("FactionWorld-1");
										Location loc = new Location(world, x, y, z);
										player.teleport(loc);
										cancel();
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&cCancelled teleporting because of you moving."));
									cancel();
								}
							}	
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&cYou can't use this command! You are in combat!"));
				}
			}
		}
		return false;
	}
}
