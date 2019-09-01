package me.WiebeHero.CustomEnchantmentsM;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.MoreStuff.SwordSwingProgress;

public class SoulBurst extends SwordSwingProgress implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMWolfPack(PlayerDeathEvent event) {
		if(event.getEntity() instanceof Player) {
			Player victim = (Player) event.getEntity();
			EntityDamageEvent ede = victim.getLastDamageCause();
			DamageCause dc = ede.getCause();
			if(event.getEntity().getKiller() instanceof Player && dc == DamageCause.ENTITY_ATTACK){
				if(event.getEntity() instanceof Player) {
					Player damager = (Player) event.getEntity().getKiller();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					double d = swordSwingProgress.get(damager.getName());
					if(d == 1.00) {
						if(damager.getInventory().getItemInMainHand() != null) {
							if(damager.getInventory().getItemInMainHand().getItemMeta() != null) {
								if(damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null) {
									String check = "";
									for(String s1 : damager.getInventory().getItemInMainHand().getItemMeta().getLore()){
										if(s1.contains(ChatColor.stripColor("Soul Burst"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Soul Burst")){
										check = check.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(check) - 1;
										if(i <= 20 + 7.5 * level) {
											animation(victim);
											double range = 10 + 1.5 * level;
											for(Entity e : victim.getNearbyEntities(range, range, range)) {
												if(e instanceof LivingEntity) {
													if(e != damager) {
														LivingEntity entity = (LivingEntity) e;
														entity.damage(15 + 6.5 * level);
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
	public void animation(Entity entity) {
		Location loc1 = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 1.50D, entity.getLocation().getZ() + 0D);
		entity.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc1, 60, 0.1, 0.1, 0.1, 0.1);
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ZOMBIE_DEATH, 2, (float) 0.5);
	}
}