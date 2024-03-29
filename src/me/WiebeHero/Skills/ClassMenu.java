package me.WiebeHero.Skills;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.DFPlayerPackage.DFPlayer;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.DFPlayerPackage.EnumSkills.Stats;
import me.WiebeHero.DFPlayerPackage.Enums.Classes;

public class ClassMenu implements Listener{
	
	private DFPlayerManager dfManager;
	
	public ClassMenu(DFPlayerManager dfManager) {
		this.dfManager = dfManager;
	}
	
	public void ClassSelect (Player player) {
		DFPlayer dfPlayer = this.dfManager.getEntity(player);
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 54, (new CCT().colorize("&6Class Selection: &b" + player.getName())));
		i.setItem(0, empty());
		i.setItem(1, empty());
		i.setItem(2, empty());
		i.setItem(3, empty());
		i.setItem(4, infoBook());
		i.setItem(5, infoSkills(dfPlayer));
		i.setItem(6, empty());
		i.setItem(7, empty());
		i.setItem(8, empty());
		i.setItem(9, empty());
		i.setItem(10, empty());
		i.setItem(11, empty());
		i.setItem(12, empty());
		i.setItem(13, empty());
		i.setItem(14, empty());
		i.setItem(15, empty());
		i.setItem(16, empty());
		i.setItem(17, empty());
		i.setItem(18, empty());
		i.setItem(19, classWrath(dfPlayer, true));
		i.setItem(20, empty());
		i.setItem(21, classLust(dfPlayer, true));
		i.setItem(22, empty());
		i.setItem(23, classGluttony(dfPlayer, true));
		i.setItem(24, empty());
		i.setItem(25, classGreed(dfPlayer, true));
		i.setItem(26, empty());
		i.setItem(27, empty());
		i.setItem(28, empty());
		i.setItem(29, empty());
		i.setItem(30, empty());
		i.setItem(31, empty());
		i.setItem(32, empty());
		i.setItem(33, empty());
		i.setItem(34, empty());
		i.setItem(35, empty());
		i.setItem(36, empty());
		i.setItem(37, empty());
		i.setItem(38, classSloth(dfPlayer, true));
		i.setItem(39, empty());
		i.setItem(40, classEnvy(dfPlayer, true));
		i.setItem(41, empty());
		i.setItem(42, classPride(dfPlayer, true));
		i.setItem(43, empty());
		i.setItem(44, empty());
		i.setItem(45, empty());
		i.setItem(46, empty());
		i.setItem(47, empty());
		i.setItem(48, empty());
		i.setItem(49, empty());
		i.setItem(50, empty());
		i.setItem(51, empty());
		i.setItem(52, empty());
		i.setItem(53, empty());
		player.openInventory(i);
	}
	public void ClassConfirm(Player player, String classTitle, ItemStack classHead) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 9, (new CCT().colorize("&6Class: " + classTitle)));
		i.setItem(0, empty());
		i.setItem(1, empty());
		i.setItem(2, yesButton());
		i.setItem(3, empty());
		i.setItem(4, classHead);
		i.setItem(5, empty());
		i.setItem(6, noButton());
		i.setItem(7, empty());
		i.setItem(8, empty());
		player.openInventory(i);
	}
	public ItemStack empty() {
		ItemStack i = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize(" "));
		i.setItemMeta(m);
		return i;
	}
	public ItemStack yesButton() {
		ItemStack i = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&aYes"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&aAccept this class."));
		lore.add(new CCT().colorize("&4Warning!"));
		lore.add(new CCT().colorize("&cIf you choose yes, you won't be"));
		lore.add(new CCT().colorize("&cable to change your class for a while!"));
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack noButton() {
		ItemStack i = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&cNo"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&cDeny this class and go back to the selection menu."));
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack infoBook() {
		ItemStack i = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&6&lInfo Classes"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7What are Classes?:"));
		lore.add(new CCT().colorize("&7Classes are sins you chose, each class"));
		lore.add(new CCT().colorize("&7Has 2 upsides (+) and 2 downsides (-)"));
		lore.add(new CCT().colorize("&7Upsides increase the base skill upgrade by 50%."));
		lore.add(new CCT().colorize("&7Downsides decrease the base skill upgrade by 50%."));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7What are Abilities?:"));
		lore.add(new CCT().colorize("&7Each class has their own ability that helps"));
		lore.add(new CCT().colorize("&7them in combat. These abilities become better based"));
		lore.add(new CCT().colorize("&7on your current Player Level. Or you can unlock Ability Modifiers"));
		lore.add(new CCT().colorize("&7that give bonusses to your ability."));
		lore.add(new CCT().colorize("&c&lIMPORTANT: &7Downsides dont have Ability Modifiers."));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7What are skills?:"));
		lore.add(new CCT().colorize("&7When you do /skills a menu will pop up. These skills range"));
		lore.add(new CCT().colorize("&7from Attack Damage to Defense. Hover over the feather to"));
		lore.add(new CCT().colorize("&7see what the skills do. You gain skill points from leveling up"));
		lore.add(new CCT().colorize("&7which you can put in skills that help you in PVP/PVE."));
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack infoSkills(DFPlayer dfPlayer) {
		ItemStack i = new ItemStack(Material.FEATHER, 1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(new CCT().colorize("&6&lInfo Skills"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&5Attack Damage:"));
		lore.add(new CCT().colorize("&7Increase the damage you deal through melee."));
		lore.add(new CCT().colorize("&7Base: &6" + dfPlayer.getBaseAtkIncrease() + "%+"));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&9Attack Speed"));
		lore.add(new CCT().colorize("&7You can attack faster."));
		lore.add(new CCT().colorize("&7Base: &6" + dfPlayer.getBaseSpdIncrease() + "%+"));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7Critical Chance:"));
		lore.add(new CCT().colorize("&7Have a chance to deal double damage. (Melee/Ranged)"));
		lore.add(new CCT().colorize("&7(NOTE: The maximum of Critical Chance is 100%"));
		lore.add(new CCT().colorize("&7if it goes past this number, then the remaining critical chance"));
		lore.add(new CCT().colorize("&7that has exceeded that cap will act as Critical Damage.)"));
		lore.add(new CCT().colorize("&7Base: &6" + dfPlayer.getBaseCrtIncrease() + "%+"));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&dRanged Damage:"));
		lore.add(new CCT().colorize("&7Increase the damage you deal through ranged attacks."));
		lore.add(new CCT().colorize("&7Base: &6" + dfPlayer.getBaseRndIncrease() + "%+"));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&cHealth:"));
		lore.add(new CCT().colorize("&7Increase your MAX HP."));
		lore.add(new CCT().colorize("&7Base: &6" + dfPlayer.getBaseHpIncrease() + "%+"));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&8Defense"));
		lore.add(new CCT().colorize("&7Increase the amount of armor points you recieve from armor."));
		lore.add(new CCT().colorize("&7(NOTE: The maximum of Armor Defense is 30, and Armor Toughness is 20."));
		lore.add(new CCT().colorize("&7This mechanic has been tested and turns out to be true. If your"));
		lore.add(new CCT().colorize("&7Armor Defense is higher then 30/Armor Toughness is higher then 20"));
		lore.add(new CCT().colorize("&7The remaining Armor Defense/Toughness will act as a damage decreasant."));
		lore.add(new CCT().colorize("&7After the damage has been decreased armor will be taken in account.)"));
		lore.add(new CCT().colorize("&7Base: &6" + dfPlayer.getBaseDfIncrease() + "%+"));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7If a skill is your upside, their base upgrade value is increased by 50%"));
		lore.add(new CCT().colorize("&7If a skill is your downside, their base upgrade value is decreased by 50%"));
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack classWrath(DFPlayer dfPlayer, boolean base) {
		ItemStack i = CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/c2fe7fd16c064dda14405ee4b710b3852533ec6a9b9b1aa8c02374273ea789b");
		SkullMeta m = (SkullMeta) i.getItemMeta();
		HashMap<Stats, Double> statList;
		if(base) {
			statList = Classes.WRATH.getBaseList();
		}
		else {
			statList = dfPlayer.getStatList();
		}
		double damage = statList.get(Stats.DAMAGE);
		double range = statList.get(Stats.RANGE);
		double cooldown = statList.get(Stats.COOLDOWN);
		m.setDisplayName(new CCT().colorize("&4&lWrath"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&4Attack Damage +"));
		lore.add(new CCT().colorize("&5Critical Chance +"));
		lore.add(new CCT().colorize("&cHealth -"));
		lore.add(new CCT().colorize("&8Defense -"));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7Ability: &6Hatred of the Wrath."));
		lore.add(new CCT().colorize("&7You strike your sorounding enemies with lightning"));
		lore.add(new CCT().colorize("&7dealing damage."));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&4Damage: " + String.format("%.2f", damage)));
		lore.add(new CCT().colorize("&6Range: " + String.format("%.2f", range)));
		lore.add(new CCT().colorize("&7Cooldown: &b" + cooldown + " Seconds"));
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack classLust(DFPlayer dfPlayer, boolean base) {
		ItemStack i = CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/78aea514edd846a3ff89024ba853cba873907dab62930d7f7c7ec83f66719086");
		SkullMeta m = (SkullMeta) i.getItemMeta();
		HashMap<Stats, Double> statList;
		if(base) {
			statList = Classes.LUST.getBaseList();
		}
		else {
			statList = dfPlayer.getStatList();
		}
		double heal = statList.get(Stats.HEAL);
		double damage = statList.get(Stats.DAMAGE);
		double range = statList.get(Stats.RANGE);
		double cooldown = statList.get(Stats.COOLDOWN);
		m.setDisplayName(new CCT().colorize("&d&lLust"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&cHealth +"));
		lore.add(new CCT().colorize("&dRanged Damage +"));
		lore.add(new CCT().colorize("&4Attack Damage -"));
		lore.add(new CCT().colorize("&9Attack Speed -"));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7Ability: &6Drain Energy"));
		lore.add(new CCT().colorize("&7You heal yourself for a certain % of your MAX HP and"));
		lore.add(new CCT().colorize("&7you deal a certain % of the enemies MAX HP"));
		lore.add(new CCT().colorize("&7as damage. You and your allies will recover by a certain %"));
		lore.add(new CCT().colorize("&7based on the damage dealt."));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&cHeal: " + String.format("%.2f", heal) + "%"));
		lore.add(new CCT().colorize("&4Damage: " + String.format("%.2f", damage) + "% of the enemies MAX HP"));
		lore.add(new CCT().colorize("&6Range: " + String.format("%.2f", range)));
		lore.add(new CCT().colorize("&bCooldown: " + String.format("%.2f", cooldown) + " Seconds"));
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack classGluttony(DFPlayer dfPlayer, boolean base) {
		ItemStack i = CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/61678bc9a728804ff41a88d56ed63ab3248a77a821a11718639b391ad522549e");
		SkullMeta m = (SkullMeta) i.getItemMeta();
		HashMap<Stats, Double> statList;
		if(base) {
			statList = Classes.GLUTTONY.getBaseList();
		}
		else {
			statList = dfPlayer.getStatList();
		}
		double heal = statList.get(Stats.HEAL);
		double increase = statList.get(Stats.DF_INC);
		double duration = statList.get(Stats.DURATION);
		double cooldown = statList.get(Stats.COOLDOWN);
		m.setDisplayName(new CCT().colorize("&c&lGluttony"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&cHealth +"));
		lore.add(new CCT().colorize("&8Defense +"));
		lore.add(new CCT().colorize("&4Attack Damage -"));
		lore.add(new CCT().colorize("&dRanged Damage -"));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7Ability: &6Natural Stance"));
		lore.add(new CCT().colorize("&7You let your life force flow throughout your body"));
		lore.add(new CCT().colorize("&7Causing you to heal and increase your defense."));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&cHeal: " + String.format("%.2f", heal) + "% of your MAX HP"));
		lore.add(new CCT().colorize("&8Defense Increase: " + String.format("%.2f", increase) + "%"));
		lore.add(new CCT().colorize("&bDuration: " + String.format("%.2f", duration) + " Seconds"));
		lore.add(new CCT().colorize("&bCooldown: " + String.format("%.2f", cooldown) + " Seconds"));
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack classGreed(DFPlayer dfPlayer, boolean base) {
		ItemStack i = CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/ec4d37a83af45d30e4b6e4793f9966d0034ab08f825e1e71a91cc2acb8c92178");
		SkullMeta m = (SkullMeta) i.getItemMeta();
		HashMap<Stats, Double> statList;
		if(base) {
			statList = Classes.GREED.getBaseList();
		}
		else {
			statList = dfPlayer.getStatList();
		}
		double defense = statList.get(Stats.DF_DEC);
		double attackS = statList.get(Stats.SPD_INC);
		double duration = statList.get(Stats.DURATION);
		double cooldown = statList.get(Stats.COOLDOWN);
		m.setDisplayName(new CCT().colorize("&9&lGreed"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&dRanged Damage +"));
		lore.add(new CCT().colorize("&9Attack Speed +"));
		lore.add(new CCT().colorize("&cHealth -"));
		lore.add(new CCT().colorize("&8Defense -"));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7Ability: &6Hunting Arrow"));
		lore.add(new CCT().colorize("&7You prepare a special arrow for hunting down prey."));
		lore.add(new CCT().colorize("&7Hitting an enemy with this arrow causes their defense"));
		lore.add(new CCT().colorize("&7to decrease for a few seconds."));
		lore.add(new CCT().colorize("&7Additionally, your attack speed increases on activation."));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&8Defense Decrease: "  + String.format("%.2f", defense) + "%"));
		lore.add(new CCT().colorize("&9Attack Speed Increase: "  + String.format("%.2f", attackS) + "%"));
		lore.add(new CCT().colorize("&bDuration: "  + String.format("%.2f", duration) + " Seconds"));
		lore.add(new CCT().colorize("&bCooldown: "  + String.format("%.2f", cooldown) + " Seconds"));
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack classSloth(DFPlayer dfPlayer, boolean base) {
		ItemStack i = CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/ca35eb10b94e888427fb23c783082658ceb81f3cf5d0aad25d7d41a194b26");
		SkullMeta m = (SkullMeta) i.getItemMeta();
		HashMap<Stats, Double> statList;
		if(base) {
			statList = Classes.SLOTH.getBaseList();
		}
		else {
			statList = dfPlayer.getStatList();
		}
		double arrow = statList.get(Stats.ARROW_COUNT);
		double damage = statList.get(Stats.DAMAGE);
		double defense = statList.get(Stats.DF_INC);
		double duration = statList.get(Stats.DURATION);
		double cooldown = statList.get(Stats.COOLDOWN);
		m.setDisplayName(new CCT().colorize("&5&lSloth"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&5Critical Chance +"));
		lore.add(new CCT().colorize("&8Defense +"));
		lore.add(new CCT().colorize("&dRanged Damage -"));
		lore.add(new CCT().colorize("&9Attack Speed -"));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7Ability: &6Hedgehog"));
		lore.add(new CCT().colorize("&7You fire the thorns you have on your back."));
		lore.add(new CCT().colorize("&7These thorns deal damage to enemies in the firing range."));
		lore.add(new CCT().colorize("&7Additionally, your defense is also increased for a few seconds."));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&6Arrow Amount: " + String.format("%.0f", arrow)));
		lore.add(new CCT().colorize("&4Damage: " + String.format("%.2f", damage)));
		lore.add(new CCT().colorize("&8Defense Increase: " + String.format("%.2f", defense)));
		lore.add(new CCT().colorize("&bDuration: " + String.format("%.2f", duration) + " Seconds"));
		lore.add(new CCT().colorize("&bCooldown: " + String.format("%.2f", cooldown) + " Seconds"));
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack classEnvy(DFPlayer dfPlayer, boolean base) {
		ItemStack i = CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/ad6edc436ffc19a185855a060f974382ec9225b6f4db26895818bb7adca2ea5b");
		SkullMeta m = (SkullMeta) i.getItemMeta();
		HashMap<Stats, Double> statList;
		if(base) {
			statList = Classes.ENVY.getBaseList();
		}
		else {
			statList = dfPlayer.getStatList();
		}
		double damage = statList.get(Stats.ATK_INC);
		double duration = statList.get(Stats.DURATION);
		double cooldown = statList.get(Stats.COOLDOWN);
		m.setDisplayName(new CCT().colorize("&8&lEnvy"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&4Attack Damage +"));
		lore.add(new CCT().colorize("&dRanged Damage +"));
		lore.add(new CCT().colorize("&5Critical Chance -"));
		lore.add(new CCT().colorize("&9Attack Speed -"));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7Ability: &6Special Attack"));
		lore.add(new CCT().colorize("&7You prepare a powerfull strike. Or ranged shot for that matter."));
		lore.add(new CCT().colorize("&7Your next attacks damage will be increased. However, you only"));
		lore.add(new CCT().colorize("&7have a few seconds to perform this attack before it gets cancelled."));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&4Damage Increase: " + String.format("%.2f", damage) + "%"));
		lore.add(new CCT().colorize("&bDuration: " + String.format("%.2f", duration) + " Seconds"));
		lore.add(new CCT().colorize("&bCooldown: " + String.format("%.2f", cooldown) + " Seconds"));
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	public ItemStack classPride(DFPlayer dfPlayer, boolean base) {
		ItemStack i = CustomEnchantments.getInstance().createHead("http://textures.minecraft.net/texture/4f950f8087f0396362cc817e6d024f35bd3aa09f2e44c601bab326ac8ba805f9");
		SkullMeta m = (SkullMeta) i.getItemMeta();
		HashMap<Stats, Double> statList;
		if(base) {
			statList = Classes.PRIDE.getBaseList();
		}
		else {
			statList = dfPlayer.getStatList();
		}
		double attackS = statList.get(Stats.SPD_INC);
		double defense = statList.get(Stats.DF_INC);
		double duration = statList.get(Stats.DURATION);
		double cooldown = statList.get(Stats.COOLDOWN);
		m.setDisplayName(new CCT().colorize("&6&lPride"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&9Attack Speed +"));
		lore.add(new CCT().colorize("&8Defense +"));
		lore.add(new CCT().colorize("&5Critical Chance -"));
		lore.add(new CCT().colorize("&cHealth -"));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&7Ability: &6Quick Attack"));
		lore.add(new CCT().colorize("&7You prepare to attack quickly and gracefully, your defense"));
		lore.add(new CCT().colorize("&7and attack speed both increase for a few seconds."));
		lore.add(new CCT().colorize("&7-------------------"));
		lore.add(new CCT().colorize("&9Attack Speed Increase: " + String.format("%.2f", attackS) + "%"));
		lore.add(new CCT().colorize("&8Defense Increase: " + String.format("%.2f", defense) + "%"));
		lore.add(new CCT().colorize("&bDuration: " + String.format("%.2f", duration) + " Seconds"));
		lore.add(new CCT().colorize("&bCooldown: " + String.format("%.2f", cooldown) + " Seconds"));
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
}
