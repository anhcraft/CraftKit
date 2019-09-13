package dev.anhcraft.craftkit.common.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.anhcraft.craftkit.common.utils.SpigotApiUtil;
import dev.anhcraft.jvmkit.builders.FilePathBuilder;

import java.io.File;

public interface CKPlugin {
    Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    File ROOT_DIR = new File(new FilePathBuilder().dir("plugins", "CraftKit").build());
    File CONFIG_FILE = new File(ROOT_DIR, "config.yml");
    File OLD_CONFIG_FILE = new File(ROOT_DIR, "config.old.yml");
    CKConfig CONFIG = new CKConfig();
    String CHANNEL_NAMESPACE = "craftkit:plugin";
    String PARTY_CHANNEL_NAMESPACE = "craftkit:party";
    int BUNGEE_RESOURCE_ID = 68505;
    int SPIGOT_RESOURCE_ID = 39007;

    default void checkUpdate(int r){
        CKProvider.TASK_HELPER.newAsyncTask(() -> {
            int expectedVer = SpigotApiUtil.getResourceLatestVersion(r).chars().sum();
            int currentVer = CKInfo.getPluginVersion().chars().sum();
            if(expectedVer < currentVer) CKProvider.CHAT_NO_PREFIX
                    .messageConsole("&c&lCraftKit is outdated! Please update <3")
                    .messageConsole("&a&l>>> Link: https://spigotmc.org/resources/"+r);
        });
    }
}
