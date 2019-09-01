package me.WiebeHero.CustomEnchantmentsM;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.MoreStuff.SwordSwingProgress;
import net.md_5.bungee.api.ChatColor;

public class Brand extends SwordSwingProgress implements Listener {
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	public HashMap<LivingEntity, Integer> brandList = new HashMap<LivingEntity, Integer>();
	@EventHandler
	public void customEnchantmentsMBrand(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player){
			if(event.getEntity() instanceof LivingEntity) {
				if(!event.isCancelled()) {
					Player damager = (Player) event.getDamager();
					LivingEntity victim = (LivingEntity) event.getEntity();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					double d = swordSwingProgress.get(damager.getName());
					DamageCause damageCause = event.getCause();
					if(damageCause == DamageCause.ENTITY_ATTACK) {
						if(d == 1.00) {
							if(damager.getInventory().getItemInMainHand() != null) {
								if(damager.getInventory().getItemInMainHand().getItemMeta() != null) {
									if(damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
										String check = "";
										for(String s1 : damager.getInventory().getItemInMainHand().getItemMeta().getLore()){
											if(s1.contains(ChatColor.stripColor("Brand"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Brand")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 4 + 0.8 * level) {
												animation(victim);
												brandList.put(victim, level);
												new BukkitRunnable() {
													public void run() {
														brandList.remove(victim);
													}
												}.runTaskLater(CustomEnchantments.getInstance(), 100 + 5 * level);
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
	public void brandDamage(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof LivingEntity) {
			if(brandList.containsKey(event.getEntity())) {
				event.setDamage(event.getFinalDamage() * (1.20 + 0.05 * brandList.get(event.getEntity())));
			}
		}
	}
	public void animation(Entity entity) {
		Location locCF1 = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 2.5D, entity.getLocation().getZ() + 0D);
		entity.getWorld().spawnParticle(Particle.FLAME, locCF1, 80, 0.15, 0.15, 0.15, 0); 
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, (float) 1.5);
	}
}
