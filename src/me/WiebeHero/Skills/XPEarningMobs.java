package me.WiebeHero.Skills;

import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtinjector.NBTInjector;
import me.WiebeHero.CustomEnchantments.CCT;
import net.md_5.bungee.api.ChatColor;

public class XPEarningMobs implements Listener{
	SkillJoin join = new SkillJoin();
	@EventHandler
	public void xpEarnMobs10(EntityDeathEvent event) {
		if(event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {
			if(event.getEntity() instanceof LivingEntity) {
				Player player = event.getEntity().getKiller();
				DFPlayer dfPlayer = new DFPlayer().getPlayer(player);
				LivingEntity victim = event.getEntity();
				int level = dfPlayer.getLevel();
				int xp = dfPlayer.getExperience();
				int maxxp = dfPlayer.getMaxExperience();
				int finalXP = 0;
				Entity ent = NBTInjector.patchEntity(victim);
				NBTCompound comp = NBTInjector.getNbtData(ent);
				if(comp.hasKey("SpawnerUUID")) {
					int tier = comp.getInteger("Tier");
					int levelMob = comp.getInteger("Level");
					String lore = "";
					ItemStack item = player.getInventory().getItemInMainHand();
					if(item != null) {
						if(item.hasItemMeta()) {
							if(item.getItemMeta().hasLore()) {
								for(String l : item.getItemMeta().getLore()) {
									if(l.contains(ChatColor.stripColor("XP Boost"))) {
										lore = ChatColor.stripColor(l);
									}
								}
							}
						}
					}
					//-----------------------------------------------------------------------------------------------------------------------------------------
					//XP Adding
					//-----------------------------------------------------------------------------------------------------------------------------------------
					int i1 = 0;
					if(tier == 0) {
						i1 = new Random().nextInt(3) + 3 + 2 * levelMob;
					}
					if(tier == 1) {
						i1 = new Random().nextInt(50) + 50 + 4 * levelMob;
					}
					else if(tier == 2) {
						i1 = new Random().nextInt(70) + 70 + 5 * levelMob;
					}
					else if(tier == 3) {
						i1 = new Random().nextInt(90) + 90 + 6 * levelMob;
					}
					else if(tier == 4) {
						i1 = new Random().nextInt(110) + 110 + 7 * levelMob;
					}
					else if(tier == 5) {
						i1 = new Random().nextInt(130) + 130 + 8 * levelMob;
					}
					if(lore.contains("XP Boost")) {
						String check = lore.replaceAll("[^\\d.]", "");
						int eLevel = Integer.parseInt(check);
						i1 = i1 * (eLevel / 2 + 1);
					}
					finalXP = i1 + xp;
				}
				else {
					if(victim.getType().equals(EntityType.ZOMBIE)) {
						finalXP = 4 + xp;
					}
					else if(victim.getType().equals(EntityType.SKELETON)) {
						finalXP = 4 + xp;
					}
					else if(victim.getType().equals(EntityType.SPIDER) || victim.getType().equals(EntityType.CAVE_SPIDER)) {
						finalXP = 5 + xp;
					}
					else if(victim.getType().equals(EntityType.CREEPER)) {
						finalXP = 11 + xp;
					}
					else if(victim.getType().equals(EntityType.ENDERMAN)) {
						finalXP = 16 + xp;
					}
					else if(victim.getType().equals(EntityType.BLAZE)) {
						finalXP = 13 + xp;
					}
					else if(victim.getType().equals(EntityType.WITCH)) {
						finalXP = 8 + xp;
					}
					else if(victim.getType().equals(EntityType.VILLAGER)) {
						finalXP = 20 + xp;
					}
					else if(victim.getType().equals(EntityType.IRON_GOLEM)) {
						finalXP = 25 + xp;
					}
					else if(victim.getType().equals(EntityType.CHICKEN)) {
						finalXP = 1 + xp;
					}
					else if(victim.getType().equals(EntityType.SHEEP)) {
						finalXP = 2 + xp;
					}
					else if(victim.getType().equals(EntityType.COW)) {
						finalXP = 3 + xp;
					}
					else if(victim.getType().equals(EntityType.PIG)) {
						finalXP = 2 + xp;
					}
					else if(victim.getType().equals(EntityType.PIG_ZOMBIE)) {
						finalXP = 6 + xp;
					}
					else if(victim.getType().equals(EntityType.HORSE)) {
						finalXP = 3 + xp;
					}
					else if(victim.getType().equals(EntityType.PLAYER)) {
						Player p = (Player) event.getEntity();
						DFPlayer dfPlayer2 = new DFPlayer().getPlayer(p);
						int levelVictim = dfPlayer2.getLevel();
						if(levelVictim > 1) {
							int i2 = new Random().nextInt(50 + 5 * levelVictim) + 50 + 5 * levelVictim;
							finalXP = i2 + xp;
						}
					}
				}
				if(level < 100) {
					if(finalXP >= maxxp) {
						for(int i = level; i < 100; i++) {
							if(finalXP >= maxxp) {
								finalXP = finalXP - maxxp;
								maxxp = Math.abs(maxxp);
								level++;
								maxxp = (int)(double)(maxxp / 100.00 * 107.00);
								dfPlayer.addLevel(1);
								dfPlayer.setExperience(finalXP);
								dfPlayer.setMaxExperience(maxxp);
								dfPlayer.addSkillPoints(3);
							}
							else {
								break;
							}
						}
	    	    		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 0.5);
	    	            player.sendMessage(new CCT().colorize("&aYou have leveled up to level: &6&l" + level));
					}
					else if(finalXP > 0){
						dfPlayer.setExperience(finalXP);
					}
				}
				float barprogress = (float) finalXP / maxxp;
				if(finalXP > 0){
					if(!(barprogress > 1)) {
						player.setLevel(level);
						player.setExp((float)barprogress);
					}
					else {
						player.setLevel(level);
						player.setExp((float)barprogress - 1.0F);
					}
				}
			}
		}
	}
	@EventHandler
	public void cancelXpSpawning(EntityDeathEvent event) {
		event.setDroppedExp(0);
	}
}
