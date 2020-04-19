package me.WiebeHero.Advancements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.AdvancementDisplay;
import eu.endercentral.crazy_advancements.AdvancementDisplay.AdvancementFrame;
import eu.endercentral.crazy_advancements.AdvancementReward;
import eu.endercentral.crazy_advancements.AdvancementVisibility;
import eu.endercentral.crazy_advancements.CrazyAdvancements;
import eu.endercentral.crazy_advancements.NameKey;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;
import me.WiebeHero.CustomMethods.ItemStackBuilder;

public class Advancements {
	
	private AdvancementManager manager;
	private ItemStackBuilder builder;
	private HashMap<String, Advancement> advancements;
	private ArrayList<AdvancementAdder> methods = new ArrayList<AdvancementAdder>();
	
	public Advancements(ItemStackBuilder builder) {
//		this.builder = builder;
//		this.manager = CrazyAdvancements.getNewAdvancementManager();
//		this.advancements = new HashMap<String, Advancement>();
//		ArrayList<Advancement> array = new ArrayList<Advancement>();
//		//------------------------------------------------------------------------------------
//		//DungeonForge basic root advancement
//		//------------------------------------------------------------------------------------
//		this.methods.add(new AdvancementAdder() {
//			@Override
//			public void addAdvancement() {
//				AdvancementDisplay display = new AdvancementDisplay(Material.GRASS_BLOCK, "Join DungeonForge!", "Join DungeonForge for the first time.", AdvancementFrame.TASK, true, true, AdvancementVisibility.VANILLA);
//				display.setBackgroundTexture("textures/block/yellow_concrete.png");
//				display.setCoordinates(0, 0);
//				Advancement advancement = new Advancement(null, new NameKey("dungeonforge_basic", "join"), display);
//				advancements.put("Player Join", advancement);
//				array.add(advancement);
//			}
//		});
//		this.methods.add(new AdvancementAdder() {
//			@Override
//			public void addAdvancement() {
//				for(int i = 10; i <= 100; i+= 10) {
//					int times = i;
//					int money = 1000 * times;
//					AdvancementDisplay display = new AdvancementDisplay(Material.EXPERIENCE_BOTTLE, "Level up!", "Reach level " + i + " you will be awarded with a $" + money + " ticket.", (i <= 80 ? AdvancementFrame.GOAL : AdvancementFrame.CHALLENGE), true, true, AdvancementVisibility.VANILLA);
//					display.setCoordinates(array.size(), 0);//x, y
//					Advancement children = new Advancement(array.get(array.size() - 1), new NameKey("dungeonforge_basic", "level_" + i), display);
//					AdvancementReward reward = new AdvancementReward() {
//						@Override
//						public void onGrant(Player player) {
//							ItemStack item = builder.constructItem(
//									Material.PAPER,
//									1,
//									"&aMoney Note: $" + money,
//									new ArrayList<String>(Arrays.asList(
//										"&7Right click this note to recieve",
//										"&7the amount of money that is on",
//										"&7the note."
//									)),
//									"Money",
//									money
//							);
//							player.getInventory().addItem(item);
//						}
//					};
//					children.setReward(reward);
//					advancements.put("Level Up " + i, children);
//					array.add(children);
//				}
//			}
//		});
//		this.addAdvancements(array);
	}
	
	public AdvancementManager getAdvancementManager() {
		return this.manager;
	}
	
	public void addAdvancements(ArrayList<Advancement> array) {
		for(Advancement advancement : array) {
			this.manager.addAdvancement(advancement);
		}
	}
	
	public void loadAdvancements(Player player) {
		for(Advancement advancement : this.manager.getAdvancements()) {
			this.manager.loadProgress(player, advancement);
		}
	}
	
	public void saveAdvancements() {
		for(Player player : this.manager.getPlayers()) {
			this.manager.saveProgress(player, "dungeonforge");
		}
	}
	
	public void registerAdvancements() {
		for(AdvancementAdder adder : this.methods) {
			adder.addAdvancement();
		}
	}
	
	public HashMap<String, Advancement> getAdancementsEasy() {
		return this.advancements;
	}
}
