package dev.anhcraft.craftkit.cb_common.internal;

import dev.anhcraft.craftkit.cb_common.internal.annotation.IsNMS;
import dev.anhcraft.craftkit.cb_common.nbt.CompoundTag;
import dev.anhcraft.craftkit.cb_common.nbt.NBTTag;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public interface CBNBTService extends CBService {
    void set(String key, NBTTag tag);
    NBTTag get(String key);
    void remove(String key);
    void load(CompoundTag root);
    void load(ItemStack item);
    void load(@IsNMS Object entity);
    void load(Block block);
    void load(DataInput dataInput);
    void load(InputStream inputStream);
    void save(CompoundTag root);
    ItemStack save(ItemStack item);
    void save(@IsNMS Object entity);
    void save(Block block);
    void save(DataOutput dataOutput);
    void save(OutputStream outputStream);
    int size();
    boolean contains(String key);
    Set<String> keySet();
    String toString();
    CompoundTag duplicate();
}
