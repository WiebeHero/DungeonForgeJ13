package me.WiebeHero.DailyRewards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javafx.util.Pair;
import me.WiebeHero.Boosters.BoosterTypes.BoosterType;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.DailyRewards.DailyRewardEnum.LootTable;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;

public class DailyRewardLootTable {
	
	private HashMap<LootTable, ArrayList<ItemStack>> dailyList;
	private HashMap<LootTable, ArrayList<ItemStack>> weeklyList;
	private HashMap<Rank, HashMap<LootTable, ArrayList<ItemStack>>> monthlyList;
	private ItemStackBuilder buider;
	
	public DailyRewardLootTable(ItemStackBuilder builder) {
		this.monthlyList = new HashMap<Rank, HashMap<LootTable, ArrayList<ItemStack>>>();
		this.monthlyList.put(Rank.BRONZE, new HashMap<LootTable, ArrayList<ItemStack>>());
		this.monthlyList.put(Rank.SILVER, new HashMap<LootTable, ArrayList<ItemStack>>());
		this.monthlyList.put(Rank.GOLD, new HashMap<LootTable, ArrayList<ItemStack>>());
		this.monthlyList.put(Rank.PLATINUM, new HashMap<LootTable, ArrayList<ItemStack>>());
		this.monthlyList.put(Rank.DIAMOND, new HashMap<LootTable, ArrayList<ItemStack>>());
		this.monthlyList.put(Rank.EMERALD, new HashMap<LootTable, ArrayList<ItemStack>>());
		this.dailyList = new HashMap<LootTable, ArrayList<ItemStack>>();
		this.weeklyList = new HashMap<LootTable, ArrayList<ItemStack>>();
		this.buider = builder;
	}
	public void constructDailyList() {
		HashMap<LootTable, ArrayList<ItemStack>> list = new HashMap<LootTable, ArrayList<ItemStack>>();
		LootTable rateC = LootTable.COMMON.setRate(62.50F);
		LootTable rateR = LootTable.RARE.setRate(20.0F);
		LootTable rateVR = LootTable.VERY_RARE.setRate(10.0F);
		LootTable rateSP = LootTable.SUPER_RARE.setRate(5.0F);
		LootTable rateIR = LootTable.INSANELY_RARE.setRate(2.5F);
		list.put(rateC, new ArrayList<ItemStack>());
		list.put(rateR, new ArrayList<ItemStack>());
		list.put(rateVR, new ArrayList<ItemStack>());
		list.put(rateSP, new ArrayList<ItemStack>());
		list.put(rateIR, new ArrayList<ItemStack>());
		list.get(rateC).add(this.buider.constructItem(
				Material.NETHER_STAR,
				2,
				"&7Common Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &7Common"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Common")
		));
		list.get(rateC).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aMoney Note: $1000-2000$",
				new Pair<String, String>("RandomMoney", ""),
				new Pair<String, Integer>("Min", 1000),
				new Pair<String, Integer>("Max", 1000)
		));
		list.get(rateC).add(this.buider.constructItem(
				Material.EXPERIENCE_BOTTLE, 
				1,
				"&a&lXP Bottle (Player)",
				new ArrayList<String>(Arrays.asList(
						"&7When you combine your item with this bottle,",
						"&7It will add the XP on this bottle to your weapon.",
						"&7XP Amount: &6Between 100-200"
				)),
				new Pair<String, String>("RandomXPBottle", ""),
				new Pair<String, Integer>("Min", 100),
				new Pair<String, Integer>("Max", 100)
		));
		list.get(rateC).add(this.buider.constructItem(
				Material.NETHER_STAR,
				2,
				"&aRare Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &aRare"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Rare")
		));
		list.get(rateR).add(this.buider.constructItem(
				Material.NETHER_STAR,
				2,
				"&bEpic Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &bEpic"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Epic")
		));
		list.get(rateR).add(this.buider.constructItem(
				Material.EXPERIENCE_BOTTLE, 
				1,
				"&a&lXP Bottle (Player)",
				new ArrayList<String>(Arrays.asList(
						"&7When you combine your item with this bottle,",
						"&7It will add the XP on this bottle to your weapon.",
						"&7XP Amount: &6Between 200-300"
				)),
				new Pair<String, String>("RandomXPBottle", ""),
				new Pair<String, Integer>("Min", 200),
				new Pair<String, Integer>("Max", 100)
		));
		list.get(rateR).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aMoney Note: $2000-3000$",
				new Pair<String, String>("RandomMoney", ""),
				new Pair<String, Integer>("Min", 2000),
				new Pair<String, Integer>("Max", 1000)
		));
		list.get(rateVR).add(this.buider.constructItem(
				Material.NETHER_STAR,
				2,
				"&cLegendary Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &cLegendary"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Legendary")
		));
		list.get(rateVR).add(this.buider.constructItem(
				Material.EXPERIENCE_BOTTLE, 
				1,
				"&a&lXP Bottle (Player)",
				new ArrayList<String>(Arrays.asList(
						"&7When you combine your item with this bottle,",
						"&7It will add the XP on this bottle to your weapon.",
						"&7XP Amount: &6Between 300-400"
				)),
				new Pair<String, String>("RandomXPBottle", ""),
				new Pair<String, Integer>("Min", 300),
				new Pair<String, Integer>("Max", 100)
		));
		list.get(rateVR).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aMoney Note: $3000-4000$",
				new Pair<String, String>("RandomMoney", ""),
				new Pair<String, Integer>("Min", 3000),
				new Pair<String, Integer>("Max", 1000)
		));
		list.get(rateVR).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aPersonal XP Booster",
				new ArrayList<String>(Arrays.asList(
						"&7You gain &610% &7more player xp for the",
						"&7next 30 minutes."
				)),
				new Pair<String, String>("Booster", ""),
				new Pair<String, Double>("Increase", 10.0),
				new Pair<String, Long>("Duration", 1800000L),
				new Pair<String, BoosterType>("BoosterType", BoosterType.PERSONAL)
		));
		list.get(rateSP).add(this.buider.constructItem(
				Material.NETHER_STAR,
				1,
				"&5Mythic Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &5Mythic"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Mythic")
		));
		list.get(rateSP).add(this.buider.constructItem(
				Material.EXPERIENCE_BOTTLE, 
				1,
				"&a&lXP Bottle (Player)",
				new ArrayList<String>(Arrays.asList(
						"&7When you combine your item with this bottle,",
						"&7It will add the XP on this bottle to your weapon.",
						"&7XP Amount: &6Between 400-500"
				)),
				new Pair<String, String>("RandomXPBottle", ""),
				new Pair<String, Integer>("Min", 400),
				new Pair<String, Integer>("Max", 100)
		));
		list.get(rateSP).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aMoney Note: $4000-5000$",
				new Pair<String, String>("RandomMoney", ""),
				new Pair<String, Integer>("Min", 4000),
				new Pair<String, Integer>("Max", 1000)
		));
		list.get(rateSP).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aPersonal XP Booster",
				new ArrayList<String>(Arrays.asList(
						"&7You gain &615% &7more player xp for the",
						"&7next 45 minutes."
				)),
				new Pair<String, String>("Booster", ""),
				new Pair<String, Double>("Increase", 15.0),
				new Pair<String, Long>("Duration", 2700000L),
				new Pair<String, BoosterType>("BoosterType", BoosterType.PERSONAL)
		));
		list.get(rateSP).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aFaction XP Booster",
				new ArrayList<String>(Arrays.asList(
						"&7Your faction members  and yourself",
						"&7gain &65% &7more player xp for the",
						"&7next 30 minutes."
				)),
				new Pair<String, String>("Booster", ""),
				new Pair<String, Double>("Increase", 5.0),
				new Pair<String, Long>("Duration", 1800000L),
				new Pair<String, BoosterType>("BoosterType", BoosterType.FACTION)
		));
		list.get(rateIR).add(this.buider.constructItem(
				Material.NETHER_STAR,
				1,
				"&eHeroic Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &eHeroic"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Heroic")
		));
		list.get(rateIR).add(this.buider.constructItem(
				Material.EXPERIENCE_BOTTLE, 
				1,
				"&a&lXP Bottle (Player)",
				new ArrayList<String>(Arrays.asList(
						"&7When you combine your item with this bottle,",
						"&7It will add the XP on this bottle to your weapon.",
						"&7XP Amount: &6Between 500-600"
				)),
				new Pair<String, String>("RandomXPBottle", ""),
				new Pair<String, Integer>("Min", 500),
				new Pair<String, Integer>("Max", 100)
		));
		list.get(rateIR).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aMoney Note: $5000-6000$",
				new Pair<String, String>("RandomMoney", ""),
				new Pair<String, Integer>("Min", 5000),
				new Pair<String, Integer>("Max", 1000)
		));
		list.get(rateIR).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aPersonal XP Booster",
				new ArrayList<String>(Arrays.asList(
						"&7You gain &620% &7more player xp for the",
						"&7next hour."
				)),
				new Pair<String, String>("Booster", ""),
				new Pair<String, Double>("Increase", 20.0),
				new Pair<String, Long>("Duration", 3600000L),
				new Pair<String, BoosterType>("BoosterType", BoosterType.PERSONAL)
		));
		list.get(rateIR).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aFaction XP Booster",
				new ArrayList<String>(Arrays.asList(
						"&7Your faction members  and yourself",
						"&7gain &610% &7more player xp for the",
						"&7next 45 minutes."
				)),
				new Pair<String, String>("Booster", ""),
				new Pair<String, Double>("Increase", 10.0),
				new Pair<String, Long>("Duration", 2700000L),
				new Pair<String, BoosterType>("BoosterType", BoosterType.FACTION)
		));
		this.dailyList = list;
	}
	public void constructWeeklyList() {
		HashMap<LootTable, ArrayList<ItemStack>> list = new HashMap<LootTable, ArrayList<ItemStack>>();
		LootTable rateC = LootTable.COMMON.setRate(43.75F);
		LootTable rateR = LootTable.RARE.setRate(40.0F);
		LootTable rateVR = LootTable.VERY_RARE.setRate(20.00F);
		LootTable rateSP = LootTable.SUPER_RARE.setRate(10.0F);
		LootTable rateIR = LootTable.INSANELY_RARE.setRate(5.0F);
		list.put(rateC, new ArrayList<ItemStack>());
		list.put(rateR, new ArrayList<ItemStack>());
		list.put(rateVR, new ArrayList<ItemStack>());
		list.put(rateSP, new ArrayList<ItemStack>());
		list.put(rateIR, new ArrayList<ItemStack>());
		list.get(rateC).add(this.buider.constructItem(
				Material.NETHER_STAR,
				2,
				"&7Common Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &7Common"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Common")
		));
		list.get(rateC).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aMoney Note: $1000-2000$",
				new Pair<String, String>("RandomMoney", ""),
				new Pair<String, Integer>("Min", 1000),
				new Pair<String, Integer>("Max", 1000)
		));
		list.get(rateC).add(this.buider.constructItem(
				Material.EXPERIENCE_BOTTLE, 
				1,
				"&a&lXP Bottle (Player)",
				new ArrayList<String>(Arrays.asList(
						"&7When you combine your item with this bottle,",
						"&7It will add the XP on this bottle to your weapon.",
						"&7XP Amount: &6Between 100-200"
				)),
				new Pair<String, String>("RandomXPBottle", ""),
				new Pair<String, Integer>("Min", 100),
				new Pair<String, Integer>("Max", 100)
		));
		list.get(rateC).add(this.buider.constructItem(
				Material.NETHER_STAR,
				2,
				"&aRare Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &aRare"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Rare")
		));
		list.get(rateR).add(this.buider.constructItem(
				Material.NETHER_STAR,
				2,
				"&bEpic Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &bEpic"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Epic")
		));
		list.get(rateR).add(this.buider.constructItem(
				Material.EXPERIENCE_BOTTLE, 
				1,
				"&a&lXP Bottle (Player)",
				new ArrayList<String>(Arrays.asList(
						"&7When you combine your item with this bottle,",
						"&7It will add the XP on this bottle to your weapon.",
						"&7XP Amount: &6Between 200-300"
				)),
				new Pair<String, String>("RandomXPBottle", ""),
				new Pair<String, Integer>("Min", 200),
				new Pair<String, Integer>("Max", 100)
		));
		list.get(rateR).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aMoney Note: $2000-3000$",
				new Pair<String, String>("RandomMoney", ""),
				new Pair<String, Integer>("Min", 2000),
				new Pair<String, Integer>("Max", 1000)
		));
		list.get(rateVR).add(this.buider.constructItem(
				Material.NETHER_STAR,
				2,
				"&cLegendary Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &cLegendary"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Legendary")
		));
		list.get(rateVR).add(this.buider.constructItem(
				Material.EXPERIENCE_BOTTLE, 
				1,
				"&a&lXP Bottle (Player)",
				new ArrayList<String>(Arrays.asList(
						"&7When you combine your item with this bottle,",
						"&7It will add the XP on this bottle to your weapon.",
						"&7XP Amount: &6Between 300-400"
				)),
				new Pair<String, String>("RandomXPBottle", ""),
				new Pair<String, Integer>("Min", 300),
				new Pair<String, Integer>("Max", 100)
		));
		list.get(rateVR).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aMoney Note: $3000-4000$",
				new Pair<String, String>("RandomMoney", ""),
				new Pair<String, Integer>("Min", 3000),
				new Pair<String, Integer>("Max", 1000)
		));
		list.get(rateVR).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aPersonal XP Booster",
				new ArrayList<String>(Arrays.asList(
						"&7You gain &610% &7more player xp for the",
						"&7next 30 minutes."
				)),
				new Pair<String, String>("Booster", ""),
				new Pair<String, Double>("Increase", 10.0),
				new Pair<String, Long>("Duration", 1800000L),
				new Pair<String, BoosterType>("BoosterType", BoosterType.PERSONAL)
		));
		list.get(rateSP).add(this.buider.constructItem(
				Material.NETHER_STAR,
				1,
				"&5Mythic Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &5Mythic"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Mythic")
		));
		list.get(rateSP).add(this.buider.constructItem(
				Material.EXPERIENCE_BOTTLE, 
				1,
				"&a&lXP Bottle (Player)",
				new ArrayList<String>(Arrays.asList(
						"&7When you combine your item with this bottle,",
						"&7It will add the XP on this bottle to your weapon.",
						"&7XP Amount: &6Between 400-500"
				)),
				new Pair<String, String>("RandomXPBottle", ""),
				new Pair<String, Integer>("Min", 400),
				new Pair<String, Integer>("Max", 100)
		));
		list.get(rateSP).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aMoney Note: $4000-5000$",
				new Pair<String, String>("RandomMoney", ""),
				new Pair<String, Integer>("Min", 4000),
				new Pair<String, Integer>("Max", 1000)
		));
		list.get(rateSP).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aPersonal XP Booster",
				new ArrayList<String>(Arrays.asList(
						"&7You gain &615% &7more player xp for the",
						"&7next 45 minutes."
				)),
				new Pair<String, String>("Booster", ""),
				new Pair<String, Double>("Increase", 15.0),
				new Pair<String, Long>("Duration", 2700000L),
				new Pair<String, BoosterType>("BoosterType", BoosterType.PERSONAL)
		));
		list.get(rateSP).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aFaction XP Booster",
				new ArrayList<String>(Arrays.asList(
						"&7Your faction members  and yourself",
						"&7gain &65% &7more player xp for the",
						"&7next 30 minutes."
				)),
				new Pair<String, String>("Booster", ""),
				new Pair<String, Double>("Increase", 5.0),
				new Pair<String, Long>("Duration", 1800000L),
				new Pair<String, BoosterType>("BoosterType", BoosterType.FACTION)
		));
		list.get(rateIR).add(this.buider.constructItem(
				Material.NETHER_STAR,
				1,
				"&eHeroic Crystal",
				new ArrayList<String>(Arrays.asList(
					"&7Bring me to &6&lNOVIS &7to get some",
					"&7really nice rewards!",
					"&7Rarity: &eHeroic"
				)),
				new Pair<String, String>("CrystalObject", ""),
				new Pair<String, String>("Rarity", "Heroic")
		));
		list.get(rateIR).add(this.buider.constructItem(
				Material.EXPERIENCE_BOTTLE, 
				1,
				"&a&lXP Bottle (Player)",
				new ArrayList<String>(Arrays.asList(
						"&7When you combine your item with this bottle,",
						"&7It will add the XP on this bottle to your weapon.",
						"&7XP Amount: &6Between 500-600"
				)),
				new Pair<String, String>("RandomXPBottle", ""),
				new Pair<String, Integer>("Min", 500),
				new Pair<String, Integer>("Max", 100)
		));
		list.get(rateIR).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aMoney Note: $5000-6000$",
				new Pair<String, String>("RandomMoney", ""),
				new Pair<String, Integer>("Min", 5000),
				new Pair<String, Integer>("Max", 1000)
		));
		list.get(rateIR).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aPersonal XP Booster",
				new ArrayList<String>(Arrays.asList(
						"&7You gain &620% &7more player xp for the",
						"&7next hour."
				)),
				new Pair<String, String>("Booster", ""),
				new Pair<String, Double>("Increase", 20.0),
				new Pair<String, Long>("Duration", 3600000L),
				new Pair<String, BoosterType>("BoosterType", BoosterType.PERSONAL)
		));
		list.get(rateIR).add(this.buider.constructItem(
				Material.PAPER, 
				1,
				"&aFaction XP Booster",
				new ArrayList<String>(Arrays.asList(
						"&7Your faction members  and yourself",
						"&7gain &610% &7more player xp for the",
						"&7next 45 minutes."
				)),
				new Pair<String, String>("Booster", ""),
				new Pair<String, Double>("Increase", 10.0),
				new Pair<String, Long>("Duration", 2700000L),
				new Pair<String, BoosterType>("BoosterType", BoosterType.FACTION)
		));
		list.get(rateIR).add(this.buider.constructItem(
				Material.TRIPWIRE_HOOK,
				1,
				"&4&lRandom Kit Key",
				new ArrayList<String>(Arrays.asList(
					"&7When you right click, you unlock",
					"&7a random kit. (Non donator kits)"
				)),
				new Pair<String, String>("KitKey", "")
		));
		this.weeklyList = list;
	}
	public void constructMonthlyList() {
		HashMap<LootTable, ArrayList<ItemStack>> list = new HashMap<LootTable, ArrayList<ItemStack>>();
		ArrayList<Rank> ranks = new ArrayList<Rank>(Arrays.asList(Rank.BRONZE, Rank.SILVER, Rank.GOLD, Rank.PLATINUM, Rank.DIAMOND, Rank.EMERALD));
		for(Rank rank : ranks) {
			switch(rank) {
				case BRONZE:
					list = new HashMap<LootTable, ArrayList<ItemStack>>();
					LootTable rateC = LootTable.COMMON.setRate(55.00F);
					LootTable rateR = LootTable.RARE.setRate(30.0F);
					LootTable rateVR = LootTable.VERY_RARE.setRate(15.0F);
					list.put(rateC, new ArrayList<ItemStack>());
					list.put(rateR, new ArrayList<ItemStack>());
					list.put(rateVR, new ArrayList<ItemStack>());
					list.get(rateC).add(this.buider.constructItem(
							Material.NETHER_STAR,
							12,
							"&7Common Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &7Common"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Common")
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.NETHER_STAR,
							10,
							"&aRare Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &aRare"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Rare")
					));
					list.get(rateC).add(this.buider.constructItem(Material.TNT, 64, "&764x TNT", new Pair<String, Integer>("Amount", 64)));
					list.get(rateC).add(this.buider.constructItem(
							Material.EXPERIENCE_BOTTLE, 
							1,
							"&a&lXP Bottle (Player)",
							new ArrayList<String>(Arrays.asList(
									"&7When you combine your item with this bottle,",
									"&7It will add the XP on this bottle to your weapon.",
									"&7XP Amount: &6Between 400-600"
							)),
							new Pair<String, String>("RandomXPBottle", ""),
							new Pair<String, Integer>("Min", 400),
							new Pair<String, Integer>("Max", 200)
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aMoney Note: $10000-12500$",
							new Pair<String, String>("RandomMoney", ""),
							new Pair<String, Integer>("Min", 10000),
							new Pair<String, Integer>("Max", 2500)
					));
					list.get(rateR).add(this.buider.constructItem(Material.SPAWNER, 1, "&a&lCow Spawner"));
					list.get(rateVR).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aPersonal XP Booster",
							new ArrayList<String>(Arrays.asList(
									"&7You gain &610% &7more player xp for the",
									"&7next hour."
							)),
							new Pair<String, String>("Booster", ""),
							new Pair<String, Double>("Increase", 10.0),
							new Pair<String, Long>("Duration", 3600000L),
							new Pair<String, BoosterType>("BoosterType", BoosterType.PERSONAL)
					));
					list.get(rateVR).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aPersonal Faction Fly Ticket",
							new ArrayList<String>(Arrays.asList(
									"&7You gain the ability to fly in your",
									"&7own faction territory for 30 minutes."
							)),
							new Pair<String, String>("Fly Ticket", ""),
							new Pair<String, Long>("Duration", 1800000L)
					));
					this.monthlyList.put(Rank.BRONZE, list);
					break;
				case SILVER:
					list = new HashMap<LootTable, ArrayList<ItemStack>>();
					rateC = LootTable.COMMON.setRate(55.00F);
					rateR = LootTable.RARE.setRate(30.0F);
					rateVR = LootTable.VERY_RARE.setRate(15.0F);
					list.put(rateC, new ArrayList<ItemStack>());
					list.put(rateR, new ArrayList<ItemStack>());
					list.put(rateVR, new ArrayList<ItemStack>());
					list.get(rateC).add(this.buider.constructItem(
							Material.NETHER_STAR,
							12,
							"&aRare Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &aRare"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Rare")
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.NETHER_STAR,
							10,
							"&bEpic Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &bEpic"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Epic")
					));
					list.get(rateC).add(this.buider.constructItem(Material.TNT, 64, "&7128x TNT", new Pair<String, Integer>("Amount", 128)));
					list.get(rateC).add(this.buider.constructItem(
							Material.EXPERIENCE_BOTTLE, 
							1,
							"&a&lXP Bottle (Player)",
							new ArrayList<String>(Arrays.asList(
									"&7When you combine your item with this bottle,",
									"&7It will add the XP on this bottle to your weapon.",
									"&7XP Amount: &6Between 700-900"
							)),
							new Pair<String, String>("RandomXPBottle", ""),
							new Pair<String, Integer>("Min", 700),
							new Pair<String, Integer>("Max", 200)
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aMoney Note: $14000-17000$",
							new Pair<String, String>("RandomMoney", ""),
							new Pair<String, Integer>("Min", 14000),
							new Pair<String, Integer>("Max", 3000)
					));
					list.get(rateR).add(this.buider.constructItem(Material.SPAWNER, 1, "&a&lSpider Spawner"));
					list.get(rateVR).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aPersonal XP Booster",
							new ArrayList<String>(Arrays.asList(
									"&7You gain &615% &7more player xp for the",
									"&7next 1.5 hours."
							)),
							new Pair<String, String>("Booster", ""),
							new Pair<String, Double>("Increase", 15.0),
							new Pair<String, Long>("Duration", 5400000L),
							new Pair<String, BoosterType>("BoosterType", BoosterType.PERSONAL)
					));
					list.get(rateVR).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aPersonal Faction Fly Ticket",
							new ArrayList<String>(Arrays.asList(
									"&7You gain the ability to fly in your",
									"&7own faction territory for 45 minutes."
							)),
							new Pair<String, String>("Fly Ticket", ""),
							new Pair<String, Long>("Duration", 2700000L)
					));
					this.monthlyList.put(Rank.SILVER, list);
					break;
				case GOLD:
					list = new HashMap<LootTable, ArrayList<ItemStack>>();
					rateC = LootTable.COMMON.setRate(55.00F);
					rateR = LootTable.RARE.setRate(30.0F);
					rateVR = LootTable.VERY_RARE.setRate(15.0F);
					list.put(rateC, new ArrayList<ItemStack>());
					list.put(rateR, new ArrayList<ItemStack>());
					list.put(rateVR, new ArrayList<ItemStack>());
					list.get(rateC).add(this.buider.constructItem(
							Material.NETHER_STAR,
							12,
							"&bEpic Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &bEpic"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Epic")
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.NETHER_STAR,
							10,
							"&cLegendary Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &cLegendary"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Legendary")
					));
					list.get(rateC).add(this.buider.constructItem(Material.TNT, 64, "&7192x TNT", new Pair<String, Integer>("Amount", 192)));
					list.get(rateC).add(this.buider.constructItem(
							Material.EXPERIENCE_BOTTLE, 
							1,
							"&a&lXP Bottle (Player)",
							new ArrayList<String>(Arrays.asList(
									"&7When you combine your item with this bottle,",
									"&7It will add the XP on this bottle to your weapon.",
									"&7XP Amount: &6Between 1000-1200"
							)),
							new Pair<String, String>("RandomXPBottle", ""),
							new Pair<String, Integer>("Min", 1000),
							new Pair<String, Integer>("Max", 200)
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aMoney Note: $20000-25000$",
							new Pair<String, String>("RandomMoney", ""),
							new Pair<String, Integer>("Min", 20000),
							new Pair<String, Integer>("Max", 5000)
					));
					list.get(rateR).add(this.buider.constructItem(Material.SPAWNER, 1, "&a&lWitch Spawner"));
					list.get(rateR).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aPersonal XP Booster",
							new ArrayList<String>(Arrays.asList(
									"&7You gain &620% &7more player xp for the",
									"&7next 2.0 hours."
							)),
							new Pair<String, String>("Booster", ""),
							new Pair<String, Double>("Increase", 20.0),
							new Pair<String, Long>("Duration", 7200000L),
							new Pair<String, BoosterType>("BoosterType", BoosterType.PERSONAL)
					));
					list.get(rateR).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aFaction XP Booster",
							new ArrayList<String>(Arrays.asList(
									"&7Your faction members  and yourself",
									"&7gain &610% &7more player xp for the",
									"&7next 1.0 hours."
							)),
							new Pair<String, String>("Booster", ""),
							new Pair<String, Double>("Increase", 10.0),
							new Pair<String, Long>("Duration", 3600000L),
							new Pair<String, BoosterType>("BoosterType", BoosterType.FACTION)
					));
					list.get(rateR).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aPersonal Faction Fly Ticket",
							new ArrayList<String>(Arrays.asList(
									"&7You gain the ability to fly in your",
									"&7own faction territory for 1.0 hours."
							)),
							new Pair<String, String>("Fly Ticket", ""),
							new Pair<String, Long>("Duration", 3600000L)
					));
					list.get(rateVR).add(this.buider.constructItem(Material.SPAWNER, 1, "&a&lPig Zombie Spawner"));
					this.monthlyList.put(Rank.GOLD, list);
					break;
				case PLATINUM:
					list = new HashMap<LootTable, ArrayList<ItemStack>>();
					rateC = LootTable.COMMON.setRate(55.00F);
					rateR = LootTable.RARE.setRate(30.0F);
					rateVR = LootTable.VERY_RARE.setRate(15.0F);
					list.put(rateC, new ArrayList<ItemStack>());
					list.put(rateR, new ArrayList<ItemStack>());
					list.put(rateVR, new ArrayList<ItemStack>());
					list.get(rateC).add(this.buider.constructItem(
							Material.NETHER_STAR,
							12,
							"&cLegendary Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &bEpic"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Legendary")
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.NETHER_STAR,
							8,
							"&5Mythic Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &5Mythic"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Mythic")
					));
					list.get(rateC).add(this.buider.constructItem(Material.TNT, 64, "&7256x TNT", new Pair<String, Integer>("Amount", 256)));
					list.get(rateC).add(this.buider.constructItem(
							Material.EXPERIENCE_BOTTLE, 
							1,
							"&a&lXP Bottle (Player)",
							new ArrayList<String>(Arrays.asList(
									"&7When you combine your item with this bottle,",
									"&7It will add the XP on this bottle to your weapon.",
									"&7XP Amount: &6Between 1400-1700"
							)),
							new Pair<String, String>("RandomXPBottle", ""),
							new Pair<String, Integer>("Min", 1400),
							new Pair<String, Integer>("Max", 300)
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aMoney Note: $28000-33000$",
							new Pair<String, String>("RandomMoney", ""),
							new Pair<String, Integer>("Min", 28000),
							new Pair<String, Integer>("Max", 5000)
					));
					list.get(rateR).add(this.buider.constructItem(Material.SPAWNER, 1, "&a&lCreeper Spawner"));
					list.get(rateR).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aPersonal XP Booster",
							new ArrayList<String>(Arrays.asList(
									"&7You gain &625% &7more player xp for the",
									"&7next 2.5 hours."
							)),
							new Pair<String, String>("Booster", ""),
							new Pair<String, Double>("Increase", 25.0),
							new Pair<String, Long>("Duration", 9000000L),
							new Pair<String, BoosterType>("BoosterType", BoosterType.PERSONAL)
					));
					list.get(rateR).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aFaction XP Booster",
							new ArrayList<String>(Arrays.asList(
									"&7Your faction members  and yourself",
									"&7gain &615% &7more player xp for the",
									"&7next 1.5 hours."
							)),
							new Pair<String, String>("Booster", ""),
							new Pair<String, Double>("Increase", 15.0),
							new Pair<String, Long>("Duration", 5400000L),
							new Pair<String, BoosterType>("BoosterType", BoosterType.FACTION)
					));
					list.get(rateR).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aPersonal Faction Fly Ticket",
							new ArrayList<String>(Arrays.asList(
									"&7You gain the ability to fly in your",
									"&7own faction territory for 1.25 hours."
							)),
							new Pair<String, String>("Fly Ticket", ""),
							new Pair<String, Long>("Duration", 4500000L)
					));
					list.get(rateVR).add(this.buider.constructItem(Material.SPAWNER, 1, "&a&lBlaze Spawner"));
					this.monthlyList.put(Rank.PLATINUM, list);
					break;
				case DIAMOND:
					list = new HashMap<LootTable, ArrayList<ItemStack>>();
					rateC = LootTable.COMMON.setRate(43.75F);
					rateR = LootTable.RARE.setRate(30.0F);
					rateVR = LootTable.VERY_RARE.setRate(15.0F);
					LootTable rateSR = LootTable.SUPER_RARE.setRate(7.50F);
					LootTable rateIR = LootTable.INSANELY_RARE.setRate(3.75F);
					list.put(rateC, new ArrayList<ItemStack>());
					list.put(rateR, new ArrayList<ItemStack>());
					list.put(rateVR, new ArrayList<ItemStack>());
					list.put(rateSR, new ArrayList<ItemStack>());
					list.put(rateIR, new ArrayList<ItemStack>());
					list.get(rateC).add(this.buider.constructItem(
							Material.NETHER_STAR,
							18,
							"&cLegendary Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &cLegendary"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Legendary")
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.NETHER_STAR,
							12,
							"&5Mythic Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &5Mythic"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Mythic")
					));
					list.get(rateC).add(this.buider.constructItem(Material.TNT, 64, "&7320x TNT", new Pair<String, Integer>("Amount", 320)));
					list.get(rateC).add(this.buider.constructItem(
							Material.EXPERIENCE_BOTTLE, 
							1,
							"&a&lXP Bottle (Player)",
							new ArrayList<String>(Arrays.asList(
									"&7When you combine your item with this bottle,",
									"&7It will add the XP on this bottle to your weapon.",
									"&7XP Amount: &6Between 2000-2500"
							)),
							new Pair<String, String>("RandomXPBottle", ""),
							new Pair<String, Integer>("Min", 2000),
							new Pair<String, Integer>("Max", 500)
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aMoney Note: $35000-40000$",
							new Pair<String, String>("RandomMoney", ""),
							new Pair<String, Integer>("Min", 35000),
							new Pair<String, Integer>("Max", 5000)
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aPersonal XP Booster",
							new ArrayList<String>(Arrays.asList(
									"&7You gain &630% &7more player xp for the",
									"&7next 3.0 hours."
							)),
							new Pair<String, String>("Booster", ""),
							new Pair<String, Double>("Increase", 30.0),
							new Pair<String, Long>("Duration", 10800000L),
							new Pair<String, BoosterType>("BoosterType", BoosterType.PERSONAL)
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aPersonal Faction Fly Ticket",
							new ArrayList<String>(Arrays.asList(
									"&7You gain the ability to fly in your",
									"&7own faction territory for 1.5 hours."
							)),
							new Pair<String, String>("Fly Ticket", ""),
							new Pair<String, Long>("Duration", 5400000L)
					));
					list.get(rateR).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aFaction XP Booster",
							new ArrayList<String>(Arrays.asList(
									"&7Your faction members  and yourself",
									"&7gain &620% &7more player xp for the",
									"&7next 2.0 hours."
							)),
							new Pair<String, String>("Booster", ""),
							new Pair<String, Double>("Increase", 20.0),
							new Pair<String, Long>("Duration", 7200000L),
							new Pair<String, BoosterType>("BoosterType", BoosterType.FACTION)
					));
					list.get(rateR).add(this.buider.constructItem(Material.SPAWNER, 1, "&a&lEnderman Spawner"));
					list.get(rateSR).add(this.buider.constructItem(
							Material.NETHER_STAR,
							1,
							"&eHeroic Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &eHeroic"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Heroic")
					));
					list.get(rateSR).add(this.buider.constructItem(Material.SPAWNER, 1, "&a&lIron Golem Spawner"));
					list.get(rateIR).add(this.buider.constructItem(
							Material.TRIPWIRE_HOOK,
							1,
							"&4&lRandom Kit Key",
							new ArrayList<String>(Arrays.asList(
								"&7When you right click, you unlock",
								"&7a random kit. (Non donator kits)"
							)),
							new Pair<String, String>("KitKey", "")
					));
					this.monthlyList.put(Rank.DIAMOND, list);
					break;
				case EMERALD:
					list = new HashMap<LootTable, ArrayList<ItemStack>>();
					rateC = LootTable.COMMON.setRate(45.0F);
					rateR = LootTable.RARE.setRate(30.0F);
					rateVR = LootTable.VERY_RARE.setRate(15.0F);
					rateSR = LootTable.SUPER_RARE.setRate(10.0F);
					list.put(rateC, new ArrayList<ItemStack>());
					list.put(rateR, new ArrayList<ItemStack>());
					list.put(rateVR, new ArrayList<ItemStack>());
					list.put(rateSR, new ArrayList<ItemStack>());
					list.get(rateC).add(this.buider.constructItem(
							Material.NETHER_STAR,
							18,
							"&5Mythic Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &5Mythic"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Mythic")
					));
					list.get(rateC).add(this.buider.constructItem(Material.TNT, 64, "&7384x TNT", new Pair<String, Integer>("Amount", 384)));
					list.get(rateC).add(this.buider.constructItem(
							Material.EXPERIENCE_BOTTLE, 
							1,
							"&a&lXP Bottle (Player)",
							new ArrayList<String>(Arrays.asList(
									"&7When you combine your item with this bottle,",
									"&7It will add the XP on this bottle to your weapon.",
									"&7XP Amount: &6Between 2500-3200"
							)),
							new Pair<String, String>("RandomXPBottle", ""),
							new Pair<String, Integer>("Min", 2500),
							new Pair<String, Integer>("Max", 700)
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aMoney Note: $45000-50000$",
							new Pair<String, String>("RandomMoney", ""),
							new Pair<String, Integer>("Min", 45000),
							new Pair<String, Integer>("Max", 5000)
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aPersonal XP Booster",
							new ArrayList<String>(Arrays.asList(
									"&7You gain &635% &7more player xp for the",
									"&7next 3.5 hours."
							)),
							new Pair<String, String>("Booster", ""),
							new Pair<String, Double>("Increase", 35.0),
							new Pair<String, Long>("Duration", 12600000L),
							new Pair<String, BoosterType>("BoosterType", BoosterType.PERSONAL)
					));
					list.get(rateC).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aPersonal Faction Fly Ticket",
							new ArrayList<String>(Arrays.asList(
									"&7You gain the ability to fly in your",
									"&7own faction territory for 2.0 hours."
							)),
							new Pair<String, String>("Fly Ticket", ""),
							new Pair<String, Long>("Duration", 7200000L)
					));
					list.get(rateR).add(this.buider.constructItem(
							Material.PAPER, 
							1,
							"&aFaction XP Booster",
							new ArrayList<String>(Arrays.asList(
									"&7Your faction members  and yourself",
									"&7gain &625% &7more player xp for the",
									"&7next 2.5 hours."
							)),
							new Pair<String, String>("Booster", ""),
							new Pair<String, Double>("Increase", 25.0),
							new Pair<String, Long>("Duration", 9000000L),
							new Pair<String, BoosterType>("BoosterType", BoosterType.FACTION)
					));
					list.get(rateR).add(this.buider.constructItem(Material.SPAWNER, 1, "&a&lIron Golem Spawner"));
					list.get(rateR).add(this.buider.constructItem(
							Material.NETHER_STAR,
							1,
							"&eHeroic Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &eHeroic"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Heroic")
					));
					list.get(rateVR).add(this.buider.constructItem(
							Material.NETHER_STAR,
							3,
							"&eHeroic Crystal",
							new ArrayList<String>(Arrays.asList(
								"&7Bring me to &6&lNOVIS &7to get some",
								"&7really nice rewards!",
								"&7Rarity: &eHeroic"
							)),
							new Pair<String, String>("CrystalObject", ""),
							new Pair<String, String>("Rarity", "Heroic")
					));
					list.get(rateSR).add(this.buider.constructItem(Material.SPAWNER, 1, "&a&lVillager Spawner"));
					list.get(rateSR).add(this.buider.constructItem(
							Material.TRIPWIRE_HOOK,
							1,
							"&4&lRandom Kit Key",
							new ArrayList<String>(Arrays.asList(
								"&7When you right click, you unlock",
								"&7a random kit. (Non donator kits)"
							)),
							new Pair<String, String>("KitKey", "")
					));
					this.monthlyList.put(Rank.EMERALD, list);
					break;
			default:
				break;
			}
		}
	
	
	
	}
	
	public HashMap<Rank, HashMap<LootTable, ArrayList<ItemStack>>> getMonthlyList(){
		return this.monthlyList;
	}
	
	public HashMap<LootTable, ArrayList<ItemStack>> getDailyList(){
		return this.dailyList;
	}
	
	public HashMap<LootTable, ArrayList<ItemStack>> getWeeklyList(){
		return this.weeklyList;
	}
	
}
