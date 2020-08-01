package me.WiebeHero.DFShops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;

public class ShopMenu {
	
	private ItemStackBuilder builder;
	private ShopItemManager shopManager;
	private HashMap<String, Inventory> shops;
	private int maxItemsOnPage;
	
	public ShopMenu(ShopItemManager shopManager, ItemStackBuilder builder) {
		this.builder = builder;
		this.shopManager = shopManager;
		this.shops = new HashMap<String, Inventory>();
	}
	
	public void ShopOverview(Player player) {
		player.openInventory(shops.get("Shop Overview"));
	}
	
	public void ShopPage(Player player, String shopName, int page, int maxPage) {
		String constructed = new CCT().colorize(shopName + ": &aPage &6" + page + " &a/&6 " + maxPage);
		for(Entry<String, Inventory> entry : this.shops.entrySet()) {
			String title = entry.getKey();
			if(title.equals(constructed)) {
				Inventory i = entry.getValue();
				player.openInventory(i);
				break;
			}
		}
	}
	public void loadShops() {
		Inventory overview = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&a&lShop Overview")));
		int slotsNone[] = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 50, 51, 52, 53};
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		for(int i = 0; i < slotsNone.length; i++) {
			overview.setItem(slotsNone[i], nothing);
		}
		int slotsFill[] = new int[] {11, 13, 19, 21, 23, 25, 28, 30, 32, 34};
		String shopNames[] = this.shopManager.getShopTitles().toArray(new String[slotsFill.length]);
		Material materials[] = new Material[] {Material.GRASS_BLOCK, Material.TNT, Material.ROTTEN_FLESH, Material.IRON_ORE, Material.COOKED_BEEF, Material.CACTUS, Material.BEACON, Material.BREWING_STAND, Material.SPAWNER, Material.DANDELION_YELLOW};
		for(int i = 0; i < shopNames.length; i++) {
			overview.setItem(slotsFill[i], this.builder.constructItem(
					materials[i],
					1,
					"&a&l" + shopNames[i],
					new ArrayList<String>(Arrays.asList(
							"&7Click this item to open the",
							"&7" + shopNames[i] + "!"
					)),
					new Pair<String, String>("Open Shop", shopNames[i])
			));
		}
		overview.setItem(49, this.builder.constructItem(
				Material.BARRIER,
				1,
				"&c&lCLOSE",
				new ArrayList<String>(Arrays.asList(
						"&7Click this item to close the",
						"&7Shop overview page!"
				)),
				new Pair<String, String>("Close", "")
		));
		this.shops.put("Shop Overview", overview);
		int availableSlotsPage[] = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
		this.maxItemsOnPage = availableSlotsPage.length;
		slotsNone = new int[] {45, 46, 47, 48, 49, 50, 51, 52, 53};
		for(Entry<String, ArrayList<ItemStack>> entry : this.shopManager.getShopItemList().entrySet()) {
			String shopName = entry.getKey();
			ArrayList<ItemStack> shopItems = entry.getValue();
			int shSize = shopItems.size();
			int maxPage = (int)Math.ceil((double)shSize / (double)availableSlotsPage.length);
			for(int i = 1; i <= maxPage; i++) {
				int currentPage = availableSlotsPage.length * (i - 1);
				String constructed = new CCT().colorize("&a&l" + shopName + ": &aPage &6" + i + " &a/&6 " + maxPage);
				Inventory page = CustomEnchantments.getInstance().getServer().createInventory(null, 54, constructed);
				for(int x = 0; x < slotsNone.length; x++) {
					page.setItem(slotsNone[x], nothing);
				}
				page.setItem(48, this.builder.constructItem(
						Material.PAPER,
						1,
						"&a&lPrevious Page",
						new ArrayList<String>(Arrays.asList(
								"&7Click this item to go to",
								"&7the previous page!"
						)),
						new Pair<String, Integer>("Previous", i - 1)
				));
				page.setItem(49, this.builder.constructItem(
						Material.BARRIER,
						1,
						"&c&lBACK",
						new ArrayList<String>(Arrays.asList(
								"&7Click this item to go back to",
								"&7the Shop overview page!"
						)),
						new Pair<String, String>("Back", "")
				));
				page.setItem(50, this.builder.constructItem(
						Material.PAPER,
						1,
						"&a&lNext Page",
						new ArrayList<String>(Arrays.asList(
								"&7Click this item to go to",
								"&7the next page!"
						)),
						new Pair<String, Integer>("Next", i + 1)
				));
				if(!shopItems.isEmpty()) {
					for(int x = 0; x < availableSlotsPage.length; x++) {
						if(currentPage + x < shSize) {
							page.setItem(availableSlotsPage[x], shopItems.get(currentPage + x));
						}
						else {
							break;
						}
					}
				}
				this.shops.put(constructed, page);
			}
		}
	}
	
	public int getMaxItemsOnPage() {
		return this.maxItemsOnPage;
	}
}
