package me.WiebeHero.Factions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import NeededStuff.CombatTag;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class DFFactions implements Listener,CommandExecutor{
	Player p1;
	String idNew = "";
	public String faction = "faction";
	public static HashMap<String, ArrayList<UUID>> factionList = new HashMap<String, ArrayList<UUID>>();
	public static HashMap<String, ArrayList<Chunk>> chunkList = new HashMap<String, ArrayList<Chunk>>();
	public static HashMap<String, Integer> chunkTotal = new HashMap<String, Integer>();
	public static HashMap<String, Integer> fTop = new HashMap<String, Integer>();
	public static HashMap<String, Location> fHomes = new HashMap<String, Location>();
	public static HashMap<UUID, Integer> ranked = new HashMap<UUID, Integer>();
	public static HashMap<String, ArrayList<UUID>> inviteList = new HashMap<String, ArrayList<UUID>>();
	public static HashMap<String, ArrayList<String>> allyList = new HashMap<String, ArrayList<String>>();
	public static HashMap<String, ArrayList<UUID>> playerAlliedList = new HashMap<String, ArrayList<UUID>>();
	public static HashMap<String, ArrayList<String>> invitedAllyList = new HashMap<String, ArrayList<String>>();
	public static ArrayList<String> factionNameList = new ArrayList<String>();
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
				File f =  new File("plugins/CustomEnchantments/factionsConfig.yml");
				YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
				if(!(cmd.getName().equalsIgnoreCase(label)) || cmd.getName().equalsIgnoreCase(faction)) {
					if(player.getWorld().getName().equals("DFWarzone-1") || player.getWorld().getName().equals("FactionWorld-1")) {
						String temp = "";
						for(Entry<String, ArrayList<UUID>> entry : factionList.entrySet()) {
							if(entry.getValue().contains(player.getUniqueId())) {
								temp = entry.getKey();
							}
						}
						final String fName = temp;
						if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
							player.sendMessage(new ColorCodeTranslator().colorize("&6)------------------=[&bHelp&6]=------------------("));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f create | Create a faction."));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f claim/unclaim | Claim/Unclaim a chunk of your faction"));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f help | Shows faction commands."));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f top | Shows how many F points you have."));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f promote/demote (Player Name)| Promote or demote a player in you faction."));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f invite/add (Player Name) | Invite a player to your faction."));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f kick/remove (Player Name) | Kick a player from your faction."));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f leave | Leave a faction."));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f abandon | Abandon/Delete your faction."));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f join/accept | Join a faction that you have been invited to."));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f decline/refuse | Refuse to join a faction that invited you."));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f list | Check current information about your faction."));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f sethome | Sets the home of your faction."));
							player.sendMessage(new ColorCodeTranslator().colorize("&b/f home | Teleports you to the faction home, if there is one."));
							player.sendMessage(new ColorCodeTranslator().colorize("&6)------------------=[&bHelp&6]=------------------("));
						}
						else if(args[0].equalsIgnoreCase("create")) {
							if(args.length == 2) {
								String facName = args[1];
								Pattern p = Pattern.compile( "[0-9]" );
							    Matcher m = p.matcher(facName);
							    if(m.find() == false && facName.indexOf("_-=+[]{}:;''<>/?!@#$%^&*()") == -1) {
							    	if(facName.length() > 8) {
							    		if(fName.equals("")) {
							    			if(!factionNameList.contains(facName)) {
									    		player.sendMessage(new ColorCodeTranslator().colorize("&aYou have created your faction &6" + facName));
									    		factionNameList.add(facName);
									    		factionList.put(facName, new ArrayList<UUID>());
									    		factionList.get(facName).add(player.getUniqueId());
									    		chunkList.put(facName, new ArrayList<Chunk>());
									    		chunkTotal.put(facName, 0);
									    		fTop.put(facName, 0);
									    		fHomes.put(facName, null);
									    		allyList.put(facName, new ArrayList<String>());
									    		ranked.put(player.getUniqueId(), 4);
						    				}
							    			else {
								    			player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis faction already exists!"));
								    		}
								        }
							    		else {
							    			player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou are already in a faction!"));
							    		}
						    		}
							    	else {
							    		player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYour faction name MUST be more then 8 characters!"));
							    	}
						    	}
							    else{
							    	player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYour faction MUST NOT contain any strange symbols!"));
							    }
						    }
						    else {
						    	player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYour faction name can NOT be nothing!"));
							}
						}
						else if(args[0].equalsIgnoreCase("abandon")) {
							if(args.length == 1) {
								if(!fName.equals("")) {
									int rank = ranked.get(player.getUniqueId());
									if(rank == 4) {
										for(UUID id : factionList.get(fName)) {
											ranked.remove(id);
										}
										factionList.remove(fName);
										factionNameList.remove(fName);
										chunkList.remove(fName);
										chunkTotal.remove(fName);
										fTop.remove(fName);
										fHomes.remove(fName);
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have abandoned this faction!"));
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou dont have permission to abandon this faction!"));
									}
								}
		    					else {
			    					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have a faction!"));
			    				}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("list")) {
							if(args.length > 0) {
								if(!fName.equals("")) {
			    					ArrayList<UUID> mList = new ArrayList<UUID>();
			    					mList.addAll(factionList.get(fName));
			    					ArrayList<String> listOnline = new ArrayList<String>();
			    					ArrayList<String> listOffline = new ArrayList<String>();
			    					for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
		    							for(int i2 = 0; i2 < mList.size(); i2++) {
		    								if(mList.get(i2).equals(p.getUniqueId())) {
		    									int finalNumber = ranked.get(p.getUniqueId());
					    						if(p.isOnline()) {
					    							if(finalNumber == 4) {
					    								listOnline.add(p.getName() + " &4&l(LEADER)&b");
					    							}
					    							else if(finalNumber == 3) {
					    								listOnline.add(p.getName() + " &c&l(OFFICER)&b");
					    							}
					    							else if(finalNumber == 2) {
					    								listOnline.add(p.getName() + " &e(MEMBER)&b");
					    							}
					    							else if(finalNumber == 1) {
					    								listOnline.add(p.getName() + " &7(RECRUIT)&b");
					    							}
				    							}
					    						else {
					    							if(finalNumber == 4) {	
					    								listOffline.add(p.getName() + " &4&l(LEADER)&b");
					    							}
					    							else if(finalNumber == 3) {	
					    								listOffline.add(p.getName() + " &c&l(OFFICER)&b");
					    							}
					    							else if(finalNumber == 2) {	
					    								listOffline.add(p.getName() + " &e(MEMBER)&b");
					    							}
					    							else if(finalNumber == 1) {	
					    								listOffline.add(p.getName() + " &7(RECRUIT)&b");
					    							}
				    							}
			    							}	
			    						}
			    					}
			    					int cClaimed = chunkTotal.get(fName);
			    					player.sendMessage(new ColorCodeTranslator().colorize("&7------------------&a[&b" + fName + "&a]&7------------------"));
			    					player.sendMessage(new ColorCodeTranslator().colorize("&7Faction Name: &b" + fName));
									player.sendMessage(new ColorCodeTranslator().colorize("&7Total Members: &b" + mList.size()));
									player.sendMessage(new ColorCodeTranslator().colorize("&7Chunks Claimed: &b" + cClaimed));
									player.sendMessage(new ColorCodeTranslator().colorize("&7Members Online: &b" + listOnline));
									player.sendMessage(new ColorCodeTranslator().colorize("&7Members Offline: &b" + listOffline));
									if(fHomes.get(fName) != null) {
										player.sendMessage(new ColorCodeTranslator().colorize("&7Faction Home: &bSet at " + "&6X: " + fHomes.get(fName).getX() + " Y: " + fHomes.get(fName).getY() + " Z: " + fHomes.get(fName).getZ()));
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&7Faction Home: &cNot Set"));
									}
									player.sendMessage(new ColorCodeTranslator().colorize("&7------------------&a[&b" + fName + "&a]&7------------------"));
			    				}
			    				else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
								}
							}
						}
						else if(args[0].equalsIgnoreCase("claim")) {
							if(args.length == 1) {
								if(player.getWorld().getName().equals("FactionWorld-1")) {
									if(!fName.equals("")) {
										if(!chunkList.get(fName).contains(player.getLocation().getChunk())) {
											int rank = ranked.get(player.getUniqueId());
											ArrayList<Chunk> yap = chunkList.get(fName);
											int count = 0;
											for(UUID uuid : factionList.get(fName)) {
												if(factionList.get(fName).contains(uuid)) {
													count++;
												}
											}
											if(rank >= 3) {
												if(yap.size() < count + 3) {
													if(yap.size() < 12) {
														yap.add(player.getLocation().getChunk());
														chunkList.put(fName, yap);
														chunkTotal.put(fName, chunkTotal.get(fName) + 1);
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have claimed this chunk!"));
													}
													else {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have claimed the maximum amount of chunks!"));
													}
												}
												else {
													player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have claimed the maximum amount of chunks! Get more faction members to claim more!"));
												}
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou dont have permission to claim chunks!"));
											}
				    					}
										else if(chunkList.get(fName).contains(player.getLocation().getChunk())) {
					    					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou already claimed this chunk!"));
										}
										else {
											for(Entry<String, ArrayList<Chunk>> entry : chunkList.entrySet()) {
												if(!entry.getKey().equals(fName)) {
													if(entry.getValue().contains(player.getLocation().getChunk())) {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + entry.getKey() + " &cAlready claimed this chunk!"));
													}
												}
											}
										}
					    			}
				    				else {
				    					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't claim here!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("unclaim")) {
							if(args.length == 1) {
								if(args.length == 1) {
									if(player.getWorld().getName().equals("FactionWorld-1")) {
										if(!fName.equals("")) {
											int rank = ranked.get(player.getUniqueId());
											if(rank >= 3) {
												if(chunkList.get(fName).contains(player.getLocation().getChunk())) {
													chunkList.get(fName).remove(player.getLocation().getChunk());
													chunkTotal.put(fName, chunkTotal.get(fName) - 1);
													player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have unclaimed this chunk!"));
												}
												else {
													boolean check = false;
													String fNameOther = "";
													for(Entry<String, ArrayList<Chunk>> entry : chunkList.entrySet()) {
														if(!entry.getKey().equals(fName)) {
															if(entry.getValue().contains(player.getLocation().getChunk())) {
																check = true;
																fNameOther = entry.getKey();
																break;
															}
														}
													}
													if(check == true) {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + fNameOther + " &cAlready claimed this chunk!"));
													}
													else {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis chunk is claimed by no one!"));
													}
												}
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou dont have permission to unclaim chunks!"));
											}
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't unclaim here!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
								}
							}
						}
						else if(args[0].equalsIgnoreCase("sethome")) {
							if(args.length == 1) {
								if(player.getWorld().getName().equals("FactionWorld-1")) {
									if(!fName.equals("")) {
										int rank = ranked.get(player.getUniqueId());
										if(rank >= 3) {
											if(chunkList.get(fName).contains(player.getLocation().getChunk())) {
												fHomes.put(fName, player.getLocation());
												player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have set your faction home!"));
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't set your faction home outside of your territory!"));
											}
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou dont have permission to set a home for your faction!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't set your faction home in this world!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("home")) {
							if(args.length == 1) {
								Location loc = player.getLocation();
								double locX = loc.getX();
								double locZ = loc.getZ();
								if(!fName.equals("")) {
									int rank = ranked.get(player.getUniqueId());
									if(rank >= 2) {
										new BukkitRunnable() {
											int count = 10;
											@Override
											public void run() {
												if(player.getLocation().getX() == locX && player.getLocation().getZ() == locZ) {
													player.sendMessage(new ColorCodeTranslator().colorize("&aTeleporting to faction home in &b" + count + "..."));
													count--;
													if(count == 0) {
														player.teleport(fHomes.get(fName));
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
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou dont have permission to set a home for your faction"));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("invite")) {
							if(args.length == 2) {
								if(!fName.equals("")) {
									int rank = ranked.get(player.getUniqueId());
									if(rank >= 3) {
										Player p = Bukkit.getPlayer(args[1]);
										if(p != null) {
											if(p.isOnline()) {
												if(p != player) {
													boolean check = false;
													for(ArrayList<UUID> uuids : factionList.values()) {
														if(uuids.contains(p.getUniqueId())) {
															check = true;
														}
													}
													if(check == true) {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player is already in a faction!"));
													}
													else {
														inviteList.get(fName).add(p.getUniqueId());
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have invited &6" + p.getName() + " &ato your faction"));
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aThey have &660 &aseconds to accept!"));
														String name = fName;
														p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ainvited you to &6" + fName + "&a!"));
														p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have 60 seconds to accept!"));
														p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aType /f accept/join to join &6" + fName +"&a, type /f decline/refuse to decline the invitation!"));
														new BukkitRunnable() {
															public void run() {
																if(inviteList.get(name).contains(p.getUniqueId())) {
																	p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cDue to you not responding, your invitation to &6" + name + " &chas expired!"));
																	player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cDue to &6" + p.getName() + " &cnot responding, your invitation has expired!"));
																}
															}
														}.runTaskLater(CustomEnchantments.getInstance(), 1200L);
													}
												}
												else {
													player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't invite yourself!"));	
												}
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + p.getName() + "&c is not online!"));
											}
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player has never joined DungeonForge!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou dont have permission to invite someone to your faction."));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("leave")) {
							if(args.length == 1) {
								if(!fName.equals("")) {
									if(ranked.get(player.getUniqueId()) != 4) {
										factionList.get(fName).remove(player.getUniqueId());
										ranked.remove(player.getUniqueId());
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have left &6" + fName));
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't leave your faction as your faction leader! Use /f abandon instead."));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("kick")) {
							if(args.length == 2) {
								if(!fName.equals("")) {
									int rank = ranked.get(player.getUniqueId());
									if(rank >= 3) {
										Player p = Bukkit.getPlayer(args[1]);
										if(p != null) {
											if(p != player) {
												if(factionList.get(fName).contains(p.getUniqueId())) {
													int rankOther = ranked.get(p.getUniqueId());
													if(rank > rankOther) {
														factionList.get(fName).remove(player.getUniqueId());
														ranked.remove(p.getUniqueId());
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have kicked &6" + p.getName() + " &afrom your faction!"));
														if(p.isOnline()) {
															p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ckicked you from their faction!"));
														}
													}
													else {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have permission to kick this person from your faction!"));
													}
												}
												else {
													player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player is not in your faction!"));
												}
											}
											else if(ranked.get(player.getUniqueId()) == 4){
												player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't remove yourself! Use /f abandon to abandon your faction!"));
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't remove yourself!"));
											}
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player does not exist!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have permission to kick players from your faction!"));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have a faction!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("join")) {
							if(args.length == 2) {
								ArrayList<UUID> invited = new ArrayList<UUID>();
								ArrayList<UUID> members = new ArrayList<UUID>();
								for(ArrayList<UUID> uuid : inviteList.values()) {
									invited.addAll(uuid);
								}
								for(ArrayList<UUID> uuid : factionList.values()) {
									members.addAll(uuid);
								}
								String facName = args[1];
								if(factionNameList.contains(facName)) {
									if(invited.contains(player.getUniqueId())) {
										if(!members.contains(player.getUniqueId())) {
											boolean check = false;
											for(Entry<String, ArrayList<UUID>> entry : factionList.entrySet()) {
												if(entry.getValue().contains(player.getUniqueId())) {
													check = true;
													break;
												}
											}
											if(check != true) {
												factionList.get(facName).add(player.getUniqueId());
												ranked.put(player.getUniqueId(), 1);
												player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have joined &6" + facName));
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cSomething went wrong, please console staff."));
											}
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou are already in a faction!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou haven't been invited to a faction!"));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis faction doesn't exist!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("decline") || args[0].equalsIgnoreCase("refuse")) {
							if(args.length == 2) {
								String facName = args[1];
								if(factionNameList.contains(facName)) {
									if(inviteList.get(facName).contains(player.getUniqueId())) {
										inviteList.get(facName).remove(player.getUniqueId());
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou haven't recieved an invite from &6" + facName + "&a!"));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis faction doesn't exist!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("promote")) {
							if(args.length == 2) {
								if(!fName.equals("")) {
									Player p = Bukkit.getPlayer(args[1]);
									if(p != null) {
										if(p != player) {
											if(factionList.get(fName).contains(p.getUniqueId())) {
												int rankMe = ranked.get(player.getUniqueId());
												int rankOther = ranked.get(player.getUniqueId());
												if(rankMe > rankOther) {
													ranked.put(p.getUniqueId(), ranked.get(p.getUniqueId()) + 1);
													if(ranked.get(p.getUniqueId()) == 2) {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " &ato Member!"));
														p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &aHas promoted you to a Member!"));
													}
													if(ranked.get(p.getUniqueId()) == 3) {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " &ato Officer!"));
														p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &aHas promoted you to a Officer!"));
													}
												}
												else {
													player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have permission to promote this faction member!"));
												}
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player is not in your faction!"));
											}
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't promote yourself!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player has never joined DungeonForge!"));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have a faction!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("demote")) {
							if(args.length == 2) {
								if(!fName.equals("")) {
									Player p = Bukkit.getPlayer(args[1]);
									if(p != null) {
										if(p != player) {
											if(factionList.get(fName).contains(p.getUniqueId())) {
												int rankMe = ranked.get(player.getUniqueId());
												int rankOther = ranked.get(p.getUniqueId());
												if(rankMe > rankOther) {
													ranked.put(p.getUniqueId(), ranked.get(p.getUniqueId()) - 1);
													if(ranked.get(p.getUniqueId()) == 2) {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " &cto Member!"));
														p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &cHas demoted you to a Member!"));
													}
													if(ranked.get(p.getUniqueId()) == 1) {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " &cto Recruit!"));
														p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &cHas demoted you to a Recruit!"));
													}
												}
												else {
													player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have permission to demote this faction member!"));
												}
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player is not in your faction!"));
											}
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't promote yourself!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player has never joined DungeonForge!"));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have a faction!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("top")) {
							if(args.length == 1) {
								player.sendMessage(new ColorCodeTranslator().colorize("&7---------------------&a[&bF TOP&a]&7---------------------"));
								int count = 0;
								for (Map.Entry<String, Integer> pair: fTop.entrySet()) {
									count++;
									if(count < 10) {
										player.sendMessage(new ColorCodeTranslator().colorize("&a&l" + count + ". &bFaction: &6" + pair.getKey() + " &a&l| " + "&bFaction Points: &6" + pair.getValue()));
									}
								}
								count = 0;
								player.sendMessage(new ColorCodeTranslator().colorize("&7---------------------&a[&bF TOP&a]&7--------------------- "));
				    		}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("ally")) {
							if(args.length == 2) {
								if(!fName.equals("")) {
									String allyName = args[1];
									if(!allyList.get(fName).contains(allyName)) {
										if(!invitedAllyList.get(fName).contains(allyName)) {
											boolean check = false;
											for(String s : factionNameList) {
												if(s.equals(allyName)) {
													check = true;
												}
											}
											if(check == true) {
												if(!allyName.equals(fName)) {
													int rank = ranked.get(player.getUniqueId());
													if(rank >= 3) {
														invitedAllyList.get(fName).add(allyName);
														if(invitedAllyList.get(fName).contains(allyName) && invitedAllyList.get(allyName).contains(fName)) {
															invitedAllyList.get(fName).remove(allyName);
															invitedAllyList.get(allyName).remove(fName);
															allyList.get(fName).add(allyName);
															allyList.get(allyName).add(fName);
															for(UUID uuid : factionList.get(fName)) {
																Player p = Bukkit.getPlayer(uuid);
																if(p.isOnline()) {
																	p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYour faction is now allies with &6" + fName));
																	
																}
															}
															for(UUID uuid : factionList.get(allyName)) {
																Player p = Bukkit.getPlayer(uuid);
																if(p.isOnline()) {
																	p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYour faction is now allies with &6" + allyName));
																}
															}
														}
														else {
															player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have requested to ally with &6" + allyName + "&a!"));
															player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aTheir leader/officers have 60 seconds to accept!"));
															for(UUID uuid : factionList.get(fName)) {
																Player p = Bukkit.getPlayer(uuid);
																if(p.isOnline()) {
																	if(ranked.get(p.getUniqueId()) >= 3) {
																		p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &arequested to be allies with &6" + allyName));
																	}
																}
															}
															for(UUID uuid : factionList.get(allyName)) {
																Player p = Bukkit.getPlayer(uuid);
																if(p.isOnline()) {
																	if(ranked.get(p.getUniqueId()) >= 3) {
																		p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &arequested to be allies with your faction!"));
																		p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aDo /f ally &6" + fName + " &ato ally them, if you don't want to be allies then just don't do /f ally &6" + fName));
																		p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have 60 seconds to accept!"));
																	}
																}
															}
														}
													}
													else {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have permission to ally this faction!"));
													}
												}
												else {
													player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cWhat are you... Whatever you can't ally yourself!"));
												}
											}
											else {
												player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis faction doesn't exist!"));
											}
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou already invited this faction to be allies!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou are already allies with this faction!"));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have a faction!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("unally")) {
							if(args.length == 2) {
								if(!fName.equals("")) {
									String allyName = args[1];
									if(allyList.get(fName).contains(allyName)) {
										int rank = ranked.get(player.getUniqueId());
										if(rank >= 3) {
											allyList.get(fName).remove(allyName);
											allyList.get(allyName).remove(fName);
											for(UUID uuid : factionList.get(fName)) {
												Player p = Bukkit.getPlayer(uuid);
												if(p.isOnline()) {
													p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYour faction has unallied with &6" + fName));
													
												}
											}
											for(UUID uuid : factionList.get(allyName)) {
												Player p = Bukkit.getPlayer(uuid);
												if(p.isOnline()) {
													p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYour faction has unallied with &6" + allyName));
												}
											}
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have permission to unally this faction!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou are not allies with this faction!"));
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou don't have a faction!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid Arguments! Use /f or /f help to see the faction commands!"));
						}
						try{
							yml.save(f);
				        }
				        catch(IOException e){
				            e.printStackTrace();
				        }
					}
				}
			}
			else {
				player.sendMessage(new ColorCodeTranslator().colorize("&cYou can't use this command! You are in combat!"));
			}
		}
		return false;
	}
	public void loadFactionNameList(YamlConfiguration yml, File f) {
		factionNameList.addAll(yml.getConfigurationSection("Factions.List").getKeys(false));
	}
	public void loadMemberList(YamlConfiguration yml, File f) {
		factionList.clear();
		String facName = "";
		Set<String> set = yml.getConfigurationSection("Factions.List").getKeys(false);
		ArrayList<String> facNames = new ArrayList<String>(set);
		for(int i = 0; i < facNames.size(); i++) {
			facName = facNames.get(i);
			Set<String> chec = yml.getConfigurationSection("Factions.List." + facName + ".Members").getKeys(false);
			ArrayList<String> facMembers = new ArrayList<String>(chec);
			for(int i1 = 0; i1 < facMembers.size(); i1++) {
				UUID uuid = UUID.fromString(facMembers.get(i1));
				if(factionList.get(facName) == null) {
					factionList.put(facName, new ArrayList<UUID>());
					factionList.get(facName).add(uuid);
				}
				else {
					factionList.get(facName).add(uuid);
				}
			}
		}
	}
	public void loadFactionHomesList(YamlConfiguration yml, File f) {
		fHomes.clear();
		String facName = "";
		Set<String> set = yml.getConfigurationSection("Factions.List").getKeys(false);
		ArrayList<String> facNames = new ArrayList<String>(set);
		for(int i = 0; i < facNames.size(); i++) {
			facName = facNames.get(i);
			Location loc = (Location) yml.get("Factions.List." + facName + ".Faction Home");
			fHomes.put(facName, loc);
		}
	}
	public void loadChunkList(YamlConfiguration yml, File f) {
		chunkList.clear();
		String facName = "";
		Set<String> set = yml.getConfigurationSection("Factions.List").getKeys(false);
		ArrayList<String> facNames = new ArrayList<String>(set);
		for(int i = 0; i < facNames.size(); i++) {
			facName = facNames.get(i);
			ArrayList<String> chec = new ArrayList<String>(yml.getStringList("Factions.List." + facName + ".Chunks List"));
			chunkList.put(facName, new ArrayList<Chunk>());
			for(int i1 = 0; i1 < chec.size(); i1++) {
				String string = chec.get(i1);
				String split[] = string.split("z");
				String x1 = split[0].replaceAll("[^\\d-.]", "");
				String z1 = split[1].replaceAll("[^\\d-.]", "");
				int x = Integer.parseInt(x1);
				int z = Integer.parseInt(z1);
				Chunk chunk = Bukkit.getWorld("FactionWorld-1").getChunkAt(x, z);
				if(chunkList.get(facName) == null) {
					chunkList.put(facName, new ArrayList<Chunk>());
					chunkList.get(facName).add(chunk);
				}
				else {
					chunkList.get(facName).add(chunk);
				}
			}
		}
	}
	public void loadChunkTotalList(YamlConfiguration yml, File f) {
		String facName = "";
		Set<String> set = yml.getConfigurationSection("Factions.List").getKeys(false);
		ArrayList<String> facNames = new ArrayList<String>(set);
		for(int i = 0; i < facNames.size(); i++) {
			facName = facNames.get(i);
			int total = yml.getInt("Factions.List." + facName + ".Chunks");
			chunkTotal.put(facName, total);
		}
	}
	public void loadRankedList(YamlConfiguration yml, File f) {
		String facName = "";
		Set<String> set = yml.getConfigurationSection("Factions.List").getKeys(false);
		ArrayList<String> facNames = new ArrayList<String>(set);
		for(int i = 0; i < facNames.size(); i++) {
			facName = facNames.get(i);
			Set<String> check = yml.getConfigurationSection("Factions.List." + facName + ".Members").getKeys(false);
			ArrayList<String> player = new ArrayList<String>(check);
			for(int i1 = 0; i1 < player.size(); i1++) {
				int rank = yml.getInt("Factions.List." + facName + ".Members." + player.get(i1) + ".Rank");
				ranked.put(UUID.fromString(player.get(i1)), rank);
			}
		}
	}
	public void loadAllyList(YamlConfiguration yml, File f) {
		String facName = "";
		Set<String> set = yml.getConfigurationSection("Factions.List").getKeys(false);
		ArrayList<String> facNames = new ArrayList<String>(set);
		for(int i = 0; i < facNames.size(); i++) {
			facName = facNames.get(i);
			ArrayList<String> fAllyList = new ArrayList<String>(yml.getStringList("Factions.List." + facName + ".Allies"));
			for(int i1 = 0; i1 < fAllyList.size(); i1++) {
				if(allyList.get(facName) == null) {
					allyList.put(facName, new ArrayList<String>());
					allyList.get(facName).add(fAllyList.get(i1));
				}
				else {
					allyList.get(facName).add(fAllyList.get(i1));
				}
			}
		}
	}
	public void loadPlayersAlliedList(YamlConfiguration yml, File f) {
		String facName = "";
		Set<String> set = yml.getConfigurationSection("Factions.List").getKeys(false);
		ArrayList<String> facNames = new ArrayList<String>(set);
		for(int i = 0; i < facNames.size(); i++) {
			facName = facNames.get(i);
			ArrayList<String> fAllyList = new ArrayList<String>(yml.getStringList("Factions.List." + facName + ".Allies"));
			for(int i1 = 0; i1 < fAllyList.size(); i1++) {
				if(playerAlliedList.get(facName) == null) {
					playerAlliedList.put(facName, new ArrayList<UUID>());
					playerAlliedList.get(facName).addAll(factionList.get(facName));
				}
				else {
					playerAlliedList.get(facName).addAll(factionList.get(facName));
				}
			}
		}
	}
	public void loadFTop(YamlConfiguration yml, File f) {
		fTop.clear();
		String facName = "";
		Set<String> set = yml.getConfigurationSection("Factions.List").getKeys(false);
		ArrayList<String> facNames = new ArrayList<String>(set);
		for(int i = 0; i < facNames.size(); i++) {
			facName = facNames.get(i);
			int fPoints = yml.getInt("Factions.List." + facName + ".Faction Points");
			fTop.put(facName, fPoints);
		}
		Object[] a = fTop.entrySet().toArray();
		Arrays.sort(a, new Comparator<Object>() {
		    public int compare(Object o1, Object o2) {
		        return ((Map.Entry<String, Integer>) o2).getValue()
		                   .compareTo(((Map.Entry<String, Integer>) o1).getValue());
		    }
		});
		
	}
	public HashMap<String, Integer> getFTop() {
		return DFFactions.fTop;
	}
	public HashMap<String, Location> getFHomes() {
		return DFFactions.fHomes;
	}
	public HashMap<String, ArrayList<Chunk>> getChunkList() {
		return DFFactions.chunkList;
	}
	public HashMap<String, Integer> getTotalChunkList() {
		return DFFactions.chunkTotal;
	}
	public ArrayList<String> getFactionNameList() {
		return DFFactions.factionNameList;
	}
	public HashMap<String, ArrayList<UUID>> getFactionMemberList() {
		return DFFactions.factionList;
	}
	public HashMap<UUID, Integer> getRankedList() {
		return DFFactions.ranked;
	}
	public HashMap<String, ArrayList<String>> getAllyList() {
		return DFFactions.allyList;
	}
	public HashMap<String, ArrayList<UUID>> getPlayerAllyList() {
		return DFFactions.playerAlliedList;
	}
	public boolean isTeammate(UUID uuid1, UUID uuid2) {
		boolean isTeammate = false;
		for(Entry<String, ArrayList<UUID>> entry : DFFactions.factionList.entrySet()) {
			if(entry.getValue().contains(uuid1) && entry.getValue().contains(uuid2)) {
				isTeammate = true;
			}
		}
		return isTeammate;
	}
}
				

