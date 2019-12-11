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
    v1_15_R1(8);

    private static final NMSVersion nms = NMSVersion.valueOf(Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3]);

    private int i;

    NMSVersion(int i) {
        this.i = i;
    }

    /**
     * Checks if this version is newer than or the same with another version.
     * @param another another NMS version
     * @return {@code true} if it is or {@code false} if not
     * @deprecated Use {@link #compare(NMSVersion)} instead
     */
    @Deprecated
    public boolean isNewerOrSame(@NotNull NMSVersion another){
        Condition.argNotNull("another", another);
        return i >= another.i;
    }

    /**
     * Checks if this version is older than another version.
     * @param another another NMS version
     * @return {@code true} if it is or {@code false} if not
     * @deprecated Use {@link #compare(NMSVersion)} instead
     */
    @Deprecated
    public boolean isOlder(@NotNull NMSVersion another){
        Condition.argNotNull("another", another);
        return i < another.i;
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
     * @deprecated Use {@link #current()} instead
     */
    @Deprecated
    public static NMSVersion getNMSVersion(){
        return nms;
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
