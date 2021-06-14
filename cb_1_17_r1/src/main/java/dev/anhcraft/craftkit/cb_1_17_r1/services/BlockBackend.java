package dev.anhcraft.craftkit.cb_1_17_r1.services;

import dev.anhcraft.craftkit.cb_1_17_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.BoundingBox;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBBlockBackend;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockTileEntity;
import net.minecraft.world.level.block.ITileEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.chunk.ChunkSection;
import net.minecraft.world.level.chunk.DataPaletteBlock;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_17_R1.block.CapturedBlockState;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_17_R1.block.data.CraftBlockData;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Collection;
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
        net.minecraft.world.level.block.Block nmsBlock = _this.getNMS().getBlock();
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

                }// ADD

            }

            return success;
        }

        //return success;
    }

    // * (BlockPosition, IBlockData, int) -> (BlockPosition, IBlockData, int, World, boolean)
    private boolean setTypeAndData(BlockPosition blockposition, IBlockData iblockdata, int i, World world, boolean light) {
        if (world.captureTreeGeneration) {
            CapturedBlockState blockstate = world.capturedBlockStates.get(blockposition);
            if (blockstate == null) {
                blockstate = CapturedBlockState.getTreeBlockState(world, blockposition, i);
                world.capturedBlockStates.put(blockposition.immutableCopy(), blockstate);
            }

            blockstate.setData(iblockdata);
            return true;
        } else if (world.isOutsideWorld(blockposition)) {
            return false;
        } else if (!world.y && world.isDebugWorld()) {
            return false;
        } else {
            Chunk chunk = world.getChunkAtWorldCoords(blockposition);
            net.minecraft.world.level.block.Block block = iblockdata.getBlock();
            boolean captured = false;
            if (world.captureBlockStates && !world.capturedBlockStates.containsKey(blockposition)) {
                CapturedBlockState blockstate = CapturedBlockState.getBlockState(world, blockposition, i);
                world.capturedBlockStates.put(blockposition.immutableCopy(), blockstate);
                captured = true;
            }

            IBlockData iblockdata1 = setType(blockposition, iblockdata, (i & 64) != 0, (i & 1024) == 0, chunk, light);
            if (iblockdata1 == null) {
                if (world.captureBlockStates && captured) {
                    world.capturedBlockStates.remove(blockposition);
                }

                return false;
            } else {
                IBlockData iblockdata2 = world.getType(blockposition);

                if(light) { // ADD
                    if ((i & 128) == 0 && iblockdata2 != iblockdata1 && (iblockdata2.b(world, blockposition) != iblockdata1.b(world, blockposition) || iblockdata2.f() != iblockdata1.f() || iblockdata2.e() || iblockdata1.e())) {
                        world.getMethodProfiler().enter("queueCheckLight");
                        world.getChunkProvider().getLightEngine().a(blockposition);
                        world.getMethodProfiler().exit();
                    }
                }

                if (!world.captureBlockStates) {
                    if(Bukkit.isPrimaryThread()) { // ADD
                        try {
                            world.notifyAndUpdatePhysics(blockposition, chunk, iblockdata1, iblockdata, iblockdata2, i, 512);
                        } catch (StackOverflowError var11) {
                            World.lastPhysicsProblem = new BlockPosition(blockposition);
                        }
                    }
                }

                return true;
            }
        }
    }

    // * (BlockPosition, IBlockData, boolean, boolean) -> (BlockPosition, IBlockData, boolean, boolean, Chunk, boolean)
    @Nullable
    public IBlockData setType(BlockPosition blockposition, IBlockData iblockdata, boolean flag, boolean doPlace, Chunk chunk, boolean light) {
        int i = blockposition.getY();
        int j = chunk.getSectionIndex(i);
        ChunkSection chunksection = chunk.getSections()[j];
        if (chunksection == Chunk.a) {
            if (iblockdata.isAir()) {
                return null;
            }

            chunksection = new ChunkSection(SectionPosition.a(i));
            chunk.getSections()[j] = chunksection;
        }

        boolean flag1 = chunksection.c();
        int k = blockposition.getX() & 15;
        int l = i & 15;
        int i1 = blockposition.getZ() & 15;

        IBlockData iblockdata1 = null; // ADD

        if(isPaletteLocked(chunksection)){ // ADD
            // ADD:START-------------------------------------
            ChunkSection cs = chunksection;
            try {
                iblockdata1 = async(() -> {
                    IBlockData bd;
                    while(true) {
                        if(System.currentTimeMillis() % 50 == 0 && !isPaletteLocked(cs)) {
                            bd = cs.setType(k, l, i1, iblockdata);
                            break;
                        }
                    }
                    return bd;
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            // ADD:END-------------------------------------
        } else {
            iblockdata1 = chunksection.setType(k, l, i1, iblockdata);
        }

        if (iblockdata1 == iblockdata) {
            return null;
        } else {
            net.minecraft.world.level.block.Block block = iblockdata.getBlock();
            ((HeightMap)chunk.j.get(HeightMap.Type.e)).a(k, i, i1, iblockdata);
            ((HeightMap)chunk.j.get(HeightMap.Type.f)).a(k, i, i1, iblockdata);
            ((HeightMap)chunk.j.get(HeightMap.Type.d)).a(k, i, i1, iblockdata);
            ((HeightMap)chunk.j.get(HeightMap.Type.b)).a(k, i, i1, iblockdata);


            if(light) { // ADD
                boolean flag2 = chunksection.c();
                if (flag1 != flag2) {
                    chunk.i.getChunkProvider().getLightEngine().a(blockposition, flag2);
                }
            }

            boolean flag3 = iblockdata1.isTileEntity();
            if (!chunk.i.y) {

                if(Bukkit.isPrimaryThread()) { // ADD
                    iblockdata1.remove(chunk.i, blockposition, iblockdata, flag);
                }

            } else if (!iblockdata1.a(block) && flag3) {
                chunk.removeTileEntity(blockposition);
            }

            if (!chunksection.getType(k, l, i1).a(block)) {
                return null;
            } else {


                if(Bukkit.isPrimaryThread()) { // ADD

                    if (!chunk.i.y && doPlace && (!chunk.i.captureBlockStates || block instanceof BlockTileEntity)) {
                        iblockdata.onPlace(chunk.i, blockposition, iblockdata1, flag);
                    }

                }

                if (iblockdata.isTileEntity()) {
                    TileEntity tileentity = chunk.a(blockposition, Chunk.EnumTileEntityState.c);
                    if (tileentity == null) {
                        tileentity = ((ITileEntity)block).createTile(blockposition, iblockdata);
                        if (tileentity != null) {
                            chunk.b(tileentity);
                        }
                    } else {
                        tileentity.b(iblockdata);

                        // REPLACE
                        ReflectionUtil.invokeDeclaredMethod(Chunk.class, chunk, "f",
                                new Class<?>[]{Entity.class}, new Object[]{tileentity});
                    }
                }

                chunk.markDirty();
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
            return new BoundingBox(aabb.a, aabb.b, aabb.c, aabb.d, aabb.e, aabb.f);
        }
    }

    @Override
    public void stopThreadPool() {
        if(blockChangeThreadQueue != null) {
            blockChangeThreadQueue.shutdownNow();
        }
    }
}
