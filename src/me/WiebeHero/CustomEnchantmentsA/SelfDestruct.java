package me.WiebeHero.CustomEnchantmentsA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class SelfDestruct implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMSelfDestruct(EntityDeathEvent event) {
		if(event.getEntity().getKiller() instanceof LivingEntity){
			if(event.getEntity() instanceof Player) {
				LivingEntity damager = (LivingEntity) event.getEntity().getKiller();
				Player victim = (Player) event.getEntity();
				float i = ThreadLocalRandom.current().nextFloat() * 100;
				EntityDamageEvent ede = victim.getLastDamageCause();
				DamageCause dc = ede.getCause();
				if(event.getEntity().getKiller() instanceof Player && dc == DamageCause.ENTITY_ATTACK || dc == DamageCause.PROJECTILE){
					if(victim.getInventory().getArmorContents() != null && victim.getInventory().getItemInOffHand() != null) {
						ArrayList<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(victim.getInventory().getArmorContents()));
						items.add(victim.getInventory().getItemInOffHand());
						for(ItemStack item : items) {
							if(item != null) {
								if(item.hasItemMeta()) {
									if(item.getItemMeta().hasLore()) {
										String check = "";
										for(String s1 : item.getItemMeta().getLore()){
											if(s1.contains(ChatColor.stripColor("Self Destruct"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Self Destruct")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i < 40 + 10 * level) {
												animation(victim, damager);
												int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
													  public void run() {
														  Entity tnt = victim.getWorld().spawn(victim.getLocation().add(0, 2, 0), TNTPrimed.class); 
														  ((TNTPrimed)tnt).setFuseTicks(90 - 15 * level);
													  }
												}, 0L, 1L);
												plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
													  public void run() {
														  Bukkit.getScheduler().cancelTask(taskID);
													  }
												}, 10 + 3 * level);
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
	public void animation(Player victim, LivingEntity damager) {
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.5D, victim.getLocation().getZ() + 0D);
		victim.getWorld().spawnParticle(Particle.SMOKE_LARGE, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_TNT_PRIMED, 2, (float) 1);
		}
	}
}