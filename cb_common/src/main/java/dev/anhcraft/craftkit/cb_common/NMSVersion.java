package dev.anhcraft.craftkit.cb_common;

import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Bukkit;

/**
 * Versions of {@code net.minecraft.server} package.
 */
public enum NMSVersion {
    v1_9_R2,
    v1_10_R1,
    v1_11_R1,
    v1_12_R1,
    v1_13_R1,
    v1_13_R2,
    v1_14_R1,
    v1_15_R1,
    v1_16_R1,
    v1_16_R2,
    v1_16_R3,
    v1_17_R1;

    private static final NMSVersion nms = NMSVersion.valueOf(Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3]);

    /**
     * Compares the current version with the given one.
     * @param another another NMS version
     * @return <b>0</b> if both versions are same; negative if this version is older; positive if this version is newer
     */
    public int compare(@NotNull NMSVersion another){
        Condition.argNotNull("another", another);
        return ordinal() - another.ordinal();
    }

    /**
     * Gets the current NMS version.
     * @return NMSVersion
     */
    @NotNull
    public static NMSVersion current(){
        return nms;
    }
}
