package dev.anhcraft.craftkit.common.internal;

import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CKInfo {
    private static String pluginVersion;
    private static int configVersion;

    public static void init(InputStream stream){
        var json = CKPlugin.GSON.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), JsonObject.class);
        pluginVersion = json.getAsJsonPrimitive("plugin_version").getAsString();
        configVersion = json.getAsJsonPrimitive("config_version").getAsInt();
    }

    public static String getPluginVersion() {
        return pluginVersion;
    }

    public static int getConfigVersion() {
        return configVersion;
    }
}
