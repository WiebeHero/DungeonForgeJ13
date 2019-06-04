package NeededStuff;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class CancelLootSteal implements Listener{
	@EventHandler
	public void addIllegalPickupList(EntityDeathEvent event) {
		if(event.getEntity().getKiller() instanceof Player) {
			if(event.getEntity() instanceof LivingEntity) {
				Player killer = event.getEntity().getKiller();
				long timeNow = System.currentTimeMillis();
				LivingEntity ent = (LivingEntity) event.getEntity();
				ArrayList<ItemStack> drops = (ArrayList<ItemStack>) event.getDrops();
				FixedMetadataValue data = new FixedMetadataValue(CustomEnchantments.getInstance(), timeNow + 1000 * 10 + ":" + killer.getUniqueId());
				for(int i = 0; i < drops.size(); i++) {
					ItemStack item = drops.get(i);
					Item dItem = ent.getWorld().dropItemNaturally(ent.getLocation(), item);
					dItem.setMetadata("Prevent", data);
				}
				drops.clear();
			}
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void cancelIllegalPickup(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		Item item = event.getItem();
		if(item.hasMetadata("Prevent")) {
			String dataFull = ((MetadataValue)item.getMetadata("Prevent").get(0)).asString();
			String data[] = dataFull.split(":");
			long time = Long.parseLong(data[0]);
			UUID uuid = UUID.fromString(data[1]);
			if(!player.getUniqueId().equals(uuid) && System.currentTimeMillis() < time){
				event.setCancelled(true);
			}
		}
	}
}
