package Skills;

import java.util.HashMap;
import java.util.UUID;

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
	HashMap<UUID, Double> speed = new HashMap<UUID, Double>();
	HashMap<UUID, Long> dur = new HashMap<UUID, Long>();
	SkillJoin join = new SkillJoin();
	ClassPride p = new ClassPride();
	ClassGreed g = new ClassGreed();
	public void attackSpeedRun(Player p, long duration, double multiplier) {
		if(duration != 0 && multiplier != 0.0) {
			if(!speed.containsKey(p.getUniqueId()) && !dur.containsKey(p.getUniqueId())) {
				speed.put(p.getUniqueId(), multiplier);
				dur.put(p.getUniqueId(), duration);
				new BukkitRunnable() {
					public void run() {
						if(dur.containsKey(p.getUniqueId())) {
							if(dur.get(p.getUniqueId()) == 0L) {
								speed.remove(p.getUniqueId());
								dur.remove(p.getUniqueId());
								cancel();
							}
							else {
								dur.put(p.getUniqueId(), dur.get(p.getUniqueId()) - 1L);
							}
						}
						else {
							cancel();
						}
					}
				}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
			}
			else {
				speed.put(p.getUniqueId(), duration + multiplier);
				dur.put(p.getUniqueId(), duration + dur.get(p.getUniqueId()));
			}
		}
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
								double currentMult = 1.0;
								if(speed.containsKey(p.getUniqueId()) && dur.containsKey(p.getUniqueId())) {
									currentMult = currentMult + speed.get(p.getUniqueId());
								}
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
									p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100 * (100 + level * 0.60) * currentMult);
								}
								else if(join.getClassList().get(p.getUniqueId()).equals("Sloth") || join.getClassList().get(p.getUniqueId()).equals("Envy")) {
									p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100 * (100 + level * 0.20) * currentMult);
								}
								else {
									p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed / 100 * (100 + level * 0.40) * currentMult);
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
		attackSpeedRun(player, 0L, 0.0D);
	}
	@EventHandler
	public void attackSpeedItemDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		attackSpeedRun(player, 0L, 0.0D);
	}
	@EventHandler
	public void attackSpeedItemPickup(@SuppressWarnings("deprecation") PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		attackSpeedRun(player, 0L, 0.0D);
	}
	@EventHandler
	public void attackSpeedInvClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			attackSpeedRun(player, 0L, 0.0D);
		}
	}
	@EventHandler
	public void attackSpeedSwitchHand(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		attackSpeedRun(player, 0L, 0.0D);
	}
}
