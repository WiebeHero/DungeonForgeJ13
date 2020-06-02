package me.WiebeHero.AuctionHouse;

import java.util.ArrayList;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import net.md_5.bungee.api.ChatColor;

public class AHEvents implements Listener{
	private AHInventory ahInv;
	private AHManager ahManager;
	private DFPlayerManager dfManager;
	public AHEvents(AHInventory ahInv, AHManager ahManager, DFPlayerManager dfManager) {
		this.ahInv = ahInv;
		this.ahManager = ahManager;
		this.dfManager = dfManager;
	}
	@EventHandler
	public void cancelAHClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			InventoryView view = player.getOpenInventory();
			if(view != null) {
				if(view.getTitle().contains("Auction House")) {
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void mainMenuClicks(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			InventoryView view = player.getOpenInventory();
			if(view != null) {
				if(ChatColor.stripColor(view.getTitle()).equals("Auction House")) {
					ItemStack i = event.getCurrentItem();
					if(i != null) {
						NBTItem item = new NBTItem(i);
						if(item.hasKey("AHItem")) {
							AHItem ah = item.getObject("AHItem", AHItem.class);
							long timeLeft = ah.getTimeLeft();
							if(!ah.getSellerUuid().equals(player.getUniqueId())) {
								if(timeLeft > 0) {
									if(dfManager.contains(player)) {
										double price = ah.getPrice();
										DFPlayer dfBuyer = dfManager.getEntity(player);
										if(dfBuyer.getMoney() >= price) {
											if(ahManager.isInAhSimple(ah.getKey())) {
												ahInv.AuctionHouseConfirm(player, event.getCurrentItem());
											}
											else {
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't purchase this item due to it already being bought by someone else!"));
											}
										}
										else {
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough money to buy this item!"));
										}
									}
									else {
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cAn error has occured, please contact higher ups."));
									}
								}
								else {
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't buy this item anymore because it has expired!"));
								}
							}
							else {
								player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't buy your own items! If you wan't to take them off the auction house, click the 'Item's im selling' button."));
							}
						}
						else if(item.hasKey("Next")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 2.0F);
							int newPage = item.getInteger("Next");
							int maxPage = (int)Math.ceil((double)ahManager.getAhItemList().size() / (double)ahInv.getAvailableList().size());
							if(newPage <= maxPage && maxPage > 1) {
								ahInv.AHInv(player, newPage);
								player.setMetadata("AHPage", new FixedMetadataValue(CustomEnchantments.getInstance(), newPage));
							}
							else if(newPage > maxPage && maxPage > 1) {
								ahInv.AHInv(player, 1);
								player.setMetadata("AHPage", new FixedMetadataValue(CustomEnchantments.getInstance(), 1));
							}
						}
						else if(item.hasKey("Previous")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2.0F, 2.0F);
							int newPage = item.getInteger("Previous");
							int maxPage = (int)Math.ceil((double)ahManager.getAhItemList().size() / (double)ahInv.getAvailableList().size());
							if(newPage >= 1 && maxPage > 1) {
								ahInv.AHInv(player, newPage);
								player.setMetadata("AHPage", new FixedMetadataValue(CustomEnchantments.getInstance(), newPage));
							}
							else if(newPage < 1 && maxPage > 1) {
								ahInv.AHInv(player, maxPage);
								player.setMetadata("AHPage", new FixedMetadataValue(CustomEnchantments.getInstance(), 1));
							}
						}
						else if(item.hasKey("Selling")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 2.0F);
							ahInv.AuctionHouseSelling(player);
						}
						else if(item.hasKey("Bin")) {
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 2.0F, 2.0F);
							ahInv.AuctionHouseCollectionBin(player);
						}
						else if(item.hasKey("Close")) {
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 2.0F);
							player.closeInventory();
						}
					}
				}
				else if(ChatColor.stripColor(view.getTitle()).equals("Auction House: Your item's")) {
					ItemStack i = event.getCurrentItem();
					if(i != null) {
						NBTItem item = new NBTItem(i);
						if(item.hasKey("AHItem")) {
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 1.0F);
							ahInv.AuctionHouseTakeOff(player, event.getCurrentItem());
						}
						else if(item.hasKey("Return")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 2.0F, 1.0F);
							ahInv.AHInv(player, 1);
						}
						else if(item.hasKey("Bin")) {
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 2.0F, 2.0F);
							ahInv.AuctionHouseCollectionBin(player);
						}
						else if(item.hasKey("Close")) {
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 2.0F);
							player.closeInventory();
						}
					}
				}
				else if(ChatColor.stripColor(view.getTitle()).equals("Auction House: Take off")) {
					ItemStack i = event.getCurrentItem();
					if(i != null) {
						NBTItem key = new NBTItem(i);
						if(key.hasKey("Confirm")) {
							ItemStack it = event.getInventory().getItem(2);
							NBTItem item = new NBTItem(it);
							if(item.hasKey("AHItem")) {
								AHItem ah = item.getObject("AHItem", AHItem.class);
								if(ahManager.isInAhSimple(ah.getKey())) {
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 1.5F);
									ahManager.removeFromAh(ah.getKey());
									item.setString("Collect", "");
									ahManager.addToBin(player.getUniqueId(), ahManager.deconstructAHItem(item.getItem()));
									ahInv.AuctionHouseSelling(player);
								}
								else {
									player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.5F);
									player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cAnother player has already bought this item!"));
									ahInv.AuctionHouseSelling(player);
								}
							}
						}
						if(key.hasKey("Cancel")) {
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.5F);
							ahInv.AuctionHouseSelling(player);
						}
					}
				}
				else if(ChatColor.stripColor(view.getTitle()).equals("Auction House: Collection bin")) {
					ItemStack i = event.getCurrentItem();
					if(i != null) {
						NBTItem item = new NBTItem(i);
						if(item.hasKey("Collect")) {
							if(player.getInventory().firstEmpty() != -1) {
								player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 2.0F, 1.0F);
								ahManager.getCollectionBin(player.getUniqueId()).remove(item.getItem());
								item.removeKey("Collect");
								player.getInventory().addItem(item.getItem());
								ahInv.AuctionHouseCollectionBin(player);
							}
						}
						else if(item.hasKey("Return")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 2.0F, 1.0F);
							ahInv.AHInv(player, 1);
						}
						else if(item.hasKey("Selling")) {
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 2.0F, 2.0F);
							ahInv.AuctionHouseSelling(player);
						}
						else if(item.hasKey("Close")) {
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 2.0F, 2.0F);
							player.closeInventory();
						}
					}
				}
				else if(ChatColor.stripColor(view.getTitle()).equals("Confirm purchase?")) {
					ItemStack i = event.getCurrentItem();
					if(i != null) {
						NBTItem item = new NBTItem(i);
						if(item.hasKey("Confirm")) {
							ItemStack purchasedItem = view.getItem(2);
							event.setCancelled(true);
							if(purchasedItem != null) {
								NBTItem pur = new NBTItem(purchasedItem);
								if(pur.hasKey("AHItem")) {
									AHItem ah = pur.getObject("AHItem", AHItem.class);
									DFPlayer dfBuyer = dfManager.getEntity(player);
									double price = ah.getPrice();
									if(dfBuyer.getMoney() >= ah.getPrice()) {
										if(ahManager.isInAhSimple(ah.getKey())) {
											if(player.getInventory().firstEmpty() != -1) {
												player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 2.0F, 1.0F);
												ahManager.removeFromAh(ah.getKey());
												DFPlayer dfSeller = dfManager.getEntity(ah.getSellerUuid());
												dfBuyer.removeMoney(price);
												dfSeller.addMoney(price);
												ItemStack finalItem = ahManager.deconstructAHItem(purchasedItem);
												player.getInventory().setItem(player.getInventory().firstEmpty(), finalItem);
												ahInv.AHInv(player, 1);
											}
											else {
												player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
												player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have any room in your inventory to purchase this item!"));
											}
										}
										else {
											player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
											player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't purchase this item due to it already being bought by someone else!"));
										}
									}
									else {
										player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
										player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cPurchase cancelled! You don't have the required amount of money!"));
									}
								}
							}
						}
						else if(item.hasKey("Cancel")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
							player.closeInventory();
							player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cPurchase cancelled!"));
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void registerBin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!ahManager.isInBin(player.getUniqueId())) {
			ahManager.ahCollectionBin.put(player.getUniqueId(), new ArrayList<ItemStack>());
		}
	}
}
