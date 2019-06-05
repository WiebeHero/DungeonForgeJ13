package Skills;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.Spawners.SpawnerList;
import net.md_5.bungee.api.ChatColor;

public class XPEarningMobs implements Listener{
	SkillJoin join = new SkillJoin();
	@EventHandler
	public void xpEarnMobs10(EntityDeathEvent event) {
		if(event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {
			if(event.getEntity() instanceof LivingEntity) {
				File f =  new File("plugins/CustomEnchantments/playerskillsDF.yml");
				YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
				//-----------------------------------------------------------------------------------------------------------------------------------------
				//File Calling
				//-----------------------------------------------------------------------------------------------------------------------------------------
				try{
					yml.load(f);
		        }
		        catch(IOException e){
		            e.printStackTrace();
		        } 
				catch (InvalidConfigurationException e) {
					e.printStackTrace();
				}
				//-----------------------------------------------------------------------------------------------------------------------------------------
				//Load File
				//-----------------------------------------------------------------------------------------------------------------------------------------
				Player player = event.getEntity().getKiller();
				LivingEntity victim = event.getEntity();
				int level = join.getLevelList().get(player.getUniqueId());
				int xp = join.getXPList().get(player.getUniqueId());
				int maxxp = join.getMXPList().get(player.getUniqueId());
				int skillPoints = join.getSkillPoints().get(player.getUniqueId());
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
						int levelVictim = join.getLevelList().get(victim.getUniqueId());
						if(levelVictim > 1) {
							int i2 = new Random().nextInt(50 + 5 * levelVictim) + 50 + 5 * levelVictim;
							finalXP = i2 + xp;
						}
					}
					//-----------------------------------------------------------------------------------------------------------------------------------------
					//Level check/xp setting
					//-----------------------------------------------------------------------------------------------------------------------------------------
				}
				if(level < 100) {
					if(finalXP >= maxxp) {
						int finalSkillPoints = skillPoints + 3;
						level++;
						xp = 0;
						int maxxpFinal = (int)(double)(maxxp / 100.00 * 107.00);
						join.getLevelList().put(player.getUniqueId(), level);
						join.getXPList().put(player.getUniqueId(), finalXP - maxxp);
						join.getMXPList().put(player.getUniqueId(), maxxpFinal);
						join.getSkillPoints().put(player.getUniqueId(), finalSkillPoints);
	    	    		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, (float) 0.5);
	    	            player.sendMessage(new ColorCodeTranslator().colorize("&aYou have leveled up to level: &6&l" + level));
					}
					else if(finalXP > 0){
						join.getXPList().put(player.getUniqueId(), finalXP);
					}
					//-----------------------------------------------------------------------------------------------------------------------------------------
					//Save File
					//-----------------------------------------------------------------------------------------------------------------------------------------
				}
				float barprogress = (float) finalXP / maxxp;
				if(finalXP > 0){
					if(!(barprogress > 1)) {
						player.setLevel(level);
						player.setExp((float)barprogress);
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
