package dev.anhcraft.craftkit.utils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import dev.anhcraft.craftkit.cb_common.NMSVersion;
import dev.anhcraft.jvmkit.utils.EnumUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * An utility class to work with {@link Material} as well as {@link MaterialData}.
 */
public class MaterialUtil {
    private static final Table<String, Integer, String> LEGACY = HashBasedTable.create();
    private static final List<Material> SKULL_TYPES = new ArrayList<>();
    private static final List<Material> ARMOR_TYPES = new ArrayList<>();
    private static final List<Material> ORE_TYPES = new ArrayList<>();
    private static final List<Material> BOAT_TYPES = new ArrayList<>();
    private static final List<Material> BUTTON_TYPES = new ArrayList<>();
    private static final List<Material> DOOR_TYPES = new ArrayList<>();
    private static final List<Material> FENCE_TYPES = new ArrayList<>();
    private static final List<Material> FENCE_GATE_TYPES = new ArrayList<>();
    private static final List<Material> LEAVES_TYPES = new ArrayList<>();
    private static final List<Material> LOG_TYPES = new ArrayList<>();
    private static final List<Material> STRIPPED_LOG_TYPES = new ArrayList<>();
    private static final List<Material> PLANKS_TYPES = new ArrayList<>();
    private static final List<Material> PRESSURE_PLATE_TYPES = new ArrayList<>();
    private static final List<Material> SAPLING_TYPES = new ArrayList<>();
    private static final List<Material> SLAB_TYPES = new ArrayList<>();
    private static final List<Material> STAIRS_TYPES = new ArrayList<>();
    private static final List<Material> TRAPDOOR_TYPES = new ArrayList<>();
    private static final List<Material> WOOD_TYPES = new ArrayList<>();
    private static final List<Material> STRIPPED_WOOD_TYPES = new ArrayList<>();
    private static final List<Material> BANNER_TYPES = new ArrayList<>();
    private static final List<Material> BED_TYPES = new ArrayList<>();
    private static final List<Material> CARPET_TYPES = new ArrayList<>();
    private static final List<Material> CONCRETE_TYPES = new ArrayList<>();
    private static final List<Material> CONCRETE_POWDER_TYPES = new ArrayList<>();
    private static final List<Material> GLAZED_TERRACOTTA_TYPES = new ArrayList<>();
    private static final List<Material> SHULKER_BOX_TYPES = new ArrayList<>();
    private static final List<Material> STAINED_GLASS_TYPES = new ArrayList<>();
    private static final List<Material> STAINED_GLASS_PANE_TYPES = new ArrayList<>();
    private static final List<Material> TERRACOTTA_TYPES = new ArrayList<>();
    private static final List<Material> WALL_BANNER_TYPES = new ArrayList<>();
    private static final List<Material> WOOL_TYPES = new ArrayList<>();
    private static final List<Material> MUSIC_DISC_TYPES = new ArrayList<>();
    private static final List<Material> BUSH_TYPES = new ArrayList<>();
    private static final List<Material> DYE_TYPES = new ArrayList<>();
    private static final List<Material> CORAL_TYPES = new ArrayList<>();
    private static final List<Material> CORAL_BLOCK_TYPES = new ArrayList<>();
    private static final List<Material> CORAL_FAN_TYPES = new ArrayList<>();
    private static final List<Material> CORAL_WALL_FAN_TYPES = new ArrayList<>();
    private static final List<Material> DEAD_CORAL_TYPES = new ArrayList<>();
    private static final List<Material> DEAD_CORAL_BLOCK_TYPES = new ArrayList<>();
    private static final List<Material> DEAD_CORAL_FAN_TYPES = new ArrayList<>();
    private static final List<Material> DEAD_CORAL_WALL_FAN_TYPES = new ArrayList<>();
    private static final List<Material> HELMET_TYPES = new ArrayList<>();
    private static final List<Material> CHESTPLATE_TYPES = new ArrayList<>();
    private static final List<Material> LEGGINGS_TYPES = new ArrayList<>();
    private static final List<Material> BOOTS_TYPES = new ArrayList<>();
    private static final List<Material> SWORD_TYPES = new ArrayList<>();
    private static final List<Material> SHOVEL_TYPES = new ArrayList<>();
    private static final List<Material> AXE_TYPES = new ArrayList<>();
    private static final List<Material> HOE_TYPES = new ArrayList<>();
    private static final List<Material> PICKAXE_TYPES = new ArrayList<>();

