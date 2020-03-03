package me.WiebeHero.RankedPlayerPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.base.Enums;

import de.tr7zw.nbtapi.NBTItem;
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
	public enum Rank{
		USER(0, "&7User"),
		BRONZE(1, "&6Bronze"),
		SILVER(2, "&7Silver"),
		GOLD(3, "&eGold"),
		PLATINUM(4, "&3Platinum"),
		DIAMOND(5, "&bDiamond"),
		EMERALD(6, "&aEmerald"),
		YOUTUBER(6, "&cY&fo&cu&ft&cu&fb&ce&fr"),
		QA(7, "&bQA"),
		QA_ADMIN(8, "&cQA Admin"),
		HELPER(9, "&aHelper"),
		HELPER_PLUS(10, "&aHelper+"),
		MOD(11, "&9Mod"),
		HEAD_MOD(12, "&1Head Mod"),
		ADMIN(13, "&4Admin"),
		HEAD_ADMIN(14, "&4Head Admin"),
		MANAGER(15, "&5Manager"),
		OWNER(16, "&2Owner");
		public final int rank;
		public final String display;
		
		private Rank(int rank, String display) {
			this.rank = rank;
			this.display = new CCT().colorize(display);
		}
	}
	public Kit getIfPresentKit(String name) {
		return Enums.getIfPresent(Kit.class, name).orNull();
	}
	public enum Kit{
		USER("&7User", 0, 86400000L){
			@Override
			public void recieveKit(Player p) {
				
			}
		},
		BRONZE("&6Bronze", 1, 259200000L){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 1);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 235, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 235, tier));
				items.add(common(2));
				items.add(rare(1));
				items.add(builder.constructItem(Material.TNT, 16));
				items.add(builder.constructItem(Material.OBSIDIAN, 16));
				items.add(playerXPBottle(75));
				items.add(moneyNote(1500));
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
		SILVER("&7Silver", 2, 259200000L){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 2);
				tier.put("Unbreaking", 1);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 270, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 270, tier));
				items.add(rare(2));
				items.add(epic(1));
				items.add(builder.constructItem(Material.TNT, 32));
				items.add(builder.constructItem(Material.OBSIDIAN, 32));
				items.add(playerXPBottle(150));
				items.add(moneyNote(2500));
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
		GOLD("&eGold", 3, 259200000L){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 3);
				tier.put("Unbreaking", 1);
				tier.put("Fortune", 1);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 305, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 305, tier));
				items.add(rare(3));
				items.add(epic(2));
				items.add(builder.constructItem(Material.TNT, 48));
				items.add(builder.constructItem(Material.OBSIDIAN, 48));
				items.add(playerXPBottle(300));
				items.add(moneyNote(5500));
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
		PLATINUM("&3Platinum", 4, 259200000L){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 4);
				tier.put("Unbreaking", 2);
				tier.put("Fortune", 2);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 340, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 340, tier));
				items.add(rare(3));
				items.add(epic(3));
				items.add(legendary(1));
				items.add(builder.constructItem(Material.TNT, 64));
				items.add(builder.constructItem(Material.OBSIDIAN, 64));
				items.add(playerXPBottle(600));
				items.add(moneyNote(8500));
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
		DIAMOND("&bDiamond", 5, 259200000L){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 5);
				tier.put("Unbreaking", 3);
				tier.put("Fortune", 3);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 375, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 375, tier));
				items.add(epic(3));
				items.add(legendary(2));
				items.add(mythic(1));
				items.add(builder.constructItem(Material.TNT, 64));
				items.add(builder.constructItem(Material.TNT, 32));
				items.add(builder.constructItem(Material.OBSIDIAN, 64));
				items.add(builder.constructItem(Material.OBSIDIAN, 32));
				items.add(playerXPBottle(1000));
				items.add(moneyNote(10000));
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
		EMERALD("&aEmerald", 6, 259200000L){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 5);
				tier.put("Unbreaking", 4);
				tier.put("Fortune", 4);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 405, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 405, tier));
				items.add(epic(3));
				items.add(legendary(3));
				items.add(mythic(2));
				items.add(builder.constructItem(Material.TNT, 64));
				items.add(builder.constructItem(Material.TNT, 64));
				items.add(builder.constructItem(Material.OBSIDIAN, 64));
				items.add(builder.constructItem(Material.OBSIDIAN, 64));
				items.add(playerXPBottle(1500));
				items.add(moneyNote(12500));
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
		YOUTUBER("&cY&fo&cu&ft&cu&fb&ce&fr", 7, 259200000L){
			@Override
			public void recieveKit(Player p) {
				HashMap<String, Integer> tier = new HashMap<String, Integer>();
				tier.put("Efficiency", 5);
				tier.put("Unbreaking", 4);
				tier.put("Fortune", 4);
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				items.add(con.equipment(Material.DIAMOND_PICKAXE, "&7Diamond Pickaxe", EquipmentSlot.HAND, 2.6, 0.6, 405, tier));
				items.add(con.equipment(Material.DIAMOND_SHOVEL, "&7Diamond Shovel", EquipmentSlot.HAND, 3.2, 1.06, 405, tier));
				items.add(epic(3));
				items.add(legendary(3));
				items.add(mythic(2));
				items.add(builder.constructItem(Material.TNT, 64));
				items.add(builder.constructItem(Material.TNT, 64));
				items.add(builder.constructItem(Material.OBSIDIAN, 64));
				items.add(builder.constructItem(Material.OBSIDIAN, 64));
				items.add(playerXPBottle(1500));
				items.add(moneyNote(12500));
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
		QA("&bQA", 8, 259200000L){
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
		QA_ADMIN("&cQA Admin", 9, 259200000L){
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
		HELPER("&aHelper", 10, 259200000L){
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
		HELPER_PLUS("&aHelper+", 11, 259200000L){
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
		MOD("&9Mod", 12, 259200000L){
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
		HEAD_MOD("&1Head Mod", 13, 259200000L){
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
		ADMIN("&4Admin", 14, 259200000L){
			@Override
			public void recieveKit(Player p) {
				
			}
		},
		HEAD_ADMIN("&4Head Admin", 15, 259200000L){
			@Override
			public void recieveKit(Player p) {
				
			}
		},
		MANAGER("&5Manager", 16, 259200000L){
			@Override
			public void recieveKit(Player p) {
				
			}
		},
		OWNER("&2Owner", 17, 259200000L){
			@Override
			public void recieveKit(Player p) {
				
			}
		},
		RAID("&6Raid", 100, 259200000L){
			@Override
			public void recieveKit(Player p) {
				
			}
		},
		POTION("&6Potion", 101, 259200000L){
			@Override
			public void recieveKit(Player p) {
				
			}
		},
		CRYSTAL("&6Crystal", 102, 259200000L){
			@Override
			public void recieveKit(Player p) {
				
			}
		},
		BUILD("&6Build", 103, 259200000L){
			@Override
			public void recieveKit(Player p) {
				
			}
		},
		FLIGHT("&6Flight", 104, 259200000L){
			@Override
			public void recieveKit(Player p) {
				
			}
		},
		SUPPLIER("&6Supplier", 105, 259200000L)
		{
			@Override
			public void recieveKit(Player p) {
				
			}
		};
		public final String display;
		public final Long cooldown;
		public final int rank;
		
		private Kit(String display, int rank, Long cooldown) {
			this.display = new CCT().colorize(display);
			this.cooldown = cooldown;
			this.rank = rank;
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
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(new CCT().colorize("&a&lXP Bottle (Player)"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new CCT().colorize("&7When you combine your item with this bottle,"));
		lore1.add(new CCT().colorize("&7It will add the XP on this bottle to your weapon."));
		lore1.add(new CCT().colorize("&7XP Amount: " + xp));
		itemmeta.setLore(lore1);
		item.setItemMeta(itemmeta);
		return item;
	}
	public static ItemStack common(int amount) {
		ItemStack item = builder.constructItem(
			Material.NETHER_STAR,
			amount,
			"&7Common Crystal",
			new ArrayList<String>(Arrays.asList(
				"&7Bring me to &6&lNOVIS &7to get some",
				"&7really nice rewards!",
				"&7Rarity: &7Common"
			)),
			new ArrayList<String>(Arrays.asList(
				"CrystalObject",
				"Rarity"
			)),
			new ArrayList<String>(Arrays.asList(
				"",
				"Common"
			))
		);
		return item;
	}
	public static ItemStack rare(int amount) {
		ItemStack item = builder.constructItem(
			Material.NETHER_STAR,
			amount,
			"&aRare Crystal",
			new ArrayList<String>(Arrays.asList(
				"&7Bring me to &6&lNOVIS &7to get some",
				"&7really nice rewards!",
				"&7Rarity: &aRare"
			)),
			new ArrayList<String>(Arrays.asList(
				"CrystalObject",
				"Rarity"
			)),
			new ArrayList<String>(Arrays.asList(
				"",
				"Rare"
			))
		);
		return item;
	}
	public static ItemStack epic(int amount) {
		ItemStack item = builder.constructItem(
			Material.NETHER_STAR,
			amount,
			"&bEpic Crystal",
			new ArrayList<String>(Arrays.asList(
				"&7Bring me to &6&lNOVIS &7to get some",
				"&7really nice rewards!",
				"&7Rarity: &bEpic"
			)),
			new ArrayList<String>(Arrays.asList(
				"CrystalObject",
				"Rarity"
			)),
			new ArrayList<String>(Arrays.asList(
				"",
				"Epic"
			))
		);
		return item;
	}
	public static ItemStack legendary(int amount) {
		ItemStack item = builder.constructItem(
			Material.NETHER_STAR,
			amount,
			"&cLegendary Crystal",
			new ArrayList<String>(Arrays.asList(
				"&7Bring me to &6&lNOVIS &7to get some",
				"&7really nice rewards!",
				"&7Rarity: &cLegendary"
			)),
			new ArrayList<String>(Arrays.asList(
				"CrystalObject",
				"Rarity"
			)),
			new ArrayList<String>(Arrays.asList(
				"",
				"Legendary"
			))
		);
		return item;
	}
	public static ItemStack mythic(int amount) {
		ItemStack item = builder.constructItem(
			Material.NETHER_STAR,
			amount,
			"&5Mythic Crystal",
			new ArrayList<String>(Arrays.asList(
				"&7Bring me to &6&lNOVIS &7to get some",
				"&7really nice rewards!",
				"&7Rarity: &5Mythic"
			)),
			new ArrayList<String>(Arrays.asList(
				"CrystalObject",
				"Rarity"
			)),
			new ArrayList<String>(Arrays.asList(
				"",
				"Mythic"
			))
		);
		return item;
	}
	public static ItemStack heroic(int amount) {
		ItemStack item = builder.constructItem(
			Material.NETHER_STAR,
			amount,
			"&eHeroic Crystal",
			new ArrayList<String>(Arrays.asList(
				"&7Bring me to &6&lNOVIS &7to get some",
				"&7really nice rewards!",
				"&7Rarity: &eHeroic"
			)),
			new ArrayList<String>(Arrays.asList(
				"CrystalObject",
				"Rarity"
			)),
			new ArrayList<String>(Arrays.asList(
				"",
				"Heroic"
			))
		);
		return item;
	}
}
