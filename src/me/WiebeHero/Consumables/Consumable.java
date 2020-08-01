package me.WiebeHero.Consumables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
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
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.Consumables.ConsumableCondition.Condition;
import me.WiebeHero.Consumables.Unlock.UnlockCraftCondition;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.CustomMethods.NewAttribute;
import me.WiebeHero.CustomMethods.PotionM;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Factions.DFFactionManager;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_13_R2.MinecraftKey;

public class Consumable {
	private PotionM p;
	private DFFactionManager facManager;
	private DFPlayerManager dfManager;
	private ItemStackBuilder builder;
	public Consumable(DFPlayerManager dfManager, DFFactionManager facManager, PotionM p, ItemStackBuilder builder) {
		this.facManager = facManager;
		this.dfManager = dfManager;
		this.builder = builder;
		this.p = p;
	}
	
	private NewAttribute attr = new NewAttribute();
	public HashMap<String, Pair<Condition, CommandFile>> listConsumables;
	public ArrayList<Pair<ArrayList<UnlockCraftCondition>, Recipe>> listRecipes;
	public ArrayList<Recipe> instantUnlock = new ArrayList<Recipe>();
	
	public void loadConsumables() {
		Consumable con = this;
		this.listRecipes = new ArrayList<Pair<ArrayList<UnlockCraftCondition>, Recipe>>();
		this.listConsumables = new HashMap<String, Pair<Condition, CommandFile>>();
		this.listConsumables.put("Santa's_Cookie", new Pair<>(Condition.CONSUME, new CommandFile() {
			@Override
			public void activateConsumable(Player eaten, PlayerInteractEvent event) {
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 2.0F, 1.0F);
				if(dfManager.contains(eaten)) {
					DFPlayer df = dfManager.getEntity(eaten);
					df.addAtkCal(3.0, 350);
					df.addSpdCal(1.0, 350);
					df.addCrtCal(1.0, 350);
					df.addRndCal(4.0, 350);
					df.addHpCal(10.0, 350);
					df.addDfCal(2.0, 350);
				}
			}
		}));
		this.listConsumables.put("Smooth_Fluzgla", new Pair<>(Condition.RIGHT_CLICK, new CommandFile() {
			@Override
			public void activateConsumable(Player eaten, PlayerInteractEvent event) {
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
				if(eaten.getHealth() + 2.5 <= eaten.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
					eaten.setHealth(eaten.getHealth() + 2.5);
				}
				else {
					eaten.setHealth(eaten.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				}
				eaten.setFoodLevel(eaten.getFoodLevel() + 2);
			}
			@Override
			public void registerRecipe() {
				ItemStack item = builder.constructItem(
						Material.WHITE_WOOL,
						1,
						"&7Smooth Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Heal: &c2.5 HP",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b3.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 60)
				);
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
			@Override
			public void activateConsumable(Player eaten, PlayerInteractEvent event) {
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_GHAST_SHOOT, 2.0F, 0.5F);
				p.applyEffect(eaten, PotionEffectType.SPEED, 0, 150);
				if(eaten instanceof Player) {
					Player p = (Player) eaten;
					p.setFoodLevel(p.getFoodLevel() + 2);
				}
				for(Entity e : eaten.getNearbyEntities(5.0, 5.0, 5.0)) {
					if(e != eaten) {
						if(e instanceof LivingEntity) {
							LivingEntity ent = (LivingEntity) e;
							if(!facManager.isFriendly(eaten, ent)) {
								p.applyEffect(ent, PotionEffectType.BLINDNESS, 0, 150);
							}
						}
					}
				}
			}
			@Override
			public void registerRecipe() {
				ItemStack item = builder.constructItem(
						Material.WHITE_WOOL,
						1,
						"&7Smooth Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Heal: &c2.5 HP",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b3.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 60)
				);
				ItemStack result = builder.constructItem(
						Material.BLACK_WOOL,
						1,
						"&7Shadow Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Speed I (To you): &b7.5 Seconds",
							"  &7Blindness I (To nearby enemies): &b7.5 Seconds",
							"  &7Food Gain: &c2",
							"  &7Range: &65.0 Blocks",
							"  &7Cooldown: &b7.5 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 150)
				);
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
			@Override
			public void activateConsumable(Player eaten, PlayerInteractEvent event) {
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
				p.applyEffect(eaten, PotionEffectType.FAST_DIGGING, 0, 200);
				eaten.setFoodLevel(eaten.getFoodLevel() + 1);
			}
			@Override
			public void registerRecipe() {
				ItemStack item = builder.constructItem(
						Material.WHITE_WOOL,
						1,
						"&7Smooth Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Heal: &c2.5 HP",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b3.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 60)
				);
				ItemStack result = builder.constructItem(
						Material.ORANGE_WOOL,
						1,
						"&7Hastened Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Haste I (To you): &b10 Seconds",
							"  &7Food Gain: &c1",
							"  &7Cooldown: &b10.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 200)
				);
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
			@Override
			public void activateConsumable(Player eaten, PlayerInteractEvent event) {
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
				eaten.setFoodLevel(eaten.getFoodLevel() + 1);
				for(int i = 0; i < 5; i++) {
					eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2.0F, 1.0F);
					float yaw = eaten.getLocation().getYaw();
	                double D = 0.5;
	                double x = -D*Math.sin(yaw*Math.PI/180);
	                double z = D*Math.cos(yaw*Math.PI/180);
					eaten.getWorld().spawn(eaten.getLocation().add(x, 1.5, z), SmallFireball.class);
				}
			}
			@Override
			public void registerRecipe() {
				ItemStack item = builder.constructItem(
						Material.WHITE_WOOL,
						1,
						"&7Smooth Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Heal: &c2.5 HP",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b3.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 60)
				);
				ItemStack result = builder.constructItem(
						Material.RED_WOOL,
						1,
						"&7Bursting Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Shoot Fireballs",
							"  &7Amount: &65",
							"  &7Food Gain: &c1",
							"  &7Cooldown: &b10.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 200)
				);
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
			@Override
			public void activateConsumable(Player eaten, PlayerInteractEvent event) {
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
				eaten.getWorld().playSound(eaten.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 2.0F);
				eaten.getWorld().spawnParticle(Particle.HEART, eaten.getLocation().add(0, 2.0, 0), 20, 0.2, 0.2, 0.2);
				p.applyEffect(eaten, PotionEffectType.REGENERATION, 0, 180);
				eaten.setFoodLevel(eaten.getFoodLevel() + 1);
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
							if(facManager.isFriendly(eaten, ent)) {
								ent.getWorld().playSound(eaten.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 2.0F);
								ent.getWorld().spawnParticle(Particle.HEART, eaten.getLocation().add(0, 2.2, 0), 20, 0.2, 0.2, 0.2);
								p.applyEffect(eaten, PotionEffectType.REGENERATION, 1, 100);
								if(ent.getHealth() + 5.0 <= ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
									ent.setHealth(ent.getHealth() + 5.0);
								}
								else {
									ent.setHealth(ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
								}
							}
						}
					}
				}
			}
			@Override
			public void registerRecipe() {
				ItemStack item = builder.constructItem(
						Material.WHITE_WOOL,
						1,
						"&7Smooth Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Heal: &c2.5 HP",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b3.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 60)
				);
				ItemStack result = builder.constructItem(
						Material.PINK_WOOL,
						1,
						"&7Healing Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Heal (To you): &c2.5 HP",
							"  &7Regen I (To you): &b9.0 Seconds",
							"  &7Heal (To members and allies): &c5.0 HP",
							"  &7Regen II (To members and allies): &b5.0 Seconds",
							"  &7Food Gain (To you): &c1",
							"  &7Range: &67.5 Blocks",
							"  &7Cooldown: &b15.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 300)
				);
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
			@Override
			public void activateConsumable(Player eaten, PlayerInteractEvent event) {
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 2.0F, 1.5F);
				int random = new Random().nextInt(types.size());
				p.applyEffect(eaten, types.get(random), 2, 100);
				eaten.setFoodLevel(eaten.getFoodLevel() + 2);
			}
			@Override
			public void registerRecipe() {
				ItemStack item = builder.constructItem(
						Material.WHITE_WOOL,
						1,
						"&7Smooth Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Heal: &c2.5 HP",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b3.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 60)
				);
				ItemStack result = builder.constructItem(
						Material.PURPLE_WOOL,
						1,
						"&7Godlike Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Random Effect III (To you): &b5.0 Seconds",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b25.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 500)
				);
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
			@Override
			public void activateConsumable(Player eaten, PlayerInteractEvent event) {
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_DOLPHIN_AMBIENT, 2.0F, 1.5F);
				p.applyEffect(eaten, PotionEffectType.DOLPHINS_GRACE, 0, 200);
				p.applyEffect(eaten, PotionEffectType.WATER_BREATHING, 0, 200);
				eaten.setFoodLevel(eaten.getFoodLevel() + 2);
			}
			@Override
			public void registerRecipe() {
				ItemStack item = builder.constructItem(
						Material.WHITE_WOOL,
						1,
						"&7Smooth Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Heal: &c2.5 HP",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b3.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 60)
				);
				ItemStack result = builder.constructItem(
						Material.LIGHT_BLUE_WOOL,
						1,
						"&7Aquatic Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Dolphins Grace I: &b10.0 Seconds",
							"  &7Water Breathing I: &b10.0 Seconds",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b15.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 300)
				);
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
			@Override
			public void activateConsumable(Player eaten, PlayerInteractEvent event) {
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_HUSK_AMBIENT, 2.0F, 0.5F);
				eaten.setFoodLevel(eaten.getFoodLevel() + 3);
				for(Entity e : eaten.getNearbyEntities(6.0, 6.0, 6.0)) {
					if(e != eaten) {
						if(e instanceof LivingEntity) {
							LivingEntity ent = (LivingEntity) e;
							if(!facManager.isFriendly(eaten, ent)) {
								p.applyEffect(ent, PotionEffectType.SLOW, 1, 100);
							}
						}
					}
				}
			}
			@Override
			public void registerRecipe() {
				ItemStack item = builder.constructItem(
						Material.WHITE_WOOL,
						1,
						"&7Smooth Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Heal: &c2.5 HP",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b3.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 60)
				);
				ItemStack result = builder.constructItem(
						Material.BLUE_WOOL,
						1,
						"&7Frozen Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Slowness II (To enemies): &b5.0 Seconds",
							"  &7Food Gain: &c3",
							"  &7Range: &66.0 Blocks",
							"  &7Cooldown: &b15.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 300)
				);
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
			@Override
			public void activateConsumable(Player eaten, PlayerInteractEvent event) {
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
				eaten.getWorld().playSound(eaten.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0F, 1.0F);
				eaten.setFoodLevel(eaten.getFoodLevel() + 3);
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
			}
			@Override
			public void registerRecipe() {
				ItemStack item = builder.constructItem(
						Material.WHITE_WOOL,
						1,
						"&7Smooth Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Heal: &c2.5 HP",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b3.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 60)
				);
				ItemStack result = builder.constructItem(
						Material.GREEN_WOOL,
						1,
						"&7Curing Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Remove random negative effect",
							"  &7Food Gain: &c3",
							"  &7Cooldown: &b17.5 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 350)
				);
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
			@Override
			public void activateConsumable(Player eaten, PlayerInteractEvent event) {
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_RABBIT_AMBIENT, 2.0F, 1.0F);
				eaten.setFoodLevel(eaten.getFoodLevel() + 2);
				p.applyEffect(eaten, PotionEffectType.JUMP, 1, 200);
			}
			@Override
			public void registerRecipe() {
				ItemStack item = builder.constructItem(
						Material.WHITE_WOOL,
						1,
						"&7Smooth Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Heal: &c2.5 HP",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b3.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 60)
				);
				ItemStack result = builder.constructItem(
						Material.LIME_WOOL,
						1,
						"&7Bouncy Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Jump Boost II: &b10.0 Seconds",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b12.5 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 250)
				);
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
			@Override
			public void activateConsumable(Player eaten, PlayerInteractEvent event) {
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
				eaten.getWorld().playSound(eaten.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 2.0F, 0.5F);
				for(Entity e : eaten.getNearbyEntities(7.5, 7.5, 7.5)) {
					if(e != eaten) {
						if(e instanceof LivingEntity) {
							LivingEntity ent = (LivingEntity) e;
							if(!facManager.isFriendly(eaten, e)) {
								p.applyEffect(ent, PotionEffectType.CONFUSION, 1, 250);
							}
						}
					}
				}
			}
			@Override
			public void registerRecipe() {
				ItemStack item = builder.constructItem(
						Material.WHITE_WOOL,
						1,
						"&7Smooth Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Heal: &c2.5 HP",
							"  &7Food Gain: &c2",
							"  &7Cooldown: &b3.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 60)
				);
				ItemStack result = builder.constructItem(
						Material.BROWN_WOOL,
						1,
						"&7Poopy Fluzgla",
						new String[] {
							"&7-----------------------",
							"&7When consumed:",
							"  &7Nausea II (To enemies): &b10.0 Seconds",
							"  &7Range: &67.5 Blocks",
							"  &7Cooldown: &b10.0 Seconds",
							"&7-----------------------",
							"&7Also used as a crafting ingridient."
						},
						new Pair<String, Integer>("Cooldown", 200)
				);
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
			@SuppressWarnings("deprecation")
			@Override
			public void registerRecipe() {
				//-----------------------------------------------------
				//Small Hearts
				//-----------------------------------------------------
				
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Small_Bark_Heart_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.heart("Small Bark", Material.OAK_LOG));
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', new RecipeChoice.ExactChoice(new ItemStack(Material.OAK_PLANKS, 1), new ItemStack(Material.SPRUCE_PLANKS, 1), new ItemStack(Material.JUNGLE_PLANKS, 1), new ItemStack(Material.ACACIA_PLANKS, 1), new ItemStack(Material.DARK_OAK_PLANKS, 1)));
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				//-----------------------------------------------------
				//Medium Hearts
				//-----------------------------------------------------
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Bark_Heart_1");
				recipe = new ShapedRecipe(key, con.heart("Bark", Material.OAK_LOG));
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', con.heart("Small Bark", Material.OAK_LOG));
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				//-----------------------------------------------------
				//Big Hearts
				//-----------------------------------------------------
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Bark_Heart_1");
				recipe = new ShapedRecipe(key, con.heart("Big Bark", Material.OAK_LOG));
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', con.heart("Bark", Material.OAK_LOG));
				recipe.setGroup("Bark Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Wooden Armor", new Pair<>(Condition.NONE, new CommandFile() {
			@SuppressWarnings("deprecation")
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Bark", Material.OAK_LOG);
				//-----------------------------------------------------------------------------------
				//Wooden Armor
				//-----------------------------------------------------------------------------------
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Protection", 1);
				ItemStack item = con.equipment(Material.LEATHER_HELMET, "&7Wooden Helmet", EquipmentSlot.HEAD, 1.5, 0.75, 80, null, Color.fromRGB(145, 95, 33));
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Helmet1");
				ShapedRecipe recipe = new ShapedRecipe(key, item);
				recipe.shape("XXX", "X X", "   ");
				recipe.setIngredient('X', new RecipeChoice.ExactChoice(new ItemStack(Material.OAK_PLANKS, 1), new ItemStack(Material.DARK_OAK_PLANKS, 1), new ItemStack(Material.ACACIA_PLANKS, 1), new ItemStack(Material.SPRUCE_PLANKS, 1), new ItemStack(Material.JUNGLE_PLANKS, 1), new ItemStack(Material.BIRCH_PLANKS, 1)));
				recipe.setGroup("Wooden Helmets");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_HELMET, "&7Wooden Helmet", EquipmentSlot.HEAD, 1.5, 0.75, 80, null, Color.fromRGB(145, 95, 33));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Helmet2");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("   ", "XXX", "X X");
				recipe.setIngredient('X', new RecipeChoice.ExactChoice(new ItemStack(Material.OAK_PLANKS, 1), new ItemStack(Material.DARK_OAK_PLANKS, 1), new ItemStack(Material.ACACIA_PLANKS, 1), new ItemStack(Material.SPRUCE_PLANKS, 1), new ItemStack(Material.JUNGLE_PLANKS, 1), new ItemStack(Material.BIRCH_PLANKS, 1)));
				recipe.setGroup("Wooden Helmets");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_HELMET, "&7Wooden Helmet", EquipmentSlot.HEAD, 1.5, 0.75, 100, tier1, Color.fromRGB(145, 95, 33));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Helmet_1");
				recipe = new ShapedRecipe(key, item);
				recipe.shape(" Y ", "YXY", " Y ");
				recipe.setIngredient('Y', heart);
				recipe.setIngredient('X', con.equipment(Material.LEATHER_HELMET, "&7Wooden Helmet", EquipmentSlot.HEAD, 1.5, 0.75, 80, null, Color.fromRGB(145, 95, 33)));
				recipe.setGroup("Wooden Helmets");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_CHESTPLATE, "&7Wooden Chestplate", EquipmentSlot.CHEST, 1.5, 0.75, 80, null, Color.fromRGB(145, 95, 33));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Chestplate");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("X X", "XXX", "XXX");
				recipe.setIngredient('X', new RecipeChoice.ExactChoice(new ItemStack(Material.OAK_PLANKS, 1), new ItemStack(Material.DARK_OAK_PLANKS, 1), new ItemStack(Material.ACACIA_PLANKS, 1), new ItemStack(Material.SPRUCE_PLANKS, 1), new ItemStack(Material.JUNGLE_PLANKS, 1), new ItemStack(Material.BIRCH_PLANKS, 1)));
				recipe.setGroup("Wooden Chestplates");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_CHESTPLATE, "&7Wooden Chestplate", EquipmentSlot.CHEST, 1.5, 0.75, 100, tier1, Color.fromRGB(145, 95, 33));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Chestplate_1");
				recipe = new ShapedRecipe(key, item);
				recipe.shape(" Y ", "YXY", " Y ");
				recipe.setIngredient('Y', heart);
				recipe.setIngredient('X', con.equipment(Material.LEATHER_CHESTPLATE, "&7Wooden Chestplate", EquipmentSlot.CHEST, 1.5, 0.75, 80, null, Color.fromRGB(145, 95, 33)));
				recipe.setGroup("Wooden Chestplates");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_LEGGINGS, "&7Wooden Leggings", EquipmentSlot.LEGS, 1.5, 0.75, 80, null, Color.fromRGB(145, 95, 33));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Leggings");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("XXX", "X X", "X X");
				recipe.setIngredient('X', new RecipeChoice.ExactChoice(new ItemStack(Material.OAK_PLANKS, 1), new ItemStack(Material.DARK_OAK_PLANKS, 1), new ItemStack(Material.ACACIA_PLANKS, 1), new ItemStack(Material.SPRUCE_PLANKS, 1), new ItemStack(Material.JUNGLE_PLANKS, 1), new ItemStack(Material.BIRCH_PLANKS, 1)));
				recipe.setGroup("Wooden Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_LEGGINGS, "&7Wooden Leggings", EquipmentSlot.LEGS, 1.5, 0.75, 100, tier1, Color.fromRGB(145, 95, 33));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Leggings_1");
				recipe = new ShapedRecipe(key, item);
				recipe.shape(" Y ", "YXY", " Y ");
				recipe.setIngredient('Y', heart);
				recipe.setIngredient('X', con.equipment(Material.LEATHER_LEGGINGS, "&7Wooden Leggings", EquipmentSlot.LEGS, 1.5, 0.75, 80, null, Color.fromRGB(145, 95, 33)));
				recipe.setGroup("Wooden Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_BOOTS, "&7Wooden Boots", EquipmentSlot.FEET, 1.5, 0.75, 80, null, Color.fromRGB(145, 95, 33));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Boots1");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("X X", "X X", "   ");
				recipe.setIngredient('X', new RecipeChoice.ExactChoice(new ItemStack(Material.OAK_PLANKS, 1), new ItemStack(Material.DARK_OAK_PLANKS, 1), new ItemStack(Material.ACACIA_PLANKS, 1), new ItemStack(Material.SPRUCE_PLANKS, 1), new ItemStack(Material.JUNGLE_PLANKS, 1), new ItemStack(Material.BIRCH_PLANKS, 1)));
				recipe.setGroup("Wooden Boot");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_BOOTS, "&7Wooden Boots", EquipmentSlot.FEET, 1.5, 0.75, 80, null, Color.fromRGB(145, 95, 33));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Boots2");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("   ", "X X", "X X");
				recipe.setIngredient('X', new RecipeChoice.ExactChoice(new ItemStack(Material.OAK_PLANKS, 1), new ItemStack(Material.DARK_OAK_PLANKS, 1), new ItemStack(Material.ACACIA_PLANKS, 1), new ItemStack(Material.SPRUCE_PLANKS, 1), new ItemStack(Material.JUNGLE_PLANKS, 1), new ItemStack(Material.BIRCH_PLANKS, 1)));
				recipe.setGroup("Wooden Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_BOOTS, "&7Wooden Boots", EquipmentSlot.FEET, 1.5, 0.75, 100, tier1, Color.fromRGB(145, 95, 33));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Boots_1");
				recipe = new ShapedRecipe(key, item);
				recipe.shape(" Y ", "YXY", " Y ");
				recipe.setIngredient('Y', heart);
				recipe.setIngredient('X', con.equipment(Material.LEATHER_BOOTS, "&7Wooden Boots", EquipmentSlot.FEET, 1.5, 0.75, 80, null, Color.fromRGB(145, 95, 33)));
				recipe.setGroup("Wooden Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));

			}
		}));
		this.listConsumables.put("Wooden Swords", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Bark", Material.OAK_LOG);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Sword_1");
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Sharpness", 1);
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.WOODEN_SWORD, "&7Wooden Sword", EquipmentSlot.HAND, 2.7, 0.8, 100, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.WOODEN_SWORD, "&7Wooden Sword", EquipmentSlot.HAND, 2.7, 0.8, 80, null));
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				recipe.setGroup("Wooden Swords");
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Wooden Pickaxes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Bark", Material.OAK_LOG);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Pickaxe_1");
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 1);
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.WOODEN_PICKAXE, "&7Wooden Pickaxe", EquipmentSlot.HAND, 1.8, 0.8, 100, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.WOODEN_PICKAXE, "&7Wooden Pickaxe", EquipmentSlot.HAND, 1.8, 0.8, 80, null));
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				recipe.setGroup("Wooden Pickaxes");
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Wooden Axes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Bark", Material.OAK_LOG);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Axe_1");
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 1);
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.WOODEN_AXE, "&7Wooden Axe", EquipmentSlot.HAND, 5.0, 0.32, 100, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.WOODEN_AXE, "&7Wooden Axe", EquipmentSlot.HAND, 5.0, 0.32, 80, null));
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				recipe.setGroup("Wooden Axes");
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Wooden Shovels", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Bark", Material.OAK_LOG);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Shovel_1");
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 1);
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.WOODEN_SHOVEL, "&7Wooden Shovel", EquipmentSlot.HAND, 2.3, 0.94, 100, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.WOODEN_SHOVEL, "&7Wooden Shovel", EquipmentSlot.HAND, 2.3, 0.94, 80, null));
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				recipe.setGroup("Wooden Shovels");
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Wooden Hoes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Bark", Material.OAK_LOG);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Wooden_Hoe_1");
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Unbreaking", 1);
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.WOODEN_HOE, "&7Wooden Hoe", EquipmentSlot.HAND, 1.5, 1.2, 100, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.WOODEN_HOE, "&7Wooden Hoe", EquipmentSlot.HAND, 1.5, 1.2, 80, null));
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				recipe.setGroup("Wooden Hoes");
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Gold Hearts", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Gold", Material.GOLD_INGOT);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Small_Gold_Heart");
				ShapedRecipe recipe = new ShapedRecipe(key, heart);
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', new ItemStack(Material.GOLD_INGOT, 1));
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Gold", Material.GOLD_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', con.heart("Small Gold", Material.GOLD_INGOT));
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Big Gold", Material.GOLD_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Gold_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', con.heart("Gold", Material.GOLD_INGOT));
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Golden Armor", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Gold", Material.GOLD_INGOT);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Protection", 2);
				tier1.put("Unbreaking", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Protection", 5);
				tier2.put("Unbreaking", 2);
				
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Helmet_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_HELMET, "&7Golden Helmet", EquipmentSlot.HEAD, 1.2, 1.25, 50, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_HELMET, "&7Golden Helmet", EquipmentSlot.HEAD, 1.2, 1.25, 40, null));
				recipe.setGroup("Golden Helmets");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Helmet_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_HELMET, "&7Golden Helmet", EquipmentSlot.HEAD, 1.2, 1.25, 60, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_HELMET, "&7Golden Helmet", EquipmentSlot.HEAD, 1.2, 1.25, 50, tier1));
				recipe.setGroup("Golden Helmets");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Chestplate_1");
				recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_CHESTPLATE, "&7Golden Chestplate", EquipmentSlot.CHEST, 1.2, 1.25, 50, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_CHESTPLATE, "&7Golden Chestplate", EquipmentSlot.CHEST, 1.2, 1.25, 40, null));
				recipe.setGroup("Golden Chestplates");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Chestplate_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_CHESTPLATE, "&7Golden Chestplate", EquipmentSlot.CHEST, 1.2, 1.25, 60, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_CHESTPLATE, "&7Golden Chestplate", EquipmentSlot.CHEST, 1.2, 1.25, 50, tier1));
				recipe.setGroup("Golden Chestplates");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Leggings_1");
				recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_LEGGINGS, "&7Golden Leggings", EquipmentSlot.LEGS, 1.2, 1.25, 50, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_LEGGINGS, "&7Golden Leggings", EquipmentSlot.LEGS, 1.2, 1.25, 40, null));
				recipe.setGroup("Golden Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Leggings_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_LEGGINGS, "&7Golden Leggings", EquipmentSlot.LEGS, 1.2, 1.25, 60, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_LEGGINGS, "&7Golden Leggings", EquipmentSlot.LEGS, 1.2, 1.25, 50, tier1));
				recipe.setGroup("Golden Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Boots_1");
				recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_BOOTS, "&7Golden Boots", EquipmentSlot.FEET, 1.2, 1.25, 50, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_BOOTS, "&7Golden Boots", EquipmentSlot.FEET, 1.2, 1.25, 40, null));
				recipe.setGroup("Golden Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Boots_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_BOOTS, "&7Golden Boots", EquipmentSlot.FEET, 1.2, 1.25, 60, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_BOOTS, "&7Golden Boots", EquipmentSlot.FEET, 1.2, 1.25, 50, tier1));
				recipe.setGroup("Golden Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Gold Swords", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Gold", Material.GOLD_INGOT);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Sharpness", 2);
				tier1.put("Unbreaking", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Sharpness", 5);
				tier2.put("Unbreaking", 2);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Sword_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_SWORD, "&7Golden Sword", EquipmentSlot.HAND, 2.4, 1.1, 50, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_SWORD, "&7Golden Sword", EquipmentSlot.HAND, 2.4, 1.1, 40, null));
				recipe.setGroup("Golden Swords");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Gold", Material.GOLD_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Sword_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_SWORD, "&7Golden Sword", EquipmentSlot.HAND, 2.4, 1.1, 60, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_SWORD, "&7Golden Sword", EquipmentSlot.HAND, 2.4, 1.1, 50, tier1));
				recipe.setGroup("Golden Swords");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Gold Pickaxes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Gold", Material.GOLD_INGOT);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 2);
				tier1.put("Unbreaking", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Efficiency", 5);
				tier2.put("Unbreaking", 2);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Pickaxe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_PICKAXE, "&7Golden Pickaxe", EquipmentSlot.HAND, 1.6, 0.9, 50, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_PICKAXE, "&7Golden Pickaxe", EquipmentSlot.HAND, 1.6, 0.9, 40, null));
				recipe.setGroup("Golden Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Gold", Material.GOLD_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Pickaxe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_PICKAXE, "&7Golden Pickaxe", EquipmentSlot.HAND, 1.6, 0.9, 60, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_PICKAXE, "&7Golden Pickaxe", EquipmentSlot.HAND, 1.6, 0.9, 50, tier1));
				recipe.setGroup("Golden Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Gold Axes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Gold", Material.GOLD_INGOT);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 2);
				tier1.put("Unbreaking", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Efficiency", 5);
				tier2.put("Unbreaking", 2);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Axe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_AXE, "&7Golden Axe", EquipmentSlot.HAND, 4.25, 0.42, 50, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_AXE, "&7Golden Axe", EquipmentSlot.HAND, 4.25, 0.42, 40, null));
				recipe.setGroup("Golden Axes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Gold", Material.GOLD_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Axe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_AXE, "&7Golden Axe", EquipmentSlot.HAND, 4.25, 0.42, 60, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_AXE, "&7Golden Axe", EquipmentSlot.HAND, 4.25, 0.42, 50, tier1));
				recipe.setGroup("Golden Axes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Gold Shovels", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Gold", Material.GOLD_INGOT);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 2);
				tier1.put("Unbreaking", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Efficiency", 5);
				tier2.put("Unbreaking", 2);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Shovel_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_SHOVEL, "&7Golden Shovel", EquipmentSlot.HAND, 1.8, 1.1, 50, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_SHOVEL, "&7Golden Shovel", EquipmentSlot.HAND, 1.8, 1.1, 40, null));
				recipe.setGroup("Golden Shovels");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Gold", Material.GOLD_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Shovel_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_SHOVEL, "&7Golden Shovel", EquipmentSlot.HAND, 1.8, 1.1, 60, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_SHOVEL, "&7Golden Shovel", EquipmentSlot.HAND, 1.8, 1.1, 50, tier1));
				recipe.setGroup("Golden Shovels");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Gold Hoes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Gold", Material.GOLD_INGOT);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Unbreaking", 2);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Unbreaking", 5);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Hoe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_HOE, "&7Golden Hoe", EquipmentSlot.HAND, 1.3, 1.44, 50, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_HOE, "&7Golden Hoe", EquipmentSlot.HAND, 1.3, 1.44, 40, null));
				recipe.setGroup("Golden Hoes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Gold", Material.GOLD_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Gold_Hoe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.GOLDEN_HOE, "&7Golden Hoe", EquipmentSlot.HAND, 1.3, 1.44, 60, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.GOLDEN_HOE, "&7Golden Hoe", EquipmentSlot.HAND, 1.3, 1.44, 50, tier1));
				recipe.setGroup("Golden Hoes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Cobblestone Hearts", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Small_Cobblestone_Heart");
				ShapedRecipe recipe = new ShapedRecipe(key, heart);
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', new ItemStack(Material.COBBLESTONE, 1));
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				recipe.setGroup("Cobblestone Hearts");
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Cobblestone", Material.COBBLESTONE);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Cobblestone_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', con.heart("Small Cobblestone", Material.COBBLESTONE));
				recipe.setGroup("Cobblestone Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Big Cobblestone", Material.COBBLESTONE);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Cobblestone_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', con.heart("Cobblestone", Material.COBBLESTONE));
				recipe.setGroup("Cobblestone Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Stone Armor", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Protection", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Protection", 2);
				tier2.put("Unbreaking", 1);
				//-----------------------------------------------------------------------------------
				//Stone Armor
				//-----------------------------------------------------------------------------------
				ItemStack item = con.equipment(Material.LEATHER_HELMET, "&7Stone Helmet", EquipmentSlot.HEAD, 1.65, 0.83, 125, null, Color.fromRGB(95, 95, 95));
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Helmet1");
				ShapedRecipe recipe = new ShapedRecipe(key, item);
				recipe.shape("XXX", "X X", "   ");
				recipe.setIngredient('X', new ItemStack(Material.COBBLESTONE, 1));
				recipe.setGroup("Stone Helmet");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_HELMET, "&7Stone Helmet", EquipmentSlot.HEAD, 1.65, 0.83, 125, null, Color.fromRGB(95, 95, 95));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Helmet2");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("   ", "XXX", "X X");
				recipe.setIngredient('X', new ItemStack(Material.COBBLESTONE, 1));
				recipe.setGroup("Stone Helmet");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_HELMET, "&7Stone Helmet", EquipmentSlot.HEAD, 1.65, 0.83, 150, tier1, Color.fromRGB(95, 95, 95));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Helmet_1");
				recipe = new ShapedRecipe(key, item);
				recipe.shape(" Y ", "YXY", " Y ");
				recipe.setIngredient('Y', heart);
				recipe.setIngredient('X', con.equipment(Material.LEATHER_HELMET, "&7Stone Helmet", EquipmentSlot.HEAD, 1.65, 0.83, 125, null, Color.fromRGB(95, 95, 95)));
				recipe.setGroup("Stone Helmets");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_HELMET, "&7Stone Helmet", EquipmentSlot.HEAD, 1.65, 0.83, 175, tier2, Color.fromRGB(95, 95, 95));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Helmet_2");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("YYY", "YXY", "YYY");
				recipe.setIngredient('Y', heart);
				recipe.setIngredient('X', con.equipment(Material.LEATHER_HELMET, "&7Stone Helmet", EquipmentSlot.HEAD, 1.65, 0.83, 150, tier1, Color.fromRGB(95, 95, 95)));
				recipe.setGroup("Stone Helmets");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_CHESTPLATE, "&7Stone Chestplate", EquipmentSlot.CHEST, 1.65, 0.83, 125, null, Color.fromRGB(95, 95, 95));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Chestplate");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("X X", "XXX", "XXX");
				recipe.setIngredient('X', new ItemStack(Material.COBBLESTONE, 1));
				recipe.setGroup("Stone Chestplate");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				item = con.equipment(Material.LEATHER_CHESTPLATE, "&7Stone Chestplate", EquipmentSlot.CHEST, 1.65, 0.83, 150, tier1, Color.fromRGB(95, 95, 95));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Chestplate_1");
				recipe = new ShapedRecipe(key, item);
				recipe.shape(" Y ", "YXY", " Y ");
				recipe.setIngredient('Y', heart);
				recipe.setIngredient('X', con.equipment(Material.LEATHER_CHESTPLATE, "&7Stone Chestplate", EquipmentSlot.CHEST, 1.65, 0.83, 125, null, Color.fromRGB(95, 95, 95)));
				recipe.setGroup("Stone Chestplates");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_CHESTPLATE, "&7Stone Chestplate", EquipmentSlot.CHEST, 1.65, 0.83, 175, tier2, Color.fromRGB(95, 95, 95));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Chestplate_2");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("YYY", "YXY", "YYY");
				recipe.setIngredient('Y', heart);
				recipe.setIngredient('X', con.equipment(Material.LEATHER_CHESTPLATE, "&7Stone Chestplate", EquipmentSlot.CHEST, 1.65, 0.83, 150, tier1, Color.fromRGB(95, 95, 95)));
				recipe.setGroup("Stone Chestplates");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_LEGGINGS, "&7Stone Leggings", EquipmentSlot.LEGS, 1.65, 0.83, 125, null, Color.fromRGB(95, 95, 95));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Leggings");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("XXX", "X X", "X X");
				recipe.setIngredient('X', new ItemStack(Material.COBBLESTONE, 1));
				recipe.setGroup("Stone Legging");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				item = con.equipment(Material.LEATHER_LEGGINGS, "&7Stone Leggings", EquipmentSlot.LEGS, 1.65, 0.83, 150, tier1, Color.fromRGB(95, 95, 95));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Leggings_1");
				recipe = new ShapedRecipe(key, item);
				recipe.shape(" Y ", "YXY", " Y ");
				recipe.setIngredient('Y', heart);
				recipe.setIngredient('X', con.equipment(Material.LEATHER_LEGGINGS, "&7Stone Leggings", EquipmentSlot.LEGS, 1.65, 0.83, 125, null, Color.fromRGB(95, 95, 95)));
				recipe.setGroup("Stone Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_LEGGINGS, "&7Stone Leggings", EquipmentSlot.LEGS, 1.65, 0.83, 175, tier2, Color.fromRGB(95, 95, 95));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Leggings_2");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("YYY", "YXY", "YYY");
				recipe.setIngredient('Y', heart);
				recipe.setIngredient('X', con.equipment(Material.LEATHER_LEGGINGS, "&7Stone Leggings", EquipmentSlot.LEGS, 1.65, 0.83, 150, tier1, Color.fromRGB(95, 95, 95)));
				recipe.setGroup("Stone Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_BOOTS, "&7Stone Boots", EquipmentSlot.FEET, 1.65, 0.83, 125, null, Color.fromRGB(95, 95, 95));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Boots1");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("X X", "X X", "   ");
				recipe.setIngredient('X', new ItemStack(Material.COBBLESTONE, 1));
				recipe.setGroup("Stone Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				item = con.equipment(Material.LEATHER_BOOTS, "&7Stone Boots", EquipmentSlot.FEET, 1.65, 0.83, 125, null, Color.fromRGB(95, 95, 95));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Boots2");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("   ", "X X", "X X");
				recipe.setIngredient('X', new ItemStack(Material.COBBLESTONE, 1));
				recipe.setGroup("Stone Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				item = con.equipment(Material.LEATHER_BOOTS, "&7Stone Boots", EquipmentSlot.FEET, 1.65, 0.83, 150, tier1, Color.fromRGB(95, 95, 95));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Boots_1");
				recipe = new ShapedRecipe(key, item);
				recipe.shape(" Y ", "YXY", " Y ");
				recipe.setIngredient('Y', heart);
				recipe.setIngredient('X', con.equipment(Material.LEATHER_BOOTS, "&7Stone Boots", EquipmentSlot.FEET, 1.65, 0.83, 125, null, Color.fromRGB(95, 95, 95)));
				recipe.setGroup("Stone Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				item = con.equipment(Material.LEATHER_BOOTS, "&7Stone Boots", EquipmentSlot.FEET, 1.65, 0.83, 175, tier2, Color.fromRGB(95, 95, 95));
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Boots_2");
				recipe = new ShapedRecipe(key, item);
				recipe.shape("YYY", "YXY", "YYY");
				recipe.setIngredient('Y', heart);
				recipe.setIngredient('X', con.equipment(Material.LEATHER_BOOTS, "&7Stone Boots", EquipmentSlot.FEET, 1.65, 0.83, 150, tier1, Color.fromRGB(95, 95, 95)));
				recipe.setGroup("Stone Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Stone Swords", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Sharpness", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Sharpness", 2);
				tier2.put("Unbreaking", 1);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Sword_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.STONE_SWORD, "&7Stone Sword", EquipmentSlot.HAND, 3.0, 0.83, 145, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.STONE_SWORD, "&7Stone Sword", EquipmentSlot.HAND, 3.0, 0.83, 120, null));
				recipe.setGroup("Stone Swords");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Sword_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.STONE_SWORD, "&7Stone Sword", EquipmentSlot.HAND, 3.0, 0.83, 170, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.STONE_SWORD, "&7Stone Sword", EquipmentSlot.HAND, 3.0, 0.83, 145, tier1));
				recipe.setGroup("Stone Swords");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Stone Pickaxes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Efficiency", 2);
				tier2.put("Unbreaking", 1);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Pickaxe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.STONE_PICKAXE, "&7Stone Pickaxe", EquipmentSlot.HAND, 2.0, 0.75, 145, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.STONE_PICKAXE, "&7Stone Pickaxe", EquipmentSlot.HAND, 2.0, 0.75, 120, null));
				recipe.setGroup("Stone Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Pickaxe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.STONE_PICKAXE, "&7Stone Pickaxe", EquipmentSlot.HAND, 2.0, 0.75, 170, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.STONE_PICKAXE, "&7Stone Pickaxe", EquipmentSlot.HAND, 2.0, 0.75, 145, tier1));
				recipe.setGroup("Stone Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Stone Axes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Efficiency", 2);
				tier2.put("Unbreaking", 1);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Axe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.STONE_AXE, "&7Stone Axe", EquipmentSlot.HAND, 5.25, 0.33, 145, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.STONE_AXE, "&7Stone Axe", EquipmentSlot.HAND, 5.25, 0.33, 120, null));
				recipe.setGroup("Stone Axes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Axe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.STONE_AXE, "&7Stone Axe", EquipmentSlot.HAND, 5.25, 0.33, 170, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.STONE_AXE, "&7Stone Axe", EquipmentSlot.HAND, 5.25, 0.33, 145, tier1));
				recipe.setGroup("Stone Axes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Stone Shovels", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Efficiency", 2);
				tier2.put("Unbreaking", 1);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Shovel_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.STONE_AXE, "&7Stone Shovel", EquipmentSlot.HAND, 2.45, 0.93, 145, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.STONE_SHOVEL, "&7Stone Shovel", EquipmentSlot.HAND, 2.45, 0.93, 120, null));
				recipe.setGroup("Stone Shovels");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Shovel_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.STONE_SHOVEL, "&7Stone Shovel", EquipmentSlot.HAND, 2.45, 0.93, 170, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.STONE_SHOVEL, "&7Stone Shovel", EquipmentSlot.HAND, 2.45, 0.93, 145, tier1));
				recipe.setGroup("Stone Shovels");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Stone Hoes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Unbreaking", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Unbreaking", 2);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Hoe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.STONE_HOE, "&7Stone Hoe", EquipmentSlot.HAND, 1.6, 1.24, 145, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.STONE_HOE, "&7Stone Hoe", EquipmentSlot.HAND, 1.6, 1.24, 120, null));
				recipe.setGroup("Stone Hoes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Cobblestone", Material.COBBLESTONE);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Stone_Hoe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.STONE_HOE, "&7Stone Hoe", EquipmentSlot.HAND, 1.6, 1.24, 170, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.STONE_HOE, "&7Stone Hoe", EquipmentSlot.HAND, 1.6, 1.24, 145, tier1));
				recipe.setGroup("Stone Hoes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Iron Hearts", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Iron", Material.IRON_INGOT);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Small_Iron_Heart");
				ShapedRecipe recipe = new ShapedRecipe(key, heart);
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', new ItemStack(Material.IRON_INGOT, 1));
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				recipe.setGroup("Iron Hearts");
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', con.heart("Small Iron", Material.IRON_INGOT));
				recipe.setGroup("Iron Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Big Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Iron_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', con.heart("Iron", Material.IRON_INGOT));
				recipe.setGroup("Iron Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Iron Armor", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Iron", Material.IRON_INGOT);
				ItemStack heart1 = con.heart("Iron", Material.IRON_INGOT);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Protection", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Protection", 2);
				tier2.put("Unbreaking", 1);
				HashMap<String, Integer> tier3 = new HashMap<String, Integer>();
				tier2.put("Protection", 3);
				tier2.put("Unbreaking", 1);
				
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Helmet_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.IRON_HELMET, "&7Iron Helmet", EquipmentSlot.HEAD, 1.8, 0.98, 250, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_HELMET, "&7Iron Helmet", EquipmentSlot.HEAD, 1.8, 0.98, 220, null));
				recipe.setGroup("Iron Helmet");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Helmet_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_HELMET, "&7Iron Helmet", EquipmentSlot.CHEST, 1.8, 0.98, 280, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_HELMET, "&7Iron Helmet", EquipmentSlot.CHEST, 1.8, 0.98, 250, tier1));
				recipe.setGroup("Iron Helmet");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Helmet_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_HELMET, "&7Iron Helmet", EquipmentSlot.HEAD, 1.8, 0.98, 310, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart1);
				recipe.setIngredient('Y', con.equipment(Material.IRON_HELMET, "&7Iron Helmet", EquipmentSlot.HEAD, 1.8, 0.98, 280, tier2));
				recipe.setGroup("Iron Helmet");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Chestplate_1");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_CHESTPLATE, "&7Iron Chestplate", EquipmentSlot.CHEST, 1.8, 0.98, 250, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_CHESTPLATE, "&7Iron Chestplate", EquipmentSlot.CHEST, 1.8, 0.98, 220, null));
				recipe.setGroup("Iron Chestplate");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Chestplate_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_CHESTPLATE, "&7Iron Chestplate", EquipmentSlot.CHEST, 1.8, 0.98, 280, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_CHESTPLATE, "&7Iron Chestplate", EquipmentSlot.CHEST, 1.8, 0.98, 250, tier1));
				recipe.setGroup("Iron Chestplate");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Chestplate_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_CHESTPLATE, "&7Iron Chestplate", EquipmentSlot.CHEST, 1.8, 0.98, 310, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart1);
				recipe.setIngredient('Y', con.equipment(Material.IRON_CHESTPLATE, "&7Iron Chestplate", EquipmentSlot.CHEST, 1.8, 0.98, 280, tier2));
				recipe.setGroup("Iron Chestplate");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Leggings_1");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_LEGGINGS, "&7Iron Leggings", EquipmentSlot.LEGS, 1.8, 0.98, 250, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_LEGGINGS, "&7Iron Leggings", EquipmentSlot.LEGS, 1.8, 0.98, 220, null));
				recipe.setGroup("Iron Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Leggings_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_LEGGINGS, "&7Iron Leggings", EquipmentSlot.LEGS, 1.8, 0.98, 280, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_LEGGINGS, "&7Iron Leggings", EquipmentSlot.LEGS, 1.8, 0.98, 250, tier1));
				recipe.setGroup("Iron Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Leggings_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_LEGGINGS, "&7Iron Leggings", EquipmentSlot.LEGS, 1.8, 0.98, 310, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart1);
				recipe.setIngredient('Y', con.equipment(Material.IRON_LEGGINGS, "&7Iron Leggings", EquipmentSlot.LEGS, 1.8, 0.98, 280, tier2));
				recipe.setGroup("Iron Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Boots_1");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_BOOTS, "&7Iron Boots", EquipmentSlot.FEET, 1.8, 0.98, 250, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_BOOTS, "&7Iron Boots", EquipmentSlot.FEET, 1.8, 0.98, 220, null));
				recipe.setGroup("Iron Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Boots_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_BOOTS, "&7Iron Boots", EquipmentSlot.FEET, 1.8, 0.98, 280, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_BOOTS, "&7Iron Boots", EquipmentSlot.FEET, 1.8, 0.98, 250, tier1));
				recipe.setGroup("Iron Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Boots_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_BOOTS, "&7Iron Boots", EquipmentSlot.FEET, 1.8, 0.98, 310, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart1);
				recipe.setIngredient('Y', con.equipment(Material.IRON_BOOTS, "&7Iron Boots", EquipmentSlot.FEET, 1.8, 0.73, 280, tier2));
				recipe.setGroup("Iron Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Iron Swords", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Iron", Material.IRON_INGOT);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Sharpness", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Sharpness", 2);
				tier2.put("Unbreaking", 1);
				HashMap<String, Integer> tier3 = new HashMap<String, Integer>();
				tier3.put("Sharpness", 3);
				tier3.put("Unbreaking", 1);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Sword_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.IRON_SWORD, "&7Iron Sword", EquipmentSlot.HAND, 3.3, 0.86, 250, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_SWORD, "&7Iron Sword", EquipmentSlot.HAND, 3.3, 0.86, 220, null));
				recipe.setGroup("Iron Swords");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Sword_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_SWORD, "&7Iron Sword", EquipmentSlot.HAND, 3.3, 0.86, 280, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_SWORD, "&7Iron Sword", EquipmentSlot.HAND, 3.3, 0.86, 250, tier1));
				recipe.setGroup("Iron Swords");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Sword_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_SWORD, "&7Iron Sword", EquipmentSlot.HAND, 3.3, 0.86, 310, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_SWORD, "&7Iron Sword", EquipmentSlot.HAND, 3.3, 0.86, 280, tier2));
				recipe.setGroup("Iron Swords");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Iron Pickaxes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Iron", Material.IRON_INGOT);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Efficiency", 2);
				tier2.put("Unbreaking", 1);
				HashMap<String, Integer> tier3 = new HashMap<String, Integer>();
				tier3.put("Efficiency", 3);
				tier3.put("Unbreaking", 1);
				tier3.put("Fortune", 1);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Pickaxe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.IRON_PICKAXE, "&7Iron Pickaxe", EquipmentSlot.HAND, 2.2, 0.7, 250, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_PICKAXE, "&7Iron Pickaxe", EquipmentSlot.HAND, 2.2, 0.7, 220, null));
				recipe.setGroup("Iron Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Pickaxe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_PICKAXE, "&7Iron Pickaxe", EquipmentSlot.HAND, 2.2, 0.7, 280, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_PICKAXE, "&7Iron Pickaxe", EquipmentSlot.HAND, 2.2, 0.7, 250, tier1));
				recipe.setGroup("Iron Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Pickaxe_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_PICKAXE, "&7Iron Pickaxe", EquipmentSlot.HAND, 2.2, 0.7, 310, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_PICKAXE, "&7Iron Pickaxe", EquipmentSlot.HAND, 2.2, 0.7, 280, tier2));
				recipe.setGroup("Iron Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Iron Axes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Iron", Material.IRON_INGOT);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Efficiency", 2);
				tier2.put("Unbreaking", 1);
				HashMap<String, Integer> tier3 = new HashMap<String, Integer>();
				tier3.put("Efficiency", 3);
				tier3.put("Unbreaking", 1);
				tier3.put("Fortune", 1);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Axe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.IRON_AXE, "&7Iron Axe", EquipmentSlot.HAND, 5.5, 0.35, 250, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_AXE, "&7Iron Axe", EquipmentSlot.HAND, 5.5, 0.35, 220, null));
				recipe.setGroup("Iron Axes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Axe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_AXE, "&7Iron Axe", EquipmentSlot.HAND, 5.5, 0.35, 280, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_AXE, "&7Iron Axe", EquipmentSlot.HAND, 5.5, 0.35, 250, tier1));
				recipe.setGroup("Iron Axes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Axe_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_AXE, "&7Iron Axe", EquipmentSlot.HAND, 5.5, 0.35, 310, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_AXE, "&7Iron Axe", EquipmentSlot.HAND, 5.5, 0.35, 280, tier2));
				recipe.setGroup("Iron Axes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Iron Shovels", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Iron", Material.IRON_INGOT);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Efficiency", 2);
				tier2.put("Unbreaking", 1);
				HashMap<String, Integer> tier3 = new HashMap<String, Integer>();
				tier3.put("Efficiency", 3);
				tier3.put("Unbreaking", 1);
				tier3.put("Fortune", 1);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Shovel_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.IRON_SHOVEL, "&7Iron Shovel", EquipmentSlot.HAND, 2.6, 1.02, 250, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_SHOVEL, "&7Iron Shovel", EquipmentSlot.HAND, 2.6, 1.02, 220, null));
				recipe.setGroup("Iron Shovels");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Shovel_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_SHOVEL, "&7Iron Shovel", EquipmentSlot.HAND, 2.6, 1.02, 280, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_SHOVEL, "&7Iron Shovel", EquipmentSlot.HAND, 2.6, 1.02, 250, tier1));
				recipe.setGroup("Iron Shovels");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Shovel_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_SHOVEL, "&7Iron Shovel", EquipmentSlot.HAND, 2.6, 1.02, 310, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_SHOVEL, "&7Iron Shovel", EquipmentSlot.HAND, 2.6, 1.02, 280, tier2));
				recipe.setGroup("Iron Shovels");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Iron Hoes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Iron", Material.IRON_INGOT);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Unbreaking", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Unbreaking", 2);
				HashMap<String, Integer> tier3 = new HashMap<String, Integer>();
				tier3.put("Unbreaking", 3);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Hoe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.IRON_HOE, "&7Iron Hoe", EquipmentSlot.HAND, 1.7, 1.28, 250, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_HOE, "&7Iron Hoe", EquipmentSlot.HAND, 1.7, 1.28, 220, null));
				recipe.setGroup("Iron Hoes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Hoe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_HOE, "&7Iron Hoe", EquipmentSlot.HAND, 1.7, 1.28, 280, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_HOE, "&7Iron Hoe", EquipmentSlot.HAND, 1.7, 1.28, 250, tier1));
				recipe.setGroup("Iron Hoes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Iron", Material.IRON_INGOT);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Iron_Hoe_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.IRON_HOE, "&7Iron Hoe", EquipmentSlot.HAND, 1.7, 1.28, 310, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.IRON_HOE, "&7Iron Hoe", EquipmentSlot.HAND, 1.7, 1.28, 280, tier2));
				recipe.setGroup("Iron Hoes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Diamond Hearts", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Diamond", Material.DIAMOND);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Small_Diamond_Heart");
				ShapedRecipe recipe = new ShapedRecipe(key, heart);
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', new ItemStack(Material.DIAMOND, 1));
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				recipe.setGroup("Diamond Hearts");
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', con.heart("Small Diamond", Material.DIAMOND));
				recipe.setGroup("Diamond Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Big Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Big_Diamond_Heart");
				recipe = new ShapedRecipe(key, heart);
				recipe.shape("   ", "XXX", "   ");
				recipe.setIngredient('X', con.heart("Diamond", Material.DIAMOND));
				recipe.setGroup("Diamond Hearts");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Diamond Armor", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Diamond", Material.DIAMOND);
				ItemStack heart1 = con.heart("Diamond", Material.DIAMOND);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Protection", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Protection", 2);
				tier2.put("Unbreaking", 1);
				HashMap<String, Integer> tier3 = new HashMap<String, Integer>();
				tier3.put("Protection", 3);
				tier3.put("Unbreaking", 1);
				HashMap<String, Integer> tier4 = new HashMap<String, Integer>();
				tier4.put("Protection", 4);
				tier4.put("Unbreaking", 2);
				
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Helmet_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_HELMET, "&7Diamond Helmet", EquipmentSlot.HEAD, 2.0, 1.1, 435, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_HELMET, "&7Diamond Helmet", EquipmentSlot.HEAD, 2.0, 1.1, 400, null));
				recipe.setGroup("Diamond Helmet");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Helmet_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_HELMET, "&7Diamond Helmet", EquipmentSlot.HEAD, 2.0, 1.1, 470, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_HELMET, "&7Diamond Helmet", EquipmentSlot.HEAD, 2.0, 1.1, 435, tier1));
				recipe.setGroup("Diamond Helmet");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Helmet_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_HELMET, "&7Diamond Helmet", EquipmentSlot.HEAD, 2.0, 1.1, 505, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart1);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_HELMET, "&7Diamond Helmet", EquipmentSlot.HEAD, 2.0, 1.1, 470, tier2));
				recipe.setGroup("Diamond Helmet");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Helmet_4");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_HELMET, "&7Diamond Helmet", EquipmentSlot.HEAD, 2.0, 1.1, 540, tier4));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart1);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_HELMET, "&7Diamond Helmet", EquipmentSlot.HEAD, 2.0, 1.1, 505, tier3));
				recipe.setGroup("Diamond Helmet");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Chestplate_1");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_CHESTPLATE, "&7Diamond Chestplate", EquipmentSlot.CHEST, 2.0, 1.1, 435, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_CHESTPLATE, "&7Diamond Chestplate", EquipmentSlot.CHEST, 2.0, 1.1, 400, null));
				recipe.setGroup("Diamond Chestplate");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Chestplate_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_CHESTPLATE, "&7Diamond Chestplate", EquipmentSlot.CHEST, 2.0, 1.1, 470, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_CHESTPLATE, "&7Diamond Chestplate", EquipmentSlot.CHEST, 2.0, 1.1, 435, tier1));
				recipe.setGroup("Diamond Chestplate");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Chestplate_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_CHESTPLATE, "&7Diamond Chestplate", EquipmentSlot.CHEST, 2.0, 1.1, 505, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart1);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_CHESTPLATE, "&7Diamond Chestplate", EquipmentSlot.CHEST, 2.0, 1.1, 470, tier2));
				recipe.setGroup("Diamond Chestplate");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Chestplate_4");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_CHESTPLATE, "&7Diamond Chestplate", EquipmentSlot.CHEST, 2.0, 1.1, 540, tier4));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart1);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_CHESTPLATE, "&7Diamond Chestplate", EquipmentSlot.CHEST, 2.0, 1.1, 505, tier3));
				recipe.setGroup("Diamond Chestplate");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Leggings_1");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_LEGGINGS, "&7Diamond Leggings", EquipmentSlot.LEGS, 2.0, 1.1, 435, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_LEGGINGS, "&7Diamond Leggings", EquipmentSlot.LEGS, 2.0, 1.1, 400, null));
				recipe.setGroup("Diamond Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Leggings_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_LEGGINGS, "&7Diamond Leggings", EquipmentSlot.LEGS, 2.0, 1.1, 470, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_LEGGINGS, "&7Diamond Leggings", EquipmentSlot.LEGS, 2.0, 1.1, 435, tier1));
				recipe.setGroup("Diamond Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Leggings_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_LEGGINGS, "&7Diamond Leggings", EquipmentSlot.LEGS, 2.0, 1.1, 505, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart1);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_LEGGINGS, "&7Diamond Leggings", EquipmentSlot.LEGS, 2.0, 1.1, 470, tier2));
				recipe.setGroup("Diamond Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Leggings_4");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_LEGGINGS, "&7Diamond Leggings", EquipmentSlot.LEGS, 2.0, 1.1, 540, tier4));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart1);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_LEGGINGS, "&7Diamond Leggings", EquipmentSlot.LEGS, 2.0, 1.1, 505, tier3));
				recipe.setGroup("Diamond Leggings");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Boots_1");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_BOOTS, "&7Diamond Boots", EquipmentSlot.FEET, 2.0, 1.1, 435, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_BOOTS, "&7Diamond Boots", EquipmentSlot.FEET, 2.0, 1.1, 400, null));
				recipe.setGroup("Diamond Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Boots_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_BOOTS, "&7Diamond Boots", EquipmentSlot.FEET, 2.0, 1.1, 470, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_BOOTS, "&7Diamond Boots", EquipmentSlot.FEET, 2.0, 1.1, 435, tier1));
				recipe.setGroup("Diamond Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Boots_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_BOOTS, "&7Diamond Boots", EquipmentSlot.FEET, 2.0, 1.1, 505, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart1);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_BOOTS, "&7Diamond Boots", EquipmentSlot.FEET, 2.0, 1.1, 470, tier2));
				recipe.setGroup("Diamond Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Boots_4");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_BOOTS, "&7Diamond Boots", EquipmentSlot.FEET, 2.0, 1.1, 540, tier4));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart1);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_BOOTS, "&7Diamond Boots", EquipmentSlot.FEET, 2.0, 1.1, 505, tier3));
				recipe.setGroup("Diamond Boots");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				
			}
		}));
		this.listConsumables.put("Diamond Swords", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Diamond", Material.DIAMOND);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Sharpness", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Sharpness", 2);
				tier2.put("Unbreaking", 1);
				HashMap<String, Integer> tier3 = new HashMap<String, Integer>();
				tier3.put("Sharpness", 3);
				tier3.put("Unbreaking", 1);
				HashMap<String, Integer> tier4 = new HashMap<String, Integer>();
				tier4.put("Sharpness", 4);
				tier4.put("Unbreaking", 2);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Sword_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_SWORD, "&7Diamond Sword", EquipmentSlot.HAND, 3.9, 0.92, 435, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_SWORD, "&7Diamond Sword", EquipmentSlot.HAND, 3.9, 0.92, 400, null));
				recipe.setGroup("Diamond Swords");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Sword_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_SWORD, "&7Diamond Sword", EquipmentSlot.HAND, 3.9, 0.92, 470, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_SWORD, "&7Diamond Sword", EquipmentSlot.HAND, 3.9, 0.92, 435, tier1));
				recipe.setGroup("Diamond Swords");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Sword_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_SWORD, "&7Diamond Sword", EquipmentSlot.HAND, 3.9, 0.92, 505, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_SWORD, "&7Diamond Sword", EquipmentSlot.HAND, 3.9, 0.92, 470, tier2));
				recipe.setGroup("Diamond Swords");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Sword_4");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_SWORD, "&7Diamond Sword", EquipmentSlot.HAND, 3.9, 0.92, 540, tier4));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_SWORD, "&7Diamond Sword", EquipmentSlot.HAND, 3.9, 0.92, 505, tier3));
				recipe.setGroup("Diamond Swords");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Diamond Pickaxes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Diamond", Material.DIAMOND);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Efficiency", 2);
				tier2.put("Unbreaking", 1);
				HashMap<String, Integer> tier3 = new HashMap<String, Integer>();
				tier3.put("Efficiency", 3);
				tier3.put("Unbreaking", 1);
				tier3.put("Fortune", 1);
				HashMap<String, Integer> tier4 = new HashMap<String, Integer>();
				tier4.put("Efficiency", 4);
				tier4.put("Unbreaking", 2);
				tier4.put("Fortune", 2);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Pickaxe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 435, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 400, null));
				recipe.setGroup("Diamond Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Pickaxe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 470, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 435, tier1));
				recipe.setGroup("Diamond Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Pickaxe_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 505, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 470, tier2));
				recipe.setGroup("Diamond Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Pickaxe_4");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 540, tier4));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 505, tier3));
				recipe.setGroup("Diamond Pickaxes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Diamond Axes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Diamond", Material.DIAMOND);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Efficiency", 2);
				tier2.put("Unbreaking", 1);
				HashMap<String, Integer> tier3 = new HashMap<String, Integer>();
				tier3.put("Efficiency", 3);
				tier3.put("Unbreaking", 1);
				tier3.put("Fortune", 1);
				HashMap<String, Integer> tier4 = new HashMap<String, Integer>();
				tier4.put("Efficiency", 4);
				tier4.put("Unbreaking", 2);
				tier4.put("Fortune", 2);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Axe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_AXE, "&7Diamond Axe", EquipmentSlot.HAND, 6.0, 0.38, 435, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_AXE, "&7Diamond Axe", EquipmentSlot.HAND, 6.0, 0.38, 400, null));
				recipe.setGroup("Diamond Axes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Axe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_AXE, "&7Diamond Axe", EquipmentSlot.HAND, 6.0, 0.38, 470, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_AXE, "&7Diamond Axe", EquipmentSlot.HAND, 6.0, 0.38, 435, tier1));
				recipe.setGroup("Diamond Axes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Axe_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_AXE, "&7Diamond Axe", EquipmentSlot.HAND, 6.0, 0.38, 505, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_AXE, "&7Diamond Axe", EquipmentSlot.HAND, 6.0, 0.38, 470, tier2));
				recipe.setGroup("Diamond Axes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Axe_4");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_AXE, "&7Diamond Axe", EquipmentSlot.HAND, 6.0, 0.38, 540, tier4));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_AXE, "&7Diamond Axe", EquipmentSlot.HAND, 6.0, 0.38, 505, tier3));
				recipe.setGroup("Diamond Axes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Diamond Shovels", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Diamond", Material.DIAMOND);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Efficiency", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Efficiency", 2);
				tier2.put("Unbreaking", 1);
				HashMap<String, Integer> tier3 = new HashMap<String, Integer>();
				tier3.put("Efficiency", 3);
				tier3.put("Unbreaking", 1);
				tier3.put("Fortune", 1);
				HashMap<String, Integer> tier4 = new HashMap<String, Integer>();
				tier4.put("Efficiency", 4);
				tier4.put("Unbreaking", 2);
				tier4.put("Fortune", 2);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Shovel_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 435, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 400, null));
				recipe.setGroup("Diamond Shovels");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Shovel_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 470, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 435, tier1));
				recipe.setGroup("Diamond Shovels");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Shovel_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 505, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 470, tier2));
				recipe.setGroup("Diamond Shovels");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Shovel_4");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 540, tier4));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 505, tier3));
				recipe.setGroup("Diamond Shovels");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
		this.listConsumables.put("Diamond Hoes", new Pair<>(Condition.NONE, new CommandFile() {
			@Override
			public void registerRecipe() {
				ItemStack heart = con.heart("Small Diamond", Material.DIAMOND);
				HashMap<String, Integer> tier1 = new HashMap<String, Integer>();
				tier1.put("Unbreaking", 1);
				HashMap<String, Integer> tier2 = new HashMap<String, Integer>();
				tier2.put("Unbreaking", 2);
				HashMap<String, Integer> tier3 = new HashMap<String, Integer>();
				tier3.put("Unbreaking", 3);
				HashMap<String, Integer> tier4 = new HashMap<String, Integer>();
				tier4.put("Unbreaking", 4);
				NamespacedKey key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Hoe_1");
				ShapedRecipe recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_HOE, "&7Diamond Hoe", EquipmentSlot.HAND, 1.9, 1.36, 435, tier1));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_HOE, "&7Diamond Hoe", EquipmentSlot.HAND, 1.9, 1.36, 400, null));
				recipe.setGroup("Diamond Hoes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Small Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Hoe_2");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_HOE, "&7Diamond Hoe", EquipmentSlot.HAND, 1.9, 1.36, 470, tier2));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_HOE, "&7Diamond Hoe", EquipmentSlot.HAND, 1.9, 1.36, 435, tier1));
				recipe.setGroup("Diamond Hoes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Hoe_3");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_HOE, "&7Diamond Hoe", EquipmentSlot.HAND, 1.9, 1.36, 505, tier3));
				recipe.shape(" X ", "XYX", " X ");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_HOE, "&7Diamond Hoe", EquipmentSlot.HAND, 1.9, 1.36, 470, tier2));
				recipe.setGroup("Diamond Hoes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				heart = con.heart("Diamond", Material.DIAMOND);
				key = new NamespacedKey(CustomEnchantments.getInstance(), "Diamond_Hoe_4");
				recipe = new ShapedRecipe(key, con.equipment(Material.DIAMOND_HOE, "&7Diamond Hoe", EquipmentSlot.HAND, 1.9, 1.36, 540, tier4));
				recipe.shape("XXX", "XYX", "XXX");
				recipe.setIngredient('X', heart);
				recipe.setIngredient('Y', con.equipment(Material.DIAMOND_HOE, "&7Diamond Hoe", EquipmentSlot.HAND, 1.9, 1.36, 505, tier3));
				recipe.setGroup("Diamond Hoes");
				CustomEnchantments.getInstance().getServer().addRecipe(recipe);
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
			}
		}));
	}
	public void registerRecipes() {
		Consumable con = this;
		Iterator<Recipe> iter = Bukkit.recipeIterator();
		ArrayList<Recipe> recipesReplace = new ArrayList<Recipe>();
		ArrayList<Recipe> recipesNew = new ArrayList<Recipe>();
		while(iter.hasNext()) {
			Recipe r = iter.next();
			if(r instanceof ShapedRecipe) {
				ShapedRecipe rec = (ShapedRecipe) r;
				if(rec.getResult().getType() == Material.WOODEN_SWORD) {
					recipesReplace.add(rec);
					ItemStack item = con.equipment(Material.WOODEN_SWORD, "&7Wooden Sword", EquipmentSlot.HAND, 2.7, 0.8, 80, null);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), item);
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.STONE_SWORD) {
					recipesReplace.add(rec);
					ItemStack item = con.equipment(Material.STONE_SWORD, "&7Stone Sword", EquipmentSlot.HAND, 3.0, 0.83, 120, null);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), item);
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.IRON_SWORD) {
					recipesReplace.add(rec);
					ItemStack item = con.equipment(Material.IRON_SWORD, "&7Iron Sword", EquipmentSlot.HAND, 3.3, 0.86, 220, null);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), item);
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.GOLDEN_SWORD) {
					recipesReplace.add(rec);
					ItemStack item = con.equipment(Material.GOLDEN_SWORD, "&7Golden Sword", EquipmentSlot.HAND, 2.4, 1.1, 40, null);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), item);
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.DIAMOND_SWORD) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_SWORD, "&7Diamond Sword", EquipmentSlot.HAND, 3.9, 0.92, 400, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Pickaxes
				//-----------------------------------------------------------------------------------
				else if(rec.getResult().getType() == Material.WOODEN_PICKAXE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.WOODEN_PICKAXE, "&7Wooden Pickaxe", EquipmentSlot.HAND, 1.8, 0.8, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.STONE_PICKAXE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.STONE_PICKAXE, "&7Stone Pickaxe", EquipmentSlot.HAND, 2.0, 0.75, 120, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.IRON_PICKAXE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_PICKAXE, "&7Iron Pickaxe", EquipmentSlot.HAND, 2.2, 0.7, 220, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.GOLDEN_PICKAXE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_PICKAXE, "&7Golden Pickaxe", EquipmentSlot.HAND, 1.6, 0.9, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.DIAMOND_PICKAXE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 400, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Axes
				//-----------------------------------------------------------------------------------
				else if(rec.getResult().getType() == Material.WOODEN_AXE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.WOODEN_AXE, "&7Wooden Axe", EquipmentSlot.HAND, 5.0, 0.32, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.STONE_AXE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.STONE_AXE, "&7Stone Axe", EquipmentSlot.HAND, 5.25, 0.33, 120, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.IRON_AXE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_AXE, "&7Iron Axe", EquipmentSlot.HAND, 5.5, 0.35, 220, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.GOLDEN_AXE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_AXE, "&7Golden Axe", EquipmentSlot.HAND, 4.25, 0.42, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.DIAMOND_AXE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_AXE, "&7Diamond Axe", EquipmentSlot.HAND, 6.0, 0.38, 400, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Shovel
				//-----------------------------------------------------------------------------------
				else if(rec.getResult().getType() == Material.WOODEN_SHOVEL) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.WOODEN_SHOVEL, "&7Wooden Shovel", EquipmentSlot.HAND, 2.3, 0.94, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.STONE_SHOVEL) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.STONE_SHOVEL, "&7Stone Shovel", EquipmentSlot.HAND, 2.45, 0.93, 120, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.IRON_SHOVEL) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_SHOVEL, "&7Iron Shovel", EquipmentSlot.HAND, 2.6, 0.96, 220, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.GOLDEN_SHOVEL) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_SHOVEL, "&7Golden Shovel", EquipmentSlot.HAND, 1.8, 1.1, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.DIAMOND_SHOVEL) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.02, 400, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Hoes
				//-----------------------------------------------------------------------------------
				else if(rec.getResult().getType() == Material.WOODEN_HOE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.WOODEN_HOE, "&7Wooden Hoe", EquipmentSlot.HAND, 1.5, 1.2, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.STONE_HOE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.STONE_HOE, "&7Stone Hoe", EquipmentSlot.HAND, 1.6, 1.24, 120, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.IRON_HOE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_HOE, "&7Iron Hoe", EquipmentSlot.HAND, 1.7, 1.28, 220, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.GOLDEN_HOE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_HOE, "&7Golden Hoe", EquipmentSlot.HAND, 1.3, 1.44, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.DIAMOND_HOE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_HOE, "&7Diamond Hoe", EquipmentSlot.HAND, 1.9, 1.36, 400, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Leather Armor
				//-----------------------------------------------------------------------------------
				else if(rec.getResult().getType() == Material.LEATHER_HELMET) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.LEATHER_HELMET, "&7Leather Helmet", EquipmentSlot.HEAD, 1.5, 0.75, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.LEATHER_CHESTPLATE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.LEATHER_CHESTPLATE, "&7Leather Chestplate", EquipmentSlot.CHEST, 1.5, 0.75, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.LEATHER_LEGGINGS) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.LEATHER_LEGGINGS, "&7Leather Leggings", EquipmentSlot.LEGS, 1.5, 0.75, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.LEATHER_BOOTS) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.LEATHER_BOOTS, "&7Leather Boots", EquipmentSlot.FEET, 1.5, 0.75, 80, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Golden Armor
				//-----------------------------------------------------------------------------------
				else if(rec.getResult().getType() == Material.GOLDEN_HELMET) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_HELMET, "&7Golden Helmet", EquipmentSlot.HEAD, 1.2, 1.25, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.GOLDEN_CHESTPLATE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_CHESTPLATE, "&7Golden Chestplate", EquipmentSlot.CHEST, 1.2, 1.25, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.GOLDEN_LEGGINGS) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_LEGGINGS, "&7Golden Leggings", EquipmentSlot.LEGS, 1.2, 1.25, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.GOLDEN_BOOTS) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.GOLDEN_BOOTS, "&7Golden Boots", EquipmentSlot.FEET, 1.2, 1.25, 40, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Iron Armor
				//-----------------------------------------------------------------------------------
				else if(rec.getResult().getType() == Material.IRON_HELMET) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_HELMET, "&7Iron Helmet", EquipmentSlot.HEAD, 1.8, 0.98, 220, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.IRON_CHESTPLATE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_CHESTPLATE, "&7Iron Chestplate", EquipmentSlot.CHEST, 1.8, 0.98, 220, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.IRON_LEGGINGS) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_LEGGINGS, "&7Iron Leggings", EquipmentSlot.LEGS, 1.8, 0.98, 220, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.IRON_BOOTS) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.IRON_BOOTS, "&7Iron Boots", EquipmentSlot.FEET, 1.8, 0.98, 220, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				//-----------------------------------------------------------------------------------
				//Iron Armor
				//-----------------------------------------------------------------------------------
				else if(rec.getResult().getType() == Material.DIAMOND_HELMET) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_HELMET, "&7Diamond Helmet", EquipmentSlot.HEAD, 2.0, 1.1, 400, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.DIAMOND_CHESTPLATE) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_CHESTPLATE, "&7Diamond Chestplate", EquipmentSlot.CHEST, 2.0, 1.1, 400, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.DIAMOND_LEGGINGS) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_LEGGINGS, "&7Diamond Leggings", EquipmentSlot.LEGS, 2.0, 1.1, 400, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else if(rec.getResult().getType() == Material.DIAMOND_BOOTS) {
					recipesReplace.add(rec);
					ShapedRecipe recipe = new ShapedRecipe(rec.getKey(), con.equipment(Material.DIAMOND_BOOTS, "&7Diamond Boots", EquipmentSlot.FEET, 2.0, 1.1, 400, null));
					recipe.shape(rec.getShape());
					for(Entry<Character, RecipeChoice> entry : rec.getChoiceMap().entrySet()) {
						recipe.setIngredient(entry.getKey(), entry.getValue());
					}
					recipe.setGroup(rec.getGroup());
					recipesNew.add(recipe);
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), recipe));
				}
				else {
					listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), r));
				}
			}
			else {
				listRecipes.add(new Pair<>(new ArrayList<UnlockCraftCondition>(Arrays.asList(UnlockCraftCondition.PLAYER_PICKUP_ITEM, UnlockCraftCondition.PLAYER_CLICK_INVENTORY)), r));
			}
		}
		for(Recipe r : recipesReplace) {
			if(r instanceof ShapelessRecipe) {
				ShapelessRecipe rec = (ShapelessRecipe) r;
				this.removeRecipeByKey(rec.getResult().getType().toString().toLowerCase());
			}
			else if(r instanceof ShapedRecipe) {
				ShapedRecipe rec = (ShapedRecipe) r;
				this.removeRecipeByKey(rec.getResult().getType().toString().toLowerCase());
			}
		}
		for(Recipe r : recipesNew) {
			Bukkit.addRecipe(r);
		}
		
		for(Entry<String, Pair<Condition, CommandFile>> entry : this.listConsumables.entrySet()) {
			entry.getValue().getValue().registerRecipe();
		}
		
	}
	
	private void removeRecipeByKey(String recipeKey) {
	    MinecraftKey key = new MinecraftKey(recipeKey);
	    if(((CraftServer) Bukkit.getServer()).getServer().getCraftingManager().recipes.containsKey(key)) {
	    	((CraftServer) Bukkit.getServer()).getServer().getCraftingManager().recipes.remove(key);
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
	public ItemStack equipment(Material mat, String name, EquipmentSlot equip, double value1, double value2, int durability, HashMap<String, Integer> enchants) {
		ItemStack item = new ItemStack(mat, 1);
		NBTItem i = new NBTItem(item);
		i.setString("ItemKey", ChatColor.stripColor(new CCT().colorize(name)));
		if(durability > 0) {
			i.setInteger("Durability", durability);
			i.setInteger("MaxDurability", durability);
		}
		ArrayList<String> lore = new ArrayList<String>();
		if(enchants != null) {
			for(Entry<String, Integer> map : enchants.entrySet()) {
				lore.add(new CCT().colorize("&9" + map.getKey() + " " + map.getValue()));
				i.setInteger(map.getKey(), map.getValue());
			}
		}
		item = i.getItem();
		ItemMeta meta = item.getItemMeta();
		if(enchants != null) {
			meta.addEnchant(Enchantment.LURE, 0, true);
			if(enchants.containsKey("Efficiency")) {
				meta.addEnchant(Enchantment.DIG_SPEED, enchants.get("Efficiency"), true);
			}
			if(enchants.containsKey("Fortune")) {
				meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, enchants.get("Fortune"), true);
			}
		}
		meta.setDisplayName(new CCT().colorize(name));
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
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
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
	public ItemStack equipment(Material mat, String name, EquipmentSlot equip, double value1, double value2, int durability, HashMap<String, Integer> enchants, Color color) {
		ItemStack item = new ItemStack(mat, 1);
		NBTItem i = new NBTItem(item);
		i.setString("ItemKey", ChatColor.stripColor(new CCT().colorize(name)));
		if(durability > 0) {
			i.setInteger("Durability", durability);
			i.setInteger("MaxDurability", durability);
		}
		ArrayList<String> lore = new ArrayList<String>();
		if(enchants != null) {
			for(Entry<String, Integer> map : enchants.entrySet()) {
				lore.add(new CCT().colorize("&9" + map.getKey() + " " + map.getValue()));
				i.setInteger(map.getKey(), map.getValue());
			}
		}
		item = i.getItem();
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		if(enchants != null) {
			meta.addEnchant(Enchantment.LURE, 0, true);
			if(enchants.containsKey("Efficiency")) {
				meta.addEnchant(Enchantment.DIG_SPEED, enchants.get("Efficiency"), true);
			}
			if(enchants.containsKey("Fortune")) {
				meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, enchants.get("Fortune"), true);
			}
		}
		meta.setDisplayName(new CCT().colorize(name));
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
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
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
