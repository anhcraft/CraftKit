package dev.anhcraft.craftkit.common.utils;

import dev.anhcraft.jvmkit.helpers.HTTPConnectionHelper;
import dev.anhcraft.jvmkit.utils.Condition;
import org.jetbrains.annotations.NotNull;

/**
 * A utility class which gives you simpler ways to interact with the Spigot API.
 */
public class SpigotApiUtil {
    /**
     * Gets the latest version of a resource.
     * @param resourceId the id of the resource
     * @return version number
     */
    public static String getResourceLatestVersion(@NotNull String resourceId) {
        Condition.argNotNull("resourceId", resourceId);
        return new HTTPConnectionHelper("https://api.spigotmc.org/legacy/update.php?resource="+resourceId)
                .setProperty("User-Agent", HTTPConnectionHelper.USER_AGENT_CHROME)
                .doOutput()
                .connect()
                .readText();
    }

    /**
     * Gets the latest version of a resource.
     * @param resourceId the id of the resource
     * @return version number
     */
    public static String getResourceLatestVersion(int resourceId) {
        return new HTTPConnectionHelper("https://api.spigotmc.org/legacy/update.php?resource="+resourceId)
                .setProperty("User-Agent", HTTPConnectionHelper.USER_AGENT_CHROME)
                .doOutput()
                .connect()
                .readText();
    }
}
