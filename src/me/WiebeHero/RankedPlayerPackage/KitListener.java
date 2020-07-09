package me.WiebeHero.RankedPlayerPackage;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.RankedPlayerPackage.RankEnum.Kit;

public class KitListener implements Listener{
	private KitMenu menu;
	private RankedManager rManager;
	private KitRoll roll;
	public KitListener(KitMenu menu, RankedManager rManager, KitRoll roll) {
		this.menu = menu;
		this.rManager = rManager;
		this.roll = roll;
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
						rPlayer.addKitCooldown(kit, System.currentTimeMillis() + kit.getCooldown());
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
	@EventHandler
	public void playerUseKitKey(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			ItemStack stack = player.getEquipment().getItemInMainHand();
			if(stack != null) {
				NBTItem item = new NBTItem(stack);
				if(item.hasKey("KitKey")) {
					RankedPlayer rPlayer = rManager.getRankedPlayer(player.getUniqueId());
					ArrayList<Kit> kitList = rPlayer.getKitsNotUnlocked();
					if(kitList.size() > 1) {
						this.roll.KitRollMenu(player);
						stack.setAmount(stack.getAmount() - 1);
					}
					else if(kitList.size() == 1){
						Kit kit = rPlayer.getKitsNotUnlocked().get(0);
						rPlayer.setKitUnlock(kit, true);
						stack.setAmount(stack.getAmount() - 1);
						String kitS = kit.toString();
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have recieved the &6" + kitS.substring(0, 1).toUpperCase() + kitS.substring(1).toLowerCase() + " &a kit!"));
					}
					else {
						player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &cYou have all of the unlockable kits unlocked!"));
					}
				}
			}
		}
	}
}
