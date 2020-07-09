package me.WiebeHero.DFShops;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Scoreboard.DFScoreboard;
import net.md_5.bungee.api.ChatColor;

public class ShopEvents implements Listener{
	
	private DFPlayerManager dfManager;
	private ShopMenu shopMenu;
	private ShopItemManager shopManager;
	private DFScoreboard board;
	
	public ShopEvents(DFPlayerManager dfManager, ShopMenu shopMenu, DFScoreboard board, ShopItemManager shopManager) {
		this.dfManager = dfManager;
		this.shopMenu = shopMenu;
		this.board = board;
		this.shopManager = shopManager;
	}
	
	@EventHandler
	public void cancelShopClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			InventoryView view = player.getOpenInventory();
			if(view != null) {
				if(view.getTitle().contains("Shop")) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void mainMenuClicks(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			ClickType click = event.getClick();
			InventoryView view = player.getOpenInventory();
			String title = view.getTitle();
			if(view != null) {
				if(ChatColor.stripColor(title).contains("Shop")) {
					ItemStack i = event.getCurrentItem();
					if(i != null) {
						NBTItem item = new NBTItem(i);
						if(item.hasKey("ShopItem")) {
							ShopItem sItem = item.getObject("ShopItem", ShopItem.class);
							if(click == ClickType.LEFT || click == ClickType.SHIFT_LEFT) {
								double buy = sItem.getCostPrice();
								if(buy > 0.00) {
									int first = player.getInventory().firstEmpty();
									if(first != -1) {
										DFPlayer dfPlayer = this.dfManager.getEntity(player);
										double money = dfPlayer.getMoney();
										if(money >= buy) {
											int amount = sItem.getAmount();
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have bought " + amount + " " + i.getItemMeta().getDisplayName() + "&a" + " for " + buy + "$!"));
											if(i.getType() != Material.SPAWNER) {
												dfPlayer.removeMoney(buy);
												ItemStack stack = new ItemStack(i.getType(), amount);
												player.getInventory().addItem(stack);
												this.board.updateScoreboard(player);
											}
											else {
												dfPlayer.removeMoney(buy);
												ItemStack stack = i;
												player.getInventory().addItem(stack);
												this.board.updateScoreboard(player);
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough money to buy this item!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't buy your own items due to your inventory being full!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't buy this item!"));
								}
							}
							else if(click == ClickType.RIGHT || click == ClickType.SHIFT_RIGHT) {
								double sell = sItem.getSellPrice();
								if(sell > 0.00) {
									int amount = sItem.getAmount();
									double amountSell = 0;
									for(ItemStack sellItem : player.getOpenInventory().getBottomInventory().getContents()) {
					  					if(sellItem != null) {
						  					if(sellItem.getType() == i.getType()) {
						  						if(!sellItem.getItemMeta().hasDisplayName() && !sellItem.getItemMeta().hasLore()) {
							  						amountSell = amountSell + sellItem.getAmount();
						  						}
						  					}
					  					}
					  				}
									if(amountSell >= amount) {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have sold " + amount + " " + i.getItemMeta().getDisplayName() + "&a" + " for " + sell + "$!"));
					  					player.getInventory().removeItem(new ItemStack(i.getType(), amount));
					  					DFPlayer dfPlayer = this.dfManager.getEntity(player);
					  					dfPlayer.addMoney(sell);
					  					this.board.updateScoreboard(player);
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough items!"));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't sell this item!"));
								}
							}
							
						}
						else if(item.hasKey("Open Shop")) {
							String construct = new CCT().colorize("&a&l" + item.getString("Open Shop"));
							int maxPage = (int)Math.ceil((double)this.shopManager.getShopItems(item.getString("Open Shop")).size() / (double)this.shopMenu.getMaxItemsOnPage());
							this.shopMenu.ShopPage(player, construct, 1, maxPage);
						}
						else if(item.hasKey("Next") || item.hasKey("Previous")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 2.0F);
							String shopName = title.split(":")[0];
							int newPage = 0;
							int maxPage = (int)Math.ceil((double)this.shopManager.getShopItems(ChatColor.stripColor(shopName)).size() / (double)this.shopMenu.getMaxItemsOnPage());
							if(item.hasKey("Next")) {
								newPage = item.getInteger("Next");
							}
							else if(item.hasKey("Previous")) {
								newPage = item.getInteger("Previous");
							}
							if(newPage < 1 && maxPage > 1) {
								this.shopMenu.ShopPage(player, shopName, maxPage, maxPage);
							}
							else if(newPage <= maxPage && maxPage > 1) {
								this.shopMenu.ShopPage(player, shopName, newPage, maxPage);
							}
							else if(newPage > maxPage && maxPage > 1) {
								this.shopMenu.ShopPage(player, shopName, 1, maxPage);
							}
						}
						else if(item.hasKey("Back")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 2.0F);
							this.shopMenu.ShopOverview(player);
						}
						else if(item.hasKey("Close")) {
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 2.0F);
							player.closeInventory();
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void novisActivate(PlayerInteractEntityEvent event) {
		if(event.getPlayer() instanceof Player) {
			if(event.getRightClicked() instanceof HumanEntity) {
				Player player = event.getPlayer();
				HumanEntity shop = (HumanEntity) event.getRightClicked();
				if(shop.getCustomName() != null) {
					if(shop.getCustomName().contains(ChatColor.stripColor("Shop"))) {
						this.shopMenu.ShopOverview(player);
					}
				}
			}
		}
	}
	@EventHandler
	public void placeSpawners(BlockPlaceEvent event) {
		if(event.getBlock().getType() == Material.SPAWNER) {
			Player player = event.getPlayer();
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
					String name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
					String stripped = ChatColor.stripColor(name);
					String split[] = stripped.split(" Spawner");
					stripped = split[0];
					stripped = stripped.toUpperCase();
					stripped = stripped.replaceAll(" ", "_");
					Block bl = event.getBlock();
	                CreatureSpawner s = (CreatureSpawner) bl.getState();
	                s.setRequiredPlayerRange(30);
	                if(!stripped.contains("SPAWNER_SETUP")) {
	                	s.setSpawnedType(EntityType.valueOf(stripped));
	                }
					s.update();
				}
			}
		}
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if(!event.isCancelled()) {
			Player player = event.getEntity();
			DFPlayer dfPlayer = dfManager.getEntity(player);
			if(dfPlayer.getMoney() >= 10000) {
				dfPlayer.removeMoney(dfPlayer.getMoney() / 100 * 5);
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou lost 10% of your current balance due to punishment of death."));
			}
		}
	}
	@EventHandler
	public void cancelPickup(PlayerAttemptPickupItemEvent event) {
		Player player = event.getPlayer();
		InventoryView view = player.getOpenInventory();
		if(view.getTitle().contains("Shop")) {
			event.setCancelled(true);
		}
	}
}
