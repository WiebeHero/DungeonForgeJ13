package me.WiebeHero.MoreStuff;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PreventIllegalItems implements Listener{
	@EventHandler
	public void preventIllegalClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		if(item != null) {
			if(item.getType() == Material.BOW || item.getType() == Material.WOODEN_SWORD || item.getType() == Material.STONE_SWORD || item.getType() == Material.GOLDEN_SWORD || item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.IRON_SWORD || item.getType() == Material.IRON_AXE || item.getType() == Material.WOODEN_AXE || item.getType() == Material.STONE_AXE || item.getType() == Material.GOLDEN_AXE || item.getType() == Material.DIAMOND_AXE) {
				if(!item.getItemMeta().hasDisplayName()) {
					event.setCurrentItem(null);
				}
			}
			else if(item.getType() == Material.BOW || item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.CHAINMAIL_HELMET || item.getType() == Material.CHAINMAIL_CHESTPLATE || item.getType() == Material.CHAINMAIL_LEGGINGS || item.getType() == Material.CHAINMAIL_BOOTS || item.getType() == Material.IRON_HELMET || item.getType() == Material.IRON_CHESTPLATE || item.getType() == Material.IRON_LEGGINGS || item.getType() == Material.IRON_BOOTS || item.getType() == Material.GOLDEN_HELMET || item.getType() == Material.GOLDEN_CHESTPLATE || item.getType() == Material.GOLDEN_LEGGINGS || item.getType() == Material.GOLDEN_BOOTS || item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.DIAMOND_BOOTS) {
				if(!item.getItemMeta().hasDisplayName()) {
					event.setCurrentItem(null);
				}
			}
			else if(item.getType() == Material.BOW || item.getType() == Material.ENDER_EYE || item.getType() == Material.END_CRYSTAL || item.getType() == Material.ENDER_CHEST || item.getType() == Material.HOPPER || item.getType() == Material.BEACON) {
				if(!item.getItemMeta().hasDisplayName()) {
					event.setCurrentItem(null);
				}
			}
		}
	}
	@EventHandler
	public void preventIllegalPickup(EntityPickupItemEvent event) {
		Item item = (Item) event.getItem();
		if(item != null) {
			if(item.getItemStack().getType() == Material.BOW ||item.getItemStack().getType() == Material.WOODEN_SWORD || item.getItemStack().getType() == Material.STONE_SWORD || item.getItemStack().getType() == Material.GOLDEN_SWORD || item.getItemStack().getType() == Material.DIAMOND_SWORD || item.getItemStack().getType() == Material.IRON_SWORD || item.getItemStack().getType() == Material.IRON_AXE || item.getItemStack().getType() == Material.WOODEN_AXE || item.getItemStack().getType() == Material.STONE_AXE || item.getItemStack().getType() == Material.GOLDEN_AXE || item.getItemStack().getType() == Material.DIAMOND_AXE) {
				if(!item.getItemStack().getItemMeta().hasDisplayName()) {
					event.setCancelled(true);
					event.getItem().remove();
				}
			}
			else if(item.getItemStack().getType() == Material.BOW || item.getItemStack().getType() == Material.LEATHER_HELMET || item.getItemStack().getType() == Material.LEATHER_CHESTPLATE || item.getItemStack().getType() == Material.LEATHER_LEGGINGS || item.getItemStack().getType() == Material.LEATHER_BOOTS || item.getItemStack().getType() == Material.CHAINMAIL_HELMET || item.getItemStack().getType() == Material.CHAINMAIL_CHESTPLATE || item.getItemStack().getType() == Material.CHAINMAIL_LEGGINGS || item.getItemStack().getType() == Material.CHAINMAIL_BOOTS || item.getItemStack().getType() == Material.IRON_HELMET || item.getItemStack().getType() == Material.IRON_CHESTPLATE || item.getItemStack().getType() == Material.IRON_LEGGINGS || item.getItemStack().getType() == Material.IRON_BOOTS || item.getItemStack().getType() == Material.GOLDEN_HELMET || item.getItemStack().getType() == Material.GOLDEN_CHESTPLATE || item.getItemStack().getType() == Material.GOLDEN_LEGGINGS || item.getItemStack().getType() == Material.GOLDEN_BOOTS || item.getItemStack().getType() == Material.DIAMOND_HELMET || item.getItemStack().getType() == Material.DIAMOND_CHESTPLATE || item.getItemStack().getType() == Material.DIAMOND_LEGGINGS || item.getItemStack().getType() == Material.DIAMOND_BOOTS) {
				if(!item.getItemStack().getItemMeta().hasDisplayName()) {
					event.setCancelled(true);
					event.getItem().remove();
				}
			}
			else if(item.getItemStack().getType() == Material.BOW || item.getItemStack().getType() == Material.ENDER_EYE || item.getItemStack().getType() == Material.END_CRYSTAL) {
				if(!item.getItemStack().getItemMeta().hasDisplayName()) {
					event.setCancelled(true);
					event.getItem().remove();
				}
			}
		}
	}
	@EventHandler
	public void disableToolUse(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item.getType() == Material.WOODEN_SWORD || item.getType() == Material.WOODEN_AXE || item.getType() == Material.WOODEN_HOE || item.getType() == Material.WOODEN_PICKAXE || item.getType() == Material.WOODEN_SHOVEL || item.getType() == Material.STONE_SWORD || item.getType() == Material.STONE_AXE || item.getType() == Material.STONE_HOE || item.getType() == Material.STONE_PICKAXE || item.getType() == Material.STONE_SHOVEL || item.getType() == Material.IRON_SWORD || item.getType() == Material.IRON_AXE || item.getType() == Material.IRON_HOE || item.getType() == Material.IRON_PICKAXE || item.getType() == Material.IRON_SHOVEL || item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.DIAMOND_AXE || item.getType() == Material.DIAMOND_HOE || item.getType() == Material.WOODEN_PICKAXE || item.getType() == Material.DIAMOND_SHOVEL || item.getType() == Material.GOLDEN_SWORD || item.getType() == Material.GOLDEN_AXE || item.getType() == Material.GOLDEN_HOE || item.getType() == Material.GOLDEN_PICKAXE || item.getType() == Material.GOLDEN_SHOVEL) {
				if(item.hasItemMeta()) {
					if(item.getItemMeta().hasLore()) {
						if(!item.getItemMeta().getLore().toString().contains("Attack Damage:") || !item.getItemMeta().getLore().toString().contains("Attack Speed:")) {
							event.setCancelled(true);
						}
					}
					else {
						event.setCancelled(true);
					}
				}
				else {
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void cancelIllegalShoot(EntityShootBowEvent event) {
		if(event.getEntity() instanceof Player) {
			if(event.getBow() != null) {
				if(event.getBow().hasItemMeta()) {
					if(!event.getBow().getItemMeta().hasDisplayName()) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
	@EventHandler
	public void cancelTrade(PlayerInteractEntityEvent event) {
		if(event.getRightClicked() instanceof Villager) {
			event.setCancelled(true);
		}
	}
}
