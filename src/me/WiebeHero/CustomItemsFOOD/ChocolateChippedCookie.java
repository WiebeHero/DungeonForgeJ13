package me.WiebeHero.CustomItemsFOOD;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class ChocolateChippedCookie implements Listener{
	@EventHandler
	public void CustomEnchantmentsMLargeFireball(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		ItemStack eatenItem = event.getItem();
		if(eatenItem != null) {
			if(eatenItem.hasItemMeta()) {
				if(eatenItem.getItemMeta().hasDisplayName()) {
					if(eatenItem.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Chocolate Chipped Cookie"))) {
						int amp = 1;
						int durationAdd = 80;
						PotionEffectType type = PotionEffectType.SPEED;
						if(player.hasPotionEffect(type) && player.getPotionEffect(type).getAmplifier() == amp) {
							int durationNow = player.getPotionEffect(type).getDuration();
							player.removePotionEffect(type);
							player.addPotionEffect(new PotionEffect(type, durationNow + durationAdd, amp));
						}
						else {
							player.removePotionEffect(type);
							player.addPotionEffect(new PotionEffect(type, durationAdd, amp));
						}
						
					}
				}
			}
		}
	}
}
