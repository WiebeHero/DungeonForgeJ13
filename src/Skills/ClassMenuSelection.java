package Skills;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class ClassMenuSelection implements Listener{
	ClassMenu menu = new ClassMenu();
	SkillJoin join = new SkillJoin();
	@EventHandler
	public void clickInInv(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		InventoryView current = player.getOpenInventory();
		if(current.getTitle().contains("Class Selection")) {
			event.setCancelled(true);
			if(item.equals(null) || !item.hasItemMeta()) {
				return;
			}
			else {
				String className = ChatColor.stripColor(item.getItemMeta().getDisplayName());
				if(className.equals("Wrath") || className.equals("Lust") || className.equals("Gluttony") || className.equals("Envy") || className.equals("Greed") || className.equals("Sloth") || className.equals("Pride")) {
					join.getClassList().put(player.getUniqueId(), "");
					menu.ClassConfirm(player, item.getItemMeta().getDisplayName(), item);
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 0.5F);
				}
			}
		}
		else if(current.getTitle().contains("Class:")) {
			event.setCancelled(true);
			if(item.equals(null) || !item.hasItemMeta()) {
				return;
			}
			else {
				String decision = ChatColor.stripColor(item.getItemMeta().getDisplayName());
				if(decision.equals("Yes")) {
					String className[] = ChatColor.stripColor(current.getTitle()).split(" ");
					join.getClassList().put(player.getUniqueId(), className[1]);
					join.getADList().put(player.getUniqueId(), 0);
					join.getASList().put(player.getUniqueId(), 0);
					join.getCCList().put(player.getUniqueId(), 0);
					join.getRDList().put(player.getUniqueId(), 0);
					join.getHHList().put(player.getUniqueId(), 0);
					join.getDFList().put(player.getUniqueId(), 0);
					join.getADMODList().put(player.getUniqueId(), 0);
					join.getASMODList().put(player.getUniqueId(), 0);
					join.getCCMODList().put(player.getUniqueId(), 0);
					join.getRDMODList().put(player.getUniqueId(), 0);
					join.getHHMODList().put(player.getUniqueId(), 0);
					join.getDFMODList().put(player.getUniqueId(), 0);
					join.getSkillPoints().put(player.getUniqueId(), 3);
					join.getLevelList().put(player.getUniqueId(), 1);
					join.getXPList().put(player.getUniqueId(), 0);
					join.getMXPList().put(player.getUniqueId(), 1500);
					player.sendMessage(new ColorCodeTranslator().colorize("&2&l[DungeonForge]: &aYou have chosen the class " + className[1]));
					player.closeInventory();
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
				}
				else if(decision.equals("No")) {
					join.getClassList().remove(player.getUniqueId());
					menu.ClassSelect(player);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
				}
			}
		}
	}
	@EventHandler
	public void closeInvClass(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		InventoryView current = event.getView();
		if(!join.getClassList().containsKey(player.getUniqueId())) {
			if(current.getTitle().contains("Class Selection")) {
				new BukkitRunnable() {
					public void run() {
						menu.ClassSelect(player);
					}
				}.runTaskLater(CustomEnchantments.getInstance(), 1L);
			}
		}
		else if(join.getClassList().containsKey(player.getUniqueId())) {
			if(join.getClassList().get(player.getUniqueId()) == null) {
				if(current.getTitle().contains("Class")) {
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
