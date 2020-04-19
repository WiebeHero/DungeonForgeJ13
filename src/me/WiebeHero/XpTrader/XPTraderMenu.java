package me.WiebeHero.XpTrader;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;

public class XPTraderMenu implements Listener{
	public void XPTraderPlayer(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&6&lXP Trader")));
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
					if(shop.getCustomName().contains(ChatColor.stripColor("XP Trader"))) {
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
		String title = ChatColor.stripColor(view.getTitle());
		if(title.contains("XP Trader")) {
			event.setCancelled(true);
			if(item.equals(null) || !item.hasItemMeta()) {
				return;
			}
			if(item != null) {
				if(open.getType() == InventoryType.CHEST) {
					if(action == ClickType.LEFT) {
						int level = 1;
						int rarity = 0;
						NBTItem it = new NBTItem(item);
						if(it.hasKey("Level")) {
							level = it.getInteger("Level");
						}
						if(it.hasKey("Rarity")) {
							if(it.getString("Rarity").equals("&7Common")) {
								rarity = 75;
							}
							if(it.getString("Rarity").equals("&aRare")) {
								rarity = 150;
							}
							if(it.getString("Rarity").equals("&bEpic")) {
								rarity = 300;
							}
							if(it.getString("Rarity").equals("&cLegendary")) {
								rarity = 600;
							}
							if(it.getString("Rarity").equals("&5Mythic")) {
								rarity = 1000;
							}
							if(it.getString("Rarity").equals("&eHeroic")) {
								rarity = 1500;
							}
						}
						if(item.getItemMeta().getDisplayName().contains("Accept?")) {
							Inventory invXPTrader = view.getTopInventory(); 
							ArrayList<ItemStack> importantSlots = new ArrayList<ItemStack>(Arrays.asList(invXPTrader.getItem(14), invXPTrader.getItem(15), invXPTrader.getItem(16), invXPTrader.getItem(23), invXPTrader.getItem(24), invXPTrader.getItem(25), invXPTrader.getItem(32), invXPTrader.getItem(33), invXPTrader.getItem(34), invXPTrader.getItem(41), invXPTrader.getItem(42), invXPTrader.getItem(43)));
							ArrayList<Integer> slotNumber = new ArrayList<Integer>(Arrays.asList(10, 11, 12, 14, 15, 16, 19, 20, 21, 23, 24, 25, 28, 29, 30, 32, 33, 34, 37, 38, 39, 41, 42, 43));
							player.sendMessage(new CCT().colorize("&aTrade Accepted!"));
							for(int i = 0; i < importantSlots.size(); i++) {
								if(importantSlots.get(i) != null) {
									if(title.contains("XP Trader")) {
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
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, (float)2, (float)1);
						}
						else if(rarity != 0) {
							Inventory invXPTrader = view.getTopInventory();
							Inventory invPlayer = view.getBottomInventory();
							invPlayer.addItem(item);
							int slot = event.getSlot();
							invXPTrader.clear(slot);
							invXPTrader.clear(slot + 4);
							player.updateInventory();
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, (float)2, (float)0.5);
						}
					}
				}
				else if(open.getType() == InventoryType.PLAYER) {
					event.setCancelled(true);
					if(action == ClickType.LEFT) {
						NBTItem it = new NBTItem(item);
						int level = 1;
						int rarity = 0;
						if(it.hasKey("Level")) {
							level = it.getInteger("Level");
						}
						if(it.hasKey("Rarity")) {
							if(it.getString("Rarity").equals("&7Common")) {
								rarity = 75;
							}
							if(it.getString("Rarity").equals("&aRare")) {
								rarity = 150;
							}
							if(it.getString("Rarity").equals("&bEpic")) {
								rarity = 300;
							}
							if(it.getString("Rarity").equals("&cLegendary")) {
								rarity = 600;
							}
							if(it.getString("Rarity").equals("&5Mythic")) {
								rarity = 1000;
							}
							if(it.getString("Rarity").equals("&eHeroic")) {
								rarity = 1500;
							}
						}
						if(rarity != 0) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, (float)2, (float)1.5);
							Inventory invXPTrader = view.getTopInventory();
							Inventory invPlayer = view.getBottomInventory();
							invPlayer.removeItem(invPlayer.getItem(event.getSlot()));
							int slot = invXPTrader.firstEmpty();
							invXPTrader.setItem(slot, item);
							invXPTrader.setItem(slot + 4, playerXPBottle(rarity * level, item.getAmount()));
							player.updateInventory();
						}
						else {
							event.setCancelled(true);
							player.sendMessage(new CCT().colorize("&cUnvalid Item."));
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
		itemmeta.setDisplayName(new CCT().colorize("&c&lBACK"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack playerXPBottle(int xp, int amount) {
		ItemStack item1 = new ItemStack(Material.EXPERIENCE_BOTTLE, amount);
		ItemMeta itemmeta = item1.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lXP Bottle"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7When you throw this bottle, you"));
		lore1.add(new CCT().colorize("&7will gain xp appropiate to it's amount"));
		lore1.add(new CCT().colorize("&7XP Amount: &6" + xp));
		itemmeta.setLore(lore1);
		item1.setItemMeta(itemmeta);
		NBTItem item = new NBTItem(item1);
		item.setInteger("XPBottle", xp);
		item1 = item.getItem();
		return item1;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Accept Trade Button
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack acceptButton() {
		ItemStack item = new ItemStack(Material.LIME_DYE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lAccept?"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7Click this to accept the trade."));
		itemmeta.setLore(lore1);
		item.setItemMeta(itemmeta);
		return item;
	}
	@EventHandler
	public void closeXPTrader(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		InventoryView inv = event.getView();
		String title = ChatColor.stripColor(inv.getTitle());
		if(title.equals("XP Trader")) {
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
