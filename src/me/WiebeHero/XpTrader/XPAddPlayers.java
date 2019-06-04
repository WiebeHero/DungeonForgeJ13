package me.WiebeHero.XpTrader;

import java.io.File;
import java.io.IOException;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.Spawners.SpawnerList;

public class XPAddPlayers extends SpawnerList implements Listener {
	@EventHandler
	public void xpAddPlayer(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().getType() == Material.EXPERIENCE_BOTTLE) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
						if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.stripColor("XP Bottle (Player)"))) {
							File f =  new File("plugins/CustomEnchantments/playerskillsDF.yml");
							YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
							//-----------------------------------------------------------------------------------------------------------------------------------------
							//File Calling
							//-----------------------------------------------------------------------------------------------------------------------------------------
							try{
								yml.load(f);
					        }
					        catch(IOException e){
					            e.printStackTrace();
					        } 
							catch (InvalidConfigurationException e) {
								e.printStackTrace();
							}
							ItemStack item = player.getInventory().getItemInMainHand();
							int xpAdd = 0;
							String xpAmount = "";
				    		for(String s : item.getItemMeta().getLore()) {
				    			if(s.contains("XP Amount:")) {
				    				xpAmount = ChatColor.stripColor(s);
				    			}
				    		}
				    		Matcher matcher1 = Pattern.compile("XP Amount: (\\d+)").matcher(ChatColor.stripColor(xpAmount));
							while(matcher1.find()) {
							    xpAdd = Integer.parseInt(matcher1.group(1));
							}
							int level = yml.getInt("Skills.Players." + player.getUniqueId() + ".Level");
							int xp = yml.getInt("Skills.Players." + player.getUniqueId() + ".XP");
							int maxxp = yml.getInt("Skills.Players." + player.getUniqueId() + ".MAXXP");	
							int skillPoints = yml.getInt("Skills.Players." + player.getUniqueId() + ".Skill Points");
							int atributePoints = yml.getInt("Skills.Players." + player.getUniqueId() + ".Atribute Points");
							int finalXP = xp + xpAdd;
							if(level < 100) {
								if(finalXP >= maxxp) {
									int finalSkillPoints = skillPoints + 15;
									int finalAtributePoints = atributePoints + 3;
									level++;
									xp = 0;
									int maxxpFinal = (int)(double)(maxxp * ((double)15 / (double)100 + 1.00));
									yml.set("Skills.Players." + player.getUniqueId() + ".Level", level);
									yml.set("Skills.Players." + player.getUniqueId() + ".XP", 0);
									yml.set("Skills.Players." + player.getUniqueId() + ".MAXXP", maxxpFinal);
									yml.set("Skills.Players." + player.getUniqueId() + ".Skill Points", finalSkillPoints);
									yml.set("Skills.Players." + player.getUniqueId() + ".Atribute Points", finalAtributePoints);
									Scoreboard scoreboard = player.getScoreboard();
									if(scoreboard.getTeam(player.getName()) != null) {
										Team t1 = scoreboard.getTeam(player.getName());
										t1.setPrefix(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7"));
										t1.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
										if(player.hasPermission("owner")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("manager")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("headadmin")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("admin")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("headmod")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("moderator")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("bronze")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("helper+")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("helper")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("silver")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("gold")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("platinum")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("diamond")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("emerald")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
										if(player.hasPermission("youtuber")) {
											player.setPlayerListName(new ColorCodeTranslator().colorize("&6[&b" + level + "&6]&7 " + player.getName() + " " + scoreboard.getTeam(player.getName()).getSuffix()));
										}
									}
								}
								else if(finalXP > 0){
									yml.set("Skills.Players." + player.getUniqueId() + ".XP", finalXP);
								}
								
								//-----------------------------------------------------------------------------------------------------------------------------------------
								//Save File
								//-----------------------------------------------------------------------------------------------------------------------------------------
								try{
									yml.save(f);
							    }
							    catch(IOException e){
							        e.printStackTrace();
							    }	
							}
							float barprogress = (float) finalXP / maxxp;
							if(finalXP > 0){
								if(!(barprogress > 1)) {
									player.setLevel(level);
									player.setExp((float)barprogress);
								}
							}
							player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
						}
					}
				}
			}
		}
	}
}
