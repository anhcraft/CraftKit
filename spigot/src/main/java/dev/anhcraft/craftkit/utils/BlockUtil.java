package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.cb_common.BoundingBox;
import dev.anhcraft.craftkit.cb_common.internal.CBBlockService;
import dev.anhcraft.craftkit.cb_common.internal.CBProvider;
import dev.anhcraft.jvmkit.utils.Condition;
import dev.anhcraft.jvmkit.utils.MathUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility methods for working with {@link Block}.
 */
public class BlockUtil {
    private static final CBBlockService SERVICE = CBProvider.getService(CBBlockService.class).orElseThrow(UnsupportedOperationException::new);
    private static final BlockFace[] HORIZONTAL_FACE = new BlockFace[]{
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };

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
        World w = loc.getWorld();
        double cx = loc.getX();
        double cy = loc.getY();
        double cz = loc.getZ();

        for (int x = -rx; x <= rx; x++){
            for (int y = -ry; y <= ry; y++) {
                for (int z = -rz; z <= rz; z++) {
                    blocks.add(new Location(w, cx + x, cy + y, cz + z).getBlock());
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
     * Creates a block-breaking animation which can only be viewed by {@code viewers}.
     * @param id the id which is used to identify the animation in the clients
     * @param block the block which will have the animation
     * @param stage the stage of the animation (0-9)
     * @param viewers a list of players who can see the animation
     */
    public static void createBreakAnimation(int id, @NotNull Block block, int stage, @NotNull List<Player> viewers){
        Condition.argNotNull("id", id);
        Condition.argNotNull("stage", stage);
        SERVICE.fakeBreak(id, block, stage, viewers);
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
