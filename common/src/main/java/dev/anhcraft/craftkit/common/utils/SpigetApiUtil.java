package dev.anhcraft.craftkit.common.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import dev.anhcraft.jvmkit.helpers.HTTPConnectionHelper;
import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import dev.anhcraft.jvmkit.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * A utility class which gives you simpler ways to interact with the Spiget API.
 */
public class SpigetApiUtil {
    /**
     * Gets the latest version of a resource.
     * @param resourceId the id of the resource
     * @return version number
     */
    public static String getResourceLatestVersion(@NotNull String resourceId) {
        Condition.argNotNull("resourceId", resourceId);
        String text = new HTTPConnectionHelper("https://api.spiget.org/v2/resources/"+resourceId+"/versions?size=1&sort=-releaseDate&fields=name")
                .setProperty("User-Agent", HTTPConnectionHelper.USER_AGENT_CHROME)
                .connect()
                .readText();
        return new Gson().fromJson(text, JsonArray.class).get(0).getAsJsonObject().getAsJsonPrimitive("name").getAsString();
    }

    /**
     * Gets the latest version of a resource.
     * @param resourceId the id of the resource
     * @return version number
     */
    public static String getResourceLatestVersion(int resourceId) {
        String text = new HTTPConnectionHelper("https://api.spiget.org/v2/resources/"+resourceId+"/versions?size=1&sort=-releaseDate&fields=name")
                .setProperty("User-Agent", HTTPConnectionHelper.USER_AGENT_CHROME)
                .connect()
                .readText();
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
        InputStream in = new HTTPConnectionHelper("https://api.spiget.org/v2/resources/"+resourceId+"/download").setProperty("User-Agent", HTTPConnectionHelper.USER_AGENT_CHROME).connect().getInput();
        boolean res = FileUtil.write(file, in);
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
