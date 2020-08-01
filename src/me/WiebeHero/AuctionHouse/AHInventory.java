package me.WiebeHero.AuctionHouse;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;

public class AHInventory {
	private AHManager ahManager;
	private ItemStackBuilder builder;
	private ArrayList<Integer> availableSlots = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35));
	public AHInventory(AHManager ahManager, ItemStackBuilder builder) {
		this.ahManager = ahManager;
		this.builder = builder;
	}
	public void AHInv(Player player, int page) {
		Inventory i = Bukkit.getServer().createInventory(null, 54, new CCT().colorize("&6Auction House"));
		int currentPage = (page - 1) * availableSlots.size();
		int sizeAv = availableSlots.size();
		int ahSize = ahManager.getAhItemList().size();
		ArrayList<ItemStack> current = ahManager.getAhItemList();
		if(!ahManager.getAhItemList().isEmpty()) {
			for(int x = 0; x < sizeAv; x++) {
				if(currentPage + x < ahSize) {
					i.setItem(availableSlots.get(x), current.get(currentPage + x));
				}
				else {
					break;
				}
			}
		}
		i.setItem(45, builder.constructItem(
				Material.CHEST,
				"&6Collection Bin",
				new ArrayList<String>(Arrays.asList(
						"&7Here you can claim your item's that have",
						"&7not been sold."
				)),
				new Pair<String, String>("Bin", "")
		));
		i.setItem(46, builder.constructItem(
				Material.DIAMOND,
				"&6Item's your selling.",
				new ArrayList<String>(Arrays.asList(
						"&7Here you can find your item's that your",
						"&7currently selling."
				)),
				new Pair<String, String>("Selling", "")
		));
		i.setItem(48, builder.constructItem(
				Material.PAPER,
				"&6Previous Page",
				new ArrayList<String>(Arrays.asList(
						"&7Go to the previous page of the Auction House."
				)),
				new Pair<String, Integer>("Previous", page - 1)
		));
		i.setItem(49, builder.constructItem(
				Material.BARRIER,
				"&6Close",
				new ArrayList<String>(Arrays.asList(
						"&7Close the Auction House."
				)),
				new Pair<String, String>("Close", "")
		));
		i.setItem(50, builder.constructItem(
				Material.PAPER,
				"&6Next Page",
				new ArrayList<String>(Arrays.asList(
						"&7Go to the next page of the Auction House."
				)),
				new Pair<String, Integer>("Next", page + 1)
		));
		i.setItem(53, builder.constructItem(
				Material.SIGN,
				"&cSearch Filter (Coming soon)",
				new ArrayList<String>(Arrays.asList(
						"&7Click this to search for the item your",
						"&7looking for."
				)),
				new Pair<String, String>("Search", "")
		));
		i.setItem(36, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(37, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(38, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(39, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(40, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(41, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(42, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(43, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(44, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(47, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(51, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		player.openInventory(i);
	}
	public void AuctionHouseConfirm(Player player, ItemStack buyingItem) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, InventoryType.HOPPER, (new CCT().colorize("&6Confirm purchase?")));
		i.setItem(0, builder.constructItem(
				Material.LIME_STAINED_GLASS_PANE,
				"&aConfirm purchase",
				new ArrayList<String>(Arrays.asList(
						"&7By pressing this button, you agree",
						"&7to purchase thise item."
				)),
				new Pair<String, String>("Confirm", "")
		));
		i.setItem(1, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(2, buyingItem);
		i.setItem(3, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(4, builder.constructItem(
				Material.RED_STAINED_GLASS_PANE,
				"&aCancel purchase",
				new ArrayList<String>(Arrays.asList(
						"&7By pressing this button, you cancel",
						"&7your current purchase."
				)),
				new Pair<String, String>("Cancel", "")
		));
		player.openInventory(i);
	}
	public void AuctionHouseSelling(Player player) {
		Inventory i = Bukkit.getServer().createInventory(null, 54, new CCT().colorize("&6Auction House: Your item's"));
		ArrayList<ItemStack> current = ahManager.getAhItemList();
		int count = 0;
		if(!current.isEmpty()) {
			for(int x = 0; x < current.size(); x++) {
				if(x < 36) {
					ItemStack item = current.get(x);
					if(item != null && item.getType() != Material.AIR) {
						NBTItem it = new NBTItem(item);
						AHItem ah = it.getObject("AHItem", AHItem.class);
						if(ah.getSellerUuid().equals(player.getUniqueId())) {
							i.setItem(availableSlots.get(count), item);
							count++;
						}
					}
					else {
						break;
					}
				}
			}
		}
		i.setItem(45, builder.constructItem(
				Material.CHEST,
				"&6Collection Bin",
				new ArrayList<String>(Arrays.asList(
						"&7Here you can claim your item's that have",
						"&7not been sold."
				)),
				new Pair<String, String>("Bin", "")
		));
		i.setItem(46, builder.constructItem(
				Material.SIGN,
				"&6Auction House",
				new ArrayList<String>(Arrays.asList(
						"&7Click here to return to the",
						"&7Auction House."
				)),
				new Pair<String, String>("Return", "")
		));
		i.setItem(48, builder.constructItem(
				Material.WHITE_STAINED_GLASS_PANE,
				""
		));
		i.setItem(49, builder.constructItem(
				Material.BARRIER,
				"&6Close",
				new ArrayList<String>(Arrays.asList(
						"&7Close the Auction House."
				)),
				new Pair<String, String>("Close", "")
		));
		i.setItem(50, builder.constructItem(
				Material.WHITE_STAINED_GLASS_PANE,
				""
		));
		i.setItem(53, builder.constructItem(
				Material.WHITE_STAINED_GLASS_PANE,
				""
		));
		i.setItem(36, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(37, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(38, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(39, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(40, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(41, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(42, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(43, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(44, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(47, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(51, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		player.openInventory(i);
	}
	public void AuctionHouseTakeOff(Player player, ItemStack buyingItem) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, InventoryType.HOPPER, (new CCT().colorize("&6Auction House: Take off")));
		i.setItem(0, builder.constructItem(
				Material.LIME_STAINED_GLASS_PANE,
				"&aConfirm",
				new ArrayList<String>(Arrays.asList(
						"&7By pressing this button, you agree",
						"&7that you want to take this item off the Auction House."
				)),
				new Pair<String, String>("Confirm", "")
		));
		i.setItem(1, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(2, buyingItem);
		i.setItem(3, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(4, builder.constructItem(
				Material.RED_STAINED_GLASS_PANE,
				"&aCancel",
				new ArrayList<String>(Arrays.asList(
						"&7By pressing this button, your item won't",
						"&7be taken off the Auction House."
				)),
				new Pair<String, String>("Cancel", "")
		));
		player.openInventory(i);
	}
	public void AuctionHouseCollectionBin(Player player) {
		Inventory i = Bukkit.getServer().createInventory(null, 54, new CCT().colorize("&6Auction House: Collection bin"));
		ArrayList<ItemStack> current = ahManager.getCollectionBin(player.getUniqueId());
		if(!current.isEmpty()) {
			for(int x = 0; x < current.size(); x++) {
				if(x < 36) {
					ItemStack item = current.get(x);
					if(item != null && item.getType() != Material.AIR) {
						i.setItem(availableSlots.get(x), item);
					}
					else {
						break;
					}
				}
				else {
					break;
				}
			}
		}
		i.setItem(45, builder.constructItem(
				Material.SIGN,
				"&6Auction House",
				new ArrayList<String>(Arrays.asList(
						"&7Click here to return to the",
						"&7Auction House."
				)),
				new Pair<String, String>("Return", "")
		));
		i.setItem(46, builder.constructItem(
				Material.DIAMOND,
				"&6Item's your selling.",
				new ArrayList<String>(Arrays.asList(
						"&7Here you can find your item's that your",
						"&7currently selling."
				)),
				new Pair<String, String>("Selling", "")
		));
		i.setItem(48, builder.constructItem(
				Material.WHITE_STAINED_GLASS_PANE,
				""
		));
		i.setItem(49, builder.constructItem(
				Material.BARRIER,
				"&6Close",
				new ArrayList<String>(Arrays.asList(
						"&7Close the Auction House."
				)),
				new Pair<String, String>("Close", "")
		));
		i.setItem(50, builder.constructItem(
				Material.WHITE_STAINED_GLASS_PANE,
				""
		));
		i.setItem(53, builder.constructItem(
				Material.WHITE_STAINED_GLASS_PANE,
				""
		));
		i.setItem(36, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(37, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(38, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(39, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(40, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(41, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(42, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(43, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(44, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(47, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		i.setItem(51, builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				" "
		));
		player.openInventory(i);
	}
	public ArrayList<Integer> getAvailableList(){
		return this.availableSlots;
	}
}	
