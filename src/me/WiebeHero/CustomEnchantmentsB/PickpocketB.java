package me.WiebeHero.CustomEnchantmentsB;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class PickpocketB implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMAllOut(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Arrow){
			Arrow arrow = (Arrow) event.getDamager();
			if(arrow.getShooter() instanceof Player) {
				if(!event.isCancelled()) {
					LivingEntity victim = (LivingEntity) event.getEntity();
					float i = ThreadLocalRandom.current().nextFloat() * 100;
					Player damager = (Player) arrow.getShooter();
					String name = arrow.getCustomName();
					double force = 0.00;
					Matcher matcher2 = Pattern.compile("(\\d+)\\.(\\d+)").matcher(ChatColor.stripColor(name));
					while(matcher2.find()) {
					    force = Double.parseDouble(matcher2.group(1) + "." + matcher2.group(2));	
					}
					if(force == 1.00) {
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
										check = check.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(check) - 1;
										if(i <= 0.5 + 0.5 * level) {
											animation(victim, damager);
											int moneyD = 0;
											int moneyV = 0;
											moneyD = yml.getInt("List." + damager.getUniqueId() + ".Money");
											moneyV = yml.getInt("List." + victim.getUniqueId() + ".Money");
											int tempMoney = (int) (moneyV * (0.01 + 0.01 * level));
											int newV = (int)(moneyV - tempMoney);
											int newD = (int)(moneyD + tempMoney);
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
						if(damager.getInventory().getItemInOffHand() != null) {
							if(damager.getInventory().getItemInOffHand().getItemMeta() != null) {
								if(damager.getInventory().getItemInOffHand().getItemMeta().getLore() != null) {
									String check = "";
									for(String s1 : damager.getInventory().getItemInOffHand().getItemMeta().getLore()){
										if(s1.contains(ChatColor.stripColor("Pickpocket"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Pickpocket")){
										File f =  new File("plugins/CustomEnchantments/moneyConfig.yml");
										YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
										check = check.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(check) - 1;
										if(i <= 0.5 + 0.5 * level) {
											animation(victim, damager);
											int moneyD = 0;
											int moneyV = 0;
											moneyD = yml.getInt("List." + damager.getUniqueId() + ".Money");
											moneyV = yml.getInt("List." + victim.getUniqueId() + ".Money");
											int tempMoney = (int) (moneyV * (0.01 + 0.01 * level));
											int newV = (int)(moneyV - tempMoney);
											int newD = (int)(moneyD + tempMoney);
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
	public void animation(LivingEntity victim, Player damager) {
		Location locCF = new Location(victim.getWorld(), victim.getLocation().getX() + 0D, victim.getLocation().getY() + 1.7D, victim.getLocation().getZ() + 0D);
		damager.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, locCF, 30, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(victim.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, (float) 1.25);
		}
	}
}
