package me.WiebeHero.ArmoryPackage;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEvents.DFItemXpGainEvent;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Novis.NovisEnchantmentGetting;

public class XPAddWeapons implements Listener{
	private NovisEnchantmentGetting enchant;
	private DFPlayerManager dfManager;
	public XPAddWeapons(NovisEnchantmentGetting enchant, DFPlayerManager dfManager) {
		this.enchant = enchant;
		this.dfManager = dfManager;
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack i = event.getCurrentItem();
		ItemStack c = event.getCursor();
		if(event.getClickedInventory() != null) {
			if(event.getClickedInventory().getType() == InventoryType.PLAYER && player.getGameMode().equals(GameMode.SURVIVAL) && event.getSlot() != 36 && event.getSlot() != 37 && event.getSlot() != 38 && event.getSlot() != 39) {
				if(i != null && c != null) {
					NBTItem item = new NBTItem(i);
					NBTItem cursor = new NBTItem(c);
					if(item.hasKey("Upgradeable") && cursor.hasKey("Upgradeable")) {
						boolean check = false;
						if(item.hasKey("WeaponKey") && cursor.hasKey("WeaponKey")){
							if(item.getString("WeaponKey").equals(cursor.getString("WeaponKey"))) {
								check = true;
							}
						}
						else if(item.hasKey("ShieldKey") && cursor.hasKey("ShieldKey")){
							if(item.getString("ShieldKey").equals(cursor.getString("ShieldKey"))) {
								check = true;
							}
						}
						else if(item.hasKey("BowKey") && cursor.hasKey("BowKey")){
							if(item.getString("BowKey").equals(cursor.getString("BowKey"))) {
								check = true;
							}
						}
						else if(item.hasKey("ArmorKey") && cursor.hasKey("ArmorKey")){
							if(item.getString("ArmorKey").equals(cursor.getString("ArmorKey"))) {
								check = true;
							}
						}
						if(check) {
							int levelItem = item.getInteger("Level");
							if(levelItem < 15) {
								int xpItem = item.getInteger("XP");
								int xpCursor = cursor.getInteger("XP") + 2500;
								int totalXpCursor = cursor.getInteger("TotalXP");
								int total = xpItem + totalXpCursor + xpCursor;
								DFItemXpGainEvent e = new DFItemXpGainEvent(player, i, c, total, event.getSlot(), dfManager, enchant);
								Bukkit.getPluginManager().callEvent(e);
								if(!e.isCancelled()) {
									event.setCancelled(true);
								}
							}
						}			
					}
				}
			}
		}
	}
}