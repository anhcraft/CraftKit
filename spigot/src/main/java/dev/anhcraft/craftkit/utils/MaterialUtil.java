package dev.anhcraft.craftkit.utils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import dev.anhcraft.craftkit.LegacyMaterial;
import dev.anhcraft.craftkit.cb_common.NMSVersion;
import dev.anhcraft.jvmkit.utils.EnumUtil;
import dev.anhcraft.jvmkit.utils.PresentPair;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * An utility class to work with {@link Material} as well as {@link MaterialData}.
 */
public class MaterialUtil {
    private static final Table<String, Integer, String> LEGACY = HashBasedTable.create();
    private static final Map<String, PresentPair<String, Integer>> MODERN = new HashMap<>();
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

    /**
     * Registers legacy material.
     * @param legacy legacy material
     * @param data legacy data
     * @param modern modern material
     * @deprecated INTERNAL USES ONLY!
     */
    @Deprecated
    public static void registerLegacyMaterial(String legacy, int data, String modern){
        LEGACY.put(legacy, data, modern);
        MODERN.put(modern, new PresentPair<>(legacy, data));
    }

    static {
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
            SKULL_TYPES.add(Material.valueOf("SKULL"));
            SKULL_TYPES.add(Material.valueOf("SKULL_ITEM"));

            // ORE
            ORE_TYPES.add(Material.valueOf("GLOWING_REDSTONE_ORE"));
            ORE_TYPES.add(Material.valueOf("QUARTZ_ORE"));

            // BOAT
            BOAT_TYPES.add(Material.valueOf("BOAT_SPRUCE"));
            BOAT_TYPES.add(Material.valueOf("BOAT_BIRCH"));
            BOAT_TYPES.add(Material.valueOf("BOAT_JUNGLE"));
            BOAT_TYPES.add(Material.valueOf("BOAT_ACACIA"));
            BOAT_TYPES.add(Material.valueOf("BOAT_DARK_OAK"));
            BOAT_TYPES.add(Material.valueOf("BOAT"));

            // BUTTON
            BUTTON_TYPES.add(Material.valueOf("STONE_BUTTON"));
            BUTTON_TYPES.add(Material.valueOf("WOOD_BUTTON"));

            // DOOR
            DOOR_TYPES.add(Material.valueOf("WOODEN_DOOR"));
            DOOR_TYPES.add(Material.valueOf("WOOD_DOOR"));

            // FENCE
            FENCE_TYPES.add(Material.valueOf("FENCE"));
            FENCE_TYPES.add(Material.valueOf("IRON_FENCE"));
            FENCE_TYPES.add(Material.valueOf("NETHER_FENCE"));

            // FENCE_GATE
            FENCE_GATE_TYPES.add(Material.valueOf("FENCE_GATE"));

            // LEAVES
            LEAVES_TYPES.add(Material.valueOf("LEAVES"));
            LEAVES_TYPES.add(Material.valueOf("LEAVES_2"));

            // LOG
            LOG_TYPES.add(Material.valueOf("LOG"));
            LOG_TYPES.add(Material.valueOf("LOG_2"));

            // PRESSURE_PLATE
            PRESSURE_PLATE_TYPES.add(Material.valueOf("IRON_PLATE"));
            PRESSURE_PLATE_TYPES.add(Material.valueOf("GOLD_PLATE"));
            PRESSURE_PLATE_TYPES.add(Material.valueOf("STONE_PLATE"));
            PRESSURE_PLATE_TYPES.add(Material.valueOf("WOOD_PLATE"));

            // SAPLING
            SAPLING_TYPES.add(Material.valueOf("SAPLING"));

            // SLAB
            if(NMSVersion.current().compare(NMSVersion.v1_9_R2) >= 0) {
                SLAB_TYPES.add(Material.valueOf("PURPUR_DOUBLE_SLAB"));
                SLAB_TYPES.add(Material.valueOf("PURPUR_SLAB"));
            }
			SLAB_TYPES.add(Material.valueOf("DOUBLE_STONE_SLAB2"));
            SLAB_TYPES.add(Material.valueOf("STONE_SLAB2"));
            SLAB_TYPES.add(Material.valueOf("STEP"));

            // STAIRS
            STAIRS_TYPES.add(Material.valueOf("WOOD_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("SMOOTH_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("NETHER_BRICK_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("SPRUCE_WOOD_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("BIRCH_WOOD_STAIRS"));
            STAIRS_TYPES.add(Material.valueOf("JUNGLE_WOOD_STAIRS"));

            // TRAPDOOR
            TRAPDOOR_TYPES.add(Material.valueOf("TRAP_DOOR"));
            TRAPDOOR_TYPES.add(Material.valueOf("IRON_TRAPDOOR"));

            // WOOD
            WOOD_TYPES.add(Material.valueOf("WOOD"));

            // BANNER
            BANNER_TYPES.add(Material.valueOf("STANDING_BANNER"));
            BANNER_TYPES.add(Material.valueOf("WALL_BANNER"));
            BANNER_TYPES.add(Material.valueOf("BANNER"));

            // BED
            BED_TYPES.add(Material.valueOf("BED_BLOCK"));
            BED_TYPES.add(Material.valueOf("BED"));

            // CARPET
            CARPET_TYPES.add(Material.valueOf("CARPET"));

            if(NMSVersion.current().compare(NMSVersion.v1_12_R1) >= 0) {
                // CONCRETE
                CONCRETE_TYPES.add(Material.valueOf("CONCRETE"));

                // CONCRETE_POWDER
                CONCRETE_POWDER_TYPES.add(Material.valueOf("CONCRETE_POWDER"));
            }

            // STAINED_GLASS
            STAINED_GLASS_TYPES.add(Material.valueOf("STAINED_GLASS"));

            // STAINED_GLASS_PANE
            STAINED_GLASS_PANE_TYPES.add(Material.valueOf("STAINED_GLASS_PANE"));

            // WALL_BANNER
            WALL_BANNER_TYPES.add(Material.valueOf("WALL_BANNER"));

            // WOOL
            WOOL_TYPES.add(Material.valueOf("WOOL"));

            // MUSIC_DISC
            MUSIC_DISC_TYPES.add(Material.valueOf("GOLD_RECORD"));
            MUSIC_DISC_TYPES.add(Material.valueOf("GREEN_RECORD"));
            MUSIC_DISC_TYPES.add(Material.valueOf("RECORD_3"));
            MUSIC_DISC_TYPES.add(Material.valueOf("RECORD_4"));
            MUSIC_DISC_TYPES.add(Material.valueOf("RECORD_5"));
            MUSIC_DISC_TYPES.add(Material.valueOf("RECORD_6"));
            MUSIC_DISC_TYPES.add(Material.valueOf("RECORD_7"));
            MUSIC_DISC_TYPES.add(Material.valueOf("RECORD_8"));
            MUSIC_DISC_TYPES.add(Material.valueOf("RECORD_9"));
            MUSIC_DISC_TYPES.add(Material.valueOf("RECORD_10"));
            MUSIC_DISC_TYPES.add(Material.valueOf("RECORD_11"));
            MUSIC_DISC_TYPES.add(Material.valueOf("RECORD_12"));

            // DYE
            DYE_TYPES.add(Material.valueOf("INK_SACK"));

            // HELMET
            HELMET_TYPES.add(Material.valueOf("GOLD_HELMET"));

            // CHESTPLATE
            CHESTPLATE_TYPES.add(Material.valueOf("GOLD_CHESTPLATE"));

            // LEGGINGS
            LEGGINGS_TYPES.add(Material.valueOf("GOLD_LEGGINGS"));

            // BOOTS
            BOOTS_TYPES.add(Material.valueOf("GOLD_BOOTS"));

			// SHOVEL
			SHOVEL_TYPES.add(Material.valueOf("IRON_SPADE"));
			SHOVEL_TYPES.add(Material.valueOf("WOOD_SPADE"));
			SHOVEL_TYPES.add(Material.valueOf("STONE_SPADE"));
			SHOVEL_TYPES.add(Material.valueOf("DIAMOND_SPADE"));
			SHOVEL_TYPES.add(Material.valueOf("GOLD_SPADE"));

			// SWORD
			SWORD_TYPES.add(Material.valueOf("GOLD_SWORD"));
			SWORD_TYPES.add(Material.valueOf("WOOD_SWORD"));

			// AXE
			AXE_TYPES.add(Material.valueOf("GOLD_AXE"));
			AXE_TYPES.add(Material.valueOf("WOOD_AXE"));

			// PICKAXE
			PICKAXE_TYPES.add(Material.valueOf("GOLD_PICKAXE"));
			PICKAXE_TYPES.add(Material.valueOf("WOOD_PICKAXE"));

			// HOE
			HOE_TYPES.add(Material.valueOf("GOLD_HOE"));
			HOE_TYPES.add(Material.valueOf("WOOD_HOE"));
        }

        // ORE
        ORE_TYPES.add(Material.valueOf("COAL_ORE"));
        ORE_TYPES.add(Material.valueOf("DIAMOND_ORE"));
        ORE_TYPES.add(Material.valueOf("EMERALD_ORE"));
        ORE_TYPES.add(Material.valueOf("GOLD_ORE"));
        ORE_TYPES.add(Material.valueOf("IRON_ORE"));
        ORE_TYPES.add(Material.valueOf("LAPIS_ORE"));
        ORE_TYPES.add(Material.valueOf("REDSTONE_ORE"));

        // DOOR
        DOOR_TYPES.add(Material.valueOf("ACACIA_DOOR"));
        DOOR_TYPES.add(Material.valueOf("BIRCH_DOOR"));
        DOOR_TYPES.add(Material.valueOf("DARK_OAK_DOOR"));
        DOOR_TYPES.add(Material.valueOf("IRON_DOOR"));
        DOOR_TYPES.add(Material.valueOf("JUNGLE_DOOR"));
        DOOR_TYPES.add(Material.valueOf("SPRUCE_DOOR"));

        // FENCE
        FENCE_TYPES.add(Material.valueOf("SPRUCE_FENCE"));
        FENCE_TYPES.add(Material.valueOf("ACACIA_FENCE"));
        FENCE_TYPES.add(Material.valueOf("BIRCH_FENCE"));
        FENCE_TYPES.add(Material.valueOf("DARK_OAK_FENCE"));
        FENCE_TYPES.add(Material.valueOf("JUNGLE_FENCE"));

        // FENCE_GATE
        FENCE_GATE_TYPES.add(Material.valueOf("ACACIA_FENCE_GATE"));
        FENCE_GATE_TYPES.add(Material.valueOf("BIRCH_FENCE_GATE"));
        FENCE_GATE_TYPES.add(Material.valueOf("DARK_OAK_FENCE_GATE"));
        FENCE_GATE_TYPES.add(Material.valueOf("JUNGLE_FENCE_GATE"));
        FENCE_GATE_TYPES.add(Material.valueOf("SPRUCE_FENCE_GATE"));

        // STAIRS
        STAIRS_TYPES.add(Material.valueOf("ACACIA_STAIRS"));
        STAIRS_TYPES.add(Material.valueOf("BRICK_STAIRS"));
        STAIRS_TYPES.add(Material.valueOf("COBBLESTONE_STAIRS"));
        STAIRS_TYPES.add(Material.valueOf("DARK_OAK_STAIRS"));
        STAIRS_TYPES.add(Material.valueOf("QUARTZ_STAIRS"));
        STAIRS_TYPES.add(Material.valueOf("RED_SANDSTONE_STAIRS"));
        STAIRS_TYPES.add(Material.valueOf("SANDSTONE_STAIRS"));

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
        BUSH_TYPES.add(Material.valueOf("DEAD_BUSH"));

        // HELMET
        HELMET_TYPES.add(Material.valueOf("LEATHER_HELMET"));
        HELMET_TYPES.add(Material.valueOf("CHAINMAIL_HELMET"));
        HELMET_TYPES.add(Material.valueOf("IRON_HELMET"));
        HELMET_TYPES.add(Material.valueOf("DIAMOND_HELMET"));

        // CHESTPLATE
        CHESTPLATE_TYPES.add(Material.valueOf("LEATHER_CHESTPLATE"));
        CHESTPLATE_TYPES.add(Material.valueOf("CHAINMAIL_CHESTPLATE"));
        CHESTPLATE_TYPES.add(Material.valueOf("IRON_CHESTPLATE"));
        CHESTPLATE_TYPES.add(Material.valueOf("DIAMOND_CHESTPLATE"));

        // LEGGINGS
        LEGGINGS_TYPES.add(Material.valueOf("LEATHER_LEGGINGS"));
        LEGGINGS_TYPES.add(Material.valueOf("CHAINMAIL_LEGGINGS"));
        LEGGINGS_TYPES.add(Material.valueOf("IRON_LEGGINGS"));
        LEGGINGS_TYPES.add(Material.valueOf("DIAMOND_LEGGINGS"));

        // BOOTS
        BOOTS_TYPES.add(Material.valueOf("LEATHER_BOOTS"));
        BOOTS_TYPES.add(Material.valueOf("CHAINMAIL_BOOTS"));
        BOOTS_TYPES.add(Material.valueOf("IRON_BOOTS"));
        BOOTS_TYPES.add(Material.valueOf("DIAMOND_BOOTS"));

		// SWORD
		SWORD_TYPES.add(Material.valueOf("IRON_SWORD"));
		SWORD_TYPES.add(Material.valueOf("STONE_SWORD"));
		SWORD_TYPES.add(Material.valueOf("DIAMOND_SWORD"));

		// AXE
		AXE_TYPES.add(Material.valueOf("IRON_AXE"));
		AXE_TYPES.add(Material.valueOf("STONE_AXE"));
		AXE_TYPES.add(Material.valueOf("DIAMOND_AXE"));

		// PICKAXE
		PICKAXE_TYPES.add(Material.valueOf("IRON_PICKAXE"));
		PICKAXE_TYPES.add(Material.valueOf("STONE_PICKAXE"));
		PICKAXE_TYPES.add(Material.valueOf("DIAMOND_PICKAXE"));

		// HOE
		HOE_TYPES.add(Material.valueOf("STONE_HOE"));
		HOE_TYPES.add(Material.valueOf("IRON_HOE"));
		HOE_TYPES.add(Material.valueOf("DIAMOND_HOE"));

        // ARMOR
        ARMOR_TYPES.addAll(HELMET_TYPES);
        ARMOR_TYPES.addAll(LEGGINGS_TYPES);
        ARMOR_TYPES.addAll(CHESTPLATE_TYPES);
        ARMOR_TYPES.addAll(BOOTS_TYPES);

        // STAIRS
        STAIRS_TYPES.add(Material.valueOf("PURPUR_STAIRS"));

        // ARMOR
        ARMOR_TYPES.add(Material.valueOf("ELYTRA"));
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
            mt = (Material) ReflectionUtil.invokeDeclaredStaticMethod(Material.class, "getMaterial",
                    new Class<?>[]{int.class},
                    new Object[]{Integer.parseInt(x[0])});
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

    /**
     * Checks if the given material is null or is air.
     * @param material material
     * @return {@code true} if it is "null". Otherwise is {@code false}.
     */
    public static boolean isEmpty(@Nullable Material material) {
        return material == null || material == Material.AIR || material.toString().endsWith("_AIR");
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

    @Nullable
    public static LegacyMaterial antiquate(@Nullable String material) {
        if(material == null || material.isEmpty()) return null;
        material = material.toUpperCase();
        Material mt = (Material) EnumUtil.findEnum(Material.class, material);
        if (mt != null) {
            return new LegacyMaterial(mt, 0);
        } else {
            PresentPair<String, Integer> pair = MODERN.get(material);
            if(pair != null) {
                mt = (Material) EnumUtil.findEnum(Material.class, pair.getFirst());
                if (mt != null) return new LegacyMaterial(mt, pair.getSecond());
                mt = (Material) EnumUtil.findEnum(Material.class, "LEGACY_" + pair.getFirst());
                if (mt != null) return new LegacyMaterial(mt, pair.getSecond());
            }
            return null;
        }
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
