package Skills;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
									armorD = armorD + armorDT / 100 * (join.getDFCalList().get(p.getUniqueId()) + join.getDFExtraList().get(p.getUniqueId()));
									armorT = armorT + armorTT / 100 * (join.getDFCalList().get(p.getUniqueId()) + join.getDFExtraList().get(p.getUniqueId()));
									
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
								armorT = armorT + (armorTT / 100.00 * (join.getDFCalList().get(p.getUniqueId()) + join.getDFExtraList().get(p.getUniqueId())));
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
	public void armorSwitch(PlayerArmorChangeEvent event) {
		Player player = event.getPlayer();
		runDefense(player);
	}
}
