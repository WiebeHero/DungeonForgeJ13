package me.WiebeHero.DailyRewards;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;
import me.WiebeHero.RankedPlayerPackage.RankedManager;
import me.WiebeHero.RankedPlayerPackage.RankedPlayer;

public class DailyRewardMenu {
	
	private ItemStackBuilder builder;
	private DailyRewardManager dManager;
	private RankedManager rManager;
	
	public DailyRewardMenu(ItemStackBuilder builder, DailyRewardManager dManager, RankedManager rManager) {
		this.builder = builder;
		this.dManager = dManager;
		this.rManager = rManager;
	}
	
	public void DailyMenu(Player player) {
		if(this.rManager.contains(player.getUniqueId()) && this.dManager.containsDailyReward(player.getUniqueId())) {
			RankedPlayer rPlayer = this.rManager.getRankedPlayer(player.getUniqueId());
			DailyReward reward = this.dManager.getDailyReward(player.getUniqueId());
			long current = System.currentTimeMillis();
			long lastMonthly = reward.getLastMonthly();
			ArrayList<Integer> claimedSlots = reward.getClaimedSlots();
			if(!claimedSlots.isEmpty()) {
				if(current >= lastMonthly) {
					reward.clearClaimedSlots();
				}
			}
			Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&6Daily Rewards")));
			ItemStack gray = this.builder.constructItem(
					Material.GRAY_STAINED_GLASS_PANE,
					1,
					" "
			);
			ItemStack green = this.builder.constructItem(
					Material.LIME_STAINED_GLASS_PANE,
					1,
					" "
			);
			
			for(int x = 0; x < i.getSize(); x++) {
				if(x < 45) {
					i.setItem(x, gray);
				}
				else {
					i.setItem(x, green);
				}
			}
		
			i.setItem(11, this.builder.constructItem(
					this.dManager.getCurrentDailyDate().equals(reward.getLastDaily()) ? Material.MINECART : Material.CHEST_MINECART,
					1,
					this.dManager.getCurrentDailyDate().equals(reward.getLastDaily()) ? "&6Daily Reward &c(Not Available)" : "&6Daily Reward &a(Available)",
					new String[] {
							"&7You can claim a random reward once a day.",
							"&7Click here to claim it!"
					},
					new Pair<String, String>("Daily", "")
			));
			long time = reward.getLastWeekly();
			i.setItem(15, this.builder.constructItem(
					System.currentTimeMillis() < time ? Material.MINECART : Material.CHEST_MINECART,
					1,
					System.currentTimeMillis() < time ? "&6Weekly Reward &c(Not Available)" : "&6Weekly Reward &a(Available)",
					new String[] {
							"&7You can claim a random reward once a week.",
							"&7Click here to claim it!"
					},
					new Pair<String, String>("Weekly", "")
			));
			
			if(rPlayer.getRanks().contains(Rank.BRONZE)) {
				ArrayList<Integer> slots = new ArrayList<Integer>(Arrays.asList(30, 32));
				for(int x = 0; x < slots.size(); x++) {
					i.setItem(slots.get(x), this.builder.constructItem(
							!claimedSlots.contains(slots.get(x)) ? Material.CHEST_MINECART : Material.MINECART,
							1,
							!claimedSlots.contains(slots.get(x)) ? "&aMontly Reward: &6&lBronze &a(Available)" : "&aMontly Reward: &6&lBronze &c(Not Available)",
							new String[] {
									"&71 of the following rewards will be given to",
									"&7you if you click this at the end of",
									"&7each month:",
									"  &712 Common Crystals",
									"  &710 Rare Crystals",
									"  &764x TNT",
									"  &7400-600 XP Bottle",
									"  &710000-12500$",
									"  &7Cow Spawner &a[Rare]",
									"  &7Personal XP Booster (10%) for 1.0 hours &2[Very Rare]",
									"  &7Personal Faction Fly Ticket for 30 minutes &2[Very Rare]"
							},
							new Pair<String, String>("Monthly", "")
					));
				}
			}
			
			else if(rPlayer.getRanks().contains(Rank.SILVER)) {
				ArrayList<Integer> slots = new ArrayList<Integer>(Arrays.asList(30, 32));
				for(int x = 0; x < slots.size(); x++) {
					i.setItem(slots.get(x), this.builder.constructItem(
							!claimedSlots.contains(slots.get(x)) ? Material.CHEST_MINECART : Material.MINECART,
							1,
							!claimedSlots.contains(slots.get(x)) ? "&aMontly Reward: &7&lSilver &a(Available)" : "&aMontly Reward: &7&lSilver &c(Not Available)",
							new String[] {
									"&71 of the following rewards will be given to",
									"&7you if you click this at the end of",
									"&7each month:",
									"  &712 Rare Crystals",
									"  &710 Epic Crystals",
									"  &7128x TNT",
									"  &7700-900 XP Bottle",
									"  &714000-17000$",
									"  &7Spider Spawner &a[Rare]",
									"  &7Personal XP Booster (15%) for 1.5 hours &2[Very Rare]",
									"  &7Personal Faction Fly Ticket for 45 minutes &2[Very Rare]"
							},
							new Pair<String, String>("Monthly", "")
					));
				}
			}
			
			else if(rPlayer.getRanks().contains(Rank.GOLD)) {
				ArrayList<Integer> slots = new ArrayList<Integer>(Arrays.asList(28, 31, 34));
				for(int x = 0; x < slots.size(); x++) {
					i.setItem(slots.get(x), this.builder.constructItem(
							!claimedSlots.contains(slots.get(x)) ? Material.CHEST_MINECART : Material.MINECART,
							1,
							!claimedSlots.contains(slots.get(x)) ? "&aMontly Reward: &e&lGold &a(Available)" : "&aMontly Reward: &e&lGold &c(Not Available)",
							new String[] {
									"&71 of the following rewards will be given to",
									"&7you if you click this at the end of",
									"&7each month:",
									"  &712 Epic Crystals",
									"  &710 Legendary Crystals",
									"  &7192x TNT",
									"  &71000-1200 XP Bottle",
									"  &720000-25000$",
									"  &7Witch Spawner &a[Rare]",
									"  &7Personal XP Booster (20%) for 2.0 hours &a[Rare]",
									"  &7Faction XP Booster (10%) for 1.0 hours &a[Rare]",
									"  &7Personal Faction Fly Ticket for 1.0 hours &a[Rare]",
									"  &7Zombie Pigman Spawner &2[Very Rare]"
							},
							new Pair<String, String>("Monthly", "")
					));
				}
			}
			
			else if(rPlayer.getRanks().contains(Rank.PLATINUM)) {
				ArrayList<Integer> slots = new ArrayList<Integer>(Arrays.asList(28, 31, 34));
				for(int x = 0; x < slots.size(); x++) {
					i.setItem(slots.get(x), this.builder.constructItem(
							!claimedSlots.contains(slots.get(x)) ? Material.CHEST_MINECART : Material.MINECART,
							1,
							!claimedSlots.contains(slots.get(x)) ? "&aMontly Reward: &3&lPlatinum &a(Available)" : "&aMontly Reward: &3&lPlatinum &c(Not Available)",
							new String[] {
									"&71 of the following rewards will be given to",
									"&7you if you click this at the end of",
									"&7each month:",
									"  &712 Legendary Crystals",
									"  &78 Mythic Crystals",
									"  &7256x TNT",
									"  &71400-1700 XP Bottle",
									"  &728000-33000$",
									"  &7Creeper Spawner &a[Rare]",
									"  &7Personal XP Booster (25%) for 2.5 hours &a[Rare]",
									"  &7Faction XP Booster (15%) for 1.5 hours &a[Rare]",
									"  &7Personal Faction Fly Ticket for 1.25 hours &a[Rare]",
									"  &7Blaze Spawner &2[Very Rare]"
							},
							new Pair<String, String>("Monthly", "")
					));
				}
			}
			
			else if(rPlayer.getRanks().contains(Rank.DIAMOND)) {
				ArrayList<Integer> slots = new ArrayList<Integer>(Arrays.asList(28, 30, 32, 34));
				for(int x = 0; x < slots.size(); x++) {
					i.setItem(slots.get(x), this.builder.constructItem(
							!claimedSlots.contains(slots.get(x)) ? Material.CHEST_MINECART : Material.MINECART,
							1,
							!claimedSlots.contains(slots.get(x)) ? "&aMontly Reward: &b&lDiamond &a(Available)" : "&aMontly Reward: &b&lDiamond &c(Not Available)",
							new String[] {
									"&71 of the following rewards will be given to",
									"&7you if you click this at the end of",
									"&7each month:",
									"  &718 Legendary Crystals",
									"  &712 Mythic Crystals",
									"  &7320x TNT",
									"  &72000-2500 XP Bottle",
									"  &735000-40000$",
									"  &7Personal XP Booster (30%) for 3.0 hours",
									"  &7Personal Faction Fly Ticket for 1.5 hours",
									"  &7Faction XP Booster (20%) for 2 hours &a[Rare]",
									"  &7Enderman Spawner &a[Rare]",
									"  &71 Heroic Crystal &b[Super Rare]",
									"  &7Iron Golem Spawner &b[Super Rare]",
									"  &7Random Kit Key &c[Insanely Rare]"
							},
							new Pair<String, String>("Monthly", "")
					));
				}
			}
			
			else if(rPlayer.getRanks().contains(Rank.EMERALD)) {
				ArrayList<Integer> slots = new ArrayList<Integer>(Arrays.asList(28, 30, 32, 34));
				for(int x = 0; x < slots.size(); x++) {
					i.setItem(slots.get(x), this.builder.constructItem(
							!claimedSlots.contains(slots.get(x)) ? Material.CHEST_MINECART : Material.MINECART,
							1,
							!claimedSlots.contains(slots.get(x)) ? "&aMontly Reward: &a&lEmerald &a(Available)" : "&aMontly Reward: &a&lEmerald &c(Not Available)",
							new String[] {
									"&71 of the following rewards will be given to",
									"&7you if you click this at the end of",
									"&7each month:",
									"  &718 Mythic Crystals",
									"  &7384x TNT",
									"  &72500-3200 XP Bottle",
									"  &745000-50000$",
									"  &7Personal XP Booster (35%) for 3.5 hours",
									"  &7Personal Faction Fly Ticket for 2.0 hours",
									"  &7Faction XP Booster (25%) for 2.5 hours &a[Rare]",
									"  &7Iron Golem Spawner &a[Rare]",
									"  &71 Heroic Crystal &a[Rare]",
									"  &73 Heroic Crystals &2[Very Rare]",
									"  &7Villager Spawner &b[Super Rare]",
									"  &7Random Kit Key &b[Super Rare]"
							},
							new Pair<String, String>("Monthly", "")
					));
				}
			}
			
			player.openInventory(i);
		}
	}
}
