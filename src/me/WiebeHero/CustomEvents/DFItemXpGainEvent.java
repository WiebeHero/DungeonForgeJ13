package me.WiebeHero.CustomEvents;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.DFPlayerPackage.DFPlayerManager;
import me.WiebeHero.Novis.NovisEnchantmentGetting;

public class DFItemXpGainEvent extends Event{
	private NovisEnchantmentGetting enchant;
	private DFPlayerManager dfManager;
	
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private boolean isCancelled;
	private ItemStack item;
	private ItemStack cursor;
	private int xp;
	private int slotR;
	private EquipmentSlot slot;
	
	public DFItemXpGainEvent(Player player, ItemStack item, int xp, EquipmentSlot slot, DFPlayerManager dfManager, NovisEnchantmentGetting enchant){
        this.player = player;
        this.isCancelled = false;
        this.xp = xp;
        this.item = item;
        this.slot = slot;
        this.enchant = enchant;
        this.dfManager = dfManager;
    }
	
	public DFItemXpGainEvent(Player player, ItemStack item, ItemStack cursor, int xp, int slot, DFPlayerManager dfManager, NovisEnchantmentGetting enchant){
        this.player = player;
        this.isCancelled = false;
        this.xp = xp;
        this.item = item;
        this.cursor = cursor;
        this.slotR = slot;
        this.enchant = enchant;
        this.dfManager = dfManager;
    }
	
	public Player getPlayer() {
		return this.player;
	}
	
	public int getLevelRequired() {
		if(this.getItemStack() != null && this.getCursorStack() != null) {
			NBTItem item = new NBTItem(this.getItemStack());
			NBTItem cursor = new NBTItem(this.getCursorStack());
			if(item.hasKey("Upgradeable") && cursor.hasKey("Upgradeable")) {
				if(item.hasKey("Level") && cursor.hasKey("Level")) {
					int levelItem = item.getInteger("Level");
					int levelCursor = cursor.getInteger("Level");
					if(levelItem >= 6 && levelCursor >= 6) {
						int loreRequired1 = levelItem - 4;
						int loreRequired2 = levelCursor - 4;
						int levelRequired1 = loreRequired1 * 5;
						int levelRequired2 = loreRequired2 * 5;
						if(loreRequired1 > loreRequired2) {
							return levelRequired1;
						}
						else if(loreRequired2 > loreRequired1) {
							return levelRequired2;
						}
						else {
							return levelRequired1;
						}
					}
					else if(levelItem >= 6) {
						int loreRequired = levelItem - 4;
						int levelRequired = loreRequired * 5;
						return levelRequired;
					}
					else if(levelCursor >= 6) {
						int loreRequired = levelCursor - 4;
						int levelRequired = loreRequired * 5;
						return levelRequired;
					}
				}
			}
		}
		else if(this.getItemStack() != null){
			NBTItem item = new NBTItem(this.getItemStack());
			if(item.hasKey("Upgradeable")) {
				if(item.hasKey("Level")) {
					int level = item.getInteger("Level");
					if(level >= 6) {
						int loreRequired = level - 4;
						int levelRequired = loreRequired * 5;
						return levelRequired;
					}
				}
			}
		}
		else if(this.getCursorStack() != null){
			NBTItem item = new NBTItem(this.getCursorStack());
			if(item.hasKey("Upgradeable")) {
				if(item.hasKey("Level")) {
					int level = item.getInteger("Level");
					if(level >= 6) {
						int loreRequired = level - 4;
						int levelRequired = loreRequired * 5;
						return levelRequired;
					}
				}
			}
		}
		return 0;
	}
	
