package dev.anhcraft.craftkit.internal;

import dev.anhcraft.craftkit.cb_common.NMSVersion;
import dev.anhcraft.jvmkit.utils.FileUtil;
import dev.anhcraft.jvmkit.utils.HttpUtil;

import java.io.File;
import java.io.IOException;

public class CBLibProvider {
    private static final String[] MIRRORS = new String[]{
            "https://github.com/anhcraft/CraftKit/blob/master/cb_{nmsVer}/target/craftkit.cb_{nmsVer}.jar?raw=true",
            "https://repo.anhcraft.dev/repository/mvn/dev/anhcraft/craftkit.cb_{nmsVer}/{pluginVer}/craftkit.cb_{nmsVer}-{pluginVer}.jar"
    };

    public static boolean downloadNMSLib(String pv, NMSVersion nv, File file){
        for(String link : MIRRORS){
            try {
                FileUtil.write(file, HttpUtil.fetch(link
                        .replace("{pluginVer}", pv)
                        .replace("{nmsVer}", nv.name().toLowerCase().substring(1))));
                return true;
            } catch (IOException ignored) {
            }
        }
        return false;
    }
}
