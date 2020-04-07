package me.WiebeHero.CustomEntities;

import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.entity.Zombie;

import me.WiebeHero.CustomEnchantments.CCT;
import net.minecraft.server.v1_13_R2.ChatMessage;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.EntityZombie;
import net.minecraft.server.v1_13_R2.PathfinderGoalNearestAttackableTarget;

public class DFZombie extends EntityZombie{

	public DFZombie(World world) {
		super(((CraftWorld)world).getHandle());
		
		Zombie craftZombie = (Zombie) this.getBukkitLivingEntity();
		
		this.setBaby(false);
		
		craftZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100.00);
		
		this.setCustomName(new ChatMessage(new CCT().colorize("&7Custom Zombie")));
		this.setCustomNameVisible(true);
		
		this.targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
		
		this.getWorld().addEntity(this);
	}

}