    static {
        /*
        The followings are generated automatically using the code:

        for(Material mt : Material.values()){
            if(mt.isLegacy()) {
                EnumSet<Material> set = EnumSet.noneOf(Material.class);
                for (byte i = 0; i < Byte.MAX_VALUE; i++) {
                    Material x = CraftLegacy.fromLegacy(new MaterialData(mt, i));
                    if(set.contains(x)) continue;
                    if(mt == Material.LEGACY_AIR || !x.isAir()) {
                        System.out.println("LEGACY.put(\"" + mt.name().substring("LEGACY_".length()) + "\", " + i + ", \"" + x.name() + "\");");
                        set.add(x);
                    }
                }
            }
        }
         */
        LEGACY.put("AIR", 0, "AIR");
        LEGACY.put("STONE", 0, "STONE");
        LEGACY.put("STONE", 1, "GRANITE");
        LEGACY.put("STONE", 2, "POLISHED_GRANITE");
        LEGACY.put("STONE", 3, "DIORITE");
        LEGACY.put("STONE", 4, "POLISHED_DIORITE");
        LEGACY.put("STONE", 5, "ANDESITE");
        LEGACY.put("STONE", 6, "POLISHED_ANDESITE");
        LEGACY.put("GRASS", 0, "GRASS_BLOCK");
        LEGACY.put("DIRT", 0, "DIRT");
        LEGACY.put("DIRT", 1, "COARSE_DIRT");
        LEGACY.put("DIRT", 2, "PODZOL");
        LEGACY.put("COBBLESTONE", 0, "COBBLESTONE");
        LEGACY.put("WOOD", 0, "OAK_PLANKS");
        LEGACY.put("WOOD", 1, "SPRUCE_PLANKS");
        LEGACY.put("WOOD", 2, "BIRCH_PLANKS");
        LEGACY.put("WOOD", 3, "JUNGLE_PLANKS");
        LEGACY.put("WOOD", 4, "ACACIA_PLANKS");
        LEGACY.put("WOOD", 5, "DARK_OAK_PLANKS");
        LEGACY.put("SAPLING", 0, "OAK_SAPLING");
        LEGACY.put("SAPLING", 1, "SPRUCE_SAPLING");
        LEGACY.put("SAPLING", 2, "BIRCH_SAPLING");
        LEGACY.put("SAPLING", 3, "JUNGLE_SAPLING");
        LEGACY.put("SAPLING", 4, "ACACIA_SAPLING");
        LEGACY.put("SAPLING", 5, "DARK_OAK_SAPLING");
        LEGACY.put("BEDROCK", 0, "BEDROCK");
        LEGACY.put("WATER", 0, "WATER");
        LEGACY.put("STATIONARY_WATER", 0, "WATER");
        LEGACY.put("LAVA", 0, "LAVA");
        LEGACY.put("STATIONARY_LAVA", 0, "LAVA");
        LEGACY.put("SAND", 0, "SAND");
        LEGACY.put("SAND", 1, "RED_SAND");
        LEGACY.put("GRAVEL", 0, "GRAVEL");
        LEGACY.put("GOLD_ORE", 0, "GOLD_ORE");
        LEGACY.put("IRON_ORE", 0, "IRON_ORE");
        LEGACY.put("COAL_ORE", 0, "COAL_ORE");
        LEGACY.put("LOG", 0, "OAK_LOG");
        LEGACY.put("LOG", 1, "SPRUCE_LOG");
        LEGACY.put("LOG", 2, "BIRCH_LOG");
        LEGACY.put("LOG", 3, "JUNGLE_LOG");
        LEGACY.put("LOG", 12, "OAK_WOOD");
        LEGACY.put("LOG", 13, "SPRUCE_WOOD");
        LEGACY.put("LOG", 14, "BIRCH_WOOD");
        LEGACY.put("LOG", 15, "JUNGLE_WOOD");
        LEGACY.put("LEAVES", 0, "OAK_LEAVES");
        LEGACY.put("LEAVES", 1, "SPRUCE_LEAVES");
        LEGACY.put("LEAVES", 2, "BIRCH_LEAVES");
        LEGACY.put("LEAVES", 3, "JUNGLE_LEAVES");
        LEGACY.put("SPONGE", 0, "SPONGE");
        LEGACY.put("SPONGE", 1, "WET_SPONGE");
        LEGACY.put("GLASS", 0, "GLASS");
        LEGACY.put("LAPIS_ORE", 0, "LAPIS_ORE");
        LEGACY.put("LAPIS_BLOCK", 0, "LAPIS_BLOCK");
        LEGACY.put("DISPENSER", 0, "DISPENSER");
        LEGACY.put("SANDSTONE", 0, "SANDSTONE");
        LEGACY.put("SANDSTONE", 1, "CHISELED_SANDSTONE");
        LEGACY.put("SANDSTONE", 2, "CUT_SANDSTONE");
        LEGACY.put("NOTE_BLOCK", 0, "NOTE_BLOCK");
        LEGACY.put("BED_BLOCK", 0, "RED_BED");
        LEGACY.put("POWERED_RAIL", 0, "POWERED_RAIL");
        LEGACY.put("DETECTOR_RAIL", 0, "DETECTOR_RAIL");
        LEGACY.put("PISTON_STICKY_BASE", 0, "STICKY_PISTON");
        LEGACY.put("WEB", 0, "COBWEB");
        LEGACY.put("LONG_GRASS", 0, "DEAD_BUSH");
        LEGACY.put("LONG_GRASS", 1, "GRASS");
        LEGACY.put("LONG_GRASS", 2, "FERN");
        LEGACY.put("DEAD_BUSH", 0, "DEAD_BUSH");
        LEGACY.put("PISTON_BASE", 0, "PISTON");
        LEGACY.put("PISTON_EXTENSION", 0, "PISTON_HEAD");
        LEGACY.put("WOOL", 0, "WHITE_WOOL");
        LEGACY.put("WOOL", 1, "ORANGE_WOOL");
        LEGACY.put("WOOL", 2, "MAGENTA_WOOL");
        LEGACY.put("WOOL", 3, "LIGHT_BLUE_WOOL");
        LEGACY.put("WOOL", 4, "YELLOW_WOOL");
        LEGACY.put("WOOL", 5, "LIME_WOOL");
        LEGACY.put("WOOL", 6, "PINK_WOOL");
        LEGACY.put("WOOL", 7, "GRAY_WOOL");
        LEGACY.put("WOOL", 8, "LIGHT_GRAY_WOOL");
        LEGACY.put("WOOL", 9, "CYAN_WOOL");
        LEGACY.put("WOOL", 10, "PURPLE_WOOL");
        LEGACY.put("WOOL", 11, "BLUE_WOOL");
        LEGACY.put("WOOL", 12, "BROWN_WOOL");
        LEGACY.put("WOOL", 13, "GREEN_WOOL");
        LEGACY.put("WOOL", 14, "RED_WOOL");
        LEGACY.put("WOOL", 15, "BLACK_WOOL");
        LEGACY.put("PISTON_MOVING_PIECE", 0, "MOVING_PISTON");
        LEGACY.put("YELLOW_FLOWER", 0, "DANDELION");
        LEGACY.put("RED_ROSE", 0, "POPPY");
        LEGACY.put("RED_ROSE", 1, "BLUE_ORCHID");
        LEGACY.put("RED_ROSE", 2, "ALLIUM");
        LEGACY.put("RED_ROSE", 3, "AZURE_BLUET");
        LEGACY.put("RED_ROSE", 4, "RED_TULIP");
        LEGACY.put("RED_ROSE", 5, "ORANGE_TULIP");
        LEGACY.put("RED_ROSE", 6, "WHITE_TULIP");
        LEGACY.put("RED_ROSE", 7, "PINK_TULIP");
        LEGACY.put("RED_ROSE", 8, "OXEYE_DAISY");
        LEGACY.put("BROWN_MUSHROOM", 0, "BROWN_MUSHROOM");
        LEGACY.put("RED_MUSHROOM", 0, "RED_MUSHROOM");
        LEGACY.put("GOLD_BLOCK", 0, "GOLD_BLOCK");
        LEGACY.put("IRON_BLOCK", 0, "IRON_BLOCK");
        LEGACY.put("DOUBLE_STEP", 0, "SMOOTH_STONE_SLAB");
        LEGACY.put("DOUBLE_STEP", 1, "SANDSTONE_SLAB");
        LEGACY.put("DOUBLE_STEP", 2, "PETRIFIED_OAK_SLAB");
        LEGACY.put("DOUBLE_STEP", 3, "COBBLESTONE_SLAB");
        LEGACY.put("DOUBLE_STEP", 4, "BRICK_SLAB");
        LEGACY.put("DOUBLE_STEP", 5, "STONE_BRICK_SLAB");
        LEGACY.put("DOUBLE_STEP", 6, "NETHER_BRICK_SLAB");
        LEGACY.put("DOUBLE_STEP", 7, "QUARTZ_SLAB");
        LEGACY.put("DOUBLE_STEP", 8, "SMOOTH_STONE");
        LEGACY.put("DOUBLE_STEP", 9, "SMOOTH_SANDSTONE");
        LEGACY.put("DOUBLE_STEP", 15, "SMOOTH_QUARTZ");
        LEGACY.put("STEP", 0, "SMOOTH_STONE_SLAB");
        LEGACY.put("STEP", 1, "SANDSTONE_SLAB");
        LEGACY.put("STEP", 2, "PETRIFIED_OAK_SLAB");
        LEGACY.put("STEP", 3, "COBBLESTONE_SLAB");
        LEGACY.put("STEP", 4, "BRICK_SLAB");
        LEGACY.put("STEP", 5, "STONE_BRICK_SLAB");
        LEGACY.put("STEP", 6, "NETHER_BRICK_SLAB");
        LEGACY.put("STEP", 7, "QUARTZ_SLAB");
        LEGACY.put("BRICK", 0, "BRICKS");
        LEGACY.put("TNT", 0, "TNT");
        LEGACY.put("BOOKSHELF", 0, "BOOKSHELF");
        LEGACY.put("MOSSY_COBBLESTONE", 0, "MOSSY_COBBLESTONE");
        LEGACY.put("OBSIDIAN", 0, "OBSIDIAN");
        LEGACY.put("TORCH", 0, "WALL_TORCH");
        LEGACY.put("TORCH", 5, "TORCH");
        LEGACY.put("FIRE", 0, "FIRE");
        LEGACY.put("MOB_SPAWNER", 0, "SPAWNER");
        LEGACY.put("WOOD_STAIRS", 0, "OAK_STAIRS");
        LEGACY.put("CHEST", 0, "CHEST");
        LEGACY.put("REDSTONE_WIRE", 0, "REDSTONE_WIRE");
        LEGACY.put("DIAMOND_ORE", 0, "DIAMOND_ORE");
        LEGACY.put("DIAMOND_BLOCK", 0, "DIAMOND_BLOCK");
        LEGACY.put("WORKBENCH", 0, "CRAFTING_TABLE");
        LEGACY.put("CROPS", 0, "WHEAT");
        LEGACY.put("SOIL", 0, "FARMLAND");
        LEGACY.put("FURNACE", 0, "FURNACE");
        LEGACY.put("BURNING_FURNACE", 0, "FURNACE");
        LEGACY.put("SIGN_POST", 0, "OAK_SIGN");
        LEGACY.put("WOODEN_DOOR", 0, "OAK_DOOR");
        LEGACY.put("LADDER", 0, "LADDER");
        LEGACY.put("RAILS", 0, "RAIL");
        LEGACY.put("COBBLESTONE_STAIRS", 0, "COBBLESTONE_STAIRS");
        LEGACY.put("WALL_SIGN", 0, "OAK_WALL_SIGN");
        LEGACY.put("LEVER", 0, "LEVER");
        LEGACY.put("STONE_PLATE", 0, "STONE_PRESSURE_PLATE");
        LEGACY.put("IRON_DOOR_BLOCK", 0, "IRON_DOOR");
        LEGACY.put("WOOD_PLATE", 0, "OAK_PRESSURE_PLATE");
        LEGACY.put("REDSTONE_ORE", 0, "REDSTONE_ORE");
        LEGACY.put("GLOWING_REDSTONE_ORE", 0, "REDSTONE_ORE");
        LEGACY.put("REDSTONE_TORCH_OFF", 0, "REDSTONE_WALL_TORCH");
        LEGACY.put("REDSTONE_TORCH_OFF", 5, "REDSTONE_TORCH");
        LEGACY.put("REDSTONE_TORCH_ON", 0, "REDSTONE_WALL_TORCH");
        LEGACY.put("REDSTONE_TORCH_ON", 5, "REDSTONE_TORCH");
        LEGACY.put("STONE_BUTTON", 0, "STONE_BUTTON");
        LEGACY.put("SNOW", 0, "SNOW");
        LEGACY.put("ICE", 0, "ICE");
        LEGACY.put("SNOW_BLOCK", 0, "SNOW_BLOCK");
        LEGACY.put("CACTUS", 0, "CACTUS");
        LEGACY.put("CLAY", 0, "CLAY");
        LEGACY.put("SUGAR_CANE_BLOCK", 0, "SUGAR_CANE");
        LEGACY.put("JUKEBOX", 0, "JUKEBOX");
        LEGACY.put("FENCE", 0, "OAK_FENCE");
        LEGACY.put("PUMPKIN", 0, "CARVED_PUMPKIN");
        LEGACY.put("NETHERRACK", 0, "NETHERRACK");
        LEGACY.put("SOUL_SAND", 0, "SOUL_SAND");
        LEGACY.put("GLOWSTONE", 0, "GLOWSTONE");
        LEGACY.put("PORTAL", 0, "NETHER_PORTAL");
        LEGACY.put("JACK_O_LANTERN", 0, "JACK_O_LANTERN");
        LEGACY.put("CAKE_BLOCK", 0, "CAKE");
        LEGACY.put("DIODE_BLOCK_OFF", 0, "REPEATER");
        LEGACY.put("DIODE_BLOCK_ON", 0, "REPEATER");
        LEGACY.put("STAINED_GLASS", 0, "WHITE_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 1, "ORANGE_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 2, "MAGENTA_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 3, "LIGHT_BLUE_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 4, "YELLOW_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 5, "LIME_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 6, "PINK_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 7, "GRAY_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 8, "LIGHT_GRAY_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 9, "CYAN_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 10, "PURPLE_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 11, "BLUE_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 12, "BROWN_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 13, "GREEN_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 14, "RED_STAINED_GLASS");
        LEGACY.put("STAINED_GLASS", 15, "BLACK_STAINED_GLASS");
        LEGACY.put("TRAP_DOOR", 0, "OAK_TRAPDOOR");
        LEGACY.put("MONSTER_EGGS", 0, "INFESTED_STONE");
        LEGACY.put("MONSTER_EGGS", 1, "INFESTED_COBBLESTONE");
        LEGACY.put("MONSTER_EGGS", 2, "INFESTED_STONE_BRICKS");
        LEGACY.put("MONSTER_EGGS", 3, "INFESTED_MOSSY_STONE_BRICKS");
        LEGACY.put("MONSTER_EGGS", 4, "INFESTED_CRACKED_STONE_BRICKS");
        LEGACY.put("MONSTER_EGGS", 5, "INFESTED_CHISELED_STONE_BRICKS");
        LEGACY.put("SMOOTH_BRICK", 0, "STONE_BRICKS");
        LEGACY.put("SMOOTH_BRICK", 1, "MOSSY_STONE_BRICKS");
        LEGACY.put("SMOOTH_BRICK", 2, "CRACKED_STONE_BRICKS");
        LEGACY.put("SMOOTH_BRICK", 3, "CHISELED_STONE_BRICKS");
        LEGACY.put("HUGE_MUSHROOM_1", 0, "BROWN_MUSHROOM_BLOCK");
        LEGACY.put("HUGE_MUSHROOM_1", 10, "MUSHROOM_STEM");
        LEGACY.put("HUGE_MUSHROOM_2", 0, "RED_MUSHROOM_BLOCK");
        LEGACY.put("HUGE_MUSHROOM_2", 10, "MUSHROOM_STEM");
        LEGACY.put("IRON_FENCE", 0, "IRON_BARS");
        LEGACY.put("THIN_GLASS", 0, "GLASS_PANE");
        LEGACY.put("MELON_BLOCK", 0, "MELON");
        LEGACY.put("PUMPKIN_STEM", 0, "PUMPKIN_STEM");
        LEGACY.put("MELON_STEM", 0, "MELON_STEM");
        LEGACY.put("VINE", 0, "VINE");
        LEGACY.put("FENCE_GATE", 0, "OAK_FENCE_GATE");
        LEGACY.put("BRICK_STAIRS", 0, "BRICK_STAIRS");
        LEGACY.put("SMOOTH_STAIRS", 0, "STONE_BRICK_STAIRS");
        LEGACY.put("MYCEL", 0, "MYCELIUM");
        LEGACY.put("WATER_LILY", 0, "LILY_PAD");
        LEGACY.put("NETHER_BRICK", 0, "NETHER_BRICKS");
        LEGACY.put("NETHER_FENCE", 0, "NETHER_BRICK_FENCE");
        LEGACY.put("NETHER_BRICK_STAIRS", 0, "NETHER_BRICK_STAIRS");
        LEGACY.put("NETHER_WARTS", 0, "NETHER_WART");
        LEGACY.put("ENCHANTMENT_TABLE", 0, "ENCHANTING_TABLE");
        LEGACY.put("BREWING_STAND", 0, "BREWING_STAND");
        LEGACY.put("CAULDRON", 0, "CAULDRON");
        LEGACY.put("ENDER_PORTAL", 0, "END_PORTAL");
        LEGACY.put("ENDER_PORTAL_FRAME", 0, "END_PORTAL_FRAME");
        LEGACY.put("ENDER_STONE", 0, "END_STONE");
        LEGACY.put("DRAGON_EGG", 0, "DRAGON_EGG");
        LEGACY.put("REDSTONE_LAMP_OFF", 0, "REDSTONE_LAMP");
        LEGACY.put("REDSTONE_LAMP_ON", 0, "REDSTONE_LAMP");
        LEGACY.put("WOOD_DOUBLE_STEP", 0, "OAK_SLAB");
        LEGACY.put("WOOD_DOUBLE_STEP", 1, "SPRUCE_SLAB");
        LEGACY.put("WOOD_DOUBLE_STEP", 2, "BIRCH_SLAB");
        LEGACY.put("WOOD_DOUBLE_STEP", 3, "JUNGLE_SLAB");
        LEGACY.put("WOOD_DOUBLE_STEP", 4, "ACACIA_SLAB");
        LEGACY.put("WOOD_DOUBLE_STEP", 5, "DARK_OAK_SLAB");
        LEGACY.put("WOOD_STEP", 0, "OAK_SLAB");
        LEGACY.put("WOOD_STEP", 1, "SPRUCE_SLAB");
        LEGACY.put("WOOD_STEP", 2, "BIRCH_SLAB");
        LEGACY.put("WOOD_STEP", 3, "JUNGLE_SLAB");
        LEGACY.put("WOOD_STEP", 4, "ACACIA_SLAB");
        LEGACY.put("WOOD_STEP", 5, "DARK_OAK_SLAB");
        LEGACY.put("COCOA", 0, "COCOA");
        LEGACY.put("SANDSTONE_STAIRS", 0, "SANDSTONE_STAIRS");
        LEGACY.put("EMERALD_ORE", 0, "EMERALD_ORE");
        LEGACY.put("ENDER_CHEST", 0, "ENDER_CHEST");
        LEGACY.put("TRIPWIRE_HOOK", 0, "TRIPWIRE_HOOK");
        LEGACY.put("TRIPWIRE", 0, "TRIPWIRE");
        LEGACY.put("EMERALD_BLOCK", 0, "EMERALD_BLOCK");
        LEGACY.put("SPRUCE_WOOD_STAIRS", 0, "SPRUCE_STAIRS");
        LEGACY.put("BIRCH_WOOD_STAIRS", 0, "BIRCH_STAIRS");
        LEGACY.put("JUNGLE_WOOD_STAIRS", 0, "JUNGLE_STAIRS");
        LEGACY.put("COMMAND", 0, "COMMAND_BLOCK");
        LEGACY.put("BEACON", 0, "BEACON");
        LEGACY.put("COBBLE_WALL", 0, "COBBLESTONE_WALL");
        LEGACY.put("COBBLE_WALL", 1, "MOSSY_COBBLESTONE_WALL");
        LEGACY.put("FLOWER_POT", 0, "POTTED_CACTUS");
        LEGACY.put("CARROT", 0, "CARROTS");
        LEGACY.put("POTATO", 0, "POTATOES");
        LEGACY.put("WOOD_BUTTON", 0, "OAK_BUTTON");
        LEGACY.put("ANVIL", 0, "ANVIL");
        LEGACY.put("ANVIL", 4, "CHIPPED_ANVIL");
        LEGACY.put("ANVIL", 8, "DAMAGED_ANVIL");
        LEGACY.put("TRAPPED_CHEST", 0, "TRAPPED_CHEST");
        LEGACY.put("GOLD_PLATE", 0, "LIGHT_WEIGHTED_PRESSURE_PLATE");
        LEGACY.put("IRON_PLATE", 0, "HEAVY_WEIGHTED_PRESSURE_PLATE");
        LEGACY.put("REDSTONE_COMPARATOR_OFF", 0, "COMPARATOR");
        LEGACY.put("REDSTONE_COMPARATOR_ON", 0, "COMPARATOR");
        LEGACY.put("DAYLIGHT_DETECTOR", 0, "DAYLIGHT_DETECTOR");
        LEGACY.put("REDSTONE_BLOCK", 0, "REDSTONE_BLOCK");
        LEGACY.put("QUARTZ_ORE", 0, "NETHER_QUARTZ_ORE");
        LEGACY.put("HOPPER", 0, "HOPPER");
        LEGACY.put("QUARTZ_BLOCK", 0, "QUARTZ_BLOCK");
        LEGACY.put("QUARTZ_BLOCK", 1, "CHISELED_QUARTZ_BLOCK");
        LEGACY.put("QUARTZ_BLOCK", 2, "QUARTZ_PILLAR");
        LEGACY.put("QUARTZ_STAIRS", 0, "QUARTZ_STAIRS");
        LEGACY.put("ACTIVATOR_RAIL", 0, "ACTIVATOR_RAIL");
        LEGACY.put("DROPPER", 0, "DROPPER");
        LEGACY.put("STAINED_CLAY", 0, "WHITE_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 1, "ORANGE_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 2, "MAGENTA_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 3, "LIGHT_BLUE_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 4, "YELLOW_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 5, "LIME_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 6, "PINK_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 7, "GRAY_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 8, "LIGHT_GRAY_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 9, "CYAN_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 10, "PURPLE_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 11, "BLUE_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 12, "BROWN_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 13, "GREEN_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 14, "RED_TERRACOTTA");
        LEGACY.put("STAINED_CLAY", 15, "BLACK_TERRACOTTA");
        LEGACY.put("STAINED_GLASS_PANE", 0, "WHITE_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 1, "ORANGE_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 2, "MAGENTA_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 3, "LIGHT_BLUE_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 4, "YELLOW_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 5, "LIME_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 6, "PINK_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 7, "GRAY_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 8, "LIGHT_GRAY_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 9, "CYAN_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 10, "PURPLE_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 11, "BLUE_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 12, "BROWN_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 13, "GREEN_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 14, "RED_STAINED_GLASS_PANE");
        LEGACY.put("STAINED_GLASS_PANE", 15, "BLACK_STAINED_GLASS_PANE");
        LEGACY.put("LEAVES_2", 0, "ACACIA_LEAVES");
        LEGACY.put("LEAVES_2", 1, "DARK_OAK_LEAVES");
        LEGACY.put("LOG_2", 0, "ACACIA_LOG");
        LEGACY.put("LOG_2", 1, "DARK_OAK_LOG");
        LEGACY.put("LOG_2", 12, "ACACIA_WOOD");
        LEGACY.put("LOG_2", 13, "DARK_OAK_WOOD");
        LEGACY.put("ACACIA_STAIRS", 0, "ACACIA_STAIRS");
        LEGACY.put("DARK_OAK_STAIRS", 0, "DARK_OAK_STAIRS");
        LEGACY.put("SLIME_BLOCK", 0, "SLIME_BLOCK");
        LEGACY.put("BARRIER", 0, "BARRIER");
        LEGACY.put("IRON_TRAPDOOR", 0, "IRON_TRAPDOOR");
        LEGACY.put("PRISMARINE", 0, "PRISMARINE");
        LEGACY.put("PRISMARINE", 1, "PRISMARINE_BRICKS");
        LEGACY.put("PRISMARINE", 2, "DARK_PRISMARINE");
        LEGACY.put("SEA_LANTERN", 0, "SEA_LANTERN");
        LEGACY.put("HAY_BLOCK", 0, "HAY_BLOCK");
        LEGACY.put("CARPET", 0, "WHITE_CARPET");
        LEGACY.put("CARPET", 1, "ORANGE_CARPET");
        LEGACY.put("CARPET", 2, "MAGENTA_CARPET");
        LEGACY.put("CARPET", 3, "LIGHT_BLUE_CARPET");
        LEGACY.put("CARPET", 4, "YELLOW_CARPET");
        LEGACY.put("CARPET", 5, "LIME_CARPET");
        LEGACY.put("CARPET", 6, "PINK_CARPET");
        LEGACY.put("CARPET", 7, "GRAY_CARPET");
        LEGACY.put("CARPET", 8, "LIGHT_GRAY_CARPET");
        LEGACY.put("CARPET", 9, "CYAN_CARPET");
        LEGACY.put("CARPET", 10, "PURPLE_CARPET");
        LEGACY.put("CARPET", 11, "BLUE_CARPET");
        LEGACY.put("CARPET", 12, "BROWN_CARPET");
        LEGACY.put("CARPET", 13, "GREEN_CARPET");
        LEGACY.put("CARPET", 14, "RED_CARPET");
        LEGACY.put("CARPET", 15, "BLACK_CARPET");
        LEGACY.put("HARD_CLAY", 0, "TERRACOTTA");
        LEGACY.put("COAL_BLOCK", 0, "COAL_BLOCK");
        LEGACY.put("PACKED_ICE", 0, "PACKED_ICE");
        LEGACY.put("DOUBLE_PLANT", 0, "SUNFLOWER");
        LEGACY.put("DOUBLE_PLANT", 1, "LILAC");
        LEGACY.put("DOUBLE_PLANT", 2, "TALL_GRASS");
        LEGACY.put("DOUBLE_PLANT", 3, "LARGE_FERN");
        LEGACY.put("DOUBLE_PLANT", 4, "ROSE_BUSH");
        LEGACY.put("DOUBLE_PLANT", 5, "PEONY");
        LEGACY.put("STANDING_BANNER", 0, "WHITE_BANNER");
        LEGACY.put("WALL_BANNER", 0, "WHITE_WALL_BANNER");
        LEGACY.put("DAYLIGHT_DETECTOR_INVERTED", 0, "DAYLIGHT_DETECTOR");
        LEGACY.put("RED_SANDSTONE", 0, "RED_SANDSTONE");
        LEGACY.put("RED_SANDSTONE", 1, "CHISELED_RED_SANDSTONE");
        LEGACY.put("RED_SANDSTONE", 2, "CUT_RED_SANDSTONE");
        LEGACY.put("RED_SANDSTONE_STAIRS", 0, "RED_SANDSTONE_STAIRS");
        LEGACY.put("DOUBLE_STONE_SLAB2", 0, "RED_SANDSTONE_SLAB");
        LEGACY.put("DOUBLE_STONE_SLAB2", 8, "SMOOTH_RED_SANDSTONE");
        LEGACY.put("STONE_SLAB2", 0, "RED_SANDSTONE_SLAB");
        LEGACY.put("SPRUCE_FENCE_GATE", 0, "SPRUCE_FENCE_GATE");
        LEGACY.put("BIRCH_FENCE_GATE", 0, "BIRCH_FENCE_GATE");
        LEGACY.put("JUNGLE_FENCE_GATE", 0, "JUNGLE_FENCE_GATE");
        LEGACY.put("DARK_OAK_FENCE_GATE", 0, "DARK_OAK_FENCE_GATE");
        LEGACY.put("ACACIA_FENCE_GATE", 0, "ACACIA_FENCE_GATE");
        LEGACY.put("SPRUCE_FENCE", 0, "SPRUCE_FENCE");
        LEGACY.put("BIRCH_FENCE", 0, "BIRCH_FENCE");
        LEGACY.put("JUNGLE_FENCE", 0, "JUNGLE_FENCE");
        LEGACY.put("DARK_OAK_FENCE", 0, "DARK_OAK_FENCE");
        LEGACY.put("ACACIA_FENCE", 0, "ACACIA_FENCE");
        LEGACY.put("SPRUCE_DOOR", 0, "SPRUCE_DOOR");
        LEGACY.put("BIRCH_DOOR", 0, "BIRCH_DOOR");
        LEGACY.put("JUNGLE_DOOR", 0, "JUNGLE_DOOR");
        LEGACY.put("ACACIA_DOOR", 0, "ACACIA_DOOR");
        LEGACY.put("DARK_OAK_DOOR", 0, "DARK_OAK_DOOR");
        LEGACY.put("END_ROD", 0, "END_ROD");
        LEGACY.put("CHORUS_PLANT", 0, "CHORUS_PLANT");
        LEGACY.put("CHORUS_FLOWER", 0, "CHORUS_FLOWER");
        LEGACY.put("PURPUR_BLOCK", 0, "PURPUR_BLOCK");
        LEGACY.put("PURPUR_PILLAR", 0, "PURPUR_PILLAR");
        LEGACY.put("PURPUR_STAIRS", 0, "PURPUR_STAIRS");
        LEGACY.put("PURPUR_DOUBLE_SLAB", 0, "PURPUR_SLAB");
        LEGACY.put("PURPUR_SLAB", 0, "PURPUR_SLAB");
        LEGACY.put("END_BRICKS", 0, "END_STONE_BRICKS");
        LEGACY.put("BEETROOT_BLOCK", 0, "BEETROOTS");
        LEGACY.put("GRASS_PATH", 0, "GRASS_PATH");
        LEGACY.put("END_GATEWAY", 0, "END_GATEWAY");
        LEGACY.put("COMMAND_REPEATING", 0, "REPEATING_COMMAND_BLOCK");
        LEGACY.put("COMMAND_CHAIN", 0, "CHAIN_COMMAND_BLOCK");
        LEGACY.put("FROSTED_ICE", 0, "FROSTED_ICE");
        LEGACY.put("MAGMA", 0, "MAGMA_BLOCK");
        LEGACY.put("NETHER_WART_BLOCK", 0, "NETHER_WART_BLOCK");
        LEGACY.put("RED_NETHER_BRICK", 0, "RED_NETHER_BRICKS");
        LEGACY.put("BONE_BLOCK", 0, "BONE_BLOCK");
        LEGACY.put("STRUCTURE_VOID", 0, "STRUCTURE_VOID");
        LEGACY.put("OBSERVER", 0, "OBSERVER");
        LEGACY.put("WHITE_SHULKER_BOX", 0, "WHITE_SHULKER_BOX");
        LEGACY.put("ORANGE_SHULKER_BOX", 0, "ORANGE_SHULKER_BOX");
        LEGACY.put("MAGENTA_SHULKER_BOX", 0, "MAGENTA_SHULKER_BOX");
        LEGACY.put("LIGHT_BLUE_SHULKER_BOX", 0, "LIGHT_BLUE_SHULKER_BOX");
        LEGACY.put("YELLOW_SHULKER_BOX", 0, "YELLOW_SHULKER_BOX");
        LEGACY.put("LIME_SHULKER_BOX", 0, "LIME_SHULKER_BOX");
        LEGACY.put("PINK_SHULKER_BOX", 0, "PINK_SHULKER_BOX");
        LEGACY.put("GRAY_SHULKER_BOX", 0, "GRAY_SHULKER_BOX");
        LEGACY.put("SILVER_SHULKER_BOX", 0, "LIGHT_GRAY_SHULKER_BOX");
        LEGACY.put("CYAN_SHULKER_BOX", 0, "CYAN_SHULKER_BOX");
        LEGACY.put("PURPLE_SHULKER_BOX", 0, "PURPLE_SHULKER_BOX"); // not sure why the generated one is SHULKER_BOX
        LEGACY.put("BLUE_SHULKER_BOX", 0, "BLUE_SHULKER_BOX");
        LEGACY.put("BROWN_SHULKER_BOX", 0, "BROWN_SHULKER_BOX");
        LEGACY.put("GREEN_SHULKER_BOX", 0, "GREEN_SHULKER_BOX");
        LEGACY.put("RED_SHULKER_BOX", 0, "RED_SHULKER_BOX");
        LEGACY.put("BLACK_SHULKER_BOX", 0, "BLACK_SHULKER_BOX");
        LEGACY.put("WHITE_GLAZED_TERRACOTTA", 0, "WHITE_GLAZED_TERRACOTTA");
        LEGACY.put("ORANGE_GLAZED_TERRACOTTA", 0, "ORANGE_GLAZED_TERRACOTTA");
        LEGACY.put("MAGENTA_GLAZED_TERRACOTTA", 0, "MAGENTA_GLAZED_TERRACOTTA");
        LEGACY.put("LIGHT_BLUE_GLAZED_TERRACOTTA", 0, "LIGHT_BLUE_GLAZED_TERRACOTTA");
        LEGACY.put("YELLOW_GLAZED_TERRACOTTA", 0, "YELLOW_GLAZED_TERRACOTTA");
        LEGACY.put("LIME_GLAZED_TERRACOTTA", 0, "LIME_GLAZED_TERRACOTTA");
        LEGACY.put("PINK_GLAZED_TERRACOTTA", 0, "PINK_GLAZED_TERRACOTTA");
        LEGACY.put("GRAY_GLAZED_TERRACOTTA", 0, "GRAY_GLAZED_TERRACOTTA");
        LEGACY.put("SILVER_GLAZED_TERRACOTTA", 0, "LIGHT_GRAY_GLAZED_TERRACOTTA");
        LEGACY.put("CYAN_GLAZED_TERRACOTTA", 0, "CYAN_GLAZED_TERRACOTTA");
        LEGACY.put("PURPLE_GLAZED_TERRACOTTA", 0, "PURPLE_GLAZED_TERRACOTTA");
        LEGACY.put("BLUE_GLAZED_TERRACOTTA", 0, "BLUE_GLAZED_TERRACOTTA");
        LEGACY.put("BROWN_GLAZED_TERRACOTTA", 0, "BROWN_GLAZED_TERRACOTTA");
        LEGACY.put("GREEN_GLAZED_TERRACOTTA", 0, "GREEN_GLAZED_TERRACOTTA");
        LEGACY.put("RED_GLAZED_TERRACOTTA", 0, "RED_GLAZED_TERRACOTTA");
        LEGACY.put("BLACK_GLAZED_TERRACOTTA", 0, "BLACK_GLAZED_TERRACOTTA");
        LEGACY.put("CONCRETE", 0, "WHITE_CONCRETE");
        LEGACY.put("CONCRETE", 1, "ORANGE_CONCRETE");
        LEGACY.put("CONCRETE", 2, "MAGENTA_CONCRETE");
        LEGACY.put("CONCRETE", 3, "LIGHT_BLUE_CONCRETE");
        LEGACY.put("CONCRETE", 4, "YELLOW_CONCRETE");
        LEGACY.put("CONCRETE", 5, "LIME_CONCRETE");
        LEGACY.put("CONCRETE", 6, "PINK_CONCRETE");
        LEGACY.put("CONCRETE", 7, "GRAY_CONCRETE");
        LEGACY.put("CONCRETE", 8, "LIGHT_GRAY_CONCRETE");
        LEGACY.put("CONCRETE", 9, "CYAN_CONCRETE");
        LEGACY.put("CONCRETE", 10, "PURPLE_CONCRETE");
        LEGACY.put("CONCRETE", 11, "BLUE_CONCRETE");
        LEGACY.put("CONCRETE", 12, "BROWN_CONCRETE");
        LEGACY.put("CONCRETE", 13, "GREEN_CONCRETE");
        LEGACY.put("CONCRETE", 14, "RED_CONCRETE");
        LEGACY.put("CONCRETE", 15, "BLACK_CONCRETE");
        LEGACY.put("CONCRETE_POWDER", 0, "WHITE_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 1, "ORANGE_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 2, "MAGENTA_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 3, "LIGHT_BLUE_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 4, "YELLOW_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 5, "LIME_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 6, "PINK_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 7, "GRAY_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 8, "LIGHT_GRAY_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 9, "CYAN_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 10, "PURPLE_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 11, "BLUE_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 12, "BROWN_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 13, "GREEN_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 14, "RED_CONCRETE_POWDER");
        LEGACY.put("CONCRETE_POWDER", 15, "BLACK_CONCRETE_POWDER");
        LEGACY.put("STRUCTURE_BLOCK", 0, "STRUCTURE_BLOCK");
        LEGACY.put("IRON_SPADE", 0, "IRON_SHOVEL");
        LEGACY.put("IRON_PICKAXE", 0, "IRON_PICKAXE");
        LEGACY.put("IRON_AXE", 0, "IRON_AXE");
        LEGACY.put("FLINT_AND_STEEL", 0, "FLINT_AND_STEEL");
        LEGACY.put("APPLE", 0, "APPLE");
        LEGACY.put("BOW", 0, "BOW");
        LEGACY.put("ARROW", 0, "ARROW");
        LEGACY.put("COAL", 0, "COAL");
        LEGACY.put("COAL", 1, "CHARCOAL");
        LEGACY.put("DIAMOND", 0, "DIAMOND");
        LEGACY.put("IRON_INGOT", 0, "IRON_INGOT");
        LEGACY.put("GOLD_INGOT", 0, "GOLD_INGOT");
        LEGACY.put("IRON_SWORD", 0, "IRON_SWORD");
        LEGACY.put("WOOD_SWORD", 0, "WOODEN_SWORD");
        LEGACY.put("WOOD_SPADE", 0, "WOODEN_SHOVEL");
        LEGACY.put("WOOD_PICKAXE", 0, "WOODEN_PICKAXE");
        LEGACY.put("WOOD_AXE", 0, "WOODEN_AXE");
        LEGACY.put("STONE_SWORD", 0, "STONE_SWORD");
        LEGACY.put("STONE_SPADE", 0, "STONE_SHOVEL");
        LEGACY.put("STONE_PICKAXE", 0, "STONE_PICKAXE");
        LEGACY.put("STONE_AXE", 0, "STONE_AXE");
        LEGACY.put("DIAMOND_SWORD", 0, "DIAMOND_SWORD");
        LEGACY.put("DIAMOND_SPADE", 0, "DIAMOND_SHOVEL");
        LEGACY.put("DIAMOND_PICKAXE", 0, "DIAMOND_PICKAXE");
        LEGACY.put("DIAMOND_AXE", 0, "DIAMOND_AXE");
        LEGACY.put("STICK", 0, "STICK");
        LEGACY.put("BOWL", 0, "BOWL");
        LEGACY.put("MUSHROOM_SOUP", 0, "MUSHROOM_STEW");
        LEGACY.put("GOLD_SWORD", 0, "GOLDEN_SWORD");
        LEGACY.put("GOLD_SPADE", 0, "GOLDEN_SHOVEL");
        LEGACY.put("GOLD_PICKAXE", 0, "GOLDEN_PICKAXE");
        LEGACY.put("GOLD_AXE", 0, "GOLDEN_AXE");
        LEGACY.put("STRING", 0, "STRING");
        LEGACY.put("FEATHER", 0, "FEATHER");
        LEGACY.put("SULPHUR", 0, "GUNPOWDER");
        LEGACY.put("WOOD_HOE", 0, "WOODEN_HOE");
        LEGACY.put("STONE_HOE", 0, "STONE_HOE");
        LEGACY.put("IRON_HOE", 0, "IRON_HOE");
        LEGACY.put("DIAMOND_HOE", 0, "DIAMOND_HOE");
        LEGACY.put("GOLD_HOE", 0, "GOLDEN_HOE");
        LEGACY.put("SEEDS", 0, "WHEAT_SEEDS");
        LEGACY.put("WHEAT", 0, "WHEAT");
        LEGACY.put("BREAD", 0, "BREAD");
        LEGACY.put("LEATHER_HELMET", 0, "LEATHER_HELMET");
        LEGACY.put("LEATHER_CHESTPLATE", 0, "LEATHER_CHESTPLATE");
        LEGACY.put("LEATHER_LEGGINGS", 0, "LEATHER_LEGGINGS");
        LEGACY.put("LEATHER_BOOTS", 0, "LEATHER_BOOTS");
        LEGACY.put("CHAINMAIL_HELMET", 0, "CHAINMAIL_HELMET");
        LEGACY.put("CHAINMAIL_CHESTPLATE", 0, "CHAINMAIL_CHESTPLATE");
        LEGACY.put("CHAINMAIL_LEGGINGS", 0, "CHAINMAIL_LEGGINGS");
        LEGACY.put("CHAINMAIL_BOOTS", 0, "CHAINMAIL_BOOTS");
        LEGACY.put("IRON_HELMET", 0, "IRON_HELMET");
        LEGACY.put("IRON_CHESTPLATE", 0, "IRON_CHESTPLATE");
        LEGACY.put("IRON_LEGGINGS", 0, "IRON_LEGGINGS");
        LEGACY.put("IRON_BOOTS", 0, "IRON_BOOTS");
        LEGACY.put("DIAMOND_HELMET", 0, "DIAMOND_HELMET");
        LEGACY.put("DIAMOND_CHESTPLATE", 0, "DIAMOND_CHESTPLATE");
        LEGACY.put("DIAMOND_LEGGINGS", 0, "DIAMOND_LEGGINGS");
        LEGACY.put("DIAMOND_BOOTS", 0, "DIAMOND_BOOTS");
        LEGACY.put("GOLD_HELMET", 0, "GOLDEN_HELMET");
        LEGACY.put("GOLD_CHESTPLATE", 0, "GOLDEN_CHESTPLATE");
        LEGACY.put("GOLD_LEGGINGS", 0, "GOLDEN_LEGGINGS");
        LEGACY.put("GOLD_BOOTS", 0, "GOLDEN_BOOTS");
        LEGACY.put("FLINT", 0, "FLINT");
        LEGACY.put("PORK", 0, "PORKCHOP");
        LEGACY.put("GRILLED_PORK", 0, "COOKED_PORKCHOP");
        LEGACY.put("PAINTING", 0, "PAINTING");
        LEGACY.put("GOLDEN_APPLE", 0, "GOLDEN_APPLE");
        LEGACY.put("GOLDEN_APPLE", 1, "ENCHANTED_GOLDEN_APPLE");
        LEGACY.put("SIGN", 0, "OAK_SIGN");
        LEGACY.put("WOOD_DOOR", 0, "OAK_DOOR");
        LEGACY.put("BUCKET", 0, "BUCKET");
        LEGACY.put("WATER_BUCKET", 0, "WATER_BUCKET");
        LEGACY.put("LAVA_BUCKET", 0, "LAVA_BUCKET");
        LEGACY.put("MINECART", 0, "MINECART");
        LEGACY.put("SADDLE", 0, "SADDLE");
        LEGACY.put("IRON_DOOR", 0, "IRON_DOOR");
        LEGACY.put("REDSTONE", 0, "REDSTONE");
        LEGACY.put("SNOW_BALL", 0, "SNOWBALL");
        LEGACY.put("BOAT", 0, "OAK_BOAT");
        LEGACY.put("LEATHER", 0, "LEATHER");
        LEGACY.put("MILK_BUCKET", 0, "MILK_BUCKET");
        LEGACY.put("CLAY_BRICK", 0, "BRICK");
        LEGACY.put("CLAY_BALL", 0, "CLAY_BALL");
        LEGACY.put("SUGAR_CANE", 0, "SUGAR_CANE");
        LEGACY.put("PAPER", 0, "PAPER");
        LEGACY.put("BOOK", 0, "BOOK");
        LEGACY.put("SLIME_BALL", 0, "SLIME_BALL");
        LEGACY.put("STORAGE_MINECART", 0, "CHEST_MINECART");
        LEGACY.put("POWERED_MINECART", 0, "FURNACE_MINECART");
        LEGACY.put("EGG", 0, "EGG");
        LEGACY.put("COMPASS", 0, "COMPASS");
        LEGACY.put("FISHING_ROD", 0, "FISHING_ROD");
        LEGACY.put("WATCH", 0, "CLOCK");
        LEGACY.put("GLOWSTONE_DUST", 0, "GLOWSTONE_DUST");
        LEGACY.put("RAW_FISH", 0, "COD");
        LEGACY.put("RAW_FISH", 1, "SALMON");
        LEGACY.put("RAW_FISH", 2, "TROPICAL_FISH");
        LEGACY.put("RAW_FISH", 3, "PUFFERFISH");
        LEGACY.put("COOKED_FISH", 0, "COOKED_COD");
        LEGACY.put("COOKED_FISH", 1, "COOKED_SALMON");
        LEGACY.put("INK_SACK", 0, "INK_SAC");
        LEGACY.put("INK_SACK", 1, "RED_DYE");
        LEGACY.put("INK_SACK", 2, "GREEN_DYE");
        LEGACY.put("INK_SACK", 3, "COCOA_BEANS");
        LEGACY.put("INK_SACK", 4, "LAPIS_LAZULI");
        LEGACY.put("INK_SACK", 5, "PURPLE_DYE");
        LEGACY.put("INK_SACK", 6, "CYAN_DYE");
        LEGACY.put("INK_SACK", 7, "LIGHT_GRAY_DYE");
        LEGACY.put("INK_SACK", 8, "GRAY_DYE");
        LEGACY.put("INK_SACK", 9, "PINK_DYE");
        LEGACY.put("INK_SACK", 10, "LIME_DYE");
        LEGACY.put("INK_SACK", 11, "YELLOW_DYE");
        LEGACY.put("INK_SACK", 12, "LIGHT_BLUE_DYE");
        LEGACY.put("INK_SACK", 13, "MAGENTA_DYE");
        LEGACY.put("INK_SACK", 14, "ORANGE_DYE");
        LEGACY.put("INK_SACK", 15, "BONE_MEAL");
        LEGACY.put("BONE", 0, "BONE");
        LEGACY.put("SUGAR", 0, "SUGAR");
        LEGACY.put("CAKE", 0, "CAKE");
        LEGACY.put("BED", 0, "RED_BED");
        LEGACY.put("BED", 1, "ORANGE_BED");
        LEGACY.put("BED", 2, "MAGENTA_BED");
        LEGACY.put("BED", 3, "LIGHT_BLUE_BED");
        LEGACY.put("BED", 4, "YELLOW_BED");
        LEGACY.put("BED", 5, "LIME_BED");
        LEGACY.put("BED", 6, "PINK_BED");
        LEGACY.put("BED", 7, "GRAY_BED");
        LEGACY.put("BED", 8, "LIGHT_GRAY_BED");
        LEGACY.put("BED", 9, "CYAN_BED");
        LEGACY.put("BED", 10, "PURPLE_BED");
        LEGACY.put("BED", 11, "BLUE_BED");
        LEGACY.put("BED", 12, "BROWN_BED");
        LEGACY.put("BED", 13, "GREEN_BED");
        LEGACY.put("BED", 15, "BLACK_BED");
        LEGACY.put("DIODE", 0, "REPEATER");
        LEGACY.put("COOKIE", 0, "COOKIE");
        LEGACY.put("MAP", 0, "FILLED_MAP");
        LEGACY.put("SHEARS", 0, "SHEARS");
        LEGACY.put("MELON", 0, "MELON_SLICE");
        LEGACY.put("PUMPKIN_SEEDS", 0, "PUMPKIN_SEEDS");
        LEGACY.put("MELON_SEEDS", 0, "MELON_SEEDS");
        LEGACY.put("RAW_BEEF", 0, "BEEF");
        LEGACY.put("COOKED_BEEF", 0, "COOKED_BEEF");
        LEGACY.put("RAW_CHICKEN", 0, "CHICKEN");
        LEGACY.put("COOKED_CHICKEN", 0, "COOKED_CHICKEN");
        LEGACY.put("ROTTEN_FLESH", 0, "ROTTEN_FLESH");
        LEGACY.put("ENDER_PEARL", 0, "ENDER_PEARL");
        LEGACY.put("BLAZE_ROD", 0, "BLAZE_ROD");
        LEGACY.put("GHAST_TEAR", 0, "GHAST_TEAR");
        LEGACY.put("GOLD_NUGGET", 0, "GOLD_NUGGET");
        LEGACY.put("NETHER_STALK", 0, "NETHER_WART");
        LEGACY.put("POTION", 0, "POTION");
        LEGACY.put("GLASS_BOTTLE", 0, "GLASS_BOTTLE");
        LEGACY.put("SPIDER_EYE", 0, "SPIDER_EYE");
        LEGACY.put("FERMENTED_SPIDER_EYE", 0, "FERMENTED_SPIDER_EYE");
        LEGACY.put("BLAZE_POWDER", 0, "BLAZE_POWDER");
        LEGACY.put("MAGMA_CREAM", 0, "MAGMA_CREAM");
        LEGACY.put("BREWING_STAND_ITEM", 0, "BREWING_STAND");
        LEGACY.put("CAULDRON_ITEM", 0, "CAULDRON");
        LEGACY.put("EYE_OF_ENDER", 0, "ENDER_EYE");
        LEGACY.put("SPECKLED_MELON", 0, "GLISTERING_MELON_SLICE");
        LEGACY.put("MONSTER_EGG", 0, "PIG_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 4, "ELDER_GUARDIAN_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 5, "WITHER_SKELETON_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 6, "STRAY_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 23, "HUSK_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 27, "ZOMBIE_VILLAGER_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 28, "SKELETON_HORSE_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 29, "ZOMBIE_HORSE_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 31, "DONKEY_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 32, "MULE_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 34, "EVOKER_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 35, "VEX_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 36, "VINDICATOR_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 50, "CREEPER_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 51, "SKELETON_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 52, "SPIDER_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 54, "ZOMBIE_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 55, "SLIME_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 56, "GHAST_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 57, "ZOMBIFIED_PIGLIN_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 58, "ENDERMAN_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 59, "CAVE_SPIDER_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 60, "SILVERFISH_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 61, "BLAZE_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 62, "MAGMA_CUBE_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 65, "BAT_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 66, "WITCH_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 67, "ENDERMITE_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 68, "GUARDIAN_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 69, "SHULKER_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 91, "SHEEP_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 92, "COW_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 93, "CHICKEN_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 94, "SQUID_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 95, "WOLF_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 96, "MOOSHROOM_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 98, "OCELOT_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 100, "HORSE_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 101, "RABBIT_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 102, "POLAR_BEAR_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 103, "LLAMA_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 105, "PARROT_SPAWN_EGG");
        LEGACY.put("MONSTER_EGG", 120, "VILLAGER_SPAWN_EGG");
        LEGACY.put("EXP_BOTTLE", 0, "EXPERIENCE_BOTTLE");
        LEGACY.put("FIREBALL", 0, "FIRE_CHARGE");
        LEGACY.put("BOOK_AND_QUILL", 0, "WRITABLE_BOOK");
        LEGACY.put("WRITTEN_BOOK", 0, "WRITTEN_BOOK");
        LEGACY.put("EMERALD", 0, "EMERALD");
        LEGACY.put("ITEM_FRAME", 0, "ITEM_FRAME");
        LEGACY.put("FLOWER_POT_ITEM", 0, "FLOWER_POT");
        LEGACY.put("CARROT_ITEM", 0, "CARROT");
        LEGACY.put("POTATO_ITEM", 0, "POTATO");
        LEGACY.put("BAKED_POTATO", 0, "BAKED_POTATO");
        LEGACY.put("POISONOUS_POTATO", 0, "POISONOUS_POTATO");
        LEGACY.put("EMPTY_MAP", 0, "MAP");
        LEGACY.put("GOLDEN_CARROT", 0, "GOLDEN_CARROT");
        LEGACY.put("SKULL_ITEM", 0, "SKELETON_SKULL");
        LEGACY.put("SKULL_ITEM", 1, "WITHER_SKELETON_SKULL");
        LEGACY.put("SKULL_ITEM", 2, "ZOMBIE_HEAD");
        LEGACY.put("SKULL_ITEM", 3, "PLAYER_HEAD");
        LEGACY.put("SKULL_ITEM", 4, "CREEPER_HEAD");
        LEGACY.put("SKULL_ITEM", 5, "DRAGON_HEAD");
        LEGACY.put("CARROT_STICK", 0, "CARROT_ON_A_STICK");
        LEGACY.put("NETHER_STAR", 0, "NETHER_STAR");
        LEGACY.put("PUMPKIN_PIE", 0, "PUMPKIN_PIE");
        LEGACY.put("FIREWORK", 0, "FIREWORK_ROCKET");
        LEGACY.put("FIREWORK_CHARGE", 0, "FIREWORK_STAR");
        LEGACY.put("ENCHANTED_BOOK", 0, "ENCHANTED_BOOK");
        LEGACY.put("REDSTONE_COMPARATOR", 0, "COMPARATOR");
        LEGACY.put("NETHER_BRICK_ITEM", 0, "NETHER_BRICK");
        LEGACY.put("QUARTZ", 0, "QUARTZ");
        LEGACY.put("EXPLOSIVE_MINECART", 0, "TNT_MINECART");
        LEGACY.put("HOPPER_MINECART", 0, "HOPPER_MINECART");
        LEGACY.put("PRISMARINE_SHARD", 0, "PRISMARINE_SHARD");
        LEGACY.put("PRISMARINE_CRYSTALS", 0, "PRISMARINE_CRYSTALS");
        LEGACY.put("RABBIT", 0, "RABBIT");
        LEGACY.put("COOKED_RABBIT", 0, "COOKED_RABBIT");
        LEGACY.put("RABBIT_STEW", 0, "RABBIT_STEW");
        LEGACY.put("RABBIT_FOOT", 0, "RABBIT_FOOT");
        LEGACY.put("RABBIT_HIDE", 0, "RABBIT_HIDE");
        LEGACY.put("ARMOR_STAND", 0, "ARMOR_STAND");
        LEGACY.put("IRON_BARDING", 0, "IRON_HORSE_ARMOR");
        LEGACY.put("GOLD_BARDING", 0, "GOLDEN_HORSE_ARMOR");
        LEGACY.put("DIAMOND_BARDING", 0, "DIAMOND_HORSE_ARMOR");
        LEGACY.put("LEASH", 0, "LEAD");
        LEGACY.put("NAME_TAG", 0, "NAME_TAG");
        LEGACY.put("COMMAND_MINECART", 0, "COMMAND_BLOCK_MINECART");
        LEGACY.put("MUTTON", 0, "MUTTON");
        LEGACY.put("COOKED_MUTTON", 0, "COOKED_MUTTON");
        LEGACY.put("BANNER", 0, "BLACK_BANNER");
        LEGACY.put("BANNER", 1, "RED_BANNER");
        LEGACY.put("BANNER", 2, "GREEN_BANNER");
        LEGACY.put("BANNER", 3, "BROWN_BANNER");
        LEGACY.put("BANNER", 4, "BLUE_BANNER");
        LEGACY.put("BANNER", 5, "PURPLE_BANNER");
        LEGACY.put("BANNER", 6, "CYAN_BANNER");
        LEGACY.put("BANNER", 7, "LIGHT_GRAY_BANNER");
        LEGACY.put("BANNER", 8, "GRAY_BANNER");
        LEGACY.put("BANNER", 9, "PINK_BANNER");
        LEGACY.put("BANNER", 10, "LIME_BANNER");
        LEGACY.put("BANNER", 11, "YELLOW_BANNER");
        LEGACY.put("BANNER", 12, "LIGHT_BLUE_BANNER");
        LEGACY.put("BANNER", 13, "MAGENTA_BANNER");
        LEGACY.put("BANNER", 14, "ORANGE_BANNER");
        LEGACY.put("BANNER", 15, "WHITE_BANNER");
        LEGACY.put("END_CRYSTAL", 0, "END_CRYSTAL");
        LEGACY.put("SPRUCE_DOOR_ITEM", 0, "SPRUCE_DOOR");
        LEGACY.put("BIRCH_DOOR_ITEM", 0, "BIRCH_DOOR");
        LEGACY.put("JUNGLE_DOOR_ITEM", 0, "JUNGLE_DOOR");
        LEGACY.put("ACACIA_DOOR_ITEM", 0, "ACACIA_DOOR");
        LEGACY.put("DARK_OAK_DOOR_ITEM", 0, "DARK_OAK_DOOR");
        LEGACY.put("CHORUS_FRUIT", 0, "CHORUS_FRUIT");
        LEGACY.put("CHORUS_FRUIT_POPPED", 0, "POPPED_CHORUS_FRUIT");
        LEGACY.put("BEETROOT", 0, "BEETROOT");
        LEGACY.put("BEETROOT_SEEDS", 0, "BEETROOT_SEEDS");
        LEGACY.put("BEETROOT_SOUP", 0, "BEETROOT_SOUP");
        LEGACY.put("DRAGONS_BREATH", 0, "DRAGON_BREATH");
        LEGACY.put("SPLASH_POTION", 0, "SPLASH_POTION");
        LEGACY.put("SPECTRAL_ARROW", 0, "SPECTRAL_ARROW");
        LEGACY.put("TIPPED_ARROW", 0, "TIPPED_ARROW");
        LEGACY.put("LINGERING_POTION", 0, "LINGERING_POTION");
        LEGACY.put("SHIELD", 0, "SHIELD");
        LEGACY.put("ELYTRA", 0, "ELYTRA");
        LEGACY.put("BOAT_SPRUCE", 0, "SPRUCE_BOAT");
        LEGACY.put("BOAT_BIRCH", 0, "BIRCH_BOAT");
        LEGACY.put("BOAT_JUNGLE", 0, "JUNGLE_BOAT");
        LEGACY.put("BOAT_ACACIA", 0, "ACACIA_BOAT");
        LEGACY.put("BOAT_DARK_OAK", 0, "DARK_OAK_BOAT");
        LEGACY.put("TOTEM", 0, "TOTEM_OF_UNDYING");
        LEGACY.put("SHULKER_SHELL", 0, "SHULKER_SHELL");
        LEGACY.put("IRON_NUGGET", 0, "IRON_NUGGET");
        LEGACY.put("KNOWLEDGE_BOOK", 0, "KNOWLEDGE_BOOK");
        LEGACY.put("GOLD_RECORD", 0, "MUSIC_DISC_13");
        LEGACY.put("GREEN_RECORD", 0, "MUSIC_DISC_CAT");
        LEGACY.put("RECORD_3", 0, "MUSIC_DISC_BLOCKS");
        LEGACY.put("RECORD_4", 0, "MUSIC_DISC_CHIRP");
        LEGACY.put("RECORD_5", 0, "MUSIC_DISC_FAR");
        LEGACY.put("RECORD_6", 0, "MUSIC_DISC_MALL");
        LEGACY.put("RECORD_7", 0, "MUSIC_DISC_MELLOHI");
        LEGACY.put("RECORD_8", 0, "MUSIC_DISC_STAL");
        LEGACY.put("RECORD_9", 0, "MUSIC_DISC_STRAD");
        LEGACY.put("RECORD_10", 0, "MUSIC_DISC_WARD");
        LEGACY.put("RECORD_11", 0, "MUSIC_DISC_11");
        LEGACY.put("RECORD_12", 0, "MUSIC_DISC_WAIT");

        if(NMSVersion.current().compare(NMSVersion.v1_13_R1) >= 0){
            // SKULL
            SKULL_TYPES.add(Material.valueOf("CREEPER_HEAD"));
            SKULL_TYPES.add(Material.valueOf("DRAGON_HEAD"));
            SKULL_TYPES.add(Material.valueOf("PLAYER_HEAD"));
            SKULL_TYPES.add(Material.valueOf("ZOMBIE_HEAD"));
            SKULL_TYPES.add(Material.valueOf("SKELETON_SKULL"));
            SKULL_TYPES.add(Material.valueOf("SKELETON_WALL_SKULL"));
            SKULL_TYPES.add(Material.valueOf("WITHER_SKELETON_SKULL"));
            SKULL_TYPES.add(Material.valueOf("WITHER_SKELETON_WALL_SKULL"));
            SKULL_TYPES.add(Material.valueOf("LEGACY_SKULL"));

            // ORE
            ORE_TYPES.add(Material.valueOf("NETHER_QUARTZ_ORE"));
            ORE_TYPES.add(Material.valueOf("LEGACY_GOLD_ORE"));
            ORE_TYPES.add(Material.valueOf("LEGACY_IRON_ORE"));
            ORE_TYPES.add(Material.valueOf("LEGACY_COAL_ORE"));
            ORE_TYPES.add(Material.valueOf("LEGACY_LAPIS_ORE"));
            ORE_TYPES.add(Material.valueOf("LEGACY_DIAMOND_ORE"));
            ORE_TYPES.add(Material.valueOf("LEGACY_REDSTONE_ORE"));
            ORE_TYPES.add(Material.valueOf("LEGACY_GLOWING_REDSTONE_ORE"));
            ORE_TYPES.add(Material.valueOf("LEGACY_EMERALD_ORE"));
            ORE_TYPES.add(Material.valueOf("LEGACY_QUARTZ_ORE"));

            // BOAT
            BOAT_TYPES.add(Material.valueOf("ACACIA_BOAT"));
            BOAT_TYPES.add(Material.valueOf("BIRCH_BOAT"));
            BOAT_TYPES.add(Material.valueOf("DARK_OAK_BOAT"));
            BOAT_TYPES.add(Material.valueOf("JUNGLE_BOAT"));
            BOAT_TYPES.add(Material.valueOf("OAK_BOAT"));
            BOAT_TYPES.add(Material.valueOf("SPRUCE_BOAT"));
            BOAT_TYPES.add(Material.valueOf("LEGACY_BOAT"));

            // BUTTON
            BUTTON_TYPES.add(Material.valueOf("ACACIA_BUTTON"));
            BUTTON_TYPES.add(Material.valueOf("BIRCH_BUTTON"));
            BUTTON_TYPES.add(Material.valueOf("DARK_OAK_BUTTON"));
            BUTTON_TYPES.add(Material.valueOf("JUNGLE_BUTTON"));
            BUTTON_TYPES.add(Material.valueOf("OAK_BUTTON"));
            BUTTON_TYPES.add(Material.valueOf("SPRUCE_BUTTON"));
            BUTTON_TYPES.add(Material.valueOf("STONE_BUTTON"));
            BUTTON_TYPES.add(Material.valueOf("LEGACY_STONE_BUTTON"));
            BUTTON_TYPES.add(Material.valueOf("LEGACY_WOOD_BUTTON"));

            // DOOR
            DOOR_TYPES.add(Material.valueOf("OAK_DOOR"));
            DOOR_TYPES.add(Material.valueOf("LEGACY_WOODEN_DOOR"));
            DOOR_TYPES.add(Material.valueOf("LEGACY_SPRUCE_DOOR"));
            DOOR_TYPES.add(Material.valueOf("LEGACY_BIRCH_DOOR"));
            DOOR_TYPES.add(Material.valueOf("LEGACY_JUNGLE_DOOR"));
            DOOR_TYPES.add(Material.valueOf("LEGACY_ACACIA_DOOR"));
            DOOR_TYPES.add(Material.valueOf("LEGACY_DARK_OAK_DOOR"));
            DOOR_TYPES.add(Material.valueOf("LEGACY_WOOD_DOOR"));
            DOOR_TYPES.add(Material.valueOf("LEGACY_IRON_DOOR"));

            // FENCE
            FENCE_TYPES.add(Material.valueOf("NETHER_BRICK_FENCE"));
            FENCE_TYPES.add(Material.valueOf("OAK_FENCE"));
            FENCE_TYPES.add(Material.valueOf("IRON_BARS"));
            FENCE_TYPES.add(Material.valueOf("LEGACY_FENCE"));
            FENCE_TYPES.add(Material.valueOf("LEGACY_IRON_FENCE"));
            FENCE_TYPES.add(Material.valueOf("LEGACY_NETHER_FENCE"));
            FENCE_TYPES.add(Material.valueOf("LEGACY_SPRUCE_FENCE"));
            FENCE_TYPES.add(Material.valueOf("LEGACY_BIRCH_FENCE"));
            FENCE_TYPES.add(Material.valueOf("LEGACY_JUNGLE_FENCE"));
            FENCE_TYPES.add(Material.valueOf("LEGACY_DARK_OAK_FENCE"));
            FENCE_TYPES.add(Material.valueOf("LEGACY_ACACIA_FENCE"));

            // FENCE_GATE
            FENCE_GATE_TYPES.add(Material.valueOf("OAK_FENCE_GATE"));
            FENCE_GATE_TYPES.add(Material.valueOf("LEGACY_FENCE_GATE"));
            FENCE_GATE_TYPES.add(Material.valueOf("LEGACY_SPRUCE_FENCE_GATE"));
            FENCE_GATE_TYPES.add(Material.valueOf("LEGACY_BIRCH_FENCE_GATE"));
            FENCE_GATE_TYPES.add(Material.valueOf("LEGACY_JUNGLE_FENCE_GATE"));
            FENCE_GATE_TYPES.add(Material.valueOf("LEGACY_DARK_OAK_FENCE_GATE"));
            FENCE_GATE_TYPES.add(Material.valueOf("LEGACY_ACACIA_FENCE_GATE"));

            // LEAVES
            LEAVES_TYPES.add(Material.valueOf("ACACIA_LEAVES"));
            LEAVES_TYPES.add(Material.valueOf("BIRCH_LEAVES"));
            LEAVES_TYPES.add(Material.valueOf("DARK_OAK_LEAVES"));
            LEAVES_TYPES.add(Material.valueOf("JUNGLE_LEAVES"));
            LEAVES_TYPES.add(Material.valueOf("OAK_LEAVES"));
            LEAVES_TYPES.add(Material.valueOf("SPRUCE_LEAVES"));
            LEAVES_TYPES.add(Material.valueOf("LEGACY_LEAVES"));

            // LOG
            LOG_TYPES.add(Material.valueOf("ACACIA_LOG"));
            LOG_TYPES.add(Material.valueOf("BIRCH_LOG"));
            LOG_TYPES.add(Material.valueOf("DARK_OAK_LOG"));
            LOG_TYPES.add(Material.valueOf("JUNGLE_LOG"));
            LOG_TYPES.add(Material.valueOf("OAK_LOG"));
            LOG_TYPES.add(Material.valueOf("SPRUCE_LOG"));
            LOG_TYPES.add(Material.valueOf("LEGACY_LOG"));

            // STRIPPED_LOG
            STRIPPED_LOG_TYPES.add(Material.valueOf("STRIPPED_ACACIA_LOG"));
            STRIPPED_LOG_TYPES.add(Material.valueOf("STRIPPED_BIRCH_LOG"));
            STRIPPED_LOG_TYPES.add(Material.valueOf("STRIPPED_DARK_OAK_LOG"));
            STRIPPED_LOG_TYPES.add(Material.valueOf("STRIPPED_JUNGLE_LOG"));
            STRIPPED_LOG_TYPES.add(Material.valueOf("STRIPPED_OAK_LOG"));
            STRIPPED_LOG_TYPES.add(Material.valueOf("STRIPPED_SPRUCE_LOG"));

            // PLANKS
            PLANKS_TYPES.add(Material.valueOf("ACACIA_PLANKS"));
            PLANKS_TYPES.add(Material.valueOf("BIRCH_PLANKS"));
            PLANKS_TYPES.add(Material.valueOf("DARK_OAK_PLANKS"));
            PLANKS_TYPES.add(Material.valueOf("JUNGLE_PLANKS"));
            PLANKS_TYPES.add(Material.valueOf("OAK_PLANKS"));
            PLANKS_TYPES.add(Material.valueOf("SPRUCE_PLANKS"));

            // PRESSURE_PLATE
            PRESSURE_PLATE_TYPES.add(Material.valueOf("ACACIA_PRESSURE_PLATE"));
            PRESSURE_PLATE_TYPES.add(Material.valueOf("BIRCH_PRESSURE_PLATE"));
            PRESSURE_PLATE_TYPES.add(Material.valueOf("DARK_OAK_PRESSURE_PLATE"));
            PRESSURE_PLATE_TYPES.add(Material.valueOf("HEAVY_WEIGHTED_PRESSURE_PLATE"));
            PRESSURE_PLATE_TYPES.add(Material.valueOf("JUNGLE_PRESSURE_PLATE"));
            PRESSURE_PLATE_TYPES.add(Material.valueOf("LIGHT_WEIGHTED_PRESSURE_PLATE"));
            PRESSURE_PLATE_TYPES.add(Material.valueOf("OAK_PRESSURE_PLATE"));
            PRESSURE_PLATE_TYPES.add(Material.valueOf("SPRUCE_PRESSURE_PLATE"));
            PRESSURE_PLATE_TYPES.add(Material.valueOf("STONE_PRESSURE_PLATE"));

            // SAPLING
            SAPLING_TYPES.add(Material.valueOf("ACACIA_SAPLING"));
            SAPLING_TYPES.add(Material.valueOf("BIRCH_SAPLING"));
            SAPLING_TYPES.add(Material.valueOf("DARK_OAK_SAPLING"));
            SAPLING_TYPES.add(Material.valueOf("JUNGLE_SAPLING"));
            SAPLING_TYPES.add(Material.valueOf("OAK_SAPLING"));
            SAPLING_TYPES.add(Material.valueOf("SPRUCE_SAPLING"));
            SAPLING_TYPES.add(Material.valueOf("LEGACY_SAPLING"));

            // SLAB
            SLAB_TYPES.add(Material.valueOf("ACACIA_SLAB"));
            SLAB_TYPES.add(Material.valueOf("BIRCH_SLAB"));
            SLAB_TYPES.add(Material.valueOf("BRICK_SLAB"));
            SLAB_TYPES.add(Material.valueOf("COBBLESTONE_SLAB"));
            SLAB_TYPES.add(Material.valueOf("DARK_OAK_SLAB"));
            SLAB_TYPES.add(Material.valueOf("DARK_PRISMARINE_SLAB"));
            SLAB_TYPES.add(Material.valueOf("JUNGLE_SLAB"));
            SLAB_TYPES.add(Material.valueOf("NETHER_BRICK_SLAB"));
            SLAB_TYPES.add(Material.valueOf("OAK_SLAB"));
            SLAB_TYPES.add(Material.valueOf("PETRIFIED_OAK_SLAB"));
            SLAB_TYPES.add(Material.valueOf("PRISMARINE_BRICK_SLAB"));
            SLAB_TYPES.add(Material.valueOf("PRISMARINE_SLAB"));
            SLAB_TYPES.add(Material.valueOf("PURPUR_SLAB"));
            SLAB_TYPES.add(Material.valueOf("QUARTZ_SLAB"));
            SLAB_TYPES.add(Material.valueOf("RED_SANDSTONE_SLAB"));
            SLAB_TYPES.add(Material.valueOf("SANDSTONE_SLAB"));
            SLAB_TYPES.add(Material.valueOf("SPRUCE_SLAB"));
            SLAB_TYPES.add(Material.valueOf("STONE_BRICK_SLAB"));
            SLAB_TYPES.add(Material.valueOf("STONE_SLAB"));
            SLAB_TYPES.add(Material.valueOf("LEGACY_PURPUR_DOUBLE_SLAB"));
            SLAB_TYPES.add(Material.valueOf("LEGACY_PURPUR_SLAB"));
			SLAB_TYPES.add(Material.valueOf("LEGACY_DOUBLE_STONE_SLAB2"));
            SLAB_TYPES.add(Material.valueOf("LEGACY_STONE_SLAB2"));
            SLAB_TYPES.add(Material.valueOf("LEGACY_STEP"));

            // STAIRS
            STAIRS_TYPES.add(Material.valueOf("DARK_PRISMARINE_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("JUNGLE_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("NETHER_BRICK_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("OAK_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("BIRCH_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("PRISMARINE_BRICK_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("PRISMARINE_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("SPRUCE_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("STONE_BRICK_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_WOOD_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_COBBLESTONE_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_BRICK_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_SMOOTH_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_NETHER_BRICK_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_SANDSTONE_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_SPRUCE_WOOD_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_BIRCH_WOOD_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_JUNGLE_WOOD_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_QUARTZ_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_ACACIA_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_DARK_OAK_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_RED_SANDSTONE_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("LEGACY_PURPUR_STAIRS"));

            // TRAPDOOR
            TRAPDOOR_TYPES.add(Material.valueOf("ACACIA_TRAPDOOR"));
            TRAPDOOR_TYPES.add(Material.valueOf("BIRCH_TRAPDOOR"));
            TRAPDOOR_TYPES.add(Material.valueOf("DARK_OAK_TRAPDOOR"));
            TRAPDOOR_TYPES.add(Material.valueOf("IRON_TRAPDOOR"));
            TRAPDOOR_TYPES.add(Material.valueOf("JUNGLE_TRAPDOOR"));
            TRAPDOOR_TYPES.add(Material.valueOf("OAK_TRAPDOOR"));
            TRAPDOOR_TYPES.add(Material.valueOf("SPRUCE_TRAPDOOR"));
            TRAPDOOR_TYPES.add(Material.valueOf("LEGACY_TRAP_DOOR"));
            TRAPDOOR_TYPES.add(Material.valueOf("LEGACY_IRON_TRAPDOOR"));

            // WOOD
            WOOD_TYPES.add(Material.valueOf("ACACIA_WOOD"));
            WOOD_TYPES.add(Material.valueOf("BIRCH_WOOD"));
            WOOD_TYPES.add(Material.valueOf("DARK_OAK_WOOD"));
            WOOD_TYPES.add(Material.valueOf("JUNGLE_WOOD"));
            WOOD_TYPES.add(Material.valueOf("OAK_WOOD"));
            WOOD_TYPES.add(Material.valueOf("SPRUCE_WOOD"));
            WOOD_TYPES.add(Material.valueOf("LEGACY_WOOD"));

            // STRIPPED_WOOD
            STRIPPED_WOOD_TYPES.add(Material.valueOf("STRIPPED_ACACIA_WOOD"));
            STRIPPED_WOOD_TYPES.add(Material.valueOf("STRIPPED_BIRCH_WOOD"));
            STRIPPED_WOOD_TYPES.add(Material.valueOf("STRIPPED_DARK_OAK_WOOD"));
            STRIPPED_WOOD_TYPES.add(Material.valueOf("STRIPPED_JUNGLE_WOOD"));
            STRIPPED_WOOD_TYPES.add(Material.valueOf("STRIPPED_OAK_WOOD"));
            STRIPPED_WOOD_TYPES.add(Material.valueOf("STRIPPED_SPRUCE_WOOD"));

            // BANNER
            BANNER_TYPES.add(Material.valueOf("BLACK_BANNER"));
            BANNER_TYPES.add(Material.valueOf("BLACK_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("BLUE_BANNER"));
            BANNER_TYPES.add(Material.valueOf("BLUE_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("BROWN_BANNER"));
            BANNER_TYPES.add(Material.valueOf("BROWN_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("CYAN_BANNER"));
            BANNER_TYPES.add(Material.valueOf("CYAN_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("GRAY_BANNER"));
            BANNER_TYPES.add(Material.valueOf("GRAY_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("GREEN_BANNER"));
            BANNER_TYPES.add(Material.valueOf("GREEN_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("LIGHT_BLUE_BANNER"));
            BANNER_TYPES.add(Material.valueOf("LIGHT_BLUE_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("LIGHT_GRAY_BANNER"));
            BANNER_TYPES.add(Material.valueOf("LIGHT_GRAY_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("LIME_BANNER"));
            BANNER_TYPES.add(Material.valueOf("LIME_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("MAGENTA_BANNER"));
            BANNER_TYPES.add(Material.valueOf("MAGENTA_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("ORANGE_BANNER"));
            BANNER_TYPES.add(Material.valueOf("ORANGE_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("PINK_BANNER"));
            BANNER_TYPES.add(Material.valueOf("PINK_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("PURPLE_BANNER"));
            BANNER_TYPES.add(Material.valueOf("PURPLE_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("RED_BANNER"));
            BANNER_TYPES.add(Material.valueOf("RED_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("WHITE_BANNER"));
            BANNER_TYPES.add(Material.valueOf("WHITE_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("YELLOW_BANNER"));
            BANNER_TYPES.add(Material.valueOf("YELLOW_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("LEGACY_STANDING_BANNER"));
            BANNER_TYPES.add(Material.valueOf("LEGACY_WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("LEGACY_BANNER"));

            // BED
            BED_TYPES.add(Material.valueOf("BLACK_BED"));
            BED_TYPES.add(Material.valueOf("BLUE_BED"));
            BED_TYPES.add(Material.valueOf("BROWN_BED"));
            BED_TYPES.add(Material.valueOf("CYAN_BED"));
            BED_TYPES.add(Material.valueOf("GRAY_BED"));
            BED_TYPES.add(Material.valueOf("GREEN_BED"));
            BED_TYPES.add(Material.valueOf("LIGHT_BLUE_BED"));
            BED_TYPES.add(Material.valueOf("LIGHT_GRAY_BED"));
            BED_TYPES.add(Material.valueOf("LIME_BED"));
            BED_TYPES.add(Material.valueOf("MAGENTA_BED"));
            BED_TYPES.add(Material.valueOf("ORANGE_BED"));
            BED_TYPES.add(Material.valueOf("PINK_BED"));
            BED_TYPES.add(Material.valueOf("PURPLE_BED"));
            BED_TYPES.add(Material.valueOf("RED_BED"));
            BED_TYPES.add(Material.valueOf("WHITE_BED"));
            BED_TYPES.add(Material.valueOf("YELLOW_BED"));
            BED_TYPES.add(Material.valueOf("LEGACY_BED"));

            // CARPET
            CARPET_TYPES.add(Material.valueOf("BLACK_CARPET"));
            CARPET_TYPES.add(Material.valueOf("BLUE_CARPET"));
            CARPET_TYPES.add(Material.valueOf("BROWN_CARPET"));
            CARPET_TYPES.add(Material.valueOf("CYAN_CARPET"));
            CARPET_TYPES.add(Material.valueOf("GRAY_CARPET"));
            CARPET_TYPES.add(Material.valueOf("GREEN_CARPET"));
            CARPET_TYPES.add(Material.valueOf("LIGHT_BLUE_CARPET"));
            CARPET_TYPES.add(Material.valueOf("LIGHT_GRAY_CARPET"));
            CARPET_TYPES.add(Material.valueOf("LIME_CARPET"));
            CARPET_TYPES.add(Material.valueOf("MAGENTA_CARPET"));
            CARPET_TYPES.add(Material.valueOf("ORANGE_CARPET"));
            CARPET_TYPES.add(Material.valueOf("PINK_CARPET"));
            CARPET_TYPES.add(Material.valueOf("PURPLE_CARPET"));
            CARPET_TYPES.add(Material.valueOf("RED_CARPET"));
            CARPET_TYPES.add(Material.valueOf("WHITE_CARPET"));
            CARPET_TYPES.add(Material.valueOf("YELLOW_CARPET"));
            CARPET_TYPES.add(Material.valueOf("LEGACY_CARPET"));

            // CONCRETE
            CONCRETE_TYPES.add(Material.valueOf("BLACK_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("BLUE_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("BROWN_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("CYAN_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("GRAY_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("GREEN_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("LIGHT_BLUE_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("LIGHT_GRAY_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("LIME_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("MAGENTA_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("ORANGE_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("PINK_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("PURPLE_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("RED_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("WHITE_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("YELLOW_CONCRETE"));
            CONCRETE_TYPES.add(Material.valueOf("LEGACY_CONCRETE"));

            // CONCRETE_POWDER
            CONCRETE_POWDER_TYPES.add(Material.valueOf("BLACK_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("BLUE_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("BROWN_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("CYAN_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("GRAY_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("GREEN_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("LIGHT_BLUE_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("LIGHT_GRAY_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("LIME_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("MAGENTA_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("ORANGE_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("PINK_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("PURPLE_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("RED_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("WHITE_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("YELLOW_CONCRETE_POWDER"));
            CONCRETE_POWDER_TYPES.add(Material.valueOf("LEGACY_CONCRETE_POWDER"));

            // GLAZED_TERRACOTTA
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LIGHT_GRAY_GLAZED_TERRACOTTA"));
			
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_WHITE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_ORANGE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_MAGENTA_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_LIGHT_BLUE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_YELLOW_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_LIME_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_PINK_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_GRAY_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_SILVER_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_CYAN_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_PURPLE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_BLUE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_BROWN_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_GREEN_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_RED_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_BLACK_GLAZED_TERRACOTTA"));

            // SHULKER_BOX
            SHULKER_BOX_TYPES.add(Material.valueOf("SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LIGHT_GRAY_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_WHITE_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_ORANGE_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_MAGENTA_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_LIGHT_BLUE_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_YELLOW_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_LIME_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_PINK_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_GRAY_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_SILVER_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_CYAN_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_PURPLE_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_BLUE_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_BROWN_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_GREEN_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_RED_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LEGACY_BLACK_SHULKER_BOX"));

            // STAINED_GLASS
            STAINED_GLASS_TYPES.add(Material.valueOf("BLACK_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("BLUE_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("BROWN_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("CYAN_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("GRAY_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("GREEN_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("LIGHT_BLUE_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("LIGHT_GRAY_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("LIME_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("MAGENTA_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("ORANGE_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("PINK_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("PURPLE_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("RED_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("WHITE_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("YELLOW_STAINED_GLASS"));
            STAINED_GLASS_TYPES.add(Material.valueOf("LEGACY_STAINED_GLASS"));

            // STAINED_GLASS_PANE
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("BLACK_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("BLUE_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("BROWN_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("CYAN_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("GRAY_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("GREEN_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("LIGHT_BLUE_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("LIGHT_GRAY_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("LIME_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("MAGENTA_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("ORANGE_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("PINK_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("PURPLE_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("RED_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("WHITE_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("YELLOW_STAINED_GLASS_PANE"));
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("LEGACY_STAINED_GLASS_PANE"));

            // TERRACOTTA
            TERRACOTTA_TYPES.add(Material.valueOf("LIGHT_GRAY_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("BLACK_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("BLUE_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("BROWN_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("CYAN_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("GRAY_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("GREEN_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("LIGHT_BLUE_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("LIME_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("MAGENTA_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("ORANGE_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("PINK_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("PURPLE_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("RED_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("WHITE_TERRACOTTA"));
            TERRACOTTA_TYPES.add(Material.valueOf("YELLOW_TERRACOTTA"));
			
			// GLAZED_TERRACOTTA
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LIGHT_GRAY_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_WHITE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_ORANGE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_MAGENTA_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_LIGHT_BLUE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_YELLOW_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_LIME_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_PINK_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_GRAY_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_SILVER_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_CYAN_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_PURPLE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_BLUE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_BROWN_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_GREEN_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_RED_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LEGACY_BLACK_GLAZED_TERRACOTTA"));

            // WALL_BANNER
            WALL_BANNER_TYPES.add(Material.valueOf("BLACK_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("BLUE_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("BROWN_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("CYAN_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("GRAY_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("GREEN_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("LIGHT_BLUE_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("LIGHT_GRAY_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("LIME_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("MAGENTA_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("ORANGE_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("PINK_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("PURPLE_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("RED_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("WHITE_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("YELLOW_WALL_BANNER"));
            WALL_BANNER_TYPES.add(Material.valueOf("LEGACY_WALL_BANNER"));

            // WOOL
            WOOL_TYPES.add(Material.valueOf("BLACK_WOOL"));
            WOOL_TYPES.add(Material.valueOf("BLUE_WOOL"));
            WOOL_TYPES.add(Material.valueOf("BROWN_WOOL"));
            WOOL_TYPES.add(Material.valueOf("CYAN_WOOL"));
            WOOL_TYPES.add(Material.valueOf("GRAY_WOOL"));
            WOOL_TYPES.add(Material.valueOf("GREEN_WOOL"));
            WOOL_TYPES.add(Material.valueOf("LIGHT_BLUE_WOOL"));
            WOOL_TYPES.add(Material.valueOf("LIGHT_GRAY_WOOL"));
            WOOL_TYPES.add(Material.valueOf("LIME_WOOL"));
            WOOL_TYPES.add(Material.valueOf("MAGENTA_WOOL"));
            WOOL_TYPES.add(Material.valueOf("ORANGE_WOOL"));
            WOOL_TYPES.add(Material.valueOf("PINK_WOOL"));
            WOOL_TYPES.add(Material.valueOf("PURPLE_WOOL"));
            WOOL_TYPES.add(Material.valueOf("RED_WOOL"));
            WOOL_TYPES.add(Material.valueOf("WHITE_WOOL"));
            WOOL_TYPES.add(Material.valueOf("YELLOW_WOOL"));
            WOOL_TYPES.add(Material.valueOf("LEGACY_WOOL"));

            // MUSIC_DISC
            MUSIC_DISC_TYPES.add(Material.valueOf("MUSIC_DISC_11"));
            MUSIC_DISC_TYPES.add(Material.valueOf("MUSIC_DISC_13"));
            MUSIC_DISC_TYPES.add(Material.valueOf("MUSIC_DISC_BLOCKS"));
            MUSIC_DISC_TYPES.add(Material.valueOf("MUSIC_DISC_CAT"));
            MUSIC_DISC_TYPES.add(Material.valueOf("MUSIC_DISC_CHIRP"));
            MUSIC_DISC_TYPES.add(Material.valueOf("MUSIC_DISC_FAR"));
            MUSIC_DISC_TYPES.add(Material.valueOf("MUSIC_DISC_MALL"));
            MUSIC_DISC_TYPES.add(Material.valueOf("MUSIC_DISC_MELLOHI"));
            MUSIC_DISC_TYPES.add(Material.valueOf("MUSIC_DISC_STAL"));
            MUSIC_DISC_TYPES.add(Material.valueOf("MUSIC_DISC_STRAD"));
            MUSIC_DISC_TYPES.add(Material.valueOf("MUSIC_DISC_WAIT"));
            MUSIC_DISC_TYPES.add(Material.valueOf("MUSIC_DISC_WARD"));

            // BUSH
            BUSH_TYPES.add(Material.valueOf("POTTED_DEAD_BUSH"));
            BUSH_TYPES.add(Material.valueOf("ROSE_BUSH"));
            BUSH_TYPES.add(Material.valueOf("LEGACY_DEAD_BUSH"));

            // DYE
            DYE_TYPES.add(Material.valueOf("CYAN_DYE"));
            DYE_TYPES.add(Material.valueOf("GRAY_DYE"));
            DYE_TYPES.add(Material.valueOf("LIGHT_BLUE_DYE"));
            DYE_TYPES.add(Material.valueOf("LIGHT_GRAY_DYE"));
            DYE_TYPES.add(Material.valueOf("LIME_DYE"));
            DYE_TYPES.add(Material.valueOf("MAGENTA_DYE"));
            DYE_TYPES.add(Material.valueOf("ORANGE_DYE"));
            DYE_TYPES.add(Material.valueOf("PINK_DYE"));
            DYE_TYPES.add(Material.valueOf("PURPLE_DYE"));

            // CORAL
            CORAL_TYPES.add(Material.valueOf("BRAIN_CORAL"));
            CORAL_TYPES.add(Material.valueOf("BUBBLE_CORAL"));
            CORAL_TYPES.add(Material.valueOf("FIRE_CORAL"));
            CORAL_TYPES.add(Material.valueOf("HORN_CORAL"));
            CORAL_TYPES.add(Material.valueOf("TUBE_CORAL"));

            // CORAL_BLOCK
            CORAL_BLOCK_TYPES.add(Material.valueOf("BRAIN_CORAL_BLOCK"));
            CORAL_BLOCK_TYPES.add(Material.valueOf("BUBBLE_CORAL_BLOCK"));
            CORAL_BLOCK_TYPES.add(Material.valueOf("FIRE_CORAL_BLOCK"));
            CORAL_BLOCK_TYPES.add(Material.valueOf("HORN_CORAL_BLOCK"));
            CORAL_BLOCK_TYPES.add(Material.valueOf("TUBE_CORAL_BLOCK"));

            // CORAL_FAN
            CORAL_FAN_TYPES.add(Material.valueOf("BRAIN_CORAL_FAN"));
            CORAL_FAN_TYPES.add(Material.valueOf("BUBBLE_CORAL_FAN"));
            CORAL_FAN_TYPES.add(Material.valueOf("FIRE_CORAL_FAN"));
            CORAL_FAN_TYPES.add(Material.valueOf("HORN_CORAL_FAN"));
            CORAL_FAN_TYPES.add(Material.valueOf("TUBE_CORAL_FAN"));

            // CORAL_WALL_FAN
            CORAL_WALL_FAN_TYPES.add(Material.valueOf("BRAIN_CORAL_WALL_FAN"));
            CORAL_WALL_FAN_TYPES.add(Material.valueOf("BUBBLE_CORAL_WALL_FAN"));
            CORAL_WALL_FAN_TYPES.add(Material.valueOf("FIRE_CORAL_WALL_FAN"));
            CORAL_WALL_FAN_TYPES.add(Material.valueOf("HORN_CORAL_WALL_FAN"));
            CORAL_WALL_FAN_TYPES.add(Material.valueOf("TUBE_CORAL_WALL_FAN"));

            // DEAD_CORAL
            DEAD_CORAL_TYPES.add(Material.valueOf("DEAD_BRAIN_CORAL"));
            DEAD_CORAL_TYPES.add(Material.valueOf("DEAD_BUBBLE_CORAL"));
            DEAD_CORAL_TYPES.add(Material.valueOf("DEAD_FIRE_CORAL"));
            DEAD_CORAL_TYPES.add(Material.valueOf("DEAD_HORN_CORAL"));
            DEAD_CORAL_TYPES.add(Material.valueOf("DEAD_TUBE_CORAL"));

            // DEAD_CORAL_BLOCK
            DEAD_CORAL_BLOCK_TYPES.add(Material.valueOf("DEAD_BRAIN_CORAL_BLOCK"));
            DEAD_CORAL_BLOCK_TYPES.add(Material.valueOf("DEAD_BUBBLE_CORAL_BLOCK"));
            DEAD_CORAL_BLOCK_TYPES.add(Material.valueOf("DEAD_FIRE_CORAL_BLOCK"));
            DEAD_CORAL_BLOCK_TYPES.add(Material.valueOf("DEAD_HORN_CORAL_BLOCK"));
            DEAD_CORAL_BLOCK_TYPES.add(Material.valueOf("DEAD_TUBE_CORAL_BLOCK"));

            // CORAL_FAN
            DEAD_CORAL_FAN_TYPES.add(Material.valueOf("DEAD_BRAIN_CORAL_FAN"));
            DEAD_CORAL_FAN_TYPES.add(Material.valueOf("DEAD_BUBBLE_CORAL_FAN"));
            DEAD_CORAL_FAN_TYPES.add(Material.valueOf("DEAD_FIRE_CORAL_FAN"));
            DEAD_CORAL_FAN_TYPES.add(Material.valueOf("DEAD_HORN_CORAL_FAN"));
            DEAD_CORAL_FAN_TYPES.add(Material.valueOf("DEAD_TUBE_CORAL_FAN"));

            // CORAL_WALL_FAN
            DEAD_CORAL_WALL_FAN_TYPES.add(Material.valueOf("DEAD_BRAIN_CORAL_WALL_FAN"));
            DEAD_CORAL_WALL_FAN_TYPES.add(Material.valueOf("DEAD_BUBBLE_CORAL_WALL_FAN"));
            DEAD_CORAL_WALL_FAN_TYPES.add(Material.valueOf("DEAD_FIRE_CORAL_WALL_FAN"));
            DEAD_CORAL_WALL_FAN_TYPES.add(Material.valueOf("DEAD_HORN_CORAL_WALL_FAN"));
            DEAD_CORAL_WALL_FAN_TYPES.add(Material.valueOf("DEAD_TUBE_CORAL_WALL_FAN"));

            // HELMET
            HELMET_TYPES.add(Material.valueOf("GOLDEN_HELMET"));

            // CHESTPLATE
            CHESTPLATE_TYPES.add(Material.valueOf("GOLDEN_CHESTPLATE"));

            // LEGGINGS
            LEGGINGS_TYPES.add(Material.valueOf("GOLDEN_LEGGINGS"));

            // BOOTS
            BOOTS_TYPES.add(Material.valueOf("GOLDEN_BOOTS"));
			
			// SHOVEL
			SHOVEL_TYPES.add(Material.valueOf("IRON_SHOVEL"));
			SHOVEL_TYPES.add(Material.valueOf("WOODEN_SHOVEL"));
			SHOVEL_TYPES.add(Material.valueOf("STONE_SHOVEL"));
			SHOVEL_TYPES.add(Material.valueOf("DIAMOND_SHOVEL"));
			SHOVEL_TYPES.add(Material.valueOf("GOLDEN_SHOVEL"));
			
			// SWORD
			SWORD_TYPES.add(Material.valueOf("GOLDEN_SWORD"));
			SWORD_TYPES.add(Material.valueOf("WOODEN_SWORD"));

			// AXE
			AXE_TYPES.add(Material.valueOf("GOLDEN_AXE"));
			AXE_TYPES.add(Material.valueOf("WOODEN_AXE"));

			// PICKAXE
			PICKAXE_TYPES.add(Material.valueOf("GOLDEN_PICKAXE"));
			PICKAXE_TYPES.add(Material.valueOf("WOODEN_PICKAXE"));

			// HOE
			HOE_TYPES.add(Material.valueOf("GOLDEN_HOE"));
			HOE_TYPES.add(Material.valueOf("WOODEN_HOE"));
        } else {
            // SKULL
            SKULL_TYPES.add(Material.SKULL);
            SKULL_TYPES.add(Material.SKULL_ITEM);

            // ORE
            ORE_TYPES.add(Material.GLOWING_REDSTONE_ORE);
            ORE_TYPES.add(Material.QUARTZ_ORE);

            // BOAT
            BOAT_TYPES.add(Material.BOAT_SPRUCE);
            BOAT_TYPES.add(Material.BOAT_BIRCH);
            BOAT_TYPES.add(Material.BOAT_JUNGLE);
            BOAT_TYPES.add(Material.BOAT_ACACIA);
            BOAT_TYPES.add(Material.BOAT_DARK_OAK);
            BOAT_TYPES.add(Material.BOAT);

            // BUTTON
            BUTTON_TYPES.add(Material.STONE_BUTTON);
            BUTTON_TYPES.add(Material.WOOD_BUTTON);

            // DOOR
            DOOR_TYPES.add(Material.WOODEN_DOOR);
            DOOR_TYPES.add(Material.WOOD_DOOR);

            // FENCE
            FENCE_TYPES.add(Material.FENCE);
            FENCE_TYPES.add(Material.IRON_FENCE);
            FENCE_TYPES.add(Material.NETHER_FENCE);

            // FENCE_GATE
            FENCE_GATE_TYPES.add(Material.FENCE_GATE);

            // LEAVES
            LEAVES_TYPES.add(Material.LEAVES);
            LEAVES_TYPES.add(Material.LEAVES_2);

            // LOG
            LOG_TYPES.add(Material.LOG);
            LOG_TYPES.add(Material.LOG_2);

            // PRESSURE_PLATE
            PRESSURE_PLATE_TYPES.add(Material.IRON_PLATE);
            PRESSURE_PLATE_TYPES.add(Material.GOLD_PLATE);
            PRESSURE_PLATE_TYPES.add(Material.STONE_PLATE);
            PRESSURE_PLATE_TYPES.add(Material.WOOD_PLATE);

            // SAPLING
            SAPLING_TYPES.add(Material.SAPLING);

            // SLAB
            if(NMSVersion.current().compare(NMSVersion.v1_9_R2) >= 0) {
                SLAB_TYPES.add(Material.PURPUR_DOUBLE_SLAB);
                SLAB_TYPES.add(Material.PURPUR_SLAB);
            }
			SLAB_TYPES.add(Material.DOUBLE_STONE_SLAB2);
            SLAB_TYPES.add(Material.STONE_SLAB2);
            SLAB_TYPES.add(Material.STEP);

            // STAIRS
            STAIRS_TYPES.add(Material.WOOD_STAIRS);
            STAIRS_TYPES.add(Material.SMOOTH_STAIRS);
            STAIRS_TYPES.add(Material.NETHER_BRICK_STAIRS);
            STAIRS_TYPES.add(Material.SPRUCE_WOOD_STAIRS);
            STAIRS_TYPES.add(Material.BIRCH_WOOD_STAIRS);
            STAIRS_TYPES.add(Material.JUNGLE_WOOD_STAIRS);

            // TRAPDOOR
            TRAPDOOR_TYPES.add(Material.TRAP_DOOR);
            TRAPDOOR_TYPES.add(Material.IRON_TRAPDOOR);
			
            // WOOD
            WOOD_TYPES.add(Material.WOOD);

            // BANNER
            BANNER_TYPES.add(Material.STANDING_BANNER);
            BANNER_TYPES.add(Material.WALL_BANNER);
            BANNER_TYPES.add(Material.BANNER);

            // BED
            BED_TYPES.add(Material.BED_BLOCK);
            BED_TYPES.add(Material.BED);

            // CARPET
            CARPET_TYPES.add(Material.CARPET);

            if(NMSVersion.current().compare(NMSVersion.v1_12_R1) >= 0) {
                // CONCRETE
                CONCRETE_TYPES.add(Material.valueOf("CONCRETE"));

                // CONCRETE_POWDER
                CONCRETE_POWDER_TYPES.add(Material.valueOf("CONCRETE_POWDER"));
            }

            // STAINED_GLASS
            STAINED_GLASS_TYPES.add(Material.STAINED_GLASS);

            // STAINED_GLASS_PANE
            STAINED_GLASS_PANE_TYPES.add(Material.STAINED_GLASS_PANE);

            // WALL_BANNER
            WALL_BANNER_TYPES.add(Material.WALL_BANNER);

            // WOOL
            WOOL_TYPES.add(Material.WOOL);

            // MUSIC_DISC
            MUSIC_DISC_TYPES.add(Material.GOLD_RECORD);
            MUSIC_DISC_TYPES.add(Material.GREEN_RECORD);
            MUSIC_DISC_TYPES.add(Material.RECORD_3);
            MUSIC_DISC_TYPES.add(Material.RECORD_4);
            MUSIC_DISC_TYPES.add(Material.RECORD_5);
            MUSIC_DISC_TYPES.add(Material.RECORD_6);
            MUSIC_DISC_TYPES.add(Material.RECORD_7);
            MUSIC_DISC_TYPES.add(Material.RECORD_8);
            MUSIC_DISC_TYPES.add(Material.RECORD_9);
            MUSIC_DISC_TYPES.add(Material.RECORD_10);
            MUSIC_DISC_TYPES.add(Material.RECORD_11);
            MUSIC_DISC_TYPES.add(Material.RECORD_12);

            // DYE
            DYE_TYPES.add(Material.INK_SACK);

            // HELMET
            HELMET_TYPES.add(Material.GOLD_HELMET);

            // CHESTPLATE
            CHESTPLATE_TYPES.add(Material.GOLD_CHESTPLATE);

            // LEGGINGS
            LEGGINGS_TYPES.add(Material.GOLD_LEGGINGS);

            // BOOTS
            BOOTS_TYPES.add(Material.GOLD_BOOTS);

			// SHOVEL
			SHOVEL_TYPES.add(Material.IRON_SPADE);
			SHOVEL_TYPES.add(Material.WOOD_SPADE);
			SHOVEL_TYPES.add(Material.STONE_SPADE);
			SHOVEL_TYPES.add(Material.DIAMOND_SPADE);
			SHOVEL_TYPES.add(Material.GOLD_SPADE);
			
			// SWORD
			SWORD_TYPES.add(Material.GOLD_SWORD);
			SWORD_TYPES.add(Material.WOOD_SWORD);

			// AXE
			AXE_TYPES.add(Material.GOLD_AXE);
			AXE_TYPES.add(Material.WOOD_AXE);

			// PICKAXE
			PICKAXE_TYPES.add(Material.GOLD_PICKAXE);
			PICKAXE_TYPES.add(Material.WOOD_PICKAXE);

			// HOE
			HOE_TYPES.add(Material.GOLD_HOE);
			HOE_TYPES.add(Material.WOOD_HOE);
        }

        // ORE
        ORE_TYPES.add(Material.COAL_ORE);
        ORE_TYPES.add(Material.DIAMOND_ORE);
        ORE_TYPES.add(Material.EMERALD_ORE);
        ORE_TYPES.add(Material.GOLD_ORE);
        ORE_TYPES.add(Material.IRON_ORE);
        ORE_TYPES.add(Material.LAPIS_ORE);
        ORE_TYPES.add(Material.REDSTONE_ORE);

        // DOOR
        DOOR_TYPES.add(Material.ACACIA_DOOR);
        DOOR_TYPES.add(Material.BIRCH_DOOR);
        DOOR_TYPES.add(Material.DARK_OAK_DOOR);
        DOOR_TYPES.add(Material.IRON_DOOR);
        DOOR_TYPES.add(Material.JUNGLE_DOOR);
        DOOR_TYPES.add(Material.SPRUCE_DOOR);

        // FENCE
        FENCE_TYPES.add(Material.SPRUCE_FENCE);
        FENCE_TYPES.add(Material.ACACIA_FENCE);
        FENCE_TYPES.add(Material.BIRCH_FENCE);
        FENCE_TYPES.add(Material.DARK_OAK_FENCE);
        FENCE_TYPES.add(Material.JUNGLE_FENCE);

        // FENCE_GATE
        FENCE_GATE_TYPES.add(Material.ACACIA_FENCE_GATE);
        FENCE_GATE_TYPES.add(Material.BIRCH_FENCE_GATE);
        FENCE_GATE_TYPES.add(Material.DARK_OAK_FENCE_GATE);
        FENCE_GATE_TYPES.add(Material.JUNGLE_FENCE_GATE);
        FENCE_GATE_TYPES.add(Material.SPRUCE_FENCE_GATE);

        // STAIRS
        STAIRS_TYPES.add(Material.ACACIA_STAIRS);
        STAIRS_TYPES.add(Material.BRICK_STAIRS);
        STAIRS_TYPES.add(Material.COBBLESTONE_STAIRS);
        STAIRS_TYPES.add(Material.DARK_OAK_STAIRS);
        STAIRS_TYPES.add(Material.QUARTZ_STAIRS);
        STAIRS_TYPES.add(Material.RED_SANDSTONE_STAIRS);
        STAIRS_TYPES.add(Material.SANDSTONE_STAIRS);

        if(NMSVersion.current().compare(NMSVersion.v1_12_R1) >= 0) {
            // GLAZED_TERRACOTTA
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("BLUE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("BLACK_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("BROWN_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("CYAN_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("GRAY_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("GREEN_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LIGHT_BLUE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("LIME_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("MAGENTA_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("ORANGE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("PINK_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("PURPLE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("RED_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("WHITE_GLAZED_TERRACOTTA"));
            GLAZED_TERRACOTTA_TYPES.add(Material.valueOf("YELLOW_GLAZED_TERRACOTTA"));
        }

        if(NMSVersion.current().compare(NMSVersion.v1_11_R1) >= 0) {
            // SHULKER_BOX
            SHULKER_BOX_TYPES.add(Material.valueOf("BLACK_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("BLUE_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("BROWN_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("CYAN_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("GRAY_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("GREEN_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LIGHT_BLUE_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("LIME_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("MAGENTA_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("ORANGE_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("PINK_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("PURPLE_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("RED_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("WHITE_SHULKER_BOX"));
            SHULKER_BOX_TYPES.add(Material.valueOf("YELLOW_SHULKER_BOX"));
        }

        // BUSH
        BUSH_TYPES.add(Material.DEAD_BUSH);

        // HELMET
        HELMET_TYPES.add(Material.LEATHER_HELMET);
        HELMET_TYPES.add(Material.CHAINMAIL_HELMET);
        HELMET_TYPES.add(Material.IRON_HELMET);
        HELMET_TYPES.add(Material.DIAMOND_HELMET);

        // CHESTPLATE
        CHESTPLATE_TYPES.add(Material.LEATHER_CHESTPLATE);
        CHESTPLATE_TYPES.add(Material.CHAINMAIL_CHESTPLATE);
        CHESTPLATE_TYPES.add(Material.IRON_CHESTPLATE);
        CHESTPLATE_TYPES.add(Material.DIAMOND_CHESTPLATE);

        // LEGGINGS
        LEGGINGS_TYPES.add(Material.LEATHER_LEGGINGS);
        LEGGINGS_TYPES.add(Material.CHAINMAIL_LEGGINGS);
        LEGGINGS_TYPES.add(Material.IRON_LEGGINGS);
        LEGGINGS_TYPES.add(Material.DIAMOND_LEGGINGS);

        // BOOTS
        BOOTS_TYPES.add(Material.LEATHER_BOOTS);
        BOOTS_TYPES.add(Material.CHAINMAIL_BOOTS);
        BOOTS_TYPES.add(Material.IRON_BOOTS);
        BOOTS_TYPES.add(Material.DIAMOND_BOOTS);

		// SWORD
		SWORD_TYPES.add(Material.IRON_SWORD);
		SWORD_TYPES.add(Material.STONE_SWORD);
		SWORD_TYPES.add(Material.DIAMOND_SWORD);

		// AXE
		AXE_TYPES.add(Material.IRON_AXE);
		AXE_TYPES.add(Material.STONE_AXE);
		AXE_TYPES.add(Material.DIAMOND_AXE);

		// PICKAXE
		PICKAXE_TYPES.add(Material.IRON_PICKAXE);
		PICKAXE_TYPES.add(Material.STONE_PICKAXE);
		PICKAXE_TYPES.add(Material.DIAMOND_PICKAXE);

		// HOE
		HOE_TYPES.add(Material.STONE_HOE);
		HOE_TYPES.add(Material.IRON_HOE);
		HOE_TYPES.add(Material.DIAMOND_HOE);

        // ARMOR
        ARMOR_TYPES.addAll(HELMET_TYPES);
        ARMOR_TYPES.addAll(LEGGINGS_TYPES);
        ARMOR_TYPES.addAll(CHESTPLATE_TYPES);
        ARMOR_TYPES.addAll(BOOTS_TYPES);

        // STAIRS
        STAIRS_TYPES.add(Material.PURPUR_STAIRS);

        // ARMOR
        ARMOR_TYPES.add(Material.ELYTRA);
    }

    /**
     * Converts the given {@link Material} to {@link String}.<br>
     * This method is similar to {@link #toString(MaterialData)}.
     * @param md the material
     * @return the material as a string
     */
    @NotNull
    public static String toString(@Nullable Material md){
        return md == null ? "null" : md.name();
    }

    /**
     * Converts the given {@link MaterialData} to {@link String}.
     * @param md the material data
     * @return the material data as a string
     */
    @NotNull
    public static String toString(@Nullable MaterialData md){
        return md == null ? "null" : (md.getItemType().name()+":"+md.getData());
    }

    /**
     * Converts the given string to the material data which it represents.
     * @param str a string
     * @return the material data
     */
    @NotNull
    public static MaterialData fromString(@Nullable String str){
        if(str == null || str.equalsIgnoreCase("null")) return new MaterialData(Material.AIR);
        String[] x = str.split(":");
        Material mt;
        if(NMSVersion.current().compare(NMSVersion.v1_13_R1) < 0 && StringUtils.isNumeric(x[0])) {
            mt = Material.getMaterial(Integer.parseInt(x[0]));
        } else {
            mt = parse(x[0].toUpperCase()).orElse(Material.AIR);
        }

        return new MaterialData(mt, x.length >= 2 ? (byte) Integer.parseInt(x[1]) : 0);
    }

    /**
     * Gets all material types related to skull.
     * @return list of material types
     */
    public static List<Material> getSkullTypes(){
        return Collections.unmodifiableList(SKULL_TYPES);
    }

    /**
     * Gets all material types related to ore.
     * @return list of material types
     */
    public static List<Material> getOreTypes(){
        return Collections.unmodifiableList(ORE_TYPES);
    }

    /**
     * Gets all material types related to boat.
     * @return list of material types
     */
    public static List<Material> getBoatTypes(){
        return Collections.unmodifiableList(BOAT_TYPES);
    }

    /**
     * Gets all material types related to button.
     * @return list of material types
     */
    public static List<Material> getButtonTypes(){
        return Collections.unmodifiableList(BUTTON_TYPES);
    }

    /**
     * Gets all material types related to door.
     * @return list of material types
     */
    public static List<Material> getDoorTypes(){
        return Collections.unmodifiableList(DOOR_TYPES);
    }

    /**
     * Gets all material types related to fence.
     * @return list of material types
     */
    public static List<Material> getFenceTypes(){
        return Collections.unmodifiableList(FENCE_TYPES);
    }

    /**
     * Gets all material types related to fence gate.
     * @return list of material types
     */
    public static List<Material> getFenceGateTypes(){
        return Collections.unmodifiableList(FENCE_GATE_TYPES);
    }

    /**
     * Gets all material types related to leaves.
     * @return list of material types
     */
    public static List<Material> getLeavesTypes(){
        return Collections.unmodifiableList(LEAVES_TYPES);
    }

    /**
     * Gets all material types related to log.
     * @return list of material types
     */
    public static List<Material> getLogTypes(){
        return Collections.unmodifiableList(LOG_TYPES);
    }

    /**
     * Gets all material types related to stripped log.
     * @return list of material types
     */
    public static List<Material> getStrippedLogTypes(){
        return Collections.unmodifiableList(STRIPPED_LOG_TYPES);
    }

    /**
     * Gets all material types related to planks.
     * @return list of material types
     */
    public static List<Material> getPlanksTypes(){
        return Collections.unmodifiableList(PLANKS_TYPES);
    }

    /**
     * Gets all material types related to pressure plate.
     * @return list of material types
     */
    public static List<Material> getPressurePlateTypes(){
        return Collections.unmodifiableList(PRESSURE_PLATE_TYPES);
    }

    /**
     * Gets all material types related to sapling.
     * @return list of material types
     */
    public static List<Material> getSaplingTypes(){
        return Collections.unmodifiableList(SAPLING_TYPES);
    }

    /**
     * Gets all material types related to slab.
     * @return list of material types
     */
    public static List<Material> getSlabTypes(){
        return Collections.unmodifiableList(SLAB_TYPES);
    }

    /**
     * Gets all material types related to stairs.
     * @return list of material types
     */
    public static List<Material> getStairsTypes(){
        return Collections.unmodifiableList(STAIRS_TYPES);
    }

    /**
     * Gets all material types related to trapdoor.
     * @return list of material types
     */
    public static List<Material> getTrapdoorTypes(){
        return Collections.unmodifiableList(TRAPDOOR_TYPES);
    }

    /**
     * Gets all material types related to wood.
     * @return list of material types
     */
    public static List<Material> getWoodTypes(){
        return Collections.unmodifiableList(WOOD_TYPES);
    }

    /**
     * Gets all material types related to stripped wood.
     * @return list of material types
     */
    public static List<Material> getStrippedWoodTypes(){
        return Collections.unmodifiableList(STRIPPED_WOOD_TYPES);
    }

    /**
     * Gets all material types related to banner.
     * @return list of material types
     */
    public static List<Material> getBannerTypes(){
        return Collections.unmodifiableList(BANNER_TYPES);
    }

    /**
     * Gets all material types related to bed.
     * @return list of material types
     */
    public static List<Material> getBedTypes(){
        return Collections.unmodifiableList(BED_TYPES);
    }

    /**
     * Gets all material types related to carpet.
     * @return list of material types
     */
    public static List<Material> getCarpetTypes(){
        return Collections.unmodifiableList(CARPET_TYPES);
    }

    /**
     * Gets all material types related to concrete.
     * @return list of material types
     */
    public static List<Material> getConcreteTypes(){
        return Collections.unmodifiableList(CONCRETE_TYPES);
    }

    /**
     * Gets all material types related to concrete powder.
     * @return list of material types
     */
    public static List<Material> getConcretePowderTypes(){
        return Collections.unmodifiableList(CONCRETE_POWDER_TYPES);
    }

    /**
     * Gets all material types related to glazed terracotta.
     * @return list of material types
     */
    public static List<Material> getGlazedTerracottaTypes(){
        return Collections.unmodifiableList(GLAZED_TERRACOTTA_TYPES);
    }

    /**
     * Gets all material types related to shulker box.
     * @return list of material types
     */
    public static List<Material> getShulkerBoxTypes(){
        return Collections.unmodifiableList(SHULKER_BOX_TYPES);
    }

    /**
     * Gets all material types related to stained glass.
     * @return list of material types
     */
    public static List<Material> getStainedGlassTypes(){
        return Collections.unmodifiableList(STAINED_GLASS_TYPES);
    }

    /**
     * Gets all material types related to stained glass pane.
     * @return list of material types
     */
    public static List<Material> getStainedGlassPaneTypes(){
        return Collections.unmodifiableList(STAINED_GLASS_PANE_TYPES);
    }

    /**
     * Gets all material types related to terracotta.
     * @return list of material types
     */
    public static List<Material> getTerracottaTypes(){
        return Collections.unmodifiableList(TERRACOTTA_TYPES);
    }

    /**
     * Gets all material types related to wall banner.
     * @return list of material types
     */
    public static List<Material> getWallBannerTypes(){
        return Collections.unmodifiableList(WALL_BANNER_TYPES);
    }

    /**
     * Gets all material types related to wool.
     * @return list of material types
     */
    public static List<Material> getWoolTypes(){
        return Collections.unmodifiableList(WOOL_TYPES);
    }

    /**
     * Gets all material types related to music disc.
     * @return list of material types
     */
    public static List<Material> getMusicDiscTypes(){
        return Collections.unmodifiableList(MUSIC_DISC_TYPES);
    }

    /**
     * Gets all material types related to bush.
     * @return list of material types
     */
    public static List<Material> getBushTypes(){
        return Collections.unmodifiableList(BUSH_TYPES);
    }

    /**
     * Gets all material types related to dye.
     * @return list of material types
     */
    public static List<Material> getDyeTypes(){
        return Collections.unmodifiableList(DYE_TYPES);
    }

    /**
     * Gets all material types related to coral.
     * @return list of material types
     */
    public static List<Material> getCoralTypes(){
        return Collections.unmodifiableList(CORAL_TYPES);
    }

    /**
     * Gets all material types related to coral block.
     * @return list of material types
     */
    public static List<Material> getCoralBlockTypes(){
        return Collections.unmodifiableList(CORAL_BLOCK_TYPES);
    }

    /**
     * Gets all material types related to coral fan.
     * @return list of material types
     */
    public static List<Material> getCoralFanTypes(){
        return Collections.unmodifiableList(CORAL_FAN_TYPES);
    }

    /**
     * Gets all material types related to coral wall fan.
     * @return list of material types
     */
    public static List<Material> getCoralWallFanTypes(){
        return Collections.unmodifiableList(CORAL_WALL_FAN_TYPES);
    }

    /**
     * Gets all material types related to dead coral.
     * @return list of material types
     */
    public static List<Material> getDeadCoralTypes(){
        return Collections.unmodifiableList(DEAD_CORAL_TYPES);
    }

    /**
     * Gets all material types related to dead coral block.
     * @return list of material types
     */
    public static List<Material> getDeadCoralBlockTypes(){
        return Collections.unmodifiableList(DEAD_CORAL_BLOCK_TYPES);
    }

    /**
     * Gets all material types related to dead coral fan.
     * @return list of material types
     */
    public static List<Material> getDeadCoralFanTypes(){
        return Collections.unmodifiableList(DEAD_CORAL_FAN_TYPES);
    }

    /**
     * Gets all material types related to dead coral wall fan.
     * @return list of material types
     */
    public static List<Material> getDeadCoralWallFanTypes(){
        return Collections.unmodifiableList(DEAD_CORAL_WALL_FAN_TYPES);
    }

    /**
     * Gets all material types related to helmet.
     * @return list of material types
     */
    public static List<Material> getHelmetTypes(){
        return Collections.unmodifiableList(HELMET_TYPES);
    }

    /**
     * Gets all material types related to chestplate.
     * @return list of material types
     */
    public static List<Material> getChestplateTypes(){
        return Collections.unmodifiableList(CHESTPLATE_TYPES);
    }

    /**
     * Gets all material types related to leggings.
     * @return list of material types
     */
    public static List<Material> getLeggingsTypes(){
        return Collections.unmodifiableList(LEGGINGS_TYPES);
    }

    /**
     * Gets all material types related to boots.
     * @return list of material types
     */
    public static List<Material> getBootsTypes(){
        return Collections.unmodifiableList(BOOTS_TYPES);
    }

    /**
     * Gets all material types related to armor.
     * @return list of material types
     */
    public static List<Material> getArmorTypes(){
        return Collections.unmodifiableList(ARMOR_TYPES);
    }

    /**
     * Gets all material types related to sword.
     * @return list of material types
     */
    public static List<Material> getSwordTypes(){
        return Collections.unmodifiableList(SWORD_TYPES);
    }

    /**
     * Gets all material types related to axe.
     * @return list of material types
     */
    public static List<Material> getAxeTypes(){
        return Collections.unmodifiableList(AXE_TYPES);
    }

    /**
     * Gets all material types related to pickaxe.
     * @return list of material types
     */
    public static List<Material> getPickaxeTypes(){
        return Collections.unmodifiableList(PICKAXE_TYPES);
    }

    /**
     * Gets all material types related to shovel.
     * @return list of material types
     */
    public static List<Material> getShovelTypes(){
        return Collections.unmodifiableList(SHOVEL_TYPES);
    }

    /**
     * Gets all material types related to hoe.
     * @return list of material types
     */
    public static List<Material> getHoeTypes(){
        return Collections.unmodifiableList(HOE_TYPES);
    }

    @Nullable
    public static String modernize(@Nullable String material) {
        return modernize(material, 0);
    }

    @Nullable
    public static String modernize(@Nullable String material, int data) {
        if(material == null || material.isEmpty()) return null;
        material = material.toUpperCase();
        Material mt = (Material) EnumUtil.findEnum(Material.class, material);
        return mt != null ? material : LEGACY.get(material, data);
    }

    @NotNull
    public static Optional<Material> parse(@Nullable String material) {
        return parse(material, 0);
    }

    @NotNull
    public static Optional<Material> parse(@Nullable String material, int data) {
        if(material == null || material.isEmpty()) return Optional.empty();
        material = material.toUpperCase();
        Material mt = (Material) EnumUtil.findEnum(Material.class, material);
        if(mt == null) {
            String found = LEGACY.get(material, data);
            if(found != null) {
                mt = (Material) EnumUtil.findEnum(Material.class, found);
            }
        }
        return Optional.ofNullable(mt);
    }
}
