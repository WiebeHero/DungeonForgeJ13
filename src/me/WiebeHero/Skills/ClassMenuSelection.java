package me.WiebeHero.Skills;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.DFPlayerPackage.Enums.Classes;
import net.md_5.bungee.api.ChatColor;

public class ClassMenuSelection implements Listener{
	private DFPlayerManager dfManager;
	private ArrayList<UUID> move = new ArrayList<UUID>();
	private ArrayList<UUID> activated = new ArrayList<UUID>();
	private ClassMenu menu;
	public ClassMenuSelection(DFPlayerManager manager, ClassMenu menu) {
		this.dfManager = manager;
		this.menu = menu;
	}
	@EventHandler
	public void joinHandler(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!this.dfManager.contains(player)) {
			if(!move.contains(player.getUniqueId())) {
				move.add(player.getUniqueId());
			}
		}
	}
	@EventHandler
	public void clickInInv(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		DFPlayer dfPlayer = dfManager.getEntity(player);
		InventoryView current = player.getOpenInventory();
		if(current.getTitle().contains("Class Selection")) {
			event.setCancelled(true);
			if(item == null || !item.hasItemMeta()) {
				return;
			}
			else {
				String className = ChatColor.stripColor(item.getItemMeta().getDisplayName());
				if(className.equals("Wrath") || className.equals("Lust") || className.equals("Gluttony") || className.equals("Envy") || className.equals("Greed") || className.equals("Sloth") || className.equals("Pride")) {
					activated.add(player.getUniqueId());
					menu.ClassConfirm(player, item.getItemMeta().getDisplayName(), item);
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 0.5F);
				}
			}
		}
		else if(current.getTitle().contains("Class:")) {
			event.setCancelled(true);
			if(item == null || !item.hasItemMeta()) {
				return;
			}
			else {
				String decision = ChatColor.stripColor(item.getItemMeta().getDisplayName());
				if(decision.equals("Yes")) {
					String className[] = ChatColor.stripColor(current.getTitle()).split(" ");
					String temp = className[1].toUpperCase();
					Classes currentClass = Enum.valueOf(Classes.class, temp);
					dfPlayer.setPlayerClass(currentClass);
					dfPlayer.resetIncreases();
					dfPlayer.resetCalculations();
					activated.remove(player.getUniqueId());
					player.sendMessage(new CCT().colorize("&2&l[DungeonForge]: &aYou have chosen the class " + className[1]));
					player.closeInventory();
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
				}
				else if(decision.equals("No")) {
					activated.remove(player.getUniqueId());
					menu.ClassSelect(player);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
				}
			}
		}
	}
	@EventHandler
	public void closeInvClass(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		DFPlayer dfPlayer = dfManager.getEntity(player);
		InventoryView current = event.getView();
		if(!dfPlayer.hasPlayerClass()) {
			if(current.getTitle().contains("Class Selection")) {
				if(!activated.contains(player.getUniqueId())) {
					new BukkitRunnable() {
						public void run() {
							if(player.isOnline()) {
								menu.ClassSelect(player);
							}
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 0L);
				}
			}
			else if(current.getTitle().contains("Class:")) {
				if(activated.contains(player.getUniqueId())) {
					String className[] = current.getTitle().split(" ");
					new BukkitRunnable() {
						public void run() {
							if(player.isOnline()) {
								menu.ClassConfirm(player, className[1], current.getItem(4));
							}
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 0L);
				}
			}
		}
	}
}
