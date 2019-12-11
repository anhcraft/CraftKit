package dev.anhcraft.craftkit.internal;

import dev.anhcraft.craftkit.cb_common.NMSVersion;
import dev.anhcraft.craftkit.cb_common.internal.services.CBServerService;
import dev.anhcraft.craftkit.cb_common.internal.services.ServiceProvider;
import dev.anhcraft.jvmkit.helpers.HTTPConnectionHelper;
import dev.anhcraft.jvmkit.utils.FileUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class CBLibProvider {
    private static final String[] MIRRORS = new String[]{
            "https://github.com/anhcraft/CraftKit/blob/master/cb_{nmsVer}/target/craftkit.cb_{nmsVer}.jar?raw=true",
            "https://repo.anhcraft.dev/repository/mvn/dev/anhcraft/craftkit.cb_{nmsVer}/{pluginVer}/craftkit.cb_{nmsVer}-{pluginVer}.jar"
    };

    public static boolean downloadNMSLib(String pv, NMSVersion nv, File file){
        for(String link : MIRRORS){
            try {
                URLConnection url = new URL(link
                        .replace("{pluginVer}", pv)
                        .replace("{nmsVer}", nv.name().toLowerCase().substring(1))
                ).openConnection();
                url.setRequestProperty("User-Agent", HTTPConnectionHelper.USER_AGENT_CHROME);
                url.connect();
                BufferedInputStream in = new BufferedInputStream(url.getInputStream());
                FileUtil.write(file, in);
                in.close();
                return true;
            } catch (IOException ignored) {
            }
        }
        return false;
    }
}