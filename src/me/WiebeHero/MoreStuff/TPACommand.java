package me.WiebeHero.MoreStuff;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class TPACommand implements Listener,CommandExecutor{
	public String tpa = "tpa";
	public String tpaccept = "tpaccept";
	public String tpatoggle = "tpatoggle";
	public String tpahere = "tpahere";
	public String tpdeny = "tpdeny";
	public HashMap<Player, Boolean> tpaToggle = new HashMap<Player, Boolean>();
	public HashMap<Player, Player> tpRequests = new HashMap<Player, Player>();
	public HashMap<Player, Player> tphereRequests = new HashMap<Player, Player>();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(tpa) || cmd.getName().equalsIgnoreCase(tpahere)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length == 1) {
						Player target = Bukkit.getPlayer(args[0]);
						if(target == null) {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThat player is not online!"));
						}
						else if(target == player) {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't send a tpa request to yourself!"));
						}
						else {
							if(tpaToggle.get(target) == false) {
								if(cmd.getName().equalsIgnoreCase(tpa)) {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTpa request has been sent!"));
									tpRequests.put(player, target);
									target.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &aWants to tp to you!"));
									target.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aType /tpaccept to teleport them to you."));
									target.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aType /tpadeny to decline the teleportation request."));
									target.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have 60 seconds to accept this request."));
									new BukkitRunnable() {
										@Override
										public void run() {
											if(tpRequests.containsKey(player)) {
												tpRequests.remove(player);
												target.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cRequest timed out."));
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cRequest timed out."));
											}
										}
									}.runTaskLater(CustomEnchantments.getInstance(), 1200L);
								}
								else if(cmd.getName().equalsIgnoreCase(tpahere)) {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTpahere request has been sent!"));
									tphereRequests.put(player, target);
									target.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &aWants to tp you to them!"));
									target.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aType /tpaccept to teleport yourself to them."));
									target.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aType /tpadeny to decline the teleportation request."));
									target.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have 60 seconds to accept this request."));
									new BukkitRunnable() {
										@Override
										public void run() {
											if(tphereRequests.containsKey(player)) {
												tphereRequests.remove(player);
												target.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cRequest timed out."));
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cRequest timed out."));
											}
										}
									}.runTaskLater(CustomEnchantments.getInstance(), 1200L);
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + target.getName() + " &cdoesn't have tpa requests enabled!"));	
							}
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou only need to type the players name you want to tp to after 'tpa'"));	
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
			if(cmd.getName().equalsIgnoreCase(tpaccept)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length == 0) {
						if(tpaToggle.get(player) == false) {
							if(tpRequests.containsValue(player)) {
								for(Entry<Player, Player> p : tpRequests.entrySet()) {
									if(p.getValue() == player) {
										Location loc = p.getKey().getLocation();
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aRequest Accepted!"));
										p.getKey().sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas accepted your tpa request!"));
										new BukkitRunnable() {
											int count = 60;
											int temp = 0;
											public void run() {
												if(loc.distance(p.getKey().getLocation()) == 0.00) {
													if(temp == 0) {
														temp = temp - 20;
														if(count / 20 != 0) {
															p.getKey().sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTeleporting in &b" + (count / 20) + " Seconds..."));
														}
													}
													if(count == 0) {
														p.getKey().sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTeleporting!"));
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getKey().getName() + "Has teleported to your location!"));
														p.getKey().teleport(player);
														tpRequests.remove(p.getKey());
														cancel();
													}
												}
												else {
													p.getKey().sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cTeleportation was cancelled, you have to stand still."));
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cTeleportation was cancelled, &6" + player.getName() + " &cdidn't stand still."));
													tpRequests.remove(p.getKey());
													cancel();
												}
												count--;
												temp++;
											}
										}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
									}
								}
							}
							else if(tphereRequests.containsValue(player)) {
								for(Entry<Player, Player> p : tphereRequests.entrySet()) {
									if(p.getValue() == player) {
										Location loc = player.getLocation();
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aRequest Accepted!"));
										p.getKey().sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas accepted your tpahere request!"));
										new BukkitRunnable() {
											int count = 60;
											int temp = 0;
											public void run() {
												if(loc.distance(player.getLocation()) == 0.00) {
													if(temp == 0) {
														temp = temp - 20;
														if(count / 20 != 0) {
															player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTeleporting in &b" + (count / 20) + " Seconds..."));
														}
													}
													if(count == 0) {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTeleporting!"));
														p.getKey().sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aHas teleported to your location!"));
														player.teleport(p.getKey());
														tpRequests.remove(p.getKey());
														cancel();
													}
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cTeleportation was cancelled, you have to stand still."));
													p.getKey().sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cTeleportation was cancelled, &6" + player.getName() + " &cdidn't stand still."));
													tpRequests.remove(p.getKey());
													cancel();
												}
												count--;
												temp++;
											}
										}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
									}
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have any tpa requests."));	
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have turned off tpa requests, turn them on by doing /tpatoggle!"));	
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cNo need for more arguments, thanks."));	
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
			if(cmd.getName().equalsIgnoreCase(tpdeny)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length == 0) {
						if(tpaToggle.get(player) == false) {
							if(tpRequests.containsValue(player)) {
								for(Entry<Player, Player> p : tpRequests.entrySet()) {
									if(p.getValue() == player) {
										p.getKey().sendMessage(new CCT().colorize("&cThe player that you sent a tpa request has denied the request."));
										player.sendMessage(new CCT().colorize("&aTpa request denied."));	
										tpRequests.remove(p.getKey());
									}
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou dont have tp requests."));	
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have turned off tp requests, turn them on by doing /tpatoggle!"));	
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &4No need for more arguments, thanks."));	
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
			if(cmd.getName().equalsIgnoreCase(tpatoggle)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length == 0) {
						if(tpaToggle.get(player) == false) {
							tpaToggle.put(player, true);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have turned off tpa requests!"));
						}
						else if(tpaToggle.get(player) == true) {
							tpaToggle.put(player, false);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have turned on tpa requests!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cNo need for more arguments, thanks."));	
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
		}
		return false;
	}
	@EventHandler
	public void joinActivate(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		tpaToggle.put(player, false);
	}
}
