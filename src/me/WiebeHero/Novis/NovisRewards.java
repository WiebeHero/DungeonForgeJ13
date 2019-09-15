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

import de.tr7zw.itemnbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
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
			String check = config.getString("Items.Weapons." + listItemsWep.get(i) + ".Rarity");
			String type = config.getString("Items.Weapons." + listItemsWep.get(i) + ".Type");
			ItemStack item = new ItemStack(Material.getMaterial(type), 1);
			ItemMeta meta = item.getItemMeta();
			if(check.equals("Common")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&7" + listItemsWep.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Rare")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&a" + listItemsWep.get(i) + " &a[&6Lv 1&a]"));		
			}
			else if(check.equals("Epic")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&b" + listItemsWep.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Legendary")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&c" + listItemsWep.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Mythic")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&5" + listItemsWep.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Heroic")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&e" + listItemsWep.get(i) + " &a[&6Lv 1&a]"));
			}
			String enchantmentsString = config.getString("Items.Weapons." + listItemsWep.get(i) + ".Enchantments");
			double damageWeapon = config.getDouble("Items.Weapons." + listItemsWep.get(i) + ".BeginDamage");
			double speedWeapon = config.getDouble("Items.Weapons." + listItemsWep.get(i) + ".BeginSpeed");
			ArrayList<String> newLore = new ArrayList<String>();
			newLore = enchant.setEnchantments(1, enchantmentsString, check, newLore);
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Attack Damage: &6" + damageWeapon));
			newLore.add(new ColorCodeTranslator().colorize("&7Attack Speed: &6" + speedWeapon));
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l3000&a]"));
			newLore.add(new ColorCodeTranslator().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Rarity: " + check));
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
			tempItem.setDouble("Attack Damage", damageWeapon);
			tempItem.setDouble("Attack Speed", speedWeapon);
			item = tempItem.getItem();
			if(check.equals("Common")) {
				rewards1.add(item);
			}
			else if(check.equals("Rare")) {
				rewards2.add(item);
			}
			else if(check.equals("Epic")) {
				rewards3.add(item);
			}
			else if(check.equals("Legendary")) {
				rewards4.add(item);
			}
			else if(check.equals("Mythic")) {
				rewards5.add(item);
			}
			else if(check.equals("Heroic")) {
				rewards6.add(item);
			}
		}
		Set<String> configListBow = config.getConfigurationSection("Items.Bows").getKeys(false);
		ArrayList<String> listItemsBows = new ArrayList<String>(configListBow);
		for(int i = 0; i < listItemsBows.size(); i++) {
			String check = config.getString("Items.Bows." + listItemsBows.get(i) + ".Rarity");
			String type = config.getString("Items.Bows." + listItemsBows.get(i) + ".Type");
			ItemStack item = new ItemStack(Material.getMaterial(type), 1);
			ItemMeta meta = item.getItemMeta();
			if(check.equals("Common")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&7" + listItemsBows.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Rare")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&a" + listItemsBows.get(i) + " &a[&6Lv 1&a]"));		
			}
			else if(check.equals("Epic")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&b" + listItemsBows.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Legendary")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&c" + listItemsBows.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Mythic")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&5" + listItemsBows.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Heroic")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&e" + listItemsBows.get(i) + " &a[&6Lv 1&a]"));
			}
			String enchantmentsString = config.getString("Items.Bows." + listItemsBows.get(i) + ".Enchantments");
			double damageWeapon = config.getDouble("Items.Bows." + listItemsBows.get(i) + ".BeginDamage");
			double drawSpeedWeapon = config.getDouble("Items.Bows." + listItemsBows.get(i) + ".BeginSpeed");
			ArrayList<String> newLore = new ArrayList<String>();
			newLore = enchant.setEnchantments(1, enchantmentsString, check, newLore);
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Attack Damage: &6" + damageWeapon));
			newLore.add(new ColorCodeTranslator().colorize("&7Attack Speed: &6" + drawSpeedWeapon));
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l3000&a]"));
			newLore.add(new ColorCodeTranslator().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Rarity: " + check));
			meta.setLore(newLore);
			meta.setUnbreakable(true);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			item.setItemMeta(meta);
			ItemStack newItem = item;
			NBTItem tempItem = new NBTItem(newItem);
			tempItem.setDouble("Attack Damage", damageWeapon);
			tempItem.setDouble("Attack Speed", drawSpeedWeapon);
			item = tempItem.getItem();
			if(check.equals("Common")) {
				rewards1.add(item);
			}
			else if(check.equals("Rare")) {
				rewards2.add(item);
			}
			else if(check.equals("Epic")) {
				rewards3.add(item);
			}
			else if(check.equals("Legendary")) {
				rewards4.add(item);
			}
			else if(check.equals("Mythic")) {
				rewards5.add(item);
			}
			else if(check.equals("Heroic")) {
				rewards6.add(item);
			}
		}
		Set<String> configListShields = config.getConfigurationSection("Items.Shields").getKeys(false);
		ArrayList<String> listItemsShields = new ArrayList<String>(configListShields);
		for(int i = 0; i < listItemsShields.size(); i++) {
			String check = config.getString("Items.Shields." + listItemsShields.get(i) + ".Rarity");
			String type = config.getString("Items.Shields." + listItemsShields.get(i) + ".Type");
			ItemStack item = new ItemStack(Material.getMaterial(type), 1);
			ItemMeta meta = item.getItemMeta();
			if(check.equals("Common")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&7" + listItemsShields.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Rare")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&a" + listItemsShields.get(i) + " &a[&6Lv 1&a]"));		
			}
			else if(check.equals("Epic")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&b" + listItemsShields.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Legendary")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&c" + listItemsShields.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Mythic")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&5" + listItemsShields.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Heroic")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&e" + listItemsShields.get(i) + " &a[&6Lv 1&a]"));
			}
			String enchantmentsString = config.getString("Items.Shields." + listItemsShields.get(i) + ".Enchantments");
			double damageWeapon = config.getDouble("Items.Shields." + listItemsShields.get(i) + ".BeginToughness");
			double cooldown = config.getDouble("Items.Shields." + listItemsShields.get(i) + ".BeginCooldown");
			ArrayList<String> newLore = new ArrayList<String>();
			newLore = enchant.setEnchantments(1, enchantmentsString, check, newLore);
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Armor Toughness: &6" + damageWeapon));
			newLore.add(new ColorCodeTranslator().colorize("&7Cooldown: &b" + cooldown + " Seconds"));
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l3000&a]"));
			newLore.add(new ColorCodeTranslator().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Rarity: " + check));
			meta.setLore(newLore);
			meta.setUnbreakable(true);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			item.setItemMeta(meta);
			NBTItem tempItem = new NBTItem(item);
			tempItem.setDouble("Toughness", damageWeapon);
			tempItem.setDouble("Cooldown", cooldown);
			item = tempItem.getItem();
			if(check.equals("Common")) {
				rewards1.add(item);
			}
			else if(check.equals("Rare")) {
				rewards2.add(item);
			}
			else if(check.equals("Epic")) {
				rewards3.add(item);
			}
			else if(check.equals("Legendary")) {
				rewards4.add(item);
			}
			else if(check.equals("Mythic")) {
				rewards5.add(item);
			}
			else if(check.equals("Heroic")) {
				rewards6.add(item);
			}
		}
		Set<String> configListArmor = config.getConfigurationSection("Items.Armor").getKeys(false);
		ArrayList<String> listItemsArmor = new ArrayList<String>(configListArmor);
		for(int i = 0; i < listItemsArmor.size(); i++) {
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
			meta.setDisplayName(new ColorCodeTranslator().colorize("&7" + listItemsArmor.get(i) + " &a[&6Lv 1&a]"));
			String enchantmentsString = config.getString("Items.Armor." + listItemsArmor.get(i) + ".Enchantments");
			double armorDefense = config.getDouble("Items.Armor." + listItemsArmor.get(i) + ".BeginDefense");
			double armorToughness = config.getDouble("Items.Armor." + listItemsArmor.get(i) + ".BeginToughness");
			if(check.equals("Common")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&7" + listItemsArmor.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Rare")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&a" + listItemsArmor.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Epic")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&b" + listItemsArmor.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Legendary")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&c" + listItemsArmor.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Mythic")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&5" + listItemsArmor.get(i) + " &a[&6Lv 1&a]"));
			}
			else if(check.equals("Heroic")) {
				meta.setDisplayName(new ColorCodeTranslator().colorize("&e" + listItemsArmor.get(i) + " &a[&6Lv 1&a]"));
			}
			ArrayList<String> newLore = new ArrayList<String>();
			newLore = enchant.setEnchantments(1, enchantmentsString, check, newLore);
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Armor Defense: &6" + armorDefense));
			newLore.add(new ColorCodeTranslator().colorize("&7Armor Toughness: &6" + armorToughness));
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l3000&a]"));
			newLore.add(new ColorCodeTranslator().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Rarity: " + check));
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
				damage.set("Operation", new NBTTagInt(3));
				damage.set("UUIDLeast", new NBTTagInt(3));
				speed.set("UUIDLeast", new NBTTagInt(4));
				speed.set("UUIDMost", new NBTTagInt(4));
	  		}
			else if(Material.getMaterial(type) == Material.LEATHER_LEGGINGS || Material.getMaterial(type) == Material.CHAINMAIL_LEGGINGS || Material.getMaterial(type) == Material.IRON_LEGGINGS || Material.getMaterial(type) == Material.GOLDEN_LEGGINGS || Material.getMaterial(type) == Material.DIAMOND_LEGGINGS) {
				damage.set("Slot", new NBTTagString("legs"));
				speed.set("Slot", new NBTTagString("legs"));
				damage.set("Operation", new NBTTagInt(5));
				damage.set("UUIDLeast", new NBTTagInt(5));
				speed.set("UUIDLeast", new NBTTagInt(6));
				speed.set("UUIDMost", new NBTTagInt(6));
			}
			else if(Material.getMaterial(type) == Material.LEATHER_BOOTS || Material.getMaterial(type) == Material.CHAINMAIL_BOOTS || Material.getMaterial(type) == Material.IRON_BOOTS || Material.getMaterial(type) == Material.GOLDEN_BOOTS || Material.getMaterial(type) == Material.DIAMOND_BOOTS) {
				damage.set("Slot", new NBTTagString("feet"));
				speed.set("Slot", new NBTTagString("feet"));
				damage.set("Operation", new NBTTagInt(7));
				damage.set("UUIDLeast", new NBTTagInt(7));
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
			NBTItem newItem = new NBTItem(item);
			newItem.setDouble("Defense", armorDefense);
			newItem.setDouble("Toughness", armorToughness);
			item = newItem.getItem();
			if(check.equals("Common")) {
				rewards1.add(item);
			}
			else if(check.equals("Rare")) {
				rewards2.add(item);
			}
			else if(check.equals("Epic")) {
				rewards3.add(item);
			}
			else if(check.equals("Legendary")) {
				rewards4.add(item);
			}
			else if(check.equals("Mythic")) {
				rewards5.add(item);
			}
			else if(check.equals("Heroic")) {
				rewards6.add(item);
			}
		}
	}
}