package me.WiebeHero.CustomEnchantmentsA;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_13_R2.AxisAlignedBB;

public class ExplodingParrot implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	public ArrayList<Player> parrotListActivated = new ArrayList<Player>();
	@EventHandler
	public void CustomEnchantmentsMEscape(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		AxisAlignedBB bb = ((CraftPlayer)player).getHandle().getBoundingBox();
		AxisAlignedBB aa = null;
		Parrot parrot = null;
		for(Entity e : player.getNearbyEntities(1, 1, 1)) {
			if(e.getType() == EntityType.PARROT) {
				Parrot parro = (Parrot) e;
				if(parro.getCustomName().contains(ChatColor.stripColor("Exploding Parrot"))) {
					aa = ((CraftEntity)parro).getHandle().getBoundingBox();
					parrot = (Parrot) parro;
				}
			}
		}
		if(aa != null) {
			Vector min1 = new Vector(bb.minX, bb.minY, bb.minZ);
			Vector max1 = new Vector(bb.maxX, bb.maxY, bb.maxZ);
			Vector min2 = new Vector(aa.minX, aa.minY, aa.minZ);
			Vector max2 = new Vector(aa.maxX, aa.maxY, aa.maxZ);
			if(min1.getX() <= max2.getX() && max1.getX() >= min1.getX()) {
				if(min1.getY() <= max2.getY() && max1.getY() >= min1.getY()) {
					if(min1.getZ() <= max2.getZ() && max1.getZ() >= min1.getZ()) {
						if(parrot.getOwner() instanceof Player) {
							Player tamer = (Player) parrot.getOwner();
							for(ItemStack item : tamer.getInventory().getArmorContents()) {
								if(item != null) {
									if(item.hasItemMeta()) {
										if(item.getItemMeta().hasLore()) {
											if(item.getItemMeta().getLore().toString().contains("Exploding Parrot")) {
												String lore = "";
												for(String s : item.getItemMeta().getLore()) {
													if(s.contains("Exploding Parrot")) {
														lore = ChatColor.stripColor(s);
													}
												}
												lore = lore.replaceAll("[^\\d.]", "");
												int level = Integer.parseInt(lore) - 1;
												double range = 1 + 0.5 * level;
												for(Entity e : parrot.getNearbyEntities(range, range, range)) {
													if(!parrot.isDead()) {
														if(tamer != e) {
															if(e instanceof Player) {
																animation(parrot);
																Player enemy = (Player) e;
																parrot.damage(1000000000);
																enemy.damage(0.5 + 0.5 * level);
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void checkParrot(PlayerJoinEvent event) {
		new BukkitRunnable() {
			int check = 0;
			public void run() {
				Player player = event.getPlayer();
				if(player.isOnline()) {
					check++;
					for(ItemStack item : player.getInventory().getArmorContents()) {
						if(item != null) {
							if(item.hasItemMeta()) {
								if(item.getItemMeta().hasLore()) {
									if(item.getItemMeta().getLore().toString().contains(ChatColor.stripColor("Exploding Parrot"))) {
										String lore = "";
										for(String s : item.getItemMeta().getLore()) {
											if(s.contains("Exploding Parrot")) {
												lore = ChatColor.stripColor(s);
											}
										}
										lore = lore.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(lore) - 1;
										int count = 0;
										if(check <= 400 - 40 * level) {
											for(Entity e : player.getNearbyEntities(15, 15, 15)) {
												if(e.getType() == EntityType.PARROT) {
													Parrot parrot = (Parrot) e;
													if(parrot.getCustomName().contains("Exploding Parrot")) {
														count = count + 1;
													}
												}
											}
											if(count < 4 + level) {
												Parrot parrot = (Parrot) player.getWorld().spawnEntity(player.getLocation().add(0.0, 1.0, 0.0), EntityType.PARROT);
												parrot.setAdult();
												parrot.setOwner(player);
												parrot.setCustomName(new ColorCodeTranslator().colorize("&cExploding Parrot"));
											}
											count = 0;
											check = 0;
										}
									}
								}
							}
						}
					}
				}
				else {
					cancel();
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
	}
	public void animation(LivingEntity victim) {
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 0.2D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_PARROT_DEATH, 2, (float) 0.5);
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, (float) 0.5);
		}
	}
}
