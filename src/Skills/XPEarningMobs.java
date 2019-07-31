package Skills;

import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import Skills.SkillEnum.Skills;
import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.Spawners.SpawnerList;
import net.md_5.bungee.api.ChatColor;

public class XPEarningMobs implements Listener{
	PlayerClass pc = new PlayerClass();
	SkillJoin join = new SkillJoin();
	@EventHandler
	public void xpEarnMobs10(EntityDeathEvent event) {
		if(event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {
			if(event.getEntity() instanceof LivingEntity) {
				Player player = event.getEntity().getKiller();
				LivingEntity victim = event.getEntity();
				int level = pc.getSkill(player.getUniqueId(), Skills.LEVEL);
				int xp = pc.getSkill(player.getUniqueId(), Skills.XP);
				int maxxp = pc.getSkill(player.getUniqueId(), Skills.MAXXP);
				int skillPoints = pc.getSkill(player.getUniqueId(), Skills.SKILL_POINTS);
				int finalXP = 0;
				if(SpawnerList.getMobList().containsKey(event.getEntity().getUniqueId())) {
					int tier = SpawnerList.getMobList().get(event.getEntity().getUniqueId());
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
					int i1 = new Random().nextInt(50 + 100 * tier) + 100 * tier;
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
						int levelVictim = pc.getSkill(player.getUniqueId(), Skills.LEVEL);
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
								int finalSkillPoints = skillPoints + 3;
								maxxp = (int)(double)(maxxp / 100.00 * 107.00);
								pc.setSkill(player.getUniqueId(), Skills.LEVEL, level);
								pc.setSkill(player.getUniqueId(), Skills.XP, finalXP);
								pc.setSkill(player.getUniqueId(), Skills.MAXXP, maxxp);
								pc.setSkill(player.getUniqueId(), Skills.SKILL_POINTS, finalSkillPoints);
							}
							else {
								break;
							}
						}
	    	    		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 0.5);
	    	            player.sendMessage(new ColorCodeTranslator().colorize("&aYou have leveled up to level: &6&l" + level));
					}
					else if(finalXP > 0){
						pc.setSkill(player.getUniqueId(), Skills.XP, finalXP);
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
