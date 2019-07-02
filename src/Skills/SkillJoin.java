package Skills;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.itemnbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.MethodAttack;
import me.WiebeHero.CustomMethods.MethodAttackSpeed;
import me.WiebeHero.CustomMethods.MethodCritical;
import me.WiebeHero.CustomMethods.MethodDefense;
import me.WiebeHero.CustomMethods.MethodHealth;
import me.WiebeHero.CustomMethods.MethodRanged;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_13_R2.NBTTagByte;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.NBTTagDouble;
import net.minecraft.server.v1_13_R2.NBTTagInt;
import net.minecraft.server.v1_13_R2.NBTTagList;
import net.minecraft.server.v1_13_R2.NBTTagString;

public class SkillJoin implements Listener{
	public ClassMenu classMenu = new ClassMenu();
	public static HashMap<UUID, String> classes = new HashMap<UUID, String>();
	public static HashMap<UUID, Integer> levels = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> skillPoints = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> xp = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> maxxp = new HashMap<UUID, Integer>();
	//Attribute Modifier Wrath
	public static HashMap<UUID, Integer> adMod = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> asMod = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> ccMod = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> rdMod = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> hhMod = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> dfMod = new HashMap<UUID, Integer>();
	//Attack Damage
	public static HashMap<UUID, Integer> ad = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Double> adCal = new HashMap<UUID, Double>();
	public static HashMap<UUID, Double> adExtra = new HashMap<UUID, Double>();
	//Attack Speed
	public static HashMap<UUID, Integer> as = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Double> asCal = new HashMap<UUID, Double>();
	public static HashMap<UUID, Double> asExtra = new HashMap<UUID, Double>();
	//Critical Chance
	public static HashMap<UUID, Integer> cc = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Double> ccCal = new HashMap<UUID, Double>();
	public static HashMap<UUID, Double> ccExtra = new HashMap<UUID, Double>();
	//Ranged Damage
	public static HashMap<UUID, Integer> rd = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Double> rdCal = new HashMap<UUID, Double>();
	public static HashMap<UUID, Double> rdExtra = new HashMap<UUID, Double>();
	//Health
	public static HashMap<UUID, Integer> hh = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Double> hhCal = new HashMap<UUID, Double>();
	public static HashMap<UUID, Double> hhExtra = new HashMap<UUID, Double>();
	//Defense
	public static HashMap<UUID, Integer> df = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Double> dfCal = new HashMap<UUID, Double>();
	public static HashMap<UUID, Double> dfExtra = new HashMap<UUID, Double>();
	@EventHandler
	public void skillJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!classes.containsKey(player.getUniqueId())) {
			new BukkitRunnable() {
				public void run() {
					classMenu.ClassSelect(player);
				}
			}.runTaskLater(CustomEnchantments.getInstance(), 10L);
			player.getInventory().addItem(apprenticeSword());
			player.getInventory().addItem(divineH());
			player.getInventory().addItem(divineC());
			player.getInventory().addItem(divineL());
			player.getInventory().addItem(divineB());
			player.teleport(player.getWorld().getSpawnLocation());
			HealthH h = new HealthH();
			h.updateHealth(player);
		}
		else {
			player.teleport(player.getWorld().getSpawnLocation());
			ClassC c = new ClassC();
			c.registerClass(player.getUniqueId());
			HealthH h = new HealthH();
			h.updateHealth(player);
		}
	}
	@EventHandler
	public void respawnActivate(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if(!classes.containsKey(player.getUniqueId())) {
			new BukkitRunnable() {
				public void run() {
					classMenu.ClassSelect(player);
				}
			}.runTaskLater(CustomEnchantments.getInstance(), 10L);
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Divine Helmet
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack divineH() {
		ItemStack item1 = new ItemStack(Material.GOLDEN_HELMET);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&7Apprentice Helmet &a[&6Lv 1&a]"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Armor Defense: &60.50"));
		lore1.add(new ColorCodeTranslator().colorize("&7Armor Toughness: &60.20"));
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l1000&a]"));
		lore1.add(new ColorCodeTranslator().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::] &a0%"));
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: Common"));
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
		item.setDouble("Defense", 0.50);
		item.setDouble("Toughness", 0.20);
		item1 = item.getItem();
		return item1;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Divine Chestplate
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack divineC() {
		ItemStack item1 = new ItemStack(Material.GOLDEN_CHESTPLATE);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&7Apprentice Chestplate &a[&6Lv 1&a]"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Armor Defense: &60.50"));
		lore1.add(new ColorCodeTranslator().colorize("&7Armor Toughness: &60.20"));
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l1000&a]"));
		lore1.add(new ColorCodeTranslator().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::] &a0%"));
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: Common"));
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
		item.setDouble("Defense", 0.50);
		item.setDouble("Toughness", 0.20);
		item1 = item.getItem();
		return item1;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Divine Leggings
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack divineL() {
		ItemStack item1 = new ItemStack(Material.GOLDEN_LEGGINGS);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&7Apprentice Leggings &a[&6Lv 1&a]"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Armor Defense: &60.50"));
		lore1.add(new ColorCodeTranslator().colorize("&7Armor Toughness: &60.20"));
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l1000&a]"));
		lore1.add(new ColorCodeTranslator().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::] &a0%"));
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: Common"));
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
		item.setDouble("Defense", 0.50);
		item.setDouble("Toughness", 0.20);
		item1 = item.getItem();
		return item1;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Divine Boots
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack divineB() {
		ItemStack item1 = new ItemStack(Material.GOLDEN_BOOTS);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&7Apprentice Boots &a[&6Lv 1&a]"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Armor Defense: &60.50"));
		lore1.add(new ColorCodeTranslator().colorize("&7Armor Toughness: &60.20"));
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l1000&a]"));
		lore1.add(new ColorCodeTranslator().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::] &a0%"));
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: Common"));
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
		item.setDouble("Defense", 0.50);
		item.setDouble("Toughness", 0.20);
		item1 = item.getItem();
		return item1;
	}
	public ItemStack apprenticeSword() {
		ItemStack item1 = new ItemStack(Material.IRON_SWORD);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&7Apprentice Sword &a[&6Lv 1&a]"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&9Weakness 1"));
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Attack Damage: &65.00"));
		lore1.add(new ColorCodeTranslator().colorize("&7Attack Speed: &60.80"));
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l3000&a]"));
		lore1.add(new ColorCodeTranslator().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::] &a0%"));
		lore1.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: Common"));
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
		item.setDouble("Attack Damage", 5.0);
		item.setDouble("Attack Speed", 0.8);
		item1 = item.getItem();
		return item1;
	}
	public void loadSkilledProfiles(YamlConfiguration yml, File f) {
		Set<String> set = yml.getConfigurationSection("Skills.Players").getKeys(false);
		ArrayList<String> players = new ArrayList<String>(set);
		for(int i = 0; i < players.size(); i++) {
			UUID id = UUID.fromString(players.get(i));
			Bukkit.getServer().getConsoleSender().sendMessage(id + "");
			String className = yml.getString("Skills.Players." + id + ".Class");
			int level = yml.getInt("Skills.Players." + id + ".Level");
			int xpA = yml.getInt("Skills.Players." + id + ".XP");
			int maxxpA = yml.getInt("Skills.Players." + id + ".MXP");
			int skillPoint = yml.getInt("Skills.Players." + id + ".Skill Points");
			int adA = yml.getInt("Skills.Players." + id + ".Attack Damage");
			int asA = yml.getInt("Skills.Players." + id + ".Attack Speed");
			int ccA = yml.getInt("Skills.Players." + id + ".Critical Chance");
			int rdA = yml.getInt("Skills.Players." + id + ".Ranged Damage");
			int hhA = yml.getInt("Skills.Players." + id + ".Health");
			int dfA = yml.getInt("Skills.Players." + id + ".Defense");
			int adM = yml.getInt("Skills.Players." + id + ".Attack Modifier");
			int asM = yml.getInt("Skills.Players." + id + ".Speed Modifier");
			int ccM = yml.getInt("Skills.Players." + id + ".Critical Modifier");
			int rdM = yml.getInt("Skills.Players." + id + ".Ranged Modifier");
			int hhM = yml.getInt("Skills.Players." + id + ".Health Modifier");
			int dfM = yml.getInt("Skills.Players." + id + ".Defense Modifier");
			classes.put(id, className);
			levels.put(id, level);
			xp.put(id, xpA);
			maxxp.put(id, maxxpA);
			skillPoints.put(id, skillPoint);
			ad.put(id, adA);
			as.put(id, asA);
			cc.put(id, ccA);
			rd.put(id, rdA);
			hh.put(id, hhA);
			df.put(id, dfA);
			MethodAttack mAT = new MethodAttack();
			MethodAttackSpeed mAS = new MethodAttackSpeed();
			MethodCritical mCC = new MethodCritical();
			MethodRanged mRD = new MethodRanged();
			MethodHealth mHH = new MethodHealth();
			MethodDefense mDF = new MethodDefense();
			mAT.updateAttack(id);
			mAS.updateAttackSpeed(id);
			mCC.updateCriticalChance(id);
			mRD.updateRanged(id);
			mHH.updateHealth(id);
			mDF.updateDefense(id);
			ClassC c = new ClassC();
			c.registerClass(id);
			adExtra.put(id, 0.0);
			asExtra.put(id, 0.0);
			ccExtra.put(id, 0.0);
			rdExtra.put(id, 0.0);
			hhExtra.put(id, 0.0);
			dfExtra.put(id, 0.0);
			adMod.put(id, adM);
			asMod.put(id, asM);
			ccMod.put(id, ccM);
			rdMod.put(id, rdM);
			hhMod.put(id, hhM);
			dfMod.put(id, dfM);
		}
	}
	public void saveSkilledProfiles(YamlConfiguration yml, File f) {
		for(Entry<UUID, String> entry : classes.entrySet()) {
			UUID id = entry.getKey();
			String className = entry.getValue();
			int level = levels.get(id);
			int xpA = xp.get(id);
			int maxxpA = maxxp.get(id);
			int skillPoint = skillPoints.get(id);
			int adA = ad.get(id);
			int asA = as.get(id);
			int ccA = cc.get(id);
			int rdA = rd.get(id);
			int hhA = hh.get(id);
			int dfA = df.get(id);
			int adM = adMod.get(id);
			int asM = asMod.get(id);
			int ccM = ccMod.get(id);
			int rdM = rdMod.get(id);
			int hhM = hhMod.get(id);
			int dfM = dfMod.get(id);
			yml.set("Skills.Players." + id.toString() + ".Class", className);
			yml.set("Skills.Players." + id.toString() + ".Level", level);
			yml.set("Skills.Players." + id.toString() + ".XP", xpA);
			yml.set("Skills.Players." + id.toString() + ".MXP", maxxpA);
			yml.set("Skills.Players." + id.toString() + ".Skill Points", skillPoint);
			yml.set("Skills.Players." + id.toString() + ".Attack Damage", adA);
			yml.set("Skills.Players." + id.toString() + ".Attack Speed", asA);
			yml.set("Skills.Players." + id.toString() + ".Critical Chance", ccA);
			yml.set("Skills.Players." + id.toString() + ".Ranged Damage", rdA);
			yml.set("Skills.Players." + id.toString() + ".Health", hhA);
			yml.set("Skills.Players." + id.toString() + ".Defense", dfA);
			yml.set("Skills.Players." + id.toString() + ".Attack Modifier", adM);
			yml.set("Skills.Players." + id.toString() + ".Speed Modifier", asM);
			yml.set("Skills.Players." + id.toString() + ".Critical Modifier", ccM);
			yml.set("Skills.Players." + id.toString() + ".Ranged Modifier", rdM);
			yml.set("Skills.Players." + id.toString() + ".Health Modifier", hhM);
			yml.set("Skills.Players." + id.toString() + ".Defense Modifier", dfM);
		}
		try{
			yml.save(f);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public HashMap<UUID, Integer> getADList(){
		return SkillJoin.ad;
	}
	public HashMap<UUID, Double> getADCalList(){
		return SkillJoin.adCal;
	}
	public HashMap<UUID, Double> getADExtraList(){
		return SkillJoin.asExtra;
	}
	public HashMap<UUID, Integer> getASList(){
		return SkillJoin.as;
	}
	public HashMap<UUID, Double> getASCalList(){
		return SkillJoin.asCal;
	}
	public HashMap<UUID, Double> getASExtraList(){
		return SkillJoin.asExtra;
	}
	public HashMap<UUID, Integer> getCCList(){
		return SkillJoin.cc;
	}
	public HashMap<UUID, Double> getCCCalList(){
		return SkillJoin.ccCal;
	}
	public HashMap<UUID, Double> getCCExtraList(){
		return SkillJoin.ccExtra;
	}
	public HashMap<UUID, Integer> getRDList(){
		return SkillJoin.rd;
	}
	public HashMap<UUID, Double> getRDCalList(){
		return SkillJoin.rdCal;
	}
	public HashMap<UUID, Double> getRDExtraList(){
		return SkillJoin.rdExtra;
	}
	public HashMap<UUID, Integer> getHHList(){
		return SkillJoin.hh;
	}
	public HashMap<UUID, Double> getHHCalList(){
		return SkillJoin.hhCal;
	}
	public HashMap<UUID, Double> getHHExtraList(){
		return SkillJoin.hhExtra;
	}
	public HashMap<UUID, Integer> getDFList(){
		return SkillJoin.df;
	}
	public HashMap<UUID, Double> getDFCalList(){
		return SkillJoin.dfCal;
	}
	public HashMap<UUID, Double> getDFExtraList(){
		return SkillJoin.dfExtra;
	}
	public HashMap<UUID, Integer> getADMODList(){
		return SkillJoin.adMod;
	}
	public HashMap<UUID, Integer> getASMODList(){
		return SkillJoin.asMod;
	}
	public HashMap<UUID, Integer> getCCMODList(){
		return SkillJoin.ccMod;
	}
	public HashMap<UUID, Integer> getRDMODList(){
		return SkillJoin.rdMod;
	}
	public HashMap<UUID, Integer> getHHMODList(){
		return SkillJoin.hhMod;
	}
	public HashMap<UUID, Integer> getDFMODList(){
		return SkillJoin.dfMod;
	}
	public HashMap<UUID, String> getClassList(){
		return SkillJoin.classes;
	}
	public HashMap<UUID, Integer> getLevelList(){
		return SkillJoin.levels;
	}
	public HashMap<UUID, Integer> getXPList(){
		return SkillJoin.xp;
	}
	public HashMap<UUID, Integer> getMXPList(){
		return SkillJoin.maxxp;
	}
	public HashMap<UUID, Integer> getSkillPoints(){
		return SkillJoin.skillPoints;
	}
}