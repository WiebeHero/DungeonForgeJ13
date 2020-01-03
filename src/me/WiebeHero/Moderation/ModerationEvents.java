package me.WiebeHero.Moderation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCloseEvent.Reason;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomClasses.Methods;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.MethodLuck;
import me.WiebeHero.DFShops.MoneyCreate;
import me.WiebeHero.LootChest.LootChest;
import me.WiebeHero.LootChest.LootChestManager;
import me.WiebeHero.Scoreboard.DFScoreboard;
import me.WiebeHero.Spawners.DFSpawner;
import me.WiebeHero.Spawners.DFSpawnerManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

public class ModerationEvents implements CommandExecutor,Listener,TabCompleter{
	public boolean joinCooldown = false;
	private PunishManager pManager = CustomEnchantments.getInstance().punishManager;
	private StaffManager sManager = CustomEnchantments.getInstance().staffManager;
	private DFSpawnerManager spManager = CustomEnchantments.getInstance().spawnerManager;
	private LootChestManager lcManager = CustomEnchantments.getInstance().lootChestManager;
	private ModerationGUI gui = new ModerationGUI();
	private MethodLuck luck = new MethodLuck();
	private Methods m = new Methods();
	private DFScoreboard board = new DFScoreboard();
	private MoneyCreate money = new MoneyCreate();
	private HashMap<UUID, UUID> target = new HashMap<UUID, UUID>();
	private HashMap<UUID, String> reason = new HashMap<UUID, String>();
	private HashMap<UUID, EntityType> spawnerType = new HashMap<UUID, EntityType>();
	private HashMap<UUID, Integer> spawnerTier = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> spawnerSlot = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> lootTier = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> lootRadius = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> lootSlot = new HashMap<UUID, Integer>();
	public String protocol = "procedure";
	public String ban = "ban";
	public String unban = "unban";
	public String mute = "mute";
	public String unmute = "unmute";
	public String staffmode = "staffmode";
	public String staff = "staff";
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(staff)) {
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("promote")) {
						if(sManager.contains(player.getUniqueId()) || sManager.get(player.getUniqueId()).getRank() != 0) {
							Staff staff = sManager.get(player.getUniqueId());
							Player p = m.convertOfflinePlayer(args[1]);
							if(p.getName() != null) {
								if(sManager.contains(p.getUniqueId())) {
									Staff s = sManager.get(p.getUniqueId());
									if(staff.getRank() >= 7) {
										if(staff.getRank() > s.getRank() || staff.getRank() == 10) {
											LuckPerms api = LuckPermsProvider.get();
											User promoted = api.getUserManager().getUser(p.getUniqueId());
											Player p1 = Bukkit.getPlayer(p.getUniqueId());
											if(p1 != player) {
												if(s.getRank() + 1 == 1) {
													luck.addParent(promoted, "qa");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " to Quality Assurance!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been promoted to Quality Assurance!"));
												}
												if(s.getRank() + 1 == 2) {
													luck.removeParent(promoted, "qa");
													luck.addParent(promoted, "qaadmin");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " to Quality Assurance Admin!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been promoted to Quality Assurance Admin!"));
												}
												if(s.getRank() + 1 == 3) {
													luck.removeParent(promoted, "qaadmin");
													luck.addParent(promoted, "helper");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " to Helper!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been promoted to Helper!"));
												}
												if(s.getRank() + 1 == 4) {
													luck.removeParent(promoted, "helper");
													luck.addParent(promoted, "helper+");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " to Helper+!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been promoted to Helper+!"));
												}
												if(s.getRank() + 1 == 5) {
													luck.removeParent(promoted, "helper+");
													luck.addParent(promoted, "moderator");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " to Moderator!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been promoted to Moderator!"));
												}
												if(s.getRank() + 1 == 6) {
													luck.removeParent(promoted, "moderator");
													luck.addParent(promoted, "headmod");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " to Head Moderator!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been promoted to Head Moderator!"));
												}
												if(s.getRank() + 1 == 7) {
													luck.removeParent(promoted, "headmod");
													luck.addParent(promoted, "admin");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " to Admin!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been promoted to Admin!"));
												}
												if(s.getRank() + 1 == 8) {
													luck.removeParent(promoted, "admin");
													luck.addParent(promoted, "headadmin");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " to Head Admin!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been promoted to Head Admin!"));
												}
												if(s.getRank() + 1 == 9) {
													luck.removeParent(promoted, "headadmin");
													luck.addParent(promoted, "manager");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " to Manager!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been promoted to Manager!"));
												}
												if(s.getRank() + 1 == 10) {
													luck.removeParent(promoted, "manager");
													luck.addParent(promoted, "owner");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " to Owner!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been promoted to Owner!"));
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aThis player can't be promoted to a higher rank!"));
												}
												board.registerRank(p);
												board.generateScoreboard(p);
												this.punishJoin(p);
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't promote yourself!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't promote player's who have an equal/higher rank then you!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank is not high enough to promote this player!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cA fatal error occured."));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
						}
					}
					else if(args[0].equalsIgnoreCase("demote")) {
						if(sManager.contains(player.getUniqueId()) || sManager.get(player.getUniqueId()).getRank() != 0) {
							Staff staff = sManager.get(player.getUniqueId());
							Player p = m.convertOfflinePlayer(args[1]);
							if(p.getName() != null) {
								if(sManager.contains(p.getUniqueId())) {
									Staff s = sManager.get(p.getUniqueId());
									if(staff.getRank() >= 7) {
										if(staff.getRank() > s.getRank() || staff.getRank() == 10) {
											LuckPerms api = LuckPermsProvider.get();
											User promoted = api.getUserManager().getUser(p.getUniqueId());
											Player p1 = Bukkit.getPlayer(p.getUniqueId());
											if(p1 != player) {
												if(s.getRank() - 1 == 0) {
													luck.removeParent(promoted, "qa");
													luck.addParent(promoted, "user");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " to User!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been demoted to User!"));
												}
												if(s.getRank() - 1 == 1) {
													luck.removeParent(promoted, "qaadmin");
													luck.addParent(promoted, "qa");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " to Quality Assurance!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been demoted to Quality Assurance!"));
												}
												if(s.getRank() - 1 == 2) {
													luck.removeParent(promoted, "helper");
													luck.addParent(promoted, "qaadmin");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " to Quality Assurance Admin!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been demoted to Quality Assurance Admin!"));
												}
												if(s.getRank() - 1 == 3) {
													luck.removeParent(promoted, "helper+");
													luck.addParent(promoted, "helper");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " to Helper!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been demoted to Helper!"));
												}
												if(s.getRank() - 1 == 4) {
													luck.removeParent(promoted, "moderator");
													luck.addParent(promoted, "helper+");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " to Helper+!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been demoted to Helper+!"));
												}
												if(s.getRank() - 1 == 5) {
													luck.removeParent(promoted, "headmod");
													luck.addParent(promoted, "moderator");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " to Moderator!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been demoted to Moderator!"));
												}
												if(s.getRank() - 1 == 6) {
													luck.removeParent(promoted, "admin");
													luck.addParent(promoted, "headmod");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " to Head Moderator!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been demoted to Head Moderator!"));
												}
												if(s.getRank() - 1 == 7) {
													luck.removeParent(promoted, "headadmin");
													luck.addParent(promoted, "admin");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " to Admin!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been demoted to Admin!"));
												}
												if(s.getRank() - 1 == 8) {
													luck.removeParent(promoted, "manager");
													luck.addParent(promoted, "headadmin");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " to Head Admin!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been demoted to Head Admin!"));
												}
												if(s.getRank() - 1 == 9) {
													luck.removeParent(promoted, "owner");
													luck.addParent(promoted, "manager");
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have demoted &6" + p.getName() + " to Manager!"));
													p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been demoted to Manager!"));
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aThis player can't be demoted to a lower rank!"));
												}
												board.registerRank(p);
												board.generateScoreboard(p);
												this.punishJoin(p);
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't promote yourself!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't promote player's who have an equal/higher rank then you!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank is not high enough to promote this player!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cA fatal error occured."));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
						}
					}
				}
			}
			if(cmd.getName().equalsIgnoreCase(staffmode)) {
				if(sManager.contains(player.getUniqueId()) || sManager.get(player.getUniqueId()).getRank() != 0) {
					Staff staff = sManager.get(player.getUniqueId());
					if(staff.getStaffMode() == true) {
						player.getInventory().setContents(staff.getInv());
						staff.switchStaffMode(false);
						staff.switchLootMode(false);
						staff.switchVanishMode(false);
						staff.switchSpawnerMode(false);
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &c&lStaffmode Disabled."));
					}
					else if(staff.getStaffMode() == false) {
						staff.saveInv(player.getInventory().getContents());
						player.getInventory().clear();
						staff.switchStaffMode(true);
						player.getInventory().setItem(0, compass());
						player.getInventory().setItem(1, punish());
						player.getInventory().setItem(2, vanish());
						player.getInventory().setItem(3, examine());
						player.getInventory().setItem(4, playerInfo());
						if(staff.getRank() >= 6) {
							player.getInventory().setItem(7, spawner());
							player.getInventory().setItem(8, loot());
						}
						player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
						player.setFoodLevel(20);
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &a&lStaffmode Enabled."));
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &4Fatal error, please consult higher ups."));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(ban)) {
				if(sManager.contains(player.getUniqueId()) || sManager.get(player.getUniqueId()).getRank() != 0) {
					if(args.length == 1) {
						Staff staff = sManager.get(player.getUniqueId());
						if(staff.getRank() >= 5) {
							Player p = m.convertOfflinePlayer(args[0]);
							if(sManager.contains(p.getUniqueId())) {
								Staff s = sManager.get(player.getUniqueId());
								if(staff.getRank() > s.getRank()) {
									if(p.getName() != null && pManager.contains(p.getUniqueId())) {
										Punish pun = pManager.get(p.getUniqueId());
										target.put(player.getUniqueId(), p.getUniqueId());
										if(!pun.isBanned()) {
											gui.BanReason(player, Bukkit.getPlayer(target.get(player.getUniqueId())));
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is already banned!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist."));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank needs to be higher then the player you're banning!"));
								}
							}
							else {
								if(p.getName() != null && pManager.contains(p.getUniqueId())) {
									Punish pun = pManager.get(p.getUniqueId());
									target.put(player.getUniqueId(), p.getUniqueId());
									if(!pun.isBanned()) {
										gui.BanReason(player, Bukkit.getPlayer(target.get(player.getUniqueId())));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is already banned!"));
									}
								}
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have permission to ban players!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCommand ussage: /ban (Player name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(unban)) {
				if(sManager.contains(player.getUniqueId()) || sManager.get(player.getUniqueId()).getRank() != 0) {
					if(args.length == 1) {
						Staff staff = sManager.get(player.getUniqueId());
						if(staff.getRank() >= 5) {
							Player p = m.convertOfflinePlayer(args[0]);
							if(p.getName() != null && pManager.contains(p.getUniqueId())) {
								Punish pun = pManager.get(p.getUniqueId());
								if(pun.isBanned()) {
									pun.setBanTime(0L);
									pun.setBanPerm(false);
									pun.removeBannedBy(pun.getBanOffense() - 1);
									pun.removeBanReason(pun.getBanOffense() - 1);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have unbanned &6" + p.getName()));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not banned!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist."));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have permission to unban players!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCommand ussage: /ban (Player name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(mute)) {
				if(sManager.contains(player.getUniqueId()) || sManager.get(player.getUniqueId()).getRank() != 0) {
					if(args.length == 1) {
						Staff staff = sManager.get(player.getUniqueId());
						if(staff.getRank() >= 3) {
							Player p = m.convertOfflinePlayer(args[0]);
							if(sManager.contains(p.getUniqueId())) {
								Staff s = sManager.get(player.getUniqueId());
								if(staff.getRank() > s.getRank()) {
									if(p.getName() != null) {
										target.put(player.getUniqueId(), p.getUniqueId());
										gui.MuteReason(player, Bukkit.getPlayer(target.get(player.getUniqueId())));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist."));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank needs to be higher then the player you're muting!"));
								}
							}
							else {
								if(p.getName() != null) {
									target.put(player.getUniqueId(), p.getUniqueId());
									gui.MuteReason(player, Bukkit.getPlayer(target.get(player.getUniqueId())));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist."));
								}
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have permission to mute players!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCommand ussage: /ban (Player name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(unmute)) {
				if(sManager.contains(player.getUniqueId()) || sManager.get(player.getUniqueId()).getRank() != 0) {
					if(args.length == 1) {
						Staff staff = sManager.get(player.getUniqueId());
						if(staff.getRank() >= 3) {
							Player p = m.convertOfflinePlayer(args[0]);
							if(p.getName() != null && pManager.contains(p.getUniqueId())) {
								Punish pun = pManager.get(p.getUniqueId());
								if(pun.isMuted() && pManager.contains(player.getUniqueId())) {
									pun.setMuteTime(0L);
									pun.removeMutedBy(pun.getMuteOffense() - 1);
									pun.removeMuteReason(pun.getMuteOffense() - 1);
									Player offender = Bukkit.getPlayer(target.get(player.getUniqueId()));
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have unmuted &6" + offender.getName()));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not muted!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist."));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have permission to unmute players!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCommand ussage: /ban (Player name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(protocol)) {
				if(sManager.contains(player.getUniqueId()) || sManager.get(player.getUniqueId()).getRank() != 0) {
					if(args.length == 1) {
						if(args[0].equalsIgnoreCase("clearall")) {
							if(sManager.contains(player.getUniqueId()) && sManager.get(player.getUniqueId()).getRank() != 0) {
								Staff staff = sManager.get(player.getUniqueId());
								if(staff.getRank() >= 7) {
									CustomEnchantments.maintenance = true;
									for(Player p : Bukkit.getOnlinePlayers()) {
										p.kickPlayer(new CCT().colorize("&2&l[DungeonForge]: &cA protocol procedure was activated, standby."));
									}
									for(OfflinePlayer temp : Bukkit.getOfflinePlayers()) {
										Player p = m.convertOfflinePlayer(temp.getUniqueId());
										if(p.getName() != null) {
											p.getInventory().clear();
											p.saveData();
										}
									}
									CustomEnchantments.maintenance = false;
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank is not high enough to use this command!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou aren't staff!"));
							}
						}
						else if(args[0].equalsIgnoreCase("shutdown")) {
							if(sManager.contains(player.getUniqueId()) && sManager.get(player.getUniqueId()).getRank() != 0) {
								Staff staff = sManager.get(player.getUniqueId());
								if(staff.getRank() >= 7) {
									CustomEnchantments.shutdown = true;
									for(Player p : Bukkit.getOnlinePlayers()) {
										if(p != null) {
											p.kickPlayer(new CCT().colorize("&2&l[DungeonForge]: &cThe server is going into shutdown, try joining back in 5 minutes."));
										}
									}
									new BukkitRunnable() {
										@Override
										public void run() {
											Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cRestarting!"));
											Bukkit.getServer().shutdown();
										}
									}.runTaskLater(CustomEnchantments.getInstance(), 6000L);
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank is not high enough to use this command!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou aren't staff!"));
							}
						}
						else if(args[0].equalsIgnoreCase("softreset")) {
							if(sManager.contains(player.getUniqueId()) && sManager.get(player.getUniqueId()).getRank() != 0) {
								Staff staff = sManager.get(player.getUniqueId());
								if(staff.getRank() >= 8) {
									CustomEnchantments.shutdown = true;
									for(Player p : Bukkit.getOnlinePlayers()) {
										if(p != null) {
											p.kickPlayer(new CCT().colorize("&2&l[DungeonForge]: &cThe server is going into shutdown, try joining back in 5 minutes."));
										}
									}
									for(OfflinePlayer temp : Bukkit.getOfflinePlayers()) {
										Player p = m.convertOfflinePlayer(temp.getUniqueId());
										if(p.getName() != null) {
											p.getInventory().clear();
											p.getEnderChest().clear();
											p.saveData();
										}
									}
									money.getMoneyList().clear();
									CustomEnchantments.getInstance().factionList.clear();
									CustomEnchantments.getInstance().dfPlayerList.clear();
									new BukkitRunnable() {
										@Override
										public void run() {
											Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cRestarting!"));
											Bukkit.getServer().shutdown();
										}
									}.runTaskLater(CustomEnchantments.getInstance(), 100L);
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank is not high enough to use this command!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou aren't staff!"));
						}
					}
					else if(args.length == 2) {
						if(args[0].equalsIgnoreCase("clear")) {
							if(sManager.contains(player.getUniqueId()) && sManager.get(player.getUniqueId()).getRank() != 0) {
								Staff staff = sManager.get(player.getUniqueId());
								if(staff.getRank() >= 7) {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aProcedure clear initiated."));
									Player p = m.convertOfflinePlayer(args[1]);
									if(p.getName() != null) {
										Player p1 = Bukkit.getPlayer(args[1]);
										if(p1 != null) {
											p1.kickPlayer(new CCT().colorize("&2&l[DungeonForge]: &cA protocol procedure was activated, standby."));
										}
										p.getInventory().clear();
										p.getEnderChest().clear();
										p.saveData();
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aProcedure clear completed."));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cProcedure clear failed, player doesn't exist."));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank is not high enough to use this command!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou aren't staff!"));
							}
						}
						else if(args[0].equalsIgnoreCase("maintenance")) {
							if(sManager.contains(player.getUniqueId()) && sManager.get(player.getUniqueId()).getRank() != 0) {
								Staff staff = sManager.get(player.getUniqueId());
								if(staff.getRank() >= 7) {
									if(args[0].equalsIgnoreCase("maintenance")) {
										if(args[1].equalsIgnoreCase("on")) {
											CustomEnchantments.maintenance = true;
											for(Player p : Bukkit.getOnlinePlayers()) {
												if(p != player) {
													if(sManager.contains(p.getUniqueId()) && sManager.get(p.getUniqueId()).getRank() == 0) {
														p.kickPlayer(new CCT().colorize("&2&l[DungeonForge]: &cMaintenance mode was initiated, standby."));
													}
												}
											}
										}
										else if(args[1].equalsIgnoreCase("off")) {
											CustomEnchantments.maintenance = false;
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cMaintenance mode is disengaged. Player's can now join again."));
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCommand ussage: /procedure maintenance (Off | On)"));
										}
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank is not high enough to use this command!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou aren't staff!"));
							}
						}
					}
				}
			}
		}
		return true;
	}
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        if(command.getName().equalsIgnoreCase("procedure")) { 
            if(sender instanceof Player) { 
            	if(args.length == 1) {
            		return Arrays.asList("maintenance", "clearinv", "shutdown");
            	}
            	else if(args.length == 2) {
            		if(args[0].equalsIgnoreCase("maintenance")) {
            			return Arrays.asList("on", "off");
            		}
            	}
            }
        }
        return null;
    }
	@EventHandler
	public void onPingChange(ServerListPingEvent event) {
		if(CustomEnchantments.maintenance == true) {
			event.setMotd(new CCT().colorize("&6&l          >-----&2&lDungeonForge&6&l-----<                        &c&lMAINTENANCE &2&lALPHA &6" + CustomEnchantments.getInstance().getDescription().getVersion() + " &cJ"));
		}
		else {
			event.setMotd(new CCT().colorize("&6&l          >-----&2&lDungeonForge&6&l-----<                             &2&lOPEN &2&lALPHA &6" + CustomEnchantments.getInstance().getDescription().getVersion() + " &cJ"));
		}
	}
	@EventHandler
	public void join(AsyncPlayerPreLoginEvent event) {
		if(CustomEnchantments.shutdown == true) {
			event.disallow(Result.KICK_OTHER, "");
			event.setKickMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server is going into shutdown, try to join back in 5 minutes."));
		}
		else if(CustomEnchantments.load == true) {
			event.disallow(Result.KICK_OTHER, "");
			event.setKickMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server is loading, try to join back in a few seconds."));
		}
		else if(CustomEnchantments.maintenance == true) {
			if(!sManager.contains(event.getPlayerProfile().getId()) || sManager.get(event.getPlayerProfile().getId()).getRank() == 0) {
				event.disallow(Result.KICK_OTHER, "");
				event.setKickMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server is in maintenance mode, try to join again later."));
			}
		}
		else if(joinCooldown == true) {
			event.disallow(Result.KICK_OTHER, "");
			event.setKickMessage(new CCT().colorize("&2&l[DungeonForge]: &cTo many players are trying to join at once! Try again later."));
		}
		else {
			joinCooldown = true;
			new BukkitRunnable() {
				public void run() {
					joinCooldown = false;
				}
			}.runTaskLater(CustomEnchantments.getInstance(), 30L);
		}
	}	
	public void priorityQueue() {
		for(OfflinePlayer temp : Bukkit.getOfflinePlayers()) {
			Player p = m.convertOfflinePlayer(temp.getUniqueId());
			if(p.getName() != null) {
				this.punishJoin(p);
			}
		}
	}
	public ItemStack compass() {
		ItemStack item = new ItemStack(Material.COMPASS, 1);
		ItemMeta meta = item.getItemMeta();	
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(new CCT().colorize("&6Teleport"));
		lore.add(new CCT().colorize("&f-------------------"));
		lore.add(new CCT().colorize("&fRight click to teleport to a player."));
		lore.add(new CCT().colorize("&f-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setString("TeleporterItem", "");
		return tempItem.getItem();
	}
	public ItemStack punish() {
		ItemStack item = new ItemStack(Material.PAPER, 1);
		ItemMeta meta = item.getItemMeta();	
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(new CCT().colorize("&cPunish"));
		lore.add(new CCT().colorize("&f-------------------"));
		lore.add(new CCT().colorize("&fRight click to punish a player."));
		lore.add(new CCT().colorize("&f-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setString("PunishItem", "");
		return tempItem.getItem();
	}
	public ItemStack vanish() {
		ItemStack item = new ItemStack(Material.LIME_DYE, 1);
		ItemMeta meta = item.getItemMeta();	
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(new CCT().colorize("&aVanish"));
		lore.add(new CCT().colorize("&f-------------------"));
		lore.add(new CCT().colorize("&fRight click to vanish/unvanish."));
		lore.add(new CCT().colorize("&f-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setString("VanishItem", "");
		return tempItem.getItem();
	}
	public ItemStack playerInfo() {
		ItemStack item = new ItemStack(Material.CLOCK, 1);
		ItemMeta meta = item.getItemMeta();	
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(new CCT().colorize("&bInfo"));
		lore.add(new CCT().colorize("&f-------------------"));
		lore.add(new CCT().colorize("&fRight click a player for their info."));
		lore.add(new CCT().colorize("&f-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setString("InfoItem", "");
		return tempItem.getItem();
	}
	public ItemStack examine() {
		ItemStack item = new ItemStack(Material.CHEST, 1);
		ItemMeta meta = item.getItemMeta();	
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(new CCT().colorize("&4Examine"));
		lore.add(new CCT().colorize("&f-------------------"));
		lore.add(new CCT().colorize("&fRight click to view an inventory of a player."));
		lore.add(new CCT().colorize("&f-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setString("ExamineItem", "");
		return tempItem.getItem();
	}
	public ItemStack spawner() {
		ItemStack item = new ItemStack(Material.SPAWNER, 1);
		ItemMeta meta = item.getItemMeta();	
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(new CCT().colorize("&dSpawner Setup"));
		lore.add(new CCT().colorize("&f-------------------"));
		lore.add(new CCT().colorize("&fRight click to switch to setting up spawners."));
		lore.add(new CCT().colorize("&f-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setString("SpawnerItem", "");
		return tempItem.getItem();
	}
	public ItemStack loot() {
		ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
		ItemMeta meta = item.getItemMeta();	
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(new CCT().colorize("&5Loot Setup"));
		lore.add(new CCT().colorize("&f-------------------"));
		lore.add(new CCT().colorize("&fRight click to switch to setting up loot chests."));
		lore.add(new CCT().colorize("&f-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setString("LootItem", "");
		return tempItem.getItem();
	}
	public void punishJoin(Player p) {
		if(!pManager.contains(p.getUniqueId())) {
			new Punish(p.getUniqueId());
		}
		if(p.hasPermission("owner")) {
			new Staff(p.getUniqueId(), 10);
		}
		else if(p.hasPermission("manager")) {
			new Staff(p.getUniqueId(), 9);
		}
		else if(p.hasPermission("headadmin")) {
			new Staff(p.getUniqueId(), 8);
		}
		else if(p.hasPermission("admin")) {
			new Staff(p.getUniqueId(), 7);
		}
		else if(p.hasPermission("headmod")) {
			new Staff(p.getUniqueId(), 6);
		}
		else if(p.hasPermission("moderator")) {
			new Staff(p.getUniqueId(), 5);
		}
		else if(p.hasPermission("helper+")) {
			new Staff(p.getUniqueId(), 4);
		}
		else if(p.hasPermission("helper")) {
			new Staff(p.getUniqueId(), 3);
		}
		else if(p.hasPermission("qaadmin")) {
			new Staff(p.getUniqueId(), 2);
		}
		else if(p.hasPermission("qa")) {
			new Staff(p.getUniqueId(), 1);
		}
		else {
			new Staff(p.getUniqueId(), 0);
		}
	}
	@EventHandler
	public void punishJoin(PlayerJoinEvent event) {
		this.punishJoin(event.getPlayer());
	}

	@EventHandler
	public void staffListLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getStaffMode() == true) {
				staff.switchStaffMode(false);
				player.getInventory().setContents(staff.getInv());
			}
		}
	}
	@EventHandler
	public void staffListLeave(PlayerKickEvent event) {
		Player player = event.getPlayer();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getStaffMode() == true) {
				staff.switchStaffMode(false);
				player.getInventory().setContents(staff.getInv());
			}
		}
	}
	@EventHandler
	public void cancelPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getStaffMode() == true) {
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void cancelDestroy(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getStaffMode() == true) {
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void cancelInvClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			if(sManager.contains(player.getUniqueId())) {
				Staff staff = sManager.get(player.getUniqueId());
				if(staff.getStaffMode() == true) {
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void foodChangeStaff(FoodLevelChangeEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(sManager.contains(player.getUniqueId())) {
				Staff staff = sManager.get(player.getUniqueId());
				if(staff.getStaffMode() == true) {
					player.setFoodLevel(20);
				}
			}
		}
	}
	@EventHandler
	public void cancelHealthLoss(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(sManager.contains(player.getUniqueId())) {
				Staff staff = sManager.get(player.getUniqueId());
				if(staff.getStaffMode() == true) {
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void cancelHealthLossEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(sManager.contains(player.getUniqueId())) {
				Staff staff = sManager.get(player.getUniqueId());
				if(staff.getStaffMode() == true) {
					event.setCancelled(true);
				}
			}
		}
		else if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(sManager.contains(player.getUniqueId())) {
				Staff staff = sManager.get(player.getUniqueId());
				if(staff.getStaffMode() == true) {
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void chatAttempt(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		if(pManager.contains(p.getUniqueId())) {
			Punish pun = pManager.get(p.getUniqueId());
			if(pun.getMutePerm()) {
				event.setCancelled(true);
				p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't chat because you have been permanently muted!"));
			}
			else if(pun.getMuteTime() > System.currentTimeMillis()) {
				event.setCancelled(true);
				Long timeLeft = pun.getMuteTime() - System.currentTimeMillis();
				long diffSeconds = timeLeft / 1000 % 60;
		        long diffMinutes = timeLeft / (60 * 1000) % 60;
		        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
		        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
			    String time = diffDays + " " + diffHours + ":" + diffMinutes + ":" + diffSeconds;
				p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't chat because you are muted! You can chat again in: &6" + time));
			}
			
			else {
				pun.setMuteTime(0L);
			}
		}
	}
	@EventHandler
	public void kickBan(AsyncPlayerPreLoginEvent event) {
		UUID uuid = event.getPlayerProfile().getId();
		if(pManager.contains(uuid)) {
			Punish pun = pManager.get(uuid);
			if(pun.getBanPerm()) {
				event.disallow(Result.KICK_BANNED, "");
				event.setKickMessage(new CCT().colorize(
						"&cYou have been banned!"
						+ "\n&cYou can join back in: &c&lNever"
						+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
						+ "\n&cBanned By: &6" + pun.getBannedBy(pun.getBannedByList().size() - 1)
						+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"));
			}
			else if(pun.getBanTime() > System.currentTimeMillis()) {
				event.disallow(Result.KICK_BANNED, "");
				Long timeLeft = pun.getBanTime() - System.currentTimeMillis();
				long diffSeconds = timeLeft / 1000 % 60;
		        long diffMinutes = timeLeft / (60 * 1000) % 60;
		        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
		        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
			    String time = diffDays + " " + diffHours + ":" + diffMinutes + ":" + diffSeconds;
				event.setKickMessage(new CCT().colorize(
						"&cYou have been banned!"
						+ "\n&cYou can join back in: &b&l" + time
						+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
						+ "\n&cBanned By: &6" + pun.getBannedBy(pun.getBannedByList().size() - 1)
						+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"));
			}
			else {
				pun.setBanTime(0L);
			}
		}
	}

	@EventHandler
	public void activatePerk(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getStaffMode() == true) {
				if(player.getInventory().getItemInMainHand() != null) {
					ItemStack i = player.getInventory().getItemInMainHand();
					NBTItem item = new NBTItem(i);
					if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if(item.hasKey("TeleporterItem")) {
							gui.InventoryTeleport(player, 1);
						}
						else if(item.hasKey("VanishItem")) {
							if(staff.getVanishMode() == true) {
								staff.switchVanishMode(false);
								for(Player p : Bukkit.getOnlinePlayers()) {
									p.showPlayer(CustomEnchantments.getInstance(), player);
								}
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &c&lVanish Disabled."));
							}
							else if(staff.getVanishMode() == false) {
								staff.switchVanishMode(true);
								for(Player p : Bukkit.getOnlinePlayers()) {
									p.hidePlayer(CustomEnchantments.getInstance(), player);
								}
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &a&lVanish Enabled."));
							}
						}
						else if(item.hasKey("SpawnerItem")) {
							staff.switchSpawnerMode(true);
							player.getInventory().clear();
							player.getInventory().setItem(0, gui.createDisplayItem(
									Material.COMPASS, 
									"&aSpawner Menu", 
									new ArrayList<String>(Arrays.asList(
										"&7Right click this item to open",
										"&7up the spawner menu to create/delete spawner items."
									)),
									"SpawnerCreate"
							));
							player.getInventory().setItem(1, gui.createDisplayItem(
									Material.BARRIER, 
									"&cSpawner Delete", 
									new ArrayList<String>(Arrays.asList(
										"&7Right click this item to delete",
										"&7any spawner. Make sure there is a block",
										"&7actually placed in the spawner."
									)),
									"SpawnerDelete"
							));
							player.getInventory().setItem(2, gui.createDisplayItem(
									Material.YELLOW_STAINED_GLASS, 
									"&6Spawner See", 
									new ArrayList<String>(Arrays.asList(
										"&7Right click this item to see",
										"&7any spawner in a 20 block radius.",
										"&7These spawners will turn into yellow glass."
									)),
									"SpawnerSee"
							));
						}
						else if(item.hasKey("LootItem")) {
							staff.switchLootMode(true);
							player.getInventory().clear();
							player.getInventory().setItem(0, gui.createDisplayItem(
									Material.COMPASS, 
									"&aLoot Menu", 
									new ArrayList<String>(Arrays.asList(
										"&7Right click this item to open",
										"&7up the loot chests menu to create/delete spawner items."
									)),
									"LootCreate"
							));
							player.getInventory().setItem(1, gui.createDisplayItem(
									Material.BARRIER, 
									"&cLoot Delete", 
									new ArrayList<String>(Arrays.asList(
										"&7Right click this item to delete",
										"&7any loot chest. Make sure there is a block",
										"&7actually placed in the position of the loot chest.",
										"&7(A chest in its place is also valid)"
									)),
									"LootDelete"
							));
							player.getInventory().setItem(2, gui.createDisplayItem(
									Material.YELLOW_STAINED_GLASS, 
									"&6Loot See", 
									new ArrayList<String>(Arrays.asList(
										"&7Right click this item to see",
										"&7any loot chest in a 20 block radius.",
										"&7These loot chests will turn into yellow glass."
									)),
									"LootSee"
							));
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void activePerk(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getStaffMode() == true) {
				if(player.getInventory().getItemInMainHand() != null && event.getRightClicked() != null) {
					if(event.getRightClicked() instanceof Player) {
						Player clicked = (Player) event.getRightClicked();
						ItemStack i = player.getInventory().getItemInMainHand();
						NBTItem item = new NBTItem(i);
						if(item.hasKey("InfoItem")) {
							gui.InfoMenu(player, clicked);
						}
						else if(item.hasKey("ExamineItem")) {
							gui.ExamineMenu(player, clicked);
						}
						else if(item.hasKey("PunishItem")) {
							gui.PunishMenu(player, clicked);
							target.put(player.getUniqueId(), clicked.getUniqueId());
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou need to right click a player."));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cNo player targetted."));
				}
			}
		}
	}
	@EventHandler
	public void teleportInv(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Teleport")) {
				if(item != null) {
					String name = item.getItemMeta().getDisplayName();
					title = ChatColor.stripColor(title);
					String split[] = title.split("/");
					String currentPageS = split[0].replaceAll("[^\\d.]", "");
					String maxPageS = split[1].replaceAll("[^\\d.]", "");
					int currentPage = Integer.parseInt(currentPageS);
					int maxPage = Integer.parseInt(maxPageS);
					if(item.getType() == Material.PLAYER_HEAD) {
						SkullMeta meta = (SkullMeta) item.getItemMeta();
						if(meta.getOwningPlayer().getPlayer().isOnline()) {
							player.teleport(meta.getOwningPlayer().getPlayer());
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have teleported to &6" + meta.getOwningPlayer().getPlayer().getName()));
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player isn't online anymore!"));
						}
					}
					else if(ChatColor.stripColor(name).equals("Next Page")) {
						if(currentPage < maxPage) {
							player.closeInventory();
							gui.InventoryTeleport(player, currentPage + 1);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThere are no more pages then this."));
						}
					}
					else if(ChatColor.stripColor(name).equals("Previous Page")) {
						if(currentPage > maxPage) {
							player.closeInventory();
							gui.InventoryTeleport(player, currentPage - 1);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThere are no more pages then this."));
						}
					}
					else if(ChatColor.stripColor(name).equals("Close")) {
						player.closeInventory();
					}
				}
			}
		}
	}
	@EventHandler
	public void punishMenu(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish")) {
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasDisplayName()) {
							String name = item.getItemMeta().getDisplayName();
							if(name.contains("Ban")) {
								if(target.containsKey(player.getUniqueId())) {
									gui.BanChoice(player, Bukkit.getPlayer(target.get(player.getUniqueId())));
									player.closeInventory();
								}
							}
							else if(name.contains("Mute")) {
								if(target.containsKey(player.getUniqueId())) {
									gui.MuteChoice(player, Bukkit.getPlayer(target.get(player.getUniqueId())));
									player.closeInventory();
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void banChoice(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish")) {
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasDisplayName()) {
							Punish pun = pManager.get(target.get(player.getUniqueId()));
							String name = item.getItemMeta().getDisplayName();
							if(name.contains("Ban")) {
								if(target.containsKey(player.getUniqueId())) {
									player.closeInventory();
									if(!pun.isBanned()) {
										gui.BanReason(player, Bukkit.getPlayer(target.get(player.getUniqueId())));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is already banned!"));
									}
								}
							}
							else if(name.contains("Unban")) {
								if(target.containsKey(player.getUniqueId())) {
									player.closeInventory();
									if(pun.isBanned()) {
										pun.setBanTime(0L);
										pun.removeBannedBy(pun.getBanOffense() - 1);
										pun.removeBanReason(pun.getBanOffense() - 1);
										Player offender = Bukkit.getPlayer(target.get(player.getUniqueId()));
										if(offender != null && offender.isOnline()) {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have unbanned &6" + offender.getName()));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not banned!"));
									}
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void banReason(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(target.containsKey(player.getUniqueId())) {
						Player p = m.convertOfflinePlayer(target.get(player.getUniqueId()));
						if(p.getName() != null) {
							Punish pun = pManager.get(p.getUniqueId());
							if(item.hasKey("Combat Related")) {
								pun.setBanPerm(true);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have banned &6" + p.getName() + " &cpermanently."));
								player.closeInventory();
								pun.addBannedBy(player.getName());
								pun.addBanReason("Combat Related");
								Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
										"&cYou have been banned!"
										+ "\n&cYou can join back in: &c&lNever"
										+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)		
										+ "\n&cBanned By: &6" + player.getName()
										+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
								));
							}
							else if(item.hasKey("Auto Related")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Auto Related");
									pun.setBanTime(System.currentTimeMillis() + 5184000000L);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have banned &6" + p.getName() + " &cfor: &b60 Days"));
									Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
											"&cYou have been banned!"
											+ "\n&cYou can join back in: &b&l60 Days"
											+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
											+ "\n&cBanned By: &6" + player.getName()
											+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
									));
								}
								else if(pun.getBanOffense() >= 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Auto Related");
									pun.setBanPerm(true);
									Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
											"&cYou have been banned!"
											+ "\n&cYou can join back in: &c&lNever"
											+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
											+ "\n&cBanned By: &6" + player.getName()
											+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
									));
								}
								player.closeInventory();
							}
							if(item.hasKey("Movement Related")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Movement Related");
									pun.setBanTime(System.currentTimeMillis() + 5184000000L);
									Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
											"&cYou have been banned!"
											+ "\n&cYou can join back in: &b&l60 Days"
											+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
											+ "\n&cBanned By: &6" + player.getName()
											+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
									));
								}
								else if(pun.getBanOffense() >= 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Movement Related");
									pun.setBanPerm(true);
									Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
											"&cYou have been banned!"
											+ "\n&cYou can join back in: &c&lNever"
											+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
											+ "\n&cBanned By: &6" + player.getName()
											+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
									));
								}
								player.closeInventory();
							}
							if(item.hasKey("ESP Related")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("ESP Related");
									pun.setBanTime(System.currentTimeMillis() + 2592000000L);
									Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
											"&cYou have been banned!"
											+ "\n&cYou can join back in: &b&l30 Days"
											+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
											+ "\n&cBanned By: &6" + player.getName()
											+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
									));
								}
								else if(pun.getBanOffense() >= 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("ESP Related");
									pun.setBanPerm(true);
									Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
											"&cYou have been banned!"
											+ "\n&cYou can join back in: &c&lNever"
											+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
											+ "\n&cBanned By: &6" + player.getName()
											+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
									));
								}
								player.closeInventory();
							}
							if(item.hasKey("Health Related")) {
								pun.setBanPerm(true);
								player.closeInventory();
								pun.addBannedBy(player.getName());
								pun.addBanReason("Health Related");
								Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
										"&cYou have been banned!"
										+ "\n&cYou can join back in: &c&lNever"
										+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
										+ "\n&cBanned By: &6" + player.getName()
										+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
								));
							}
							if(item.hasKey("Building Related")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Building Related");
									pun.setBanTime(System.currentTimeMillis() + 2592000000L);
									Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
											"&cYou have been banned!"
											+ "\n&cYou can join back in: &b&l30 Days"
											+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
											+ "\n&cBanned By: &6" + player.getName()
											+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
									));
								}
								else if(pun.getBanOffense() >= 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Building Related");
									pun.setBanPerm(true);
									Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
											"&cYou have been banned!"
											+ "\n&cYou can join back in: &c&lNever"
											+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
											+ "\n&cBanned By: &6" + player.getName()
											+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
									));
								}
								player.closeInventory();
							}
							if(item.hasKey("Wallhack Related")) {
								pun.setBanPerm(true);
								pun.addBannedBy(player.getName());
								pun.addBanReason("Wallhack Related");
								Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
										"&cYou have been banned!"
										+ "\n&cYou can join back in: &b&l30 Days"
										+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
										+ "\n&cBanned By: &6" + player.getName()
										+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
								));
								player.closeInventory();
							}
							if(item.hasKey("Party Related")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Party Related");
									pun.setBanTime(System.currentTimeMillis() + 604800000L);
									Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
											"&cYou have been banned!"
											+ "\n&cYou can join back in: &b&l7 Days"
											+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
											+ "\n&cBanned By: &6" + player.getName()
											+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
									));
								}
								if(pun.getBanOffense() == 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Party Related");
									pun.setBanTime(System.currentTimeMillis() + 2592000000L);
									Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
											"&cYou have been banned!"
											+ "\n&cYou can join back in: &b&l30 Days"
											+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
											+ "\n&cBanned By: &6" + player.getName()
											+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
									));
								}
								else if(pun.getBanOffense() >= 2) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Party Related");
									pun.setBanPerm(true);
									Bukkit.getPlayer(target.get(player.getUniqueId())).kickPlayer(new CCT().colorize(
											"&cYou have been banned!"
											+ "\n&cYou can join back in: &c&lNever"
											+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)
											+ "\n&cBanned By: &6" + player.getName()
											+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
									));
								}
								player.closeInventory();
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void muteChoice(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish")) {
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasDisplayName()) {
							Punish pun = pManager.get(target.get(player.getUniqueId()));
							String name = item.getItemMeta().getDisplayName();
							if(name.contains("Mute")) {
								if(target.containsKey(player.getUniqueId())) {
									player.closeInventory();
									if(!pun.isMuted()) {
										gui.MuteReason(player, Bukkit.getPlayer(target.get(player.getUniqueId())));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is already banned!"));
									}
								}
							}
							else if(name.contains("Unmute")) {
								if(target.containsKey(player.getUniqueId())) {
									player.closeInventory();
									if(pun.isMuted()) {
										pun.setMuteTime(0L);
										pun.setMutePerm(false);
										pun.removeMutedBy(pun.getMuteOffense() - 1);
										pun.removeMuteReason(pun.getMuteOffense() - 1);
										Player offender = Bukkit.getPlayer(target.get(player.getUniqueId()));
										if(offender != null && offender.isOnline()) {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have unmuted &6" + offender.getName()));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not muted!"));
									}
									
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void muteReason(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(target.containsKey(player.getUniqueId())) {
						Player p = m.convertOfflinePlayer(target.get(player.getUniqueId()));
						if(p.getName() != null) {
							Punish pun = pManager.get(p.getUniqueId());
							if(item.hasKey("Spamming Related")) {
								if(pun.getMuteOffense() == 0) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Spamming/Bypassing the chat filter.");
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have warned &6" + p.getName() + " &cfor spamming/bypassing the chat filter."));
									p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been warned by: &6" + player.getName() + " &cfor spamming/bypassing the chat filter"));
								}
								else if(pun.getMuteOffense() == 1) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Spamming/Bypassing the chat filter.");
									pun.setMuteTime(System.currentTimeMillis() + 86400000L);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have muted &6" + p.getName() + " &cfor spamming/bypassing the chat filter for 24 hours."));
									p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been muted by: &6" + player.getName() + " &cfor spamming/bypassing the chat filter for 24 hours."));
								}
								else if(pun.getMuteOffense() >= 2) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Spamming/Bypassing the chat filter.");
									pun.setMutePerm(true);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have permanently muted &6" + p.getName() + " &cfor spamming/bypassing the chat filter."));
									p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been permanently muted by: &6" + player.getName() + " &cfor spamming/bypassing the chat filter."));
								}
							}
							else if(item.hasKey("Insult Related")) {
								if(pun.getMuteOffense() == 0) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Insulting a player.");
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have warned &6" + p.getName() + " &cfor insulting a player."));
									p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been warned by: &6" + player.getName() + " &cfor insulting a player."));
								}
								else if(pun.getMuteOffense() == 1) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Insulting a player.");
									pun.setMuteTime(System.currentTimeMillis() + 604800000L);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have muted &6" + p.getName() + " &cfor insulting a player for 7 days."));
									p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been muted by: &6" + player.getName() + " &cfor insulting a player for 7 days."));
								}
								else if(pun.getMuteOffense() >= 2) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Insulting a player.");
									pun.setMutePerm(true);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have permanently muted &6" + p.getName() + " &cfor insulting a player."));
									p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been permanently muted by: &6" + player.getName() + " &cfor insulting a player."));
								}
							}
							else if(item.hasKey("Slur Related")) {
								if(pun.getMuteOffense() == 0) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("using Racist/Sexual/Pedopholic slurs.");
									pun.setMuteTime(System.currentTimeMillis() + 1209600000L);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have muted &6" + p.getName() + " &cfor using racist/sexual/pedopholic slurs for 14 days."));
									p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been muted by: &6" + player.getName() + " &cfor using racist/sexual/pedopholic slurs for 14 days."));
								}
								else if(pun.getMuteOffense() >= 1) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Using Racist/Sexual/Pedopholic slurs.");
									pun.setMutePerm(true);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have permanently muted &6" + p.getName() + " &cfor using racist/sexual/pedopholic slurs."));
									p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been permanently muted by: &6" + player.getName() + " &cfor using racist/sexual/pedopholic slurs."));
								}
							}
							else if(item.hasKey("Threat Related")) {
								pun.addMutedBy(player.getName());
								pun.addMuteReason("Threatening.");
								pun.setMutePerm(true);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have permanently muted &6" + p.getName() + " &cfor threatening someone."));
								p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been permanently muted by: &6" + player.getName() + " &cfor threatening someone."));
							}
							else if(item.hasKey("Chat Related")) {
								if(pun.getMuteOffense() == 0) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Using Chat Modifications.");
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have warned &6" + p.getName() + " &cfor using Chat Modifications."));
									p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been warned by: &6" + player.getName() + " &cfor using Chat Modifications."));
								}
								else if(pun.getMuteOffense() == 1) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Using Chat Modifications.");
									pun.setMuteTime(System.currentTimeMillis() + 86400000L);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have muted &6" + p.getName() + " &cfor using Chat Modifications for 24 hours."));
									p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been muted by: &6" + player.getName() + " &cfor using Chat Modifications for 24 hours."));
								}
								else if(pun.getMuteOffense() >= 2) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Using Chat Modifications.");
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have permanently muted &6" + p.getName() + " &cfor Chat Modifications."));
									p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have been permanently muted by: &6" + player.getName() + " &cfor Chat Modifications."));
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void cancelPunish(InventoryCloseEvent event) {
		if(event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			if(event.getReason() == Reason.PLAYER) {
				if(event.getView().getTitle().contains("Punish")) {
					target.remove(player.getUniqueId());
					reason.remove(player.getUniqueId());
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cReport cancelled."));
				}
			}
		}
	}
	@EventHandler
	public void spawnerMenu(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		ItemStack i = player.getInventory().getItemInMainHand();
		if(i != null) {
			NBTItem item = new NBTItem(i);
			if(item.hasKey("SpawnerCreate")) {
				gui.SpawnerCreate(player);
			}
			else if(item.hasKey("SpawnerDelete")){
				boolean message = false;
				Location loc = event.getClickedBlock().getLocation();
				for(DFSpawner spawner : spManager.getSpawnerList().values()) {
					if(spawner != null) {
						if(spawner.getLocation().distance(loc) <= 2.5) {
							spManager.remove(spawner.getUUID());
							message = true;
						}
					}
				}
				if(message == true) {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aSpawner Removed!"));
				}
				else if(message == false) {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cNo spawner found."));
				}
			}
			else if(item.hasKey("SpawnerSee")) {
				int count = 0;
				for(DFSpawner spawner : spManager.getSpawnerList().values()) {
					if(spawner != null) {
						if(player.getLocation().distance(spawner.getLocation()) <= 40.0) {
							player.sendBlockChange(spawner.getLocation(), Material.YELLOW_STAINED_GLASS.createBlockData());
							count++;
							new BukkitRunnable() {
								public void run() {
									Location loc = spawner.getLocation();
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aSpawner Location: " + String.format("%.2f", loc.getX()) + " " + String.format("%.2f", loc.getY()) + " " + String.format("%.2f", loc.getZ())));
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 1L);
						}
					}
				}
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + count + " &aspawners found."));
			}
		}
	}
	@EventHandler
	public void chooseSlot(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Spawner")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("SpawnerSlot")) {
						spawnerSlot.put(player.getUniqueId(), event.getSlot() - 9);
						gui.SpawnerEntity(player);
					}
					else if(item.hasKey("SpawnerItemDelete")) {
						if(event.getClick() == ClickType.LEFT) {
							spawnerSlot.put(player.getUniqueId(), event.getSlot() - 9);
							gui.SpawnerEntity(player);
						}
						if(event.getClick() == ClickType.RIGHT) {
							player.getInventory().setItem(event.getSlot() - 9, new ItemStack(Material.AIR));
							gui.SpawnerCreate(player);
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void chooseEntityType(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Spawner")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("EntityType")) {
						EntityType type = item.getObject("EntityType", EntityType.class);
						spawnerType.put(player.getUniqueId(), type);
						gui.SpawnerTier(player);
					}
				}
			}
		}
	}
	@EventHandler
	public void chooseTier(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Spawner")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("Tier")) {
						int tier = item.getInteger("Tier");
						spawnerTier.put(player.getUniqueId(), tier);
						this.finishSpawnerCreation(player);
						player.closeInventory();
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aSpawner created!"));
					}
				}
			}
		}
	}

	@EventHandler
	public void placeSpawner(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack i = event.getItem();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getSpawnerMode() == true) {
				event.setCancelled(true);
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("SpawnerPlace")) {
						if(event.getClickedBlock() != null) {
							Location loc = event.getClickedBlock().getLocation().add(0.5, 0.5, 0.5);
							EntityType type = item.getObject("EntityTypeItem", EntityType.class);
							int tier = item.getInteger("Tier");
							new DFSpawner(loc, type, tier);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aSpawner placed!"));
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou need to click a block."));
						}
					}
				}
			}
		}
	}
	public void finishSpawnerCreation(Player player) {
		ItemStack item = new ItemStack(Material.EGG);
		ItemMeta meta = item.getItemMeta();
		String ent = spawnerType.get(player.getUniqueId()).toString();
		ent = ent.toLowerCase();
		ent = ent.substring(0, 1).toUpperCase() + ent.substring(1);
		ent = ent.replace("_", " ");
		meta.setDisplayName(new CCT().colorize("&7Spawner"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7Type: " + ent));
		lore.add(new CCT().colorize("&7Tier: " + spawnerTier.get(player.getUniqueId())));
		lore.add(new CCT().colorize("&7-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setObject("EntityTypeItem", spawnerType.get(player.getUniqueId()));
		i.setInteger("Tier", spawnerTier.get(player.getUniqueId()));
		i.setString("SpawnerPlace", "");
		i.setString("SpawnerItemDelete", "");
		player.getInventory().setItem(spawnerSlot.get(player.getUniqueId()), i.getItem());
		spawnerType.remove(player.getUniqueId());
		spawnerTier.remove(player.getUniqueId());
		spawnerSlot.remove(player.getUniqueId());
	}
	
	@EventHandler
	public void lootMenu(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		ItemStack i = player.getInventory().getItemInMainHand();
		if(i != null) {
			NBTItem item = new NBTItem(i);
			if(item.hasKey("LootCreate")) {
				gui.LootCreate(player);
			}
			else if(item.hasKey("LootDelete")){
				boolean message = false;
				Location loc = event.getClickedBlock().getLocation();
				for(LootChest loot : lcManager.getLootList().values()) {
					if(loot != null) {
						if(loot.getLocation().distance(loc) <= 2.5) {
							lcManager.remove(loot.getUUID());
							message = true;
						}
					}
				}
				if(message == true) {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aLoot Chest Removed!"));
				}
				else if(message == false) {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cNo Loot Chest found."));
				}
			}
			else if(item.hasKey("LootSee")) {
				int count = 0;
				for(LootChest loot : lcManager.getLootList().values()) {
					if(loot != null) {
						if(player.getLocation().distance(loot.getLocation()) <= 40.0) {
							player.sendBlockChange(loot.getLocation(), Material.YELLOW_STAINED_GLASS.createBlockData());
							count++;
							new BukkitRunnable() {
								public void run() {
									Location loc = loot.getLocation();
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aLoot Chest Location: " + String.format("%.2f", loc.getX()) + " " + String.format("%.2f", loc.getY()) + " " + String.format("%.2f", loc.getZ())));
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 1L);
						}
					}
				}
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + count + " &aloot chests found."));
			}
		}
	}
	@EventHandler
	public void chooseLootSlot(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Loot")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("LootSlot")) {
						lootSlot.put(player.getUniqueId(), event.getSlot() - 9);
						gui.LootRadius(player);
					}
					else if(item.hasKey("LootItemDelete")) {
						if(event.getClick() == ClickType.LEFT) {
							lootSlot.put(player.getUniqueId(), event.getSlot() - 9);
							gui.LootRadius(player);
						}
						if(event.getClick() == ClickType.RIGHT) {
							player.getInventory().setItem(event.getSlot() - 9, new ItemStack(Material.AIR));
							gui.LootCreate(player);
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void chooseRadiusType(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Loot")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("Radius")) {
						int radius = item.getInteger("Radius");
						lootRadius.put(player.getUniqueId(), radius);
						gui.LootTier(player);
					}
				}
			}
		}
	}
	@EventHandler
	public void chooseLootTier(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Loot")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("Tier")) {
						int tier = item.getInteger("Tier");
						lootTier.put(player.getUniqueId(), tier);
						this.finishLootCreation(player);
						player.closeInventory();
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aLoot Chest created!"));
					}
				}
			}
		}
	}
	@EventHandler
	public void placeLoot(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		ItemStack i = player.getInventory().getItemInMainHand();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getLootMode() == true) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("LootPlace")) {
						event.setCancelled(false);
						Location loc = event.getBlock().getLocation().add(0.5, 0.5, 0.5);
						int radius = item.getInteger("RadiusItem");
						int tier = item.getInteger("Tier");
						new LootChest(loc, tier, radius);
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aLoot Chest placed!"));
					}
				}
			}
		}
	}
	public void finishLootCreation(Player player) {
		ItemStack item = new ItemStack(Material.CHEST);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize("&7Loot Chest"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7Radius: " + lootRadius.get(player.getUniqueId())));
		lore.add(new CCT().colorize("&7Tier: " + lootTier.get(player.getUniqueId())));
		lore.add(new CCT().colorize("&7-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setInteger("RadiusItem", lootRadius.get(player.getUniqueId()));
		i.setInteger("Tier", lootTier.get(player.getUniqueId()));
		i.setString("LootPlace", "");
		i.setString("LootItemDelete", "");
		player.getInventory().setItem(lootSlot.get(player.getUniqueId()), i.getItem());
		lootRadius.remove(player.getUniqueId());
		lootTier.remove(player.getUniqueId());
		lootSlot.remove(player.getUniqueId());
	}
	@EventHandler
	public void cancelClick(InventoryClickEvent event) {
		InventoryView inv = event.getView();
		if(inv.getTitle().contains("Examine") || inv.getTitle().contains("Info") || inv.getTitle().contains("Teleport") || inv.getTitle().contains("Punish")) {
			event.setCancelled(true);
		}
	}
	
}
