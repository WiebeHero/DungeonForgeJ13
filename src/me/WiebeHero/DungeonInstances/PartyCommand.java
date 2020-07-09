package me.WiebeHero.DungeonInstances;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.MoreStuff.CombatTag;
import me.WiebeHero.Party.Party;
import me.WiebeHero.Party.PartyManager;

public class PartyCommand implements CommandExecutor {
	
	private PartyManager pManager;
	
	public String comParty = "party";
	
	public PartyCommand(PartyManager pManager) {
		this.pManager = pManager;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			UUID pUuid = player.getUniqueId();
			if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
				Party cParty = this.pManager.getPartyByPlayer(player.getUniqueId());
				if(!(cmd.getName().equalsIgnoreCase(label)) || cmd.getName().equalsIgnoreCase(comParty)) {
					if(args.length == 1) {
						if(args[0].equalsIgnoreCase("create")) {
							if(cParty == null) {
								Party p = new Party(pUuid, 3);
								this.pManager.addParty(p);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have created a party! Use /party invite (Player Name) to invite other players!"));
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are already in a party!"));
							}
						}
						else if(args[0].equalsIgnoreCase("abandon")) {
							if(cParty != null) {
								if(cParty.getRank(pUuid) == 3) {
									this.pManager.removeParty(cParty);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have abandoned your party!"));
									for(UUID uuid : cParty.getPartyMembers().keySet()) {
										Player p = Bukkit.getPlayer(uuid);
										if(p != null && player != p) {
											p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas abandoned the party!"));
										}
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou need to be the leader of the party to execute this command!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou aren't in a party!"));
							}
						}
						else if(args[0].equalsIgnoreCase("leave")) {
							if(cParty != null) {
								if(cParty.getRank(pUuid) != 3) {
									cParty.removePartyMember(pUuid);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have left the party!"));
									for(UUID uuid :  cParty.getPartyMembers().keySet()) {
										Player p = Bukkit.getPlayer(uuid);
										if(p != null && player != p) {
											p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas left the party!"));
										}
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou need to be the leader of the party to execute this command!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou aren't in a party!"));
							}
						}
					}
					else if(args.length == 2) {
						if(args[0].equalsIgnoreCase("invite")) {
							if(cParty != null) {
								if(cParty.getRank(pUuid) >= 2) {
									Player p = Bukkit.getPlayer(args[1]);
									if(p != null) {
										if(!cParty.getPartyMembers().containsKey(p.getUniqueId())) {
											Party otherParty = this.pManager.getPartyByPlayer(p.getUniqueId());
											if(otherParty == null) {
												cParty.addInvitedPartyMember(p.getUniqueId());
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &ahas been invited to your party!"));
												p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas invited you to join their party!"));
												p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aType '/party join &6" + player.getName() + "' to join their party!"));
												p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aType '/party deny &6" + player.getName() + "' to decline their invitation!"));
												new BukkitRunnable() {
													public void run() {
														if(cParty.containsInvitedPartyMember(p.getUniqueId())) {
															cParty.removeInvitedPartyMember(p.getUniqueId());
															player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe request to &6" + p.getName() + " &chas timed out!"));
															p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe party request of &6" + player.getName() + " &chas timed out!"));
														}
													}
												}.runTaskLater(CustomEnchantments.getInstance(), 1200L);
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &cis already in a party!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &cis already in your party!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou need to be the leader of the party to execute this command!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou aren't in a party!"));
							}
						}
						else if(args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("accept")) {
							if(cParty == null) {
								Player p = Bukkit.getPlayer(args[1]);
								if(p != null) {
									Party party = this.pManager.getPartyByPlayer(p.getUniqueId());
									if(party != null) {
										if(party.containsInvitedPartyMember(p.getUniqueId())) {
											party.removeInvitedPartyMember(p.getUniqueId());
											party.addPartyMember(p.getUniqueId(), 1);
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have joined &6" + p.getName() + "'s &aparty!"));
											p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + "'s &ahas joined your party!"));
											for(UUID id : party.getPartyMembers().keySet()) {
												Player temp = Bukkit.getPlayer(id);
												if(temp != p && temp != player) {
													temp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas joined the party!"));
												}
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou haven't been invited to this party!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis party does not exist!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are already in a party!"));
							}
						}
						else if(args[0].equalsIgnoreCase("decline") || args[0].equalsIgnoreCase("deny")) {
							Player p = Bukkit.getPlayer(args[1]);
							if(p != null) {
								Party party = this.pManager.getPartyByPlayer(p.getUniqueId());
								if(party != null) {
									if(party.containsInvitedPartyMember(p.getUniqueId())) {
										party.removeInvitedPartyMember(p.getUniqueId());
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have declined &6" + p.getName() + "'s &aparty invitation!"));
										p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + "'s &ahas declined your party invitation!"));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou haven't been invited to this party!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis party does not exist!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
							}
						}
						else if(args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("remove")) {
							if(cParty != null) {
								if(cParty.getRank(pUuid) >= 2) {
									Player p = Bukkit.getPlayer(args[1]);
									if(cParty.containsPartyMember(p.getUniqueId())) {
										if(cParty.getRank(pUuid) > cParty.getRank(p.getUniqueId())) {
											cParty.removePartyMember(p.getUniqueId());
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have kicked &6" + p.getName() + " &afrom the party!"));
											p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been kicked from &6" + player.getName() + "'s &cparty!"));
											for(UUID id : cParty.getPartyMembers().keySet()) {
												Player temp = Bukkit.getPlayer(id);
												if(temp != p && temp != player) {
													temp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &chas been kicked from &6" + player.getName() + "'s &cparty!"));
												}
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank must be higher then the player that your kicking!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not in your party!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou need to be the leader of the party to execute this command!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou aren't in a party!"));
							}
						}
						else if(args[0].equalsIgnoreCase("promote")) {
							if(cParty != null) {
								if(cParty.getRank(pUuid) >= 2) {
									Player p = Bukkit.getPlayer(args[1]);
									if(p != null) {
										if(p != player) {
											if(cParty.containsPartyMember(p.getUniqueId())) {
												if(cParty.getRank(pUuid) > cParty.getRank(p.getUniqueId())) {
													if(cParty.getRank(p.getUniqueId()) + 1 <= 2) {
														cParty.addPartyMember(p.getUniqueId(), cParty.getRank(p.getUniqueId()) + 1);
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " &ato " + cParty.getDisplay(p.getUniqueId()) + "!"));
														p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been promoted by &6" + player.getName() + " &ato" + cParty.getDisplay(p.getUniqueId()) + "!"));
														for(UUID id : cParty.getPartyMembers().keySet()) {
															Player temp = Bukkit.getPlayer(id);
															if(temp != p && temp != player) {
																temp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &ahas been promoted by &6" + player.getName() + "'s &ato " + cParty.getDisplay(p.getUniqueId())));
															}
														}
													}
													else {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can not promote this player any further!"));
													}
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank must be higher then the player that you are promoting!"));
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not in your party!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't promote yourself!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou need to be the leader of the party to execute this command!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou aren't in a party!"));
							}
						}
						else if(args[0].equalsIgnoreCase("demote")) {
							if(cParty != null) {
								if(cParty.getRank(pUuid) >= 2) {
									Player p = Bukkit.getPlayer(args[1]);
									if(p != null) {
										if(p != player) {
											if(cParty.containsPartyMember(p.getUniqueId())) {
												if(cParty.getRank(pUuid) > cParty.getRank(p.getUniqueId())) {
													if(cParty.getRank(p.getUniqueId()) - 1 >= 1) {
														cParty.addPartyMember(p.getUniqueId(), cParty.getRank(p.getUniqueId()) - 1);
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have demoted &6" + p.getName() + " &ato " + cParty.getDisplay(p.getUniqueId()) + "!"));
														p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been demoted by &6" + player.getName() + " &ato" + cParty.getDisplay(p.getUniqueId()) + "!"));
														for(UUID id : cParty.getPartyMembers().keySet()) {
															Player temp = Bukkit.getPlayer(id);
															if(temp != p && temp != player) {
																temp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &ahas been demoted by &6" + player.getName() + "'s &ato " + cParty.getDisplay(p.getUniqueId())));
															}
														}
													}
													else {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can not promote this player any further!"));
													}
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank must be higher then the player that you are promoting!"));
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not in your party!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't promote yourself!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou need to be the leader of the party to execute this command!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou aren't in a party!"));
							}
						}
					}
				}
			}
			else {
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command when in combat!"));
			}
		}
		return false;
	}
}
