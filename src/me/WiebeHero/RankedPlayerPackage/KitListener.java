package me.WiebeHero.RankedPlayerPackage;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Kit;

public class KitListener implements Listener{
	private KitMenu menu;
	private RankedManager rManager;
	public KitListener(KitMenu menu, RankedManager rManager) {
		this.menu = menu;
		this.rManager = rManager;
	}
	@EventHandler
	public void kitClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
		if(player.getOpenInventory().getTitle().contains("Kits")) {
			event.setCancelled(true);
			ItemStack item = event.getCurrentItem();
			if(item != null) {
				if(item.getType() == Material.CHEST_MINECART) {
					NBTItem i = new NBTItem(item);
					if(i.hasKey("Kit")) {
						Kit kit = i.getObject("Kit", Kit.class);
						kit.recieveKit(player);
						rPlayer.addKitCooldown(kit, System.currentTimeMillis() + kit.cooldown);
						menu.Kits(player);
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 2.0F, 1.0F);
						player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 1.5F);
					}
				}
				else {
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 0.5F);
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou can't claim this kit yet!"));
				}
			}
		}
	}
}
