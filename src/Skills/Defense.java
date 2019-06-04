package Skills;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class Defense implements Listener{
	SkillJoin join = new SkillJoin();
	public void runDefense(Player p) {
		new BukkitRunnable() {
			public void run() {
				int level = 0;
				if(join.getDFList().get(p.getUniqueId()) != null) {
					level = join.getDFList().get(p.getUniqueId());
				}
				double armorD = 0.0;
				double armorT = 0.0;
				for(ItemStack item : p.getInventory().getArmorContents()) {
					if(item != null) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasLore()) {
								if(item.getItemMeta().getLore().toString().contains("Armor Defense") && item.getItemMeta().getLore().toString().contains("Armor Toughness")) {
									String check1 = "";
									String check2 = "";
									for(String s : item.getItemMeta().getLore()) {
										if(s.contains("Armor Defense")) {
											check1 = ChatColor.stripColor(s);
										}
										else if(s.contains("Armor Toughness")){
											check2 = ChatColor.stripColor(s);
										}
									}
									check1 = check1.replaceAll("[^\\d.]", "");
									check2 = check2.replaceAll("[^\\d.]", "");
									double armorDT = Double.parseDouble(check1);
									double armorTT = Double.parseDouble(check2);
									if(join.getClassList().get(p.getUniqueId()).equals("Gluttony") || join.getClassList().get(p.getUniqueId()).equals("Sloth") || join.getClassList().get(p.getUniqueId()).equals("Pride")) {
										armorD = armorD + armorDT / 100 * (100 + level * 5.0);
										armorT = armorT + armorTT / 100 * (100 + level * 5.0);
									}
									else if(join.getClassList().get(p.getUniqueId()).equals("Wrath") || join.getClassList().get(p.getUniqueId()).equals("Greed")) {
										armorD = armorD + armorDT / 100 * (100 + level * 1.66);
										armorT = armorT + armorTT / 100 * (100 + level * 1.66);
									}
									else {
										armorD = armorD + armorDT / 100 * (100 + level * 3.33);
										armorT = armorT + armorTT / 100 * (100 + level * 3.33);
									}
								}
							}
						}
					}
				}
				ItemStack item = p.getInventory().getItemInOffHand();
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasLore()) {
							if(item.getItemMeta().getLore().toString().contains("Armor Toughness")) {
								String check1 = "";
								for(String s : item.getItemMeta().getLore()) {
									if(s.contains("Armor Toughness")) {
										check1 = ChatColor.stripColor(s);
									}
								}
								check1 = check1.replaceAll("[^\\d.]", "");
								double armorTT = Double.parseDouble(check1);
								if(join.getClassList().get(p.getUniqueId()).equals("Gluttony") || join.getClassList().get(p.getUniqueId()).equals("Sloth") || join.getClassList().get(p.getUniqueId()).equals("Pride")) {
									armorT = armorT + armorTT / 100 * (100 + level * 5.0);
								}
								else if(join.getClassList().get(p.getUniqueId()).equals("Wrath") || join.getClassList().get(p.getUniqueId()).equals("Greed")) {
									armorT = armorT + armorTT / 100 * (100 + level * 1.66);
								}
								else {
									armorT = armorT + armorTT / 100 * (100 + level * 3.33);
								}
							}
						}
					}
				}
				p.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(armorD);
				p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(armorT);
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 1L);
	}
	@EventHandler
	public void defenseInv(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Bukkit.broadcastMessage("Yes!");
			Player player = (Player) event.getWhoClicked();
			runDefense(player);
		}
	}
	@EventHandler
	public void defenseDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		runDefense(player);
	}
	@EventHandler
	public void defenseDrop(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		runDefense(player);
	}
	@EventHandler
	public void armorSwitch(PlayerArmorChangeEvent event) {
		Bukkit.broadcastMessage("Yes!");
	}
}
