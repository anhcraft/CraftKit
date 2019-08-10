package dev.anhcraft.craftkit.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.anhcraft.craftkit.common.internal.CKPlugin;
import dev.anhcraft.craftkit.common.kits.skin.Skin;
import dev.anhcraft.jvmkit.utils.Condition;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.connection.LoginResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Utility methods which are related to {@link ProxiedPlayer}.
 */
public class PlayerUtil {
    /**
     * Gets the offline id of given player.
     * @param player the player
     * @return the offline id.
     */
    public static UUID getOfflineId(@NotNull String player){
        Condition.argNotNull("player", player);
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + player).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gets the skin of given player.
     * @param player player
     * @return the skin
     */
    @Nullable
    public static Skin getSkin(@NotNull ProxiedPlayer player){
        Condition.argNotNull("player", player);
        InitialHandler ih = (InitialHandler) player.getPendingConnection();
        LoginResult lr = ih.getLoginProfile();
        if(lr != null) {
            for (LoginResult.Property p : lr.getProperties()) {
                if (p.getName().equals("textures")) return new Skin(p.getValue(), p.getSignature());
            }
        }
        return null;
    }

    /**
     * Changes the skin of given player.
     * @param player the player
     * @param skin the new skin
     */
    public static void changeSkin(@NotNull ProxiedPlayer player, @NotNull Skin skin){
        Condition.argNotNull("player", player);
        Condition.argNotNull("skin", skin);
        InitialHandler ih = (InitialHandler) player.getPendingConnection();
        LoginResult lr = ih.getLoginProfile();
        if(lr == null) lr = new LoginResult(player.getUniqueId().toString().replace("-", ""),player.getName(), null);
        lr.setProperties(new LoginResult.Property[]{
                new LoginResult.Property("textures", skin.getValue(), skin.getSignature())
        });
        ReflectionUtil.setDeclaredField(InitialHandler.class, ih,"loginProfile", lr);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ChangeSkin");
        out.writeUTF(player.getName());
        out.writeUTF(skin.getValue());
        out.writeUTF(skin.getSignature());
        player.getServer().getInfo().sendData(CKPlugin.CHANNEL_NAMESPACE, out.toByteArray(), true);
    }
}
