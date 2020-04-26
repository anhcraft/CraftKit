package dev.anhcraft.craftkit.cb_common.nbt;

import dev.anhcraft.craftkit.cb_common.internal.backend.CBEntityBackend;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBNBTBackend;
import dev.anhcraft.craftkit.cb_common.internal.backend.BackendManager;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a compound NBT tag.
 */
public class CompoundTag extends NBTTag<Map<String, NBTTag>> implements Serializable {
    private static final long serialVersionUID = 18520914327229522L;
    private static final CBEntityBackend E_SERVICE = BackendManager.request(CBEntityBackend.class).orElseThrow(UnsupportedOperationException::new);
    private final CBNBTBackend service = BackendManager.request(CBNBTBackend.class, false).orElseThrow(UnsupportedOperationException::new);

    /**
     * Constructs an instance of {@code CompoundTag}.
     */
    public CompoundTag() {
        super(new HashMap<>());
    }

    /**
     * Returns the compound tag of the given item stack.
     * @param item the item stack
     * @return stack's compound
     */
    @NotNull
    public static CompoundTag of(@NotNull ItemStack item){
        Condition.argNotNull("item", item);
        CompoundTag c = new CompoundTag();
        c.load(item);
        return c;
    }

    /**
     * Returns the compound tag of the given entity.
     * @param entity the entity
     * @return entity's compound
     */
    @NotNull
    public static CompoundTag of(@NotNull Entity entity){
        Condition.argNotNull("entity", entity);
        CompoundTag c = new CompoundTag();
        c.load(entity);
        return c;
    }

    /**
     * Returns the compound tag of the given block.
     * @param block the block
     * @return block's compound
     */
    @NotNull
    public static CompoundTag of(@NotNull Block block){
        Condition.argNotNull("block", block);
        CompoundTag c = new CompoundTag();
        c.load(block);
        return c;
    }

    @Override
    public int getTypeId() {
        return TagType.COMPOUND_TAG;
    }

    /**
     * Loads all NBT tags from the given item stack and puts them into this compound.
     * @param item the item stack
     */
    public void load(@NotNull ItemStack item){
        Condition.argNotNull("item", item);
        service.load(item);
    }

    /**
     * Loads all NBT tags from the given entity and puts them into this compound.
     * @param entity the entity
     */
    public void load(@NotNull Entity entity){
        Condition.argNotNull("entity", entity);
        service.load(E_SERVICE.toNms(entity));
    }

    /**
     * Loads all NBT tags from the given block and puts them into this compound.
     * @param block the block
     */
    public void load(@NotNull Block block){
        Condition.argNotNull("block", block);
        service.load(block);
    }

    /**
     * Loads all NBT tags from the given {@link DataInput} and puts them into this compound.
     * @param dataInput the data input
     */
    public void load(@NotNull DataInput dataInput){
        Condition.argNotNull("dataInput", dataInput);
        service.load(dataInput);
    }

    /**
     * Loads all NBT tags from the given {@link InputStream} and puts them into this compound.
     * @param inputStream the input stream
     */
    public void load(@NotNull InputStream inputStream){
        Condition.argNotNull("inputStream", inputStream);
        service.load(inputStream);
    }

