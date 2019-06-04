package me.WiebeHero.CustomItemsFOOD;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class PurifiedSpiderEye implements Listener{
	@EventHandler
	public void CustomEnchantmentsMLargeFireball(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		ItemStack eatenItem = event.getItem();
		if(eatenItem != null) {
			if(eatenItem.hasItemMeta()) {
				if(eatenItem.getItemMeta().hasDisplayName()) {
					if(eatenItem.getItemMeta().getDisplayName().contains(ChatColor.stripColor("Purified Spider Eye"))) {
						double attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
						double heal = player.getHealth() + attribute * 0.02;
						if(heal < attribute) {
							player.setHealth(heal);
						}
						else {
							player.setHealth(attribute);
						}
						new BukkitRunnable() {
							@Override
							public void run() {
								player.removePotionEffect(PotionEffectType.POISON);
							}
						}.runTaskLater(CustomEnchantments.getInstance(), 1L);
					}
				}
			}
		}
	}
}
