package me.WiebeHero.Moderation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StringUtil;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.onarandombox.MultiverseCore.MultiverseCore;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtinjector.NBTInjector;
import javafx.util.Pair;
import me.WiebeHero.CapturePoints.CapturePoint;
import me.WiebeHero.CapturePoints.CapturePointManager;
import me.WiebeHero.Chat.MSG;
import me.WiebeHero.Chat.MSGManager;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.CustomMethods.MethodLuck;
import me.WiebeHero.CustomMethods.MethodMulti;
import me.WiebeHero.CustomMethods.Methods;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionManager;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;
import me.WiebeHero.LootChest.LootChest;
import me.WiebeHero.LootChest.LootChestManager;
import me.WiebeHero.Novis.NovisEnchantmentGetting;
import me.WiebeHero.Novis.NovisRewards;
import me.WiebeHero.Novis.RarityEnum;
import me.WiebeHero.Novis.RarityEnum.Rarity;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;
import me.WiebeHero.RankedPlayerPackage.RankedManager;
import me.WiebeHero.RankedPlayerPackage.RankedPlayer;
import me.WiebeHero.Scoreboard.DFScoreboard;
import me.WiebeHero.Skills.ClassMenu;
import me.WiebeHero.Spawners.DFSpawner;
import me.WiebeHero.Spawners.DFSpawnerManager;
import me.WiebeHero.Waterfall.PluginMessenger;
import net.luckperms.api.model.user.User;

public class ModerationEvents implements CommandExecutor,Listener,TabCompleter{
	public boolean joinCooldown = false;
	private RankedManager rManager;
	private DFFactionManager facManager;
	private DFFactionPlayerManager facPlayerManager;
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
	private MSGManager msgManager;
	private CapturePointManager cpManager;
	private NovisRewards rewards;
	private ItemStackBuilder builder;
	private NovisEnchantmentGetting enchant;
	private PluginMessenger pluginMessenger;
	private HashMap<UUID, Pair<UUID, String>> target = new HashMap<UUID, Pair<UUID, String>>();
	private HashMap<UUID, String> reason = new HashMap<UUID, String>();
	private HashMap<UUID, EntityType> spawnerType = new HashMap<UUID, EntityType>();
	private HashMap<UUID, Integer> spawnerTier = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> spawnerSlot = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> capturePointSlot = new HashMap<UUID, Integer>();
	private HashMap<UUID, Double> capturePointRadius = new HashMap<UUID, Double>();
	private HashMap<UUID, Double> capturePointMultiplier = new HashMap<UUID, Double>();
	private HashMap<UUID, Integer> lootTier = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> lootRadius = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> lootSlot = new HashMap<UUID, Integer>();
	private ArrayList<UUID> freezeList = new ArrayList<UUID>();
	public String protocol = "procedure";
	public String punish = "punish";
	public String history = "history";
	public String ban = "ban";
	public String unban = "unban";
	public String mute = "mute";
	public String unmute = "unmute";
	public String staffmode = "staffmode";
	public String rank = "rank";
	public String checkstaff = "checkstaff";
	public String checkban = "checkban";
	public String checkmute = "checkmute";
	public String clearchat = "clearchat";
	public String staffchat = "staffchat";
	public String stafflist = "stafflist";
	public String freeze = "freeze";
	public String unfreeze = "unfreeze";
	public String fly = "fly";
	public String feed = "feed";
	public String heal = "heal";
	public String time = "time";
	public String weather = "weather";
	public String checkmode = "checkmode";
	public String clearinventory = "clearinventory";
	public String tp = "tp";
	public String tptp = "tptp";
	public String tphere = "tphere";
	public String af = "af";
	public String a = "a";
	public String god = "god";
	public String gamemode = "gamemode";
	public String item = "item";
	
