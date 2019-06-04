package Skills;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.md_5.bungee.api.ChatColor;

public class AttackSpeed implements Listener{
	SkillJoin join = new SkillJoin();
	ClassPride p = new ClassPride();
	ClassGreed g = new ClassGreed();
	public void attackSpeedRun(Player p, double multiplier) {
		new BukkitRunnable() {
			public void run() {
				int level = 0;
				if(join.getASList().get(p.getUniqueId()) != null) {
					level = join.getASList().get(p.getUniqueId());
				}
				ItemStack item = p.getInventory().getItemInMainHand();
				if(item != null) {
					if(item.hasItemMeta()) {
						if(item.getItemMeta().hasLore()) {
							if(item.getItemMeta().getLore().toString().contains("Attack Speed")) {
								String check1 = "";
								String check2 = "";
								for(String s : item.getItemMeta().getLore()) {
									if(s.contains("Attack Speed")) {
										check1 = ChatColor.stripColor(s);
									}
									else if(s.contains("Attack Damage")) {
										check2 = ChatColor.stripColor(s);
									}
								}
								check1 = check1.replaceAll("[^\\d.]", "");
								double attackSpeed = Double.parseDouble(check1);
								check2 = check2.replaceAll("[^\\d.]", "");
								double attackDamage = Double.parseDouble(check2);
								p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(attackDamage + 1);
								if(join.getClassList().get(p.getUniqueId()).equals("Pride") || join.getClassList().get(p.getUniqueId()).equals("Greed")) {
									p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100 * (100 + level * 0.60) * multiplier);
								}
								else if(join.getClassList().get(p.getUniqueId()).equals("Sloth") || join.getClassList().get(p.getUniqueId()).equals("Envy")) {
									p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100 * (100 + level * 0.20) * multiplier);
								}
								else {
									p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100 * (100 + level * 0.40) * multiplier);
								}
							}
							else {
								p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
								p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
							}
						}
						else {
							p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
							p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
						}
					}
					else {
						p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
						p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
					}
				}
				else {
					p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
					p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
				}
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 1L);
	}
	@EventHandler
	public void attackSpeedItemChange(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		if(p.getPrideList().contains(player.getUniqueId()) || g.getGreedList().contains(player.getUniqueId())) {
			int level = join.getLevelList().get(player.getUniqueId());
			double attackS = 20 + level * 0.30;
			attackSpeedRun(player, (1.00 + attackS / 100));
		}
		else {
			attackSpeedRun(player, 1.00);
		}
	}
	@EventHandler
	public void attackSpeedItemDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if(p.getPrideList().contains(player.getUniqueId()) || g.getGreedList().contains(player.getUniqueId())) {
			int level = join.getLevelList().get(player.getUniqueId());
			double attackS = 20 + level * 0.30;
			attackSpeedRun(player, (1.00 + attackS / 100));
		}
		else {
			attackSpeedRun(player, 1.00);
		}
	}
	@EventHandler
	public void attackSpeedItemPickup(@SuppressWarnings("deprecation") PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		if(p.getPrideList().contains(player.getUniqueId()) || g.getGreedList().contains(player.getUniqueId())) {
			int level = join.getLevelList().get(player.getUniqueId());
			double attackS = 20 + level * 0.30;
			attackSpeedRun(player, (1.00 + attackS / 100));
		}
		else {
			attackSpeedRun(player, 1.00);
		}
	}
	@EventHandler
	public void attackSpeedInvClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			if(p.getPrideList().contains(player.getUniqueId()) || g.getGreedList().contains(player.getUniqueId())) {
				int level = join.getLevelList().get(player.getUniqueId());
				double attackS = 20 + level * 0.30;
				attackSpeedRun(player, (1.00 + attackS / 100));
			}
			else {
				attackSpeedRun(player, 1.00);
			}
		}
	}
	@EventHandler
	public void attackSpeedSwitchHand(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if(p.getPrideList().contains(player.getUniqueId()) || g.getGreedList().contains(player.getUniqueId())) {
			int level = join.getLevelList().get(player.getUniqueId());
			double attackS = 20 + level * 0.30;
			attackSpeedRun(player, (1.00 + attackS / 100));
		}
		else {
			attackSpeedRun(player, 1.00);
		}
	}
}
