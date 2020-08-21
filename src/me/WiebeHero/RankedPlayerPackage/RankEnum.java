package me.WiebeHero.RankedPlayerPackage;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.base.Enums;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.Consumables.Consumable;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomMethods.ItemStackBuilder;

public class RankEnum {
	private static ItemStackBuilder builder;
	private static Consumable con;
	public RankEnum(ItemStackBuilder build, Consumable consumable) {
		builder = build;
		con = consumable;
	}
	public Rank getIfPresent(String name) {
		return Enums.getIfPresent(Rank.class, name).orNull();
	}
	public Rank getIfPresent(Kit kit) {
		return Enums.getIfPresent(Rank.class, kit.toString()).orNull();
	}
	public enum Rank{
		USER(0, "&7User"),
		BRONZE(1, "&6Bronze"),
		SILVER(2, "&7Silver"),
		GOLD(3, "&eGold"),
		PLATINUM(4, "&3Platinum"),
		DIAMOND(5, "&bDiamond"),
		EMERALD(6, "&aEmerald"),
		YOUTUBER(7, "&cY&fo&cu&ft&cu&fb&ce&fr"),
		QA(8, "&bQA"),
		QA_ADMIN(9, "&cQA Admin"),
		HELPER(10, "&aHelper"),
		HELPER_PLUS(11, "&2Helper+"),
		MOD(12, "&9Mod"),
		HEAD_MOD(13, "&1Head Mod"),
		ADMIN(14, "&cAdmin"),
		HEAD_ADMIN(15, "&4Head Admin"),
		TEAM_ADMIN(16, "&4Team Admin"),
		MANAGER(17, "&5Manager"),
		OWNER(18, "&2Owner");
		private final int rank;
		private final String display;
		
		public int getRank() {
			return this.rank;
		}
		
		public String getDisplay() {
			return this.display;
		}
		
