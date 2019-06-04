package me.WiebeHero.CraftRecipes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import net.minecraft.server.v1_13_R2.PacketPlayOutWindowData;

public class BrewingRecipe {
	public static HashMap<UUID, ArrayList<Inventory>> dontTryList = new HashMap<UUID, ArrayList<Inventory>>();
    private static List<BrewingRecipe> recipes = new ArrayList<BrewingRecipe>();
    private ItemStack ingridient;
    private BrewAction action;
    private static ArrayList<ItemStack> stacks;
    private boolean perfect;

    public BrewingRecipe(ItemStack ingridient , ArrayList<ItemStack> stacks, BrewAction action , boolean perfect) {
        this.ingridient = ingridient;
        this.action = action;
        this.perfect = perfect;
        BrewingRecipe.stacks = stacks;
        recipes.add(this);
    }
 
    public BrewingRecipe(ItemStack ingridient , ArrayList<ItemStack> stacks, BrewAction action)
    {
        this(ingridient, stacks, action, false);
    }
    public ItemStack getIngridient() {
        return ingridient;
    }
    public ArrayList<ItemStack> getResults() {
        return stacks;
    }
    public BrewAction getAction() {
        return action;
    }

    public boolean isPerfect() {
        return perfect;
    }

    /**
     * Get the BrewRecipe of the given recipe , will return null if no recipe is found
     * @param inventory The inventory
     * @return The recipe
     */
    /**
     * Get the BrewRecipe of the given recipe , will return null if no recipe is found
     * @param inventory The inventory
     * @return The recipe
     */
    @Nullable
    public static BrewingRecipe getRecipe(BrewerInventory inventory)
    {
        boolean notAllAir = false;
        boolean checkItem = false;
        for(int i = 0 ; i < 3 && !notAllAir ; i++)
        {
            if(inventory.getItem(i) == null)
                continue;
            if(inventory.getItem(i).getType() == Material.AIR)
                continue;
            notAllAir = true;
        }
        if(!notAllAir)
            return null;
        for(BrewingRecipe recipe : recipes)
        {
            if(!recipe.isPerfect() && inventory.getIngredient().isSimilar(recipe.getIngridient()))
            {
            	for(int i = 0; i < 3; i++) 
            	{
            		if(inventory.getItem(i) == null)
                        continue;
                    if(inventory.getItem(i).getType() == Material.AIR)
                        continue;
                    for(ItemStack item : recipe.getResults()) {
                    	Bukkit.broadcastMessage(item.getType().toString() + "len: " + stacks.size());
	                    if(inventory.getItem(i).isSimilar(item)) {
	                    	checkItem = true;
	                    	break;
	                    }
	                    else if(!inventory.getItem(i).isSimilar(item) && inventory.getItem(i).getType() != Material.AIR) {
	                        checkItem = false;
	                    }
                    }
            	}
            	if(checkItem == true) 
            		return recipe;
            }
            if(recipe.isPerfect() && inventory.getIngredient().isSimilar(recipe.getIngridient()))
            {
            	for(int i = 0; i < 3; i++) 
            	{
            		if(inventory.getItem(i) == null)
                        continue;
                    if(inventory.getItem(i).getType() == Material.AIR)
                        continue;
                    for(ItemStack item : recipe.getResults()) {
                    	Bukkit.broadcastMessage(item.getType().toString() + "len: " + stacks.size());
	                    if(inventory.getItem(i).isSimilar(item)) {
	                    	checkItem = true;
	                    	break;
	                    }
	                    else if(!inventory.getItem(i).isSimilar(item) && inventory.getItem(i).getType() != Material.AIR) {
	                        checkItem = false;
	                    }
                    }
            	}
            	if(checkItem == true) 
            		return recipe;
            }
        }
        return null;
    }
    public void startBrewing(BrewerInventory inventory, Player p)
    {
    	if(dontTryList.get(p.getUniqueId()) == null) {
    		dontTryList.put(p.getUniqueId(), new ArrayList<Inventory>());
    	}
    	if(!dontTryList.get(p.getUniqueId()).contains(inventory)) {
    		new BrewClock(this, inventory, p);
    	}
    }
 
    private class BrewClock extends BukkitRunnable
    {
    	private Player player;
        private BrewerInventory inventory;
        private BrewingRecipe recipe;
        private ItemStack ingridient;
        private BrewingStand stand;
        private ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        private int time = 400; //Like I said the starting time is 400
     
        public BrewClock(BrewingRecipe recipe , BrewerInventory inventory, Player p) {
        	this.player = p;
            this.recipe = recipe;
            this.inventory = inventory;
            this.ingridient = inventory.getIngredient();
            this.stand = inventory.getHolder();
            dontTryList.get(p.getUniqueId()).add(inventory);
            runTaskTimer(CustomEnchantments.getInstance(), 0L, 1L);
        }
     
        @Override
        public void run() {
            if(time == 0)
            {
                inventory.setIngredient(new ItemStack(Material.AIR));
                for(int i = 0; i < 3 ; i ++)
                {
                    if(inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR)
                        continue;
                    recipe.getAction().brew(inventory, inventory.getItem(i), ingridient);
                }
                dontTryList.get(player.getUniqueId()).remove(inventory);
                if(inventory.equals(player.getOpenInventory().getTopInventory())) {
    	            int i = ((CraftPlayer)player).getHandle().activeContainer.windowId;
    	            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutWindowData(i, 0, 400));
                } 
                cancel();
                return;
            }
            for(int i = 0; i < 3; i++) {
            	if(inventory.getItem(i) != null && inventory.getItem(i).getType() != Material.AIR) {
            		items.add(inventory.getItem(i));
            	}
            }
            if(inventory.getIngredient() != null && items != null)
            {
	            if(!inventory.getIngredient().isSimilar(ingridient) || items.isEmpty())
	            {
	            	if(inventory.equals(player.getOpenInventory().getTopInventory())) {
	    	            int i = ((CraftPlayer)player).getHandle().activeContainer.windowId;
	    	            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutWindowData(i, 0, 400));
	                } 
	                dontTryList.get(player.getUniqueId()).remove(inventory);
	                cancel();
	                return;
	            }
            }
            else
            {
            	if(inventory.equals(player.getOpenInventory().getTopInventory())) {
    	            int i = ((CraftPlayer)player).getHandle().activeContainer.windowId;
    	            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutWindowData(i, 0, 400));
                } 
            	dontTryList.get(player.getUniqueId()).remove(inventory);
                cancel();
                return;
            }
            //You should also add here a check to make sure that there are still items to brew
            if(inventory.equals(player.getOpenInventory().getTopInventory())) {
            	items.clear();
	            int i = ((CraftPlayer)player).getHandle().activeContainer.windowId;
	            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutWindowData(i, 0, time));
            } 
            time--;
        }
    }
}