package dev.anhcraft.craftkit.cb_common;

import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Bukkit;

/**
 * Versions of {@code net.minecraft.server} package.
 */
public enum NMSVersion {
    v1_9_R2(1),
    v1_10_R1(2),
    v1_11_R1(3),
    v1_12_R1(4),
    v1_13_R1(5),
    v1_13_R2(6),
    v1_14_R1(7),
    v1_15_R1(8),
    v1_16_R1(9),
    v1_16_R2(10),
    v1_16_R3(11);

    private static final NMSVersion nms = NMSVersion.valueOf(Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3]);

    private final int i;

    NMSVersion(int i) {
        this.i = i;
    }

    /**
     * Compares the current version with the given one.
     * @param another another NMS version
     * @return <b>0</b> if both versions are same; negative if this version is older; positive if this version is newer
     */
    public int compare(@NotNull NMSVersion another){
        Condition.argNotNull("another", another);
        return i - another.i;
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
