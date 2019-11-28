package me.WiebeHero.MoreStuff;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent.SlotType;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.Skills.DFPlayer;

public class LevelRestrictions implements Listener{
	@EventHandler
	public void levelRestrictWeapons(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player || event.getDamager() instanceof Arrow) {
			Player player = null;
			if(event.getDamager() instanceof Player) {
				player = (Player) event.getDamager();
			}
			if(event.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) event.getDamager();
				if(arrow.getShooter() instanceof Player) {
					player = (Player) arrow.getShooter();
				}
			}
			if(player != null) {
				if(player.getInventory().getItemInMainHand() != null) {
					if(player.getInventory().getItemInMainHand().hasItemMeta()) {
						if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
							List<String> loreList = player.getInventory().getItemInMainHand().getItemMeta().getLore();
							for (int i=0; i<player.getInventory().getItemInMainHand().getItemMeta().getLore().size(); i++) {
								if(loreList.get(i).contains(ChatColor.stripColor("Level Required:"))) {	
									DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
									int level = dfPlayer.getLevel();
									String line = ChatColor.stripColor(loreList.get(i));
									line = line.replaceAll("[^\\d.]", "");
									int reqLevel = Integer.parseInt(line);
				  					if(level < reqLevel) {
				  						player.sendMessage(new ColorCodeTranslator().colorize("&cYou can't use this, you are to low level."));
				  						event.setCancelled(true);
					  				}
				  					break;
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void levelRestrictArmorEquip(PlayerArmorChangeEvent event) {
		Player player = event.getPlayer();
		if(event.getNewItem() != null) {
			if(event.getNewItem().hasItemMeta()) {
				if(event.getNewItem().getItemMeta().hasLore()) {
					for(String lore : event.getNewItem().getItemMeta().getLore()) {
						if(lore.contains(ChatColor.stripColor("Level Required:"))) {
							String check = ChatColor.stripColor(lore);
							DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
							int level = dfPlayer.getLevel();
							check = check.replaceAll("[^\\d.]", "");
							int reqLevel = Integer.parseInt(check);
		  					if(level < reqLevel) {
		  						player.sendMessage(new ColorCodeTranslator().colorize("&cYou can't use this, you are to low level."));
		  						if(event.getSlotType() == SlotType.HEAD) {
		  							player.getInventory().setHelmet(new ItemStack(Material.AIR));
		  						}
		  						if(event.getSlotType() == SlotType.CHEST) {
		  							player.getInventory().setChestplate(new ItemStack(Material.AIR));
		  						}
		  						if(event.getSlotType() == SlotType.LEGS) {
		  							player.getInventory().setLeggings(new ItemStack(Material.AIR));
		  						}
		  						if(event.getSlotType() == SlotType.FEET) {
		  							player.getInventory().setBoots(new ItemStack(Material.AIR));
		  						}
		  						player.getInventory().addItem(event.getNewItem());
		  					}
		  					break;
						}
					}
				}
			}
		}
	}
}
