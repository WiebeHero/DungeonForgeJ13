package me.WiebeHero.Factions;

import java.util.ArrayList;
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
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.MoreStuff.CombatTag;

public class DFFactions implements Listener,CommandExecutor{
	String idNew = "";
	public String faction = "faction";
	public DFFaction method = new DFFaction();
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
				if(!(cmd.getName().equalsIgnoreCase(label)) || cmd.getName().equalsIgnoreCase(faction)) {
					if(player.getWorld().getName().equals("DFWarzone-1") || player.getWorld().getName().equals("FactionWorld-1")) {
						DFFaction faction = method.getFaction(player.getUniqueId());
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
							    	if(facName.length() >= 4 || facName.length() <= 20) {
							    		if(faction == null) {
							    			if(method.isNameAvailable(facName)) {
									    		player.sendMessage(new ColorCodeTranslator().colorize("&aYou have created your faction &6" + facName));
									    		CustomEnchantments.getInstance().factionList.add(new DFFaction(facName, player));
									    		CustomEnchantments.getInstance().score.generateScoreboard(player);
						    				}
							    			else {
								    			player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis faction name is already taken!"));
								    		}
								        }
							    		else {
							    			player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou are already in a faction!"));
							    		}
						    		}
							    	else {
							    		player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYour faction name MUST be more then 4 characters!"));
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
								if(faction != null) {
									int rank = faction.getRank(player.getUniqueId());
									if(rank == 4) {
										for(UUID uuid : faction.getMemberList().keySet()) {
											Player p = Bukkit.getPlayer(uuid);
											if(p != null && p != player) {
												p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " has abandoned the faction!"));
											}
										}
										method.deleteFaction(faction.getName());
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have abandoned your faction!"));
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
								if(faction != null) {
			    					ArrayList<UUID> mList = new ArrayList<UUID>();
			    					mList.addAll(faction.getMemberList().keySet());
			    					ArrayList<String> listOnline = new ArrayList<String>();
			    					ArrayList<String> listOffline = new ArrayList<String>();
			    					for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
		    							for(int i2 = 0; i2 < mList.size(); i2++) {
		    								if(mList.get(i2).equals(p.getUniqueId())) {
		    									int finalNumber = faction.getRank(p.getUniqueId());
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
			    					int cClaimed = faction.getChunkList().size();
			    					player.sendMessage(new ColorCodeTranslator().colorize("&7------------------&a[&b" + faction.getName() + "&a]&7------------------"));
			    					player.sendMessage(new ColorCodeTranslator().colorize("&7Faction Name: &b" + faction.getName()));
									player.sendMessage(new ColorCodeTranslator().colorize("&7Total Members: &b" + mList.size()));
									player.sendMessage(new ColorCodeTranslator().colorize("&7Chunks Claimed: &b" + cClaimed));
									player.sendMessage(new ColorCodeTranslator().colorize("&7Members Online: &b" + listOnline));
									player.sendMessage(new ColorCodeTranslator().colorize("&7Members Offline: &b" + listOffline));
									if(faction.getFactionHome() != null) {
										player.sendMessage(new ColorCodeTranslator().colorize("&7Faction Home: &bSet at " + "&6X: " + faction.getFactionHome().getX() + " Y: " + faction.getFactionHome().getY() + " Z: " + faction.getFactionHome().getZ()));
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&7Faction Home: &cNot Set"));
									}
									player.sendMessage(new ColorCodeTranslator().colorize("&7------------------&a[&b" + faction.getName() + "&a]&7------------------"));
			    				}
			    				else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
								}
							}
						}
						else if(args[0].equalsIgnoreCase("claim")) {
							if(args.length == 1) {
								if(player.getWorld().getName().equals("FactionWorld-1")) {
									if(faction != null) {
										if(!faction.isInAChunk(player)) {
											if(!faction.isInChunk(player)) {
												int rank = faction.getRank(player.getUniqueId());
												if(rank >= 3) {
													int chunkTotal = faction.getChunkList().size();
													int maxChunks = faction.getMemberList().size() + 3;
													if(maxChunks > 16) {
														maxChunks = 16;
													}
													if(chunkTotal == 0) {
														if(faction.getChunkList().size() < maxChunks) {
															if(faction.getEnergy() > faction.getChunkList().size() + 1) {
																faction.addChunk(player.getChunk());
																player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have claimed this chunk!"));
																CustomEnchantments.getInstance().score.generateScoreboard(player);
															}
															else {
																player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou do not have enough energy to claim more chunks!"));
															}
														}
														else {
															player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have claimed the maximum amount of chunks! Get more faction members to claim more!"));
														}
													}
													else {
														Location loc = player.getLocation();
														Location tempLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
														tempLoc.add(16.00, 0.00, 0.00);
														Chunk chunk1 = player.getWorld().getChunkAt(tempLoc);
														tempLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
														tempLoc.add(0.00, 0.00, 16.00);
														Chunk chunk2 = player.getWorld().getChunkAt(tempLoc);
														tempLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
														tempLoc.add(-16.00, 0.00, 0.00);
														Chunk chunk3 = player.getWorld().getChunkAt(tempLoc);
														tempLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
														tempLoc.add(0.00, 0.00, -16.00);
														Chunk chunk4 = player.getWorld().getChunkAt(tempLoc);
														if(faction.getChunkList().contains(chunk1) || faction.getChunkList().contains(chunk2) || faction.getChunkList().contains(chunk3) || faction.getChunkList().contains(chunk4)) {
															if(faction.getChunkList().size() < maxChunks) {
																if(faction.getEnergy() > faction.getChunkList().size() + 1) {
																	faction.addChunk(player.getChunk());
																	player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have claimed this chunk!"));
																	CustomEnchantments.getInstance().score.generateScoreboard(player);
																}
																else {
																	player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou do not have enough energy to claim more chunks!"));
																}
															}
															else {
																player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have claimed the maximum amount of chunks! Get more faction members to claim more!"));
															}
														}
														else {
															player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cClaim a chunk that is near your area!"));
														}
													}
												}
												else {
													player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou dont have permission to claim chunks!"));
												}
											}
											else {
						    					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis chunk is already claimed by your faction!"));
											}
				    					}
										else {
					    					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis chunk is already claimed by another faction!"));
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
										if(faction != null) {
											int rank = faction.getRank(player.getUniqueId());
											if(rank >= 3) {
												if(faction.isInChunk(player)) {
													faction.removeChunk(player.getChunk());
													player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have unclaimed this chunk!"));
												}
												else if(faction.isInAChunk(player)){
													DFFaction fac = null;
													for(DFFaction f : CustomEnchantments.getInstance().factionList) {
														if(f.getChunkList().contains(player.getChunk())) {
															fac = f;
														}
													}
													if(fac != null) {
														if(fac.getEnergy() < fac.getChunkList().size()) {
															fac.removeChunk(player.getChunk());
															for(UUID id : fac.getMemberList().keySet()) {
																Player p = Bukkit.getPlayer(id);
																if(p != null) {
																	p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &cunclaimed 1 of your chunks!"));
																}
															}
															player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have unclaimed 1 chunk of the faction &6" + fac.getName()));
														}
														else {
															player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou can't unclaim this chunk of &6" + fac.getName() + " &cbecause they still have enough energy to keep this chunk!"));
														}
													}
													else {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis faction doesn't exis... wait a minute somethin's not right here."));
													}
												}
												else {
													player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis chunk is not claimed!"));
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
						else if(args[0].equalsIgnoreCase("power")) {
							if(args.length == 1) {
								if(faction != null) {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYour faction has &6" + String.format("%.2f", faction.getEnergy()) + " &aenergy"));
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
								}
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("sethome")) {
							if(args.length == 1) {
								if(player.getWorld().getName().equals("FactionWorld-1")) {
									if(faction != null) {
										int rank = faction.getRank(player.getUniqueId());
										if(rank >= 3) {
											if(faction.isInChunk(player)) {
												faction.setFactionHome(player.getLocation());
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
								if(faction != null) {
									int rank = faction.getRank(player.getUniqueId());
									if(rank >= 2) {
										new BukkitRunnable() {
											int count = 10;
											@Override
											public void run() {
												if(player.getLocation().getX() == locX && player.getLocation().getZ() == locZ) {
													player.sendMessage(new ColorCodeTranslator().colorize("&aTeleporting to faction home in &b" + count + "..."));
													count--;
													if(count == 0) {
														player.teleport(faction.getFactionHome());
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
								if(faction != null) {
									int rank = faction.getRank(player.getUniqueId());
									if(rank >= 3) {
										Player p = Bukkit.getPlayer(args[1]);
										if(p != null) {
											if(p.isOnline()) {
												if(p != player) {
													if(faction.isAMember(p.getUniqueId())) {
														if(faction.isMember(p.getUniqueId())) {
															faction.addInvite(p.getUniqueId());
															player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have invited &6" + p.getName() + " &ato your faction"));
															player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aThey have &b60 seconds &ato accept!"));
															p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ainvited you to &6" + faction.getName() + "&a!"));
															p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have 60 seconds to accept!"));
															p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aType /f accept/join &6" + player.getName() + " to join &6" + faction.getName() +"&a, type /f decline/refuse " + player.getName() + " to decline the invitation!"));
															p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aType /f accept/join &6" + faction.getName() + " to join &6" + faction.getName() +"&a, type /f decline/refuse " + faction.getName() + " to decline the invitation!"));
															new BukkitRunnable() {
																public void run() {
																	if(faction.getInvitedList().contains(p.getUniqueId())) {
																		faction.removeInvite(p.getUniqueId());
																		p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cDue to you not responding, the invitation from &6" + player.getName() + " &chas expired!"));
																		player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cDue to &6" + p.getName() + " &cnot responding, your invitation has expired!"));
																	}
																}
															}.runTaskLater(CustomEnchantments.getInstance(), 1200L);
														}
														else {
															player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player is already in your faction!"));
														}
													}
													else {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player is already in a faction!"));
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
								if(faction != null) {
									if(faction.getRank(player.getUniqueId()) != 4) {
										faction.removeMember(player);
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have left &6" + faction.getName()));
										for(UUID id : faction.getMemberList().keySet()) {
											Player p = Bukkit.getPlayer(id);
											if(player != null) {
												p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas left the faction!"));
											}
										}
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
								if(faction != null) {
									int rank = faction.getRank(player.getUniqueId());
									if(rank >= 3) {
										Player p = Bukkit.getPlayer(args[1]);
										if(p != null) {
											if(p != player) {
												if(faction.isMember(p.getUniqueId())) {
													int rankOther = faction.getRank(p.getUniqueId());
													if(rank > rankOther) {
														faction.removeMember(p);
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have kicked &6" + p.getName() + " &afrom the faction!"));
														if(p.isOnline()) {
															p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ckicked you from the faction!"));
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
											else if(faction.getRank(player.getUniqueId()) == 4){
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
								String string = args[1];
								Player p = Bukkit.getPlayer(string);
								if(p != null) {
									if(method.getFaction(p.getUniqueId()) != null) {
										DFFaction fac = method.getFaction(p.getUniqueId());
										if(fac.isInvited(player.getUniqueId())) {
											p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas joined the faction!"));
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have joined " + fac.getName()));
											for(UUID id : fac.getMemberList().keySet()) {
												Player pl = Bukkit.getPlayer(id);
												if(pl != null && pl != p) {
													pl.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas joined the faction!"));
												}
											}
											fac.removeInvite(player.getUniqueId());
											fac.addMember(player);
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou haven't recieved an invite from " + p.getName() + "&a!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &cIsn't part of a faction!"));
									}
								}
								else {
									DFFaction fac = method.getFaction(string);
									if(fac != null) {
										if(fac.isInvited(player.getUniqueId())) {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have joined &6" + fac.getName() + "&a!"));
											for(UUID id : fac.getMemberList().keySet()) {
												Player pl = Bukkit.getPlayer(id);
												if(pl != null) {
													pl.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas joined the faction!"));
												}
											}
											fac.removeInvite(player.getUniqueId());
											fac.addMember(player);
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou haven't been invited to this faction!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis faction doesn't exist!"));
									}
								}
								
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("decline") || args[0].equalsIgnoreCase("refuse")) {
							if(args.length == 2) {
								String string = args[1];
								Player p = Bukkit.getPlayer(string);
								if(p != null) {
									if(method.getFaction(p.getUniqueId()) != null) {
										DFFaction fac = method.getFaction(p.getUniqueId());
										if(fac.isInvited(player.getUniqueId())) {
											fac.removeInvite(player.getUniqueId());
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have denied the request to join &6" + p.getName() + "'s &afaction!"));
											p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas denied your request to join your faction!"));
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou haven't recieved an invite from &6" + p.getName() + "&a!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis faction doesn't exist!"));
									}
								}
								else {
									DFFaction fac = method.getFaction(string);
									if(fac != null) {
										if(fac.isInvited(player.getUniqueId())) {
											fac.removeInvite(player.getUniqueId());
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have denied the request to join the faction &6" + fac.getName() + "&a!"));
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou haven't been invited to this faction!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis faction doesn't exist!"));
									}
								}
								
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("promote")) {
							if(args.length == 2) {
								if(faction != null) {
									Player p = Bukkit.getPlayer(args[1]);
									if(p != null) {
										if(p != player) {
											if(faction.isMember(p.getUniqueId())) {
												int rankMe = faction.getRank(player.getUniqueId());
												int rankOther = faction.getRank(p.getUniqueId());
												if(rankMe > rankOther) {
													faction.promoteMember(p.getUniqueId());
													if(faction.getRank(p.getUniqueId()) == 2) {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " &ato Member!"));
														p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &aHas promoted you to a Member!"));
													}
													if(faction.getRank(p.getUniqueId()) == 3) {
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
								if(faction != null) {
									Player p = Bukkit.getPlayer(args[1]);
									if(p != null) {
										if(p != player) {
											if(faction.isMember(p.getUniqueId())) {
												int rankMe = faction.getRank(player.getUniqueId());
												int rankOther = faction.getRank(p.getUniqueId());
												if(rankMe > rankOther) {
													faction.demoteMember(p.getUniqueId());
													if(faction.getRank(p.getUniqueId()) == 2) {
														player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " &cto Member!"));
														p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &cHas demoted you to a Member!"));
													}
													if(faction.getRank(p.getUniqueId()) == 1) {
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
						else if(args[0].equalsIgnoreCase("ally")) {
							if(args.length == 2) {
								if(faction != null) {
									String allyName = args[1];
									DFFaction fac = method.getFaction(allyName);
									if(fac != null) {
										if(!faction.isAlly(fac.getName())) {
											if(!faction.isInvitedAlly(fac.getName())) {
												if(!faction.getName().equals(fac.getName())) {
													int rank = faction.getRank(player.getUniqueId());
													if(rank >= 3) {
														fac.addInvitedAlly(fac.getName());
														if(faction.isInvitedAlly(fac.getName()) && fac.isInvitedAlly(faction.getName())) {
															faction.removeInvitedAlly(fac.getName());
															fac.removeInvitedAlly(faction.getName());
															faction.addAlly(fac.getName());
															fac.addAlly(faction.getName());
															for(UUID uuid : faction.getMemberList().keySet()) {
																Player p = Bukkit.getPlayer(uuid);
																if(p.isOnline()) {
																	p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYour faction is now allies with &6" + fac.getName()));
																	
																}
															}
															for(UUID uuid : fac.getMemberList().keySet()) {
																Player p = Bukkit.getPlayer(uuid);
																if(p.isOnline()) {
																	p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYour faction is now allies with &6" + faction.getName()));
																}
															}
														}
														else {
															player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have requested to ally with &6" + allyName + "&a!"));
															player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aTheir leader/officers have 60 seconds to accept!"));
															for(UUID uuid : faction.getMemberList().keySet()) {
																Player p = Bukkit.getPlayer(uuid);
																if(p.isOnline()) {
																	if(faction.getRank(p.getUniqueId()) >= 3) {
																		p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &arequested to be allies with &6" + allyName));
																	}
																}
															}
															for(UUID uuid : fac.getMemberList().keySet()) {
																Player p = Bukkit.getPlayer(uuid);
																if(p.isOnline()) {
																	if(fac.getRank(p.getUniqueId()) >= 3) {
																		p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + faction.getName() + " &arequested to be allies with your faction!"));
																		p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aDo /f ally &6" + faction.getName() + " &ato ally them, if you don't want to be allies then just don't do /f ally &6" + faction.getName()));
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
												player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou already invited this faction to be allies!"));
											}
										}
										else {
											player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou are already allies with this faction!"));
										}
									}
									else {
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis faction doesn't exist!"));
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
								if(faction != null) {
									String allyName = args[1];
									DFFaction fac = method.getFaction(allyName);
									if(fac != null) {
										if(faction.isAlly(fac.getName())) {
											int rank = faction.getRank(player.getUniqueId());
											if(rank >= 3) {
												faction.removeAlly(fac.getName());
												fac.removeAlly(faction.getName());
												for(UUID uuid : faction.getMemberList().keySet()) {
													Player p = Bukkit.getPlayer(uuid);
													if(p.isOnline()) {
														p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYour faction has unallied with &6" + fac.getName()));
														
													}
												}
												for(UUID uuid : fac.getMemberList().keySet()) {
													Player p = Bukkit.getPlayer(uuid);
													if(p.isOnline()) {
														p.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYour faction has unallied with &6" + faction.getName()));
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
										player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis faction does not exist!"));
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
					}
				}
			}
			else {
				player.sendMessage(new ColorCodeTranslator().colorize("&cYou can't use this command! You are in combat!"));
			}
		}
		return false;
	}
}
				

