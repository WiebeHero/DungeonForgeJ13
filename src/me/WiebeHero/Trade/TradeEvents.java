package me.WiebeHero.Trade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCloseEvent.Reason;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.OtherClasses.SignMenuFactory;

public class TradeEvents implements Listener{
	
	private TradeMenu menu;
	private HashMap<Pair<UUID, UUID>, Inventory> trading;
	private ArrayList<Pair<UUID, UUID>> doing;
	private ArrayList<UUID> exceptions;
	private SignMenuFactory sFactory;
	private ItemStackBuilder builder;
	private DFPlayerManager dfManager;
	
	public TradeEvents(TradeMenu menu, SignMenuFactory sFactory, ItemStackBuilder builder, DFPlayerManager dfManager) {
		this.menu = menu;
		this.trading = new HashMap<Pair<UUID, UUID>, Inventory>();
		this.exceptions = new ArrayList<UUID>();
		this.sFactory = sFactory;
		this.builder = builder;
		this.dfManager = dfManager;
	}
	
	@EventHandler
	public void tradeClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		UUID uuid = player.getUniqueId();
		Inventory inv = event.getClickedInventory();
		InventoryView view = player.getOpenInventory();
		ClickType click = event.getClick();
		if(view.getTitle().contains("trading with")) {
			event.setCancelled(true);
			Pair<UUID, UUID> p1 = null;
			for(Pair<UUID, UUID> p : this.trading.keySet()) {
				if(p.getKey().equals(uuid) || p.getValue().equals(uuid)) {
					p1 = p;
				}
			}
			final Pair<UUID, UUID> pair = p1;
			if(pair != null) {
				if(click == ClickType.LEFT) {
					if(inv != null) {
						InventoryView otherView = null;
						if(pair.getKey().equals(uuid)) {
							Player p = Bukkit.getPlayer(pair.getValue());
							otherView = p.getOpenInventory();
						}
						else if(pair.getValue().equals(uuid)) {
							Player p = Bukkit.getPlayer(pair.getKey());
							otherView = p.getOpenInventory();
						}
						if(inv.getType() == InventoryType.PLAYER) {
							if(event.getCurrentItem() != null) {
								ArrayList<Integer> slots = null;
								if(pair.getKey().equals(uuid)) {
									slots = this.menu.getYourSlots();
								}
								else if(pair.getValue().equals(uuid)) {
									slots = this.menu.getTheirSlots();
								}
								Inventory top1 = view.getTopInventory();
								Inventory top2 = otherView.getTopInventory();
								int empty = -1;
								for(int slot : slots) {
									if(top1.getItem(slot) == null || top1.getItem(slot).getType() == Material.AIR) {
										empty = slot;
										break;
									}
								}
								if(empty != -1) {
									ItemStack clicked = event.getCurrentItem().clone();
									top1.setItem(empty, clicked);
									if(!exceptions.contains(uuid)) {
										top2.setItem(empty, clicked);
									}
									event.setCurrentItem(null);
									player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 1.25F);
									this.trading.put(pair, top1);
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYour trading inventory is full!"));
								}
							}
						}
						else if(inv.getType() == InventoryType.CHEST) {
							if(event.getCurrentItem() != null) {
								NBTItem item = new NBTItem(event.getCurrentItem());
								Inventory top1 = view.getTopInventory();
								Inventory top2 = otherView.getTopInventory();
								if(item.hasKey("Trade Start")) {
									if(pair.getKey().equals(uuid)) {
										ItemStack ready = this.builder.constructItem(
												Material.LIME_DYE,
												1,
												"&7Status: &aReady",
												new ArrayList<String>(),
												new Pair<String, String>("Status", "Ready")
										);
										top1.setItem(46, ready);
										if(!exceptions.contains(pair.getValue())) {
											top2.setItem(46, ready);
										}
									}
									else if(pair.getValue().equals(uuid)) {
										ItemStack ready = this.builder.constructItem(
												Material.LIME_DYE,
												1,
												"&7Status: &aReady",
												new ArrayList<String>(),
												new Pair<String, String>("Status", "Ready")
										);
										top1.setItem(52, ready);
										if(!exceptions.contains(pair.getKey())) {
											top2.setItem(52, ready);
										}
									}
									ItemStack r1 = top1.getItem(46);
									ItemStack r2 = top1.getItem(52);
									NBTItem ready1 = new NBTItem(r1);
									NBTItem ready2 = new NBTItem(r2);
									this.trading.put(pair, top1);
									if(!this.doing.contains(pair)) {
										if(ready1.hasKey("Status") && ready2.hasKey("Status")) {
											if(ready1.getString("Status").equals("Ready") && ready2.getString("Status").equals("Ready")) {
												this.doing.add(pair);
												new BukkitRunnable() {
													int seconds = 6;
													public void run() {
														seconds--;
														if(trading.containsKey(pair)) {
															if(seconds != 0) {
																ItemStack nothing = builder.constructItem(
																		Material.ORANGE_STAINED_GLASS_PANE,
																		seconds,
																		"&6" + seconds,
																		new ArrayList<String>()
																);
																top1.setItem(4, nothing);
																top1.setItem(13, nothing);
																top1.setItem(22, nothing);
																top1.setItem(31, nothing);
																top2.setItem(4, nothing);
																top2.setItem(13, nothing);
																top2.setItem(22, nothing);
																top2.setItem(31, nothing);
																Player pl1 = Bukkit.getPlayer(pair.getKey());
																Player pl2 = Bukkit.getPlayer(pair.getValue());
																pl1.playSound(pl1.getLocation(), Sound.UI_BUTTON_CLICK, 2.0F, 1F);
																pl2.playSound(pl2.getLocation(), Sound.UI_BUTTON_CLICK, 2.0F, 1F);
															}
															else {
																if(doing.contains(pair)) {
																	doing.remove(pair);
																}
																trading.remove(pair);
																ArrayList<ItemStack> listForYou = new ArrayList<ItemStack>();
																ArrayList<ItemStack> listForThem = new ArrayList<ItemStack>();
																ArrayList<Integer> slotsForYou = menu.getTheirSlots();
																ArrayList<Integer> slotsForThem = menu.getYourSlots();
																for(int i = 0; i < slotsForYou.size(); i++) {
																	listForYou.add(top1.getItem(slotsForYou.get(i)));
																}
																for(int i = 0; i < slotsForYou.size(); i++) {
																	listForThem.add(top1.getItem(slotsForThem.get(i)));
																}
																Player pl1 = Bukkit.getPlayer(pair.getKey());
																Player pl2 = Bukkit.getPlayer(pair.getValue());
																Inventory inv1 = pl1.getInventory();
																Inventory inv2 = pl2.getInventory();
																for(ItemStack stack : listForYou) {
																	if(stack != null) {
																		if(inv1.firstEmpty() != -1) {
																			inv1.setItem(inv1.firstEmpty(), stack);
																		}
																		else {
																			pl1.getWorld().dropItem(pl1.getLocation(), stack);
																		}
																	}
																}
																for(ItemStack stack : listForThem) {
																	if(stack != null) {
																		if(inv2.firstEmpty() != -1) {
																			inv2.setItem(inv2.firstEmpty(), stack);
																		}
																		else {
																			pl2.getWorld().dropItem(pl2.getLocation(), stack);
																		}
																	}
																}
																DFPlayer dfPlayer1 = dfManager.getEntity(pair.getKey());
																DFPlayer dfPlayer2 = dfManager.getEntity(pair.getValue());
																NBTItem moneyItemMe = new NBTItem(top1.getItem(27));
																NBTItem moneyItemThem = new NBTItem(top1.getItem(35));
																dfPlayer1.removeMoney(moneyItemMe.getDouble("Money"));
																dfPlayer2.removeMoney(moneyItemThem.getDouble("Money"));
																dfPlayer1.addMoney(moneyItemThem.getDouble("Money"));
																dfPlayer2.addMoney(moneyItemMe.getDouble("Money"));
																trading.remove(pair);
																pl1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTrade accepted!"));
																pl2.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aTrade accepted!"));
																cancel();
															}
														}
														else {
															if(doing.contains(pair)) {
																doing.remove(pair);
															}
															cancel();
														}
													}
												}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
											}
										}
									}
								}
								else {
									ArrayList<Integer> slots = null;
									if(pair.getKey().equals(uuid)) {
										slots = this.menu.getYourSlots();
									}
									else if(pair.getValue().equals(uuid)) {
										slots = this.menu.getTheirSlots();
									}
									ItemStack r1 = top1.getItem(46);
									ItemStack r2 = top1.getItem(52);
									NBTItem ready1 = new NBTItem(r1);
									NBTItem ready2 = new NBTItem(r2);
									if(ready1.hasKey("Status") && ready2.hasKey("Status")) {
										if(!ready1.getString("Status").equals("Ready") && !ready2.getString("Status").equals("Ready")) {
											if(slots.contains(event.getSlot())) {
												int first = player.getInventory().firstEmpty();
												if(first != -1) {
													ItemStack clicked = event.getCurrentItem().clone();
													event.setCurrentItem(null);
													player.getInventory().setItem(first, clicked);
												}
												else {
													ItemStack clicked = event.getCurrentItem().clone();
													event.setCurrentItem(null);
													player.getWorld().dropItem(player.getLocation(), clicked);
												}
												if(!this.exceptions.contains(uuid)) {
													top2.setItem(event.getSlot(), new ItemStack(Material.AIR));
												}
												player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 0.75F);
												this.trading.put(pair, top1);
											}
											else {
												if(event.getCurrentItem().getType() == Material.SIGN) {
													player.closeInventory();
													this.exceptions.add(uuid);
													Inventory repInv = this.trading.get(pair);
													sFactory
													.newMenu(new ArrayList<String>(Arrays.asList("&7----------------", "", "&a^^^^^^^^^^^^^^", "&7Type money here")))
													.reopenIfFail()
													.response((writer, lines) -> {
														double money = 0.00;
														try {
															money = Double.parseDouble(lines[1]);
														}
														catch(NumberFormatException ex) {
															writer.sendMessage("Failed!");
														}
														if(money >= 0.00) {
															DFPlayer dfPlayer = dfManager.getEntity(writer);
															if(dfPlayer.getMoney() >= money) {
																if(pair.getKey().equals(uuid)) {
																	ItemStack newMoney = this.builder.constructItem(
																			money > 0.00 ? Material.GOLD_BLOCK : Material.BARRIER,
																			1,
																			"&aMoney: " + money + "$",
																			new ArrayList<String>(),
																			new Pair<String, Double>("Money", money)
																	);
																	repInv.setItem(27, newMoney);
																	new BukkitRunnable() {
																		public void run() {
																			player.openInventory(repInv);
																			if(!exceptions.contains(pair.getValue())) {
																				top2.setItem(27, newMoney);
																			}
																		}
																	}.runTaskLater(CustomEnchantments.getInstance(), 2L);
																	player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 2.0F, 1.0F);
																	this.trading.put(pair, repInv);
																}
																else if(pair.getValue().equals(uuid)) {
																	ItemStack newMoney = this.builder.constructItem(
																			money > 0.00 ? Material.GOLD_BLOCK : Material.BARRIER,
																			1,
																			"&aMoney: " + money + "$",
																			new ArrayList<String>(),
																			new Pair<String, Double>("Money", money)
																	);
																	repInv.setItem(35, newMoney);
																	new BukkitRunnable() {
																		public void run() {
																			player.openInventory(inv);
																			if(!exceptions.contains(pair.getKey())) {
																				top2.setItem(35, newMoney);
																			}
																		}
																	}.runTaskLater(CustomEnchantments.getInstance(), 2L);
																	player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 2.0F, 1.0F);
																	this.trading.put(pair, repInv);
																}
															}
															else {
																new BukkitRunnable() {
																	public void run() {
																		player.openInventory(repInv);
																	}
																}.runTaskLater(CustomEnchantments.getInstance(), 2L);
																writer.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough money to put in the trade!"));
																player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
															}
															this.exceptions.remove(writer.getUniqueId());
															return true;
														}
														player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
														return false;
													})
													.open(player);
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
	}
	
	@EventHandler
	public void cancelTrade(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		UUID uuid = player.getUniqueId();
		Reason reason = event.getReason();
		Inventory inv = event.getInventory();
		Pair<UUID, UUID> pair = null;
		for(Pair<UUID, UUID> p : this.trading.keySet()) {
			if(p.getKey().equals(uuid) || p.getValue().equals(uuid)) {
				pair = p;
			}
		}
		if(pair != null) {
			if(reason == Reason.PLAYER) {
				this.trading.remove(pair);
				if(this.doing.contains(pair)) {
					this.doing.remove(pair);
				}
				Player p1 = Bukkit.getPlayer(pair.getKey());
				ArrayList<Integer> mySlots = this.menu.getYourSlots();
				if(p1 != null) {
					for(int i = 0; i < mySlots.size(); i++) {
						ItemStack item = inv.getItem(mySlots.get(i));
						if(item != null) {
							int first = p1.getInventory().firstEmpty();
							if(first != -1) {
								p1.getInventory().setItem(first, item);
							}
							else {
								p1.getWorld().dropItem(p1.getLocation(), item);
							}
						}
					}
					p1.closeInventory();
					p1.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe trade has been cancelled!"));
					p1.playSound(p1.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
				}
				Player p2 = Bukkit.getPlayer(pair.getValue());
				ArrayList<Integer> theirSlots = this.menu.getTheirSlots();
				if(p2 != null) {
					for(int i = 0; i < theirSlots.size(); i++) {
						ItemStack item = inv.getItem(theirSlots.get(i));
						if(item != null) {
							int first = p2.getInventory().firstEmpty();
							if(first != -1) {
								p2.getInventory().setItem(first, item);
							}
							else {
								p2.getWorld().dropItem(p2.getLocation(), item);
							}
						}
					}
					p2.closeInventory();
					p2.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThe trade has been cancelled!"));
					p2.playSound(p2.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
				}
			}
		}
	}
	
	public void addTrading(UUID me, UUID them) {
		Player p = Bukkit.getPlayer(me);
		this.trading.put(new Pair<UUID, UUID>(me, them), p.getOpenInventory().getTopInventory());
	}
	
}
