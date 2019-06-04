package me.WiebeHero.CustomItemsFOOD;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class Fusgel implements Listener{
	@EventHandler
	public void CustomEnchantmentsMLargeFireball(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_AIR){
			Player player = event.getPlayer();
			if(player.getInventory().getItemInMainHand() != null) {
				if(player.getInventory().getItemInMainHand().hasItemMeta()) {
					if(player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
						if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.stripColor("Fusgel"))) {
							player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
							int amp = 1;
							int durationAdd = 100;
							PotionEffectType type = PotionEffectType.REGENERATION;
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
}
