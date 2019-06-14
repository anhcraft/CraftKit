package dev.anhcraft.craftkit.internal.listeners;

import com.google.common.io.ByteStreams;
import dev.anhcraft.craftkit.common.internal.CKPlugin;
import dev.anhcraft.craftkit.common.kits.skin.Skin;
import dev.anhcraft.craftkit.utils.PlayerUtil;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static net.md_5.bungee.api.ProxyServer.getInstance;

public class CKChannelListener implements Listener {
    @EventHandler
    public void onPluginMessage(PluginMessageEvent ev) {
        if (!ev.getTag().equals(CKPlugin.CHANNEL_NAMESPACE)) return;
        try {
            var stream = new ByteArrayInputStream(ev.getData());
            var data = new DataInputStream(stream);
            var sc = data.readUTF();
            switch(sc) {
                case "ChangeSkin": {
                    String sign;
                    PlayerUtil.changeSkin(
                            getInstance().getPlayer(data.readUTF()),
                            new Skin(data.readUTF(),
                                    (sign = data.readUTF()).isEmpty() ? null : sign));
                    break;
                }
                case "RunProxyCmdConsole":
                    getInstance().getPluginManager().dispatchCommand(
                            getInstance().getConsole(),
                            data.readUTF()
                    );
                    break;
                case "RunProxyCmdPlayer":
                    getInstance().getPluginManager().dispatchCommand(
                            getInstance().getPlayer(data.readUTF()),
                            data.readUTF()
                    );
                    break;
                case "RunServerCmdConsole": {
                    var out = ByteStreams.newDataOutput();
                    var sv = data.readUTF();
                    out.writeUTF("RunServerCmdConsole");
                    out.writeUTF(data.readUTF());
                    getInstance().getServerInfo(sv).sendData(CKPlugin.CHANNEL_NAMESPACE,
                            out.toByteArray(), true);
                    break;
                }
                case "RunServerCmdPlayer": {
                    var out = ByteStreams.newDataOutput();
                    var player = data.readUTF();
                    out.writeUTF("RunServerCmdPlayer");
                    out.writeUTF(player);
                    out.writeUTF(data.readUTF());
                    getInstance().getPlayer(player).getServer().getInfo().sendData(CKPlugin.CHANNEL_NAMESPACE,
                            out.toByteArray(), true);
                    break;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
