package me.WiebeHero.Factions;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.MoreStuff.CombatTag;
import me.WiebeHero.Scoreboard.DFScoreboard;
import me.WiebeHero.Scoreboard.WGMethods;

public class DFFactions implements Listener,CommandExecutor{
	public String faction = "faction";
	private DFFactionManager facManager;
	private DFFactionPlayerManager facPlayerManager;
	private DFScoreboard board;
	private DFPlayerManager dfManager;
	private WGMethods wg;
	private ArrayList<String> spawning;
	public DFFactions(DFFactionManager facManager, DFFactionPlayerManager facPlayerManager, DFScoreboard board, DFPlayerManager dfManager, WGMethods wg) {
		this.facManager = facManager;
		this.facPlayerManager = facPlayerManager;
		this.board = board;
		this.dfManager = dfManager;
		this.wg = wg;
		this.spawning = new ArrayList<String>();
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
				if(!(cmd.getName().equalsIgnoreCase(label)) || cmd.getName().equalsIgnoreCase(faction)) {
					if(player.getWorld().getName().equals("DFWarzone-1") || player.getWorld().getName().equals("FactionWorld-1")) {
						DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(player.getUniqueId());
						DFFaction faction = facManager.getFaction(facPlayer.getFactionId());
						if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
							player.sendMessage(new CCT().colorize("&6)------------------=[&bHelp&6]=------------------("));
							player.sendMessage(new CCT().colorize("&b/f create | Create a faction."));
							player.sendMessage(new CCT().colorize("&b/f claim/unclaim | Claim/Unclaim a chunk of your faction"));
							player.sendMessage(new CCT().colorize("&b/f unclaim all | Unclaim all chunks of your faction"));
							player.sendMessage(new CCT().colorize("&b/f help | Shows faction commands."));
							player.sendMessage(new CCT().colorize("&b/f top | Shows how many F points you have."));
							player.sendMessage(new CCT().colorize("&b/f promote/demote (Player Name)| Promote or demote a player in you faction."));
							player.sendMessage(new CCT().colorize("&b/f invite/add (Player Name) | Invite a player to your faction."));
							player.sendMessage(new CCT().colorize("&b/f kick/remove (Player Name) | Kick a player from your faction."));
							player.sendMessage(new CCT().colorize("&b/f leave | Leave a faction."));
							player.sendMessage(new CCT().colorize("&b/f abandon | Abandon/Delete your faction."));
							player.sendMessage(new CCT().colorize("&b/f join/accept | Join a faction that you have been invited to."));
							player.sendMessage(new CCT().colorize("&b/f decline/refuse | Refuse to join a faction that invited you."));
							player.sendMessage(new CCT().colorize("&b/f list | Check current information about your faction."));
							player.sendMessage(new CCT().colorize("&b/f sethome | Sets the home of your faction."));
							player.sendMessage(new CCT().colorize("&b/f home | Teleports you to the faction home, if there is one."));
							player.sendMessage(new CCT().colorize("&6)------------------=[&bHelp&6]=------------------("));
						}
						else if(args[0].equalsIgnoreCase("create")) {
							if(args.length == 2) {
								String facName = args[1];
								Pattern p = Pattern.compile( "[0-9]" );
							    Matcher m = p.matcher(facName);
							    if(m.find() == false && facName.indexOf("_-=+[]{}:;''<>/?!@#$%^&*()") == -1) {
							    	if(facName.length() >= 4 || facName.length() <= 20) {
							    		if(faction == null) {
							    			if(facManager.isNameAvailable(facName)) {
									    		player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have created your faction &6" + facName));
									    		DFFaction fac = new DFFaction(facName, player, facManager, facPlayerManager);
									    		facManager.add(fac);
									    		board.updateScoreboard(player);
						    				}
							    			else {
								    			player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis faction name is already taken!"));
								    		}
								        }
							    		else {
							    			player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are already in a faction!"));
							    		}
						    		}
							    	else {
							    		player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour faction name MUST be more then 4 characters!"));
							    	}
						    	}
							    else{
							    	player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour faction MUST NOT contain any strange symbols!"));
							    }
						    }
						    else {
						    	player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour faction name can NOT be nothing!"));
							}
						}
						else if(args[0].equalsIgnoreCase("abandon")) {
							if(args.length == 1) {
								if(faction != null) {
									int rank = facPlayer.getRank();
									if(rank == 4) {
										facPlayer.setFactionId(null);
										facPlayer.setRank(1);
										board.updateScoreboard(player);
										for(Entry<UUID, DFFactionPlayer> entry : facPlayerManager.getFactionPlayerMap().entrySet()) {
											if(entry.getValue().getFactionId() != null) {
												if(faction.getFactionId().equals(entry.getValue().getFactionId())) {
													Player p = Bukkit.getPlayer(entry.getKey());
													if(p != null && p != player) {
														p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " has abandoned the faction!"));
														DFFactionPlayer facP = facPlayerManager.getFactionPlayer(p);
														facP.setFactionId(null);
														facP.setRank(1);
														board.updateScoreboard(player);
													}
													else {
														DFFactionPlayer facP = facPlayerManager.getFactionPlayer(p);
														facP.setFactionId(null);
														facP.setRank(1);
														board.updateScoreboard(player);
													}
												}
											}
										}
										facManager.remove(faction.getFactionId());
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have abandoned your faction!"));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou dont have permission to abandon this faction!"));
									}
								}
		    					else {
			    					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have a faction!"));
			    				}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("list")) {
							if(args.length > 0) {
								if(faction != null) {
			    					ArrayList<String> listOnline = new ArrayList<String>();
			    					ArrayList<String> listOffline = new ArrayList<String>();
			    					int size = 0;
			    					for(Entry<UUID, DFFactionPlayer> entry : facPlayerManager.getFactionPlayerMap().entrySet()) {
			    						if(entry.getValue().getFactionId() != null) {
				    						if(entry.getValue().getFactionId().equals(faction.getFactionId())) {
				    							size++;
				    							OfflinePlayer p = Bukkit.getOfflinePlayer(entry.getKey());
		    									int finalNumber = entry.getValue().getRank();
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
			    					player.sendMessage(new CCT().colorize("&7------------------&a[&b" + faction.getName() + "&a]&7------------------"));
			    					player.sendMessage(new CCT().colorize("&7Faction Name: &b" + faction.getName()));
									player.sendMessage(new CCT().colorize("&7Total Members: &b" + size));
									player.sendMessage(new CCT().colorize("&7Chunks Claimed: &b" + cClaimed));
									player.sendMessage(new CCT().colorize("&7Members Online: &b" + listOnline));
									player.sendMessage(new CCT().colorize("&7Members Offline: &b" + listOffline));
									if(faction.getFactionHome() != null) {
										player.sendMessage(new CCT().colorize("&7Faction Home: &bSet at " + "&6X: " + faction.getFactionHome().getX() + " Y: " + faction.getFactionHome().getY() + " Z: " + faction.getFactionHome().getZ()));
									}
									else {
										player.sendMessage(new CCT().colorize("&7Faction Home: &cNot Set"));
									}
									player.sendMessage(new CCT().colorize("&7------------------&a[&b" + faction.getName() + "&a]&7------------------"));
			    				}
			    				else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
								}
							}
						}
						else if(args[0].equalsIgnoreCase("bank")) {
							if(args.length == 3) {
								if(args[1].equalsIgnoreCase("deposit")) {
									if(facPlayer.getRank() >= 2) {
										DFPlayer dfPlayer = dfManager.getEntity(player);
										double money = 0.00;
										try {
											money = Double.parseDouble(args[1]);
										}
										catch(NumberFormatException ex){
											System.out.print(ex);
										}
										if(money <= 0.00) {
											if(dfPlayer.getMoney() >= money) {
												dfPlayer.removeMoney(money);
												faction.addBank(money);
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have deposited " + money + "$!"));
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough money!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid amount of money."));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to perform this action!"));
									}
								}
								else if(args[1].equalsIgnoreCase("withdraw")) {
									if(facPlayer.getRank() >= 2) {
										DFPlayer dfPlayer = dfManager.getEntity(player);
										double money = 0.00;
										try {
											money = Double.parseDouble(args[1]);
										}
										catch(NumberFormatException ex){
											System.out.print(ex);
										}
										if(money <= 0.00) {
											if(faction.getBank() >= money) {
												dfPlayer.addMoney(money);
												faction.removeBank(money);
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have deposited " + money + "$!"));
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough money!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid amount of money."));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to perform this action!"));
									}
								}
							}
						}
						else if(args[0].equalsIgnoreCase("claim")) {
							if(args.length == 1) {
								if(player.getWorld().getName().equals("FactionWorld-1")) {
									if(faction != null) {
										if(!facManager.isInAChunk(player)) {
											if(!faction.isInChunk(player)) {
												int rank = facPlayer.getRank();
												if(rank >= 3) {
													int chunkTotal = faction.getChunkList().size();
													int maxChunks = 16;
													if(chunkTotal == 0) {
														if(faction.getChunkList().size() < maxChunks) {
															if(faction.getEnergy() > faction.getChunkList().size() + 1) {
																faction.addChunk(player.getChunk());
																player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have claimed this chunk!"));
																board.updateScoreboard(player);
															}
															else {
																player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have enough energy to claim more chunks!"));
															}
														}
														else {
															player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have claimed the maximum amount of chunks! Get more faction members to claim more!"));
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
														if(faction.getChunkList().contains(chunk1.getChunkKey()) || faction.getChunkList().contains(chunk2.getChunkKey()) || faction.getChunkList().contains(chunk3.getChunkKey()) || faction.getChunkList().contains(chunk4.getChunkKey())) {
															if(faction.getChunkList().size() < maxChunks) {
																if(faction.getEnergy() > faction.getChunkList().size() + 1) {
																	faction.addChunk(player.getChunk());
																	player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have claimed this chunk!"));
																	board.updateScoreboard(player);
																}
																else {
																	player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have enough energy to claim more chunks!"));
																}
															}
															else {
																player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have claimed the maximum amount of chunks! Get more faction members to claim more!"));
															}
														}
														else {
															player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cClaim a chunk that is near your area!"));
														}
													}
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou dont have permission to claim chunks!"));
												}
											}
											else {
						    					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis chunk is already claimed by your faction!"));
											}
				    					}
										else {
					    					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis chunk is already claimed by another faction!"));
										}
					    			}
				    				else {
				    					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't claim here!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("unclaim")) {
							if(args.length == 1) {
								if(player.getWorld().getName().equals("FactionWorld-1")) {
									if(faction != null) {
										int rank = facPlayer.getRank();
										if(rank >= 3) {
											if(faction.isInChunk(player)) {
												faction.removeChunk(player.getChunk());
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have unclaimed this chunk!"));
											}
											else if(facManager.isInAChunk(player)){
												DFFaction fac = null;
												for(DFFaction f : facManager.getFactionMap().values()) {
													if(f.getChunkList().contains(player.getChunk().getChunkKey())) {
														fac = f;
													}
												}
												if(fac != null) {
													if(fac.getEnergy() < fac.getChunkList().size()) {
														fac.removeChunk(player.getChunk());
														if(fac.getFactionHome() != null) {
															if(fac.getFactionHome().getChunk().getChunkKey() == player.getChunk().getChunkKey()) {
																fac.setFactionHome(null);
															}
														}
														for(UUID id : facPlayerManager.getFactionPlayerMap().keySet()) {
															Player p = Bukkit.getPlayer(id);
															if(p != null) {
																p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &cunclaimed 1 of your chunks!"));
															}
														}
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have unclaimed 1 chunk of the faction &6" + fac.getName()));
													}
													else {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't unclaim this chunk of &6" + fac.getName() + " &cbecause they still have enough energy to keep this chunk!"));
													}
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis faction doesn't exist... wait a minute somethin's not right here."));
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis chunk is not claimed!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou dont have permission to unclaim chunks!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't unclaim here!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
								}
							}
							else if(args.length == 2) {
								if(args[1].equalsIgnoreCase("all")) {
									if(player.getWorld().getName().equals("FactionWorld-1")) {
										if(faction != null) {
											int rank = facPlayer.getRank();
											if(rank >= 3) {
												if(!faction.getChunkList().isEmpty()) {
													faction.setFactionHome(null);
													faction.clearChunks();
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have unclaimed all of your factions chunks!"));
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour faction doesn't own any chunks!"));
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou dont have permission to unclaim chunks!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't unclaim here!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("power")) {
							if(args.length == 1) {
								if(faction != null) {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYour faction has &6" + String.format("%.2f", faction.getEnergy()) + " &aenergy"));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("sethome")) {
							if(args.length == 1) {
								if(player.getWorld().getName().equals("FactionWorld-1")) {
									if(faction != null) {
										int rank = facPlayer.getRank();
										if(rank >= 3) {
											if(faction.isInChunk(player)) {
												faction.setFactionHome(player.getLocation());
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have set your faction home!"));
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't set your faction home outside of your territory!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou dont have permission to set a home for your faction!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't set your faction home in this world!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("home")) {
							if(args.length == 1) {
								Location loc = player.getLocation();
								if(faction != null) {
									final DFFaction fac = faction;
									int rank = facPlayer.getRank();
									if(rank >= 2) {
										if(fac.getFactionHome() != null) {
											if(wg.isInZone(player, "spawn")) {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTeleporting!"));
												player.teleport(fac.getFactionHome());
											}
											else {
												new BukkitRunnable() {
													int count = 200;
													int temp = 0;
													@Override
													public void run() {
														if(loc.getWorld().getName().equals(player.getWorld().getName())) {
															if(loc.distance(player.getLocation()) <= 0.1) {
																if(temp == 0) {
																	temp = temp - 20;
																	if(count / 20 != 0) {
																		player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aSending you to your faction home in " + (count / 20) + "..."));
																	}
																}
																if(count == 0) {
																	player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTeleporting!"));
																	player.teleport(fac.getFactionHome());
																	count = 10;
																	spawning.remove(player.getUniqueId().toString());
																	cancel();
																}
															}
															else {
																player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCancelled teleporting because of you moving."));
																cancel();
																spawning.remove(player.getUniqueId().toString());
															}
														}
														else {
															player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCancelled teleporting because of you moving."));
															cancel();
															spawning.remove(player.getUniqueId().toString());
														}
														count--;
														temp++;
													}	
												}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have set your faction home!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou dont have permission to teleport to your faction home!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("invite")) {
							if(args.length == 2) {
								if(faction != null) {
									int rank = facPlayer.getRank();
									if(rank >= 3) {
										Player p = Bukkit.getPlayer(args[1]);
										if(p != null) {
											if(p.isOnline()) {
												if(p != player) {
													if(!facManager.isAMember(p.getUniqueId())) {
														if(!faction.isMember(p.getUniqueId())) {
															faction.addInvite(p.getUniqueId());
															player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have invited &6" + p.getName() + " &ato your faction"));
															player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aThey have &b60 seconds &ato accept!"));
															p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ainvited you to &6" + faction.getName() + "&a!"));
															p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have 60 seconds to accept!"));
															p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aType /f accept/join &6" + player.getName() + " to join &6" + faction.getName() +"&a, type /f decline/refuse " + player.getName() + " to decline the invitation!"));
															p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aType /f accept/join &6" + faction.getName() + " to join &6" + faction.getName() +"&a, type /f decline/refuse " + faction.getName() + " to decline the invitation!"));
															final DFFaction fac = faction;
															new BukkitRunnable() {
																public void run() {
																	if(fac.getInvitedList().contains(p.getUniqueId())) {
																		fac.removeInvite(p.getUniqueId());
																		p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cDue to you not responding, the invitation from &6" + player.getName() + " &chas expired!"));
																		player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cDue to &6" + p.getName() + " &cnot responding, your invitation has expired!"));
																	}
																}
															}.runTaskLater(CustomEnchantments.getInstance(), 1200L);
														}
														else {
															player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is already in your faction!"));
														}
													}
													else {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is already in a faction!"));
													}
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't invite yourself!"));	
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + "&c is not online!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player has never joined DungeonForge!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou dont have permission to invite someone to your faction."));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("leave")) {
							if(args.length == 1) {
								if(faction != null) {
									if(facPlayer.getRank() != 4) {
										faction.removeMember(player);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have left &6" + faction.getName()));
										if(!facPlayerManager.getFactionPlayerMap().isEmpty()) {
											for(Entry<UUID, DFFactionPlayer> entry : facPlayerManager.getFactionPlayerMap().entrySet()) {
												if(entry.getValue() != null) {
													if(entry.getValue().getFactionId() != null) {
														UUID id = entry.getKey();
														Player p = Bukkit.getPlayer(id);
														if(player != null) {
															p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas left the faction!"));
														}
													}
												}
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cAn error has occured, please contact higher ups."));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't leave your faction as your faction leader! Use /f abandon instead."));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou do not have a faction!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("kick")) {
							if(args.length == 2) {
								if(faction != null) {
									int rank = facPlayer.getRank();
									if(rank >= 3) {
										Player p = Bukkit.getPlayer(args[1]);
										if(p != null) {
											if(p != player) {
												if(faction.isMember(p.getUniqueId())) {
													int rankOther = facPlayer.getRank();
													if(rank > rankOther) {
														faction.removeMember(p);
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have kicked &6" + p.getName() + " &afrom the faction!"));
														if(p.isOnline()) {
															p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ckicked you from the faction!"));
														}
													}
													else {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have permission to kick this person from your faction!"));
													}
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not in your faction!"));
												}
											}
											else if(facPlayer.getRank() == 4){
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't remove yourself! Use /f abandon to abandon your faction!"));
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't remove yourself!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player does not exist!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have permission to kick players from your faction!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have a faction!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("join")) {
							if(args.length == 2) {
								String string = args[1];
								Player p = Bukkit.getPlayer(string);
								if(p != null) {
									DFFactionPlayer facP = facPlayerManager.getFactionPlayer(p);
									if(facP.getFactionId() != null) {
										DFFaction fac = facManager.getFaction(facP.getFactionId());
										if(fac.isInvited(player.getUniqueId())) {
											p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas joined the faction!"));
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have joined " + fac.getName()));
											fac.removeInvite(player.getUniqueId());
											fac.addMember(player);
											for(UUID id : facPlayerManager.getFactionPlayerMap().keySet()) {
												Player pl = Bukkit.getPlayer(id);
												DFFactionPlayer fPlayer = facPlayerManager.getFactionPlayer(id);
												if(pl != null && pl != p) {
													if(pl.isOnline()) {
														if(fPlayer.getFactionId().equals(fac.getFactionId())) {
															pl.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas joined the faction!"));
														}
													}
												}
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou haven't recieved an invite from " + p.getName() + "&a!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &cIsn't part of a faction!"));
									}
								}
								else {
									DFFaction fac = facManager.getFaction(string);
									if(fac != null) {
										if(fac.isInvited(player.getUniqueId())) {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have joined &6" + fac.getName() + "&a!"));
											for(UUID id : facPlayerManager.getFactionPlayerMap().keySet()) {
												Player pl = Bukkit.getPlayer(id);
												if(pl != null) {
													pl.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas joined the faction!"));
												}
											}
											fac.removeInvite(player.getUniqueId());
											fac.addMember(player);
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou haven't been invited to this faction!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis faction doesn't exist!"));
									}
								}
								
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("decline") || args[0].equalsIgnoreCase("refuse")) {
							if(args.length == 2) {
								String string = args[1];
								Player p = Bukkit.getPlayer(string);
								if(p != null) {
									DFFactionPlayer facP = facPlayerManager.getFactionPlayer(p);
									if(facP.getFactionId() != null) {
										DFFaction fac = facManager.getFaction(facP.getFactionId());
										if(fac.isInvited(player.getUniqueId())) {
											fac.removeInvite(player.getUniqueId());
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have denied the request to join &6" + p.getName() + "'s &afaction!"));
											p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas denied your request to join your faction!"));
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou haven't recieved an invite from &6" + p.getName() + "&a!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis faction doesn't exist!"));
									}
								}
								else {
									DFFaction fac = facManager.getFaction(string);
									if(fac != null) {
										if(fac.isInvited(player.getUniqueId())) {
											fac.removeInvite(player.getUniqueId());
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have denied the request to join the faction &6" + fac.getName() + "&a!"));
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou haven't been invited to this faction!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis faction doesn't exist!"));
									}
								}
								
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("promote")) {
							if(args.length == 2) {
								if(faction != null) {
									Player p = Bukkit.getPlayer(args[1]);
									if(p != null) {
										if(p != player) {
											if(faction.isMember(p.getUniqueId())) {
												DFFactionPlayer facP = facPlayerManager.getFactionPlayer(p);
												int rankMe = facPlayer.getRank();
												int rankOther = facP.getRank();
												if(rankMe > rankOther) {
													faction.promoteMember(p.getUniqueId());
													if(facP.getRank() == 2) {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " &ato Member!"));
														p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &aHas promoted you to a Member!"));
													}
													if(facP.getRank() == 3) {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " &ato Officer!"));
														p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &aHas promoted you to a Officer!"));
													}
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have permission to promote this faction member!"));
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not in your faction!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't promote yourself!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player has never joined DungeonForge!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have a faction!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("demote")) {
							if(args.length == 2) {
								if(faction != null) {
									Player p = Bukkit.getPlayer(args[1]);
									if(p != null) {
										if(p != player) {
											if(faction.isMember(p.getUniqueId())) {
												DFFactionPlayer facP = facPlayerManager.getFactionPlayer(p);
												int rankMe = facPlayer.getRank();
												int rankOther = facP.getRank();
												if(rankMe > rankOther) {
													faction.demoteMember(p.getUniqueId());
													if(facP.getRank() == 2) {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " &cto Member!"));
														p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &cHas demoted you to a Member!"));
													}
													if(facP.getRank() == 1) {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " &cto Recruit!"));
														p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &cHas demoted you to a Recruit!"));
													}
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have permission to demote this faction member!"));
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not in your faction!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't promote yourself!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player has never joined DungeonForge!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have a faction!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("ally")) {
							if(args.length == 2) {
								if(faction != null) {
									String allyName = args[1];
									DFFaction fac = facManager.getFaction(allyName);
									if(fac != null) {
										if(!faction.isAlly(fac.getName())) {
											if(!faction.isInvitedAlly(fac.getFactionId())) {
												if(!faction.getName().equals(fac.getName())) {
													int rank = facPlayer.getRank();
													if(rank >= 3) {
														fac.addInvitedAlly(fac.getFactionId());
														if(faction.isInvitedAlly(fac.getFactionId()) && fac.isInvitedAlly(faction.getFactionId())) {
															faction.removeInvitedAlly(fac.getFactionId());
															fac.removeInvitedAlly(faction.getFactionId());
															faction.addAlly(fac.getFactionId());
															fac.addAlly(faction.getFactionId());
															for(Entry<UUID, DFFactionPlayer> entry : facPlayerManager.getFactionPlayerMap().entrySet()) {
																if(entry.getValue().getFactionId().equals(faction.getFactionId())) {
																	Player p = Bukkit.getPlayer(entry.getKey());
																	if(p.isOnline()) {
																		p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYour faction is now allies with &6" + fac.getName()));
																		
																	}
																}
															}
															for(Entry<UUID, DFFactionPlayer> entry : facPlayerManager.getFactionPlayerMap().entrySet()) {
																if(entry.getValue().getFactionId().equals(fac.getFactionId())) {
																	Player p = Bukkit.getPlayer(entry.getKey());
																	if(p.isOnline()) {
																		p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYour faction is now allies with &6" + faction.getName()));
																	}
																}
															}
														}
														else {
															player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have requested to ally with &6" + allyName + "&a!"));
															player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTheir leader/officers have 60 seconds to accept!"));
															for(Entry<UUID, DFFactionPlayer> entry : facPlayerManager.getFactionPlayerMap().entrySet()) {
																if(faction.getFactionId().equals(entry.getValue().getFactionId())) {
																	Player p = Bukkit.getPlayer(entry.getKey());
																	if(p.isOnline()) {
																		if(entry.getValue().getRank() >= 3) {
																			p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &arequested to be allies with &6" + allyName));
																		}
																	}
																}
															}
															for(Entry<UUID, DFFactionPlayer> entry : facPlayerManager.getFactionPlayerMap().entrySet()) {
																if(fac.getFactionId().equals(entry.getValue().getFactionId())) {
																	Player p = Bukkit.getPlayer(entry.getKey());
																	if(p.isOnline()) {
																		if(entry.getValue().getRank() >= 3) {
																			p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + faction.getName() + " &arequested to be allies with your faction!"));
																			p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aDo /f ally &6" + faction.getName() + " &ato ally them, if you don't want to be allies then just don't do /f ally &6" + faction.getName()));
																			p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have 60 seconds to accept!"));
																		}
																	}
																}
															}
														}
													}
													else {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have permission to ally this faction!"));
													}
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cWhat are you... Whatever you can't ally yourself!"));
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou already invited this faction to be allies!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are already allies with this faction!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis faction doesn't exist!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have a faction!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else if(args[0].equalsIgnoreCase("unally")) {
							if(args.length == 2) {
								if(faction != null) {
									String allyName = args[1];
									DFFaction fac = facManager.getFaction(allyName);
									if(fac != null) {
										if(faction.isAlly(fac.getName())) {
											int rank = facPlayer.getRank();
											if(rank >= 3) {
												faction.removeAlly(fac.getFactionId());
												fac.removeAlly(faction.getFactionId());
												for(Entry<UUID, DFFactionPlayer> entry : facPlayerManager.getFactionPlayerMap().entrySet()) {
													if(faction.getFactionId().equals(entry.getValue().getFactionId())) {
														Player p = Bukkit.getPlayer(entry.getKey());
														if(p.isOnline()) {
															p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour faction has unallied with &6" + fac.getName()));
															
														}
													}
												}
												for(Entry<UUID, DFFactionPlayer> entry : facPlayerManager.getFactionPlayerMap().entrySet()) {
													if(fac.getFactionId().equals(entry.getValue().getFactionId())) {
														Player p = Bukkit.getPlayer(entry.getKey());
														if(p.isOnline()) {
															p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour faction has unallied with &6" + faction.getName()));
														}
													}
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have permission to unally this faction!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not allies with this faction!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis faction does not exist!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have a faction!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid Arguments! Use /f or /f help to see the faction commands!"));
						}
					}
				}
			}
			else {
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
			}
		}
		return false;
	}
}
				

