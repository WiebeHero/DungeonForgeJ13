package me.WiebeHero.CustomMethods;

import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;

public class NewAttribute {
	public AttributeModifier newModifier(Attribute at, EquipmentSlot slot, double amount, Operation op){
		AttributeModifier mod = null;
		if(at == Attribute.GENERIC_ARMOR) {
			mod = new AttributeModifier(UUID.randomUUID(), "generic.armor", amount, op, slot);
		}
		else if(at == Attribute.GENERIC_ARMOR_TOUGHNESS) {
			mod = new AttributeModifier(UUID.randomUUID(), "generic.armorToughness", amount, op, slot);
		}
		else if(at == Attribute.GENERIC_MAX_HEALTH) {
			mod = new AttributeModifier(UUID.randomUUID(), "generic.maxHealth", amount, op, slot);
		}
		else if(at == Attribute.GENERIC_ATTACK_DAMAGE) {
			mod = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", amount, op, slot);
		}
		else if(at == Attribute.GENERIC_ATTACK_SPEED) {
			mod = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", amount, op, slot);
		}
		else if(at == Attribute.GENERIC_FLYING_SPEED) {
			mod = new AttributeModifier(UUID.randomUUID(), "generic.flyingSpeed", amount, op, slot);
		}
		else if(at == Attribute.GENERIC_FOLLOW_RANGE) {
			mod = new AttributeModifier(UUID.randomUUID(), "generic.followRange", amount, op, slot);
		}
		else if(at == Attribute.GENERIC_KNOCKBACK_RESISTANCE) {
			mod = new AttributeModifier(UUID.randomUUID(), "generic.attackKnockback", amount, op, slot);
		}
		else if(at == Attribute.GENERIC_LUCK) {
			mod = new AttributeModifier(UUID.randomUUID(), "generic.luck", amount, op, slot);
		}
		else if(at == Attribute.GENERIC_MOVEMENT_SPEED) {
			mod = new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed", amount, op, slot);
		}
		else if(at == Attribute.HORSE_JUMP_STRENGTH) {
			mod = new AttributeModifier(UUID.randomUUID(), "generic.jumpStrength", amount, op, slot);
		}
		else if(at == Attribute.ZOMBIE_SPAWN_REINFORCEMENTS) {
			mod = new AttributeModifier(UUID.randomUUID(), "generic.spawnReinforcements", amount, op, slot);
		}
		return mod;
	}
}
