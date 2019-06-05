package dev.anhcraft.craftkit.common.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.anhcraft.craftkit.common.utils.SpigetApiUtil;
import dev.anhcraft.jvmkit.builders.FilePathBuilder;

import java.io.File;

public interface CKPlugin {
    Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    File ROOT_DIR = new File(new FilePathBuilder().dir("plugins", "CraftKit").build());
    File SKIN_DIR = new File(ROOT_DIR, "skins");
    File CONFIG_FILE = new File(ROOT_DIR, "config.yml");
    File OLD_CONFIG_FILE = new File(ROOT_DIR, "config.old.yml");
    CKConfig CONFIG = new CKConfig();
    String CHANNEL_NAMESPACE = "craftkit:plugin";
    String PARTY_CHANNEL_NAMESPACE = "craftkit:party";

    default void checkUpdate(){
        CKProvider.TASK_HELPER.newAsyncTask(() -> {
            var expectedVer = SpigetApiUtil.getResourceLatestVersion("39007").chars().sum();
            var currentVer = CKInfo.getPluginVersion().chars().sum();
            if(expectedVer < currentVer) CKProvider.CHAT_NO_PREFIX
                    .messageConsole("&c&lCraftKit is outdated! Please consider to update it xD")
                    .messageConsole("&a&l>>> Link: https://spigotmc.org/resources/39007/");
        });
    }
}
