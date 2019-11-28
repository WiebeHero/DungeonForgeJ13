package me.WiebeHero.EnchantmentAPI;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import me.WiebeHero.CustomEvents.DFShootBowEvent;

public abstract class CommandFile implements Listener{
	void serverShutdown() {};
	void activateEnchantment(LivingEntity damager, int level) {};
	void activateEnchantment(LivingEntity entity, int level, boolean equiped) {};
	void activateEnchantment(LivingEntity entity, int level, boolean equiped, PlayerArmorChangeEvent event) {};
	void activateEnchantment(LivingEntity entity, int level, PlayerArmorChangeEvent event) {};
	void activateEnchantment(LivingEntity entity, int level, EntityDamageEvent event) {};
	void activateEnchantment(LivingEntity entity, int level, DFShootBowEvent event) {};
	void activateEnchantment(LivingEntity entity, int level, ProjectileHitEvent event) {};
	void activateEnchantment(LivingEntity entity, int level, EntityDeathEvent event) {};
	void activateEnchantment(LivingEntity entity, int level, PlayerDeathEvent event) {};
	void activateEnchantment(LivingEntity entity, int level, PlayerInteractEvent event) {};
    void activateEnchantment(LivingEntity entity, int level, EntityDamageByEntityEvent event) {};
    void activateEnchantment(LivingEntity entity, int level, PlayerItemConsumeEvent event) {};
    void activateEnchantment(LivingEntity entity, int level, FoodLevelChangeEvent event) {};
	void activateEnchantment(LivingEntity damager, LivingEntity victim, int level) {};
	void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, ProjectileHitEvent event) {};
	void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDeathEvent event) {};
	void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, PlayerDeathEvent event) {};
	void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, PlayerInteractEvent event) {};
	void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageEvent event) {};
    void activateEnchantment(LivingEntity damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {};
}