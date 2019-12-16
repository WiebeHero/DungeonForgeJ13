package me.WiebeHero.CustomMethods;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.NBTTagDouble;
import net.minecraft.server.v1_13_R2.NBTTagInt;
import net.minecraft.server.v1_13_R2.NBTTagList;
import net.minecraft.server.v1_13_R2.NBTTagString;

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
	public ItemStack stripModifier(ItemStack item, ArrayList<Attribute> attrList, String slot) {
		ItemStack newItem = item;
		net.minecraft.server.v1_13_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(newItem);
  	    NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
  		NBTTagList modifiers = new NBTTagList();
  		NBTTagCompound damage = new NBTTagCompound();
  		NBTTagCompound speed = new NBTTagCompound();	
  		NBTTagCompound armor = new NBTTagCompound();
  		NBTTagCompound toughness = new NBTTagCompound();
  		if(attrList.contains(Attribute.GENERIC_ATTACK_DAMAGE)) {
  			damage.set("AttributeName", new NBTTagString("generic.attackDamage"));
  			damage.set("Name", new NBTTagString("generic.attackDamage"));
  			damage.set("Amount", new NBTTagDouble(0));
  			damage.set("Operation", new NBTTagInt(0));
  			damage.set("UUIDLeast", new NBTTagInt(894654));
  			damage.set("UUIDMost", new NBTTagInt(2872));
  			damage.set("Slot", new NBTTagString(slot));
  			modifiers.add(damage);
  		}
  		if(attrList.contains(Attribute.GENERIC_ATTACK_SPEED)) {
  			speed.set("AttributeName", new NBTTagString("generic.attackSpeed"));
  			speed.set("Name", new NBTTagString("generic.attackSpeed"));
  			speed.set("Amount", new NBTTagDouble(0));
  			speed.set("Operation", new NBTTagInt(0));
  			speed.set("UUIDLeast", new NBTTagInt(10001));
  			speed.set("UUIDMost", new NBTTagInt(10001));
  			speed.set("Slot", new NBTTagString(slot));
  			modifiers.add(speed);
  		}
  		if(attrList.contains(Attribute.GENERIC_ARMOR)) {
  			armor.set("AttributeName", new NBTTagString("generic.armor"));
  			armor.set("Name", new NBTTagString("generic.armor"));
  			armor.set("Amount", new NBTTagDouble(0));
  			armor.set("Operation", new NBTTagInt(0));
  			if(slot.equalsIgnoreCase("head")) {
	  			armor.set("UUIDLeast", new NBTTagInt(1));
	  			armor.set("UUIDMost", new NBTTagInt(1));
  			}
  			else if(slot.equalsIgnoreCase("chest")) {
	  			armor.set("UUIDLeast", new NBTTagInt(2));
	  			armor.set("UUIDMost", new NBTTagInt(2));
  			}
  			else if(slot.equalsIgnoreCase("legs")) {
	  			armor.set("UUIDLeast", new NBTTagInt(3));
	  			armor.set("UUIDMost", new NBTTagInt(3));
  			}
  			else if(slot.equalsIgnoreCase("feet")) {
	  			armor.set("UUIDLeast", new NBTTagInt(4));
	  			armor.set("UUIDMost", new NBTTagInt(4));
  			}
  			armor.set("Slot", new NBTTagString(slot));
  			modifiers.add(armor);
  		}
  		if(attrList.contains(Attribute.GENERIC_ARMOR)) {
  			toughness.set("AttributeName", new NBTTagString("generic.armorToughness"));
  			toughness.set("Name", new NBTTagString("generic.armorToughness"));
  			toughness.set("Amount", new NBTTagDouble(0));
  			toughness.set("Operation", new NBTTagInt(0));
  			if(slot.equalsIgnoreCase("head")) {
  				toughness.set("UUIDLeast", new NBTTagInt(5));
  				toughness.set("UUIDMost", new NBTTagInt(5));
  			}
  			if(slot.equalsIgnoreCase("chest")) {
  				toughness.set("UUIDLeast", new NBTTagInt(6));
  				toughness.set("UUIDMost", new NBTTagInt(6));
  			}
  			if(slot.equalsIgnoreCase("legs")) {
  				toughness.set("UUIDLeast", new NBTTagInt(7));
  				toughness.set("UUIDMost", new NBTTagInt(7));
  			}
  			if(slot.equalsIgnoreCase("feet")) {
  				toughness.set("UUIDLeast", new NBTTagInt(8));
	  			toughness.set("UUIDMost", new NBTTagInt(8));
  			}
  			toughness.set("Slot", new NBTTagString(slot));
  			modifiers.add(toughness);
  		}
		compound.set("AttributeModifiers", modifiers);
		nmsStack.setTag(compound);
		nmsStack.save(compound);
		ItemStack finalItem = CraftItemStack.asBukkitCopy(nmsStack);
		return finalItem;
	}
}
