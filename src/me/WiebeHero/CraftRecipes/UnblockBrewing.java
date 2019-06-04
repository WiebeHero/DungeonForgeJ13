package me.WiebeHero.CraftRecipes;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;

public class UnblockBrewing implements Listener{
	ArrayList<Material> checkList = new ArrayList<Material>(Arrays.asList(Material.TURTLE_HELMET, Material.CAKE, Material.NETHER_STAR, Material.IRON_SWORD));
	@EventHandler(priority = EventPriority.HIGHEST)
    public void potionItemPlacer(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null)
            return;
        if (e.getClickedInventory().getType() != InventoryType.BREWING)
            return;
        if (e.getClick() != ClickType.LEFT) //Make sure we are placing an item
            return;
        final ItemStack is = e.getCurrentItem(); //We want to get the item in the slot
        final ItemStack is2 = e.getCursor().clone(); //And the item in the cursor
        if(is2 == null) //We make sure we got something in the cursor
            return;
        if(is2.getType() == Material.AIR)
            return;
        if(!checkList.contains(is2.getType()))
        	return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(CustomEnchantments.getInstance(), new Runnable() {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                e.setCursor(is);//Now we make the switch
                e.getClickedInventory().setItem(e.getSlot(), is2);
                ((Player)e.getWhoClicked()).updateInventory();//And we update the inventory
            }
        }, 1L);//(Delay in 1 tick)
    }
}