	public ModerationEvents(DFFactionManager facManager, DFPlayerManager dfManager, RankedManager rManager, PunishManager pManager, StaffManager sManager, DFSpawnerManager spManager, LootChestManager lcManager, ModerationGUI gui, MethodMulti multi, MethodLuck luck, Methods m, DFScoreboard board, ClassMenu menu, MSGManager msgManager, CapturePointManager cpManager, DFFactionPlayerManager facPlayerManager, NovisRewards rewards, NovisEnchantmentGetting enchant, ItemStackBuilder builder, PluginMessenger pluginMessenger) {
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
		this.msgManager = msgManager;
		this.cpManager = cpManager;
		this.facPlayerManager = facPlayerManager;
		this.rewards = rewards;
		this.enchant = enchant;
		this.builder = builder;
		this.pluginMessenger = pluginMessenger;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
			if(cmd.getName().equalsIgnoreCase(rank)) {
				if(args.length == 3) {
					if(args[0].equalsIgnoreCase("set")) {
						if(rPlayer.isStaff()) {
							if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
								OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
								if(p.getName() != null) {
									if(rManager.contains(p.getUniqueId())) {
										RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
										String r = args[2].toUpperCase();
										if(Rank.valueOf(r) != null) {
											Rank rank = Rank.valueOf(r);
											User promoted = luck.loadUser(p.getUniqueId());
											if(!player.getUniqueId().equals(p.getUniqueId())) {
												if(rPlayer.getHighestRank().getRank() > rank.getRank()) {
													if(rPlayer.getHighestRank().getRank() > oPlayer.getHighestRank().getRank()) {
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
			else if(cmd.getName().equalsIgnoreCase(clearchat)) {
				if(rPlayer.isStaff()) {
					if(args.length == 0) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HELPER_PLUS.getRank()) {
							Bukkit.broadcastMessage(StringUtils.repeat(" \n", 25));
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to clear chat!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /clearchat"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(fly)) {
				if(rPlayer.isStaff()) {
					if(args.length == 0) {
						if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
							if(player.getAllowFlight()) {
								player.setAllowFlight(false);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have disabled fly mode!"));
							}
							else {
								player.setAllowFlight(true);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have enabled fly mode!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to fly!"));
						}
					}
					else if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
							Player p = Bukkit.getPlayer(args[0]);
							if(p != null) {
								RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
								if(rPlayer.getHighestRank().getRank() > oPlayer.getHighestRank().getRank()) {
									if(p.getAllowFlight()) {
										p.setAllowFlight(false);
										p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas turned off fly mode!"));
									}
									else {
										p.setAllowFlight(true);
										p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas turned on fly mode!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYour rank must be higher then the players rank you want to fly!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to fly!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /fly (Optionally: Player Name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(feed)) {
				if(rPlayer.isStaff()) {
					if(args.length == 0) {
						if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
							player.setFoodLevel(20);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou fed yourself!"));
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to feed yourself!"));
						}
					}
					else if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
							Player p = Bukkit.getPlayer(args[0]);
							if(p != null) {
								RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
								if(rPlayer.getHighestRank().getRank() > oPlayer.getHighestRank().getRank()) {
									p.setFoodLevel(20);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou fed &6" + p.getName()));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYour rank must be higher then the players rank you want to feed!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to feed!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /feed (Optionally: Player Name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(heal)) {
				if(rPlayer.isStaff()) {
					if(args.length == 0) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_MOD.getRank()) {
							player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou healed yourself!"));
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to heal yourself!"));
						}
					}
					else if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_MOD.getRank()) {
							Player p = Bukkit.getPlayer(args[0]);
							if(p != null) {
								RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
								if(rPlayer.getHighestRank().getRank() > oPlayer.getHighestRank().getRank()) {
									player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou healed &6" + p.getName()));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYour rank must be higher then the players rank you want to heal!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to heal!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /heal (Optionally: Player Name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(clearinventory)) {
				if(rPlayer.isStaff()) {
					if(args.length == 0) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_MOD.getRank()) {
							player.getInventory().clear();
							player.getEquipment().clear();
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou cleared your inventory!"));
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to clear your own inventory!"));
						}
					}
					else if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_MOD.getRank()) {
							Player p = Bukkit.getPlayer(args[0]);
							if(p != null) {
								RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
								if(rPlayer.getHighestRank().getRank() > oPlayer.getHighestRank().getRank()) {
									player.getInventory().clear();
									player.getEquipment().clear();
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou cleared &6" + p.getName() + "'s &ainventory!"));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYour rank must be higher then the players you want to clear their inventory!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to heal!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /heal (Optionally: Player Name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(time)) {
				if(rPlayer.isStaff()) {
					if(args.length == 2) {
						if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
							if(args[0].equalsIgnoreCase("set")) {
								if(args[1].equalsIgnoreCase("day")) {
									player.getWorld().setTime(0L);
								}
								else if(args[1].equalsIgnoreCase("night")) {
									player.getWorld().setTime(13000L);
								}
								else {
									boolean verified = false;
									long time = 0;
									try {
										time = Long.parseLong(args[1]);
										verified = true;
									}
									catch(NumberFormatException ex) {
										ex.printStackTrace();
									}
									if(verified) {
										player.getWorld().setTime(time);
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe time value can only be set as day, night and as a number!"));
									}
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can only set the time!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to clear chat!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /time set (day/night/number)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(weather)) {
				if(rPlayer.isStaff()) {
					if(args.length == 2) {
						if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
							if(args[0].equalsIgnoreCase("set")) {
								if(args[1].equalsIgnoreCase("rain") || args[1].equalsIgnoreCase("snow")) {
									player.getWorld().setStorm(true);
								}
								else if(args[1].equalsIgnoreCase("thunder")) {
									player.getWorld().setThundering(true);
								}
								else if(args[1].equalsIgnoreCase("clear")) {
									player.getWorld().setThundering(true);
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can only set the weather to rain, snow or thunder!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can only set the time!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to clear chat!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /weather set (rain/snow/thunder/clear)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(tp)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_MOD.getRank()) {
							Player p = Bukkit.getPlayer(args[0]);
							if(p != null) {
								player.teleport(p);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou teleported to &6" + p.getName() + "!"));
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to tp to someone!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /tp (Player Name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(tphere)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_MOD.getRank()) {
							Player p = Bukkit.getPlayer(args[0]);
							if(p != null) {
								p.teleport(player);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou teleported to &6" + p.getName() + " &ato you!"));
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to tp the player here!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /tphere (Player Name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(tptp)) {
				if(rPlayer.isStaff()) {
					if(args.length == 2) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_MOD.getRank()) {
							Player pFrom = Bukkit.getPlayer(args[0]);
							Player pTo = Bukkit.getPlayer(args[1]);
							if(pFrom != null && pTo != null) {
								pFrom.teleport(pTo);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have teleported &6" + pFrom.getName() + " &ato &6" + pTo.getName() + "!"));
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to tp to someone!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /tptp (Player Name) (Player Name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(freeze)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
							Player p = Bukkit.getPlayer(args[0]);
							if(p != null) {
								RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
								if(rPlayer.getHighestRank().getRank() > oPlayer.getHighestRank().getRank()) {
									if(!freezeList.contains(p.getUniqueId())) {
										freezeList.add(p.getUniqueId());
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have frozen &6" + p.getName() + "!"));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &cis already frozen!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYour rank must be higher then the players rank you want to freeze!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to clear chat!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /clearchat"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(unfreeze)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
							Player p = Bukkit.getPlayer(args[0]);
							if(p != null) {
								RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
								if(rPlayer.getHighestRank().getRank() > oPlayer.getHighestRank().getRank()) {
									if(freezeList.contains(p.getUniqueId())) {
										freezeList.remove(p.getUniqueId());
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have unfrozen &6" + p.getName() + "!"));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &cis not frozen!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYour rank must be higher then the players rank you want to unfreeze!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to clear chat!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /clearchat"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(checkban)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HELPER.getRank()) {
							@SuppressWarnings("deprecation")
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
							if(p.getName() != null) {
								if(pManager.contains(p.getUniqueId())) {
									Punish pun = pManager.get(p.getUniqueId());
									if(pun.isBanned()) {
										Long timeLeft = pun.getMuteTime() - System.currentTimeMillis();
										long diffSeconds = timeLeft / 1000 % 60;
								        long diffMinutes = timeLeft / (60 * 1000) % 60;
								        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
								        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
										String time = diffDays + " Days " + diffHours + ":" + diffMinutes + ":" + diffSeconds;
										if(timeLeft <= 0) {
											time = "&c&lNever";
										}
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &cis currently banned! Banned by: &6" + pun.getBannedBy(pun.getBannedByList().size() - 1) + " &cBan Reason: &6" + pun.getBanReason(pun.getBanReasonsList().size() - 1) + " &cCan join again: &6" + time));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &cis not banned!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis " + p.getName() + " has never joined DungeonForge!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player does not exist!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to clear chat!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /checkban (Player Name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(checkmute)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HELPER.getRank()) {
							@SuppressWarnings("deprecation")
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
							if(p.getName() != null) {
								if(pManager.contains(p.getUniqueId())) {
									Punish pun = pManager.get(p.getUniqueId());
									if(pun.isMuted()) {
										Long timeLeft = pun.getMuteTime() - System.currentTimeMillis();
										long diffSeconds = timeLeft / 1000 % 60;
								        long diffMinutes = timeLeft / (60 * 1000) % 60;
								        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
								        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
										String time = diffDays + " Days " + diffHours + ":" + diffMinutes + ":" + diffSeconds;
										if(timeLeft <= 0) {
											time = "&c&lNever";
										}
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &cis currently muted! Muted by: &6" + pun.getMutedBy(pun.getMutedByList().size() - 1) + " &cMute Reason: &6" + pun.getMuteReason(pun.getMuteReasonsList().size() - 1) + " &cCan chat again: &6" + time));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &cis not muted!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis " + p.getName() + " has never joined DungeonForge!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player does not exist!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to clear chat!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /checkmute (Player Name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(checkstaff)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HELPER.getRank()) {
							@SuppressWarnings("deprecation")
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
							if(p.getName() != null) {
								if(rManager.contains(p.getUniqueId())) {
									RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
									if(oPlayer.isStaff()) {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aThis player is staff. Rank: " + oPlayer.getHighestRank().getDisplay()));
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
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to clear chat!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /checkstaff (Player Name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(staffchat)) {
				if(rPlayer.isStaff()) {
					if(args.length == 0) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HELPER.getRank()) {
							if(msgManager.contains(player.getUniqueId())) {
								MSG msg = msgManager.get(player.getUniqueId());
								if(msg.getStaffChat()) {
									msg.setStaffChat(false);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have disabled staff chat!"));
								}
								else if(!msg.getStaffChat()) {
									msg.setStaffChat(true);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have enabled staff chat!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player does not exist!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to enter staff chat!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /staffchat"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(stafflist)) {
				if(rPlayer.isStaff()) {
					if(args.length == 0) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HELPER.getRank()) {
							for(Entry<UUID, RankedPlayer> entry : rManager.getRankedPlayerList().entrySet()) {
								UUID uuid = entry.getKey();
								RankedPlayer oPlayer = entry.getValue();
								ArrayList<String> messages = new ArrayList<String>();
								if(oPlayer.getHighestRank().getRank() >= Rank.HELPER.getRank()) {
									if(Bukkit.getPlayer(uuid) != null) {
										String name = Bukkit.getPlayer(uuid).getName();
										messages.add(new CCT().colorize(oPlayer.getHighestRank().getDisplay() + " " + name + " &7(OFFLINE)"));
									}
									else {
										String name = Bukkit.getOfflinePlayer(uuid).getName();
										messages.add(new CCT().colorize(oPlayer.getHighestRank().getDisplay() + " " + name + " &a(ONLINE)"));
									}
								}
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: " + messages.toString()));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to enter staff chat!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /stafflist"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(staffmode)) {
				if(sManager.contains(player.getUniqueId()) && rPlayer.getHighestRank().getRank() >= Rank.HELPER.getRank()) {
					Staff staff = sManager.get(player.getUniqueId());
					if(staff.getStaffMode() == true) {
						player.getInventory().setContents(staff.getInv());
						staff.switchStaffMode(false);
						staff.switchLootMode(false);
						staff.switchCapturePointMode(false);
						if(staff.getVanishMode() == true) {
							staff.switchVanishMode(false);
							for(Player p : Bukkit.getOnlinePlayers()) {
								p.showPlayer(CustomEnchantments.getInstance(), player);
							}
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &c&lVanish Disabled."));
						}
						staff.switchSpawnerMode(false);
						player.setAllowFlight(false);
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
						if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
							player.getInventory().setItem(6, capture());
							player.getInventory().setItem(7, spawner());
							player.getInventory().setItem(8, loot());
						}
						player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
						player.setFoodLevel(20);
						player.setAllowFlight(true);
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
			else if(cmd.getName().equalsIgnoreCase(god)) {
				if(rPlayer.isStaff()) {
					if(args.length == 0) {
						if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
							Staff staff = sManager.get(player.getUniqueId());
							if(staff.getGodMode()) {
								staff.switchGodModeMode(false);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aGod mode enabled!"));
							}
							else if(!staff.getGodMode()) {
								staff.switchGodModeMode(true);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cGod mode disabled!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to clear chat!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /god"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(gamemode)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
							int mode = -1;
							try {
								mode = Integer.parseInt(args[0]);
							}
							catch(NumberFormatException ex) {
								ex.printStackTrace();
							}
							if(mode > -1 && mode < 4) {
								if(mode != player.getGameMode().getValue()) {
									player.setGameMode(GameMode.getByValue(mode));
									String origin = player.getGameMode().toString().toLowerCase();
									String newMode = origin.substring(0, 1).toUpperCase() + origin.substring(1);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have entered the gamemode: &6" + newMode));
								}
								else {
									String origin = GameMode.getByValue(mode).toString().toLowerCase();
									String newMode = origin.substring(0, 1).toUpperCase() + origin.substring(1);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are already in the gamemode: &6" + newMode));
								}
							}
							else {
								if(GameMode.valueOf(args[0].toUpperCase()) != null) {
									player.setGameMode(GameMode.valueOf(args[0].toUpperCase()));
									String origin = player.getGameMode().toString().toLowerCase();
									String newMode = origin.substring(0, 1).toUpperCase() + origin.substring(1);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have entered the gamemode: &6" + newMode));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cGamemode is invalid! Valid gamemodes: 0/1/2/3 survival/creative/spectator/adventure"));
								}
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to set your own gamemode!"));
						}
					}
					else if(args.length == 2) {
						if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
							Player p = Bukkit.getPlayer(args[0]);
							if(p != null) {
								int mode = -1;
								try {
									mode = Integer.parseInt(args[1]);
								}
								catch(NumberFormatException ex) {
									ex.printStackTrace();
								}
								if(mode > -1 && mode < 4) {
									if(mode != player.getGameMode().getValue()) {
										p.setGameMode(GameMode.getByValue(mode));
										String origin = player.getGameMode().toString().toLowerCase();
										String newMode = origin.substring(0, 1).toUpperCase() + origin.substring(1);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have entered the gamemode: &6" + newMode));
									}
									else {
										String origin = GameMode.getByValue(mode).toString().toLowerCase();
										String newMode = origin.substring(0, 1).toUpperCase() + origin.substring(1);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are already in the gamemode: &6" + newMode));
									}
								}
								else {
									if(GameMode.valueOf(args[1].toUpperCase()) != null) {
										p.setGameMode(GameMode.valueOf(args[1].toUpperCase()));
										String origin = player.getGameMode().toString().toLowerCase();
										String newMode = origin.substring(0, 1).toUpperCase() + origin.substring(1);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have entered the gamemode: &6" + newMode));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cGamemode is invalid! Valid gamemodes: 0/1/2/3 survival/creative/spectator/adventure"));
									}
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to set your someones gamemode!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /gamemode (Optional)(Player Name) (0/1/2/3) or (survival/creative/spectator/adventure)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(checkmode)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
							Player p = Bukkit.getPlayer(args[0]);
							if(p != null) {
								Staff staff = sManager.get(p.getUniqueId());
								if(staff.getStaffMode()) {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &ais currently in staffmode!"));
								}
								else if(!staff.getStaffMode()) {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &ais not in staffmode!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not online!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to clear chat!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /checkmode (Player Name)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(punish)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
							if(p.getName() != null) {
								if(rManager.contains(p.getUniqueId()) && pManager.contains(p.getUniqueId())) {
									if(!p.getUniqueId().equals(player.getUniqueId())) {
										RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
										if(rPlayer.getHighestRank().getRank() >= oPlayer.getHighestRank().getRank()) {
											target.put(player.getUniqueId(), new Pair<UUID, String>(p.getUniqueId(), p.getName()));
											gui.PunishMenu(player, p.getName());
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank needs to be higher then the player you're banning!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't punish yourself!"));
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
			else if(cmd.getName().equalsIgnoreCase(history)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
							if(p.getName() != null) {
								if(rManager.contains(p.getUniqueId()) && pManager.contains(p.getUniqueId())) {
									if(!p.getUniqueId().equals(player.getUniqueId())) {
										RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
										if(rPlayer.getHighestRank().getRank() >= oPlayer.getHighestRank().getRank()) {
											target.put(player.getUniqueId(), new Pair<UUID, String>(p.getUniqueId(), p.getName()));
											gui.PunishHistory(player, p.getName());
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank needs to be higher then the player you're banning!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't open your own history!"));
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
			else if(cmd.getName().equalsIgnoreCase(ban)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HELPER_PLUS.getRank()) {
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
							if(p.getName() != null) {
								if(rManager.contains(p.getUniqueId()) && pManager.contains(p.getUniqueId())) {
									if(!p.getUniqueId().equals(player.getUniqueId())) {
										RankedPlayer oPlayer = rManager.getRankedPlayer(p.getUniqueId());
										if(rPlayer.getHighestRank().getRank() > oPlayer.getHighestRank().getRank()) {
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
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't ban yourself!"));
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
						if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
							if(p.getName() != null) {
								if(rManager.contains(p.getUniqueId()) && pManager.contains(p.getUniqueId())) {
									if(!p.getUniqueId().equals(player.getUniqueId())) {
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
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't unban yourself!"));
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
						if(rPlayer.getHighestRank().getRank() >= Rank.HELPER.getRank()) {
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
							if(p.getName() != null) {
								if(rManager.contains(p.getUniqueId()) && pManager.contains(p.getUniqueId())) {
									if(!p.getUniqueId().equals(player.getUniqueId())) {
										RankedPlayer rp = rManager.getRankedPlayer(p.getUniqueId());
										if(rPlayer.getHighestRank().getRank() > rp.getHighestRank().getRank()) {
											target.put(player.getUniqueId(), new Pair<UUID, String>(p.getUniqueId(), p.getName()));
											gui.MuteReason(player, p.getName());
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou're rank needs to be higher then the player you're muting!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't mute yourself!"));
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
						if(rPlayer.getHighestRank().getRank() >= Rank.HELPER_PLUS.getRank()) {
							OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
							if(p.getName() != null) {
								if(rManager.contains(p.getUniqueId()) && pManager.contains(p.getUniqueId())) {
									if(!p.getUniqueId().equals(player.getUniqueId())) {
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
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't unmute yourself!"));
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
			else if(cmd.getName().equalsIgnoreCase(af)) {
				if(rPlayer.isStaff()) {
					if(args.length == 2) {
						if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
							if(args[0].equalsIgnoreCase("leader")) {
								if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
									OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
									if(p.getName() != null) {
										if(facPlayerManager.contains(p.getUniqueId())) {
											DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(p.getUniqueId());
											if(facPlayer.getFactionId() != null) {
												DFFaction faction = facManager.getFaction(facPlayer.getFactionId());
												if(facPlayer.getRank() - 1 >= 1) {
													for(Entry<UUID, DFFactionPlayer> entry : facPlayerManager.getFactionPlayerMap().entrySet()) {
														DFFactionPlayer facP = entry.getValue();
														if(facP.getFactionId().equals(faction.getFactionId())) {
															if(facP.getRank() == 4) {
																faction.demoteMember(entry.getKey());
															}
														}
													}
													facPlayer.setRank(4);
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have given &6" + p.getName() + " &aleadership of the faction &6" + faction.getName()));
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &ccannot be demoted any more!"));
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not part of a faction!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not part of a faction!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player does not exist!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use this command!"));
								}
							}
							else if(args[0].equalsIgnoreCase("abandon") || args[0].equalsIgnoreCase("disband")) {
								if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
									String facName = args[1];
									if(facManager.getFaction(facName) != null) {
										DFFaction faction = facManager.getFaction(facName);
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
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis faction does not exist!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use this command!"));
								}
							}
							else if(args[0].equalsIgnoreCase("kick")) {
								OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
								if(p.getName() != null) {
									if(facPlayerManager.contains(p.getUniqueId())) {
										DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(p.getUniqueId());
										if(facPlayer.getFactionId() != null) {
											if(facPlayer.getRank() != 4) {
												DFFaction faction = facManager.getFaction(facPlayer.getFactionId());
												faction.removeMember(p.getUniqueId());
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have kicked &6" + p.getName() + " &afrom the faction &6" + faction.getName()));
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't kick a leader from his faction! Use /af abandon (Faction Name)"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not part of a faction!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not part of a faction!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player does not exist!"));
								}
							}
							else if(args[0].equalsIgnoreCase("promote")) {
								OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
								if(p.getName() != null) {
									if(facPlayerManager.contains(p.getUniqueId())) {
										DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(p.getUniqueId());
										if(facPlayer.getFactionId() != null) {
											DFFaction faction = facManager.getFaction(facPlayer.getFactionId());
											if(facPlayer.getRank() + 1 <= 3) {
												faction.promoteMember(p.getUniqueId());
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have promoted &6" + p.getName()));
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &ccannot be promoted any more!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not part of a faction!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not part of a faction!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player does not exist!"));
								}
							}
							else if(args[0].equalsIgnoreCase("demote")) {
								OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
								if(p.getName() != null) {
									if(facPlayerManager.contains(p.getUniqueId())) {
										DFFactionPlayer facPlayer = facPlayerManager.getFactionPlayer(p.getUniqueId());
										if(facPlayer.getFactionId() != null) {
											DFFaction faction = facManager.getFaction(facPlayer.getFactionId());
											if(facPlayer.getRank() - 1 >= 1 && facPlayer.getRank() != 4) {
												faction.demoteMember(p.getUniqueId());
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have demoted &6" + p.getName()));
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &ccannot be demoted any more!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not part of a faction!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is not part of a faction!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player does not exist!"));
								}
							}
							else if(args[0].equalsIgnoreCase("unclaim")) {
								String facName = args[1];
								if(facManager.getFaction(facName) != null) {
									DFFaction faction = facManager.getFaction(facName);
									if(faction.getChunkList().contains(player.getChunk().getChunkKey())) {
										faction.removeChunk(player.getChunk().getChunkKey());
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have unclaimed this chunk!"));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis chunk is not claimed by this claimed!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis faction does not exist!"));
								}
							}
						}
					}
					else if(args.length == 3) {
						if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
							if(args[0].equalsIgnoreCase("unclaim")) {
								if(args[1].equalsIgnoreCase("all")) {
									String facName = args[2];
									if(facManager.getFaction(facName) != null) {
										DFFaction faction = facManager.getFaction(facName);
										faction.clearChunks();
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have unclaimed all &6" + faction.getName() + "'s &achunks!"));
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis faction does not exist!"));
									}
								}
							}
							else if(args[0].equalsIgnoreCase("rename")) {
								String facName = args[1];
								if(facManager.getFaction(facName) != null) {
									DFFaction faction = facManager.getFaction(facName);
									Pattern p = Pattern.compile( "[0-9]" );
								    Matcher m = p.matcher(facName);
								    if(m.find() == false && facName.indexOf("_-=+[]{}:;''<>/?!@#$%^&*()") == -1) {
								    	if(facName.length() >= 4 || facName.length() <= 20) {
							    			if(facManager.isNameAvailable(facName)) {
							    				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have renamed the faction &6" + faction.getName() + " &ato &6" + facName));
							    				faction.setName(facName);
							    			}
						    				else {
								    			player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis faction name is already taken!"));
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
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis faction does not exist!"));
								}
							}
						}
					}
					else if(args.length == 5) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
							if(args[0].equalsIgnoreCase("bank")) {
								if(args[1].equalsIgnoreCase("set")) {
									String facName = args[2];
									String a = args[3];
									if(facManager.getFaction(facName) != null) {
										double amount = 0.00;
										try {
											amount = Double.parseDouble(a);
										}
										catch(NumberFormatException e) {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aThis money value is invalid!"));
											e.printStackTrace();
										}
										if(amount > 0.00) {
											DFFaction faction = facManager.getFaction(facName);
											faction.setBank(amount);
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou set &6" + faction.getName() + "'s &abank balance to &6" + faction.getBank() + "$!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis faction does not exist!"));
									}
								}
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use this command!"));
						}
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(a)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(args[0].equalsIgnoreCase("kitkey")) {
							if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
								ItemStack note = this.builder.constructItem(
										Material.TRIPWIRE_HOOK,
										1,
										"&4&lRandom Kit Key",
										new String[] {
											"&7When you right click, you unlock",
											"&7a random kit. (Non donator kits)"
										},
										new Pair<String, String>("KitKey", "")
								);
								player.getInventory().addItem(note);
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use this command!"));
							}
						}
					}
					if(args.length == 2) {
						if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
							if(args[0].equalsIgnoreCase("item")) {
								if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
									HashMap<String, ItemStack> stacks = this.rewards.getItemList();
									if(stacks.containsKey(args[1])) {
										ItemStack i = stacks.get(args[1]).clone();
										player.getInventory().addItem(i);
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis item doesn't exist! Try to type the specific item in full lowercase and replace all spaces with _"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use this command!"));
								}
							}
							else if(args[0].equalsIgnoreCase("spawner")) {
								if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
									String type = args[1].toUpperCase();
									EntityType newType = null;
									try {
										newType = EntityType.valueOf(type);
									}
									catch(IllegalArgumentException ex) {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis spawner type doesn't exist!"));
										ex.printStackTrace();
									}
									if(newType != null) {
										String name = args[1].substring(0, 1).toUpperCase() + args[1].substring(1);
										String finalName = new CCT().colorize("&a&l" + name + " Spawner");
										ItemStack spawner = this.builder.constructItem(
												Material.SPAWNER,
												1,
												finalName
										);
										player.getInventory().addItem(spawner);
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use this command!"));
								}
							}
							else if(args[0].equalsIgnoreCase("moneynote")) {
								if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
									double amount = 0.00;
									try {
										amount = Double.parseDouble(args[1]);
									}
									catch(NumberFormatException ex) {
										ex.printStackTrace();
									}
									if(amount > 0.00) {
										ItemStack note = this.builder.constructItem(
												Material.PAPER,
												1,
												"&aMoney Note: $" + amount,
												new String[] {},
												new Pair<String, Double>("Money", amount)
										);
										player.getInventory().addItem(note);
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid amount of money!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use this command!"));
								}
							}
							else if(args[0].equalsIgnoreCase("xpbottle")) {
								if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
									int amount = 0;
									try {
										amount = Integer.parseInt(args[1]);
									}
									catch(NumberFormatException ex) {
										ex.printStackTrace();
									}
									if(amount > 0) {
										ItemStack note = this.builder.constructItem(
												Material.EXPERIENCE_BOTTLE,
												1,
												"&a&lXP Bottle (Player)",
												new String[] {
														"&7When you throw this bottle, you will",
														"&7gain player xp appropiate to it's amount",
														"&7XP Amount: &6" + amount
												},
												new Pair<String, Integer>("XPBottle", amount)
										);
										player.getInventory().addItem(note);
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid amount of xp!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use this command!"));
								}
							}
							else if(args[0].equalsIgnoreCase("fxpbottle")) {
								if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
									int amount = 0;
									try {
										amount = Integer.parseInt(args[1]);
									}
									catch(NumberFormatException ex) {
										ex.printStackTrace();
									}
									if(amount > 0) {
										ItemStack note = this.builder.constructItem(
												Material.EXPERIENCE_BOTTLE,
												1,
												"&a&lXP Bottle (Faction)",
												new String[] {
														"&7When you throw this bottle, you will",
														"&7gain faction xp appropiate to it's amount",
														"&7XP Amount: &6" + amount
												},
												new Pair<String, Integer>("FXPBottle", amount)
										);
										player.getInventory().addItem(note);
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid amount of xp!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use this command!"));
								}
							}
							else if(args[0].equalsIgnoreCase("crystal")) {
								if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
									String rarity = args[1].toUpperCase();
									if(RarityEnum.getIfPresent(rarity) != null) {
										Rarity rar = RarityEnum.getIfPresent(rarity);
										ItemStack item = builder.constructItem(
											Material.NETHER_STAR,
											1,
											rar.getColorCode() + rar.getDisplay() + " Crystal",
											new String[] {
												"&7Bring me to &6&lNOVIS &7to get some",
												"&7really nice rewards!",
												"&7Rarity: " + rar.getColorCode() + rar.getDisplay()
											},
											new Pair<String, String>("CrystalObject", ""),
											new Pair<String, String>("Rarity", rar.getDisplay())
										);
										player.getInventory().addItem(item);
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis rarity doesn't exist!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use this command!"));
								}
							}
						}
					}
					if(args.length == 3) {
						if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
							if(args[0].equalsIgnoreCase("item")) {
								HashMap<String, ItemStack> stacks = this.rewards.getItemList();
								if(stacks.containsKey(args[1])) {
									ItemStack i = stacks.get(args[1]).clone();
									NBTItem item = new NBTItem(i);
									boolean valid = true;
									int level = 0;
									try {
										level = Integer.parseInt(args[2]);
									}
									catch(NumberFormatException ex) {
										valid = false;
										ex.printStackTrace();
									}
									if(valid) {
										if(level > 0 && level <= 15) {
											if(item.hasKey("WeaponKey") || item.hasKey("BowKey")) {
												int itemLevel = item.getInteger("Level");
												int maxxp = item.getInteger("MAXXP");
												int total = item.getInteger("TotalXP");	
												String name = item.getString("ItemName");
												String rarity = item.getString("Rarity");
												String enchantmentString = item.getString("EnchantmentString");
												double baseDamage = item.getDouble("Base Attack Damage");
												double baseSpeed = item.getDouble("Base Attack Speed");
												double incDamage = item.getDouble("Inc Attack Damage");
												double incSpeed = item.getDouble("Inc Attack Speed");
												for(int x = itemLevel + 1; x <= level; x++) {
													itemLevel = x;
													total = total + maxxp;
													maxxp = maxxp / 100 * 135;
												}
												double value1 = baseDamage + incDamage * (itemLevel - 1);
								            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
												//Config Data
												//Weapon Data
								            	item.setInteger("Level", itemLevel);
								            	item.setInteger("XP", 0);
								            	item.setInteger("MAXXP", maxxp);
								            	item.setInteger("TotalXP", total);
								            	item.setDouble("Attack Damage", value1);
								            	item.setDouble("Attack Speed", value2);
								            	i = item.getItem();
												ItemMeta meta = i.getItemMeta();
												meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
												ArrayList<String> newLore = new ArrayList<String>();
												newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
												double roundOff1 = (double) Math.round(value1 * 100) / 100;
												double roundOff2 = (double) Math.round(value2 * 100) / 100;
												newLore.add(new CCT().colorize("&7-----------------------"));
												newLore.add(new CCT().colorize("&7Attack Damage: &6" + roundOff1));
												newLore.add(new CCT().colorize("&7Attack Speed: &6" + roundOff2));
												newLore.add(new CCT().colorize("&7-----------------------"));
												if(itemLevel < 15) {
													newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + 0 + " &6/ &b&l" + maxxp + "&a]"));
													newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
												}
												else if(itemLevel == 15) {
													newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
												}
												newLore.add(new CCT().colorize("&7-----------------------"));
												if(itemLevel >= 6) {
													int loreRequired = itemLevel - 4;
													int levelRequired = loreRequired * 5;
													newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
												}
												newLore.add(new CCT().colorize("&7Rarity: " + rarity));
												meta.setLore(newLore);
												i.setItemMeta(meta);
											}
											else if(item.hasKey("ArmorKey")) {
												if(i.getType().toString().contains("HELMET")) {
													int itemLevel = item.getInteger("Level");
													int maxxp = item.getInteger("MAXXP");
													int total = item.getInteger("TotalXP");	
													String name = item.getString("ItemName");
													String rarity = item.getString("Rarity");
													String enchantmentString = item.getString("EnchantmentString");
													double baseDamage = item.getDouble("Base Armor Defense");
													double baseSpeed = item.getDouble("Base Armor Toughness");
													double incDamage = item.getDouble("Inc Armor Defense");
													double incSpeed = item.getDouble("Inc Armor Toughness");
													for(int x = itemLevel + 1; x <= level; x++) {
														itemLevel = x;
														total = total + maxxp;
														maxxp = maxxp / 100 * 135;
													}
													double value1 = baseDamage + incDamage * (itemLevel - 1);
									            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
													//Config Data
													//Weapon Data
									            	item.setInteger("Level", itemLevel);
									            	item.setInteger("XP", 0);
									            	item.setInteger("MAXXP", maxxp);
									            	item.setInteger("TotalXP", total);
									            	item.setDouble("Armor Defense", value1);
									            	item.setDouble("Armor Toughness", value2);
									            	i = item.getItem();
													ItemMeta meta = i.getItemMeta();
													meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
													ArrayList<String> newLore = new ArrayList<String>();
													newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
													double roundOff1 = (double) Math.round(value1 * 100) / 100;
													double roundOff2 = (double) Math.round(value2 * 100) / 100;
													newLore.add(new CCT().colorize("&7-----------------------"));
													newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
													newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel < 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + 0 + " &6/ &b&l" + maxxp + "&a]"));
														newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
													}
													else if(itemLevel == 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
													}
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel >= 6) {
														int loreRequired = itemLevel - 4;
														int levelRequired = loreRequired * 5;
														newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
													}
													newLore.add(new CCT().colorize("&7Rarity: " + rarity));
													meta.setLore(newLore);
													i.setItemMeta(meta);
												}
												else if(i.getType().toString().contains("CHESTPLATE")) {
													int itemLevel = item.getInteger("Level");
													int maxxp = item.getInteger("MAXXP");
													int total = item.getInteger("TotalXP");	
													String name = item.getString("ItemName");
													String rarity = item.getString("Rarity");
													String enchantmentString = item.getString("EnchantmentString");
													double baseDamage = item.getDouble("Base Armor Defense");
													double baseSpeed = item.getDouble("Base Armor Toughness");
													double incDamage = item.getDouble("Inc Armor Defense");
													double incSpeed = item.getDouble("Inc Armor Toughness");
													for(int x = itemLevel + 1; x <= level; x++) {
														itemLevel = x;
														total = total + maxxp;
														maxxp = maxxp / 100 * 135;
													}
													double value1 = baseDamage + incDamage * (itemLevel - 1);
									            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
													//Config Data
													//Weapon Data
									            	item.setInteger("Level", itemLevel);
									            	item.setInteger("XP", 0);
									            	item.setInteger("MAXXP", maxxp);
									            	item.setInteger("TotalXP", total);
									            	item.setDouble("Armor Defense", value1);
									            	item.setDouble("Armor Toughness", value2);
									            	i = item.getItem();
													ItemMeta meta = i.getItemMeta();
													meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
													ArrayList<String> newLore = new ArrayList<String>();
													newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
													double roundOff1 = (double) Math.round(value1 * 100) / 100;
													double roundOff2 = (double) Math.round(value2 * 100) / 100;
													newLore.add(new CCT().colorize("&7-----------------------"));
													newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
													newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel < 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + 0 + " &6/ &b&l" + maxxp + "&a]"));
														newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
													}
													else if(itemLevel == 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
													}
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel >= 6) {
														int loreRequired = itemLevel - 4;
														int levelRequired = loreRequired * 5;
														newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
													}
													newLore.add(new CCT().colorize("&7Rarity: " + rarity));
													meta.setLore(newLore);
													i.setItemMeta(meta);
												}
												else if(i.getType().toString().contains("LEGGINGS")) {
													int itemLevel = item.getInteger("Level");
													int maxxp = item.getInteger("MAXXP");
													int total = item.getInteger("TotalXP");	
													String name = item.getString("ItemName");
													String rarity = item.getString("Rarity");
													String enchantmentString = item.getString("EnchantmentString");
													double baseDamage = item.getDouble("Base Armor Defense");
													double baseSpeed = item.getDouble("Base Armor Toughness");
													double incDamage = item.getDouble("Inc Armor Defense");
													double incSpeed = item.getDouble("Inc Armor Toughness");
													for(int x = itemLevel + 1; x <= level; x++) {
														itemLevel = x;
														total = total + maxxp;
														maxxp = maxxp / 100 * 135;
													}
													double value1 = baseDamage + incDamage * (itemLevel - 1);
									            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
													//Config Data
													//Weapon Data
									            	item.setInteger("Level", itemLevel);
									            	item.setInteger("XP", 0);
									            	item.setInteger("MAXXP", maxxp);
									            	item.setInteger("TotalXP", total);
									            	item.setDouble("Armor Defense", value1);
									            	item.setDouble("Armor Toughness", value2);
									            	i = item.getItem();
													ItemMeta meta = i.getItemMeta();
													meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
													ArrayList<String> newLore = new ArrayList<String>();
													newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
													double roundOff1 = (double) Math.round(value1 * 100) / 100;
													double roundOff2 = (double) Math.round(value2 * 100) / 100;
													newLore.add(new CCT().colorize("&7-----------------------"));
													newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
													newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel < 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + 0 + " &6/ &b&l" + maxxp + "&a]"));
														newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
													}
													else if(itemLevel == 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
													}
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel >= 6) {
														int loreRequired = itemLevel - 4;
														int levelRequired = loreRequired * 5;
														newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
													}
													newLore.add(new CCT().colorize("&7Rarity: " + rarity));
													meta.setLore(newLore);
													i.setItemMeta(meta);
												}
												else if(i.getType().toString().contains("BOOTS")) {
													int itemLevel = item.getInteger("Level");
													int maxxp = item.getInteger("MAXXP");
													int total = item.getInteger("TotalXP");	
													String name = item.getString("ItemName");
													String rarity = item.getString("Rarity");
													String enchantmentString = item.getString("EnchantmentString");
													double baseDamage = item.getDouble("Base Armor Defense");
													double baseSpeed = item.getDouble("Base Armor Toughness");
													double incDamage = item.getDouble("Inc Armor Defense");
													double incSpeed = item.getDouble("Inc Armor Toughness");
													for(int x = itemLevel + 1; x <= level; x++) {
														itemLevel = x;
														total = total + maxxp;
														maxxp = maxxp / 100 * 135;
													}
													double value1 = baseDamage + incDamage * (itemLevel - 1);
									            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
													//Config Data
													//Weapon Data
									            	item.setInteger("Level", itemLevel);
									            	item.setInteger("XP", 0);
									            	item.setInteger("MAXXP", maxxp);
									            	item.setInteger("TotalXP", total);
									            	item.setDouble("Armor Defense", value1);
									            	item.setDouble("Armor Toughness", value2);
									            	i = item.getItem();
													ItemMeta meta = i.getItemMeta();
													meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
													ArrayList<String> newLore = new ArrayList<String>();
													newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
													double roundOff1 = (double) Math.round(value1 * 100) / 100;
													double roundOff2 = (double) Math.round(value2 * 100) / 100;
													newLore.add(new CCT().colorize("&7-----------------------"));
													newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
													newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel < 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + 0 + " &6/ &b&l" + maxxp + "&a]"));
														newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
													}
													else if(itemLevel == 15) {
														newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
													}
													newLore.add(new CCT().colorize("&7-----------------------"));
													if(itemLevel >= 6) {
														int loreRequired = itemLevel - 4;
														int levelRequired = loreRequired * 5;
														newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
													}
													newLore.add(new CCT().colorize("&7Rarity: " + rarity));
													meta.setLore(newLore);
													i.setItemMeta(meta);
												}
											}
											else if(item.hasKey("ShieldKey")) {
												int itemLevel = item.getInteger("Level");
												int maxxp = item.getInteger("MAXXP");
												int total = item.getInteger("TotalXP");	
												String name = item.getString("ItemName");
												String rarity = item.getString("Rarity");
												String enchantmentString = item.getString("EnchantmentString");
												double baseDamage = item.getDouble("Base Armor Toughness");
												double baseSpeed = item.getDouble("Base Cooldown");
												double incDamage = item.getDouble("Inc Armor Toughness");
												double incSpeed = item.getDouble("Inc Cooldown");
												for(int x = itemLevel + 1; x <= level; x++) {
													itemLevel = x;
													total = total + maxxp;
													maxxp = maxxp / 100 * 135;
												}
												double value1 = baseDamage + incDamage * (itemLevel - 1);
								            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
												//Config Data
												//Weapon Data
								            	item.setInteger("Level", itemLevel);
								            	item.setInteger("XP", 0);
								            	item.setInteger("MAXXP", maxxp);
								            	item.setInteger("TotalXP", total);
								            	item.setDouble("Armor Toughness", value1);
								            	item.setDouble("Cooldown", value2);
								            	i = item.getItem();
												ItemMeta meta = i.getItemMeta();
												meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
												ArrayList<String> newLore = new ArrayList<String>();
												newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
												double roundOff1 = (double) Math.round(value1 * 100) / 100;
												double roundOff2 = (double) Math.round(value2 * 100) / 100;
												newLore.add(new CCT().colorize("&7-----------------------"));
												newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff1));
												newLore.add(new CCT().colorize("&7Cooldown: &b" + roundOff2 + " Seconds"));
												newLore.add(new CCT().colorize("&7-----------------------"));
												if(itemLevel < 15) {
													newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + 0 + " &6/ &b&l" + maxxp + "&a]"));
													newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
												}
												else if(itemLevel == 15) {
													newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
												}
												newLore.add(new CCT().colorize("&7-----------------------"));
												if(itemLevel >= 6) {
													int loreRequired = itemLevel - 4;
													int levelRequired = loreRequired * 5;
													newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
												}
												newLore.add(new CCT().colorize("&7Rarity: " + rarity));
												meta.setLore(newLore);
												i.setItemMeta(meta);
											}
											player.getInventory().addItem(i);
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe level must be in between 1 and 15!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis input as level is not valid! The level needs to be a number!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis item doesn't exist! Try to type the specific item in full lowercase and replace all spaces with _"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /a item (Item Name) (Level)"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use this command!"));
						}
					}
					if(args.length == 4) {
						if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
							if(args[0].equalsIgnoreCase("money")) {
								if(args[1].equalsIgnoreCase("give")) {
									OfflinePlayer p = Bukkit.getOfflinePlayer(args[2]);
									if(p.getName() != null) {
										if(dfManager.contains(p.getUniqueId())) {
											DFPlayer dfPlayer = dfManager.getEntity(p.getUniqueId());
											double amount = 0.00;
											try {
												amount = Double.parseDouble(args[3]);
											}
											catch(NumberFormatException ex) {
												ex.printStackTrace();
											}
											if(amount > 0.00) {
												dfPlayer.addMoney(amount);
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have given &6" + p.getName() + " &6" + amount + "$!"));
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis value is invalid!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis has never joined!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player does not exist!"));
									}
								}
								else if(args[1].equalsIgnoreCase("set")) {
									if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
										OfflinePlayer p = Bukkit.getOfflinePlayer(args[2]);
										if(p.getName() != null) {
											if(dfManager.contains(p.getUniqueId())) {
												DFPlayer dfPlayer = dfManager.getEntity(p.getUniqueId());
												double amount = 0.00;
												try {
													amount = Double.parseDouble(args[3]);
												}
												catch(NumberFormatException ex) {
													ex.printStackTrace();
												}
												if(amount >= 0.00) {
													dfPlayer.setMoney(amount);
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have set &6" + p.getName() + " &cmoney to &6" + amount + "$!"));
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis value is invalid!"));
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis has never joined!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player does not exist!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is to low to set someones money!"));
									}
								}
								else if(args[1].equalsIgnoreCase("remove")) {
									if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
										OfflinePlayer p = Bukkit.getOfflinePlayer(args[2]);
										if(p.getName() != null) {
											if(dfManager.contains(p.getUniqueId())) {
												DFPlayer dfPlayer = dfManager.getEntity(p.getUniqueId());
												double amount = 0.00;
												try {
													amount = Double.parseDouble(args[3]);
												}
												catch(NumberFormatException ex) {
													ex.printStackTrace();
												}
												if(amount > 0.00) {
													if(dfPlayer.getMoney() >= amount) {
														dfPlayer.removeMoney(amount);
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have removed &6" + amount + "$ &cfrom &6" + p.getName()));
													}
													else {
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't remove more money! Else their balance will gon into the negative!"));
													}
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis value is invalid!"));
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis has never joined!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player does not exist!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is to low to set someones money!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /a money give/set/remove (Player Name) (Amount)"));
								}
							}
							else if(args[0].equalsIgnoreCase("level")) {
								if(args[1].equalsIgnoreCase("set")) {
									OfflinePlayer p = Bukkit.getOfflinePlayer(args[2]);
									if(p.getName() != null) {
										int level = -1;
										try {
											level = Integer.parseInt(args[3]);
										}
										catch(NumberFormatException ex) {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe level you gave is not a valid number!"));
											ex.printStackTrace();
										}
										if(level > 0 && level <= 100) {
											dfManager.softResetEntity(p.getUniqueId());
											DFPlayer dfPlayer = dfManager.getEntity(p.getUniqueId());
											for(int i = 1; i < level; i++) {
										        if(dfPlayer.getLevel() < 100) {
													dfPlayer.addLevel(1);
													dfPlayer.setExperience(0);
													dfPlayer.setMaxExperience((int)(double)(dfPlayer.getMaxExperience() / 100.00 * (dfPlayer.getExperienceMultiplier() * 100.00)));
													dfPlayer.addSkillPoints(3);
												}
									        }
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have set &6" + p.getName() + "'s &alevel to &6" + level + "!"));
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe level can only be between 1 and 100!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player doesn't exist!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! /level set (Player Name)"));
								}
							}
							else if(args[0].equalsIgnoreCase("item")) {
								if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
									HashMap<String, ItemStack> stacks = this.rewards.getItemList();
									if(stacks.containsKey(args[1])) {
										ItemStack i = stacks.get(args[1]).clone();
										NBTItem item = new NBTItem(i);
										boolean validLevel = true;
										boolean validXp = true;
										int level = 0;
										int xp = 0;
										try {
											level = Integer.parseInt(args[2]);
										}
										catch(NumberFormatException ex) {
											validLevel = false;
											ex.printStackTrace();
										}
										try {
											xp = Integer.parseInt(args[3]);
										}
										catch(NumberFormatException ex) {
											validXp = false;
											ex.printStackTrace();
										}
										if(validLevel) {
											if(level > 0 && level <= 15) {
												if(validXp) {
													if(item.hasKey("WeaponKey") || item.hasKey("BowKey")) {
														int itemLevel = item.getInteger("Level");
														int maxxp = item.getInteger("MAXXP");
														int total = item.getInteger("TotalXP");	
														String name = item.getString("ItemName");
														String rarity = item.getString("Rarity");
														String enchantmentString = item.getString("EnchantmentString");
														double baseDamage = item.getDouble("Base Attack Damage");
														double baseSpeed = item.getDouble("Base Attack Speed");
														double incDamage = item.getDouble("Inc Attack Damage");
														double incSpeed = item.getDouble("Inc Attack Speed");
														for(int x = itemLevel + 1; x <= level; x++) {
															itemLevel = x;
															total = total + maxxp;
															maxxp = maxxp / 100 * 135;
														}
														double value1 = baseDamage + incDamage * (itemLevel - 1);
										            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
										            	if(xp >= maxxp) {
										            		xp = maxxp - 1;
										            	}
														//Config Data
														//Weapon Data
										            	item.setInteger("Level", itemLevel);
										            	item.setInteger("XP", xp);
										            	item.setInteger("MAXXP", maxxp);
										            	item.setInteger("TotalXP", total);
										            	item.setDouble("Attack Damage", value1);
										            	item.setDouble("Attack Speed", value2);
										            	i = item.getItem();
														ItemMeta meta = i.getItemMeta();
														meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
														ArrayList<String> newLore = new ArrayList<String>();
														newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
														double roundOff1 = (double) Math.round(value1 * 100) / 100;
														double roundOff2 = (double) Math.round(value2 * 100) / 100;
														newLore.add(new CCT().colorize("&7-----------------------"));
														newLore.add(new CCT().colorize("&7Attack Damage: &6" + roundOff1));
														newLore.add(new CCT().colorize("&7Attack Speed: &6" + roundOff2));
														newLore.add(new CCT().colorize("&7-----------------------"));
														if(itemLevel < 15) {
															newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + xp + " &6/ &b&l" + maxxp + "&a]"));
															newLore.add(new CCT().colorize(this.getNewXPBar(xp, maxxp)));
														}
														else if(itemLevel == 15) {
															newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
														}
														newLore.add(new CCT().colorize("&7-----------------------"));
														if(itemLevel >= 6) {
															int loreRequired = itemLevel - 4;
															int levelRequired = loreRequired * 5;
															newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
														}
														newLore.add(new CCT().colorize("&7Rarity: " + rarity));
														meta.setLore(newLore);
														i.setItemMeta(meta);
													}
													else if(item.hasKey("ArmorKey")) {
														if(i.getType().toString().contains("HELMET")) {
															int itemLevel = item.getInteger("Level");
															int maxxp = item.getInteger("MAXXP");
															int total = item.getInteger("TotalXP");	
															String name = item.getString("ItemName");
															String rarity = item.getString("Rarity");
															String enchantmentString = item.getString("EnchantmentString");
															double baseDamage = item.getDouble("Base Armor Defense");
															double baseSpeed = item.getDouble("Base Armor Toughness");
															double incDamage = item.getDouble("Inc Armor Defense");
															double incSpeed = item.getDouble("Inc Armor Toughness");
															for(int x = itemLevel + 1; x <= level; x++) {
																itemLevel = x;
																total = total + maxxp;
																maxxp = maxxp / 100 * 135;
															}
															double value1 = baseDamage + incDamage * (itemLevel - 1);
											            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
															//Config Data
															//Weapon Data
											            	if(xp >= maxxp) {
											            		xp = maxxp - 1;
											            	}
											            	item.setInteger("Level", itemLevel);
											            	item.setInteger("XP", xp);
											            	item.setInteger("MAXXP", maxxp);
											            	item.setInteger("TotalXP", total);
											            	item.setDouble("Armor Defense", value1);
											            	item.setDouble("Armor Toughness", value2);
											            	i = item.getItem();
															ItemMeta meta = i.getItemMeta();
															meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
															ArrayList<String> newLore = new ArrayList<String>();
															newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
															double roundOff1 = (double) Math.round(value1 * 100) / 100;
															double roundOff2 = (double) Math.round(value2 * 100) / 100;
															newLore.add(new CCT().colorize("&7-----------------------"));
															newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
															newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
															newLore.add(new CCT().colorize("&7-----------------------"));
															if(itemLevel < 15) {
																newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + xp + " &6/ &b&l" + maxxp + "&a]"));
																newLore.add(new CCT().colorize(this.getNewXPBar(xp, maxxp)));
															}
															else if(itemLevel == 15) {
																newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
															}
															newLore.add(new CCT().colorize("&7-----------------------"));
															if(itemLevel >= 6) {
																int loreRequired = itemLevel - 4;
																int levelRequired = loreRequired * 5;
																newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
															}
															newLore.add(new CCT().colorize("&7Rarity: " + rarity));
															meta.setLore(newLore);
															i.setItemMeta(meta);
														}
														if(i.getType().toString().contains("CHESTPLATE")) {
															int itemLevel = item.getInteger("Level");
															int maxxp = item.getInteger("MAXXP");
															int total = item.getInteger("TotalXP");	
															String name = item.getString("ItemName");
															String rarity = item.getString("Rarity");
															String enchantmentString = item.getString("EnchantmentString");
															double baseDamage = item.getDouble("Base Armor Defense");
															double baseSpeed = item.getDouble("Base Armor Toughness");
															double incDamage = item.getDouble("Inc Armor Defense");
															double incSpeed = item.getDouble("Inc Armor Toughness");
															for(int x = itemLevel + 1; x <= level; x++) {
																itemLevel = x;
																total = total + maxxp;
																maxxp = maxxp / 100 * 135;
															}
															double value1 = baseDamage + incDamage * (itemLevel - 1);
											            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
															//Config Data
															//Weapon Data
											            	if(xp >= maxxp) {
											            		xp = maxxp - 1;
											            	}
											            	item.setInteger("Level", itemLevel);
											            	item.setInteger("XP", xp);
											            	item.setInteger("MAXXP", maxxp);
											            	item.setInteger("TotalXP", total);
											            	item.setDouble("Armor Defense", value1);
											            	item.setDouble("Armor Toughness", value2);
											            	i = item.getItem();
															ItemMeta meta = i.getItemMeta();
															meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
															ArrayList<String> newLore = new ArrayList<String>();
															newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
															double roundOff1 = (double) Math.round(value1 * 100) / 100;
															double roundOff2 = (double) Math.round(value2 * 100) / 100;
															newLore.add(new CCT().colorize("&7-----------------------"));
															newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
															newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
															newLore.add(new CCT().colorize("&7-----------------------"));
															if(itemLevel < 15) {
																newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + xp + " &6/ &b&l" + maxxp + "&a]"));
																newLore.add(new CCT().colorize(this.getNewXPBar(xp, maxxp)));
															}
															else if(itemLevel == 15) {
																newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
															}
															newLore.add(new CCT().colorize("&7-----------------------"));
															if(itemLevel >= 6) {
																int loreRequired = itemLevel - 4;
																int levelRequired = loreRequired * 5;
																newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
															}
															newLore.add(new CCT().colorize("&7Rarity: " + rarity));
															meta.setLore(newLore);
															i.setItemMeta(meta);
														}
														if(i.getType().toString().contains("LEGGINGS")) {
															int itemLevel = item.getInteger("Level");
															int maxxp = item.getInteger("MAXXP");
															int total = item.getInteger("TotalXP");	
															String name = item.getString("ItemName");
															String rarity = item.getString("Rarity");
															String enchantmentString = item.getString("EnchantmentString");
															double baseDamage = item.getDouble("Base Armor Defense");
															double baseSpeed = item.getDouble("Base Armor Toughness");
															double incDamage = item.getDouble("Inc Armor Defense");
															double incSpeed = item.getDouble("Inc Armor Toughness");
															for(int x = itemLevel + 1; x <= level; x++) {
																itemLevel = x;
																total = total + maxxp;
																maxxp = maxxp / 100 * 135;
															}
															double value1 = baseDamage + incDamage * (itemLevel - 1);
											            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
															//Config Data
															//Weapon Data
											            	if(xp >= maxxp) {
											            		xp = maxxp - 1;
											            	}
											            	item.setInteger("Level", itemLevel);
											            	item.setInteger("XP", xp);
											            	item.setInteger("MAXXP", maxxp);
											            	item.setInteger("TotalXP", total);
											            	item.setDouble("Armor Defense", value1);
											            	item.setDouble("Armor Toughness", value2);
											            	i = item.getItem();
															ItemMeta meta = i.getItemMeta();
															meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
															ArrayList<String> newLore = new ArrayList<String>();
															newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
															double roundOff1 = (double) Math.round(value1 * 100) / 100;
															double roundOff2 = (double) Math.round(value2 * 100) / 100;
															newLore.add(new CCT().colorize("&7-----------------------"));
															newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
															newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
															newLore.add(new CCT().colorize("&7-----------------------"));
															if(itemLevel < 15) {
																newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + xp + " &6/ &b&l" + maxxp + "&a]"));
																newLore.add(new CCT().colorize(this.getNewXPBar(xp, maxxp)));
															}
															else if(itemLevel == 15) {
																newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
															}
															newLore.add(new CCT().colorize("&7-----------------------"));
															if(itemLevel >= 6) {
																int loreRequired = itemLevel - 4;
																int levelRequired = loreRequired * 5;
																newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
															}
															newLore.add(new CCT().colorize("&7Rarity: " + rarity));
															meta.setLore(newLore);
															i.setItemMeta(meta);
														}
														if(i.getType().toString().contains("BOOTS")) {
															int itemLevel = item.getInteger("Level");
															int maxxp = item.getInteger("MAXXP");
															int total = item.getInteger("TotalXP");	
															String name = item.getString("ItemName");
															String rarity = item.getString("Rarity");
															String enchantmentString = item.getString("EnchantmentString");
															double baseDamage = item.getDouble("Base Armor Defense");
															double baseSpeed = item.getDouble("Base Armor Toughness");
															double incDamage = item.getDouble("Inc Armor Defense");
															double incSpeed = item.getDouble("Inc Armor Toughness");
															for(int x = itemLevel + 1; x <= level; x++) {
																itemLevel = x;
																total = total + maxxp;
																maxxp = maxxp / 100 * 135;
															}
															double value1 = baseDamage + incDamage * (itemLevel - 1);
											            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
															//Config Data
															//Weapon Data
											            	if(xp >= maxxp) {
											            		xp = maxxp - 1;
											            	}
											            	item.setInteger("Level", itemLevel);
											            	item.setInteger("XP", xp);
											            	item.setInteger("MAXXP", maxxp);
											            	item.setInteger("TotalXP", total);
											            	item.setDouble("Armor Defense", value1);
											            	item.setDouble("Armor Toughness", value2);
											            	i = item.getItem();
															ItemMeta meta = i.getItemMeta();
															meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
															ArrayList<String> newLore = new ArrayList<String>();
															newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
															double roundOff1 = (double) Math.round(value1 * 100) / 100;
															double roundOff2 = (double) Math.round(value2 * 100) / 100;
															newLore.add(new CCT().colorize("&7-----------------------"));
															newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
															newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
															newLore.add(new CCT().colorize("&7-----------------------"));
															if(itemLevel < 15) {
																newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + xp + " &6/ &b&l" + maxxp + "&a]"));
																newLore.add(new CCT().colorize(this.getNewXPBar(xp, maxxp)));
															}
															else if(itemLevel == 15) {
																newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
															}
															newLore.add(new CCT().colorize("&7-----------------------"));
															if(itemLevel >= 6) {
																int loreRequired = itemLevel - 4;
																int levelRequired = loreRequired * 5;
																newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
															}
															newLore.add(new CCT().colorize("&7Rarity: " + rarity));
															meta.setLore(newLore);
															i.setItemMeta(meta);
														}
													}
													else if(item.hasKey("ShieldKey")) {
														int itemLevel = item.getInteger("Level");
														int maxxp = item.getInteger("MAXXP");
														int total = item.getInteger("TotalXP");	
														String name = item.getString("ItemName");
														String rarity = item.getString("Rarity");
														String enchantmentString = item.getString("EnchantmentString");
														double baseDamage = item.getDouble("Base Armor Toughness");
														double baseSpeed = item.getDouble("Base Cooldown");
														double incDamage = item.getDouble("Inc Armor Toughness");
														double incSpeed = item.getDouble("Inc Cooldown");
														for(int x = itemLevel + 1; x <= level; x++) {
															itemLevel = x;
															total = total + maxxp;
															maxxp = maxxp / 100 * 135;
														}
														double value1 = baseDamage + incDamage * (itemLevel - 1);
										            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
														//Config Data
														//Weapon Data
										            	if(xp >= maxxp) {
										            		xp = maxxp - 1;
										            	}
										            	item.setInteger("Level", itemLevel);
										            	item.setInteger("XP", xp);
										            	item.setInteger("MAXXP", maxxp);
										            	item.setInteger("TotalXP", total);
										            	item.setDouble("Armor Toughness", value1);
										            	item.setDouble("Cooldown", value2);
										            	i = item.getItem();
														ItemMeta meta = i.getItemMeta();
														meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
														ArrayList<String> newLore = new ArrayList<String>();
														newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
														double roundOff1 = (double) Math.round(value1 * 100) / 100;
														double roundOff2 = (double) Math.round(value2 * 100) / 100;
														newLore.add(new CCT().colorize("&7-----------------------"));
														newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff1));
														newLore.add(new CCT().colorize("&7Cooldown: &b" + roundOff2 + " Seconds"));
														newLore.add(new CCT().colorize("&7-----------------------"));
														if(itemLevel < 15) {
															newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + xp + " &6/ &b&l" + maxxp + "&a]"));
															newLore.add(new CCT().colorize(this.getNewXPBar(xp, maxxp)));
														}
														else if(itemLevel == 15) {
															newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
														}
														newLore.add(new CCT().colorize("&7-----------------------"));
														if(itemLevel >= 6) {
															int loreRequired = itemLevel - 4;
															int levelRequired = loreRequired * 5;
															newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
														}
														newLore.add(new CCT().colorize("&7Rarity: " + rarity));
														meta.setLore(newLore);
														i.setItemMeta(meta);
													}
													player.getInventory().addItem(i);
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis input as xp is not valid! The xp needs to be a number!"));
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe level must be in between 1 and 15!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis input as level is not valid! The level needs to be a number!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis item doesn't exist! Try to type the specific item in full lowercase and replace all spaces with _"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use this command!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cWrong ussage: /item (Item Name) (Level)"));
							}
						}
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not staff!"));
				}	
			}
			else if(cmd.getName().equalsIgnoreCase(item)) {
				if(rPlayer.isStaff()) {
					if(args.length == 1) {
						if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
							String itemName = args[0].toUpperCase();
							if(Material.getMaterial(itemName) != null) {
								ItemStack stack = new ItemStack(Material.getMaterial(itemName), 1);
								player.getInventory().addItem(stack);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aItem has been given!"));
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis item does not exist! Try to type the name with the spaces replaced with _"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use admin item commands!"));
						}
					}
					if(args.length == 2) {
						if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
							String itemName = args[0].toUpperCase();
							int amount = 0;
							try {
								amount = Integer.parseInt(args[1]);
							}
							catch(NumberFormatException ex) {
								ex.printStackTrace();
							}
							if(Material.getMaterial(itemName) != null) {
								if(amount > 0) {
									ItemStack stack = new ItemStack(Material.getMaterial(itemName), amount);
									player.getInventory().addItem(stack);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aItem has been given!"));
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe amount cannot be 0 or below!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis item does not exist! Try to type the name with the spaces replaced with _"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to use admin item commands!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid ussage: /item (item_name) (Optional)(Amount)"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is to low to use this command!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(protocol)) {
				if(rPlayer.isStaff()) {
					if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
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
							if(args[0].equalsIgnoreCase("updatechests")) {
								CustomEnchantments.maintenance = true;
								for(LootChest lc : this.lcManager.getLootList().values()) {
									Block block = lc.getLocation().getBlock();
									if(block != null) {
										if(block.getType() != Material.AIR) {
											BlockState bs = block.getState();
								            if(bs instanceof Chest) {
								            	NBTCompound comp = NBTInjector.getNbtData(block.getState());
												if(!comp.hasKey("LootChest")) {
													comp.setString("LootChest", "");
												}
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
											if(!oPlayer.isStaff()) {
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
        if(sender instanceof Player) { 
        	Player player = (Player) sender;
        	RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
        	if(command.getName().equalsIgnoreCase(protocol)) { 
            	if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
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
        	else if(command.getName().equalsIgnoreCase(gamemode)) { 
        		if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
        			if(args.length == 1) {
        				ArrayList<String> itemNames = new ArrayList<String>(Arrays.asList("survival", "creative", "adventure", "spectator", "0", "1", "2", "3"));
            			ArrayList<String> collection = new ArrayList<String>();
            			StringUtil.copyPartialMatches(args[0], itemNames, collection);
        				return collection;
        			}
        		}
        	}
        	else if(command.getName().equalsIgnoreCase(a)) { 
        		if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
	            	if(args.length == 1) {
	            		ArrayList<String> itemNames = new ArrayList<String>(Arrays.asList("money", "level", "item", "spawner", "moneynote", "crystal", "xpbottle", "fxpbottle", "kitkey"));
            			ArrayList<String> collection = new ArrayList<String>();
            			return StringUtil.copyPartialMatches(args[0], itemNames, collection);
	            	}
	            	else if(args.length == 2) {
	            		if(args[0].equalsIgnoreCase("money")) {
	            			ArrayList<String> itemNames = new ArrayList<String>(Arrays.asList("give", "set", "remove"));
	            			ArrayList<String> collection = new ArrayList<String>();
	            			return StringUtil.copyPartialMatches(args[1], itemNames, collection);
	            		}
	            		else if(args[0].equalsIgnoreCase("level")) {
	            			return Arrays.asList("set");
	            		}
	            		else if(args[0].equalsIgnoreCase("item")) {
	            			ArrayList<String> itemNames = new ArrayList<String>(rewards.getItemList().keySet());
	            			ArrayList<String> collection = new ArrayList<String>();
	            			StringUtil.copyPartialMatches(args[1], itemNames, collection);
	            			return collection;
	            		}
	            	}
	            	else if(args.length == 4) {
	            		if(args[0].equalsIgnoreCase("money")) {
	            			return Arrays.asList("number");
	            		}
	            		else if(args[0].equalsIgnoreCase("level")) {
	            			return Arrays.asList("number");
	            		}
	            	}
            	}
        		else if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
	            	if(args.length == 1) {
	            		return Arrays.asList("money");
	            	}
	            	else if(args.length == 2) {
	            		if(args[0].equalsIgnoreCase("money")) {
	            			return Arrays.asList("give");
	            		}
	            	}
	            	else if(args.length == 3) {
	            		if(args[0].equalsIgnoreCase("money")) {
	            			return Arrays.asList("number");
	            		}
	            	}
            	}
            }
        	else if(command.getName().equalsIgnoreCase(af)) { 
        		if(rPlayer.getHighestRank().getRank() >= Rank.HEAD_ADMIN.getRank()) {
        			if(args.length == 1) {
        				ArrayList<String> list = new ArrayList<String>(Arrays.asList("kick", "promote", "demote", "unclaim", "rename", "leader", "disband", "bank"));
        				ArrayList<String> found = new ArrayList<String>();
        				StringUtil.copyPartialMatches(args[0], list, found);
        				return found;
        			}
        			else if(args.length == 2) {
        				if(args[0].equalsIgnoreCase("unclaim")) {
        					ArrayList<String> list = facManager.getAllFactionNames();
            				ArrayList<String> found = new ArrayList<String>();
            				StringUtil.copyPartialMatches(args[0], list, found);
            				return found;
        				}
        				else if(args[0].equalsIgnoreCase("rename")) {
        					ArrayList<String> list = facManager.getAllFactionNames();
            				ArrayList<String> found = new ArrayList<String>();
            				StringUtil.copyPartialMatches(args[0], list, found);
            				return found;
        				}
        				else if(args[0].equalsIgnoreCase("disband")) {
        					ArrayList<String> list = facManager.getAllFactionNames();
            				ArrayList<String> found = new ArrayList<String>();
            				StringUtil.copyPartialMatches(args[0], list, found);
            				return found;
        				}
        				else if(args[0].equalsIgnoreCase("bank")) {
            				return Arrays.asList("set");
        				}
        			}
        			else if(args.length == 3) {
        				if(args[0].equalsIgnoreCase("rename")) {
        					return Arrays.asList("");
        				}
        				else if(args[0].equalsIgnoreCase("bank")) {
        					ArrayList<String> list = facManager.getAllFactionNames();
            				ArrayList<String> found = new ArrayList<String>();
            				StringUtil.copyPartialMatches(args[0], list, found);
            				return found;
        				}
        			}
        			else if(args.length == 4) {
        				if(args[0].equalsIgnoreCase("bank")) {
            				return Arrays.asList("amount");
        				}
        			}
        		}
        		else if(rPlayer.getHighestRank().getRank() >= Rank.ADMIN.getRank()) {
        			if(args.length == 1) {
        				ArrayList<String> list = new ArrayList<String>(Arrays.asList("kick", "promote", "demote", "unclaim", "rename"));
        				ArrayList<String> found = new ArrayList<String>();
        				StringUtil.copyPartialMatches(args[0], list, found);
        				return found;
        			}
        			else if(args.length == 2) {
        				if(args[0].equalsIgnoreCase("unclaim")) {
        					ArrayList<String> list = facManager.getAllFactionNames();
            				ArrayList<String> found = new ArrayList<String>();
            				StringUtil.copyPartialMatches(args[0], list, found);
            				return found;
        				}
        				else if(args[0].equalsIgnoreCase("rename")) {
        					ArrayList<String> list = facManager.getAllFactionNames();
            				ArrayList<String> found = new ArrayList<String>();
            				StringUtil.copyPartialMatches(args[0], list, found);
            				return found;
        				}
        			}
        			else if(args.length == 3) {
        				if(args[0].equalsIgnoreCase("rename")) {
        					return Arrays.asList("");
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
	public ItemStack capture() {
		ItemStack item = new ItemStack(Material.BLUE_BANNER, 1);
		ItemMeta meta = item.getItemMeta();	
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(new CCT().colorize("&9Capture Point Setup"));
		lore.add(new CCT().colorize("&f-------------------"));
		lore.add(new CCT().colorize("&fRight click to switch to setting up capture points."));
		lore.add(new CCT().colorize("&f-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem tempItem = new NBTItem(item);
		tempItem.setString("CapturePointItem", "");
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
	public void damageGod(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(sManager.contains(player.getUniqueId())) {
				Staff staff = sManager.get(player.getUniqueId());
				if(staff.getGodMode()) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void punishJoin(PlayerJoinEvent event) {
		this.punishJoin(event.getPlayer());
	}
	
	private String getNewXPBar(double xp, double maxxp) {
		double barprogress = (double) xp / (double) maxxp * 100.0;
		String loreString = "&7[&a";
		boolean canStop = true;
		for(double x = 0.00; x <= 100.00; x+=2.00) {
			if(barprogress >= x) {
				loreString = loreString + ":";
			}
			else if(canStop) {
				loreString = loreString + "&7:";
				canStop = false;
			}
			else {
				loreString = loreString + ":";
			}
			if(x == 100) {
				loreString = loreString + "&7] &a" + String.format("%.2f", barprogress) + "%";
			}
		}
		return new CCT().colorize(loreString);
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
	public void moveEvent(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(this.freezeList.contains(player.getUniqueId())) {
			if(event.getFrom().distance(event.getTo()) >= 0.01) {
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void jumpEvent(PlayerJumpEvent event) {
		Player player = event.getPlayer();
		if(this.freezeList.contains(player.getUniqueId())) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void cancelDamage(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(this.freezeList.contains(player.getUniqueId())) {
				event.setCancelled(true);
			}
		}
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(this.freezeList.contains(player.getUniqueId())) {
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(this.freezeList.contains(player.getUniqueId())) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void blockBreak(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(this.freezeList.contains(player.getUniqueId())) {
			event.setCancelled(true);
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
							player.getInventory().setItem(0, this.builder.constructItem(
									Material.COMPASS, 
									"&aSpawner Menu", 
									new String[] {
										"&7Right click this item to open",
										"&7up the spawner menu to create/delete spawner items."
									},
									new Pair<String, String>("SpawnerCreate", "")
							));
							player.getInventory().setItem(1, this.builder.constructItem(
									Material.BARRIER, 
									"&cSpawner Delete", 
									new String[] {
										"&7Right click this item to delete",
										"&7any spawner. Make sure there is a block",
										"&7actually placed in the spawner."
									},
									new Pair<String, String>("SpawnerDelete", "")
							));
							player.getInventory().setItem(2, this.builder.constructItem(
									Material.YELLOW_STAINED_GLASS, 
									"&6Spawner See", 
									new String[] {
										"&7Right click this item to see",
										"&7any spawner in a 20 block radius.",
										"&7These spawners will turn into yellow glass."
									},
									new Pair<String, String>("SpawnerSee", "")
							));
						}
						else if(item.hasKey("LootItem")) {
							staff.switchLootMode(true);
							player.getInventory().clear();
							player.getInventory().setItem(0, this.builder.constructItem(
									Material.COMPASS, 
									"&aLoot Menu", 
									new String[] {
										"&7Right click this item to open",
										"&7up the loot chests menu to create/delete chest items."
									},
									new Pair<String, String>("LootCreate", "")
							));
							player.getInventory().setItem(1, this.builder.constructItem(
									Material.BARRIER, 
									"&cLoot Delete", 
									new String[] {
										"&7Right click this item to delete",
										"&7any loot chest. Make sure there is a block",
										"&7actually placed in the position of the loot chest.",
										"&7(A chest in its place is also valid)"
									},
									new Pair<String, String>("LootDelete", "")
							));
							player.getInventory().setItem(2, this.builder.constructItem(
									Material.YELLOW_STAINED_GLASS, 
									"&6Loot See", 
									new String[] {
										"&7Right click this item to see",
										"&7any loot chest in a 20 block radius.",
										"&7These loot chests will turn into yellow glass."
									},
									new Pair<String, String>("LootSee", "")
							));
						}
						else if(item.hasKey("CapturePointItem")) {
							staff.switchCapturePointMode(true);
							player.getInventory().clear();
							player.getInventory().setItem(0, this.builder.constructItem(
									Material.COMPASS, 
									"&aCapture Point Menu", 
									new String[] {
										"&7Right click this item to open",
										"&7up the capture points menu to create/delete",
										"&7capture point items."
									},
									new Pair<String, String>("CapturePointCreate", "")
							));
							player.getInventory().setItem(1, this.builder.constructItem(
									Material.BARRIER, 
									"&cCapture Point Delete", 
									new String[] {
										"&7Right click this item to delete",
										"&7any capture points. Make sure there is a block",
										"&7actually placed in the position of the",
										"&7capture point."
									},
									new Pair<String, String>("CapturePointDelete", "")
							));
							player.getInventory().setItem(2, this.builder.constructItem(
									Material.BLUE_STAINED_GLASS, 
									"&6Capture Point See", 
									new String[] {
										"&7Right click this item to see",
										"&7any capture points in a 20 block radius.",
										"&7These capture points will turn into blue glass."
									},
									new Pair<String, String>("CapturePointSee", "")
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
							if(rPlayer.getHighestRank().getRank() >= Rank.HELPER_PLUS.getRank()) {
								player.closeInventory();
								gui.BanChoice(player, target.get(player.getUniqueId()).getValue());
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to ban players!"));
							}
						}
					}
					else if(i.hasKey("History Option")) {
						if(target.containsKey(player.getUniqueId())) {
							if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
								player.closeInventory();
								gui.PunishHistory(player, target.get(player.getUniqueId()).getValue());
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to ban players!"));
							}
						}
					}
					else if(i.hasKey("Mute Option")) {
						if(target.containsKey(player.getUniqueId())) {
							if(rPlayer.getHighestRank().getRank() >= Rank.HELPER.getRank()) {
								player.closeInventory();
								gui.MuteChoice(player, target.get(player.getUniqueId()).getValue());
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
							if(rPlayer.getHighestRank().getRank() >= Rank.HELPER_PLUS.getRank()) {
								if(!pun.isBanned()) {
									player.closeInventory();
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
							if(rPlayer.getHighestRank().getRank() >= Rank.MOD.getRank()) {
								if(pun.isBanned()) {
									player.closeInventory();
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
	@EventHandler(priority = EventPriority.LOWEST)
	public void banReason(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish, Ban Reason")) {
				if(i != null && i.getType() != Material.AIR) {
					NBTItem item = new NBTItem(i);
					if(target.containsKey(player.getUniqueId())) {
						OfflinePlayer p = Bukkit.getOfflinePlayer(target.get(player.getUniqueId()).getKey());
						if(p.getName() != null) {
							Punish pun = pManager.get(p.getUniqueId());
							if(item.hasKey("Combat Modifications")) {
								pun.setBanPerm(true);
								pun.addBannedBy(player.getName());
								pun.addBanReason("Combat Modifications");
							}
							else if(item.hasKey("Auto Modifications")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Auto Modifications");
									pun.setBanTime(System.currentTimeMillis() + 5184000000L);
								}
								else if(pun.getBanOffense() >= 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Auto Modifications");
									pun.setBanPerm(true);
								}
							}
							else if(item.hasKey("Movement Modifications")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Movement Modifications");
									pun.setBanTime(System.currentTimeMillis() + 5184000000L);
								}
								else if(pun.getBanOffense() >= 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Movement Modifications");
									pun.setBanPerm(true);
								}
							}
							else if(item.hasKey("ESP Modifications")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("ESP Modifications");
									pun.setBanTime(System.currentTimeMillis() + 2592000000L);
								}
								else if(pun.getBanOffense() >= 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("ESP Modifications");
									pun.setBanPerm(true);
								}
							}
							else if(item.hasKey("Health Modifications")) {
								pun.setBanPerm(true);
								pun.addBannedBy(player.getName());
								pun.addBanReason("Health Modifications");
							}
							else if(item.hasKey("Building Modifications")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Building Modifications");
									pun.setBanTime(System.currentTimeMillis() + 2592000000L);
								}
								else if(pun.getBanOffense() >= 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Building Modifications");
									pun.setBanPerm(true);
								}
							}
							else if(item.hasKey("Wallhack Modifications")) {
								pun.setBanPerm(true);
								pun.addBannedBy(player.getName());
								pun.addBanReason("Wallhack Modifications");
							}
							else if(item.hasKey("Party Modifications")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Party Modifications");
									pun.setBanTime(System.currentTimeMillis() + 604800000L);
								}
								if(pun.getBanOffense() == 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Party Modifications");
									pun.setBanTime(System.currentTimeMillis() + 2592000000L);
								}
								else if(pun.getBanOffense() >= 2) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Party Modifications");
									pun.setBanPerm(true);
								}
							}
							else if(item.hasKey("Illegal Raiding")) {
								if(pun.getBanOffense() >= 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Illegal Raiding");
									pun.setBanPerm(true);
								}
							}
							else if(item.hasKey("Map Glitching")) {
								if(pun.getBanOffense() == 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Map Glitching");
									pun.setBanTime(System.currentTimeMillis() + 5184000000L);
								}
								if(pun.getBanOffense() >= 1) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Map Glitching");
									pun.setBanPerm(true);
								}
							}
							else if(item.hasKey("Item Duplication/Glitching")) {
								if(pun.getBanOffense() >= 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Item Duplication/Glitching");
									pun.setBanPerm(true);
								}
							}
							else if(item.hasKey("Abusing selfmade/other plugins")) {
								if(pun.getBanOffense() >= 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Abusing selfmade/other plugins to gain an advantage");
									pun.setBanPerm(true);
								}
							}
							else if(item.hasKey("Forbidden Items")) {
								if(pun.getBanOffense() >= 0) {
									pun.addBannedBy(player.getName());
									pun.addBanReason("Keep/Using forbidden items");
									pun.setBanPerm(true);
								}
							}
							int year = Calendar.getInstance().get(Calendar.YEAR);
							int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
							int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
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
							RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
							for(Entry<UUID, RankedPlayer> entry : rManager.getRankedPlayerList().entrySet()) {
								if(!entry.getKey().equals(player.getUniqueId())) {
									RankedPlayer r = entry.getValue();
									if(rPlayer.getHighestRank().getRank() >= r.getHighestRank().getRank()) {
										if(Bukkit.getPlayer(entry.getKey()) != null) {
											Player pp = Bukkit.getPlayer(entry.getKey());
											pp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas banned " + p.getName() + " for: &b" + time + " &creason: " + pun.getBanReason(pun.getBanReasonsList().size() - 1)));
										}
									}
								}
							}
							this.pluginMessenger.getServers(player);
							new BukkitRunnable() {
								public void run() {
									String data = "";
									data += "MODERATION,,";
									data += p.getUniqueId().toString() + ",,";
									data += pun.getMutePerm() + ",,";
									data += pun.getMuteTime() + ",,";
									ArrayList<String> list = pun.getMuteReasonsList();
									for(int i = 0; i < list.size(); i++) {
										data += list.get(i);
									}
									data += ",,";
									list = pun.getMuteDateList();
									for(int i = 0; i < list.size(); i++) {
										data += list.get(i);
									}
									data += ",,";
									list = pun.getMutedByList();
									for(int i = 0; i < list.size(); i++) {
										data += list.get(i);
									}
									data += ",,";
									data += pun.getBanPerm() + ",,";
									data += pun.getBanTime() + ",,";
									list = pun.getBanReasonsList();
									for(int i = 0; i < list.size(); i++) {
										data += list.get(i);
									}
									data += ",,";
									list = pun.getBanDateList();
									for(int i = 0; i < list.size(); i++) {
										data += list.get(i);
									}
									data += ",,";
									list = pun.getBannedByList();
									for(int i = 0; i < list.size(); i++) {
										data += list.get(i);
									}
									HashMap<String, Pair<String, Integer>> serverIps = new HashMap<String, Pair<String, Integer>>(pluginMessenger.getServerIps());
									String serverName = CustomEnchantments.getInstance().getServer().getName();
									if(serverIps.containsKey(serverName)) {
										serverIps.remove(serverName);
									}
									Bukkit.broadcastMessage(serverIps.keySet().toString());
									for(Pair<String, Integer> ipport : serverIps.values()) {
										CustomEnchantments.sendDataToSocket(ipport.getKey(), ipport.getValue() - 24228, data);
									}
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 3L);
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
							if(rPlayer.getHighestRank().getRank() >= Rank.HELPER.getRank()) {
								if(!pun.isMuted()) {
									player.closeInventory();
									gui.MuteReason(player, target.get(player.getUniqueId()).getValue());
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player is already muted!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour rank is not high enough to mute players!"));
							}
						}
					}
					else if(i.hasKey("Unmute")) {
						if(target.containsKey(player.getUniqueId())) {
							if(rPlayer.getHighestRank().getRank() >= Rank.HELPER_PLUS.getRank()) {
								if(pun.isMuted()) {
									player.closeInventory();
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
	@EventHandler(priority = EventPriority.LOWEST)
	public void muteReason(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish, Mute Reason")) {
				if(i != null && i.getType() != Material.AIR && i.getType() != Material.AIR) {
					NBTItem item = new NBTItem(i);
					if(target.containsKey(player.getUniqueId())) {
						OfflinePlayer p = Bukkit.getOfflinePlayer(target.get(player.getUniqueId()).getKey());
						if(p.getName() != null) {
							Punish pun = pManager.get(p.getUniqueId());
							if(item.hasKey("Spamming Related")) {
								if(pun.getMuteOffense() == 0) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Spamming/Bypassing the chat filter. (Warn)");
								}
								else if(pun.getMuteOffense() == 1) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Spamming/Bypassing the chat filter.");
									pun.setMuteTime(System.currentTimeMillis() + 86400000L);
								}
								else if(pun.getMuteOffense() >= 2) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Spamming/Bypassing the chat filter.");
									pun.setMuteTime(System.currentTimeMillis() + 2592000000L);
								}
							}
							else if(item.hasKey("Insult Related")) {
								if(pun.getMuteOffense() == 0) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Insulting a player. (Warn)");
								}
								else if(pun.getMuteOffense() == 1) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Insulting a player.");
									pun.setMuteTime(System.currentTimeMillis() + 604800000L);
								}
								else if(pun.getMuteOffense() >= 2) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Insulting a player.");
									pun.setMuteTime(System.currentTimeMillis() + 5184000000L);
								}
							}
							else if(item.hasKey("Slur Related")) {
								if(pun.getMuteOffense() == 0) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("using Racist/Sexual/Pedopholic slurs.");
									pun.setMuteTime(System.currentTimeMillis() + 1209600000L);
								}
								else if(pun.getMuteOffense() >= 1) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Using Racist/Sexual/Pedopholic slurs.");
									pun.setMuteTime(System.currentTimeMillis() + 5184000000L);
								}
							}
							else if(item.hasKey("Threat Related")) {
								pun.addMutedBy(player.getName());
								pun.addMuteReason("Threatening.");
								pun.setMuteTime(System.currentTimeMillis() + 5184000000L);
							}
							else if(item.hasKey("Chat Related")) {
								if(pun.getMuteOffense() == 0) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Using Chat Modifications. (Warn)");
								}
								else if(pun.getMuteOffense() == 1) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Using Chat Modifications.");
									pun.setMuteTime(System.currentTimeMillis() + 86400000L);
								}
								else if(pun.getMuteOffense() >= 2) {
									pun.addMutedBy(player.getName());
									pun.addMuteReason("Using Chat Modifications.");
									pun.setMuteTime(System.currentTimeMillis() + 5184000000L);
								}
							}
							player.closeInventory();
							int year = Calendar.getInstance().get(Calendar.YEAR);
							int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
							int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
							int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
							int minute = Calendar.getInstance().get(Calendar.MINUTE);
							int second = Calendar.getInstance().get(Calendar.SECOND);
							String superTime = hour + ":" + minute + ":" + second + " " + day + "-" + month + "-" + year;
							pun.addMuteDate(superTime);
							String time = "";
							if(!pun.getMutePerm()) {
								Long timeLeft = pun.getMuteTime() - System.currentTimeMillis();
								long diffSeconds = timeLeft / 1000 % 60;
						        long diffMinutes = timeLeft / (60 * 1000) % 60;
						        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
						        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
							    time = diffDays + " " + diffHours + ":" + diffMinutes + ":" + diffSeconds;
							}
							else {
								time = "&c&lNever";
							}
							if(pun.isMuted()) {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have muted &6" + p.getName() + " &cfor: &b" + time));
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have warned &6" + p.getName()));
							}
							if(Bukkit.getPlayer(target.get(player.getUniqueId()).getKey()) != null) {
								Player pp = Bukkit.getPlayer(target.get(player.getUniqueId()).getKey());
								if(pun.isMuted()) {
									pp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas muted you for: &b" + time + " &creason: " + pun.getMuteReason(pun.getMuteReasonsList().size() - 1)));
								}
								else {
									pp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas warned you. &cReason: " + pun.getMuteReason(pun.getMuteReasonsList().size() - 1)));
								}
							}
							RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
							for(Entry<UUID, RankedPlayer> entry : rManager.getRankedPlayerList().entrySet()) {
								if(!entry.getKey().equals(player.getUniqueId())) {
									RankedPlayer r = entry.getValue();
									if(rPlayer.getHighestRank().getRank() >= r.getHighestRank().getRank()) {
										if(Bukkit.getPlayer(entry.getKey()) != null) {
											Player pp = Bukkit.getPlayer(entry.getKey());
											if(pun.isMuted()) {
												pp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas muted " + p.getName() + " for: &b" + time + " &creason: " + pun.getMuteReason(pun.getMuteReasonsList().size() - 1)));
											}
											else {
												pp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &chas warned " + p.getName() + ". &cReason: " + pun.getMuteReason(pun.getMuteReasonsList().size() - 1)));
											}
										}
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
	public void punishHistory(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(ChatColor.stripColor(title).contains("Punish History")) {
				if(i != null && i.getType() != Material.AIR && i.getType() != Material.AIR) {
					NBTItem item = new NBTItem(i);
					if(target.containsKey(player.getUniqueId())) {
						OfflinePlayer p = Bukkit.getOfflinePlayer(target.get(player.getUniqueId()).getKey());
						if(p.getName() != null) {
							Punish pun = pManager.get(p.getUniqueId());
							String message = "";
							if(item.getKeys().toString().contains("Ban") || item.getKeys().toString().contains("Mute")) {
								if(item.getKeys().toString().contains("Ban")) {
									if(!pun.getBanReasonsList().isEmpty()) {
										if(item.hasKey("Remove First Ban")) {
											pun.removeBanReason(0);
											pun.removeBanDate(0);
											pun.removeBannedBy(0);
											message = "removed &6" + p.getName() + "'s &cfirst ban punishment!";
										}
										else if(item.hasKey("Remove Last Ban")) {
											int offense = pun.getBanOffense();
											pun.removeBanReason(offense - 1);
											pun.removeBanDate(offense - 1);
											pun.removeBannedBy(offense - 1);
											message = "removed &6" + p.getName() + "'s &clast ban punishment!";
										}
										else if(item.hasKey("Clear Bans")) {
											pun.getBanReasonsList().clear();
											pun.getBanDateList().clear();
											pun.getBannedByList().clear();
											message = "cleared &6" + p.getName() + "'s &cban punishments!";
										}
										pun.setBanTime(0L);
										pun.setBanPerm(false);
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &cdoesn't have any ban punishments!"));
									}
								}
								if(item.getKeys().toString().contains("Mute")) {
									if(!pun.getMuteReasonsList().isEmpty()) {
										if(item.hasKey("Remove First Mute")) {
											pun.removeMuteReason(0);
											pun.removeMuteDate(0);
											pun.removeMutedBy(0);
											message = "removed &6" + p.getName() + "'s &cfirst mute punishment!";
										}
										else if(item.hasKey("Remove Last Mute")) {
											int offense = pun.getMuteOffense();
											pun.removeMuteReason(offense - 1);
											pun.removeMuteDate(offense - 1);
											pun.removeMutedBy(offense - 1);
											message = "removed &6" + p.getName() + "'s &clast mute punishment!";
										}
										else if(item.hasKey("Clear Mutes")) {
											pun.getMuteReasonsList().clear();
											pun.getMuteDateList().clear();
											pun.getMutedByList().clear();
											message = "cleared &6" + p.getName() + "'s &cmute punishments!";
										}
										pun.setMuteTime(0L);
										pun.setMutePerm(false);
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + p.getName() + " &cdoesn't have any mute punishments!"));
									}
								}
								if(!message.equals("")) {
									RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
									for(Entry<UUID, RankedPlayer> entry : rManager.getRankedPlayerList().entrySet()) {
										RankedPlayer r = entry.getValue();
										if(rPlayer.getHighestRank().getRank() >= r.getHighestRank().getRank()) {
											if(Bukkit.getPlayer(entry.getKey()) != null) {
												Player pp = Bukkit.getPlayer(entry.getKey());
												pp.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + "&c " + message));
											}
										}
									}
								}
								gui.PunishHistory(player, p.getName());
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
				if(event.getView().getTitle().contains("Punish") && !event.getView().getTitle().contains("Punish History")) {
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
		if(i != null && i.getType() != Material.AIR && i.getType() != Material.AIR) {
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
				if(i != null && i.getType() != Material.AIR && i.getType() != Material.AIR) {
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
				if(i != null && i.getType() != Material.AIR && i.getType() != Material.AIR) {
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
				if(i != null && i.getType() != Material.AIR && i.getType() != Material.AIR) {
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
				if(i != null && i.getType() != Material.AIR && i.getType() != Material.AIR) {
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
		if(i != null && i.getType() != Material.AIR && i.getType() != Material.AIR) {
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
				if(i != null && i.getType() != Material.AIR && i.getType() != Material.AIR) {
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
				if(i != null && i.getType() != Material.AIR && i.getType() != Material.AIR) {
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
				if(i != null && i.getType() != Material.AIR && i.getType() != Material.AIR) {
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
				if(i != null && i.getType() != Material.AIR && i.getType() != Material.AIR) {
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
	public void capturePointMenu(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		ItemStack i = player.getInventory().getItemInMainHand();
		if(i != null && i.getType() != Material.AIR) {
			NBTItem item = new NBTItem(i);
			if(item.hasKey("CapturePointCreate")) {
				gui.CapturePointMenu(player);
			}
			else if(item.hasKey("CapturePointDelete")){
				boolean message = false;
				Location loc = event.getClickedBlock().getLocation();
				if(!cpManager.getCapturePointList().isEmpty()) {
					for(CapturePoint point : cpManager.getCapturePointList()) {
						if(point != null) {
							if(point.getCaptureLocation().distance(loc) <= 2.5) {
								cpManager.removeCapturePoint(point);
								message = true;
							}
						}
					}
				}
				if(message == true) {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aCapture Point Removed!"));
				}
				else if(message == false) {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cNo Capture Point found."));
				}
			}
			else if(item.hasKey("CapturePointSee")) {
				int count = 0;
				for(CapturePoint point : cpManager.getCapturePointList()) {
					if(point != null) {
						if(player.getLocation().distance(point.getCaptureLocation()) <= 40.0) {
							player.sendBlockChange(point.getCaptureLocation(), Material.BLUE_STAINED_GLASS.createBlockData());
							count++;
							new BukkitRunnable() {
								public void run() {
									Location loc = point.getCaptureLocation();
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aCapture Point Location: " + String.format("%.2f", loc.getX()) + " " + String.format("%.2f", loc.getY()) + " " + String.format("%.2f", loc.getZ())));
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 1L);
						}
					}
				}
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + count + " &aCapture Point found."));
			}
		}
	}
	@EventHandler
	public void chooseCapturePointSlot(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		String title = player.getOpenInventory().getTitle();
		if(player.getOpenInventory() != null) {
			if(i != null && i.getType() != Material.AIR) {
				if(ChatColor.stripColor(title).contains("Capture Point Create")) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("CapturePointSlot")) {
						capturePointSlot.put(player.getUniqueId(), event.getSlot() - 9);
						gui.CapturePointMultiplier(player);
					}
					else if(item.hasKey("CapturePointDelete")) {
						if(event.getClick() == ClickType.LEFT) {
							capturePointSlot.put(player.getUniqueId(), event.getSlot() - 9);
							gui.CapturePointMultiplier(player);
						}
						if(event.getClick() == ClickType.RIGHT) {
							player.getInventory().setItem(event.getSlot() - 9, new ItemStack(Material.AIR));
							gui.CapturePointMenu(player);
						}
					}
				}
				else if(ChatColor.stripColor(title).contains("Capture Point Multiplier")) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("CapturePointMultiplier")) {
						double percent = item.getDouble("CapturePointMultiplier");
						capturePointMultiplier.put(player.getUniqueId(), percent);
						gui.CapturePointRadius(player);
					}
				}
				else if(ChatColor.stripColor(title).contains("Capture Point Radius")) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("CapturePointRadius")) {
						double radius = item.getDouble("CapturePointRadius");
						capturePointRadius.put(player.getUniqueId(), radius);
						this.finishCapturePointCreation(player);
						player.closeInventory();
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aCapture Point created!"));
					}
				}
			}
		}
	}

	@EventHandler
	public void placeCapturePoint(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack i = player.getInventory().getItemInMainHand();
		if(sManager.contains(player.getUniqueId())) {
			Staff staff = sManager.get(player.getUniqueId());
			if(staff.getCapturePointMode() == true) {
				if(i != null && i.getType() != Material.AIR) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("CapturePointPlace")) {
						event.setCancelled(true);
						Location loc = event.getClickedBlock().getLocation().add(0.5, 1.0, 0.5);
						loc.getBlock().setType(Material.BLACK_BANNER);
						double radius = item.getInteger("CapturePointRadius");
						double multiplier = item.getInteger("CapturePointMultiplier");
						CapturePoint cp = new CapturePoint(loc, radius, multiplier);
						cpManager.addCapturePoint(cp);
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aCapture Point placed!"));
					}
				}
			}
		}
	}
	public void finishCapturePointCreation(Player player) {
		ItemStack item = new ItemStack(Material.BLUE_BANNER);
		ItemMeta meta = item.getItemMeta();
		double radius = capturePointRadius.get(player.getUniqueId());
		double multiplier = capturePointMultiplier.get(player.getUniqueId());
		meta.setDisplayName(new CCT().colorize("&7Capture Point"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7Capture Radius: " + radius + " &7Blocks"));
		lore.add(new CCT().colorize("&7Experience Multiplier: " + multiplier + "%"));
		lore.add(new CCT().colorize("&7-------------------"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setDouble("CapturePointRadius", radius);
		i.setDouble("CapturePointMultiplier", multiplier);
		i.setString("CapturePointPlace", "");
		player.getInventory().setItem(capturePointSlot.get(player.getUniqueId()), i.getItem());
		capturePointSlot.remove(player.getUniqueId());
		capturePointRadius.remove(player.getUniqueId());
		capturePointMultiplier.remove(player.getUniqueId());
	}
	
	@EventHandler
	public void cancelClick(InventoryClickEvent event) {
		InventoryView inv = event.getView();
		if(inv.getTitle().contains("Examine") || inv.getTitle().contains("Info") || inv.getTitle().contains("Teleport") || inv.getTitle().contains("Punish")) {
			event.setCancelled(true);
		}
	}
	
}
