package me.WiebeHero.CustomItemsFOOD;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class HolyPotato implements Listener{
	@EventHandler
	public void CustomEnchantmentsMLargeFireball(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		ItemStack eatenItem = event.getItem();
		if(eatenItem != null) {
			if(eatenItem.hasItemMeta()) {
				if(eatenItem.getItemMeta().hasDisplayName()) {
					if(eatenItem.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Holy Potato"))) {
						int amp = 1;
						int amp1 = 2;
						int durationAdd = 200;
						int durationAdd1 = 400;
						PotionEffectType type = PotionEffectType.REGENERATION;
						PotionEffectType type1 = PotionEffectType.ABSORPTION;
						if(player.hasPotionEffect(type) && player.hasPotionEffect(type1) && player.getPotionEffect(type).getAmplifier() == amp && player.getPotionEffect(type1).getAmplifier() == amp1) {
							int durationNow = player.getPotionEffect(type).getDuration();
							player.removePotionEffect(type);
							player.removePotionEffect(type1);
							player.addPotionEffect(new PotionEffect(type, durationNow + durationAdd1, amp1));
							player.addPotionEffect(new PotionEffect(type1, durationNow + durationAdd, amp));
						}
						else {
							player.removePotionEffect(type);
							player.removePotionEffect(type1);
							player.addPotionEffect(new PotionEffect(type, durationAdd1, amp1));
							player.addPotionEffect(new PotionEffect(type1, durationAdd, amp));
						}
					}
				}
			}
		}
	}
}
