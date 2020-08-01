package me.WiebeHero.RankedPlayerPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.CapturePoints.CapturePoint;
import me.WiebeHero.CapturePoints.CapturePointManager;
import me.WiebeHero.Chat.MSG;
import me.WiebeHero.Chat.MSGManager;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.CustomMethods.MethodLuck;
import me.WiebeHero.CustomMethods.PotionM;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.DFShops.ShopItem;
import me.WiebeHero.DFShops.ShopItemManager;
import me.WiebeHero.DFShops.ShopMenu;
import me.WiebeHero.Factions.DFFactionPlayer;
import me.WiebeHero.Factions.DFFactionPlayerManager;
import me.WiebeHero.MoreStuff.CombatTag;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;
import me.WiebeHero.Scoreboard.DFScoreboard;
import me.WiebeHero.Scoreboard.WGMethods;
import me.WiebeHero.XpTrader.XPTraderMenu;
import net.luckperms.api.model.user.User;

public class RankedCommands implements CommandExecutor{
	
	private RankedManager rManager;
	private WGMethods wg;
	private XPTraderMenu xpTrader;
	private MSGManager msgManager;
	private ShopItemManager shopItemManager;
	private ShopMenu shopMenu;
	private PotionM pApi;
	private DFScoreboard board;
	private DFPlayerManager dfManager;
	private CapturePointManager cpManager;
	private DFFactionPlayerManager facPlayerManager;
	private ItemStackBuilder builder;
	private MethodLuck lp;
	private ArrayList<UUID> feedCooldown;
	private ArrayList<UUID> sellHandCooldown;
	private ArrayList<UUID> sellAllCooldown;
	private ArrayList<UUID> shopCooldown;
	private ArrayList<UUID> teleporting;
	private HashMap<Double, ItemStack> giftList;
	
	public String dchat = "dchat";
	public String xptrader = "xptrader";
	public String workbench = "workbench";
	public String pv = "personalvault";
	public String near = "near";
	public String enderchest = "enderchest";
	public String playershop = "playershop";
	public String playertime = "playertime";
	public String playerbright = "playerbright";
	public String sellhand = "sellhand";
	public String playerweather = "playerweather";
	public String playerfeed = "playerfeed";
	public String teleportcapturepoint = "teleportcapturepoint";
	public String sellall = "sellall";
	public String giftall = "giftall";
	public String playerfly = "playerfly";
	public String freerank = "freerank";
	
