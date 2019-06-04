package me.WiebeHero.CustomItemsFOOD;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class MenJerky implements Listener{
	@EventHandler
	public void CustomEnchantmentsMLargeFireball(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		ItemStack eatenItem = event.getItem();
		if(eatenItem != null) {
			if(eatenItem.hasItemMeta()) {
				if(eatenItem.getItemMeta().hasDisplayName()) {
					if(eatenItem.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Men Jerky"))) {
						double attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
						double heal = player.getHealth() + 2.00;
						if(heal < attribute) {
							player.setHealth(heal);
						}
						else {
							player.setHealth(attribute);
						}
						int amp = 0;
						int durationAdd = 100;
						PotionEffectType type = PotionEffectType.INCREASE_DAMAGE;
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
