package me.WiebeHero.Moderation;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;

public class ModerationGUICommand implements CommandExecutor,Listener{
	public HashMap<UUID, Boolean> staffModeList = new HashMap<UUID, Boolean>();
	public HashMap<UUID, Boolean> vanishMode = new HashMap<UUID, Boolean>();
	public ArrayList<UUID> staffChat = new ArrayList<UUID>();
	public HashMap<UUID, ItemStack[]> saveInv = new HashMap<UUID, ItemStack[]>();
	public HashMap<UUID, ArrayList<String>> reasonBanList = new HashMap<UUID, ArrayList<String>>();
	public HashMap<UUID, ArrayList<String>> reasonMuteList = new HashMap<UUID, ArrayList<String>>();
	public HashMap<UUID, Long> banTimeList = new HashMap<UUID, Long>();
	public HashMap<UUID, Long> muteTimeList = new HashMap<UUID, Long>();
	public ArrayList<UUID> banPerm = new ArrayList<UUID>();
	public ArrayList<UUID> mutePerm = new ArrayList<UUID>();
	public HashMap<UUID, Integer> banOffends = new HashMap<UUID, Integer>();
	public HashMap<UUID, Integer> muteOffends = new HashMap<UUID, Integer>();
	public HashMap<UUID, Integer> warnList = new HashMap<UUID, Integer>();
	public HashMap<UUID, ArrayList<UUID>> bannedBy = new HashMap<UUID, ArrayList<UUID>>();
	public HashMap<UUID, UUID> kickedBy = new HashMap<UUID, UUID>();
	public HashMap<UUID, ArrayList<String>> warnReasonList = new HashMap<UUID, ArrayList<String>>();
	public HashMap<UUID, Date> dateList = new HashMap<UUID, Date>();
	public String ban = "ban";
	public String unban = "unban";
	public String mute = "mute";
	public String unmute = "unmute";
	public String warn = "warn";
	public String unwarn = "unwarn";
	public String kick = "kick";
	public String staffmode = "staffmode";
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			LuckPermsApi api = LuckPerms.getApi();
			if(cmd.getName().equalsIgnoreCase(staffmode)) {
				if(staffModeList.containsKey(player.getUniqueId())) {
					if(staffModeList.get(player.getUniqueId()) == true) {
						player.getInventory().setContents(saveInv.get(player.getUniqueId()));
						saveInv.remove(player.getUniqueId());
						staffModeList.put(player.getUniqueId(), false);
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &c&lStaffmode Disabled."));
					}
					else if(staffModeList.get(player.getUniqueId()) == false) {
						saveInv.put(player.getUniqueId(), player.getInventory().getContents());
						player.getInventory().clear();
						staffModeList.put(player.getUniqueId(), true);
						player.getInventory().setItem(0, compass());
						player.getInventory().setItem(1, report());
						player.getInventory().setItem(2, vanish());
						player.getInventory().setItem(3, playerInfo());
						player.getInventory().setItem(4, examine());
						player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
						player.setFoodLevel(20);
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &a&lStaffmode Enabled."));
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &4Fatal error, please consult higher ups."));
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(ban)) {
				if(api.getUser(player.getUniqueId()).getPrimaryGroup().equalsIgnoreCase("Owner")) {
					if(args.length >= 3) {
						String p = args[0];
						Player offender = Bukkit.getPlayer(p);
						if(offender != null) {
							String timeS = args[1];
							String reason = "";
							for(int i = 2; i < args.length; i++) {
								if(i == 2) {
									reason = reason + args[i];
								}
								else {
									reason = reason + " " + args[i];
								}
							}
							int time = 0;
							Long timeTillMuteOver = null;
							if(timeS.contains("minutes") || timeS.contains("minute") || timeS.contains("m")) {
								timeS = timeS.replaceAll("[^\\d.]", "");
								time = Integer.parseInt(timeS);
								timeTillMuteOver = System.currentTimeMillis() + time * (60 * 1000);
								banTimeList.put(offender.getUniqueId(), timeTillMuteOver);
							}
							else if(timeS.contains("hours") || timeS.contains("hour") || timeS.contains("h")) {
								timeS = timeS.replaceAll("[^\\d.]", "");
								time = Integer.parseInt(timeS);
								timeTillMuteOver = System.currentTimeMillis() + time * (60 * 60 * 1000);
								banTimeList.put(offender.getUniqueId(), timeTillMuteOver);
							}
							else if(timeS.contains("days") || timeS.contains("day") || timeS.contains("d")) {
								timeS = timeS.replaceAll("[^\\d.]", "");
								time = Integer.parseInt(timeS);
								timeTillMuteOver = System.currentTimeMillis() + time * (60 * 60 * 1000 * 24);
								banTimeList.put(offender.getUniqueId(), timeTillMuteOver);
							}
							else if(timeS.contains("months") || timeS.contains("month") || timeS.contains("mo")) {
								timeS = timeS.replaceAll("[^\\d.]", "");
								time = Integer.parseInt(timeS);
								timeTillMuteOver = (long) (System.currentTimeMillis() + time * (60 * 60 * 1000 * 24 * 30.41666666));
								banTimeList.put(offender.getUniqueId(), timeTillMuteOver);
							}
							else if(timeS.contains("years") || timeS.contains("year") || timeS.contains("y")) {
								timeS = timeS.replaceAll("[^\\d.]", "");
								time = Integer.parseInt(timeS);
								timeTillMuteOver = System.currentTimeMillis() + (time * ((long)60 * 60 * 1000 * 24 * 365));
								banTimeList.put(offender.getUniqueId(), timeTillMuteOver);
							}
							else if(timeS.equalsIgnoreCase("permanent") || timeS.equalsIgnoreCase("perm") || timeS.equalsIgnoreCase("permanents") || timeS.equalsIgnoreCase("p")) {
								banPerm.add(player.getUniqueId());
							}
							if(!reasonBanList.containsKey(player.getUniqueId())) {
								reasonBanList.put(player.getUniqueId(), new ArrayList<String>());
								reasonBanList.get(player.getUniqueId()).add(reason);
							}
							else {
								reasonBanList.get(player.getUniqueId()).add(reason);
							}
							long now = System.currentTimeMillis();
							long future = System.currentTimeMillis() + (banTimeList.get(player.getUniqueId()) - System.currentTimeMillis());
							long timeDifference = future - now;
							long diffSeconds = timeDifference / 1000 % 60;
						    long diffMinutes = timeDifference / (60 * 1000) % 60;
						    long diffHours = timeDifference / (60 * 60 * 1000) % 24;
						    long diffDays = (long) (timeDifference / (24 * 60 * 60 * 1000) % 30.41666666);
						    long diffMonths = (long) (timeDifference / (60 * 60 * 1000 * 24 * 30.41666666) % 12);
						    long diffYears = timeDifference / ((long)60 * 60 * 1000 * 24 * 365);
							String message = new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have banned &6" + offender.getName() + " &afor &6");
							if(diffSeconds > 0) {
								message = message + diffSeconds + " Seconds, ";
							}
							if(diffMinutes > 0) {
								message = message + diffMinutes + " Minutes, ";
							}
							if(diffHours > 0) {
								message = message + diffHours + " Hours, ";
							}
							if(diffDays > 0) {
								message = message + diffDays + " Days, ";
							}
							if(diffMonths > 0) {
								message = message + diffMonths + " Months, ";
							}
							if(diffYears > 0) {
								message = message + diffYears + " Years.";
							}
							player.sendMessage(new ColorCodeTranslator().colorize(message));
							if(reasonBanList.get(offender.getUniqueId()) == null) {
								reasonBanList.put(offender.getUniqueId(), new ArrayList<String>());
								reasonBanList.get(offender.getUniqueId()).add(reason);
							}
							else {
								reasonBanList.get(offender.getUniqueId()).add(reason);
							}
							if(banOffends.get(offender.getUniqueId()) == null) {
								banOffends.put(offender.getUniqueId(), 1);
							}
							else {
								banOffends.put(offender.getUniqueId(), banOffends.get(offender.getUniqueId()) + 1);
							}
							if(bannedBy.get(player.getUniqueId()) == null) {
								bannedBy.put(player.getUniqueId(), new ArrayList<UUID>());
							}
							bannedBy.get(player.getUniqueId()).add(offender.getUniqueId());
							offender.kickPlayer(offender.getName());
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
						}
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(mute)) {
				if(staffModeList.containsKey(player.getUniqueId())) {
					if(args.length >= 3) {
						String p = args[0];
						Player offender = Bukkit.getPlayer(p);
						if(offender != null) {
							String timeS = args[1];
							String reason = "";
							for(int i = 2; i < args.length; i++) {
								reason = reason + " " + args[i];
							}
							int time = 0;
							Long timeTillMuteOver = null;
							if(timeS.contains("minutes") || timeS.contains("minute") || timeS.contains("m")) {
								timeS = timeS.replaceAll("[^\\d.]", "");
								time = Integer.parseInt(timeS);
								timeTillMuteOver = System.currentTimeMillis() + time * (60 * 1000);
							}
							else if(timeS.contains("hours") || timeS.contains("hour") || timeS.contains("h")) {
								timeS = timeS.replaceAll("[^\\d.]", "");
								time = Integer.parseInt(timeS);
								timeTillMuteOver = System.currentTimeMillis() + time * (60 * 60 * 1000);
							}
							else if(timeS.contains("days") || timeS.contains("day") || timeS.contains("d")) {
								timeS = timeS.replaceAll("[^\\d.]", "");
								time = Integer.parseInt(timeS);
								timeTillMuteOver = System.currentTimeMillis() + time * (60 * 60 * 1000 * 24);
							}
							else if(timeS.contains("months") || timeS.contains("month") || timeS.contains("mo")) {
								timeS = timeS.replaceAll("[^\\d.]", "");
								time = Integer.parseInt(timeS);
								timeTillMuteOver = (long) (System.currentTimeMillis() + time * (60 * 60 * 1000 * 24 * 30.41666666));
							}
							else if(timeS.contains("years") || timeS.contains("year") || timeS.contains("y")) {
								timeS = timeS.replaceAll("[^\\d.]", "");
								time = Integer.parseInt(timeS);
								timeTillMuteOver = System.currentTimeMillis() + (time * ((long)60 * 60 * 1000 * 24 * 365));
							}
							else if(timeS.equalsIgnoreCase("permanent") || timeS.equalsIgnoreCase("perm") || timeS.equalsIgnoreCase("permanents") || timeS.equalsIgnoreCase("p")) {
								mutePerm.add(player.getUniqueId());
							}
							muteTimeList.put(offender.getUniqueId(), timeTillMuteOver);
							if(!reasonMuteList.containsKey(player.getUniqueId())) {
								reasonMuteList.put(player.getUniqueId(), new ArrayList<String>());
								reasonMuteList.get(player.getUniqueId()).add(reason);
							}
							else {
								reasonMuteList.get(player.getUniqueId()).add(reason);
							}
							long now = System.currentTimeMillis();
							long future = System.currentTimeMillis() + (muteTimeList.get(player.getUniqueId()) - System.currentTimeMillis());
							long timeDifference = future - now;
							long diffSeconds = timeDifference / 1000 % 60;
						    long diffMinutes = timeDifference / (60 * 1000) % 60;
						    long diffHours = timeDifference / (60 * 60 * 1000) % 24;
						    long diffDays = (long) (timeDifference / (24 * 60 * 60 * 1000) % 30.41666666);
						    long diffMonths = (long) (timeDifference / (60 * 60 * 1000 * 24 * 30.41666666) % 12);
						    long diffYears = timeDifference / ((long)60 * 60 * 1000 * 24 * 365);
							String message = new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have muted &6" + offender.getName() + " &afor &6");
							if(diffSeconds > 0) {
								message = message + diffSeconds + " Seconds, ";
							}
							if(diffMinutes > 0) {
								message = message + diffMinutes + " Minutes, ";
							}
							if(diffHours > 0) {
								message = message + diffHours + " Hours, ";
							}
							if(diffDays > 0) {
								message = message + diffDays + " Days, ";
							}
							if(diffMonths > 0) {
								message = message + diffMonths + " Months, ";
							}
							if(diffYears > 0) {
								message = message + diffYears + " Years.";
							}
							player.sendMessage(new ColorCodeTranslator().colorize(message));
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
						}
					}
				}
			}
			else if(cmd.getName().equalsIgnoreCase(unban)) {
				if(staffModeList.containsKey(player.getUniqueId())) {
					if(args.length == 1) {
						Player offender = Bukkit.getPlayer(args[0]);
						if(offender != null) {
							if(banTimeList.containsKey(offender.getUniqueId())|| banPerm.contains(offender.getUniqueId())) {
								banTimeList.remove(offender.getUniqueId());
								banPerm.remove(offender.getUniqueId());
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have unbanned &6" + offender.getName()));
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + offender.getName() + " &cis not banned!"));
							}
						}
						else{
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid arguments! Usage: /unban (Player Name)"));
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(unmute)) {
				if(staffModeList.containsKey(player.getUniqueId())) {
					if(args.length == 1) {
						Player offender = Bukkit.getPlayer(args[0]);
						if(offender != null) {
							if(muteTimeList.containsKey(offender.getUniqueId())|| mutePerm.contains(offender.getUniqueId())) {
								muteTimeList.remove(offender.getUniqueId());
								mutePerm.remove(offender.getUniqueId());
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have unmuted &6" + offender.getName()));
							}
							else {
								player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &6" + offender.getName() + " &cis not muted!"));
							}
						}
						else{
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid arguments! Usage: /unmute (Player Name)"));
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(warn)) {
				if(staffModeList.containsKey(player.getUniqueId())) {
					if(args.length == 2) {
						Player offender = Bukkit.getPlayer(args[0]);
						if(offender != null) {
							warnList.put(offender.getUniqueId(), warnList.get(offender.getUniqueId()) + 1);
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have warned &6" + offender.getName()));
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid arguments! Usage: /warn (Player Name) (Reason)"));
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(unwarn)) {
				if(staffModeList.containsKey(player.getUniqueId())) {
					if(args.length == 1) {
						Player offender = Bukkit.getPlayer(args[0]);
						if(offender != null) {
							warnList.put(offender.getUniqueId(), 0);
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have unwarned &6" + offender.getName()));
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid arguments! Usage: /warn (Player Name) (Reason)"));
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(kick)) {
				if(staffModeList.containsKey(player.getUniqueId())) {
					if(args.length == 2) {
						Player offender = Bukkit.getPlayer(args[0]);
						if(offender != null) {
							kickedBy.put(player.getUniqueId(), offender.getUniqueId());
							player.kickPlayer(offender.getName());
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have kicked &6" + offender.getName()));
						}
						else {
							player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
						}
					}
					else {
						player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cInvalid arguments! Usage: /warn (Player Name) (Reason)"));
					}
				}
				else {
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
		}
		return false;
	}
	@EventHandler
	public void preventChatting(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if(muteTimeList.containsKey(player.getUniqueId())) {
			if(System.currentTimeMillis() < muteTimeList.get(player.getUniqueId())) {
				event.setCancelled(true);
				long now = System.currentTimeMillis();
				long future = System.currentTimeMillis() + (muteTimeList.get(player.getUniqueId()) - System.currentTimeMillis());
				long timeDifference = future - now;
				long diffSeconds = timeDifference / 1000 % 60;
			    long diffMinutes = timeDifference / (60 * 1000) % 60;
			    long diffHours = timeDifference / (60 * 60 * 1000) % 24;
			    long diffDays = (long) (timeDifference / (24 * 60 * 60 * 1000) % 30.41666666);
			    long diffMonths = (long) (timeDifference / (60 * 60 * 1000 * 24 * 30.41666666) % 12);
			    long diffYears = timeDifference / ((long)60 * 60 * 1000 * 24 * 365);
				String message = new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &cYou have been muted! You can chat again in &6");
				if(diffSeconds > 0) {
					message = message + diffSeconds + " Seconds, ";
				}
				if(diffMinutes > 0) {
					message = message + diffMinutes + " Minutes, ";
				}
				if(diffHours > 0) {
					message = message + diffHours + " Hours, ";
				}
				if(diffDays > 0) {
					message = message + diffDays + " Days, ";
				}
				if(diffMonths > 0) {
					message = message + diffMonths + " Months, ";
				}
				if(diffYears > 0) {
					message = message + diffYears + " Years.";
				}
				player.sendMessage(new ColorCodeTranslator().colorize(message));
			}
			else {
				muteTimeList.remove(player.getUniqueId());
			}
		}
		else if(mutePerm.contains(player.getUniqueId())){
			player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: You have been permanently muted. You can appeal at: "));
		}
	}
	@EventHandler
	public void banned(PlayerJoinEvent event) {
		Player offender = event.getPlayer();
		if(banPerm.contains(offender.getUniqueId())) {
			offender.kickPlayer(offender.getName());
		}
		else if(banTimeList.containsKey(offender.getUniqueId())) {
			if(System.currentTimeMillis() < banTimeList.get(offender.getUniqueId())) {
				offender.kickPlayer(offender.getName());
			}
			else {
				banTimeList.remove(offender.getUniqueId());
			}
		}
	}
	@EventHandler 
	public void bannedMessage(PlayerKickEvent event) {
		Player offender = event.getPlayer();
		if(banPerm.contains(offender.getUniqueId())) {
			event.setReason(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: You have been permanently banned! You can appeal at: "));
		}
		else if(banTimeList.containsKey(offender.getUniqueId())) {
			long now = System.currentTimeMillis();
			long future = System.currentTimeMillis() + (banTimeList.get(offender.getUniqueId()) - System.currentTimeMillis());
			long timeDifference = future - now;
			long diffSeconds = timeDifference / 1000 % 60;
		    long diffMinutes = timeDifference / (60 * 1000) % 60;
		    long diffHours = timeDifference / (60 * 60 * 1000) % 24;
		    long diffDays = (long) (timeDifference / (24 * 60 * 60 * 1000) % 30.41666666);
		    long diffMonths = (long) (timeDifference / (60 * 60 * 1000 * 24 * 30.41666666) % 12);
		    long diffYears = timeDifference / ((long)60 * 60 * 1000 * 24 * 365);
			String message = new ColorCodeTranslator().colorize("&cYou have been banned! You can join again in: &6");
			if(diffSeconds > 0) {
				message = message + diffSeconds + " Seconds, ";
			}
			if(diffMinutes > 0) {
				message = message + diffMinutes + " Minutes, ";
			}
			if(diffHours > 0) {
				message = message + diffHours + " Hours, ";
			}
			if(diffDays > 0) {
				message = message + diffDays + " Days, ";
			}
			if(diffMonths > 0) {
				message = message + diffMonths + " Months, ";
			}
			if(diffYears > 0) {
				message = message + diffYears + " Years.";
			}
			String name = "Console";
			for(Entry<UUID, ArrayList<UUID>> entry : bannedBy.entrySet()) {
				if(entry.getValue().contains(offender.getUniqueId())) {
					name = Bukkit.getPlayer(entry.getKey()).getName();
				}
			}
			event.setReason(new ColorCodeTranslator().colorize(message + "\n&cIngame Name: &6" + offender.getName() + "\n&cBanned by: &6" + name + "\n&cReason: &4&l" + reasonBanList.get(offender.getUniqueId()).get(banOffends.get(offender.getUniqueId())) + "\n&cAppeal at: "));
		}
		else if(kickedBy.containsValue(offender.getUniqueId())){
			String name = "Console";
			for(Entry<UUID, UUID> entry : kickedBy.entrySet()) {
				if(entry.getValue().equals(offender.getUniqueId())) {
					name = Bukkit.getPlayer(entry.getKey()).getName();
				}
			}
			event.setReason(new ColorCodeTranslator().colorize("&cYou have been kicked from the server by &6" + name + "\n&cReason: &4&l"));
		}
	}
	@EventHandler
	public void staffList(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(player.hasPermission("owner") || player.hasPermission("manager") || player.hasPermission("headadmin") || player.hasPermission("admin") || player.hasPermission("headmod") || player.hasPermission("mod") || player.hasPermission("helper+") || player.hasPermission("helper")) {
			if(!staffModeList.containsKey(player.getUniqueId())) {
				staffModeList.put(player.getUniqueId(), false);
			}
		}
	}
	@EventHandler
	public void staffListLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(staffModeList.containsKey(player.getUniqueId())) {
			if(staffModeList.get(player.getUniqueId()) == true) {
				staffModeList.remove(player.getUniqueId());
				player.getInventory().setContents(saveInv.get(player.getUniqueId()));
				saveInv.remove(player.getUniqueId());
			}
		}
	}
	@EventHandler
	public void foodChangeStaff(FoodLevelChangeEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(staffModeList.get(player.getUniqueId()) == true) {
				player.setFoodLevel(20);
			}
		}
	}
	@EventHandler
	public void cancelHealthLoss(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(staffModeList.get(player.getUniqueId()) == true) {
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void cancelHealthLossEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(staffModeList.get(player.getUniqueId()) == true) {
				event.setCancelled(true);
			}
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void activatePerk(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(staffModeList.get(player.getUniqueId()) == true) {
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().hasItemMeta()) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
						String name = ChatColor.stripColor(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
						if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
							ModerationGUI gui = new ModerationGUI();
							if(name.equals("Teleport")) {
								gui.InventoryTeleport(player, 1);
							}
							else if(name.equals("Report")) {
								
							}
							else if(name.equals("Vanish")) {
								if(vanishMode.get(player.getUniqueId()) == true) {
									vanishMode.put(player.getUniqueId(), false);
									for(Player p : Bukkit.getOnlinePlayers()) {
										p.showPlayer(player);
									}
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &c&lVanish Disabled."));
								}
								else if(vanishMode.get(player.getUniqueId()) == false) {
									vanishMode.put(player.getUniqueId(), true);
									for(Player p : Bukkit.getOnlinePlayers()) {
										p.hidePlayer(player);
									}
									player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &a&lVanish Enabled."));
								}
							}
						}
					}
				}
			}
		}
	}
	public ItemStack compass() {
		ItemStack item = new ItemStack(Material.COMPASS, 1);
		ItemMeta meta = item.getItemMeta();	
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(new ColorCodeTranslator().colorize("&6Teleport"));
		lore.add(new ColorCodeTranslator().colorize("&f-------------------"));
		lore.add(new ColorCodeTranslator().colorize("&fRight click to teleport to a player."));
		lore.add(new ColorCodeTranslator().colorize("&f-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack report() {
		ItemStack item = new ItemStack(Material.PAPER, 1);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(new ColorCodeTranslator().colorize("&cReport"));
		lore.add(new ColorCodeTranslator().colorize("&f-------------------"));
		lore.add(new ColorCodeTranslator().colorize("&fRight click to open the report history."));
		lore.add(new ColorCodeTranslator().colorize("&f-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack vanish() {
		ItemStack item = new ItemStack(Material.LIME_DYE, 1);
		ItemMeta meta = item.getItemMeta();	
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(new ColorCodeTranslator().colorize("&aVansih"));
		lore.add(new ColorCodeTranslator().colorize("&f-------------------"));
		lore.add(new ColorCodeTranslator().colorize("&fRight click to vanish/unvanish."));
		lore.add(new ColorCodeTranslator().colorize("&f-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack playerInfo() {
		ItemStack item = new ItemStack(Material.CLOCK, 1);
		ItemMeta meta = item.getItemMeta();	
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(new ColorCodeTranslator().colorize("&bInfo"));
		lore.add(new ColorCodeTranslator().colorize("&f-------------------"));
		lore.add(new ColorCodeTranslator().colorize("&fRight click a player for their info."));
		lore.add(new ColorCodeTranslator().colorize("&f-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack examine() {
		ItemStack item = new ItemStack(Material.CHEST, 1);
		ItemMeta meta = item.getItemMeta();	
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(new ColorCodeTranslator().colorize("&4Examine"));
		lore.add(new ColorCodeTranslator().colorize("&f-------------------"));
		lore.add(new ColorCodeTranslator().colorize("&fRight click to view an inventory of a player."));
		lore.add(new ColorCodeTranslator().colorize("&f-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public void loadReasonBanList(YamlConfiguration yml, File f) {
		reasonBanList.clear();
		UUID uuid = null;
		Set<String> set = yml.getConfigurationSection("Mod.Punishments").getKeys(false);
		ArrayList<String> uuids = new ArrayList<String>(set);
		for(int i = 0; i < uuids.size(); i++) {
			uuid = UUID.fromString(uuids.get(i));
			ArrayList<String> offense = (ArrayList<String>) yml.getStringList("Mod.Punishments." + uuid + ".Ban Data.Reason");
			reasonBanList.put(uuid, offense);
		}
	}
	public void loadReasonMuteList(YamlConfiguration yml, File f) {
		reasonMuteList.clear();
		UUID uuid = null;
		Set<String> set = yml.getConfigurationSection("Mod.Punishments").getKeys(false);
		ArrayList<String> uuids = new ArrayList<String>(set);
		for(int i = 0; i < uuids.size(); i++) {
			uuid = UUID.fromString(uuids.get(i));
			ArrayList<String> offense = (ArrayList<String>) yml.getStringList("Mod.Punishments." + uuid + ".Mute Data.Reason");
			reasonMuteList.put(uuid, offense);
		}
	}
	public void loadBanTime(YamlConfiguration yml, File f) {
		banTimeList.clear();
		UUID uuid = null;
		Set<String> set = yml.getConfigurationSection("Mod.Punishments").getKeys(false);
		ArrayList<String> uuids = new ArrayList<String>(set);
		for(int i = 0; i < uuids.size(); i++) {
			uuid = UUID.fromString(uuids.get(i));
			Long offense = yml.getLong("Mod.Punishments." + uuid + ".Ban Data.Temp");
			banTimeList.put(uuid, offense);
		}
	}
	public void loadMuteTime(YamlConfiguration yml, File f) {
		muteTimeList.clear();
		UUID uuid = null;
		Set<String> set = yml.getConfigurationSection("Mod.Punishments").getKeys(false);
		ArrayList<String> uuids = new ArrayList<String>(set);
		for(int i = 0; i < uuids.size(); i++) {
			uuid = UUID.fromString(uuids.get(i));
			Long offense = yml.getLong("Mod.Punishments." + uuid + ".Mute Data.Temp");
			muteTimeList.put(uuid, offense);
		}
	}
	public void loadPermBan(YamlConfiguration yml, File f) {
		banPerm.clear();
		UUID uuid = null;
		Set<String> set = yml.getConfigurationSection("Mod.Punishments").getKeys(false);
		ArrayList<String> uuids = new ArrayList<String>(set);
		for(int i = 0; i < uuids.size(); i++) {
			uuid = UUID.fromString(uuids.get(i));
			boolean offense = yml.getBoolean("Mod.Punishments." + uuid + ".Ban Data.Perm");
			if(offense == true) {
				banPerm.add(uuid);
			}
		}
	}
	public void loadPermMute(YamlConfiguration yml, File f) {
		mutePerm.clear();
		UUID uuid = null;
		Set<String> set = yml.getConfigurationSection("Mod.Punishments").getKeys(false);
		ArrayList<String> uuids = new ArrayList<String>(set);
		for(int i = 0; i < uuids.size(); i++) {
			uuid = UUID.fromString(uuids.get(i));
			boolean offense = yml.getBoolean("Mod.Punishments." + uuid + ".Mute Data.Perm");
			if(offense == true) {
				banPerm.add(uuid);
			}
		}
	}
	public void loadWarnOffenseList(YamlConfiguration yml, File f) {
		warnList.clear();
		UUID uuid = null;
		Set<String> set = yml.getConfigurationSection("Mod.Punishments").getKeys(false);
		ArrayList<String> uuids = new ArrayList<String>(set);
		for(int i = 0; i < uuids.size(); i++) {
			uuid = UUID.fromString(uuids.get(i));
			int offense = yml.getInt("Mod.Punishments." + uuid + ".Warn Data.Temp");
			warnList.put(uuid, offense);
		}
	}
	public void loadWarnReasonList(YamlConfiguration yml, File f) {
		warnReasonList.clear();
		UUID uuid = null;
		Set<String> set = yml.getConfigurationSection("Mod.Punishments").getKeys(false);
		ArrayList<String> uuids = new ArrayList<String>(set);
		for(int i = 0; i < uuids.size(); i++) {
			uuid = UUID.fromString(uuids.get(i));
			ArrayList<String> offense = (ArrayList<String>) yml.getStringList("Mod.Punishments." + uuid + ".Warn Data.Reason");
			warnReasonList.put(uuid, offense);
		}
	}
	public HashMap<UUID, ArrayList<String>> getBanReasonList(){
		return this.reasonBanList;
	}
	public HashMap<UUID, ArrayList<String>> getMuteReasonList(){
		return this.reasonMuteList;
	}
	public HashMap<UUID, Long> getBanTimeList(){
		return this.banTimeList;
	}
	public HashMap<UUID, Long> getMuteTimeList(){
		return this.muteTimeList;
	}
	public ArrayList<UUID> getBanPermList(){
		return this.banPerm;
	}
	public ArrayList<UUID> getMutePermList(){
		return this.mutePerm;
	}
	public HashMap<UUID, Integer> getBanOffendsList(){
		return this.banOffends;
	}
	public HashMap<UUID, Integer> getMuteOffendsList(){
		return this.muteOffends;
	}
	public HashMap<UUID, Integer> getWarnList(){
		return this.warnList;
	}
	public HashMap<UUID, ArrayList<String>> getWarnReasonList(){
		return this.warnReasonList;
	}
}
