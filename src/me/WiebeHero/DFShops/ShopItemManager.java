package me.WiebeHero.DFShops;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class ShopItemManager {
	
	private HashMap<String, ArrayList<ItemStack>> shopItemList;
	private ArrayList<String> shopTitles;
	
	public ShopItemManager() {
		this.shopItemList = new HashMap<String, ArrayList<ItemStack>>();
		this.shopTitles = new ArrayList<String>();
	}
	
	public ArrayList<ItemStack> getShopItems(String shop) {
		return this.shopItemList.get(shop);
	}
	
	public ArrayList<String> getShopTitles(){
		return this.shopTitles;
	}
	
	public HashMap<String, ArrayList<ItemStack>> getShopItemList(){
		return this.shopItemList;
	}
	
	public ItemStack getItemByType(Material type) {
		for(ArrayList<ItemStack> stacks : this.shopItemList.values()) {
			for(ItemStack stack : stacks) {
				if(stack.getType() == type) {
					return stack;
				}
			}
		}
		return null;
	}
	
	public ArrayList<ItemStack> getAllShopItems(){
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		for(ArrayList<ItemStack> stack : this.shopItemList.values()) {
			stacks.addAll(stack);
		}
		return stacks;
	}
	
	public void loadShopItems() {
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
		Set<String> shops = yml.getConfigurationSection("Shop").getKeys(false);
		for(String shopName : shops) {
			this.shopTitles.add(shopName);
			this.shopItemList.put(shopName, new ArrayList<ItemStack>());
			Set<String> items = yml.getConfigurationSection("Shop." + shopName).getKeys(false);
			for(String type : items) {
				String materialString = yml.getString("Shop." + shopName + "." + type + ".Material");
				ItemStack item = null;
				Material mat = null;
				try {
					mat = Material.getMaterial(materialString.replaceAll(" ", "_").toUpperCase());
				}
				catch(IllegalArgumentException ex) {
					CustomEnchantments.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.RED + "A shop could not be registered due to an invalid material!");
				}
				if(mat != null) {
					item = new ItemStack(mat, 1);
					ShopItem shopItem = new ShopItem();
					ItemMeta itemmeta = item.getItemMeta();
					itemmeta.setDisplayName(new CCT().colorize("&a&l" + type));
					ArrayList<String> lore1 = new ArrayList<String>();
					double cost = yml.getDouble("Shop." + shopName + "." + type + ".Cost");
					double sell = yml.getDouble("Shop." + shopName + "." + type + ".Sell");
					int amount = yml.getInt("Shop." + shopName + "." + type + ".Amount");
					if(cost > 0) {
						shopItem.setCostPrice(cost);
						lore1.add(new CCT().colorize("&7Left Click to buy " + amount + " = " + cost + "$"));
					}
					if(sell > 0) {
						shopItem.setSellPrice(sell);
						lore1.add(new CCT().colorize(""));
						lore1.add(new CCT().colorize("&7Right Click to sell " + amount + " = " + sell + "$"));
					}
					shopItem.setAmount(amount);
					itemmeta.setLore(lore1);
					item.setItemMeta(itemmeta);
					NBTItem i = new NBTItem(item);
					i.setObject("ShopItem", shopItem);
					item = i.getItem();
					this.shopItemList.get(shopName).add(item);
				}
			}
		}
	}
}
