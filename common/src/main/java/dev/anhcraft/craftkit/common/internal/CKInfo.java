package dev.anhcraft.craftkit.common.internal;

import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CKInfo {
    private static final Map<String, String> MHF_SKIN = new HashMap<>();
    private static String pluginVersion;
    private static int configVersion;

    public static void init(InputStream stream){
        var json = CKPlugin.GSON.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), JsonObject.class);
        pluginVersion = json.getAsJsonPrimitive("plugin_version").getAsString();
        configVersion = json.getAsJsonPrimitive("config_version").getAsInt();
        var skins = json.getAsJsonObject("mhf_skins");
        skins.entrySet().forEach(x -> MHF_SKIN.put(x.getKey(), x.getValue().getAsString()));
    }

    public static String getPluginVersion() {
        return pluginVersion;
    }

    public static int getConfigVersion() {
        return configVersion;
    }

    public static String getMHFSkin(String name) {
        return MHF_SKIN.get(name);
    }
}