	public boolean isCancelled() {
		return this.isCancelled;
	}
	
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;
	}
	
	public ItemStack getItemStack() {
		return this.item;
	}
	
	public int getXP() {
		return this.xp;
	}
	
	public void setXP(int xp) {
		this.xp = xp;
	}
	
	public void setItemStack(ItemStack item) {
		this.item = item;
	}
	
	public EquipmentSlot getEquipmentSlot() {
		return this.slot;
	}
	
	public void setEquipmentSlot(EquipmentSlot slot) {
		this.slot = slot;
	}
	
	public int getClickedSlot() {
		return this.slotR;
	}
	
	public void setClickedSlot(int slot) {
		this.slotR = slot;
	}
	
	public ItemStack getCursorStack() {
		return this.cursor;
	}
	
	public void setCursorStack(ItemStack cursor) {
		this.cursor = cursor;
	}
	
	public void activate() {
		if(!this.isCancelled()) {
			ItemStack i = this.getItemStack();
			NBTItem item = new NBTItem(i);
			if(item.hasKey("MAXXP")) {
				int xp = this.getXP();
				int maxxp = item.getInteger("MAXXP");
				if(xp >= maxxp) {
					if(this.getEquipmentSlot() != null) {
						DFItemLevelUpEvent e = new DFItemLevelUpEvent(this.getPlayer(), i, xp, this.getEquipmentSlot(), dfManager, enchant);
						Bukkit.getPluginManager().callEvent(e);
					}
					else {
						DFItemLevelUpEvent e = new DFItemLevelUpEvent(this.getPlayer(), i, this.getCursorStack(), xp, this.getClickedSlot(), dfManager, enchant);
						Bukkit.getPluginManager().callEvent(e);
					}
				}
				else {
					item.setInteger("XP", this.getXP());
					i = item.getItem();
					ItemMeta im = i.getItemMeta();
		    		ArrayList<String> lore = new ArrayList<String>(im.getLore());
					lore.set(lore.size() - 4, new CCT().colorize("&7Upgrade Progress: " + "&a[&b&l" + (this.getXP()) + " &6/ " + "&b&l" + maxxp + "&a]"));
					double barprogress = (double) xp / (double) maxxp * 100.0;
					String loreString = "&7[&a";
					boolean canStop = true;
					for(double x = 0.00; x <= 100.00; x+=2.00) {
						if(barprogress >= x) {
							loreString = loreString + ":";
						}
						else if(canStop) {
							loreString = loreString + "&7:";
							canStop = false;
						}
						else {
							loreString = loreString + ":";
						}
						if(x == 100) {
							loreString = loreString + "&7] &a" + String.format("%.2f", barprogress) + "%";
						}
					}
					int size = 3;
					if(this.getLevelRequired() > 0) {
						size = 4;
					}
					lore.set(lore.size() - size, new CCT().colorize(loreString));
		    		im.setLore(lore);
		    		i.setItemMeta(im);
		    		if(this.getEquipmentSlot() == EquipmentSlot.HEAD) {
		    			this.getPlayer().getInventory().setHelmet(i);
		    		}
		    		else if(this.getEquipmentSlot() == EquipmentSlot.CHEST) {
		    			this.getPlayer().getInventory().setChestplate(i);
		    		}
		    		else if(this.getEquipmentSlot() == EquipmentSlot.LEGS) {
		    			this.getPlayer().getInventory().setLeggings(i);
		    		}
		    		else if(this.getEquipmentSlot() == EquipmentSlot.FEET) {
		    			this.getPlayer().getInventory().setBoots(i);
		    		}
		    		else if(this.getEquipmentSlot() == EquipmentSlot.OFF_HAND) {
		    			this.getPlayer().getInventory().setItemInOffHand(i);
		    		}
		    		else if(this.getEquipmentSlot() == EquipmentSlot.HAND) {
		    			this.getPlayer().getInventory().setItemInMainHand(i);
		    		}
		    		else {
			    		this.getPlayer().playSound(this.getPlayer().getLocation(), Sound.BLOCK_ANVIL_USE, (float)2.00, (float)1.00);
		    			this.getPlayer().getItemOnCursor().setAmount(this.getCursorStack().getAmount() -1);
			    		this.getPlayer().getInventory().setItem(this.getClickedSlot(), i);
			    		this.getPlayer().updateInventory();
		    		}
				}
			}
		}
	}
	
	@Override public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
}
