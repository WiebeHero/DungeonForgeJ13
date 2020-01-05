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
import me.WiebeHero.Scoreboard.DFScoreboard;
import net.md_5.bungee.api.ChatColor;

public class XPEarningMobs implements Listener{
	DFScoreboard board = new DFScoreboard();
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
				if(!(victim instanceof Player)) {
					Entity ent = NBTInjector.patchEntity(victim);
					NBTCompound comp = NBTInjector.getNbtData(ent);
					if(comp.hasKey("SpawnerUUID")) {
						int tier = comp.getInteger("Tier");
						int levelMob = comp.getInteger("Level");
						//-----------------------------------------------------------------------------------------------------------------------------------------
						//XP Adding
						//-----------------------------------------------------------------------------------------------------------------------------------------
						int i1 = 0;
						if(tier == 0) {
							i1 = new Random().nextInt(3) + 3 + 2 * levelMob;
						}
						if(tier == 1) {
							i1 = new Random().nextInt(100) + 100 + 2 * levelMob;
						}
						else if(tier == 2) {
							i1 = new Random().nextInt(135) + 135 + 3 * levelMob;
						}
						else if(tier == 3) {
							i1 = new Random().nextInt(170) + 170 + 4 * levelMob;
						}
						else if(tier == 4) {
							i1 = new Random().nextInt(205) + 205 + 5 * levelMob;
						}
						else if(tier == 5) {
							i1 = new Random().nextInt(240) + 240 + 6 * levelMob;
						}
						else {
							i1 = new Random().nextInt(3) + 3 + 2 * levelMob;
						}
						finalXP = i1 + xp;
					}
				}
				else {
					DFPlayer df = new DFPlayer().getPlayer(victim);
					int otherLevel = df.getLevel();
					int i1 = 0;
					i1 = new Random().nextInt(7 * otherLevel) + 4 * otherLevel;
					finalXP = i1 + xp;
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
						board.generateScoreboard(player);
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
