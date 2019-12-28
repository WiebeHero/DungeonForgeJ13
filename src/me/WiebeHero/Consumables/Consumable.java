package me.WiebeHero.Consumables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.Consumables.ConsumableCondition.Condition;
import me.WiebeHero.Consumables.Unlock.UnlockCraftCondition;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.NewAttribute;
import me.WiebeHero.CustomMethods.PotionM;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Skills.DFPlayer;

public class Consumable {
	private PotionM p = new PotionM();
	private DFFaction fac = new DFFaction();
	private DFPlayer df = new DFPlayer();
	public Consumable() {
		
	}
	
	private NewAttribute attr = new NewAttribute();
	public HashMap<String, Pair<Condition, CommandFile>> listConsumables;
	public ArrayList<Pair<ArrayList<UnlockCraftCondition>, Recipe>> listRecipes;
	public ArrayList<Recipe> instantUnlock = new ArrayList<Recipe>();
	
	public void loadConsumables() {
		this.listRecipes = new ArrayList<Pair<ArrayList<UnlockCraftCondition>, Recipe>>();
		this.listConsumables = new HashMap<String, Pair<Condition, CommandFile>>();
		this.listConsumables.put("Santa's_Cookie", new Pair<>(Condition.CONSUME, new CommandFile() {
			@Override
			public void activateConsumable(LivingEntity eaten, PlayerInteractEvent event) {
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 2.0F, 1.0F);
				df.addAtkCal(3.0, 350);
				df.addSpdCal(1.0, 350);
				df.addCrtCal(1.0, 350);
				df.addRndCal(4.0, 350);
				df.addHpCal(10.0, 350);
				df.addDfCal(2.0, 350);
			}
		}));
		this.listConsumables.put("Smooth_Fluzgla", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateConsumable(LivingEntity eaten, PlayerInteractEvent event) {
				if(!cooldown.contains(eaten.getUniqueId())) {
					cooldown.add(eaten.getUniqueId());
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
					event.getItem().setAmount(event.getItem().getAmount() - 1);
					if(eaten.getHealth() + 2.5 <= eaten.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
						eaten.setHealth(eaten.getHealth() + 2.5);
					}
					else {
						eaten.setHealth(eaten.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					}
					if(eaten instanceof Player) {
						Player p = (Player) eaten;
						p.setFoodLevel(p.getFoodLevel() + 2);
					}
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(eaten.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 60L);
				}
			}
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack item = con.item(
						"&7Smooth Fluzgla", 
						Material.WHITE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Heal: &c2.5 HP//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b3.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Smooth_Fluzgla");
				ShapedRecipe recipe = new ShapedRecipe(key, item);
				recipe.shape(" Y ", "YXY", " Y ");
				recipe.setIngredient('X', Material.WHITE_WOOL);
				recipe.setIngredient('Y', Material.DIORITE);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Shadow_Fluzgla", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateConsumable(LivingEntity eaten, PlayerInteractEvent event) {
				if(!cooldown.contains(eaten.getUniqueId())) {
					cooldown.add(eaten.getUniqueId());
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_GHAST_SHOOT, 2.0F, 0.5F);
					event.getItem().setAmount(event.getItem().getAmount() - 1);
					DFFaction faction = fac.getFaction(eaten.getUniqueId());
					p.applyEffect(eaten, PotionEffectType.SPEED, 0, 150);
					if(eaten instanceof Player) {
						Player p = (Player) eaten;
						p.setFoodLevel(p.getFoodLevel() + 2);
					}
					for(Entity e : eaten.getNearbyEntities(5.0, 5.0, 5.0)) {
						if(e != eaten) {
							if(e instanceof LivingEntity) {
								LivingEntity ent = (LivingEntity) e;
								if(faction != null) {
									if(!faction.isMember(ent.getUniqueId()) && !faction.isAlly(ent.getUniqueId())) {
										p.applyEffect(ent, PotionEffectType.BLINDNESS, 0, 150);
									}
								}
								else {
									p.applyEffect(ent, PotionEffectType.BLINDNESS, 0, 150);
								}
							}
						}
					}
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(eaten.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 150L);
				}
			}
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack item = con.item(
						"&7Smooth Fluzgla", 
						Material.WHITE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Heal: &c2.5 HP//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b3.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				ItemStack result = con.item(
						"&7Shadow Fluzgla", 
						Material.BLACK_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Speed I (To you): &b7.5 Seconds//"
						+ "  &7Blindness I (To nearby enemies): &b7.5 Seconds//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Range: &65.0 Blocks//"
						+ "  &7Cooldown: &b7.5 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Shadow_Fluzgla");
				ShapedRecipe recipe = new ShapedRecipe(key, result);
				recipe.shape(" Y ", "ZXZ", " Y ");
				recipe.setIngredient('X', item);
				recipe.setIngredient('Y', Material.BLACK_CONCRETE);
				recipe.setIngredient('Z', Material.INK_SAC);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Hastened_Fluzgla", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateConsumable(LivingEntity eaten, PlayerInteractEvent event) {
				if(!cooldown.contains(eaten.getUniqueId())) {
					cooldown.add(eaten.getUniqueId());
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
					event.getItem().setAmount(event.getItem().getAmount() - 1);
					p.applyEffect(eaten, PotionEffectType.FAST_DIGGING, 0, 200);
					if(eaten instanceof Player) {
						Player p = (Player) eaten;
						p.setFoodLevel(p.getFoodLevel() + 1);
					}
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(eaten.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 200L);
				}
			}
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack item = con.item(
						"&7Smooth Fluzgla", 
						Material.WHITE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Heal: &c2.5 HP//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b3.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				ItemStack result = con.item(
						"&7Hastened Fluzgla", 
						Material.YELLOW_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Haste I (To you): &b10.0 Seconds//"
						+ "  &7Food Gain: &c1//"
						+ "  &7Cooldown: &b10.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Hastened_Fluzgla");
				ShapedRecipe recipe = new ShapedRecipe(key, result);
				recipe.shape(" Y ", "ZXZ", " Y ");
				recipe.setIngredient('X', item);
				recipe.setIngredient('Y', Material.YELLOW_CONCRETE);
				recipe.setIngredient('Z', Material.COOKIE);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Bursting_Fluzgla", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateConsumable(LivingEntity eaten, PlayerInteractEvent event) {
				if(!cooldown.contains(eaten.getUniqueId())) {
					cooldown.add(eaten.getUniqueId());
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
					if(eaten instanceof Player) {
						Player p = (Player) eaten;
						p.setFoodLevel(p.getFoodLevel() + 1);
					}
					for(int i = 0; i < 5; i++) {
						eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2.0F, 1.0F);
						float yaw = eaten.getLocation().getYaw();
		                double D = 0.5;
		                double x = -D*Math.sin(yaw*Math.PI/180);
		                double z = D*Math.cos(yaw*Math.PI/180);
						eaten.getWorld().spawn(eaten.getLocation().add(x, 1.5, z), SmallFireball.class);
					}
					event.getItem().setAmount(event.getItem().getAmount() - 1);
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(eaten.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 200L);
				}
			}
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack item = con.item(
						"&7Smooth Fluzgla", 
						Material.WHITE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Heal: &c2.5 HP//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b3.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				ItemStack result = con.item(
						"&7Bursting Fluzgla", 
						Material.RED_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Shoot Fireballs//"
						+ "  &7Amount: &65//"
						+ "  &7Food Gain: &c1//"
						+ "  &7Cooldown: &b10.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Bursting_Fluzgla");
				ShapedRecipe recipe = new ShapedRecipe(key, result);
				recipe.shape(" Y ", "ZXZ", " Y ");
				recipe.setIngredient('X', item);
				recipe.setIngredient('Y', Material.RED_CONCRETE);
				recipe.setIngredient('Z', Material.BLAZE_ROD);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Healing_Fluzgla", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateConsumable(LivingEntity eaten, PlayerInteractEvent event) {
				if(!cooldown.contains(eaten.getUniqueId())) {
					cooldown.add(eaten.getUniqueId());
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
					eaten.getWorld().playSound(eaten.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 2.0F);
					eaten.getWorld().spawnParticle(Particle.HEART, eaten.getLocation().add(0, 2.0, 0), 20, 0.2, 0.2, 0.2);
					event.getItem().setAmount(event.getItem().getAmount() - 1);
					DFFaction faction = fac.getFaction(eaten.getUniqueId());
					p.applyEffect(eaten, PotionEffectType.REGENERATION, 0, 180);
					if(eaten instanceof Player) {
						Player p = (Player) eaten;
						p.setFoodLevel(p.getFoodLevel() + 1);
					}
					if(eaten.getHealth() + 2.5 <= eaten.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
						eaten.setHealth(eaten.getHealth() + 2.5);
					}
					else {
						eaten.setHealth(eaten.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					}
					for(Entity e : eaten.getNearbyEntities(7.5, 7.5, 7.5)) {
						if(e != eaten) {
							if(e instanceof LivingEntity) {
								LivingEntity ent = (LivingEntity) e;
								if(faction != null) {
									if(faction.isMember(ent.getUniqueId())) {
										ent.getWorld().playSound(eaten.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 2.0F);
										ent.getWorld().spawnParticle(Particle.HEART, eaten.getLocation().add(0, 2.2, 0), 20, 0.2, 0.2, 0.2);
										p.applyEffect(eaten, PotionEffectType.REGENERATION, 1, 90);
										if(ent.getHealth() + 5.0 <= ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
											ent.setHealth(ent.getHealth() + 5.0);
										}
										else {
											ent.setHealth(ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
										}
									}
									else if(faction.isAlly(ent.getUniqueId())) {
										ent.getWorld().playSound(eaten.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 2.0F);
										ent.getWorld().spawnParticle(Particle.HEART, eaten.getLocation().add(0, 2.2, 0), 20, 0.2, 0.2, 0.2);
										p.applyEffect(eaten, PotionEffectType.REGENERATION, 0, 180);
										if(ent.getHealth() + 2.5 <= ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
											ent.setHealth(ent.getHealth() + 2.5);
										}
										else {
											ent.setHealth(ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
										}
									}
								}
							}
						}
					}
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(eaten.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 300L);
				}
			}
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack item = con.item(
						"&7Smooth Fluzgla", 
						Material.WHITE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Heal: &c2.5 HP//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b3.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				ItemStack result = con.item(
						"&7Healing Fluzgla", 
						Material.PINK_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Heal (To you and allies): &c2.5 HP//"
						+ "  &7Regen I (To you and allies): &b9.0 Seconds//"
						+ "  &7Heal (To members): &c5.0 HP//"
						+ "  &7Regen II (To members): &b4.5 Seconds//"
						+ "  &7Food Gain (To you): &c1//"
						+ "  &7Range: &67.5 Blocks//"
						+ "  &7Cooldown: &b15.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Healing_Fluzgla");
				ShapedRecipe recipe = new ShapedRecipe(key, result);
				recipe.shape(" Y ", "ZXZ", " Y ");
				recipe.setIngredient('X', item);
				recipe.setIngredient('Y', Material.PINK_CONCRETE);
				recipe.setIngredient('Z', Material.GLISTERING_MELON_SLICE);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Godlike_Fluzgla", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>(Arrays.asList(
					PotionEffectType.REGENERATION,
					PotionEffectType.FAST_DIGGING,
					PotionEffectType.DAMAGE_RESISTANCE,
					PotionEffectType.DOLPHINS_GRACE,
					PotionEffectType.CONDUIT_POWER,
					PotionEffectType.HEALTH_BOOST,
					PotionEffectType.SATURATION,
					PotionEffectType.ABSORPTION,
					PotionEffectType.WATER_BREATHING,
					PotionEffectType.INCREASE_DAMAGE,
					PotionEffectType.JUMP,
					PotionEffectType.SPEED));
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateConsumable(LivingEntity eaten, PlayerInteractEvent event) {
				if(!cooldown.contains(eaten.getUniqueId())) {
					cooldown.add(eaten.getUniqueId());
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 2.0F, 1.5F);
					event.getItem().setAmount(event.getItem().getAmount() - 1);
					int random = new Random().nextInt(types.size());
					p.applyEffect(eaten, types.get(random), 2, 100);
					if(eaten instanceof Player) {
						Player p = (Player) eaten;
						p.setFoodLevel(p.getFoodLevel() + 2);
					}
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(eaten.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 500L);
				}
			}
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack item = con.item(
						"&7Smooth Fluzgla", 
						Material.WHITE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Heal: &c2.5 HP//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b3.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				ItemStack result = con.item(
						"&7Godlike Fluzgla", 
						Material.PURPLE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Random Effect III (To you): &b5.0 Seconds//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b25.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Godlike_Fluzgla");
				ShapedRecipe recipe = new ShapedRecipe(key, result);
				recipe.shape(" Y ", "ZXZ", " Y ");
				recipe.setIngredient('X', item);
				recipe.setIngredient('Y', Material.PURPLE_CONCRETE);
				recipe.setIngredient('Z', Material.GOLDEN_APPLE);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Aquatic_Fluzgla", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateConsumable(LivingEntity eaten, PlayerInteractEvent event) {
				if(!cooldown.contains(eaten.getUniqueId())) {
					cooldown.add(eaten.getUniqueId());
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_DOLPHIN_AMBIENT, 2.0F, 1.5F);
					event.getItem().setAmount(event.getItem().getAmount() - 1);
					p.applyEffect(eaten, PotionEffectType.DOLPHINS_GRACE, 0, 200);
					p.applyEffect(eaten, PotionEffectType.WATER_BREATHING, 0, 200);
					if(eaten instanceof Player) {
						Player p = (Player) eaten;
						p.setFoodLevel(p.getFoodLevel() + 2);
					}
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(eaten.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 300L);
				}
			}
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack item = con.item(
						"&7Smooth Fluzgla", 
						Material.WHITE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Heal: &c2.5 HP//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b3.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				ItemStack result = con.item(
						"&7Aquatic Fluzgla", 
						Material.LIGHT_BLUE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Dolphins Grace I: &b10.0 Seconds//"
						+ "  &7Water Breathing I: &b10.0 Seconds//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b15.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Aquatic_Fluzgla");
				ShapedRecipe recipe = new ShapedRecipe(key, result);
				recipe.shape(" Y ", "ZXZ", " Y ");
				recipe.setIngredient('X', item);
				recipe.setIngredient('Y', Material.LIGHT_BLUE_CONCRETE);
				recipe.setIngredient('Z', Material.COD);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Frozen_Fluzgla", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateConsumable(LivingEntity eaten, PlayerInteractEvent event) {
				if(!cooldown.contains(eaten.getUniqueId())) {
					cooldown.add(eaten.getUniqueId());
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_HUSK_AMBIENT, 2.0F, 0.5F);
					event.getItem().setAmount(event.getItem().getAmount() - 1);
					DFFaction faction = fac.getFaction(eaten.getUniqueId());
					for(Entity e : eaten.getNearbyEntities(6.0, 6.0, 6.0)) {
						if(e != eaten) {
							if(e instanceof LivingEntity) {
								LivingEntity ent = (LivingEntity) e;
								if(faction != null) {
									if(!faction.isMember(ent.getUniqueId()) && !faction.isAlly(ent.getUniqueId())) {
										p.applyEffect(ent, PotionEffectType.SLOW, 1, 100);
									}
								}
								else {
									p.applyEffect(ent, PotionEffectType.SLOW, 1, 100);
								}
							}
						}
					}
					if(eaten instanceof Player) {
						Player p = (Player) eaten;
						p.setFoodLevel(p.getFoodLevel() + 3);
					}
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(eaten.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 300L);
				}
			}
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack item = con.item(
						"&7Smooth Fluzgla", 
						Material.WHITE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Heal: &c2.5 HP//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b3.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				ItemStack result = con.item(
						"&7Frozen Fluzgla", 
						Material.BLUE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Slowness II (To enemies): &b5.0 Seconds//"
						+ "  &7Food Gain: &c3//"
						+ "  &7Range: &66.0 Blocks//"
						+ "  &7Cooldown: &b15.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Frozen_Fluzgla");
				ShapedRecipe recipe = new ShapedRecipe(key, result);
				recipe.shape(" Y ", "ZXZ", " Y ");
				recipe.setIngredient('X', item);
				recipe.setIngredient('Y', Material.BLUE_CONCRETE);
				recipe.setIngredient('Z', Material.COD);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Curing_Fluzgla", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<PotionEffectType> negative = new ArrayList<PotionEffectType>(Arrays.asList(
					PotionEffectType.BLINDNESS,
					PotionEffectType.POISON,
					PotionEffectType.WITHER,
					PotionEffectType.CONFUSION,
					PotionEffectType.HUNGER,
					PotionEffectType.LEVITATION,
					PotionEffectType.WEAKNESS,
					PotionEffectType.SLOW,
					PotionEffectType.SLOW_DIGGING,
					PotionEffectType.SLOW_FALLING));
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateConsumable(LivingEntity eaten, PlayerInteractEvent event) {
				if(!cooldown.contains(eaten.getUniqueId())) {
					cooldown.add(eaten.getUniqueId());
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
					eaten.getWorld().playSound(eaten.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
					event.getItem().setAmount(event.getItem().getAmount() - 1);
					if(eaten instanceof Player) {
						Player p = (Player) eaten;
						p.setFoodLevel(p.getFoodLevel() + 3);
					}
					ArrayList<PotionEffectType> types = new ArrayList<PotionEffectType>();
					if(eaten.getActivePotionEffects() != null || !eaten.getActivePotionEffects().isEmpty()) {
						for(PotionEffect effect : eaten.getActivePotionEffects()) {
							if(effect != null) {
								if(negative.contains(effect.getType())) {
									types.add(effect.getType());
								}
							}
						}
					}
					if(!types.isEmpty()) {
						int random = new Random().nextInt(types.size());
						eaten.removePotionEffect(types.get(random));
					}
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(eaten.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 350L);
				}
			}
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack item = con.item(
						"&7Smooth Fluzgla", 
						Material.WHITE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Heal: &c2.5 HP//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b3.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				ItemStack result = con.item(
						"&7Curing Fluzgla", 
						Material.GREEN_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Remove random negative effect//"
						+ "  &7Food Gain: &c3//"
						+ "  &7Cooldown: &b17.5 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Curing_Fluzgla");
				ShapedRecipe recipe = new ShapedRecipe(key, result);
				recipe.shape(" Y ", "ZXZ", " Y ");
				recipe.setIngredient('X', item);
				recipe.setIngredient('Y', Material.GREEN_CONCRETE);
				recipe.setIngredient('Z', Material.DRIED_KELP);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Bouncy_Fluzgla", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateConsumable(LivingEntity eaten, PlayerInteractEvent event) {
				if(!cooldown.contains(eaten.getUniqueId())) {
					cooldown.add(eaten.getUniqueId());
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_RABBIT_AMBIENT, 2.0F, 1.0F);
					event.getItem().setAmount(event.getItem().getAmount() - 1);
					if(eaten instanceof Player) {
						Player p = (Player) eaten;
						p.setFoodLevel(p.getFoodLevel() + 2);
					}
					p.applyEffect(eaten, PotionEffectType.JUMP, 1, 200);
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(eaten.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 250L);
				}
			}
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack item = con.item(
						"&7Smooth Fluzgla", 
						Material.WHITE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Heal: &c2.5 HP//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b3.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				ItemStack result = con.item(
						"&7Bouncy Fluzgla", 
						Material.LIME_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Jump Boost II: &b10.0 Seconds//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b12.5 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Bouncy_Fluzgla");
				ShapedRecipe recipe = new ShapedRecipe(key, result);
				recipe.shape(" Y ", "ZXZ", " Y ");
				recipe.setIngredient('X', item);
				recipe.setIngredient('Y', Material.LIME_CONCRETE);
				recipe.setIngredient('Z', Material.RABBIT_HIDE);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Poopy_Fluzgla", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			ArrayList<UUID> cooldown = new ArrayList<UUID>();
			@Override
			public void activateConsumable(LivingEntity eaten, PlayerInteractEvent event) {
				if(!cooldown.contains(eaten.getUniqueId())) {
					cooldown.add(eaten.getUniqueId());
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2.0F, 0.5F);
					event.getItem().setAmount(event.getItem().getAmount() - 1);
					DFFaction faction = fac.getFaction(eaten.getUniqueId());
					for(Entity e : eaten.getNearbyEntities(7.5, 7.5, 7.5)) {
						if(e != eaten) {
							if(e instanceof LivingEntity) {
								LivingEntity ent = (LivingEntity) e;
								if(faction != null) {
									if(!faction.isMember(ent.getUniqueId()) && !faction.isAlly(ent.getUniqueId())) {
										p.applyEffect(ent, PotionEffectType.CONFUSION, 1, 250);
									}
								}
								else {
									p.applyEffect(ent, PotionEffectType.CONFUSION, 1, 250);
								}
							}
						}
					}
					new BukkitRunnable() {
						public void run() {
							cooldown.remove(eaten.getUniqueId());
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 250L);
				}
			}
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack item = con.item(
						"&7Smooth Fluzgla", 
						Material.WHITE_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Heal: &c2.5 HP//"
						+ "  &7Food Gain: &c2//"
						+ "  &7Cooldown: &b3.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				ItemStack result = con.item(
						"&7Poopy Fluzgla", 
						Material.BROWN_WOOL, 
						1, 
						"&7-----------------------//"
						+ "&7When consumed://"
						+ "  &7Nausea II (To enemies): &b10.0 Seconds//"
						+ "  &7Range: &67.5 Blocks//"
						+ "  &7Cooldown: &b10.0 Seconds//"
						+ "&7-----------------------//"
						+ "&7Also used as a crafting ingridient.");
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Poopy_Fluzgla");
				ShapedRecipe recipe = new ShapedRecipe(key, result);
				recipe.shape(" Y ", "ZXZ", " Y ");
				recipe.setIngredient('X', item);
				recipe.setIngredient('Y', Material.BROWN_CONCRETE);
				recipe.setIngredient('Z', new RecipeChoice.MaterialChoice(Arrays.asList(Material.CHARCOAL, Material.COAL)));
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Bark Hearts", new Pair<>(Condition.NONE, new CommandFile() {
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				//-----------------------------------------------------
				//Small Hearts
				//-----------------------------------------------------
				
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Small_Bark_Heart_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.heart("Small Bark", Material.OAK_LOG));
				recipe.shape("X X", "XXX", " X ");
				recipe.setIngredient('X', new RecipeChoice.MaterialChoice(new ArrayList<Material>(Arrays.asList(Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.JUNGLE_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.ACACIA_PLANKS))));
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				//-----------------------------------------------------
				//Medium Hearts
				//-----------------------------------------------------
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Bark_Heart_1");
				recipe = new ShapedRecipe(key, con.heart("Bark", Material.OAK_LOG));
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Small Bark", Material.OAK_LOG));
				recipe.setIngredient('X', Material.OAK_PLANKS);
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Bark_Heart_2");
				recipe = new ShapedRecipe(key, con.heart("Bark", Material.OAK_LOG));
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Small Bark", Material.OAK_LOG));
				recipe.setIngredient('X', Material.SPRUCE_PLANKS);
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));		
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Bark_Heart_3");
				recipe = new ShapedRecipe(key, con.heart("Bark", Material.OAK_LOG));
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Small Bark", Material.OAK_LOG));
				recipe.setIngredient('X', Material.BIRCH_PLANKS);
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Bark_Heart_4");
				recipe = new ShapedRecipe(key, con.heart("Bark", Material.OAK_LOG));
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Small Bark", Material.OAK_LOG));
				recipe.setIngredient('X', Material.JUNGLE_PLANKS);
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Bark_Heart_5");
				recipe = new ShapedRecipe(key, con.heart("Bark", Material.OAK_LOG));
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Small Bark", Material.OAK_LOG));
				recipe.setIngredient('X', Material.ACACIA_PLANKS);
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Bark_Heart_6");
				recipe = new ShapedRecipe(key, con.heart("Bark", Material.OAK_LOG));
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Small Bark", Material.OAK_LOG));
				recipe.setIngredient('X', Material.DARK_OAK_PLANKS);
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				//-----------------------------------------------------
				//Big Hearts
				//-----------------------------------------------------
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Bark_Heart_1");
				recipe = new ShapedRecipe(key, con.heart("Big Bark", Material.OAK_LOG));
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Bark", Material.OAK_LOG));
				recipe.setIngredient('X', Material.OAK_PLANKS);
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Bark_Heart_2");
				recipe = new ShapedRecipe(key, con.heart("Big Bark", Material.OAK_LOG));
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Bark", Material.OAK_LOG));
				recipe.setIngredient('X', Material.SPRUCE_PLANKS);
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));		
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Bark_Heart_3");
				recipe = new ShapedRecipe(key, con.heart("Big Bark", Material.OAK_LOG));
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Bark", Material.OAK_LOG));
				recipe.setIngredient('X', Material.BIRCH_PLANKS);
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Bark_Heart_4");
				recipe = new ShapedRecipe(key, con.heart("Big Bark", Material.OAK_LOG));
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Bark", Material.OAK_LOG));
				recipe.setIngredient('X', Material.JUNGLE_PLANKS);
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Bark_Heart_5");
				recipe = new ShapedRecipe(key, con.heart("Big Bark", Material.OAK_LOG));
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Bark", Material.OAK_LOG));
				recipe.setIngredient('X', Material.ACACIA_PLANKS);
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Bark_Heart_6");
				recipe = new ShapedRecipe(key, con.heart("Big Bark", Material.OAK_LOG));
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Bark", Material.OAK_LOG));
				recipe.setIngredient('X', Material.DARK_OAK_PLANKS);
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Wooden Pickaxes", new Pair<>(Condition.NONE, new CommandFile() {
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Bark", Material.OAK_LOG);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Pickaxe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.WOODEN_PICKAXE, "&7Wooden Pickaxe", 1, EquipmentSlot.HAND, 1.8, 0.8, 80, null));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', Material.WOODEN_PICKAXE);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				recipe.setGroup("Wooden Pickaxes");
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Gold Hearts", new Pair<>(Condition.NONE, new CommandFile() {
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Gold", Material.GOLD_INGOT);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Small_Gold_Heart");
				ShapedRecipe recipe = new ShapedRecipe(key, heart);
				recipe.shape("X X", "XXX", " X ");
				recipe.setIngredient('X', Material.GOLD_INGOT);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Gold", Material.GOLD_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Small Gold", Material.GOLD_INGOT));
				recipe.setIngredient('X', Material.GOLD_INGOT);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Big Gold", Material.GOLD_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Gold_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Gold", Material.GOLD_INGOT));
				recipe.setIngredient('X', Material.GOLD_INGOT);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Gold Pickaxes", new Pair<>(Condition.NONE, new CommandFile() {
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Gold", Material.GOLD_INGOT);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Pickaxe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_PICKAXE, "&7Golden Pickaxe", 1, EquipmentSlot.HAND, 1.4, 1.2, 40, null));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', Material.GOLDEN_PICKAXE);
				recipe.setGroup("Golden Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Gold", Material.GOLD_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Pickaxe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_PICKAXE, "&7Golden Pickaxe", 2, EquipmentSlot.HAND, 1.4, 1.2, 40, null));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_PICKAXE, "&7Golden Pickaxe", 1, EquipmentSlot.HAND, 1.4, 1.2, 40, null));
				recipe.setGroup("Golden Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Cobblestone Hearts", new Pair<>(Condition.NONE, new CommandFile() {
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Small_Cobblestone_Heart");
				ShapedRecipe recipe = new ShapedRecipe(key, heart);
				recipe.shape("X X", "XXX", " X ");
				recipe.setIngredient('X', Material.COBBLESTONE);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				recipe.setGroup("Cobblestone Hearts");
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Cobblestone", Material.COBBLESTONE);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Cobblestone_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Small Cobblestone", Material.COBBLESTONE));
				recipe.setIngredient('X', Material.COBBLESTONE);
				recipe.setGroup("Cobblestone Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Big Cobblestone", Material.COBBLESTONE);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Cobblestone_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Cobblestone", Material.COBBLESTONE));
				recipe.setIngredient('X', Material.COBBLESTONE);
				recipe.setGroup("Cobblestone Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Stone Pickaxes", new Pair<>(Condition.NONE, new CommandFile() {
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Pickaxe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.STONE_PICKAXE, "&7Stone Pickaxe", 1, EquipmentSlot.HAND, 2.0, 0.75, 120, null));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', Material.STONE_PICKAXE);
				recipe.setGroup("Stone Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Pickaxe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.STONE_PICKAXE, "&7Stone Pickaxe", 2, EquipmentSlot.HAND, 2.0, 0.75, 120, null));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.STONE_PICKAXE, "&7Stone Pickaxe", 1, EquipmentSlot.HAND, 2.0, 0.75, 120, null));
				recipe.setGroup("Stone Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Iron Hearts", new Pair<>(Condition.NONE, new CommandFile() {
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Iron", Material.IRON_INGOT);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Small_Iron_Heart");
				ShapedRecipe recipe = new ShapedRecipe(key, heart);
				recipe.shape("X X", "XXX", " X ");
				recipe.setIngredient('X', Material.IRON_INGOT);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				recipe.setGroup("Iron Hearts");
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Small Iron", Material.IRON_INGOT));
				recipe.setIngredient('X', Material.IRON_INGOT);
				recipe.setGroup("Iron Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Big Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Iron_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Iron", Material.IRON_INGOT));
				recipe.setIngredient('X', Material.IRON_INGOT);
				recipe.setGroup("Iron Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			
			}
		}));
		this.listConsumables.put("Iron Pickaxes", new Pair<>(Condition.NONE, new CommandFile() {
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Iron", Material.IRON_INGOT);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Pickaxe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.IRON_PICKAXE, "&7Iron Pickaxe", 1, EquipmentSlot.HAND, 2.2, 0.7, 160, null));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', Material.IRON_PICKAXE);
				recipe.setGroup("Iron Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Pickaxe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_PICKAXE, "&7Iron Pickaxe", 2, EquipmentSlot.HAND, 2.2, 0.7, 160, null));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_PICKAXE, "&7Iron Pickaxe", 1, EquipmentSlot.HAND, 2.2, 0.7, 160, null));
				recipe.setGroup("Iron Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Pickaxe_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_PICKAXE, "&7Iron Pickaxe", 3, EquipmentSlot.HAND, 2.2, 0.7, 160, null));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_PICKAXE, "&7Iron Pickaxe", 2, EquipmentSlot.HAND, 2.2, 0.7, 160, null));
				recipe.setGroup("Iron Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Diamond Hearts", new Pair<>(Condition.NONE, new CommandFile() {
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Diamond", Material.DIAMOND);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Small_Diamond_Heart");
				ShapedRecipe recipe = new ShapedRecipe(key, heart);
				recipe.shape("X X", "XXX", " X ");
				recipe.setIngredient('X', Material.DIAMOND);
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				recipe.setGroup("Diamond Hearts");
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Small Diamond", Material.DIAMOND));
				recipe.setIngredient('X', Material.DIAMOND);
				recipe.setGroup("Diamond Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Big Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Diamond_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("X X", "XYX", " X ");
				recipe.setIngredient('Y', con.heart("Diamond", Material.DIAMOND));
				recipe.setIngredient('X', Material.DIAMOND);
				recipe.setGroup("Diamond Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Diamond Pickaxes", new Pair<>(Condition.NONE, new CommandFile() {
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Diamond", Material.DIAMOND);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Pickaxe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", 1, EquipmentSlot.HAND, 2.4, 0.65, 200, null));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', Material.DIAMOND_PICKAXE);
				recipe.setGroup("Diamond Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Pickaxe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", 2, EquipmentSlot.HAND, 2.4, 0.65, 200, null));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", 1, EquipmentSlot.HAND, 2.4, 0.65, 200, null));
				recipe.setGroup("Diamond Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Pickaxe_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", 3, EquipmentSlot.HAND, 2.4, 0.65, 200, null));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", 2, EquipmentSlot.HAND, 2.4, 0.65, 200, null));
				recipe.setGroup("Diamond Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Pickaxe_4");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", 4, EquipmentSlot.HAND, 2.4, 0.65, 200, null));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", 3, EquipmentSlot.HAND, 2.4, 0.65, 200, null));
				recipe.setGroup("Diamond Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Stone Armor", new Pair<>(Condition.NONE, new CommandFile() {
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				//-----------------------------------------------------------------------------------
				//Stone Armor
				//-----------------------------------------------------------------------------------
				ItemStack item = con.equipment(Material.LEATHER_HELMET, "&7Stone Helmet", 0, EquipmentSlot.HEAD, 1.15, 0.58, 100, null);
				LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
				meta.setColor(Color.fromRGB(95, 95, 95));
				item.setItemMeta(meta);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Helmet_1");
				ShapedRecipe recipe = new ShapedRecipe(key, item);
				recipe.shape("XXX", "X X", "   ");
				recipe.setIngredient('X', Material.COBBLESTONE);
				recipe.setGroup("Stone Helmets");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				item = con.equipment(Material.LEATHER_HELMET, "&7Stone Helmet", 0, EquipmentSlot.HEAD, 1.15, 0.58, 100, null);
				meta = (LeatherArmorMeta) item.getItemMeta();
				meta.setColor(Color.fromRGB(95, 95, 95));
				item.setItemMeta(meta);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Helmet_2");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("   ", "XXX", "X X");
				recipe.setIngredient('X', Material.COBBLESTONE);
				recipe.setGroup("Stone Helmet");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				item = con.equipment(Material.LEATHER_CHESTPLATE, "&7Stone Chestplate", 0, EquipmentSlot.CHEST, 1.15, 0.58, 100, null);
				meta = (LeatherArmorMeta) item.getItemMeta();
				meta.setColor(Color.fromRGB(95, 95, 95));
				item.setItemMeta(meta);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Chestplate");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("X X", "XXX", "XXX");
				recipe.setIngredient('X', Material.COBBLESTONE);
				recipe.setGroup("Stone Chestplates");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				item = con.equipment(Material.LEATHER_LEGGINGS, "&7Stone Leggings", 0, EquipmentSlot.LEGS, 1.15, 0.58, 100, null);
				meta = (LeatherArmorMeta) item.getItemMeta();
				meta.setColor(Color.fromRGB(95, 95, 95));
				item.setItemMeta(meta);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Leggings");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("XXX", "X X", "X X");
				recipe.setIngredient('X', Material.COBBLESTONE);
				recipe.setGroup("Stone Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				item = con.equipment(Material.LEATHER_BOOTS, "&7Stone Boots", 0, EquipmentSlot.FEET, 1.15, 0.58, 100, null);
				meta = (LeatherArmorMeta) item.getItemMeta();
				meta.setColor(Color.fromRGB(95, 95, 95));
				item.setItemMeta(meta);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Boots_1");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("X X", "X X", "   ");
				recipe.setIngredient('X', Material.COBBLESTONE);
				recipe.setGroup("Stone Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				item = con.equipment(Material.LEATHER_BOOTS, "&7Stone Boots", 0, EquipmentSlot.FEET, 1.15, 0.58, 100, null);
				meta = (LeatherArmorMeta) item.getItemMeta();
				meta.setColor(Color.fromRGB(95, 95, 95));
				item.setItemMeta(meta);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Boots_2");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("   ", "X X", "X X");
				recipe.setIngredient('X', Material.COBBLESTONE);
				recipe.setGroup("Stone Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Chainmail Armor", new Pair<>(Condition.NONE, new CommandFile() {
			Consumable con = new Consumable();
			@Override
			public void registerRecipe() {
				//-----------------------------------------------------------------------------------
				//Chainmail Armor
				//-----------------------------------------------------------------------------------
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Chainmail_Helmet_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.CHAINMAIL_HELMET, "&7Chainmail Helmet", 0, EquipmentSlot.HEAD, 1.3, 0.65, 120, null));
				recipe.shape("YXY", "X X", "   ");
				recipe.setIngredient('X', Material.IRON_NUGGET);
				recipe.setIngredient('Y', Material.IRON_INGOT);
				recipe.setGroup("Chainmail Helmets");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Chainmail_Helmet_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.CHAINMAIL_HELMET, "&7Chainmail Helmet", 0, EquipmentSlot.HEAD, 1.3, 0.65, 120, null));
				recipe.shape("   ", "YXY", "X X");
				recipe.setIngredient('X', Material.IRON_NUGGET);
				recipe.setIngredient('Y', Material.IRON_INGOT);
				recipe.setGroup("Chainmail Helmet");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Chainmail_Chestplate");
				recipe = new ShapedRecipe(key, con.equipment(Material.CHAINMAIL_CHESTPLATE, "&7Chainmail Chestplate", 0, EquipmentSlot.CHEST, 1.3, 0.65, 120, null));
				recipe.shape("X X", "YXY", "XYX");
				recipe.setIngredient('X', Material.IRON_NUGGET);
				recipe.setIngredient('Y', Material.IRON_INGOT);
				recipe.setGroup("Chainmail Chestplates");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Chainmail_Leggings");
				recipe = new ShapedRecipe(key, con.equipment(Material.CHAINMAIL_LEGGINGS, "&7Chainmail Leggings", 0, EquipmentSlot.LEGS, 1.3, 0.65, 120, null));
				recipe.shape("XYX", "Y Y", "X X");
				recipe.setIngredient('X', Material.IRON_NUGGET);
				recipe.setIngredient('Y', Material.IRON_INGOT);
				recipe.setGroup("Chainmail Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Chainmail_Boots_1");
				recipe = new ShapedRecipe(key, con.equipment(Material.CHAINMAIL_BOOTS, "&7Chainmail Boots", 0, EquipmentSlot.FEET, 1.3, 0.65, 120, null));
				recipe.shape("X X", "Y Y", "   ");
				recipe.setIngredient('X', Material.IRON_NUGGET);
				recipe.setIngredient('Y', Material.IRON_INGOT);
				recipe.setGroup("Chainmail Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Chainmail_Boots_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.CHAINMAIL_BOOTS, "&7Chainmail Boots", 0, EquipmentSlot.FEET, 1.3, 0.65, 120, null));
				recipe.shape("   ", "X X", "Y Y");
				recipe.setIngredient('X', Material.IRON_NUGGET);
				recipe.setIngredient('Y', Material.IRON_INGOT);
				recipe.setGroup("Chainmail Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
	}
	
	public void registerRecipes() {
		Consumable con = new Consumable();
		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
		Iterator<Recipe> iter = Bukkit.recipeIterator();
		iter.forEachRemaining(recipeList::add); 
		for(int i = 0; i < recipeList.size(); i++) {
			Recipe r = recipeList.get(i);
			if(r instanceof ShapedRecipe) {
				ShapedRecipe rec = (ShapedRecipe) r;
				//-----------------------------------------------------------------------------------
				//Swords
				//-----------------------------------------------------------------------------------
				if(rec.getResult().getType() == Material.WOODEN_SWORD) {
					ItemStack item = con.equipment(Material.WOODEN_SWORD, "&7Wooden Sword", 0, EquipmentSlot.HAND, 2.7, 0.8, 80, null);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), item);
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.STONE_SWORD) {
					ItemStack item = con.equipment(Material.STONE_SWORD, "&7Stone Sword", 0, EquipmentSlot.HAND, 3.0, 0.83, 120, null);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), item);
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.IRON_SWORD) {
					ItemStack item = con.equipment(Material.IRON_SWORD, "&7Iron Sword", 0, EquipmentSlot.HAND, 3.3, 0.86, 160, null);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), item);
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.GOLDEN_SWORD) {
					ItemStack item = con.equipment(Material.GOLDEN_SWORD, "&7Golden Sword", 0, EquipmentSlot.HAND, 2.4, 1.1, 40, null);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), item);
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.DIAMOND_SWORD) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_SWORD, "&7Diamond Sword", 0, EquipmentSlot.HAND, 3.9, 0.92, 200, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Pickaxes
				//-----------------------------------------------------------------------------------
				if(rec.getResult().getType() == Material.WOODEN_PICKAXE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.WOODEN_PICKAXE, "&7Wooden Pickaxe", 0, EquipmentSlot.HAND, 1.8, 0.8, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.STONE_PICKAXE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.STONE_PICKAXE, "&7Stone Pickaxe", 0, EquipmentSlot.HAND, 2.0, 0.75, 120, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.IRON_PICKAXE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_PICKAXE, "&7Iron Pickaxe", 0, EquipmentSlot.HAND, 2.2, 0.7, 160, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.GOLDEN_PICKAXE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_PICKAXE, "&7Golden Pickaxe", 0, EquipmentSlot.HAND, 1.6, 0.9, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.DIAMOND_PICKAXE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_PICKAXE, "&7Stone Pickaxe", 0, EquipmentSlot.HAND, 2.6, 0.6, 200, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Axes
				//-----------------------------------------------------------------------------------
				if(rec.getResult().getType() == Material.WOODEN_AXE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.WOODEN_AXE, "&7Wooden Axe", 0, EquipmentSlot.HAND, 5.0, 0.32, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.STONE_AXE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.STONE_AXE, "&7Stone Axe", 0, EquipmentSlot.HAND, 5.25, 0.33, 120, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.IRON_AXE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_AXE, "&7Iron Axe", 0, EquipmentSlot.HAND, 5.5, 0.35, 160, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.GOLDEN_AXE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_AXE, "&7Golden Axe", 0, EquipmentSlot.HAND, 4.25, 0.42, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.DIAMOND_AXE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_AXE, "&7Diamond Axe", 0, EquipmentSlot.HAND, 6.0, 0.38, 200, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Shovel
				//-----------------------------------------------------------------------------------
				if(rec.getResult().getType() == Material.WOODEN_SHOVEL) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.WOODEN_SHOVEL, "&7Wooden Shovel", 0, EquipmentSlot.HAND, 2.3, 0.9, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.STONE_SHOVEL) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.STONE_SHOVEL, "&7Stone Shovel", 0, EquipmentSlot.HAND, 2.45, 0.93, 120, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.IRON_SHOVEL) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_SHOVEL, "&7Iron Shovel", 0, EquipmentSlot.HAND, 2.6, 0.96, 160, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.GOLDEN_SHOVEL) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_SHOVEL, "&7Golden Shovel", 0, EquipmentSlot.HAND, 1.8, 1.1, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.DIAMOND_SHOVEL) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", 0, EquipmentSlot.HAND, 3.2, 1.02, 200, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Hoes
				//-----------------------------------------------------------------------------------
				if(rec.getResult().getType() == Material.WOODEN_HOE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.WOODEN_HOE, "&7Wooden Hoe", 0, EquipmentSlot.HAND, 1.5, 1.2, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.STONE_HOE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.STONE_HOE, "&7Stone Hoe", 0, EquipmentSlot.HAND, 1.6, 1.24, 120, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.IRON_HOE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_HOE, "&7Iron Hoe", 0, EquipmentSlot.HAND, 1.7, 1.28, 160, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.GOLDEN_HOE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_HOE, "&7Golden Hoe", 0, EquipmentSlot.HAND, 1.3, 1.44, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.DIAMOND_HOE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_HOE, "&7Diamond Hoe", 0, EquipmentSlot.HAND, 1.9, 1.36, 200, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Leather Armor
				//-----------------------------------------------------------------------------------
				if(rec.getResult().getType() == Material.LEATHER_HELMET) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.LEATHER_HELMET, "&7Leather Helmet", 0, EquipmentSlot.HEAD, 1.0, 0.5, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.LEATHER_CHESTPLATE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.LEATHER_CHESTPLATE, "&7Leather Chestplate", 0, EquipmentSlot.CHEST, 1.0, 0.5, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.LEATHER_LEGGINGS) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.LEATHER_LEGGINGS, "&7Leather Leggings", 0, EquipmentSlot.LEGS, 1.0, 0.5, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.LEATHER_BOOTS) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.LEATHER_BOOTS, "&7Leather Boots", 0, EquipmentSlot.FEET, 1.0, 0.5, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Golden Armor
				//-----------------------------------------------------------------------------------
				if(rec.getResult().getType() == Material.GOLDEN_HELMET) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_HELMET, "&7Golden Helmet", 0, EquipmentSlot.HEAD, 0.7, 1.0, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.GOLDEN_CHESTPLATE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_CHESTPLATE, "&7Golden Chestplate", 0, EquipmentSlot.CHEST, 0.7, 1.0, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.GOLDEN_LEGGINGS) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_LEGGINGS, "&7Golden Leggings", 0, EquipmentSlot.LEGS, 0.7, 1.0, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.GOLDEN_BOOTS) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_BOOTS, "&7Golden Boots", 0, EquipmentSlot.FEET, 0.7, 1.0, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Iron Armor
				//-----------------------------------------------------------------------------------
				if(rec.getResult().getType() == Material.IRON_HELMET) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_HELMET, "&7Iron Helmet", 0, EquipmentSlot.HEAD, 1.3, 0.73, 160, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.IRON_CHESTPLATE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_CHESTPLATE, "&7Iron Chestplate", 0, EquipmentSlot.CHEST, 1.3, 0.73, 160, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.IRON_LEGGINGS) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_LEGGINGS, "&7Iron Leggings", 0, EquipmentSlot.LEGS, 1.3, 0.73, 160, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.IRON_BOOTS) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_BOOTS, "&7Iron Boots", 0, EquipmentSlot.FEET, 1.3, 0.73, 160, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Iron Armor
				//-----------------------------------------------------------------------------------
				if(rec.getResult().getType() == Material.DIAMOND_HELMET) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_HELMET, "&7Diamond Helmet", 0, EquipmentSlot.HEAD, 1.5, 0.85, 200, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.DIAMOND_CHESTPLATE) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_CHESTPLATE, "&7Diamond Chestplate", 0, EquipmentSlot.CHEST, 1.5, 0.85, 200, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.DIAMOND_LEGGINGS) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_LEGGINGS, "&7Diamond Leggings", 0, EquipmentSlot.LEGS, 1.5, 0.85, 200, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				if(rec.getResult().getType() == Material.DIAMOND_BOOTS) {
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_BOOTS, "&7Diamond Boots", 0, EquipmentSlot.FEET, 1.5, 0.85, 200, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipeList.set(i, recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
			}
		}
		Bukkit.clearRecipes();
		for(Recipe rec : recipeList) {
			Bukkit.addRecipe(rec);
		}
		for(Entry<String, Pair<Condition, CommandFile>> entry : this.listConsumables.entrySet()) {
			entry.getValue().getValue().registerRecipe();
		}
		
	}
	
	public HashMap<String, Pair<Condition, CommandFile>> getConsumables(){
		return this.listConsumables;
	}
	
	public ArrayList<Recipe> getInstantUnlocks(){
		return this.instantUnlock;
	}
	
	public ArrayList<Pair<ArrayList<UnlockCraftCondition>, Recipe>> getCustomRecipeList(){
		return this.listRecipes;
	}
	public ItemStack item(String name, Material mat, int amount, String lore) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		String parts[] = lore.split("//");
		for(int i = 0; i < parts.length; i++) {
			parts[i] = new CCT().colorize(parts[i]);
		}
		meta.setLore(new ArrayList<String>(Arrays.asList(parts)));
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack heart(String part, Material type) {
		ItemStack item = new ItemStack(type, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.LURE, 0, true);
		meta.setDisplayName(new CCT().colorize("&7" + part + " Heart"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7It's a collection of compressed wood, interesting."));
		if(type.toString().contains("LOG")) {
			lore.add(new CCT().colorize("&7Due to its compressed properties, it has started to glow?"));
		}
		else if(type == Material.GOLD_INGOT) {
			lore.add(new CCT().colorize("&7It shines when you need it the most? Or not."));
		}
		else if(type == Material.COBBLESTONE) {
			lore.add(new CCT().colorize("&7It's harder, stronger, faster and better for sure."));
		}
		else if(type == Material.IRON_INGOT) {
			lore.add(new CCT().colorize("&7The hard properties of this material are incredible."));
		}
		else if(type == Material.DIAMOND) {
			lore.add(new CCT().colorize("&7The strongest of them all... Right?"));
		}
		lore.add(new CCT().colorize("&7Maybe you can use this for something handy..."));
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack equipment(Material mat, String name, int tier, EquipmentSlot equip, double value1, double value2, int durability, HashMap<String, Integer> enchants) {
		ItemStack item = new ItemStack(mat, 1);
		if(durability > 0) {
			NBTItem i = new NBTItem(item);
			i.setInteger("Durability", durability);
			i.setInteger("MaxDurability", durability);
			item = i.getItem();
		}
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(new CCT().colorize(name));
		ArrayList<String> lore = new ArrayList<String>();
		if(enchants != null) {
			for(Entry<String, Integer> map : enchants.entrySet()) {
				lore.add(new CCT().colorize("&9" + map.getKey() + " " + map.getValue()));
			}
		}
		lore.add(new CCT().colorize("&7-----------------------"));
		if(equip == EquipmentSlot.HAND) {
			lore.add(new CCT().colorize("&7Attack Damage: &6" + value1));
			lore.add(new CCT().colorize("&7Attack Speed: &6" + value2));
		}
		else if(equip == EquipmentSlot.HEAD || equip == EquipmentSlot.CHEST || equip == EquipmentSlot.LEGS || equip == EquipmentSlot.FEET) {
			lore.add(new CCT().colorize("&7Armor Defense: &6" + value1));
			lore.add(new CCT().colorize("&7Armor Toughness: &6" + value2));
		}
		else if(equip == EquipmentSlot.OFF_HAND) {
			lore.add(new CCT().colorize("&7Armor Toughness: &6" + value1));
			lore.add(new CCT().colorize("&7Cooldown: &b" + value2));
		}
		lore.add(new CCT().colorize("&7-----------------------"));
		if(durability != 0) {
			lore.add(new CCT().colorize("&7Durability: &6" + durability + " &7/ &6" + durability));
		}
		lore.add(new CCT().colorize("&7Rarity: &fRegular"));
		if(tier != 0) {
			int max = 1;
			if(item.getType().toString().contains("STONE")) {
				max = 2;
			}
			if(item.getType().toString().contains("IRON")) {
				max = 3;
			}
			if(item.getType().toString().contains("DIAMOND")) {
				max = 4;
			}
			if(item.getType().toString().contains("GOLDEN")) {
				meta.addEnchant(Enchantment.DIG_SPEED, (int)Math.floor(tier * 2.5), true);
				if(tier == 2) {
					meta.addEnchant(Enchantment.DURABILITY, 1, true);
				}
			}
			else {
				meta.addEnchant(Enchantment.DIG_SPEED, tier, true);
				if((int)Math.floor(tier) >= 1) {
					meta.addEnchant(Enchantment.DURABILITY, (int)Math.floor(tier), true);
				}
				if(tier >= 3) {
					meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, tier - 2, true);
				}
			}
			if(item.getType().toString().contains("PICKAXE")) {
				lore.add(new CCT().colorize("&7-----------------------"));
				lore.add(new CCT().colorize("&7A powerfull condensed pickaxe."));
			}
			else if(item.getType().toString().contains("AXE")) {
				lore.add(new CCT().colorize("&7-----------------------"));
				lore.add(new CCT().colorize("&7A powerfull condensed axe."));
			}
			else if(item.getType().toString().contains("SHOVEL")) {
				lore.add(new CCT().colorize("&7-----------------------"));
				lore.add(new CCT().colorize("&7A powerfull condensed shovel."));
			}
			if(tier < max) {
				lore.add(new CCT().colorize("&7It seems like it can still become stronger..."));
			}
		}
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.setLore(lore);
		item.setItemMeta(meta);
		if(equip == EquipmentSlot.HAND) {
			return attr.stripModifier(item, new ArrayList<Attribute>(Arrays.asList(Attribute.GENERIC_ATTACK_DAMAGE, Attribute.GENERIC_ATTACK_SPEED)), "mainhand");
		}
		else if(equip == EquipmentSlot.HEAD || equip == EquipmentSlot.CHEST || equip == EquipmentSlot.LEGS || equip == EquipmentSlot.FEET) {
			if(equip == EquipmentSlot.HEAD) {
				return attr.stripModifier(item, new ArrayList<Attribute>(Arrays.asList(Attribute.GENERIC_ARMOR, Attribute.GENERIC_ARMOR_TOUGHNESS)), "head");
			}
			else if(equip == EquipmentSlot.CHEST) {
				return attr.stripModifier(item, new ArrayList<Attribute>(Arrays.asList(Attribute.GENERIC_ARMOR, Attribute.GENERIC_ARMOR_TOUGHNESS)), "chest");
			}
			else if(equip == EquipmentSlot.LEGS) {
				return attr.stripModifier(item, new ArrayList<Attribute>(Arrays.asList(Attribute.GENERIC_ARMOR, Attribute.GENERIC_ARMOR_TOUGHNESS)), "legs");
			}
			else if(equip == EquipmentSlot.FEET) {
				return attr.stripModifier(item, new ArrayList<Attribute>(Arrays.asList(Attribute.GENERIC_ARMOR, Attribute.GENERIC_ARMOR_TOUGHNESS)), "feet");
			}
		}
		else if(equip == EquipmentSlot.OFF_HAND) {
			return attr.stripModifier(item, new ArrayList<Attribute>(Arrays.asList(Attribute.GENERIC_ATTACK_DAMAGE, Attribute.GENERIC_ATTACK_SPEED)), "offhand");
		}
		return item;
	}
}
