package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.common.kits.skin.Skin;
import dev.anhcraft.jvmkit.lang.annotation.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.connection.InitialHandler;

import javax.naming.OperationNotSupportedException;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
    public static Skin getSkin(@NotNull ProxiedPlayer player){
        Condition.argNotNull("player", player);
        var ih = (InitialHandler) player.getPendingConnection();
        var lr = ih.getLoginProfile();
        if(lr != null) {
            for (var p : lr.getProperties()) {
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
        try {
            throw new OperationNotSupportedException();
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
        // TODO Add change skin for proxy side
    }

    /**
     * Changes the skin of given player.
     * @param player the player
     * @param skin the new skin
     * @param viewers list of players who can see the new skin of that player (if that player is not included in this list, he will not able to view his new skin)
     */
    public static void changeSkin(@NotNull ProxiedPlayer player, @NotNull Skin skin, @NotNull List<ProxiedPlayer> viewers){
        Condition.argNotNull("player", player);
        Condition.argNotNull("skin", skin);
        Condition.argNotNull("viewers", viewers);
        try {
            throw new OperationNotSupportedException();
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
        // TODO Add change skin for proxy side
    }
}
