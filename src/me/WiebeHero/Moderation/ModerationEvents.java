package me.WiebeHero.Moderation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
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
import org.bukkit.event.player.PlayerDropItemEvent;
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

import com.onarandombox.MultiverseCore.MultiverseCore;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.CustomClasses.Methods;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.MethodLuck;
import me.WiebeHero.CustomMethods.MethodMulti;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.LootChest.LootChest;
import me.WiebeHero.LootChest.LootChestManager;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;
import me.WiebeHero.RankedPlayerPackage.RankedManager;
import me.WiebeHero.RankedPlayerPackage.RankedPlayer;
import me.WiebeHero.Scoreboard.DFScoreboard;
import me.WiebeHero.Skills.ClassMenu;
import me.WiebeHero.Spawners.DFSpawner;
import me.WiebeHero.Spawners.DFSpawnerManager;
import net.luckperms.api.model.user.User;

public class ModerationEvents implements CommandExecutor,Listener,TabCompleter{
	public boolean joinCooldown = false;
	private RankedManager rManager;
	private DFFactionManager facManager;
	private DFPlayerManager dfManager;
	private PunishManager pManager;
	private StaffManager sManager;
	private DFSpawnerManager spManager;
	private LootChestManager lcManager;
	private ModerationGUI gui;
	private MethodMulti multi;
	private MethodLuck luck;
	private Methods m;
	private DFScoreboard board;
	private ClassMenu menu;
	private HashMap<UUID, Pair<UUID, String>> target = new HashMap<UUID, Pair<UUID, String>>();
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
	public String rank = "rank";
	public String checkstaff = "checkstaff";
	