    /**
     * Loads all NBT tags from the given file and puts them into this compound.
     * @param file the file
     */
    public void load(@NotNull File file){
        Condition.argNotNull("file", file);
        try {
            FileInputStream in = new FileInputStream(file);
            load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves this compound to the given item stack.
     * @param item the item stack
     * @return a cloned item
     */
    public ItemStack save(@NotNull ItemStack item){
        Condition.argNotNull("item", item);
        return service.save(item);
    }

    /**
     * Saves this compound to the given entity.
     * @param entity the entity
     */
    public void save(@NotNull Entity entity){
        Condition.argNotNull("entity", entity);
        service.save(E_SERVICE.toNms(entity));
    }

    /**
     * Saves this compound to the given "tile-entity" block.
     * @param block the block
     */
    public void save(@NotNull Block block){
        Condition.argNotNull("block", block);
        service.save(block, true);
    }

    /**
     * Saves this compound to the given "tile-entity" block.
     * @param block the block
     * @param replace should we replace the existing one
     */
    public void save(@NotNull Block block, boolean replace){
        Condition.argNotNull("block", block);
        Condition.argNotNull("replace", replace);
        service.save(block, replace);
    }

    /**
     * Saves this compound to the given {@link DataOutput}.
     * @param dataOutput the data output
     */
    public void save(@NotNull DataOutput dataOutput){
        Condition.argNotNull("dataOutput", dataOutput);
        service.save(dataOutput);
    }

    /**
     * Saves this compound to the given {@link OutputStream}.
     * @param outputStream the output stream
     */
    public void save(@NotNull OutputStream outputStream){
        Condition.argNotNull("outputStream", outputStream);
        service.save(outputStream);
    }

    /**
     * Saves this compound to the given file.
     * @param file the file
     */
    public void save(@NotNull File file){
        Condition.argNotNull("file", file);
        try {
            FileOutputStream out = new FileOutputStream(file);
            save(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Puts a NBT tag into this compound.
     * @param name the name of the tag
     * @param tag the tag
     */
    public void put(@NotNull String name, @NotNull NBTTag tag){
        Condition.argNotNull("name", name);
        Condition.argNotNull("tag", tag);
        service.set(name, tag);
    }

    /**
     * Puts a NBT tag into this compound.
     * @param name the name of the tag
     * @param value the tag's value
     */
    public void put(@NotNull String name, byte value){
        Condition.argNotNull("name", name);
        service.set(name, new ByteTag(value));
    }
    /**
     * Puts a NBT tag into this compound.
     * @param name the name of the tag
     * @param value the tag's value
     */
    public void put(@NotNull String name, byte[] value){
        Condition.argNotNull("name", name);
        service.set(name, new ByteArrayTag(value));
    }

    /**
     * Puts a NBT tag into this compound.
     * @param name the name of the tag
     * @param value the tag's value
     */
    public void put(@NotNull String name, double value){
        Condition.argNotNull("name", name);
        service.set(name, new DoubleTag(value));
    }

    /**
     * Puts a NBT tag into this compound.
     * @param name the name of the tag
     * @param value the tag's value
     */
    public void put(@NotNull String name, float value){
        Condition.argNotNull("name", name);
        service.set(name, new FloatTag(value));
    }

    /**
     * Puts a NBT tag into this compound.
     * @param name the name of the tag
     * @param value the tag's value
     */
    public void put(@NotNull String name, int value){
        Condition.argNotNull("name", name);
        service.set(name, new IntTag(value));
    }

    /**
     * Puts a NBT tag into this compound.
     * @param name the name of the tag
     * @param value the tag's value
     */
    public void put(@NotNull String name, @NotNull int[] value){
        Condition.argNotNull("name", name);
        Condition.argNotNull("value", value);
        service.set(name, new IntArrayTag(value));
    }

    /**
     * Puts a NBT tag into this compound.
     * @param name the name of the tag
     * @param value the tag's value
     */
    public void put(@NotNull String name, long value){
        Condition.argNotNull("name", name);
        service.set(name, new LongTag(value));
    }

    /**
     * Puts a NBT tag into this compound.
     * @param name the name of the tag
     * @param value the tag's value
     */
    public void put(@NotNull String name, @NotNull long[] value){
        Condition.argNotNull("name", name);
        Condition.argNotNull("value", value);
        service.set(name, new LongArrayTag(value));
    }

    /**
     * Puts a NBT tag into this compound.
     * @param name the name of the tag
     * @param value the tag's value
     */
    public void put(@NotNull String name, short value){
        Condition.argNotNull("name", name);
        service.set(name, new ShortTag(value));
    }

    /**
     * Puts a NBT tag into this compound.
     * @param name the name of the tag
     * @param value the tag's value
     */
    public void put(@NotNull String name, @NotNull String value){
        Condition.argNotNull("name", name);
        Condition.argNotNull("value", value);
        service.set(name, new StringTag(value));
    }

    /**
     * Removes the tag which has the given name.
     * @param name the tag's name
     */
    public void remove(@NotNull String name){
        Condition.argNotNull("name", name);
        service.remove(name);
    }

    /**
     * Gets the tag which has the given name.
     * @param name the tag's name
     * @return the tag (may be null if the tag did not exist)
     */
    @Nullable
    public NBTTag get(@NotNull String name){
        Condition.argNotNull("name", name);
        return service.get(name);
    }

    /**
     * Gets the tag which has both given name and class type.
     * @param name the tags name
     * @param classType the tags class type
     * @param <T> the type of the tag
     * @return the tag (may be null if the tag did not exist)
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends NBTTag> T get(@NotNull String name, @NotNull Class<T> classType){
        Condition.argNotNull("name", name);
        Condition.argNotNull("classType", classType);
        NBTTag s = service.get(name);
        return (s != null && classType.isAssignableFrom(s.getClass())) ? (T) s : null;
    }

    /**
     * Gets the tag which has both given name and class type.<br>
     * If it did not exist, this method will try to create it with default value.
     * @param name the tag's name
     * @param classType the tag's class type
     * @param <T> type of the tag
     * @return the tag
     */
    @SuppressWarnings("unchecked")
    public <T extends NBTTag> T getOrCreateDefault(@NotNull String name, @NotNull Class<T> classType){
        Condition.argNotNull("name", name);
        Condition.argNotNull("classType", classType);
        NBTTag s = service.get(name);
        return (s != null && classType.isAssignableFrom(s.getClass())) ? (T) s : NBTTag.createDefaultTag(classType);
    }

    /**
     * Gets the value of a tag.
     * @param name tag's name
     * @param classType tag's class type
     * @param <V> tag's value
     * @param <T> NBT tag
     * @return tag's value
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <V, T extends NBTTag<V>> V getValue(@NotNull String name, @NotNull Class<T> classType){
        Condition.argNotNull("name", name);
        Condition.argNotNull("classType", classType);
        NBTTag s = service.get(name);
        return (s != null && classType.isAssignableFrom(s.getClass())) ? (V) s.getValue() : null;
    }

    /**
     * Returns the total number of tags in this compound.
     * @return this compound's size
     */
    public int size(){
        return service.size();
    }

    /**
     * Checks if this compound has a specific tag.
     * @param name the name of the tag you want to check
     * @return {@code true} if the tag is existed or {@code false} if not
     */
    public boolean has(@NotNull String name){
        Condition.argNotNull("name", name);
        return service.contains(name);
    }

    /**
     * Lists the names of all NBT tags.
     * @return a set of names
     */
    @NotNull
    public Set<String> listNames(){
        return service.keySet();
    }

    /**
     * Lists all NBT tags.
     * @return a set of tags
     */
    @NotNull
    public Set<NBTTag> listTags(){
        return listNames().stream().map(this::get).collect(Collectors.toSet());
    }

    /**
     * Converts this compound into {@link String}.
     * @return the string which represents this compound
     */
    @NotNull
    public String toString(){
        return service.toString();
    }

    /**
     * Clones this compound.
     * @return a copy of this compound
     */
    @NotNull
    public CompoundTag duplicate(){
        return service.duplicate();
    }

    /**
     * @deprecated Uses {@link #duplicate()} instead.
     */
    @NotNull
    @Deprecated
    public CompoundTag clone(){
        return service.duplicate();
    }

    /**
     * Merges the given compound into this one.
     * @param another another NBT compound
     */
    public void merge(@NotNull CompoundTag another){
        Condition.argNotNull("another", another);
        another.service.keySet().forEach(s -> service.set(s, another.service.get(s)));
    }

    @NotNull
    @Deprecated
    @Override
    public Map<String, NBTTag> getValue(){
        throw new UnsupportedOperationException();
    }
}
