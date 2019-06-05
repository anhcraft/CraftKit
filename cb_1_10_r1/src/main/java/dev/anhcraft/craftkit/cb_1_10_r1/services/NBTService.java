package dev.anhcraft.craftkit.cb_1_10_r1.services;

import dev.anhcraft.craftkit.cb_1_10_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.CBNBTService;
import dev.anhcraft.craftkit.cb_common.kits.nbt.*;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class NBTService extends CBModule implements CBNBTService {
    private NBTTagCompound root = new NBTTagCompound();

    private static void fromNMS(NBTTagCompound from, CompoundTag to){
        for (String s : from.c()) to.getValue().put(s, fromNMS(from.get(s)));
    }

    private static NBTTag fromNMS(NBTBase tag){
        switch (tag.getTypeId()) {
            case 1: return new ByteTag(((NBTTagByte) tag).g());
            case 2: return new ShortTag(((NBTTagShort) tag).f());
            case 3: return new IntTag(((NBTTagInt) tag).e());
            case 4: return new LongTag(((NBTTagLong) tag).d());
            case 5: return new FloatTag(((NBTTagFloat) tag).i());
            case 6: return new DoubleTag(((NBTTagDouble) tag).h());
            case 7: return new ByteArrayTag(((NBTTagByteArray) tag).c());
            case 8: return new StringTag(((NBTTagString) tag).c_());
            case 9:
                var list = new ListTag();
                var nbtList = (NBTTagList) tag;
                for(var i = 0; i < nbtList.size(); i++) list.getValue().add(fromNMS(nbtList.h(i)));
                return list;
            case 10:
                var compound = new CompoundTag();
                fromNMS((NBTTagCompound) tag, compound);
                return compound;
            case 11: return new IntArrayTag(((NBTTagIntArray) tag).d());
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
        i.c(root);
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
        return root.c();
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
