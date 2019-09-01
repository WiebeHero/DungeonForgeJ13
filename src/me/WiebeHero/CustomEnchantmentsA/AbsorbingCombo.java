package me.WiebeHero.CustomEnchantmentsA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class AbsorbingCombo implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	public HashMap<Player, Integer> comboCount = new HashMap<Player, Integer>();
	@EventHandler
	public void CustomEnchantmentsMAbsorbing(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					Player victim = (Player) event.getEntity();
					DamageCause damageCause = event.getCause();
					if(damageCause == DamageCause.ENTITY_ATTACK || damageCause == DamageCause.PROJECTILE) {
						if(victim.getInventory().getArmorContents() != null) {
							ArrayList<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(victim.getInventory().getArmorContents()));
							items.add(victim.getInventory().getItemInOffHand());
							for(ItemStack item : items) {
								if(item != null) {
									if(item.getItemMeta().getLore() != null) {
										String check = "";
										for(String s1 : item.getItemMeta().getLore()){
											if(s1.contains(ChatColor.stripColor("Absorbing Combo"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Absorbing Combo")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											int count = comboCount.get(victim);
											if(comboCount.get(victim) == null) {
												count = 0;
											}
											count++;
											int amp = (int) Math.floor((double)count / (5 - level));
											int durationAdd = 30 + 10 * level;
											PotionEffectType type = PotionEffectType.REGENERATION;
											if(victim.hasPotionEffect(type) && victim.getPotionEffect(type).getAmplifier() == amp) {
												int durationNow = victim.getPotionEffect(type).getDuration();
												victim.removePotionEffect(type);
												victim.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
											}
											else {
												victim.removePotionEffect(type);
												victim.addPotionEffect(new PotionEffect(type, durationAdd, amp));
											}
											new BukkitRunnable() {
												public void run() {
													comboCount.put(victim, 0);
													victim.removePotionEffect(PotionEffectType.REGENERATION);
												}
											}.runTaskLater(CustomEnchantments.getInstance(), 30L + 10 * level);
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
