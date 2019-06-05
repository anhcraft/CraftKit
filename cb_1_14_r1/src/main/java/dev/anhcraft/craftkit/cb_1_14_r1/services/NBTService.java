package dev.anhcraft.craftkit.cb_1_14_r1.services;

import dev.anhcraft.craftkit.cb_1_14_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.CBNBTService;
import dev.anhcraft.craftkit.cb_common.kits.nbt.*;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class NBTService extends CBModule implements CBNBTService {
    private NBTTagCompound root = new NBTTagCompound();

    private static void fromNMS(NBTTagCompound from, CompoundTag to){
        for (String s : from.getKeys()) to.getValue().put(s, fromNMS(from.get(s)));
    }

    private static NBTTag fromNMS(NBTBase tag){
        if(tag == null) return null;
        switch (tag.getTypeId()) {
            case 1: return new ByteTag(((NBTTagByte) tag).asByte());
            case 2: return new ShortTag(((NBTTagShort) tag).asShort());
            case 3: return new IntTag(((NBTTagInt) tag).asInt());
            case 4: return new LongTag(((NBTTagLong) tag).asLong());
            case 5: return new FloatTag(((NBTTagFloat) tag).asFloat());
            case 6: return new DoubleTag(((NBTTagDouble) tag).asDouble());
            case 7: return new ByteArrayTag(((NBTTagByteArray) tag).getBytes());
            case 8: return new StringTag(tag.asString());
            case 9:
                var list = new ListTag();
                var nbtList = (NBTTagList) tag;
                for (NBTBase nbtBase : nbtList) list.getValue().add(fromNMS(nbtBase));
                return list;
            case 10:
                var compound = new CompoundTag();
                fromNMS((NBTTagCompound) tag, compound);
                return compound;
            case 11: return new IntArrayTag(((NBTTagIntArray) tag).getInts());
            case 12: return new LongArrayTag(((NBTTagLongArray) tag).getLongs());
        }
        throw new UnsupportedOperationException();
    }

    private static NBTBase toNMS(NBTTag tag){
        switch (tag.getTypeId()) {
            case 1: return new NBTTagByte((Byte) tag.getValue());
            case 2: return new NBTTagShort((Short) tag.getValue());
            case 3: return new NBTTagInt((Integer) tag.getValue());
            case 4: return new NBTTagLong((Long) tag.getValue());
            case 5: return new NBTTagFloat((Float) tag.getValue());
            case 6: return new NBTTagDouble((Double) tag.getValue());
            case 7: return new NBTTagByteArray((byte[]) tag.getValue());
            case 8: return new NBTTagString((String) tag.getValue());
            case 9:
                var list = new NBTTagList();
                ((List<NBTTag>) tag.getValue()).stream().map(NBTService::toNMS).forEach(list::add);
                return list;
            case 10:
                var compound = new NBTTagCompound();
                ((CompoundTag) tag).getValue().forEach((k, v) -> compound.set(k, toNMS(v)));
                return compound;
            case 11: return new NBTTagIntArray((int[]) tag.getValue());
            case 12: return new NBTTagLongArray((long[]) tag.getValue());
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(String key, NBTTag tag) {
        root.set(key, toNMS(tag));
    }

    @Override
    public NBTTag get(String key) {
        var t = root.get(key);
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
    public void save(CompoundTag root) {
        fromNMS(this.root, root);
    }

    @Override
    public ItemStack save(ItemStack item) {
        var i = CraftItemStack.asNMSCopy(item);
        ReflectionUtil.invokeDeclaredMethod(net.minecraft.server.v1_14_R1.ItemStack.class, i, "load",
                new Class<?>[]{NBTTagCompound.class},
                new Object[]{root});
        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public void save(Object entity) {
        ((Entity) entity).f(root);
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
        return root.getKeys();
    }

    @Override
    public String toString() {
        return root.toString();
    }

    @Override
    public CompoundTag clone() {
        return (CompoundTag) fromNMS(root.clone());
    }
}
