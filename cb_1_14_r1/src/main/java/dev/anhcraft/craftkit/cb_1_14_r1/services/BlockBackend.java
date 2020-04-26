package dev.anhcraft.craftkit.cb_1_14_r1.services;

import dev.anhcraft.craftkit.cb_1_14_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.BoundingBox;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBBlockBackend;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_14_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_14_R1.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_14_R1.block.data.CraftBlockData;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class BlockBackend extends CBModule implements CBBlockBackend {
    private ExecutorService blockChangeThreadQueue;

    public <T> Future<T> async(Callable<T> a) {
        if(blockChangeThreadQueue == null) {
            blockChangeThreadQueue = Executors.newSingleThreadExecutor();
        }
        return blockChangeThreadQueue.submit(a);
    }

    @Override
    public void fakeBreak(int id, org.bukkit.block.Block block, int stage, Player viewer) {
        BlockPosition pos = new BlockPosition(
                block.getLocation().getBlockX(),
                block.getLocation().getBlockY(),
                block.getLocation().getBlockZ());
        sendPacket(new PacketPlayOutBlockBreakAnimation(id, pos, stage), toEntityPlayer(viewer));
    }

    @Override
    public void fakeBreak(int id, org.bukkit.block.Block block, int stage, Collection<Player> viewers) {
        BlockPosition pos = new BlockPosition(
                block.getLocation().getBlockX(),
                block.getLocation().getBlockY(),
                block.getLocation().getBlockZ());
        sendPacket(new PacketPlayOutBlockBreakAnimation(id, pos, stage), toEntityPlayers(viewers));
    }

    @Override
    public boolean setBlockType(org.bukkit.block.Block block, Object data, boolean applyPhysics, boolean light) {
        // ADD:START-------------------------------------
        IBlockData blockData;
        if(data instanceof BlockData) {
            blockData = ((CraftBlockData) data).getState();
        } else if(data instanceof org.bukkit.Material) {
            blockData = ((CraftBlockData) ((org.bukkit.Material) data).createBlockData()).getState();
        } else if(data instanceof MaterialData) {
            blockData = ((CraftBlockData) ((MaterialData) data).getItemType().createBlockData()).getState();
        } else {
            throw new UnsupportedOperationException("supported data: BlocKData, Material, MaterialData");
        }
        CraftBlock _this = (CraftBlock) block;
        Block nmsBlock = _this.getNMS().getBlock();
        BlockPosition position = _this.getPosition();
        World world = _this.getCraftWorld().getHandle();
        // ADD:END-------------------------------------

        // * this.getNMSBlock() -> nmsBlock
        if (!blockData.isAir() && blockData.getBlock() instanceof BlockTileEntity && blockData.getBlock() != nmsBlock) {
            //if (this.world instanceof World) {
                // * this.world -> world
                // * this.position -> position
                world.removeTileEntity(position);
            //} else {
                //this.world.setTypeAndData(this.position, Blocks.AIR.getBlockData(), 0);
            //}
        }

        if (applyPhysics) {
            // * this.world -> this
            // * this.position -> position
            // * (BlockPosition, IBlockData, int) -> (BlockPosition, IBlockData, int, boolean)
            return this.setTypeAndData(position, blockData, 3, world, light);
        } else {
            // * this.world -> world
            // * this.position -> position
            IBlockData old = world.getType(position);
            // * this.world -> this
            // * this.position -> position
            // * (BlockPosition, IBlockData, int) -> (BlockPosition, IBlockData, int, boolean)
            boolean success = this.setTypeAndData(position, blockData, 1042, world, light);
            if (success) {

                if(Bukkit.isPrimaryThread()) { // ADD

                    // * this.world -> world
                    // * this.position -> position
                    world.getMinecraftWorld().notify(position, old, blockData, 3);

                } // ADD

            }

            return success;
        }

        //return success;
    }

    // * (BlockPosition, IBlockData, int) -> (BlockPosition, IBlockData, int, World, boolean)
    private boolean setTypeAndData(BlockPosition blockposition, IBlockData iblockdata, int i, World world, boolean light) {
        //CraftBlockState blockstate;
        // * this.captureTreeGeneration -> world.captureTreeGeneration
        if (world.captureTreeGeneration) {
            CraftBlockState blockstate = null;
            // * this -> world
            Iterator it = world.capturedBlockStates.iterator();

            while(it.hasNext()) {
                // * blockstate -> CraftBlockState blockstate1
                CraftBlockState blockstate1 = (CraftBlockState)it.next();
                //if (blockstate.getPosition().equals(blockposition)) {
                if (blockstate1.getPosition().equals(blockposition)) {
                    //blockstate = blockstate;
                    blockstate = blockstate1;
                    it.remove();
                    break;
                }
            }

            if (blockstate == null) {
                // * this -> world
                blockstate = CraftBlockState.getBlockState(world, blockposition, i);
            }

            blockstate.setData(iblockdata);
            // * this -> world
            world.capturedBlockStates.add(blockstate);
            return true;
        }
        // * isOutsideWorld -> World.isOutsideWorld
        else if (World.isOutsideWorld(blockposition)) {
            return false;
        }
        // * this -> world
        else if (!world.isClientSide && world.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES) {
            return false;
        }
        else {
            // * this -> world
            Chunk chunk = world.getChunkAtWorldCoords(blockposition);
            Block block = iblockdata.getBlock();
            // * blockstate -> CraftBlockState blockstate
            CraftBlockState blockstate = null;
            // * this -> world
            if (world.captureBlockStates) {
                // * this -> world
                blockstate = CraftBlockState.getBlockState(world, blockposition, i);
                // * this -> world
                world.capturedBlockStates.add(blockstate);
            }

            // * chunk.setType -> setType
            // * (BlockPosition, IBlockData, boolean, boolean) -> (BlockPosition, IBlockData, boolean, boolean, Chunk, boolean)
            IBlockData iblockdata1 = setType(blockposition, iblockdata, (i & 64) != 0, (i & 1024) == 0, chunk, light);
            if (iblockdata1 == null) {
                // * this -> world
                if (world.captureBlockStates) {
                    // * this -> world
                    world.capturedBlockStates.remove(blockstate);
                }

                return false;
            } else {
                // * this -> world
                IBlockData iblockdata2 = world.getType(blockposition);

                if (light) { // ADD

                    // * this -> world
                    if (iblockdata2 != iblockdata1 && (iblockdata2.b(world, blockposition) != iblockdata1.b(world, blockposition) || iblockdata2.h() != iblockdata1.h() || iblockdata2.g() || iblockdata1.g())) {
                        // * this.methodProfiler -> world.getMethodProfiler()
                        world.getMethodProfiler().enter("queueCheckLight");
                        // * this -> world
                        world.getChunkProvider().getLightEngine().a(blockposition);
                        // * this.methodProfiler -> world.getMethodProfiler()
                        world.getMethodProfiler().exit();
                    }

                } // ADD

                // * this -> world
                if (!world.captureBlockStates) {

                    if (Bukkit.isPrimaryThread()) { // ADD

                        try {
                            // * this -> world
                            world.notifyAndUpdatePhysics(blockposition, chunk, iblockdata1, iblockdata, iblockdata2, i);
                        } catch (StackOverflowError var10) {
                            // * lastPhysicsProblem -> World.lastPhysicsProblem
                            World.lastPhysicsProblem = new BlockPosition(blockposition);
                        }

                    }// ADD

                }

                return true;
            }
        }
    }

    // * (BlockPosition, IBlockData, boolean, boolean) -> (BlockPosition, IBlockData, boolean, boolean, Chunk, boolean)
    @Nullable
    public IBlockData setType(BlockPosition blockposition, IBlockData iblockdata, boolean flag, boolean doPlace, Chunk chunk, boolean light) {
        int i = blockposition.getX() & 15;
        int j = blockposition.getY();
        int k = blockposition.getZ() & 15;
        // * this.sections -> chunk.getSections()
        ChunkSection chunksection = chunk.getSections()[j >> 4];
        // * a -> Chunk.a
        if (chunksection == Chunk.a) {
            if (iblockdata.isAir()) {
                return null;
            }

            chunksection = new ChunkSection(j >> 4 << 4);
            // * this.sections -> chunk.getSections()
            chunk.getSections()[j >> 4] = chunksection;
        }

        boolean flag1 = chunksection.c();

        IBlockData iblockdata1 = null; // ADD

        if(isPaletteLocked(chunksection)) { // ADD
            // ADD:START-------------------------------------
            ChunkSection cs = chunksection;
            try {
                iblockdata1 = async(() -> {
                    IBlockData bd;
                    while(true) {
                        if(System.currentTimeMillis() % 50 == 0 && !isPaletteLocked(cs)) {
                            bd = cs.setType(i, j & 15, k, iblockdata);
                            break;
                        }
                    }
                    return bd;
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            // ADD:END-------------------------------------

        } else { // ADD

            // IBlockData iblockdata1 -> iblockdata1
            iblockdata1 = chunksection.setType(i, j & 15, k, iblockdata);

        } // ADD

        if (iblockdata1 == iblockdata) {
            return null;
        } else {
            Block block = iblockdata.getBlock();
            Block block1 = iblockdata1.getBlock();
            // * this -> chunk
            ((HeightMap)chunk.heightMap.get(HeightMap.Type.MOTION_BLOCKING)).a(i, j, k, iblockdata);
            // * this -> chunk
            ((HeightMap)chunk.heightMap.get(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES)).a(i, j, k, iblockdata);
            // * this -> chunk
            ((HeightMap)chunk.heightMap.get(HeightMap.Type.OCEAN_FLOOR)).a(i, j, k, iblockdata);
            // * this -> chunk
            ((HeightMap)chunk.heightMap.get(HeightMap.Type.WORLD_SURFACE)).a(i, j, k, iblockdata);

            if(light) { // ADD

                boolean flag2 = chunksection.c();
                if (flag1 != flag2) {
                    // * this -> chunk
                    chunk.world.getChunkProvider().getLightEngine().a(blockposition, flag2);
                }

            }  // ADD

            // * this -> chunk
            if (!chunk.world.isClientSide) {

                if(Bukkit.isPrimaryThread()) { // ADD

                    // * this -> chunk
                    iblockdata1.remove(chunk.world, blockposition, iblockdata, flag);

                }// ADD

            } else if (block1 != block && block1 instanceof ITileEntity) {
                // * this -> chunk
                chunk.world.removeTileEntity(blockposition);
            }

            if (chunksection.getType(i, j & 15, k).getBlock() != block) {
                return null;
            } else {
                TileEntity tileentity;
                if (block1 instanceof ITileEntity) {
                    // * this -> chunk
                    tileentity = chunk.a(blockposition, Chunk.EnumTileEntityState.CHECK);
                    if (tileentity != null) {
                        tileentity.invalidateBlockCache();
                    }
                }

                if(Bukkit.isPrimaryThread()) { // ADD

                    // * this -> chunk
                    if (!chunk.world.isClientSide && doPlace && (!chunk.world.captureBlockStates || block instanceof BlockTileEntity)) {
                        // * this -> chunk
                        iblockdata.onPlace(chunk.world, blockposition, iblockdata1, flag);
                    }

                } // ADD

                if (block instanceof ITileEntity) {
                    // * this -> chunk
                    tileentity = chunk.a(blockposition, Chunk.EnumTileEntityState.CHECK);
                    if (tileentity == null) {
                        // * this -> chunk
                        tileentity = ((ITileEntity)block).createTile(chunk.world);
                        // * this -> chunk
                        chunk.world.setTileEntity(blockposition, tileentity);
                    } else {
                        tileentity.invalidateBlockCache();
                    }
                }

                //this.s = true;
                return iblockdata1;
            }
        }
    }

    private synchronized boolean isPaletteLocked(ChunkSection chunkSection){
        DataPaletteBlock<?> dpb = chunkSection.getBlocks();
        for (Field f : DataPaletteBlock.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (ReentrantLock.class.isAssignableFrom(f.getType())) {
                try {
                    ReentrantLock lock = (ReentrantLock) f.get(dpb);
                    return lock != null && lock.isLocked();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public BoundingBox getBoundingBox(org.bukkit.block.Block block) {
        CraftBlock craftBlock = (CraftBlock) block;
        IBlockAccess blockAccess = craftBlock.getCraftWorld().getHandle();
        IBlockData blockData = craftBlock.getNMS();
        VoxelShape vs = blockData.getShape(blockAccess, craftBlock.getPosition());
        if(vs.isEmpty()) return new BoundingBox();
        else {
            AxisAlignedBB aabb = vs.getBoundingBox();
            return new BoundingBox(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
        }
    }

    @Override
    public void stopThreadPool() {
        if(blockChangeThreadQueue != null) {
            blockChangeThreadQueue.shutdownNow();
        }
    }
}
