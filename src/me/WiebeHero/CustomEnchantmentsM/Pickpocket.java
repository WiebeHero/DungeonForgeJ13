package me.WiebeHero.CustomEnchantmentsM;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.MoreStuff.SwordSwingProgress;
import net.md_5.bungee.api.ChatColor;

public class Pickpocket extends SwordSwingProgress implements Listener {
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player){
			if(event.getEntity() instanceof Player) {
				if(!event.isCancelled()) {
					Player damager = (Player) event.getDamager();
					Player victim = (Player) event.getEntity();
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
											if(s1.contains(ChatColor.stripColor("Pickpocket"))) {
												check = ChatColor.stripColor(s1);
											}
										}
										if(check.contains("Pickpocket")){
											File f =  new File("plugins/CustomEnchantments/moneyConfig.yml");
											YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
											double moneyD = 0;
											double moneyV = 0;
											String moneyS1 = yml.getString("List." + damager.getUniqueId() + ".Money");
					    					Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)").matcher(ChatColor.stripColor(moneyS1));
											while(matcher.find()) {
												moneyD = Integer.parseInt(matcher.group(1)); 
											}
											String moneyS2 = yml.getString("List." + victim.getUniqueId() + ".Money");
					    					Matcher matcher1 = Pattern.compile("(\\d+)\\.(\\d+)").matcher(ChatColor.stripColor(moneyS2));
											while(matcher1.find()) {
												moneyV = Integer.parseInt(matcher1.group(1)); 
											}
											check = check.replaceAll("[^\\d.]", "");
											int level = Integer.parseInt(check) - 1;
											if(i <= 0.5 + 0.5 * level) {
												animation(victim, damager);
												double tempMoney = (double) (moneyV * (0.01 + 0.01 * level));
												double newV = (double)(moneyV - tempMoney);
												double newD = (double)(moneyD + tempMoney);
												yml.set("List." + damager.getUniqueId() + ".Money", newD);
												yml.set("List." + victim.getUniqueId() + ".Money", newV);
												try{
													yml.save(f);
										        }
										        catch(IOException e){
										            e.printStackTrace();
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
	public void animation(LivingEntity victim, Player damager) {
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
		damager.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 30, 0.1, 0.1, 0.1);
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, (float) 1.25);
		}
	}
}
