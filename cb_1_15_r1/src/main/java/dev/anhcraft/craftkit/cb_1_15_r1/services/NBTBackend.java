package dev.anhcraft.craftkit.cb_1_15_r1.services;

import dev.anhcraft.craftkit.cb_1_15_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBNBTBackend;
import dev.anhcraft.craftkit.cb_common.nbt.*;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class NBTBackend extends CBModule implements CBNBTBackend {
    private NBTTagCompound root = new NBTTagCompound();

    private static void fromNMS(NBTTagCompound from, CompoundTag to){
        for (String s : from.getKeys()) to.put(s, fromNMS(from.get(s)));
    }

    private static NBTTag fromNMS(NBTBase tag){
        if(tag == null) return null;
        switch (tag.getTypeId()) {
            case TagType.BYTE_TAG: return new ByteTag(((NBTTagByte) tag).asByte());
            case TagType.SHORT_TAG: return new ShortTag(((NBTTagShort) tag).asShort());
            case TagType.INT_TAG: return new IntTag(((NBTTagInt) tag).asInt());
            case TagType.LONG_TAG: return new LongTag(((NBTTagLong) tag).asLong());
            case TagType.FLOAT_TAG: return new FloatTag(((NBTTagFloat) tag).asFloat());
            case TagType.DOUBLE_TAG: return new DoubleTag(((NBTTagDouble) tag).asDouble());
            case TagType.BYTE_ARRAY_TAG: return new ByteArrayTag(((NBTTagByteArray) tag).getBytes());
            case TagType.STRING_TAG: return new StringTag(tag.asString());
            case TagType.LIST_TAG:
                ListTag list = new ListTag();
                NBTTagList nbtList = (NBTTagList) tag;
                for (NBTBase nbtBase : nbtList) list.getValue().add(fromNMS(nbtBase));
                return list;
            case TagType.COMPOUND_TAG:
                CompoundTag compound = new CompoundTag();
                fromNMS((NBTTagCompound) tag, compound);
                return compound;
            case TagType.INT_ARRAY_TAG: return new IntArrayTag(((NBTTagIntArray) tag).getInts());
            case TagType.LONG_ARRAY_TAG: return new LongArrayTag(((NBTTagLongArray) tag).getLongs());
        }
        throw new UnsupportedOperationException();
    }

    private static NBTBase toNMS(NBTTag tag){
        switch (tag.getTypeId()) {
            case TagType.BYTE_TAG: return NBTTagByte.a((Byte) tag.getValue());
            case TagType.SHORT_TAG: return NBTTagShort.a((Short) tag.getValue());
            case TagType.INT_TAG: return NBTTagInt.a((Integer) tag.getValue());
            case TagType.LONG_TAG: return NBTTagLong.a((Long) tag.getValue());
            case TagType.FLOAT_TAG: return NBTTagFloat.a((Float) tag.getValue());
            case TagType.DOUBLE_TAG: return NBTTagDouble.a((Double) tag.getValue());
            case TagType.BYTE_ARRAY_TAG: return new NBTTagByteArray((byte[]) tag.getValue());
            case TagType.STRING_TAG: return NBTTagString.a((String) tag.getValue());
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
    public void load(org.bukkit.entity.Entity entity) {
        ((CraftEntity) entity).getHandle().c(root);
    }

    @Override
    public void load(Block block) {
        CraftWorld craftWorld = (CraftWorld) block.getWorld();
        TileEntity tileEntity = craftWorld.getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
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
        net.minecraft.server.v1_15_R1.ItemStack i = CraftItemStack.asNMSCopy(item);
        ReflectionUtil.invokeDeclaredMethod(net.minecraft.server.v1_15_R1.ItemStack.class, i, "load",
                new Class<?>[]{NBTTagCompound.class},
                new Object[]{root});
        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public void save(org.bukkit.entity.Entity entity) {
        ((CraftEntity) entity).getHandle().f(root);
    }

    @Override
    public void save(Block block, boolean replace) {
        CraftWorld craftWorld = (CraftWorld) block.getWorld();
        if(replace) {
            craftWorld.getHandle().setTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()), TileEntity.create(root));
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
        return root.e();
    }

    @Override
    public boolean contains(String key) {
        return root.hasKey(key);
    }

    @Override
    public Set<String> keySet() {
        return root.getKeys();
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
