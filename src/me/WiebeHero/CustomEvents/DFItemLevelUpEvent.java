package me.WiebeHero.CustomEvents;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Novis.NovisEnchantmentGetting;

public class DFItemLevelUpEvent extends Event{
	private NovisEnchantmentGetting enchant;
	private DFPlayerManager dfManager;
	
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private boolean isCancelled;
	private ItemStack item;
	private ItemStack cursor;
	private int xp;
	private int slotR;
	private EquipmentSlot slot;
	
	public DFItemLevelUpEvent(Player player, ItemStack item, int xp, EquipmentSlot slot, DFPlayerManager dfManager, NovisEnchantmentGetting enchant){
        this.player = player;
        this.isCancelled = false;
        this.xp = xp;
        this.item = item;
        this.slot = slot;
        this.dfManager = dfManager;
        this.enchant = enchant;
    }
	
	public DFItemLevelUpEvent(Player player, ItemStack item, ItemStack cursor, int xp, int slot, DFPlayerManager dfManager, NovisEnchantmentGetting enchant){
        this.player = player;
        this.isCancelled = false;
        this.xp = xp;
        this.item = item;
        this.cursor = cursor;
        this.slotR = slot;
        this.dfManager = dfManager;
        this.enchant = enchant;
    }
	
	public Player getPlayer() {
		return this.player;
	}
	
	public int getLevelRequired() {
		NBTItem item = new NBTItem(this.getItemStack());
		if(item.hasKey("Upgradeable")) {
			if(item.hasKey("Level")) {
				int level = item.getInteger("Level");
				if(level >= 6) {
					int loreRequired = level - 4;
					int levelRequired = loreRequired * 5;
					return levelRequired;
				}
			}
		}
		return 0;
	}
	
