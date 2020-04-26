package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.cb_common.BoundingBox;
import dev.anhcraft.craftkit.cb_common.internal.backend.BackendManager;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBBlockBackend;
import dev.anhcraft.jvmkit.lang.annotation.Beta;
import dev.anhcraft.jvmkit.utils.Condition;
import dev.anhcraft.jvmkit.utils.MathUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Utility methods for working with {@link Block}.
 */
public class BlockUtil {
    private static final CBBlockBackend SERVICE = BackendManager.request(CBBlockBackend.class).orElseThrow(UnsupportedOperationException::new);
    private static final BlockFace[] HORIZONTAL_FACE = new BlockFace[]{
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };

    /**
     * Sets block fast with synchronous-check bypassed.<br>
     * Warning: the following issues may occur:
     * <ul>
     *     <li>Concurrent modification exception</li>
     *     <li>Ghost blocks</li>
     *     <li>...</li>
     * </ul>
     * Another thing is about calling the method outside of the main thread.<br>
     * Tile entities may get no updates if calling asynchronously.<br>
     * The Spigot-patched server will throw an exception if an tile entity<br>
     * is removed while working off the main thread. But luckily that case<br>
     * not for adding a newly block or replacing from a non-tile block.<br>
     * In general, if the block is definitely empty (e.g: when working with<br>
     * a new chunk or a new world, it is still "safe" to call this method<br>
     * asynchronously). Otherwise, better to stay in the main thread.<br>
     * Currently, this method only works at 1.13 or newer versions.
     * @param block the block
     * @param data block data (Material, MaterialData, BlockData)
     * @param physics apply physics or not
     * @param light do light check or not
     * @return {@code true} if success; {@code false} otherwise
     */
    @Beta
    public static boolean setBlockFast(@NotNull Block block, @Nullable Object data, boolean physics, boolean light) {
        Condition.argNotNull("block", block);
        Condition.argNotNull("data", data);
        return SERVICE.setBlockType(block, data, physics, light);
    }

    /**
     * Gets all blocks in specific distance from given central location.
     * @param loc the central location
     * @param rx the maximum distance on the x axis
     * @param ry the maximum distance on the y axis
     * @param rz the maximum distance on the z axis
     * @return list of blocks
     */
    @NotNull
    public static List<Block> getNearbyBlocks(@NotNull Location loc, int rx, int ry, int rz){
        Condition.argNotNull("loc", loc);

        List<Block> blocks = new ArrayList<>();
        double cx = loc.getX();
        double cy = loc.getY();
        double cz = loc.getZ();

        for (int x = -rx; x <= rx; x++){
            for (int y = -ry; y <= ry; y++) {
                for (int z = -rz; z <= rz; z++) {
                    loc.setX(cx + x);
                    loc.setY(cy + y);
                    loc.setZ(cz + z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }

    /**
     * Rotates the given block face by {@code angle}.
     * @param face the original face
     * @param angle the angle
     * @return rotated face
     */
    @NotNull
    public static BlockFace rotateFace(@NotNull BlockFace face, double angle){
        Condition.argNotNull("face", face);

        angle = MathUtil.nextMultiple(angle, 90); // round the angle
        int delta = (int) (angle/90); // convert the angle (0-360) to (0-4)
        if(delta == 0) return face;
        else {
            int index = Arrays.binarySearch(HORIZONTAL_FACE, face);
            if(index == -1) return face;
            index += delta;
            if(index >= HORIZONTAL_FACE.length) index = 0;
            return HORIZONTAL_FACE[index];
        }
    }

    /**
     * Creates a block-breaking animation which can only be viewed by the given viewer.
     * @param id the id which is used to identify the animation in the clients
     * @param block the block which will have the animation
     * @param stage the stage of the animation (0-9)
     * @param viewer the player who can view
     */
    public static void createBreakAnimation(int id, @NotNull Block block, int stage, @NotNull Player viewer){
        Condition.argNotNull("id", id);
        Condition.argNotNull("stage", stage);
        SERVICE.fakeBreak(id, block, MathUtil.clampInt(stage, 0, 9), viewer);
    }

    /**
     * Creates a block-breaking animation which can only be viewed by {@code viewers}.
     * @param id the id which is used to identify the animation in the clients
     * @param block the block which will have the animation
     * @param stage the stage of the animation (0-9)
     * @param viewers a list of players who can see the animation
     */
    public static void createBreakAnimation(int id, @NotNull Block block, int stage, @NotNull Collection<Player> viewers){
        Condition.argNotNull("id", id);
        Condition.argNotNull("stage", stage);
        SERVICE.fakeBreak(id, block, MathUtil.clampInt(stage, 0, 9), viewers);
    }

    /**
     * Gets the bounding box of the given block.
     * @param block the block
     * @return {@link BoundingBox}
     */
    @NotNull
    public static BoundingBox getBoundingBox(@NotNull Block block){
        return SERVICE.getBoundingBox(block);
    }
}
