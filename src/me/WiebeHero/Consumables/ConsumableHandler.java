package me.WiebeHero.Consumables;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import de.tr7zw.nbtapi.NBTItem;
import javafx.util.Pair;
import me.WiebeHero.Consumables.ConsumableCondition.Condition;
import me.WiebeHero.Consumables.Unlock.UnlockCraftCondition;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;

public class ConsumableHandler implements Listener{
	private Consumable con;
	private DFPlayerManager dfManager;
	public ConsumableHandler(DFPlayerManager dfManager, Consumable con) {
		this.dfManager = dfManager;
		this.con = con;
	}
	@EventHandler
	public void consumeHandler(PlayerItemConsumeEvent event) {
		Player p = event.getPlayer();
		if(event.getItem() != null) {
			ItemStack item = event.getItem();
			if(item.hasItemMeta()) {
				if(item.getItemMeta().hasDisplayName()) {
					String contain = ChatColor.stripColor(item.getItemMeta().getDisplayName());
					contain = contain.replace(' ', '_');
					if(con.getConsumables().containsKey(contain)) {
						if(con.getConsumables().get(contain).getKey() == Condition.CONSUME) {
							con.getConsumables().get(contain).getValue().activateConsumable(p);
							con.getConsumables().get(contain).getValue().activateConsumable(p, event);
							if(item.getItemMeta().getLore().toString().contains("Cooldown")) {
								for(String s : item.getItemMeta().getLore()) {
									if(s.contains("Cooldown")) {
										int cooldown = Integer.parseInt(s.replaceAll("[^\\d.]", "")) * 20;
										p.setCooldown(item.getType(), cooldown);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void clickHandler(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if(event.getItem() != null) {
			ItemStack item = event.getItem();
			if(item.hasItemMeta()) {
				if(item.getItemMeta().hasDisplayName()) {
					String contain = ChatColor.stripColor(item.getItemMeta().getDisplayName());
					contain = contain.replace(' ', '_');
					if(con.getConsumables().containsKey(contain)) {
						if(con.getConsumables().get(contain).getKey() == Condition.LEFT_CLICK || con.getConsumables().get(contain).getKey() == Condition.RIGHT_CLICK) {
							if(!p.hasCooldown(item.getType())) {
								con.getConsumables().get(contain).getValue().activateConsumable(p);
								con.getConsumables().get(contain).getValue().activateConsumable(p, event);
								event.getItem().setAmount(event.getItem().getAmount() - 1);
								NBTItem i = new NBTItem(item);
								if(i.hasKey("Cooldown")) {
									int cooldown = i.getInteger("Cooldown");
									p.setCooldown(item.getType(), cooldown);
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void cancelPlaceConsumable(BlockPlaceEvent event) {
		if(event.getItemInHand() != null) {
			ItemStack item = event.getItemInHand();
			if(item.hasItemMeta()) {
				if(item.getItemMeta().hasDisplayName()) {
					String contain = ChatColor.stripColor(item.getItemMeta().getDisplayName());
					contain = contain.replace(' ', '_');
					if(con.getConsumables().containsKey(contain)) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
	@EventHandler
	public void discoverRecipePickup(PlayerAttemptPickupItemEvent event) {
		if(!event.isCancelled()) {
			ArrayList<Pair<ArrayList<UnlockCraftCondition>, Recipe>> list = con.getCustomRecipeList();
			Player player = event.getPlayer();
			Item item = event.getItem();
			if(item != null) {
				for(int i = 0; i < list.size(); i++) {
					if(list.get(i).getKey().contains(UnlockCraftCondition.PLAYER_PICKUP_ITEM)) {
						Recipe rec = list.get(i).getValue();
						if(rec instanceof ShapedRecipe) {
							ShapedRecipe recipe = (ShapedRecipe) rec;
							ArrayList<ItemStack> s = new ArrayList<>(recipe.getIngredientMap().values());
							for(int i1 = 0; i1 < s.size(); i1++) {
								if(s.get(i1) != null) {
									if(item.getItemStack().isSimilar(s.get(i1))) {
										player.discoverRecipe(recipe.getKey());
									}
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void discoverRecipeClick(InventoryClickEvent event) {
		if(!event.isCancelled()) {
			if(event.getWhoClicked() instanceof Player) {
				ArrayList<Pair<ArrayList<UnlockCraftCondition>, Recipe>> list = con.getCustomRecipeList();
				Player player = (Player) event.getWhoClicked();
				ItemStack item = event.getCurrentItem();
				if(item != null) {
					for(int i = 0; i < list.size(); i++) {
						if(list.get(i).getKey().contains(UnlockCraftCondition.PLAYER_CLICK_INVENTORY)) {
							Recipe rec = list.get(i).getValue();
							if(rec instanceof ShapedRecipe) {
								ShapedRecipe recipe = (ShapedRecipe) rec;
								ArrayList<ItemStack> s = new ArrayList<>(recipe.getIngredientMap().values());
								for(int i1 = 0; i1 < s.size(); i1++) {
									if(s.get(i1) != null) {
										if(item.isSimilar(s.get(i1))) {
											player.discoverRecipe(recipe.getKey());
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void joinUnlock(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		ArrayList<Recipe> recipeList = con.getInstantUnlocks();
		for(int i = 0; i < recipeList.size(); i++) {
			if(recipeList.get(i) instanceof ShapedRecipe) {
				ShapedRecipe recipe = (ShapedRecipe) recipeList.get(i);
				player.discoverRecipe(recipe.getKey());
			}
			if(recipeList.get(i) instanceof ShapelessRecipe) {
				ShapelessRecipe recipe = (ShapelessRecipe) recipeList.get(i);
				player.discoverRecipe(recipe.getKey());
			}
		}
	}
}
