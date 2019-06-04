package me.WiebeHero.XpTrader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class XPTraderMenu implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	public void XPTraderItem(Player player) {
		Inventory i = plugin.getServer().createInventory(null, 54, (new ColorCodeTranslator().colorize("&6&lBlacksmith")));
		i.setItem(0, emptyVoid());
		i.setItem(1, emptyVoid());
		i.setItem(2, emptyVoid());
		i.setItem(3, emptyVoid());
		i.setItem(4, emptyVoid());
		i.setItem(5, emptyVoid());
		i.setItem(6, emptyVoid());
		i.setItem(7, emptyVoid());
		i.setItem(8, emptyVoid());
		i.setItem(9, emptyVoid());
		i.setItem(13, emptyVoid());
		i.setItem(17, emptyVoid());
		i.setItem(18, emptyVoid());
		i.setItem(22, emptyVoid());
		i.setItem(26, emptyVoid());
		i.setItem(27, emptyVoid());
		i.setItem(31, emptyVoid());
		i.setItem(35, emptyVoid());
		i.setItem(36, emptyVoid());
		i.setItem(40, emptyVoid());
		i.setItem(44, emptyVoid());
		i.setItem(45, emptyVoid());
		i.setItem(46, emptyVoid());
		i.setItem(47, emptyVoid());
		i.setItem(48, emptyVoid());
		i.setItem(49, emptyVoid());
		i.setItem(50, emptyVoid());
		i.setItem(51, emptyVoid());
		i.setItem(52, emptyVoid());
		i.setItem(53, acceptButton());
		player.openInventory(i);
	}
	public void XPTraderPlayer(Player player) {
		Inventory i = plugin.getServer().createInventory(null, 54, (new ColorCodeTranslator().colorize("&6&lXP Trader")));
		i.setItem(0, emptyVoid());
		i.setItem(1, emptyVoid());
		i.setItem(2, emptyVoid());
		i.setItem(3, emptyVoid());
		i.setItem(4, emptyVoid());
		i.setItem(5, emptyVoid());
		i.setItem(6, emptyVoid());
		i.setItem(7, emptyVoid());
		i.setItem(8, emptyVoid());
		i.setItem(9, emptyVoid());
		i.setItem(13, emptyVoid());
		i.setItem(17, emptyVoid());
		i.setItem(18, emptyVoid());
		i.setItem(22, emptyVoid());
		i.setItem(26, emptyVoid());
		i.setItem(27, emptyVoid());
		i.setItem(31, emptyVoid());
		i.setItem(35, emptyVoid());
		i.setItem(36, emptyVoid());
		i.setItem(40, emptyVoid());
		i.setItem(44, emptyVoid());
		i.setItem(45, emptyVoid());
		i.setItem(46, emptyVoid());
		i.setItem(47, emptyVoid());
		i.setItem(48, emptyVoid());
		i.setItem(49, emptyVoid());
		i.setItem(50, emptyVoid());
		i.setItem(51, emptyVoid());
		i.setItem(52, emptyVoid());
		i.setItem(53, acceptButton());
		player.openInventory(i);
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Activate Shop
	//--------------------------------------------------------------------------------------------------------------------
	@EventHandler
	public void xpTraderOpen(PlayerInteractEntityEvent event) {
		if(event.getPlayer() instanceof Player) {
			if(event.getRightClicked() instanceof HumanEntity) {
				Player player = event.getPlayer();
				HumanEntity shop = (HumanEntity) event.getRightClicked();
				if(shop.getCustomName() != null) {
					if(shop.getCustomName().contains(ChatColor.stripColor("Blacksmith"))) {
						XPTraderItem(player);
					}
					else if(shop.getCustomName().contains(ChatColor.stripColor("XP Trader"))) {
						XPTraderPlayer(player);
					}
				}
			}
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Prevent Clicking in the XP Trader
	//--------------------------------------------------------------------------------------------------------------------
	@EventHandler
	public void menuXPTrader(InventoryClickEvent event) {	
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		Inventory open = event.getClickedInventory();
		InventoryView view = player.getOpenInventory();
		ClickType action = event.getClick();
		if(open == null) {
			return;
		}
		String title = ChatColor.stripColor(open.getTitle());
		if(title.contains("Blacksmith") || title.contains("XP Trader")) {
			event.setCancelled(true);
			if(item.equals(null) || !item.hasItemMeta()) {
				return;
			}
			if(item != null) {
				if(action == ClickType.LEFT) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasDisplayName()) {
							String loreFinal = "";
							for(String loreRequired : item.getItemMeta().getLore()) {
								if(loreRequired.contains(ChatColor.stripColor("Common"))) {
									loreFinal = loreRequired;
								}
								else if(loreRequired.contains(ChatColor.stripColor("Rare"))) {
									loreFinal = loreRequired;
								}
								else if(loreRequired.contains(ChatColor.stripColor("Epic"))) {
									loreFinal = loreRequired;
								}
								else if(loreRequired.contains(ChatColor.stripColor("Legendary"))) {
									loreFinal = loreRequired;
								}
								else if(loreRequired.contains(ChatColor.stripColor("Mythic"))) {
									loreFinal = loreRequired;
								}
								else if(loreRequired.contains(ChatColor.stripColor("Heroic"))) {
									loreFinal = loreRequired;
								}
							}
							if(item.getItemMeta().getDisplayName().contains("Lv") || loreFinal.contains("Common") || loreFinal.contains("Rare") || loreFinal.contains("Epic") || loreFinal.contains("Legendary") || loreFinal.contains("Mythic") || loreFinal.contains("Heroic")) {
								if(item.getItemMeta().hasLore()) {
									Inventory invXPTrader = view.getTopInventory();
									Inventory invPlayer = player.getInventory();
									invPlayer.addItem(item);
									int slot = event.getSlot();
									invXPTrader.clear(slot);
									invXPTrader.clear(slot + 4);
									player.updateInventory();
								}
							}
							else if(item.getItemMeta().getDisplayName().contains("Accept?")) {
								Inventory invXPTrader = view.getTopInventory(); 
								ArrayList<ItemStack> importantSlots = new ArrayList<ItemStack>(Arrays.asList(invXPTrader.getItem(14), invXPTrader.getItem(15), invXPTrader.getItem(16), invXPTrader.getItem(23), invXPTrader.getItem(24), invXPTrader.getItem(25), invXPTrader.getItem(32), invXPTrader.getItem(33), invXPTrader.getItem(34), invXPTrader.getItem(41), invXPTrader.getItem(42), invXPTrader.getItem(43)));
								ArrayList<Integer> slotNumber = new ArrayList<Integer>(Arrays.asList(10, 11, 12, 14, 15, 16, 19, 20, 21, 23, 24, 25, 28, 29, 30, 32, 33, 34, 37, 38, 39, 41, 42, 43));
								player.sendMessage(new ColorCodeTranslator().colorize("&aTrade Accepted!"));
								for(int i = 0; i < importantSlots.size(); i++) {
									if(importantSlots.get(i) != null) {
										if(title.contains("Blacksmith")) {
											ItemStack xpPot = importantSlots.get(i);
											player.getInventory().addItem(xpPot);
										}
										else if(title.contains("XP Trader")) {
											ItemStack xpPot = importantSlots.get(i);
											player.getInventory().addItem(xpPot);
										}
									}
								}
								for(int i = 0; i < slotNumber.size(); i++) {
									int i1 = slotNumber.get(i);
									invXPTrader.remove(view.getItem(i1));
									player.updateInventory();
								}
							}
						}
					}
				}
			}
		}
		else{
			if(view.getTopInventory().getTitle().contains("Blacksmith") || view.getTopInventory().getTitle().contains("XP Trader")) {
				if(view.getBottomInventory().getType() == InventoryType.PLAYER) {
					event.setCancelled(true);
					if(action == ClickType.LEFT) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasDisplayName()) {
								if(item.getItemMeta().hasLore()) {
									String nameC = item.getItemMeta().getDisplayName();
									String name = ChatColor.stripColor(nameC);
									String loreFinal = "";
									for(String loreRequired : item.getItemMeta().getLore()) {
										if(loreRequired.contains(ChatColor.stripColor("Common"))) {
											loreFinal = loreRequired;
										}
										else if(loreRequired.contains(ChatColor.stripColor("Rare"))) {
											loreFinal = loreRequired;
										}
										else if(loreRequired.contains(ChatColor.stripColor("Epic"))) {
											loreFinal = loreRequired;
										}
										else if(loreRequired.contains(ChatColor.stripColor("Legendary"))) {
											loreFinal = loreRequired;
										}
										else if(loreRequired.contains(ChatColor.stripColor("Mythic"))) {
											loreFinal = loreRequired;
										}
										else if(loreRequired.contains(ChatColor.stripColor("Heroic"))) {
											loreFinal = loreRequired;
										}
									}
									if(loreFinal.contains("Common") || loreFinal.contains("Rare") || loreFinal.contains("Epic") || loreFinal.contains("Legendary") || loreFinal.contains("Mythic") || loreFinal.contains("Heroic")) {
										if(name.contains("Lv")) {
											String finalNameL = "";
											String levelS[] = name.split("\\[|\\]");
											finalNameL = levelS[1];
											int level = 0;
											Matcher matcher = Pattern.compile("(\\d+)").matcher(finalNameL);
											while(matcher.find()) {
											    level = Integer.parseInt(matcher.group(0));
											}
											Inventory invXPTrader = view.getTopInventory();
											Inventory invPlayer = player.getInventory();
											invPlayer.removeItem(invPlayer.getItem(event.getSlot()));
											int slot = invXPTrader.firstEmpty();
											invXPTrader.setItem(slot, item);
											String nameXPInv = view.getTitle();
											if(nameXPInv.contains("Blacksmith")) {
												if(loreFinal.contains("Common")) {
													invXPTrader.setItem(slot + 4, xpBottleItem(25 * level, item.getAmount()));
												}
												else if(loreFinal.contains("Rare")) {
													invXPTrader.setItem(slot + 4, xpBottleItem(50 * level, item.getAmount()));
												}
												else if(loreFinal.contains("Epic")) {
													invXPTrader.setItem(slot + 4, xpBottleItem(100 * level, item.getAmount()));
												}
												else if(loreFinal.contains("Legendary")) {
													invXPTrader.setItem(slot + 4, xpBottleItem(250 * level, item.getAmount()));
												}
												else if(loreFinal.contains("Mythic")) {
													invXPTrader.setItem(slot + 4, xpBottleItem(500 * level, item.getAmount()));
												}
												else if(loreFinal.contains("Heroic")) {
													invXPTrader.setItem(slot + 4, xpBottleItem(1000 * level, item.getAmount()));
												}
											}
											else {
												if(loreFinal.contains("Common")) {
													invXPTrader.setItem(slot + 4, xpBottlePlayer(25 * level, item.getAmount()));
												}
												else if(loreFinal.contains("Rare")) {
													invXPTrader.setItem(slot + 4, xpBottlePlayer(50 * level, item.getAmount()));
												}
												else if(loreFinal.contains("Epic")) {
													invXPTrader.setItem(slot + 4, xpBottlePlayer(100 * level, item.getAmount()));
												}
												else if(loreFinal.contains("Legendary")) {
													invXPTrader.setItem(slot + 4, xpBottlePlayer(250 * level, item.getAmount()));
												}
												else if(loreFinal.contains("Mythic")) {
													invXPTrader.setItem(slot + 4, xpBottlePlayer(500 * level, item.getAmount()));
												}
												else if(loreFinal.contains("Heroic")) {
													invXPTrader.setItem(slot + 4, xpBottlePlayer(1000 * level, item.getAmount()));
												}
											}
											player.updateInventory();
										}
										else {
											Inventory invXPTrader = view.getTopInventory();
											Inventory invPlayer = player.getInventory();
											invPlayer.removeItem(invPlayer.getItem(event.getSlot()));
											int slot = invXPTrader.firstEmpty();
											invXPTrader.setItem(slot, item);
											String nameXPInv = view.getTitle();
											if(nameXPInv.contains("Blacksmith")) {
												if(loreFinal.contains("Common")) {
													invXPTrader.setItem(slot + 4, xpBottleItem(25, item.getAmount()));
												}
												else if(loreFinal.contains("Rare")) {
													invXPTrader.setItem(slot + 4, xpBottleItem(50, item.getAmount()));
												}
												else if(loreFinal.contains("Epic")) {
													invXPTrader.setItem(slot + 4, xpBottleItem(100, item.getAmount()));
												}
												else if(loreFinal.contains("Legendary")) {
													invXPTrader.setItem(slot + 4, xpBottleItem(250, item.getAmount()));
												}
												else if(loreFinal.contains("Mythic")) {
													invXPTrader.setItem(slot + 4, xpBottleItem(500, item.getAmount()));
												}
												else if(loreFinal.contains("Heroic")) {
													invXPTrader.setItem(slot + 4, xpBottleItem(1000, item.getAmount()));
												}
											}
											else {
												if(loreFinal.contains("Common")) {
													invXPTrader.setItem(slot + 4, xpBottlePlayer(25, item.getAmount()));
												}
												else if(loreFinal.contains("Rare")) {
													invXPTrader.setItem(slot + 4, xpBottlePlayer(50, item.getAmount()));
												}
												else if(loreFinal.contains("Epic")) {
													invXPTrader.setItem(slot + 4, xpBottlePlayer(100, item.getAmount()));
												}
												else if(loreFinal.contains("Legendary")) {
													invXPTrader.setItem(slot + 4, xpBottlePlayer(250, item.getAmount()));
												}
												else if(loreFinal.contains("Mythic")) {
													invXPTrader.setItem(slot + 4, xpBottlePlayer(500, item.getAmount()));
												}
												else if(loreFinal.contains("Heroic")) {
													invXPTrader.setItem(slot + 4, xpBottlePlayer(1000, item.getAmount()));
												}
											}
											player.updateInventory();
										}
									}
									else {
										event.setCancelled(true);
										player.sendMessage(new ColorCodeTranslator().colorize("&cUnvalid Item."));
									}
								}
								else {
									event.setCancelled(true);
									player.sendMessage(new ColorCodeTranslator().colorize("&cUnvalid Item."));
								}
							}
							else {
								event.setCancelled(true);
								player.sendMessage(new ColorCodeTranslator().colorize("&cUnvalid Item."));
							}
						}
					}
				}
			}
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Empty
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack emptyVoid() {
		ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(" ");
		item.setItemMeta(itemmeta);
		return item;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Back Button
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack backButton() {
		ItemStack item = new ItemStack(Material.BARRIER, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new ColorCodeTranslator().colorize("&c&lBACK"));
		item.setItemMeta(itemmeta);
		return item;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//XP Pot
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack xpBottleItem(int xpAmount, int amountItem) {
		ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, amountItem);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new ColorCodeTranslator().colorize("&a&lXP Bottle (Item)"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you combine your item with this bottle,"));
		lore1.add(new ColorCodeTranslator().colorize("&7It will add the XP on this bottle to your weapon."));
		lore1.add(new ColorCodeTranslator().colorize("&6XP Amount: " + xpAmount));
		itemmeta.setLore(lore1);
		item.setItemMeta(itemmeta);
		return item;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//XP Pot
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack xpBottlePlayer(int xpAmount, int amountItem) {
		ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, amountItem);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new ColorCodeTranslator().colorize("&a&lXP Bottle (Player)"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7When you combine your item with this bottle,"));
		lore1.add(new ColorCodeTranslator().colorize("&7It will add the XP on this bottle to your weapon."));
		lore1.add(new ColorCodeTranslator().colorize("&6XP Amount: " + xpAmount));
		itemmeta.setLore(lore1);
		item.setItemMeta(itemmeta);
		return item;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Accept Trade Button
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack acceptButton() {
		ItemStack item = new ItemStack(Material.LIME_DYE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new ColorCodeTranslator().colorize("&a&lAccept?"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7Click this to accept the trade."));
		itemmeta.setLore(lore1);
		item.setItemMeta(itemmeta);
		return item;
	}
	@EventHandler
	public void closeXPTrader(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		InventoryView inv = event.getView();
		String title = ChatColor.stripColor(inv.getTitle());
		if(title.equals("Blacksmith") || title.equals("XP Trader")) {
			InventoryView invXPTrader = inv;
			ArrayList<ItemStack> importantSlots = new ArrayList<ItemStack>(Arrays.asList(invXPTrader.getItem(10), invXPTrader.getItem(11), invXPTrader.getItem(12), invXPTrader.getItem(19), invXPTrader.getItem(20), invXPTrader.getItem(21), invXPTrader.getItem(28), invXPTrader.getItem(29), invXPTrader.getItem(30), invXPTrader.getItem(37), invXPTrader.getItem(38), invXPTrader.getItem(39)));
			for(int i = 0; i < importantSlots.size(); i++) {
				if(importantSlots.get(i) != null) {
					ItemStack items = importantSlots.get(i);
					player.getInventory().addItem(items);
				}
			}
		}
	}
	@EventHandler
	public void throwXPPot(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = event.getPlayer();
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().getType() == Material.EXPERIENCE_BOTTLE) {
					event.setCancelled(true);
				}
			}
		}
	}
}
