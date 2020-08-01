package me.WiebeHero.DailyRewards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DailyRewards.DailyRewardEnum.LootTable;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Rank;
import net.md_5.bungee.api.ChatColor;

public class DailyRewardRoll {
	
	private DailyRewardLootTable lootTable;
	
	public DailyRewardRoll(DailyRewardLootTable lootTable) {
		this.lootTable = lootTable;
	}
	
	public void RewardMonthly(Player player, Rank rank) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Decrypting Monthly Reward...")));
		player.openInventory(i);
		HashMap<Rank, HashMap<LootTable, ArrayList<ItemStack>>> list = this.lootTable.getMonthlyList();
		HashMap<LootTable, ArrayList<ItemStack>> rewards = list.get(rank);
		ArrayList<LootTable> tables = new ArrayList<LootTable>(rewards.keySet());
		ArrayList<ItemStack> lootList = new ArrayList<ItemStack>();
		for(int i1 = 0; i1 <= 39; i1++) {
			float lastChance = 0.00F;
			float currentChance = 0.00F;
			float rand = ThreadLocalRandom.current().nextFloat() * 100;
			for(int x = 0; x < tables.size(); x++) {
				LootTable table = tables.get(x);
				currentChance += table.getRate();
				if(rand < currentChance && rand >= lastChance) {
					int random = new Random().nextInt(rewards.get(table).size());
					lootList.add(rewards.get(table).get(random));
				}
				lastChance += table.getRate();
			}
		}
		ItemStack finalReward = lootList.get(32);
		//--------------------------------------------------------------------------------------------------------------------
		//Inventory Animation
		//--------------------------------------------------------------------------------------------------------------------
		new BukkitRunnable() {
			int counter = 17;
			int count = 0;
			@Override
			public void run() {
				if(player.getOpenInventory().getTitle().contains(ChatColor.stripColor("Decrypting "))) {
					i.setItem(0, emptyVoid(player));
					i.setItem(1, emptyVoid(player));
					i.setItem(2, emptyVoid(player));
					i.setItem(3, emptyVoid(player));
					i.setItem(4, emptyVoid(player));
					i.setItem(5, emptyVoid(player));
					i.setItem(6, emptyVoid(player));
					i.setItem(7, emptyVoid(player));
					i.setItem(8, emptyVoid(player));
					i.setItem(9, lootList.get(count));
					i.setItem(10, lootList.get(count + 1));
					i.setItem(11, lootList.get(count + 2));
					i.setItem(12, lootList.get(count + 3));
					i.setItem(13, lootList.get(count + 4));
					i.setItem(14, lootList.get(count + 5));
					i.setItem(15, lootList.get(count + 6));
					i.setItem(16, lootList.get(count + 7));
					i.setItem(17, lootList.get(count + 8));
					i.setItem(18, emptyVoid(player));
					i.setItem(19, emptyVoid(player));
					i.setItem(20, emptyVoid(player));
					i.setItem(21, emptyVoid(player));
					i.setItem(22, emptyVoid(player));
					i.setItem(23, emptyVoid(player));
					i.setItem(24, emptyVoid(player));
					i.setItem(25, emptyVoid(player));
					i.setItem(26, emptyVoid(player));
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.4F);
					if(!(counter <= 1)) {
						count++;
					}
					counter--;
					if(counter == 0) {
						cancel();
						counter = 9;
						new BukkitRunnable() {
							@Override
							public void run() {
								if(player.getOpenInventory().getTitle().contains(ChatColor.stripColor("Decrypting "))) {
									i.setItem(0, emptyVoid(player));
									i.setItem(1, emptyVoid(player));
									i.setItem(2, emptyVoid(player));
									i.setItem(3, emptyVoid(player));
									i.setItem(4, emptyVoid(player));
									i.setItem(5, emptyVoid(player));
									i.setItem(6, emptyVoid(player));
									i.setItem(7, emptyVoid(player));
									i.setItem(8, emptyVoid(player));
									i.setItem(9, lootList.get(count));
									i.setItem(10, lootList.get(count + 1));
									i.setItem(11, lootList.get(count + 2));
									i.setItem(12, lootList.get(count + 3));
									i.setItem(13, lootList.get(count + 4));
									i.setItem(14, lootList.get(count + 5));
									i.setItem(15, lootList.get(count + 6));
									i.setItem(16, lootList.get(count + 7));
									i.setItem(17, lootList.get(count + 8));
									i.setItem(18, emptyVoid(player));
									i.setItem(19, emptyVoid(player));
									i.setItem(20, emptyVoid(player));
									i.setItem(21, emptyVoid(player));
									i.setItem(22, emptyVoid(player));
									i.setItem(23, emptyVoid(player));
									i.setItem(24, emptyVoid(player));
									i.setItem(25, emptyVoid(player));
									i.setItem(26, emptyVoid(player));
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.6F);
									if(!(counter <= 1)) {
										count++;
									}
									counter--;
									if(counter == 0) {
										cancel();
										counter = 5;
										new BukkitRunnable() {
											@Override
											public void run() {
												if(player.getOpenInventory().getTitle().contains(ChatColor.stripColor("Decrypting "))) {
													i.setItem(0, emptyVoid(player));
													i.setItem(1, emptyVoid(player));
													i.setItem(2, emptyVoid(player));
													i.setItem(3, emptyVoid(player));
													i.setItem(4, emptyVoid(player));
													i.setItem(5, emptyVoid(player));
													i.setItem(6, emptyVoid(player));
													i.setItem(7, emptyVoid(player));
													i.setItem(8, emptyVoid(player));
													i.setItem(9, lootList.get(count));
													i.setItem(10, lootList.get(count + 1));
													i.setItem(11, lootList.get(count + 2));
													i.setItem(12, lootList.get(count + 3));
													i.setItem(13, lootList.get(count + 4));
													i.setItem(14, lootList.get(count + 5));
													i.setItem(15, lootList.get(count + 6));
													i.setItem(16, lootList.get(count + 7));
													i.setItem(17, lootList.get(count + 8));
													i.setItem(18, emptyVoid(player));
													i.setItem(19, emptyVoid(player));
													i.setItem(20, emptyVoid(player));
													i.setItem(21, emptyVoid(player));
													i.setItem(22, emptyVoid(player));
													i.setItem(23, emptyVoid(player));
													i.setItem(24, emptyVoid(player));
													i.setItem(25, emptyVoid(player));
													i.setItem(26, emptyVoid(player));
													player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.8F);
													if(!(counter <= 1)) {
														count++;
													}
													counter--;
													if(counter == 0) {
														cancel();
														new BukkitRunnable() {
															@Override
															public void run() {
																i.setItem(0, emptyVoid(player));
																i.setItem(1, emptyVoid(player));
																i.setItem(2, emptyVoid(player));
																i.setItem(3, emptyVoid(player));
																i.setItem(4, emptyVoid(player));
																i.setItem(5, emptyVoid(player));
																i.setItem(6, emptyVoid(player));
																i.setItem(7, emptyVoid(player));
																i.setItem(8, emptyVoid(player));
																i.setItem(9, lootList.get(count));
																i.setItem(10, lootList.get(count + 1));
																i.setItem(11, lootList.get(count + 2));
																i.setItem(12, lootList.get(count + 3));
																i.setItem(13, lootList.get(count + 4));
																i.setItem(14, lootList.get(count + 5));
																i.setItem(15, lootList.get(count + 6));
																i.setItem(16, lootList.get(count + 7));
																i.setItem(17, lootList.get(count + 8));
																i.setItem(18, emptyVoid(player));
																i.setItem(19, emptyVoid(player));
																i.setItem(20, emptyVoid(player));
																i.setItem(21, emptyVoid(player));
																i.setItem(22, emptyVoid(player));
																i.setItem(23, emptyVoid(player));
																i.setItem(24, emptyVoid(player));
																i.setItem(25, emptyVoid(player));
																i.setItem(26, emptyVoid(player));
																player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 1.0F);
																cancel();
																giveItem(player, finalReward);
															}
														}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 16L);
													}
												}
												else {
													cancel();
													giveItem(player, finalReward);
												}
											}
										}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 12L);
									}
								}
								else {
									cancel();
									giveItem(player, finalReward);
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 6L);
					}				
				}
				else {
					cancel();
					giveItem(player, finalReward);
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 3L);
	}
	
	public void RewardDaily(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Decrypting Daily Reward...")));
		player.openInventory(i);
		HashMap<LootTable, ArrayList<ItemStack>> rewards = this.lootTable.getDailyList();
		ArrayList<LootTable> tables = new ArrayList<LootTable>(rewards.keySet());
		ArrayList<ItemStack> lootList = new ArrayList<ItemStack>();
		for(int i1 = 0; i1 <= 39; i1++) {
			float lastChance = 0.00F;
			float currentChance = 0.00F;
			float rand = ThreadLocalRandom.current().nextFloat() * 100;
			for(int x = 0; x < tables.size(); x++) {
				LootTable table = tables.get(x);
				currentChance += table.getRate();
				if(x == 0) {
					if(rand < currentChance) {
						int random = new Random().nextInt(rewards.get(table).size());
						lootList.add(rewards.get(table).get(random));
					}
				}
				else {
					if(rand < currentChance && rand >= lastChance) {
						int random = new Random().nextInt(rewards.get(table).size());
						lootList.add(rewards.get(table).get(random));
					}
				}
				lastChance += table.getRate();
			}
		}
		ItemStack finalReward = lootList.get(32);
		//--------------------------------------------------------------------------------------------------------------------
		//Inventory Animation
		//--------------------------------------------------------------------------------------------------------------------
		new BukkitRunnable() {
			int counter = 17;
			int count = 0;
			@Override
			public void run() {
				if(player.getOpenInventory().getTitle().contains(ChatColor.stripColor("Decrypting "))) {
					i.setItem(0, emptyVoid(player));
					i.setItem(1, emptyVoid(player));
					i.setItem(2, emptyVoid(player));
					i.setItem(3, emptyVoid(player));
					i.setItem(4, emptyVoid(player));
					i.setItem(5, emptyVoid(player));
					i.setItem(6, emptyVoid(player));
					i.setItem(7, emptyVoid(player));
					i.setItem(8, emptyVoid(player));
					i.setItem(9, lootList.get(count));
					i.setItem(10, lootList.get(count + 1));
					i.setItem(11, lootList.get(count + 2));
					i.setItem(12, lootList.get(count + 3));
					i.setItem(13, lootList.get(count + 4));
					i.setItem(14, lootList.get(count + 5));
					i.setItem(15, lootList.get(count + 6));
					i.setItem(16, lootList.get(count + 7));
					i.setItem(17, lootList.get(count + 8));
					i.setItem(18, emptyVoid(player));
					i.setItem(19, emptyVoid(player));
					i.setItem(20, emptyVoid(player));
					i.setItem(21, emptyVoid(player));
					i.setItem(22, emptyVoid(player));
					i.setItem(23, emptyVoid(player));
					i.setItem(24, emptyVoid(player));
					i.setItem(25, emptyVoid(player));
					i.setItem(26, emptyVoid(player));
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.4F);
					if(!(counter <= 1)) {
						count++;
					}
					counter--;
					if(counter == 0) {
						cancel();
						counter = 9;
						new BukkitRunnable() {
							@Override
							public void run() {
								if(player.getOpenInventory().getTitle().contains(ChatColor.stripColor("Decrypting "))) {
									i.setItem(0, emptyVoid(player));
									i.setItem(1, emptyVoid(player));
									i.setItem(2, emptyVoid(player));
									i.setItem(3, emptyVoid(player));
									i.setItem(4, emptyVoid(player));
									i.setItem(5, emptyVoid(player));
									i.setItem(6, emptyVoid(player));
									i.setItem(7, emptyVoid(player));
									i.setItem(8, emptyVoid(player));
									i.setItem(9, lootList.get(count));
									i.setItem(10, lootList.get(count + 1));
									i.setItem(11, lootList.get(count + 2));
									i.setItem(12, lootList.get(count + 3));
									i.setItem(13, lootList.get(count + 4));
									i.setItem(14, lootList.get(count + 5));
									i.setItem(15, lootList.get(count + 6));
									i.setItem(16, lootList.get(count + 7));
									i.setItem(17, lootList.get(count + 8));
									i.setItem(18, emptyVoid(player));
									i.setItem(19, emptyVoid(player));
									i.setItem(20, emptyVoid(player));
									i.setItem(21, emptyVoid(player));
									i.setItem(22, emptyVoid(player));
									i.setItem(23, emptyVoid(player));
									i.setItem(24, emptyVoid(player));
									i.setItem(25, emptyVoid(player));
									i.setItem(26, emptyVoid(player));
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.6F);
									if(!(counter <= 1)) {
										count++;
									}
									counter--;
									if(counter == 0) {
										cancel();
										counter = 5;
										new BukkitRunnable() {
											@Override
											public void run() {
												if(player.getOpenInventory().getTitle().contains(ChatColor.stripColor("Decrypting "))) {
													i.setItem(0, emptyVoid(player));
													i.setItem(1, emptyVoid(player));
													i.setItem(2, emptyVoid(player));
													i.setItem(3, emptyVoid(player));
													i.setItem(4, emptyVoid(player));
													i.setItem(5, emptyVoid(player));
													i.setItem(6, emptyVoid(player));
													i.setItem(7, emptyVoid(player));
													i.setItem(8, emptyVoid(player));
													i.setItem(9, lootList.get(count));
													i.setItem(10, lootList.get(count + 1));
													i.setItem(11, lootList.get(count + 2));
													i.setItem(12, lootList.get(count + 3));
													i.setItem(13, lootList.get(count + 4));
													i.setItem(14, lootList.get(count + 5));
													i.setItem(15, lootList.get(count + 6));
													i.setItem(16, lootList.get(count + 7));
													i.setItem(17, lootList.get(count + 8));
													i.setItem(18, emptyVoid(player));
													i.setItem(19, emptyVoid(player));
													i.setItem(20, emptyVoid(player));
													i.setItem(21, emptyVoid(player));
													i.setItem(22, emptyVoid(player));
													i.setItem(23, emptyVoid(player));
													i.setItem(24, emptyVoid(player));
													i.setItem(25, emptyVoid(player));
													i.setItem(26, emptyVoid(player));
													player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.8F);
													if(!(counter <= 1)) {
														count++;
													}
													counter--;
													if(counter == 0) {
														cancel();
														new BukkitRunnable() {
															@Override
															public void run() {
																i.setItem(0, emptyVoid(player));
																i.setItem(1, emptyVoid(player));
																i.setItem(2, emptyVoid(player));
																i.setItem(3, emptyVoid(player));
																i.setItem(4, emptyVoid(player));
																i.setItem(5, emptyVoid(player));
																i.setItem(6, emptyVoid(player));
																i.setItem(7, emptyVoid(player));
																i.setItem(8, emptyVoid(player));
																i.setItem(9, lootList.get(count));
																i.setItem(10, lootList.get(count + 1));
																i.setItem(11, lootList.get(count + 2));
																i.setItem(12, lootList.get(count + 3));
																i.setItem(13, lootList.get(count + 4));
																i.setItem(14, lootList.get(count + 5));
																i.setItem(15, lootList.get(count + 6));
																i.setItem(16, lootList.get(count + 7));
																i.setItem(17, lootList.get(count + 8));
																i.setItem(18, emptyVoid(player));
																i.setItem(19, emptyVoid(player));
																i.setItem(20, emptyVoid(player));
																i.setItem(21, emptyVoid(player));
																i.setItem(22, emptyVoid(player));
																i.setItem(23, emptyVoid(player));
																i.setItem(24, emptyVoid(player));
																i.setItem(25, emptyVoid(player));
																i.setItem(26, emptyVoid(player));
																player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 1.0F);
																cancel();
																giveItem(player, finalReward);
															}
														}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 16L);
													}
												}
												else {
													cancel();
													giveItem(player, finalReward);
												}
											}
										}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 12L);
									}
								}
								else {
									cancel();
									giveItem(player, finalReward);
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 6L);
					}				
				}
				else {
					cancel();
					giveItem(player, finalReward);
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 3L);
	}
	
	public void RewardWeekly(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Decrypting Weekly Reward...")));
		player.openInventory(i);
		HashMap<LootTable, ArrayList<ItemStack>> rewards = this.lootTable.getWeeklyList();
		ArrayList<LootTable> tables = new ArrayList<LootTable>(rewards.keySet());
		ArrayList<ItemStack> lootList = new ArrayList<ItemStack>();
		for(int i1 = 0; i1 <= 39; i1++) {
			float lastChance = 0.00F;
			float currentChance = 0.00F;
			float rand = ThreadLocalRandom.current().nextFloat() * 100;
			for(int x = 0; x < tables.size(); x++) {
				LootTable table = tables.get(x);
				currentChance += table.getRate();
				if(x == 0) {
					if(rand < currentChance) {
						int random = new Random().nextInt(rewards.get(table).size());
						lootList.add(rewards.get(table).get(random));
					}
				}
				else {
					if(rand < currentChance && rand >= lastChance) {
						int random = new Random().nextInt(rewards.get(table).size());
						lootList.add(rewards.get(table).get(random));
					}
				}
				lastChance += table.getRate();
			}
		}
		ItemStack finalReward = lootList.get(32);
		//--------------------------------------------------------------------------------------------------------------------
		//Inventory Animation
		//--------------------------------------------------------------------------------------------------------------------
		new BukkitRunnable() {
			int counter = 17;
			int count = 0;
			@Override
			public void run() {
				if(player.getOpenInventory().getTitle().contains(ChatColor.stripColor("Decrypting "))) {
					i.setItem(0, emptyVoid(player));
					i.setItem(1, emptyVoid(player));
					i.setItem(2, emptyVoid(player));
					i.setItem(3, emptyVoid(player));
					i.setItem(4, emptyVoid(player));
					i.setItem(5, emptyVoid(player));
					i.setItem(6, emptyVoid(player));
					i.setItem(7, emptyVoid(player));
					i.setItem(8, emptyVoid(player));
					i.setItem(9, lootList.get(count));
					i.setItem(10, lootList.get(count + 1));
					i.setItem(11, lootList.get(count + 2));
					i.setItem(12, lootList.get(count + 3));
					i.setItem(13, lootList.get(count + 4));
					i.setItem(14, lootList.get(count + 5));
					i.setItem(15, lootList.get(count + 6));
					i.setItem(16, lootList.get(count + 7));
					i.setItem(17, lootList.get(count + 8));
					i.setItem(18, emptyVoid(player));
					i.setItem(19, emptyVoid(player));
					i.setItem(20, emptyVoid(player));
					i.setItem(21, emptyVoid(player));
					i.setItem(22, emptyVoid(player));
					i.setItem(23, emptyVoid(player));
					i.setItem(24, emptyVoid(player));
					i.setItem(25, emptyVoid(player));
					i.setItem(26, emptyVoid(player));
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.4F);
					if(!(counter <= 1)) {
						count++;
					}
					counter--;
					if(counter == 0) {
						cancel();
						counter = 9;
						new BukkitRunnable() {
							@Override
							public void run() {
								if(player.getOpenInventory().getTitle().contains(ChatColor.stripColor("Decrypting "))) {
									i.setItem(0, emptyVoid(player));
									i.setItem(1, emptyVoid(player));
									i.setItem(2, emptyVoid(player));
									i.setItem(3, emptyVoid(player));
									i.setItem(4, emptyVoid(player));
									i.setItem(5, emptyVoid(player));
									i.setItem(6, emptyVoid(player));
									i.setItem(7, emptyVoid(player));
									i.setItem(8, emptyVoid(player));
									i.setItem(9, lootList.get(count));
									i.setItem(10, lootList.get(count + 1));
									i.setItem(11, lootList.get(count + 2));
									i.setItem(12, lootList.get(count + 3));
									i.setItem(13, lootList.get(count + 4));
									i.setItem(14, lootList.get(count + 5));
									i.setItem(15, lootList.get(count + 6));
									i.setItem(16, lootList.get(count + 7));
									i.setItem(17, lootList.get(count + 8));
									i.setItem(18, emptyVoid(player));
									i.setItem(19, emptyVoid(player));
									i.setItem(20, emptyVoid(player));
									i.setItem(21, emptyVoid(player));
									i.setItem(22, emptyVoid(player));
									i.setItem(23, emptyVoid(player));
									i.setItem(24, emptyVoid(player));
									i.setItem(25, emptyVoid(player));
									i.setItem(26, emptyVoid(player));
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.6F);
									if(!(counter <= 1)) {
										count++;
									}
									counter--;
									if(counter == 0) {
										cancel();
										counter = 5;
										new BukkitRunnable() {
											@Override
											public void run() {
												if(player.getOpenInventory().getTitle().contains(ChatColor.stripColor("Decrypting "))) {
													i.setItem(0, emptyVoid(player));
													i.setItem(1, emptyVoid(player));
													i.setItem(2, emptyVoid(player));
													i.setItem(3, emptyVoid(player));
													i.setItem(4, emptyVoid(player));
													i.setItem(5, emptyVoid(player));
													i.setItem(6, emptyVoid(player));
													i.setItem(7, emptyVoid(player));
													i.setItem(8, emptyVoid(player));
													i.setItem(9, lootList.get(count));
													i.setItem(10, lootList.get(count + 1));
													i.setItem(11, lootList.get(count + 2));
													i.setItem(12, lootList.get(count + 3));
													i.setItem(13, lootList.get(count + 4));
													i.setItem(14, lootList.get(count + 5));
													i.setItem(15, lootList.get(count + 6));
													i.setItem(16, lootList.get(count + 7));
													i.setItem(17, lootList.get(count + 8));
													i.setItem(18, emptyVoid(player));
													i.setItem(19, emptyVoid(player));
													i.setItem(20, emptyVoid(player));
													i.setItem(21, emptyVoid(player));
													i.setItem(22, emptyVoid(player));
													i.setItem(23, emptyVoid(player));
													i.setItem(24, emptyVoid(player));
													i.setItem(25, emptyVoid(player));
													i.setItem(26, emptyVoid(player));
													player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.8F);
													if(!(counter <= 1)) {
														count++;
													}
													counter--;
													if(counter == 0) {
														cancel();
														new BukkitRunnable() {
															@Override
															public void run() {
																i.setItem(0, emptyVoid(player));
																i.setItem(1, emptyVoid(player));
																i.setItem(2, emptyVoid(player));
																i.setItem(3, emptyVoid(player));
																i.setItem(4, emptyVoid(player));
																i.setItem(5, emptyVoid(player));
																i.setItem(6, emptyVoid(player));
																i.setItem(7, emptyVoid(player));
																i.setItem(8, emptyVoid(player));
																i.setItem(9, lootList.get(count));
																i.setItem(10, lootList.get(count + 1));
																i.setItem(11, lootList.get(count + 2));
																i.setItem(12, lootList.get(count + 3));
																i.setItem(13, lootList.get(count + 4));
																i.setItem(14, lootList.get(count + 5));
																i.setItem(15, lootList.get(count + 6));
																i.setItem(16, lootList.get(count + 7));
																i.setItem(17, lootList.get(count + 8));
																i.setItem(18, emptyVoid(player));
																i.setItem(19, emptyVoid(player));
																i.setItem(20, emptyVoid(player));
																i.setItem(21, emptyVoid(player));
																i.setItem(22, emptyVoid(player));
																i.setItem(23, emptyVoid(player));
																i.setItem(24, emptyVoid(player));
																i.setItem(25, emptyVoid(player));
																i.setItem(26, emptyVoid(player));
																player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 1.0F);
																cancel();
																giveItem(player, finalReward);
															}
														}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 16L);
													}
												}
												else {
													cancel();
													giveItem(player, finalReward);
												}
											}
										}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 12L);
									}
								}
								else {
									cancel();
									giveItem(player, finalReward);
								}
							}
						}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 6L);
					}				
				}
				else {
					cancel();
					giveItem(player, finalReward);
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 3L);
	}
	
	public void giveItem(Player player, ItemStack item) {
		if(item.getType() == Material.TNT) {
			NBTItem i = new NBTItem(item);
			if(i.hasKey("Amount")) {
				int amount = i.getInteger("Amount");
				int loop = i.getInteger("Amount") / 64;
				ItemStack newItem = new ItemStack(Material.TNT, amount);
				for(int x = 0; x < loop; x++) {
					if(amount > 64) {
						newItem.setAmount(64);
						amount -= 64;
						if(player.getInventory().firstEmpty() != -1) {
							player.getInventory().setItem(player.getInventory().firstEmpty(), newItem);
						}
						else {
							player.getWorld().dropItem(player.getLocation(), item);
						}
					}
					else {
						newItem.setAmount(amount);
						if(player.getInventory().firstEmpty() != -1) {
							player.getInventory().setItem(player.getInventory().firstEmpty(), newItem);
						}
						else {
							player.getWorld().dropItem(player.getLocation(), item);
						}
					}
				}
			}
		}
		else {
			if(player.getInventory().firstEmpty() != -1) {
				player.getInventory().setItem(player.getInventory().firstEmpty(), item);
			}
			else {
				player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cThere is not enough space in your inventory, so the item has been dropped on the ground."));
			}
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	//Random GUI Color
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack emptyVoid(Player player) {
		ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(" ");
		item.setItemMeta(itemmeta);
		return item;
	}
}
