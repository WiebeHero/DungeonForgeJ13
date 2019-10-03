package me.WiebeHero.Spawners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.itemnbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Skills.DFPlayer;
import me.WiebeHero.Skills.EffectSkills;
import me.WiebeHero.Skills.Enums.Classes;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.NBTTagDouble;
import net.minecraft.server.v1_13_R2.NBTTagInt;
import net.minecraft.server.v1_13_R2.NBTTagList;
import net.minecraft.server.v1_13_R2.NBTTagString;

public class SpawnerList implements Listener{
	private static HashMap<UUID, Integer> mobList = new HashMap<UUID, Integer>();
	private HashMap<UUID, Integer> teleportBack = new HashMap<UUID, Integer>();
	private static HashMap<UUID, Integer> levelList = new HashMap<UUID, Integer>();
	private HashMap<Integer, Boolean> goList = new HashMap<Integer, Boolean>();
	private HashMap<Integer, Boolean> bossList = new HashMap<Integer, Boolean>();
	public static HashMap<EntityType, Pair<ArrayList<ItemStack>, ArrayList<String>>> names = new HashMap<EntityType, Pair<ArrayList<ItemStack>, ArrayList<String>>>();
	public DFPlayer method = new DFPlayer();
	public EffectSkills sk = new EffectSkills();
	public void spawnerJoin() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(CustomEnchantments.getInstance().getShutdownState() == false) {
					if(!SetSpawner.getSpawnerLocList().isEmpty()) {
						for(int counter = 1; counter <= SetSpawner.getSpawnerLocList().size(); counter++) {
							if(SetSpawner.getSpawnerLocList().get(counter) != null) {
								Location spawnerLoc = new Location(Bukkit.getWorld(SetSpawner.getSpawnerLocList().get(counter).getWorld().getName()), SetSpawner.getSpawnerLocList().get(counter).getX(), SetSpawner.getSpawnerLocList().get(counter).getY(), SetSpawner.getSpawnerLocList().get(counter).getZ());
			  					spawnerLoc.setPitch(0.0F);
			  					spawnerLoc.setYaw(0.0F);
					  			spawnerLoc.setX(spawnerLoc.getX() + randomLocOffSet());
					  			spawnerLoc.setZ(spawnerLoc.getZ() + randomLocOffSet());
					  			Location rightLoc = SetSpawner.getSpawnerLocList().get(counter);
					  			for(Entity e : rightLoc.getWorld().getNearbyEntities(rightLoc, 40, 40, 40)) {
					  				if(e instanceof Monster) {
					  					if(teleportBack.containsKey(e.getUniqueId()) && teleportBack.get(e.getUniqueId()) == counter) {
					  						Location check2 = e.getLocation();
					  						if(rightLoc.distance(check2) > 25) {
					  							for(double y = spawnerLoc.getY() + 15.00; y > 0.00;) {
					  								y--;
					  								spawnerLoc.setY(y);
					  								if(spawnerLoc.getBlock().getType() != Material.AIR) {
					  									spawnerLoc.add(0, 3.0, 0);
					  									break;
					  								}
					  							}
					  							e.teleport(spawnerLoc);
					  						}
					  					}
					  				}
					  			}
							}
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 50L);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(CustomEnchantments.getInstance().getShutdownState() == false) {
					for(int counter = 1; counter <= SetSpawner.getSpawnerLocList().size(); counter++) {
						boolean check = false;
						Location fixedLoc = SetSpawner.getSpawnerLocList().get(counter);
						for(Entity e1 : fixedLoc.getWorld().getNearbyEntities(fixedLoc, 30, 30, 30)) {
							if(e1 instanceof Player) {
								check = true;
								break;
							}
						}
						if(check == true) {
							if(goList.containsKey(counter)) {
								if(goList.get(counter) == true) {
									if(bossList.get(counter) == true) {
										goList.put(counter, false);
										spawnMob(counter, true);
									}
									else if(bossList.get(counter) == false) {
										goList.put(counter, false);
										spawnMob(counter, false);
									}
								}
							}
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 50L);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(CustomEnchantments.getInstance().getShutdownState() == false) {
					if(!SetSpawner.getSpawnerLocList().isEmpty()) {
						for(int counter = 1; counter <= SetSpawner.getSpawnerLocList().size(); counter++) {
							if(SetSpawner.getTieredList().get(counter) <= 5) {
								goList.put(counter, true);
								bossList.put(counter, false);
							}
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 900L);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(CustomEnchantments.getInstance().getShutdownState() == false) {
					if(!SetSpawner.getSpawnerLocList().isEmpty()) {
						for(int counter = 1; counter <= SetSpawner.getSpawnerLocList().size(); counter++) {
							if(SetSpawner.getTieredList().get(counter) == 6) {
								goList.put(counter, true);
								bossList.put(counter, true);
							}
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 18000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(CustomEnchantments.getInstance().getShutdownState() == false) {
					if(!SetSpawner.getSpawnerLocList().isEmpty()) {
						for(int counter = 1; counter <= SetSpawner.getSpawnerLocList().size(); counter++) {
							if(SetSpawner.getTieredList().get(counter) == 7) {
								goList.put(counter, true);
							}
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 36000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(CustomEnchantments.getInstance().getShutdownState() == false) {
					if(!SetSpawner.getSpawnerLocList().isEmpty()) {
						for(int counter = 1; counter <= SetSpawner.getSpawnerLocList().size(); counter++) {
							if(SetSpawner.getTieredList().get(counter) == 8) {
								goList.put(counter, true);
							}
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 72000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(CustomEnchantments.getInstance().getShutdownState() == false) {
					if(!SetSpawner.getSpawnerLocList().isEmpty()) {
						for(int counter = 1; counter <= SetSpawner.getSpawnerLocList().size(); counter++) {
							if(SetSpawner.getTieredList().get(counter) == 9) {
								goList.put(counter, true);
							}
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 144000L);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(CustomEnchantments.getInstance().getShutdownState() == false) {
					if(!SetSpawner.getSpawnerLocList().isEmpty()) {
						for(int counter = 1; counter <= SetSpawner.getSpawnerLocList().size(); counter++) {
							if(SetSpawner.getTieredList().get(counter) == 10) {
								goList.put(counter, true);
							}
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 216000L);
	}
	public ItemStack mobArmor(Material mat, String name, String enchantment, double def, double tough, boolean boss) {
		ItemStack item1 = new ItemStack(mat, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize(name));
		ArrayList<String> newLore = new ArrayList<String>();
		if(enchantment != null) {
			newLore.add(new ColorCodeTranslator().colorize(enchantment));
		}
		newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		if(!item1.getType().toString().contains("SHIELD")) {
			newLore.add(new ColorCodeTranslator().colorize("&7Armor Defense: &6" + def));
		}
		newLore.add(new ColorCodeTranslator().colorize("&7Armor Toughness: &6" + tough));
		if(boss == true) {
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l3000&a]"));
			newLore.add(new ColorCodeTranslator().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
		}
		newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		if(boss == true) {
			newLore.add(new ColorCodeTranslator().colorize("&7Rarity: &dElite"));
		}
		else {
			newLore.add(new ColorCodeTranslator().colorize("&7Rarity: &fBasic"));
		}
		meta1.setLore(newLore);
		meta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta1.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		if(boss == true) {
			meta1.setUnbreakable(true);
		}
		else if(item1.getMaxItemUseDuration() != 0){
			int dur = item1.getMaxItemUseDuration();
			int rand = new Random().nextInt(dur)+ dur / 100 * 30;
			if(rand <= dur) {
				Damageable met = (Damageable) meta1;
				met.setDamage(rand);
			}
			else {
				Damageable met = (Damageable) meta1;
				met.setDamage(item1.getMaxItemUseDuration());
			}
		}
		item1.setItemMeta(meta1);
		net.minecraft.server.v1_13_R2.ItemStack nmsStack1 = CraftItemStack.asNMSCopy(item1);
		NBTTagCompound compound1 = (nmsStack1.hasTag()) ? nmsStack1.getTag() : new NBTTagCompound();
		NBTTagList modifiers1 = new NBTTagList();
		NBTTagCompound defense = new NBTTagCompound();
		defense.set("AttributeName", new NBTTagString("generic.armor"));
		defense.set("Name", new NBTTagString("generic.armor"));
		defense.set("Amount", new NBTTagDouble(0.00));
		defense.set("Operation", new NBTTagInt(0));
		if(item1.getType().toString().contains("HELMET") || item1.getType().toString().contains("HEAD")) {
			defense.set("UUIDLeast", new NBTTagInt(1));
			defense.set("UUIDMost", new NBTTagInt(1));
			defense.set("Slot", new NBTTagString("head"));
			modifiers1.add(defense);
		}
		else if(item1.getType().toString().contains("CHESTPLATE")) {
			defense.set("UUIDLeast", new NBTTagInt(2));
			defense.set("UUIDMost", new NBTTagInt(1));
			defense.set("Slot", new NBTTagString("chest"));
			modifiers1.add(defense);
		}
		else if(item1.getType().toString().contains("LEGGINGS")) {
			defense.set("UUIDLeast", new NBTTagInt(1));
			defense.set("UUIDMost", new NBTTagInt(2));
			defense.set("Slot", new NBTTagString("legs"));
			modifiers1.add(defense);
		}
		else if(item1.getType().toString().contains("BOOTS")) {
			defense.set("UUIDLeast", new NBTTagInt(2));
			defense.set("UUIDMost", new NBTTagInt(2));
			defense.set("Slot", new NBTTagString("feet"));
			modifiers1.add(defense);
		}
		NBTTagCompound toughness = new NBTTagCompound();
		toughness.set("AttributeName", new NBTTagString("generic.armorToughness"));
		toughness.set("Name", new NBTTagString("generic.armorToughness"));
		toughness.set("Amount", new NBTTagDouble(0.00));
		toughness.set("Operation", new NBTTagInt(0));
		if(item1.getType().toString().contains("HELMET") || item1.getType().toString().contains("HEAD")) {
			toughness.set("UUIDLeast", new NBTTagInt(1));
			toughness.set("UUIDMost", new NBTTagInt(1));
			toughness.set("Slot", new NBTTagString("head"));
			modifiers1.add(toughness);
			compound1.set("AttributeModifiers", modifiers1);
			nmsStack1.setTag(compound1);
		}
		else if(item1.getType().toString().contains("CHESTPLATE")) {
			toughness.set("UUIDLeast", new NBTTagInt(2));
			toughness.set("UUIDMost", new NBTTagInt(1));
			toughness.set("Slot", new NBTTagString("chest"));
			modifiers1.add(toughness);
			compound1.set("AttributeModifiers", modifiers1);
			nmsStack1.setTag(compound1);
		}
		else if(item1.getType().toString().contains("LEGGINGS")) {
			toughness.set("UUIDLeast", new NBTTagInt(1));
			toughness.set("UUIDMost", new NBTTagInt(2));
			toughness.set("Slot", new NBTTagString("legs"));
			modifiers1.add(toughness);
			compound1.set("AttributeModifiers", modifiers1);
			nmsStack1.setTag(compound1);
		}
		else if(item1.getType().toString().contains("BOOTS")) {
			toughness.set("UUIDLeast", new NBTTagInt(2));
			toughness.set("UUIDMost", new NBTTagInt(2));
			toughness.set("Slot", new NBTTagString("feet"));
			modifiers1.add(toughness);
			compound1.set("AttributeModifiers", modifiers1);
			nmsStack1.setTag(compound1);
		}
		ItemStack newItem = CraftItemStack.asBukkitCopy(nmsStack1);
		NBTItem item = new NBTItem(newItem);
		item.setDouble("Defense", def);
		item.setDouble("Toughness", tough);
		item1 = item.getItem();
		return item1;
	}
	public ItemStack mobArmor(ItemStack stack, String name, String enchantment, double def, double tough, boolean boss) {
		ItemStack item1 = stack;
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize(name));
		ArrayList<String> newLore = new ArrayList<String>();
		if(enchantment != null) {
			newLore.add(new ColorCodeTranslator().colorize(enchantment));
		}
		newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		if(!item1.getType().toString().contains("SHIELD")) {
			newLore.add(new ColorCodeTranslator().colorize("&7Armor Defense: &6" + def));
		}
		newLore.add(new ColorCodeTranslator().colorize("&7Armor Toughness: &6" + tough));
		if(boss == true) {
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l3000&a]"));
			newLore.add(new ColorCodeTranslator().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
		}
		newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		if(boss == true) {
			newLore.add(new ColorCodeTranslator().colorize("&7Rarity: &dElite"));
		}
		else {
			newLore.add(new ColorCodeTranslator().colorize("&7Rarity: &fBasic"));
		}
		meta1.setLore(newLore);
		meta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta1.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		if(boss == true) {
			meta1.setUnbreakable(true);
		}
		else if(item1.getMaxItemUseDuration() != 0){
			int dur = item1.getMaxItemUseDuration();
			int rand = new Random().nextInt(dur)+ dur / 100 * 30;
			if(rand <= dur) {
				Damageable met = (Damageable) meta1;
				met.setDamage(rand);
			}
			else {
				Damageable met = (Damageable) meta1;
				met.setDamage(item1.getMaxItemUseDuration());
			}
		}
		item1.setItemMeta(meta1);
		net.minecraft.server.v1_13_R2.ItemStack nmsStack1 = CraftItemStack.asNMSCopy(item1);
		NBTTagCompound compound1 = (nmsStack1.hasTag()) ? nmsStack1.getTag() : new NBTTagCompound();
		NBTTagList modifiers1 = new NBTTagList();
		NBTTagCompound defense = new NBTTagCompound();
		defense.set("AttributeName", new NBTTagString("generic.armor"));
		defense.set("Name", new NBTTagString("generic.armor"));
		defense.set("Amount", new NBTTagDouble(0.00));
		defense.set("Operation", new NBTTagInt(0));
		if(item1.getType().toString().contains("HELMET") || item1.getType().toString().contains("HEAD")) {
			defense.set("UUIDLeast", new NBTTagInt(1));
			defense.set("UUIDMost", new NBTTagInt(1));
			defense.set("Slot", new NBTTagString("head"));
			modifiers1.add(defense);
		}
		else if(item1.getType().toString().contains("CHESTPLATE")) {
			defense.set("UUIDLeast", new NBTTagInt(2));
			defense.set("UUIDMost", new NBTTagInt(1));
			defense.set("Slot", new NBTTagString("chest"));
			modifiers1.add(defense);
		}
		else if(item1.getType().toString().contains("LEGGINGS")) {
			defense.set("UUIDLeast", new NBTTagInt(1));
			defense.set("UUIDMost", new NBTTagInt(2));
			defense.set("Slot", new NBTTagString("legs"));
			modifiers1.add(defense);
		}
		else if(item1.getType().toString().contains("BOOTS")) {
			defense.set("UUIDLeast", new NBTTagInt(2));
			defense.set("UUIDMost", new NBTTagInt(2));
			defense.set("Slot", new NBTTagString("feet"));
			modifiers1.add(defense);
		}
		NBTTagCompound toughness = new NBTTagCompound();
		toughness.set("AttributeName", new NBTTagString("generic.armorToughness"));
		toughness.set("Name", new NBTTagString("generic.armorToughness"));
		toughness.set("Amount", new NBTTagDouble(0.00));
		toughness.set("Operation", new NBTTagInt(0));
		if(item1.getType().toString().contains("HELMET") || item1.getType().toString().contains("HEAD")) {
			toughness.set("UUIDLeast", new NBTTagInt(1));
			toughness.set("UUIDMost", new NBTTagInt(1));
			toughness.set("Slot", new NBTTagString("head"));
			modifiers1.add(toughness);
			compound1.set("AttributeModifiers", modifiers1);
			nmsStack1.setTag(compound1);
		}
		else if(item1.getType().toString().contains("CHESTPLATE")) {
			toughness.set("UUIDLeast", new NBTTagInt(2));
			toughness.set("UUIDMost", new NBTTagInt(1));
			toughness.set("Slot", new NBTTagString("chest"));
			modifiers1.add(toughness);
			compound1.set("AttributeModifiers", modifiers1);
			nmsStack1.setTag(compound1);
		}
		else if(item1.getType().toString().contains("LEGGINGS")) {
			toughness.set("UUIDLeast", new NBTTagInt(1));
			toughness.set("UUIDMost", new NBTTagInt(2));
			toughness.set("Slot", new NBTTagString("legs"));
			modifiers1.add(toughness);
			compound1.set("AttributeModifiers", modifiers1);
			nmsStack1.setTag(compound1);
		}
		else if(item1.getType().toString().contains("BOOTS")) {
			toughness.set("UUIDLeast", new NBTTagInt(2));
			toughness.set("UUIDMost", new NBTTagInt(2));
			toughness.set("Slot", new NBTTagString("feet"));
			modifiers1.add(toughness);
			compound1.set("AttributeModifiers", modifiers1);
			nmsStack1.setTag(compound1);
		}
		ItemStack newItem = CraftItemStack.asBukkitCopy(nmsStack1);
		NBTItem item = new NBTItem(newItem);
		item.setDouble("Defense", def);
		item.setDouble("Toughness", tough);
		item1 = item.getItem();
		return item1;
	}
	public ItemStack mobWeapon(Material mat, String name, String enchantment, double atk, double spd, boolean boss) {
		ItemStack item1 = new ItemStack(mat, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize(name));
		ArrayList<String> newLore = new ArrayList<String>();
		if(enchantment != null) {
			newLore.add(new ColorCodeTranslator().colorize(enchantment));
		}
		newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		newLore.add(new ColorCodeTranslator().colorize("&7Attack Damage: &6" + atk));
		newLore.add(new ColorCodeTranslator().colorize("&7Attack Speed: &6" + spd));
		if(boss == true) {
			newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
			newLore.add(new ColorCodeTranslator().colorize("&7Upgrade Progress: &a[&b&l0 &6/ &b&l3000&a]"));
			newLore.add(new ColorCodeTranslator().colorize("&7[::::::::::::::::::::::::::::::::::::::::::::::::::&7] &a0%"));
		}
		newLore.add(new ColorCodeTranslator().colorize("&7-----------------------"));
		if(boss == true) {
			newLore.add(new ColorCodeTranslator().colorize("&7Rarity: &dElite"));
		}
		else {
			newLore.add(new ColorCodeTranslator().colorize("&7Rarity: &fBasic"));
		}
		meta1.setLore(newLore);
		meta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta1.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		if(boss == true) {
			meta1.setUnbreakable(true);
		}
		else if(item1.getMaxItemUseDuration() != 0){
			int dur = item1.getMaxItemUseDuration();
			int rand = new Random().nextInt(dur)+ dur / 100 * 30;
			if(rand <= dur) {
				Damageable met = (Damageable) meta1;
				met.setDamage(rand);
			}
			else {
				Damageable met = (Damageable) meta1;
				met.setDamage(item1.getMaxItemUseDuration());
			}
		}
		item1.setItemMeta(meta1);
		net.minecraft.server.v1_13_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(item1);
  	    NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
  		NBTTagList modifiers = new NBTTagList();
  		if(!item1.getType().toString().contains("BOW")) {
  			NBTTagCompound damage = new NBTTagCompound();
			damage.set("AttributeName", new NBTTagString("generic.attackDamage"));
			damage.set("Name", new NBTTagString("generic.attackDamage"));
			damage.set("Amount", new NBTTagDouble(0));
			damage.set("Operation", new NBTTagInt(0));
			damage.set("UUIDLeast", new NBTTagInt(894654));
			damage.set("UUIDMost", new NBTTagInt(2872));
			damage.set("Slot", new NBTTagString("mainhand"));
			modifiers.add(damage);
  		}
		NBTTagCompound speed = new NBTTagCompound();	
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
		NBTItem item = new NBTItem(newItem);
		item.setDouble("Attack Damage", atk);
		item.setDouble("Attack Speed", spd);
		item1 = item.getItem();
		return item1;
	}
	public void spawnMob(int counter, boolean boss) {
		if(SetSpawner.getSpawnerLocList().get(counter) != null) {
			if(boss == false) {
				Location fixedLoc = SetSpawner.getSpawnerLocList().get(counter);
				int countMobs = 0;
				fixedLoc.setWorld(Bukkit.getWorld(SetSpawner.getSpawnerLocList().get(counter).getWorld().getName()));
	  			for(Entity e : fixedLoc.getWorld().getNearbyEntities(fixedLoc, 30, 30, 30)) {
	  				if(e != null) {
	  					if(teleportBack.containsKey(e.getUniqueId())) {
		  					if(teleportBack.get(e.getUniqueId()) == counter) {
		  						countMobs++;
		  					}
	  					}
	  				}
	  			}
	  			int tier = SetSpawner.getTieredList().get(counter);
	  			int temp = (int) Math.round(tier / 2);
	  			int tier1 = new Random().nextInt(temp + 1);
	  			int mobMax = 2 + tier1 - countMobs;
	  			for(int i = 0; i <= mobMax; i++) {
	  				Location spawnLoc = new Location(Bukkit.getWorld(SetSpawner.getSpawnerLocList().get(counter).getWorld().getName()), SetSpawner.getSpawnerLocList().get(counter).getX(), SetSpawner.getSpawnerLocList().get(counter).getY(), SetSpawner.getSpawnerLocList().get(counter).getZ());
	  				double originalY = spawnLoc.getY();
	  				double x = spawnLoc.getX();
	  				double z = spawnLoc.getZ();
					spawnLoc.setX(spawnLoc.getX() + randomLocOffSet());
					spawnLoc.setZ(spawnLoc.getZ() + randomLocOffSet());
	  				for(double y1 = spawnLoc.getY() + 10.00; y1 > 0;) {
	  					y1--;
	  					spawnLoc.setY(y1);
	  					if(spawnLoc.getBlock().getType() != Material.AIR) {
	  						spawnLoc.setY(y1 + 2.00);
	  						if(spawnLoc.getBlock().getType() == Material.AIR) {
	  							break;
	  						}
	  						else {
	  							y1 = originalY + 10.00;
	  							spawnLoc.setX(spawnLoc.getX() + randomLocOffSet());
			  					spawnLoc.setZ(spawnLoc.getZ() + randomLocOffSet());
	  						}
	  					}
	  				}
	  				LivingEntity mob = (LivingEntity) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.valueOf(SetSpawner.getTypeList().get(counter)));
	  				SetSpawner.getSpawnerLocList().get(counter).setX(x);
	  				SetSpawner.getSpawnerLocList().get(counter).setZ(z);
	  				Pair<ArrayList<ItemStack>, ArrayList<String>> list = names.get(EntityType.valueOf(SetSpawner.getTypeList().get(counter)));
	  				int rand = randomHead();
	  				String name = list.getValue().get(rand);
	  				ItemStack head = list.getKey().get(rand);
	  				if(mob instanceof Zombie) {
	  					Zombie zombie = (Zombie) mob;
	  					zombie.setBaby(false);
	  				}
	  				else if(mob instanceof PigZombie) {
	  					Zombie zombie = (Zombie) mob;
	  					zombie.setBaby(false);
	  				}
	  				mob.getEquipment().setHelmet(head);
					int level = (new Random().nextInt(20) + 20 * (tier - 1));
					if(level == 0) {
						level++;
					}
					levelList.put(mob.getUniqueId(), level);
	  				mob.setCustomName(new ColorCodeTranslator().colorize(name + " &b[&6Lv " + levelList.get(mob.getUniqueId()) + "&b]"));
	  				mob.setCustomNameVisible(true);
	  				if(tier == 1) {
	  					mob.getEquipment().setHelmet(this.mobArmor(head, name + "'s Head", null, 1.2, 0.5, false));
	  					if(randomDouble() <= 10 + 0.5 * level) {
	  						mob.getEquipment().setChestplate(this.mobArmor(Material.LEATHER_CHESTPLATE, name + "'s Chestplate", null, 1.2, 0.6, false));
	  					}
	  					if(randomDouble() <= 10 + 0.5 * level) {
	  						mob.getEquipment().setLeggings(this.mobArmor(Material.LEATHER_CHESTPLATE, name + "'s Leggings", null, 1.2, 0.6, false));
	  					}
	  					if(randomDouble() <= 10 + 0.5 * level) {
	  						mob.getEquipment().setBoots(this.mobArmor(Material.LEATHER_CHESTPLATE, name + "'s Boots", null, 1.2, 0.6, false));
	  					}
	  					int dice = dice();
	  					if(dice == 1) {
							mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.WOODEN_SWORD, name + "'s Sword", null, 5.15, 0.37, false));
						}
	  					else if(dice == 2) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.WOODEN_AXE, name + "'s Axe", null, 6.7, 0.305, false));
						}
	  					else if(dice == 3) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.WOODEN_PICKAXE, name + "'s Pickaxe", null, 4.30, 0.43, false));
						}
	  					else if(dice == 4) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.WOODEN_SHOVEL, name + "'s Shovel", null, 3.20, 0.56, false));
						}
	  					else if(dice == 5) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.WOODEN_HOE, name + "'s Hoe", null, 1.8, 1.25, false));
						}
	  				}
	  				else if(tier == 2) {
	  					mob.getEquipment().setHelmet(this.mobArmor(head, name + "'s Head", null, 0.8, 1.2, false));
	  					if(randomDouble() <= 15 + 0.55 * level) {
	  						mob.getEquipment().setChestplate(this.mobArmor(Material.GOLDEN_CHESTPLATE, name + "'s Chestplate", null, 0.8, 1.2, false));
	  					}
	  					if(randomDouble() <= 15 + 0.55 * level) {
	  						mob.getEquipment().setLeggings(this.mobArmor(Material.GOLDEN_LEGGINGS, name + "'s Leggings", null, 0.8, 1.2, false));
	  					}
	  					if(randomDouble() <= 15 + 0.55 * level) {
	  						mob.getEquipment().setBoots(this.mobArmor(Material.GOLDEN_BOOTS, name + "'s Boots", null, 0.8, 1.2, false));
	  					}
	  					int dice = dice();
	  					if(dice == 1) {
							mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.GOLDEN_SWORD, name + "'s Sword", null, 4.0, 0.5, false));
						}
	  					else if(dice == 2) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.GOLDEN_AXE, name + "'s Axe", null, 5.0, 0.45, false));
						}
	  					else if(dice == 3) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.GOLDEN_PICKAXE, name + "'s Pickaxe", null, 3.35, 0.53, false));
						}
	  					else if(dice == 4) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.GOLDEN_SHOVEL, name + "'s Shovel", null, 2.40, 0.64, false));
						}
	  					else if(dice == 5) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.GOLDEN_HOE, name + "'s Hoe", null, 1.5, 1.6, false));
						}
	  				}
	  				else if(tier == 3) {
	  					mob.getEquipment().setHelmet(this.mobArmor(head, name + "'s Head", null, 1.4, 0.7, false));
	  					if(randomDouble() <= 20 + 0.6 * level) {
	  						mob.getEquipment().setChestplate(this.mobArmor(Material.CHAINMAIL_CHESTPLATE, name + "'s Chestplate", null, 1.4, 0.7, false));
	  					}
	  					if(randomDouble() <= 20 + 0.6 * level) {
	  						mob.getEquipment().setLeggings(this.mobArmor(Material.CHAINMAIL_LEGGINGS, name + "'s Leggings", null, 1.4, 0.7, false));
	  					}
	  					if(randomDouble() <= 20 + 0.6 * level) {
	  						mob.getEquipment().setBoots(this.mobArmor(Material.CHAINMAIL_BOOTS, name + "'s Boots", null, 1.4, 0.7, false));
	  					}
	  					int dice = dice();
	  					if(dice == 1) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.STONE_SWORD, name + "'s Sword", null, 5.45, 0.39, false));
						}
	  					else if(dice == 2) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.STONE_AXE, name + "'s Axe", null, 7.1, 0.315, false));
						}
	  					else if(dice == 3) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.STONE_PICKAXE, name + "'s Pickaxe", null, 4.50, 0.44, false));
						}
	  					else if(dice == 4) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.STONE_SHOVEL, name + "'s Shovel", null, 3.30, 0.57, false));
						}
	  					else if(dice == 5) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.STONE_HOE, name + "'s Hoe", null, 2.0, 1.35, false));
						}
	  				}
	  				else if(tier == 4) {
	  					mob.getEquipment().setHelmet(this.mobArmor(head, name + "'s Head", null, 1.6, 0.8, false));
	  					if(randomDouble() <= 25 + 0.65 * level) {
	  						mob.getEquipment().setChestplate(this.mobArmor(Material.IRON_CHESTPLATE, name + "'s Chestplate", null, 1.6, 0.8, false));
	  					}
	  					if(randomDouble() <= 25 + 0.65 * level) {
	  						mob.getEquipment().setLeggings(this.mobArmor(Material.IRON_LEGGINGS, name + "'s Leggings", null, 1.6, 0.8, false));
	  					}
	  					if(randomDouble() <= 25 + 0.65 * level) {
	  						mob.getEquipment().setBoots(this.mobArmor(Material.IRON_BOOTS, name + "'s Boots", null, 1.6, 0.8, false));
	  					}
	  					int dice = dice();
	  					if(dice == 1) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.IRON_SWORD, name + "'s Sword", null, 5.60, 0.40, false));
						}
	  					else if(dice == 2) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.IRON_AXE, name + "'s Axe", null, 7.3, 0.32, false));
						}
	  					else if(dice == 3) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.IRON_PICKAXE, name + "'s Pickaxe", null, 4.70, 0.45, false));
						}
	  					else if(dice == 4) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.IRON_SHOVEL, name + "'s Shovel", null, 3.40, 0.58, false));
						}
	  					else if(dice == 5) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.IRON_HOE, name + "'s Hoe", null, 2.1, 1.40, false));
						}
	  				}
	  				else if(tier == 5) {
	  					mob.getEquipment().setHelmet(this.mobArmor(head, name + "'s Head", null, 1.8, 0.9, false));
	  					if(randomDouble() <= 30 + 0.70 * level) {
	  						mob.getEquipment().setChestplate(this.mobArmor(Material.DIAMOND_CHESTPLATE, name + "'s Chestplate", null, 1.8, 0.9, false));
	  					}
	  					if(randomDouble() <= 30 + 0.70 * level) {
	  						mob.getEquipment().setLeggings(this.mobArmor(Material.DIAMOND_LEGGINGS, name + "'s Leggings", null, 1.8, 0.9, false));
	  					}
	  					if(randomDouble() <= 30 + 0.70 * level) {
	  						mob.getEquipment().setBoots(this.mobArmor(Material.DIAMOND_BOOTS, name + "'s Boots", null, 1.8, 0.9, false));
	  					}
	  					int dice = dice();
	  					if(dice == 1) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.DIAMOND_SWORD, name + "'s Sword", null, 5.9, 0.42, false));
						}
	  					else if(dice == 2) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.DIAMOND_AXE, name + "'s Axe", null, 7.7, 0.33, false));
						}
	  					else if(dice == 3) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.DIAMOND_PICKAXE, name + "'s Pickaxe", null, 4.9, 0.46, false));
						}
	  					else if(dice == 4) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.DIAMOND_SHOVEL, name + "'s Shovel", null, 3.50, 0.60, false));
						}
	  					else if(dice == 5) {
	  						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.DIAMOND_SHOVEL, name + "'s Hoe", null, 2.2, 1.50, false));
						}
	  				}
					method.addPlayer(mob);
					DFPlayer dfPlayer = method.getPlayer(mob);
					dfPlayer.setLevel(level);
					int skills = level * 8;
					int cl = new Random().nextInt(7) + 1;
					switch(cl) {
					case 1:
						dfPlayer.setPlayerClass(Classes.WRATH);
						break;
					case 2:
						dfPlayer.setPlayerClass(Classes.LUST);
						break;
					case 3:
						dfPlayer.setPlayerClass(Classes.GLUTTONY);
						break;
					case 4:
						dfPlayer.setPlayerClass(Classes.GREED);
						break;
					case 5:
						dfPlayer.setPlayerClass(Classes.SLOTH);
						break;
					case 6:
						dfPlayer.setPlayerClass(Classes.ENVY);
						break;
					case 7:
						dfPlayer.setPlayerClass(Classes.PRIDE);
						break;
					default:
						dfPlayer.setPlayerClass(Classes.NONE);
						break;
					}
					for(int m = 0; m <= skills; m++) {
						int random = this.randomAmount(6);
						switch(random) {
						case 1:
							dfPlayer.addAtk(1);
							break;
						case 2:
							dfPlayer.addSpd(1);
							break;
						case 3:
							dfPlayer.addCrt(1);
							break;
						case 4:
							dfPlayer.addRnd(1);
							break;
						case 5:
							dfPlayer.addHp(1);
							break;
						case 6:
							if(dfPlayer.getDf() < 135) {
								dfPlayer.addDf(1);
							}
							else {
								m--;
							}
							break;
						}
					}
					dfPlayer.setMove(0.2 + 0.02 * tier + 0.005 * level);
					dfPlayer.resetCalculations();
					sk.attackSpeed(mob);
					sk.changeHealth(mob);
					new BukkitRunnable() {
						public void run() {
							mob.setHealth(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 10L);
					sk.runDefense(mob);
					mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(12.50);
					mob.getEquipment().setHelmetDropChance(0.22F - 0.02F * level);
					mob.getEquipment().setChestplateDropChance(0.22F - 0.02F * level);
					mob.getEquipment().setLeggingsDropChance(0.22F - 0.02F * level);
					mob.getEquipment().setBootsDropChance(0.22F - 0.02F * level);
					mob.getEquipment().setItemInMainHandDropChance(0.22F - 0.02F * level);
			  		mobList.put(mob.getUniqueId(), tier);
			  		teleportBack.put(mob.getUniqueId(), counter);
	  			}
	  			countMobs = 0;
			}
			else {
				Location fixedLoc = SetSpawner.getSpawnerLocList().get(counter);
				int countMobs = 0;
				fixedLoc.setWorld(Bukkit.getWorld(SetSpawner.getSpawnerLocList().get(counter).getWorld().getName()));
	  			for(Entity e : fixedLoc.getWorld().getNearbyEntities(fixedLoc, 30, 30, 30)) {
	  				if(e != null) {
	  					if(teleportBack.containsKey(e.getUniqueId())) {
		  					if(teleportBack.get(e.getUniqueId()) == counter) {
		  						countMobs++;
		  					}
	  					}
	  				}
	  			}
	  			int tier = SetSpawner.getTieredList().get(counter);
	  			int mobMax = 1 - countMobs;
	  			for(int i = 0; i < mobMax; i++) {
	  				Location spawnLoc = new Location(Bukkit.getWorld(SetSpawner.getSpawnerLocList().get(counter).getWorld().getName()), SetSpawner.getSpawnerLocList().get(counter).getX(), SetSpawner.getSpawnerLocList().get(counter).getY(), SetSpawner.getSpawnerLocList().get(counter).getZ());
	  				double originalY = spawnLoc.getY();
	  				double x = spawnLoc.getX();
	  				double z = spawnLoc.getZ();
					spawnLoc.setX(spawnLoc.getX() + randomLocOffSet());
					spawnLoc.setZ(spawnLoc.getZ() + randomLocOffSet());
	  				for(double y1 = spawnLoc.getY() + 10.00; y1 > 0;) {
	  					y1--;
	  					spawnLoc.setY(y1);
	  					if(spawnLoc.getBlock().getType() != Material.AIR) {
	  						spawnLoc.setY(y1 + 2.00);
	  						if(spawnLoc.getBlock().getType() == Material.AIR) {
	  							break;
	  						}
	  						else {
	  							y1 = originalY + 10.00;
	  							spawnLoc.setX(spawnLoc.getX() + randomLocOffSet());
			  					spawnLoc.setZ(spawnLoc.getZ() + randomLocOffSet());
	  						}
	  					}
	  				}
	  				LivingEntity mob = (LivingEntity) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.valueOf(SetSpawner.getTypeList().get(counter)));
	  				SetSpawner.getSpawnerLocList().get(counter).setX(x);
	  				SetSpawner.getSpawnerLocList().get(counter).setZ(z);
	  				if(mob instanceof Zombie) {
	  					Zombie zombie = (Zombie) mob;
	  					zombie.setBaby(false);
	  				}
	  				else if(mob instanceof PigZombie) {
	  					PigZombie zombie = (PigZombie) mob;
	  					zombie.setBaby(false);
	  					
	  				}
					int level = (new Random().nextInt(20) + 20 * (tier - 1));
					if(level == 0) {
						level++;
					}
					levelList.put(mob.getUniqueId(), level);
	  				mob.setCustomName(new ColorCodeTranslator().colorize("&4&lDemonic Monkey King &b[&6Lv 20&b]"));
	  				mob.setCustomNameVisible(true);
	  				if(tier == 6) {
	  					mob.getEquipment().setHelmet(this.mobArmor(CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/a5e1b3835e36ec0bb2e31e1cd2b8036563fd53e377437e368bae512f61566b0"), "&dMonkey Kings Head &b[&6Lv 1&b]", "&9Ranged Resistance 1", 2.2, 0.3, true));
  						mob.getEquipment().setChestplate(this.mobArmor(Material.LEATHER_CHESTPLATE, "&dMonkey Kings Chestplate &b[&6Lv 1&b]", "&9Rage 1", 2.2, 0.3, true));
  						mob.getEquipment().setLeggings(this.mobArmor(Material.LEATHER_LEGGINGS, "&dMonkey Kings Leggings &b[&6Lv 1&b]", "&9Reinforced 1", 2.2, 0.3, true));
  						mob.getEquipment().setBoots(this.mobArmor(Material.LEATHER_BOOTS, "&dMonkey Kings Boots &b[&6Lv 1&b]", "&9Escape 1", 2.2, 0.3, true));
						mob.getEquipment().setItemInMainHand(this.mobWeapon(Material.STICK, "&dMonkey Kings Staff &b[&6Lv 1&b]", "&9Featherweight 1", 3.0, 1.8, false));
						LeatherArmorMeta meta = (LeatherArmorMeta) mob.getEquipment().getChestplate().getItemMeta();
						meta.setColor(Color.RED);
						mob.getEquipment().getChestplate().setItemMeta(meta);
						meta = (LeatherArmorMeta) mob.getEquipment().getLeggings().getItemMeta();
						meta.setColor(Color.RED);
						mob.getEquipment().getLeggings().setItemMeta(meta);
						meta = (LeatherArmorMeta) mob.getEquipment().getBoots().getItemMeta();
						meta.setColor(Color.RED);
						mob.getEquipment().getBoots().setItemMeta(meta);
						method.addPlayer(mob);
						DFPlayer dfPlayer = method.getPlayer(mob);
						dfPlayer.setLevel(level);
						dfPlayer.setPlayerClass(Classes.WRATH);
						dfPlayer.setAtk(80);
						dfPlayer.setSpd(10);
						dfPlayer.setCrt(130);
						dfPlayer.setRnd(0);
						dfPlayer.setHp(80);
						dfPlayer.setDf(80);
						sk.attackSpeed(mob);
						sk.changeHealth(mob);
						sk.runDefense(mob);
						dfPlayer.setMove(0.45);
						dfPlayer.resetCalculations();
						mob.getEquipment().setHelmetDropChance(0.075F);
						mob.getEquipment().setChestplateDropChance(0.075F);
						mob.getEquipment().setLeggingsDropChance(0.075F);
						mob.getEquipment().setBootsDropChance(0.075F);
						mob.getEquipment().setItemInMainHandDropChance(0.075F);
	  				}
			  		mobList.put(mob.getUniqueId(), tier);
			  		teleportBack.put(mob.getUniqueId(), counter);
	  			}
	  			countMobs = 0;
			}
		}
	}
	public int randomAmount(int am) {
		return new Random().nextInt(am) + 1;
	}
	public void addNames() {
		Pair<ArrayList<ItemStack>, ArrayList<String>> pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/3a77916e84769b2c9547934dd21aebedb1972758aa5a739b612743653f6ea"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/b6b972e32d761b192626e5d6d01edc094940910103cea5e2e2d1f231adb755d5"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/b7350fd2df430bc4b9aaecd13c51c951e5bfc9f133bad0cdaefe1922a9e47fa3")
		)), new ArrayList<String>(Arrays.asList(
		"&2Troll",
		"&2Goblin",
		"&2Undead Soldier"
		)));
		names.put(EntityType.ZOMBIE, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/47d9514acf3b1764af62541295937c0ac433ed45730593782088cae202b4c30d"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/685ad247c42211de83ce0f7f6dd24290ca34eb25f167ca86279f139005d5b050"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/37a0154a8f095b7b8d18b9fec87398ee8c34b4238f08d43c108e15a2a99")
		)), new ArrayList<String>(Arrays.asList(
		"&6Flamethrower",
		"&eChimera",
		"&4Flare Shooter"
		)));
		names.put(EntityType.BLAZE, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/5ab5fde2f75805ab0f7f0b3605ec5d37a39c5681fdfee6ed7aa42b8ba94d54"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/944965c2469a3c68c97b57549fb41a990a2e6d641b56aaf4cc52360b198fb4c"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/6d11cdb6e6494ebd82c5a4e2c5767e13a36175bb23e6dcfe7de7a2453c8feb7c")
		)), new ArrayList<String>(Arrays.asList(
		"&8Deadly Sect",
		"&aPoisoned Argos",
		"&2Green Inferno"
		)));
		names.put(EntityType.CAVE_SPIDER, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/da737e2357fb7d48496f16fe3045927bbb3e1b34639e30874d22dca233ecd5"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/4dfe1ae340e9af91d6d33060ad606ee57ae3f82261e1299091c1bdb6e9bcd0f9"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/e966557dbaa35f358bc7f0e495c178a670232b3dc9677b917dc42e1f446b65cb")
		)), new ArrayList<String>(Arrays.asList(
		"&1Anrgy Hippocampus",
		"&3Pontos",
		"&bNereus"
		)));
		names.put(EntityType.DROWNED, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/6c3701fe7131861ed4cbe3c0846923400845e470167283a31747807f3fce423c"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/6085b464afbdef4b17ac618789c526897542f839c41debd9b401c719d3e36f55"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/7c49ad4680d0742ff1494b3c9ce9a06e34c4f03b319cb703fa1c8d555cc302e4")
		)), new ArrayList<String>(Arrays.asList(
		"&7Orkeanos",
		"&8Hyperion",
		"&9Mnemosyne"
		)));
		names.put(EntityType.ELDER_GUARDIAN, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/86debf383e34378d1313196972e05b192f70f2b2f1167cea8806fc7d25a556b6"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/c2b9679263d335d901f132820085babc96459fef3fb38273adb130de1e07aa5f"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/169380db31142f32804adb711ce7dbf2acc4677cfd529ef4bdd4afaa0aee15b")
		)), new ArrayList<String>(Arrays.asList(
		"&8Voldemort",
		"&2Ill-Bringer",
		"&0Thanatos"
		)));
		names.put(EntityType.EVOKER, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/a6abb8d88a97d1c2bbf4e69dc62c2b1a83de925f133f7482e89089f682987386"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/ec88ed962646e7d3b4e2e3ec8756f482452a93d7b5e94ac8dec535c5cf997"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/3fcb82e17fbba219ba24e1bcc06a311576951a75a1a778da6cd3a234f6fc57")
		)), new ArrayList<String>(Arrays.asList(
		"&9Alseid",
		"&9Amemasu",
		"&3Ayakashi"
		)));
		names.put(EntityType.GUARDIAN, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/f83a2aa9d3734b919ac24c9659e5e0f86ecafbf64d4788cfa433bbec189e8"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/9a91fc8c9f6bd69f9a01e4d4f83b9ac63b1c7bb3726556e3ef46c0d71d0f60d6"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/63c795ce9eeb6da43315716d6ccb85b78aafe66eda1bbcf969b20284be19a65")
		)), new ArrayList<String>(Arrays.asList(
		"&aBasilisk",
		"&2Eris",
		"&aMorpheus"
		)));
		names.put(EntityType.HUSK, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/25f03c7f03a85b4ae5ae19a7edf6d3d689d2e36a4ed37f40a4ea7ce633d782d3"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/8beee8b5e18397f7814770c09e91d481c3f8c610f5f005e324be35c23bd86bd1"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/5b64ead66119ff2e3ddf25ebf55a7497bc991c9672dcccad0b24fa3e93dd189c")
		)), new ArrayList<String>(Arrays.asList(
		"&eMedeia",
		"&4Half Devil",
		"&4Satan's Devil"
		)));
		names.put(EntityType.ILLUSIONER, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/e47bd31a123ea21edb3e49a33f38e90905b8c7cac4ff7803f6865e48399b6a"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/333052e6b7a87923bdb88f94185e84b562dc5b854b7bb01cc017f22c5a8a4"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/d412342b6165e7ff251898d4c859befc45c89bc26e7243062193497385f3")
		)), new ArrayList<String>(Arrays.asList(
		"&eCursed Aigeiros",
		"&7Deadly Ampelos",
		"&4Possesed Orea"
		)));
		names.put(EntityType.MAGMA_CUBE, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/664698fea5ec3f2fd7db7a8e3f4e989a1716035c2bd3666434ba1af58157"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/bb2abd66939f4cb7257a88cf52fbc6fdceec1433ec2a6ef16d62e34f6238781"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/343488e8183a0e42bb367d76d27a6adc4d4ccf20952055c4a962d7a0bede932c")
		)), new ArrayList<String>(Arrays.asList(
		"&dPossesed Pig",
		"&5Schmedgar",
		"&dUndead Pig"
		)));
		names.put(EntityType.PIG_ZOMBIE, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/664698fea5ec3f2fd7db7a8e3f4e989a1716035c2bd3666434ba1af58157"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/bb2abd66939f4cb7257a88cf52fbc6fdceec1433ec2a6ef16d62e34f6238781"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/343488e8183a0e42bb367d76d27a6adc4d4ccf20952055c4a962d7a0bede932c")
		)), new ArrayList<String>(Arrays.asList(
		"&6Cursed Elf",
		"&7Ugly Bones",
		"&8Undieable"
		)));
		names.put(EntityType.SKELETON, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/ae5488994346c2f3b84f3026489b708e869fe84815ffd5ebfbe42ac9d4d03680"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/5e9f6fc03d73dfaba7f9ad24648e5b59ad9cc022aec524dcdef4434d125b"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/aebc508e9509813007aaca0a349cddbff16348c2d39acee9f243e1490578c910")
		)), new ArrayList<String>(Arrays.asList(
		"&aUgly Bunny",
		"&8Hydra",
		"&bLailaps"
		)));
		names.put(EntityType.SLIME, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/cd4fdad5a6104aa549d1e76d73a3c6fe3c6724bf09f7ffcc02f33f9ede7fade"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/47e637f1ca735ef35467a62759dae41cf4ffb5c2c14aad42ea8ace9bc7e68ec"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/409256c59b825cd3944548fd57ba978cbea5b02f7bc063c79835068b9ebe3380")
		)), new ArrayList<String>(Arrays.asList(
		"&0Black Widdow",
		"&dUgly Thing",
		"&cDeath Prophesy"
		)));
		names.put(EntityType.SPIDER, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/8300986ed0a04ea79904f6ae53f49ed3a0ff5b1df62bba622ecbd3777f156df8"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/b6d79c0268747941df9a2a45103cbd731fdedcba588f643b670fd77aa2bd918c"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/9d7e3b19ac4f3dee9c5677c135333b9d35a7f568b63d1ef4ada4b068b5a25")
		)), new ArrayList<String>(Arrays.asList(
		"&8Ghul",
		"&7Reaper",
		"&4Angry Sniper"
		)));
		names.put(EntityType.STRAY, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/105f707b1f9160adc2f769760998b6b50f49100cc8dff13ae9295a74a20b0779"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/8840d932a84f87e926929e658737863b8a26184c2124433a8ee6601d88913497"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/ab437600155f15c7a734c9c62259ae1bc55a1aed456bcc3ca607de97a2d2705a")
		)), new ArrayList<String>(Arrays.asList(
		"&bPossesed Servant",
		"&cBetrayed Servant",
		"&4Dead Indicator"
		)));
		names.put(EntityType.VINDICATOR, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/12cae432d84c3594e7e76b3f52d2c143ed403a658a8e488cb7f566e0a270670"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/2d27f130c1acdd784ceee2b75fb181fa52ff3a75024574eb4af7faf1ae75bc"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/b67da78b47eee622edfe1675f7795619e1fbe726de684639a0fbb3c5c027d8be")
		)), new ArrayList<String>(Arrays.asList(
		"&dMagician",
		"&4Evil Eye",
		"&3Vampir"
		)));
		names.put(EntityType.WITCH, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/927af941c8b33e633aa7ecf7cfc25e9fafb6f86c51a171c5e855a9efc2ad70dc"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/4af424e09292176e6d0183adfa7714eb1139251d565a3a666f8bd94f8407f04d"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/f2e6858d5e23dcfcc7a662b3fc6e9df84e7c21a47252a60742ad22d3ce94e")
		)), new ArrayList<String>(Arrays.asList(
		"&6Cursed Orc",
		"&6Beserker",
		"&0Black Devil"
		)));
		names.put(EntityType.WITHER_SKELETON, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/2bfe4a59b164548732fd5b754f266411969a2c2feb08a89b40a1293244abec"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/12f1cc1561a433a3e40211eba4e965c5fe4781b8baad49d01c0c3ca8dd0ad4aa"),
		CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/62be9577b0fabb2c70f9b79a5ecfac7daad0cccf76a7d5e97ef2bde05580ecae")
		)), new ArrayList<String>(Arrays.asList(
		"&8Rat",
		"&6Worm",
		"&dLovely Potato"
		)));
		names.put(EntityType.SILVERFISH, pair);
	}
	public double randomDouble() {
		double i = ThreadLocalRandom.current().nextDouble() * 100;
		return i;
	}
	public double randomDouble1(int tier, int level) {
		double i = (5.00 + 7.50 * tier) + (0.25 * level);
		return i;
	}
	public int randomHead() {
		int i = new Random().nextInt(3);
		return i;
	}
	public int dice() {
		int i = new Random().nextInt(4) + 1;
		return i;
	}
	public double enchantmentChance() {
		double i = ThreadLocalRandom.current().nextDouble() * 100;
		return i;
	}
	public int enchantmentLevel(int tier) {
		int i = new Random().nextInt(tier) + 1;
		return i;
	}
	public double randomLocOffSet() {
		double i = ThreadLocalRandom.current().nextDouble() * 20.00 - 10.00;
		return i;
	}
	public double randomLocOffSetY() {
		double i = ThreadLocalRandom.current().nextDouble() * 2.00 + 1.00;
		return i;
	}
	public static HashMap<UUID, Integer> getMobList() {
		return SpawnerList.mobList;
	}
	public static HashMap<UUID, Integer> getLevelMobList(){
		return SpawnerList.levelList;
	}
	public static HashMap<EntityType, Pair<ArrayList<ItemStack>, ArrayList<String>>> getNameList(){
		return SpawnerList.names;
	}
}
