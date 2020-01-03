package me.WiebeHero.Consumables;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEvents.DFShootBowEvent;

public class CustomDurability implements Listener{
	Consumable con = new Consumable();
	@EventHandler
	public void removeDuraBreakBlock(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(player != null) {
			ItemStack i = player.getInventory().getItemInMainHand();
			if(decreaseDurability(player, i) != null) {
				i = decreaseDurability(player, i);
				player.getInventory().setItemInMainHand(i);
			}
		}
	}
	@EventHandler
	public void removeDuraHit(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(player != null) {
				ItemStack i = player.getInventory().getItemInMainHand();
				if(decreaseDurability(player, i) != null) {
					i = decreaseDurability(player, i);
					player.getInventory().setItemInMainHand(i);
				}
			}
		}
	}
	@EventHandler
	public void removeDuraShoot(DFShootBowEvent event) {
		Player player = event.getShooter();
		if(player != null) {
			ItemStack i = event.getBow();
			if(decreaseDurability(player, i) != null) {
				i = decreaseDurability(player, i);
				player.getInventory().setItemInMainHand(i);
			}
		}
	}
	@EventHandler
	public void removeDuraDamage(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			ItemStack i = player.getInventory().getItemInOffHand();
			if(i != null) {
				if(decreaseDurability(player, i) != null) {
					i = decreaseDurability(player, i);
					player.getInventory().setItemInOffHand(i);
				}
			}
		}
	}
	@EventHandler
	public void removeDuraEnviromental(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			for(ItemStack i : player.getInventory().getArmorContents()) {
				if(i != null) {
					if(decreaseDurability(player, i) != null) {
						i = decreaseDurability(player, i);
						if(i.getType().toString().contains("HELMET") || i.getType().toString().contains("HEAD")) {
							player.getInventory().setItem(EquipmentSlot.HEAD, i);
						}
						if(i.getType().toString().contains("CHESTPLATE")) {
							player.getInventory().setItem(EquipmentSlot.CHEST, i);
						}
						if(i.getType().toString().contains("LEGGINGS")) {
							player.getInventory().setItem(EquipmentSlot.LEGS, i);
						}
						if(i.getType().toString().contains("BOOTS")) {
							player.getInventory().setItem(EquipmentSlot.FEET, i);
						}
					}
				}
			}
		}
	}
	public ItemStack decreaseDurability(Player p, ItemStack i) {
		NBTItem item = new NBTItem(i);
		if(item.hasKey("Durability") && item.hasKey("MaxDurability")) {
			int dura = item.getInteger("Durability");
			int maxDura = item.getInteger("MaxDurability");
			if(item.hasKey("Unbreaking")) {
				float i1 = ThreadLocalRandom.current().nextFloat() * 100;
				if(!(i1 <= 12.50 + 12.50 * item.getInteger("Unbreaking"))) {
					dura = dura - 1;
				}
			}
			else {
				dura = dura - 1;
			}
			item.setInteger("Durability", dura);
			i = item.getItem();
			double totalLost = 1.00 - (double)dura / (double)maxDura;
			Damageable dam = (Damageable) i.getItemMeta();
			dam.setDamage((int)(i.getType().getMaxDurability() * totalLost));
			i.setItemMeta((ItemMeta)dam);
			ItemMeta meta = i.getItemMeta();
			if(meta.hasLore()) {
				ArrayList<String> lore = new ArrayList<String>(meta.getLore());
				for(int x = 0; x < lore.size(); x++) {
					if(lore.get(x).contains("Durability")) {
						lore.set(x, new CCT().colorize("&7Durability: &6" + dura + " &7/ &6" + maxDura));
					}
				}
				meta.setLore(lore);
			}
			i.setItemMeta(meta);
			if(dura <= 0) {
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 2.0F, 1.0F);
				return new ItemStack(Material.AIR, 1);
			}
			else {
				return i;
			}
		}
		else {
			return null;
		}
	}
}
