package me.WiebeHero.CustomEnchantmentsM;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.WiebeHero.MoreStuff.SwordSwingProgress;
import me.WiebeHero.Skills.DFPlayer;
import net.md_5.bungee.api.ChatColor;

public class Numbness extends SwordSwingProgress implements Listener {
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
											if(s1.contains(ChatColor.stripColor("Numbness"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Numbness")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check);
											if(i <= 6 + 0.5 * level) {
												animation(victim);
												DFPlayer dfPlayer = new DFPlayer().getPlayer(victim);
												dfPlayer.removeRndCal(6.0 * level, 140 + (level * 20));
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
		Location locCF1 = new Location(entity.getWorld(), entity.getLocation().getX() + 0D, entity.getLocation().getY() + 1.8D, entity.getLocation().getZ() + 0D);
		entity.getWorld().spawnParticle(Particle.BLOCK_CRACK, locCF1, 80, 0.15, 0.15, 0.15, 0, Material.PINK_WOOL.createBlockData()); 
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ARROW_SHOOT, 2, (float) 0.5);
	}
}
