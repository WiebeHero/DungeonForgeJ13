package me.WiebeHero.CustomItemsFOOD;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class LegendaryHero implements Listener{
	Set<UUID> playersstuff = new HashSet<UUID>();
	@EventHandler
	public void food(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		ItemStack eatenItem = event.getItem();
		if(eatenItem != null) {
			if(eatenItem.hasItemMeta()) {
				if(eatenItem.getItemMeta().hasDisplayName()) {
					if(eatenItem.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Legendary Hero"))) {
						double attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
						double heal = player.getHealth() + attribute * 0.05;
						if(heal < attribute) {
							player.setHealth(heal);
						}
						else {
							player.setHealth(attribute);
						}
						if(!playersstuff.contains(player.getUniqueId())) {
							playersstuff.add(player.getUniqueId());
						}
						new BukkitRunnable() {
							@Override
							public void run() {
								if(playersstuff.contains(player.getUniqueId())) {
									playersstuff.remove(player.getUniqueId());
								}
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 200L);
					}
				}
			}
		}
	}
	public void boi(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player attacker = (Player) event.getDamager();
			if(playersstuff.contains(attacker.getUniqueId())) {
				double damage = event.getDamage();
				event.setDamage(damage * 1.10);
			}
		}
	}
}
