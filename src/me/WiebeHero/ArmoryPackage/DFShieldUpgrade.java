package me.WiebeHero.ArmoryPackage;

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
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Novis.NovisEnchantmentGetting;

public class DFShieldUpgrade implements Listener{
	private NovisEnchantmentGetting enchant;
	private DFPlayerManager dfManager;
	public DFShieldUpgrade(DFPlayerManager dfManager, NovisEnchantmentGetting enchant) {
		this.dfManager = dfManager;
		this.enchant = enchant;
	}
	@EventHandler
	public void weapons(EntityDeathEvent event) {
		LivingEntity victim = (LivingEntity) event.getEntity();
		EntityDamageEvent ede = victim.getLastDamageCause();
		DamageCause dc = ede.getCause();
		if(event.getEntity().getKiller() instanceof Player && (dc == DamageCause.ENTITY_ATTACK || dc == DamageCause.PROJECTILE)){
			Player damager = (Player) event.getEntity().getKiller();
			ItemStack i = damager.getInventory().getItemInOffHand();
			if(i != null) {
				NBTItem item = new NBTItem(i);
				if(item.hasKey("Upgradeable")) {
					if(item.hasKey("ShieldKey")) {
						int xp = item.getInteger("XP");
			    		int totalxpearned = 0;
			    		if(!(victim instanceof Player)) {
							Entity ent = NBTInjector.patchEntity(victim);
							NBTCompound comp = NBTInjector.getNbtData(ent);
							if(comp.hasKey("SpawnerUUID")) {
								int level = comp.getInteger("Level");
								int tier = comp.getInteger("Tier");
								if(tier == 0) {
									totalxpearned = xp + 1;
								}
								else {
									totalxpearned = xp + 5 + level;
								}
								//-----------------------------------------------------------------------------------------------------------------------------------------
								//XP Adding
								//-----------------------------------------------------------------------------------------------------------------------------------------
								
							}
							else {
								totalxpearned = xp + 1;
							}
						}
						else {
							DFPlayer dfVictim = dfManager.getEntity(victim);
							int level = dfVictim.getLevel();
							totalxpearned = level + xp;
						}
						int itemLevel = item.getInteger("Level");
						if(itemLevel != 15) {
							DFItemXpGainEvent e = new DFItemXpGainEvent(damager, i, totalxpearned, EquipmentSlot.OFF_HAND, dfManager, enchant);
							Bukkit.getPluginManager().callEvent(e);
				    	}
					}
			    }
			}
		}
	}
}