	public boolean isCancelled() {
		return this.isCancelled;
	}
	
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;
	}
	
	public ItemStack getItemStack() {
		return this.item;
	}
	
	public int getXP() {
		return this.xp;
	}
	
	public void setXP(int xp) {
		this.xp = xp;
	}
	
	public void setItemStack(ItemStack item) {
		this.item = item;
	}
	
	public EquipmentSlot getEquipmentSlot() {
		return this.slot;
	}
	
	public void setEquipmentSlot(EquipmentSlot slot) {
		this.slot = slot;
	}
	
	public int getClickedSlot() {
		return this.slotR;
	}
	
	public void setClickedSlot(int slot) {
		this.slotR = slot;
	}
	
	public ItemStack getCursorStack() {
		return this.cursor;
	}
	
	public void setCursorStack(ItemStack cursor) {
		this.cursor = cursor;
	}
	
	public void activate() {
		if(!this.isCancelled()) {
			if(this.getEquipmentSlot() == EquipmentSlot.HAND) {
				ItemStack i = this.getItemStack();
				NBTItem item = new NBTItem(i);
				int itemLevel = item.getInteger("Level");
				int maxxp = item.getInteger("MAXXP");
				int total = item.getInteger("TotalXP");	
				String name = item.getString("ItemName");
				String rarity = item.getString("Rarity");
				String enchantmentString = item.getString("EnchantmentString");
				double baseDamage = item.getDouble("Base Attack Damage");
				double baseSpeed = item.getDouble("Base Attack Speed");
				double incDamage = item.getDouble("Inc Attack Damage");
				double incSpeed = item.getDouble("Inc Attack Speed");
				this.getPlayer().getWorld().playSound(this.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, (float)2, (float)1.5);
				for(int x = itemLevel + 1; x <= 15; x++) {
					if(this.getXP() >= maxxp) {
						itemLevel = x;
						this.setXP(this.getXP() - maxxp);
						total = total + maxxp;
						maxxp = maxxp / 100 * 135;
					}
					else {
						break;
					}
				}
				double value1 = baseDamage + incDamage * (itemLevel - 1);
            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
				//Config Data
				//Weapon Data
            	item.setInteger("Level", itemLevel);
            	item.setInteger("XP", this.getXP());
            	item.setInteger("MAXXP", maxxp);
            	item.setInteger("TotalXP", total);
            	item.setDouble("Attack Damage", value1);
            	item.setDouble("Attack Speed", value2);
            	i = item.getItem();
				ItemMeta meta = i.getItemMeta();
				meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
				ArrayList<String> newLore = new ArrayList<String>();
				newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
				double roundOff1 = (double) Math.round(value1 * 100) / 100;
				double roundOff2 = (double) Math.round(value2 * 100) / 100;
				newLore.add(new CCT().colorize("&7-----------------------"));
				newLore.add(new CCT().colorize("&7Attack Damage: &6" + roundOff1));
				newLore.add(new CCT().colorize("&7Attack Speed: &6" + roundOff2));
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(itemLevel < 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + this.getXP() + " &6/ &b&l" + maxxp + "&a]"));
					newLore.add(this.getNewXPBar(this.getXP(), maxxp));
				}
				else if(itemLevel == 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
				}
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(itemLevel >= 6) {
					int loreRequired = itemLevel - 4;
					int levelRequired = loreRequired * 5;
					newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
				}
				newLore.add(new CCT().colorize("&7Rarity: " + rarity));
				meta.setLore(newLore);
				i.setItemMeta(meta);
				this.getPlayer().getInventory().setItemInMainHand(i);
				//Weapon Data
				if(dfManager.contains(this.getPlayer())) {
					DFPlayer dfPlayer = dfManager.getEntity(this.getPlayer());
					dfPlayer.attackSpeed();
				}
			}
			else if(this.getEquipmentSlot() == EquipmentSlot.HEAD) {
				ItemStack i = this.getItemStack();
				NBTItem item = new NBTItem(i);
				int itemLevel = item.getInteger("Level");
				int maxxp = item.getInteger("MAXXP");
				int total = item.getInteger("TotalXP");	
				String name = item.getString("ItemName");
				String rarity = item.getString("Rarity");
				String enchantmentString = item.getString("EnchantmentString");
				double baseDamage = item.getDouble("Base Armor Defense");
				double baseSpeed = item.getDouble("Base Armor Toughness");
				double incDamage = item.getDouble("Inc Armor Defense");
				double incSpeed = item.getDouble("Inc Armor Toughness");
				this.getPlayer().getWorld().playSound(this.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, (float)2, (float)1.5);
				for(int x = itemLevel + 1; x <= 15; x++) {
					if(this.getXP() >= maxxp) {
						itemLevel = x;
						this.setXP(this.getXP() - maxxp);
						total = total + maxxp;
						maxxp = maxxp / 100 * 135;
					}
					else {
						break;
					}
				}
				double value1 = baseDamage + incDamage * (itemLevel - 1);
            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
				//Config Data
				//Weapon Data
            	item.setInteger("Level", itemLevel);
            	item.setInteger("XP", this.getXP());
            	item.setInteger("MAXXP", maxxp);
            	item.setInteger("TotalXP", total);
            	item.setDouble("Armor Defense", value1);
            	item.setDouble("Armor Toughness", value2);
            	i = item.getItem();
				ItemMeta meta = i.getItemMeta();
				meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
				ArrayList<String> newLore = new ArrayList<String>();
				newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
				double roundOff1 = (double) Math.round(value1 * 100) / 100;
				double roundOff2 = (double) Math.round(value2 * 100) / 100;
				newLore.add(new CCT().colorize("&7-----------------------"));
				newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
				newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(itemLevel < 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + this.getXP() + " &6/ &b&l" + maxxp + "&a]"));
					newLore.add(this.getNewXPBar(this.getXP(), maxxp));
				}
				else if(itemLevel == 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
				}
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(itemLevel >= 6) {
					int loreRequired = itemLevel - 4;
					int levelRequired = loreRequired * 5;
					newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
				}
				newLore.add(new CCT().colorize("&7Rarity: " + rarity));
				meta.setLore(newLore);
				i.setItemMeta(meta);
				this.getPlayer().getInventory().setHelmet(i);
				//Weapon Data
				if(dfManager.contains(this.getPlayer())) {
					DFPlayer dfPlayer = dfManager.getEntity(this.getPlayer());
					dfPlayer.runDefense();
				}
			}
			else if(this.getEquipmentSlot() == EquipmentSlot.CHEST) {
				ItemStack i = this.getItemStack();
				NBTItem item = new NBTItem(i);
				int itemLevel = item.getInteger("Level");
				int maxxp = item.getInteger("MAXXP");
				int total = item.getInteger("TotalXP");	
				String name = item.getString("ItemName");
				String rarity = item.getString("Rarity");
				String enchantmentString = item.getString("EnchantmentString");
				double baseDamage = item.getDouble("Base Armor Defense");
				double baseSpeed = item.getDouble("Base Armor Toughness");
				double incDamage = item.getDouble("Inc Armor Defense");
				double incSpeed = item.getDouble("Inc Armor Toughness");
				this.getPlayer().getWorld().playSound(this.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, (float)2, (float)1.5);
				for(int x = itemLevel + 1; x <= 15; x++) {
					if(this.getXP() >= maxxp) {
						itemLevel = x;
						this.setXP(this.getXP() - maxxp);
						total = total + maxxp;
						maxxp = maxxp / 100 * 135;
					}
					else {
						break;
					}
				}
				double value1 = baseDamage + incDamage * (itemLevel - 1);
            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
				//Config Data
				//Weapon Data
            	item.setInteger("Level", itemLevel);
            	item.setInteger("XP", this.getXP());
            	item.setInteger("MAXXP", maxxp);
            	item.setInteger("TotalXP", total);
            	item.setDouble("Armor Defense", value1);
            	item.setDouble("Armor Toughness", value2);
            	i = item.getItem();
				ItemMeta meta = i.getItemMeta();
				meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
				ArrayList<String> newLore = new ArrayList<String>();
				newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
				double roundOff1 = (double) Math.round(value1 * 100) / 100;
				double roundOff2 = (double) Math.round(value2 * 100) / 100;
				newLore.add(new CCT().colorize("&7-----------------------"));
				newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
				newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(itemLevel < 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + this.getXP() + " &6/ &b&l" + maxxp + "&a]"));
					newLore.add(this.getNewXPBar(this.getXP(), maxxp));
				}
				else if(itemLevel == 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
				}
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(itemLevel >= 6) {
					int loreRequired = itemLevel - 4;
					int levelRequired = loreRequired * 5;
					newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
				}
				newLore.add(new CCT().colorize("&7Rarity: " + rarity));
				meta.setLore(newLore);
				i.setItemMeta(meta);
				this.getPlayer().getInventory().setChestplate(i);
				//Weapon Data
				if(dfManager.contains(this.getPlayer())) {
					DFPlayer dfPlayer = dfManager.getEntity(this.getPlayer());
					dfPlayer.runDefense();
				}
			}
			else if(this.getEquipmentSlot() == EquipmentSlot.LEGS) {
				ItemStack i = this.getItemStack();
				NBTItem item = new NBTItem(i);
				int itemLevel = item.getInteger("Level");
				int maxxp = item.getInteger("MAXXP");
				int total = item.getInteger("TotalXP");	
				String name = item.getString("ItemName");
				String rarity = item.getString("Rarity");
				String enchantmentString = item.getString("EnchantmentString");
				double baseDamage = item.getDouble("Base Armor Defense");
				double baseSpeed = item.getDouble("Base Armor Toughness");
				double incDamage = item.getDouble("Inc Armor Defense");
				double incSpeed = item.getDouble("Inc Armor Toughness");
				this.getPlayer().getWorld().playSound(this.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, (float)2, (float)1.5);
				for(int x = itemLevel + 1; x <= 15; x++) {
					if(this.getXP() >= maxxp) {
						itemLevel = x;
						this.setXP(this.getXP() - maxxp);
						total = total + maxxp;
						maxxp = maxxp / 100 * 135;
					}
					else {
						break;
					}
				}
				double value1 = baseDamage + incDamage * (itemLevel - 1);
            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
				//Config Data
				//Weapon Data
            	item.setInteger("Level", itemLevel);
            	item.setInteger("XP", this.getXP());
            	item.setInteger("MAXXP", maxxp);
            	item.setInteger("TotalXP", total);
            	item.setDouble("Armor Defense", value1);
            	item.setDouble("Armor Toughness", value2);
            	i = item.getItem();
				ItemMeta meta = i.getItemMeta();
				meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
				ArrayList<String> newLore = new ArrayList<String>();
				newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
				double roundOff1 = (double) Math.round(value1 * 100) / 100;
				double roundOff2 = (double) Math.round(value2 * 100) / 100;
				newLore.add(new CCT().colorize("&7-----------------------"));
				newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
				newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(itemLevel < 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + this.getXP() + " &6/ &b&l" + maxxp + "&a]"));
					newLore.add(this.getNewXPBar(this.getXP(), maxxp));
				}
				else if(itemLevel == 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
				}
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(itemLevel >= 6) {
					int loreRequired = itemLevel - 4;
					int levelRequired = loreRequired * 5;
					newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
				}
				newLore.add(new CCT().colorize("&7Rarity: " + rarity));
				meta.setLore(newLore);
				i.setItemMeta(meta);
				this.getPlayer().getInventory().setLeggings(i);
				//Weapon Data
				if(dfManager.contains(this.getPlayer())) {
					DFPlayer dfPlayer = dfManager.getEntity(this.getPlayer());
					dfPlayer.runDefense();
				}
			}
			else if(this.getEquipmentSlot() == EquipmentSlot.FEET) {
				ItemStack i = this.getItemStack();
				NBTItem item = new NBTItem(i);
				int itemLevel = item.getInteger("Level");
				int maxxp = item.getInteger("MAXXP");
				int total = item.getInteger("TotalXP");	
				String name = item.getString("ItemName");
				String rarity = item.getString("Rarity");
				String enchantmentString = item.getString("EnchantmentString");
				double baseDamage = item.getDouble("Base Armor Defense");
				double baseSpeed = item.getDouble("Base Armor Toughness");
				double incDamage = item.getDouble("Inc Armor Defense");
				double incSpeed = item.getDouble("Inc Armor Toughness");
				this.getPlayer().getWorld().playSound(this.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, (float)2, (float)1.5);
				for(int x = itemLevel + 1; x <= 15; x++) {
					if(this.getXP() >= maxxp) {
						itemLevel = x;
						this.setXP(this.getXP() - maxxp);
						total = total + maxxp;
						maxxp = maxxp / 100 * 135;
					}
					else {
						break;
					}
				}
				double value1 = baseDamage + incDamage * (itemLevel - 1);
            	double value2 = baseSpeed + incSpeed * (itemLevel - 1);
				//Config Data
				//Weapon Data
            	item.setInteger("Level", itemLevel);
            	item.setInteger("XP", this.getXP());
            	item.setInteger("MAXXP", maxxp);
            	item.setInteger("TotalXP", total);
            	item.setDouble("Armor Defense", value1);
            	item.setDouble("Armor Toughness", value2);
            	i = item.getItem();
				ItemMeta meta = i.getItemMeta();
				meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
				ArrayList<String> newLore = new ArrayList<String>();
				newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
				double roundOff1 = (double) Math.round(value1 * 100) / 100;
				double roundOff2 = (double) Math.round(value2 * 100) / 100;
				newLore.add(new CCT().colorize("&7-----------------------"));
				newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
				newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(itemLevel < 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + this.getXP() + " &6/ &b&l" + maxxp + "&a]"));
					newLore.add(this.getNewXPBar(this.getXP(), maxxp));
				}
				else if(itemLevel == 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
				}
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(itemLevel >= 6) {
					int loreRequired = itemLevel - 4;
					int levelRequired = loreRequired * 5;
					newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
				}
				newLore.add(new CCT().colorize("&7Rarity: " + rarity));
				meta.setLore(newLore);
				i.setItemMeta(meta);
				this.getPlayer().getInventory().setBoots(i);
				//Weapon Data
				if(dfManager.contains(this.getPlayer())) {
					DFPlayer dfPlayer = dfManager.getEntity(this.getPlayer());
					dfPlayer.runDefense();
				}
			}
			else if(this.getEquipmentSlot() == EquipmentSlot.OFF_HAND) {
				ItemStack i = this.getItemStack();
				NBTItem item = new NBTItem(i);
				int itemLevel = item.getInteger("Level");
				int maxxp = item.getInteger("MAXXP");
				int total = item.getInteger("TotalXP");	
				String name = item.getString("ItemName");
				String rarity = item.getString("Rarity");
				String enchantmentString = item.getString("EnchantmentString");
				double baseDamage = item.getDouble("Base Armor Toughness");
				double baseSpeed = item.getDouble("Base Cooldown");
				double incDamage = item.getDouble("Inc Armor Toughness");
				double incSpeed = item.getDouble("Inc Cooldown");
				this.getPlayer().getWorld().playSound(this.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, (float)2, (float)1.5);
				for(int x = itemLevel + 1; x <= 15; x++) {
					if(this.getXP() >= maxxp) {
						itemLevel = x;
						this.setXP(this.getXP() - maxxp);
						total = total + maxxp;
						maxxp = maxxp / 100 * 135;
					}
					else {
						break;
					}
				}
				double value1 = baseDamage + incDamage * (itemLevel - 1);
            	double value2 = baseSpeed - incSpeed * (itemLevel - 1);
				//Config Data
				//Weapon Data
            	item.setInteger("Level", itemLevel);
            	item.setInteger("XP", this.getXP());
            	item.setInteger("MAXXP", maxxp);
            	item.setInteger("TotalXP", total);
            	item.setDouble("Armor Toughness", value1);
            	item.setDouble("Cooldown", value2);
            	i = item.getItem();
				ItemMeta meta = i.getItemMeta();
				meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + itemLevel + "&a]"));
				ArrayList<String> newLore = new ArrayList<String>();
				newLore = enchant.setEnchantments(itemLevel, enchantmentString, newLore);
				double roundOff1 = (double) Math.round(value1 * 100) / 100;
				double roundOff2 = (double) Math.round(value2 * 100) / 100;
				newLore.add(new CCT().colorize("&7-----------------------"));
				newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff1));
				newLore.add(new CCT().colorize("&7Cooldown: &b" + roundOff2 + " Seconds"));
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(itemLevel < 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + this.getXP() + " &6/ &b&l" + maxxp + "&a]"));
					newLore.add(this.getNewXPBar(this.getXP(), maxxp));
				}
				else if(itemLevel == 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
				}
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(itemLevel >= 6) {
					int loreRequired = itemLevel - 4;
					int levelRequired = loreRequired * 5;
					newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
				}
				newLore.add(new CCT().colorize("&7Rarity: " + rarity));
				meta.setLore(newLore);
				i.setItemMeta(meta);
				this.getPlayer().getInventory().setItemInOffHand(i);
				//Weapon Data
				if(dfManager.contains(this.getPlayer())) {
					DFPlayer dfPlayer = dfManager.getEntity(this.getPlayer());
					dfPlayer.runDefense();
				}
			}
			else {
				Player player = this.getPlayer();
				ItemStack i = this.getItemStack();
				ItemStack c = this.getCursorStack();
				NBTItem item = new NBTItem(i);
				NBTItem cursor = new NBTItem(c);
				int levelItem = item.getInteger("Level");
				int xpItem = item.getInteger("XP");
				int xpCursor = cursor.getInteger("XP") + 2500;
				int maxxpItem = item.getInteger("MAXXP");
				int totalXpItem = item.getInteger("TotalXP");
				int totalXpCursor = cursor.getInteger("TotalXP");
				double value1 = 0.0;
            	double value2 = 0.0;
            	double value1Inc = 0.0;
            	double value2Inc = 0.0;
				if(item.hasKey("WeaponKey") || item.hasKey("BowKey")) {
					value1 = item.getDouble("Base Attack Damage");
					value2 = item.getDouble("Base Attack Speed");
					value1Inc = item.getDouble("Inc Attack Damage");
					value2Inc = item.getDouble("Inc Attack Speed");
				}
				else if(item.hasKey("ShieldKey")) {
					value1 = item.getDouble("Base Armor Toughness");
					value2 = item.getDouble("Base Cooldown");
					value1Inc = item.getDouble("Inc Armor Toughness");
					value2Inc = item.getDouble("Inc Cooldown");
				}
				else if(item.hasKey("ArmorKey")) {
					value1 = item.getDouble("Base Armor Defense");
					value2 = item.getDouble("Base Armor Toughness");
					value1Inc = item.getDouble("Inc Armor Defense");
					value2Inc = item.getDouble("Inc Armor Toughness");
				}
            	String name = item.getString("ItemName");
            	String enchantmentsString = item.getString("EnchantmentString");
            	String rarity = item.getString("Rarity");
				int total = xpItem + totalXpCursor + xpCursor;
            	if(total >= maxxpItem) {
            		int addedOn = 0;
            		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, (float)2.00, (float)1.5);
	            	for(int x = levelItem + 1; x <= 15; x++) {
	            		if(total >= maxxpItem) {
	            			total = total - maxxpItem;
	            			levelItem = x;
	            			addedOn = addedOn + maxxpItem;
	            			maxxpItem = maxxpItem / 100 * 135;
	            		}
	            	}
	            	item.setInteger("TotalXP", totalXpItem + addedOn);
            	} 
            	value1 = value1 + value1Inc * (levelItem - 1);
            	value2 = value2 + value2Inc * (levelItem - 1);
            	item.setInteger("Level", levelItem);
            	if(item.hasKey("WeaponKey") || item.hasKey("BowKey")) {
	            	item.setDouble("Attack Damage", value1);
	            	item.setDouble("Attack Speed", value2);
				}
				else if(item.hasKey("ShieldKey")) {
					item.setDouble("Armor Toughness", value1);
					value2 = value2 - value2Inc * (levelItem - 1);
	            	item.setDouble("Cooldown", value2);
				}
				else if(item.hasKey("ArmorKey")) {
					item.setDouble("Armor Defense", value1);
	            	item.setDouble("Armor Toughness", value2);
				}
            	item.setInteger("XP", total);
            	item.setInteger("MAXXP", maxxpItem);
				i = item.getItem();
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, (float)2.00, (float)1.00);
				//Config Data
				//Weapon Data
				ItemMeta meta = i.getItemMeta();
				meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv " + levelItem + "&a]"));
				ArrayList<String> newLore = new ArrayList<String>();
				enchant.setEnchantments(levelItem, enchantmentsString, newLore);
				newLore.add(new CCT().colorize("&7-----------------------"));
				double roundOff1 = (double) Math.round((value1) * 100) / 100;
				double roundOff2 = (double) Math.round((value2) * 100) / 100;
				if(item.hasKey("WeaponKey") || item.hasKey("BowKey")) {
					newLore.add(new CCT().colorize("&7Attack Damage: &6" + roundOff1));
    				newLore.add(new CCT().colorize("&7Attack Speed: &6" + roundOff2));
				}
				else if(item.hasKey("ShieldKey")) {
					newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff1));
    				newLore.add(new CCT().colorize("&7Cooldown: &b" + roundOff2 + " Seconds"));
				}
				else if(item.hasKey("ArmorKey")) {
    				newLore.add(new CCT().colorize("&7Armor Defense: &6" + roundOff1));
					newLore.add(new CCT().colorize("&7Armor Toughness: &6" + roundOff2));
				}
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(levelItem < 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l" + total + " &6/ &b&l" + maxxpItem + "&a]"));
					newLore.add(this.getNewXPBar(total, maxxpItem));
				}
				else if(levelItem == 15) {
					newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&lMAX &6/ &b&lMAX&a]"));
				}
				newLore.add(new CCT().colorize("&7-----------------------"));
				if(levelItem >= 6) {
					int loreRequired = levelItem - 4;
					int levelRequired = loreRequired * 5;
					newLore.add(new CCT().colorize("&7Level Required: &6" + levelRequired));
				}
				newLore.add(new CCT().colorize("&7Rarity: " + rarity));
				meta.setLore(newLore);
				i.setItemMeta(meta);
				player.getItemOnCursor().setAmount(this.getCursorStack().getAmount() -1);
	    		player.getInventory().setItem(this.getClickedSlot(), i);
				player.updateInventory();
			}
		}
	}
	private String getNewXPBar(double xp, double maxxp) {
		double barprogress = (double) xp / (double) maxxp * 100.0;
		String loreString = "&7[&a";
		boolean canStop = true;
		for(double x = 0.00; x <= 100.00; x+=2.00) {
			if(barprogress >= x) {
				loreString = loreString + ":";
			}
			else if(canStop) {
				loreString = loreString + "&7:";
				canStop = false;
			}
			else {
				loreString = loreString + ":";
			}
			if(x == 100) {
				loreString = loreString + "&7] &a" + String.format("%.2f", barprogress) + "%";
			}
		}
		return new CCT().colorize(loreString);
	}

	@Override public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
}
