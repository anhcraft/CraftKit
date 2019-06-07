package dev.anhcraft.craftkit.cb_common.kits.nbt;

import dev.anhcraft.craftkit.cb_common.internal.CBEntityService;
import dev.anhcraft.craftkit.cb_common.internal.CBNBTService;
import dev.anhcraft.craftkit.cb_common.internal.CBProvider;
import dev.anhcraft.jvmkit.lang.annotation.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a compound NBT tag.
 */
public class CompoundTag extends NBTTag<Map<String, NBTTag>> implements Serializable {
    private static final long serialVersionUID = 18520914327229522L;
    private static final CBEntityService E_SERVICE = CBProvider.getService(CBEntityService.class).orElseThrow();
    private final CBNBTService service = CBProvider.getService(CBNBTService.class, false).orElseThrow();

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
    public static CompoundTag of(@NotNull ItemStack item){
        Condition.argNotNull("item", item);
        var c = new CompoundTag();
        c.load(item);
        return c;
    }

    /**
     * Returns the compound tag of the given entity.
     * @param entity the entity
     * @return entity's compound
     */
    public static CompoundTag of(@NotNull Entity entity){
        Condition.argNotNull("entity", entity);
        var c = new CompoundTag();
        c.load(entity);
        return c;
    }

    @Override
    public int getTypeId() {
        return TagType.COMPOUND_TAG;
    }

    /**
     * Loads all NBT tags of the given item stack and puts them into this compound.
     * @param item the item stack
     */
    public void load(@NotNull ItemStack item){
        Condition.argNotNull("item", item);
        service.load(item);
    }

    /**
     * Loads all NBT tags of the given entity and puts them into this compound.
     * @param entity the entity
     */
    public void load(@NotNull Entity entity){
        Condition.argNotNull("entity", entity);
        service.load(E_SERVICE.toNms(entity));
    }

    /**
     * Saves this compound to the given item stack.
     * @param item the item stack
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
    public NBTTag get(@NotNull String name){
        Condition.argNotNull("name", name);
        return service.get(name);
    }

    /**
     * Gets the tag which has both given name and class type.
     * @param name the tag's name
     * @param classType the tag's class type
     * @return the tag (may be null if the tag did not exist)
     */
    @SuppressWarnings("unchecked")
    public <T extends NBTTag> T get(@NotNull String name, Class<T> classType){
        Condition.argNotNull("name", name);
        var s = service.get(name);
        return (s != null && classType.isAssignableFrom(s.getClass())) ? (T) s : null;
    }

    /**
     * Gets the tag which has both given name and class type.<br>
     * If it did not exist, this method will try to create it with default value.
     * @param name the tag's name
     * @param classType the tag's class type
     * @return the tag
     */
    @SuppressWarnings("unchecked")
    public <T extends NBTTag> T getOrCreateDefault(@NotNull String name, Class<T> classType){
        Condition.argNotNull("name", name);
        var s = service.get(name);
        return (s != null && classType.isAssignableFrom(s.getClass())) ? (T) s : NBTTag.createDefaultTag(classType);
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
    public Set<String> listNames(){
        return service.keySet();
    }

    /**
     * Lists all NBT tags.
     * @return a set of tags
     */
    public Set<NBTTag> listTags(){
        return listNames().stream().map(this::get)
                .collect(Collectors.toSet());
    }

    /**
     * Converts this compound into {@link String}.
     * @return the string which represents this compound
     */
    public String toString(){
        return service.toString();
    }

    /**
     * Clones this compound.
     * @return a copy of this compound
     */
    public CompoundTag clone(){
        return service.clone();
    }

    /**
     * Merges the given compound into this one.
     * @param another another NBT compound
     */
    public void merge(@NotNull CompoundTag another){
        Condition.argNotNull("another", another);
        another.listNames().forEach(s -> put(s, another.get(s)));
    }

    @Deprecated
    @Override
    public Map<String, NBTTag> getValue(){
        throw new UnsupportedOperationException();
    }
}