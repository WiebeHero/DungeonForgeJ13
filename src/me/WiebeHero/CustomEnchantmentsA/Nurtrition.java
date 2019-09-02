package me.WiebeHero.CustomEnchantmentsA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class Nurtrition implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMSlowness(FoodLevelChangeEvent event) {
		Player p = (Player) event.getEntity();
		int foodBefore = p.getFoodLevel();
		int foodAfter = event.getFoodLevel();
		float i = ThreadLocalRandom.current().nextFloat() * 100;
		if(foodAfter > foodBefore) {
			if(p.getInventory().getArmorContents() != null && p.getInventory().getItemInOffHand() != null) {
				ArrayList<ItemStack> items = new ArrayList<ItemStack>(Arrays.asList(p.getInventory().getArmorContents()));
				items.add(p.getInventory().getItemInOffHand());
				for(ItemStack item : items) {
					if(item != null) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasLore()) {
								String check = "";
								for(String s1 : item.getItemMeta().getLore()){
									if(s1.contains(ChatColor.stripColor("Nurtrition"))) {
										check = ChatColor.stripColor(s1);
									}
								}
								if(check.contains("Nurtrition")){
									check = check.replaceAll("[^\\d.]", "");
									int level = Integer.parseInt(check) - 1;
									if(i < 25 + 25 * level) {
										animation(p);
										double foodGained = foodAfter - foodBefore;
										int food = (int)(foodGained * (1.5 + 0.3 * level));
										if(p.getFoodLevel() + food <= 20) {
											p.setFoodLevel(p.getFoodLevel() + food);
										}
										else {
											p.setFoodLevel(20);
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
	public void animation(Player p) {
		Location locCF = new Location(p.getWorld(), p.getLocation().getX() + 0D, p.getLocation().getY() + 1.5D, p.getLocation().getZ() + 0D);
		p.getWorld().spawnParticle(Particle.DRIP_WATER, locCF, 60, 0.1, 0.1, 0.1, 0.1); 
		for(Player victim1 : Bukkit.getOnlinePlayers()) {
			((Player) victim1).playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 2, (float) 1.1);
		}
	}
}
