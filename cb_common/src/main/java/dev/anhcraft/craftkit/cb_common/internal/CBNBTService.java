package dev.anhcraft.craftkit.cb_common.internal;

import dev.anhcraft.craftkit.cb_common.internal.annotation.IsNMS;
import dev.anhcraft.craftkit.cb_common.nbt.CompoundTag;
import dev.anhcraft.craftkit.cb_common.nbt.NBTTag;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public interface CBNBTService extends CBService {
    void set(String key, NBTTag tag);
    NBTTag get(String key);
    void remove(String key);
    void load(CompoundTag root);
    void load(ItemStack item);
    void load(@IsNMS Object entity);
    void save(CompoundTag root);
    ItemStack save(ItemStack item);
    void save(@IsNMS Object entity);
    int size();
    boolean contains(String key);
    Set<String> keySet();
    String toString();
    CompoundTag clone();
}
