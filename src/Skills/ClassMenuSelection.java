package Skills;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import Skills.Enums.Classes;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class ClassMenuSelection implements Listener{
	ArrayList<UUID> activated = new ArrayList<UUID>();
	PlayerClass pc = new PlayerClass();
	ClassMenu menu = new ClassMenu();
	SkillJoin join = new SkillJoin();
	@EventHandler
	public void clickInInv(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
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
			UUID uuid = player.getUniqueId();
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
					pc.setClass(uuid, currentClass);
					pc.createProfile(uuid, pc.getClass(uuid));
					activated.remove(player.getUniqueId());
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have chosen the class " + className[1]));
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
		UUID uuid = player.getUniqueId();
		InventoryView current = event.getView();
		if(pc.hasClass(uuid)) {
			if(current.getTitle().contains("Class Selection")) {
				if(!activated.contains(player.getUniqueId())) {
					new BukkitRunnable() {
						public void run() {
							menu.ClassSelect(player);
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 1L);
				}
			}
			else if(current.getTitle().contains("Class:")) {
				if(activated.contains(player.getUniqueId())) {
					String className[] = current.getTitle().split(" ");
					new BukkitRunnable() {
						public void run() {
							menu.ClassConfirm(player, className[1], current.getItem(4));
						}
					}.runTaskLater(CustomEnchantments.getInstance(), 1L);
				}
			}
		}
	}
}
