package me.WiebeHero.CapturePoints;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.CustomMethods.ItemStackBuilder;
import me.WiebeHero.Factions.DFFaction;
import me.WiebeHero.Factions.DFFactionManager;

public class CapturePointMenu {
	
	private CapturePointManager cpManager;
	private ItemStackBuilder builder;
	private DFFactionManager facManager;
	
	public CapturePointMenu(CapturePointManager cpManager, ItemStackBuilder builder, DFFactionManager facManager) {
		this.cpManager = cpManager;
		this.builder = builder;
		this.facManager = facManager;
	}
	
	public void CapturePointOverview(Player player) {
		Inventory i = CustomEnchantments.getInstance().getServer().createInventory(null, 27, (new CCT().colorize("&6Capture Points")));
		ItemStack nothing = this.builder.constructItem(
				Material.GRAY_STAINED_GLASS_PANE,
				1,
				" "
		);
		i.setItem(0, nothing);
		i.setItem(1, nothing);
		i.setItem(2, nothing);
		i.setItem(3, nothing);
		i.setItem(4, nothing);
		i.setItem(5, nothing);
		i.setItem(6, nothing);
		i.setItem(7, nothing);
		i.setItem(8, nothing);
		i.setItem(9, nothing);
		ArrayList<CapturePoint> points = new ArrayList<CapturePoint>(this.cpManager.getCapturePointList().values());
		int amount = points.size();
		ArrayList<Integer> slots = new ArrayList<Integer>();
		if(amount == 1) {
			slots.add(13);
		}
		else if(amount == 2) {
			slots.add(12);
			slots.add(14);
		}
		else if(amount == 3) {
			slots.add(11);
			slots.add(13);
			slots.add(15);
		}
		else if(amount == 4) {
			slots.add(10);
			slots.add(12);
			slots.add(14);
			slots.add(16);
		}
		else if(amount == 5) {
			slots.add(10);
			slots.add(12);
			slots.add(13);
			slots.add(14);
			slots.add(16);
		}
		else if(amount == 6) {
			slots.add(10);
			slots.add(11);
			slots.add(12);
			slots.add(14);
			slots.add(15);
			slots.add(16);
		}
		else if(amount == 7) {
			slots.add(10);
			slots.add(11);
			slots.add(12);
			slots.add(13);
			slots.add(14);
			slots.add(15);
			slots.add(16);
		}
		for(int x = 0; x < amount; x++) {
			CapturePoint point = points.get(x);
			Location capLocation = point.getCaptureLocation();
			DFFaction faction = facManager.getFaction(points.get(x).getCapturedId());
			ItemStack banner = new ItemStack(Material.BLACK_BANNER);
			String capturedString = "";
			if(faction != null) {
				capturedString = "&aCapture Point: &6" + facManager.getFaction(points.get(x).getCapturedId()).getName();
				banner = faction.getBanner().clone();
			}
			else {
				capturedString = "&aCapture Point";
			}
			i.setItem(slots.get(x), this.builder.constructItem(
					banner,
					capturedString,
					new ArrayList<String>(Arrays.asList(
							"&7Location: &6X: " + capLocation.getX() + " Y: " + capLocation.getY() + " Z: " + capLocation.getZ(),
							"&7Extra Player XP: &b" + point.getXPMultiplier() + "%",
							"&7Capture Radius: &6" + point.getCaptureRadius() + " Blocks"
					))
			));
		}
		
		i.setItem(17, nothing);
		i.setItem(18, nothing);
		i.setItem(19, nothing);
		i.setItem(20, nothing);
		i.setItem(21, nothing);
		i.setItem(22, nothing);
		i.setItem(23, nothing);
		i.setItem(24, nothing);
		i.setItem(25, nothing);
		i.setItem(26, nothing);
		player.openInventory(i);
	}
}
