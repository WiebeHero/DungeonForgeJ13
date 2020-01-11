package me.WiebeHero.ArmoryPackage;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtinjector.NBTInjector;
import me.WiebeHero.CustomEvents.DFItemXpGainEvent;
import me.WiebeHero.Novis.NovisEnchantmentGetting;
import me.WiebeHero.Skills.DFPlayer;
import me.WiebeHero.Skills.EffectSkills;

public class DFArmorUpgrade implements Listener{
	public NovisEnchantmentGetting enchant = new NovisEnchantmentGetting();
	public String colorize(String msg)
    {
        String coloredMsg = "";
        for(int i = 0; i < msg.length(); i++)
        {
            if(msg.charAt(i) == '&')
                coloredMsg += '§';
            else
                coloredMsg += msg.charAt(i);
        }
        return coloredMsg;
    }
	EffectSkills sk = new EffectSkills();
	@EventHandler
	public void weapons(EntityDeathEvent event) {
		LivingEntity victim = (LivingEntity) event.getEntity();
		EntityDamageEvent ede = victim.getLastDamageCause();
		DamageCause dc = ede.getCause();
		if(event.getEntity().getKiller() instanceof Player && (dc == DamageCause.ENTITY_ATTACK || dc == DamageCause.PROJECTILE)){
			Player damager = (Player) event.getEntity().getKiller();
			ItemStack items[] = damager.getInventory().getArmorContents();
			for(ItemStack i : items) {
				if(i != null) {
					NBTItem item = new NBTItem(i);
					if(item.hasKey("Upgradeable")) {
						if(item.hasKey("ArmorKey")) {
							int xp = item.getInteger("XP");
				    		int totalxpearned = 0;
							DFPlayer dfPlayer = new DFPlayer().getPlayer(damager);
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
										i1 = new Random().nextInt(50) + 50 + 2 * levelMob;
									}
									else if(tier == 2) {
										i1 = new Random().nextInt(70) + 70 + 3 * levelMob;
									}
									else if(tier == 3) {
										i1 = new Random().nextInt(90) + 90 + 4 * levelMob;
									}
									else if(tier == 4) {
										i1 = new Random().nextInt(110) + 110 + 5 * levelMob;
									}
									else if(tier == 5) {
										i1 = new Random().nextInt(130) + 130 + 6 * levelMob;
									}
									else {
										i1 = new Random().nextInt(3) + 3 + 2 * levelMob;
									}
									totalxpearned = i1 + xp;
								}
							}
							else {
								int level = dfPlayer.getLevel();
								int i1 = 0;
								i1 = new Random().nextInt(7 * level) + 4 * level;
								totalxpearned = i1 + xp;
							}

							int itemLevel = item.getInteger("Level");
		    				if(itemLevel != 15) {
			    				if(i.getType().toString().contains("HELMET")) {
			    					DFItemXpGainEvent e = new DFItemXpGainEvent(damager, i, totalxpearned, EquipmentSlot.HEAD);
			    					Bukkit.getPluginManager().callEvent(e);
			    				}
			    				else if(i.getType().toString().contains("CHESTPLATE")) {
			    					DFItemXpGainEvent e = new DFItemXpGainEvent(damager, i, totalxpearned, EquipmentSlot.CHEST);
			    					Bukkit.getPluginManager().callEvent(e);
			    				}
			    				else if(i.getType().toString().contains("LEGGINGS")) {
			    					DFItemXpGainEvent e = new DFItemXpGainEvent(damager, i, totalxpearned, EquipmentSlot.LEGS);
			    					Bukkit.getPluginManager().callEvent(e);
			    				}
			    				else if(i.getType().toString().contains("BOOTS")) {
			    					DFItemXpGainEvent e = new DFItemXpGainEvent(damager, i, totalxpearned, EquipmentSlot.FEET);
			    					Bukkit.getPluginManager().callEvent(e);
			    				}
					    	}
						}
				    }
				}
			}
		}
	}
}

