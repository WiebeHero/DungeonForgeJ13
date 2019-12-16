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

public class DungeonPartyCommand implements CommandExecutor {
	public String comParty = "party";
	DungeonParty placeHolder = new DungeonParty();
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
				if(!(cmd.getName().equalsIgnoreCase(label)) || cmd.getName().equalsIgnoreCase(comParty)) {
					if(args.length == 1) {
						if(args[0].equalsIgnoreCase("create")) {
							if(!placeHolder.isInAParty(player)) {
								placeHolder.createParty(player);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have created a party! Use /party invite (Player Name) to invite other players!"));
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are already in a party!"));
							}
						}
						else if(args[0].equalsIgnoreCase("abandon")) {
							if(placeHolder.isInAParty(player)) {
								DungeonParty party = placeHolder.getDungeonParty(player);
								if(party.getRank(player) == 2) {
									party.deleteParty(player);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have abandoned your party!"));
									for(UUID uuid : party.getPartyMemberList().keySet()) {
										Player p = Bukkit.getPlayer(uuid);
										if(player != p) {
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
							if(placeHolder.isInAParty(player)) {
								DungeonParty party = placeHolder.getDungeonParty(player);
								if(party.getRank(player) == 1) {
									party.leaveParty(player);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have left &6" + party.getLeader().getName() + "'s &aparty!"));
									for(UUID uuid : party.getPartyMemberList().keySet()) {
										Player p = Bukkit.getPlayer(uuid);
										if(player != p) {
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
							if(placeHolder.isInAParty(player)) {
								DungeonParty party = placeHolder.getDungeonParty(player);
								if(party.getRank(player) == 2) {
									Player p = Bukkit.getPlayer(args[1]);
									if(p != null) {
										if(!party.isMember(p)) {
											if(!placeHolder.isInAParty(p)) {
												party.addInvited(p);
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &ahas been invited to your party!"));
												p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas invited you to join their party!"));
												p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aType '/party join &6" + player.getName() + "' to join their party!"));
												p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aType '/party deny &6" + player.getName() + "' to decline their invitation!"));
												new BukkitRunnable() {
													public void run() {
														if(party.getPartyInvitedList().contains(p.getUniqueId())) {
															party.removeInvited(p);
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
							if(!placeHolder.isInAParty(player)) {
								Player p = Bukkit.getPlayer(args[1]);
								if(p != null) {
									if(placeHolder.getDungeonParty(p) != null) {
										DungeonParty party = placeHolder.getDungeonParty(p);
										if(party.getPartyInvitedList().contains(p.getUniqueId())) {
											party.removeInvited(player);
											party.addMember(player);
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have joined &6" + p.getName() + "'s &aparty!"));
											p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + "'s &ahas joined your party!"));
											for(UUID id : party.getPartyMemberList().keySet()) {
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
							if(!placeHolder.isInAParty(player)) {
								Player p = Bukkit.getPlayer(args[1]);
								if(p != null) {
									if(placeHolder.getDungeonParty(p) != null) {
										DungeonParty party = placeHolder.getDungeonParty(p);
										if(party.getPartyInvitedList().contains(p.getUniqueId())) {
											party.removeInvited(player);
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
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are already in a party!"));
							}
						}
						else if(args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("remove")) {
							if(placeHolder.isInAParty(player)) {
								DungeonParty party = placeHolder.getDungeonParty(player);
								if(party.getRank(player) == 2) {
									Player p = Bukkit.getPlayer(args[1]);
									if(party.isMember(p)) {
										party.removeMember(p);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have kicked &6" + p.getName() + " &afrom the party!"));
										p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been kicked from &6" + player.getName() + "'s &cparty!"));
										for(UUID id : party.getPartyMemberList().keySet()) {
											Player temp = Bukkit.getPlayer(id);
											if(temp != p && temp != player) {
												temp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &chas been kicked from &6" + player.getName() + "'s &cparty!"));
											}
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
							if(placeHolder.isInAParty(player)) {
								DungeonParty party = placeHolder.getDungeonParty(player);
								if(party.getRank(player) == 2) {
									Player p = Bukkit.getPlayer(args[1]);
									if(p != null) {
										if(party.isMember(p)) {
											party.promote(p);
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " &ato the leader of the party!"));
											p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been promoted by &6" + player.getName() + "'s &ato be the leader of the party!"));
											for(UUID id : party.getPartyMemberList().keySet()) {
												Player temp = Bukkit.getPlayer(id);
												if(temp != p && temp != player) {
													temp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &ahas been promoted by &6" + player.getName() + "'s &ato be the leader of the party!"));
												}
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not in your party!"));
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
