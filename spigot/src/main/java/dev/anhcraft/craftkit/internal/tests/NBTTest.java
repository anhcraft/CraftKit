package dev.anhcraft.craftkit.internal.tests;

import dev.anhcraft.craftkit.cb_common.nbt.CompoundTag;
import dev.anhcraft.craftkit.cb_common.nbt.IntArrayTag;
import dev.anhcraft.craftkit.cb_common.nbt.IntTag;
import dev.anhcraft.jvmkit.utils.FileUtil;
import dev.anhcraft.jvmkit.utils.ObjectUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class NBTTest implements ITest {
    @Override
    public @NotNull String name() {
        return "NBT Test";
    }

    @Override
    public void run(@NotNull Player player, @NotNull TestChain chain) {
        CompoundTag tag = new CompoundTag();
        tag.put("a", "string");
        tag.put("b", 1);
        tag.put("c", 0.47f);
        tag.put("d", 2.13d);
        tag.put("e", (byte) 5);
        tag.put("f", new int[]{3, 0, 1, 4, 7, 2, 9});
        tag.put("g", tag.duplicate());
        if(tag.has("h")) chain.report(false, null);
        if(!tag.has("a")) chain.report(false, null);
        if(tag.getValue("b", IntTag.class) == null) chain.report(false, null);
        if(tag.getValue("f", IntArrayTag.class) == null) chain.report(false, null);
        File f = new File(FileUtil.TEMP_DIR, "abc.xyz");
        tag.save(f);
        tag = new CompoundTag();
        tag.load(f);
        if(tag.has("h")) chain.report(false, null);
        if(!tag.has("a")) chain.report(false, null);
        if(tag.getValue("b", IntTag.class) == null) chain.report(false, null);
        if(tag.getValue("f", IntArrayTag.class) == null) chain.report(false, null);
        if(!(tag.get("g") instanceof CompoundTag)) chain.report(false, null);
        tag = CompoundTag.of(player);
        tag.save(f);
        tag = CompoundTag.of(player.getLocation().getBlock());
        tag.save(f);
        tag = CompoundTag.of(ObjectUtil.optional(player.getInventory().getItemInMainHand(), new ItemStack(Material.AIR)));
        tag.save(f);
        f.delete();
    }
}
