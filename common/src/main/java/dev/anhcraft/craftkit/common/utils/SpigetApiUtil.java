package dev.anhcraft.craftkit.common.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import dev.anhcraft.jvmkit.utils.HttpUtil;
import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import dev.anhcraft.jvmkit.utils.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * A utility class which gives you simpler ways to interact with the Spiget API.
 */
public class SpigetApiUtil {
    /**
     * Gets the latest version of a resource.
     * @param resourceId the id of the resource
     * @return version
     */
    @NotNull
    public static String getResourceLatestVersion(@NotNull String resourceId) {
        Condition.argNotNull("resourceId", resourceId);
        String text;
        try {
            text = HttpUtil.fetchString("https://api.spiget.org/v2/resources/"+resourceId+"/versions?size=1&sort=-releaseDate&fields=name");
        } catch (IOException e) {
            return "";
        }
        return new Gson().fromJson(text, JsonArray.class).get(0).getAsJsonObject().getAsJsonPrimitive("name").getAsString();
    }

    /**
     * Gets the latest version of a resource.
     * @param resourceId the id of the resource
     * @return version
     */
    @NotNull
    public static String getResourceLatestVersion(int resourceId) {
        String text;
        try {
            text = HttpUtil.fetchString("https://api.spiget.org/v2/resources/"+resourceId+"/versions?size=1&sort=-releaseDate&fields=name");
        } catch (IOException e) {
            return "";
        }
        return new Gson().fromJson(text, JsonArray.class).get(0).getAsJsonObject().getAsJsonPrimitive("name").getAsString();
    }

    /**
     * Download a resource to the given file.
     * @param resourceId the id of the resource
     * @param file the file
     * @return {@code true} if the action was done. Otherwise is {@code false}.
     */
    public static boolean downloadResource(@NotNull String resourceId, @NotNull File file) {
        Condition.argNotNull("resourceId", resourceId);
        Condition.argNotNull("file", file);
        try {
            return FileUtil.write(file, HttpUtil.fetch("https://api.spiget.org/v2/resources/"+resourceId+"/download"));
        } catch (IOException e) {
            return false;
        }
    }
}
