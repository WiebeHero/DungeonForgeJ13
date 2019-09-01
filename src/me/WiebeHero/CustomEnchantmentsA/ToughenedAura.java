package me.WiebeHero.CustomEnchantmentsA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import me.WiebeHero.Factions.DFFactions;
import net.md_5.bungee.api.ChatColor;

public class ToughenedAura implements Listener{
	private DFFactions f = new DFFactions();
	@EventHandler
	public void toughenedAura(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					Player victim = (Player) event.getEntity();
					DamageCause damageCause = event.getCause();
					if(damageCause == DamageCause.ENTITY_ATTACK || damageCause == DamageCause.PROJECTILE) {
						String facName = "";
						for(Entry<String, ArrayList<UUID>> entry : f.getFactionMemberList().entrySet()) {
							if(entry.getValue().contains(victim.getUniqueId())) {
								facName = entry.getKey();
							}
						}
						if(victim.getInventory().getArmorContents() != null) {
							ArrayList<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(victim.getInventory().getArmorContents()));
							items.add(victim.getInventory().getItemInOffHand());
							for(ItemStack item : items) {
								if(item != null) {
									if(item.getItemMeta().getLore() != null) {
										String check = "";
										for(String s1 : item.getItemMeta().getLore()){
											if(s1.contains(ChatColor.stripColor("Toughened Aura"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Toughened Aura")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											event.setDamage(event.getFinalDamage() / 100 * (100 - (level * 0.5 + 1)));
										}
									}
								}
							}
						}
						for(Entity e : victim.getNearbyEntities(7, 7, 7)) {
							if(e instanceof Player) {
								Player player = (Player) e;
								if(f.getFactionMemberList().get(facName).contains(player.getUniqueId())) {
									ItemStack[] items = player.getInventory().getArmorContents();
									for(ItemStack item : items) {
										if(item != null) {
											if(item.getItemMeta().getLore() != null) {
												String check = "";
												for(String s1 : item.getItemMeta().getLore()){
													if(s1.contains(ChatColor.stripColor("Toughened Aura"))) {
														check = ChatColor.stripColor(s1);
													}
												}
												if(check.contains("Toughened Aura")){
													check = check.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(check) - 1;
													event.setDamage(event.getFinalDamage() / 100 * (100 - (0.25 + level * 0.25)));
												}
											}
										}
									}
								}
								if(f.getPlayerAllyList().get(facName).contains(player.getUniqueId())) {
									ItemStack[] items = player.getInventory().getArmorContents();
									for(ItemStack item : items) {
										if(item != null) {
											if(item.getItemMeta().getLore() != null) {
												String check = "";
												for(String s1 : item.getItemMeta().getLore()){
													if(s1.contains(ChatColor.stripColor("Toughened Aura"))) {
														check = ChatColor.stripColor(s1);
													}
												}
												if(check.contains("Toughened Aura")){
													check = check.replaceAll("[^\\d.]", "");
													int level = Integer.parseInt(check) - 1;
													event.setDamage(event.getFinalDamage() / 100 * (100 - (0.15 + level * 0.15)));
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
