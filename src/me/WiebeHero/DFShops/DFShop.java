package me.WiebeHero.DFShops;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Scoreboard.DFScoreboard;

public class DFShop implements Listener{
	public Set<String> check = new HashSet<String>();
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	public String getLast(Set<String> set) {
        return set.stream().skip(set.stream().count() - 1).findFirst().get();
    }
	private DFPlayerManager dfManager;
	private DFScoreboard board;
	public DFShop(DFPlayerManager dfManager, DFScoreboard board) {
		this.dfManager = dfManager;
		this.board = board;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Main Page Shop
	//--------------------------------------------------------------------------------------------------------------------
	public void ShopMainPage(Player player) {
		Inventory i = plugin.getServer().createInventory(null, 54, (new CCT().colorize("&a&lShop")));
		player.openInventory(i);
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
		i.setItem(11, foodShop());
		i.setItem(13, farmableShop());
		i.setItem(19, blockShop());
		i.setItem(21, redstoneShop());
		i.setItem(23, mobDropShop());
		i.setItem(25, oreShop());
		i.setItem(17, emptyVoid());
		i.setItem(18, emptyVoid());
		i.setItem(28, specialShop());
		i.setItem(30, spawnerShop());
		i.setItem(32, brewingShop());
		i.setItem(34, decorationShop());
		i.setItem(26, emptyVoid());
		i.setItem(27, emptyVoid());
		i.setItem(35, emptyVoid());
		i.setItem(36, emptyVoid());
		i.setItem(44, emptyVoid());
		i.setItem(45, emptyVoid());
		i.setItem(46, emptyVoid());
		i.setItem(47, emptyVoid());
		i.setItem(48, emptyVoid());
		i.setItem(49, closeButton());
		i.setItem(50, emptyVoid());
		i.setItem(51, emptyVoid());
		i.setItem(52, emptyVoid());
		i.setItem(53, emptyVoid());
	}
	public void EveryOtherShop(Player player, String name, int pageNumber) {
		File f =  new File("plugins/CustomEnchantments/shopConfig.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		try{
			yml.load(f);
	    }
	    catch(IOException e){
	        e.printStackTrace();
	    } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		Set<String> maxPageNumbers = yml.getConfigurationSection("Shop." + name).getKeys(false);
		String string = getLast(maxPageNumbers);
		String split[] = string.split(" ");
		int maxPageNumber = Integer.parseInt(split[1]);
		if(pageNumber >= 1 && pageNumber <= maxPageNumber) {
			Set<String> items = yml.getConfigurationSection("Shop." + name + ".Page " + pageNumber).getKeys(false);
			ArrayList<String> mainPageItems = new ArrayList<String>(items);
			Inventory i = plugin.getServer().createInventory(null, 54, (new CCT().colorize("&a&l" + name + ": Page " + pageNumber + "/" + maxPageNumber)));
			player.openInventory(i);
			for(int i1 = 0; i1 < mainPageItems.size(); i1++) {
				String materialString = yml.getString("Shop." + name + ".Page " + pageNumber + "." + mainPageItems.get(i1) + ".Material");
				materialString = materialString.replaceAll(" ", "_");
				materialString = materialString.toUpperCase();
				ItemStack item = null;
				if(yml.getInt("Shop." + name + ".Page " + pageNumber + "." + mainPageItems.get(i1) + ".Data Value") > -1) {
					item = new ItemStack(Material.getMaterial(materialString), 1);
				}
				else if(yml.getInt("Shop." + name + ".Page " + pageNumber + "." + mainPageItems.get(i1) + ".Data Value") == -1){
					item = new ItemStack(Material.getMaterial(materialString), 1);
				}
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName(new CCT().colorize("&a&l" + mainPageItems.get(i1)));
				ArrayList<String> lore1 = new ArrayList<String>();
				if(yml.getInt("Shop." + name + ".Page " + pageNumber + "." + mainPageItems.get(i1) + ".Cost") > 0) {
					lore1.add(new CCT().colorize("&7Left Click to buy " + yml.getInt("Shop." + name + ".Page " + pageNumber + "." + mainPageItems.get(i1) + ".Amount") + " = " + yml.getInt("Shop." + name + ".Page " + pageNumber + "." + mainPageItems.get(i1) + ".Cost") + "$"));
				}
				if(yml.getInt("Shop." + name + ".Page " + pageNumber + "." + mainPageItems.get(i1) + ".Sell") > 0) {
					lore1.add(new CCT().colorize(""));
					lore1.add(new CCT().colorize("&7Right Click to sell " + yml.getInt("Shop." + name + ".Page " + pageNumber + "." + mainPageItems.get(i1) + ".Amount") + " = " + yml.getInt("Shop." + name + ".Page " + pageNumber + "." + mainPageItems.get(i1) + ".Sell") + "$"));
				}
				itemmeta.setLore(lore1);
				item.setItemMeta(itemmeta);
				i.setItem(i1, item);
			}
			i.setItem(36, emptyVoid());
			i.setItem(37, emptyVoid());
			i.setItem(38, emptyVoid());
			i.setItem(39, emptyVoid());
			i.setItem(40, emptyVoid());
			i.setItem(41, emptyVoid());
			i.setItem(42, emptyVoid());
			i.setItem(43, emptyVoid());
			i.setItem(44, emptyVoid());
			i.setItem(48, previousPage());
			i.setItem(49, backButton());
			i.setItem(50, nextPage());
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Shop Items
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack emptyVoid() {
		ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize(""));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack backButton() {
		ItemStack item = new ItemStack(Material.BARRIER, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&c&lBACK"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack closeButton() {
		ItemStack item = new ItemStack(Material.BARRIER, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&c&lCLOSE"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack nextPage() {
		ItemStack item = new ItemStack(Material.PAPER, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lNext Page"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack previousPage() {
		ItemStack item = new ItemStack(Material.PAPER, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lPrevious Page"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack blockShop() {
		ItemStack item = new ItemStack(Material.GRASS_BLOCK, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lBlock Shop"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack foodShop() {
		ItemStack item = new ItemStack(Material.COOKED_BEEF, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lFood Shop"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack redstoneShop() {
		ItemStack item = new ItemStack(Material.TNT, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lRaid Shop"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack mobDropShop() {
		ItemStack item = new ItemStack(Material.ROTTEN_FLESH, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lMobdrop Shop"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack oreShop() {
		ItemStack item = new ItemStack(Material.IRON_ORE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lOre Shop"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack specialShop() {
		ItemStack item = new ItemStack(Material.BEACON, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lSpecial Shop"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack spawnerShop() {
		ItemStack item = new ItemStack(Material.SPAWNER, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lSpawner Shop"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack decorationShop() {
		ItemStack item = new ItemStack(Material.DANDELION_YELLOW, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lDecoration Shop"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack brewingShop() {
		ItemStack item = new ItemStack(Material.BREWING_STAND, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lBrewing Shop"));
		item.setItemMeta(itemmeta);
		return item;
	}
	public ItemStack farmableShop() {
		ItemStack item = new ItemStack(Material.CACTUS, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lFarmable Shop"));
		item.setItemMeta(itemmeta);
		return item;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Prevent Clicking in Shop
	//--------------------------------------------------------------------------------------------------------------------
	@EventHandler
	public void novisPreventClick(InventoryClickEvent event) {	
		File f1 =  new File("plugins/CustomEnchantments/shopConfig.yml");
		YamlConfiguration yml1 = YamlConfiguration.loadConfiguration(f1);
		Player player = (Player) event.getWhoClicked();
		DFPlayer dfPlayer = dfManager.getEntity(player);
		ItemStack item = event.getCurrentItem();
		Inventory open = event.getClickedInventory();
		InventoryView view = player.getOpenInventory();
		ClickType action = event.getClick();
		if(open == null) {
			return;
		}
		try{
			yml1.load(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		String title = ChatColor.stripColor(view.getTitle());
		Set<String> shops = yml1.getConfigurationSection("Shop").getKeys(false);
		ArrayList<String> shopNames = new ArrayList<String>(shops);
		String splits[] = title.split(":");
		title = splits[0];
		if(title.equals("Shop")) {
			event.setCancelled(true);
			if(item.equals(null) || !item.hasItemMeta()) {
				return;
			}
			else if(item.getType() == Material.BARRIER) {
				player.closeInventory();
			}
			else if(item.getItemMeta().hasDisplayName()){
				if(shopNames.contains(ChatColor.stripColor(item.getItemMeta().getDisplayName()))) {
					EveryOtherShop(player, ChatColor.stripColor(item.getItemMeta().getDisplayName()), 1);
				}
			}
		}
		else if(shopNames.contains(title)) {
			event.setCancelled(true);
			if(item.equals(null) || !item.hasItemMeta()) {
				return;
			}
			if(item.getType() != Material.BARRIER) {
				List<String> lore = item.getItemMeta().getLore();
				if(action == ClickType.LEFT || action == ClickType.SHIFT_LEFT) {
					if(item.getItemMeta().hasLore()) {
						if(item.getItemMeta().getLore().toString().contains("buy")) {
							String loreBuy = lore.get(0);
							String parts1[] = loreBuy.split(" |\\$");
							int amount = Integer.parseInt(parts1[4]);
							double cost = Double.parseDouble(parts1[6]);
			  				double money = dfPlayer.getMoney();
			  				if(money >= cost) {
			  					if(item.getType() != Material.SPAWNER) {
				  					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have bought " + amount + " " + item.getItemMeta().getDisplayName() + "&a for " + cost + "$!"));
				  					player.getInventory().addItem(new ItemStack(item.getType(), amount));
				  					dfPlayer.removeMoney(cost);
				  					board.updateScoreboard(player);
			  					}
			  					else {
			  						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have bought " + amount + " " + item.getItemMeta().getDisplayName() + "&a foraa " + cost + "$!"));
			  						ItemStack item1 = new ItemStack(item);
			  						ItemMeta itemmeta = (ItemMeta) item1.getItemMeta();
			  						itemmeta.setLore(null);
			  						item1.setItemMeta(itemmeta);
			  						player.getInventory().addItem(item1);
			  						dfPlayer.removeMoney(cost);
			  						board.updateScoreboard(player);
			  					}
			  				}
			  				else {
			  					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou don't have enough money!"));
			  				}
						}
						else {
		  					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't buy this item!"));
		  				}
					}
				}
				else if(action == ClickType.RIGHT || action == ClickType.SHIFT_RIGHT) {
					if(item.getItemMeta().hasLore()) {
						if(item.getItemMeta().getLore().toString().contains("sell")) {
							String loreSell = lore.get(2);
							String parts2[] = loreSell.split(" |\\$");
							int amount = Integer.parseInt(parts2[4]);
							double sell = Double.parseDouble(parts2[6]);
			  				double amountSell = 0;
			  				for(ItemStack sellItem : player.getOpenInventory().getBottomInventory().getContents()) {
			  					if(sellItem != null) {
				  					if(sellItem.getType() == item.getType()) {
				  						if(!sellItem.getItemMeta().hasDisplayName() && !sellItem.getItemMeta().hasLore()) {
					  						amountSell = amountSell + sellItem.getAmount();
				  						}
				  					}
			  					}
			  				}
		  					if(amountSell >= amount) {
			  					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have sold " + amount + " " + item.getItemMeta().getDisplayName() + "&a" + " for " + sell + "$!"));
			  					player.getInventory().removeItem(new ItemStack(item.getType(), amount));
			  					dfPlayer.addMoney(sell);
			  					board.updateScoreboard(player);
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
			}
			if(item.getItemMeta().getDisplayName().contains("BACK")) {
				ShopMainPage(player);
			}
			else if(item.getItemMeta().getDisplayName().contains("Next Page")) {
				String tempTitle = ChatColor.stripColor(view.getTitle());
				String split[] = tempTitle.split("Page |/");
				int pageNumber = Integer.parseInt(split[1]);
				EveryOtherShop(player, ChatColor.stripColor(title), pageNumber + 1);
			}
			else if(item.getItemMeta().getDisplayName().contains("Previous Page")) {
				String tempTitle = ChatColor.stripColor(view.getTitle());
				String split[] = tempTitle.split("Page |/");
				int pageNumber = Integer.parseInt(split[1]);
				EveryOtherShop(player, ChatColor.stripColor(title), pageNumber - 1);
			}
		}
		else {
			if(view.getTitle().contains("Shop") || shopNames.contains(title)) {
				if(view.getBottomInventory().getType() == InventoryType.PLAYER) {
					event.setCancelled(true);
				}
			}
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Activate Shop
	//--------------------------------------------------------------------------------------------------------------------
	@EventHandler
	public void novisActivate(PlayerInteractEntityEvent event) {
		if(event.getPlayer() instanceof Player) {
			if(event.getRightClicked() instanceof HumanEntity) {
				Player player = event.getPlayer();
				HumanEntity shop = (HumanEntity) event.getRightClicked();
				if(shop.getCustomName() != null) {
					if(shop.getCustomName().contains(ChatColor.stripColor("Shop"))) {
						ShopMainPage(player);
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
}

