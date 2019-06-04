package me.WiebeHero.CraftRecipes;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.ColorCodeTranslator;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class CallRecipe implements Listener{
	public ArrayList<ItemStack> stackList = new ArrayList<ItemStack>(); 
	@EventHandler(priority = EventPriority.NORMAL)
    public void PotionListener(InventoryClickEvent e){
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			new BukkitRunnable() {
				public void run() {
			        if(e.getClickedInventory() == null)
			            return;
			        if(e.getClickedInventory().getType() != InventoryType.BREWING)
			            return; 
			        if(((BrewerInventory)e.getInventory()).getIngredient() == null)
			            return;
			        //--------------------------------------------------------------------------------------------------------------------------------------------
			        //Resistance Potion
			        //--------------------------------------------------------------------------------------------------------------------------------------------
			        new BrewingRecipe(stackCreator(new ItemStack(Material.TURTLE_HELMET), null, null), new ArrayList<ItemStack>(Arrays.asList(thick(Material.POTION), thick(Material.SPLASH_POTION), thick(Material.LINGERING_POTION))), (inventory, item, ingridient) -> {//Some lambda magic
			            PotionMeta meta = (PotionMeta) item.getItemMeta();
			            meta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 0), true);
			            meta.setColor(Color.fromRGB(111, 55, 23));
			            meta.setDisplayName(new ColorCodeTranslator().colorize("&fPotion of Resistance"));
			            item.setItemMeta(meta);
			            if(!stackList.contains(item)) {
			            	stackList.add(item);
			            }
			        });
			        new BrewingRecipe(stackCreator(new ItemStack(Material.REDSTONE), null, null), new ArrayList<ItemStack>(Arrays.asList(resistancePotion(Material.POTION), resistancePotion(Material.SPLASH_POTION), resistancePotion(Material.LINGERING_POTION))), (inventory, item, ingridient) -> {//Some lambda magic
			            PotionMeta meta = (PotionMeta) item.getItemMeta();
			            meta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 900, 0), true);
			            meta.setColor(Color.fromRGB(111, 55, 23));
			            meta.setDisplayName(new ColorCodeTranslator().colorize("&fPotion of Resistance"));
			            item.setItemMeta(meta);
			            if(!stackList.contains(item)) {
			            	stackList.add(item);
			            }
			        });
			        new BrewingRecipe(stackCreator(new ItemStack(Material.GLOWSTONE_DUST), null, null), new ArrayList<ItemStack>(Arrays.asList(resistancePotion(Material.POTION), resistancePotion(Material.SPLASH_POTION), resistancePotion(Material.LINGERING_POTION))), (inventory, item, ingridient) -> {//Some lambda magic
			            PotionMeta meta = (PotionMeta) item.getItemMeta();
			            meta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 1), true);
			            meta.setColor(Color.fromRGB(111, 55, 23));
			            meta.setDisplayName(new ColorCodeTranslator().colorize("&fPotion of Resistance"));
			            item.setItemMeta(meta);
			            if(!stackList.contains(item)) {
			            	stackList.add(item);
			            }
			        });
			        //--------------------------------------------------------------------------------------------------------------------------------------------
			        //Haste Potion
			        //--------------------------------------------------------------------------------------------------------------------------------------------
			        new BrewingRecipe(stackCreator(new ItemStack(Material.CAKE), null, null), new ArrayList<ItemStack>(Arrays.asList(thick(Material.POTION), thick(Material.SPLASH_POTION), thick(Material.LINGERING_POTION))), (inventory, item, ingridient) -> {//Some lambda magic
			            PotionMeta meta = (PotionMeta) item.getItemMeta();
			            meta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600, 0), true);
			            meta.setColor(Color.fromRGB(111, 55, 23));
			            meta.setDisplayName(new ColorCodeTranslator().colorize("&fPotion of Haste"));
			            item.setItemMeta(meta);
			            if(!stackList.contains(item)) {
			            	stackList.add(item);
			            }
			        });
			        new BrewingRecipe(stackCreator(new ItemStack(Material.REDSTONE), null, null), new ArrayList<ItemStack>(Arrays.asList(hastePotion(Material.POTION), hastePotion(Material.SPLASH_POTION), hastePotion(Material.LINGERING_POTION))), (inventory, item, ingridient) -> {//Some lambda magic
			            PotionMeta meta = (PotionMeta) item.getItemMeta();
			            meta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 900, 0), true);
			            meta.setColor(Color.fromRGB(111, 55, 23));
			            meta.setDisplayName(new ColorCodeTranslator().colorize("&fPotion of Haste"));
			            item.setItemMeta(meta);
			            if(!stackList.contains(item)) {
			            	stackList.add(item);
			            }
			        });
			        new BrewingRecipe(stackCreator(new ItemStack(Material.GLOWSTONE_DUST), null, null), new ArrayList<ItemStack>(Arrays.asList(hastePotion(Material.POTION), hastePotion(Material.SPLASH_POTION), hastePotion(Material.LINGERING_POTION))), (inventory, item, ingridient) -> {//Some lambda magic
			            PotionMeta meta = (PotionMeta) item.getItemMeta();
			            meta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 300, 1), true);
			            meta.setColor(Color.fromRGB(111, 55, 23));
			            meta.setDisplayName(new ColorCodeTranslator().colorize("&fPotion of Haste"));
			            item.setItemMeta(meta);
			            if(!stackList.contains(item)) {
			            	stackList.add(item);
			            }
			        });
			        //--------------------------------------------------------------------------------------------------------------------------------------------
			        //Blindness Potion
			        //--------------------------------------------------------------------------------------------------------------------------------------------
			        new BrewingRecipe(stackCreator(new ItemStack(Material.ENDER_PEARL), null, null), new ArrayList<ItemStack>(Arrays.asList(mundane(Material.POTION), mundane(Material.SPLASH_POTION), mundane(Material.LINGERING_POTION))), (inventory, item, ingridient) -> {//Some lambda magic
			            PotionMeta meta = (PotionMeta) item.getItemMeta();
			            meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 0), true);
			            meta.setColor(Color.fromRGB(0, 0, 0));
			            meta.setDisplayName(new ColorCodeTranslator().colorize("&fPotion of Blindness"));
			            item.setItemMeta(meta);
			            if(!stackList.contains(item)) {
			            	stackList.add(item);
			            }
			        });
			        new BrewingRecipe(stackCreator(new ItemStack(Material.REDSTONE), null, null), new ArrayList<ItemStack>(Arrays.asList(blindnessPotion(Material.POTION), blindnessPotion(Material.SPLASH_POTION), blindnessPotion(Material.LINGERING_POTION))), (inventory, item, ingridient) -> {//Some lambda magic
			            PotionMeta meta = (PotionMeta) item.getItemMeta();
			            meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 900, 0), true);
			            meta.setColor(Color.fromRGB(0, 0, 0));
			            meta.setDisplayName(new ColorCodeTranslator().colorize("&fPotion of Blindness"));
			            item.setItemMeta(meta);
			            if(!stackList.contains(item)) {
			            	stackList.add(item);
			            }
			        });
			        new BrewingRecipe(stackCreator(new ItemStack(Material.GLOWSTONE_DUST), null, null), new ArrayList<ItemStack>(Arrays.asList(blindnessPotion(Material.POTION), blindnessPotion(Material.SPLASH_POTION), blindnessPotion(Material.LINGERING_POTION))), (inventory, item, ingridient) -> {//Some lambda magic
			            PotionMeta meta = (PotionMeta) item.getItemMeta();
			            meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 1), true);
			            meta.setColor(Color.fromRGB(111, 55, 23));
			            meta.setDisplayName(new ColorCodeTranslator().colorize("&fPotion of Blindness"));
			            item.setItemMeta(meta);
			            if(!stackList.contains(item)) {
			            	stackList.add(item);
			            }
			        });
			        BrewingRecipe recipe = BrewingRecipe.getRecipe((BrewerInventory) e.getClickedInventory());
			        if(recipe == null)
			            return;
			        recipe.startBrewing((BrewerInventory) e.getClickedInventory(), p); 
				}
			}.runTaskLater(CustomEnchantments.getInstance(), 2L);
		}
    }
	@EventHandler
	public void brewEvent(BrewEvent event) {
		if(event.getContents().getIngredient().getType() == Material.GUNPOWDER) {
			for(ItemStack item : event.getContents().getContents()) {
				if(item != null && item.getType() != Material.AIR) {
					if(item.hasItemMeta()) {
						item.setType(Material.SPLASH_POTION);
						ItemMeta meta = item.getItemMeta();
						item.setItemMeta(meta);
						event.getContents().first(item);
					}
				}
			}
		}
		else if(event.getContents().getIngredient().getType() == Material.DRAGON_BREATH) {
			for(ItemStack item : event.getContents().getContents()) {
				if(item != null && item.getType() != Material.AIR) {
					if(item.hasItemMeta()) {
						item.setType(Material.LINGERING_POTION);
						ItemMeta meta = item.getItemMeta();
						item.setItemMeta(meta);
						event.getContents().first(item);
					}
				}
			}
		}
	}
	public ItemStack stackCreator(ItemStack item, String display, ArrayList<String> lore) {
		if(item == null) {
			return null;
		}
		ItemMeta meta = item.getItemMeta();
		if(display != null) {
			meta.setDisplayName(new ColorCodeTranslator().colorize(display));
		}
		if(lore != null && lore.size() > 0) {
			meta.setLore(lore);
		}
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack thick(Material mat) {
		ItemStack item = new ItemStack(mat, 1);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.setBasePotionData(new PotionData(PotionType.THICK));
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack akward(Material mat) {
		ItemStack item = new ItemStack(mat, 1);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.setBasePotionData(new PotionData(PotionType.AWKWARD));
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack mundane(Material mat) {
		ItemStack item = new ItemStack(mat, 1);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.setBasePotionData(new PotionData(PotionType.MUNDANE));
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack resistancePotion(Material mat) {
		ItemStack item = new ItemStack(mat, 1);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.setBasePotionData(new PotionData(PotionType.THICK));
		meta.setColor(Color.fromRGB(111, 55, 23));
		meta.setDisplayName(new ColorCodeTranslator().colorize("&fPotion of Resistance"));
		meta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 0), true);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack hastePotion(Material mat) {
		ItemStack item = new ItemStack(mat, 1);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.setBasePotionData(new PotionData(PotionType.THICK));
		meta.setColor(Color.fromRGB(249, 246, 44));
		meta.setDisplayName(new ColorCodeTranslator().colorize("&fPotion of Haste"));
		meta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600, 0), true);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack blindnessPotion(Material mat) {
		ItemStack item = new ItemStack(mat, 1);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.setBasePotionData(new PotionData(PotionType.MUNDANE));
		meta.setColor(Color.fromRGB(0, 0, 0));
		meta.setDisplayName(new ColorCodeTranslator().colorize("&fPotion of Blindess"));
		meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 0), true);
		item.setItemMeta(meta);
		return item;
	}
	public ItemStack commonCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&7Common Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new ColorCodeTranslator().colorize("&7Really nice rewards!"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: Common"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack rareCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&aRare Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new ColorCodeTranslator().colorize("&7Really nice rewards!"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: &aRare"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack epicCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&bEpic Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new ColorCodeTranslator().colorize("&7Really nice rewards!"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: &bEpic"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack legendaryCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&cLegendary Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new ColorCodeTranslator().colorize("&7Really nice rewards!"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: &cLegendary"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack mythicCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&5Mythic Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new ColorCodeTranslator().colorize("&7Really nice rewards!"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: &5Mythic"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
	public ItemStack heroicCrystal() {
		ItemStack item1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta meta1 = item1.getItemMeta();	
		meta1.setDisplayName(new ColorCodeTranslator().colorize("&eHeroic Crystal"));
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add(new ColorCodeTranslator().colorize("&7Bring me to &6&lNOVIS &7to get some"));
		lore1.add(new ColorCodeTranslator().colorize("&7Really nice rewards!"));
		lore1.add(new ColorCodeTranslator().colorize("&7Rarity: &eHeroic"));
		meta1.setLore(lore1);
		item1.setItemMeta(meta1);
		return item1;
	}
}
