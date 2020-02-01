package me.WiebeHero.Novis;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.NBTTagDouble;
import net.minecraft.server.v1_13_R2.NBTTagInt;
import net.minecraft.server.v1_13_R2.NBTTagList;
import net.minecraft.server.v1_13_R2.NBTTagString;

public class NovisRewards {
	public static ArrayList<ItemStack> rewards1 = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> rewards2 = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> rewards3 = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> rewards4 = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> rewards5 = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> rewards6 = new ArrayList<ItemStack>();
	public NovisEnchantmentGetting enchant = new NovisEnchantmentGetting();
	public NovisRewards() {
		addRewards();
	}
	public void addRewards() {
		FileConfiguration config = CustomEnchantments.getInstance().getConfig();
		Set<String> configListWep = config.getConfigurationSection("Items.Weapons").getKeys(false);
		ArrayList<String> listItemsWep = new ArrayList<String>(configListWep);
		for(int i = 0; i < listItemsWep.size(); i++) {
			String name = config.getString("Items.Weapons." + listItemsWep.get(i) + ".Name");
			String check = config.getString("Items.Weapons." + listItemsWep.get(i) + ".Rarity");
			String type = config.getString("Items.Weapons." + listItemsWep.get(i) + ".Type");
			ItemStack item = new ItemStack(Material.getMaterial(type), 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv 1&a]"));
			String enchantmentsString = config.getString("Items.Weapons." + listItemsWep.get(i) + ".Enchantments");
			double damageWeapon = config.getDouble("Items.Weapons." + listItemsWep.get(i) + ".BeginDamage");
			double speedWeapon = config.getDouble("Items.Weapons." + listItemsWep.get(i) + ".BeginSpeed");
			double incDamageWeapon = config.getDouble("Items.Weapons." + listItemsWep.get(i) + ".IncDamage");
			double incDamageSpeed = config.getDouble("Items.Weapons." + listItemsWep.get(i) + ".IncSpeed");
			ArrayList<String> newLore = new ArrayList<String>();
			newLore = enchant.setEnchantments(1, enchantmentsString, newLore);
			newLore.add(new CCT().colorize("&7-----------------------"));
			newLore.add(new CCT().colorize("&7Attack Damage: &6" + damageWeapon));
			newLore.add(new CCT().colorize("&7Attack Speed: &6" + speedWeapon));
			newLore.add(new CCT().colorize("&7-----------------------"));
			newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l5000&a]"));
			newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
			newLore.add(new CCT().colorize("&7-----------------------"));
			newLore.add(new CCT().colorize("&7Rarity: " + check));
			meta.setLore(newLore);
			meta.setUnbreakable(true);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			item.setItemMeta(meta);
			//Weapon Data
			net.minecraft.server.v1_13_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
	  	    NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
	  		NBTTagList modifiers = new NBTTagList();
	  		NBTTagCompound damage = new NBTTagCompound();
	  		NBTTagCompound speed = new NBTTagCompound();	
			damage.set("AttributeName", new NBTTagString("generic.attackDamage"));
			damage.set("Name", new NBTTagString("generic.attackDamage"));
			damage.set("Amount", new NBTTagDouble(0));
			damage.set("Operation", new NBTTagInt(0));
			damage.set("UUIDLeast", new NBTTagInt(894654));
			damage.set("UUIDMost", new NBTTagInt(2872));
			damage.set("Slot", new NBTTagString("mainhand"));
			modifiers.add(damage);
			speed.set("AttributeName", new NBTTagString("generic.attackSpeed"));
			speed.set("Name", new NBTTagString("generic.attackSpeed"));
			speed.set("Amount", new NBTTagDouble(0));
			speed.set("Operation", new NBTTagInt(0));
			speed.set("UUIDLeast", new NBTTagInt(10001));
			speed.set("UUIDMost", new NBTTagInt(10001));
			speed.set("Slot", new NBTTagString("mainhand"));
			modifiers.add(speed);
			compound.set("AttributeModifiers", modifiers);
			nmsStack.setTag(compound);
			nmsStack.save(compound);
			ItemStack newItem = CraftItemStack.asBukkitCopy(nmsStack);
			NBTItem tempItem = new NBTItem(newItem);
			tempItem.setString("Upgradeable", "");
			tempItem.setString("WeaponKey", listItemsWep.get(i));
			tempItem.setString("EnchantmentString", enchantmentsString);
			tempItem.setInteger("Level", 1);
			tempItem.setInteger("XP", 0);
			tempItem.setInteger("MAXXP", 5000);
			tempItem.setInteger("TotalXP", 0);
			tempItem.setDouble("Base Attack Damage", damageWeapon);
			tempItem.setDouble("Base Attack Speed", speedWeapon);
			tempItem.setDouble("Inc Attack Damage", incDamageWeapon);
			tempItem.setDouble("Inc Attack Speed", incDamageSpeed);
			tempItem.setDouble("Attack Damage", damageWeapon);
			tempItem.setDouble("Attack Speed", speedWeapon);
			tempItem.setString("Rarity", check);
			tempItem.setString("ItemName", name);
			item = tempItem.getItem();
			if(check.contains("Common")) {
				rewards1.add(item);
			}
			else if(check.contains("Rare")) {
				rewards2.add(item);
			}
			else if(check.contains("Epic")) {
				rewards3.add(item);
			}
			else if(check.contains("Legendary")) {
				rewards4.add(item);
			}
			else if(check.contains("Mythic")) {
				rewards5.add(item);
			}
			else if(check.contains("Heroic")) {
				rewards6.add(item);
			}
		}
		Set<String> configListBow = config.getConfigurationSection("Items.Bows").getKeys(false);
		ArrayList<String> listItemsBows = new ArrayList<String>(configListBow);
		for(int i = 0; i < listItemsBows.size(); i++) {
			String name = config.getString("Items.Bows." + listItemsBows.get(i) + ".Name");
			String check = config.getString("Items.Bows." + listItemsBows.get(i) + ".Rarity");
			String type = config.getString("Items.Bows." + listItemsBows.get(i) + ".Type");
			ItemStack item = new ItemStack(Material.getMaterial(type), 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv 1&a]"));
			String enchantmentsString = config.getString("Items.Bows." + listItemsBows.get(i) + ".Enchantments");
			double damageWeapon = config.getDouble("Items.Bows." + listItemsBows.get(i) + ".BeginDamage");
			double drawSpeedWeapon = config.getDouble("Items.Bows." + listItemsBows.get(i) + ".BeginSpeed");
			double incDamage = config.getDouble("Items.Bows." + listItemsBows.get(i) + ".IncDamage");
			double incSpeed = config.getDouble("Items.Bows." + listItemsBows.get(i) + ".IncSpeed");
			ArrayList<String> newLore = new ArrayList<String>();
			newLore = enchant.setEnchantments(1, enchantmentsString, newLore);
			newLore.add(new CCT().colorize("&7-----------------------"));
			newLore.add(new CCT().colorize("&7Attack Damage: &6" + damageWeapon));
			newLore.add(new CCT().colorize("&7Attack Speed: &6" + drawSpeedWeapon));
			newLore.add(new CCT().colorize("&7-----------------------"));
			newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l5000&a]"));
			newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
			newLore.add(new CCT().colorize("&7-----------------------"));
			newLore.add(new CCT().colorize("&7Rarity: " + check));
			meta.setLore(newLore);
			meta.setUnbreakable(true);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			item.setItemMeta(meta);
			ItemStack newItem = item;
			NBTItem tempItem = new NBTItem(newItem);
			tempItem.setString("Upgradeable", "");
			tempItem.setString("BowKey", listItemsBows.get(i));
			tempItem.setString("EnchantmentString", enchantmentsString);
			tempItem.setInteger("Level", 1);
			tempItem.setInteger("XP", 0);
			tempItem.setInteger("MAXXP", 5000);
			tempItem.setInteger("TotalXP", 0);
			tempItem.setDouble("Base Attack Damage", damageWeapon);
			tempItem.setDouble("Base Attack Speed", drawSpeedWeapon);
			tempItem.setDouble("Inc Attack Damage", incDamage);
			tempItem.setDouble("Inc Attack Speed", incSpeed);
			tempItem.setDouble("Attack Damage", damageWeapon);
			tempItem.setDouble("Attack Speed", drawSpeedWeapon);
			tempItem.setString("Rarity", check);
			tempItem.setString("ItemName", name);
			item = tempItem.getItem();
			if(check.contains("Common")) {
				rewards1.add(item);
			}
			else if(check.contains("Rare")) {
				rewards2.add(item);
			}
			else if(check.contains("Epic")) {
				rewards3.add(item);
			}
			else if(check.contains("Legendary")) {
				rewards4.add(item);
			}
			else if(check.contains("Mythic")) {
				rewards5.add(item);
			}
			else if(check.contains("Heroic")) {
				rewards6.add(item);
			}
		}
		Set<String> configListShields = config.getConfigurationSection("Items.Shields").getKeys(false);
		ArrayList<String> listItemsShields = new ArrayList<String>(configListShields);
		for(int i = 0; i < listItemsShields.size(); i++) {
			String name = config.getString("Items.Shields." + listItemsShields.get(i) + ".Name");
			String check = config.getString("Items.Shields." + listItemsShields.get(i) + ".Rarity");
			String type = config.getString("Items.Shields." + listItemsShields.get(i) + ".Type");
			ItemStack item = new ItemStack(Material.getMaterial(type), 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv 1&a]"));
			String enchantmentsString = config.getString("Items.Shields." + listItemsShields.get(i) + ".Enchantments");
			double damageWeapon = config.getDouble("Items.Shields." + listItemsShields.get(i) + ".BeginToughness");
			double cooldown = config.getDouble("Items.Shields." + listItemsShields.get(i) + ".BeginCooldown");
			double incDamageWeapon = config.getDouble("Items.Shields." + listItemsShields.get(i) + ".IncToughness");
			double incCooldown = config.getDouble("Items.Shields." + listItemsShields.get(i) + ".IncCooldown");
			ArrayList<String> newLore = new ArrayList<String>();
			newLore = enchant.setEnchantments(1, enchantmentsString, newLore);
			newLore.add(new CCT().colorize("&7-----------------------"));
			newLore.add(new CCT().colorize("&7Armor Toughness: &6" + damageWeapon));
			newLore.add(new CCT().colorize("&7Cooldown: &b" + cooldown + " Seconds"));
			newLore.add(new CCT().colorize("&7-----------------------"));
			newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l5000&a]"));
			newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
			newLore.add(new CCT().colorize("&7-----------------------"));
			newLore.add(new CCT().colorize("&7Rarity: " + check));
			meta.setLore(newLore);
			meta.setUnbreakable(true);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			item.setItemMeta(meta);
			NBTItem tempItem = new NBTItem(item);
			tempItem.setString("Upgradeable", "");
			tempItem.setString("ShieldKey", listItemsShields.get(i));
			tempItem.setString("EnchantmentString", enchantmentsString);
			tempItem.setInteger("Level", 1);
			tempItem.setInteger("XP", 0);
			tempItem.setInteger("MAXXP", 5000);
			tempItem.setInteger("TotalXP", 0);
			tempItem.setDouble("Base Armor Toughness", damageWeapon);
			tempItem.setDouble("Base Cooldown", cooldown);
			tempItem.setDouble("Inc Armor Toughness", incDamageWeapon);
			tempItem.setDouble("Inc Cooldown", incCooldown);
			tempItem.setDouble("Armor Toughness", damageWeapon);
			tempItem.setDouble("Cooldown", cooldown);
			tempItem.setString("Rarity", check);
			tempItem.setString("ItemName", name);
			item = tempItem.getItem();
			if(check.contains("Common")) {
				rewards1.add(item);
			}
			else if(check.contains("Rare")) {
				rewards2.add(item);
			}
			else if(check.contains("Epic")) {
				rewards3.add(item);
			}
			else if(check.contains("Legendary")) {
				rewards4.add(item);
			}
			else if(check.contains("Mythic")) {
				rewards5.add(item);
			}
			else if(check.contains("Heroic")) {
				rewards6.add(item);
			}
		}
		Set<String> configListArmor = config.getConfigurationSection("Items.Armor").getKeys(false);
		ArrayList<String> listItemsArmor = new ArrayList<String>(configListArmor);
		for(int i = 0; i < listItemsArmor.size(); i++) {
			String name = config.getString("Items.Armor." + listItemsArmor.get(i) + ".Name");
			String check = config.getString("Items.Armor." + listItemsArmor.get(i) + ".Rarity");
			String type = config.getString("Items.Armor." + listItemsArmor.get(i) + ".Type");
			ItemStack item = new ItemStack(Material.getMaterial(type), 1);
			if(item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.LEATHER_BOOTS) {
				LeatherArmorMeta meta1 = (LeatherArmorMeta) item.getItemMeta();
				int red = config.getInt("Items.Armor." + listItemsArmor.get(i) + ".RGB.Red");
				int green = config.getInt("Items.Armor." + listItemsArmor.get(i) + ".RGB.Green");
				int blue = config.getInt("Items.Armor." + listItemsArmor.get(i) + ".RGB.Blue");
				meta1.setColor(Color.fromRGB(red, green, blue));
				item.setItemMeta(meta1);
			}
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(new CCT().colorize("&7" + listItemsArmor.get(i) + " &a[&6Lv 1&a]"));
			String enchantmentsString = config.getString("Items.Armor." + listItemsArmor.get(i) + ".Enchantments");
			double armorDefense = config.getDouble("Items.Armor." + listItemsArmor.get(i) + ".BeginDefense");
			double armorToughness = config.getDouble("Items.Armor." + listItemsArmor.get(i) + ".BeginToughness");
			double incArmorDefense = config.getDouble("Items.Armor." + listItemsArmor.get(i) + ".IncDefense");
			double incArmorToughness = config.getDouble("Items.Armor." + listItemsArmor.get(i) + ".IncToughness");
			meta.setDisplayName(new CCT().colorize(name + " &a[&6Lv 1&a]"));
			ArrayList<String> newLore = new ArrayList<String>();
			newLore = enchant.setEnchantments(1, enchantmentsString, newLore);
			newLore.add(new CCT().colorize("&7-----------------------"));
			newLore.add(new CCT().colorize("&7Armor Defense: &6" + armorDefense));
			newLore.add(new CCT().colorize("&7Armor Toughness: &6" + armorToughness));
			newLore.add(new CCT().colorize("&7-----------------------"));
			newLore.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l5000&a]"));
			newLore.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
			newLore.add(new CCT().colorize("&7-----------------------"));
			newLore.add(new CCT().colorize("&7Rarity: " + check));
			meta.setLore(newLore);
			meta.setUnbreakable(true);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			item.setItemMeta(meta);
			//Weapon Data
			net.minecraft.server.v1_13_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
	  	    NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
	  		NBTTagList modifiers = new NBTTagList();
	  		NBTTagCompound damage = new NBTTagCompound();
	  		NBTTagCompound speed = new NBTTagCompound();
			damage.set("AttributeName", new NBTTagString("generic.armor"));
			damage.set("Name", new NBTTagString("generic.armor"));
			damage.set("Amount", new NBTTagDouble(0));
			damage.set("Operation", new NBTTagInt(0));
			if(Material.getMaterial(type) == Material.LEATHER_HELMET || Material.getMaterial(type) == Material.CHAINMAIL_HELMET || Material.getMaterial(type) == Material.IRON_HELMET || Material.getMaterial(type) == Material.GOLDEN_HELMET || Material.getMaterial(type) == Material.DIAMOND_HELMET) {
				damage.set("Slot", new NBTTagString("head"));
				speed.set("Slot", new NBTTagString("head"));
				damage.set("UUIDLeast", new NBTTagInt(1));
				damage.set("UUIDMost", new NBTTagInt(1));
				speed.set("UUIDLeast", new NBTTagInt(2));
				speed.set("UUIDMost", new NBTTagInt(2));
			}
			else if(Material.getMaterial(type) == Material.LEATHER_CHESTPLATE || Material.getMaterial(type) == Material.CHAINMAIL_CHESTPLATE || Material.getMaterial(type) == Material.IRON_CHESTPLATE || Material.getMaterial(type) == Material.GOLDEN_CHESTPLATE || Material.getMaterial(type) == Material.DIAMOND_CHESTPLATE) {
				damage.set("Slot", new NBTTagString("chest"));
				speed.set("Slot", new NBTTagString("chest"));
				damage.set("UUIDLeast", new NBTTagInt(3));
				damage.set("UUIDMost", new NBTTagInt(3));
				speed.set("UUIDLeast", new NBTTagInt(4));
				speed.set("UUIDMost", new NBTTagInt(4));
	  		}
			else if(Material.getMaterial(type) == Material.LEATHER_LEGGINGS || Material.getMaterial(type) == Material.CHAINMAIL_LEGGINGS || Material.getMaterial(type) == Material.IRON_LEGGINGS || Material.getMaterial(type) == Material.GOLDEN_LEGGINGS || Material.getMaterial(type) == Material.DIAMOND_LEGGINGS) {
				damage.set("Slot", new NBTTagString("legs"));
				speed.set("Slot", new NBTTagString("legs"));
				damage.set("UUIDLeast", new NBTTagInt(5));
				damage.set("UUIDMost", new NBTTagInt(5));
				speed.set("UUIDLeast", new NBTTagInt(6));
				speed.set("UUIDMost", new NBTTagInt(6));
			}
			else if(Material.getMaterial(type) == Material.LEATHER_BOOTS || Material.getMaterial(type) == Material.CHAINMAIL_BOOTS || Material.getMaterial(type) == Material.IRON_BOOTS || Material.getMaterial(type) == Material.GOLDEN_BOOTS || Material.getMaterial(type) == Material.DIAMOND_BOOTS) {
				damage.set("Slot", new NBTTagString("feet"));
				speed.set("Slot", new NBTTagString("feet"));
				damage.set("UUIDLeast", new NBTTagInt(7));
				damage.set("UUIDMost", new NBTTagInt(7));
				speed.set("UUIDLeast", new NBTTagInt(8));
				speed.set("UUIDMost", new NBTTagInt(8));
			}
			modifiers.add(damage);
			speed.set("AttributeName", new NBTTagString("generic.armorToughness"));
			speed.set("Name", new NBTTagString("generic.armorToughness"));
			speed.set("Amount", new NBTTagDouble(0));
			speed.set("Operation", new NBTTagInt(0));
			modifiers.add(speed);
			compound.set("AttributeModifiers", modifiers);
			nmsStack.setTag(compound);
			nmsStack.save(compound);
			item = CraftItemStack.asCraftMirror(nmsStack);
			NBTItem tempItem = new NBTItem(item);
			tempItem.setString("Upgradeable", "");
			tempItem.setString("ArmorKey", listItemsArmor.get(i));
			tempItem.setString("EnchantmentString", enchantmentsString);
			tempItem.setInteger("Level", 1);
			tempItem.setInteger("XP", 0);
			tempItem.setInteger("TotalXP", 0);
			tempItem.setInteger("MAXXP", 5000);
			tempItem.setDouble("Base Armor Defense", armorDefense);
			tempItem.setDouble("Base Armor Toughness", armorToughness);
			tempItem.setDouble("Inc Armor Defense", incArmorDefense);
			tempItem.setDouble("Inc Armor Toughness", incArmorToughness);
			tempItem.setDouble("Armor Defense", armorDefense);
			tempItem.setDouble("Armor Toughness", armorToughness);
			tempItem.setString("Rarity", check);
			tempItem.setString("ItemName", name);
			item = tempItem.getItem();
			if(check.contains("Common")) {
				rewards1.add(item);
			}
			else if(check.contains("Rare")) {
				rewards2.add(item);
			}
			else if(check.contains("Epic")) {
				rewards3.add(item);
			}
			else if(check.contains("Legendary")) {
				rewards4.add(item);
			}
			else if(check.contains("Mythic")) {
				rewards5.add(item);
			}
			else if(check.contains("Heroic")) {
				rewards6.add(item);
			}
		}
	}
}