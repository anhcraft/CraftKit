package dev.anhcraft.craftkit.common.utils;

import dev.anhcraft.jvmkit.utils.Condition;
import dev.anhcraft.jvmkit.utils.HttpUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * A utility class which gives you simpler ways to interact with the Spigot API.
 */
public class SpigotApiUtil {
    /**
     * Gets the latest version of a resource.
     * @param resourceId the id of the resource
     * @return version number
     */
    @NotNull
    public static String getResourceLatestVersion(@NotNull String resourceId) {
        Condition.argNotNull("resourceId", resourceId);
        try {
            return HttpUtil.fetchString("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Gets the latest version of a resource.
     * @param resourceId the id of the resource
     * @return version number
     */
    @NotNull
    public static String getResourceLatestVersion(int resourceId) {
        try {
            return HttpUtil.fetchString("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
