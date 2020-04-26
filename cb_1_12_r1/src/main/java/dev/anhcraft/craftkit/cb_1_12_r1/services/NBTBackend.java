package dev.anhcraft.craftkit.cb_1_12_r1.services;

import dev.anhcraft.craftkit.cb_1_12_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBNBTBackend;
import dev.anhcraft.craftkit.cb_common.nbt.*;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class NBTBackend extends CBModule implements CBNBTBackend {
    private NBTTagCompound root = new NBTTagCompound();

    private static void fromNMS(NBTTagCompound from, CompoundTag to){
        for (String s : from.c()) to.put(s, fromNMS(from.get(s)));
    }

    private static NBTTag fromNMS(NBTBase tag){
        switch (tag.getTypeId()) {
            case TagType.BYTE_TAG: return new ByteTag(((NBTTagByte) tag).g());
            case TagType.SHORT_TAG: return new ShortTag(((NBTTagShort) tag).f());
            case TagType.INT_TAG: return new IntTag(((NBTTagInt) tag).e());
            case TagType.LONG_TAG: return new LongTag(((NBTTagLong) tag).d());
            case TagType.FLOAT_TAG: return new FloatTag(((NBTTagFloat) tag).i());
            case TagType.DOUBLE_TAG: return new DoubleTag(((NBTTagDouble) tag).asDouble());
            case TagType.BYTE_ARRAY_TAG: return new ByteArrayTag(((NBTTagByteArray) tag).c());
            case TagType.STRING_TAG: return new StringTag(((NBTTagString) tag).c_());
            case TagType.LIST_TAG:
                ListTag list = new ListTag();
                NBTTagList nbtList = (NBTTagList) tag;
                for(int i = 0; i < nbtList.size(); i++) list.getValue().add(fromNMS(nbtList.i(i)));
                return list;
            case TagType.COMPOUND_TAG:
                CompoundTag compound = new CompoundTag();
                fromNMS((NBTTagCompound) tag, compound);
                return compound;
            case TagType.INT_ARRAY_TAG: return new IntArrayTag(((NBTTagIntArray) tag).d());
            case TagType.LONG_ARRAY_TAG: return new LongArrayTag((long[]) ReflectionUtil.getDeclaredField(NBTTagLongArray.class, tag, "b"));
        }
        throw new UnsupportedOperationException();
    }

    private static NBTBase toNMS(NBTTag tag){
        switch (tag.getTypeId()) {
            case TagType.BYTE_TAG: return new NBTTagByte((Byte) tag.getValue());
            case TagType.SHORT_TAG: return new NBTTagShort((Short) tag.getValue());
            case TagType.INT_TAG: return new NBTTagInt((Integer) tag.getValue());
            case TagType.LONG_TAG: return new NBTTagLong((Long) tag.getValue());
            case TagType.FLOAT_TAG: return new NBTTagFloat((Float) tag.getValue());
            case TagType.DOUBLE_TAG: return new NBTTagDouble((Double) tag.getValue());
            case TagType.BYTE_ARRAY_TAG: return new NBTTagByteArray((byte[]) tag.getValue());
            case TagType.STRING_TAG: return new NBTTagString((String) tag.getValue());
            case TagType.LIST_TAG:
                NBTTagList list = new NBTTagList();
                ((List<NBTTag>) tag.getValue()).stream().map(NBTBackend::toNMS).forEach(list::add);
                return list;
            case TagType.COMPOUND_TAG:
                NBTTagCompound compound = new NBTTagCompound();
                CompoundTag ctag = (CompoundTag) tag;
                ctag.listNames().forEach(k -> compound.set(k, toNMS(ctag.get(k))));
                return compound;
            case TagType.INT_ARRAY_TAG: return new NBTTagIntArray((int[]) tag.getValue());
            case TagType.LONG_ARRAY_TAG: return new NBTTagLongArray((long[]) tag.getValue());
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(String key, NBTTag tag) {
        root.set(key, toNMS(tag));
    }

    @Override
    public NBTTag get(String key) {
        NBTBase t = root.get(key);
        return t == null ? null : fromNMS(t);
    }

    @Override
    public void remove(String key) {
        root.remove(key);
    }

    @Override
    public void load(CompoundTag root) {
        this.root = (NBTTagCompound) toNMS(root);
    }

    @Override
    public void load(ItemStack item) {
        CraftItemStack.asNMSCopy(item).save(root);
    }

    @Override
    public void load(Object entity) {
        ((Entity) entity).c(root);
    }

    @Override
    public void load(Block block) {
        CraftWorld craftWorld = (CraftWorld) block.getWorld();
        TileEntity tileEntity = craftWorld.getTileEntityAt(block.getX(), block.getY(), block.getZ());
        if(tileEntity != null) tileEntity.save(root);
    }

    @Override
    public void load(DataInput dataInput) {
        try {
            root = NBTCompressedStreamTools.a(dataInput, NBTReadLimiter.a);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load(InputStream inputStream) {
        try {
            root = NBTCompressedStreamTools.a(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(CompoundTag root) {
        fromNMS(this.root, root);
    }

    @Override
    public ItemStack save(ItemStack item) {
        net.minecraft.server.v1_12_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        i.load(root);
        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public void save(Object entity) {
        ((Entity) entity).f(root);
    }

    @Override
    public void save(Block block, boolean replace) {
        CraftWorld craftWorld = (CraftWorld) block.getWorld();
        if(replace) {
            craftWorld.getHandle().setTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()), TileEntity.create(craftWorld.getHandle(), root));
        } else {
            TileEntity tileEntity = craftWorld.getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
            if (tileEntity != null) {
                tileEntity.load(root);
            }
        }
    }

    @Override
    public void save(DataOutput dataOutput) {
        try {
            NBTCompressedStreamTools.a(root, dataOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(OutputStream outputStream) {
        try {
            NBTCompressedStreamTools.a(root, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
        return root.d();
    }

    @Override
    public boolean contains(String key) {
        return root.hasKey(key);
    }

    @Override
    public Set<String> keySet() {
        return root.c();
    }

    @Override
    public String toString() {
        return root.toString();
    }

    @Override
    public CompoundTag duplicate() {
        return (CompoundTag) fromNMS(root.clone());
    }
}
