package me.WiebeHero.CustomEnchantmentsM;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import NeededStuff.SwordSwingProgress;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_13_R2.PacketPlayOutWorldParticles;

public class Cyclone extends SwordSwingProgress implements Listener {
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDamageByEntityEvent event) {
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
											if(s1.contains(ChatColor.stripColor("Cyclone"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Cyclone")){
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 0.5 + 0.5 * level) {
												double range = 5 + 0.5 * level;
												animation(victim, range);
												new BukkitRunnable() {
													public void run() {
														for(Entity e : victim.getNearbyEntities(range, range, range)) {
															if(e instanceof LivingEntity) {
																if(e != damager) {
																	int random1 = new Random().nextInt(4) - 4;
																	int random2 = new Random().nextInt(4) - 4;
																	float x = ThreadLocalRandom.current().nextFloat() * 4 + (float)random1;
																	float z = ThreadLocalRandom.current().nextFloat() * 4 + (float)random2;
																	LivingEntity entity = (LivingEntity) e;
																	entity.setVelocity(new Vector(x, 1.8 + 0.2 * level, z));
																}
															}
														}
													}
												}.runTaskLater(CustomEnchantments.getInstance(), 1L);
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
	public void animation(Entity entity, double range) {
		Location loc = entity.getLocation();
	    int radius = 5;
	    for(double y = 0; y <= 20; y+=0.0075) {
	        double x = radius * Math.cos(y);
	        double z = radius * Math.sin(y);
	        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(null, true, (float) (loc.getX() + x), (float) (loc.getY() + y), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
	        for(Player online : Bukkit.getOnlinePlayers()) {
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
	        }
	    }
	}
}
