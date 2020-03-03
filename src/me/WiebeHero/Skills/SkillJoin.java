package me.WiebeHero.Skills;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.DFPlayerPackage.EffectSkills;
import me.WiebeHero.RankedPlayerPackage.RankEnum;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.NBTTagDouble;
import net.minecraft.server.v1_13_R2.NBTTagInt;
import net.minecraft.server.v1_13_R2.NBTTagList;
import net.minecraft.server.v1_13_R2.NBTTagString;

public class SkillJoin implements Listener{
	private DFPlayerManager dfManager;
	private ClassMenu classMenu;
	private EffectSkills es;
	private RankEnum rEnum;
	public SkillJoin(DFPlayerManager manager, ClassMenu menu, EffectSkills es) {
		this.dfManager = manager;
		this.classMenu = menu;
		this.es = es;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void skillJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.teleport(Bukkit.getWorld("DFWarzone-1").getSpawnLocation());
		if(dfManager.contains(player)) {
			DFPlayer dfPlayer = dfManager.getEntity(player);
			if(!dfPlayer.hasPlayerClass()) {
				new BukkitRunnable() {
					public void run() {
						classMenu.ClassSelect(player);
					}
				}.runTaskLater(CustomEnchantments.getInstance(), 10L);
				new BukkitRunnable() {
					public void run() {
						rEnum.getIfPresentKit("USER").recieveKit(player);
						player.getInventory().addItem(apprenticeSword());
						player.getInventory().addItem(divineH());
						player.getInventory().addItem(divineC());
						player.getInventory().addItem(divineL());
						player.getInventory().addItem(divineB());
						player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
						player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 4));
						player.getInventory().addItem(new ItemStack(Material.TORCH, 16));
						player.getInventory().addItem(new ItemStack(Material.OAK_LOG, 64));
						player.getInventory().addItem(new ItemStack(Material.STONE, 64));
						player.getInventory().addItem(new ItemStack(Material.BUCKET, 2));
					}
				}.runTaskLater(CustomEnchantments.getInstance(), 1L);
			}
			else {
				dfPlayer.resetCalculations();
				es.changeHealth(player);
				es.attackSpeed(player);
				es.runDefense(player);
			}
		}
	}
	@EventHandler
	public void respawnActivate(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if(dfManager.contains(player)) {
			DFPlayer dfPlayer = dfManager.getEntity(player);
			if(!dfPlayer.hasPlayerClass()) {
				new BukkitRunnable() {
					public void run() {
						classMenu.ClassSelect(player);
					}
				}.runTaskLater(CustomEnchantments.getInstance(), 10L);
			}
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Divine Helmet
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack divineH() {
		ItemStack item1 = new ItemStack(Material.GOLDEN_HELMET);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new CCT().colorize("&7Apprentice Helmet &a[&6Lv 1&a]"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Armor Defense: &61.5"));
		lore1.add(new CCT().colorize("&7Armor Toughness: &60.65"));
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l5000&a]"));
		lore1.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::] &a0%"));
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Rarity: Common"));
		meta1.setLore(lore1);
		meta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta1.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta1.setUnbreakable(true);
		item1.setItemMeta(meta1);
		net.minecraft.server.v1_13_R2.ItemStack nmsStack1 = CraftItemStack.asNMSCopy(item1);
		NBTTagCompound compound1 = (nmsStack1.hasTag()) ? nmsStack1.getTag() : new NBTTagCompound();
		NBTTagList modifiers1 = new NBTTagList();
		NBTTagCompound defense = new NBTTagCompound();
		defense.set("AttributeName", new NBTTagString("generic.armor"));
		defense.set("Name", new NBTTagString("generic.armor"));
		defense.set("Amount", new NBTTagDouble(0.00));
		defense.set("Operation", new NBTTagInt(0));
		defense.set("UUIDLeast", new NBTTagInt(1));
		defense.set("UUIDMost", new NBTTagInt(1));
		defense.set("Slot", new NBTTagString("head"));
		modifiers1.add(defense);
		NBTTagCompound toughness = new NBTTagCompound();
		toughness.set("AttributeName", new NBTTagString("generic.armorToughness"));
		toughness.set("Name", new NBTTagString("generic.armorToughness"));
		toughness.set("Amount", new NBTTagDouble(0.00));
		toughness.set("Operation", new NBTTagInt(0));
		toughness.set("UUIDLeast", new NBTTagInt(1));
		toughness.set("UUIDMost", new NBTTagInt(1));
		toughness.set("Slot", new NBTTagString("head"));
		modifiers1.add(defense);
		modifiers1.add(toughness);
		compound1.set("AttributeModifiers", modifiers1);
		nmsStack1.setTag(compound1);
		ItemStack newItem = CraftItemStack.asBukkitCopy(nmsStack1);
		NBTItem item = new NBTItem(newItem);
		item.setString("Upgradeable", "");
		item.setString("ArmorKey", "Apprentice Helmet");
		item.setString("EnchantmentString", "Saturation 4//Nurtrition 1");
		item.setInteger("Level", 1);
		item.setInteger("XP", 0);
		item.setInteger("TotalXP", 0);
		item.setInteger("MAXXP", 5000);
		item.setDouble("Base Armor Defense", 1.5);
		item.setDouble("Base Armor Toughness", 0.65);
		item.setDouble("Inc Armor Defense", 0.03);
		item.setDouble("Inc Armor Toughness", 0.025);
		item.setDouble("Armor Defense", 1.5);
		item.setDouble("Armor Toughness", 0.65);
		item.setString("Rarity", "&7Common");
		item.setString("ItemName", "&7Apprentice Helmet");
		item1 = item.getItem();
		return item1;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Divine Chestplate
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack divineC() {
		ItemStack item1 = new ItemStack(Material.GOLDEN_CHESTPLATE);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new CCT().colorize("&7Apprentice Chestplate &a[&6Lv 1&a]"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Armor Defense: &61.5"));
		lore1.add(new CCT().colorize("&7Armor Toughness: &60.65"));
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l5000&a]"));
		lore1.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::] &a0%"));
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Rarity: Common"));
		meta1.setLore(lore1);
		meta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta1.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta1.setUnbreakable(true);
		item1.setItemMeta(meta1);
		net.minecraft.server.v1_13_R2.ItemStack nmsStack1 = CraftItemStack.asNMSCopy(item1);
		NBTTagCompound compound1 = (nmsStack1.hasTag()) ? nmsStack1.getTag() : new NBTTagCompound();
		NBTTagList modifiers1 = new NBTTagList();
		NBTTagCompound defense = new NBTTagCompound();
		defense.set("AttributeName", new NBTTagString("generic.armor"));
		defense.set("Name", new NBTTagString("generic.armor"));
		defense.set("Amount", new NBTTagDouble(0.00));
		defense.set("Operation", new NBTTagInt(0));
		defense.set("UUIDLeast", new NBTTagInt(2));
		defense.set("UUIDMost", new NBTTagInt(1));
		defense.set("Slot", new NBTTagString("chest"));
		modifiers1.add(defense);
		NBTTagCompound toughness = new NBTTagCompound();
		toughness.set("AttributeName", new NBTTagString("generic.armorToughness"));
		toughness.set("Name", new NBTTagString("generic.armorToughness"));
		toughness.set("Amount", new NBTTagDouble(0.00));
		toughness.set("Operation", new NBTTagInt(0));
		toughness.set("UUIDLeast", new NBTTagInt(2));
		toughness.set("UUIDMost", new NBTTagInt(1));
		toughness.set("Slot", new NBTTagString("chest"));
		modifiers1.add(defense);
		modifiers1.add(toughness);
		compound1.set("AttributeModifiers", modifiers1);
		nmsStack1.setTag(compound1);
		ItemStack newItem = CraftItemStack.asBukkitCopy(nmsStack1);
		NBTItem item = new NBTItem(newItem);
		item.setString("Upgradeable", "");
		item.setString("ArmorKey", "Apprentice Chestplate");
		item.setString("EnchantmentString", "Valor 4//Protection 1");
		item.setInteger("Level", 1);
		item.setInteger("XP", 0);
		item.setInteger("TotalXP", 0);
		item.setInteger("MAXXP", 5000);
		item.setDouble("Base Armor Defense", 1.5);
		item.setDouble("Base Armor Toughness", 0.65);
		item.setDouble("Inc Armor Defense", 0.03);
		item.setDouble("Inc Armor Toughness", 0.025);
		item.setDouble("Armor Defense", 1.5);
		item.setDouble("Armor Toughness", 0.65);
		item.setString("Rarity", "&7Common");
		item.setString("ItemName", "&7Apprentice Chestplate");
		item1 = item.getItem();
		return item1;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Divine Leggings
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack divineL() {
		ItemStack item1 = new ItemStack(Material.GOLDEN_LEGGINGS);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new CCT().colorize("&7Apprentice Leggings &a[&6Lv 1&a]"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Armor Defense: &61.5"));
		lore1.add(new CCT().colorize("&7Armor Toughness: &60.65"));
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l5000&a]"));
		lore1.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::] &a0%"));
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Rarity: Common"));
		meta1.setLore(lore1);
		meta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta1.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta1.setUnbreakable(true);
		item1.setItemMeta(meta1);
		net.minecraft.server.v1_13_R2.ItemStack nmsStack1 = CraftItemStack.asNMSCopy(item1);
		NBTTagCompound compound1 = (nmsStack1.hasTag()) ? nmsStack1.getTag() : new NBTTagCompound();
		NBTTagList modifiers1 = new NBTTagList();
		NBTTagCompound defense = new NBTTagCompound();
		defense.set("AttributeName", new NBTTagString("generic.armor"));
		defense.set("Name", new NBTTagString("generic.armor"));
		defense.set("Amount", new NBTTagDouble(0.00));
		defense.set("Operation", new NBTTagInt(0));
		defense.set("UUIDLeast", new NBTTagInt(1));
		defense.set("UUIDMost", new NBTTagInt(2));
		defense.set("Slot", new NBTTagString("legs"));
		modifiers1.add(defense);
		NBTTagCompound toughness = new NBTTagCompound();
		toughness.set("AttributeName", new NBTTagString("generic.armorToughness"));
		toughness.set("Name", new NBTTagString("generic.armorToughness"));
		toughness.set("Amount", new NBTTagDouble(0.00));
		toughness.set("Operation", new NBTTagInt(0));
		toughness.set("UUIDLeast", new NBTTagInt(1));
		toughness.set("UUIDMost", new NBTTagInt(2));
		toughness.set("Slot", new NBTTagString("legs"));
		modifiers1.add(defense);
		modifiers1.add(toughness);
		compound1.set("AttributeModifiers", modifiers1);
		nmsStack1.setTag(compound1);
		ItemStack newItem = CraftItemStack.asBukkitCopy(nmsStack1);
		NBTItem item = new NBTItem(newItem);
		item.setString("Upgradeable", "");
		item.setString("ArmorKey", "Apprentice Leggings");
		item.setString("EnchantmentString", "Ignite 4//Enlightened 1");
		item.setInteger("Level", 1);
		item.setInteger("XP", 0);
		item.setInteger("TotalXP", 0);
		item.setInteger("MAXXP", 5000);
		item.setDouble("Base Armor Defense", 1.5);
		item.setDouble("Base Armor Toughness", 0.65);
		item.setDouble("Inc Armor Defense", 0.03);
		item.setDouble("Inc Armor Toughness", 0.025);
		item.setDouble("Armor Defense", 1.5);
		item.setDouble("Armor Toughness", 0.65);
		item.setString("Rarity", "&7Common");
		item.setString("ItemName", "&7Apprentice Leggings");
		item1 = item.getItem();
		return item1;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Divine Boots
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack divineB() {
		ItemStack item1 = new ItemStack(Material.GOLDEN_BOOTS);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new CCT().colorize("&7Apprentice Boots &a[&6Lv 1&a]"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Armor Defense: &61.5"));
		lore1.add(new CCT().colorize("&7Armor Toughness: &60.65"));
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l5000&a]"));
		lore1.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::] &a0%"));
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Rarity: Common"));
		meta1.setLore(lore1);
		meta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta1.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta1.setUnbreakable(true);
		item1.setItemMeta(meta1);
		net.minecraft.server.v1_13_R2.ItemStack nmsStack1 = CraftItemStack.asNMSCopy(item1);
		NBTTagCompound compound1 = (nmsStack1.hasTag()) ? nmsStack1.getTag() : new NBTTagCompound();
		NBTTagList modifiers1 = new NBTTagList();
		NBTTagCompound defense = new NBTTagCompound();
		defense.set("AttributeName", new NBTTagString("generic.armor"));
		defense.set("Name", new NBTTagString("generic.armor"));
		defense.set("Amount", new NBTTagDouble(0.00));
		defense.set("Operation", new NBTTagInt(0));
		defense.set("UUIDLeast", new NBTTagInt(2));
		defense.set("UUIDMost", new NBTTagInt(2));
		defense.set("Slot", new NBTTagString("feet"));
		modifiers1.add(defense);
		NBTTagCompound toughness = new NBTTagCompound();
		toughness.set("AttributeName", new NBTTagString("generic.armorToughness"));
		toughness.set("Name", new NBTTagString("generic.armorToughness"));
		toughness.set("Amount", new NBTTagDouble(0.00));
		toughness.set("Operation", new NBTTagInt(0));
		toughness.set("UUIDLeast", new NBTTagInt(2));
		toughness.set("UUIDMost", new NBTTagInt(2));
		toughness.set("Slot", new NBTTagString("feet"));
		modifiers1.add(defense);
		modifiers1.add(toughness);
		compound1.set("AttributeModifiers", modifiers1);
		nmsStack1.setTag(compound1);
		ItemStack newItem = CraftItemStack.asBukkitCopy(nmsStack1);
		NBTItem item = new NBTItem(newItem);
		item.setString("Upgradeable", "");
		item.setString("ArmorKey", "Apprentice Boots");
		item.setString("EnchantmentString", "Ignite 4//Enlightened 1");
		item.setInteger("Level", 1);
		item.setInteger("XP", 0);
		item.setInteger("TotalXP", 0);
		item.setInteger("MAXXP", 5000);
		item.setDouble("Base Armor Defense", 1.5);
		item.setDouble("Base Armor Toughness", 0.65);
		item.setDouble("Inc Armor Defense", 0.03);
		item.setDouble("Inc Armor Toughness", 0.025);
		item.setDouble("Armor Defense", 1.5);
		item.setDouble("Armor Toughness", 0.65);
		item.setString("Rarity", "&7Common");
		item.setString("ItemName", "&7Apprentice Leggings");
		item1 = item.getItem();
		item1 = item.getItem();
		return item1;
	}
	public ItemStack apprenticeSword() {
		ItemStack item1 = new ItemStack(Material.IRON_SWORD);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new CCT().colorize("&7Apprentice Sword &a[&6Lv 1&a]"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&9Weakness 1"));
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Attack Damage: &65.00"));
		lore1.add(new CCT().colorize("&7Attack Speed: &60.80"));
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l5000&a]"));
		lore1.add(new CCT().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::] &a0%"));
		lore1.add(new CCT().colorize("&7-----------------------"));
		lore1.add(new CCT().colorize("&7Rarity: Common"));
		meta1.setLore(lore1);
		meta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta1.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta1.setUnbreakable(true);
		item1.setItemMeta(meta1);
		net.minecraft.server.v1_13_R2.ItemStack nmsStack1 = CraftItemStack.asNMSCopy(item1);
		NBTTagCompound compound1 = (nmsStack1.hasTag()) ? nmsStack1.getTag() : new NBTTagCompound();
		NBTTagList modifiers1 = new NBTTagList();
		NBTTagCompound damage = new NBTTagCompound();
		damage.set("AttributeName", new NBTTagString("generic.attackDamage"));
		damage.set("Name", new NBTTagString("generic.attackDamage"));
		damage.set("Amount", new NBTTagDouble(0));
		damage.set("Operation", new NBTTagInt(0));
		damage.set("UUIDLeast", new NBTTagInt(10000));
		damage.set("UUIDMost", new NBTTagInt(10000));
		damage.set("Slot", new NBTTagString("mainhand"));
		NBTTagCompound speed = new NBTTagCompound();
        speed.set("AttributeName", new NBTTagString("generic.attackSpeed"));
        speed.set("Name", new NBTTagString("generic.attackSpeed"));
        speed.set("Amount", new NBTTagDouble(0));
        speed.set("Operation", new NBTTagInt(0));
        speed.set("UUIDLeast", new NBTTagInt(10001));
        speed.set("UUIDMost", new NBTTagInt(10001));
        speed.set("Slot", new NBTTagString("mainhand"));
		modifiers1.add(damage);
		modifiers1.add(speed);
		compound1.set("AttributeModifiers", modifiers1);
		nmsStack1.setTag(compound1);
		ItemStack newItem = CraftItemStack.asBukkitCopy(nmsStack1);
		NBTItem item = new NBTItem(newItem);
		item.setString("Upgradeable", "");
		item.setString("WeaponKey", "Apprentice Helmet");
		item.setString("EnchantmentString", "Weakness 4//Slow 1");
		item.setInteger("Level", 1);
		item.setInteger("XP", 0);
		item.setInteger("MAXXP", 5000);
		item.setInteger("TotalXP", 0);
		item.setDouble("Base Attack Damage", 5.0);
		item.setDouble("Base Attack Speed", 0.8);
		item.setDouble("Inc Attack Damage", 0.17857142857142858);
		item.setDouble("Inc Attack Speed", 0.014285714285714285);
		item.setDouble("Attack Damage", 5.0);
		item.setDouble("Attack Speed", 0.8);
		item.setString("Rarity", "&7Common");
		item.setString("ItemName", "&7Apprentice Sword");
		item1 = item.getItem();
		return item1;
	}
}