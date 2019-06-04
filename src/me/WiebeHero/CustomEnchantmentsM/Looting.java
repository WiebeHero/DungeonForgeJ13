package me.WiebeHero.CustomEnchantmentsM;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import NeededStuff.SwordSwingProgress;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class Looting extends SwordSwingProgress implements Listener{
	public Plugin plugin = CustomEnchantments.getPlugin(CustomEnchantments.class);
	@EventHandler
	public void CustomEnchantmentsMWolfPack(EntityDeathEvent event) {
		if(event.getEntity() instanceof LivingEntity) {
			LivingEntity victim = (LivingEntity) event.getEntity();
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
										if(s1.contains(ChatColor.stripColor("Looting"))) {
											check = ChatColor.stripColor(s1);
										}
									}
									if(check.contains("Looting")){
										check = check.replaceAll("[^\\d.]", "");
										int level = Integer.parseInt(check) - 1;
										if(i <= 30 + 10 * level) {
											if(!(victim instanceof Player)) {
												List<ItemStack> stacks = event.getDrops();
												for(int i1 = 0; i1 < stacks.size(); i1++) {
													stacks.get(i1).setAmount((int)(stacks.get(i1).getAmount() * (2.00 + 0.30 * level)));
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
