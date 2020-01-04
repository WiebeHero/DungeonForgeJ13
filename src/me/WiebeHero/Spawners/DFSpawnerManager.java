package me.WiebeHero.Spawners;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtinjector.NBTInjector;
import javafx.util.Pair;
import me.WiebeHero.CustomEnchantments.CCT;
import me.WiebeHero.CustomEnchantments.CustomEnchantments;
import me.WiebeHero.Skills.DFPlayer;
import me.WiebeHero.Skills.EffectSkills;
import me.WiebeHero.Skills.Enums.Classes;

public class DFSpawnerManager {
	public HashMap<UUID, DFSpawner> spawnerList;
	public HashMap<EntityType, Pair<ArrayList<ItemStack>, ArrayList<String>>> names = new HashMap<EntityType, Pair<ArrayList<ItemStack>, ArrayList<String>>>();
	public DFPlayer method = new DFPlayer();
	public EffectSkills sk = new EffectSkills();
	public DFSpawnerManager() {
		this.addNames();
		this.spawnerList = new HashMap<UUID, DFSpawner>();
	}
	//---------------------------------------------------------
	//Saving and loading Spawners
	//---------------------------------------------------------
	public void loadSpawners() {
		File f1 =  new File("plugins/CustomEnchantments/spawnerConfig.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f1);
		try{
			yml.load(f1);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(yml.getConfigurationSection("Spawners.UUID") != null) {
			Set<String> l = yml.getConfigurationSection("Spawners.UUID").getKeys(false);
			ArrayList<String> list = new ArrayList<String>(l);
			for(int i = 0; i < list.size(); i++) {
				UUID uuid = UUID.fromString(list.get(i));
				int tier = yml.getInt("Spawners.UUID." + list.get(i) + ".Tier");
				EntityType type = EntityType.valueOf(yml.getString("Spawners.UUID." + list.get(i) + ".EntityType"));
				Location loc = (Location) yml.get("Spawners.UUID." + list.get(i) + ".Location");
				new DFSpawner(uuid, loc, tier, type);
			}
		}
	}
	
	public void saveSpawners() {
		File f =  new File("plugins/CustomEnchantments/spawnerConfig.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		yml.createSection("Spawners.UUID");
		if(!this.spawnerList.isEmpty()) {
			for(DFSpawner spawner : this.spawnerList.values()) {
				if(spawner != null) {
					yml.set("Spawners.UUID." + spawner.getUUID() + ".Location", spawner.getLocation());
					yml.set("Spawners.UUID." + spawner.getUUID() + ".Tier", spawner.getTier());
					yml.set("Spawners.UUID." + spawner.getUUID() + ".EntityType", spawner.getType().toString());
				}
			}
		}
		try{
			yml.save(f);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public void hardSaveSpawners() {
		File f =  new File("plugins/CustomEnchantments/spawnerConfig.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		try{
			yml.load(f);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(!this.spawnerList.isEmpty()) {
			for(DFSpawner spawner : this.spawnerList.values()) {
				if(spawner != null) {
					yml.set("Spawners.UUID." + spawner.getUUID() + ".Location", spawner.getLocation());
					yml.set("Spawners.UUID." + spawner.getUUID() + ".Tier", spawner.getTier());
					yml.set("Spawners.UUID." + spawner.getUUID() + ".EntityType", spawner.getType().toString());
				}
			}
		}
		try{
			yml.save(f);
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	public void startSpawning() {
		new BukkitRunnable() {
			public void run() {
				for(DFSpawner spawner : spawnerList.values()) {
					Location loc = spawner.getLocation();
					Location spawnerLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
					spawnerLoc.setPitch(0.0F);
					spawnerLoc.setYaw(0.0F);
					for(Entity e : spawnerLoc.getWorld().getNearbyEntities(spawnerLoc, 50, 50, 50)) {
						if(e instanceof Monster) {
							Location check2 = e.getLocation();
							if(spawnerLoc.distance(check2) > 25) {
								e = NBTInjector.patchEntity(e);
								NBTCompound comp = NBTInjector.getNbtData(e);
								if(comp.hasKey("SpawnerUUID")) {
									if(!comp.getString("SpawnerUUID").equals("")) {
										if(spawner.getUUID().equals(UUID.fromString(comp.getString("SpawnerUUID")))) {
											spawnerLoc.setX(spawnerLoc.getX() + randomLocOffSet());
											spawnerLoc.setZ(spawnerLoc.getZ() + randomLocOffSet());
											for(double y = spawnerLoc.getY() + 10.00; y > 0.00;) {
												y--;
												spawnerLoc.setY(y);
												if(spawnerLoc.getBlock().getType() != Material.AIR) {
													spawnerLoc.add(0, 3.0, 0);
													break;
												}
											}
											e.teleport(spawnerLoc);
										}
									}
								}
							}
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 100L);
		new BukkitRunnable() {
			public void run() {
				for(DFSpawner spawner : spawnerList.values()) {
					spawner.setCanSpawn(true);
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 3600L);
		DFSpawnerManager manager = new DFSpawnerManager();
		new BukkitRunnable() {
			@Override
			public void run() {
				for(DFSpawner spawner : spawnerList.values()) {
					if(spawner.getCanSpawn()) {
						Location loc = spawner.getLocation();
						Location spawnerLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
						spawnerLoc.setPitch(0.0F);
						spawnerLoc.setYaw(0.0F);
						spawnerLoc.setX(spawnerLoc.getX() + randomLocOffSet());
						spawnerLoc.setZ(spawnerLoc.getZ() + randomLocOffSet());
						int count = 0;
						for(Entity e : spawnerLoc.getWorld().getNearbyEntities(spawnerLoc, 50, 50, 50)) {
							if(e != null) {
								if(e instanceof Monster) {
									Entity ent = NBTInjector.patchEntity(e);
									NBTCompound comp = NBTInjector.getNbtData(ent);
									if(comp.hasKey("SpawnerUUID")) {
										if(!comp.getString("SpawnerUUID").equals("")) {
											if(spawner.getUUID().equals(UUID.fromString(comp.getString("SpawnerUUID")))) {
												count++;
											}
										}
									}
								}
							}
						}
						boolean confirmed = false;
						for(Entity e : spawner.getLocation().getNearbyEntities(40, 40, 40)) {
							if(e instanceof Player) {
								confirmed = true;
							}
						}
						if(confirmed == true) {
							int max = 2 + (int)Math.round((spawner.getTier() * 0.35));
							for(int current = count; current <= max; current++) {
								manager.spawnMob(spawner);
							}
							spawner.setCanSpawn(false);
						}
					}
				}
			}
		}.runTaskTimer(CustomEnchantments.getInstance(), 0L, 100L);
	}
	public DFSpawner get(UUID uuid) {
		return this.spawnerList.get(uuid);
	}
	public boolean contains(UUID uuid) {
		if(this.spawnerList.containsKey(uuid)) {
			return true;
		}
		return false;
	}
	public void add(UUID uuid, DFSpawner p) {
		this.spawnerList.put(uuid, p);
	}
	public void remove(UUID uuid) {
		if(this.spawnerList.containsKey(uuid)) {
			this.spawnerList.remove(uuid);
		}
	}
	public HashMap<UUID, DFSpawner> getSpawnerList(){
		return this.spawnerList;
	}
	
	
	public void spawnMob(DFSpawner spawner) {
		Location fixedLoc = spawner.getLocation();
		int tier = spawner.getTier();
		Location spawnLoc = new Location(fixedLoc.getWorld(), fixedLoc.getX(), fixedLoc.getY(), fixedLoc.getZ());
		double originalY = spawnLoc.getY();
		spawnLoc.setX(spawnLoc.getX() + randomLocOffSet());
		spawnLoc.setZ(spawnLoc.getZ() + randomLocOffSet());
		for(double y1 = spawnLoc.getY() + 10.00; y1 > 0;) {
			y1--;
			spawnLoc.setY(y1);
			if(spawnLoc.getBlock().getType() != Material.AIR) {
				spawnLoc.setY(y1 + 2.00);
				if(spawnLoc.getBlock().getType() == Material.AIR) {
					break;
				}
				else {
					y1 = originalY + 10.00;
					spawnLoc.setX(spawnLoc.getX() + randomLocOffSet());
  					spawnLoc.setZ(spawnLoc.getZ() + randomLocOffSet());
				}
			}
		}
		LivingEntity mob = (LivingEntity) spawnLoc.getWorld().spawnEntity(spawnLoc, spawner.getType());
		Pair<ArrayList<ItemStack>, ArrayList<String>> list = names.get(spawner.getType());
		int rand = randomHead();
		String name = list.getValue().get(rand);
		ItemStack head = list.getKey().get(rand);
		if(mob instanceof Zombie) {
			Zombie zombie = (Zombie) mob;
			zombie.setBaby(false);
		}
		else if(mob instanceof PigZombie) {
			Zombie zombie = (Zombie) mob;
			zombie.setBaby(false);
		}
		mob.getEquipment().setHelmet(head);
		int level = (new Random().nextInt(20) + 20 * (tier - 1));
		if(level == 0) {
			level++;
		}
		Entity ent = NBTInjector.patchEntity(mob);
		NBTCompound comp = NBTInjector.getNbtData(ent);
		comp.setString("SpawnerUUID", spawner.getUUID().toString());
		comp.setInteger("Tier", tier);
		comp.setInteger("Level", level);
		mob.setCustomName(new CCT().colorize(name + " &b[&6Lv " + level + "&b]"));
		mob.setCustomNameVisible(true);
		if(tier == 1) {
			mob.getEquipment().setHelmet(head);
			if(randomDouble() <= 10 + 0.5 * level) {
				mob.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			}
			if(randomDouble() <= 10 + 0.5 * level) {
				mob.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			}
			if(randomDouble() <= 10 + 0.5 * level) {
				mob.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
			}
			int dice = dice();
			if(dice == 1) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_SWORD));
			}
			else if(dice == 2) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_AXE));
			}
			else if(dice == 3) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_PICKAXE));
			}
			else if(dice == 4) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_SHOVEL));
			}
		}
		else if(tier == 2) {
			mob.getEquipment().setHelmet(head);
			if(randomDouble() <= 15 + 0.55 * level) {
				mob.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
			}
			if(randomDouble() <= 15 + 0.55 * level) {
				mob.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
			}
			if(randomDouble() <= 15 + 0.55 * level) {
				mob.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
			}
			int dice = dice();
			if(dice == 1) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD));
			}
			else if(dice == 2) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
			}
			else if(dice == 3) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_PICKAXE));
			}
			else if(dice == 4) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SHOVEL));
			}
		}
		else if(tier == 3) {
			mob.getEquipment().setHelmet(head);
			if(randomDouble() <= 20 + 0.6 * level) {
				mob.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			}
			if(randomDouble() <= 20 + 0.6 * level) {
				mob.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			}
			if(randomDouble() <= 20 + 0.6 * level) {
				mob.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
			}
			int dice = dice();
			if(dice == 1) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
			}
			else if(dice == 2) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_AXE));
			}
			else if(dice == 3) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_PICKAXE));
			}
			else if(dice == 4) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SHOVEL));
			}
		}
		else if(tier == 4) {
			mob.getEquipment().setHelmet(head);
			if(randomDouble() <= 25 + 0.65 * level) {
				mob.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			}
			if(randomDouble() <= 25 + 0.65 * level) {
				mob.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			}
			if(randomDouble() <= 25 + 0.65 * level) {
				mob.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
			}
			int dice = dice();
			if(dice == 1) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
			}
			else if(dice == 2) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
			}
			else if(dice == 3) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_PICKAXE));
			}
			else if(dice == 4) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SHOVEL));
			}
		}
		else if(tier == 5) {
			mob.getEquipment().setHelmet(head);
			if(randomDouble() <= 30 + 0.70 * level) {
				mob.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			}
			if(randomDouble() <= 30 + 0.70 * level) {
				mob.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			}
			if(randomDouble() <= 30 + 0.70 * level) {
				mob.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			}
			int dice = dice();
			if(dice == 1) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
			}
			else if(dice == 2) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
			}
			else if(dice == 3) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_PICKAXE));
			}
			else if(dice == 4) {
				mob.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SHOVEL));
			}
		}
		method.addPlayer(mob);
		DFPlayer dfPlayer = method.getPlayer(mob);
		dfPlayer.setLevel(level);
		int skills = level * 8;
		int cl = new Random().nextInt(7) + 1;
		switch(cl) {
		case 1:
			dfPlayer.setPlayerClass(Classes.WRATH);
			break;
		case 2:
			dfPlayer.setPlayerClass(Classes.LUST);
			break;
		case 3:
			dfPlayer.setPlayerClass(Classes.GLUTTONY);
			break;
		case 4:
			dfPlayer.setPlayerClass(Classes.GREED);
			break;
		case 5:
			dfPlayer.setPlayerClass(Classes.SLOTH);
			break;
		case 6:
			dfPlayer.setPlayerClass(Classes.ENVY);
			break;
		case 7:
			dfPlayer.setPlayerClass(Classes.PRIDE);
			break;
		default:
			dfPlayer.setPlayerClass(Classes.NONE);
			break;
		}
		for(int m = 0; m <= skills; m++) {
			int random = this.randomAmount(6);
			switch(random) {
			case 1:
				dfPlayer.addAtk(1);
				break;
			case 2:
				dfPlayer.addSpd(1);
				break;
			case 3:
				dfPlayer.addCrt(1);
				break;
			case 4:
				dfPlayer.addRnd(1);
				break;
			case 5:
				dfPlayer.addHp(1);
				break;
			case 6:
				if(dfPlayer.getDf() < 150) {
					dfPlayer.addDf(1);
				}
				else {
					m--;
				}
				break;
			}
		}
		dfPlayer.setMove(0.2 + 0.0175 * tier + 0.00035 * level);
		dfPlayer.resetCalculations();
		sk.attackSpeed(mob);
		sk.changeHealth(mob);
		new BukkitRunnable() {
			public void run() {
				mob.setHealth(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			}
		}.runTaskLater(CustomEnchantments.getInstance(), 10L);
		sk.runDefense(mob);
		mob.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(0.2 * dfPlayer.getDf());
		mob.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(0.1333 * dfPlayer.getDf());
		mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(17.50);
		mob.getEquipment().setHelmetDropChance(0.00F);
		mob.getEquipment().setChestplateDropChance(0.00F);
		mob.getEquipment().setLeggingsDropChance(0.00F);
		mob.getEquipment().setBootsDropChance(0.00F);
		mob.getEquipment().setItemInMainHandDropChance(0.00F);
	}
	public int randomAmount(int am) {
		return new Random().nextInt(am) + 1;
	}
	public ItemStack createHead(String paramString)
    {
        ItemStack localItemStack = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta localSkullMeta = (SkullMeta)localItemStack.getItemMeta();

        GameProfile localGameProfile = new GameProfile(UUID.randomUUID(), null);
        byte[] arrayOfByte = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { paramString }).getBytes());
        localGameProfile.getProperties().put("textures", new Property("textures", new String(arrayOfByte)));
        Field localField = null;
        try
        {
            localField = localSkullMeta.getClass().getDeclaredField("profile");
            localField.setAccessible(true);
            localField.set(localSkullMeta, localGameProfile);
        }
        catch (NoSuchFieldException|IllegalArgumentException|IllegalAccessException localNoSuchFieldException)
        {
            System.out.println("error: " + localNoSuchFieldException.getMessage());
        }
        localSkullMeta.setDisplayName("head");
        localItemStack.setItemMeta(localSkullMeta);

        return localItemStack;
    }
	public void addNames() {
		Pair<ArrayList<ItemStack>, ArrayList<String>> pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/3a77916e84769b2c9547934dd21aebedb1972758aa5a739b612743653f6ea"),
		this.createHead("http://textures.minecraft.net/texture/b6b972e32d761b192626e5d6d01edc094940910103cea5e2e2d1f231adb755d5"),
		this.createHead("http://textures.minecraft.net/texture/b7350fd2df430bc4b9aaecd13c51c951e5bfc9f133bad0cdaefe1922a9e47fa3")
		)), new ArrayList<String>(Arrays.asList(
		"&2Troll",
		"&2Goblin",
		"&2Undead Soldier"
		)));
		this.names.put(EntityType.ZOMBIE, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/47d9514acf3b1764af62541295937c0ac433ed45730593782088cae202b4c30d"),
		this.createHead("http://textures.minecraft.net/texture/685ad247c42211de83ce0f7f6dd24290ca34eb25f167ca86279f139005d5b050"),
		this.createHead("http://textures.minecraft.net/texture/37a0154a8f095b7b8d18b9fec87398ee8c34b4238f08d43c108e15a2a99")
		)), new ArrayList<String>(Arrays.asList(
		"&6Flamethrower",
		"&eChimera",
		"&4Flare Shooter"
		)));
		this.names.put(EntityType.BLAZE, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/5ab5fde2f75805ab0f7f0b3605ec5d37a39c5681fdfee6ed7aa42b8ba94d54"),
		this.createHead("http://textures.minecraft.net/texture/944965c2469a3c68c97b57549fb41a990a2e6d641b56aaf4cc52360b198fb4c"),
		this.createHead("http://textures.minecraft.net/texture/6d11cdb6e6494ebd82c5a4e2c5767e13a36175bb23e6dcfe7de7a2453c8feb7c")
		)), new ArrayList<String>(Arrays.asList(
		"&8Deadly Sect",
		"&aPoisoned Argos",
		"&2Green Inferno"
		)));
		this.names.put(EntityType.CAVE_SPIDER, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/da737e2357fb7d48496f16fe3045927bbb3e1b34639e30874d22dca233ecd5"),
		this.createHead("http://textures.minecraft.net/texture/4dfe1ae340e9af91d6d33060ad606ee57ae3f82261e1299091c1bdb6e9bcd0f9"),
		this.createHead("http://textures.minecraft.net/texture/e966557dbaa35f358bc7f0e495c178a670232b3dc9677b917dc42e1f446b65cb")
		)), new ArrayList<String>(Arrays.asList(
		"&1Anrgy Hippocampus",
		"&3Pontos",
		"&bNereus"
		)));
		this.names.put(EntityType.DROWNED, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/6c3701fe7131861ed4cbe3c0846923400845e470167283a31747807f3fce423c"),
		this.createHead("http://textures.minecraft.net/texture/6085b464afbdef4b17ac618789c526897542f839c41debd9b401c719d3e36f55"),
		this.createHead("http://textures.minecraft.net/texture/7c49ad4680d0742ff1494b3c9ce9a06e34c4f03b319cb703fa1c8d555cc302e4")
		)), new ArrayList<String>(Arrays.asList(
		"&7Orkeanos",
		"&8Hyperion",
		"&9Mnemosyne"
		)));
		this.names.put(EntityType.ELDER_GUARDIAN, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/86debf383e34378d1313196972e05b192f70f2b2f1167cea8806fc7d25a556b6"),
		this.createHead("http://textures.minecraft.net/texture/c2b9679263d335d901f132820085babc96459fef3fb38273adb130de1e07aa5f"),
		this.createHead("http://textures.minecraft.net/texture/169380db31142f32804adb711ce7dbf2acc4677cfd529ef4bdd4afaa0aee15b")
		)), new ArrayList<String>(Arrays.asList(
		"&8Voldemort",
		"&2Ill-Bringer",
		"&0Thanatos"
		)));
		this.names.put(EntityType.EVOKER, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/a6abb8d88a97d1c2bbf4e69dc62c2b1a83de925f133f7482e89089f682987386"),
		this.createHead("http://textures.minecraft.net/texture/ec88ed962646e7d3b4e2e3ec8756f482452a93d7b5e94ac8dec535c5cf997"),
		this.createHead("http://textures.minecraft.net/texture/3fcb82e17fbba219ba24e1bcc06a311576951a75a1a778da6cd3a234f6fc57")
		)), new ArrayList<String>(Arrays.asList(
		"&9Alseid",
		"&9Amemasu",
		"&3Ayakashi"
		)));
		this.names.put(EntityType.GUARDIAN, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/f83a2aa9d3734b919ac24c9659e5e0f86ecafbf64d4788cfa433bbec189e8"),
		this.createHead("http://textures.minecraft.net/texture/9a91fc8c9f6bd69f9a01e4d4f83b9ac63b1c7bb3726556e3ef46c0d71d0f60d6"),
		this.createHead("http://textures.minecraft.net/texture/63c795ce9eeb6da43315716d6ccb85b78aafe66eda1bbcf969b20284be19a65")
		)), new ArrayList<String>(Arrays.asList(
		"&aBasilisk",
		"&2Eris",
		"&aMorpheus"
		)));
		this.names.put(EntityType.HUSK, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/25f03c7f03a85b4ae5ae19a7edf6d3d689d2e36a4ed37f40a4ea7ce633d782d3"),
		this.createHead("http://textures.minecraft.net/texture/8beee8b5e18397f7814770c09e91d481c3f8c610f5f005e324be35c23bd86bd1"),
		this.createHead("http://textures.minecraft.net/texture/5b64ead66119ff2e3ddf25ebf55a7497bc991c9672dcccad0b24fa3e93dd189c")
		)), new ArrayList<String>(Arrays.asList(
		"&eMedeia",
		"&4Half Devil",
		"&4Satan's Devil"
		)));
		this.names.put(EntityType.ILLUSIONER, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/e47bd31a123ea21edb3e49a33f38e90905b8c7cac4ff7803f6865e48399b6a"),
		this.createHead("http://textures.minecraft.net/texture/333052e6b7a87923bdb88f94185e84b562dc5b854b7bb01cc017f22c5a8a4"),
		this.createHead("http://textures.minecraft.net/texture/d412342b6165e7ff251898d4c859befc45c89bc26e7243062193497385f3")
		)), new ArrayList<String>(Arrays.asList(
		"&eCursed Aigeiros",
		"&7Deadly Ampelos",
		"&4Possesed Orea"
		)));
		this.names.put(EntityType.MAGMA_CUBE, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/664698fea5ec3f2fd7db7a8e3f4e989a1716035c2bd3666434ba1af58157"),
		this.createHead("http://textures.minecraft.net/texture/bb2abd66939f4cb7257a88cf52fbc6fdceec1433ec2a6ef16d62e34f6238781"),
		this.createHead("http://textures.minecraft.net/texture/343488e8183a0e42bb367d76d27a6adc4d4ccf20952055c4a962d7a0bede932c")
		)), new ArrayList<String>(Arrays.asList(
		"&dPossesed Pig",
		"&5Schmedgar",
		"&dUndead Pig"
		)));
		this.names.put(EntityType.PIG_ZOMBIE, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/664698fea5ec3f2fd7db7a8e3f4e989a1716035c2bd3666434ba1af58157"),
		this.createHead("http://textures.minecraft.net/texture/bb2abd66939f4cb7257a88cf52fbc6fdceec1433ec2a6ef16d62e34f6238781"),
		this.createHead("http://textures.minecraft.net/texture/343488e8183a0e42bb367d76d27a6adc4d4ccf20952055c4a962d7a0bede932c")
		)), new ArrayList<String>(Arrays.asList(
		"&6Cursed Elf",
		"&7Ugly Bones",
		"&8Undieable"
		)));
		this.names.put(EntityType.SKELETON, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/ae5488994346c2f3b84f3026489b708e869fe84815ffd5ebfbe42ac9d4d03680"),
		this.createHead("http://textures.minecraft.net/texture/5e9f6fc03d73dfaba7f9ad24648e5b59ad9cc022aec524dcdef4434d125b"),
		this.createHead("http://textures.minecraft.net/texture/aebc508e9509813007aaca0a349cddbff16348c2d39acee9f243e1490578c910")
		)), new ArrayList<String>(Arrays.asList(
		"&aUgly Bunny",
		"&8Hydra",
		"&bLailaps"
		)));
		this.names.put(EntityType.SLIME, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/cd4fdad5a6104aa549d1e76d73a3c6fe3c6724bf09f7ffcc02f33f9ede7fade"),
		this.createHead("http://textures.minecraft.net/texture/47e637f1ca735ef35467a62759dae41cf4ffb5c2c14aad42ea8ace9bc7e68ec"),
		this.createHead("http://textures.minecraft.net/texture/409256c59b825cd3944548fd57ba978cbea5b02f7bc063c79835068b9ebe3380")
		)), new ArrayList<String>(Arrays.asList(
		"&0Black Widdow",
		"&dUgly Thing",
		"&cDeath Prophesy"
		)));
		this.names.put(EntityType.SPIDER, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/8300986ed0a04ea79904f6ae53f49ed3a0ff5b1df62bba622ecbd3777f156df8"),
		this.createHead("http://textures.minecraft.net/texture/b6d79c0268747941df9a2a45103cbd731fdedcba588f643b670fd77aa2bd918c"),
		this.createHead("http://textures.minecraft.net/texture/9d7e3b19ac4f3dee9c5677c135333b9d35a7f568b63d1ef4ada4b068b5a25")
		)), new ArrayList<String>(Arrays.asList(
		"&8Ghul",
		"&7Reaper",
		"&4Angry Sniper"
		)));
		this.names.put(EntityType.STRAY, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/105f707b1f9160adc2f769760998b6b50f49100cc8dff13ae9295a74a20b0779"),
		this.createHead("http://textures.minecraft.net/texture/8840d932a84f87e926929e658737863b8a26184c2124433a8ee6601d88913497"),
		this.createHead("http://textures.minecraft.net/texture/ab437600155f15c7a734c9c62259ae1bc55a1aed456bcc3ca607de97a2d2705a")
		)), new ArrayList<String>(Arrays.asList(
		"&bPossesed Servant",
		"&cBetrayed Servant",
		"&4Dead Indicator"
		)));
		this.names.put(EntityType.VINDICATOR, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/12cae432d84c3594e7e76b3f52d2c143ed403a658a8e488cb7f566e0a270670"),
		this.createHead("http://textures.minecraft.net/texture/2d27f130c1acdd784ceee2b75fb181fa52ff3a75024574eb4af7faf1ae75bc"),
		this.createHead("http://textures.minecraft.net/texture/b67da78b47eee622edfe1675f7795619e1fbe726de684639a0fbb3c5c027d8be")
		)), new ArrayList<String>(Arrays.asList(
		"&dMagician",
		"&4Evil Eye",
		"&3Vampir"
		)));
		this.names.put(EntityType.WITCH, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/927af941c8b33e633aa7ecf7cfc25e9fafb6f86c51a171c5e855a9efc2ad70dc"),
		this.createHead("http://textures.minecraft.net/texture/4af424e09292176e6d0183adfa7714eb1139251d565a3a666f8bd94f8407f04d"),
		this.createHead("http://textures.minecraft.net/texture/f2e6858d5e23dcfcc7a662b3fc6e9df84e7c21a47252a60742ad22d3ce94e")
		)), new ArrayList<String>(Arrays.asList(
		"&6Cursed Orc",
		"&6Beserker",
		"&0Black Devil"
		)));
		this.names.put(EntityType.WITHER_SKELETON, pair);
		pair = new Pair<>(new ArrayList<ItemStack>(Arrays.asList(
		this.createHead("http://textures.minecraft.net/texture/2bfe4a59b164548732fd5b754f266411969a2c2feb08a89b40a1293244abec"),
		this.createHead("http://textures.minecraft.net/texture/12f1cc1561a433a3e40211eba4e965c5fe4781b8baad49d01c0c3ca8dd0ad4aa"),
		this.createHead("http://textures.minecraft.net/texture/62be9577b0fabb2c70f9b79a5ecfac7daad0cccf76a7d5e97ef2bde05580ecae")
		)), new ArrayList<String>(Arrays.asList(
		"&8Rat",
		"&6Worm",
		"&dLovely Potato"
		)));
		this.names.put(EntityType.SILVERFISH, pair);
	}
	public double randomDouble() {
		double i = ThreadLocalRandom.current().nextDouble() * 100;
		return i;
	}
	public double randomDouble1(int tier, int level) {
		double i = (5.00 + 7.50 * tier) + (0.25 * level);
		return i;
	}
	public int randomHead() {
		int i = new Random().nextInt(3);
		return i;
	}
	public int dice() {
		int i = new Random().nextInt(3) + 1;
		return i;
	}
	public double enchantmentChance() {
		double i = ThreadLocalRandom.current().nextDouble() * 100;
		return i;
	}
	public int enchantmentLevel(int tier) {
		int i = new Random().nextInt(tier) + 1;
		return i;
	}
	public double randomLocOffSet() {
		double i = ThreadLocalRandom.current().nextDouble() * 20.00 - 10.00;
		return i;
	}
	public double randomLocOffSetY() {
		double i = ThreadLocalRandom.current().nextDouble() * 2.00 + 1.00;
		return i;
	}
}
