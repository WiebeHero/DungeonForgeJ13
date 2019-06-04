package NeededStuff;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;

public class SetHomeSystem implements Listener,CommandExecutor{
	public String command = "sethome";
	public String homeCommand = "home";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(command)) {
				if(player.getWorld().getName().equals("FactionWorld-1")) {
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
					if(args.length > 0) {
						String homeNumberS = args[0];
						int homeNumber = 0;
						Matcher matcher6 = Pattern.compile("(\\d+)").matcher(ChatColor.stripColor(homeNumberS));
						while(matcher6.find()) {
							homeNumber = Integer.parseInt(matcher6.group(1)); 
						}
						if(homeNumber == 1) {
							yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locX", player.getLocation().getX());
							yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locY", player.getLocation().getY());
							yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locZ", player.getLocation().getZ());
							player.sendMessage(new ColorCodeTranslator().colorize("&aHome " + homeNumber + " has been set."));
						}
						if(homeNumber == 2) {
							if(player.hasPermission("bronze") || player.hasPermission("silver") || player.hasPermission("gold") || player.hasPermission("platinum") || player.hasPermission("diamond") || player.hasPermission("emerald")) {
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locX", player.getLocation().getX());
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locY", player.getLocation().getY());
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locZ", player.getLocation().getZ());
								player.sendMessage(new ColorCodeTranslator().colorize("&aHome " + homeNumber + " has been set."));
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cYou atleast need RANK BRONZE for more homes!"));
							}
						}
						else if(homeNumber == 3) {
							if(player.hasPermission("silver") || player.hasPermission("gold") || player.hasPermission("platinum") || player.hasPermission("diamond") || player.hasPermission("emerald")) {
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locX", player.getLocation().getX());
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locY", player.getLocation().getY());
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locZ", player.getLocation().getZ());
								player.sendMessage(new ColorCodeTranslator().colorize("&aHome " + homeNumber + " has been set."));
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cYou atleast need RANK SILVER for more homes!"));
							}
						}
						else if(homeNumber == 4) {
							if(player.hasPermission("gold") || player.hasPermission("platinum") || player.hasPermission("diamond") || player.hasPermission("emerald")) {
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locX", player.getLocation().getX());
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locY", player.getLocation().getY());
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locZ", player.getLocation().getZ());
								player.sendMessage(new ColorCodeTranslator().colorize("&aHome " + homeNumber + " has been set."));
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cYou atleast need RANK GOLD for more homes!"));
							}
						}
						else if(homeNumber == 5) {
							if(player.hasPermission("platinum") || player.hasPermission("diamond") || player.hasPermission("emerald")) {
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locX", player.getLocation().getX());
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locY", player.getLocation().getY());
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locZ", player.getLocation().getZ());
								player.sendMessage(new ColorCodeTranslator().colorize("&aHome " + homeNumber + " has been set."));
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cYou atleast need RANK PLATINUM for more homes!"));
							}
						}
						else if(homeNumber == 6) {
							if(player.hasPermission("diamond") || player.hasPermission("emerald")) {
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locX", player.getLocation().getX());
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locY", player.getLocation().getY());
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locZ", player.getLocation().getZ());
								player.sendMessage(new ColorCodeTranslator().colorize("&aHome " + homeNumber + " has been set."));
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cYou atleast need RANK DIAMOND for more homes!"));
							}
						}
						else if(homeNumber == 7) {
							if(player.hasPermission("emerald")) {
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locX", player.getLocation().getX());
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locY", player.getLocation().getY());
								yml.set("Homes." + player.getUniqueId().toString() + ".Home " + homeNumber + ".locZ", player.getLocation().getZ());
								player.sendMessage(new ColorCodeTranslator().colorize("&aHome " + homeNumber + " has been set."));
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cYou atleast need RANK EMERALD for more homes!"));
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid home number!"));
						}
						try{
							yml.save(f);
					    }
					    catch(IOException e){
					        e.printStackTrace();
					    }	
					}
					else {
						yml.set("Homes." + player.getUniqueId().toString() + ".Home 1.LocX", player.getLocation().getX());
						yml.set("Homes." + player.getUniqueId().toString() + ".Home 1.LocY", player.getLocation().getY());
						yml.set("Homes." + player.getUniqueId().toString() + ".Home 1.LocZ", player.getLocation().getZ());
						player.sendMessage(new ColorCodeTranslator().colorize("&aFirst home has been set."));
						try{
							yml.save(f);
					    }
					    catch(IOException e){
					        e.printStackTrace();
					    }	
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&cYou cant set a home here."));
				}
			}
		}
		return false;
	}
}