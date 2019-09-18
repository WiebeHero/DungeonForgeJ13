package me.WiebeHero.MoreStuff;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.Skills.DFPlayer;

public class LevelRestrictions implements Listener{
	File f =  new File("plugins/CustomEnchantments/playerskillsDF.yml");
	YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
	@EventHandler
	public void levelRestrictWeapons(EntityDamageByEntityEvent event) {
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().hasItemMeta()) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
						List<String> loreList = player.getInventory().getItemInMainHand().getItemMeta().getLore();
						for (int i=0; i<player.getInventory().getItemInMainHand().getItemMeta().getLore().size(); i++) {
							if(loreList.get(i).contains(ChatColor.stripColor("Level Required:"))) {
								DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
								int level = dfPlayer.getLevel();
								String line = ChatColor.stripColor(loreList.get(i));
								line = line.replaceAll("[^\\d.]", "");
								int reqLevel = Integer.parseInt(line);
			  					if(level < reqLevel) {
			  						player.sendMessage(new ColorCodeTranslator().colorize("&cYou can't use this, you are to low level."));
			  						event.setCancelled(true);
				  				}
			  					break;
							}
						}
					}
				}
			}
			if(player.getInventory().getItemInOffHand() != null) {
				if(player.getInventory().getItemInOffHand().getType() == Material.BOW) {
					if(player.getInventory().getItemInMainHand().hasItemMeta()) {
						if(player.getInventory().getItemInOffHand().getItemMeta().hasLore()) {
							List<String> loreList = player.getInventory().getItemInOffHand().getItemMeta().getLore();
							for (int i=0; i<player.getInventory().getItemInOffHand().getItemMeta().getLore().size(); i++) {
								if(loreList.get(i).contains(ChatColor.stripColor("Level Required:"))) {
									DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
									int level = dfPlayer.getLevel();
									String line = ChatColor.stripColor(loreList.get(i));
									line = line.replaceAll("[^\\d.]", "");
									int reqLevel = Integer.parseInt(line);
				  					if(level < reqLevel) {
				  						player.sendMessage(new ColorCodeTranslator().colorize("&cYou can't use this, you are to low level."));
				  						event.setCancelled(true);
				  					}
				  					break;
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void levelRestrictArmor(PlayerInteractEvent event) {
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		Player player = event.getPlayer();
		Action action = event.getAction();
		if(action == Action.RIGHT_CLICK_AIR || action == Action.LEFT_CLICK_AIR) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item != null) {
				if(item.hasItemMeta()) {
					if(item.getItemMeta().hasLore()) {
						for(String lore : item.getItemMeta().getLore()) {
							if(lore.contains(ChatColor.stripColor("Level Required:"))) {
								String check = lore;
								DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
								int level = dfPlayer.getLevel();
								check = check.replaceAll("[^\\d.]", "");
								int reqLevel = Integer.parseInt(check);
			  					if(level < reqLevel) {
			  						player.sendMessage(new ColorCodeTranslator().colorize("&cYou can't use this, you are to low level."));
			  						event.setCancelled(true);
			  					}
			  					break;
							}
						}
					}
				}
			}
		}
	}
}