		private Rank(int rank, String display) {
			this.rank = rank;
			this.display = new CCT().colorize(display);
		}
	}
	public Kit getIfPresentKit(String name) {
		return Enums.getIfPresent(Kit.class, name).orNull();
	}
	public Kit getIfPresentKit(Rank rank) {
		return Enums.getIfPresent(Kit.class, rank.toString()).orNull();
	}
	public enum Kit{
		USER("&7User", 0, 10800000L, Material.OAK_LOG){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tierS = new HashMap<String, Integer>();
				tierS.put("Sharpness", 1);
				HashMap<String, Integer> tierT = new HashMap<String, Integer>();
				tierT.put("Efficiency", 1);
				HashMap<String, Integer> tierH = new HashMap<String, Integer>();
				tierH.put("Unbreaking", 1);
				HashMap<String, Integer> tierA = new HashMap<String, Integer>();
				tierA.put("Protection", 1);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.WOODEN_SWORD, "&7Wooden Sword", EquipmentSlot.HAND, 2.7, 0.8, 100, tierS));
				items.add(con.equipment(Material.WOODEN_AXE, "&7Wooden Axe", EquipmentSlot.HAND, 5.0, 0.32, 100, tierT));
				items.add(con.equipment(Material.WOODEN_PICKAXE, "&7Wooden Pickaxe", EquipmentSlot.HAND, 1.8, 0.8, 100, tierT));
				items.add(con.equipment(Material.WOODEN_SHOVEL, "&7Wooden Shovel", EquipmentSlot.HAND, 2.3, 0.94, 100, tierT));
				items.add(con.equipment(Material.WOODEN_HOE, "&7Wooden Hoe", EquipmentSlot.HAND, 1.5, 1.2, 100, tierH));
				items.add(con.equipment(Material.LEATHER_HELMET, "&7Wooden Helmet", EquipmentSlot.HEAD, 1.5, 0.75, 100, tierA, Color.fromRGB(145, 95, 33)));
				items.add(con.equipment(Material.LEATHER_CHESTPLATE, "&7Wooden Chestplate", EquipmentSlot.CHEST, 1.5, 0.75, 100, tierA, Color.fromRGB(145, 95, 33)));
				items.add(con.equipment(Material.LEATHER_LEGGINGS, "&7Wooden Leggings", EquipmentSlot.LEGS, 1.5, 0.75, 100, tierA, Color.fromRGB(145, 95, 33)));
				items.add(con.equipment(Material.LEATHER_BOOTS, "&7Wooden Boots", EquipmentSlot.FEET, 1.5, 0.75, 100, tierA, Color.fromRGB(145, 95, 33)));
				items.add(builder.constructItem(Material.OAK_LOG, 32));
				items.add(builder.constructItem(Material.STONE, 32));
				items.add(builder.constructItem(Material.ENDER_PEARL, 8));
				items.add(builder.constructItem(Material.TORCH, 16));
				items.add(builder.constructItem(Material.WATER_BUCKET));
				items.add(builder.constructItem(Material.LAVA_BUCKET));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		BRONZE("&6Bronze", 1, 345600000L, Material.BRICK){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 2);
				tier.put("Unbreaking", 2);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&6Bronze Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 500, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&6Bronze Shovel", EquipmentSlot.HAND, 3.2, 1.06, 500, tier));
				items.add(common(2));
				items.add(rare(1));
				items.add(builder.constructItem(Material.TNT, 16));
				items.add(builder.constructItem(Material.OBSIDIAN, 4));
				items.add(playerXPBottle(50));
				items.add(moneyNote(1500));
				items.add(builder.constructItem(Material.GOLDEN_APPLE, 1));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		SILVER("&7Silver", 2, 345600000L, Material.IRON_NUGGET){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 3);
				tier.put("Unbreaking", 2);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&7Silver Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 570, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&7Silver Shovel", EquipmentSlot.HAND, 3.2, 1.06, 570, tier));
				items.add(rare(2));
				items.add(epic(1));
				items.add(builder.constructItem(Material.TNT, 32));
				items.add(builder.constructItem(Material.OBSIDIAN, 6));
				items.add(playerXPBottle(100));
				items.add(moneyNote(2500));
				items.add(builder.constructItem(Material.GOLDEN_APPLE, 3));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		GOLD("&eGold", 3, 345600000L, Material.GOLD_INGOT){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 3);
				tier.put("Unbreaking", 3);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&eGold Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 640, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&eGold Shovel", EquipmentSlot.HAND, 3.2, 1.06, 640, tier));
				items.add(rare(3));
				items.add(epic(2));
				items.add(builder.constructItem(Material.TNT, 64));
				items.add(builder.constructItem(Material.OBSIDIAN, 10));
				items.add(playerXPBottle(200));
				items.add(moneyNote(5500));
				items.add(builder.constructItem(Material.GOLDEN_APPLE, 6));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		PLATINUM("&3Platinum", 4, 345600000L, Material.IRON_INGOT){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 4);
				tier.put("Unbreaking", 3);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&3Platinum Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 710, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&3Platinum Shovel", EquipmentSlot.HAND, 3.2, 1.06, 710, tier));
				items.add(rare(3));
				items.add(epic(3));
				items.add(legendary(1));
				items.add(builder.constructItem(Material.TNT, 64));
				items.add(builder.constructItem(Material.TNT, 16));
				items.add(builder.constructItem(Material.OBSIDIAN, 16));
				items.add(playerXPBottle(400));
				items.add(moneyNote(8500));
				items.add(builder.constructItem(Material.GOLDEN_APPLE, 15));
				items.add(builder.constructItem(Material.CREEPER_SPAWN_EGG, 1));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		DIAMOND("&bDiamond", 5, 345600000L, Material.DIAMOND){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 4);
				tier.put("Unbreaking", 4);
				tier.put("Fortune", 1);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&bDiamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 780, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&bDiamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 780, tier));
				items.add(epic(3));
				items.add(legendary(2));
				items.add(mythic(1));
				items.add(builder.constructItem(Material.TNT, 64));
				items.add(builder.constructItem(Material.TNT, 48));
				items.add(builder.constructItem(Material.OBSIDIAN, 24));
				items.add(playerXPBottle(600));
				items.add(moneyNote(10000));
				items.add(builder.constructItem(Material.GOLDEN_APPLE, 24));
				items.add(builder.constructItem(Material.CREEPER_SPAWN_EGG, 2));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		EMERALD("&aEmerald", 6, 345600000L, Material.EMERALD){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 5);
				tier.put("Unbreaking", 5);
				tier.put("Fortune", 2);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&aEmerald Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 850, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&aEmerald Shovel", EquipmentSlot.HAND, 3.2, 1.06, 850, tier));
				items.add(epic(3));
				items.add(legendary(3));
				items.add(mythic(2));
				items.add(builder.constructItem(Material.TNT, 64));
				items.add(builder.constructItem(Material.TNT, 64));
				items.add(builder.constructItem(Material.TNT, 48));
				items.add(builder.constructItem(Material.OBSIDIAN, 32));
				items.add(playerXPBottle(800));
				items.add(moneyNote(12500));
				items.add(builder.constructItem(Material.GOLDEN_APPLE, 32));
				items.add(builder.constructItem(Material.CREEPER_SPAWN_EGG, 4));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		YOUTUBER("&cY&fo&cu&ft&cu&fb&ce&fr", 7, 345600000L, Material.PINK_DYE){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 5);
				tier.put("Unbreaking", 5);
				tier.put("Fortune", 2);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&cY&fo&cu&ft&cu&fb&ce&fr &cP&fi&cc&fk&ca&fx&ce", EquipmentSlot.HAND, 2.6, 0.6, 405, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&cY&fo&cu&ft&cu&fb&ce&fr &cS&fh&co&fv&ce&fl&c", EquipmentSlot.HAND, 3.2, 1.06, 405, tier));
				items.add(epic(3));
				items.add(legendary(3));
				items.add(mythic(2));
				items.add(builder.constructItem(Material.TNT, 64));
				items.add(builder.constructItem(Material.TNT, 64));
				items.add(builder.constructItem(Material.TNT, 48));
				items.add(builder.constructItem(Material.OBSIDIAN, 32));
				items.add(playerXPBottle(800));
				items.add(moneyNote(12500));
				items.add(builder.constructItem(Material.GOLDEN_APPLE, 32));
				items.add(builder.constructItem(Material.CREEPER_SPAWN_EGG, 4));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		QA("&bQA", 8, 345600000L, Material.LIGHT_BLUE_DYE){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(common(2));
				items.add(playerXPBottle(75));
				items.add(builder.constructItem(Material.LIGHT_BLUE_DYE));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		QA_ADMIN("&cQA Admin", 9, 345600000L, Material.LIGHT_BLUE_DYE){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(common(2));
				items.add(playerXPBottle(75));
				items.add(builder.constructItem(Material.LIGHT_BLUE_DYE));
				items.add(builder.constructItem(Material.ROSE_RED));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		HELPER("&aHelper", 10, 345600000L, Material.LIME_DYE){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(common(2));
				items.add(playerXPBottle(75));
				items.add(builder.constructItem(Material.LIME_DYE));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		HELPER_PLUS("&aHelper+", 11, 345600000L, Material.CACTUS_GREEN){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(common(2));
				items.add(rare(1));
				items.add(playerXPBottle(150));
				items.add(moneyNote(1000));
				items.add(builder.constructItem(Material.CACTUS_GREEN));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		MOD("&9Mod", 12, 345600000L, Material.LIGHT_BLUE_DYE){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(rare(2));
				items.add(epic(1));
				items.add(playerXPBottle(300));
				items.add(moneyNote(2500));
				items.add(builder.constructItem(Material.LIGHT_BLUE_DYE));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		HEAD_MOD("&1Head Mod", 13, 345600000L, Material.CYAN_DYE){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(rare(1));
				items.add(epic(2));
				items.add(legendary(1));
				items.add(playerXPBottle(600));
				items.add(moneyNote(4000));
				items.add(builder.constructItem(Material.CYAN_DYE));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		ADMIN("&4Admin", 14, 345600000L, Material.ROSE_RED){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(common(64));
				items.add(rare(64));
				items.add(epic(64));
				items.add(legendary(64));
				items.add(mythic(64));
				items.add(heroic(64));
				items.add(playerXPBottle(1000));
				items.add(moneyNote(5000));
				items.add(builder.constructItem(Material.ROSE_RED));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		HEAD_ADMIN("&4Head Admin", 15, 345600000L, Material.REDSTONE){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(common(64));
				items.add(rare(64));
				items.add(epic(64));
				items.add(legendary(64));
				items.add(mythic(64));
				items.add(heroic(64));
				items.add(playerXPBottle(2000));
				items.add(moneyNote(6000));
				items.add(builder.constructItem(Material.REDSTONE));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		TEAM_ADMIN("&4Team Admin", 15, 345600000L, Material.REDSTONE){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(common(64));
				items.add(rare(64));
				items.add(epic(64));
				items.add(legendary(64));
				items.add(mythic(64));
				items.add(heroic(64));
				items.add(playerXPBottle(2000));
				items.add(moneyNote(6000));
				items.add(builder.constructItem(Material.REDSTONE));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		MANAGER("&5Manager", 16, 345600000L, Material.MAGENTA_DYE){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(common(64));
				items.add(rare(64));
				items.add(epic(64));
				items.add(legendary(64));
				items.add(mythic(64));
				items.add(heroic(64));
				items.add(playerXPBottle(3000));
				items.add(moneyNote(7000));
				items.add(builder.constructItem(Material.MAGENTA_DYE));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		OWNER("&2Owner", 17, 345600000L, Material.CACTUS_GREEN){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(common(64));
				items.add(rare(64));
				items.add(epic(64));
				items.add(legendary(64));
				items.add(mythic(64));
				items.add(heroic(64));
				items.add(playerXPBottle(4000));
				items.add(moneyNote(8000));
				items.add(builder.constructItem(Material.CACTUS_GREEN));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		RAID("&6Raid", 100, 172800000L, Material.TNT){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(builder.constructItem(Material.TNT, 32));
				items.add(builder.constructItem(Material.COBWEB, 8));
				items.add(builder.constructItem(Material.PISTON, 16));
				items.add(builder.constructItem(Material.STICKY_PISTON, 8));
				items.add(builder.constructItem(Material.REDSTONE, 64));
				items.add(builder.constructItem(Material.REPEATER, 16));
				items.add(builder.constructItem(Material.DISPENSER, 8));
				items.add(builder.constructItem(Material.WATER_BUCKET, 1));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		POTION("&6Potion", 101, 345600000L, Material.POTION){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				ItemStack i = builder.constructItem(Material.POTION, 1);
	            PotionMeta meta = (PotionMeta) i.getItemMeta();
	            meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 1800, 0), true);
	            meta.setColor(PotionEffectType.SPEED.getColor());
	            meta.setDisplayName(new CCT().colorize("&fPotion of Speed"));
	            i.setItemMeta(meta);
				items.add(i);
				meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1), true);
	            meta.setColor(PotionEffectType.SPEED.getColor());
	            meta.setDisplayName(new CCT().colorize("&fPotion of Speed"));
	            i.setItemMeta(meta);
	            items.add(i);
	            meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1200, 0), true);
	            meta.setColor(PotionEffectType.INCREASE_DAMAGE.getColor());
	            meta.setDisplayName(new CCT().colorize("&fPotion of Strength"));
	            i.setItemMeta(meta);
	            items.add(i);
	            meta.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 3000, 0), true);
	            meta.setColor(PotionEffectType.FIRE_RESISTANCE.getColor());
	            meta.setDisplayName(new CCT().colorize("&fPotion of Fire Resistance"));
	            i.setItemMeta(meta);
	            items.add(i);
	            meta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 1, 0), true);
	            meta.setColor(PotionEffectType.HEAL.getColor());
	            meta.setDisplayName(new CCT().colorize("&fPotion of Instant Health"));
	            i.setItemMeta(meta);
	            i.setAmount(2);
	            items.add(i);
	            meta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 0), true);
	            meta.setColor(PotionEffectType.JUMP.getColor());
	            meta.setDisplayName(new CCT().colorize("&fPotion of Jump Boost"));
	            i.setItemMeta(meta);
	            items.add(i);
	            meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 0), true);
	            meta.setColor(PotionEffectType.REGENERATION.getColor());
	            meta.setDisplayName(new CCT().colorize("&fPotion of Regeneration"));
	            i.setItemMeta(meta);
	            i.setAmount(1);
	            items.add(i);
	            i.setType(Material.SPLASH_POTION);
	            meta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 1, 1), true);
	            meta.setColor(PotionEffectType.HARM.getColor());
	            meta.setDisplayName(new CCT().colorize("&fPotion of Harming"));
	            i.setItemMeta(meta);
	            items.add(i);
	            meta.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 0), true);
	            meta.setColor(PotionEffectType.WEAKNESS.getColor());
	            meta.setDisplayName(new CCT().colorize("&fPotion of Weakness"));
	            i.setItemMeta(meta);
	            items.add(i);
	            meta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 1200, 0), true);
	            meta.setColor(PotionEffectType.SLOW_FALLING.getColor());
	            meta.setDisplayName(new CCT().colorize("&fPotion of Slow Falling"));
	            i.setItemMeta(meta);
	            items.add(i);
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		CRYSTAL("&6Crystal", 102, 345600000L, Material.NETHER_STAR){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(common(5));
				items.add(rare(4));
				items.add(epic(3));
				items.add(legendary(2));
				items.add(mythic(1));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		BUILD("&6Build", 103, 172800000L, Material.GRASS){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(builder.constructItem(Material.OAK_LOG, 64));
				items.add(builder.constructItem(Material.COBBLESTONE, 64));
				items.add(builder.constructItem(Material.COBBLESTONE, 64));
				items.add(builder.constructItem(Material.OBSIDIAN, 4));
				items.add(builder.constructItem(Material.GLASS, 64));
				items.add(builder.constructItem(Material.WHITE_WOOL, 64));
				items.add(builder.constructItem(Material.QUARTZ_BLOCK, 64));
				items.add(builder.constructItem(Material.GLOWSTONE, 16));
				items.add(builder.constructItem(Material.BRICKS, 64));
				items.add(builder.constructItem(Material.OAK_LEAVES, 64));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		FLIGHT("&6Flight", 104, 432000000L, Material.FEATHER){
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(builder.constructItem(
						Material.FEATHER,
						1,
						"&6Faction Flying Feather (10min)",
						new String[] {
							"&7When you right click this item, you will be",
							"&7able to fly in your territory for 10 minutes.",
							"&7This will stack if you already have this effect active."
						},
						new Pair<String, String>("Fly Ticket", ""),
						new Pair<String, Long>("Duration", 600000L)
				));
				items.add(builder.constructItem(
						Material.FEATHER,
						1,
						"&6Faction Flying Feather (20min)",
						new String[] {
							"&7When you right click this item, you will be",
							"&7able to fly in your territory for 20 minutes.",
							"&7This will stack if you already have this effect active."
						},
						new Pair<String, String>("Fly Ticket", ""),
						new Pair<String, Long>("Duration", 1200000L)
				));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		},
		SUPPLIER("&6Supplier", 105, 345600000L, Material.CHEST)
		{
			@Override
			public void recieveKit(Player p) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(common(4));
				items.add(rare(3));
				items.add(epic(2));
				items.add(legendary(1));
				items.add(builder.constructItem(Material.GOLDEN_APPLE, 16));
				items.add(builder.constructItem(Material.ENDER_PEARL, 16));
				items.add(builder.constructItem(Material.COOKED_BEEF, 64));
				items.add(builder.constructItem(Material.TNT, 16));
				for(ItemStack item : items) {
					if(p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem(item);
					}
					else {
						p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.25, 0), item);
					}
				}
			}
		};
		private final String display;
		private final Long cooldown;
		private final int rank;
		private final Material kDisplay;
		
		public String getDisplay() {
			return this.display;
		}
		
		public Long getCooldown() {
			return this.cooldown;
		}
		
		public int getRank() {
			return this.rank;
		}
		
		public Material getMaterialDisplay() {
			return this.kDisplay;
		}
		
		private Kit(String display, int rank, Long cooldown, Material kDisplay) {
			this.display = new CCT().colorize(display);
			this.cooldown = cooldown;
			this.rank = rank;
			this.kDisplay = kDisplay;
		}
		public void recieveKit(Player p) {};
	}
	public static ItemStack moneyNote(int money) {
		ItemStack item1 = new ItemStack(Material.PAPER, 1);
		NBTItem i = new NBTItem(item1);
		i.setInteger("Money", money);
		item1 = i.getItem();
		ItemMeta meta1 = item1.getItemMeta();
		meta1.setDisplayName(new CCT().colorize("&aMoney Note: $" + i.getInteger("Money")));
		item1.setItemMeta(meta1);
		return item1;
	}
	public static ItemStack playerXPBottle(int xp) {
		ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
		NBTItem i = new NBTItem(item);
		i.setInteger("XPBottle", xp);
		item = i.getItem();
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lXP Bottle (Player)"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7When you combine your item with this bottle,"));
		lore1.add(new CCT().colorize("&7It will add the XP on this bottle to your weapon."));
		lore1.add(new CCT().colorize("&7XP Amount: &6" + xp));
		itemmeta.setLore(lore1);
		item.setItemMeta(itemmeta);
		return item;
	}
	public static ItemStack common(int amount) {
		ItemStack item = builder.constructItem(
			Material.NETHER_STAR,
			amount,
			"&7Common Crystal",
			new String[] {
				"&7Bring me to &6&lNOVIS &7to get some",
				"&7really nice rewards!",
				"&7Rarity: &7Common"
			},
			new Pair<String, String>("CrystalObject", ""),
			new Pair<String, String>("Rarity", "Common")
		);
		return item;
	}
	public static ItemStack rare(int amount) {
		ItemStack item = builder.constructItem(
			Material.NETHER_STAR,
			amount,
			"&aRare Crystal",
			new String[] {
				"&7Bring me to &6&lNOVIS &7to get some",
				"&7really nice rewards!",
				"&7Rarity: &aRare"
			},
			new Pair<String, String>("CrystalObject", ""),
			new Pair<String, String>("Rarity", "Rare")
		);
		return item;
	}
	public static ItemStack epic(int amount) {
		ItemStack item = builder.constructItem(
			Material.NETHER_STAR,
			amount,
			"&bEpic Crystal",
			new String[] {
				"&7Bring me to &6&lNOVIS &7to get some",
				"&7really nice rewards!",
				"&7Rarity: &bEpic"
			},
			new Pair<String, String>("CrystalObject", ""),
			new Pair<String, String>("Rarity", "Epic")
		);
		return item;
	}
	public static ItemStack legendary(int amount) {
		ItemStack item = builder.constructItem(
			Material.NETHER_STAR,
			amount,
			"&cLegendary Crystal",
			new String[] {
				"&7Bring me to &6&lNOVIS &7to get some",
				"&7really nice rewards!",
				"&7Rarity: &cLegendary"
			},
			new Pair<String, String>("CrystalObject", ""),
			new Pair<String, String>("Rarity", "Legendary")
		);
		return item;
	}
	public static ItemStack mythic(int amount) {
		ItemStack item = builder.constructItem(
			Material.NETHER_STAR,
			amount,
			"&5Mythic Crystal",
			new String[] {
				"&7Bring me to &6&lNOVIS &7to get some",
				"&7really nice rewards!",
				"&7Rarity: &5Mythic"
			},
			new Pair<String, String>("CrystalObject", ""),
			new Pair<String, String>("Rarity", "Mythic")
		);
		return item;
	}
	public static ItemStack heroic(int amount) {
		ItemStack item = builder.constructItem(
			Material.NETHER_STAR,
			amount,
			"&eHeroic Crystal",
			new String[] {
				"&7Bring me to &6&lNOVIS &7to get some",
				"&7really nice rewards!",
				"&7Rarity: &eHeroic"
			},
			new Pair<String, String>("CrystalObject", ""),
			new Pair<String, String>("Rarity", "Heroic")
		);
		return item;
	}
}