	public RankedCommands(RankedManager rManager, WGMethods wg, XPTraderMenu xpTrader, MSGManager msgManager, ShopItemManager shopItemManager, ShopMenu shopMenu, PotionM pApi, DFScoreboard board, DFPlayerManager dfManager, CapturePointManager cpManager, DFFactionPlayerManager facPlayerManager, ItemStackBuilder builder, MethodLuck lp) {
		this.rManager = rManager;
		this.xpTrader = xpTrader;
		this.wg = wg;
		this.msgManager = msgManager;
		this.shopItemManager = shopItemManager;
		this.pApi = pApi;
		this.shopMenu = shopMenu;
		this.board = board;
		this.dfManager = dfManager;
		this.cpManager = cpManager;
		this.facPlayerManager = facPlayerManager;
		this.builder = builder;
		this.lp = lp;
		this.feedCooldown = new ArrayList<UUID>();
		this.sellHandCooldown = new ArrayList<UUID>();
		this.sellAllCooldown = new ArrayList<UUID>();
		this.shopCooldown = new ArrayList<UUID>();
		this.teleporting = new ArrayList<UUID>();
		this.giftList = new HashMap<Double, ItemStack>();
		this.giftList.put(60.0, this.builder.constructItem(
				Material.NETHER_STAR,
				1,
				"&7Common Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &7Common"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Common")
		));
		this.giftList.put(20.0, this.builder.constructItem(
				Material.NETHER_STAR,
				1,
				"&aRare Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &aRare"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Rare")
		));
		this.giftList.put(10.0, this.builder.constructItem(
				Material.NETHER_STAR,
				1,
				"&bEpic Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &bEpic"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Epic")
		));
		this.giftList.put(5.0, this.builder.constructItem(
				Material.NETHER_STAR,
				1,
				"&cLegendary Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &cLegendary"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Legendary")
		));
		this.giftList.put(3.5, this.builder.constructItem(
				Material.NETHER_STAR,
				1,
				"&5Mythic Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &5Mythic"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Mythic")
		));
		this.giftList.put(1.5, this.builder.constructItem(
				Material.NETHER_STAR,
				1,
				"&eHeroic Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &eHeroic"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Heroic")
		));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
			if(cmd.getName().equalsIgnoreCase(dchat)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length == 0) {
						Rank rank = rPlayer.getPaidRank();
						if(rank != null && rank.getRank() >= Rank.BRONZE.getRank()) {
							if(msgManager.contains(player.getUniqueId())) {
								MSG msg = this.msgManager.get(player.getUniqueId());
								if(msg.getDonatorChat()) {
									msg.setDonatorChat(false);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have disabled the donator chat!"));
								}
								else {
									msg.setDonatorChat(true);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have enabled the donator chat!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cSomething went wrong, please contact administrators."));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Bronze &crank to use this command!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /dchat"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(xptrader)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length == 0) {
						Rank rank = rPlayer.getPaidRank();
						if(rank != null && rank.getRank() >= Rank.BRONZE.getRank()) {
							if(!this.wg.isInZone(player, "warzone")) {
								this.xpTrader.XPTraderPlayer(player);
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command in the warzone!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Bronze &crank to use this command!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /xptrader"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(workbench)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length == 0) {
						Rank rank = rPlayer.getPaidRank();
						if(rank != null && rank.getRank() >= Rank.BRONZE.getRank()) {
							Inventory i = CustomEnchantments.getInstance().getServer().createInventory(player, InventoryType.WORKBENCH, new CCT().colorize("&fPersonal Workbench"));
							player.openInventory(i);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Bronze &crank to use this command!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /workbench"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(pv)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length == 0) {
						Rank rank = rPlayer.getPaidRank();
						if(rank != null && rank.getRank() >= Rank.BRONZE.getRank()) {
							player.openInventory(rPlayer.getPV());
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Bronze &crank to use this command!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /personalvault or /pv"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(near)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length == 0) {
						Rank rank = rPlayer.getPaidRank();
						if(rank != null && rank.getRank() >= Rank.SILVER.getRank()) {
							int count = 0;
							ArrayList<Entity> ents = new ArrayList<Entity>(player.getNearbyEntities(150.0, 150.0, 150.0));
							ArrayList<Player> players = new ArrayList<Player>();
							if(!ents.isEmpty()) {
								for(Entity ent : ents) {
									if(ent instanceof Player) {
										players.add(player);
									}
								}
							}
							if(!player.isEmpty()) {
								for(Player p : players) {
									if(player.getLocation().distance(p.getLocation()) <= 150.0 && count != 5 && p != player) {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aPlayer: &6" + p.getName() + " &aDistance: &6" + player.getLocation().distance(p.getLocation())));
										count++;
									}
									else {
										break;
									}
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cNo players are near you!"));
								count++;
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Silver &crank to use this command!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /near"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(enderchest)) {
				if(CombatTag.getCombatTag().get(player.getUniqueId()) == 0) {
					if(args.length == 0) {
						Rank rank = rPlayer.getPaidRank();
						if(rank != null && rank.getRank() >= Rank.SILVER.getRank()) {
							player.openInventory(player.getEnderChest());
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Silver &crank to use this command!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /enderchest or /ec"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't use this command! You are in combat!"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(playershop)) {
				if(args.length == 0) {
					Rank rank = rPlayer.getPaidRank();
					if(rank != null && rank.getRank() >= Rank.SILVER.getRank()) {
						if(!this.shopCooldown.contains(player.getUniqueId())) {
							this.shopMenu.ShopOverview(player);
							this.shopCooldown.add(player.getUniqueId());
							new BukkitRunnable() {
								public void run() {
									if(shopCooldown.contains(player.getUniqueId())) {
										shopCooldown.remove(player.getUniqueId());
									}
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 1200L);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe shop command is on cooldown!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Silver &crank to use this command!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /playershop or /pshop"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(playertime)) {
				if(args.length == 1) {
					Rank rank = rPlayer.getPaidRank();
					if(rank != null && rank.getRank() >= Rank.SILVER.getRank()) {
						long time = -1L;
						try {
							time = Long.parseLong(args[0]);
						}
						catch(NumberFormatException ex) {
							
						}
						if(args[0].equalsIgnoreCase("reset")) {
							player.setPlayerTime(0L, true);
						}
						else if(time >= 0L && time <= 24000L) {
							player.setPlayerTime(time, false);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis is not a valid time!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Silver &crank to use this command!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /playertime (Time or 'Reset') or /ptime (Time or 'Reset')"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(playerbright)) {
				if(args.length == 0) {
					Rank rank = rPlayer.getPaidRank();
					if(rank != null && rank.getRank() >= Rank.GOLD.getRank()) {
						if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
							player.removePotionEffect(PotionEffectType.NIGHT_VISION);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cTurned off bright mode!"));
						}
						else {
							this.pApi.applyEffect(player, PotionEffectType.NIGHT_VISION, 0, 999999999);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTurned on bright mode!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Gold &crank to use this command!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /playerbright or /pbright"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(sellhand)) {
				if(args.length == 0) {
					Rank rank = rPlayer.getPaidRank();
					if(rank != null && rank.getRank() >= Rank.GOLD.getRank()) {
						ItemStack stack = player.getInventory().getItemInMainHand();
						if(stack != null) {
							if(!this.sellHandCooldown.contains(player.getUniqueId())) {
								ItemStack sPlace = this.shopItemManager.getItemByType(stack.getType());
								if(sPlace != null) {
									NBTItem i = new NBTItem(sPlace);
									ShopItem sItem = i.getObject("ShopItem", ShopItem.class);
									double sell = sItem.getSellPrice();
									if(sell > 0.00) {
										int amount = sItem.getAmount();
										double per = sell / amount;
										int amountSell = 0;
				  						if(!stack.getItemMeta().hasDisplayName() && !stack.getItemMeta().hasLore()) {
					  						amountSell = amountSell + stack.getAmount();
				  						}
				  						double complete = per * amountSell;
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have sold " + stack.getAmount() + " " + sPlace.getItemMeta().getDisplayName() + "&a" + " for " +  complete + "$!"));
					  					player.getInventory().removeItem(new ItemStack(stack.getType(), amountSell));
					  					DFPlayer dfPlayer = this.dfManager.getEntity(player);
					  					dfPlayer.addMoney(complete);
					  					this.sellHandCooldown.add(player.getUniqueId());
										new BukkitRunnable() {
											public void run() {
												if(sellHandCooldown.contains(player.getUniqueId())) {
													sellHandCooldown.remove(player.getUniqueId());
												}
											}
										}.runTaskLater(CustomEnchantments.getInstance(), 60L);
					  					board.updateScoreboard(player);
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't sell this item!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't sell this item!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe sell hand command is on cooldown!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have anything in your hand to sell!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Gold &crank to use this command!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /sellhand"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(playerweather)) {
				if(args.length == 1) {
					Rank rank = rPlayer.getPaidRank();
					if(rank != null && rank.getRank() >= Rank.GOLD.getRank()) {
						WeatherType type = null;
						try {
							type = WeatherType.valueOf(args[0].toUpperCase());
							player.setPlayerWeather(type);
						}
						catch(IllegalArgumentException ex) {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid weather type! Choose from clear or downfall!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Gold &crank to use this command!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /playerweather (Time or 'Reset') or /pweather (Time or 'Reset')"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(playerfeed)) {
				if(args.length == 0) {
					Rank rank = rPlayer.getPaidRank();
					if(rank != null && rank.getRank() >= Rank.PLATINUM.getRank()) {
						if(!this.feedCooldown.contains(player.getUniqueId())) {
							player.setFoodLevel(20);
							this.feedCooldown.add(player.getUniqueId());
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have used the feed command!"));
							new BukkitRunnable() {
								public void run() {
									if(feedCooldown.contains(player.getUniqueId())) {
										feedCooldown.remove(player.getUniqueId());
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou can use the feed command again!"));
									}
								}
							}.runTaskLater(CustomEnchantments.getInstance(), 600L);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis command is on cooldown!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Platinum &crank to use this command!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /playerfeed or /pfeed"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(teleportcapturepoint)) {
				if(args.length == 1) {
					Rank rank = rPlayer.getPaidRank();
					if(rank != null && rank.getRank() >= Rank.PLATINUM.getRank()) {
						int number = -1;
						try {
							number = Integer.parseInt(args[0]) - 1;
						}
						catch(NumberFormatException ex) {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis number is invalid!"));
						}
						ArrayList<CapturePoint> points = new ArrayList<CapturePoint>(this.cpManager.getCapturePointList().values());
						Location loc = player.getLocation().clone();
						if(number > -1 && number < points.size()) {
							CapturePoint point = points.get(number);
							DFFactionPlayer facPlayer = this.facPlayerManager.getFactionPlayer(player.getUniqueId());
							this.teleporting.add(player.getUniqueId());
							new BukkitRunnable() {
								int count = 200;
								int temp = 0;
								@Override
								public void run() {
									if(loc.getWorld().getName().equals(player.getWorld().getName())) {
										if(loc.distance(player.getLocation()) <= 0.1) {
											if(facPlayer.getFactionId() != null) {
												if(point.getCapturedId() != null && point.getCapturedId().equals(facPlayer.getFactionId()) && point.getCaptureProgress() == 10) {
													if(temp == 0) {
														temp = temp - 20;
														if(count / 20 != 0) {
															player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aSending you to a capture point in " + (count / 20) + "..."));
														}
													}
													if(count == 0) {
														player.teleport(point.getCaptureLocation());
														player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTeleported!"));
														count = 10;
														teleporting.remove(player.getUniqueId());
														cancel();
													}
												}
												else {
													player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have this capture point fully captured!"));
													teleporting.remove(player.getUniqueId());
													cancel();
												}
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou are not in a faction!"));
												teleporting.remove(player.getUniqueId());
												cancel();
											}
											
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCancelled teleporting because of you moving."));
											teleporting.remove(player.getUniqueId());
											cancel();
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cCancelled teleporting because of you moving."));
										teleporting.remove(player.getUniqueId());
										cancel();
									}
									count--;
									temp++;
								}	
							}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis number is invalid!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Platinum &crank to use this command!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /teleportcapturepoint (Number) or /tpcp (Number)"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(sellall)) {
				if(args.length == 0) {
					Rank rank = rPlayer.getPaidRank();
					if(rank != null && rank.getRank() >= Rank.DIAMOND.getRank()) {
						if(!this.sellAllCooldown.contains(player.getUniqueId())) {
							double totalSold = 0.00;
							for(ItemStack sellItem : player.getInventory().getContents()) {
			  					if(sellItem != null) {
			  						for(ItemStack shopItem : this.shopItemManager.getAllShopItems()) {
			  							NBTItem i = new NBTItem(shopItem);
			  							ShopItem shop = i.getObject("ShopItem", ShopItem.class);
			  							if(shop.getSellPrice() > 0.00) {
					  						if(sellItem.getType() == shopItem.getType()) {
						  						if(!sellItem.getItemMeta().hasDisplayName() && !sellItem.getItemMeta().hasLore()) {
						  							double per = shop.getSellPrice() / shop.getAmount();
						  							totalSold += per * sellItem.getAmount();
							  						player.getInventory().removeItem(new ItemStack(sellItem.getType(), sellItem.getAmount()));
						  						}
					  						}
					  					}
			  						}
			  					}
			  				}
							if(totalSold >= 0.00) {
								this.sellAllCooldown.add(player.getUniqueId());
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have used the sellall command! You have earned " + totalSold + "$"));
								new BukkitRunnable() {
									public void run() {
										if(sellAllCooldown.contains(player.getUniqueId())) {
											sellAllCooldown.remove(player.getUniqueId());
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou can use the sellall command again!"));
										}
									}
								}.runTaskLater(CustomEnchantments.getInstance(), 6000L);
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cNo items to be sold!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis command is on cooldown!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Diamond &crank to use this command!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /sellall"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(giftall)) {
				if(args.length == 0) {
					Rank rank = rPlayer.getPaidRank();
					if(rank != null && rank.getRank() >= Rank.DIAMOND.getRank()) {
						if(System.currentTimeMillis() >= rPlayer.getGiftCooldown()) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								float lastChance = 0.00F;
								float currentChance = 0.00F;
								float rand = ThreadLocalRandom.current().nextFloat() * 100;
								for(Entry<Double, ItemStack> entry : this.giftList.entrySet()) {
									currentChance += entry.getKey();
									if(rand < currentChance && rand >= lastChance) {
										ItemStack stack = entry.getValue();
										p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ajust gifted you " + stack.getItemMeta().getDisplayName() + "!"));
										if(p.getInventory().firstEmpty() != -1) {
											p.getInventory().addItem(stack);
										}
										else {
											p.getWorld().dropItemNaturally(p.getLocation(), stack);
										}
										break;
									}
									lastChance += entry.getKey();
								}
							}
							rPlayer.setGiftCooldown(System.currentTimeMillis() + 259200000L);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have used the giftall command!"));
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis command is on cooldown!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Diamond &crank to use this command!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /giftall"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(playerfly)) {
				if(args.length == 0) {
					Rank rank = rPlayer.getPaidRank();
					if(rank != null && rank.getRank() >= Rank.EMERALD.getRank()) {
						if(wg.isInZone(player, "spawn")) {
							rPlayer.setFlight(true);
							player.setAllowFlight(true);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTurned on player fly!"));
						}
						else {
							rPlayer.setFlight(false);
							player.setAllowFlight(false);
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can only use player fly in spawn!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Emerald &crank to use this command!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /giftall"));
				}
			}
			else if(cmd.getName().equalsIgnoreCase(freerank)) {
				if(args.length == 1) {
					Rank rank = rPlayer.getPaidRank();
					if(rank != null && rank.getRank() >= Rank.EMERALD.getRank()) {
						Player p = Bukkit.getPlayer(args[0]);
						if(p != null) {
							RankedPlayer other = this.rManager.getRankedPlayer(p.getUniqueId());
							if(other.getPaidRank() == null) {
								other.addRank(Rank.BRONZE);
								User user = lp.loadUser(p.getUniqueId());
								lp.addParent(user, "bronze");
								rPlayer.setFreeRankCooldown(System.currentTimeMillis() + 2592000000L);
								other.setBronzeTime(System.currentTimeMillis() + 345600000L);
								long endTime = (other.getBronzeTime() - System.currentTimeMillis()) / 50;
								UUID id = p.getUniqueId();
								BukkitTask task = new BukkitRunnable() {
									public void run() {
										User newUser = lp.loadUser(id);
										if(lp.containsParrent(newUser, "bronze")) {
											lp.removeParent(newUser, "broze");
											rPlayer.setBronzeTime(0L);
											Player p = Bukkit.getPlayer(id);
											if(p != null) {
												p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour free &6Bronze &crank rank out! If you want to have this rank permanently. Visit: dungeonforge.eu/shop/"));
											}
										}
									}
								}.runTaskLater(CustomEnchantments.getInstance(), endTime);
								other.setTask(task);
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have gifted &6" + p.getName() + " &aa free &6Bronze &arank for 4 days!"));
								p.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &6" + player.getName() + " &ahas gifted a bronze rank to you for 4 days! Show them some love <3"));
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player already has a rank! He can not be given one!"));
							}
						}
						else {
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThis player needs to be online to perform this command!"));
						}
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou atleast need the &6Emerald &crank to use this command!"));
					}
				}
				else {
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cInvalid arguments! Ussage: /freerank (Player Name)"));
				}
			}
		}
		return false;
	}
}