	public ModerationEvents(DFFactionManager facManager, DFPlayerManager dfManager, RankedManager rManager, PunishManager pManager, StaffManager sManager, DFSpawnerManager spManager, LootChestManager lcManager, ModerationGUI gui, MethodMulti multi, MethodLuck luck, Methods m, DFScoreboard board, ClassMenu menu) {
		this.facManager = facManager;
		this.dfManager = dfManager;
		this.rManager = rManager;
		this.pManager = pManager;
		this.sManager = sManager;
		this.spManager = spManager;
		this.lcManager = lcManager;
		this.gui = gui;
		this.multi = multi;
		this.luck = luck;
		this.m = m;
		this.board = board;
		this.menu = menu;	
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
			if(cmd.getName().equalsIgnoreCase(rank)) {
				if(args.length == 3) {
					if(args[0].equalsIgnoreCase("set")) {
						if(rPlayer.isStaff()) {
							if(rPlayer.getHighestRank().rank >= Rank.ADMIN.rank) {
								OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
								if(p.getName() != null) {
									if(rManager.contains(p.getUniqueId())) {
										RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
										String r = args[2].toUpperCase();
										if(Rank.valueOf(r) != null) {
											Rank rank = Rank.valueOf(r);
											User promoted = luck.loadUser(p.getUniqueId());
											if(!player.getUniqueId().equals(p.getUniqueId())) {
												if(rPlayer.getHighestRank().rank > rank.rank) {
													if(rPlayer.getHighestRank().rank > oPlayer.getHighestRank().rank) {
														for(Rank ranks : Rank.values()) {
															String group = ranks.toString().toLowerCase();
															if(luck.containsParrent(promoted, group)) {
																luck.removeParent(promoted, group);
															}
														}
														luck.addParent(promoted, rank.toString().toLowerCase());
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName() + " to Owner!"));
														if(Bukkit.getPlayer(args[1]) != null) {
															Player t = Bukkit.getPlayer(args[1]);
															t.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have been promoted to Owner!"));
															board.updateScoreboard(t);
															this.punishJoin(t);
														}
													}
													else {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYour rank must be higher then the players rank you are setting!"));
													}
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou can't set a higher rank than this!"));
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't set your own rank!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis rank doesn't exist!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player has never joined DungeonForge!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank is not high enough to set someone's rank!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
						}
					}
				}
			}
			if(cmd.getName().equalsIgnoreCase(checkstaff)) {
				if(args.length == 1) {
					@SuppressWarnings("deprecation")
					OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
					if(p.getName() != null) {
						if(rManager.contains(p.getUniqueId())) {
							RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
							if(oPlayer.isStaff()) {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aThis player is staff. Rank: " + oPlayer.getHighestRank().display));
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not staff! If he is impersonating staff, please consider reporting him!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player has never joined DungeonForge!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player does not exist!"));
					}
				}
			}
			if(cmd.getName().equalsIgnoreCase(staffmode)) {
				if(sManager.contains(player.getUniqueId()) && rPlayer.getHighestRank().rank >= Rank.HELPER.rank) {
					Staff staff = sManager.get(player.getUniqueId());
					if(staff.getStaffMode() == true) {
						player.getInventory().setContents(staff.getInv());
						staff.switchStaffMode(false);
						staff.switchLootMode(false);
						if(staff.getVanishMode() == true) {
							staff.switchVanishMode(false);
							for(Player p : Bukkit.getOnlinePlayers()) {
								p.showPlayer(CustomEnchantments.getInstance(), player);
							}
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &c&lVanish Disabled."));
						}
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
						if(rPlayer.getHighestRank().rank >= Rank.ADMIN.rank) {
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
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().rank >= Rank.HELPER_PLUS.rank) {
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
							if(p.getName() != null) {
								if(rManager.contains(p.getUniqueId()) && pManager.contains(p.getUniqueId())) {
									RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
									if(rPlayer.getHighestRank().rank >= oPlayer.getHighestRank().rank) {
										Punish pun = pManager.get(p.getUniqueId());
										target.put(player.getUniqueId(), new Pair<UUID, String>(p.getUniqueId(), p.getName()));
										if(!pun.isBanned()) {
											gui.BanReason(player, p.getName());
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is already banned!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank needs to be higher then the player you're banning!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player has never joined DungeonForge!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
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
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().rank >= Rank.MOD.rank) {
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
							if(p.getName() != null) {
								if(rManager.contains(p.getUniqueId()) && pManager.contains(p.getUniqueId())) {
									Punish pun = pManager.get(p.getUniqueId());
									if(pun.isBanned()) {
										pun.setBanTime(0L);
										pun.setBanPerm(false);
										int offense = pun.getBanOffense();
										if(!pun.getBanReasonsList().isEmpty()) {
											pun.removeBanReason(offense - 1);
										}
										if(!pun.getBannedByList().isEmpty()) {
											pun.removeBannedBy(offense - 1);
										}
										if(!pun.getBanDateList().isEmpty()) {
											pun.removeBanDate(offense - 1);
										}
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have unbanned &6" + p.getName()));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not banned!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player has never joined DungeonForge!"));
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
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().rank >= Rank.HELPER.rank) {
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
							if(p.getName() != null) {
								if(rManager.contains(p.getUniqueId()) && pManager.contains(p.getUniqueId())) {
									RankedPlayer rp = rManager.getRankedPlayer(p.getUniqueId());
									if(rPlayer.getHighestRank().rank > rp.getHighestRank().rank) {
										target.put(player.getUniqueId(), new Pair<UUID, String>(p.getUniqueId(), p.getName()));
										gui.MuteReason(player, p.getName());
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank needs to be higher then the player you're muting!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player has never joined DungeonForge!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist."));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have permission to mute players!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCommand ussage: /mute (Player name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(unmute)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().rank >= Rank.HELPER_PLUS.rank) {
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
							if(p.getName() != null) {
								if(rManager.contains(p.getUniqueId()) && pManager.contains(p.getUniqueId())) {
									Punish pun = pManager.get(p.getUniqueId());
									if(pun.isMuted()) {
										pun.setMutePerm(false);
										pun.setMuteTime(0L);
										int offense = pun.getMuteOffense();
										if(!pun.getMuteReasonsList().isEmpty()) {
											pun.removeMuteReason(offense - 1);
										}
										if(!pun.getMutedByList().isEmpty()) {
											pun.removeMutedBy(offense - 1);
										}
										if(!pun.getMuteDateList().isEmpty()) {
											pun.removeMuteDate(offense - 1);
										}
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have unmuted &6" + p.getName()));
										if(Bukkit.getPlayer(p.getUniqueId()) != null) {
											Player offender = Bukkit.getPlayer(p.getUniqueId());
											offender.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas unmuted you!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not muted!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player has neven joined DungeonForge!"));
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
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCommand ussage: /unmute (Player name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(protocol)) {
				if(rPlayer.isStaff()) {
					if(rPlayer.getHighestRank().rank >= Rank.ADMIN.rank) {
						if(args.length == 1) {
							if(args[0].equalsIgnoreCase("clearall")) {
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
							else if(args[0].equalsIgnoreCase("shutdown")) {
								CustomEnchantments.shutdown = true;
								for(Player p : Bukkit.getOnlinePlayers()) {
									if(p != null) {
										p.kickPlayer(new CCT().colorize("&2&l[DungeonForge]: &cThe server is going into shutdown, try joining back in 5 minutes."));
									}
								}
								Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cRestarting!"));
								Bukkit.getServer().shutdown();
							}
							else if(args[0].equalsIgnoreCase("softreset")) {
								CustomEnchantments.shutdown = true;
								CustomEnchantments.maintenance = true;
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
								new BukkitRunnable() {
									@Override
									public void run() {
										Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cRestarting!"));
										Bukkit.getServer().shutdown();
									}
								}.runTaskLater(CustomEnchantments.getInstance(), 100L);
							}
							else if(args[0].equalsIgnoreCase("hardreset")) {
								CustomEnchantments.shutdown = true;
								CustomEnchantments.maintenance = true;
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
								MultiverseCore mul = multi.getMultiverseCore();
								if(mul.getMVWorldManager().getMVWorld("FactionWorld-1") != null) {
									mul.deleteWorld("FactionWorld-1");
									long mapSeed = new Random().nextLong();
									mul.getMVWorldManager().addWorld("FactionWorld-1", Environment.NORMAL, mapSeed + "", WorldType.NORMAL, false, "World");
								}
								else if(mul.getMVWorldManager().getMVWorld("FactionWorld-1") == null) {
									long mapSeed = new Random().nextLong();
									mul.getMVWorldManager().addWorld("FactionWorld-1", Environment.NORMAL, mapSeed + "", WorldType.NORMAL, false, "World");
								}
								World world = Bukkit.getWorld("FactionWorld-1");
								world.getWorldBorder().setSize(5000.00);
								new BukkitRunnable() {
									@Override
									public void run() {
										Bukkit.broadcastMessage(new CCT().colorize("&2&l[DungeonForge]: &cRestarting!"));
										Bukkit.getServer().shutdown();
									}
								}.runTaskLater(CustomEnchantments.getInstance(), 3000L);
							}
							if(args[0].equalsIgnoreCase("updatexpvalues")) {
								CustomEnchantments.maintenance = true;
								for(Entry<UUID, DFPlayer> entry : dfManager.getDFEntityList().entrySet()) {
									if(Bukkit.getOfflinePlayer(entry.getKey()).getName() != null) {
										DFPlayer dfPlayer = entry.getValue();
										int level = dfPlayer.getLevel();
										dfPlayer.setLevel(1);
										dfPlayer.setMaxExperience(750);
										for(int i = 1; i < level; i++) {
									        if(dfPlayer.getLevel() < 100) {
												dfPlayer.addLevel(1);
												dfPlayer.setMaxExperience((int)(double)(dfPlayer.getMaxExperience() / 100.00 * (dfPlayer.getExperienceMultiplier() * 100.00)));
											}
								        }
									}
								}
								CustomEnchantments.maintenance = false;
							}
						}
						else if(args.length == 2) {
							if(args[0].equalsIgnoreCase("clear")) {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aProcedure clearinv initiated."));
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
							else if(args[0].equalsIgnoreCase("maintenance")) {
								if(args[1].equalsIgnoreCase("on")) {
									CustomEnchantments.maintenance = true;
									for(Player p : Bukkit.getOnlinePlayers()) {
										if(p != player) {
											RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
											if(oPlayer.isStaff()) {
												p.kickPlayer(new CCT().colorize("&2&l[DungeonForge]: &cMaintenance mode was initiated, standby."));
											}
										}
									}
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cMaintenance mode is engaged. Player's can now not join."));
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
						else if(args.length == 3) {
							if(args[0].equalsIgnoreCase("profile")) {
								OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
								if(p.getName() != null) {
									if(args[2].equalsIgnoreCase("reset")) {
										if(dfManager.contains(p.getUniqueId())) {
											Player p1 = (Player) p;
											dfManager.resetEntity(p1.getUniqueId());
											p1.teleport(Bukkit.getWorld("DFWarzone-1").getSpawnLocation());
											p1.closeInventory();
											menu.ClassSelect(p1);
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have reset &6" + p1.getName() + "!"));
											p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas reset your player profile!"));
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player has never joined DF!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Usage: /procedure profile (Player Name) reset"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
								}
							}
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to execute this command!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou aren't staff!"));
				}
			}
		}
		return true;
	}
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        if(command.getName().equalsIgnoreCase("procedure")) { 
            if(sender instanceof Player) { 
            	Player player = (Player) sender;
            	RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
            	if(rPlayer.getHighestRank().rank >= Rank.ADMIN.rank) {
	            	if(args.length == 1) {
	            		return Arrays.asList("maintenance", "clearinv", "shutdown", "softreset", "hardreset", "profile", "updatexpvalues");
	            	}
	            	else if(args.length == 2) {
	            		if(args[0].equalsIgnoreCase("maintenance")) {
	            			return Arrays.asList("on", "off");
	            		}
	            	}
            	}
            }
        }
        return null;
    }
	@EventHandler
	public void staff(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(rManager.contains(player.getUniqueId())) {
			RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
			if(rPlayer.isStaff()) {
				sManager.add(player.getUniqueId(), new Staff());
			}
		}
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
			if(rManager.contains(event.getUniqueId())) {
				RankedPlayer rPlayer = rManager.getRankedPlayer(event.getUniqueId());
				if(!rPlayer.isStaff()) {
					event.disallow(Result.KICK_OTHER, "");
					event.setKickMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe server is in maintenance mode, try to join again later."));
				}
			}
			else {
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
			Punish pu = new Punish(p.getUniqueId());
			pManager.add(p.getUniqueId(), pu);
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
	public void cancelDrop(PlayerDropItemEvent event) {
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
					if(event.getAction() == Action.RIGHT_CLICK_AIR) {
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
							gui.PunishMenu(player, clicked.getName());
							target.put(player.getUniqueId(), new Pair<UUID, String>(clicked.getUniqueId(), clicked.getName()));
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
				if(item != null && item.getType() != Material.AIR) {
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
		RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish Menu")) {
				if(item != null) {
					NBTItem i = new NBTItem(item);
					if(i.hasKey("Ban Option")) {
						if(target.containsKey(player.getUniqueId())) {
							if(rPlayer.getHighestRank().rank >= Rank.HELPER_PLUS.rank) {
								gui.BanChoice(player, target.get(player.getUniqueId()).getValue());
								player.closeInventory();
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to ban players!"));
							}
						}
					}
					else if(i.hasKey("Mute Option")) {
						if(target.containsKey(player.getUniqueId())) {
							if(rPlayer.getHighestRank().rank >= Rank.HELPER.rank) {
								gui.MuteChoice(player, target.get(player.getUniqueId()).getValue());
								player.closeInventory();
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to mute players!"));
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
		RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish, Ban Choice")) {
				if(item != null) {
					NBTItem i = new NBTItem(item);
					Punish pun = pManager.get(target.get(player.getUniqueId()).getKey());
					if(i.hasKey("Ban")) {
						if(target.containsKey(player.getUniqueId())) {
							if(rPlayer.getHighestRank().rank >= Rank.HELPER_PLUS.rank) {
								player.closeInventory();
								if(!pun.isBanned()) {
									gui.BanReason(player, target.get(player.getUniqueId()).getValue());
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is already banned!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to ban players!"));
							}
						}
					}
					else if(i.hasKey("Unban")) {
						if(target.containsKey(player.getUniqueId())) {
							if(rPlayer.getHighestRank().rank >= Rank.MOD.rank) {
								player.closeInventory();
								if(pun.isBanned()) {
									pun.setBanTime(0L);
									pun.setBanPerm(false);
									pun.removeBannedBy(pun.getBanOffense() - 1);
									pun.removeBanReason(pun.getBanOffense() - 1);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have unbanned &6" + target.get(player.getUniqueId()).getValue()));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not banned!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to unban players!"));
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
			if(ChatColor.stripColor(title).contains("Punish, Ban Reason")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(target.containsKey(player.getUniqueId())) {
						OfflinePlayer p = Bukkit.getOfflinePlayer(target.get(player.getUniqueId()).getKey());
						if(p.getName() != null) {
							Punish pun = pManager.get(p.getUniqueId());
							if(item.hasKey("Combat Related")) {
								pun.setBanPerm(true);
								pun.addBannedBy(player.getName());
								pun.addBanReason("Combat Related");
							}
							else if(item.hasKey("Auto Related")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Auto Related");
									pun.setBanTime(System.currentTimeMillis() + 5184000000L);
								}
								else if(pun.getBanOffense() >= 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Auto Related");
									pun.setBanPerm(true);
								}
							}
							else if(item.hasKey("Movement Related")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Movement Related");
									pun.setBanTime(System.currentTimeMillis() + 5184000000L);
								}
								else if(pun.getBanOffense() >= 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Movement Related");
									pun.setBanPerm(true);
								}
								player.closeInventory();
							}
							else if(item.hasKey("ESP Related")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("ESP Related");
									pun.setBanTime(System.currentTimeMillis() + 2592000000L);
								}
								else if(pun.getBanOffense() >= 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("ESP Related");
									pun.setBanPerm(true);
								}
							}
							else if(item.hasKey("Health Related")) {
								pun.setBanPerm(true);
								pun.addBannedBy(player.getName());
								pun.addBanReason("Health Related");
							}
							else if(item.hasKey("Building Related")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Building Related");
									pun.setBanTime(System.currentTimeMillis() + 2592000000L);
								}
								else if(pun.getBanOffense() >= 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Building Related");
									pun.setBanPerm(true);
								}
							}
							else if(item.hasKey("Wallhack Related")) {
								pun.setBanPerm(true);
								pun.addBannedBy(player.getName());
								pun.addBanReason("Wallhack Related");
							}
							else if(item.hasKey("Party Related")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Party Related");
									pun.setBanTime(System.currentTimeMillis() + 604800000L);
								}
								if(pun.getBanOffense() == 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Party Related");
									pun.setBanTime(System.currentTimeMillis() + 2592000000L);
								}
								else if(pun.getBanOffense() >= 2) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Party Related");
									pun.setBanPerm(true);
								}
							}
							int year = Calendar.getInstance().get(Calendar.YEAR);
							int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK_IN_MONTH);
							int month = Calendar.getInstance().get(Calendar.MONTH);
							int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
							int minute = Calendar.getInstance().get(Calendar.MINUTE);
							int second = Calendar.getInstance().get(Calendar.SECOND);
							String superTime = hour + ":" + minute + ":" + second + " " + day + "-" + month + "-" + year;
							pun.addBanDate(superTime);
							String time = "";
							if(!pun.getBanPerm()) {
								Long timeLeft = pun.getBanTime() - System.currentTimeMillis();
								long diffSeconds = timeLeft / 1000 % 60;
						        long diffMinutes = timeLeft / (60 * 1000) % 60;
						        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
						        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
							    time = diffDays + " " + diffHours + ":" + diffMinutes + ":" + diffSeconds;
							}
							else {
								time = "&c&lNever";
							}
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have banned &6" + p.getName() + " &cfor: &b" + time));
							player.closeInventory();
							if(Bukkit.getPlayer(target.get(player.getUniqueId()).getKey()) != null) {
								Bukkit.getPlayer(target.get(player.getUniqueId()).getKey()).kickPlayer(new CCT().colorize(
										"&cYou have been banned!"
										+ "\n&cYou can join back in: &b&l" + time
										+ "\n&cBan Reason: &c&l" + pun.getBanReason(pun.getBanReasonsList().size() - 1)		
										+ "\n&cBanned By: &6" + player.getName()
										+ "\n&cAppeal at: &6https://www.dungeonforge.eu/forums/ban-appeals.72/"
								));
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
		RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish, Mute Choice")) {
				if(item != null) {
					NBTItem i = new NBTItem(item);
					Punish pun = pManager.get(target.get(player.getUniqueId()).getKey());
					if(i.hasKey("Mute")) {
						if(target.containsKey(player.getUniqueId())) {
							if(rPlayer.getHighestRank().rank >= Rank.HELPER.rank) {
								player.closeInventory();
								if(!pun.isMuted()) {
									gui.MuteReason(player, target.get(player.getUniqueId()).getValue());
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is already banned!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to mute players!"));
							}
						}
					}
					else if(i.hasKey("Unmute")) {
						if(target.containsKey(player.getUniqueId())) {
							if(rPlayer.getHighestRank().rank >= Rank.HELPER_PLUS.rank) {
								player.closeInventory();
								if(pun.isMuted()) {
									pun.setMuteTime(0L);
									pun.setMutePerm(false);
									int offense = pun.getMuteOffense();
									pun.removeMutedBy(offense - 1);
									pun.removeMuteReason(offense - 1);
									pun.removeMuteDate(offense - 1);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have unmuted &6" + target.get(player.getUniqueId()).getValue()));
									Player offender = Bukkit.getPlayer(target.get(player.getUniqueId()).getKey());
									if(offender != null) {
										offender.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas unmuted you!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not muted!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to unmute players!"));
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
			if(ChatColor.stripColor(title).contains("Punish, Mute Reason")) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(target.containsKey(player.getUniqueId())) {
						OfflinePlayer p = Bukkit.getOfflinePlayer(target.get(player.getUniqueId()).getKey());
						if(p.getName() != null) {
							Punish pun = pManager.get(p.getUniqueId());
							String mMe = "";
							String mThem = "";
							if(item.hasKey("Spamming Related")) {
								if(pun.getMuteOffense() == 0) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Spamming/Bypassing the chat filter. (Warn)");
									mMe = "&2&l[DungeonForge]: &cYou have warned &6" + p.getName() + " &cfor spamming/bypassing the chat filter.";
									mThem = "&2&l[DungeonForge]: &cYou have been warned by: &6" + player.getName() + " &cfor spamming/bypassing the chat filter";
								}
								else if(pun.getMuteOffense() == 1) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Spamming/Bypassing the chat filter.");
									pun.setMuteTime(System.currentTimeMillis() + 86400000L);
									mMe = "&2&l[DungeonForge]: &cYou have muted &6" + p.getName() + " &cfor spamming/bypassing the chat filter for 24 hours.";
									mThem = "&2&l[DungeonForge]: &cYou have been muted by: &6" + player.getName() + " &cfor spamming/bypassing the chat filter for 24 hours.";
								}
								else if(pun.getMuteOffense() >= 2) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Spamming/Bypassing the chat filter.");
									pun.setMutePerm(true);
									mMe = "&2&l[DungeonForge]: &cYou have permanently muted &6" + p.getName() + " &cfor spamming/bypassing the chat filter.";
									mThem = "&2&l[DungeonForge]: &cYou have been permanently muted by: &6" + player.getName() + " &cfor spamming/bypassing the chat filter.";
								}
							}
							else if(item.hasKey("Insult Related")) {
								if(pun.getMuteOffense() == 0) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Insulting a player. (Warn)");
									mMe = "&2&l[DungeonForge]: &cYou have warned &6" + p.getName() + " &cfor insulting a player.";
									mThem = "&2&l[DungeonForge]: &cYou have been warned by: &6" + player.getName() + " &cfor insulting a player.";
								}
								else if(pun.getMuteOffense() == 1) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Insulting a player.");
									pun.setMuteTime(System.currentTimeMillis() + 604800000L);
									mMe = "&2&l[DungeonForge]: &cYou have muted &6" + p.getName() + " &cfor insulting a player for 7 days.";
									mThem = "&2&l[DungeonForge]: &cYou have been muted by: &6" + player.getName() + " &cfor insulting a player for 7 days.";
								}
								else if(pun.getMuteOffense() >= 2) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Insulting a player.");
									pun.setMutePerm(true);
									mMe = "&2&l[DungeonForge]: &cYou have permanently muted &6" + p.getName() + " &cfor insulting a player.";
									mThem = "&2&l[DungeonForge]: &cYou have been permanently muted by: &6" + player.getName() + " &cfor insulting a player.";
								}
							}
							else if(item.hasKey("Slur Related")) {
								if(pun.getMuteOffense() == 0) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("using Racist/Sexual/Pedopholic slurs.");
									pun.setMuteTime(System.currentTimeMillis() + 1209600000L);
									mMe = "&2&l[DungeonForge]: &cYou have muted &6" + p.getName() + " &cfor using racist/sexual/pedopholic slurs for 14 days.";
									mThem = "&2&l[DungeonForge]: &cYou have been muted by: &6" + player.getName() + " &cfor using racist/sexual/pedopholic slurs for 14 days.";
								}
								else if(pun.getMuteOffense() >= 1) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Using Racist/Sexual/Pedopholic slurs.");
									pun.setMutePerm(true);
									mMe = "&2&l[DungeonForge]: &cYou have permanently muted &6" + p.getName() + " &cfor using racist/sexual/pedopholic slurs.";
									mThem = "&2&l[DungeonForge]: &cYou have been permanently muted by: &6" + player.getName() + " &cfor using racist/sexual/pedopholic slurs.";
								}
							}
							else if(item.hasKey("Threat Related")) {
								pun.addMutedBy(player.getName());
								pun.addMuteReason("Threatening.");
								pun.setMutePerm(true);
								mMe = "&2&l[DungeonForge]: &cYou have permanently muted &6" + p.getName() + " &cfor threatening someone.";
								mThem = "&2&l[DungeonForge]: &cYou have been permanently muted by: &6" + player.getName() + " &cfor threatening someone.";
							}
							else if(item.hasKey("Chat Related")) {
								if(pun.getMuteOffense() == 0) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Using Chat Modifications. (Warn)");
									mMe = "&2&l[DungeonForge]: &cYou have warned &6" + p.getName() + " &cfor using Chat Modifications.";
									mThem = "&2&l[DungeonForge]: &cYou have been warned by: &6" + player.getName() + " &cfor using Chat Modifications.";
								}
								else if(pun.getMuteOffense() == 1) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Using Chat Modifications.");
									pun.setMuteTime(System.currentTimeMillis() + 86400000L);
									mMe = "&2&l[DungeonForge]: &cYou have muted &6" + p.getName() + " &cfor using Chat Modifications for 24 hours.";
									mThem = "&2&l[DungeonForge]: &cYou have been muted by: &6" + player.getName() + " &cfor using Chat Modifications for 24 hours.";
								}
								else if(pun.getMuteOffense() >= 2) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Using Chat Modifications.");
									mMe = "&2&l[DungeonForge]: &cYou have permanently muted &6" + p.getName() + " &cfor Chat Modifications.";
									mThem = "&2&l[DungeonForge]: &cYou have been permanently muted by: &6" + player.getName() + " &cfor Chat Modifications.";
								}
							}
							player.closeInventory();
							player.sendMessage(new CCT().colorize(mMe));
							if(Bukkit.getPlayer(target.get(player.getUniqueId()).getKey()) != null) {
								Player pp = Bukkit.getPlayer(target.get(player.getUniqueId()).getKey());
								pp.sendMessage(new CCT().colorize(mThem));
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
							DFSpawner dfSpawner = new DFSpawner(loc, type, tier);
							spManager.add(UUID.randomUUID(), dfSpawner);
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
						LootChest chest = new LootChest(loc, tier, radius);
						lcManager.add(chest.getUUID(), chest);
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
