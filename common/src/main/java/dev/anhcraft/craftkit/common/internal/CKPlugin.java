package dev.anhcraft.craftkit.common.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.anhcraft.craftkit.common.utils.SpigotResourceInfo;
import dev.anhcraft.craftkit.common.utils.VersionUtil;

import java.io.File;
import java.io.IOException;

public interface CKPlugin {
    Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    String CHANNEL_NAMESPACE = "craftkit:plugin";
    String PARTY_CHANNEL_NAMESPACE = "craftkit:party";
    File libDir = new File("plugins"+File.separatorChar+"CraftKit"+File.separatorChar+"libraries");

    default void checkUpdate(){
        CKProvider.TASK_HELPER.newAsyncTask(() -> {
            try {
                SpigotResourceInfo sri = SpigotResourceInfo.of(CKInfo.isSpigot() ? "39007" : "68505");
                String current = CKInfo.getPluginVersion();
                String expected = sri.getCurrentVersion();
                int delta = VersionUtil.compareVersion(current, expected);
                if(delta > 0) {
                    CKProvider.CHAT_NO_PREFIX.messageConsole("&e&lYou are running CraftKit in development.");
                } else if(delta < 0) {
                    CKProvider.CHAT_NO_PREFIX
                            .messageConsole("&c&lCraftKit is outdated. Please update asap!")
                            .messageConsole("&a&l>>> Link: " + sri.getCurrentVersion());
                }
            } catch (IOException e) {
                CKProvider.CHAT_NO_PREFIX.messageConsole("&e&lFailed to check update for CraftKit.");
            }
        });
    }
}
