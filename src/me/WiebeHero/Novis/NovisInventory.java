package me.WiebeHero.Novis;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class NovisInventory extends NovisRewards implements Listener{
	public Set<String> check = new HashSet<String>();
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	BukkitTask bt1;
	BukkitTask bt2;
	BukkitTask bt3;
	BukkitTask bt4;
	//--------------------------------------------------------------------------------------------------------------------
	//
	//
	//Common
	//
	//
	//--------------------------------------------------------------------------------------------------------------------
	public void NewInventory1(Player player) {
		Inventory i = plugin.getServer().createInventory(null, 27, (new ColorCodeTranslator().colorize("&6Decrypting Crystal...")));
		player.openInventory(i);
		check.add(player.getName());
		//--------------------------------------------------------------------------------------------------------------------
		//Inventory Animation
		//--------------------------------------------------------------------------------------------------------------------
		bt1 = new BukkitRunnable() {
			int counter = 17;
			@Override
			public void run() {
				int finalRewards = new Random().nextInt(NovisRewards.rewards1.size());
				if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
					i.setItem(0, emptyVoid(player));
					i.setItem(1, emptyVoid(player));
					i.setItem(2, emptyVoid(player));
					i.setItem(3, emptyVoid(player));
					i.setItem(4, emptyVoid(player));
					i.setItem(5, emptyVoid(player));
					i.setItem(6, emptyVoid(player));
					i.setItem(7, emptyVoid(player));
					i.setItem(8, emptyVoid(player));
					i.setItem(9, emptyVoid(player));
					i.setItem(10, rewards1.get(rewards1()));
					i.setItem(11, rewards1.get(rewards1()));
					i.setItem(12, rewards1.get(rewards1()));
					i.setItem(13, rewards1.get(rewards1()));
					i.setItem(14, rewards1.get(rewards1()));
					i.setItem(15, rewards1.get(rewards1()));
					i.setItem(16, rewards1.get(rewards1()));
					i.setItem(17, emptyVoid(player));
					i.setItem(18, emptyVoid(player));
					i.setItem(19, emptyVoid(player));
					i.setItem(20, emptyVoid(player));
					i.setItem(21, emptyVoid(player));
					i.setItem(22, emptyVoid(player));
					i.setItem(23, emptyVoid(player));
					i.setItem(24, emptyVoid(player));
					i.setItem(25, emptyVoid(player));
					i.setItem(26, emptyVoid(player));
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.4);
					}
					counter--;
					if(counter == 0) {
						cancel();
						counter = 9;
						bt2 = new BukkitRunnable() {
							@Override
							public void run() {
								if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
									i.setItem(0, emptyVoid(player));
									i.setItem(1, emptyVoid(player));
									i.setItem(2, emptyVoid(player));
									i.setItem(3, emptyVoid(player));
									i.setItem(4, emptyVoid(player));
									i.setItem(5, emptyVoid(player));
									i.setItem(6, emptyVoid(player));
									i.setItem(7, emptyVoid(player));
									i.setItem(8, emptyVoid(player));
									i.setItem(9, emptyVoid(player));
									i.setItem(10, emptyVoid(player));
									i.setItem(11, rewards1.get(rewards1()));
									i.setItem(12, rewards1.get(rewards1()));
									i.setItem(13, rewards1.get(rewards1()));
									i.setItem(14, rewards1.get(rewards1()));
									i.setItem(15, rewards1.get(rewards1()));
									i.setItem(16, emptyVoid(player));
									i.setItem(17, emptyVoid(player));
									i.setItem(18, emptyVoid(player));
									i.setItem(19, emptyVoid(player));
									i.setItem(20, emptyVoid(player));
									i.setItem(21, emptyVoid(player));
									i.setItem(23, emptyVoid(player));
									i.setItem(24, emptyVoid(player));
									i.setItem(25, emptyVoid(player));
									i.setItem(26, emptyVoid(player));
									for(Player victim1 : Bukkit.getOnlinePlayers()) {
										((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.6);
									}
									counter--;
									if(counter == 0) {
										cancel();
										counter = 5;
										bt3 = new BukkitRunnable() {
											@Override
											public void run() {
												if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
													i.setItem(0, emptyVoid(player));
													i.setItem(1, emptyVoid(player));
													i.setItem(2, emptyVoid(player));
													i.setItem(3, emptyVoid(player));
													i.setItem(4, emptyVoid(player));
													i.setItem(5, emptyVoid(player));
													i.setItem(6, emptyVoid(player));
													i.setItem(7, emptyVoid(player));
													i.setItem(8, emptyVoid(player));
													i.setItem(9, emptyVoid(player));
													i.setItem(10, emptyVoid(player));
													i.setItem(11, emptyVoid(player));
													i.setItem(12, rewards1.get(rewards1()));
													i.setItem(13, rewards1.get(rewards1()));
													i.setItem(14, rewards1.get(rewards1()));
													i.setItem(15, emptyVoid(player));
													i.setItem(16, emptyVoid(player));
													i.setItem(17, emptyVoid(player));
													i.setItem(18, emptyVoid(player));
													i.setItem(19, emptyVoid(player));
													i.setItem(20, emptyVoid(player));
													i.setItem(21, emptyVoid(player));
													i.setItem(22, emptyVoid(player));
													i.setItem(23, emptyVoid(player));
													i.setItem(24, emptyVoid(player));
													i.setItem(25, emptyVoid(player));
													i.setItem(26, emptyVoid(player));
													for(Player victim1 : Bukkit.getOnlinePlayers()) {
														((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.8);
													}
													counter--;
													if(counter == 0) {
														cancel();
														bt4 = new BukkitRunnable() {
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
																i.setItem(9, emptyVoid(player));
																i.setItem(10, emptyVoid(player));
																i.setItem(11, emptyVoid(player));
																i.setItem(12, emptyVoid(player));
																i.setItem(13, rewards1.get(finalRewards));
																i.setItem(14, emptyVoid(player));
																i.setItem(15, emptyVoid(player));
																i.setItem(16, emptyVoid(player));
																i.setItem(17, emptyVoid(player));
																i.setItem(18, emptyVoid(player));
																i.setItem(19, emptyVoid(player));
																i.setItem(20, emptyVoid(player));
																i.setItem(21, emptyVoid(player));
																i.setItem(22, emptyVoid(player));
																i.setItem(23, emptyVoid(player));
																i.setItem(24, emptyVoid(player));
																i.setItem(25, emptyVoid(player));
																i.setItem(26, emptyVoid(player));
																for(Player victim1 : Bukkit.getOnlinePlayers()) {
																	((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1);
																}
																cancel();
																player.getInventory().addItem(rewards1.get(finalRewards));
																new BukkitRunnable() {
																	@Override
																	public void run() {
																		if(player.getInventory().getName().equals(ChatColor.stripColor("Decrypting..."))) {
																			player.closeInventory();
																		}
																	}
																}.runTaskLater(plugin, 0L);
															}
														}.runTaskTimer(plugin, 0L, 16L);
													}
												}
												else {
													cancel();
													player.getInventory().addItem(rewards1.get(finalRewards));
												}
											}
										}.runTaskTimer(plugin, 0L, 12L);
									}
								}
								else {
									cancel();
									player.getInventory().addItem(rewards1.get(finalRewards));
								}
							}
						}.runTaskTimer(plugin, 0L, 6L);
					}				
				}
				else {
					cancel();
					player.getInventory().addItem(rewards1.get(finalRewards));
				}
			}
		}.runTaskTimer(plugin, 0L, 3L);
	}
	//--------------------------------------------------------------------------------------------------------------------
	//
	//
	//Rare
	//
	//
	//--------------------------------------------------------------------------------------------------------------------
	public void NewInventory2(Player player) {
		Inventory i = plugin.getServer().createInventory(null, 27, (new ColorCodeTranslator().colorize("&6Decrypting Crystal...")));
		player.openInventory(i);
		check.add(player.getName());
		//--------------------------------------------------------------------------------------------------------------------
		//Inventory Animation
		//--------------------------------------------------------------------------------------------------------------------
		bt1 = new BukkitRunnable() {
			int counter = 17;
			@Override
			public void run() {
				int finalRewards = new Random().nextInt(NovisRewards.rewards2.size());
				if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
					i.setItem(0, emptyVoid(player));
					i.setItem(1, emptyVoid(player));
					i.setItem(2, emptyVoid(player));
					i.setItem(3, emptyVoid(player));
					i.setItem(4, emptyVoid(player));
					i.setItem(5, emptyVoid(player));
					i.setItem(6, emptyVoid(player));
					i.setItem(7, emptyVoid(player));
					i.setItem(8, emptyVoid(player));
					i.setItem(9, emptyVoid(player));
					i.setItem(10, rewards2.get(rewards2()));
					i.setItem(11, rewards2.get(rewards2()));
					i.setItem(12, rewards2.get(rewards2()));
					i.setItem(13, rewards2.get(rewards2()));
					i.setItem(14, rewards2.get(rewards2()));
					i.setItem(15, rewards2.get(rewards2()));
					i.setItem(16, rewards2.get(rewards2()));
					i.setItem(17, emptyVoid(player));
					i.setItem(18, emptyVoid(player));
					i.setItem(19, emptyVoid(player));
					i.setItem(20, emptyVoid(player));
					i.setItem(21, emptyVoid(player));
					i.setItem(22, emptyVoid(player));
					i.setItem(23, emptyVoid(player));
					i.setItem(24, emptyVoid(player));
					i.setItem(25, emptyVoid(player));
					i.setItem(26, emptyVoid(player));
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.4);
					}
					counter--;
					if(counter == 0) {
						cancel();
						counter = 9;
						bt2 = new BukkitRunnable() {
							@Override
							public void run() {
								if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
									i.setItem(0, emptyVoid(player));
									i.setItem(1, emptyVoid(player));
									i.setItem(2, emptyVoid(player));
									i.setItem(3, emptyVoid(player));
									i.setItem(4, emptyVoid(player));
									i.setItem(5, emptyVoid(player));
									i.setItem(6, emptyVoid(player));
									i.setItem(7, emptyVoid(player));
									i.setItem(8, emptyVoid(player));
									i.setItem(9, emptyVoid(player));
									i.setItem(10, emptyVoid(player));
									i.setItem(11, rewards2.get(rewards2()));
									i.setItem(12, rewards2.get(rewards2()));
									i.setItem(13, rewards2.get(rewards2()));
									i.setItem(14, rewards2.get(rewards2()));
									i.setItem(15, rewards2.get(rewards2()));
									i.setItem(16, emptyVoid(player));
									i.setItem(17, emptyVoid(player));
									i.setItem(18, emptyVoid(player));
									i.setItem(19, emptyVoid(player));
									i.setItem(20, emptyVoid(player));
									i.setItem(21, emptyVoid(player));
									i.setItem(23, emptyVoid(player));
									i.setItem(24, emptyVoid(player));
									i.setItem(25, emptyVoid(player));
									i.setItem(26, emptyVoid(player));
									for(Player victim1 : Bukkit.getOnlinePlayers()) {
										((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.6);
									}
									counter--;
									if(counter == 0) {
										cancel();
										counter = 5;
										bt3 = new BukkitRunnable() {
											@Override
											public void run() {
												if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
													i.setItem(0, emptyVoid(player));
													i.setItem(1, emptyVoid(player));
													i.setItem(2, emptyVoid(player));
													i.setItem(3, emptyVoid(player));
													i.setItem(4, emptyVoid(player));
													i.setItem(5, emptyVoid(player));
													i.setItem(6, emptyVoid(player));
													i.setItem(7, emptyVoid(player));
													i.setItem(8, emptyVoid(player));
													i.setItem(9, emptyVoid(player));
													i.setItem(10, emptyVoid(player));
													i.setItem(11, emptyVoid(player));
													i.setItem(12, rewards2.get(rewards2()));
													i.setItem(13, rewards2.get(rewards2()));
													i.setItem(14, rewards2.get(rewards2()));
													i.setItem(15, emptyVoid(player));
													i.setItem(16, emptyVoid(player));
													i.setItem(17, emptyVoid(player));
													i.setItem(18, emptyVoid(player));
													i.setItem(19, emptyVoid(player));
													i.setItem(20, emptyVoid(player));
													i.setItem(21, emptyVoid(player));
													i.setItem(22, emptyVoid(player));
													i.setItem(23, emptyVoid(player));
													i.setItem(24, emptyVoid(player));
													i.setItem(25, emptyVoid(player));
													i.setItem(26, emptyVoid(player));
													for(Player victim1 : Bukkit.getOnlinePlayers()) {
														((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.8);
													}
													counter--;
													if(counter == 0) {
														cancel();
														bt4 = new BukkitRunnable() {
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
																i.setItem(9, emptyVoid(player));
																i.setItem(10, emptyVoid(player));
																i.setItem(11, emptyVoid(player));
																i.setItem(12, emptyVoid(player));
																i.setItem(13, rewards2.get(finalRewards));
																i.setItem(14, emptyVoid(player));
																i.setItem(15, emptyVoid(player));
																i.setItem(16, emptyVoid(player));
																i.setItem(17, emptyVoid(player));
																i.setItem(18, emptyVoid(player));
																i.setItem(19, emptyVoid(player));
																i.setItem(20, emptyVoid(player));
																i.setItem(21, emptyVoid(player));
																i.setItem(22, emptyVoid(player));
																i.setItem(23, emptyVoid(player));
																i.setItem(24, emptyVoid(player));
																i.setItem(25, emptyVoid(player));
																i.setItem(26, emptyVoid(player));
																for(Player victim1 : Bukkit.getOnlinePlayers()) {
																	((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1);
																}
																cancel();
																player.getInventory().addItem(rewards2.get(finalRewards));
																new BukkitRunnable() {
																	@Override
																	public void run() {
																		if(player.getInventory().getName().equals(ChatColor.stripColor("Decrypting..."))) {
																			player.closeInventory();
																		}
																	}
																}.runTaskLater(plugin, 0L);
															}
														}.runTaskTimer(plugin, 0L, 16L);
													}
												}
												else {
													cancel();
													player.getInventory().addItem(rewards2.get(finalRewards));
												}
											}
										}.runTaskTimer(plugin, 0L, 12L);
									}
								}
								else {
									cancel();
									player.getInventory().addItem(rewards2.get(finalRewards));
								}
							}
						}.runTaskTimer(plugin, 0L, 6L);
					}				
				}
				else {
					cancel();
					player.getInventory().addItem(rewards2.get(finalRewards));
				}
			}
		}.runTaskTimer(plugin, 0L, 3L);
	}
	//--------------------------------------------------------------------------------------------------------------------
	//
	//
	//Rare
	//
	//
	//--------------------------------------------------------------------------------------------------------------------
	public void NewInventory3(Player player) {
		Inventory i = plugin.getServer().createInventory(null, 27, (new ColorCodeTranslator().colorize("&6Decrypting Crystal...")));
		player.openInventory(i);
		check.add(player.getName());
		//--------------------------------------------------------------------------------------------------------------------
		//Inventory Animation
		//--------------------------------------------------------------------------------------------------------------------
		bt1 = new BukkitRunnable() {
			int counter = 17;
			@Override
			public void run() {
				int finalRewards = new Random().nextInt(NovisRewards.rewards3.size());
				if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
					i.setItem(0, emptyVoid(player));
					i.setItem(1, emptyVoid(player));
					i.setItem(2, emptyVoid(player));
					i.setItem(3, emptyVoid(player));
					i.setItem(4, emptyVoid(player));
					i.setItem(5, emptyVoid(player));
					i.setItem(6, emptyVoid(player));
					i.setItem(7, emptyVoid(player));
					i.setItem(8, emptyVoid(player));
					i.setItem(9, emptyVoid(player));
					i.setItem(10, rewards3.get(rewards3()));
					i.setItem(11, rewards3.get(rewards3()));
					i.setItem(12, rewards3.get(rewards3()));
					i.setItem(13, rewards3.get(rewards3()));
					i.setItem(14, rewards3.get(rewards3()));
					i.setItem(15, rewards3.get(rewards3()));
					i.setItem(16, rewards3.get(rewards3()));
					i.setItem(17, emptyVoid(player));
					i.setItem(18, emptyVoid(player));
					i.setItem(19, emptyVoid(player));
					i.setItem(20, emptyVoid(player));
					i.setItem(21, emptyVoid(player));
					i.setItem(22, emptyVoid(player));
					i.setItem(23, emptyVoid(player));
					i.setItem(24, emptyVoid(player));
					i.setItem(25, emptyVoid(player));
					i.setItem(26, emptyVoid(player));
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.4);
					}
					counter--;
					if(counter == 0) {
						cancel();
						counter = 9;
						bt2 = new BukkitRunnable() {
							@Override
							public void run() {
								if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
									i.setItem(0, emptyVoid(player));
									i.setItem(1, emptyVoid(player));
									i.setItem(2, emptyVoid(player));
									i.setItem(3, emptyVoid(player));
									i.setItem(4, emptyVoid(player));
									i.setItem(5, emptyVoid(player));
									i.setItem(6, emptyVoid(player));
									i.setItem(7, emptyVoid(player));
									i.setItem(8, emptyVoid(player));
									i.setItem(9, emptyVoid(player));
									i.setItem(10, emptyVoid(player));
									i.setItem(11, rewards3.get(rewards3()));
									i.setItem(12, rewards3.get(rewards3()));
									i.setItem(13, rewards3.get(rewards3()));
									i.setItem(14, rewards3.get(rewards3()));
									i.setItem(15, rewards3.get(rewards3()));
									i.setItem(16, emptyVoid(player));
									i.setItem(17, emptyVoid(player));
									i.setItem(18, emptyVoid(player));
									i.setItem(19, emptyVoid(player));
									i.setItem(20, emptyVoid(player));
									i.setItem(21, emptyVoid(player));
									i.setItem(23, emptyVoid(player));
									i.setItem(24, emptyVoid(player));
									i.setItem(25, emptyVoid(player));
									i.setItem(26, emptyVoid(player));
									for(Player victim1 : Bukkit.getOnlinePlayers()) {
										((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.6);
									}
									counter--;
									if(counter == 0) {
										cancel();
										counter = 5;
										bt3 = new BukkitRunnable() {
											@Override
											public void run() {
												if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
													i.setItem(0, emptyVoid(player));
													i.setItem(1, emptyVoid(player));
													i.setItem(2, emptyVoid(player));
													i.setItem(3, emptyVoid(player));
													i.setItem(4, emptyVoid(player));
													i.setItem(5, emptyVoid(player));
													i.setItem(6, emptyVoid(player));
													i.setItem(7, emptyVoid(player));
													i.setItem(8, emptyVoid(player));
													i.setItem(9, emptyVoid(player));
													i.setItem(10, emptyVoid(player));
													i.setItem(11, emptyVoid(player));
													i.setItem(12, rewards3.get(rewards3()));
													i.setItem(13, rewards3.get(rewards3()));
													i.setItem(14, rewards3.get(rewards3()));
													i.setItem(15, emptyVoid(player));
													i.setItem(16, emptyVoid(player));
													i.setItem(17, emptyVoid(player));
													i.setItem(18, emptyVoid(player));
													i.setItem(19, emptyVoid(player));
													i.setItem(20, emptyVoid(player));
													i.setItem(21, emptyVoid(player));
													i.setItem(22, emptyVoid(player));
													i.setItem(23, emptyVoid(player));
													i.setItem(24, emptyVoid(player));
													i.setItem(25, emptyVoid(player));
													i.setItem(26, emptyVoid(player));
													for(Player victim1 : Bukkit.getOnlinePlayers()) {
														((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.8);
													}
													counter--;
													if(counter == 0) {
														cancel();
														bt4 = new BukkitRunnable() {
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
																i.setItem(9, emptyVoid(player));
																i.setItem(10, emptyVoid(player));
																i.setItem(11, emptyVoid(player));
																i.setItem(12, emptyVoid(player));
																i.setItem(13, rewards3.get(finalRewards));
																i.setItem(14, emptyVoid(player));
																i.setItem(15, emptyVoid(player));
																i.setItem(16, emptyVoid(player));
																i.setItem(17, emptyVoid(player));
																i.setItem(18, emptyVoid(player));
																i.setItem(19, emptyVoid(player));
																i.setItem(20, emptyVoid(player));
																i.setItem(21, emptyVoid(player));
																i.setItem(22, emptyVoid(player));
																i.setItem(23, emptyVoid(player));
																i.setItem(24, emptyVoid(player));
																i.setItem(25, emptyVoid(player));
																i.setItem(26, emptyVoid(player));
																for(Player victim1 : Bukkit.getOnlinePlayers()) {
																	((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1);
																}
																cancel();
																player.getInventory().addItem(rewards3.get(finalRewards));
																new BukkitRunnable() {
																	@Override
																	public void run() {
																		if(player.getInventory().getName().equals(ChatColor.stripColor("Decrypting..."))) {
																			player.closeInventory();
																		}
																	}
																}.runTaskLater(plugin, 0L);
															}
														}.runTaskTimer(plugin, 0L, 16L);
													}
												}
												else {
													cancel();
													player.getInventory().addItem(rewards3.get(finalRewards));
												}
											}
										}.runTaskTimer(plugin, 0L, 12L);
									}
								}
								else {
									cancel();
									player.getInventory().addItem(rewards3.get(finalRewards));
								}
							}
						}.runTaskTimer(plugin, 0L, 6L);
					}				
				}
				else {
					cancel();
					player.getInventory().addItem(rewards3.get(finalRewards));
				}
			}
		}.runTaskTimer(plugin, 0L, 3L);
	}
	//--------------------------------------------------------------------------------------------------------------------
	//
	//
	//Legendary
	//
	//
	//--------------------------------------------------------------------------------------------------------------------
	public void NewInventory4(Player player) {
		Inventory i = plugin.getServer().createInventory(null, 27, (new ColorCodeTranslator().colorize("&6Decrypting Crystal...")));
		player.openInventory(i);
		check.add(player.getName());
		//--------------------------------------------------------------------------------------------------------------------
		//Inventory Animation
		//--------------------------------------------------------------------------------------------------------------------
		bt1 = new BukkitRunnable() {
			int counter = 17;
			@Override
			public void run() {
				int finalRewards = new Random().nextInt(NovisRewards.rewards4.size());
				if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
					i.setItem(0, emptyVoid(player));
					i.setItem(1, emptyVoid(player));
					i.setItem(2, emptyVoid(player));
					i.setItem(3, emptyVoid(player));
					i.setItem(4, emptyVoid(player));
					i.setItem(5, emptyVoid(player));
					i.setItem(6, emptyVoid(player));
					i.setItem(7, emptyVoid(player));
					i.setItem(8, emptyVoid(player));
					i.setItem(9, emptyVoid(player));
					i.setItem(10, rewards4.get(rewards4()));
					i.setItem(11, rewards4.get(rewards4()));
					i.setItem(12, rewards4.get(rewards4()));
					i.setItem(13, rewards4.get(rewards4()));
					i.setItem(14, rewards4.get(rewards4()));
					i.setItem(15, rewards4.get(rewards4()));
					i.setItem(16, rewards4.get(rewards4()));
					i.setItem(17, emptyVoid(player));
					i.setItem(18, emptyVoid(player));
					i.setItem(19, emptyVoid(player));
					i.setItem(20, emptyVoid(player));
					i.setItem(21, emptyVoid(player));
					i.setItem(22, emptyVoid(player));
					i.setItem(23, emptyVoid(player));
					i.setItem(24, emptyVoid(player));
					i.setItem(25, emptyVoid(player));
					i.setItem(26, emptyVoid(player));
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.4);
					}
					counter--;
					if(counter == 0) {
						cancel();
						counter = 9;
						bt2 = new BukkitRunnable() {
							@Override
							public void run() {
								if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
									i.setItem(0, emptyVoid(player));
									i.setItem(1, emptyVoid(player));
									i.setItem(2, emptyVoid(player));
									i.setItem(3, emptyVoid(player));
									i.setItem(4, emptyVoid(player));
									i.setItem(5, emptyVoid(player));
									i.setItem(6, emptyVoid(player));
									i.setItem(7, emptyVoid(player));
									i.setItem(8, emptyVoid(player));
									i.setItem(9, emptyVoid(player));
									i.setItem(10, emptyVoid(player));
									i.setItem(11, rewards4.get(rewards4()));
									i.setItem(12, rewards4.get(rewards4()));
									i.setItem(13, rewards4.get(rewards4()));
									i.setItem(14, rewards4.get(rewards4()));
									i.setItem(15, rewards4.get(rewards4()));
									i.setItem(16, emptyVoid(player));
									i.setItem(17, emptyVoid(player));
									i.setItem(18, emptyVoid(player));
									i.setItem(19, emptyVoid(player));
									i.setItem(20, emptyVoid(player));
									i.setItem(21, emptyVoid(player));
									i.setItem(23, emptyVoid(player));
									i.setItem(24, emptyVoid(player));
									i.setItem(25, emptyVoid(player));
									i.setItem(26, emptyVoid(player));
									for(Player victim1 : Bukkit.getOnlinePlayers()) {
										((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.6);
									}
									counter--;
									if(counter == 0) {
										cancel();
										counter = 5;
										bt3 = new BukkitRunnable() {
											@Override
											public void run() {
												if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
													i.setItem(0, emptyVoid(player));
													i.setItem(1, emptyVoid(player));
													i.setItem(2, emptyVoid(player));
													i.setItem(3, emptyVoid(player));
													i.setItem(4, emptyVoid(player));
													i.setItem(5, emptyVoid(player));
													i.setItem(6, emptyVoid(player));
													i.setItem(7, emptyVoid(player));
													i.setItem(8, emptyVoid(player));
													i.setItem(9, emptyVoid(player));
													i.setItem(10, emptyVoid(player));
													i.setItem(11, emptyVoid(player));
													i.setItem(12, rewards4.get(rewards4()));
													i.setItem(13, rewards4.get(rewards4()));
													i.setItem(14, rewards4.get(rewards4()));
													i.setItem(15, emptyVoid(player));
													i.setItem(16, emptyVoid(player));
													i.setItem(17, emptyVoid(player));
													i.setItem(18, emptyVoid(player));
													i.setItem(19, emptyVoid(player));
													i.setItem(20, emptyVoid(player));
													i.setItem(21, emptyVoid(player));
													i.setItem(22, emptyVoid(player));
													i.setItem(23, emptyVoid(player));
													i.setItem(24, emptyVoid(player));
													i.setItem(25, emptyVoid(player));
													i.setItem(26, emptyVoid(player));
													for(Player victim1 : Bukkit.getOnlinePlayers()) {
														((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.8);
													}
													counter--;
													if(counter == 0) {
														cancel();
														bt4 = new BukkitRunnable() {
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
																i.setItem(9, emptyVoid(player));
																i.setItem(10, emptyVoid(player));
																i.setItem(11, emptyVoid(player));
																i.setItem(12, emptyVoid(player));
																i.setItem(13, rewards4.get(finalRewards));
																i.setItem(14, emptyVoid(player));
																i.setItem(15, emptyVoid(player));
																i.setItem(16, emptyVoid(player));
																i.setItem(17, emptyVoid(player));
																i.setItem(18, emptyVoid(player));
																i.setItem(19, emptyVoid(player));
																i.setItem(20, emptyVoid(player));
																i.setItem(21, emptyVoid(player));
																i.setItem(22, emptyVoid(player));
																i.setItem(23, emptyVoid(player));
																i.setItem(24, emptyVoid(player));
																i.setItem(25, emptyVoid(player));
																i.setItem(26, emptyVoid(player));
																for(Player victim1 : Bukkit.getOnlinePlayers()) {
																	((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1);
																}
																cancel();
																player.getInventory().addItem(rewards4.get(finalRewards));
																new BukkitRunnable() {
																	@Override
																	public void run() {
																		if(player.getInventory().getName().equals(ChatColor.stripColor("Decrypting..."))) {
																			player.closeInventory();
																		}
																	}
																}.runTaskLater(plugin, 0L);
															}
														}.runTaskTimer(plugin, 0L, 16L);
													}
												}
												else {
													cancel();
													player.getInventory().addItem(rewards4.get(finalRewards));
												}
											}
										}.runTaskTimer(plugin, 0L, 12L);
									}
								}
								else {
									cancel();
									player.getInventory().addItem(rewards4.get(finalRewards));
								}
							}
						}.runTaskTimer(plugin, 0L, 6L);
					}				
				}
				else {
					cancel();
					player.getInventory().addItem(rewards4.get(finalRewards));
				}
			}
		}.runTaskTimer(plugin, 0L, 3L);
	}
	//--------------------------------------------------------------------------------------------------------------------
	//
	//
	//Legendary
	//
	//
	//--------------------------------------------------------------------------------------------------------------------
	public void NewInventory5(Player player) {
		Inventory i = plugin.getServer().createInventory(null, 27, (new ColorCodeTranslator().colorize("&6Decrypting Crystal...")));
		player.openInventory(i);
		check.add(player.getName());
		//--------------------------------------------------------------------------------------------------------------------
		//Inventory Animation
		//--------------------------------------------------------------------------------------------------------------------
		bt1 = new BukkitRunnable() {
			int counter = 17;
			@Override
			public void run() {
				int finalRewards = new Random().nextInt(NovisRewards.rewards5.size());
				if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
					i.setItem(0, emptyVoid(player));
					i.setItem(1, emptyVoid(player));
					i.setItem(2, emptyVoid(player));
					i.setItem(3, emptyVoid(player));
					i.setItem(4, emptyVoid(player));
					i.setItem(5, emptyVoid(player));
					i.setItem(6, emptyVoid(player));
					i.setItem(7, emptyVoid(player));
					i.setItem(8, emptyVoid(player));
					i.setItem(9, emptyVoid(player));
					i.setItem(10, rewards5.get(rewards5()));
					i.setItem(11, rewards5.get(rewards5()));
					i.setItem(12, rewards5.get(rewards5()));
					i.setItem(13, rewards5.get(rewards5()));
					i.setItem(14, rewards5.get(rewards5()));
					i.setItem(15, rewards5.get(rewards5()));
					i.setItem(16, rewards5.get(rewards5()));
					i.setItem(17, emptyVoid(player));
					i.setItem(18, emptyVoid(player));
					i.setItem(19, emptyVoid(player));
					i.setItem(20, emptyVoid(player));
					i.setItem(21, emptyVoid(player));
					i.setItem(22, emptyVoid(player));
					i.setItem(23, emptyVoid(player));
					i.setItem(24, emptyVoid(player));
					i.setItem(25, emptyVoid(player));
					i.setItem(26, emptyVoid(player));
					for(Player victim1 : Bukkit.getOnlinePlayers()) {
						((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.4);
					}
					counter--;
					if(counter == 0) {
						cancel();
						counter = 9;
						bt2 = new BukkitRunnable() {
							@Override
							public void run() {
								if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
									i.setItem(0, emptyVoid(player));
									i.setItem(1, emptyVoid(player));
									i.setItem(2, emptyVoid(player));
									i.setItem(3, emptyVoid(player));
									i.setItem(4, emptyVoid(player));
									i.setItem(5, emptyVoid(player));
									i.setItem(6, emptyVoid(player));
									i.setItem(7, emptyVoid(player));
									i.setItem(8, emptyVoid(player));
									i.setItem(9, emptyVoid(player));
									i.setItem(10, emptyVoid(player));
									i.setItem(11, rewards5.get(rewards5()));
									i.setItem(12, rewards5.get(rewards5()));
									i.setItem(13, rewards5.get(rewards5()));
									i.setItem(14, rewards5.get(rewards5()));
									i.setItem(15, rewards5.get(rewards5()));
									i.setItem(16, emptyVoid(player));
									i.setItem(17, emptyVoid(player));
									i.setItem(18, emptyVoid(player));
									i.setItem(19, emptyVoid(player));
									i.setItem(20, emptyVoid(player));
									i.setItem(21, emptyVoid(player));
									i.setItem(23, emptyVoid(player));
									i.setItem(24, emptyVoid(player));
									i.setItem(25, emptyVoid(player));
									i.setItem(26, emptyVoid(player));
									for(Player victim1 : Bukkit.getOnlinePlayers()) {
										((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.6);
									}
									counter--;
									if(counter == 0) {
										cancel();
										counter = 5;
										bt3 = new BukkitRunnable() {
											@Override
											public void run() {
												if(player.getOpenInventory().getTopInventory().getName().contains(ChatColor.stripColor("Decrypting "))) {
													i.setItem(0, emptyVoid(player));
													i.setItem(1, emptyVoid(player));
													i.setItem(2, emptyVoid(player));
													i.setItem(3, emptyVoid(player));
													i.setItem(4, emptyVoid(player));
													i.setItem(5, emptyVoid(player));
													i.setItem(6, emptyVoid(player));
													i.setItem(7, emptyVoid(player));
													i.setItem(8, emptyVoid(player));
													i.setItem(9, emptyVoid(player));
													i.setItem(10, emptyVoid(player));
													i.setItem(11, emptyVoid(player));
													i.setItem(12, rewards5.get(rewards5()));
													i.setItem(13, rewards5.get(rewards5()));
													i.setItem(14, rewards5.get(rewards5()));
													i.setItem(15, emptyVoid(player));
													i.setItem(16, emptyVoid(player));
													i.setItem(17, emptyVoid(player));
													i.setItem(18, emptyVoid(player));
													i.setItem(19, emptyVoid(player));
													i.setItem(20, emptyVoid(player));
													i.setItem(21, emptyVoid(player));
													i.setItem(22, emptyVoid(player));
													i.setItem(23, emptyVoid(player));
													i.setItem(24, emptyVoid(player));
													i.setItem(25, emptyVoid(player));
													i.setItem(26, emptyVoid(player));
													for(Player victim1 : Bukkit.getOnlinePlayers()) {
														((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 0.8);
													}
													counter--;
													if(counter == 0) {
														cancel();
														bt4 = new BukkitRunnable() {
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
																i.setItem(9, emptyVoid(player));
																i.setItem(10, emptyVoid(player));
																i.setItem(11, emptyVoid(player));
																i.setItem(12, emptyVoid(player));
																i.setItem(13, rewards5.get(finalRewards));
																i.setItem(14, emptyVoid(player));
																i.setItem(15, emptyVoid(player));
																i.setItem(16, emptyVoid(player));
																i.setItem(17, emptyVoid(player));
																i.setItem(18, emptyVoid(player));
																i.setItem(19, emptyVoid(player));
																i.setItem(20, emptyVoid(player));
																i.setItem(21, emptyVoid(player));
																i.setItem(22, emptyVoid(player));
																i.setItem(23, emptyVoid(player));
																i.setItem(24, emptyVoid(player));
																i.setItem(25, emptyVoid(player));
																i.setItem(26, emptyVoid(player));
																for(Player victim1 : Bukkit.getOnlinePlayers()) {
																	((Player) victim1).playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, (float) 1);
																}
																cancel();
																player.getInventory().addItem(rewards5.get(finalRewards));
																new BukkitRunnable() {
																	@Override
																	public void run() {
																		if(player.getInventory().getName().equals(ChatColor.stripColor("Decrypting..."))) {
																			player.closeInventory();
																		}
																	}
																}.runTaskLater(plugin, 0L);
															}
														}.runTaskTimer(plugin, 0L, 16L);
													}
												}
												else {
													cancel();
													player.getInventory().addItem(rewards5.get(finalRewards));
												}
											}
										}.runTaskTimer(plugin, 0L, 12L);
									}
								}
								else {
									cancel();
									player.getInventory().addItem(rewards5.get(finalRewards));
								}
							}
						}.runTaskTimer(plugin, 0L, 6L);
					}				
				}
				else {
					cancel();
					player.getInventory().addItem(rewards5.get(finalRewards));
				}
			}
		}.runTaskTimer(plugin, 0L, 3L);
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Activate Novis
	//--------------------------------------------------------------------------------------------------------------------
	@EventHandler
	public void novisActivate(PlayerInteractEntityEvent event) {
		if(event.getPlayer() instanceof Player) {
			if(event.getRightClicked() instanceof MagmaCube) {
				Player player = event.getPlayer();
				ItemStack item = player.getInventory().getItemInMainHand();
				LivingEntity novis = (LivingEntity) event.getRightClicked();
				if(novis.getName().contains(ChatColor.stripColor("Novis"))) {
					if(item != null) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasDisplayName()){
								if(!(check.contains(player.getName()))) {
									if(item.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Common Crystal"))) {
										player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
										NovisInventory novis1 = new NovisInventory();
										novis1.NewInventory1(player);
										check.add(player.getName());
										plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
											  public void run() {
												  check.remove(player.getName());
											  }
										}, 50L);
									}
									else if(item.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Rare Crystal"))) {
										player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
										NovisInventory novis1 = new NovisInventory();
										novis1.NewInventory2(player);
										check.add(player.getName());
										plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
											  public void run() {
												  check.remove(player.getName());
											  }
										}, 50L);
									}
									else if(item.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Epic Crystal"))) {
										player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
										NovisInventory novis1 = new NovisInventory();
										novis1.NewInventory3(player);
										check.add(player.getName());
										plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
											  public void run() {
												  check.remove(player.getName());
											  }
										}, 50L);
									}
									else if(item.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Legendary Crystal"))) {
										player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
										NovisInventory novis1 = new NovisInventory();
										novis1.NewInventory4(player);
										check.add(player.getName());
										plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
											  public void run() {
												  check.remove(player.getName());
											  }
										}, 50L);
									}
									else if(item.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Mythic Crystal"))) {
										player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
										NovisInventory novis1 = new NovisInventory();
										novis1.NewInventory5(player);
										check.add(player.getName());
										plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
											  public void run() {
												  check.remove(player.getName());
											  }
										}, 50L);
									}
								}
								else {
									player.sendMessage(new ColorCodeTranslator().colorize("&cThere is a cooldown!"));
								}
							}
						}
					}
				}
			}
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Random GUI Color
	//--------------------------------------------------------------------------------------------------------------------
	public ItemStack emptyVoid(Player player) {
		int randomInt = new Random().nextInt(6) + 1;
		ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(" ");
		item.setItemMeta(itemmeta);
		return item;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Prevent Clicking in Novis
	//--------------------------------------------------------------------------------------------------------------------
	@EventHandler
	public void novisPreventClick(InventoryClickEvent event) {		
		Player player = (Player) event.getWhoClicked();
		ClickType click = event.getClick();
		ItemStack item = event.getCurrentItem();
		InventoryView open = event.getView();
		if(open == null) {
			return;
		}
		if(open.getTitle().contains(ChatColor.stripColor("Decrypting "))) {
			event.setCancelled(true);
			if(item.equals(null) || !item.hasItemMeta()) {
				return;
			}
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Random Reward Selection
	//--------------------------------------------------------------------------------------------------------------------
	public int rewards1() {
		int rewardsR1 = new Random().nextInt(NovisRewards.rewards1.size());
		return rewardsR1;
	}
	public int rewards2() {
		int rewardsR1 = new Random().nextInt(NovisRewards.rewards2.size());
		return rewardsR1;
	}
	public int rewards3() {
		int rewardsR1 = new Random().nextInt(NovisRewards.rewards3.size());
		return rewardsR1;
	}
	public int rewards4() {
		int rewardsR1 = new Random().nextInt(NovisRewards.rewards4.size());
		return rewardsR1;
	}
	public int rewards5() {
		int rewardsR1 = new Random().nextInt(NovisRewards.rewards5.size());
		return rewardsR1;
	}
	
}
