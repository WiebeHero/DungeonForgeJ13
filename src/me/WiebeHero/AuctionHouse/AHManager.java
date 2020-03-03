package me.WiebeHero.AuctionHouse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class AHManager {
	private ArrayList<Integer> availableSlots;
	public HashMap<UUID, ArrayList<ItemStack>> ahCollectionBin = new HashMap<UUID, ArrayList<ItemStack>>();
	public ArrayList<ItemStack> ahList = new ArrayList<ItemStack>();
	public AHManager(ArrayList<Integer> availableSlots) {
		this.ahList = new ArrayList<ItemStack>();
		this.ahCollectionBin = new HashMap<UUID, ArrayList<ItemStack>>();
		this.availableSlots = availableSlots;
	}
	public void start() {
		AHManager manager = this;
		new BukkitRunnable(){
			public void run() {
				manager.refreshAH();
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 20L);
	}
	public void loadAuctionHouse() {
		File f = new File("plugins/CustomEnchantments/AuctionHouse.yml");
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
		if(yml.getConfigurationSection("AuctionHouse.Items") != null) {
			if(!yml.get("AuctionHouse.Items").equals("{}")) {
				ArrayList<String> uuidStrings = new ArrayList<String>(yml.getConfigurationSection("AuctionHouse.Items").getKeys(false));
				for(int i = 0; i < uuidStrings.size(); i++) {
					ItemStack item = yml.getItemStack("AuctionHouse.Items." + uuidStrings.get(i));
					this.ahList.add(item);
				}
			}
		}
	}
	public void saveAuctionHouse() {
		File f1 =  new File("plugins/CustomEnchantments/AuctionHouse.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f1);
		try{
			yml.load(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		yml.set("AuctionHouse.Items", null);
		for(ItemStack item : this.ahList) {
			NBTItem i = new NBTItem(item);
			AHItem ah = i.getObject("AHItem", AHItem.class);
			yml.set("AuctionHouse.Items." + ah.getKey(), item);
		}
		try{
			yml.save(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public int getItemSizeAh(UUID uuid) {
		int count = 0;
		for(ItemStack item : this.ahList) {
			if(item != null) {
				NBTItem i = new NBTItem(item);
				if(i.hasKey("AHItem")) {
					AHItem ah = i.getObject("AHItem", AHItem.class);
					if(ah.getKey().equals(uuid)) {
						count++;
					}
				}
			}
		}
		return count;
	}
	public int getCollectionBinCount(UUID uuid) {
		int count = 0;
		if(this.ahCollectionBin.containsKey(uuid)) {
			count = this.ahCollectionBin.get(uuid).size();
		}
		return count;
	}
	public void addToAh(ItemStack item) {
		this.ahList.add(0, item);
	}
	public void removeFromAh(UUID uuid) {
		Pair<Boolean, ItemStack> pair = this.isInAh(uuid);
		if(pair.getKey()) {
			this.ahList.remove(pair.getValue());
		}
	}
	public Pair<Boolean, ItemStack> isInAh(UUID uuid) {
		for(ItemStack it : this.ahList) {
			NBTItem n = new NBTItem(it);
			AHItem ah = n.getObject("AHItem", AHItem.class);
			UUID nUuid = ah.getKey();
			if(uuid.equals(nUuid)) {
				return new Pair<Boolean, ItemStack>(true, it);
			}
		}
		return new Pair<Boolean, ItemStack>(false, null);
	}
	public boolean isInAhSimple(UUID uuid) {
		for(ItemStack it : this.ahList) {
			NBTItem n = new NBTItem(it);
			AHItem ah = n.getObject("AHItem", AHItem.class);
			UUID nUuid = ah.getKey();
			if(uuid.equals(nUuid)) {
				return true;
			}
		}
		return false;
	}
	public void addToBin(UUID uuid, ItemStack item) {
		this.ahCollectionBin.get(uuid).add(item);
	}
	public void removeFromBin(UUID uuidSeller, UUID uuidItem) {
		Pair<Boolean, ItemStack> pair = this.isInAh(uuidItem);
		if(pair.getKey()) {
			this.ahCollectionBin.get(uuidSeller).remove(pair.getValue());
		}
	}
	public boolean isInBin(UUID uuid, ItemStack item) {
		if(this.ahCollectionBin.get(uuid).contains(item)) {
			return true;
		}
		return false;
	}
	public boolean isInBin(UUID uuid) {
		if(this.ahCollectionBin.containsKey(uuid)) {
			return true;
		}
		return false;
	}
	public ArrayList<ItemStack> getAhItemList(){
		return this.ahList;
	}
	public ArrayList<ItemStack> getCollectionBin(UUID uuid){
		return this.ahCollectionBin.get(uuid);
	}
	public ItemStack constructAHItem(ItemStack item, AHItem ah) {
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		if(meta.hasLore()) {
			lore = new ArrayList<String>(meta.getLore());
		}
		if(lore.toString().contains("Time Left")) {
			int loreSize = lore.size();
			lore.remove(loreSize - 1);
			lore.remove(loreSize - 2);
			lore.remove(loreSize - 3);
			lore.remove(loreSize - 4);
		}
		lore.add("");
		lore.add(new CCT().colorize("&7Seller: &6" + ah.getName()));
		lore.add(new CCT().colorize("&7Price: &a" + ah.getPrice()));
		long timeLeft = ah.getTimeLeft();
		if(timeLeft < 0) {
			timeLeft = 0;
		}
		long diffSeconds = timeLeft / 1000 % 60;
        long diffMinutes = timeLeft / (60 * 1000) % 60;
        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
		lore.add(new CCT().colorize("&7Time Left: &b" + diffDays + "d " + diffHours + ":" + diffMinutes + ":" + diffSeconds));
		meta.setLore(lore);
		item.setItemMeta(meta);
		NBTItem i = new NBTItem(item);
		i.setObject("AHItem", ah);
		item = i.getItem();
		return item;
	}
	public ItemStack refreshTime(ItemStack item) {
		if(!this.ahList.isEmpty()) {
			ArrayList<String> lore = new ArrayList<String>(item.getLore());
			NBTItem i = new NBTItem(item);
			AHItem ah = i.getObject("AHItem", AHItem.class);
			long timeLeft = ah.getTimeLeft();
			if(timeLeft < 0) {
				timeLeft = 0;
			}
			long diffSeconds = timeLeft / 1000 % 60;
	        long diffMinutes = timeLeft / (60 * 1000) % 60;
	        long diffHours = timeLeft / (60 * 60 * 1000) % 24;
	        long diffDays = timeLeft / (24 * 60 * 60 * 1000);
	        lore.set(lore.size() - 1, new CCT().colorize("&7Time Left: &b" + diffDays + "d " + diffHours + ":" + diffMinutes + ":" + diffSeconds));
	        item.setLore(lore);
	        return item;
		}
		return null;
	} 
	public ItemStack deconstructAHItem(ItemStack item) {
		NBTItem temp = new NBTItem(item);
		if(temp.hasKey("AHItem")) {
			ItemMeta meta = item.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>(meta.getLore());
			int loreSize = lore.size();
			if(lore.toString().contains("Time Left")) {
				lore.remove(loreSize - 1);
				lore.remove(loreSize - 2);
				lore.remove(loreSize - 3);
				lore.remove(loreSize - 4);
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			NBTItem i = new NBTItem(item);
			i.removeKey("AHItem");
			item = i.getItem();
			return item;
		}
		return null;
	}
	public void refreshAH() {
		if(!this.ahList.isEmpty()) {
			ArrayList<ItemStack> newList = new ArrayList<ItemStack>();
			for(int i = 0; i < this.ahList.size(); i++) {
				ItemStack item = this.ahList.get(i);
				NBTItem it = new NBTItem(item);
				AHItem ah = it.getObject("AHItem", AHItem.class);
				if(ah.getTimeLeft() == 0) {
					it.setString("Collect", "");
					if(this.ahCollectionBin.containsKey(ah.getSellerUuid())) {
						this.ahCollectionBin.get(ah.getSellerUuid()).add(this.deconstructAHItem(it.getItem()));
					}
				}
				else {
					item = this.refreshTime(item);
					newList.add(item);
				}
			}
			this.ahList = newList;
		}
	}
	public void refreshConfirm(InventoryView view) {
		if(!this.ahList.isEmpty()) {
			if(ChatColor.stripColor(view.getTitle()).equals("Auction House: Collection bin") || ChatColor.stripColor(view.getTitle()).equals("Auction House: Take off")) {
				Inventory inv = view.getTopInventory();
				ItemStack compare = inv.getItem(2);
				NBTItem c = new NBTItem(compare);
				AHItem ahC = c.getObject("AHItem", AHItem.class);
				for(int i = 0; i < this.ahList.size(); i++) {
					ItemStack item = this.ahList.get(i);
					NBTItem x = new NBTItem(compare);
					AHItem ahX = x.getObject("AHItem", AHItem.class);
					if(ahC.getKey().equals(ahX.getKey())) {
						inv.setItem(2, item);
					}
				}
			}
		}
	}
	public void refreshAHItems(Player player) {
		if(!this.ahList.isEmpty()) {
			ArrayList<ItemStack> newList = this.ahList;
			InventoryView view = player.getOpenInventory();
			int page = player.getMetadata("AHPage").get(0).asInt();
			Inventory i = view.getTopInventory();
			ArrayList<Integer> availableSlots = this.availableSlots;
			int sizeAv = availableSlots.size();
			int ahSize = newList.size();
			int currentPage = (page - 1) * availableSlots.size();
			for(int x = 0; x < sizeAv; x++) {
				if(currentPage + x < ahSize) {
					i.setItem(availableSlots.get(x), newList.get(currentPage + x));
				}
				else {
					break;
				}
			}
		}
	}
	public void refreshYourItems(Player player) {
		if(!this.ahList.isEmpty()) {
			ArrayList<ItemStack> newList = this.ahList;
			InventoryView view = player.getOpenInventory();
			Inventory i = view.getTopInventory();
			ArrayList<Integer> availableSlots = this.availableSlots;
			int count = 0;
			if(!newList.isEmpty()) {
				for(int x = 0; x < newList.size(); x++) {
					ItemStack item = newList.get(x);
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
	}
}
