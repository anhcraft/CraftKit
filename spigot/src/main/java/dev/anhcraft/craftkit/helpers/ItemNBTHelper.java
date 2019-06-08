package dev.anhcraft.craftkit.helpers;

import com.google.gson.JsonObject;
import dev.anhcraft.craftkit.cb_common.kits.nbt.*;
import dev.anhcraft.craftkit.common.helpers.Selector;
import dev.anhcraft.craftkit.common.internal.CKPlugin;
import dev.anhcraft.craftkit.common.kits.skin.Skin;
import dev.anhcraft.craftkit.common.utils.ChatUtil;
import dev.anhcraft.craftkit.kits.attribute.ItemModifier;
import dev.anhcraft.craftkit.kits.attribute.Modifier;
import dev.anhcraft.craftkit.lang.enumeration.Attribute;
import dev.anhcraft.craftkit.lang.enumeration.BookGeneration;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Advanced item manipulation by working with NBT tags.
 * <b>Warning: Due to some limitations, there is no object reference to the original. When you select the target, you are creating a copy of it. Therefore, you must call {@link #save()} to get the copy which was applied all changes.</b>
 */
public class ItemNBTHelper extends Selector<ItemStack> {
    private CompoundTag tag;

    @NotNull
    private static String getNmsEquipName(EquipmentSlot slot){
        switch (slot){
            case HAND: return "mainhand";
            case OFF_HAND: return "offhand";
            case HEAD: return "head";
            case CHEST: return "chest";
            case LEGS: return "legs";
            case FEET: return "feet";
            default: return "";
        }
    }

    private static EquipmentSlot getBuukitEquipName(String str){
        switch (str){
            case "mainhand": return EquipmentSlot.HAND;
            case "offhand": return EquipmentSlot.OFF_HAND;
            case "head": return EquipmentSlot.HEAD;
            case "chest": return EquipmentSlot.CHEST;
            case "legs": return EquipmentSlot.LEGS;
            case "feet": return EquipmentSlot.FEET;
        }
        return null;
    }

    /**
     * Constructs an {@code ItemNBTHelper} object which selects the given item stack.
     * @param itemStack the item stack
     * @return ItemNBTHelper
     */
    public static ItemNBTHelper of(@NotNull ItemStack itemStack){
        var i = new ItemNBTHelper();
        i.select(itemStack);
        return i;
    }

    @Override
    protected boolean onSelected(@NotNull ItemStack target) {
        tag = CompoundTag.of(target).getOrCreateDefault("tag", CompoundTag.class);
        return true;
    }

    /**
     * Applies all changes to the stack and returns it.
     * @return the target
     */
    public ItemStack save(){
        var nbt = CompoundTag.of(getTarget());
        nbt.put("tag", tag);
        setTarget(nbt.save(getTarget()));
        return getTarget();
    }

    /**
     * Makes the stack unbreakable or breakable.
     * @param unbreakable {@code true} to make it unbreakable, otherwise is {@code false}
     * @return this object
     */
    public ItemNBTHelper setUnbreakable(boolean unbreakable){
        if(unbreakable) tag.put("Unbreakable", new ByteTag(1));
        else tag.remove("Unbreakable");
        return this;
    }

    /**
     * Checks if the stack is unbreakable.
     * @return {@code true} if it is or {@code false} otherwise
     */
    public boolean isUnbreakable(){
        var t = tag.get("Unbreakable", ByteTag.class);
        return t != null && t.getValue() == 1;
    }

    /**
     * Sets the given skin.<br>
     * Only works if the stack is player skulls.
     * @param skin the skin (its signature can be null)
     * @return this object
     */
    public ItemNBTHelper setSkin(@NotNull Skin skin){
        Condition.argNotNull("skin", skin);
        var skullOwner = tag.getOrCreateDefault("SkullOwner", CompoundTag.class);
        if(!skullOwner.has("Id")) skullOwner.put("Id", new StringTag(UUID.randomUUID().toString()));
        var properties = tag.getOrCreateDefault("Properties", CompoundTag.class);
        var textures = new ListTag(); // reset the textures tag
        var texture = new CompoundTag();
        texture.put("Value", new StringTag(skin.getValue()));
        if(skin.getSignature() != null) texture.put("Signature", new StringTag(skin.getSignature()));
        textures.getValue().add(texture);
        properties.put("textures", textures);
        skullOwner.put("Properties", properties);
        tag.put("SkullOwner", skullOwner);
        return this;
    }

    /**
     * Gets the skin.
     * @return the skin
     */
    @Nullable
    public Skin setSkin(){
        var skullOwner = tag.get("SkullOwner", CompoundTag.class);
        if(skullOwner != null){
            var properties = tag.get("Properties", CompoundTag.class);
            if(properties != null){
                var textures = tag.get("textures", ListTag.class);
                if(textures != null && !textures.getValue().isEmpty()){
                    var texture = textures.getValue().get(0);
                    if(texture instanceof CompoundTag) {
                        var tt = (CompoundTag) texture;
                        var val = tt.get("Value", StringTag.class);
                        var sgn = tt.get("Signature", StringTag.class);
                        if(val != null && sgn != null) return new Skin(val.getValue(), sgn.getValue());
                    }
                }
            }
        }
        return null;
    }

    /**
     * Sets the copy tier.<br>
     * This method only works if the stack is written books.
     * @param generation the copy tier
     * @return this object
     */
    public ItemNBTHelper setGeneration(@NotNull BookGeneration generation){
        Condition.argNotNull("generation", generation);
        tag.put("generation", new IntTag(generation.getId()));
        return this;
    }

    /**
     * Gets the copy tier.
     * @return the copy tier
     */
    @Nullable
    public BookGeneration getGeneration(){
        var t = tag.get("generation", IntTag.class);
        return t == null ? null : BookGeneration.getById(t.getValue());
    }

    /**
     * Sets the author.<br>
     * Formatting codes which are begun with ampersands ({@code &}) are supported.<br>
     * This method only works if the stack is written books.
     * @param author author's name
     * @return this object
     */
    public ItemNBTHelper setAuthor(@NotNull String author){
        Condition.argNotNull("author", author);
        tag.put("author", new StringTag(ChatUtil.formatColorCodes(author)));
        return this;
    }

    /**
     * Gets the author.
     * @return the author
     */
    @Nullable
    public String getAuthor(){
        var t = tag.get("author", StringTag.class);
        return t == null ? null : t.getValue();
    }

    /**
     * Sets the title.<br>
     * Formatting codes which are begun with ampersands ({@code &}) are supported.<br>
     * This method only works if the stack is written books.
     * @param title new title
     * @return this object
     */
    public ItemNBTHelper setTitle(@NotNull String title){
        Condition.argNotNull("title", title);
        tag.put("title", new StringTag(ChatUtil.formatColorCodes(title)));
        return this;
    }

    /**
     * Gets the title.
     * @return the title
     */
    @Nullable
    public String getTitle(){
        var t = tag.get("title", StringTag.class);
        return t == null ? null : t.getValue();
    }

    /**
     * Sets the resolved status.<br>
     * This method only works if the stack is written books.
     * @param resolve {@code true} to resolve books or {@code false} to "unresolve"
     * @return this object
     */
    public ItemNBTHelper setResolve(boolean resolve){
        tag.put("resolved", new ByteTag(resolve ? 1 : 0));
        return this;
    }

    /**
     * Checks if books are resolved.
     * @return {@code true} if they are
     */
    public boolean isResolved(){
        var t = tag.get("resolved", ByteTag.class);
        return t != null && t.getValue() == 1;
    }

    /**
     * Overrides books content.<br>
     * Formatting codes which are begun with ampersands ({@code &}) are supported.<br>
     * This method only works if the stack is written books.
     * @param pages list of pages
     * @return this object
     */
    public ItemNBTHelper setPages(@Nullable List<String> pages){
        var ltag = new ListTag();
        pages = ChatUtil.formatColorCodes(pages);
        if(pages != null) {
            for (var content : pages) {
                var j = new JsonObject();
                j.addProperty("text", content);
                ltag.getValue().add(new StringTag(CKPlugin.GSON.toJson(j)));
            }
        }
        tag.put("pages", ltag);
        return this;
    }

    /**
     * Gets books content.
     * @return a list of pages
     */
    @NotNull
    public List<String> getPages(){
        var ltag = tag.get("pages", ListTag.class);
        return ltag == null ? new ArrayList<>() : ltag.getValue()
                .stream().map(tag -> ((StringTag) tag).getValue()).collect(Collectors.toList());
    }

    /**
     * Gets the content of a page by its index.
     * @param index page's index
     * @return the content (may be null if the page did not exist)
     */
    @Nullable
    public String getPage(int index){
        var ltag = tag.get("pages", ListTag.class);
        if(ltag != null && index < ltag.getValue().size()){
            return ((StringTag) ltag.getValue().get(index)).getValue();
        }
        return null;
    }

    /**
     * Appends a new page.<br>
     * Formatting codes which are begun with ampersands ({@code &}) are supported.<br>
     * This method only works if the stack is written books.
     * @param content the content of that page
     * @return this object
     */
    public ItemNBTHelper addPage(@NotNull String content){
        Condition.argNotNull("content", content);
        var ltag = tag.getOrCreateDefault("pages", ListTag.class);
        var j = new JsonObject();
        j.addProperty("text", ChatUtil.formatColorCodes(content));
        ltag.getValue().add(new StringTag(CKPlugin.GSON.toJson(j)));
        tag.put("pages", ltag);
        return this;
    }

    /**
     * Overrides the content of a page by its index.<br>
     * Formatting codes which are begun with ampersands ({@code &}) are supported.<br>
     * This method only works if the stack is written books.
     * @param index the index
     * @param content new content
     * @return this object
     */
    public ItemNBTHelper setPage(int index, @NotNull String content){
        Condition.argNotNull("content", content);
        var ltag = tag.get("pages", ListTag.class);
        if(ltag != null && index < ltag.getValue().size()){
            var j = new JsonObject();
            j.addProperty("text", ChatUtil.formatColorCodes(content));
            ltag.getValue().set(index, new StringTag(CKPlugin.GSON.toJson(j)));
            tag.put("pages", ltag);
        }
        return this;
    }

    /**
     * Removes a page by its index.<br>
     * This method only works if the stack is written books.
     * @param index the index of the page
     * @return this object
     */
    public ItemNBTHelper removePage(int index){
        var ltag = tag.get("pages", ListTag.class);
        if(ltag != null && index < ltag.getValue().size()){
            ltag.getValue().remove(index);
            tag.put("pages", ltag);
        }
        return this;
    }

    /**
     * Clear books content.
     * @return this object
     */
    public ItemNBTHelper clearPages(){
        tag.remove("pages");
        return this;
    }

    /**
     * Adds an attribute modifier.
     * @param modifier the modifier
     * @return this object
     */
    public ItemNBTHelper addModifier(@NotNull ItemModifier modifier){
        Condition.argNotNull("modifier", modifier);

        var ltag = tag.getOrCreateDefault("AttributeModifiers", ListTag.class);
        var attr = new CompoundTag();
        attr.put("AttributeName", new StringTag(modifier.getAttribute().getId()));
        attr.put("Name", new StringTag(modifier.getName()));
        attr.put("Amount", new DoubleTag(modifier.getAmount()));
        attr.put("Operation", new IntTag(modifier.getOperation().getId()));
        attr.put("UUIDLeast", new LongTag(modifier.getUniqueId().getLeastSignificantBits()));
        attr.put("UUIDMost", new LongTag(modifier.getUniqueId().getMostSignificantBits()));
        attr.put("Slot", new StringTag(getNmsEquipName(modifier.getSlot())));
        ltag.getValue().add(attr);
        tag.put("AttributeModifiers", ltag);
        return this;
    }

    /**
     * Removes all modifiers which are on the given slot.
     * @param slot the slot
     * @return this object
     */
    public ItemNBTHelper removeModifiersBySlot(@NotNull EquipmentSlot slot){
        Condition.argNotNull("slot", slot);

        var ltag = tag.get("AttributeModifiers", ListTag.class);
        if(ltag != null) {
            var nslot = getNmsEquipName(slot);
            var nl = ltag.getValue().stream().filter(tag -> {
                var s = ((CompoundTag) tag).get("Slot", StringTag.class);
                return s == null || !s.getValue().equals(nslot);
            }).collect(Collectors.toList());
            ltag.getValue().clear();
            ltag.getValue().addAll(nl);
            tag.put("AttributeModifiers", ltag);
        }
        return this;
    }

    /**
     * Removes all modifiers which have the same given attribute.
     * @param attribute the attribute
     * @return this object
     */
    public ItemNBTHelper removeModifiersByAttribute(@NotNull Attribute attribute){
        Condition.argNotNull("attribute", attribute);

        var ltag = tag.get("AttributeModifiers", ListTag.class);
        if(ltag != null) {
            var nl = ltag.getValue().stream().filter(tag -> {
                var s = ((CompoundTag) tag).get("AttributeName", StringTag.class);
                return s == null || !s.getValue().equals(attribute.getId());
            }).collect(Collectors.toList());
            ltag.getValue().clear();
            ltag.getValue().addAll(nl);
            tag.put("AttributeModifiers", ltag);
        }
        return this;
    }

    /**
     * Clears all modifiers.
     * @return this object
     */
    public ItemNBTHelper clearModifiers(){
        tag.remove("AttributeModifiers");
        return this;
    }

    /**
     * Gets all attribute modifiers on the stack.
     * @return a list of modifiers
     */
    @NotNull
    public List<ItemModifier> getModifiers(){
        var ltag = tag.get("AttributeModifiers", ListTag.class);
        if(ltag != null) {
            return ltag.getValue().stream().map(tag -> (CompoundTag) tag).map(tag -> {
                var attr = Objects.requireNonNull(tag.get("AttributeName", StringTag.class)).getValue();
                var um = Objects.requireNonNull(tag.get("UUIDMost", LongTag.class)).getValue();
                var ul = Objects.requireNonNull(tag.get("UUIDLeast", LongTag.class)).getValue();
                var name = Objects.requireNonNull(tag.get("Name", StringTag.class)).getValue();
                var amt = Objects.requireNonNull(tag.get("Amount", DoubleTag.class)).getValue();
                var op = Objects.requireNonNull(tag.get("Operation", IntTag.class)).getValue();
                var slot = Objects.requireNonNull(tag.get("Slot", StringTag.class)).getValue();
                return new ItemModifier(
                        new UUID(um, ul), name, amt,
                        Objects.requireNonNull(Modifier.Operation.getById(op)),
                        Objects.requireNonNull(Attribute.getById(attr)),
                        Objects.requireNonNull(getBuukitEquipName(slot))
                );
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
