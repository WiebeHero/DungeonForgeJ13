package Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
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

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
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
			h.changeHealth(player);
		}
		else {
			player.teleport(player.getWorld().getSpawnLocation());
			HealthH h = new HealthH();
			h.changeHealth(player);
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
		compound1.set("Unbreakable", new NBTTagByte((byte) 1));
		nmsStack1.setTag(compound1);
		return item1 = CraftItemStack.asBukkitCopy(nmsStack1);
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
		compound1.set("Unbreakable", new NBTTagByte((byte) 1));
		nmsStack1.setTag(compound1);
		return item1 = CraftItemStack.asBukkitCopy(nmsStack1);
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
		compound1.set("Unbreakable", new NBTTagByte((byte) 1));
		nmsStack1.setTag(compound1);
		return item1 = CraftItemStack.asBukkitCopy(nmsStack1);
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
		compound1.set("Unbreakable", new NBTTagByte((byte) 1));
		nmsStack1.setTag(compound1);
		return item1 = CraftItemStack.asBukkitCopy(nmsStack1);
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
		item1.setItemMeta(meta1);
		meta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta1.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
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
		compound1.set("Unbreakable", new NBTTagByte((byte) 1));
		nmsStack1.setTag(compound1);
		return item1 = CraftItemStack.asBukkitCopy(nmsStack1);
	}
	public HashMap<UUID, Integer> getADList(){
		return SkillJoin.ad;
	}
	public HashMap<UUID, Double> getADCalList(){
		return SkillJoin.adCal;
	}
	public HashMap<UUID, Double> getADExtraList(){
		return SkillJoin.adExtra;
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